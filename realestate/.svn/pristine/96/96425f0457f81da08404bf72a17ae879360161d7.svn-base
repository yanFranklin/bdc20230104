package cn.gtmap.realestate.exchange.service.impl.inf.yancheng;

import cn.gtmap.realestate.common.core.dto.building.YcHsZtResDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.building.FwYcHsFeignService;
import cn.gtmap.realestate.exchange.core.dto.yancheng.court.SdJsBaseResponseDTO;
import cn.gtmap.realestate.exchange.service.inf.yancheng.BdcFwxxService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2020/12/10
 * @description 给互联网+银行提供查询房屋户室信息
 */
@Service("bdcFwhsSetvicesImpl")
public class BdcFwhsSetvicesImpl implements BdcFwxxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcFwhsSetvicesImpl.class);

    @Autowired
    private FwYcHsFeignService fwYcHsFeignService;


    /**
     * 根据index查询户室信息
     *
     * @param param param
     * @return YcHsZtResDTO
     * @Date 2020/12/10
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Map queryHsZt(JSONObject param) {

        Map all = new HashMap();
        SdJsBaseResponseDTO responseDTO = new SdJsBaseResponseDTO();

        String fwIndex = param.getString("fwdcbIndex");
        if (StringUtils.isBlank(fwIndex)) {
            throw new MissingArgumentException("房屋户室查询参数不能为空！");
        }
        List<YcHsZtResDTO> ycHsZtResDTOList = new ArrayList<>();

        try {
            ycHsZtResDTOList = fwYcHsFeignService.listYchsZt(fwIndex,"");
            responseDTO.setSuccess("true");
        } catch (Exception e) {
            responseDTO.setMsg(e.getMessage());
            responseDTO.setSuccess("false");
        }
        all.put("head",responseDTO);
        all.put("data",ycHsZtResDTOList);
//        LOGGER.info("户室内容{}", all);

        LOGGER.info("户室数量{}", ycHsZtResDTOList.size());
        return all;
    }
}
