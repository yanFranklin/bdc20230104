package cn.gtmap.realestate.exchange.service.impl.inf.wwsq;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcZsInitFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.exchange.core.dto.wwsq.zscx.ZscxReponseData;
import cn.gtmap.realestate.exchange.service.inf.wwsq.ZscxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ZscxServiceImpl implements ZscxService {

    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    BdcZsInitFeignService bdcZsInitFeignService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Override
    public List<ZscxReponseData> listBdcZsAndYl(String slbh) throws Exception {
        BdcXmQO xmQO = new BdcXmQO();
        xmQO.setSlbh(slbh);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(xmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            return Lists.emptyList();
        }

        String gzlslid = bdcXmDOS.get(0).getGzlslid();
        if (StringUtils.isBlank(gzlslid)) {
            return Lists.emptyList();
        }
        List<ZscxReponseData> reponseDataList = Lists.newArrayList();
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(gzlslid);
        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
        // 未生成证书，预览证书
        if (CollectionUtils.isEmpty(bdcZsDOList)) {
            bdcZsDOList = bdcZsInitFeignService.initBdcqzs(gzlslid, true);
        }
        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
            for (BdcZsDO bdcZsDO : bdcZsDOList) {
                ZscxReponseData reponseData = setZscxReponseData(bdcZsDO);
                reponseDataList.add(reponseData);
            }
        }
        return reponseDataList;
    }

    private ZscxReponseData setZscxReponseData(BdcZsDO bdcZsDO) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        ZscxReponseData zscx = new ZscxReponseData();
//        zscx.setBh(bdcZsDO.getZzbs());
        zscx.setZmqlhsx(bdcZsDO.getZmqlsx());
        zscx.setQlr(bdcZsDO.getQlr());
        zscx.setQlqtzk(bdcZsDO.getQlqtzk());
        zscx.setYwr(bdcZsDO.getYwr());
        zscx.setZl(bdcZsDO.getZl());
        zscx.setBdcdyh(bdcZsDO.getBdcdyh());
        zscx.setFj(bdcZsDO.getFj());
        zscx.setZslx(bdcZsDO.getZslx());
        zscx.setGyqkmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsDO.getGyfs(), zdMap.get("gyfs")));
        zscx.setQllxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsDO.getQllx(), zdMap.get("qllx")));
        zscx.setQlxzmc(bdcZsDO.getQlxz());
        zscx.setYtmc(bdcZsDO.getYt());
        zscx.setMj(bdcZsDO.getMj());
        zscx.setSyqx(bdcZsDO.getSyqx());
        return zscx;
    }

}
