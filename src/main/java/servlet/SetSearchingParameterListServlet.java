package servlet;

import common.SearchingParameter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by mustafa on 10.06.2017.
 */
@WebServlet(name = "SetSearchingParameterListServlet", urlPatterns = {"/setsearchingparameterlist"})
public class SetSearchingParameterListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        try {
            int threshold = Integer.parseInt(request.getParameter("threshold"));
            int answerCount = Integer.parseInt(request.getParameter("answer_count"));
            int linkCount = Integer.parseInt(request.getParameter("link_count"));
            String source = request.getParameter("source");
            String questionWordDeleted = request.getParameter("question_word");

            SearchingParameter searchingParameter = SearchingParameter.getSearchingParameter();
            searchingParameter.setAnswerCount(answerCount);
            searchingParameter.setLinkCount(linkCount);
            searchingParameter.setSource(source);
            searchingParameter.setThreshold(threshold);
            searchingParameter.setQuestionWordDeleted(questionWordDeleted);

            session.setAttribute("set", "1");
            session.setAttribute("threshold", threshold);
            session.setAttribute("answercount", answerCount);
        } catch (Exception ex) {
            ex.getMessage();
            ex.printStackTrace();
            session.setAttribute("set", "2");
        }

        response.sendRedirect("developermode.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
