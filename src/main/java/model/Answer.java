package model;

/**
 * Created by mustafa on 10.06.2017.
 */
public class Answer {
    private String originalAnswer;
    private String deepLearingForm;
    private double similarityValue;
    private String sourceName;

    public Answer() {
    }

    public Answer(String originalAnswer, String sourceName) {
        this.originalAnswer = originalAnswer;
        this.sourceName = sourceName;
    }

    public double getSimilarityValue() {
        return similarityValue;
    }

    public void setSimilarityValue(double similarityValue) {
        this.similarityValue = similarityValue;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getOriginalAnswer() {
        return originalAnswer;
    }

    public void setOriginalAnswer(String originalAnswer) {
        this.originalAnswer = originalAnswer;
    }

    public String getDeepLearingForm() {
        return deepLearingForm;
    }

    public void setDeepLearingForm(String deepLearingForm) {
        this.deepLearingForm = deepLearingForm;
    }
}
