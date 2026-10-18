package pt.utp.estg.pp.printhub.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "materiais")
public class Material {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

     @Column(nullable = false, length = 100)
    private String nome;

     @Column(name="preco_por_grama", nullable = false, precision = 10, scale = 4)
    private BigDecimal precoPorGrama;

     @Column(name = "densidade", nullable = false, precision = 10, scale = 3)
    private BigDecimal densidade;

     @Column(name="ativo", nullable = false)
     private Boolean ativo = true;

    public Material() {
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

    public BigDecimal getPrecoPorGrama() {
        return precoPorGrama;
    }

    public void setPrecoPorGrama(BigDecimal precoPorGrama) {
        this.precoPorGrama = precoPorGrama;
    }

    public BigDecimal getDensidade() {
        return densidade;
    }

    public void setDensidade(BigDecimal densidade) {
        this.densidade = densidade;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
