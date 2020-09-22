package cn.ann.service;

import org.springframework.stereotype.Service;

/**
 * Description：person service
 * <p>
 * Date: 2020-9-22 14:04
 *
 * @author 88475
 * @version v1.0
 */
@Service
public class PersonService {
    public String test(String str) {
        return str + ": test";
    }

}
