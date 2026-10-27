
package pt.utp.estg.pp.printhub.dto;

import java.math.BigDecimal;

public record ProductDTO(
        Integer id,
        String name,
        String description,
        BigDecimal basePrice,
        String imageUrl,
        String dimensions,
        Boolean active,
        Integer categoryId
) {
}
