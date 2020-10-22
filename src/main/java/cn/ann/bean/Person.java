package cn.ann.bean;

import lombok.Data;
import lombok.ToString;

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
public class Person implements Serializable {
    private static final long serialVersionUID = 5422510964630842449L;

    private String name;

    private Integer age;

    private String email;

    private LocalDate birthday;
}
