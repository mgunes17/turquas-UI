package servlet;

import common.PythonSocket;
import common.SearchingParameter;
import common.StatisticValue;
import data_provider.Provider;
import model.Answer;
import model.QuestionUI;
import operator.AnswerOperator;
import operator.FileOperator;
import operator.QuestionOperator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mustafa on 10.06.2017.
 */
@WebServlet(name = "FindingAnswerListServlet" , urlPatterns = {"/findinganswers"})
public class FindingAnswerListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long totalStart = System.nanoTime();
        double pythonDiff = 0;
        double candidateDiff = 0;

        StatisticValue statisticValue = new StatisticValue();
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        session.setAttribute("set", "0");
        String questionText = request.getParameter("question").toLowerCase();
        session.setAttribute("question", request.getParameter("question"));
        QuestionOperator questionOperator = new QuestionOperator();


        if(!questionOperator.validateQuestion(questionText)) {
            session.setAttribute("cevap", 2);
        } else {
            try {
                Provider provider = SearchingParameter.getSearchingParameter().getProvider();
                QuestionUI questionUI = questionOperator.createQuestion(questionText);

                long start_time = System.nanoTime();
                Set<Answer> candidateSet = provider.findCandidateList(questionUI);
                long end_time = System.nanoTime();
                candidateDiff = (end_time - start_time)/1e6;
                System.out.println("candidate elde edilmesi" + candidateDiff);
                statisticValue.setCandidateFetchTime(candidateDiff);

                if(candidateSet.size() == 0) {
                    session.setAttribute("cevap", 4); //Aday cümle bulunamadı
                } else {
                    //Kullanıcı sorusunu dosyaya yazdır
                    FileOperator fileOperator = new FileOperator();
                    fileOperator.writeUserQuestionToFile(questionText);

                    AnswerOperator answerOperator = new AnswerOperator();
                    answerOperator.prepareForDeepLearningText(candidateSet);
                    Map<String, Answer> candidateMap = answerOperator.convertSetToMap(candidateSet);

                    candidateSet = new HashSet<>();
                    for(String answer: candidateMap.keySet())
                        candidateSet.add(candidateMap.get(answer));
                    fileOperator.saveListForDeepLearning(candidateSet);

                    statisticValue.setTotalCandidateCount(candidateSet.size());
                    start_time = System.nanoTime();
                    List<Answer> orderedCandidateList = null;
                    if(PythonSocket.askForPrediction()){ // hazırsa ve başarılıysa cevapları dosyadan oku
                        orderedCandidateList = fileOperator.parseOutput(candidateMap);
                    } else {
                        System.out.println("hata oluştu");
                        session.setAttribute("cevap", 5); //Soket bağlantı hatası

                    }
                    end_time = System.nanoTime();
                    pythonDiff = (end_time - start_time)/1e6;
                    System.out.println("python cevap verilmesi" + pythonDiff);
                    statisticValue.setPythonAnswerTime(pythonDiff);

                    if(orderedCandidateList.size() > SearchingParameter.getSearchingParameter().getAnswerCount())
                        orderedCandidateList = orderedCandidateList.subList(0, SearchingParameter.getSearchingParameter().getAnswerCount());

                    session.setAttribute("cevap", 1);
                    session.setAttribute("answerList", orderedCandidateList);
                }
            } catch (Exception ex) {
                ex.getMessage();
                ex.printStackTrace();
                session.setAttribute("cevap", 3);
            }
        }

        long totalEnd = System.nanoTime();
        double totalDiff = (totalEnd - totalStart)/1e6;
        System.out.println("toplam cevap süresi" + totalDiff);
        statisticValue.setTotalAnswerTime(totalDiff);
        statisticValue.setOtherTime(totalDiff - pythonDiff - candidateDiff);


        session.setAttribute("istatistik", statisticValue);
        response.sendRedirect("developermode.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
