package pt.utp.estg.pp.printhub.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pt.utp.estg.pp.printhub.exception.InvalidFileException;
import pt.utp.estg.pp.printhub.entity.ModelFile;
import pt.utp.estg.pp.printhub.exception.FileStorageException;
import pt.utp.estg.pp.printhub.model.FileFormat;
import pt.utp.estg.pp.printhub.repository.ModelFileRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class ModelFileService {

    private static final long MAX_FILE_SIZE_BYTES = 50L * 1024 * 1024; // 50 MB
    private static final int MAX_FILE_NAME_LENGTH = 255;                // coluna nome_original

    private final ModelFileRepository repository;
    private final Path uploadPath;

    public ModelFileService(ModelFileRepository repository,
                            @Value("${app.uploads.dir}") String uploadPath) {
        this.repository = repository;
        this.uploadPath = Path.of(uploadPath).toAbsolutePath().normalize();
    }

    /**
     * Valida o ficheiro, grava-o em uploads/ com um nome único e regista-o na base de dados.
     * O campo "filePath" guarda o nome gerado do ficheiro dentro da pasta uploads/.
     */
    public ModelFile save(MultipartFile file, Integer userId) throws InvalidFileException {
        if (file == null || file.isEmpty()) {
            throw new InvalidFileException("O ficheiro está vazio ou não foi enviado.");
        }
        if (userId == null) {
            throw new InvalidFileException("O ID do utilizador é obrigatório.");
        }

        String originalName = sanitizeFileName(file.getOriginalFilename());

        FileFormat format = FileFormat.fromFileName(originalName);
        if (format == null) {
            throw new InvalidFileException("Formato não suportado. Por favor use .stl, .3mf ou .obj.");
        }

        if (file.getSize() > MAX_FILE_SIZE_BYTES) {
            throw new InvalidFileException("O ficheiro excede o limite máximo de 50 MB.");
        }

        // Nome único gerado: o nome original do cliente nunca é guardado diretamente no disco
        String savedFileName = UUID.randomUUID() + "." + format.getDbValue().toLowerCase();
        Path targetPath = uploadPath.resolve(savedFileName).normalize();
        if (!targetPath.startsWith(uploadPath)) {
            throw new InvalidFileException("Caminho de ficheiro inválido.");
        }

        saveToDisk(file, targetPath);

        ModelFile modelFile = new ModelFile();
        modelFile.setOriginalName(originalName);
        modelFile.setFilePath(savedFileName);
        modelFile.setSizeBytes((int) file.getSize());
        modelFile.setFormat(FileFormat.valueOf(String.valueOf(format)));
        modelFile.setUserId(userId);

        try {
            return repository.save(modelFile);
        } catch (RuntimeException e) {
            deleteQuietly(targetPath); // Evita ficheiros órfãos no disco se a BD falhar
            throw e;
        }
    }

    private void saveToDisk(MultipartFile file, Path targetPath) {
        try {
            Files.createDirectories(uploadPath);
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetPath);
            }
        } catch (IOException e) {
            throw new FileStorageException("Não foi possível guardar o ficheiro no disco.", e);
        }
    }

    private void deleteQuietly(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {
            // Limpeza em modo best-effort
        }
    }

    /** Extrai apenas o nome do ficheiro (sem o caminho de pastas) e garante que cabe na coluna. */
    private static String sanitizeFileName(String fileName) throws InvalidFileException {
        if (fileName == null || fileName.isBlank()) {
            throw new InvalidFileException("O nome do ficheiro não pode estar vazio.");
        }
        String cleanPath = StringUtils.cleanPath(fileName);
        int lastSeparator = Math.max(cleanPath.lastIndexOf('/'), cleanPath.lastIndexOf('\\'));
        String nameOnly = (lastSeparator >= 0 ? cleanPath.substring(lastSeparator + 1) : cleanPath).trim();

        if (nameOnly.isEmpty()) {
            throw new InvalidFileException("O nome do ficheiro não pode estar vazio.");
        }
        if (nameOnly.length() > MAX_FILE_NAME_LENGTH) {
            throw new InvalidFileException("O nome do ficheiro é demasiado longo.");
        }
        return nameOnly;
    }
}