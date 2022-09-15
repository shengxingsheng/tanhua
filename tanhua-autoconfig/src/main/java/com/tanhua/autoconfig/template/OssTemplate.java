package com.tanhua.autoconfig.template;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.tanhua.autoconfig.properties.OssProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author sxs
 * @create 2022-09-08 16:45
 */
@Slf4j
public class OssTemplate {

    private OssProperties ossProperties;

    public OssTemplate(OssProperties ossProperties){
        this.ossProperties=ossProperties;
    }

    /**
     * @return
     */
    public String upload(String fileName, InputStream inputStream) {
        //拼写图片路径
        String objectName = new SimpleDateFormat("yyyy/MM/dd/").format(new Date()) + UUID.randomUUID() + fileName.substring(fileName.lastIndexOf('.'));
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ossProperties.getEndpoint();
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ossProperties.getAccessKeyId();
        String accessKeySecret = ossProperties.getAccessKeySecret();
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ossProperties.getBucketName();
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String url="";
        try {
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, inputStream);
            url = "https://"+bucketName+"."+endpoint+"/"+objectName;
            log.info("上传成功："+url);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }

            return url;
        }
    }
}
