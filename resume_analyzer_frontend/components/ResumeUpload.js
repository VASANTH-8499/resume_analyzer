 import React, { useState } from "react";
import axios from "axios";
import "./ResumeUpload.css";

function ResumeUpload() {
  const [file, setFile] = useState(null);
  const [role, setRole] = useState("java developer");
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
    setResult(null);
  };

  const handleAnalyze = async () => {
    if (!file) {
      alert("Please upload a resume first!");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);
    formData.append("role", role);

    try {
      setLoading(true);
      const response = await axios.post(
        "http://localhost:8080/api/analyze",
        formData
      );
      setResult(response.data);
    } catch (error) {
      console.error("Error analyzing resume:", error);
      alert("Backend not running or API error");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="card">
      <h1 className="title">📄 AI Resume Analyzer</h1>

      <p className="subtitle">
        Upload your resume & get ATS Score, Skills & Role-based Analysis
      </p>

      {/* Role Dropdown */}
      <select
        className="dropdown"
        value={role}
        onChange={(e) => setRole(e.target.value)}
      >
        <option value="java developer">Java Developer</option>
        <option value="full stack developer">Full Stack Developer</option>
        <option value="frontend developer">Frontend Developer</option>
      </select>

      {/* File Upload */}
      <input
        type="file"
        accept=".pdf,.doc,.docx"
        onChange={handleFileChange}
        className="file-input"
      />

      {file && <p className="file-name">Selected: {file.name}</p>}

      {/* Button */}
      <button className="analyze-btn" onClick={handleAnalyze}>
        {loading ? "Analyzing..." : "🚀 Analyze Resume"}
      </button>

      {/* Result */}
      {result && (
        <div className="result-card">
          <div className="score">ATS Score: {result.score}/100</div>

          <div className="section-title">✅ Skills Found:</div>
          <div className="content-text">{result.skillsFound}</div>

          <div className="section-title">💡 Suggestions:</div>
          <div className="content-text">{result.suggestions}</div>
        </div>
      )}
    </div>
  );
}

export default ResumeUpload;
