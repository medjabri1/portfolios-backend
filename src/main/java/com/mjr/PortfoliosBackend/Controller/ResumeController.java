package com.mjr.PortfoliosBackend.Controller;

import com.mjr.PortfoliosBackend.Model.ProjectDetails;
import com.mjr.PortfoliosBackend.Model.Resume;
import com.mjr.PortfoliosBackend.Service.ResumeService.ResumeService;
import com.mjr.PortfoliosBackend.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins = { "http://localhost:3000" }, allowedHeaders = "*", allowCredentials = "true")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired private UserService userService;

    // GET ALL RESUMES

    @GetMapping("/all-resumes")
    public List<Resume> getAllResumes() {
        return resumeService.getResumes();
    }

    // GET RESUME OF USER BY ID

    @GetMapping("/user")
    public HashMap<String, Object> getResumeOfUser(@RequestParam(name = "user_id") int user_id) {

        HashMap<String, Object> response = new HashMap<>();

        if(resumeService.getResumeOfUser(user_id) != null) {
            // RESUME EXIST
            response.put("status", 1);
            response.put("resume", resumeService.getResumeOfUser(user_id));
        } else {
            // RESUME DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "RESUME DOESN'T EXIST");
        }

        return response;
    }

    // GET RESUME BY ID

    @GetMapping("/id")
    public HashMap<String, Object> getResumeById(@RequestParam(name = "id") int id) {

        HashMap<String, Object> response = new HashMap<>();

        if(resumeService.resumeExist(id)) {
            // RESUME EXIST
            response.put("status", 1);
            response.put("resume", resumeService.getResume(id));
        } else {
            // RESUME DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "RESUME DOESN'T EXIST");
        }

        return response;
    }

    // ADD NEW RESUME

    @PostMapping("/new")
    public HashMap<String, Object> addNewResume(@RequestBody Resume resume) {

        HashMap<String, Object> response = new HashMap<>();

        if(resumeService.getResumeOfUser(resume.getUser().getId()) != null) {
            // RESUME ALREADY EXISTS
            response.put("status", 0);
            response.put("error", "RESUME ALREADY EXISTS");

        } else {

            if(userService.userExist(resume.getUser().getId())) {
                // USER EXISTS
                resume.setCreatedAt(new Date());

                response.put("status", 1);
                response.put("resume", resumeService.saveResume(resume));
            } else {
                // USER DOESN'T EXIST
                response.put("status", 0);
                response.put("error", "USER DOESN'T EXIST");
            }
        }

        return response;
    }

    // UPDATE RESUME

    @PutMapping("/update")
    public HashMap<String, Object> updateResume(@RequestBody Resume resume) {

        HashMap<String, Object> response = new HashMap<>();

        if(resumeService.resumeExist(resume.getId())) {
            // RESUME EXISTS
            Resume initial_resume = resumeService.getResume(resume.getId());


            if(resume.getBiography() == null)
                resume.setBiography(initial_resume.getBiography());

            resume.setUser(initial_resume.getUser());
            resume.setCreatedAt(initial_resume.getCreatedAt());

            response.put("status", 1);
            response.put("resume", resumeService.saveResume(resume));
        } else {
            // RESUME DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "RESUME DOESN'T EXIST");
        }

        return response;
    }

    // DELETE RESUME

    @DeleteMapping("/delete")
    public HashMap<String, Object> deleteResume(@RequestParam(name = "id") int id) {

        HashMap<String, Object> response = new HashMap<>();

        if(resumeService.resumeExist(id)) {
            // RESUME EXISTS
            resumeService.deleteResume(id);
            response.put("status", 1);
        } else {
            // RESUME DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "RESUME DOESN'T EXIST");
        }

        return response;
    }

}
