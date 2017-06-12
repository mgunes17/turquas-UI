package common;

import junit.framework.TestCase;

/**
 * Created by mustafa on 13.06.2017.
 */
public class SearchingParameterTest extends TestCase {
    private SearchingParameter searchingParameter = SearchingParameter.getSearchingParameter();

    public void testSetThreshold() throws Exception {
        searchingParameter.setThreshold(55);
        searchingParameter.setThreshold(120);
        assertEquals(55, searchingParameter.getThreshold());
    }

    public void testSetAnswerCount() throws Exception {
        searchingParameter.setAnswerCount(100);
        searchingParameter.setAnswerCount(-12);
        assertEquals(100, searchingParameter.getAnswerCount());
    }

    public void testSetLinkCount() throws Exception {
        searchingParameter.setLinkCount(10);
        searchingParameter.setLinkCount(3);
        assertEquals(10, searchingParameter.getLinkCount());
    }

}