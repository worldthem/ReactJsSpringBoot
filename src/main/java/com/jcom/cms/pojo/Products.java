package com.jcom.cms.pojo;

import com.jcom.cms.entity.Comments;
import com.jcom.cms.entity.Gallery;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Products {
    private Integer productid;
    private Integer userid;
    private String catid;
    private String SKU;
    private Integer qtu = (Integer) 0;
    private Integer store;
    private String title;
    private String cpu;
    private String metad;
    private String metak;
    private String description;
    private String text;
    private Float weight= (float) 0.0;
    private String stock;
    private Float price= (float) 0.0;
    private Float sale_price= (float) 0.0;
    private String attr;
    private String optionsdata;
    private String image;
    private Integer hide;
    private LocalDateTime updated_at;
    private LocalDateTime created_at;
    @OneToMany(mappedBy="product", fetch = FetchType.LAZY)
    private List<Gallery> gallery;
    @OneToMany(mappedBy="product", fetch = FetchType.LAZY)
    private  List<Comments> comments;


}
