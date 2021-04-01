package com.jcom.cms.controllers;

import com.jcom.cms.dto.ListResponse;
import com.jcom.cms.entity.Page;
import com.jcom.cms.entity.Product;
import com.jcom.cms.hepers.Helpers;
import com.jcom.cms.services.DoShortCode;
import com.jcom.cms.services.PagesServices;
import com.jcom.cms.services.ProductService;
import com.jcom.cms.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;
//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/")
public class HomeController {
    @Autowired // This means to get the bean called userRepository
    private PagesServices pagesServices;

    @Autowired
    private ProductService productService;

    @Autowired
    UsersService usersService;

    @Autowired
    private DoShortCode doShortCode;


    @GetMapping("/homepage") // , produces = "application/json"
    public ListResponse getdata() { //@PathVariable int id
        ListResponse listResponse = new ListResponse();

        int settingsPageHome  = Helpers.getConfig().getMinOptions().getPageHome();

        Long countU = usersService.count();

        if (countU==0){
            //return view("frontend::start/admin");
        }


        if(settingsPageHome >0){
            Page page = pagesServices.getOne(settingsPageHome);

            try{
                listResponse.setTitle(page.getTitle());
                listResponse.setMetad(page.getMetad());
                listResponse.setMetak(page.getMetak());
                listResponse.setCss(Helpers.generateCss(page.getStyle(), page.getCss()));
                listResponse.setText(doShortCode.doshortcodes(page.getText()));
            }catch (Exception e){}

        }else{
            /*
            org.springframework.data.domain.Page<Product> products = productService.findAll(pagination(request, productService.count(), model, 30));
            model.addAttribute("title", Helpers.getConfig().getMinOptions().getSite_title());
            model.addAttribute("metad", Helpers.getConfig().getMinOptions().getMetad());
            model.addAttribute("metak", Helpers.getConfig().getMinOptions().getMetak());
            model.addAttribute("products", products);

             */
        }


          String view= settingsPageHome>0 ? "theme::pages" : "theme::home";

        return listResponse;
    }


}
