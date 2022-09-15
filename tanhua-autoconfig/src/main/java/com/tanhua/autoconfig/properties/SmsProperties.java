package com.tanhua.autoconfig.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sxs
 * @create 2022-09-06 22:10
 */
@Data
@ConfigurationProperties(prefix = "tanhua.sms")
public class SmsProperties {
    private String signName;
    private String templateCode;
    private String accessKeyId;
    private String accessKeySecret;
}
