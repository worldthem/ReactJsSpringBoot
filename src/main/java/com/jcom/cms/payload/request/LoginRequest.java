package com.jcom.cms.payload.request;

import lombok.*;



@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
 }