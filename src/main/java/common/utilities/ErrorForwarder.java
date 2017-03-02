package common.utilities;

import controllers.AuthorizationServlet;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by smoldyrev on 02.03.17.
 */
public class ErrorForwarder {

    private static Logger logger = Logger.getLogger(ErrorForwarder.class);

    public static void forwardToErrorPage(HttpServletRequest req, HttpServletResponse resp, String message) {

        try {
            req.setAttribute("msg", message);
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        } catch (ServletException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
