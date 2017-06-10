package operator;

import junit.framework.TestCase;

/**
 * Created by mustafa on 10.06.2017.
 */
public class AnswerOperatorTest extends TestCase {
    AnswerOperator answerOperator = new AnswerOperator();

    public void testConvertDeepLearningInputForm() throws Exception {
        assertEquals("türkiyenin başkenti ankaradır",
                answerOperator.convertDeepLearningInputForm("Türkiye'nin başkenti Ankara'dır."));
        assertEquals("türkiyenin başkenti ankaradır",
                answerOperator.convertDeepLearningInputForm(" Türkiye'nin       başkenti Ankara'dır."));
        assertEquals("bana yarın geliyorum dedi",
                answerOperator.convertDeepLearningInputForm("Bana \"Yarın geliyorum\" dedi."));
        assertEquals("java bir programlama dili oldukça popülerdir",
                answerOperator.convertDeepLearningInputForm("Java (bir programlama dili) oldukça popülerdir."));
        assertEquals("java bir programlama dili oldukça popülerdir",
                answerOperator.convertDeepLearningInputForm("    Java [bir programlama dili]     oldukça popülerdir."));
    }

    public void testEliminateStopWords() throws Exception {
        assertEquals("kalemim", answerOperator.eliminateStopWords("benim bir kalemim var"));
    }

}