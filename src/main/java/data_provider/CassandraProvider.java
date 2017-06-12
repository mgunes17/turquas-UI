package data_provider;

import component.user_interface.candidate.FindingCandidate;
import home_base.SentenceType;
import model.Answer;
import model.QuestionUI;
import model.QuestionForCompare;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mustafa on 10.06.2017.
 */
public class CassandraProvider implements Provider {
    public Set<Answer> findCandidateList(QuestionUI questionUI) {
        FindingCandidate findingCandidate = new FindingCandidate("token_average");
        List<QuestionForCompare> questionForCompareList = findingCandidate
                .findSentencesForDeepLearning(questionUI.getQuestion());

        return convertQuestionForCompareToAnswer(questionForCompareList);
    }

    protected Set<Answer> convertQuestionForCompareToAnswer(List<QuestionForCompare> questionForCompareList) {
        Set<Answer> answerSet = new HashSet<Answer>();

        for(QuestionForCompare questionForCompare: questionForCompareList) {
            answerSet.add(new Answer(questionForCompare.getAnswer(), questionForCompare.getSourceName()));
        }

        return answerSet;
    }
}
