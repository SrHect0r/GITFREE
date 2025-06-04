package model;

public class PersonalLifeQuestion extends AbstractQuestion {
    public PersonalLifeQuestion(String question, String[] options, int correctIndex) {
        super(question, options, correctIndex);
    }
    @Override
    public String getCategory() {
        return "Personal";
    }
}
