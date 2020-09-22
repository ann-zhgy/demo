package cn.ann.valid.validator;

import cn.ann.service.PersonService;
import cn.ann.valid.validation.BirthdayValidation;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * Description：出生日期验证器
 * <p>
 * Date: 2020-9-21 14:49
 *
 * @author 88475
 * @version v1.0
 */
@Slf4j
public class BirthdayValidator implements ConstraintValidator<BirthdayValidation, LocalDate> {
    @Resource(name = "personService")
    private PersonService service;

    /**
     * 检验方法。此处校验出生日期和年龄是否匹配
     *
     * @param value   出生日期的值
     * @param context context
     * @return true：校验通过 | false：校验不通过
     */
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        log.info("BirthdayValidation 校验中……");
        log.info("value: {}", value);
        log.info("service: {}", service);
        log.info("test: {}", service.test("person service!"));
        return value != null && value.isBefore(LocalDate.now());
    }
}
