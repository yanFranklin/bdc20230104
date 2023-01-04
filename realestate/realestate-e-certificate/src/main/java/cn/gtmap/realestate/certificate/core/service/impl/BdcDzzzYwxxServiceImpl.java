package cn.gtmap.realestate.certificate.core.service.impl;


import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzYwxxMapper;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzYwxxDo;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzYwxxService;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @version 1.0, chenyongqiang
 * @description  不动产电子证照业务信息
 */
@Service
public class BdcDzzzYwxxServiceImpl implements BdcDzzzYwxxService {

    @Autowired
    private BdcDzzzYwxxMapper bdcDzzzYwxxMapper;

    @Override
    public BdcDzzzYwxxDo getYwxxFromZzxx(BdcDzzzZzxx bdcDzzzZzxx) {
        BdcDzzzYwxxDo bdcDzzzYwxxDo = null;
        if (null != bdcDzzzZzxx) {
            bdcDzzzYwxxDo = new BdcDzzzYwxxDo();
            bdcDzzzYwxxDo.setYwid(UUIDGenerator.generate());
            bdcDzzzYwxxDo.setZzid(bdcDzzzZzxx.getZzid());
            bdcDzzzYwxxDo.setYwh(bdcDzzzZzxx.getYwh());
            bdcDzzzYwxxDo.setBdcqzh(bdcDzzzZzxx.getBdcqzh());
            bdcDzzzYwxxDo.setBdcdyh(bdcDzzzZzxx.getBdcdyh());
            bdcDzzzYwxxDo.setZl(bdcDzzzZzxx.getZl());
            bdcDzzzYwxxDo.setDwdm(bdcDzzzZzxx.getDwdm());
            bdcDzzzYwxxDo.setSqsjc(bdcDzzzZzxx.getSqsjc());
            bdcDzzzYwxxDo.setSzsxqc(bdcDzzzZzxx.getSzsxqc());
            bdcDzzzYwxxDo.setFzrq(bdcDzzzZzxx.getFzrq());
            bdcDzzzYwxxDo.setGyqk(bdcDzzzZzxx.getGyqk());
            bdcDzzzYwxxDo.setQllx(bdcDzzzZzxx.getQllx());
            bdcDzzzYwxxDo.setQlxz(bdcDzzzZzxx.getQlxz());
            bdcDzzzYwxxDo.setYt(bdcDzzzZzxx.getYt());
            bdcDzzzYwxxDo.setMj(bdcDzzzZzxx.getMj());
            bdcDzzzYwxxDo.setSyqx(bdcDzzzZzxx.getSyqx());
            bdcDzzzYwxxDo.setZhlsh(bdcDzzzZzxx.getZhlsh());
            bdcDzzzYwxxDo.setQlr(bdcDzzzZzxx.getQlr());
            bdcDzzzYwxxDo.setYwr(bdcDzzzZzxx.getYwr());
            bdcDzzzYwxxDo.setNf(bdcDzzzZzxx.getNf());
            bdcDzzzYwxxDo.setZmqlsx(bdcDzzzZzxx.getZmqlsx());
            bdcDzzzYwxxDo.setFj(bdcDzzzZzxx.getFj());
            bdcDzzzYwxxDo.setQlqtzk(bdcDzzzZzxx.getQlqtzk());
            bdcDzzzYwxxDo.setQt(bdcDzzzZzxx.getQt());
            bdcDzzzYwxxDo.setCjsj(DateUtil.now());
            bdcDzzzYwxxDo.setEwmnr(bdcDzzzZzxx.getEwmnr());
            bdcDzzzYwxxDo.setZstype(bdcDzzzZzxx.getZstype());
            bdcDzzzYwxxDo.setZsbh(bdcDzzzZzxx.getZsbh());
            bdcDzzzYwxxDo.setTbzt(bdcDzzzZzxx.getTbzt());
            bdcDzzzYwxxDo.setSmct(bdcDzzzZzxx.getSmct());
        }
        return bdcDzzzYwxxDo;
    }

    @Override
    public BdcDzzzZzxx getZzxxFromYwxx(BdcDzzzYwxxDo bdcDzzzYwxxDo, BdcDzzzZzxx bdcDzzzZzxx) {
        if (null != bdcDzzzYwxxDo) {
            if (null == bdcDzzzZzxx) {
                bdcDzzzZzxx = new BdcDzzzZzxx();
            }
            bdcDzzzZzxx.setYwh(bdcDzzzYwxxDo.getYwh());
            bdcDzzzZzxx.setBdcqzh(bdcDzzzYwxxDo.getBdcqzh());
            bdcDzzzZzxx.setBdcdyh(bdcDzzzYwxxDo.getBdcdyh());
            bdcDzzzZzxx.setZl(bdcDzzzYwxxDo.getZl());
            bdcDzzzZzxx.setDwdm(bdcDzzzYwxxDo.getDwdm());
            bdcDzzzZzxx.setSqsjc(bdcDzzzYwxxDo.getSqsjc());
            bdcDzzzZzxx.setSzsxqc(bdcDzzzYwxxDo.getSzsxqc());
            bdcDzzzZzxx.setFzrq(bdcDzzzYwxxDo.getFzrq());
            bdcDzzzZzxx.setGyqk(bdcDzzzYwxxDo.getGyqk());
            bdcDzzzZzxx.setQllx(bdcDzzzYwxxDo.getQllx());
            bdcDzzzZzxx.setQlxz(bdcDzzzYwxxDo.getQlxz());
            bdcDzzzZzxx.setYt(bdcDzzzYwxxDo.getYt());
            bdcDzzzZzxx.setMj(bdcDzzzYwxxDo.getMj());
            bdcDzzzZzxx.setSyqx(bdcDzzzYwxxDo.getSyqx());
            bdcDzzzZzxx.setZhlsh(bdcDzzzYwxxDo.getZhlsh());
            bdcDzzzZzxx.setQlr(bdcDzzzYwxxDo.getQlr());
            bdcDzzzZzxx.setYwr(bdcDzzzYwxxDo.getYwr());
            bdcDzzzZzxx.setNf(bdcDzzzYwxxDo.getNf());
            bdcDzzzZzxx.setZmqlsx(bdcDzzzYwxxDo.getZmqlsx());
            bdcDzzzZzxx.setFj(bdcDzzzYwxxDo.getFj());
            bdcDzzzZzxx.setQlqtzk(bdcDzzzYwxxDo.getQlqtzk());
            bdcDzzzZzxx.setQt(bdcDzzzYwxxDo.getQt());
            bdcDzzzZzxx.setEwmnr(bdcDzzzYwxxDo.getEwmnr());
            bdcDzzzZzxx.setZsbh(bdcDzzzYwxxDo.getZsbh());
            bdcDzzzZzxx.setTbzt(bdcDzzzYwxxDo.getTbzt());
        }
        return bdcDzzzZzxx;
    }

    @Override
    public int insertYwxx(BdcDzzzZzxx bdcDzzzZzxx) {
        int result = 0;
        BdcDzzzYwxxDo bdcDzzzYwxxDo = getYwxxFromZzxx(bdcDzzzZzxx);
        if (null != bdcDzzzYwxxDo) {
            result = bdcDzzzYwxxMapper.insertYwxx(bdcDzzzYwxxDo);
        }
        return result;
    }

    @Override
    public BdcDzzzYwxxDo queryYwxxByZzid(String zzid) {
        List<BdcDzzzYwxxDo> listYwxx =  bdcDzzzYwxxMapper.queryYwxxByZzid(zzid);
        if (CollectionUtils.isNotEmpty(listYwxx)) {
            return listYwxx.get(0);
        }
        return null;
    }

    @Override
    public int updateYwxxTbzt(String zzid, Integer tbzt) {
        BdcDzzzYwxxDo ywxxDo = new BdcDzzzYwxxDo();
        ywxxDo.setZzid(zzid);
        ywxxDo.setTbzt(tbzt);
        return bdcDzzzYwxxMapper.updateYwxxByZzid(ywxxDo);
    }
}
