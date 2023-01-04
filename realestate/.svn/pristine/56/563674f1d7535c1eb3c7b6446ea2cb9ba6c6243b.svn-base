package cn.gtmap.realestate.init.service.qlxx.qlfl;

import cn.gtmap.realestate.common.core.domain.BdcFdcq3GyxxDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.InitBdcQlDataAbstractService;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * 初始化建筑物区分所有权共有部分信息
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/10/31.
 * @description
 */
public abstract class InitBdcFdcq3AbstractService extends InitBdcQlDataAbstractService {

    @Override
    public InitServiceDTO init(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        //参数空值返回
        if (initServiceQO == null) {
            return initServiceDTO;
        }
        initServiceDTO = initQlxx(initServiceQO,initServiceDTO);
        //取出生成的权利
        BdcQl initQlxx=initServiceDTO.getBdcQl();
        if (initQlxx != null && !initServiceQO.isSfdzbflpbsj()) {
            List<BdcFdcq3GyxxDO> bdcFdcq3GyxxList = initFdcq3Gyxx(initServiceQO, initQlxx.getQlid());
            // 为不动产权赋值
            if (CollectionUtils.isNotEmpty(bdcFdcq3GyxxList)) {
                initServiceDTO.setBdcFdcq3GyxxList(bdcFdcq3GyxxList);
            }
        }
        return initServiceDTO;
    }

    /**
     * 处理房建筑物区分所有权业主共有部分登记信息_共有部分
     *
     * @param initServiceQO
     * @param qlid
     * @return
     */
    public abstract List<BdcFdcq3GyxxDO> initFdcq3Gyxx(InitServiceQO initServiceQO, String qlid) throws InstantiationException, IllegalAccessException;

}
