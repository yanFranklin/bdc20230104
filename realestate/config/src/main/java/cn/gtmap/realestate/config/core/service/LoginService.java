package cn.gtmap.realestate.config.core.service;

import cn.gtmap.realestate.common.core.domain.job.BdcJobUserDO;
import cn.gtmap.realestate.common.core.dto.ReturnT;
import cn.gtmap.realestate.config.core.mapper.BdcJobUserMapper;
import cn.gtmap.realestate.config.job.util.CookieUtil;
import cn.gtmap.realestate.config.job.util.I18nUtil;
import cn.gtmap.realestate.config.job.util.JacksonUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;

/**
 * @author  2019-05-04 22:13:264
 */
@Configuration
public class LoginService {

    public static final String LOGIN_IDENTITY_KEY = "XXL_JOB_LOGIN_IDENTITY";

    @Resource
    private BdcJobUserMapper bdcJobUserMapper;


    private String makeToken(BdcJobUserDO bdcJobUserDO){
        String tokenJson = JacksonUtil.writeValueAsString(bdcJobUserDO);
        String tokenHex = new BigInteger(tokenJson.getBytes()).toString(16);
        return tokenHex;
    }
    private BdcJobUserDO parseToken(String tokenHex){
        BdcJobUserDO bdcJobUserDO = null;
        if (tokenHex != null) {
            String tokenJson = new String(new BigInteger(tokenHex, 16).toByteArray());      // username_password(md5)
            bdcJobUserDO = JacksonUtil.readValue(tokenJson, BdcJobUserDO.class);
        }
        return bdcJobUserDO;
    }


    public ReturnT<String> login(HttpServletRequest request, HttpServletResponse response, String username, String password, boolean ifRemember){

        // param
        if (username==null || username.trim().length()==0 || password==null || password.trim().length()==0){
            return new ReturnT<String>(500, I18nUtil.getString("login_param_empty"));
        }

        // valid passowrd
        BdcJobUserDO bdcJobUserDO = bdcJobUserMapper.loadByUserName(username);
        if (bdcJobUserDO == null) {
            return new ReturnT<String>(500, I18nUtil.getString("login_param_unvalid"));
        }
        String passwordMd5 = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!passwordMd5.equals(bdcJobUserDO.getPassword())) {
            return new ReturnT<String>(500, I18nUtil.getString("login_param_unvalid"));
        }

        String loginToken = makeToken(bdcJobUserDO);

        // do login
        CookieUtil.set(response, LOGIN_IDENTITY_KEY, loginToken, ifRemember);
        return ReturnT.SUCCESS;
    }

    /**
     * logout
     *
     * @param request
     * @param response
     */
    public ReturnT<String> logout(HttpServletRequest request, HttpServletResponse response){
        CookieUtil.remove(request, response, LOGIN_IDENTITY_KEY);
        return ReturnT.SUCCESS;
    }

    /**
     * logout
     *
     * @param request
     * @return
     */
    public BdcJobUserDO ifLogin(HttpServletRequest request, HttpServletResponse response){
        String cookieToken = CookieUtil.getValue(request, LOGIN_IDENTITY_KEY);
        if (cookieToken != null) {
            BdcJobUserDO cookieUser = null;
            try {
                cookieUser = parseToken(cookieToken);
            } catch (Exception e) {
                logout(request, response);
            }
            if (cookieUser != null) {
                BdcJobUserDO dbUser = bdcJobUserMapper.loadByUserName(cookieUser.getUsername());
                if (dbUser != null) {
                    if (cookieUser.getPassword().equals(dbUser.getPassword())) {
                        return dbUser;
                    }
                }
            }
        }
        return null;
    }


}
