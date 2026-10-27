
package pt.utp.estg.pp.printhub.service;

import org.springframework.stereotype.Service;
import pt.utp.estg.pp.printhub.dto.CategoryDTO;
import pt.utp.estg.pp.printhub.entity.Category;
import pt.utp.estg.pp.printhub.repository.CategoryRepository;

import java.util.List;
import java.util.NoSuchElementException;

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

    public CategoryDTO createCategory(String name) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "O nome da categoria é obrigatório."
            );
        }

        String cleanName = name.trim();

        if (categoryRepository.existsByNameIgnoreCase(cleanName)) {
            throw new IllegalArgumentException(
                    "Já existe uma categoria com este nome."
            );
        }

        Category category = new Category();
        category.setName(cleanName);
        category.setActive(true);

        Category savedCategory = categoryRepository.save(category);

        return toDTO(savedCategory);
    }


    public CategoryDTO updateCategory(Integer id, CategoryDTO categoryDTO) {


        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Categoria não encontrada."
                ));

        // Validar o nome recebido
        if (categoryDTO == null ||
                categoryDTO.name() == null ||
                categoryDTO.name().isBlank()) {
            throw new IllegalArgumentException(
                    "O nome da categoria é obrigatório."
            );
        }

        String cleanName = categoryDTO.name().trim();

        // nao deixa ter nomes duplicados
        if (!category.getName().equalsIgnoreCase(cleanName)
                && categoryRepository.existsByNameIgnoreCase(cleanName)) {
            throw new IllegalArgumentException(
                    "Já existe uma categoria com este nome."
            );
        }

        // aatualiza os dados
        category.setName(cleanName);

        if (categoryDTO.active() != null) {
            category.setActive(categoryDTO.active());
        }

        // guarda as alterações
        Category updatedCategory = categoryRepository.save(category);

        return toDTO(updatedCategory);
    }


    public void deleteCategory(Integer id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Categoria não encontrada."
                ));

        category.setActive(false);
        categoryRepository.save(category);
    }




    private CategoryDTO toDTO(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getActive()
        );
    }
}
