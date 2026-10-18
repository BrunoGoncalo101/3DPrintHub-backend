
package pt.utp.estg.pp.printhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.utp.estg.pp.printhub.entity.Category;

// o JPA vai dar varios metodos tipo, findALL, findbyid, save, deletebyid

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
