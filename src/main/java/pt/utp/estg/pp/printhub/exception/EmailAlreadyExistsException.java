package pt.utp.estg.pp.printhub.exception;

/** Lançada quando se tenta registar um email que já existe. Devolve 409. */
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("O email " + email + " já está registado.");
    }
}
