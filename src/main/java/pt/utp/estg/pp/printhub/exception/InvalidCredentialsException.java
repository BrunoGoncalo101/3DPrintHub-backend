package pt.utp.estg.pp.printhub.exception;

/** Credenciais erradas ou sessão inválida. Devolve 401. */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Email ou palavra-passe incorretos.");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
