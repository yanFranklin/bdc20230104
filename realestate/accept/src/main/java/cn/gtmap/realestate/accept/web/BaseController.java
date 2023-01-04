package cn.gtmap.realestate.accept.web;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/1
 * @description 基础controller
 */
public class BaseController {
    @Autowired
    protected MessageProvider messageProvider;
    @Autowired
    protected UserManagerUtils userManagerUtils;

  /**
   * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
   * @param errorCode 错误代码
   * @description 抛出自定义异常
   */
    public void errorException(Integer errorCode) {
        throw new AppException(errorCode, messageProvider.getMessage("error." + errorCode));
    }

    /**
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @return UserDto
     * @description 获取当前登录人信息
     */
    public UserDto getCurrentUser(){
        return userManagerUtils.getCurrentUser();
    }

}
