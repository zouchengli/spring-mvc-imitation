package site.clzblog.imitation.spring.mvc.web.handler.adapter;

import site.clzblog.imitation.spring.mvc.web.controller.AnnotationController;

public class AnnotationHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof AnnotationController;
    }

    @Override
    public void handle(Object handler) {
        if (supports(handler)) ((AnnotationController) handler).handler();
    }
}
