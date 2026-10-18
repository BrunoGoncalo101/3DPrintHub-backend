package pt.utp.estg.pp.printhub.dto;

/** Formato único dos erros da API: {"error": "mensagem", "code": 400}. */
public record ErrorResponse(String error, int code) {
}