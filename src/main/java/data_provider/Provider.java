package data_provider;

import model.Answer;
import model.QuestionUI;

import java.io.IOException;
import java.util.Set;

/**
 * Created by mustafa on 10.06.2017.
 */
public interface Provider {
    Set<Answer> findCandidateList(QuestionUI questionUI) throws IOException;
}
