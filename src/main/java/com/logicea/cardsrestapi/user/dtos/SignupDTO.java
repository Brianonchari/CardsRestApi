package com.logicea.cardsrestapi.user.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@RequiredArgsConstructor
public class SignupDTO {
    private String email;
    private String password;
    private String role;
}
