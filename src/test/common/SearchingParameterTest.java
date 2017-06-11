package common;

import junit.framework.TestCase;

/**
 * Created by mustafa on 10.06.2017.
 */
public class SearchingParameterTest extends TestCase {
    private SearchingParameter searchingParameter = new SearchingParameter();

    public void testSetParameter() throws Exception {
        assertEquals(true, searchingParameter.setParameter(100, "google", 15, 8));
        try {
            searchingParameter.setParameter(44, "", 11, 10);
        } catch (SetSearchParameterException ex) {
            assertEquals("Geçersiz parametre", ex.getMessage());
        }

        assertEquals(SearchingParameter.getSource(), "google");
        assertEquals(SearchingParameter.getThreshold(), 100);
        assertEquals(SearchingParameter.getAnswerCount(), 15);
    }

    public void testValidateThreshold() throws Exception {
        assertEquals(true, searchingParameter.validateThreshold(88));
        assertEquals(false, searchingParameter.validateThreshold(101));
    }

    public void testValidateAnswerCount() throws Exception {
        assertEquals(true, searchingParameter.validateAnswerCount(88));
        assertEquals(false, searchingParameter.validateAnswerCount(0));
    }

    public void testValidateSource() throws Exception {
        assertEquals(true, searchingParameter.validateSource("database"));
        assertEquals(true, searchingParameter.validateSource("google"));
        assertEquals(false, searchingParameter.validateSource(" "));
    }

}