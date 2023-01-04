package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcHysyqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.building.ZhZhjnbdyjlb;
import cn.gtmap.realestate.common.core.dto.building.ZhDjdcbResponseDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcHysyqAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/15
 * @description 从权籍加载数据到海域所有权
 */
@Service
public class InitLpbToBdcHysyqServiceImpl extends InitBdcHysyqAbstractService {
    /**
     * @return 对照开关值
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description
     */
    @Override
    public String getVal() {
        return Constants.QLSJLY_LPB;
    }

    /**
     * 初始化权利信息接口
     *
     * @param initServiceQO 初始化所需数据结构
     * @return 返回权利信息
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description
     */
    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException {
        BdcHysyqDO bdcHysyqDO=initTdFromLpb(initServiceQO,BdcHysyqDO.class);
        //宗海的处理使用总金额
        ZhDjdcbResponseDTO zhDjdcbResponseDTO = (ZhDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO();
        if (zhDjdcbResponseDTO != null && CollectionUtils.isNotEmpty(zhDjdcbResponseDTO.getZhZhjnbdyjlbList())) {
            Double syjze=0.0;
            for(ZhZhjnbdyjlb zhZhjnbdyjlb : zhDjdcbResponseDTO.getZhZhjnbdyjlbList()){
                if(zhZhjnbdyjlb.getSyjse()!=null){
                    syjze+=zhZhjnbdyjlb.getSyjse();
                }
            }
            if(bdcHysyqDO!=null){
                bdcHysyqDO.setSyzje(syjze);
            }
        }
        return bdcHysyqDO;
    }
}
