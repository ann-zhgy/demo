package cn.ann.util;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.internal.util.Contracts;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hibernate.validator.internal.util.logging.Messages.MESSAGES;

/**
 * Description：hibernate-validator 工具类
 * <p>
 * Date: 2020-10-16 12:47
 *
 * @author 88475
 * @version v1.0
 */
public class ValidatorUtils {
    private ValidatorUtils() {
    }

    /**
     * validator：线程安全的，直接构建也可以，这里使用静态代码块一样的效果
     */
    private static final Validator VALIDATOR;
    /**
     * validator_fast: 快速校验器，线程安全
     */
    private static final Validator VALIDATOR_FAST;

    static {
        VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
        VALIDATOR_FAST = Validation.byProvider(HibernateValidator.class)
                .configure()
                // failFast为true，则有一个错误就结束校验
                .failFast(true)
                .buildValidatorFactory().getValidator();
    }

    /**
     * 执行校验，不通过抛出异常
     *
     * @param entity 被校验的实体
     * @param groups 校验分组，不传入使用 Default.class
     */
    public static <T> void validate(T entity, Class<?>... groups) {
        validate(null, entity, "", groups);
    }

    /**
     * 执行校验，不通过抛出异常
     *
     * @param entity 被校验的实体
     * @param suffix 校验失败的消息之间的间隔
     * @param groups 校验分组，不传入使用 Default.class
     */
    public static <T> void validate(
            T entity, String suffix, Class<?>... groups) {
        validate(null, entity, suffix, groups);
    }

    /**
     * 使用自定义的校验器执行校验，不通过抛出异常
     *
     * @param entity 被校验的实体
     * @param groups 校验分组，不传入使用 Default.class
     */
    public static <T> void validate(Validator validator,
                                    T entity,
                                    Class<?>... groups) {
        validate(validator, entity, "", groups);
    }

    /**
     * 使用自定义的校验器执行校验，不通过抛出异常
     *
     * @param entity 被校验的实体
     * @param suffix 校验失败的消息间隔
     * @param groups 校验分组，不传入使用 Default.class
     */
    public static <T> void validate(Validator validator,
                                    T entity,
                                    String suffix,
                                    Class<?>... groups) {
        Contracts.assertNotNull(entity, MESSAGES.validatedObjectMustNotBeNull());
        Set<ConstraintViolation<T>> constraintViolations =
                getValidator(validator, false).validate(entity, getGroups(groups));
        if (constraintViolations.size() > 0) {
            String validateError = generateValidateMessage(constraintViolations, suffix);

            throw new ValidationException(validateError);
        }
    }

    /**
     * 执行快速校验（遇到第一个校验不通过的字段就返回），不通过抛出异常
     *
     * @param entity 被校验的实体
     * @param groups 校验分组，不传入使用 Default.class
     */
    public static <T> void validateFast(T entity, Class<?>... groups) {
        validateFast(entity, "", groups);
    }

    /**
     * 执行快速校验（遇到第一个校验不通过的字段就返回），不通过抛出异常
     *
     * @param entity 被校验的实体
     * @param suffix 校验失败的消息间隔
     * @param groups 校验分组，不传入使用 Default.class
     */
    public static <T> void validateFast(T entity, String suffix, Class<?>... groups) {
        validate(VALIDATOR_FAST, entity, suffix, groups);
    }

    /**
     * 校验实体，返回实体所有属性的校验结果
     *
     * @param entity 被校验的实体
     * @param groups 校验分组，不传入使用 Default.class
     * @return 校验结果 {@link ValidationResult}
     */
    public static <T> ValidationResult validateEntity(T entity, Class<?>... groups) {
        return validateEntity(null, entity, groups);
    }

    /**
     * 使用自定义的校验器校验实体，返回实体所有属性的校验结果
     *
     * @param entity 被校验的实体
     * @param groups 校验分组，不传入使用 Default.class
     * @return 校验结果 {@link ValidationResult}
     */
    public static <T> ValidationResult validateEntity(Validator validator, T entity, Class<?>... groups) {
        Set<ConstraintViolation<T>> constraintViolations =
                getValidator(validator, false).validate(entity, getGroups(groups));
        return buildValidationResult(constraintViolations);
    }

    /**
     * 快速校验实体，返回实体第一个校验不通过的属性的结果
     *
     * @param entity 被校验的实体
     * @param groups 校验分组，不传入使用 Default.class
     * @return 校验结果 {@link ValidationResult}
     */
    public static <T> ValidationResult validateEntityFast(T entity, Class<?>... groups) {
        Set<ConstraintViolation<T>> constraintViolations =
                getValidator(null, true).validate(entity, getGroups(groups));
        return buildValidationResult(constraintViolations);
    }

    /**
     * 校验指定实体的指定属性是否校验通过
     *
     * @param entity       被校验的实体
     * @param propertyName 被校验实体的属性名
     * @return 校验结果 {@link ValidationResult}
     */
    public static <T> ValidationResult validateProperty(T entity, String propertyName, Class<?>... groups) {
        return validateProperty(null, entity, propertyName, groups);
    }

    /**
     * 使用自定义的校验器校验指定实体的指定属性
     *
     * @param entity       被校验的实体
     * @param propertyName 被校验实体的属性名
     * @return 校验结果 {@link ValidationResult}
     */
    public static <T> ValidationResult validateProperty(Validator validator, T entity, String propertyName, Class<?>... groups) {
        Set<ConstraintViolation<T>> constraintViolations = getValidator(validator, false)
                .validateProperty(entity, propertyName, getGroups(groups));

        return buildValidationResult(constraintViolations);
    }

    /**
     * 将校验结果封装返回
     *
     * @param validateSet 校验结果集合 {@link Set<ConstraintViolation>}
     * @return 校验结果 {@link ValidationResult}
     */
    private static <T> ValidationResult buildValidationResult(Set<ConstraintViolation<T>> validateSet) {
        ValidationResult validationResult = new ValidationResult();
        if (!validateSet.isEmpty()) {
            validationResult.setHasErrors(true);
            Map<String, String> errorMsgMap = new HashMap<>(16);
            for (ConstraintViolation<T> constraintViolation : validateSet) {
                errorMsgMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
            validationResult.setErrorMsg(errorMsgMap);
        }
        return validationResult;
    }

    /**
     * 获取校验器.
     *
     * @param validator 传入的校验器
     * @param fast      是否为快速校验
     * @return 实际用的校验器 {@link Validator}
     */
    private static Validator getValidator(Validator validator, boolean fast) {
        return fast ? VALIDATOR_FAST : validator == null ? VALIDATOR : validator;
    }

    /**
     * 获取校验分组
     *
     * @param groups 传入的校验分组
     * @return 实际要用的校验分组
     */
    private static Class<?>[] getGroups(Class<?>[] groups) {
        Contracts.assertNotNull(groups, MESSAGES.parameterMustNotBeNull("校验分组"));
        return groups.length == 0 ? new Class<?>[]{Default.class} : groups;
    }

    private static <T> String generateValidateMessage(
            Set<ConstraintViolation<T>> constraintViolations,
            String suffix) {
        Contracts.assertNotNull(suffix, MESSAGES.parameterMustNotBeNull("校验失败的消息之间的间隔"));
        Contracts.assertNotEmpty(constraintViolations, MESSAGES.parameterMustNotBeNull("检验结果"));
        StringBuilder validateError = new StringBuilder();
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            validateError.append(constraintViolation.getMessage())
                    .append(suffix);
        }
        return validateError.toString();
    }
}
