package com.jcom.cms.dto.Attributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jcom.cms.hepers.HelpersFile;


import java.util.LinkedHashMap;
import java.util.Map;

public class Attributes {
    private Map<String, AttributesVar> fields  = new LinkedHashMap<String, AttributesVar>();

    public Map<String, AttributesVar> getFields() {
        return fields==null? new LinkedHashMap<>() : fields;
    }

    public void setFields(Map<String, AttributesVar> fields) {
        this.fields = fields;
    }

    public void save(){
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String data =gson.toJson(this);
        HelpersFile.updateFile(data, "content/config/attr.json");
    }

}
