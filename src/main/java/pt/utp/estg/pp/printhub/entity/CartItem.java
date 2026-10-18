package pt.utp.estg.pp.printhub.entity;

import jakarta.persistence.*;

@Entity
@Table(name="carrinho_itens")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // TODO: trocar por @ManyToOne User quando a tabela utilizadores (Miguel fazer) existir
    @Column(name = "utilizador_id", nullable = false)
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @ManyToOne
    @JoinColumn(name = "cor_id", nullable = false)
    private Color color;

    @Column(name = "quantidade", nullable = false)
    private Integer quantity;

    public CartItem() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
