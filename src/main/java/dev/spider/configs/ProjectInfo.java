package dev.spider.configs;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "info")
public class ProjectInfo implements InitializingBean, ApplicationListener<WebServerInitializedEvent> {

    private Map<String, String> map;

    public static String[][] getTwoArrayObject(Map<String, String> map) {
        String[][] object = null;
        if (map != null && !map.isEmpty()) {
            int size = map.size();
            object = new String[size][2];

            Iterator iterator = map.entrySet().iterator();
            for (int i = 0; i < size; i++) {
                Map.Entry<String, String> entry = (Map.Entry) iterator.next();
                String key = entry.getKey();
                String value = entry.getValue();
                object[i][0] = key;
                object[i][1] = value;
            }
        }
        return object;
    }

    @Override
    public void afterPropertiesSet() {
        String buildTime = map.get("buildTime");
        map.put("buildTime", buildTime + " [UTC]");
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        map.put("port", String.valueOf(event.getWebServer().getPort()));
        map.put("profile", Arrays.toString(event.getApplicationContext().getEnvironment().getActiveProfiles()));
    }
}
