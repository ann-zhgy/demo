package cn.ann.web;

import cn.ann.bean.Person;
import cn.ann.response.ResponseResult;
import cn.ann.valid.group.Update;
import cn.ann.valid.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description：web 接口
 * <p>
 * Date: 2020-9-21 13:03
 *
 * @author 88475
 * @version v1.0
 */
@Slf4j
@RestController
public class HibernateValidateDemoController {
    @Resource(name = "personService")
    private PersonService service;

    @PostMapping(value = "test1")
    public ResponseResult<Person> test(@RequestBody @Validated({Update.class}) Person person) {
        log.info("service = {}", service);
        return ResponseResult.ok(person);
    }

    @PostMapping(value = "test2")
    public ResponseResult<Person> test(@RequestBody @Validated Person person, BindingResult result) {
        // 如果定义全局异常处理器的话，就不用 BindingResult 了
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseResult.error(errorMessages.toString(), person);
        }
        return ResponseResult.ok(person);
    }

    /**
     * 定义参数异常处理器.
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
