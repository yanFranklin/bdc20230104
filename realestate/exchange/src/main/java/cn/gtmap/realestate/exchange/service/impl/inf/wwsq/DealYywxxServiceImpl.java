package cn.gtmap.realestate.exchange.service.impl.inf.wwsq;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.ydjxt.DvHlwQlrResponseDto;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.ydjxt.DvHlwResponseDto;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcInitFeignService;
import cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.request.GrdacxRequestBody;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.zy.InitRequestData;
import cn.gtmap.realestate.exchange.service.inf.qzview.QzViewDataService;
import cn.gtmap.realestate.exchange.service.inf.wwsq.DealYywxxService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/6/8.
 * @description
 */
@Service
public class DealYywxxServiceImpl implements DealYywxxService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DealYywxxServiceImpl.class);
    @Autowired
    BdcInitFeignService bdcInitFeignService;
    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;
    @Autowired
    QzViewDataService qzViewDataService;

    /**
     * 处理原业务信息  涉及很多关联问题在bean中对照不好处理，写在代码里
     *
     * @param yywxxList
     * @return
     * @throws Exception
     */
    @Override
    public String insertYywxx(List<DvHlwResponseDto> yywxxList) throws Exception {
        if (CollectionUtils.isNotEmpty(yywxxList)) {
            DvHlwResponseDto dvHlwResponseDto = JSONObject.parseObject(JSONObject.toJSONString(yywxxList.get(0)), DvHlwResponseDto.class);
            // 对照项目数据
            BdcXmDO bdcXmDO = new BdcXmDO();
            dozerBeanMapper.map(dvHlwResponseDto, bdcXmDO, "bdcXm");
            // 初始化权利数据
            BdcFdcqDO bdcFdcqDO = new BdcFdcqDO();
            dozerBeanMapper.map(dvHlwResponseDto, bdcFdcqDO, "bdcQl");
            bdcFdcqDO.setSlbh(bdcXmDO.getSlbh());
            bdcFdcqDO.setXmid(bdcXmDO.getXmid());
            bdcFdcqDO.setSlbh(bdcXmDO.getSlbh());
            bdcFdcqDO.setQllx(bdcXmDO.getQllx());
            // 初始化权利人数据
            List<BdcQlrDO> qlrDOList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(dvHlwResponseDto.getQlrs())) {
                for (DvHlwQlrResponseDto qlr : dvHlwResponseDto.getQlrs()) {
                    BdcQlrDO bdcQlrDO = new BdcQlrDO();
                    dozerBeanMapper.map(qlr, bdcQlrDO, "bdcQlr");
                    bdcQlrDO.setXmid(bdcXmDO.getXmid());
                    qlrDOList.add(bdcQlrDO);
                }
            }
            BdcYwxxDTO bdcYwxxDTO = new BdcYwxxDTO();
            bdcYwxxDTO.setBdcXm(bdcXmDO);
            bdcYwxxDTO.setBdcQl(bdcFdcqDO);
            bdcYwxxDTO.setBdcQlrList(qlrDOList);
            String yxmid = bdcInitFeignService.insertYwxx(bdcYwxxDTO);
            if (StringUtils.isBlank(yxmid)) {
                throw new AppException("保存原业务信息失败");
            }
            return yxmid;
        }
        return null;
    }

    @Override
    public Map insertYywxxReturnMap(List<DvHlwResponseDto> yywxxList) throws Exception {
        Map map = new HashMap();
        map.put("yxmid", insertYywxx(yywxxList));
        return map;
    }

    public String insertYywxx_changzhou(InitRequestData initRequestData) {
        LOGGER.info(initRequestData.toString());
        if (StringUtils.isNotBlank(initRequestData.getRoomid())) {
            GrdacxRequestBody grdacxRequestBody = new GrdacxRequestBody();
            grdacxRequestBody.setRoomid(initRequestData.getRoomid());
            List<DvHlwResponseDto> yywxxList = qzViewDataService.dvHlwListByParamMap(grdacxRequestBody);
            try {
                return insertYywxx(yywxxList);
            } catch (Exception e) {
                throw new AppException("保存原业务信息失败");
            }
        }
        return null;
    }
}
