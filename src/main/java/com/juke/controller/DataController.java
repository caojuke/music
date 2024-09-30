package com.juke.controller;

import com.juke.pojo.NewUser;

import com.juke.pojo.SimpleObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class DataController {

    @ResponseBody
    @RequestMapping("loadData")
    public String loadData(String callback, HttpServletResponse response){
        System.out.println("callback = "+callback);
        response.setCharacterEncoding("utf-8");
        response.setContentType("utf-8");
        return callback+"({\"content\":\"data to remote\"});";
    }
    @ResponseBody
    @RequestMapping("registerUser")
    public NewUser registerUser(HttpServletRequest request,HttpServletResponse response, NewUser user){
        System.out.println("User = : "+user);

        //response.setCharacterEncoding("utf-8");
        //response.setContentType("application/json");
        return user;
    }

    @ResponseBody
    @RequestMapping("xhr")
    public SimpleObj testXHR(@RequestBody SimpleObj obj,HttpServletRequest request,HttpServletResponse response){
        System.out.println("data =  : "+obj);
        return new SimpleObj("success");
    }

    @ResponseBody
    @RequestMapping("xhr2")
    public String testXHR2(@RequestBody SimpleObj obj,HttpServletRequest request,HttpServletResponse response){
        return new SimpleObj("success").toString();
    }

    @RequestMapping("JSP/first")
    public String jsp(){
        return "/first";
    }
    @RequestMapping("JSP/index")
    public ModelAndView index(HttpServletRequest request){
        // Get the servlet path (where the servlet was mapped)
        String servletPath = request.getServletPath();
        System.out.println("Servlet Path: " + servletPath);

        // You can also get other details like the context path, request URI, etc.
        String contextPath = request.getContextPath();
        String requestUri = request.getRequestURI();

        System.out.println("Context Path: " + contextPath);
        System.out.println("Request URI: " + requestUri);

        ModelAndView mv=new ModelAndView();
        Map<String, Object> model = mv.getModel();
        model.put("item1", "12345");//JSP页面中可访问：${requestScope.item1}
        model.put("item2", "67890");//JSP页面中可访问：${requestScope.item2}

        // 设置视图
        mv.setViewName("index");//mv.setViewName("forward:/showDataPage.jsp");
        return mv;
    }
    @Autowired
    private WebApplicationContext webApplicationContext;

    @RequestMapping("/printBeans")
    @ResponseBody
    public String printBeans() {
        String[] beanNames = webApplicationContext.getBeanDefinitionNames();
        StringBuilder beansList = new StringBuilder("Beans in the WebApplicationContext:<br>");

        for (String beanName : beanNames) {
            beansList.append(beanName).append("<br>");
        }

        return beansList.toString();
    }

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping("/printBeans2")
    @ResponseBody
    public String printBeans2() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();

        StringBuilder beansList = new StringBuilder("Beans in the ApplicationContext:<br>");

        for (String beanName : beanNames) {
            beansList.append(beanName).append("<br>");
        }

        return beansList.toString();
    }
}
