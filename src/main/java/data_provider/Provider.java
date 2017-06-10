package data_provider;

import model.Answer;
import model.Question;

import java.io.IOException;
import java.util.Set;

/**
 * Created by mustafa on 10.06.2017.
 */
public interface Provider {
    Set<Answer> findCandidateList(Question question) throws IOException;
}
