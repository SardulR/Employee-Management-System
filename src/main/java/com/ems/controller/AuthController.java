package com.ems.controller;

import com.ems.dto.request.LoginRequestDto;
import com.ems.dto.request.UserRequestDto;
import com.ems.dto.response.UserResponseDto;
import com.ems.entity.User;
import com.ems.jwt.JwtUtil;
import com.ems.repository.UserRepo;
import com.ems.service.UserService;
import com.ems.utils.CookieClient;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController{



    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil  jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;

    //USER LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );
        User user = userRepo.findByUsername(loginRequestDto.getUsername());
        if(user!=null){
            String token = jwtUtil.generateToken(user);
            CookieClient.setCookie(httpServletResponse,token,user.getUsername());
            CookieClient.setCookie(httpServletResponse,token,user.getRole().toString());
            return new ResponseEntity<>(Map.of("token",token, "role", user.getRole()), HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error","Invalid username or password"), HttpStatus.UNAUTHORIZED);
    }

}
