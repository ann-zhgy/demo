package cn.ann.valid.validator;

import cn.ann.valid.validation.BirthdayValidation;
import lombok.extern.slf4j.Slf4j;

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
    /**
     * 初始化方法，可以做一些初始化操作
     *
     * @param constraintAnnotation {@link BirthdayValidation}
     */
    @Override
    public void initialize(BirthdayValidation constraintAnnotation) {
        log.info("BirthdayValidation 初始化");
    }

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
        return value != null && value.isBefore(LocalDate.now());
    }
}
