
package pt.utp.estg.pp.printhub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.utp.estg.pp.printhub.dto.CategoryDTO;
import pt.utp.estg.pp.printhub.service.CategoryService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/admin/categorias")
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(
            @RequestBody CategoryDTO categoryDTO) {

        try {
            // Envia o nome da categoria para o Service
            CategoryDTO savedCategory =
                    categoryService.createCategory(categoryDTO.name());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedCategory);

        } catch (IllegalArgumentException e) {

            // Devolve quando o nome é inválido
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Integer id) {

        try {
            categoryService.deleteCategory(id);

            return ResponseEntity.noContent().build();

        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Integer id,
            @RequestBody CategoryDTO dto) {

        try {
            CategoryDTO updated = categoryService.updateCategory(id, dto);

            return ResponseEntity.ok(updated);

        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
