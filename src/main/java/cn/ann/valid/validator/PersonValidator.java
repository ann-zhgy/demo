package cn.ann.valid.validator;

import cn.ann.bean.Person;
import cn.ann.valid.validation.PersonValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * Description：person 校验器
 * <p>
 * Date: 2020-9-21 16:30
 *
 * @author 88475
 * @version v1.0
 */
public class PersonValidator implements ConstraintValidator<PersonValidation, Person> {
    @Override
    public boolean isValid(Person value, ConstraintValidatorContext context) {
        if (value.getAge() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("age 不能为空").addConstraintViolation();

            return false;
        }
        if (value.getBirthday() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("birthday 不能为空").addConstraintViolation();

            return false;
        }

        return value.getBirthday().isBefore(LocalDate.now().minusYears(value.getAge()));
    }
}
