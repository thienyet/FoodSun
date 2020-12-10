package luabui.application.controller;

import lombok.extern.slf4j.Slf4j;
import luabui.application.model.User;
import luabui.application.security.jwt.JwtProvider;
import luabui.application.service.UserService;
import luabui.application.vo.request.LoginForm;
import luabui.application.vo.response.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/foodsun/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginForm loginForm) {
        // throws Exception if authentication failed

        try {

            Authentication authentication=new UsernamePasswordAuthenticationToken(loginForm,loginForm.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails=(UserDetails)authentication.getPrincipal();
            String jwt = jwtProvider.generate(authentication);

            User user = userService.findByEmail(userDetails.getUsername());
            return ResponseEntity.ok(new JwtResponse(jwt, user.getEmail(), user.getRole().getRole()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
