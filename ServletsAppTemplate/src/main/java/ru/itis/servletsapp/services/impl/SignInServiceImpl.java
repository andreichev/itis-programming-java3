package ru.itis.servletsapp.services.impl;

import io.jsonwebtoken.*;
import ru.itis.servletsapp.dao.UsersRepository;
import ru.itis.servletsapp.dto.UserDto;
import ru.itis.servletsapp.dto.UserForm;
import ru.itis.servletsapp.exceptions.ValidationException;
import ru.itis.servletsapp.model.User;
import ru.itis.servletsapp.services.PasswordEncoder;
import ru.itis.servletsapp.services.SignInService;
import ru.itis.servletsapp.services.validation.ErrorEntity;

public class SignInServiceImpl implements SignInService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtBuilder jwtBuilder;
    private final JwtParser jwtParser;
    private final String jwtSecret;

    public SignInServiceImpl(String jwtSecret, UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.jwtSecret = jwtSecret;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtSecret);
        jwtParser = Jwts.parser()
                .setSigningKey(jwtSecret);
    }

    @Override
    public UserDto signIn(UserForm userForm) {
        User user = usersRepository.findByEmail(userForm.getEmail())
                .orElseThrow(() -> new ValidationException(ErrorEntity.NOT_FOUND));
        if (passwordEncoder.matches(userForm.getPassword(), user.getHashPassword()) == false) {
            throw new ValidationException(ErrorEntity.INCORRECT_PASSWORD);
        }
        UserDto userDto = UserDto.from(user);
        jwtBuilder.claim("email", userForm.getEmail());
        jwtBuilder.claim("password", userForm.getPassword());
        String token = jwtBuilder.compact();
        userDto.setToken(token);
        return userDto;
    }

    @Override
    public UserDto signIn(String token) {
        Claims claims;
        try {
            claims = jwtParser.parseClaimsJws(token).getBody();
        } catch (SignatureException e) {
            throw new ValidationException(ErrorEntity.FORBIDDEN);
        }
        String email = (String) claims.get("email");
        String password = (String) claims.get("password");
        return signIn(UserForm.builder().email(email).password(password).build());
    }
}
