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
            int answerCount = Integer.parseInt(request.getParameter("answercount"));
            int linkCount = Integer.parseInt(request.getParameter("linkcount"));
            String source = request.getParameter("source");
            new SearchingParameter().setParameter(threshold, source, answerCount, linkCount);
            session.setAttribute("set", "1");
            session.setAttribute("threshold", SearchingParameter.getThreshold());
            session.setAttribute("answercount", SearchingParameter.getAnswerCount());
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
