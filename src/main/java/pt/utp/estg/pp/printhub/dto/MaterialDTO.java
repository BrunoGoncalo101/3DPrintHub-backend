
package pt.utp.estg.pp.printhub.dto;

import java.math.BigDecimal;

public record MaterialDTO(
        Integer id,
        String name,
        BigDecimal pricePerGram,
        BigDecimal density,
        Boolean active
) {
}
