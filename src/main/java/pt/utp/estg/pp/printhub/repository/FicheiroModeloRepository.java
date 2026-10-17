package pt.utp.estg.pp.printhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.utp.estg.pp.printhub.model.FicheiroModelo;

import java.util.List;

public interface FicheiroModeloRepository extends JpaRepository<FicheiroModelo, Integer> {

    List<FicheiroModelo> findByUtilizadorId(Integer utilizadorId);
}