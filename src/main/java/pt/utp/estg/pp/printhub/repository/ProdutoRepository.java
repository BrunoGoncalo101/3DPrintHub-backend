
package pt.utp.estg.pp.printhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.utp.estg.pp.printhub.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
