package site.clzblog.imitation.spring.mvc.web.handler;

import site.clzblog.imitation.spring.mvc.view.ModelAndView;

import java.lang.reflect.Method;

public class HandlerExecutionChain {
    private HandlerMethod method;

    public HandlerExecutionChain(HandlerMethod method) {
        this.method = method;
    }

    public ModelAndView handler() throws Exception {
        Method method = this.method.getMethod();
        Object viewName = method.invoke(this.method.getBean(), null);
        return new ModelAndView(viewName.toString());
    }
}
