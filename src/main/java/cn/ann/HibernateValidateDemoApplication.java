package cn.ann;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.annotation.Resource;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Description：启动类
 * <p>
 * Date: 2020-9-21 13:01
 *
 * @author 88475
 * @version v1.0
 */
@SpringBootApplication
public class HibernateValidateDemoApplication {

    @Resource
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    public static void main(String[] args) {
        SpringApplication.run(HibernateValidateDemoApplication.class, args);
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(ApplicationContext applicationContext) {
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
                .failFast(true)
                .buildValidatorFactory();

        return validatorFactory.getValidator();
    }


}
