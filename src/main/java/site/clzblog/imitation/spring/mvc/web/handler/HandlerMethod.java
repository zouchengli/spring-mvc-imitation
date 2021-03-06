package site.clzblog.imitation.spring.mvc.web.handler;

import java.lang.reflect.Method;

public class HandlerMethod {
    private Object bean;
    private Method method;

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public HandlerMethod(Object bean, Method method) {
        this.bean = bean;
        this.method = method;
    }
}
