package cn.ann.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Description：hibernate-validator 配置
 * <p>
 * Date: 2020-9-22 21:47
 *
 * @author 88475
 * @version v1.0
 */
@Configuration
public class HibernateValidatorConfiguration {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(ApplicationContext applicationContext) {
        // 使用自定义 Validator 的时候，需要添加这个配置
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        postProcessor.setValidator(validator(applicationContext));
        return postProcessor;
    }

    @Bean
    public Validator validator(ApplicationContext applicationContext){
        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure()
                // 自定义的 validator 需要注入 SpringConstraintValidatorFactory 才支持 DI，否则注入进去的都是 null
                .constraintValidatorFactory(new SpringConstraintValidatorFactory(applicationContext.getAutowireCapableBeanFactory()))
                // 设置validator模式为快速失败返回：就是检验到第一个不符合规则的就不继续检验了，直接返回错误信息
//                .failFast(true)
                .buildValidatorFactory();

        return validatorFactory.getValidator();
    }
}
