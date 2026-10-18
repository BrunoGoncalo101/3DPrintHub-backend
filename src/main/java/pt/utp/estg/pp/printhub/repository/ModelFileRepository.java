package pt.utp.estg.pp.printhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.utp.estg.pp.printhub.entity.ModelFile;

import java.util.List;

public interface ModelFileRepository extends JpaRepository<ModelFile, Integer> {

    List<ModelFile> findByUserId(Integer userId);
}