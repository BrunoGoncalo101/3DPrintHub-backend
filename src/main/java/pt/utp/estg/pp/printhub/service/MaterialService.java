
package pt.utp.estg.pp.printhub.service;

import org.springframework.stereotype.Service;
import pt.utp.estg.pp.printhub.dto.MaterialDTO;
import pt.utp.estg.pp.printhub.entity.Material;
import pt.utp.estg.pp.printhub.repository.MaterialRepository;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    // Criar material
    public MaterialDTO createMaterial(MaterialDTO dto) {

        validateMaterial(dto);

        String name = dto.name().trim();

        if (materialRepository.existsByNameIgnoreCase(name)) {
            throw new IllegalArgumentException(
                    "Já existe um material com este nome."
            );
        }

        Material material = new Material();

        material.setName(name);
        material.setPricePerGram(dto.pricePerGram());
        material.setDensity(dto.density());
        material.setActive(true);

        Material saved = materialRepository.save(material);

        return toDTO(saved);
    }

    // Editar material
    public MaterialDTO updateMaterial(Integer id, MaterialDTO dto) {

        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Material não encontrado."
                ));

        validateMaterial(dto);

        String name = dto.name().trim();

        if (materialRepository.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new IllegalArgumentException(
                    "Já existe outro material com este nome."
            );
        }

        material.setName(name);
        material.setPricePerGram(dto.pricePerGram());
        material.setDensity(dto.density());

        if (dto.active() != null) {
            material.setActive(dto.active());
        }

        Material updated = materialRepository.save(material);

        return toDTO(updated);
    }

    // Desativar material
    public void deleteMaterial(Integer id) {

        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Material não encontrado."
                ));

        material.setActive(false);

        materialRepository.save(material);
    }

    // Validar os dados
    private void validateMaterial(MaterialDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException(
                    "Os dados do material são obrigatórios."
            );
        }

        if (dto.name() == null || dto.name().isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do material é obrigatório."
            );
        }

        if (dto.name().trim().length() > 100) {
            throw new IllegalArgumentException(
                    "O nome não pode ultrapassar 100 caracteres."
            );
        }

        if (dto.pricePerGram() == null
                || dto.pricePerGram().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "O preço por grama deve ser superior a zero."
            );
        }

        if (dto.pricePerGram().scale() > 4
                || dto.pricePerGram().precision()
                - dto.pricePerGram().scale() > 6) {
            throw new IllegalArgumentException(
                    "O preço por grama deve respeitar DECIMAL(10,4)."
            );
        }

        if (dto.density() == null
                || dto.density().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "A densidade deve ser superior a zero."
            );
        }

        if (dto.density().scale() > 3
                || dto.density().precision()
                - dto.density().scale() > 7) {
            throw new IllegalArgumentException(
                    "A densidade deve respeitar DECIMAL(10,3)."
            );
        }
    }

    // Converter entidade para DTO
    private MaterialDTO toDTO(Material material) {

        return new MaterialDTO(
                material.getId(),
                material.getName(),
                material.getPricePerGram(),
                material.getDensity(),
                material.getActive()
        );
    }
}
