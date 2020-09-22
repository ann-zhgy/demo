package cn.ann.web;

import cn.ann.bean.Person;
import cn.ann.response.ResponseResult;
import cn.ann.service.PersonService;
import cn.ann.valid.group.GroupOrder;
import cn.ann.valid.group.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseResult<Person> test1(@RequestBody @Validated({Update.class}) Person person) {
        log.info("service = {}", service);
        return ResponseResult.ok(person);
    }

    @PostMapping(value = "test2")
    public ResponseResult<Person> test2(@RequestBody @Validated Person person, BindingResult result) {
        // 如果定义全局异常处理器的话，就不用 BindingResult 了
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseResult.error(errorMessages.toString(), person);
        }
        return ResponseResult.ok(person);
    }

    @PostMapping(value = "test3")
    public ResponseResult<Person> test3(@RequestBody @Validated({GroupOrder.class}) Person person) {
        log.info("service = {}", service);
        return ResponseResult.ok(person);
    }

}
