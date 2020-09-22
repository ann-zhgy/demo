# hibernate-validator-demo

# 一、介绍

* 此 demo 仅涉及hibernate-validator的基本使用

# 二、使用

## 1. 环境搭建

1. 使用idea创建spring boot项目，勾选相关选项即可

   * pom.xml 中的依赖

     ```xml
     <dependencies>
         <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-validation</artifactId>
         </dependency>
         <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-web</artifactId>
         </dependency>
     
         <dependency>
             <groupId>org.projectlombok</groupId>
             <artifactId>lombok</artifactId>
             <optional>true</optional>
         </dependency>
     </dependencies>
     ```

2. 目录介绍

   ```tex
   src
    ├─main
      ├─java
      │ └─cn.ann
      │    │  HibernateValidateDemoApplication.java			# 启动类
      │    │  
      │    ├─bean
      │    │      Person.java									# 测试bean
      │    │      
      │    ├─config
      │    │      HibernateValidatorConfiguration.java			# hibernate-validator配置
      │    │      WebConfiguration.java						# web层全局异常处理配置
      │    │      
      │    ├─response
      │    │      ResponseResult.java							# 响应结果封装
      │    │      
      │    ├─service
      │    │      PersonService.java							# 用来测试自动注入的bean
      │    │      
      │    ├─valid
      │    │  ├─group
      │    │  │      Update.java								# validator 组
      │    │  │      GroupOrder.java							# validator 组校验顺序
      │    │  │      
      │    │  ├─validation
      │    │  │      BirthdayValidation.java					# 自定义校验器
      │    │  │      PersonValidation.java						# 自定义校验器，使用@Resource自动注入spring-bean
      │    │  │      
      │    │  └─validator
      │    │          BirthdayValidator.java					# BirthdayValidation 的校验逻辑实现
      │    │          PersonValidator.java						# PersonValidation 的校验逻辑实现
      │    │          
      │    └─web
      │            HibernateValidateDemoController.java		# Rest 接口
      │                 
      └─resources
        └─application.yml										# spring boot配置文件
   ```

## 2. 注意点

1. 建议添加 `WebConfiguration.java` 中的配置进行全局异常捕获

2. `HibernateValidatorConfiguration.java` 的配置是为了添加方法级验证参数并开启 `validator` 的快速失败返回模式：检验到第一个不符合的就返回提示信息，不再继续校验。方法及验证参数支持将 `@Validated` 写到类上，验证不通过时，会抛 `ConstraintViolationException` 。如果没有注入 `SpringConstraintValidatorFactory`，DI 注入进去的值将会是 null 。不需要开启的不用添加此配置。

3. validation 里面的注解照着官方给的 `@NotNull` 等抄过来就好了，注意 `@Constraint` 注解中的 `validatedBy` 属性要指向自定义实现的校验逻辑；`@Target` 注解声明了你写的注解可以添加到哪个位置；`@Repeatable`注解和`List`定义可以让该注解在同一个位置重复多次，通常是不同的配置（比如不同的分组和消息）

   * `message` 错误提示信息，可以写死,也可以填写国际化的key
   * `groups` 分组信息，允许指定此约束所属的验证组（下面会说到分组约束）
   * `payload` 有效负载，可以通过payload来标记一些需要特殊处理的操作

4. 自定义的 `validator` 需要实现 `ConstraintValidator<A extends Annotation, T>` 接口，该接口中 `initialize` 方法是初始化方法，只会运行一次； `isValid(T value, ConstraintValidatorContext context)` 方法是写校验逻辑的地方，`value` 保存了要校验的对象的值，`context` 是上下文对象，里面存储了上下文相关的数据和操作：校验失败默认显示的消息、字段路径……

5. 接口上的Bean验证，需要在参数前加上`@Valid`或Spring的 `@Validated` 注解，这两种注释都会导致应用标准Bean验证。如果验证不通过会抛出`BindException`异常，并变成400（BAD_REQUEST）响应；或者可以通过`Errors`或`BindingResult`参数在控制器内本地处理验证错误。另外，如果参数前有`@RequestBody`注解，验证错误会抛出`MethodArgumentNotValidException`异常。

6. hibernage validtaion框架有校验分组的概念，指定校验注解的时候可以加上分组，默认是Default分组。

7. spring 的 `@Validated` 注解支持分组验证

8. 可以在 `GroupOrder.java` 上使用 `@GroupSequence({})` 来定义组的校验顺序，在 `@Validated({})` 中使用即可

9. 所有的 `Group`、`GroupOrder` 都是接口

10. 常见注解

    1. `@Null`：被注释的元素必须为 null
    2. `@NotNull`：被注释的元素必须不为 null 
    3. `@AssertTrue`：被注释的元素必须为 true 
    4. `@AssertFalse`：被注释的元素必须为 false
    5. `@Min(value)`：被注释的元素必须是一个数字，其值必须大于等于指定的最小值 
    6. `@Max(value)`：被注释的元素必须是一个数字，其值必须小于等于指定的最大值 
    7. `@DecimalMin(value)`：被注释的元素必须是一个数字，其值必须大于等于指定的最小值 
    8. `@DecimalMax(value)`：被注释的元素必须是一个数字，其值必须小于等于指定的最大值    
    9. `@Size(max=, min=)`：被注释的元素的大小必须在指定的范围内 
    10. `@Digits (integer, fraction)`：被注释的元素必须是一个数字，其值必须在可接受的范围内     
    11. `@Past`：被注释的元素必须是一个过去的日期     
    12. `@Future`：被注释的元素必须是一个将来的日期     
    13. `@Pattern(regex=,flag=)`：被注释的元素必须符合指定的正则表达式     

    * // Hibernate Validator 	// 附加的 constraint

    1. `@NotBlank(message =)`：验证字符串非null，且长度必须大于0     
    2. `@Email`：被注释的元素必须是电子邮箱地址     
    3. `@Length(min=,max=)`：被注释的字符串的大小必须在指定的范围内     
    4. `@NotEmpty`：被注释的字符串的必须非空     
    5. `@Range(min=,max=,message=)`：被注释的元素必须在合适的范围内

    * 这些注解还可以添加在 **参数、本地变量、成员变量、泛型** 前进行数据校验