package cn.gtmap.realestate.exchange.service.impl.inf.xuancheng;

import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.inquiry.xuancheng.BdcZzdzZmcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZzdzZmxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZzdzZmxxResponseDTO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZzdzFeignService;
import cn.gtmap.realestate.exchange.core.dto.xuancehng.zzdz.BdcZmxxDTO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/12/20
 * @description 自助打证
 */
@Service(value = "zzdzServiceImpl")
public class ZzdzServiceImpl {

    @Autowired
    BdcZzdzFeignService bdcZzdzFeignService;

    @Autowired
    private DozerBeanMapper exchangeDozerMapper;

    /**
     * @param bdcZzdzZmcxRequestDTO 查询接口
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 登记证明信息查询
     */
    public JSONObject djzmxx(BdcZzdzZmcxRequestDTO bdcZzdzZmcxRequestDTO) {
        JSONObject jsonObject =new JSONObject();
        BdcZzdzZmxxResponseDTO bdcZzdzZmxxResponseDTO =bdcZzdzFeignService.mokeQueryDjzmxx(bdcZzdzZmcxRequestDTO);
        if(bdcZzdzZmxxResponseDTO != null){
            JSONArray jsonArray =new JSONArray();
            if(CollectionUtils.isNotEmpty(bdcZzdzZmxxResponseDTO.getBdcZzdzZmxxDTOList())){
                for(BdcZzdzZmxxDTO bdcZzdzZmxxDTO: bdcZzdzZmxxResponseDTO.getBdcZzdzZmxxDTOList()){
                    BdcZmxxDTO bdcZmxxDTO =new BdcZmxxDTO();
                    exchangeDozerMapper.map(bdcZzdzZmxxDTO,bdcZmxxDTO,"zzdzj_djzmxx");
                    jsonArray.add(bdcZmxxDTO);

                }

            }
            jsonObject.put("data",jsonArray);
            jsonObject.put("code",bdcZzdzZmxxResponseDTO.getCode());
            jsonObject.put("message",bdcZzdzZmxxResponseDTO.getMessage());

        }
        return jsonObject;
    }
}
