package cn.ann.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Description：响应结果
 * <p>
 * Date: 2020-9-21 13:06
 *
 * @author 88475
 * @version v1.0
 */
@Data
public class ResponseResult<E> implements Serializable {
    private static final long serialVersionUID = 7508974068619612170L;

    private final int status;
    private final String message;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private E data;

    private ResponseResult(HttpStatus status) {
        this.status = status.value();
        this.message = status.getReasonPhrase();
    }

    private ResponseResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private ResponseResult(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }

    private ResponseResult(int status, Exception ex) {
        this.status = status;
        this.message = ex.getMessage();
    }

    private ResponseResult(HttpStatus status, Exception ex) {
        this.status = status.value();
        this.message = ex.getMessage();
    }

    private ResponseResult(HttpStatus status, E data) {
        this.status = status.value();
        this.message = status.getReasonPhrase();
        this.data = data;
    }

    private ResponseResult(int status, String message, E data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    private ResponseResult(HttpStatus status, String message, E data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }

    public static ResponseResult<Void> error() {
        return new ResponseResult<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseResult<Void> error(String message) {
        return new ResponseResult<>(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static <E> ResponseResult<E> error(E data) {
        return new ResponseResult<>(HttpStatus.INTERNAL_SERVER_ERROR, data);
    }

    public static <E> ResponseResult<E> error(String message, E data) {
        return new ResponseResult<>(HttpStatus.INTERNAL_SERVER_ERROR, message, data);
    }

    public static ResponseResult<Void> ok() {
        return new ResponseResult<>(HttpStatus.OK);
    }

    public static ResponseResult<Void> ok(String message) {
        return new ResponseResult<>(HttpStatus.OK, message);
    }

    public static <E> ResponseResult<E> ok(E data) {
        return new ResponseResult<>(HttpStatus.OK, data);
    }

    public static <E> ResponseResult<E> ok(String message, E data) {
        return new ResponseResult<>(HttpStatus.OK, message, data);
    }

}
