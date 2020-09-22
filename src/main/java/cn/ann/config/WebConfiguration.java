package cn.ann.config;

import cn.ann.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
     * @return org.springframework.http.ResponseEntity
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<Void> validateErrorHandler(MethodArgumentNotValidException e) {
        log.warn(e.getMessage());
        BindingResult result = e.getBindingResult();
        String errorMessage = "数据校验出错";
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            String errorStr = errorMessages.toString();
            errorMessage = errorStr.substring(1, errorStr.length() - 1);
        }
        return ResponseResult.error(errorMessage);
    }

}
