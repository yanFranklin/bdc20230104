package cn.gtmap.realestate.inquiry.service.changzhou.impl;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSfxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxmQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxmFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.ChangeMoneyToCnUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.inquiry.core.dto.BdcXmSfxmDTO;
import cn.gtmap.realestate.inquiry.core.dto.BdcXmSfxxDTO;
import cn.gtmap.realestate.inquiry.service.changzhou.BdcFpDyService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@ConfigurationProperties("changzhou.fph")
public class BdcFpDyServiceImpl implements BdcFpDyService {

    /**
     * ????????????-????????????
     */
    public static final String JKFS_SMZF = "3";

    /**
     * ???????????????????????????
     */
    private Map<String, String> xmlbdb;
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcFpDyServiceImpl.class);
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;
    @Autowired
    private BdcSlSfxmFeignService bdcSlSfxmFeignService;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    /**
     * ????????????????????? ?????????
     *
     * @param fplb   fplb
     * @param sfxxid sfxxid
     * @return ????????????
     */
    @Override
    public Object getJksZbSjy(String sfxxid, String fplb) {
        BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxFeignService.queryBdcSlSfxxBySfxxid(sfxxid);
        if (bdcSlSfxxDO == null) {
            throw new AppException("???????????? sfxx");
        }

        BdcXmQO xmQO = new BdcXmQO();
        xmQO.setGzlslid(bdcSlSfxxDO.getGzlslid());
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(xmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            throw new AppException("????????????????????????");
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);
        String slsj = DateFormatUtils.format(bdcXmDO.getSlsj(), "yyyy-MM-dd HH:mm:ss");

        List<BdcSlSfxmDO> bdcSlSfxmDOS = getBdcSlSfxmDOS(sfxxid, fplb);
        Double hj = bdcSlSfxmDOS.stream().filter(sfxm -> sfxm.getSsje() != null).mapToDouble(BdcSlSfxmDO::getSsje).sum();
        String dxhj = "";
        try {
            dxhj = ChangeMoneyToCnUtils.convert(Double.toString(hj));
        } catch (Exception e) {
            e.printStackTrace();
        }


        Map<String, String> sfxxMap = new HashMap<>(16);
        sfxxMap.put("slsj", slsj);
        sfxxMap.put("fph", bdcSlSfxxDO.getFssrfph());
        sfxxMap.put("fff", bdcSlSfxxDO.getFff());
        sfxxMap.put("jfrxm", bdcSlSfxxDO.getJfrxm());
        sfxxMap.put("sfrzh", bdcSlSfxxDO.getSfrzh());
        sfxxMap.put("sfrkhyh", bdcSlSfxxDO.getSfrkhyh());
        sfxxMap.put("sfdwmc", bdcSlSfxxDO.getSfdwmc());
        sfxxMap.put("sfztczrxm", bdcSlSfxxDO.getSfztczrxm());
        sfxxMap.put("hj", Double.toString(hj));
        sfxxMap.put("dxhj", dxhj);
        return Collections.singleton(sfxxMap);
    }

    private List<BdcSlSfxmDO> getBdcSlSfxmDOS(String sfxxid, String fplb) {
        BdcSlSfxmQO bdcSlSfxmQO = new BdcSlSfxmQO();
        String sfxmdms = MapUtils.getString(xmlbdb, fplb);
        bdcSlSfxmQO.setSfxmdm(sfxmdms);
        bdcSlSfxmQO.setSfxxid(sfxxid);
        return bdcSlSfxmFeignService.listFpcxSfxm(bdcSlSfxmQO);
    }

    /**
     * ????????????????????? ?????????
     *
     * @param sfxxid sfxxid
     * @param fplb   fplb
     * @return ????????????
     */
    @Override
    public Object getJksZb(String sfxxid, String fplb) {
        List<BdcSlSfxmDO> bdcSlSfxmDOS = getBdcSlSfxmDOS(sfxxid, fplb);
        List<Map<String, String>> sfxmList = Lists.newArrayList();
        List<Map> zdList = bdcZdFeignService.listBdcZd().get("jedw");
        for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOS) {
            // ?????????????????????????????????
            if (bdcSlSfxmDO.getSl() == null) {
                continue;
            }
            Map<String, String> sfxmMap = new HashMap<>(8);
            sfxmMap.put("sfxmdm", bdcSlSfxmDO.getSfxmdm());
            sfxmMap.put("sfxmmc", bdcSlSfxmDO.getSfxmmc());
            sfxmMap.put("sfxmbz", bdcSlSfxmDO.getSfxmbz());
            sfxmMap.put("sl", Double.toString(bdcSlSfxmDO.getSl()));
            String jedw;
            if (StringUtils.isNotBlank(bdcSlSfxmDO.getJedw())) {
                jedw = StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(bdcSlSfxmDO.getJedw()), zdList);
            } else {
                jedw = bdcSlSfxmDO.getJedw();
            }
            sfxmMap.put("jedw", jedw);
            sfxmMap.put("ysje", bdcSlSfxmDO.getYsje() == null ? null : Double.toString(bdcSlSfxmDO.getYsje()));
            sfxmList.add(sfxmMap);
        }
        return sfxmList;
    }

    /**
     * ??????????????????????????????<br>
     * ?????? sfxx ????????????????????? qlrmc???gzldymc ??? ????????????????????????
     *
     * @param slbh   slbh
     * @param sfxmdm sfxmdm ??????????????????
     * @return {List} ??????????????????
     */
    @Override
    public Object queryFpcxSfxx(String slbh, String sfxmdm) {
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException("slbh");
        }
        if (StringUtils.isBlank(sfxmdm)) {
            throw new MissingArgumentException("sfxmdm");
        }
        BdcXmQO xmQO = new BdcXmQO();
        xmQO.setSlbh(slbh);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(xmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            throw new AppException("????????????????????????");
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);

        BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
        bdcSlSfxxQO.setGzlslid(bdcXmDO.getGzlslid());
        bdcSlSfxxQO.setSfxmdm(sfxmdm);

        List<BdcSfxxDTO> bdcSfxxDTOS = bdcSlSfxxFeignService.listFpcxSfxxSfxm(bdcSlSfxxQO);
        if (CollectionUtils.isEmpty(bdcSfxxDTOS)) {
            bdcSfxxDTOS = new ArrayList<>();
        }
        List<BdcXmSfxxDTO> bdcXmSfxxDTOS = Lists.newArrayList();
        for (BdcSfxxDTO bdcSfxxDTO : bdcSfxxDTOS) {
            List<BdcSlSfxmDO> bdcSlSfxmDOS = bdcSfxxDTO.getBdcSlSfxmDOS();
            Double hj = bdcSlSfxmDOS.stream().filter(sfxm -> null != sfxm.getSsje()).map(t -> new BigDecimal(String.valueOf(t.getSsje())))
                    .reduce((m,n)->m.add(n)).map(BigDecimal::doubleValue).orElse(0d);
            BdcSlSfxxDO bdcSlSfxxDO = bdcSfxxDTO.getBdcSlSfxxDO();
            bdcSlSfxxDO.setHj(hj);
            BdcXmSfxxDTO bdcXmSfxxDTO = new BdcXmSfxxDTO();
            BeanUtils.copyProperties(bdcSlSfxxDO, bdcXmSfxxDTO);
            bdcXmSfxxDTO.setGzldymc(bdcXmDO.getGzldymc());
            List<BdcQlrDO> bdcQlrDOS = bdcQlrFeignService.listAllBdcQlrBySlbh(slbh, bdcSlSfxxDO.getQlrlb());
            BdcQlrDO bdcQlrDO = bdcQlrDOS.get(0);
            bdcXmSfxxDTO.setQlrmc(bdcQlrDO.getQlrmc());
            bdcXmSfxxDTOS.add(bdcXmSfxxDTO);
        }
        return bdcXmSfxxDTOS;
    }

    /**
     * ????????????????????????<br>
     * ???????????????????????? jkfs ??? ?????????????????? jspzfph ??? fssrfph ????????????????????????
     *
     * @param qlrlb qlrlb
     * @param fplb  fplb
     * @return {sfxx} ????????????
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    @Override
    public Object listFpcxSfxx(String qlrlb, String fplb) {
        BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
        bdcSlSfxxQO.setQlrlb(qlrlb);
        bdcSlSfxxQO.setFsFphSfwk(StringUtils.equals(fplb, CommonConstantUtils.FPLB_FSSR));
        bdcSlSfxxQO.setJspzFphSfwk(StringUtils.equals(fplb, CommonConstantUtils.FPLB_JSPZ));
        return this.fpcxSfxx(fplb, bdcSlSfxxQO);
    }

    @Override
    public Object listFpcxSfxx(List<String> sfxxidList, String fplb) {
        BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
        bdcSlSfxxQO.setSfxxidList(sfxxidList);
        return this.fpcxSfxx(fplb, bdcSlSfxxQO);
    }

    private Object fpcxSfxx(String fplb, BdcSlSfxxQO bdcSlSfxxQO) {
        String sfxmdms = MapUtils.getString(xmlbdb, fplb);
        bdcSlSfxxQO.setSfxmdm(sfxmdms);
        bdcSlSfxxQO.setJkfs(JKFS_SMZF);
//        bdcSlSfxxQO.setJkfs("2");
        List<BdcSfxxDTO> bdcSfxxDTOS = bdcSlSfxxFeignService.listFpcxSfxxSfxm(bdcSlSfxxQO);
        List<BdcXmSfxmDTO> bdcXmSfxxDTOS = Lists.newArrayList();
        for (BdcSfxxDTO bdcSfxxDTO : bdcSfxxDTOS) {
            List<BdcSlSfxmDO> bdcSlSfxmDOS = bdcSfxxDTO.getBdcSlSfxmDOS();
            Double hj = bdcSlSfxmDOS.stream().filter(sfxm -> null!=sfxm.getSsje()).map(t -> new BigDecimal(String.valueOf(t.getSsje())))
                    .reduce((m,n)->m.add(n)).map(BigDecimal::doubleValue).orElse(0d);
            BdcSlSfxxDO bdcSlSfxxDO = bdcSfxxDTO.getBdcSlSfxxDO();
            bdcSlSfxxDO.setHj(hj);
            BdcXmSfxmDTO bdcXmSfxxDTO = new BdcXmSfxmDTO();
            bdcXmSfxxDTO.setBdcSlSfxxDO(bdcSlSfxxDO);
            bdcXmSfxxDTO.setBdcSlSfxmDOS(bdcSlSfxmDOS);

            BdcXmQO xmQO = new BdcXmQO();
            xmQO.setGzlslid(bdcSlSfxxDO.getGzlslid());
            List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(xmQO);
            if (CollectionUtils.isEmpty(bdcXmDOS)) {
                LOGGER.error("gzlslid: {},????????????????????????", bdcSlSfxxDO.getGzlslid());
                continue;
            }
            BdcXmDO bdcXmDO = bdcXmDOS.get(0);
            bdcXmSfxxDTO.setGzldymc(bdcXmDO.getGzldymc());
            bdcXmSfxxDTO.setSlbh(bdcXmDO.getSlbh());
            bdcXmSfxxDTO.setHj(hj);
            bdcXmSfxxDTO.setFph(StringUtils.equals(fplb, CommonConstantUtils.FPLB_FSSR) ? bdcSlSfxxDO.getFssrfph() :
                    bdcSlSfxxDO.getJspzfph());
            List<BdcQlrDO> bdcQlrDOS = bdcQlrFeignService.listAllBdcQlrBySlbh(bdcXmDO.getSlbh(), bdcSlSfxxDO.getQlrlb());
            BdcQlrDO bdcQlrDO = bdcQlrDOS.get(0);
            bdcXmSfxxDTO.setQlrmc(bdcQlrDO.getQlrmc());
            bdcXmSfxxDTOS.add(bdcXmSfxxDTO);
        }
        return bdcXmSfxxDTOS;
    }

    public Map<String, String> getXmlbdb() {
        return xmlbdb;
    }

    public void setXmlbdb(Map<String, String> xmlbdb) {
        this.xmlbdb = xmlbdb;
    }
}
