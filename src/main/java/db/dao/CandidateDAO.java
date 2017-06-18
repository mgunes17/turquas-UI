package db.dao;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Result;
import db.accessor.QuestionAccessor;
import db.accessor.SentenceAccessor;
import db.configuration.ConnectionConfiguration;
import db.configuration.MappingManagerConfiguration;
import model.Question;
import model.Sentence;
import model.UniqueWord;

import java.util.*;

/**
 * Created by mustafa on 11.05.2017.
 */
public class CandidateDAO {
    private Session session;
    private static final QuestionAccessor questionAccessor = MappingManagerConfiguration
            .getMappingManager()
            .createAccessor(QuestionAccessor.class);
    private static final SentenceAccessor sentenceAccessor = MappingManagerConfiguration
            .getMappingManager()
            .createAccessor(SentenceAccessor.class);

    public CandidateDAO() {
        session = ConnectionConfiguration.getSession();
    }

    public List<Question> getQuestions(String[] words, boolean nounClause, String questionType) {
        List<String> sourceList = findSources(words);
        return findQuestions(sourceList, nounClause, questionType);
    }

    private List<Question> findQuestions(List<String> sourceList, boolean nounClause, String questionType){
        List<Question> questionList = new ArrayList<>();
        List<String> sourceNames = new ArrayList<>();

        sourceNames.addAll(sourceList);

        Result<Question> result = questionAccessor.getQuestionsWithInClause(sourceNames, nounClause, questionType);
        for(Question question: result) {
            questionList.add(question);
        }

        return questionList;
    }

    public List<Sentence> getSentences(String[] words){
        List<String> sourceList = findSources(words);

        return findSentencesInSources(sourceList);
    }

    private List<Sentence> findSentencesInSources(List<String> sourceList){
        List<String> sourceNames = new ArrayList<>();
        List<Sentence> sentenceList = new ArrayList<>();

        sourceNames.addAll(sourceList);

        Result<Sentence> result = sentenceAccessor.getSentencesBySources(sourceNames);
        for(Sentence sentence: result) {
            sentenceList.add(sentence);
        }

        return sentenceList;
    }

    private List<String> findSources(String[] words){
        List<String> sourceList = new ArrayList<>();
        Map<String, Integer> sourceCount = new HashMap<>();
        int i = 0;
        for(String word: words) {
            if(!word.equals("")){
                UniqueWord uniqueWord = MappingManagerConfiguration.getUniqueWordMapper().get(word);
                if(uniqueWord != null) {
                    if(uniqueWord.getValueMap() != null){
                        for(String sourceName: uniqueWord.getValueMap().keySet()) {
                            if(uniqueWord.getValueMap().get(sourceName) > 0) {
                                if(sourceCount.containsKey(sourceName))
                                    sourceCount.put(sourceName, sourceCount.get(sourceName) + 1);
                                else
                                    sourceCount.put(sourceName, 1);
                            }
                        }
                    }
                } else {
                   i++;
                }
            } else {
                i++;
            }
        }

        for(String source: sourceCount.keySet()) {
            //if(sourceCount.get(source) == words.length - i)
            if(sourceCount.get(source) >= (words.length - i) * 0.0)
                sourceList.add(source);
        }

        return sourceList;
    }
}
