
package pt.utp.estg.pp.printhub.service;

import org.springframework.stereotype.Service;
import pt.utp.estg.pp.printhub.dto.CategoryDTO;
import pt.utp.estg.pp.printhub.entity.Category;
import pt.utp.estg.pp.printhub.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> listCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private CategoryDTO toDTO(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getActive()
        );
    }
}
