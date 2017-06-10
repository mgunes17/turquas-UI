package data_provider;

import junit.framework.TestCase;

/**
 * Created by mustafa on 10.06.2017.
 */
public class GoogleProviderTest extends TestCase {
    public void testConvertSearchableForm() throws Exception {
        GoogleProvider provider = new GoogleProvider();
        assertEquals("java+nedir", provider.convertSearchableForm("java nedir"));
        assertEquals("java+nedir", provider.convertSearchableForm("java    nedir"));
        assertEquals("türkiyenin+başkenti+neresidir", provider.convertSearchableForm("türkiyenin başkenti    neresidir"));
        assertEquals("bilgisayar", provider.convertSearchableForm("bilgisayar"));
    }

}