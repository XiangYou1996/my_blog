package com.blog.Controller;

import com.blog.Service.TagService;
import com.blog.Util.PageQueryUtil;
import com.blog.Util.Result;
import com.blog.Util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
@RequestMapping(value = "/admin")
public class TagController {
    @Resource
    private TagService tagService;

    @GetMapping("/tags")
    public String tagPage(HttpServletRequest request){
        System.out.println("come to tags");
        request.setAttribute("path","tags");
        return  "admin/tag";
    }

    //  list

    @GetMapping("/tags/list")
    @ResponseBody
    public Result tagList(@RequestParam Map<String,Object> params){
        System.out.println("come to tags/list");
        if(StringUtils.isEmpty(params.get("page"))||StringUtils.isEmpty(params.get("limit"))){
            return ResultGenerator.genFailResult("参数错误!");
        }
        PageQueryUtil pageQueryUtil =new PageQueryUtil(params);
        System.out.println("begin to tag!");
        return ResultGenerator.genSuccessResult(tagService.getBlogTagPage(pageQueryUtil));
    }

    //  save

    @PostMapping("/tags/save")
    @ResponseBody
    public Result save (@RequestParam("tagName") String tagName){
        if(StringUtils.isEmpty(tagName)){
            return ResultGenerator.genFailResult("参数错误!");
        }
        if (tagService.saveTag(tagName)){
            return ResultGenerator.genSuccessResult();
        }
        else {
            return ResultGenerator.genFailResult("名称重复，保存失败！");
        }
    }

    //delete

    @PostMapping("/tags/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] Ids){
        if (Ids.length<0){
            return ResultGenerator.genFailResult("参数错误！");
        }
        if (tagService.deleteBatch(Ids)){
            return  ResultGenerator.genSuccessResult();
        }
        else {
            return  ResultGenerator.genFailResult("删除失败~");
        }
    }

}
