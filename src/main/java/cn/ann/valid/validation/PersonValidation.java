package cn.ann.valid.validation;

import cn.ann.valid.validator.PersonValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Description：person 校验
 * <p>
 * Date: 2020-9-21 16:30
 *
 * @author 88475
 * @version v1.0
 */
@Documented
@Constraint(validatedBy = PersonValidator.class)
// 需要加在类上面，所以添加 TYPE
@Target({TYPE, METHOD, FIELD})
@Retention(RUNTIME)
public @interface PersonValidation {
    String message() default "出生日期与年龄不匹配";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        BirthdayValidation[] value();
    }
}
