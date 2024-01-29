package in.weprolance.controller;

import in.weprolance.entity.AuthRequest;
import in.weprolance.entity.UserInfo;
import in.weprolance.service.JwtService;
import in.weprolance.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Spring Security Tutorial!";
    }

    @PostMapping("/users")
    public String addUser(@RequestBody UserInfo userInfo) {
        return userInfoService.addUser(userInfo);
    }

    @PostMapping("/login")
    public String authUser(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUserName());
        } else {
            throw new UsernameNotFoundException("Invalid User request");
        }
    }

    @GetMapping("/users")
    public List<UserInfo> getAllUsers(){
        return userInfoService.getAllUsers();
    }

    @GetMapping("/users/{userId}")
    public UserInfo getUser(@PathVariable Integer userId){
        return userInfoService.getUser(userId);
    }

}
