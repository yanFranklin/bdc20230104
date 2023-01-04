package cn.gtmap.realestate.certificate.util;

import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/18
 */
public class JJWTUtil {
    private static final Logger logger = LoggerFactory.getLogger(JJWTUtil.class);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param jsonWebToken
     * @param base64Security
     * @return
     * @description 验证 token
     */
    public static Claims parseJWT(String jsonWebToken, String base64Security) {
        Claims ex = null;
        try {
            JwtParser jwtParser = Jwts.parser();
            if(StringUtils.isNotBlank(base64Security)) {
                jwtParser.setSigningKey(base64Security.getBytes());
            }

            ex = jwtParser.parseClaimsJws(jsonWebToken).getBody();
        } catch (Exception e) {
            logger.error("JJWTUtil:parseJWT", e);
        }
        return ex;
    }

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param claimMap
     * @param signature
     * @param TTLMillis
     * @param base64Security
     * @return
     * @description 生成token
     */
    public static String createJWT(Map<String, Object> claimMap, SignatureAlgorithm signature, long TTLMillis, String base64Security) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "njgt");
        Iterator expMillis;
        if(claimMap != null && claimMap.size() > 0) {
            expMillis = claimMap.keySet().iterator();

            while(expMillis.hasNext()) {
                String apiKeySecretBytes = (String)expMillis.next();
                builder.claim(apiKeySecretBytes, claimMap.get(apiKeySecretBytes));
            }
        }

        if(StringUtils.isNotBlank(base64Security)) {
            SignatureAlgorithm expMillis2;
            if(signature != null && signature != SignatureAlgorithm.NONE) {
                expMillis2 = signature;
            } else {
                expMillis2 = SignatureAlgorithm.HS256;
            }

            byte[] apiKeySecretBytes1 = base64Security.getBytes();
            SecretKeySpec exp = new SecretKeySpec(apiKeySecretBytes1, expMillis2.getJcaName());
            builder.signWith(expMillis2, exp);
        }

        if(TTLMillis > 0L) {
            long expMillis1 = nowMillis + TTLMillis;
            Date exp1 = new Date(expMillis1);
            builder.setExpiration(exp1);
        }

        builder.setNotBefore(now);
        return builder.compact();
    }

    private JJWTUtil() {
    }
}
