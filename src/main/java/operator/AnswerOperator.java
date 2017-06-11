package operator;

import common.StopWord;
import model.Answer;
import zemberek.langid.LanguageIdentifier;

import java.io.IOException;
import java.util.*;

/**
 * Created by mustafa on 10.06.2017.
 */
public class AnswerOperator {
    public Set<Answer> chooseCandidateSentences(List<Answer> answerList) {
        Set<Answer> candidateList = new HashSet<Answer>();

        for(Answer answer: answerList) {
            if(!answer.getOriginalAnswer().contains("?")
                    && answer.getOriginalAnswer().split(" ").length <= 20
                    && answer.getOriginalAnswer().split(" ").length >= 5
                    && !answer.getOriginalAnswer().contains("|")
                    && !answer.getOriginalAnswer().contains("·")
                    && validateLanguage(answer.getOriginalAnswer()))
                candidateList.add(answer);
        }

        return candidateList;
    }

    public void preparaForDeepLearningText(Set<Answer> answerList) {
        for(Answer answer: answerList) {
            String sentence = convertDeepLearningInputForm(answer.getOriginalAnswer());
            sentence = eliminateStopWords(sentence);
            answer.setDeepLearingForm(sentence);
        }
    }

    protected String convertDeepLearningInputForm(String text) {
        String pattern = "[:|'|.|,|;|!|\"|\\[|\\]|(|)]";
        return text.replaceAll(pattern, "").toLowerCase().replaceAll(" ( )+", " ")
                .replaceFirst("^[ ]+", "");
    }

    protected String eliminateStopWords(String sentence) {
        String[] words = sentence.split(" ");
        StringBuilder returnedSentence = new StringBuilder();

        for(String word: words) {
            if(!StopWord.STOP_WORD_SET.contains(word)) {
                returnedSentence.append(word);
                returnedSentence.append(" ");
            }
        }

        returnedSentence.deleteCharAt(returnedSentence.length() - 1);
        return returnedSentence.toString();
    }

    protected boolean validateLanguage(String sentence) {
        try {
            LanguageIdentifier languageIdentifier  = LanguageIdentifier.fromInternalModelGroup("tr_group");
            if(languageIdentifier.identify(sentence).equals("tr")){
                return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Answer> convertSetToMap(Set<Answer> answerSets) {
        Map<String, Answer> answerMap = new HashMap<String, Answer>();

        for(Answer answer: answerSets)
            answerMap.put(answer.getDeepLearingForm(), answer);

        return answerMap;
    }
}
