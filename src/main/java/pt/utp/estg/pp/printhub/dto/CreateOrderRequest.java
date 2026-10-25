package pt.utp.estg.pp.printhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateOrderRequest(
        @NotBlank @Size(max = 255) String shippingAddress
) {
}