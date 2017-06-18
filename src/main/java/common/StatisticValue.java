package common;

/**
 * Created by ercan on 13.06.2017.
 */
public class StatisticValue {
    private double candidateFetchTime;
    private double pythonAnswerTime;
    private double totalAnswerTime;
    private double otherTime;
    private int totalCandidateCount;

    public double getCandidateFetchTime() {
        return candidateFetchTime;
    }

    public void setCandidateFetchTime(double candidateFetchTime) {
        this.candidateFetchTime = candidateFetchTime;
    }

    public double getPythonAnswerTime() {
        return pythonAnswerTime;
    }

    public void setPythonAnswerTime(double pythonAnswerTime) {
        this.pythonAnswerTime = pythonAnswerTime;
    }

    public double getTotalAnswerTime() {
        return totalAnswerTime;
    }

    public void setTotalAnswerTime(double totalAnswerTime) {
        this.totalAnswerTime = totalAnswerTime;
    }

    public double getOtherTime() {
        return otherTime;
    }

    public void setOtherTime(double otherTime) {
        this.otherTime = otherTime;
    }

    public int getTotalCandidateCount() {
        return totalCandidateCount;
    }

    public void setTotalCandidateCount(int totalCandidateCount) {
        this.totalCandidateCount = totalCandidateCount;
    }
}
