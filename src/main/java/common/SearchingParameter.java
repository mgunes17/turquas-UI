package common;

import data_provider.CassandraProvider;
import data_provider.GoogleProvider;
import data_provider.Provider;

/**
 * Created by mustafa on 10.06.2017.
 */
public class SearchingParameter {
    private static int threshold = 30;
    private static int answerCount = 20;
    private static int linkCount = 8;
    private static String source = "google";

    public boolean setParameter(int threshold, String source, int answerCount, int linkCount) throws SetSearchParameterException{
        if(validateThreshold(threshold) && validateSource(source)
                && validateAnswerCount(answerCount) && validateLinkCount(linkCount)) {
            SearchingParameter.threshold = threshold;
            SearchingParameter.source = source;
            SearchingParameter.answerCount = answerCount;
            SearchingParameter.linkCount = linkCount;
            return true;
        } else
            throw new SetSearchParameterException("Geçersiz parametre");
    }

    public static Provider getProvider() throws NotFoundDataProviderException {
        if(source.equals("google"))
            return new GoogleProvider();
        else if(source.equals("database"))
            return new CassandraProvider();
        else
            throw new NotFoundDataProviderException("Veri kaynağı bulunamadı:" + source);
    }

    protected boolean validateThreshold(int value) {
        return (value >= 0 && value <= 100);
    }

    protected boolean validateAnswerCount(int value) {
        return value > 0 && value <= 100;
    }

    protected boolean validateSource(String value) {
        return (value.equals("google") || value.equals("database"));
    }

    protected boolean validateLinkCount(int value) {
        return value >=5 && value <= 15;
    }

    public static int getThreshold() {
        return threshold;
    }

    public static String getSource() {
        return source;
    }

    public static int getAnswerCount() {
        return answerCount;
    }

    public static int getLinkCount() {
        return linkCount;
    }
}
