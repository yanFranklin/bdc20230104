package cn.gtmap.realestate.portal.ui.service.impl;

import cn.gtmap.gtc.feign.common.exception.GtFeignException;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.accept.BdcLzQO;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcPpFeignService;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.portal.ui.service.BdcPpLzService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class BdcPpLzServiceImpl implements BdcPpLzService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcPpLzServiceImpl.class);

    @Autowired
    private BdcdyFeignService bdcdyFeignService;
    @Autowired
    private BdcPpFeignService bdcPpFeignService;

    /**
     * 匹配数据
     * 执行落宗操作，如果有数据可以落宗就会执行并返回 true，否则返回 false
     *
     * @param bdcdyh 不动产单元号
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public void matchData(String bdcdyh) {
        if (BdcdyhToolUtils.checkXnbdcdyh(bdcdyh)) {
            try {
                BdcdyResponseDTO bdcdyResponseDTO = bdcdyFeignService.queryFwHsAndFwLjzByYbdcdyh(bdcdyh,"");
                if (bdcdyResponseDTO != null && StringUtils.isNotBlank(bdcdyResponseDTO.getBdcdyh())) {
                    // 落宗
                    BdcLzQO bdcLzQO = new BdcLzQO();
                    bdcLzQO.setLsdyh(bdcdyh);
                    bdcLzQO.setBdcdyh(bdcdyResponseDTO.getBdcdyh());
                    bdcPpFeignService.lz(bdcLzQO);
                }
            } catch (Exception e) {
                // 调用 Feign 服务抛出异常手动处理， taskMap 提示 msg 内容
                if (e.getCause() instanceof GtFeignException) {
                    GtFeignException gte = (GtFeignException) e.getCause();
                    if (gte != null) {
                        String responseBody = gte.getMsgBody();
                        LOGGER.info("匹配数据 Feign 服务抛出异常：{}", responseBody);
                        Map bodyMap = JSONObject.parseObject(responseBody, Map.class);
                        if (MapUtils.getInteger(bodyMap, "code") != null && StringUtils.isNotBlank(MapUtils.getString(bodyMap, "msg"))) {
                            throw new AppException(MapUtils.getString(bodyMap, "msg"));
                        }
                    }
                } else {
                    LOGGER.info("匹配数据抛出异常：{}", e.getMessage());
                    throw new AppException(e.getMessage());
                }
            }
        }
    }
}
