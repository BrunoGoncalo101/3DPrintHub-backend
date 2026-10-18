
package pt.utp.estg.pp.printhub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.utp.estg.pp.printhub.entity.Material;
import pt.utp.estg.pp.printhub.repository.MaterialRepository;

import java.util.List;

@RestController
@RequestMapping("/materiais")
public class MaterialController {

    private final MaterialRepository materialRepository;

    public MaterialController(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @GetMapping
    public List<Material> listMaterials() {
        return materialRepository.findAll();
    }
}
