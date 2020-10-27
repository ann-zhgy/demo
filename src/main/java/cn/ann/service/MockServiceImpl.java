package cn.ann.service;

import cn.ann.bean.Person;
import cn.ann.common.exception.BusinessException;
import cn.ann.dto.PersonDTO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description：模拟数据的service
 * <p>
 * Date: 2020-10-23 16:09
 *
 * @author 88475
 * @version v1.0
 */
@Service
public class MockServiceImpl implements IMockService {
    private static final List<Person> PERSON_LIST = new ArrayList<>();

    static {
        PERSON_LIST.add(
                new Person(
                        LocalDateTime.now().plusSeconds(1).toEpochSecond(ZoneOffset.UTC),
                        "Jack", 20, "jack@ann.cn", LocalDate.now().minusYears(20)));
        PERSON_LIST.add(
                new Person(
                        LocalDateTime.now().plusSeconds(2).toEpochSecond(ZoneOffset.UTC),
                        "John", 23, "john@ann.cn", LocalDate.now().minusYears(23)));
        PERSON_LIST.add(
                new Person(
                        LocalDateTime.now().plusSeconds(3).toEpochSecond(ZoneOffset.UTC),
                        "Tom", 21, "tom@ann.cn", LocalDate.now().minusYears(21)));
        PERSON_LIST.add(
                new Person(
                        LocalDateTime.now().plusSeconds(4).toEpochSecond(ZoneOffset.UTC),
                        "David", 20, "david@ann.cn", LocalDate.now().minusYears(20)));
        PERSON_LIST.add(
                new Person(
                        LocalDateTime.now().plusSeconds(5).toEpochSecond(ZoneOffset.UTC),
                        "Jerry", 19, "jerry@ann.cn", LocalDate.now().minusYears(19)));
    }

    @Override
    public Person save(Person person) {
        person.setId(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        PERSON_LIST.add(person);
        return person;
    }

    @Override
    public Person modify(Person person) {
        Person oldPerson = this.get(person.getId());
        if (oldPerson == null) {
            throw new BusinessException("查不到对应的person");
        }
        // 拷贝属性时忽略 null 值
        BeanUtil.copyProperties(person, oldPerson, CopyOptions.create().ignoreNullValue());
        // 应该有更新操作，但是是list，无所谓了
        return oldPerson;
    }

    @Override
    public Person update(Person person) {
        // 实现1
        /*int index = -1;
        for (int i = 0; i < PERSON_LIST.size(); i++) {
            if (person.getId().equals(PERSON_LIST.get(i).getId())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new BusinessException("查不到对应的person");
        }
        Person oldPerson = PERSON_LIST.get(index);
        PERSON_LIST.set(index, person);*/
        Person oldPerson = this.get(person.getId());
        BeanUtil.copyProperties(person, oldPerson);
        return oldPerson;
    }

    @Override
    public Person get(Long id) {
        for (Person person : PERSON_LIST) {
            if (id.equals(person.getId())) {
                return person;
            }
        }
        return null;
    }

    @Override
    public List<Person> get() {
        return PERSON_LIST;
    }

    @Override
    public List<Person> get(PersonDTO dto) {
        return PERSON_LIST.stream().filter(person -> {
            if (dto.getId() != null) {
                return dto.getId().equals(person.getId());
            }
            if (!StrUtil.isEmpty(dto.getName())) {
                if (!person.getName().contains(dto.getName())) {
                    return false;
                }
            }
            if (dto.getAge() != null) {
                if (!dto.getAge().equals(person.getAge())) {
                    return false;
                }
            }
            if (!StrUtil.isEmpty(dto.getEmail())) {
                if (!person.getEmail().equals(dto.getEmail())) {
                    return false;
                }
            }
            if (dto.getBirthday() != null) {
                if (!dto.getBirthday().equals(person.getBirthday())) {
                    return false;
                }
            }
            if (dto.getMinAge() != null) {
                if (dto.getMinAge().compareTo(person.getAge()) > 0) {
                    return false;
                }
            }
            if (dto.getMaxAge() != null) {
                if (dto.getMaxAge().compareTo(person.getAge()) < 0) {
                    return false;
                }
            }
            if (dto.getBeginBirth() != null) {
                if (dto.getBeginBirth().isAfter(person.getBirthday())) {
                    return false;
                }
            }
            if (dto.getEndBirth() != null) {
                return dto.getEndBirth().isAfter(person.getBirthday());
            }

            return true;
        }).collect(Collectors.toList());
    }

    @Override
    public Person delete(Long id) {
        Person person = this.get(id);
        if (person == null) {
            throw new BusinessException("person不存在");
        }
        boolean remove = PERSON_LIST.remove(person);

        return remove ? person : null;
    }

    @Override
    public List<Person> delete(PersonDTO dto) {
        List<Person> personList = this.get(dto);
        boolean remove = PERSON_LIST.removeAll(personList);
        return remove ? personList : Collections.emptyList();
    }


}
