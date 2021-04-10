package com.blog.Controller;

import com.blog.Entity.Devoloper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AboutController {


    @RequestMapping(value = "/about")
    public  String aboutUs(HttpServletRequest request){

        Devoloper devoloper1 =new Devoloper();
        devoloper1.setName("向优");
        devoloper1.setStudentId("2019********");
        devoloper1.setYears(2019);
        devoloper1.setDepartment("服务计算");

        Devoloper devoloper2 = new Devoloper();
        devoloper2.setName("姚培");
        devoloper2.setStudentId("2019********");
        devoloper2.setYears(2019);
        devoloper2.setDepartment("机器学习");

        request.setAttribute("u1",devoloper1.toString());
        request.setAttribute("u2",devoloper2.toString());
        return "blog/yummy-jekyll/about";
    }
}
