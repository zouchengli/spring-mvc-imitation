package site.clzblog.imitation.spring.mvc.web.handler.adapter;

public interface HandlerAdapter {
    boolean supports(Object handler);

    void handle(Object handler);
}
