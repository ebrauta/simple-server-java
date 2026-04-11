package github.ebrauta.core.http;

public enum HttpStatus {
    OK(200),
    CREATED(201),
    NO_CONTENT(204),
    NOT_FOUND(404),
    BAD_REQUEST(400),
    INTERNAL_SERVER_ERROR(500);

    private  final int code;
    HttpStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
