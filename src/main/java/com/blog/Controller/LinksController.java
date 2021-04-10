package com.blog.Controller;

import com.blog.Entity.BlogLink;
import com.blog.Service.LinkService;
import com.blog.Util.PageQueryUtil;
import com.blog.Util.Result;
import com.blog.Util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class LinksController {
    @Resource
    LinkService linkService;

    @GetMapping("/links")
    public  String links(HttpServletRequest request){
        request.setAttribute("path","links");
        return "admin/link";
    }

    @GetMapping("/links/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(linkService.getBlogLinkPage(pageUtil));
    }


    // 保存链接
    @RequestMapping(value = "/links/save",method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestParam("linkType") Integer linkType,
                       @RequestParam("linkName") String linkName,
                       @RequestParam("linkUrl") String linkUrl,
                       @RequestParam("linkRank") Integer linkRank,
                       @RequestParam("linkDescription") String linkDescription
                     ){
        if (linkType ==null||linkRank ==0|| StringUtils.isEmpty(linkName)||StringUtils.isEmpty(linkUrl)||StringUtils.isEmpty(linkDescription)){
            return ResultGenerator.genFailResult("参数错误！");
        }
        BlogLink blogLink = new BlogLink();
        blogLink.setLinkType(linkType.byteValue());
        blogLink.setLinkName(linkName);
        blogLink.setLinkUrl(linkUrl);
        blogLink.setLinkDescription(linkDescription);
        blogLink.setLinkRank(linkRank);
        return ResultGenerator.genSuccessResult(linkService.saveLink(blogLink));
    }

    // delete link
    @RequestMapping(value = "/links/delete",method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids){
        if (ids.length<1){
            ResultGenerator.genFailResult("参数错误！");
        }
        if (linkService.deleteBatch(ids)){
            return ResultGenerator.genSuccessResult("删除成功！");
        }
        else {
            return ResultGenerator.genFailResult("删除失败！");
        }
    }

    // update
    @RequestMapping(value = "/links/update",method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestParam("linkId") Integer Id,
                         @RequestParam("linkType") Integer linkType,
                         @RequestParam("linkName") String linkName,
                         @RequestParam("linkUrl") String linkUrl,
                         @RequestParam("linkDescription") String linkDescription,
                         @RequestParam("linkRank") Integer linkRank){
        if (Id==null){
            return ResultGenerator.genFailResult("参数错误!");
        }
        BlogLink tempLink = linkService.selectById(Id);
        if (tempLink==null||linkType<0||linkRank<0||StringUtils.isEmpty(linkName)||StringUtils.isEmpty(linkUrl)||StringUtils.isEmpty(linkDescription)){
            return ResultGenerator.genFailResult("参数错误");
        }

        tempLink.setLinkType(linkType.byteValue());
        tempLink.setLinkRank(linkRank);
        tempLink.setLinkName(linkName);
        tempLink.setLinkDescription(linkDescription);
        tempLink.setLinkUrl(linkUrl);

        return ResultGenerator.genSuccessResult(linkService.updateLink(tempLink));
    }

    // detail
    @RequestMapping(value = "/links/info/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Result info(@PathVariable Integer id ){
        BlogLink blogLink = linkService.selectById(id);
        return  ResultGenerator.genSuccessResult(blogLink);
    }

}
