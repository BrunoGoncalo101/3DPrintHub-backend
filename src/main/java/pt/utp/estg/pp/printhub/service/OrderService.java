package pt.utp.estg.pp.printhub.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pt.utp.estg.pp.printhub.dto.CreateOrderRequest;
import pt.utp.estg.pp.printhub.dto.OrderLineResponse;
import pt.utp.estg.pp.printhub.dto.OrderResponse;
import pt.utp.estg.pp.printhub.entity.CartItem;
import pt.utp.estg.pp.printhub.entity.Order;
import pt.utp.estg.pp.printhub.entity.OrderLine;
import pt.utp.estg.pp.printhub.repository.CartItemRepository;
import pt.utp.estg.pp.printhub.repository.OrderRepository;
import pt.utp.estg.pp.printhub.repository.UserRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        CartItemRepository cartItemRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    // Cria uma encomenda com o que está no carrinho e depois esvazia o carrinho
    @Transactional
    public OrderResponse createFromCart(Integer userId, CreateOrderRequest request) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O carrinho está vazio.");
        }

        Order order = new Order();
        order.setUser(userRepository.getReferenceById(userId));
        order.setShippingAddress(request.shippingAddress());

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cartItems) {
            if (!Boolean.TRUE.equals(item.getProduct().getActive())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "O produto " + item.getProduct().getName() + " já não está disponível.");
            }

            OrderLine line = new OrderLine();
            line.setProduct(item.getProduct());
            line.setMaterial(item.getMaterial());
            line.setColor(item.getColor());
            line.setQuantity(item.getQuantity());
            line.setUnitPrice(item.getProduct().getBasePrice());
            order.addLine(line);

            BigDecimal subtotal = line.getUnitPrice().multiply(BigDecimal.valueOf(line.getQuantity()));
            total = total.add(subtotal);
        }

        order.setTotal(total);
        Order saved = orderRepository.save(order);

        cartItemRepository.deleteAll(cartItems);

        return toResponse(saved);
    }

    private OrderResponse toResponse(Order order) {
        List<OrderLineResponse> lines = new ArrayList<>();

        for (OrderLine line : order.getLines()) {
            BigDecimal subtotal = line.getUnitPrice().multiply(BigDecimal.valueOf(line.getQuantity()));
            lines.add(new OrderLineResponse(
                    line.getProduct().getId(),
                    line.getProduct().getName(),
                    line.getMaterial().getName(),
                    line.getColor().getName(),
                    line.getQuantity(),
                    line.getUnitPrice(),
                    subtotal
            ));
        }

        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                order.getTotal(),
                order.getShippingAddress(),
                order.getCreatedAt(),
                lines
        );
    }
}