
package pt.utp.estg.pp.printhub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.utp.estg.pp.printhub.dto.MaterialDTO;
import pt.utp.estg.pp.printhub.service.MaterialService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/admin/materiais")
public class AdminMaterialController {

    private final MaterialService materialService;

    public AdminMaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    // Criar material
    @PostMapping
    public ResponseEntity<MaterialDTO> createMaterial(
            @RequestBody MaterialDTO dto) {

        try {
            MaterialDTO created = materialService.createMaterial(dto);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(created);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Editar material
    @PutMapping("/{id}")
    public ResponseEntity<MaterialDTO> updateMaterial(
            @PathVariable Integer id,
            @RequestBody MaterialDTO dto) {

        try {
            MaterialDTO updated = materialService.updateMaterial(id, dto);

            return ResponseEntity.ok(updated);

        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Desativar material
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(
            @PathVariable Integer id) {

        try {
            materialService.deleteMaterial(id);

            return ResponseEntity.noContent().build();

        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
