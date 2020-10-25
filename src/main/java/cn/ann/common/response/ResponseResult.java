package cn.ann.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

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
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private E data;

    private ResponseResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private ResponseResult(ResponseCode status, String message) {
        this.status = status.code;
        this.message = message;
    }

    private ResponseResult(int status, Exception ex) {
        this.status = status;
        this.message = ex.getMessage();
    }

    private ResponseResult(ResponseCode status, Exception ex) {
        this.status = status.code;
        this.message = ex.getMessage();
    }

    private ResponseResult(int status, String message, E data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    private ResponseResult(ResponseCode status, String message, E data) {
        this.status = status.code;
        this.message = message;
        this.data = data;
    }

    public static <E> ResponseResult<E> ok(String message) {
        return new ResponseResult<>(ResponseCode.OK, message);
    }

    public static <E> ResponseResult<E> ok(String message, E data) {
        return new ResponseResult<>(ResponseCode.OK, message, data);
    }

    public static <E> ResponseResult<E> error(ResponseCode code, String message) {
        return new ResponseResult<>(code, message);
    }

    public static <E> ResponseResult<E> error(ResponseCode code, String message, E data) {
        return new ResponseResult<>(code, message, data);
    }

    public enum ResponseCode {
        // OK
        OK(20000),
        // 业务错误
        BUSINESS_ERROR(50002),
        // 系统错误
        SYSTEM_ERROR(50000),
        // 请求参数不正确
        BAD_REQUEST(40000),
        // 请求未发现
        REQUEST_NOT_FOUNT(40004),
        ;

        int code;

        ResponseCode(int code) {
            this.code = code;
        }
    }

}
