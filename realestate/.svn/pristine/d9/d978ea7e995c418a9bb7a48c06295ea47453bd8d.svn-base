package cn.gtmap.realestate.exchange.service.impl.hefei;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmRequestDTO;
import cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFeignService;
import cn.gtmap.realestate.exchange.core.dto.adapter.WwsqCjBdcXmRequestDTOAdapter;
import cn.gtmap.realestate.exchange.core.dto.wwsq.dj.request.GyjsydscdjDto;
import cn.gtmap.realestate.exchange.service.hefei.GyjsydsyqdjService;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description //国土建设用地
 * @Date 2022/5/17 11:10
 **/
@Service
public class GyjsydsyqdjServiceImpl implements GyjsydsyqdjService {


    private static final Logger LOGGER = LoggerFactory.getLogger(GyjsydsyqdjServiceImpl.class);


    @Autowired
    private BdcSlFeignService bdcSlFeignService;

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //国土建设用地首次登记
     * @Date 2022/5/17 11:10
     **/
    @Override
    public CommonResponse<List<String>> gyjsydsyqscdj(GyjsydscdjDto gyjsydscdjDto) {
        WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO = new WwsqCjBdcXmRequestDTOAdapter(gyjsydscdjDto).buildParam();
        try {
            LOGGER.info("受理的参数为：{}", JSONObject.toJSONString(wwsqCjBdcXmRequestDTO));
            WwsqCjBdcXmResponseDTO wwsqCjBdcXmResponseDTO = bdcSlFeignService.wwsqCjBdcXm(wwsqCjBdcXmRequestDTO);
            if (CollectionUtils.isNotEmpty(wwsqCjBdcXmResponseDTO.getBdcXmDOList())) {
                List<String> slbhs = wwsqCjBdcXmResponseDTO.getBdcXmDOList().stream()
                        .map(BdcXmDO::getSlbh)
                        .filter(StrUtil::isNotEmpty)
                        .collect(Collectors.toList());
                return CommonResponse.ok(slbhs);
            } else {
                return CommonResponse.fail("为空");
            }
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        }
    }
}
