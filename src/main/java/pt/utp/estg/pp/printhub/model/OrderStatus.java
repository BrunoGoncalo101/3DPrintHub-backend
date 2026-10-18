package pt.utp.estg.pp.printhub.model;

public enum OrderStatus {
    PENDING_PAYMENT,   // Por pagar
    PAID,              // Paga
    PRINTING,          // Em impressão
    COMPLETED,         // Concluída
    SHIPPED,           // Enviada
    CANCELLED          // Cancelada
}