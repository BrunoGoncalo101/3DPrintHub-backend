package pt.utp.estg.pp.printhub.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "linhas_encomenda")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "encomenda_id", nullable = false)
    private Order order;

    // Preenchido quando a linha é um produto do catálogo
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Product product;

    // TODO: trocar por @ManyToOne quando a tabela de pedidos (Bruno) existir
    @Column(name = "pedido_id")
    private Integer requestId;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @ManyToOne
    @JoinColumn(name = "cor_id", nullable = false)
    private Color color;

    @Column(name = "quantidade", nullable = false)
    private Integer quantity;

    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    public OrderLine() {
    }

    public Integer getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}