package cn.gtmap.realestate.building.web.main;

import cn.gtmap.realestate.building.util.ErrorCodeConstants;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-01
 * @description
 */
public class BaseController {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description Message提供者接口
     */
    @Autowired
    protected MessageProvider messageProvider;

    /**
     * @param errorCode
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 抛出自定义异常
     */
    public void errorException(Integer errorCode) {
        throw new AppException(errorCode, messageProvider.getMessage(ErrorCodeConstants.ERROR + errorCode));
    }
}
