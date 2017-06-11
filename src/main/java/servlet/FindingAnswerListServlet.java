package servlet;

import common.PythonSocket;
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
import java.util.*;

/**
 * Created by mustafa on 10.06.2017.
 */
@WebServlet(name = "FindingAnswerListServlet" , urlPatterns = {"/findinganswers"})
public class FindingAnswerListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String questionText = request.getParameter("question").toLowerCase();
        QuestionOperator questionOperator = new QuestionOperator();
        FileOperator fileOperator = new FileOperator();

        if(!questionOperator.validateQuestion(questionText)) {
            session.setAttribute("cevap", 2);
        } else {
            try {
                fileOperator.writeUserQuestionToFile(questionText);
                Question question = questionOperator.createQuestion(questionText);
                Provider provider = SearchingParameter.getProvider();
                Set<Answer> candidateSet = provider.findCandidateList(question);

                if(candidateSet.size() == 0) {
                    session.setAttribute("cevap", 4); //Aday cümle bulunamadı
                } else {
                    AnswerOperator answerOperator = new AnswerOperator();
                    answerOperator.preparaForDeepLearningText(candidateSet);
                    Map<String, Answer> candidateMap = answerOperator.convertSetToMap(candidateSet);

                    candidateSet = new HashSet<Answer>();
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
