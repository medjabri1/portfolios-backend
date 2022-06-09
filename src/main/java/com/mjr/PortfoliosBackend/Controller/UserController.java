package com.mjr.PortfoliosBackend.Controller;

import com.mjr.PortfoliosBackend.Model.User;
import com.mjr.PortfoliosBackend.Service.UserService.UserService;
import com.mjr.PortfoliosBackend.Utility.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.io.IOUtils;



@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = { "http://localhost:3000" }, allowedHeaders = "*", allowCredentials = "true")
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

    @Autowired
    ServletContext context;

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
            response.put("action", "SIGNED UP SUCCESSFULLY");
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

    // UPDATE USER

    @PutMapping("/update")
    public HashMap<String, Object> updateUser(@RequestBody User user) {

        HashMap<String, Object> response = new HashMap<>();

        if(userService.userExist(user.getId())) {
            // USER EXIST

            User previousData = userService.getUser(user.getId());

            if(previousData.getEmail() ==  user.getEmail()) {
                // SAME EMAIL -> NO PROBLEM
                userService.updateUser(user);

                response.put("status", 1);
                response.put("result", "USER UPDATED");

            } else {
                // EMAIL CHANGED
                if(!userService.userExistEmail(user.getEmail())) {
                    // EMAIL IS FREE FOR USE

                    response.put("status", 1);
                    response.put("action", "USER UPDATED");

                } else {
                    // EMAIL ALREADY TAKEN BY ANOTHER ACCOUNT

                    response.put("status", 0);
                    response.put("error", "EMAIL ALREADY TAKEN");

                }
            }

        } else {
            // USER DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "USER DOESN'T EXIST");
        }

        return response;

    }

    // CHANGE USER PHOTO

    @PostMapping("/update/photo")
    public HashMap<String, Object> updatePhoto(
            @RequestParam(name = "id") int id,
            @RequestParam("image") MultipartFile multipartFile)
    throws IOException {

        HashMap<String, Object> response = new HashMap<>();

        if(userService.userExist(id)) {
            // USER EXIST

            User user = userService.getUser(id);
            String previousPhoto = user.getPhoto();

            String currentTime = new SimpleDateFormat("yyyy-mm-dd---hh-mm-ss").format(Calendar.getInstance().getTime());
            String originalFileName = multipartFile.getOriginalFilename();

            String fileName = "profile---"+ user.getId() +"---"+ currentTime;

            fileName = fileName +"."+ originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

            user.setPhoto(fileName);
            userService.updateUser(user);

            String uploadPath = "upload/user-photos";

            FileUploadUtil.saveFile(uploadPath, fileName, multipartFile);

            if(previousPhoto != null) {

                String folderPath = new FileSystemResource("upload/user-photos/").getFile().getAbsolutePath();

                File previous_image_file = new File(folderPath +"/"+ previousPhoto);

                if(previous_image_file != null) {
                    response.put("previous", "deleted");
                    previous_image_file.delete();
                } else {
                    response.put("previous", "not found");
                }
            } else {
                response.put("previous", "null");
            }

            response.put("status", 1);
            response.put("fileName", fileName);
            response.put("action", "USER PHOTO UPDATED");

        } else {
            // USER DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "USER DOESN'T EXIST");
        }

        return response;
    }

    // RETURN USER PHOTO

    @GetMapping("/photo/")
    @ResponseBody
        public void  getImage(@RequestParam(name = "filename") String filename, HttpServletResponse response) {

        String folderPath = new FileSystemResource("upload/user-photos/").getFile().getAbsolutePath();

        try {
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis = new FileInputStream(folderPath+"/"+filename);
            int len;
            byte[] buf = new byte[1024];
            while((len = fis.read(buf)) > 0) {
                bos.write(buf,0,len);
            }
            bos.close();
            response.flushBuffer();
        }
        catch(IOException e) {
            e.printStackTrace();
            response.setStatus(404);
        }

    }


}
