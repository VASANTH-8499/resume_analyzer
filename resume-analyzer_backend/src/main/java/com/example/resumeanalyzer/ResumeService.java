 package com.example.resumeanalyzer;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Service
public class ResumeService {

    // Role based skills map
    private final Map<String, List<String>> roleSkills = Map.of(
            "java developer", Arrays.asList("java", "spring", "spring boot", "hibernate", "jdbc", "mysql", "sql"),
            "full stack developer", Arrays.asList("java", "spring", "react", "javascript", "html", "css", "mysql"),
            "frontend developer", Arrays.asList("html", "css", "javascript", "react", "bootstrap")
    );

    public AnalysisResult analyzeResume(MultipartFile file, String role) {
        try {
            InputStream inputStream = file.getInputStream();
            String text = ResumeParser.extractText(inputStream);

            System.out.println("===== EXTRACTED RESUME TEXT =====");
            System.out.println(text);
            System.out.println("=================================");

            if (text == null || text.isEmpty()) {
                return new AnalysisResult(0, "No text extracted",
                        "Resume text could not be read. Upload proper PDF/DOCX.");
            }

            text = text.toLowerCase();

            // Get role skills
            List<String> requiredSkills = roleSkills.getOrDefault(role.toLowerCase(),
                    roleSkills.get("java developer"));

            int matched = 0;
            List<String> foundSkills = new ArrayList<>();
            List<String> missingSkills = new ArrayList<>();

            for (String skill : requiredSkills) {
                if (text.contains(skill)) {
                    matched++;
                    foundSkills.add(skill);
                } else {
                    missingSkills.add(skill);
                }
            }

            // ATS Score Calculation
            int skillScore = (matched * 70) / requiredSkills.size();
            int projectScore = text.contains("project") ? 15 : 0;
            int experienceScore = text.contains("experience") ? 15 : 0;

            int finalScore = skillScore + projectScore + experienceScore;

            String suggestions = generateSuggestions(finalScore, missingSkills);

            String skillsResult = foundSkills.isEmpty()
                    ? "No matching skills detected"
                    : String.join(", ", foundSkills);

            return new AnalysisResult(finalScore, skillsResult, suggestions);

        } catch (Exception e) {
            e.printStackTrace();
            return new AnalysisResult(0, "", "Error analyzing resume.");
        }
    }

    private String generateSuggestions(int score, List<String> missingSkills) {
        if (score >= 80) {
            return "Excellent ATS Resume! Strong match for the role.";
        } else if (score >= 50) {
            return "Good resume. Add more projects and missing skills: " + missingSkills;
        } else {
            return "Low ATS score. Add skills, projects, and experience keywords. Missing: " + missingSkills;
        }
    }
}
