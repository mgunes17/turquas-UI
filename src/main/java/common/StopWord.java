package common;

import operator.FileOperator;

import java.util.Set;

/**
 * Created by mustafa on 11.06.2017.
 */
public class StopWord {
    public static Set<String> STOP_WORD_SET;

    static {
        STOP_WORD_SET = new FileOperator().readStopWords();
    }
}
