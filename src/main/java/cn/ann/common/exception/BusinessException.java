package cn.ann.common.exception;

/**
 * Description：业务异常类
 * <p>
 * Date: 2020-10-23 16:38
 *
 * @author 88475
 * @version v1.0
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -3665165768436994851L;

    private BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }
}
