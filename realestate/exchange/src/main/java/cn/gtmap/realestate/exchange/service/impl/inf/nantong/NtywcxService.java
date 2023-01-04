package cn.gtmap.realestate.exchange.service.impl.inf.nantong;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJyxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.nantong.bdcfyxx.response.BdcfyxxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.nantong.bdcfyxx.response.BdcfyxxResponseQlxx;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-04-29
 * @description 南通 自助交换机一体化查询接口
 */
@Service(value = "ntywcxService")
public class NtywcxService {

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    BdcSlJyxxFeignService bdcSlJyxxFeignService;

    /**
     * 根据合同编号查询ywid
     *
     * @param htbh
     * @return
     */
    public JSONObject queryGzlslidByHtbh(String htbh) {
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isBlank(htbh)) {
            throw new MissingArgumentException("合同编号参数不能为空！");
        }
        List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxFeignService.queryBdcSlJyxxByHtbh(htbh);
        if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
            BdcSlXmDO bdcSlXmDO = bdcSlXmFeignService.queryBdcSlXmByXmid(bdcSlJyxxDOList.get(0).getXmid());
            if (bdcSlXmDO != null) {
                BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByJbxxid(bdcSlXmDO.getJbxxid());
                if (bdcSlJbxxDO != null && StringUtils.isNotBlank(bdcSlJbxxDO.getGzlslid())) {
                    jsonObject.put("ywid", bdcSlJbxxDO.getGzlslid());
                }
            }
        }
        if (StringUtils.isBlank(MapUtils.getString(jsonObject, "ywid"))) {
            jsonObject.put("ywid", "");
        }
        return jsonObject;
    }


}
