package ua.social.network.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Mykola Yashchenko
 */
public enum UserServiceExceptionDetails implements SnExceptionDetails {

    NOT_FOUND("US-001", HttpStatus.NOT_FOUND),
    FRIEND_REQUEST_HAS_SENT_ALREADY("US-002", HttpStatus.FORBIDDEN),
    CANNOT_MODIFY_USER("US-003", HttpStatus.FORBIDDEN),
    IMAGE_TYPE_IS_NOT_SUPPORTED("US-004", HttpStatus.BAD_REQUEST);

    private final String code;
    private final HttpStatus status;

    UserServiceExceptionDetails(final String code, final HttpStatus status) {
        this.code = code;
        this.status = status;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public HttpStatus status() {
        return status;
    }
}
