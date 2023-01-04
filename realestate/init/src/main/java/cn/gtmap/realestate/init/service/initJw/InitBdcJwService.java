package cn.gtmap.realestate.init.service.initJw;

import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;

import java.util.List;

/**
 * 初始化入库数据之后的服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/17.
 * @description
 */
public abstract class InitBdcJwService {

    /**
     * 初始化入库数据之后的服务
     * @param initResultDTO  初始化后的数据
     * @throws Exception
     */
    public abstract void initJw(InitResultDTO initResultDTO,List<InitServiceQO> listQO) throws Exception;

    /**
     * 获取服务版本
     *
     * @return
     */
    public List<String> getVersion() {
        return null;
    }
}
