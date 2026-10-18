package pt.utp.estg.pp.printhub.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name= "ficheiros_modelo")
public class FicheiroModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome_original", nullable = false, length = 255)
    private String nomeOriginal;

    @Column(name = "caminho", nullable = false, length = 500)
    private String caminho;

    @Column(name = "tamanho_bytes", nullable = false)
    private int tamanhoBytes;

    @Convert(converter = FormatoFicheiroConverter.class)
    @Column(name = "formato", nullable = false, length = 45)
    private String formato;

    @Column(name = "volume_cm3", precision = 10, scale = 2)
    private BigDecimal volumeCm3;

    @Column(name = "utilizador_id", nullable = false)
    private int utilizadorId;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    void aoCriar() {
        if(criadoEm == null){
            criadoEm = LocalDateTime.now();
        }

    }

    public Integer getId() { return id; }

    public String getNomeOriginal() { return nomeOriginal; }
    public void setNomeOriginal(String nomeOriginal) { this.nomeOriginal = nomeOriginal; }

    public String getCaminho() { return caminho; }
    public void setCaminho(String caminho) { this.caminho = caminho; }

    public Integer getTamanhoBytes() { return tamanhoBytes; }
    public void setTamanhoBytes(Integer tamanhoBytes) { this.tamanhoBytes = tamanhoBytes; }

    public String getFormato() { return formato; }
    public void setFormato(String formato) { this.formato = formato; }

    public BigDecimal getVolumeCm3() { return volumeCm3; }
    public void setVolumeCm3(BigDecimal volumeCm3) { this.volumeCm3 = volumeCm3; }

    public Integer getUtilizadorId() { return utilizadorId; }
    public void setUtilizadorId(Integer utilizadorId) { this.utilizadorId = utilizadorId; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
}
