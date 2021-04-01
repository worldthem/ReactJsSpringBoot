package com.jcom.cms.dto.Config;


import com.jcom.cms.entity.Lang;
import com.jcom.cms.hepers.Helpers;
import com.jcom.cms.hepers.Language;

public class Menu {
    private String title;
    private String position;
    private Lang munuIttem;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPosition() {
        return position == null? "" : position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Lang getMunuIttem() {
        return  munuIttem;
    }

    public String getMunuIttemLang() {
        return  Language.getLangData(munuIttem);
     }

     public String getMunuIttemByLang(String lang) {
        return Language.getLangDataByLang(munuIttem, lang);
    }

    public String getMenu() {
        return   Helpers.prepareMenu(Language.getLangData(munuIttem));
    }

    public void setMunuIttem(Lang munuIttem) {
        this.munuIttem = munuIttem;
    }

}
