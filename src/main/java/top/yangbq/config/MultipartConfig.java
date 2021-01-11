package top.yangbq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.io.File;

@Configuration
public class MultipartConfig {

    @Value ("${locationTemp}")
    private String locationTemp;

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        File tmpFile = new File(locationTemp);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        factory.setLocation(locationTemp);
        return factory.createMultipartConfig();
    }

}
