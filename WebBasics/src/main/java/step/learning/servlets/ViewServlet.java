package step.learning.servlets;

import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Singleton
// @WebServlet( "/servlet" )
public class ViewServlet extends HttpServlet {
    @Override
    protected void doGet(
            HttpServletRequest  request,
            HttpServletResponse response ) throws ServletException, IOException {

        response.setCharacterEncoding( "UTF-8" ) ;
        // response.getWriter().print( "<h1>Сервлет работает</h1>" ) ;
        HttpSession session = request.getSession() ;
        String userInput = (String) session.getAttribute( "userInput" ) ;
        request.setAttribute( "userInput", userInput ) ;
        if( userInput != null ) {
            session.removeAttribute( "userInput" ) ;
        }
        request.getRequestDispatcher( "WEB-INF/servlets.jsp" )
               .forward( request, response ) ;
        /* Д.З. Реализовать прием данных из формы прошлого ДЗ, вывести полученные
        данные на странице. Использовать Сервлеты, перенаправления, сессии.
         */
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // При наличие параметов, передаваемых формой, они становятся доступными
        // через req.getParameter
        // ! до первого чтения из req необходимо указать кодировку
        req.setCharacterEncoding( "UTF-8" ) ;
        String userInput = req.getParameter( "userInput" ) ;  // form-data
        // Тут могла бы быть валидация
        // req.setAttribute( "userInput", userInput ) ; - при редиректе бесполезно
        // req.getRequestDispatcher( "WEB-INF/servlets.jsp" ).forward( req, resp ) ;

        // сессия - хранит данные между запросами
        req.getSession().setAttribute( "userInput", userInput ) ;
        resp.sendRedirect( req.getRequestURI() ) ;
    }
}
/*
HttpServlet по своему принципу напоминает ApiController (ASP)
 */
