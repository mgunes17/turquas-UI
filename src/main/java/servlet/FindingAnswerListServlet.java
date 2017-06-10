package servlet;

import common.SearchingParameter;
import data_provider.Provider;
import model.Answer;
import model.Question;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mustafa on 10.06.2017.
 */
@WebServlet(name = "FindingAnswerListServlet" , urlPatterns = {"/findinganswers"})
public class FindingAnswerListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String questionText = request.getParameter("question").toLowerCase();
        QuestionOperator questionOperator = new QuestionOperator();

        if(!questionOperator.validateQuestion(questionText)) {
            session.setAttribute("cevap", 2);
        } else {
            try {
                Question question = questionOperator.createQuestion(questionText);
                Provider provider = SearchingParameter.getProvider();
                Set<Answer> candidateList = provider.findCandidateList(question);

                AnswerOperator answerOperator = new AnswerOperator();
                answerOperator.preparaForDeepLearningText(candidateList);
                FileOperator fileOperator = new FileOperator();
                fileOperator.saveListForDeepLearning(candidateList);

                Map<String, Answer> answerMap = new HashMap<String, Answer>();
                for(Answer answer: candidateList)
                    answerMap.put(answer.getDeepLearingForm(), answer);

                ///soket işlemleri

                ////hazır olduktan sonra dosya okunacak
                List<Answer> orderedCandidateList = fileOperator.parseOutput(answerMap);

                session.setAttribute("cevap", 1);
                session.setAttribute("answerList", orderedCandidateList); //burası değişecek !!!!!
            } catch (Exception ex) {
                ex.getMessage();
                ex.printStackTrace();
                session.setAttribute("cevap", 3);
            }
        }

        response.sendRedirect("developermode.jsp");
        /*
        soruyu al
        kelimelere ayır
        kuralları çıkar (isim cümlesi vs)

        parametreler ???

        google dan x tane cümle elde et | dbden x tane cümle elde et
        x cümleyi dosyaya yaz
            hepsi lower case
            stop wordleri temizle

            pythonsal işler soket vs

        dosyadan | soldaki cümle sağdaki benzerlik değeri
            sınıf yap bunun için 2 alan

         listeyi yazdır
         */
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
