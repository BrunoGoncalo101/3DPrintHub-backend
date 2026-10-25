package pt.utp.estg.pp.printhub.dto;

import java.math.BigDecimal;

public record QuoteResponse (
    Integer fileId,
    String originalName,
    BigDecimal volumeCm3,
    Integer materialId,
    String materialName,
    int infillPercent,
    BigDecimal estimatedWeightGrams,
    BigDecimal totalPrice
) {}
