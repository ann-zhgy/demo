package cn.ann;

import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

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

    public static void main(String[] args) {
        SpringApplication.run(HibernateValidateDemoApplication.class, args);
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        // 设置validator模式为快速失败返回
        postProcessor.setValidator(validator());
        return postProcessor;
    }

    @Bean
    public Validator validator(){
        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure()
                .addProperty( "hibernate.validator.fail_fast", "true" )
                .buildValidatorFactory();

        return validatorFactory.getValidator();
    }


}
