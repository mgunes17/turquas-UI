package common;

import data_provider.CassandraProvider;
import data_provider.GoogleProvider;
import data_provider.Provider;

/**
 * Created by mustafa on 10.06.2017.
 */
public class SearchingParameter {
    private static SearchingParameter searchingParameter = null;
    private int threshold = 30;
    private int answerCount = 20;
    private int linkCount = 8;
    private String source = "google";
    private String questionWordDeleted = "no";

    private SearchingParameter() {

    }

    public static SearchingParameter getSearchingParameter() {
        if(searchingParameter == null)
            searchingParameter = new SearchingParameter();

        return searchingParameter;
    }

    public Provider getProvider() throws NotFoundDataProviderException {
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

    protected boolean validateQuestionWordDeleted(String questionWordDeleted) {
        return questionWordDeleted.equals("yes") || questionWordDeleted.equals("no");
    }

    public int getThreshold() {
        return threshold;
    }

    public String getSource() {
        return source;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public int getLinkCount() {
        return linkCount;
    }

    public void setThreshold(int threshold) {
        if(validateThreshold(threshold))
            this.threshold = threshold;
    }

    public void setAnswerCount(int answerCount) {
        if(validateAnswerCount(answerCount))
            this.answerCount = answerCount;
    }

    public void setLinkCount(int linkCount) {
        if(validateLinkCount(linkCount))
            this.linkCount = linkCount;
    }

    public void setSource(String source) {
        if(validateSource(source))
            this.source = source;
    }

    public String getQuestionWordDeleted() {
        return questionWordDeleted;
    }

    public void setQuestionWordDeleted(String questionWordDeleted) {
        if(validateQuestionWordDeleted(questionWordDeleted))
            this.questionWordDeleted = questionWordDeleted;
    }
}
