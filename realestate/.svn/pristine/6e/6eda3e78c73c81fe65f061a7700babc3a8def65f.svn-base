package cn.gtmap.realestate.accept.service.impl.jyxx;

import cn.gtmap.realestate.accept.service.BdcSlJyxxAbstractService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcNtFjParamDTO;
import cn.gtmap.realestate.common.core.dto.accept.FcjyBaxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.etl.RudongFcjyDataFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/12/3
 * @description 如东版本交易信息处理
 */
@Service
public class BdcSlJyxxAbstractServiceImpl_rudong implements BdcSlJyxxAbstractService {

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlJyxxAbstractServiceImpl_rudong.class);

    @Autowired
    RudongFcjyDataFeignService rudongFcjyDataFeignService;

    /**
     * @return 接口标识码，同一接口中的标识码不能出现重复
     * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取实现类的标识码
     */
    @Override
    public Set<String> getInterfaceCode() {
        Set<String> set = new LinkedHashSet<>(1);
        set.add(CommonConstantUtils.SYSTEM_VERSION_RD);
        return set;
    }

    @Override
    public void uploadJyFj(FcjyBaxxDTO fcjyBaxxDTO,String gzlslid){
        if(fcjyBaxxDTO != null &&fcjyBaxxDTO.getBdcSlJyxx() != null &&StringUtils.isNotBlank(gzlslid)) {
            if (CollectionUtils.isNotEmpty(fcjyBaxxDTO.getBdcSlSqr())) {
                LOGGER.info("上传附件时，sqr信息为：{}", JSONObject.toJSONString(fcjyBaxxDTO.getBdcSlSqr()));
                BdcNtFjParamDTO bdcNtFjParamDTO = new BdcNtFjParamDTO();
                Set<String> qlrzjh = new HashSet<>();
                Set<String> ywrzjh = new HashSet<>();
                for (BdcSlSqrDO bdcSlSqrDO : fcjyBaxxDTO.getBdcSlSqr()) {
                    if (bdcSlSqrDO != null && bdcSlSqrDO.getSqrlb() != null
                            && StringUtils.isNotBlank(bdcSlSqrDO.getZjh())) {
                        if (StringUtils.equals(bdcSlSqrDO.getSqrlb(), CommonConstantUtils.QLRLB_QLR)) {
                            qlrzjh.add(bdcSlSqrDO.getZjh());
                        } else {
                            ywrzjh.add(bdcSlSqrDO.getZjh());
                        }
                    }
                }
                bdcNtFjParamDTO.setYwrzjh(ywrzjh);
                bdcNtFjParamDTO.setQlrzjh(qlrzjh);
                rudongFcjyDataFeignService.uploadFcjyfj(fcjyBaxxDTO.getFcjyxxQO().getFwlx(), gzlslid, fcjyBaxxDTO.getBdcSlJyxx().getHtbh(), bdcNtFjParamDTO);

            } else {
                throw new AppException("缺失sqr信息，无法上传附件");
            }
        }

    }
}
