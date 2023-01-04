package cn.gtmap.realestate.building.ui.util;

import cn.gtmap.realestate.common.core.qo.engine.BdcGzZhGzQO;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZhGzFeignService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class BeforeCheckUtil {

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BeforeCheckUtil.class);

    @Autowired
    private static BdcGzZhGzFeignService service;

    @Autowired
    private BdcGzZhGzFeignService bdcGzZhGzFeignService;

    @PostConstruct
    public void init() {
        service = this.bdcGzZhGzFeignService;
    }

    public static List<String> zhgzList = new ArrayList<>();

    /**
    * @Description:  刷新楼盘表需要验证的功能
    * @Param:
    * @return:
    * @Author: <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
    * @Date:
    */
    public static void updateCheckCache() {
        BdcGzZhGzQO bdcGzZhGzQO = new BdcGzZhGzQO();
        bdcGzZhGzQO.setZhbs(Constants.LPB_GZ_ZHBSQZ);
        List<String> zhbsList = new ArrayList<>();
        try {
            zhbsList = service.listBdcGzZhgzBs(bdcGzZhGzQO);
        } catch (Exception e) {
            LOGGER.error("规则子系统服务异常",e);
        }
        if (CollectionUtils.isNotEmpty(zhbsList)) {
            zhgzList.addAll(zhbsList);
        }
    }
    /**
    * @Description:  判断请求是否需要进行验证
    * @Param:
    * @return:
    * @Author: <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
    * @Date:
    */

    public static boolean getCheckResult(String code) {
        if (CollectionUtils.isEmpty(zhgzList)) {
            updateCheckCache();
        }
        return zhgzList.contains(code);
    }

}
