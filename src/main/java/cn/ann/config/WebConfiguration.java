package cn.ann.config;

import cn.ann.common.exception.BusinessException;
import cn.ann.common.response.ResponseResult;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Description：web全局异常配置
 * <p>
 * Date: 2020-10-23 17:02
 *
 * @author 88475
 * @version v1.0
 */
@Configuration
@RestControllerAdvice
public class WebConfiguration {
    @ExceptionHandler({BusinessException.class})
    public ResponseResult<Void> handle(BusinessException ex) {
        ex.printStackTrace();
        return ResponseResult.error(ResponseResult.ResponseCode.BUSINESS_ERROR, ex.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseResult<Void> handle(Exception ex) {
        ex.printStackTrace();
        return ResponseResult.error(ResponseResult.ResponseCode.SYSTEM_ERROR, ex.getMessage());
    }

}
