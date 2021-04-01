package com.jcom.cms.payload.response;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String accessToken;
    private String type = "Bearer";
    private Integer id;
     private String email;
    private List<String> roles;




}
