package site.clzblog.imitation.spring.mvc.web.handler.adapter;

import site.clzblog.imitation.spring.mvc.web.controller.HttpController;

public class HttpRequestHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof HttpController;
    }

    @Override
    public void handle(Object handler) {
        if (supports(handler)) ((HttpController) handler).handler();
    }
}
