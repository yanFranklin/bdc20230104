package cn.gtmap.realestate.exchange.service.impl.inf.bengbu;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.register.BdcdyZtDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.exchange.core.dto.bengbu.djxx.DjxxCommonResponse;
import cn.gtmap.realestate.exchange.core.dto.bengbu.djxx.qsdjxx.response.*;
import cn.gtmap.realestate.exchange.core.dto.bengbu.djxx.DjxxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.bengbu.djxx.ywxqxx.response.*;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.bengbu.BengbuDjxxService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @version 1.0  2020-03-25
 * @description 读取不动产登记信息相关服务
 */
@Service
public class BengbuDjxxServiceImpl implements BengbuDjxxService {

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    BdcQllxFeignService bdcQllxFeignService;

    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    BdcdjMapper bdcdjMapper;

    @Autowired
    private DozerBeanMapper exchangeDozerMapper;


    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BengbuDjxxServiceImpl.class);

    /**
     * @description 获取当前请求数据项目表的qxdm
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/12/22 16:51
     * @param djxxRequestDTO
     * @return DjxxCommonResponse
     */
    @Override
    public String getQxdm(DjxxRequestDTO djxxRequestDTO) {
        if (!CheckParameter.checkAnyParameter(djxxRequestDTO, "bdcqzh", "bdcdyh")) {
            LOGGER.error("不动产权证号或不动产单元号为空");
            return null;
        }
        List<BdcXscqxxDTO> bdcXscqxxDTOList = bdcdjMapper.listFwfyxx(djxxRequestDTO);
        if (CollectionUtils.isEmpty(bdcXscqxxDTOList)) {
            LOGGER.error("蚌埠登记信息加密获取qxdm请求参数{}，未查询到项目数据", djxxRequestDTO);
            return null;
        }
        LOGGER.info("蚌埠登记信息加密获取qxdm请求参数{}，qxdm为{}", djxxRequestDTO, bdcXscqxxDTOList.get(0).getQxdm());
        return bdcXscqxxDTOList.get(0).getQxdm();
    }

    /**
     * @description 获取权属登记信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/12/22 16:40
     * @param djxxRequestDTO
     * @return DjxxCommonResponse
     */
    @Override
    public DjxxCommonResponse getQsdjxx(DjxxRequestDTO djxxRequestDTO) {
        if (!CheckParameter.checkAnyParameter(djxxRequestDTO, "bdcqzh", "bdcdyh")) {
            return DjxxCommonResponse.fail("不动产权证号或不动产单元号为空");
        }
        BdcQsdjxxResponseDTO bdcQsdjxxResponse = new BdcQsdjxxResponseDTO();

        // 房源信息查询
        List<BdcXscqxxDTO> bdcXscqxxDTOList = bdcdjMapper.listFwfyxx(djxxRequestDTO);
        LOGGER.info("蚌埠获取权属登记信息的请求参数{}，查询结果为{}", djxxRequestDTO, bdcXscqxxDTOList);
        if (CollectionUtils.isEmpty(bdcXscqxxDTOList)) {
            return DjxxCommonResponse.ok();
        }

        BdcXscqxxDTO bdcXscqxxDTO = bdcXscqxxDTOList.get(0);
        BdcFyxxResponseDTO bdcFyxxResponseDTO = exchangeDozerMapper.map(bdcXscqxxDTO, BdcFyxxResponseDTO.class, "bb_djxx_bdcfyxx");

        String bdcdyh = bdcXscqxxDTO.getBdcdyh();

        // 权属状态查询
        BdcQsztResponseDTO bdcQsztResponseDTO = new BdcQsztResponseDTO();
        BdcdyZtDTO bdcdyZtDTO = bdcdyZtFeignService.queryBdcdyZt(bdcdyh);
        if (Objects.nonNull(bdcdyZtDTO)) {
            if (CommonConstantUtils.DJLX_ZYDJ_DM.equals(bdcXscqxxDTO.getDjlx()) && Arrays.asList(CommonConstantUtils.QLLX_FDCQ).contains(bdcXscqxxDTO.getQllx())) {
                bdcQsztResponseDTO.setOwnershiptransfer("1");
            } else {
                bdcQsztResponseDTO.setOwnershiptransfer("");
            }
            bdcQsztResponseDTO.setHeraldDebt(Boolean.TRUE.equals(bdcdyZtDTO.getYdya()) ? "1" : "");
            bdcQsztResponseDTO.setDebt(Boolean.TRUE.equals(bdcdyZtDTO.getDya()) ? "1" : "");
            bdcQsztResponseDTO.setSeal(Boolean.TRUE.equals(bdcdyZtDTO.getCf()) ? "1" : "");
            bdcQsztResponseDTO.setHerald(Boolean.TRUE.equals(bdcdyZtDTO.getYg()) ? "1" : "");
            bdcQsztResponseDTO.setDissent(Boolean.TRUE.equals(bdcdyZtDTO.getYy()) ? "1" : "");
            bdcQsztResponseDTO.setLock(Boolean.TRUE.equals(bdcdyZtDTO.getSd()) ? "1" : "");
            bdcQsztResponseDTO.setLive(Boolean.TRUE.equals(bdcdyZtDTO.getJzq()) ? "1" : "");
            if (CommonConstantUtils.AJZT_ZB_DM.equals(bdcXscqxxDTO.getAjzt())) {
                bdcQsztResponseDTO.setIsHandling("1");
            } else {
                bdcQsztResponseDTO.setIsHandling("");
            }

        }
        bdcFyxxResponseDTO.setHouseRightStatusPro(bdcQsztResponseDTO);

        List<BdcFyxxResponseDTO> transHousePros = new ArrayList<>(1);
        transHousePros.add(bdcFyxxResponseDTO);
        bdcQsdjxxResponse.setTransHousePros(transHousePros);

        // 人员信息查询
        String xmid = bdcXscqxxDTO.getXmid();
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            List<BdcQlrResponseDTO> transEntPros = new ArrayList<>(bdcQlrDOList.size());
            bdcQlrDOList.forEach(bdcQlrDO -> {
                BdcQlrResponseDTO bdcQlrResponseDTO = exchangeDozerMapper.map(bdcQlrDO, BdcQlrResponseDTO.class, "bb_djxx_bdcryxx");
                transEntPros.add(bdcQlrResponseDTO);
            });
            bdcQsdjxxResponse.setTransEntPros(transEntPros);
        }
        return DjxxCommonResponse.ok(bdcQsdjxxResponse);
    }

    @Override
    public DjxxCommonResponse getYwxqxx(DjxxRequestDTO djxxRequestDTO) {
        if (Objects.isNull(djxxRequestDTO) || StringUtils.isBlank(djxxRequestDTO.getBdcdyh())) {
            return DjxxCommonResponse.fail("不动产单元号为空");
        }
        BdcYwxqxxResponseDTO bdcYwxqxxResponseDTO = new BdcYwxqxxResponseDTO();
        List<BdcSyqxxDTO> syq = new ArrayList<>(4);
        List<BdcDyxxDTO> dy = new ArrayList<>(4);
        List<BdcCfxxDTO> cf = new ArrayList<>(4);
        List<BdcYgxxDTO> yg = new ArrayList<>(4);
        List<BdcYyxxDTO> yy = new ArrayList<>(4);

        String bdcdyh = djxxRequestDTO.getBdcdyh();
        //字典信息
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        //查询所有权
        List<BdcQl> listFdcq = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_FDCQ_DM.toString());
        if (CollectionUtils.isNotEmpty(listFdcq)) {
            for (BdcQl bdcQl: listFdcq) {
                BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                String xmid = bdcFdcqDO.getXmid();
                BdcXmDO bdcXmDO = bdcXmFeignService.queryBdcXmByXmid(xmid);
                if (Objects.nonNull(bdcXmDO)) {
                    BdcSyqxxDTO bdcSyqxxDTO = new BdcSyqxxDTO();
                    bdcSyqxxDTO.setBdcywh(bdcXmDO.getSlbh());
                    bdcSyqxxDTO.setYwsxmc(bdcXmDO.getGzldymc());
                    bdcSyqxxDTO.setBdcqzh(bdcXmDO.getBdcqzh());
                    // 查询权利人
                    List<BdcQlrDO> qlrList = commonService.listBdcQlrByXmid(xmid, Constants.QLRLB_QLR);
                    if (CollectionUtils.isNotEmpty(qlrList)) {
                        bdcSyqxxDTO.setQlrxm(CommonUtil.wmString(qlrList, "getQlrmc", ","));
                        bdcSyqxxDTO.setQlrzjhm(CommonUtil.wmString(qlrList, "getZjh", ","));
                        bdcSyqxxDTO.setLxfs(CommonUtil.wmString(qlrList, "getDh", ","));
                    }
                    bdcSyqxxDTO.setDbsj(null != bdcXmDO.getDjsj() ? DateUtils.formateTime(bdcXmDO.getDjsj(), DateUtils.DATE_FORMAT_5) : "");
                    syq.add(bdcSyqxxDTO);
                }
            }
        }

        //查询抵押
        List<BdcQl> listDyaq = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_DYAQ_DM.toString());
        if (CollectionUtils.isNotEmpty(listDyaq)) {
            for (BdcQl bdcQl: listDyaq) {
                BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) bdcQl;
                String xmid = bdcDyaqDO.getXmid();
                BdcXmDO bdcXmDO = bdcXmFeignService.queryBdcXmByXmid(xmid);
                if (Objects.nonNull(bdcXmDO)) {
                    BdcDyxxDTO bdcDyxxDTO = new BdcDyxxDTO();
                    bdcDyxxDTO.setBdcywh(bdcXmDO.getSlbh());
                    bdcDyxxDTO.setYwsxmc(bdcXmDO.getGzldymc());
                    bdcDyxxDTO.setDylx(StringToolUtils.convertBeanPropertyValueOfZd(bdcDyaqDO.getDyfs(), zdMap.get("dyfs")));
                    if ("1".equals(bdcDyaqDO.getDyfs().toString())) {
                        //一般抵押，被担保主债权数额
                        bdcDyxxDTO.setDyzqse(bdcDyaqDO.getBdbzzqse() != null ? String.valueOf(bdcDyaqDO.getBdbzzqse()) : "0");
                    } else {
                        //最高额抵押 最高债权确定数额
                        bdcDyxxDTO.setDyzqse(bdcDyaqDO.getZgzqqdse() != null ? String.valueOf(bdcDyaqDO.getZgzqqdse()) : "0");
                    }
                    // 查询权利人和义务人
                    List<BdcQlrDO> qlrList = commonService.listBdcQlrByXmid(bdcQl.getXmid(), Constants.QLRLB_QLR);
                    List<BdcQlrDO> ywrList = commonService.listBdcQlrByXmid(bdcQl.getXmid(), Constants.QLRLB_YWR);
                    if (CollectionUtils.isNotEmpty(qlrList)) {
                        bdcDyxxDTO.setDyqr(CommonUtil.wmString(qlrList, "getQlrmc", ","));
                    }
                    if (CollectionUtils.isNotEmpty(ywrList)) {
                        bdcDyxxDTO.setDyr(CommonUtil.wmString(ywrList, "getQlrmc", ","));
                    }
                    bdcDyxxDTO.setZwr(bdcDyaqDO.getZwr());
                    String dykssj = bdcDyaqDO.getZwlxqssj() != null ? DateUtils.formateTime(bdcDyaqDO.getZwlxqssj(), DateUtils.DATE_FORMAT_5) : "";
                    String dyjssj = bdcDyaqDO.getZwlxjssj() != null ? DateUtils.formateTime(bdcDyaqDO.getZwlxjssj(), DateUtils.DATE_FORMAT_5) : "";
                    bdcDyxxDTO.setDyqzsj(dykssj + CommonConstantUtils.ZF_YW_HG + dyjssj);
                    dy.add(bdcDyxxDTO);
                }
            }
        }

        //查询查封
        List<BdcQl> listCf = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_CF.toString());
        if (CollectionUtils.isNotEmpty(listCf)) {
            for (BdcQl bdcQl: listCf) {
                BdcCfDO bdcCfDO = (BdcCfDO) bdcQl;
                String xmid = bdcCfDO.getXmid();
                BdcXmDO bdcXmDO = bdcXmFeignService.queryBdcXmByXmid(xmid);
                if (Objects.nonNull(bdcXmDO)) {
                    BdcCfxxDTO bdcCfxxDTO = new BdcCfxxDTO();
                    bdcCfxxDTO.setBdcywh(bdcXmDO.getSlbh());
                    bdcCfxxDTO.setYwsxmc(bdcXmDO.getGzldymc());
                    bdcCfxxDTO.setCflx(StringToolUtils.convertBeanPropertyValueOfZd(bdcCfDO.getCflx(), zdMap.get("cflx")));
                    String cfkssj = bdcCfDO.getCfqssj() != null ? DateUtils.formateTime(bdcCfDO.getCfqssj(), DateUtils.DATE_FORMAT_5) : "";
                    String cfjssj = bdcCfDO.getCfjssj() != null ? DateUtils.formateTime(bdcCfDO.getCfjssj(), DateUtils.DATE_FORMAT_5) : "";
                    bdcCfxxDTO.setCfqzsj(cfkssj + CommonConstantUtils.ZF_YW_HG + cfjssj);
                    bdcCfxxDTO.setSqzxr(bdcCfDO.getZxsqr());
                    bdcCfxxDTO.setBzxr(bdcCfDO.getBzxr());
                    bdcCfxxDTO.setZxdw(bdcCfDO.getCfjg());
                    bdcCfxxDTO.setWsh(bdcCfDO.getCfwh());
                    cf.add(bdcCfxxDTO);
                }
            }
        }

        //查询预告
        List<BdcQl> listYg = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_YG_DM.toString());
        if (CollectionUtils.isNotEmpty(listYg)) {
            for (BdcQl bdcQl: listYg) {
                BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
                String xmid = bdcYgDO.getXmid();
                Integer ygdjzl = bdcYgDO.getYgdjzl();
                BdcXmDO bdcXmDO = bdcXmFeignService.queryBdcXmByXmid(xmid);
                if (Objects.nonNull(bdcXmDO)) {
                    // 预告
                    if (Arrays.asList(CommonConstantUtils.YG_YGDJZL_ARR).contains(ygdjzl)) {
                        BdcYgxxDTO bdcYgxxDTO = new BdcYgxxDTO();
                        bdcYgxxDTO.setBdcywh(bdcXmDO.getSlbh());
                        bdcYgxxDTO.setYwsxmc(bdcXmDO.getGzldymc());
                        // 查询权利人
                        List<BdcQlrDO> qlrList = commonService.listBdcQlrByXmid(xmid, Constants.QLRLB_QLR);
                        if (CollectionUtils.isNotEmpty(qlrList)) {
                            bdcYgxxDTO.setQlrxm(CommonUtil.wmString(qlrList, "getQlrmc", ","));
                            bdcYgxxDTO.setQlrzjhm(CommonUtil.wmString(qlrList, "getZjh", ","));
                            bdcYgxxDTO.setLxfs(CommonUtil.wmString(qlrList, "getDh", ","));
                        }
                        bdcYgxxDTO.setDbsj(null != bdcXmDO.getDjsj() ? DateUtils.formateTime(bdcXmDO.getDjsj(), DateUtils.DATE_FORMAT_5) : "");
                        yg.add(bdcYgxxDTO);
                    } else if (Arrays.asList(CommonConstantUtils.YG_YGDJZL_YDY_ARR).contains(ygdjzl)) {
                        // 预抵押
                        BdcDyxxDTO bdcDyxxDTO = new BdcDyxxDTO();
                        bdcDyxxDTO.setBdcywh(bdcXmDO.getSlbh());
                        bdcDyxxDTO.setYwsxmc(bdcXmDO.getGzldymc());
                        bdcDyxxDTO.setDylx(StringToolUtils.convertBeanPropertyValueOfZd(bdcYgDO.getDyfs(), zdMap.get("dyfs")));
                        bdcDyxxDTO.setDyzqse(bdcYgDO.getJyje() != null ? String.valueOf(bdcYgDO.getJyje()) : "0");
                        // 查询权利人和义务人
                        List<BdcQlrDO> qlrList = commonService.listBdcQlrByXmid(bdcQl.getXmid(), Constants.QLRLB_QLR);
                        List<BdcQlrDO> ywrList = commonService.listBdcQlrByXmid(bdcQl.getXmid(), Constants.QLRLB_YWR);
                        if (CollectionUtils.isNotEmpty(qlrList)) {
                            bdcDyxxDTO.setDyqr(CommonUtil.wmString(qlrList, "getQlrmc", ","));
                        }
                        if (CollectionUtils.isNotEmpty(ywrList)) {
                            bdcDyxxDTO.setDyr(CommonUtil.wmString(ywrList, "getQlrmc", ","));
                            bdcDyxxDTO.setZwr(CommonUtil.wmString(ywrList, "getQlrmc", ","));
                        }
                        String dykssj = bdcYgDO.getZwlxqssj() != null ? DateUtils.formateTime(bdcYgDO.getZwlxqssj(), DateUtils.DATE_FORMAT_5) : "";
                        String dyjssj = bdcYgDO.getZwlxjssj() != null ? DateUtils.formateTime(bdcYgDO.getZwlxjssj(), DateUtils.DATE_FORMAT_5) : "";
                        bdcDyxxDTO.setDyqzsj(dykssj + CommonConstantUtils.ZF_YW_HG + dyjssj);
                        dy.add(bdcDyxxDTO);
                    }
                }
            }
        }

        //查询异议
        List<BdcQl> listYy = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_YY.toString());
        if (CollectionUtils.isNotEmpty(listYy)) {
            for (BdcQl bdcQl: listYy) {
                BdcYyDO bdcYyDO = (BdcYyDO) bdcQl;
                String xmid = bdcYyDO.getXmid();
                BdcXmDO bdcXmDO = bdcXmFeignService.queryBdcXmByXmid(xmid);
                if (Objects.nonNull(bdcXmDO)) {
                    BdcYyxxDTO bdcYyxxDTO = new BdcYyxxDTO();
                    bdcYyxxDTO.setBdcywh(bdcXmDO.getSlbh());
                    bdcYyxxDTO.setYwsxmc(bdcXmDO.getGzldymc());
                    bdcYyxxDTO.setYysx(bdcYyDO.getYysx());
                    // 查询权利人
                    List<BdcQlrDO> qlrList = commonService.listBdcQlrByXmid(xmid, Constants.QLRLB_QLR);
                    if (CollectionUtils.isNotEmpty(qlrList)) {
                        bdcYyxxDTO.setYysqr(CommonUtil.wmString(qlrList, "getQlrmc", ","));
                    }
                    bdcYyxxDTO.setYyyy(bdcYyDO.getYysx());
                    bdcYyxxDTO.setYysj(bdcYyDO.getYydjksrq() != null ? DateUtils.formateTime(bdcYyDO.getYydjksrq(), DateUtils.DATE_FORMAT_5) : "");
                    yy.add(bdcYyxxDTO);
                }
            }
        }
        bdcYwxqxxResponseDTO.setSyq(syq);
        bdcYwxqxxResponseDTO.setDy(dy);
        bdcYwxqxxResponseDTO.setCf(cf);
        bdcYwxqxxResponseDTO.setYg(yg);
        bdcYwxqxxResponseDTO.setYy(yy);

        return DjxxCommonResponse.ok(bdcYwxqxxResponseDTO);
    }
}
