package cn.ann.config;

import cn.ann.response.ResponseResult;
import cn.ann.util.ValidatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description：web 配置
 * <p>
 * Date: 2020-9-22 21:45
 *
 * @author 88475
 * @version v1.0
 */
@Slf4j
@Configurable
@RestControllerAdvice
public class WebConfiguration {
    /**
     * 定义全局异常处理器.
     *
     * @param e 当前平台异常参数对象.
     * @return 异常响应结果 {@link ResponseResult<Void>}
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<Void> validateErrorHandler(MethodArgumentNotValidException e) {
        log.error(e.getClass().getName());
        log.error(e.getMessage());
        BindingResult result = e.getBindingResult();
        String errorMessage = "数据校验出错：";
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            String errorStr = errorMessages.toString();
            errorMessage += errorStr.substring(1, errorStr.length() - 1);
        }
        return ResponseResult.error(errorMessage);
    }

    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    public ResponseResult<Void> validateErrorHandler(ValidationException e) {
        log.error(e.getClass().getName());
        log.error(e.getMessage());
        String errorMessage = "数据校验出错：" + e.getMessage();
        return ResponseResult.error(errorMessage);
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult<Void> validateErrorHandler(ConstraintViolationException e) {
        log.error(e.getClass().getName());
        log.error(e.getMessage());
        String errorMessage = "数据校验出错：" +
                ValidatorUtils.getConstraintViolationMessageNoPathNoTail(e.getConstraintViolations(), ", ");
        return ResponseResult.error(errorMessage);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseResult<Void> validateErrorHandler(Exception e) {
        log.error(e.getClass().getName());
        log.error(e.getMessage());
        return ResponseResult.error(e.getMessage());
    }

}
