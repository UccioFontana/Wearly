package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        AuthenticationFacade authFacade = new AuthenticationFacade(req.getSession());
        Map<String, Integer> returnValues = authFacade.login(email, password);

        req.setAttribute("numOutfit",returnValues.get("numOutfit"));
        req.setAttribute("numCapi",returnValues.get("numCapi"));

        int loginType = returnValues.get("loginType");

        if(loginType == 0){
            RequestDispatcher dispatcher = req.getRequestDispatcher("front-end/jsp/userPage.jsp");
            dispatcher.forward(req, resp);
        }
        else if (loginType == 1){
            RequestDispatcher dispatcher = req.getRequestDispatcher("adminServlet");
            dispatcher.forward(req, resp);
        }
        else{
            resp.getWriter().write("HAI SBAGLIATO AD INSERIRE LE CREDENZIALI");
        }

    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}