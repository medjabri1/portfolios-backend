package com.mjr.PortfoliosBackend.Controller;

import com.mjr.PortfoliosBackend.Model.User;
import com.mjr.PortfoliosBackend.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


@RestController
@RequestMapping("/api/user")
public class UserController {

    // PASSWORD ENCODER
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    // GET LIST OF ALL USERS

    @GetMapping("/all-users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    // GET ONE USER BY ID

    @GetMapping("/id/")
    public HashMap<String, Object> getUserById(@RequestParam(name = "id") int id) {

        HashMap<String, Object> response = new HashMap<>();

        if(userService.userExist(id)) {
            // USER EXISTS
            response.put("status", 1);
            response.put("user", userService.getUser(id));
        } else {
            // USER DOESN'T EXIST
            response.put("status", 0);
            response.put("user", new User());
            response.put("error", "USER DOESN'T EXIST");
            response.put("status_code", 404);
        }

        return response;
    }

    // GET ONE USER BY EMAIL

    @GetMapping("/email/")
    public HashMap<String, Object> getUserByEmail(@RequestParam(name = "email") String email) {

        HashMap<String, Object> response = new HashMap<>();

        if(userService.userExistEmail(email)) {
            // USER EXISTS
            response.put("status", 1);
            response.put("user", userService.getUserByEmail(email));
        } else {
            // USER DOESN'T EXIST
            response.put("status", 0);
            response.put("user", new User());
            response.put("error", "USER DOESN'T EXIST");
            response.put("status_code", 404);
        }

        return response;
    }

    // SIGN UP FOR A NEW USER ACCOUNT

    @PostMapping("/signup")
    public HashMap<String, Object> signup(@RequestBody User user) {

        HashMap<String, Object> response = new HashMap<>();

        if(userService.userExistEmail(user.getEmail())) {
            // EMAIL ALREADY TAKEN
            response.put("status", 0);
            response.put("error", "EMAIL ALREADY TAKEN");
        } else {
            // EMAIL AVAILABLE
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCreatedAt(new Date());
            response.put("status", 1);
            response.put("user", userService.saveUser(user));
            //response.put("user", user);
        }

        return response;
    }

    // LOG IN FOR ONE USER

    @PostMapping("/login")
    public HashMap<String, Object> login(@RequestBody HashMap<String, String> auth, HttpSession httpSession) {

        HashMap<String, Object> response = new HashMap<>();

        String email = auth.get("email").toLowerCase(Locale.ROOT);
        String password = auth.get("password");

        if(userService.userExistEmail(email)) {
            // EMAIL EXIST

            User user = userService.getUserByEmail(email);
            boolean password_match = passwordEncoder.matches(password, user.getPassword());

            if(password_match) {
                // CORRECT PASSWORD
                httpSession.setAttribute("user_id", user.getId());

                response.put("status", 1);
                response.put("action", "LOGGED IN");
                response.put("user_id", user.getId());
                response.put("http_session", httpSession);

            } else {
                // INCORRECT PASSWORD

                response.put("status", 0);
                response.put("error", "INCORRECT PASSWORD");
            }
        } else {
            // EMAIL DOESN'T EXIST

            response.put("status", 0);
            response.put("error", "EMAIL DOESN'T EXIST");
        }

        return response;
    }

    // LOG OUT

    @PostMapping("/logout")
    public HashMap<String, Object> logout(HttpSession httpSession) {

        HashMap<String, Object> response = new HashMap<>();
        httpSession.removeAttribute("user_id");
        httpSession.invalidate();
        response.put("status", 1);
        response.put("action", "LOGGED OUT SUCCESS");

        return response;
    }

    // LOG STATUS

    @GetMapping("/log-status")
    public HashMap<String, Object> logStatus(HttpSession httpSession) {

        HashMap<String, Object> response = new HashMap<>();

        Object session_user_id = httpSession.getAttribute("user_id");
        //String user_id = session_user_id != null ? String.valueOf(session_user_id.toString()) : "0";

        response.put("status", session_user_id != null ? 1 : "LOGGED OUT");
        response.put("message", session_user_id != null ? "LOGGED IN" : "LOGGED OUT");
        response.put("http_session", httpSession);
        response.put("session_user_id", String.valueOf(session_user_id));

        return response;
    }

    // TEST

    @GetMapping("test")
    public HashMap<String, Object> test() {

        HashMap<String, Object> response = new HashMap<>();

        response.put("status", 10);

        return response;
    }

}
