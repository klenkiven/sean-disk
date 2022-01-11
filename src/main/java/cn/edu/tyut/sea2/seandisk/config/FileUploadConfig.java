package cn.edu.tyut.sea2.seandisk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 文件上传配置
 * @author KlenKiven
 */
@Configuration
public class FileUploadConfig {

    /**
     * 文件上传配置
     */
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(5L * 1024 * 1024 * 1024);
        commonsMultipartResolver.setMaxInMemorySize(1024*1024);
        return commonsMultipartResolver;
    }

}
