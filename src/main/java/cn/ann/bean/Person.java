package cn.ann.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Person implements Serializable {
    private static final long serialVersionUID = 5422510964630842449L;

    private Long id;

    private String name;

    private Integer age;

    private String email;

    private LocalDate birthday;

    public Person(Long id, String name, Integer age, String email, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.birthday = birthday;
    }
}
