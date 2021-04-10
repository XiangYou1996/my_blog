package com.blog.Controller;


import com.blog.Entity.Blog;
import com.blog.Entity.BlogLink;
import com.blog.Service.*;
import com.blog.Util.PageResult;
import com.blog.Util.vo.BlogDetailVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class BlogIndexController {
    @Resource
    BlogService blogService;
    @Resource
    TagService tagService;
    @Resource
    LinkService linkService;
    @Resource
    CategoryService categoryService;
    @Resource
    ConfigService configService;

    @GetMapping({"/","/index"})
    public String index(HttpServletRequest request){
        return this.getPage(request,1);
    }


    @GetMapping("/page/{pageNum}")
    public  String getPage(HttpServletRequest request , @PathVariable("pageNum") int pageNum){
        PageResult pageResult = blogService.getBlogsForIndexPage(pageNum);
        if(pageResult ==null){
            return "error/error_404";
        }
        request.setAttribute("pageNum","首页");
        request.setAttribute("blogPageResult",pageResult);
        request.setAttribute("hotBlogs",blogService.getBlogListForIndexPage(0));
        request.setAttribute("newBlogs",blogService.getBlogListForIndexPage(1));
        request.setAttribute("configurations",configService.getAllConfigs());
        return "blog/yummy-jekyll/index";
    }
    @GetMapping({"/categories"})
    public String categories(HttpServletRequest request) {
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("categories", categoryService.getAllCategories());
        request.setAttribute("pageName", "分类页面");
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/yummy-jekyll/category";
    }

    //chose a detail page!
    @GetMapping({"/blog/{blogId}", "/article/{blogId}"})
    public String detail(HttpServletRequest request, @PathVariable("blogId") Long blogId) {
        System.out.println("detail 1");
        Blog blogDetail = blogService.getBlogById(blogId);        //
        if (blogDetail != null) {
            request.setAttribute("blogDetail", blogDetail);
        }
        System.out.println("detail 2");
        request.setAttribute("pageName", "详情");
        request.setAttribute("configurations", configService.getAllConfigs());
        // 访问量加1
        blogDetail.setBlogViews(blogDetail.getBlogViews()+1);
        blogService.updateBlog(blogDetail);
        System.out.println(blogDetail.getBlogId());
        return "blog/yummy-jekyll/detail";
    }
    // tag
    @GetMapping({"/tag/{tagName}"})
    public String tag(HttpServletRequest request, @PathVariable("tagName") String tagName) {
        return tag(request, tagName, 1);
    }

    @GetMapping({"/tag/{tagName}/{page}"})
    public String tag(HttpServletRequest request, @PathVariable("tagName") String tagName, @PathVariable("page") Integer page) {
        PageResult blogPageResult = blogService.getBlogsPageByTag(tagName, page);
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("pageName", "标签");
        request.setAttribute("pageUrl", "tag");
        request.setAttribute("keyword", tagName);
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/yummy-jekyll/list";
    }

    // category
    @GetMapping({"/category/{categoryName}"})
    public String category(HttpServletRequest request, @PathVariable("categoryName") String categoryName) {
        return category(request, categoryName, 1);
    }
    @GetMapping({"/category/{categoryName}/{page}"})
    public String category(HttpServletRequest request, @PathVariable("categoryName") String categoryName, @PathVariable("page") Integer page) {
        PageResult blogPageResult = blogService.getBlogsPageByCategory(categoryName, page);
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("pageName", "分类");
        request.setAttribute("pageUrl", "category");
        request.setAttribute("keyword", categoryName);
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/yummy-jekyll/list";
    }


    // link
    @GetMapping({"/link"})
    public String link(HttpServletRequest request) {
        request.setAttribute("pageName", "友情链接");
        Map<Byte, List<BlogLink>> linkMap = linkService.getLinksForLinkPage();
        if (linkMap != null) {
            //判断友链类别并封装数据 0-友链 1-推荐 2-个人网站
            if (linkMap.containsKey((byte) 0)) {
                request.setAttribute("favoriteLinks", linkMap.get((byte) 0));
            }
            if (linkMap.containsKey((byte) 1)) {
                request.setAttribute("recommendLinks", linkMap.get((byte) 1));
            }
            if (linkMap.containsKey((byte) 2)) {
                request.setAttribute("personalLinks", linkMap.get((byte) 2));
            }
        }
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/yummy-jekyll/link";
    }

    // sub_url
    @GetMapping({"/{subUrl}"})
    public String detail(HttpServletRequest request, @PathVariable("subUrl") String subUrl) {
        BlogDetailVO blogDetailVO = blogService.getBlogDetailBySubUrl(subUrl);
        if (blogDetailVO != null) {
            request.setAttribute("blogDetailVO", blogDetailVO);
            request.setAttribute("pageName", subUrl);
            request.setAttribute("configurations", configService.getAllConfigs());
            return "blog/yummy-jekyll/detail";
        } else {
            return "error/error_400";
        }
    }
}
