package cn.ann.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Description：person数据传输对象
 * <p>
 * Date: 2020-10-23 16:23
 *
 * @author 88475
 * @version v1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(description = "数据传输对象", value = "用来传输条件参数")
public class PersonDTO implements Serializable {
    private static final long serialVersionUID = 1648372485477112350L;

    @ApiModelProperty(name = "id", value = "主键")
    private Long id;

    @ApiModelProperty(name = "name", value = "姓名")
    private String name;

    @ApiModelProperty(name = "age", value = "年龄")
    private Integer age;

    @ApiModelProperty(name = "minAge", value = "表示查询条件为小于这个年龄")
    private Integer minAge;

    @ApiModelProperty(name = "maxAge", value = "表示查询条件为大于这个年龄")
    private Integer maxAge;

    @ApiModelProperty(name = "email", value = "邮箱")
    private String email;

    @ApiModelProperty(name = "birthday", value = "生日")
    private LocalDate birthday;

    @ApiModelProperty(name = "beginBirth", value = "表示查询条件为生日在这个日期之后")
    private LocalDate beginBirth;

    @ApiModelProperty(name = "endBirth", value = "表示查询条件为生日在这个日期之前")
    private LocalDate endBirth;
}
