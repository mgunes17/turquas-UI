package servlet;

import common.PythonSocket;
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
@WebServlet(name = "InitializeServlet", urlPatterns = {"/initialize"})
public class InitializeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("threshold", SearchingParameter.getThreshold());
        session.setAttribute("answercount", SearchingParameter.getAnswerCount());
        session.setAttribute("linkcount", SearchingParameter.getLinkCount());
        response.sendRedirect("developermode.jsp");
        new PythonSocket();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
