package cn.gtmap.realestate.inquiry.core.cloudMapper;


import cn.gtmap.realestate.common.core.dto.inquiry.BdcBjscTjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BjsctjQO;

import java.util.List;

public interface BdcCloudMapper {

    List<BdcBjscTjDTO> countBjsc(BjsctjQO bjsctjQO);
}
