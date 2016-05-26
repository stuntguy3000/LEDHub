package me.stuntguy3000.java.ledhub.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.object.LEDService;

/**
 * @author stuntguy3000
 */
@WebServlet(name = "ServiceServlet", urlPatterns = "/service")
public class ServiceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("serviceName");

        if (action != null) {
            LEDService ledService = LEDHub.getInstance().getServiceHandler().getService(action);

            if (ledService != null) {
                resp.getWriter().append("Activated");
                LEDHub.getInstance().getServiceHandler().addToServiceQueue(ledService);
                return;
            }
        }

        resp.sendError(404);
    }
}
