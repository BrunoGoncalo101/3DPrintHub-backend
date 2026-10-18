
package pt.utp.estg.pp.printhub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.utp.estg.pp.printhub.entity.Category;
import pt.utp.estg.pp.printhub.repository.CategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }
}
