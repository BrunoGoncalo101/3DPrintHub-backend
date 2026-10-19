package pt.utp.estg.pp.printhub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pt.utp.estg.pp.printhub.dto.AddCartItemRequest;
import pt.utp.estg.pp.printhub.dto.CartResponse;
import pt.utp.estg.pp.printhub.service.CartService;

@RestController
@RequestMapping("/carrinho")
@Tag(name = "Carrinho", description = "Gerir o carrinho do cliente com sessão iniciada")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @Operation(summary = "Ver o carrinho", description = "Devolve os itens do carrinho e o total.")
    public CartResponse getCart(@AuthenticationPrincipal Jwt jwt) {
        return cartService.getCart(currentUserId(jwt));
    }

    @PostMapping("/itens")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adicionar ao carrinho",
            description = "Se o produto já estiver no carrinho com o mesmo material e cor, soma a quantidade (máx. 10).")
    public CartResponse addItem(@AuthenticationPrincipal Jwt jwt,
                                @Valid @RequestBody AddCartItemRequest request) {
        return cartService.addItem(currentUserId(jwt), request);
    }

    @DeleteMapping("/itens/{id}")
    @Operation(summary = "Remover do carrinho", description = "Remove um item do carrinho do próprio cliente.")
    public CartResponse removeItem(@AuthenticationPrincipal Jwt jwt,
                                   @PathVariable Integer id) {
        return cartService.removeItem(currentUserId(jwt), id);
    }

    /** Lê o id do utilizador a partir do token. Sem token, responde 401. */
    private Integer currentUserId(Jwt jwt) {
        if (jwt == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "É preciso iniciar sessão.");
        }
        return Integer.valueOf(jwt.getSubject());
    }
}