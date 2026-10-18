
package pt.utp.estg.pp.printhub.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cores")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 100)
    private String name;

    @Column(name = "codigo_hex", nullable = false, length = 7)
    private String hexCode;

    @Column(name = "disponivel", nullable = false)
    private Boolean available = true;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    public Color() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHexCode() {
        return hexCode;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
