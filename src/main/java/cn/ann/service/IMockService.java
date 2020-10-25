package cn.ann.service;

import cn.ann.bean.Person;
import cn.ann.dto.PersonDTO;

import java.util.List;

/**
 * Description：模拟的service接口
 * <p>
 * Date: 2020-10-23 16:20
 *
 * @author 88475
 * @version v1.0
 */
public interface IMockService {
    /**
     * save
     *
     * @param person person
     * @return person after save
     */
    Person save(Person person);

    /**
     * 更新所有字段
     *
     * @param person person
     * @return person after update
     */
    Person update(Person person);

    /**
     * 更新局部字段
     *
     * @param person person
     * @return person after update
     */
    Person modify(Person person);

    /**
     * get by id
     * @param id id
     * @return result
     */
    Person get(Long id);

    /**
     * get all
     * @return personList
     */
    List<Person> get();

    /**
     * get by parameter
     * @param dto param
     * @return result
     */
    List<Person> get(PersonDTO dto);

    /**
     * delete by id
     * @param id id
     * @return the person by delete
     */
    Person delete(Long id);

    /**
     * 根据条件删除
     *
     * @param dto dto
     * @return 删除的数据
     */
    List<Person> delete(PersonDTO dto);
}
