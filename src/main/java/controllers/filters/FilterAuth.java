package controllers.filters;

import controllers.AuthorizationServlet;
import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

/**
 * Created by smoldyrev on 25.02.17.
 */
public class FilterAuth implements Filter {

    private static Logger logger = Logger.getLogger(FilterAuth.class);

    private FilterConfig config = null;
    private boolean active = false;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("filte init");
        this.config = filterConfig;
        String act = filterConfig.getInitParameter("active");
        if (act != null)
            active = (act.toUpperCase().equals("TRUE"));
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.debug("dofilter");
        // Get the IP address of client machine.
        String ipAddress = request.getRemoteAddr();

        if (active) {
            logger.debug("IP " + ipAddress + ", Time "
                    + new Date().toString());
        } else {
            logger.debug("wowow");
        }

        // Pass request back down the filter chain
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        this.config = null;
    }
}
