package springsecurity.withjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springsecurity.withjwt.jwt.JWTTokenProvider;
import springsecurity.withjwt.payload.LoginRequest;
import springsecurity.withjwt.payload.LoginResponse;
import springsecurity.withjwt.payload.RandomStuff;
import springsecurity.withjwt.security.CustomUserDetail;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class LodaRestController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest ){

//        /** xác thục từ username password*/
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getUsername(),
//                        loginRequest.getPassword()
//                )
//        );
//        /** nếu không xảy ra ngoai lệ tức là thông tin hợp lệ
//         * thiết lập thông tin authentication va security context
//         */
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        /** trả jwt về cho người dùng*/
//        String jwt= jwtTokenProvider.generationJWTToken((CustomUserDetail) authentication.getPrincipal());

        return new LoginResponse("quang dz");
    }

    @GetMapping("/random")
    public RandomStuff randomStuff(){
        return  new RandomStuff("JWT hợp lệ!!!");
    }

}
