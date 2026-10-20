package pt.utp.estg.pp.printhub.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddCartItemRequest(
        @NotNull Integer productId,
        @NotNull Integer materialId,
        @NotNull Integer colorId,
        @NotNull @Min(1) @Max(10) Integer quantity
) {
}