package site.clzblog.imitation.spring.mvc.web;

import org.apache.commons.lang3.StringUtils;
import site.clzblog.imitation.spring.mvc.annotation.ComponentScan;
import site.clzblog.imitation.spring.mvc.annotation.Controller;
import site.clzblog.imitation.spring.mvc.annotation.RequestMapping;
import site.clzblog.imitation.spring.mvc.config.SpringMvcConfig;
import site.clzblog.imitation.spring.mvc.utils.ReflexUtils;
import site.clzblog.imitation.spring.mvc.web.handler.HandlerMethod;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RequestMappingHandlerMapping {
    private Map<String, HandlerMethod> registry = new ConcurrentHashMap<>();

    public void initHandlerMappings() {
        ComponentScan scan = SpringMvcConfig.class.getDeclaredAnnotation(ComponentScan.class);
        String[] packages = scan.basePackages();
        for (String packageName : packages) {
            if (StringUtils.isEmpty(packageName)) continue;
            Set<Class<?>> classes = ReflexUtils.getClasses(packageName);
            classes.forEach(cls -> {
                Controller controller = cls.getDeclaredAnnotation(Controller.class);
                if (controller != null) {
                    Method[] methods = cls.getDeclaredMethods();
                    for (Method method : methods) {
                        RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
                        if (null == requestMapping) continue;
                        try {
                            registry.put(requestMapping.value(), new HandlerMethod(cls.newInstance(), method));
                        } catch (InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public HandlerMethod getHandlerMethod(String key) {
        return registry.get(key);
    }
}
