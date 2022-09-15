package com.tanhua.autoconfig.template;

import com.baidu.aip.face.AipFace;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

/**
 * @author sxs
 * @create 2022-09-08 20:33
 */
@Slf4j
public class AipFaceTemplate {

    @Autowired
    AipFace aipFace;
    /**
     * 判断图片是否含有人脸
     * true有
     * false 没有
     */
    public boolean detect(String imageUrl){
        // 调用接口
        String imageType = "URL";
        // 传入可选参数调用接口
        HashMap<String, Object> options = new HashMap<>();
        options.put("face_field", "age");
        options.put("max_face_num", "2");
        options.put("face_type", "LIVE");
        options.put("liveness_control", "LOW");
        // 人脸检测
        JSONObject res = aipFace.detect(imageUrl, imageType, options);
        Integer errorCode = (Integer) res.get("error_code");
        log.info("errorCode:"+errorCode);
        return errorCode==0;
    }


}
