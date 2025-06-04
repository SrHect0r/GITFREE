package model;

import java.io.*;
import java.util.*;

public class QuestionFactory {
    public static List<AbstractQuestion> loadQuestions(String path) {
        List<AbstractQuestion> questions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length < 7) continue;

                String category = parts[0];
                String q = parts[1];
                String[] opts = { parts[2], parts[3], parts[4], parts[5] };
                int correct = Integer.parseInt(parts[6]);
                if (correct < 0 || correct > 3) continue;

                switch (category.toLowerCase()) {
                    case "albumes":
                        questions.add(new AlbumQuestion(q, opts, correct));
                        break;
                    case "colaboraciones":
                        questions.add(new CollabQuestion(q, opts, correct));
                        break;
                    case "vida personal":
                        questions.add(new PersonalLifeQuestion(q, opts, correct));
                        break;
                    default:
                        System.out.println("Categoría desconocida: " + category);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }
}