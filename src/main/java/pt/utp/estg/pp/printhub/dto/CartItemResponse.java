package pt.utp.estg.pp.printhub.dto;

import java.math.BigDecimal;

public record CartItemResponse(
        Integer id,
        Integer productId,
        String productName,
        String materialName,
        String colorName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal
) {
}