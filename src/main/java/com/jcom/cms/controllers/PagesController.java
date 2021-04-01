package com.jcom.cms.controllers;


import com.jcom.cms.dto.ListResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://test1.ru")
@RestController
@RequestMapping("/api/v1")
public class PagesController {


/*
    @GetMapping("/product-list")
    public ListResponse productList(HttpServletRequest request, Pageable paginate){ //@PathVariable String cpu,
        return new ListResponse(productService.findAll(paginate),"Home Page");
    }

 */

}
