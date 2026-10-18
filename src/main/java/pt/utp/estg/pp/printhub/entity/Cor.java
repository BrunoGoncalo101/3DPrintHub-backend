
package pt.utp.estg.pp.printhub.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cores")
public class Cor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "codigo_hex", nullable = false, length = 7)
    private String codigoHex;

    @Column(name = "disponivel", nullable = false)
    private Boolean disponivel = true;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    public Cor() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigoHex() {
        return codigoHex;
    }

    public void setCodigoHex(String codigoHex) {
        this.codigoHex = codigoHex;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
