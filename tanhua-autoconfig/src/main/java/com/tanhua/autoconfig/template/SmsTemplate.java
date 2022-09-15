package com.tanhua.autoconfig.template;


import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.tanhua.autoconfig.properties.SmsProperties;
import lombok.extern.slf4j.Slf4j;


/**
 * @author sxs
 * @create 2022-09-06 21:23
 */
@Slf4j
public class SmsTemplate {

    private SmsProperties smsProperties;

    public SmsTemplate(SmsProperties smsProperties) {
        this.smsProperties=smsProperties;
    }
    public void sendSms(String phone, String code) throws Exception {
//        try {
            com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                    // 您的 AccessKey ID
                    .setAccessKeyId(smsProperties.getAccessKeyId())
                    // 您的 AccessKey Secret
                    .setAccessKeySecret(smsProperties.getAccessKeySecret());
            // 访问的域名
            config.endpoint = "dysmsapi.aliyuncs.com";
            com.aliyun.dysmsapi20170525.Client client = new com.aliyun.dysmsapi20170525.Client(config);
            com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                    .setSignName(smsProperties.getSignName())
                    .setTemplateCode(smsProperties.getTemplateCode())
                    .setPhoneNumbers(phone)
                    .setTemplateParam("{\"code\":\""+code+"\"}");
            com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
            // 复制代码运行请自行打印 API 的返回值
            SendSmsResponse response = client.sendSmsWithOptions(sendSmsRequest, runtime);
            log.info(response.getBody().getMessage());
//        } catch (TeaException error) {
//            // 如有需要，请打印 error
//            com.aliyun.teautil.Common.assertAsString(error.message);
//        } catch (Exception _error) {
//            TeaException error = new TeaException(_error.getMessage(), _error);
//            // 如有需要，请打印 error
//            com.aliyun.teautil.Common.assertAsString(error.message);
//        }
    }
}
