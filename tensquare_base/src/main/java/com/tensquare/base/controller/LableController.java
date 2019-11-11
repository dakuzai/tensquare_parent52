package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @RestController和@Controller的区别在于注解@@RestController可以省略@ResponseBody
 * @CrossOrigin 跨域:在不同的平台作调用
 * @ClassName BaseController
 * @Author admin
 * @Date 2019/11/11 9:47
 **/
@RestController
@CrossOrigin
@RequestMapping("/label")
public class LableController {

    @Autowired
    private LabelService labelService;

    /**
     * 查询所有
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {

        return new Result(true, StatusCode.OK, "查询成功", labelService.findAll());
    }

    /**
     * 根据Id查询
     *
     * @param labelId
     * @return
     */
    @RequestMapping(value = "/{labelId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("labelId") String labelId) {

        return new Result(true, StatusCode.OK, "查询成功", labelService.findById(labelId));
    }

    /**
     * 添加
     *
     * @param label
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label) {
        labelService.save(label);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 更新
     *
     * @param labelId
     * @return
     */
    @RequestMapping(value = "/{labelId}", method = RequestMethod.PUT)
    public Result update(@PathVariable("labelId") String labelId, @RequestBody Label label) {
        label.setId(labelId);
        labelService.update(label);
        return new Result(true, StatusCode.OK, "更新成功");
    }

    /**
     * 删除
     *
     * @param labelId
     * @return
     */
    @RequestMapping(value = "/{labelId}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable("labelId") String labelId) {
        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 条件查询
     * @param label
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Label label){

        List<Label> list = labelService.findsearch(label);
        return new Result(true,StatusCode.OK,"查询成功",list);

    }

    /**
     * 分页查询
     * @param label
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result pageQuery(@RequestBody Label label,@PathVariable int page,@PathVariable int size){

       Page<Label> pageDate = labelService.pageQuery(label,page,size);
       //getTotalElements记录数 getContent内容
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Label>(pageDate.getTotalElements(),pageDate.getContent()));

    }

}
