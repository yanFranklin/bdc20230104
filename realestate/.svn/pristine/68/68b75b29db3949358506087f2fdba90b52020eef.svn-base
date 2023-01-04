package cn.gtmap.realestate.exchange.service.impl.inf.nantong;

import cn.gtmap.realestate.exchange.core.component.ExchangeFactory;
import cn.gtmap.realestate.exchange.core.dto.nantong.hlwtj.request.BlhjtjQO;
import cn.gtmap.realestate.exchange.core.dto.nantong.hlwtj.request.BlhjtjRequestData;
import cn.gtmap.realestate.exchange.core.dto.nantong.hlwtj.request.DjlxtjQO;
import cn.gtmap.realestate.exchange.core.dto.nantong.hlwtj.request.DjlxtjRequestData;
import cn.gtmap.realestate.exchange.core.dto.nantong.hlwtj.response.*;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 统计相关服务
 */
@Service
public class NtHlwTjService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NtHlwTjService.class);
    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;
    @Autowired
    private CommonService commonService;
    @Autowired
    BdcdjMapper bdcdjMapper;

    /**
     * 登记类型统计
     *
     * @param djlxtjQO
     * @return
     */
    public List<DjlxtjResponseData> djlxtj(DjlxtjQO djlxtjQO) {
        List<DjlxtjResponseData> djlxtjResponseData = new ArrayList<>();
        if (Objects.nonNull(djlxtjQO.getData()) &&
                StringUtils.isNotBlank(djlxtjQO.getData().getSlly())) {
            DjlxtjRequestData data = djlxtjQO.getData();
            //尝试替换第三方字典
            if (StringUtils.isNotBlank(data.getXzqdm())) {
                String xzqdm = Optional
                        .ofNullable(ExchangeFactory.getDsfZdToBdcDm().conver("DSF_ZD_QXDM", "wwsq", data.getXzqdm()))
                        .map(Object::toString).orElse(data.getXzqdm());
                data.setXzqdm(xzqdm);
            }
            if (StringUtils.isNotBlank(data.getSlly())) {
                String slly = Optional
                        .ofNullable(ExchangeFactory.getDsfZdToBdcDm().conver("DSF_ZD_SPLY", "wwsq", data.getSlly()))
                        .map(Object::toString).orElse(data.getSlly());
                data.setSlly(slly);
            }
            djlxtjResponseData = bdcdjMapper.countDjlxtj(
                    data.getSlksrq(), data.getSljsrq(), data.getSlly(), data.getXzqdm());
        }
        return djlxtjResponseData;
    }


    /**
     * 办理环节统计
     *
     * @param blhjtjQO
     * @return
     */
    public List<BlhjtjResponseData> blhjtj(BlhjtjQO blhjtjQO) {
        List<BlhjtjResponseData> blhjtjResponseDataList = new ArrayList<>();
        if (Objects.nonNull(blhjtjQO.getData()) &&
                StringUtils.isNotBlank(blhjtjQO.getData().getSlly())) {
            BlhjtjRequestData data = blhjtjQO.getData();
            //尝试替换第三方字典
            if (StringUtils.isNotBlank(data.getXzqdm())) {
                String xzqdm = Optional
                        .ofNullable(ExchangeFactory.getDsfZdToBdcDm().conver("DSF_ZD_QXDM", "wwsq", data.getXzqdm()))
                        .map(Object::toString).orElse(data.getXzqdm());
                data.setXzqdm(xzqdm);
            }
            if (StringUtils.isNotBlank(data.getSlly())) {
                String slly = Optional
                        .ofNullable(ExchangeFactory.getDsfZdToBdcDm().conver("DSF_ZD_SPLY", "wwsq", data.getSlly()))
                        .map(Object::toString).orElse(data.getSlly());
                data.setSlly(slly);
            }
            blhjtjResponseDataList = bdcdjMapper.countBlhjtj(
                    data.getSlksrq(), data.getSljsrq(), data.getSlly(), data.getXzqdm());

            if(CollectionUtils.isNotEmpty(blhjtjResponseDataList)){
                for (BlhjtjResponseData blhjtjResponseData : blhjtjResponseDataList) {
                    if (blhjtjResponseData.getBjztdm().equals("1")) {
                        blhjtjResponseData.setBjztmc("审核中");
                    }else {
                        blhjtjResponseData.setBjztmc("已办结");
                    }
                }
            }
        }
        return blhjtjResponseDataList;
    }

}
