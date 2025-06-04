package model;

public class CollabQuestion extends AbstractQuestion {
    public CollabQuestion(String question, String[] options, int correctIndex) {
        super(question, options, correctIndex);
    }

    @Override
    public String getCategory() {
        return "Colaboraciones";
    }
}
