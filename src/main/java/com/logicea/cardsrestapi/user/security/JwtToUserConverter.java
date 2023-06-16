package com.logicea.cardsrestapi.user.security;


import com.logicea.cardsrestapi.user.model.Role;
import com.logicea.cardsrestapi.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Slf4j
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {
    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        User user = new User();
        user.setId(Long.valueOf(jwt.getSubject()));
        user.setRole(Role.valueOf(jwt.getClaim("role")));
        String claim = jwt.getClaim("role");
        log.error("Subject : {}", jwt.getSubject());
        log.error("Claim : {}",claim);

        return new UsernamePasswordAuthenticationToken(user,jwt, Collections.EMPTY_LIST);
    }

}
