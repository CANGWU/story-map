package cn.edu.nju.story.map.utils;

import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.jwt.crypto.sign.Signer;

/**
 * JwtGenrator
 *
 * @author xuan
 * @date 2019-01-15
 */
public class JwtGenerator {


    private final static SignatureVerifier SIGNER = new MacSigner("IStory");


    /**
     * 根据内容生成jwt
     * @param content
     * @return
     */
    public static String generateJwtString(String content){

        Jwt jwt = JwtHelper.encode(content, (Signer) SIGNER);
        return jwt.getEncoded();
    }


    /**
     * 返回jwt中保存的内容
     * @param jwt
     * @return
     */
    public static String verifyJwt(String jwt){

        return JwtHelper.decodeAndVerify(jwt, SIGNER).getClaims();
    }


}
