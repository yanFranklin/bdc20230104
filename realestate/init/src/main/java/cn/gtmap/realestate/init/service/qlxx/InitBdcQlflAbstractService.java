package cn.gtmap.realestate.init.service.qlxx;

import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.InitService;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * 初始化不动产权利信息
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/10/31.
 * @description
 */
public abstract class InitBdcQlflAbstractService extends InitBdcQlBaseAbstractService {

    @Override
    public String getCode() {
        return "qllx";
    }

    /**
     * 初始化权利信息接口
     * @param initServiceQO  初始化所需数据结构
     * @param initServiceDTO
     * @return 返回所有权利相关信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO initQlxx(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        List<InitService> list = initBeanFactory.getTrafficMode(initServiceQO.getBdcCshFwkgSl(), getQlzl());
        if (CollectionUtils.isNotEmpty(list)) {
            for (InitService service : list) {
                service.init(initServiceQO, initServiceDTO);
            }
        }
        return initServiceDTO;
    }

    /**
     * 获取当前权利的加载数据服务类
     *
     * @return Class 返回加载类
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    public abstract Class getQlzl();

}
