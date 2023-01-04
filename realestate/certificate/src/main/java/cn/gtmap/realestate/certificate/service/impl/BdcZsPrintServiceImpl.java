package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.realestate.certificate.core.service.BdcQlrService;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.certificate.service.BdcZsPrintService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.common.core.annotations.ZsDyZt;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJyxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DESUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/17
 * @description 证书打印服务实现类
 */
@Service
public class BdcZsPrintServiceImpl implements BdcZsPrintService {

    private static final Logger logger = LoggerFactory.getLogger(BdcZsPrintServiceImpl.class);
    /**
     * 分别持证
     */
    private static final Integer FBCZ = 1;
    /**
     * 打印现势二维码的地址
     */
    private static final String EWM_URL = "/rest/v1.0/zs/print/{zsid}/ewm";

    /*
     * 证书打印增加户室图二维码地址
     * */
    private static final String EWM_HST = "/rest/v1.0/print/ewm?ewmnr=";

    @Value("${hst.url:}")
    private String hstUrl;

    //配置证书上二维码的外网地址后缀为bdcdyh
    @Value("${zsewm.wwurl:}")
    private String zsEwmUrl;
    //配置证书二维码后缀为zsid
    @Value("${zsewm.wwurl2:}")
    private String zsEwmUrl2;
    //#证书二维码互联网+地址+bdcdyh
    @Value("${zsewm.hlw:}")
    private String hlwewm;

    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcPrintFeignService bdcPrintFeignService;
    @Autowired
    BdcQlrService bdcQlrService;
    @Autowired
    BdcZsService bdcZsService;
    @Autowired
    BdcSlJyxxFeignService bdcSlJyxxFeignService;

    // 特殊共有方式处理逻辑
    @Value("${bengbu.tsgyfs:0}")
    private String tsgyfs;

    /**
     * @param bdcPrintDTO 证书打印对象
     * @return String 打印的xml字符串
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 证书打印
     */
    @Override
    public String zsPrintXml(BdcPrintDTO bdcPrintDTO) {
        String xml = "";
        String xmid = bdcPrintDTO.getXmid();
        String zslx = bdcPrintDTO.getDylx();
        String certificateUrl = bdcPrintDTO.getBdcUrlDTO().getCertificateUrl();

        if (StringUtils.isBlank(xmid) || StringUtils.isBlank(zslx)) {
            throw new MissingArgumentException("缺失xmid或者zslx");
        }
        Map<String, List<Map>> paramMap = new HashMap(1);
        BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);

        if (null == bdcXmDO) {
            throw new MissingArgumentException("未查询到相关项目信息，xmid=" + xmid);
        }
        List<BdcQlrDO> bdcQlrDOList = bdcQlrService.queryListBdcQlr(xmid, CommonConstantUtils.QLRLB_QLR_DM);
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            List<Map> maps = new ArrayList();
            if (FBCZ.equals(bdcXmDO.getSqfbcz())) {
                for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                    Map<String, String> map = new HashMap();
                    map.put("zsid", bdcQlrDO.getZsid());
                    map.put("ewmUrl", StringUtils.replace(certificateUrl + EWM_URL, "{zsid}", bdcQlrDO.getZsid()));
                    maps.add(map);
                }
            } else {
                Map<String, String> map = new HashMap();
                map.put("zsid", bdcQlrDOList.get(0).getZsid());
                map.put("ewmUrl", StringUtils.replace(certificateUrl + EWM_URL, "{zsid}", bdcQlrDOList.get(0).getZsid()));
                maps.add(map);
            }
            paramMap.put(zslx, maps);
            xml = bdcPrintFeignService.print(paramMap);
        }

        return xml;
    }

    /**
     * @param bdcPrintDTO 证书打印对象
     * @return 单个证书的打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取单个证书的打印xml
     */
    @Override
    @ZsDyZt(paramName = Constants.ZSID)
    public String singleZsPrintXml(BdcPrintDTO bdcPrintDTO) {
        String zsid = bdcPrintDTO.getZsid();
        String zslx = bdcPrintDTO.getDylx();
        String certificateUrl = bdcPrintDTO.getBdcUrlDTO().getCertificateUrl();
        String registerUiUrl=bdcPrintDTO.getBdcUrlDTO().getRegisterUiUrl();
        if (StringUtils.isBlank(zsid) || StringUtils.isBlank(zslx)) {
            throw new MissingArgumentException("缺失zsid或者zslx");
        }
        Map<String, List<Map>> paramMap = new HashMap();

        List<Map> maps = new ArrayList(2);
        Map<String, String> map = new HashMap();
        map.put("zsid", zsid);
        map.put("ewmUrl", StringUtils.replace(certificateUrl + EWM_URL, "{zsid}", zsid));
        map.put("jbr", Optional.ofNullable(bdcPrintDTO.getJbr()).orElse(""));
        // 首次证明单获取预售许可证
        List<BdcXmDO> bdcXmDOList = bdcZsService.queryZsXmByZsid(zsid);
        if (StringUtils.equals(zslx, CommonConstantUtils.ZS_MODAL_SCZMD)) {
            map.put("xmid", "");
            map.put("ysxkzh", "");
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                map.put("xmid", bdcXmDOList.get(0).getXmid());
                if (StringUtils.isNotBlank(bdcXmDOList.get(0).getXmid())) {
                    BdcXmFbDO bdcXmFbDO = entityMapper.selectByPrimaryKey(BdcXmFbDO.class, bdcXmDOList.get(0).getXmid());
                    if (bdcXmFbDO != null) {
                        map.put("ysxkzh", bdcXmFbDO.getYsxkzh());
                    }
                }
            }
        }
        String qjgldm = "";
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcXmFbDO bdcXmFbDO = entityMapper.selectByPrimaryKey(BdcXmFbDO.class, bdcXmDOList.get(0).getXmid());
            if (Objects.nonNull(bdcXmFbDO)) {
                if (StringUtils.isNotBlank(bdcXmFbDO.getQjgldm())) {
                    qjgldm = bdcXmFbDO.getQjgldm();
                }
            }
        }

        // 设置宗地图
        BdcZsDO bdcZsDO = bdcZsService.queryBdcZs(zsid);
        String bdcdyh = bdcZsDO.getBdcdyh();
        String djh = "";
        if (bdcZsDO != null && StringUtils.isNotBlank(bdcZsDO.getBdcdyh())) {
            djh = StringUtils.substring(bdcZsDO.getBdcdyh(), 0, 19);
        }
        map.put("zdtUrl", StringUtils.replace(registerUiUrl + CommonConstantUtils.ZDT_URL + "?qjgldm=" + qjgldm, "{bdcdyh}", bdcdyh));
        if(StringUtils.isNotBlank(qjgldm)) {
            map.put("zdtUrl2", StringUtils.replace(registerUiUrl + CommonConstantUtils.ZDT_URL2, "{bdcdyh}", bdcdyh).replace("{qjgldm}", qjgldm));
        } else {
            map.put("zdtUrl2", map.get("zdtUrl"));
        }
        map.put("hstUrl", StringUtils.replace(registerUiUrl + CommonConstantUtils.HST_URL + "?qjgldm=" + qjgldm, "{bdcdyh}", bdcdyh));
        if(StringUtils.isNotBlank(qjgldm)){
            map.put("hstUrl2",StringUtils.replace(registerUiUrl + CommonConstantUtils.HST_URL2, "{bdcdyh}", bdcdyh).replace("{qjgldm}", qjgldm));
        }else{
            map.put("hstUrl2",map.get("hstUrl"));
        }
        map.put("djh", djh);
        try {
            //先对二维码的不动产单元号进行加密
            logger.info("查询户室图单元号{}", bdcdyh);
            String jmBdcdyh = DESUtils.encrypt(bdcdyh);
            map.put("hstewm", registerUiUrl + EWM_HST + URLEncoder.encode(hstUrl + jmBdcdyh, "utf-8"));
            logger.info("户室图二维码地址{}", map.get("hstewm"));
            //设置常州上证书展示的二维码
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                String dyhewm = (StringUtils.isNotBlank(bdcdyh) ? bdcdyh.replace("等", "") : bdcdyh) + "&" + (StringUtils.isNotBlank(bdcXmDOList.get(0).getBdcdywybh()) ? bdcXmDOList.get(0).getBdcdywybh() : "") + "&" + bdcXmDOList.get(0).getSlbh();
                map.put("dyhEwm", registerUiUrl + EWM_HST + URLEncoder.encode(dyhewm, "utf-8"));
                logger.info("常州单元号二维码地址{}", map.get("dyhEwm"));
            } else {
                map.put("dyhEwm", "");
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("生成户室图二维码地址失败");
        }
        //证书共有情况特殊处理
        if (StringUtils.equals("1", tsgyfs)) {
            String gyqk = "";
            try {
                gyqk = bdcZsService.dealZsGyqk(bdcZsDO, false, "");
            } catch (Exception e) {
                logger.error("证书特殊处理共有情况异常，请检查日志");
            }
            map.put("gyqk", gyqk);
        } else {
            map.put("gyqk", "");
        }
        //蚌埠地区证书上二维码要展示外网地址+ bdcdyh去做查询
        map.put("zswwurl", "");
        if (StringUtils.isNotBlank(zsEwmUrl)) {
            map.put("zswwurl", registerUiUrl + EWM_HST + StringUtils.replace(zsEwmUrl, "{bdcdyh}", bdcdyh));
            logger.warn("蚌埠证书二维码生成地址：{}", map.get("zswwurl"));
        }
        //蚌埠地区证书二维码--外网地址+zsid
        map.put("zswwurl2", "");
        if (StringUtils.isNotBlank(zsEwmUrl2)) {
            map.put("zswwurl2", registerUiUrl + EWM_HST + StringUtils.replace(zsEwmUrl2, "{zsid}", zsid));
            logger.warn("蚌埠证书二维码生成地址2：{}", map.get("zswwurl2"));
        }
        //宣城地区证书二维码--互联网+地址+bdcdyh
        map.put("hlwewm","");
        if (StringUtils.isNotBlank(hlwewm)){
            map.put("hlwewm", registerUiUrl + EWM_HST + hlwewm + "?bdcdyh=" + bdcdyh);
            logger.warn("宣城地区证书二维码：{}", map.get("hlwewm"));
        }
        try {
            map.put("qlrzsewm", registerUiUrl + EWM_HST + URLEncoder.encode(bdcZsDO.getQlr() + "、" + bdcZsDO.getBdcqzh() + "、" + bdcZsDO.getZl(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("生成权利人产权证号二维码地址失败");
        }
        maps.add(map);
        paramMap.put(zslx, maps);
        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * @param bdcPrintDTO 证书打印对象参数
     * @return String 打印的xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取证书批量打印的xml
     */
    @Override
    @ZsDyZt(paramName = Constants.LIST_ZSID)
    public String batchZsPrintXml(BdcPrintDTO bdcPrintDTO) {
        List<String> zsidList = bdcPrintDTO.getListZsid();
        String zslx = bdcPrintDTO.getDylx();
        String certificateUrl = bdcPrintDTO.getBdcUrlDTO().getCertificateUrl();
        String registerUiUrl=bdcPrintDTO.getBdcUrlDTO().getRegisterUiUrl();
        if (CollectionUtils.isEmpty(zsidList) || StringUtils.isBlank(zslx)) {
            throw new MissingArgumentException("缺失证书ID值或打印证书类型！");
        }
        Map<String, List<Map>> paramMap = new HashMap(CollectionUtils.size(zsidList));

        List<Map> maps = new ArrayList();
        for (String zsid : zsidList) {
            if (StringUtils.isNotBlank(zsid)) {
                // 查询参数
                Map<String, String> map = new HashMap();
                map.put("zsid", zsid);
                map.put("ewmUrl", StringUtils.replace(certificateUrl + EWM_URL, "{zsid}", zsid));
                map.put("jbr", Optional.ofNullable(bdcPrintDTO.getJbr()).orElse(""));
                // 首次证明单获取预售许可证
                List<BdcXmDO> bdcXmDOList = bdcZsService.queryZsXmByZsid(zsid);
                if (StringUtils.equals(zslx, CommonConstantUtils.ZS_MODAL_SCZMD)) {
                    map.put("xmid", "");
                    map.put("ysxkzh", "");
                    if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                        map.put("xmid", bdcXmDOList.get(0).getXmid());
                        if(StringUtils.isNotBlank(bdcXmDOList.get(0).getXmid())) {
                            BdcXmFbDO bdcXmFbDO = entityMapper.selectByPrimaryKey(BdcXmFbDO.class, bdcXmDOList.get(0).getXmid());
                            if (bdcXmFbDO != null && StringUtils.isNotBlank(bdcXmFbDO.getYsxkzh())) {
                                map.put("ysxkzh", bdcXmFbDO.getYsxkzh());
                            }
                        }
                    }
                }

                String qjgldm = "";
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    BdcXmFbDO bdcXmFbDO = entityMapper.selectByPrimaryKey(BdcXmFbDO.class, bdcXmDOList.get(0).getXmid());
                    if (Objects.nonNull(bdcXmFbDO)) {
                        if (StringUtils.isNotBlank(bdcXmFbDO.getQjgldm())) {
                            qjgldm = bdcXmFbDO.getQjgldm();
                        }
                    }
                }

                // 宗地图
                BdcZsDO bdcZsDO = bdcZsService.queryBdcZs(zsid);
                String bdcdyh = bdcZsDO.getBdcdyh();
                String djh = "";
                if (bdcZsDO != null && StringUtils.isNotBlank(bdcZsDO.getBdcdyh())) {
                    djh = StringUtils.substring(bdcZsDO.getBdcdyh(), 0, 19);
                }
                map.put("zdtUrl", StringUtils.replace(registerUiUrl + CommonConstantUtils.ZDT_URL, "{bdcdyh}", bdcdyh));

                if(StringUtils.isNotBlank(qjgldm)) {
                    map.put("zdtUrl2", StringUtils.replace(registerUiUrl + CommonConstantUtils.ZDT_URL2, "{bdcdyh}", bdcdyh).replace("{qjgldm}", qjgldm));
                } else {
                    map.put("zdtUrl2", map.get("zdtUrl"));
                }
                map.put("hstUrl", StringUtils.replace(registerUiUrl + CommonConstantUtils.HST_URL, "{bdcdyh}", bdcdyh));
                if(StringUtils.isNotBlank(qjgldm)){
                    map.put("hstUrl2",StringUtils.replace(registerUiUrl + CommonConstantUtils.HST_URL2, "{bdcdyh}", bdcdyh).replace("{qjgldm}", qjgldm));
                }else{
                    map.put("hstUrl2",map.get("hstUrl"));
                }
                map.put("djh", djh);

                try {
                    //先对二维码的不动产单元号进行加密
                    logger.info("查询户室图单元号{}", bdcdyh);
                    String jmBdcdyh = DESUtils.encrypt(bdcdyh);
                    logger.info("加密单元号{}", jmBdcdyh);
                    map.put("hstewm", registerUiUrl + EWM_HST + URLEncoder.encode(hstUrl + jmBdcdyh, "utf-8"));
                    //设置常州上证书展示的二维码
                    if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                        String dyhewm = (StringUtils.isNotBlank(bdcdyh) ? bdcdyh.replace("等","") : bdcdyh) + "&" + (StringUtils.isNotBlank(bdcXmDOList.get(0).getBdcdywybh()) ? bdcXmDOList.get(0).getBdcdywybh() : "") + "&" + bdcXmDOList.get(0).getSlbh();
                        map.put("dyhEwm", registerUiUrl + EWM_HST + URLEncoder.encode(dyhewm, "utf-8"));
                        logger.info("常州单元号二维码地址{}", map.get("dyhEwm"));
                    } else {
                        map.put("dyhEwm", "");
                    }
                } catch (UnsupportedEncodingException e) {
                    logger.error("生成户室图二维码地址失败");
                }

                //证书共有情况特殊处理
                if (StringUtils.equals("1", tsgyfs)) {
                    String gyqk = "";
                    try {
                        gyqk = bdcZsService.dealZsGyqk(bdcZsDO, false, "");
                    } catch (Exception e) {
                        logger.error("证书特殊处理共有情况异常，请检查日志");
                    }
                    map.put("gyqk", gyqk);
                } else {
                    map.put("gyqk", "");
                }
                //蚌埠地区证书上二维码要展示外网地址+ bdcdyh去做查询
                map.put("zswwurl", "");
                if (StringUtils.isNotBlank(zsEwmUrl)) {
                    map.put("zswwurl", registerUiUrl + EWM_HST + StringUtils.replace(zsEwmUrl, "{bdcdyh}", bdcdyh));
                    logger.warn("蚌埠证书二维码生成地址：{}", map.get("zswwurl"));
                }

                //蚌埠地区证书二维码--外网地址+zsid
                map.put("zswwurl2", "");
                if (StringUtils.isNotBlank(zsEwmUrl2)) {
                    map.put("zswwurl2", registerUiUrl + EWM_HST + StringUtils.replace(zsEwmUrl2, "{zsid}", zsid));
                    logger.warn("蚌埠证书二维码生成地址2：{}", map.get("zswwurl2"));
                }
                //宣城地区证书二维码--互联网+地址+bdcdyh
                map.put("hlwewm","");
                if (StringUtils.isNotBlank(hlwewm)){
                    map.put("hlwewm", registerUiUrl + EWM_HST + hlwewm + "?bdcdyh=" + bdcdyh);
                    logger.warn("宣城地区证书二维码：{}", map.get("hlwewm"));
                }
                try {
                    map.put("qlrzsewm", registerUiUrl + EWM_HST + URLEncoder.encode(bdcZsDO.getQlr() + "、" + bdcZsDO.getBdcqzh() + "、" + bdcZsDO.getZl(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    logger.error("生成权利人产权证号二维码地址失败");
                }
                maps.add(map);

            }
        }

        paramMap.put(zslx, maps);
        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * @param bdcPrintDTO 打印参数
     * @return 打印xml结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取和证书相关的附属清单的打印xml
     */
    @Override
    public String zsFsqdPrintXml(BdcPrintDTO bdcPrintDTO) {
        String dylx = bdcPrintDTO.getDylx();
        String gzlslid = bdcPrintDTO.getGzlslid();
        String zsid = bdcPrintDTO.getZsid();
        if (StringUtils.isBlank(dylx)) {
            throw new MissingArgumentException("缺失打印类型参数！");
        }
        if (StringUtils.isBlank(gzlslid) && StringUtils.isBlank(zsid)) {
            throw new MissingArgumentException("gzlslid和zsid至少一个要有值！");
        }
        Map<String, List<Map>> paramMap = new HashMap();
        List<Map> maps = new ArrayList();
        // 查询参数
        if (StringUtils.isNotBlank(zsid)) {
            HashMap map = new HashMap();
            map.put("zsid", zsid);
            maps.add(map);
        } else {
            maps = generateFsqdParamGzlslid(gzlslid);
        }
        paramMap.put(dylx, maps);
        return bdcPrintFeignService.print(paramMap);
    }

    private List<Map> generateFsqdParamGzlslid(String gzlslid) {
        List<Map> maps = new ArrayList();

        BdcZsQO bdcZsQO = new BdcZsQO();
        List<Integer> qsztList = new ArrayList();
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        qsztList.add(CommonConstantUtils.QSZT_TEMPORY);
        bdcZsQO.setQsztList(qsztList);
        bdcZsQO.setGzlslid(gzlslid);
        List<String> zsidList = bdcZsService.queryZsid(bdcZsQO);
        if (CollectionUtils.isNotEmpty(zsidList)) {
            for (String zsid : zsidList) {
                Map map = new HashMap();
                map.put("zsid", zsid);
                maps.add(map);
            }
        }
        return maps;
    }
}
