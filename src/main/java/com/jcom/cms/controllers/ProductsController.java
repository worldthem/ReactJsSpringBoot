package com.jcom.cms.controllers;

import com.jcom.cms.dto.ListResponse;
import com.jcom.cms.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;


//@CrossOrigin(origins = "http://test1.ru")
@RestController
@RequestMapping("/api/v1/")
public class ProductsController extends MainController{

    @Autowired
    ProductService productService;

    @GetMapping("/product-list")
    public ListResponse productList(HttpServletRequest request, Pageable paginate){ //@PathVariable String cpu,
       return new ListResponse(productService.findAll(paginate),"Home Page");
    }



}
