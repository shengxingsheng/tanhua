package com.tanhua.commons.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author sxs
 * @create 2022-09-07 16:21
 */

public class JwtUtils {

    //TODO TOKEN的有效期24小时（ms）
    public static final int TOkEN_TIME_OUT = 1000 * 60 * 60 * 24;
    // 加密KEY
    private static final String TOKEN_SECRET = "tanhua";


    public static String getToken(Map params) {
        String token = Jwts.builder()
                .setClaims(params)
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                .setExpiration(new Date(System.currentTimeMillis() + TOkEN_TIME_OUT))
                .compact();

        return token;
    }

    /**
     * 根据token获取 claims
     * @return
     */
    public static Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(TOKEN_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 是否有效 true-有效，false-失效
     */
    public static boolean verifyToken(String token){

        if(StringUtils.isEmpty(token)) {
            return false;
        }
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(TOKEN_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e) {
            return false;
        }
        return true;
    }
}
