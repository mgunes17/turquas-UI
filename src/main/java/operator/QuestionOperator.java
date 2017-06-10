package operator;

import model.Question;

/**
 * Created by mustafa on 10.06.2017.
 */
public class QuestionOperator {
    public boolean validateQuestion(String question) {
        //kelime sayısı, türkçe, soru cümlesi olmaması ....
        return true;
    }

    public Question createQuestion(String questionText) {
        //kuralları çıkar kelimelere ayır vs
        return new Question(questionText);
    }
}
