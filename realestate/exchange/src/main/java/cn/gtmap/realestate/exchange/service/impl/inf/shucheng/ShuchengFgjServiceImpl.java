package cn.gtmap.realestate.exchange.service.impl.inf.shucheng;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQjgldmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwLjzFeginService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.exchange.core.dto.ExchangeDsfCommonResponse;
import cn.gtmap.realestate.exchange.core.dto.shucheng.fgj.*;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.response.FwqlCxResponseSdxx;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.nantong.ZfcxService;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/4/6
 * @description 给舒城房管局提供查询接口服务类
 */
@Service(value = "shuchengFgjServiceImpl")
public class ShuchengFgjServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShuchengFgjServiceImpl.class);

    private static final Pattern pattern = Pattern.compile("[^0-9]");

    @Autowired
    BdcXmMapper bdcXmMapper;

    @Autowired
    private BdcXmfbFeignService bdcXmfbFeignService;
    @Autowired
    private FwLjzFeginService fwLjzFeginService;

    @Autowired
    private FwHsFeignService fwHsFeignService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ZfcxService zfcxService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcXmFeignService xmFeignService;
	@Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;


    /**
     * 给房管局提供查询接口-舒城县存量房交易备案接口
     *
     * @param
     * @return
     * @Date 2022/4/6
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public ExchangeDsfCommonResponse shuchengClfHyxx(ShuchengFgjClfHyxxDTO clfHyxxDTO) {
        List<ShuchengFgjClfHyxxDTO> shuchengFgjClfHyxxDTOList = new ArrayList<>();

        if (checkparam(clfHyxxDTO)) {
            List<Map> clfHyxxDTOList = bdcXmMapper.queryFgjClfxx(clfHyxxDTO);
            for (Map map : clfHyxxDTOList) {
                ShuchengFgjClfHyxxDTO fgjClfHyxxDTO = new ShuchengFgjClfHyxxDTO();
                fgjClfHyxxDTO = JSONObject.parseObject(JSONObject.toJSONString(map), ShuchengFgjClfHyxxDTO.class);
                //查询权籍数据库，fw_ljz，查询房屋名称；查询fw-hs取房屋编号
                BdcQjgldmQO qjgldmQO = new BdcQjgldmQO();
                qjgldmQO.setBdcdyh(fgjClfHyxxDTO.getBDCDYBH());
                String qjgldm = bdcXmfbFeignService.queryQjgldm(qjgldmQO);
                FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(fgjClfHyxxDTO.getBDCDYBH(), qjgldm);
                FwLjzDO fwLjzDO = new FwLjzDO();

                if (null != fwHsDO) {
//                    fgjClfHyxxDTO.setFWBH(fwHsDO.getYsfwbm());
                    fwLjzDO = fwLjzFeginService.queryLjzByFwDcbIndex(fwHsDO.getFwDcbIndex(), qjgldm);

                } else {
                    //意味这个是独幢，或者多幢
                    fwLjzDO = fwLjzFeginService.queryLjzByBdcdyh(fgjClfHyxxDTO.getBDCDYBH(), qjgldm);

                }
                if (null != fwLjzDO) {
                    fgjClfHyxxDTO.setLDMC(fwLjzDO.getFwmc());
                }
                //查询土地证
                String tdbdcdyh = StringUtils.substring(fgjClfHyxxDTO.getBDCDYBH(), 0, 19) + "W00000000";
                List<String> qllxs = new ArrayList<>();
                qllxs.add(CommonConstantUtils.QLXX_QLLX_JSYDSYQ);
                qllxs.add(CommonConstantUtils.QLXX_QLLX_ZJDSYQ);
                qllxs.add(CommonConstantUtils.QLXX_QLLX_JTJSYDSYQ);
                BdcXmQO xmQO = new BdcXmQO();
                xmQO.setBdcdyh(tdbdcdyh);
                xmQO.setQllxs(qllxs);
                xmQO.setQszt(CommonConstantUtils.QSZT_VALID);
                List<BdcXmDO> xmDOList = xmFeignService.listBdcXm(xmQO);
                if (CollectionUtils.isNotEmpty(xmDOList)) {
                    fgjClfHyxxDTO.setTDZBH(xmDOList.get(0).getBdcqzh());
                }

                //查询抵押
                List<BdcQl> listDyaq = commonService.listXsQlByBdcdyh(fgjClfHyxxDTO.getBDCDYBH(), CommonConstantUtils.QLLX_DYAQ_DM.toString());
                if (CollectionUtils.isNotEmpty(listDyaq)) {
                    fgjClfHyxxDTO.setISDY(CommonConstantUtils.SF_S_DM);
                } else {
                    fgjClfHyxxDTO.setISDY(CommonConstantUtils.SF_F_DM);
                }
                //查询查封
                List<BdcQl> listCf = commonService.listXsQlByBdcdyh(fgjClfHyxxDTO.getBDCDYBH(), CommonConstantUtils.QLLX_CF.toString());
                if (CollectionUtils.isNotEmpty(listCf)) {
                    fgjClfHyxxDTO.setISCF(CommonConstantUtils.SF_S_DM);
                } else {
                    fgjClfHyxxDTO.setISCF(CommonConstantUtils.SF_F_DM);
                }
                //查询异议
                List<BdcQl> listYy = commonService.listXsQlByBdcdyh(fgjClfHyxxDTO.getBDCDYBH(), CommonConstantUtils.QLLX_YY.toString());
                if (CollectionUtils.isNotEmpty(listYy)) {
                    fgjClfHyxxDTO.setISYY(CommonConstantUtils.SF_S_DM);
                } else {
                    fgjClfHyxxDTO.setISYY(CommonConstantUtils.SF_F_DM);
                }
                // 锁定信息
                String xmid = null == map.get("XMID") ? "" : map.get("XMID").toString();
                List<FwqlCxResponseSdxx> sdxxList = zfcxService.getSdxx(fgjClfHyxxDTO.getBDCDYBH(), xmid);
                if (CollectionUtils.isNotEmpty(sdxxList)) {
                    fgjClfHyxxDTO.setISXZ(CommonConstantUtils.SF_S_DM);

                } else {
                    fgjClfHyxxDTO.setISXZ(CommonConstantUtils.SF_F_DM);

                }
                //开始组织共有人信息
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(xmid);
                bdcQlrQO.setQlrlb(Constants.QLRLB_QLR);
                List<BdcQlrDO> qlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);

                if (CollectionUtils.isNotEmpty(qlrDOList)) {
                    String gyrmc = "";
                    String gyrzjh = "";
                    String gyrzjlx = "";
                    String gyrdz = "";

                    //取sxh ！= 1的，都是共有人
                    for (BdcQlrDO qlrDO : qlrDOList) {
                        if (null != qlrDO.getSxh() && qlrDO.getSxh() != Constants.QLR_SXH1) {
                            if (StringUtils.isNotBlank(qlrDO.getQlrmc())) {
                                gyrmc += qlrDO.getQlrmc() + ",";

                            }
                            if (StringUtils.isNotBlank(qlrDO.getZjh())) {
                                gyrzjh += qlrDO.getZjh() + ",";
                            }
                            if (null != qlrDO.getZjzl()) {
                                //获取zjzl的字典表
                                List<Map> zjzlMaps = bdcZdFeignService.queryBdcZd("zjzl");
                                String zjzl = StringToolUtils.convertBeanPropertyValueOfZd(qlrDO.getZjzl(), zjzlMaps);
                                gyrzjlx += zjzl + ",";

                            }
                            if (StringUtils.isNotBlank(qlrDO.getTxdz())) {
                                gyrdz += qlrDO.getQlrmc() + ",";
                            }
                        }
                    }
                    if (gyrmc.endsWith(",")) {
                        gyrmc = gyrmc.substring(0, gyrmc.length() - 1);
                    }
                    if (gyrzjh.endsWith(",")) {
                        gyrzjh = gyrzjh.substring(0, gyrzjh.length() - 1);
                    }
                    if (gyrzjlx.endsWith(",")) {
                        gyrzjlx = gyrzjlx.substring(0, gyrzjlx.length() - 1);
                    }
                    if (gyrdz.endsWith(",")) {
                        gyrdz = gyrdz.substring(0, gyrdz.length() - 1);
                    }
                    fgjClfHyxxDTO.setGYRXM(gyrmc);
                    fgjClfHyxxDTO.setGYRZJHM(gyrzjh);
                    fgjClfHyxxDTO.setGYRZJLB(gyrzjlx);
                    fgjClfHyxxDTO.setGYRDZ(gyrdz);
                }
                shuchengFgjClfHyxxDTOList.add(fgjClfHyxxDTO);
            }
        } else {
            return ExchangeDsfCommonResponse.fail("查询参数必须两两组合！");
        }

        return ExchangeDsfCommonResponse.ok(shuchengFgjClfHyxxDTOList);
    }


    /**
     * 给房管局提供查询接口-舒城县一手房登记数据接口
     *
     * @param
     * @return
     * @Date 2022/4/16
     * @author wangyinghao
     */
    public ExchangeDsfCommonResponse shuchengYsfDjsj(ShuchengFgjYsfDjxxDTO clfHyxxDTO) {
        List<ShuchengFgjYsfDjxxDTO> shuchengFgjYsfDjxxDTOS = new ArrayList<>();
        if (StringUtils.isNotBlank(clfHyxxDTO.getFwbm())) {
            List<Map> clfHyxxDTOList = bdcXmMapper.queryFgjYsfxx(Collections.emptyList(), clfHyxxDTO.getFwbm());
            //处理数据
            for (Map map : clfHyxxDTOList) {
                //单个返回实体
                String xmid = null == map.get("XMID") ? "" : map.get("XMID").toString();
                String bdcdyh = null == map.get("BDCDYH") ? "" : map.get("BDCDYH").toString();
                String bdcqzh = null == map.get("BDCQZH") ? "" : map.get("BDCQZH").toString();
                String qlid = null == map.get("QLID") ? "" : map.get("QLID").toString();
                if (Objects.isNull(xmid) || Objects.isNull(bdcdyh)) {
                    continue;
                }
                ShuchengFgjYsfDjxxDTO shuchengFgjYsfDjxxDTO = new ShuchengFgjYsfDjxxDTO();
                shuchengFgjYsfDjxxDTO = JSONObject.parseObject(JSONObject.toJSONString(map), ShuchengFgjYsfDjxxDTO.class);
                //查询权籍数据库，fw_ljz，查询房屋名称,项目名称；
                BdcQjgldmQO qjgldmQO = new BdcQjgldmQO();
                qjgldmQO.setBdcdyh(shuchengFgjYsfDjxxDTO.getBdcdyh());
                String qjgldm = bdcXmfbFeignService.queryQjgldm(qjgldmQO);
                FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(shuchengFgjYsfDjxxDTO.getBdcdyh(), qjgldm);
                FwLjzDO fwLjzDO = new FwLjzDO();
                if (Objects.nonNull(fwHsDO)) {
                    fwLjzDO = fwLjzFeginService.queryLjzByFwDcbIndex(fwHsDO.getFwDcbIndex(), qjgldm);
                } else {
                    //意味这个是独幢，或者多幢
                    fwLjzDO = fwLjzFeginService.queryLjzByBdcdyh(shuchengFgjYsfDjxxDTO.getBdcdyh(), qjgldm);
                }
                //查询抵押
                List<BdcQl> listDyaq = commonService.listXsQlByBdcdyh(shuchengFgjYsfDjxxDTO.getBdcdyh(), CommonConstantUtils.QLLX_DYAQ_DM.toString());
                //查询查封
                List<BdcQl> listCf = commonService.listXsQlByBdcdyh(shuchengFgjYsfDjxxDTO.getBdcdyh(), CommonConstantUtils.QLLX_CF.toString());
                //查询预告
                List<BdcQl> listYg = commonService.listXsQlByBdcdyh(shuchengFgjYsfDjxxDTO.getBdcdyh(), CommonConstantUtils.QLLX_YG_DM.toString());
                if(CollectionUtils.isNotEmpty(listYg)){
                    listYg = listYg.stream()
                            .filter(bdcQl -> {
                                BdcYgDO bdcYgDO = (BdcYgDO)bdcQl;
                                return Arrays.asList(CommonConstantUtils.YGDJZL_YSSPFYG,
                                        CommonConstantUtils.YGDJZL_QTYG,
                                        CommonConstantUtils.YGDJZL_YSSPF
                                ).contains(bdcYgDO.getYgdjzl());
                            })
                            .collect(Collectors.toList());
                }
                //查询异议
                List<BdcQl> listYy = commonService.listXsQlByBdcdyh(shuchengFgjYsfDjxxDTO.getBdcdyh(), CommonConstantUtils.QLLX_YY.toString());
                // 锁定信息
                List<FwqlCxResponseSdxx> sdxxList = zfcxService.getSdxx(shuchengFgjYsfDjxxDTO.getBdcdyh(), xmid);
                //权利人信息
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(xmid);
                bdcQlrQO.setQlrlb(Constants.QLRLB_QLR);
                List<BdcQlrDO> qlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                //项目附表信息
                BdcXmFbDO bdcXmFbDO = null;
                BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
                bdcXmFbQO.setXmid(xmid);
                List<BdcXmFbDO> bdcXmFbDOS = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
                if (CollectionUtils.isEmpty(bdcXmFbDOS)) {
                    bdcXmFbDO = new BdcXmFbDO();
                    bdcXmFbDO.setCqly("");
                }else {
                    bdcXmFbDO = bdcXmFbDOS.get(0);
                }
                //字典信息
                Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
                //设置返回值
                if (Objects.nonNull(fwHsDO)) {
                    shuchengFgjYsfDjxxDTO.setHouseno(fwHsDO.getFjh());
                    if (Objects.nonNull(fwHsDO.getScjzmj())) {
                        shuchengFgjYsfDjxxDTO.setArea(fwHsDO.getScjzmj());
                    }
                    if (Objects.nonNull(fwHsDO.getSctnjzmj())) {
                        shuchengFgjYsfDjxxDTO.setAreawithin(fwHsDO.getSctnjzmj());
                    }
                    if (Objects.nonNull(fwHsDO.getScftjzmj())) {
                        shuchengFgjYsfDjxxDTO.setAreapublic(fwHsDO.getScftjzmj());
                    }
                }
                if (Objects.nonNull(fwLjzDO)) {
                    shuchengFgjYsfDjxxDTO.setBuildno(fwLjzDO.getFwmc());
                    shuchengFgjYsfDjxxDTO.setXmmc(fwLjzDO.getXmmc());
                }
                //抵押状态
                if (CollectionUtils.isNotEmpty(listDyaq)) {
                    shuchengFgjYsfDjxxDTO.setDyzt(CommonConstantUtils.SF_S_DM);
                } else {
                    shuchengFgjYsfDjxxDTO.setDyzt(CommonConstantUtils.SF_F_DM);
                }
                //查封状态
                if (CollectionUtils.isNotEmpty(listCf)) {
                    shuchengFgjYsfDjxxDTO.setCfzt(CommonConstantUtils.SF_S_DM);
                } else {
                    shuchengFgjYsfDjxxDTO.setCfzt(CommonConstantUtils.SF_F_DM);
                }
                //预告状态
                if (CollectionUtils.isNotEmpty(listYg)) {
                    shuchengFgjYsfDjxxDTO.setYgzt(CommonConstantUtils.SF_S_DM);
                } else {
                    shuchengFgjYsfDjxxDTO.setYgzt(CommonConstantUtils.SF_F_DM);
                }
                //异议状态
                if (CollectionUtils.isNotEmpty(listYy)) {
                    shuchengFgjYsfDjxxDTO.setYyzt(CommonConstantUtils.SF_S_DM);
                } else {
                    shuchengFgjYsfDjxxDTO.setYyzt(CommonConstantUtils.SF_F_DM);
                }
                // 限制信息
                if (CollectionUtils.isNotEmpty(listDyaq) || CollectionUtils.isNotEmpty(listCf)) {
                    shuchengFgjYsfDjxxDTO.setXzzt(CommonConstantUtils.SF_S_DM);
                } else {
                    shuchengFgjYsfDjxxDTO.setXzzt(CommonConstantUtils.SF_F_DM);
                }
                //权利人信息
                if (CollectionUtils.isNotEmpty(qlrDOList)) {
                    List<ShuchengFgjQlrxxDTO> shuchengFgjQlrxxDTOS = new ArrayList<>();
                    for (BdcQlrDO bdcQlrDO : qlrDOList) {
                        ShuchengFgjQlrxxDTO shuchengFgjQlrxxDTO = new ShuchengFgjQlrxxDTO();
                        BeanUtils.copyProperties(bdcQlrDO, shuchengFgjQlrxxDTO);
                        shuchengFgjQlrxxDTO.setCqzh(bdcqzh);
                        shuchengFgjQlrxxDTO.setGyfs(StringToolUtils.convertBeanPropertyValueOfZd(bdcQlrDO.getGyfs(), zdMap.get("gyfs")));
                        shuchengFgjQlrxxDTO.setZjzl(StringToolUtils.convertBeanPropertyValueOfZd(bdcQlrDO.getZjzl(), zdMap.get("zjzl")));
                        shuchengFgjQlrxxDTOS.add(shuchengFgjQlrxxDTO);
                    }
                    shuchengFgjYsfDjxxDTO.setQlr(shuchengFgjQlrxxDTOS);
                }

                //查封信息
                if (CollectionUtils.isNotEmpty(listCf)) {
                    List<ShuchengFgjCfxxDTO> shuchengFgjCfxxDTOS = new ArrayList<>();
                    for (BdcQl bdcQl : listCf) {
                        BdcCfDO bdcCfDO = (BdcCfDO) bdcQl;
                        ShuchengFgjCfxxDTO shuchengFgjCfxxDTO = new ShuchengFgjCfxxDTO();
                        BeanUtils.copyProperties(bdcCfDO, shuchengFgjCfxxDTO);
                        shuchengFgjCfxxDTO.setBdcdybh(bdcCfDO.getBdcdywybh());
                        shuchengFgjCfxxDTO.setCflx(StringToolUtils.convertBeanPropertyValueOfZd(bdcCfDO.getCflx(), zdMap.get("cflx")));
                        shuchengFgjCfxxDTO.setQszt(StringToolUtils.convertBeanPropertyValueOfZd(bdcCfDO.getQszt(), zdMap.get("qszt")));
                        shuchengFgjCfxxDTO.setCqly(bdcXmFbDO.getCqly());
                        shuchengFgjCfxxDTOS.add(shuchengFgjCfxxDTO);
                    }
                    shuchengFgjYsfDjxxDTO.setCfxx(shuchengFgjCfxxDTOS);
                }

                //异议信息
                if (CollectionUtils.isNotEmpty(listYy)) {
                    List<ShuchengFgjYyxxDTO> shuchengFgjYyxxDTOS = new ArrayList<>();
                    for (BdcQl bdcQl : listYy) {
                        BdcYyDO bdcYyDO = (BdcYyDO) bdcQl;
                        ShuchengFgjYyxxDTO shuchengFgjYyxxDTO = new ShuchengFgjYyxxDTO();
                        BeanUtils.copyProperties(bdcYyDO, shuchengFgjYyxxDTO);
                        shuchengFgjYyxxDTO.setBdcdybh(bdcYyDO.getBdcdywybh());
                        shuchengFgjYyxxDTO.setQszt(StringToolUtils.convertBeanPropertyValueOfZd(bdcYyDO.getQszt(), zdMap.get("qszt")));
                        shuchengFgjYyxxDTO.setCqly(bdcXmFbDO.getCqly());
                        // 查询权利人（异议申请人 证件号）
                        List<BdcQlrDO> qlrList = commonService.listBdcQlrByXmid(bdcYyDO.getXmid(), Constants.QLRLB_QLR);
                        if (CollectionUtils.isNotEmpty(qlrList)) {
                            shuchengFgjYyxxDTO.setYysqr(CommonUtil.wmString(qlrList, "getQlrmc", ","));
                            shuchengFgjYyxxDTO.setYysqrzjhm(CommonUtil.wmString(qlrList, "getZjh", ","));
                        }
                        shuchengFgjYyxxDTOS.add(shuchengFgjYyxxDTO);
                    }
                    shuchengFgjYsfDjxxDTO.setYyxx(shuchengFgjYyxxDTOS);
                }

                //锁定信息
                if (CollectionUtils.isNotEmpty(sdxxList)) {
                    List<ShuchengFgjSdxxDTO> shuchengFgjSdxxDTOS = new ArrayList<>();
                    for (FwqlCxResponseSdxx fwqlCxResponseSdxx : sdxxList) {
                        ShuchengFgjSdxxDTO shuchengFgjSdxxDTO = new ShuchengFgjSdxxDTO();
                        BeanUtils.copyProperties(fwqlCxResponseSdxx, shuchengFgjSdxxDTO);
                        shuchengFgjSdxxDTOS.add(shuchengFgjSdxxDTO);
                    }
                    shuchengFgjYsfDjxxDTO.setSdxx(shuchengFgjSdxxDTOS);
                }


                //抵押信息
                List<BdcQl> bdcQls = new ArrayList<>();
                if (StringUtils.isNotBlank(qlid) && CollectionUtils.isNotEmpty(listDyaq)) {
                    bdcQls = listDyaq;
                } else if (StringUtils.isBlank(qlid) && CollectionUtils.isNotEmpty(listYg)){
                    bdcQls = listYg;
                }
                if (CollectionUtils.isNotEmpty(bdcQls)) {
                    //如果有产权，则用抵押权信息
                    List<ShuchengFgjDyxxDTO> shuchengFgjDyxxDTOS = new ArrayList<>();
                    for (BdcQl bdcQl : bdcQls) {
                        ShuchengFgjDyxxDTO shuchengFgjDyxxDTO = new ShuchengFgjDyxxDTO();
                        BeanUtils.copyProperties(bdcQl,shuchengFgjDyxxDTO);
                        BdcZsQO bdcZsQO = new BdcZsQO();
                        bdcZsQO.setXmid(bdcQl.getXmid());
                        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);
                        if (CollectionUtils.isNotEmpty(bdcZsDOS)) {
                            BdcZsDO bdcZsDO = bdcZsDOS.get(0);
                            shuchengFgjDyxxDTO.setBdcdjzmh(bdcZsDO.getBdcqzh());
                        }
                        shuchengFgjDyxxDTO.setCqzh(bdcqzh);
                        shuchengFgjDyxxDTO.setQszt(StringToolUtils.convertBeanPropertyValueOfZd(bdcQl.getQszt(), zdMap.get("qszt")));
                        shuchengFgjDyxxDTO.setCqly(bdcXmFbDO.getCqly());
                        //抵押金额
                        if(bdcQl instanceof BdcDyaqDO) {
                            BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) bdcQl;
                            shuchengFgjDyxxDTO.setBdcdybh(bdcDyaqDO.getBdcdywybh());
                            shuchengFgjDyxxDTO.setDyfs(StringToolUtils.convertBeanPropertyValueOfZd(bdcDyaqDO.getDyfs(), zdMap.get("dyfs")));
                            if ("1".equals(bdcDyaqDO.getDyfs().toString())) {
                                //一般抵押，被担保主债权数额
                                shuchengFgjDyxxDTO.setDyje(bdcDyaqDO.getBdbzzqse());
                            } else {
                                //最高额抵押 最高债权确定数额
                                shuchengFgjDyxxDTO.setDyje(bdcDyaqDO.getZgzqqdse());
                            }
                            shuchengFgjDyxxDTO.setDykssj(bdcDyaqDO.getZwlxqssj());
                            shuchengFgjDyxxDTO.setDyjssj(bdcDyaqDO.getZwlxjssj());
                        } else if(bdcQl instanceof BdcYgDO){
                            BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
                            shuchengFgjDyxxDTO.setBdcdybh(bdcYgDO.getBdcdywybh());
                            shuchengFgjDyxxDTO.setDyfs(StringToolUtils.convertBeanPropertyValueOfZd(bdcYgDO.getDyfs(), zdMap.get("dyfs")));
                            shuchengFgjDyxxDTO.setDyje(bdcYgDO.getJyje());
                            shuchengFgjDyxxDTO.setDykssj(bdcYgDO.getZwlxqssj());
                            shuchengFgjDyxxDTO.setDyjssj(bdcYgDO.getZwlxjssj());
                        }
                        // 查询权利人（异议申请人 证件号）
                        List<BdcQlrDO> qlrList = commonService.listBdcQlrByXmid(bdcQl.getXmid(), Constants.QLRLB_QLR);
                        List<BdcQlrDO> ywrList = commonService.listBdcQlrByXmid(bdcQl.getXmid(), Constants.QLRLB_YWR);
                        if (CollectionUtils.isNotEmpty(qlrList)) {
                            shuchengFgjDyxxDTO.setDyqr(CommonUtil.wmString(qlrList, "getQlrmc", ","));
                        }
                        if (CollectionUtils.isNotEmpty(ywrList)) {
                            shuchengFgjDyxxDTO.setDyr(CommonUtil.wmString(ywrList, "getQlrmc", ","));
                        }
                        shuchengFgjDyxxDTOS.add(shuchengFgjDyxxDTO);
                    }
                    shuchengFgjYsfDjxxDTO.setDyxx(shuchengFgjDyxxDTOS);
                }

                //预告
                if (StringUtils.isNotBlank(qlid) &&CollectionUtils.isNotEmpty(listYg)) {
                    List<ShuchengFgjYgxxDTO> shuchengFgjYgxxDTOS = new ArrayList<>();
                    for (BdcQl bdcQl : listYg) {
                        BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
                        ShuchengFgjYgxxDTO shuchengFgjYgxxDTO = new ShuchengFgjYgxxDTO();
                        BeanUtils.copyProperties(bdcYgDO,shuchengFgjYgxxDTO);
                        BdcZsQO bdcZsQO = new BdcZsQO();
                        bdcZsQO.setXmid(bdcYgDO.getXmid());
                        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);
                        if (CollectionUtils.isNotEmpty(bdcZsDOS)) {
                            BdcZsDO bdcZsDO = bdcZsDOS.get(0);
                            shuchengFgjYgxxDTO.setYgdjzmh(bdcZsDO.getBdcqzh());
                        }
                        shuchengFgjYgxxDTO.setYgdjzl(StringToolUtils.convertBeanPropertyValueOfZd(bdcYgDO.getYgdjzl(), zdMap.get("ygdjzl")));
                        shuchengFgjYgxxDTO.setZwlxqssj(bdcYgDO.getZwlxqssj());
                        shuchengFgjYgxxDTO.setZwlxjssj(bdcYgDO.getZwlxjssj());
                        // 查询权利人（异议申请人 证件号）
                        List<BdcQlrDO> qlrList = commonService.listBdcQlrByXmid(bdcYgDO.getXmid(), Constants.QLRLB_QLR);
                        if (CollectionUtils.isNotEmpty(qlrList)) {
                            shuchengFgjYgxxDTO.setQlr(CommonUtil.wmString(qlrList, "getQlrmc", ","));
                        }
                        shuchengFgjYgxxDTOS.add(shuchengFgjYgxxDTO);
                    }
                    shuchengFgjYsfDjxxDTO.setYgxx(shuchengFgjYgxxDTOS);
                }
                shuchengFgjYsfDjxxDTOS.add(shuchengFgjYsfDjxxDTO);
            }
        } else {
            return ExchangeDsfCommonResponse.fail("参数不能为空！");
        }

        ExchangeDsfCommonResponse<List<ShuchengFgjYsfDjxxDTO>> ok = ExchangeDsfCommonResponse.ok(shuchengFgjYsfDjxxDTOS);
        LOGGER.info("{}", JSON.toJSONString(ok));
        return ok;
    }


    /**
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @param paramJob 规则验证的参数obj
     * @return 存量房合同信息
     * @description 规则验证，登记获取房产存量房备案信息
     */
    public Object getClfHtxxByXmid(JSONObject paramJob){
        if (null != paramJob) {
            List<Map<String,String>> mapList = bdcXmMapper.getQlrAndBdcqzhByXmid((String)paramJob.get("xmid"));
            if (CollectionUtils.isNotEmpty(mapList)) {
                for (Map<String,String> map : mapList) {
                    Object obj =  getBaxxByZjhAndCqzh(map.get("ZJH"), map.get("BDCQZH"), map.get("YXTCQZH"), "fgj_GetContract");
                    if (getBaxxByZjhAndCqzh(obj)) {
                        return obj;
                    }
                }
            }
        }
        return new JSONObject();
    }

    /**
     * 查询条件为产权证号、权利人名称、权利人证件号，三个查询条件必须为组合查询，
     * 如产权证号+权利人名称，产权证号+权利人证件号，权利人名称+权利人证件号，无论那种组合，权利人名称与权利人证件号为精确查询，
     * 产权证号为模糊查询，也可只输入产权证号的数字部分
     *
     * @param clfHyxxDTO
     * @return Boolean Boolean
     * @Date 2022/4/6
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public Boolean checkparam(ShuchengFgjClfHyxxDTO clfHyxxDTO) {
        if (StringUtils.isNotBlank(clfHyxxDTO.getCQZBH()) && StringUtils.isNotBlank(clfHyxxDTO.getCQRXM())) {
            return true;
        } else if (StringUtils.isNotBlank(clfHyxxDTO.getCQZBH()) && StringUtils.isNotBlank(clfHyxxDTO.getCQRZJHM())) {
            return true;
        } else if (StringUtils.isNotBlank(clfHyxxDTO.getCQRXM()) && StringUtils.isNotBlank(clfHyxxDTO.getCQRZJHM())) {
            return true;
        }
        return false;

    }

    /**
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @param zjh 证件号
     * @param cqzh 产权证号
     * @param yxtcqzh 原系统产权证号
     * @param beanName
     * @return 存量房合同信息
     * @description 根据zjh+cqzh或者zjh+yxtcqzh查询存量房合同信息
     */
    public Object getBaxxByZjhAndCqzh(String zjh,String cqzh,String yxtcqzh,String beanName){
        Object obj = getBaxxByZjhAndCqzh(zjh, cqzh, beanName);
        if (getBaxxByZjhAndCqzh(obj)) {
            return obj;
        }

        return getBaxxByZjhAndCqzh(zjh,yxtcqzh, beanName);
    }


    /**
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @param zjh 证件号
     * @param cqzh 产权证号
     * @param beanName
     * @return  存量房合同信息
     * @description 调房管局接口查询存量房合同信息
     */
    public Object getBaxxByZjhAndCqzh(String zjh, String cqzh, String beanName) {
        if (StringUtils.isNoneBlank(zjh, cqzh, beanName)) {
//            Pattern pattern = Pattern.compile("[^0-9]");
            Matcher matcher = pattern.matcher(cqzh);
            cqzh = matcher.replaceAll("").trim();
            Map paramMap = new HashMap();
            paramMap.put("zjh", zjh);
            paramMap.put("cqzh", cqzh);
            return exchangeBeanRequestService.request(beanName, paramMap);
        }
        return null;
    }

    /**
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @param obj 接口返回的存量房合同信息
     * @return  是否有交易信息
     * @description 判断存量房合同信息是否存在交易信息
     */
    private boolean getBaxxByZjhAndCqzh(Object obj){
        boolean flag = false;
        if (null != obj) {
            JSONObject job = JSON.parseObject(JSON.toJSONString(obj));
            if (null != job) {
                JSONObject bdcSlJyxxDO = job.getJSONObject("bdcSlJyxx");
                if (null != bdcSlJyxxDO && StringUtils.isNotBlank(bdcSlJyxxDO.getString("htbh"))) {
                    flag = true;
                }
            }
        }

        return flag;
    }

}
