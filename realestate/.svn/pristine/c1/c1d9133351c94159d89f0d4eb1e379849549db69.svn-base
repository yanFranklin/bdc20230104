package cn.gtmap.realestate.certificate.core.service.impl;



/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, 2019/1/17
 * @description  不动产电子证照证照库业务
 */

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzJzjxxMapper;
import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzMlxxMapper;
import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzYwxxMapper;
import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzZzxxMapper;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.domain.*;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxxYsj;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.core.service.create.BdcDzzzJiangSuCreateService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileCenterService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileConfigService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.certificate.util.SM4Util;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class BdcDzzzZzxxServiceImpl implements BdcDzzzZzxxService {
    private final Logger logger = LoggerFactory.getLogger(BdcDzzzZzxxServiceImpl.class);
    @Resource
    private BdcDzzzZzxxMapper bdcDzzzZzxxMapper;
    @Autowired
    private BdcDzzzZzxxPdfService bdcDzzzZzxxPdfService;
    @Autowired
    private BdcDzzzZzxxYsjService bdcDzzzZzxxYsjService;
    @Autowired
    private BdcDzzzJzjxxMapper bdcDzzzJzjxxMapper;
    @Autowired
    private BdcDzzzJzjxxService bdcDzzzJzjxxService;
    @Autowired
    private BdcDzzzConfigService bdcDzzzConfigService;
    @Autowired
    private BdcDzzzZzxxCzztService bdcDzzzZzxxCzztService;
    @Autowired
    private BdcDzzzYwxxService bdcDzzzYwxxService;
    @Autowired
    private BdcDzzzYwxxMapper bdcDzzzYwxxMapper;
    @Autowired
    BdcDzzzLshService bdcDzzzLshService;
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    private BdcDzzzCheckInfoService bdcDzzzCheckInfoService;
    @Autowired
    private BdcDzzzFileCenterService bdcDzzzFileCenterService;
    @Autowired
    private BdcDzzzMlxxService bdcDzzzMlxxService;
    @Autowired
    private BdcDzzzMlxxMapper bdcDzzzMlxxMapper;
    @Autowired
    private BdcDzzzWaterMarkService bdcDzzzWaterMarkService;
    @Autowired
    private BdcDzzzSignConfigService bdcDzzzSignConfigService;
    @Autowired
    private BdcDzzzFileConfigService bdcDzzzFileConfigService;
    @Autowired
    private BdcDzzzJiangSuCreateService bdcDzzzJiangSuCreateService;

    /**
     * 电子证照生成前是否验证本地电子证照库是否已存在记录。
     * 默认校验
     */
    @Value("${electronic.dzzz.check.sfyzbdzzk:true}")
    private Boolean sfyzbdzzk;

    /**
     * @param zzid 证照库id
     * @return BdcDzzzZzkDO 不动产电子证照证照库
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description 获取电子证照证照库
     */
    @Override
    public BdcDzzzZzxxDO queryBdcDzzzZzxx(String zzid) {
        return bdcDzzzZzxxMapper.queryBdcDzzzZzxxByZzid(zzid);
    }

    @Override
    public BdcDzzzZzxxDO queryBdcDzzzZzxxByZsid(String zsid) {
        return bdcDzzzZzxxMapper.queryBdcDzzzZzxxByZsid(zsid);
    }

    @Override
    public BdcDzzzZzxxDO queryBdcDzzzZzxxByBdcqzh(String bdcqzh) {
        return bdcDzzzZzxxMapper.queryBdcDzzzZzxxByBdcqzh(bdcqzh);
    }

    @Override
    public BdcDzzzZzxxDO queryBdcDzzzZzxxByZzbs(String zzbs) {
        return bdcDzzzZzxxMapper.queryBdcDzzzZzxxByZzbs(zzbs);
    }

    @Override
    @Transactional
    public DzzzResponseModel insertBdcDzzzZzxx(BdcDzzzZzxx bdcDzzzZzxx) throws DataAccessException {
        if (bdcDzzzZzxx != null) {
            BdcDzzzZzxxDO bdcDzzzZzxxDO = getBdcDzzzZzxxDOFromBdcDzzzZzxx(bdcDzzzZzxx);

            //加密证件号
            String zjh_ecb = SM4Util.encryptData_ECB(bdcDzzzZzxxDO.getCzztdm());
            bdcDzzzZzxxDO.setCzztdm(zjh_ecb);
            int resultZzxx = bdcDzzzZzxxMapper.insertBdcDzzzZzxx(bdcDzzzZzxxDO);
            if (0 == resultZzxx) {
                return bdcDzzzService.dzzzResponseFalse("证照信息入库失败！", null);
            }
            int resultCzzt = bdcDzzzZzxxCzztService.insertBdcDzzzZzxxCzztDo(bdcDzzzZzxx);
            if (0 == resultCzzt) {
                return bdcDzzzService.dzzzResponseFalse("持证主体信息入库失败！", null);
            }
            int resultYwxx = bdcDzzzYwxxService.insertYwxx(bdcDzzzZzxx);
            if (0 == resultYwxx) {
                return bdcDzzzService.dzzzResponseFalse("业务信息入库失败！", null);
            }
            BdcDzzzMlxxDO bdcDzzzMlxxDO = getBdcDzzzMlxxDOFromBdcDzzzZzxx(bdcDzzzZzxx);
            bdcDzzzMlxxDO.setGdzt(0);
            bdcDzzzMlxxDO.setCzztdm(zjh_ecb);
            int resultMlxx = bdcDzzzMlxxService.insertBdcDzzzMlxx(bdcDzzzMlxxDO);
            if (0 == resultMlxx) {
                return bdcDzzzService.dzzzResponseFalse("目录信息入库失败！", null);
            }
        }
        return bdcDzzzService.dzzzResponseSuccess(null);
    }

    @Override
    public List<BdcDzzzZzxxDO> listBdcDzzzZzxx(Map map) {
        return bdcDzzzZzxxMapper.getBdcDzzzZzxxDoListByMap(map);
    }

    @Override
    public BdcDzzzZzxxDO getBdcDzzzZzxxDOFromBdcDzzzZzxx(BdcDzzzZzxx bdcDzzzZzxx) {
        BdcDzzzZzxxDO bdcDzzzZzxxDO = new BdcDzzzZzxxDO();
        if (bdcDzzzZzxx != null) {
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzid())) {
                bdcDzzzZzxxDO.setZzid(bdcDzzzZzxx.getZzid());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZsid())) {
                bdcDzzzZzxxDO.setZsid(bdcDzzzZzxx.getZsid());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzmc())) {
                bdcDzzzZzxxDO.setZzmc(bdcDzzzZzxx.getZzmc());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzlxdm())) {
                bdcDzzzZzxxDO.setZzlxdm(bdcDzzzZzxx.getZzlxdm());
            } else {
                bdcDzzzZzxxDO.setZzlxdm(Constants.BDC_ZZLXDM_ZS);
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzbh())) {
                bdcDzzzZzxxDO.setZzbh(bdcDzzzZzxx.getZzbh());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzbs())) {
                bdcDzzzZzxxDO.setZzbs(bdcDzzzZzxx.getZzbs());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzbfjg())) {
                bdcDzzzZzxxDO.setZzbfjg(bdcDzzzZzxx.getZzbfjg());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzbfjgdm())) {
                bdcDzzzZzxxDO.setZzbfjgdm(bdcDzzzZzxx.getZzbfjgdm());
            }
            if (null != bdcDzzzZzxx.getZzbfrq()) {
                bdcDzzzZzxxDO.setZzbfrq(bdcDzzzZzxx.getZzbfrq());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getCzzt())) {
                bdcDzzzZzxxDO.setCzzt(bdcDzzzZzxx.getCzzt());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getCzztdm())) {
                bdcDzzzZzxxDO.setCzztdm(bdcDzzzZzxx.getCzztdm());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getCzztdmlx())) {
                bdcDzzzZzxxDO.setCzztdmlx(bdcDzzzZzxx.getCzztdmlx());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getCzztdmlxdm())) {
                bdcDzzzZzxxDO.setCzztdmlxdm(bdcDzzzZzxx.getCzztdmlxdm());
            }
            if (null != bdcDzzzZzxx.getZzyxqqsrq()) {
                bdcDzzzZzxxDO.setZzyxqqsrq(bdcDzzzZzxx.getZzyxqqsrq());
            }
            if (null != bdcDzzzZzxx.getZzyxqjzrq()) {
                bdcDzzzZzxxDO.setZzyxqjzrq(bdcDzzzZzxx.getZzyxqjzrq());
            }
            if (null != bdcDzzzZzxx.getZzbgsj()) {
                bdcDzzzZzxxDO.setZzbgsj(bdcDzzzZzxx.getZzbgsj());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzbgyy())) {
                bdcDzzzZzxxDO.setZzbgyy(bdcDzzzZzxx.getZzbgyy());
            }
            if (null != bdcDzzzZzxx.getZzqzsj()) {
                bdcDzzzZzxxDO.setZzqzsj(bdcDzzzZzxx.getZzqzsj());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzqzmc())) {
                bdcDzzzZzxxDO.setZzqzmc(bdcDzzzZzxx.getZzqzmc());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzqzr())) {
                bdcDzzzZzxxDO.setZzqzr(bdcDzzzZzxx.getZzqzr());
            }

            if (null != bdcDzzzZzxx.getCjsj()) {
                bdcDzzzZzxxDO.setCjsj(bdcDzzzZzxx.getCjsj());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzwjlj())) {
                bdcDzzzZzxxDO.setZzwjlj(bdcDzzzZzxx.getZzwjlj());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzlsh())) {
                bdcDzzzZzxxDO.setZzlsh(bdcDzzzZzxx.getZzlsh());
            }
            if (null != bdcDzzzZzxx.getZzzt()) {
                bdcDzzzZzxxDO.setZzzt(bdcDzzzZzxx.getZzzt());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getSzzs())) {
                bdcDzzzZzxxDO.setSzzs(bdcDzzzZzxx.getSzzs());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getBdcqzh())) {
                bdcDzzzZzxxDO.setBdcqzh(bdcDzzzZzxx.getBdcqzh());
            }
            bdcDzzzZzxxDO.setZzqzlj(bdcDzzzZzxx.getZzqzlj());
            bdcDzzzZzxxDO.setQzzt(bdcDzzzZzxx.getQzzt());
            bdcDzzzZzxxDO.setDzqzwybs(bdcDzzzZzxx.getDzqzwybs());
        }
        return bdcDzzzZzxxDO;
    }

    @Override
    public BdcDzzzZzxx getBdcDzzzZzxxFromBdcDzzzZzxxDO(BdcDzzzZzxxDO bdcDzzzZzxxDO, BdcDzzzZzxx bdcDzzzZzxx) {
        if (null != bdcDzzzZzxxDO) {
            if (bdcDzzzZzxx == null) {
                bdcDzzzZzxx = new BdcDzzzZzxx();
            }
            bdcDzzzZzxx.setZzid(bdcDzzzZzxxDO.getZzid());
            bdcDzzzZzxx.setZsid(bdcDzzzZzxxDO.getZsid());
            bdcDzzzZzxx.setZzmc(bdcDzzzZzxxDO.getZzmc());
            bdcDzzzZzxx.setZzlxdm(bdcDzzzZzxxDO.getZzlxdm());
            bdcDzzzZzxx.setZstype(getBdcDzzzZzxxZstypeByZzlxdm(bdcDzzzZzxxDO.getZzlxdm()));
            bdcDzzzZzxx.setZzbh(bdcDzzzZzxxDO.getZzbh());
            bdcDzzzZzxx.setZzbs(bdcDzzzZzxxDO.getZzbs());
            bdcDzzzZzxx.setZzbfjg(bdcDzzzZzxxDO.getZzbfjg());
            bdcDzzzZzxx.setZzbfjgdm(bdcDzzzZzxxDO.getZzbfjgdm());
            bdcDzzzZzxx.setZzbfrq(bdcDzzzZzxxDO.getZzbfrq());
            bdcDzzzZzxx.setCzzt(bdcDzzzZzxxDO.getCzzt());
            bdcDzzzZzxx.setCzztdm(bdcDzzzZzxxDO.getCzztdm());
            bdcDzzzZzxx.setCzztdmlx(bdcDzzzZzxxDO.getCzztdmlx());
            bdcDzzzZzxx.setCzztdmlxdm(bdcDzzzZzxxDO.getCzztdmlxdm());
            bdcDzzzZzxx.setZzyxqqsrq(bdcDzzzZzxxDO.getZzyxqqsrq());
            bdcDzzzZzxx.setZzyxqjzrq(bdcDzzzZzxxDO.getZzyxqjzrq());
            bdcDzzzZzxx.setZzbgsj(bdcDzzzZzxxDO.getZzbgsj());
            bdcDzzzZzxx.setZzbgyy(bdcDzzzZzxxDO.getZzbgyy());
            bdcDzzzZzxx.setZzqzsj(bdcDzzzZzxxDO.getZzqzsj());
            bdcDzzzZzxx.setZzqzmc(bdcDzzzZzxxDO.getZzqzmc());
            bdcDzzzZzxx.setZzqzr(bdcDzzzZzxxDO.getZzqzr());
            bdcDzzzZzxx.setCjsj(bdcDzzzZzxxDO.getCjsj());
            bdcDzzzZzxx.setZzwjlj(bdcDzzzZzxxDO.getZzwjlj());
            bdcDzzzZzxx.setZzzt(bdcDzzzZzxxDO.getZzzt());
            bdcDzzzZzxx.setSzzs(bdcDzzzZzxxDO.getSzzs());
            bdcDzzzZzxx.setBdcqzh(bdcDzzzZzxxDO.getBdcqzh());
            bdcDzzzZzxx.setZzlsh(bdcDzzzZzxxDO.getZzlsh());
            bdcDzzzZzxx.setZzqzlj(bdcDzzzZzxxDO.getZzqzlj());
            bdcDzzzZzxx.setQzzt(bdcDzzzZzxxDO.getQzzt());
            bdcDzzzZzxx.setDzqzwybs(bdcDzzzZzxxDO.getDzqzwybs());
            BdcDzzzYwxxDo bdcDzzzYwxxDo = bdcDzzzYwxxService.queryYwxxByZzid(bdcDzzzZzxxDO.getZzid());
            if (null != bdcDzzzYwxxDo) {
                bdcDzzzYwxxService.getZzxxFromYwxx(bdcDzzzYwxxDo, bdcDzzzZzxx);
            }
        }
        return bdcDzzzZzxx;

    }


    @Override
    public BdcDzzzZzxx getBdcDzzzZzxxFromBdcDzzzZzxxYsj(BdcDzzzZzxxYsj bdcDzzzZzxxYsj, BdcDzzzZzxx bdcDzzzZzxx) {
        if (bdcDzzzZzxx == null) {
            bdcDzzzZzxx = new BdcDzzzZzxx();
        }
        if (bdcDzzzZzxxYsj != null) {
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getZZMC())) {
                bdcDzzzZzxx.setZzmc(bdcDzzzZzxxYsj.getZZMC());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getZZLXDM())) {
                bdcDzzzZzxx.setZzlxdm(bdcDzzzZzxxYsj.getZZLXDM());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getZZBH())) {
                bdcDzzzZzxx.setZzbh(bdcDzzzZzxxYsj.getZZBH());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getZZBS())) {
                bdcDzzzZzxx.setZzbs(bdcDzzzZzxxYsj.getZZBS());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getZZBFJG())) {
                bdcDzzzZzxx.setZzbfjg(bdcDzzzZzxxYsj.getZZBFJG());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getZZBFJGDM())) {
                bdcDzzzZzxx.setZzbfjgdm(bdcDzzzZzxxYsj.getZZBFJGDM());
            }
            if (null != bdcDzzzZzxxYsj.getZZBFRQ()) {
                bdcDzzzZzxx.setZzbfrq(bdcDzzzZzxxYsj.getZZBFRQ());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getCZZT())) {
                bdcDzzzZzxx.setCzzt(bdcDzzzZzxxYsj.getCZZT());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getCZZTDM())) {
                bdcDzzzZzxx.setCzztdm(bdcDzzzZzxxYsj.getCZZTDM());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getCZZTDMLX())) {
                bdcDzzzZzxx.setCzztdmlx(bdcDzzzZzxxYsj.getCZZTDMLX());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getCZZTDMLXDM())) {
                bdcDzzzZzxx.setCzztdmlxdm(bdcDzzzZzxxYsj.getCZZTDMLXDM());
            }
            if (null != bdcDzzzZzxxYsj.getZZYXQQSRQ()) {
                bdcDzzzZzxx.setZzyxqqsrq(bdcDzzzZzxxYsj.getZZYXQQSRQ());
            }
            if (null != bdcDzzzZzxxYsj.getZZYXQJZRQ()) {
                bdcDzzzZzxx.setZzyxqjzrq(bdcDzzzZzxxYsj.getZZYXQJZRQ());
            }
            if (null != bdcDzzzZzxxYsj.getZZQZSJ()) {
                bdcDzzzZzxx.setZzqzsj(bdcDzzzZzxxYsj.getZZQZSJ());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getZZQZMC())) {
                bdcDzzzZzxx.setZzqzmc(bdcDzzzZzxxYsj.getZZQZMC());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getZZQZR())) {
                bdcDzzzZzxx.setZzqzr(bdcDzzzZzxxYsj.getZZQZR());
            }
            if (null != bdcDzzzZzxxYsj.getCJSJ()) {
                bdcDzzzZzxx.setCjsj(bdcDzzzZzxxYsj.getCJSJ());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_YWH())) {
                bdcDzzzZzxx.setYwh(bdcDzzzZzxxYsj.getKZ_YWH());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_BDCQZH())) {
                bdcDzzzZzxx.setBdcqzh(bdcDzzzZzxxYsj.getKZ_BDCQZH());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_ZHLSH())) {
                bdcDzzzZzxx.setZhlsh(bdcDzzzZzxxYsj.getKZ_ZHLSH());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_NF())) {
                bdcDzzzZzxx.setNf(bdcDzzzZzxxYsj.getKZ_NF());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_QLQTZK())) {
                bdcDzzzZzxx.setQlqtzk(bdcDzzzZzxxYsj.getKZ_QLQTZK());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_DWDM())) {
                bdcDzzzZzxx.setDwdm(bdcDzzzZzxxYsj.getKZ_DWDM());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_SQSJC())) {
                bdcDzzzZzxx.setSqsjc(bdcDzzzZzxxYsj.getKZ_SQSJC());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_SZSXQC())) {
                bdcDzzzZzxx.setSzsxqc(bdcDzzzZzxxYsj.getKZ_SZSXQC());
            }
            if (null != bdcDzzzZzxxYsj.getKZ_FZRQ()) {
                bdcDzzzZzxx.setFzrq(bdcDzzzZzxxYsj.getKZ_FZRQ());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_EWMNR())) {
                bdcDzzzZzxx.setEwmnr(bdcDzzzZzxxYsj.getKZ_EWMNR());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_FJ())) {
                bdcDzzzZzxx.setFj(bdcDzzzZzxxYsj.getKZ_FJ());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_BDCDYH())) {
                bdcDzzzZzxx.setBdcdyh(bdcDzzzZzxxYsj.getKZ_BDCDYH());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_ZL())) {
                bdcDzzzZzxx.setZl(bdcDzzzZzxxYsj.getKZ_ZL());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_ZMQLSX())) {
                bdcDzzzZzxx.setZmqlsx(bdcDzzzZzxxYsj.getKZ_ZMQLSX());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_GYQK())) {
                bdcDzzzZzxx.setGyqk(bdcDzzzZzxxYsj.getKZ_GYQK());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_QLLX())) {
                bdcDzzzZzxx.setQllx(bdcDzzzZzxxYsj.getKZ_QLLX());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_QLXZ())) {
                bdcDzzzZzxx.setQlxz(bdcDzzzZzxxYsj.getKZ_QLXZ());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_YT())) {
                bdcDzzzZzxx.setYt(bdcDzzzZzxxYsj.getKZ_YT());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_MJ())) {
                bdcDzzzZzxx.setMj(bdcDzzzZzxxYsj.getKZ_MJ());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_SYQX())) {
                bdcDzzzZzxx.setSyqx(bdcDzzzZzxxYsj.getKZ_SYQX());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_QLR())) {
                bdcDzzzZzxx.setQlr(bdcDzzzZzxxYsj.getKZ_QLR());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxxYsj.getKZ_YWR())) {
                bdcDzzzZzxx.setYwr(bdcDzzzZzxxYsj.getKZ_YWR());
            }
        }
        return bdcDzzzZzxx;
    }

    @Override
    public BdcDzzzZzxxYsj getBdcDzzzZzxxYsjFromBdcDzzzZzxx(BdcDzzzZzxxYsj bdcDzzzZzxxYsj, BdcDzzzZzxx bdcDzzzZzxx) {
        if (bdcDzzzZzxxYsj == null) {
            bdcDzzzZzxxYsj = new BdcDzzzZzxxYsj();
        }
        if (bdcDzzzZzxx != null) {
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzmc())) {
                bdcDzzzZzxxYsj.setZZMC(bdcDzzzZzxx.getZzmc());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzlxdm())) {
                bdcDzzzZzxxYsj.setZZLXDM(bdcDzzzZzxx.getZzlxdm());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzbh())) {
                bdcDzzzZzxxYsj.setZZBH(bdcDzzzZzxx.getZzbh());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzbs())) {
                bdcDzzzZzxxYsj.setZZBS(bdcDzzzZzxx.getZzbs());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzbfjg())) {
                bdcDzzzZzxxYsj.setZZBFJG(bdcDzzzZzxx.getZzbfjg());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzbfjgdm())) {
                bdcDzzzZzxxYsj.setZZBFJGDM(bdcDzzzZzxx.getZzbfjgdm());
            }
            if (null != bdcDzzzZzxx.getZzbfrq()) {
                bdcDzzzZzxxYsj.setZZBFRQ(bdcDzzzZzxx.getZzbfrq());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getCzzt())) {
                bdcDzzzZzxxYsj.setCZZT(bdcDzzzZzxx.getCzzt());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getCzztdm())) {
                bdcDzzzZzxxYsj.setCZZTDM(bdcDzzzZzxx.getCzztdm());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getCzztdmlx())) {
                bdcDzzzZzxxYsj.setCZZTDMLX(bdcDzzzZzxx.getCzztdmlx());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getCzztdmlxdm())) {
                bdcDzzzZzxxYsj.setCZZTDMLXDM(bdcDzzzZzxx.getCzztdmlxdm());
            }
            if (null != bdcDzzzZzxx.getZzyxqqsrq()) {
                bdcDzzzZzxxYsj.setZZYXQQSRQ(bdcDzzzZzxx.getZzyxqqsrq());
            }
            if (null != bdcDzzzZzxx.getZzyxqjzrq()) {
                bdcDzzzZzxxYsj.setZZYXQJZRQ(bdcDzzzZzxx.getZzyxqjzrq());
            }
            if (null != bdcDzzzZzxx.getZzqzsj()) {
                bdcDzzzZzxxYsj.setZZQZSJ(bdcDzzzZzxx.getZzqzsj());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzqzmc())) {
                bdcDzzzZzxxYsj.setZZQZMC(bdcDzzzZzxx.getZzqzmc());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzqzr())) {
                bdcDzzzZzxxYsj.setZZQZR(bdcDzzzZzxx.getZzqzr());
            }
            if (null != bdcDzzzZzxx.getCjsj()) {
                bdcDzzzZzxxYsj.setCJSJ(bdcDzzzZzxx.getCjsj());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getYwh())) {
                bdcDzzzZzxxYsj.setKZ_YWH(bdcDzzzZzxx.getYwh());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getBdcqzh())) {
                bdcDzzzZzxxYsj.setKZ_BDCQZH(bdcDzzzZzxx.getBdcqzh());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZhlsh())) {
                bdcDzzzZzxxYsj.setKZ_ZHLSH(bdcDzzzZzxx.getZhlsh());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getNf())) {
                bdcDzzzZzxxYsj.setKZ_NF(bdcDzzzZzxx.getNf());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getQlqtzk())) {
                bdcDzzzZzxxYsj.setKZ_QLQTZK(bdcDzzzZzxx.getQlqtzk());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getDwdm())) {
                bdcDzzzZzxxYsj.setKZ_DWDM(bdcDzzzZzxx.getDwdm());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getSqsjc())) {
                bdcDzzzZzxxYsj.setKZ_SQSJC(bdcDzzzZzxx.getSqsjc());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getSzsxqc())) {
                bdcDzzzZzxxYsj.setKZ_SZSXQC(bdcDzzzZzxx.getSzsxqc());
            }
            if (null != bdcDzzzZzxx.getFzrq()) {
                bdcDzzzZzxxYsj.setKZ_FZRQ(bdcDzzzZzxx.getFzrq());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getEwmnr())) {
                bdcDzzzZzxxYsj.setKZ_EWMNR(bdcDzzzZzxx.getEwmnr());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getFj())) {
                bdcDzzzZzxxYsj.setKZ_FJ(bdcDzzzZzxx.getFj());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getBdcdyh())) {
                bdcDzzzZzxxYsj.setKZ_BDCDYH(bdcDzzzZzxx.getBdcdyh());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZl())) {
                bdcDzzzZzxxYsj.setKZ_ZL(bdcDzzzZzxx.getZl());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZmqlsx())) {
                bdcDzzzZzxxYsj.setKZ_ZMQLSX(bdcDzzzZzxx.getZmqlsx());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getGyqk())) {
                bdcDzzzZzxxYsj.setKZ_GYQK(bdcDzzzZzxx.getGyqk());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getQllx())) {
                bdcDzzzZzxxYsj.setKZ_QLLX(bdcDzzzZzxx.getQllx());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getQlxz())) {
                bdcDzzzZzxxYsj.setKZ_QLXZ(bdcDzzzZzxx.getQlxz());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getYt())) {
                bdcDzzzZzxxYsj.setKZ_YT(bdcDzzzZzxx.getYt());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getMj())) {
                bdcDzzzZzxxYsj.setKZ_MJ(bdcDzzzZzxx.getMj());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getSyqx())) {
                bdcDzzzZzxxYsj.setKZ_SYQX(bdcDzzzZzxx.getSyqx());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getQlr())) {
                bdcDzzzZzxxYsj.setKZ_QLR(bdcDzzzZzxx.getQlr());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getYwr())) {
                bdcDzzzZzxxYsj.setKZ_YWR(bdcDzzzZzxx.getYwr());
            }
        }
        return bdcDzzzZzxxYsj;
    }

    @Override
    public BdcDzzzJzjxxDO getBdcDzzzJzjxxDOFromBdcDzzzZzxx(BdcDzzzZzxx bdcDzzzZzxx) {
        BdcDzzzJzjxxDO bdcDzzzJzjxxDO = new BdcDzzzJzjxxDO();
        if (bdcDzzzZzxx != null) {
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getJzjid())) {
                bdcDzzzJzjxxDO.setJzjid(bdcDzzzZzxx.getJzjid());
            } else {
                bdcDzzzJzjxxDO.setJzjid(UUIDGenerator.generate());
            }

            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzid())) {
                bdcDzzzJzjxxDO.setZzid(bdcDzzzZzxx.getZzid());
            }
            if (null != bdcDzzzZzxx.getJzjzzsj()) {
                bdcDzzzJzjxxDO.setJzjzzsj(bdcDzzzZzxx.getJzjzzsj());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getJzjzzz())) {
                bdcDzzzJzjxxDO.setJzjzzz(bdcDzzzZzxx.getJzjzzz());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getJzjzzsy())) {
                bdcDzzzJzjxxDO.setJzjzzsy(bdcDzzzZzxx.getJzjzzsy());
            }
            if (null != bdcDzzzZzxx.getJzjyxqjzsj()) {
                bdcDzzzJzjxxDO.setJzjyxqjzsj(bdcDzzzZzxx.getJzjyxqjzsj());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzqzlj())) {
                bdcDzzzJzjxxDO.setZzwjlj(bdcDzzzZzxx.getZzqzlj());
            }
            if (null != bdcDzzzZzxx.getCjsj()) {
                bdcDzzzJzjxxDO.setCjsj(bdcDzzzZzxx.getCjsj());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getSzzs())) {
                bdcDzzzJzjxxDO.setSzzs(bdcDzzzZzxx.getSzzs());
            }
        }
        return bdcDzzzJzjxxDO;
    }

    @Override
    public BdcDzzzMlxxDO getBdcDzzzMlxxDOFromBdcDzzzZzxx(BdcDzzzZzxx bdcDzzzZzxx) {
        BdcDzzzMlxxDO bdcDzzzMlxxDO = new BdcDzzzMlxxDO();
        if (null != bdcDzzzZzxx) {
            bdcDzzzMlxxDO.setMlid(UUIDGenerator.generate());

            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzid())) {
                bdcDzzzMlxxDO.setZzid(bdcDzzzZzxx.getZzid());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzmc())) {
                bdcDzzzMlxxDO.setZzmc(bdcDzzzZzxx.getZzmc());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzlxdm())) {
                bdcDzzzMlxxDO.setZzlxdm(bdcDzzzZzxx.getZzlxdm());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzbh())) {
                bdcDzzzMlxxDO.setZzbh(bdcDzzzZzxx.getZzbh());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzbs())) {
                bdcDzzzMlxxDO.setZzbs(bdcDzzzZzxx.getZzbs());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzbfjg())) {
                bdcDzzzMlxxDO.setZzbfjg(bdcDzzzZzxx.getZzbfjg());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzbfjgdm())) {
                bdcDzzzMlxxDO.setZzbfjgdm(bdcDzzzZzxx.getZzbfjgdm());
            }
            if (null != bdcDzzzZzxx.getZzbfrq()) {
                bdcDzzzMlxxDO.setZzbfrq(bdcDzzzZzxx.getZzbfrq());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getCzzt())) {
                bdcDzzzMlxxDO.setCzzt(bdcDzzzZzxx.getCzzt());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getCzztdm())) {
                bdcDzzzMlxxDO.setCzztdm(bdcDzzzZzxx.getCzztdm());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getCzztdmlx())) {
                bdcDzzzMlxxDO.setCzztdmlx(bdcDzzzZzxx.getCzztdmlx());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getCzztdmlxdm())) {
                bdcDzzzMlxxDO.setCzztdmlxdm(bdcDzzzZzxx.getCzztdmlxdm());
            }
            if (null != bdcDzzzZzxx.getZzyxqqsrq()) {
                bdcDzzzMlxxDO.setZzyxqqsrq(bdcDzzzZzxx.getZzyxqqsrq());
            }
            if (null != bdcDzzzZzxx.getZzyxqjzrq()) {
                bdcDzzzMlxxDO.setZzyxqjzrq(bdcDzzzZzxx.getZzyxqjzrq());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzqzr())) {
                bdcDzzzMlxxDO.setZzqzr(bdcDzzzZzxx.getZzqzr());
            }
            if (null != bdcDzzzZzxx.getZzqzsj()) {
                bdcDzzzMlxxDO.setZzqzsj(bdcDzzzZzxx.getZzqzsj());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzqzmc())) {
                bdcDzzzMlxxDO.setZzqzmc(bdcDzzzZzxx.getZzqzmc());
            }
            if (null != bdcDzzzZzxx.getCjsj()) {
                bdcDzzzMlxxDO.setCjsj(bdcDzzzZzxx.getCjsj());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getDwdm())) {
                bdcDzzzMlxxDO.setDwdm(bdcDzzzZzxx.getDwdm());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getBdcqzh())) {
                bdcDzzzMlxxDO.setBdcqzh(bdcDzzzZzxx.getBdcqzh());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getBdcdyh())) {
                bdcDzzzMlxxDO.setBdcdyh(bdcDzzzZzxx.getBdcdyh());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZzwjlj())) {
                bdcDzzzMlxxDO.setZzwjlj(bdcDzzzZzxx.getZzwjlj());
            }
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getZstype())) {
                bdcDzzzMlxxDO.setZstype(bdcDzzzZzxx.getZstype());
            }
        }
        return bdcDzzzMlxxDO;
    }

    @Override
    public DzzzResponseModel createPdfBdcDzzzByZzid(String zzid) {
        BdcDzzzZzxx bdcDzzzZzxx = getBdcDzzzZzxxFromBdcDzzzZzxxDO(queryBdcDzzzZzxx(zzid), null);
        if (null != bdcDzzzZzxx) {
            //完善电子证照数据
            bdcDzzzZzxx = getPerfectBdcDzzzZzxx(bdcDzzzZzxx);

            List<String> resultList = new ArrayList<>(1);
            resultList.add("zzbs");
            Map<String, Object> createMap = bdcDzzzCheckInfoService.check(bdcDzzzZzxx, ResponseEnum.CHECK_DZZZ_CREATE.getCode(), resultList);
            if (MapUtils.isNotEmpty(createMap)) {
                return bdcDzzzService.dzzzResponseFalse("已存在电子证照，请勿重复生成！", null);
            }
            Map<String, Object> requiredMap = bdcDzzzCheckInfoService.check(bdcDzzzZzxx, ResponseEnum.CHECK_DZZZ_REQUIRED.getCode(), null);
            if (MapUtils.isNotEmpty(requiredMap)) {
                logger.info("验证必填字段：{}，请求时间：{}", requiredMap, DateUtil.now());
                return bdcDzzzService.dzzzResponseFalse("存在为空的必填字段，请检查数据后操作！", null);
            }
            Map<String, Object> parameterMap = bdcDzzzCheckInfoService.check(bdcDzzzZzxx, ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
            if (MapUtils.isNotEmpty(parameterMap)) {
                logger.info("参数合法验证：{}，请求时间：{}", parameterMap, DateUtil.now());
                return bdcDzzzService.dzzzResponseFalse("参数不合法，请检查数据后操作！", parameterMap);
            }

            DzzzResponseModel createModel = bdcDzzzJiangSuCreateService.createOne(bdcDzzzZzxx);
            if (StringUtils.equals(ResponseEnum.FALSE.getCode(), createModel.getHead().getStatus())) {
                return createModel;
            }

            updateBdcDzzzZzqzlj(bdcDzzzZzxx.getZzqzlj(), bdcDzzzZzxx.getZzid());

            return bdcDzzzService.dzzzResponseSuccess("生成不动产电子证照PDF成功，上传文件中心成功！", null);
        }
        return bdcDzzzService.dzzzResponseFalse("未找到该记录！", null);
    }

    @Override
    public int updateBdcDzzzByZzid(BdcDzzzZzxx bdcDzzzZzxx) {
        int count = 0;
        BdcDzzzZzxxDO bdcDzzzZzxxDO = getBdcDzzzZzxxDOFromBdcDzzzZzxx(bdcDzzzZzxx);
        if (null != bdcDzzzZzxxDO) {
            count = bdcDzzzZzxxMapper.updateBdcDzzzByZzid(bdcDzzzZzxxDO);
        }
        return count;
    }


    @Override
    @Transactional
    public DzzzResponseModel deleteBdcDzzz(BdcDzzzZzxx bdcDzzzZzxx) {
        if (null != bdcDzzzZzxx && StringUtils.isNotEmpty(bdcDzzzZzxx.getZzbs())) {
            BdcDzzzZzxxDO bdcDzzzZzxxDO = bdcDzzzZzxxMapper.queryBdcDzzzZzxxByZzbs(bdcDzzzZzxx.getZzbs());
            if (null == bdcDzzzZzxxDO) {
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_RECORD_ACQUIRED_ERROR.getCode(), null);
            }

            // 删除加注件信息
            bdcDzzzJzjxxService.deleteBdcDzzzJzjxxByZzid(bdcDzzzZzxxDO.getZzid());

            // 删除持证主体信息
            bdcDzzzZzxxCzztService.delBdcDzzzZzxxCzztDoByZzid(bdcDzzzZzxxDO.getZzid());

            // 删除业务信息
            bdcDzzzYwxxMapper.deleteYwxxByZzid(bdcDzzzZzxxDO.getZzid());

            // 删除目录信息
            bdcDzzzMlxxMapper.deleteBdcDzzzMlxxByZzid(bdcDzzzZzxxDO.getZzid());

            // 删除电子证照信息
            deleteBdcDzzzDO(bdcDzzzZzxxDO);

            return bdcDzzzService.dzzzResponseSuccess(null);
        } else {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }

    }

    @Override
    public int updateBdcDzzzZzqzlj(String zzqzlj, String zzid) {
        int result = 0;
        if (StringUtils.isNotBlank(zzqzlj) && StringUtils.isNotBlank(zzid)) {
            BdcDzzzZzxxDO bdcDzzz = new BdcDzzzZzxxDO();
            bdcDzzz.setZzid(zzid);
            bdcDzzz.setZzqzlj(zzqzlj);
            bdcDzzz.setQzzt(Constants.DZZZ_QZZT_YQZ);
            result = bdcDzzzZzxxMapper.updateBdcDzzzByZzid(bdcDzzz);
        }

        return result;
    }

    @Override
    public String getBdcDzzzZzxxZstypeByZzlxdm(String zzlxdm) {
        String zstype = "";
        if (StringUtils.equals(zzlxdm, Constants.BDC_ZZLXDM_ZS)) {
            zstype = Constants.BDC_ZSLX_MC_ZS;
        } else if (StringUtils.equals(zzlxdm, Constants.BDC_ZZLXDM_ZM)) {
            zstype = Constants.BDC_ZSLX_MC_ZMS;
        }
        return zstype;
    }

    @Override
    public String countDzzzQuantitativeDistribution(Map map) {
        List<Map> resultList = bdcDzzzZzxxMapper.countBdcDzzzZzxx(map);
        return bdcDzzzMlxxService.countToJsonString(resultList);
    }

    /**
     * @param bdcDzzzZzxx
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn bdcDzzzZzxx
     * @description 完善BdcDzzzZzxx 实体数据
     */

    @Override
    public BdcDzzzZzxx getPerfectBdcDzzzZzxx(BdcDzzzZzxx bdcDzzzZzxx) {
        if (bdcDzzzZzxx != null) {
            //解决传入json 不动产单元号可能存在空格问题
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getBdcdyh())) {
                bdcDzzzZzxx.setBdcdyh(StringUtils.deleteWhitespace(bdcDzzzZzxx.getBdcdyh()));
            }

            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getBdcqzh())) {
                bdcDzzzZzxx.setZzmc(bdcDzzzZzxx.getBdcqzh());
            }
            bdcDzzzZzxx.setZstype(getBdcDzzzZzxxZstypeByZzlxdm(bdcDzzzZzxx.getZzlxdm()));
            if (StringUtils.isNotEmpty(bdcDzzzZzxx.getDwdm())) {
                BdcDzzzConfigDo bdcDzzzConfigDo = bdcDzzzConfigService.queryBdcDzzzConfigDoByDwdm(bdcDzzzZzxx.getDwdm());
                if (null != bdcDzzzConfigDo) {
                    bdcDzzzZzxx.setZzqzr(bdcDzzzConfigDo.getZzqzr());
                    bdcDzzzZzxx.setZzqzmc(bdcDzzzConfigDo.getZzqzmc());
                    bdcDzzzZzxx.setZzqzsj(DateUtil.now());
                }
            }

            if (null != bdcDzzzZzxx.getZzbfrq()) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(bdcDzzzZzxx.getZzbfrq().getTime());
                bdcDzzzZzxx.setYear("" + calendar.get(Calendar.YEAR));
                bdcDzzzZzxx.setMonth("" + (calendar.get(Calendar.MONTH) + 1));
                bdcDzzzZzxx.setDay("" + calendar.get(Calendar.DATE));
            }
        }

        return bdcDzzzZzxx;
    }


    @Override
    public DzzzResponseModel checkBdcDzzzZzxx(BdcDzzzZzxx bdcDzzzZzxx, List<String> resultList) {
        if (bdcDzzzZzxx != null) {
            //完善电子证照数据
            DzzzResponseModel checkModel = checkBdcDzzzZzxxCreate(bdcDzzzZzxx, resultList);
            if (StringUtils.equals(ResponseEnum.FALSE.getCode(), checkModel.getHead().getStatus())) {
                return checkModel;
            }
            bdcDzzzZzxx.setZzzt(Integer.valueOf(Constants.BDC_DZZZ_ZZZT_Y));
            bdcDzzzZzxx.setQzzt(Constants.DZZZ_QZZT_WQZ);
            bdcDzzzZzxx.setTbzt(Constants.DZZZ_TBZT_WTB);
            if (StringUtils.isBlank(bdcDzzzZzxx.getZzid())) {
                bdcDzzzZzxx.setZzid(UUIDGenerator.generate());
            }
            bdcDzzzZzxx.setCjsj(DateUtil.now());
            bdcDzzzZzxx.setZzlsh(bdcDzzzLshService.getBdcDzzzLsh(bdcDzzzZzxx.getZzlxdm(), bdcDzzzZzxx.getZzbfjgdm()));
            bdcDzzzZzxx.setZzbh(getZzbhByDwdmAndLsh(bdcDzzzZzxx.getDwdm(), bdcDzzzZzxx.getZzlsh()));
            String zzbs = bdcDzzzZzxxYsjService.encryptCalculateZzbs(bdcDzzzZzxx);
            bdcDzzzZzxx.setZzbs(zzbs);
            bdcDzzzZzxx.setEwmnr(zzbs + "&" + bdcDzzzZzxx.getBdcdyh());
        } else {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }
        return bdcDzzzService.dzzzResponseSuccess(null);

    }

    @Override
    public DzzzResponseModel checkBdcDzzzZzxxCreate(BdcDzzzZzxx bdcDzzzZzxx, List<String> resultList) {
        bdcDzzzZzxx = getPerfectBdcDzzzZzxx(bdcDzzzZzxx);
        Map<String, Object> requiredMap = bdcDzzzCheckInfoService.check(bdcDzzzZzxx, ResponseEnum.CHECK_DZZZ_REQUIRED.getCode(), null);
        if (MapUtils.isNotEmpty(requiredMap)) {
            logger.info("验证必填字段：{}，请求时间：{}", JSON.toJSONString(requiredMap), DateUtil.now());
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.CHECK_DZZZ_REQUIRED.getCode(), requiredMap);
        }
        Map<String, Object> parameterMap = bdcDzzzCheckInfoService.check(bdcDzzzZzxx, ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        if (MapUtils.isNotEmpty(parameterMap)) {
            logger.info("参数合法验证：{}，请求时间：{}", JSON.toJSONString(parameterMap), DateUtil.now());
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), parameterMap);
        }
        // 根据配置判断是否需要验证本地数据库是否已经存在记录
        if(sfyzbdzzk){
            Map<String, Object> createMap = bdcDzzzCheckInfoService.check(bdcDzzzZzxx, ResponseEnum.CHECK_DZZZ_CREATE.getCode(), resultList);
            if (MapUtils.isNotEmpty(createMap)) {
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.CHECK_DZZZ_CREATE.getCode(), createMap);
            }
            Map<String, Object> insertMap = bdcDzzzCheckInfoService.check(bdcDzzzZzxx, ResponseEnum.CHECK_DZZZ_INSERT.getCode(), resultList);
            if (MapUtils.isNotEmpty(insertMap)) {
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.CHECK_DZZZ_INSERT.getCode(), insertMap);
            }
        }
        return bdcDzzzService.dzzzResponseSuccess(null);
    }

    @Override
    public synchronized String getZzbhByDwdmAndLsh(String dwdm, String lsh) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("D");
        if (StringUtils.isNotEmpty(dwdm) && dwdm.length() >= 6) {
            stringBuilder.append(dwdm.substring(0, 6));
        }
        stringBuilder.append(lsh);
        return stringBuilder.toString();
    }

    @Override
    public List<Map> countBdcDzzzZzxx(Map map) {
        return bdcDzzzZzxxMapper.countBdcDzzzZzxx(map);
    }

    @Override
    public void deleteBdcDzzzDO(BdcDzzzZzxxDO bdcDzzzZzxxDO) {
        bdcDzzzFileConfigService.deleteFileByzzid(bdcDzzzZzxxDO.getZzid());
        bdcDzzzZzxxMapper.deleteBdcDzzzByZzid(bdcDzzzZzxxDO.getZzid());
    }

    @Override
    public String countZzxxZxAndQf(Map param) {
        List<Map> resultList = bdcDzzzZzxxMapper.countZzxxZxAndQf(param);
        if (CollectionUtils.isNotEmpty(resultList)) {
            JSONObject obj = new JSONObject();
            JSONArray mc = new JSONArray();
            JSONArray zzqf = new JSONArray();
            JSONArray zzzx = new JSONArray();

            for (Map map : resultList) {
                mc.add(map.get("MC"));
                zzqf.add(map.get("ZZQF_COUNT"));
                zzzx.add(map.get("ZZZX_COUNT"));
            }

            obj.put("mc", mc);
            obj.put("zzqf", zzqf);
            obj.put("zzzx", zzzx);

            return JSON.toJSONString(obj);
        }
        return "";
    }
}
