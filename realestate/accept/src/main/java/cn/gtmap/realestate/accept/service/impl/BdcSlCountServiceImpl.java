package cn.gtmap.realestate.accept.service.impl;


import cn.gtmap.realestate.accept.service.BdcSlCountService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcMjDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.NumberUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: realestate
 * @description: 受理计算服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-06-19 14:00
 **/
@Repository
public class BdcSlCountServiceImpl extends BaseController implements BdcSlCountService {
    @Autowired
    BdcXmFeignService bdcXmFeignService;

    /**
     * @param gzlslid
     * @program: realestate
     * @description: 受理计算宗地宗海面积，定着物面积服务
     * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @create: 2019-06-19 13:57
     */
    @Override
    public BdcMjDTO countMj(String gzlslid) {
        BdcMjDTO bdcMjDTO = new BdcMjDTO();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            //根据不动产单元号去重，如果去重后数量大于1则为批量流程
            bdcXmDOList = bdcXmDOList.stream().filter(bdcXmDO -> StringUtils.isNotBlank(bdcXmDO.getBdcdyh())).collect(Collectors.toList());
            Set<BdcXmDO> bdcxm = new TreeSet<>(Comparator.comparing(BdcXmDO::getBdcdyh));
            bdcxm.addAll(bdcXmDOList);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)) {
                BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                if (bdcxm.size() > 1) {
                    //批量面积要求和
                    //批量不动产单元判断所在宗地是否为同一宗地，同一宗地宗地面积取一个宗地即可，否则宗地相加
                    List<BdcXmDO> bdcXmList = new ArrayList<>();
                    for (BdcXmDO bdcXm : bdcxm) {
                        if (bdcXm.getBdcdyh().length() > 19) {
                            String zdBdcdyh = bdcXm.getBdcdyh().substring(0, 19) + "W00000000";
                            bdcXm.setBdcdyh(zdBdcdyh);
                            bdcXmList.add(bdcXm);
                        }
                    }
                    //根据宗地的不动产单元号去重
                    Set<BdcXmDO> newBdcXm = new TreeSet<>(Comparator.comparing(BdcXmDO::getBdcdyh));
                    newBdcXm.addAll(bdcXmList);
                    Double zdzhmj = newBdcXm.stream().filter(xm -> xm.getZdzhmj() != null).mapToDouble(BdcXmDO::getZdzhmj).sum();
                    Double dzwmj = bdcxm.stream().filter(xm -> xm.getDzwmj() != null).mapToDouble(BdcXmDO::getDzwmj).sum();
                    bdcMjDTO.setDzwmj(NumberUtil.formatDigit(dzwmj, 2));
                    bdcMjDTO.setZdzhmj(NumberUtil.formatDigit(zdzhmj, 2));
                } else {
                    bdcMjDTO.setDzwmj(bdcXmDO.getDzwmj());
                    bdcMjDTO.setZdzhmj(bdcXmDO.getZdzhmj());
                }
            }
        }
        return bdcMjDTO;
    }
}
