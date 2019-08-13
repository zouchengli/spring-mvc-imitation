package site.clzblog.imitation.spring.mvc.servlet;

import site.clzblog.imitation.spring.mvc.view.ModelAndView;
import site.clzblog.imitation.spring.mvc.web.RequestMappingHandlerMapping;
import site.clzblog.imitation.spring.mvc.web.controller.Controller;
import site.clzblog.imitation.spring.mvc.web.controller.HttpController;
import site.clzblog.imitation.spring.mvc.web.handler.HandlerExecutionChain;
import site.clzblog.imitation.spring.mvc.web.handler.HandlerMethod;
import site.clzblog.imitation.spring.mvc.web.handler.adapter.AnnotationHandlerAdapter;
import site.clzblog.imitation.spring.mvc.web.handler.adapter.HandlerAdapter;
import site.clzblog.imitation.spring.mvc.web.handler.adapter.HttpRequestHandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class DispatcherServlet extends FrameworkServlet {
    private List<HandlerAdapter> handlerAdapters;

    private RequestMappingHandlerMapping handlerMapping;

    public DispatcherServlet() {
        handlerMapping = new RequestMappingHandlerMapping();
        handlerAdapters = new ArrayList<>();
        handlerAdapters.add(new HttpRequestHandlerAdapter());
        handlerAdapters.add(new AnnotationHandlerAdapter());
    }

    public HandlerAdapter getHandlerAdapter(Controller controller) {
        if (this.handlerAdapters == null) return null;
        for (HandlerAdapter ha: handlerAdapters) {
            if (ha.supports(controller)) return ha;
        }
        return null;
    }

    @Override
    protected void onRefresh() {
        initStrategies();
    }

    private void initStrategies() {
        System.out.println("Init methods ....");
        handlerMapping.initHandlerMappings();
    }

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        doDispatch(req, resp);
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpController controller = new HttpController();
        HandlerAdapter handlerAdapter = getHandlerAdapter(controller);
        handlerAdapter.handle(controller);
        // Up code test use only

        String uri = req.getRequestURI();
        HandlerExecutionChain handler = getHandler(uri);
        if (handler == null) {
            noHandlerFound(req, resp);
            return;
        }
        render(handler.handler(), req, resp);
    }

    private void render(ModelAndView handler, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.getRequestDispatcher("/WEB-INF/view/" + handler.getViewName() + ".jsp").forward(req, resp);
    }

    private void noHandlerFound(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.getWriter().println("404 not found");
        throw new Exception("Not found 404");
    }

    private HandlerExecutionChain getHandler(String url) {
        HandlerMethod handlerMethod = handlerMapping.getHandlerMethod(url);
        if (handlerMethod == null) return null;
        return new HandlerExecutionChain(handlerMethod);
    }
}
