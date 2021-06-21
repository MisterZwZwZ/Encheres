package fr.eni.servlets;

import org.eclipse.jdt.internal.compiler.env.ISourceType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/accueil")
public class Accueil extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("coucou");
        req.getRequestDispatcher("WEB-INF/accueil.jsp").forward(req, resp);
    }
}
