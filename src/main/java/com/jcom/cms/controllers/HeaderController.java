package com.jcom.cms.controllers;

import com.jcom.cms.entity.Settings;
import com.jcom.cms.hepers.Helpers;
import com.jcom.cms.hepers.HelpersFile;
import com.jcom.cms.hepers.HelpersJson;
import com.jcom.cms.services.DoShortCode;
import com.jcom.cms.services.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class HeaderController {
    @Autowired
    private SettingsService settingsRepository;


    @Autowired
    private DoShortCode doShortCode;

    @GetMapping("/header")
    public Map<String, Object> prepareData(){
        HashMap<String, Object> map = new HashMap<>();

        String configThemePath= "content/views/themes/"+Helpers.getConfig().getTheme()+"/config/config.xml";

        if(Helpers.fileExist(configThemePath)){
            Map<String, String> ConfigData =  HelpersFile.getConfigXmlData("config","hooks", configThemePath);
            for(Map.Entry<String, String> entry2: ConfigData.entrySet()){
                map.put(entry2.getKey()+"_hook", entry2.getValue());
            }
        }

        List<String> list = new ArrayList<>();
        list.add("_header_");  list.add("_footer_"); list.add("_shop_sidebar_");  list.add("_footerjs_");


        List<Settings> settings = settingsRepository.findByParamInOrderByIdDesc(list);

        for (Settings row:settings){
            if(row.getParam().contains("_footerjs_")){
                map.put(row.getParam(), row.getValue1());
            }else {
                map.put(row.getParam(), doShortCode.doshortcodes(row.getValue1Lang()));
                map.put(row.getParam() + "css", Helpers.editableBlocks(row.getValue(), "css") );
            }
        }

        //Menu
        List<Settings> settingsMenu = settingsRepository.findByParam("website_menu");
        if(settingsMenu !=null) {
            for (Settings rowMenu : settingsMenu) {
                if (rowMenu.getValue2() != null) {
                    map.put(rowMenu.getValue2(), rowMenu.getValue1Lang());
                }
                map.put("menuId" + rowMenu.getId(), rowMenu.getValue1Lang());
            }
        }


        //Website URL
        String url = Helpers.mainOptions().getBaseurl();
        map.put("baseurl", url);
        map.put("mainOptions", Helpers.mainOptions());
        map.put("logo", "<a href='"+url+"'>"+
                         "<img class='logo loading' src='"+url+"content/images/"+Helpers.mainOptions().getLogo()+"'></a>");
        map.put("favicon", url+"content/images/"+Helpers.mainOptions().getFavicon());


        //map.put("themeassets", url+"content/views/themes/"+ HelpersJson.getConfig().getTheme()+"/assets");

        //String themePath= "/themes/"+ HelpersJson.getConfig().getTheme();
        //map.put("themepath",  themePath);

        //map.put("productpath", Helpers.fileExist("content/views"+themePath+"/layouts/product.html") ?
         //       themePath+"/layouts/product.html" :"/cms/standart/product/product.html" );

        //map.put("adminassets", url+"content/views/assets/admin");
        //map.put("frontassets", url+"content/views/assets/front");
        //map.put("assets", url+"content/views/assets");
        //map.put("adminmenu", adminmenu("full", url));
        //map.put("adminmenusubmenu", adminmenu("submenu", url));

        return map;
    }
}
