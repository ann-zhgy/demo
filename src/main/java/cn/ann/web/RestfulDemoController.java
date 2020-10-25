package cn.ann.web;

import cn.ann.bean.Person;
import cn.ann.common.response.ResponseResult;
import cn.ann.dto.PersonDTO;
import cn.ann.service.IMockService;
import cn.hutool.core.collection.CollectionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description：controller
 * <p>
 * Date: 2020-10-22 10:14
 *
 * @author 88475
 * @version v1.0
 */
@RestController
@RequestMapping("rest")
@Api("RESTFul 风格接口测试")
public class RestfulDemoController {
    @Resource(name = "mockServiceImpl")
    private IMockService service;

    @GetMapping("persons")
    @ApiOperation(value = "获取所有信息")
    public ResponseResult<List<Person>> get() {
        return ResponseResult.ok("查询成功", service.get());
    }

    @GetMapping("person/{id}")
    @ApiOperation(value = "获取指定id的信息")
    @ApiParam(name = "主键", value = "id，写在路径中")
    public ResponseResult<Person> get(@PathVariable long id) {
        return ResponseResult.ok("查询成功", service.get(id));
    }

    @GetMapping("person")
    @ApiOperation(value = "根据条件获取信息")
    @ApiParam(name = "dto", value = "查询条件")
    public ResponseResult<List<Person>> get(@RequestBody PersonDTO dto) {
        return ResponseResult.ok("查询成功", service.get(dto));
    }

    @PostMapping("person")
    @ApiOperation(value = "保存信息")
    @ApiParam(name = "person", value = "要保存的实体，id不能有值")
    public ResponseResult<Person> save(@RequestBody Person person) {
        if (person.getId() != null) {
            // 实际上应该抛出异常，没整合 hibernate-validator，先将就一下
            return ResponseResult.error(ResponseResult.ResponseCode.BAD_REQUEST, "保存时id必须为空");
        }
        return ResponseResult.ok("person保存成功", service.save(person));
    }

    @PutMapping("person")
    @ApiOperation(value = "更新信息，更新所有属性")
    @ApiParam(name = "person", value = "要替换的实体，id不能为空")
    public ResponseResult<Person> updateAll(@RequestBody Person person) {
        if (person.getId() == null) {
            // 实际上应该抛出异常，没整合 hibernate-validator，先将就一下
            return ResponseResult.error(ResponseResult.ResponseCode.BAD_REQUEST, "更新时id不能为空");
        }
        return ResponseResult.ok("person更新成功", service.update(person));
    }

    @PatchMapping("person")
    @ApiOperation(value = "更新信息，更新部分属性")
    @ApiParam(name = "person", value = "要更新的实体，id不能为空")
    public ResponseResult<Person> update(@RequestBody Person person) {
        if (person.getId() == null) {
            // 实际上应该抛出异常，没整合 hibernate-validator，先将就一下
            return ResponseResult.error(ResponseResult.ResponseCode.BAD_REQUEST, "更新时id不能为空");
        }
        return ResponseResult.ok("person更新成功", service.modify(person));
    }

    @DeleteMapping("person/{id}")
    @ApiOperation(value = "根据id删除")
    @ApiParam(name = "id", value = "跟在url路径中")
    public ResponseResult<Person> delete(@PathVariable long id) {
        Person person = service.delete(id);
        if (person != null) {
            return ResponseResult.ok("person删除成功", person);
        } else {
            return ResponseResult.ok("person删除失败", null);
        }
    }

    @DeleteMapping("person")
    @ApiOperation(value = "根据条件删除")
    @ApiParam(name = "dto", value = "符合删除操作的条件")
    public ResponseResult<List<Person>> delete(@RequestBody PersonDTO dto) {
        List<Person> personList = service.delete(dto);
        if (CollectionUtil.isEmpty(personList)) {
            return ResponseResult.ok("person删除失败");
        } else {
            return ResponseResult.ok("person删除成功", personList);
        }
    }

}
