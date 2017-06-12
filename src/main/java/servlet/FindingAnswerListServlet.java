package servlet;

import common.PythonSocket;
import common.SearchingParameter;
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
                Set<Answer> candidateSet = provider.findCandidateList(questionUI);

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

                    //sokete mesaj at ve cevap gelene kdr bekle
                    List<Answer> orderedCandidateList = null;
                    if(PythonSocket.askForPrediction()){ // hazırsa ve başarılıysa cevapları dosyadan oku
                        orderedCandidateList = fileOperator.parseOutput(candidateMap);
                    } else {
                        System.out.println("hata oluştu");
                        session.setAttribute("cevap", 5); //Soket bağlantı hatası

                    }

                    orderedCandidateList = orderedCandidateList.subList(0, SearchingParameter.getSearchingParameter().getAnswerCount());

                    ////hazır olduktan sonra dosya okunacak
                    session.setAttribute("cevap", 1);
                    session.setAttribute("answerList", orderedCandidateList);
                }
            } catch (Exception ex) {
                ex.getMessage();
                ex.printStackTrace();
                session.setAttribute("cevap", 3);
            }
        }

        response.sendRedirect("developermode.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
