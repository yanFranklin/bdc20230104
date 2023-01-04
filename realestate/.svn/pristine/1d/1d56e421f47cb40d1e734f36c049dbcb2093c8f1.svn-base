package cn.gtmap.realestate.exchange.web.rest.nantong;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.exchange.core.dto.nantong.jsyh.slfk.*;
import cn.gtmap.realestate.exchange.core.enums.CCbSlztEnum;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import cn.gtmap.realestate.exchange.util.XmlUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/3/9
 * @description 建设银行推送受理信息controller
 */
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/tsyhxx")
public class JsyhTsslxxRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsyhTsslxxRestController.class);

    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    ExchangeBeanRequestService exchangeBeanRequestService;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Value("#{${yhtsxx.beanId:{'NTCCB': 'jsyh_slxx'}}}")
    private Map<String, String> yhtsBeanid;


    /**
     * 推送南通建设银行相关受理信息
     *
     * @param processId
     * @return
     * @Date 2022/3/9
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/slxxByGzlslid")
    public void NtCCBSlxx(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "slzt", required = false) String slzt) {
        //获取字典项
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> xmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        //组织list 信息
        List<JsyhDataModel> dataModels = new ArrayList<>();
        JsyhDataModel dataModel = new JsyhDataModel();
        if (CollectionUtils.isNotEmpty(xmDOList)) {
            //外层组织单个实体数据
            dataModel.setIssuCtf_Ahr(xmDOList.get(0).getDjjg());

            if (StringUtils.isNotBlank(slzt)) {
                dataModel.setDealW_St(CCbSlztEnum.getDescriptionBySlzt(slzt));
            } else {
                //没有slzt，表示从工作流触发，直接为登簿办结状态
                dataModel.setDealW_St("11");
            }

            dataModel.setDealW_Psn_Nm(xmDOList.get(0).getDbr());
            dataModel.setAcpt_No(xmDOList.get(0).getSlbh());
            //根据xmid查询证书信息
            List<BdcZsDO> zsDOList = bdcZsFeignService.queryBdcZsByXmid(xmDOList.get(0).getXmid());
            if (CollectionUtils.isNotEmpty(zsDOList)) {
                dataModel.setInfo_Inf(zsDOList.get(0).getFj());
                dataModel.setCtfn_Rght_Tp(String.valueOf(zsDOList.get(0).getQllx()));
                dataModel.setRght_Other_Inf(zsDOList.get(0).getQlqtzk());
            }
            List<Othr_Data> othr_dataList = new ArrayList<>();
            List<Mrtg_Psn_Inf> ywrList = new ArrayList<>();
            List<Mrtg_Wght_Psn_Inf> qlrList = new ArrayList<>();
            for (BdcXmDO xmDO : xmDOList) {

                Othr_Data othr_data = new Othr_Data();
                othr_data.setRealEst_Bsn_No(xmDO.getBdcdyh());
                othr_data.setRg_Dt(DateFormatUtils.format(xmDO.getDjsj(), "yyyy-MM-dd HH:mm:ss"));
                othr_data.setCrt_Tm(DateFormatUtils.format(xmDO.getSlsj(), "yyyy-MM-dd HH:mm:ss"));
                //根据xmid查询证书信息
//                List<BdcZsDO> zsDOList1 = bdcZsFeignService.queryBdcZsByXmid(xmDO.getXmid());?
                if (CollectionUtils.isNotEmpty(zsDOList)) {
                    othr_data.setRealEst_Ctfn_No(zsDOList.get(0).getBdcqzh());
                    othr_data.setRealEst_Ecb_No(zsDOList.get(0).getBdcqzh());
                    othr_data.setDistrict_Cd(zsDOList.get(0).getQxdm());
                }
                Mrtg_Psn_Inf mrtg_psn_inf = new Mrtg_Psn_Inf();
                Mrtg_Wght_Psn_Inf mrtg_wght_psn_inf = new Mrtg_Wght_Psn_Inf();
                //根据xmid查抵押权人
                List<BdcQlrDO> qlrDOList = bdcQlrFeignService.listAllBdcQlr(xmDO.getGzlslid(), CommonConstantUtils.QLRLB_QLR, "");
                if (CollectionUtils.isNotEmpty(qlrDOList)) {
                    for (BdcQlrDO qlrDO : qlrDOList) {
                        mrtg_wght_psn_inf.setMrtg_Wght_Psn_Nm(qlrDO.getQlrmc());
                        mrtg_wght_psn_inf.setMrtg_Wght_Psn_Crdt_Tp(StringToolUtils.convertBeanPropertyValueOfZd(qlrDO.getZjzl(), zdMap.get("zjzl")));
                        mrtg_wght_psn_inf.setMrtg_Wght_Psn_Crdt_No(qlrDO.getZjh());
                        mrtg_wght_psn_inf.setMrtg_Wght_Psn_Ctc_Tel(qlrDO.getDh());
                        mrtg_wght_psn_inf.setMrtg_Wght_Psn_Tp(StringToolUtils.convertBeanPropertyValueOfZd(qlrDO.getQlrlx(), zdMap.get("qlrlx")));
                        mrtg_wght_psn_inf.setMrtg_Wght_Psn_Lgl_Tbl_Or_Pnp(qlrDO.getDlrmc());
                        mrtg_wght_psn_inf.setMrtg_Wght_Psn_Adr(qlrDO.getTxdz());
                        mrtg_wght_psn_inf.setMrtg_Wght_Psn_Mnplt_St("0");

                    }
                }
                //根据xmid查抵押人
                List<BdcQlrDO> ywrDOList = bdcQlrFeignService.listAllBdcQlr(xmDO.getGzlslid(), CommonConstantUtils.QLRLB_YWR, "");
                if (CollectionUtils.isNotEmpty(ywrDOList)) {
                    for (BdcQlrDO ywrDo : qlrDOList) {
                        mrtg_psn_inf.setMrtg_Psn_Nm(ywrDo.getQlrmc());
                        mrtg_psn_inf.setMrtg_Psn_Crdt_Tp(StringToolUtils.convertBeanPropertyValueOfZd(ywrDo.getZjzl(), zdMap.get("zjzl")));
                        mrtg_psn_inf.setMrtg_Psn_Crdt_No(ywrDo.getZjh());
                        mrtg_psn_inf.setMrtg_Psn_Ctc_Tel(ywrDo.getDh());
                        mrtg_psn_inf.setMrtg_Psn_Tp(StringToolUtils.convertBeanPropertyValueOfZd(ywrDo.getQlrlx(), zdMap.get("qlrlx")));
                        mrtg_psn_inf.setMrtg_Psn_Lgl_Tbl_Or_Pnp(ywrDo.getDlrmc());
                        mrtg_psn_inf.setMrtg_Psn_Adr(ywrDo.getTxdz());
                        mrtg_psn_inf.setMrtg_Psn_Mnplt_St("0");

                    }
                }
                ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(othr_data);
                ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(mrtg_psn_inf);
                ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(mrtg_wght_psn_inf);


                othr_dataList.add(othr_data);
                qlrList.add(mrtg_wght_psn_inf);
                ywrList.add(mrtg_psn_inf);
            }

            dataModel.setOthr_data(othr_dataList);
            dataModel.setMrtg_Psn_Inf(ywrList);
            dataModel.setMrtg_Wght_Psn_Inf(qlrList);
            ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(dataModel);

            //开始组织xml
            try {
                String xml = XmlUtils.getXmlStrByObjectGBK(dataModel, JsyhDataModel.class);
//                String xml = XmlEntityCommonConvertUtil.entityToXmlStatic(xmlModel);
               /* String replace = "xsi:nil=\\\"true\\\" xmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\"";
                xml = xml.replaceAll("\\s*xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "");*/
                LOGGER.info("推送南通建设银行相关受理信息xml===={}", xml);
                Map data = new HashMap();
                data.put("areaCode", xmDOList.get(0).getQxdm());
                data.put("data", xml);
                //根据slbh特征，判断掉用不同银行，调用接口推送南通CCB
                String beanid = "";
                for (String yhmc : yhtsBeanid.keySet()) {
                    if (xmDOList.get(0).getSlbh().contains(yhmc)) {
                        Object key;
                        beanid = (String) yhtsBeanid.get(yhmc);
                    }
                }

                Object object = exchangeBeanRequestService.request(beanid, data);
                LOGGER.info("推送南通建设银行相关接口返回信息xml===={}", object.toString());
            } catch (Exception e) {
                LOGGER.info("推送南通建设银行相关受理信息,转换xml报错！");
                e.printStackTrace();
            }

        }
    }


}
