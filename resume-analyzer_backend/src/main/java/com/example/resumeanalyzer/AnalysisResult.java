 package com.example.resumeanalyzer;

public class AnalysisResult {
    private int score;
    private String skillsFound;
    private String suggestions;

    public AnalysisResult(int score, String skillsFound, String suggestions) {
        this.score = score;
        this.skillsFound = skillsFound;
        this.suggestions = suggestions;
    }

    public int getScore() {
        return score;
    }

    public String getSkillsFound() {
        return skillsFound;
    }

    public String getSuggestions() {
        return suggestions;
    }
}
