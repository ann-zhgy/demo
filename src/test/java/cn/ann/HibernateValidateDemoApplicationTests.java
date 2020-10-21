package cn.ann;
import cn.ann.bean.Person;
import cn.ann.util.ValidatorUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import javax.validation.ValidationException;
import javax.validation.Validator;

@SpringBootTest
@ContextConfiguration(classes = {HibernateValidateDemoApplication.class})
public class HibernateValidateDemoApplicationTests {
    @Resource(name = "validator")
    private Validator validator;

    @Test
    void loadContext() {

    }

    @Test
    @DisplayName("手动调用hibernate校验器并抛出异常")
    void validateWithValidator() {
        Person person = new Person();
        person.setName("john");
//        person.setAge(20);
//        person.setEmail("john@ann.cn");
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA);
//        person.setBirthday(LocalDate.parse("2010-10-11", formatter));

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            ValidatorUtils.validate(validator, person, "T_T||");
        });
        System.out.println(exception.getMessage());
    }

    @Test
    @DisplayName("手动调用hibernate校验器并抛出异常——has tail")
    void validateWithValidatorHasTail() {
        Person person = new Person();
        person.setName("john");
//        person.setAge(20);
//        person.setEmail("john@ann.cn");
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA);
//        person.setBirthday(LocalDate.parse("2010-10-11", formatter));

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            ValidatorUtils.validateHasTail(validator, person, "T_T||");
        });
        System.out.println(exception.getMessage());
    }

}
