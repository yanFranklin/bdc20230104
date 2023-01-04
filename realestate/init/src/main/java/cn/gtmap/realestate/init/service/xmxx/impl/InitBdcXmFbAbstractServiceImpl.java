package cn.gtmap.realestate.init.service.xmxx.impl;

import cn.gtmap.realestate.init.service.xmxx.InitBdcXmFbAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.springframework.stereotype.Service;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/6/10.
 * @description 初始化不动产项目附表信息的服务实现
 */
@Service
public class InitBdcXmFbAbstractServiceImpl  extends InitBdcXmFbAbstractService{
    @Override
    public String getVal() {
        return Constants.DEFAULT;
    }

}
