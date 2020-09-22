package cn.ann.valid.validation;

import cn.ann.valid.validator.BirthdayValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Description：出生日期验证
 * 注解里面的内容 抄 其他注解的就行了
 * <p>
 * Date: 2020-9-21 14:48
 *
 * @author 88475
 * @version v1.0
 */
@Documented
// BirthdayValidator 是验证的逻辑实现
@Constraint(validatedBy = BirthdayValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
// 使用 @Repeatable 注解时， @List 的修饰范围不能超过 @BirthdayValidation
@Repeatable(BirthdayValidation.List.class)
public @interface BirthdayValidation {
    /**
     * 错误提示信息，可以写死,也可以填写国际化的key
     */
    String message() default "出生日期有误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        BirthdayValidation[] value();
    }
}
