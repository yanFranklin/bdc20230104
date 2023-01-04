package cn.gtmap.realestate.exchange.service.impl.inf.nantong;

import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.bo.grdacx.QlxxBO;
import cn.gtmap.realestate.exchange.core.dto.nantong.bdcfyxx.response.BdcfyxxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.nantong.bdcfyxx.response.BdcfyxxResponseQlxx;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-22
 * @description 南通 为房产提供 房源信息查询特殊处理
 */
@Service
public class BdcFyxxService {

    @Autowired
    private BdcdjMapper bdcdjMapper;


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param responseDTO
     * @return cn.gtmap.realestate.exchange.core.dto.nantong.bdcfyxx.response.BdcfyxxResponseDTO
     * @description 查询产权是否在办
     */
    public BdcfyxxResponseDTO setSfzbForFdcq(BdcfyxxResponseDTO responseDTO){
        if(responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getQlxx())){
            for(BdcfyxxResponseQlxx qlxx:responseDTO.getQlxx()){
                String bdcdyh = qlxx.getBdcdyh();
                Map<String,String> paramMap = new HashMap<>();
                paramMap.put("bdcdyh",bdcdyh);
                List<Map> resultList = bdcdjMapper.queryBdcdyhSfzb(paramMap);
                if(CollectionUtils.isNotEmpty(resultList)){
                    qlxx.setSfzb(CommonConstantUtils.SF_S_DM+"");
                }else{
                    qlxx.setSfzb(CommonConstantUtils.SF_F_DM+"");
                }
            }
        }
        return responseDTO;
    }


}
