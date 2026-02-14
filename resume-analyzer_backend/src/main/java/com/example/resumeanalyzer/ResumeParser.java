 package com.example.resumeanalyzer;

import org.apache.tika.Tika;
import java.io.InputStream;

public class ResumeParser {

    public static String extractText(InputStream inputStream) {
        try {
            Tika tika = new Tika();
            String text = tika.parseToString(inputStream);

            if (text == null || text.trim().isEmpty()) {
                return "";
            }

            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
