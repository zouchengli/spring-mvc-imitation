package site.clzblog.imitation.spring.mvc.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrameworkServlet extends HttpServletBean{
    @Override
    protected void initServletBean() {
        onRefresh();
    }

    protected void onRefresh() {
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processRequest(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        doService(req, resp);
    }

    protected void doService(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    }
}
