package operator;

import junit.framework.TestCase;

import java.util.Set;

/**
 * Created by mustafa on 11.06.2017.
 */
public class FileOperatorTest extends TestCase {
    public void testReadStopWords() throws Exception {
        FileOperator fileOperator = new FileOperator();
        Set<String> stopWords = fileOperator.readStopWords();

        assertEquals(209, stopWords.size());
        assertEquals(true, stopWords.contains("şöyle"));
        assertEquals(false, stopWords.contains("kader"));
    }

}