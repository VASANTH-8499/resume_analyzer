 package com.example.resumeanalyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/analyze")
    public AnalysisResult analyzeResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("role") String role) {

        return resumeService.analyzeResume(file, role);
    }
}
