package pt.utp.estg.pp.printhub.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pt.utp.estg.pp.printhub.dto.AddCartItemRequest;
import pt.utp.estg.pp.printhub.dto.CartItemResponse;
import pt.utp.estg.pp.printhub.dto.CartResponse;
import pt.utp.estg.pp.printhub.entity.CartItem;
import pt.utp.estg.pp.printhub.entity.Color;
import pt.utp.estg.pp.printhub.entity.Material;
import pt.utp.estg.pp.printhub.entity.Product;
import pt.utp.estg.pp.printhub.repository.CartItemRepository;
import pt.utp.estg.pp.printhub.repository.ColorRepository;
import pt.utp.estg.pp.printhub.repository.MaterialRepository;
import pt.utp.estg.pp.printhub.repository.ProductRepository;
import pt.utp.estg.pp.printhub.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    private static final int MAX_QUANTITY = 10;

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final MaterialRepository materialRepository;
    private final ColorRepository colorRepository;
    private final UserRepository userRepository;

    public CartService(CartItemRepository cartItemRepository,
                       ProductRepository productRepository,
                       MaterialRepository materialRepository,
                       ColorRepository colorRepository,
                       UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.materialRepository = materialRepository;
        this.colorRepository = colorRepository;
        this.userRepository = userRepository;
    }

    /** Devolve o carrinho do utilizador, com o total. */
    @Transactional(readOnly = true)
    public CartResponse getCart(Integer userId) {
        List<CartItemResponse> items = cartItemRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .toList();

        BigDecimal total = items.stream()
                .map(CartItemResponse::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(items, total);
    }

    /** Adiciona um produto ao carrinho. Se já lá estiver, soma a quantidade. */
    @Transactional
    public CartResponse addItem(Integer userId, AddCartItemRequest request) {
        Product product = productRepository.findById(request.productId())
                .filter(p -> Boolean.TRUE.equals(p.getActive()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));

        Material material = materialRepository.findById(request.materialId())
                .filter(m -> Boolean.TRUE.equals(m.getActive()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Material não encontrado."));

        Color color = colorRepository.findById(request.colorId())
                .filter(c -> Boolean.TRUE.equals(c.getAvailable()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cor não encontrada."));

        if (!color.getMaterial().getId().equals(material.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A cor não pertence ao material escolhido.");
        }

        CartItem item = cartItemRepository
                .findByUserIdAndProductIdAndMaterialIdAndColorId(userId, product.getId(), material.getId(), color.getId())
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setUser(userRepository.getReferenceById(userId));
                    newItem.setProduct(product);
                    newItem.setMaterial(material);
                    newItem.setColor(color);
                    newItem.setQuantity(0);
                    return newItem;
                });

        int newQuantity = item.getQuantity() + request.quantity();
        if (newQuantity > MAX_QUANTITY) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Máximo de " + MAX_QUANTITY + " unidades por produto.");
        }

        item.setQuantity(newQuantity);
        cartItemRepository.save(item);
        return getCart(userId);
    }

    /** Remove um item do carrinho, só se pertencer ao utilizador. */
    @Transactional
    public CartResponse removeItem(Integer userId, Integer itemId) {
        CartItem item = cartItemRepository.findByIdAndUserId(itemId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado no carrinho."));

        cartItemRepository.delete(item);
        return getCart(userId);
    }

    /** Converte a entidade no DTO que a API devolve. */
    private CartItemResponse toResponse(CartItem item) {
        BigDecimal unitPrice = item.getProduct().getBasePrice();
        BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));

        return new CartItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getMaterial().getName(),
                item.getColor().getName(),
                item.getQuantity(),
                unitPrice,
                subtotal
        );
    }
}