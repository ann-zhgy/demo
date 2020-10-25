package cn.ann;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Description：启动类
 * <p>
 * Date: 2020-9-21 13:01
 *
 * @author 88475
 * @version v1.0
 */
@EnableSwagger2
@SpringBootApplication
public class RestfulDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestfulDemoApplication.class, args);
    }

}
