package cn.ann.bean;

import cn.ann.valid.group.Update;
import cn.ann.valid.validation.BirthdayValidation;
import cn.ann.valid.validation.PersonValidation;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Description：person
 * <p>
 * Date: 2020-9-21 12:56
 *
 * @author 88475
 * @version v1.0
 */
@Data
@ToString
@PersonValidation(groups = {Update.class})
public class Person implements Serializable {
    private static final long serialVersionUID = 5422510964630842449L;

    @NotBlank(message = "name must be not-blank!")
    private String name;

    @NotNull(message = "age must be not-null!")
    @Max(value = 150L, message = "age is too large!")
    @Min(value = 0L, message = "age is too small!")
    private Integer age;

    @NotBlank(message = "email must be not-blank!")
    @Email(message = "email address format is not correct!")
    private String email;

    @BirthdayValidation(message = "birthday is invalided！")
    @NotNull(message = "birthday must be not-null!")
    private LocalDate birthday;
}
