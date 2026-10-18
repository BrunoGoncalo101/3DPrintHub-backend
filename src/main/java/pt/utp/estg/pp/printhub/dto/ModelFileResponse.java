package pt.utp.estg.pp.printhub.dto;

import pt.utp.estg.pp.printhub.entity.ModelFile;

import java.time.LocalDateTime;

/** O que a API devolve depois de um upload. Não inclui o caminho interno do servidor. */
public record ModelFileResponse(
        Integer id,
        String originalName,
        String format,
        Integer sizeBytes,
        LocalDateTime createdAt
) {

    public static ModelFileResponse from(ModelFile modelFile) {
        return new ModelFileResponse(
                modelFile.getId(),
                modelFile.getOriginalName(),
                modelFile.getFormat().getDbValue(),
                modelFile.getSizeBytes(),
                modelFile.getCreatedAt()
        );
    }
}