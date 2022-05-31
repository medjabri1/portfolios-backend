package com.mjr.PortfoliosBackend.Controller;

import com.mjr.PortfoliosBackend.Model.ResumeSection;
import com.mjr.PortfoliosBackend.Service.ResumeSectionService.ResumeSectionService;
import com.mjr.PortfoliosBackend.Service.ResumeService.ResumeService;
import com.mjr.PortfoliosBackend.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/resume-section")
@CrossOrigin(origins = { "http://localhost:3000" }, allowedHeaders = "*", allowCredentials = "true")
public class ResumeSectionController {

    @Autowired
    private ResumeSectionService resumeSectionService;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private UserService userService;

    // GET ALL RESUME SECTIONS

    @GetMapping("/all-resume-sections")
    public List<ResumeSection> getAllResumeSections() {
        return resumeSectionService.getResumeSections();
    }

    // GET ONE RESUME SECTION BY ID

    @GetMapping("/id")
    public HashMap<String, Object> getResumeSectionById(@RequestParam(name = "id") int id) {

        HashMap<String, Object> response = new HashMap<>();

        if(resumeSectionService.resumeSectionExist(id)) {
            // RESUME SECTION EXIST
            response.put("status", 1);
            response.put("section", resumeSectionService.getResumeSection(id));
        } else {
            response.put("status", 0);
            response.put("error", "RESUME SECTION DOESN'T EXIST");
        }

        return response;
    }

    // GET RESUME SECTIONS OF RESUME

    @GetMapping("/resume")
    public HashMap<String, Object> getResumeSectionsOfResume(@RequestParam(name = "resume_id") int resume_id) {

        HashMap<String, Object> response = new HashMap<>();

        if(resumeService.resumeExist(resume_id)) {
            // RESUME EXIST
            response.put("status", 1);
            response.put("sections", resumeSectionService.getResumeSectionOfResume(resume_id));
        } else {
            response.put("status", 0);
            response.put("error", "RESUME DOESN'T EXIST");
        }

        return response;
    }

    // GET RESUME SECTIONS OF USER

    @GetMapping("/user")
    public HashMap<String, Object> getResumeSectionsOfUser(@RequestParam(name = "user_id") int user_id) {

        HashMap<String, Object> response = new HashMap<>();

        if(userService.userExist(user_id)) {
            // USER EXIST
            response.put("status", 1);
            response.put("sections", resumeSectionService.getUserResumeSections(user_id));
        } else {
            response.put("status", 0);
            response.put("error", "USER DOESN'T EXIST");
        }

        return response;
    }

    // ADD NEW RESUME SECTION

    @PostMapping("/new")
    public HashMap<String, Object> addNewResumeSection(@RequestBody ResumeSection resumeSection) {

        HashMap<String, Object> response = new HashMap<>();

        if(resumeService.resumeExist(resumeSection.getResume().getId())) {
            // RESUME EXIST

            resumeSection.setCreatedAt(new Date());

            response.put("status", 1);
            response.put("section", resumeSectionService.saveResumeSection(resumeSection));

        } else {
            // RESUME DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "RESUME DOESN'T EXIST");
        }

        return response;
    }

    // UPDATE RESUME SECTION

    @PutMapping("/update")
    public HashMap<String, Object> updateResumeSection(@RequestBody ResumeSection resumeSection) {

        HashMap<String, Object> response = new HashMap<>();

        if(resumeSectionService.resumeSectionExist(resumeSection.getId())) {
            // RESUME SECTION EXIST

            ResumeSection initial_resumeSection = resumeSectionService.getResumeSection(resumeSection.getId());

            if(resumeSection.getTitle() == null)
                resumeSection.setTitle(initial_resumeSection.getTitle());

            if(resumeSection.getContent() == null)
                resumeSection.setContent(initial_resumeSection.getContent());

            resumeSection.setResume(initial_resumeSection.getResume());
            resumeSection.setCreatedAt(new Date());

            response.put("status", 1);
            response.put("section", resumeSectionService.saveResumeSection(resumeSection));

        } else {
            // RESUME DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "RESUME DOESN'T EXIST");
        }

        return response;
    }

    // DELETE RESUME SECTION

    @DeleteMapping("/delete")
    public HashMap<String, Object> deleteResumeSection(@RequestParam(name = "id") int id) {

        HashMap<String, Object> response = new HashMap<>();

        if(resumeSectionService.resumeSectionExist(id)) {
            // RESUME SECTION EXIST

            resumeSectionService.deleteResumeSection(id);
            response.put("status", 1);

        } else {
            // RESUME DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "RESUME DOESN'T EXIST");
        }

        return response;
    }

}
