package github.ebrauta.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super("Erro de Validação: " + message);
    }
}
