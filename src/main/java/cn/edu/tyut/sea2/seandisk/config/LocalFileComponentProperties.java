package cn.edu.tyut.sea2.seandisk.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("sean.local-file")
@Component
@Setter
public class LocalFileComponentProperties {

    /**
     * 本地文件位置
     */
    private String location;

}
