package com.blog.Controller;


import com.blog.Service.CategoryService;
import com.blog.Util.PageQueryUtil;
import com.blog.Util.Result;
import com.blog.Util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categoryPage(HttpServletRequest request){

        request.setAttribute("path","categories");
        return "admin/category";
    }
    //list
    @RequestMapping(value = "/categories/list",method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String,Object> params){
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageQueryUtil= new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(categoryService.getBlogCategoryPage(pageQueryUtil));
    }
    // save
    @RequestMapping(value = "/categories/save",method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestParam("categoryName") String categoryName,
                       @RequestParam("categoryIcon") String categoryIcon){
        if (StringUtils.isEmpty(categoryName)||StringUtils.isEmpty(categoryIcon)){
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (categoryService.saveCategory(categoryName,categoryIcon)){
            return ResultGenerator.genSuccessResult("添加图标成功！");
        }
        else {
            return ResultGenerator.genFailResult("图标已存在！");
        }
    }

    // update
    @RequestMapping(value = "/categories/update",method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestParam("categoryId") int categoryId,
                         @RequestParam("categoryName")String categoryName,
                         @RequestParam("categoryIcon") String categoryIcon){
        if (StringUtils.isEmpty(categoryId)||StringUtils.isEmpty(categoryName)||StringUtils.isEmpty(categoryIcon)){
            return ResultGenerator.genFailResult("参数错误！");
        }
        if (categoryService.updateCategory(categoryId,categoryName,categoryIcon)){
            return ResultGenerator.genSuccessResult("修改图标成功!");
        }else {
            return ResultGenerator.genFailResult("图标修改重复");
        }
    }
    //  delete
    @RequestMapping(value = "/categories/delete",method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids){
        if (ids.length<1){
            return ResultGenerator.genFailResult("参数错误！");
        }
        if (categoryService.deleteBatch(ids)){
            return ResultGenerator.genSuccessResult("删除成功！");
        }
        else {
            return ResultGenerator.genFailResult("删除失败！");
        }
    }
}
