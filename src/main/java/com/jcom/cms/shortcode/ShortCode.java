package com.jcom.cms.shortcode;

import java.util.Map;

public interface ShortCode {
    String shortCodes(String code, Map<String,String> map);
}
