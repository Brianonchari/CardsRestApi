package com.logicea.cardsrestapi.user.dtos;

import com.logicea.cardsrestapi.user.model.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {
    private Long id;
    private String username;

    public static UserDTO from(User user) {
        return builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
