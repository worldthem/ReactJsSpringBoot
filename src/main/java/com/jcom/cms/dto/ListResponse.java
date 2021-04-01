package com.jcom.cms.dto;

import lombok.*;
import org.springframework.data.domain.Page;


import java.util.Map;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListResponse<T> {
    private Page<T> content;
    private Object row;
    private String title;
    private String metad;
    private String metak;
    private Map<String, Object> mapData;
    private String text;
    private String css;
    private String redirect;

    public ListResponse(Page<T> content, String title) {
        this.content = content;
        this.title = title;
    }

    public ListResponse(Page<T> content, String title, String metad, String metak) {
        this.content = content;
        this.title = title;
        this.metad = metad;
        this.metak = metak;
    }

    public ListResponse(Page<T> content, String title, Map<String, Object> mapData) {
        this.content = content;
        this.title = title;
        this.mapData = mapData;
    }

    public ListResponse(Object row, String title, Map<String, Object> mapData) {
        this.row = row;
        this.title = title;
        this.mapData = mapData;
    }

    public ListResponse(Object row, String title) {
        this.row = row;
        this.title = title;
    }


}
