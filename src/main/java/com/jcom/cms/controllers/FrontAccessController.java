package com.jcom.cms.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("")
public class FrontAccessController {

    @GetMapping("/ddddd")
    public ModelAndView productList(HttpServletRequest request ){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/themes/electro/index");
        return  mav;
    }

}
