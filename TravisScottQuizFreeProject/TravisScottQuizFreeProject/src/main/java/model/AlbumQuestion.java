package model;

public class AlbumQuestion extends AbstractQuestion {
    public AlbumQuestion(String questionText, String[] options, int correctAnswerIndex) {
        super(questionText, options, correctAnswerIndex);
    }

    @Override
    public String getCategory() {
        return "Álbumes";
    }
}
