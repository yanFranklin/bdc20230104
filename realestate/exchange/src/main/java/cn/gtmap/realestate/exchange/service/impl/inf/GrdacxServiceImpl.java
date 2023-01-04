package cn.gtmap.realestate.exchange.service.impl.inf;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcQjgldmQO;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwLjzFeginService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcPpFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDjbxxFeignService;
import cn.gtmap.realestate.common.util.CardNumberTransformation;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringUtil;
import cn.gtmap.realestate.exchange.core.bo.grdacx.*;
import cn.gtmap.realestate.exchange.core.dto.common.QueryQlRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.common.YgQlWithXmQlrDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getYgxx.request.GetYgxxRequestData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.request.GrdacxRequestBody;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.GrdacxService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-02
 * @description
 */
@Service(value = "standardGrdacx")
public class GrdacxServiceImpl implements GrdacxService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GrdacxServiceImpl.class);

    @Autowired
    private CommonService commonService;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Autowired
    private BdcDjbxxFeignService bdcDjbxxFeignService;

    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;

    @Value("${data.version:}")
    private String dataversion;

    //是否置空qlrlb参数
    @Value("${getYgxx.zkqlrlb: false}")
    private boolean zkqlrlb;

    //是否取szc参数
    @Value("${getwwsqcqzxx.szc: false}")
    private boolean szcqz;

    @Autowired
    private BdcXmfbFeignService bdcXmfbFeignService;


    @Autowired
    private BdcZsFeignService bdcZsFeignService;

    @Autowired
    private FwLjzFeginService fwLjzFeginService;

    @Autowired
    private FwHsFeignService fwHsFeignService;
    @Autowired
    private BdcdyFeignService bdcdyFeignService;

    @Autowired
    BdcPpFeignService bdcPpFeignService;

    /**
     * grdacx 当查询没有数据时 data 返回空arrayList
     *
     * @param grdacxRequestBody
     * @return
     */
    public GrdacxModel grdacxWithNullData(GrdacxRequestBody grdacxRequestBody) {
        GrdacxModel grdacxModel = grdacx(grdacxRequestBody);
        if (CollectionUtils.isEmpty(grdacxModel.getData())) {
            grdacxModel.setData(new ArrayList<>());
        }
        return grdacxModel;
    }

    /**
     * @param grdacxRequestBody
     * @return cn.gtmap.realestate.exchange.core.bo.grdacx.GrdacxModel
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description qlrmc qlrzjh
     */
    @Override
    public GrdacxModel grdacx(GrdacxRequestBody grdacxRequestBody) {

        LOGGER.info("grdacx查询参数为：{}", grdacxRequestBody.toString());
        GrdacxModel grdacxModel = new GrdacxModel();

        //58957 【蚌埠】产权证接口入参需求  houseid为蚌埠权籍特有字段，现要求用这个字段也能查
        if (StringUtils.isNotBlank(grdacxRequestBody.getHouseid())) {
            BdcdyResponseDTO bdcdyResponseDTO = bdcdyFeignService.queryBdcdyByHoseId(grdacxRequestBody.getLikeBdcdyh(), grdacxRequestBody.getHouseid(), "", "");
            if (null != bdcdyResponseDTO && StringUtils.isNotBlank(bdcdyResponseDTO.getBdcdyh())) {
                grdacxRequestBody.setBdcdyh(bdcdyResponseDTO.getBdcdyh());
            } else {
                LOGGER.info("未查询到权籍数据！houseid：{}", grdacxRequestBody.getHouseid());
                return grdacxModel;
            }
        }

        revertQlrZjh(grdacxRequestBody);
        // 房地产权
        if (BooleanUtils.toBoolean(grdacxRequestBody.getQueryFdcq())) {
            setFdcq(grdacxRequestBody, grdacxModel);
        }
        // 预告
        if (BooleanUtils.toBoolean(grdacxRequestBody.getQueryYg())) {
            setYg(grdacxRequestBody, grdacxModel);
        }
        //土地权利
        if (BooleanUtils.toBoolean(grdacxRequestBody.getQueryTdsyqAndJsydsyq())) {
            setTdql(grdacxRequestBody, grdacxModel);
        }
        // 限制权利
        setXzql(grdacxRequestBody, grdacxModel);
        // 单元号锁定数据
        setDysd(grdacxRequestBody, grdacxModel);
        //获取证书锁定数据
        setZssd(grdacxRequestBody, grdacxModel);
        return grdacxModel;
    }

    private void setTdql(GrdacxRequestBody grdacxRequestBody, GrdacxModel grdacxModel) {

        if (CheckParameter.checkAnyParameter(grdacxRequestBody, "qlrmc", "qlrzjh", "zl", "likeZl", "qlrmcmh", "slbh", "likeBdcdyh", "qlrmcmh", "qlrzjhmh",
                "likebdcqzh", "bdcqzhmh", "bdcqzh", "zhlsh", "ntbdcqzh", "bdcdybh", "xmid", "bdcdyh")) {
            List<GrdacxData> grdacxDataList = new ArrayList<>(10);
            List<BdcTdsyqDO> tdsyqDOList = bdcdjMapper.queryBdcTdsyq(grdacxRequestBody);
            if (CollectionUtils.isNotEmpty(tdsyqDOList)) {
                for (BdcTdsyqDO tdsyqDO : tdsyqDOList) {
                    BdcTdsyqQlxxBO bdcTdsyqQlxxBO = new BdcTdsyqQlxxBO();
                    bdcTdsyqQlxxBO.setQlxx(tdsyqDO);
                    if (StringUtils.isNotBlank(tdsyqDO.getXmid()) && grdacxRequestBody.getCqWithXm()) {
                        bdcTdsyqQlxxBO.setBdcXmDO(commonService.getBdcXmByXmid(tdsyqDO.getXmid()));
                        // 查询权利人
                        List<BdcQlrDO> bdcQlrList = commonService.listBdcQlrByXmid(tdsyqDO.getXmid(), Constants.QLRLB_QLR);
                        if (CollectionUtils.isNotEmpty(bdcQlrList)) {
                            bdcTdsyqQlxxBO.setBdcQlrDOList(bdcQlrList);
                        }
                    }
                    GrdacxData grdacxData = new GrdacxData();
                    // 查询宗地基本信息
                    if (StringUtils.isNotBlank(tdsyqDO.getBdcdyh())) {
                        BdcBdcdjbZdjbxxDO zdjbxxDO = bdcDjbxxFeignService.queryBdcBdcdjbZdjbxx(tdsyqDO.getBdcdyh());
                        bdcTdsyqQlxxBO.setZdjbxxDO(zdjbxxDO);
                        if(null != zdjbxxDO){
                            grdacxData.setQjzl(zdjbxxDO.getZl());
                        }
                    }

                    grdacxData.setBdcTdsyqQlxxBO(bdcTdsyqQlxxBO);
                    //根据不动产单元号获取权籍库的坐落
                    grdacxDataList.add(grdacxData);
                }
            } else {
                List<BdcJsydsyqDO> bdcJsydsyqDOS = bdcdjMapper.queryBdcJsydsyq(grdacxRequestBody);
                if (CollectionUtils.isNotEmpty(bdcJsydsyqDOS)) {
                    for (BdcJsydsyqDO bdcJsydsyqDO : bdcJsydsyqDOS) {
                        BdcJsydsyqQlxxBO bdcJsydsyqQlxxBO = new BdcJsydsyqQlxxBO();
                        bdcJsydsyqQlxxBO.setQlxx(bdcJsydsyqDO);
                        if (StringUtils.isNotBlank(bdcJsydsyqDO.getXmid()) && grdacxRequestBody.getCqWithXm()) {
                            bdcJsydsyqQlxxBO.setBdcXmDO(commonService.getBdcXmByXmid(bdcJsydsyqDO.getXmid()));
                            // 查询权利人
                            List<BdcQlrDO> bdcQlrList = commonService.listBdcQlrByXmid(bdcJsydsyqDO.getXmid(), Constants.QLRLB_QLR);
                            if (CollectionUtils.isNotEmpty(bdcQlrList)) {
                                bdcJsydsyqQlxxBO.setBdcQlrDOList(bdcQlrList);
                            }
                        }
                        GrdacxData grdacxData = new GrdacxData();
                        // 查询宗地基本信息
                        if (StringUtils.isNotBlank(bdcJsydsyqDO.getBdcdyh())) {
                            BdcBdcdjbZdjbxxDO zdjbxxDO = bdcDjbxxFeignService.queryBdcBdcdjbZdjbxx(bdcJsydsyqDO.getBdcdyh());
                            bdcJsydsyqQlxxBO.setZdjbxxDO(zdjbxxDO);
                            if(null != zdjbxxDO){
                                grdacxData.setQjzl(zdjbxxDO.getZl());
                            }
                        }
                        grdacxData.setBdcJsydsyqQlxxBO(bdcJsydsyqQlxxBO);
                        grdacxDataList.add(grdacxData);
                    }
                }
            }
            if (grdacxModel.getData() == null) {
                grdacxModel.setData(new ArrayList<>());
            }

            if(CollectionUtils.isNotEmpty(grdacxDataList)){
                grdacxModel.setData(grdacxDataList);
            } else {
                grdacxModel.setData(new ArrayList<>());
            }
        }
    }

    /**
     * @param grdacxRequestBody
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 转换证件号
     */
    public void revertQlrZjh(GrdacxRequestBody grdacxRequestBody) {
        if (StringUtils.isNotBlank(grdacxRequestBody.getQlrzjh())) {
            String qlrzjh = grdacxRequestBody.getQlrzjh();
            if (BooleanUtils.toBoolean(grdacxRequestBody.getNeedZjhRevert())) {
                String revertZjhs = CardNumberTransformation.zjhTransformation(qlrzjh);
                String[] zjhArr = revertZjhs.split(",");
                grdacxRequestBody.setQlrzjhList(Arrays.asList(zjhArr));
            } else {
                String[] zjhArr = new String[]{qlrzjh};
                grdacxRequestBody.setQlrzjhList(Arrays.asList(zjhArr));
            }
        }
    }

    /**
     * @param requestBodylist
     * @return java.util.List<cn.gtmap.realestate.exchange.core.bo.grdacx.GrdacxModel>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 集合查询
     */
    @Override
    public GrdacxModel grdacxBatch(List<GrdacxRequestBody> requestBodylist) {
        GrdacxModel grdacxModel = new GrdacxModel();
        grdacxModel.setData(new ArrayList<>());
        if (CollectionUtils.isNotEmpty(requestBodylist)) {
            for (GrdacxRequestBody request : requestBodylist) {
                GrdacxModel temp = grdacx(request);
                if (CollectionUtils.isNotEmpty(temp.getData())) {
                    grdacxModel.getData().addAll(temp.getData());
                }
            }
        }
        return grdacxModel;
    }

    /**
     * @param ygxxRequestDatas 查询参数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 预告查询
     */
    @Override
    public GrdacxModel getYgxx(List<GetYgxxRequestData> ygxxRequestDatas) {
        GrdacxModel grdacxModel = new GrdacxModel();
        grdacxModel.setData(new ArrayList<>());
        if (CollectionUtils.isNotEmpty(ygxxRequestDatas)) {
            for (Object param : ygxxRequestDatas) {
                GetYgxxRequestData ygxxRequestData = new GetYgxxRequestData();

                if (param instanceof GetYgxxRequestData) {
                    ygxxRequestData = (GetYgxxRequestData) param;
                } else if (param instanceof JSONObject) {
                    ygxxRequestData = JSONObject.parseObject(((JSONObject) param).toJSONString(), GetYgxxRequestData.class);
                }
                GrdacxRequestBody requestBody = new GrdacxRequestBody();
                if (param instanceof GrdacxRequestBody) {
                    requestBody = (GrdacxRequestBody) param;
                } else {
                    dozerBeanMapper.map(ygxxRequestData, requestBody, "ygxxRequest");
                }

                GrdacxModel temp = grdacx(requestBody);
                if (CollectionUtils.isNotEmpty(temp.getData())) {
                    grdacxModel.getData().addAll(temp.getData());
                }
            }
        }
        return grdacxModel;
    }

    /**
     *
     * @param grdacxRequestBody
     * @return
     */
    @Override
    public GrdacxModel grdacxNt(GrdacxRequestBody grdacxRequestBody) {
        /**
         * 南通要求如果没有数据的话就返回一个空对象
         */
        GrdacxModel grdacxModel = grdacx(grdacxRequestBody);
        if(Objects.isNull(grdacxModel.getData())){
            List<GrdacxData> data = new ArrayList<>();
            GrdacxData grdacxData = new GrdacxData();
            data.add(grdacxData);
            grdacxModel.setData(data);
        }
        return grdacxModel;
    }

    /**
     * @param grdacxRequestBody
     * @param grdacxModel
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    public void setYg(GrdacxRequestBody grdacxRequestBody, GrdacxModel grdacxModel) {
        if (CheckParameter.checkAnyParameter(grdacxRequestBody, "qlrmc", "qlrzjh", "zl", "likeZl", "qlrmcmh", "slbh", "likeBdcdyh", "qlrmcmh", "qlrzjhmh",
                "likebdcqzh", "bdcqzhmh", "bdcqzh", "zhlsh", "ntbdcqzh", "bdcdybh", "xmid", "bdcdyh")) {
            if (zkqlrlb) {
                grdacxRequestBody.setCqQlrlb("");
            }
            List<BdcYgDO> ygDOList = bdcdjMapper.queryBdcYg(grdacxRequestBody);
            if (CollectionUtils.isNotEmpty(ygDOList)) {
                List<YgQlWithXmQlrDTO> ygList = new ArrayList<>();
                for (BdcYgDO ygDO : ygDOList) {
                    YgQlWithXmQlrDTO ygQlWithXmQlrDTO = new YgQlWithXmQlrDTO();
                    ygQlWithXmQlrDTO.setBdcql(ygDO);
                    // 保存项目
                    if (grdacxRequestBody.getWithXm() && StringUtils.isNotBlank(ygDO.getXmid())) {
                        ygQlWithXmQlrDTO.setBdcXmDO(commonService.getBdcXmByXmid(ygDO.getXmid()));
                    }
                    // 保存权利人
                    if (grdacxRequestBody.getWithQlr() && StringUtils.isNotBlank(ygDO.getXmid())) {
                        ygQlWithXmQlrDTO.setBdcQlrList(commonService.listBdcQlrByXmid(ygDO.getXmid(), grdacxRequestBody.getQlrlb()));
                    }
                    ygList.add(ygQlWithXmQlrDTO);
                }
                if (CollectionUtils.isNotEmpty(ygList)) {
                    GrdacxData grdacxData = new GrdacxData();
                    if (StringUtils.isNotBlank(grdacxRequestBody.getLikeBdcdyh()) && StringUtils.isBlank(grdacxRequestBody.getQlrmc())
                            && StringUtils.isBlank(grdacxRequestBody.getQlrzjh())) {
                        grdacxData.setOnlyBdcdyhQuery(true);
                    } else {
                        grdacxData.setOnlyBdcdyhQuery(false);
                    }
                    grdacxData.setYgList(ygList);
                    if (grdacxModel.getData() == null) {
                        grdacxModel.setData(new ArrayList<>());
                    }
                    grdacxModel.getData().add(grdacxData);
                }
            }
        }
    }

    /**
     * 获取限制权利数据
     *
     * @param grdacxRequestBody
     * @param grdacxModel
     */
    private void setXzql(GrdacxRequestBody grdacxRequestBody, GrdacxModel grdacxModel) {
        if (grdacxModel != null && CollectionUtils.isNotEmpty(grdacxModel.getData())
                && grdacxRequestBody != null && grdacxRequestBody.getXzqllx() != null && grdacxRequestBody.getXzqllx().length > 0) {
            QueryQlRequestDTO requestDTO = new QueryQlRequestDTO();
            requestDTO.setWithQlr(grdacxRequestBody.getWithQlr() == null ? false : grdacxRequestBody.getWithQlr());
            requestDTO.setWithXm(grdacxRequestBody.getWithXm() == null ? false : grdacxRequestBody.getWithXm());
            requestDTO.setQlrlb(grdacxRequestBody.getQlrlb());
            requestDTO.setQszt(grdacxRequestBody.getXzqlQszt());
            //查档对象数据
            for (GrdacxData grdacxData : grdacxModel.getData()) {
                if (grdacxData.gtMainBdcXm() != null) {
                    requestDTO.setBdcdyh(grdacxData.gtMainBdcXm().getBdcdyh());
                    for (Integer qllx : grdacxRequestBody.getXzqllx()) {
                        if (CommonConstantUtils.QLLX_DYAQ_DM.equals(qllx)) {
                            grdacxData.setDyaqList(commonService.listDyaqByBdcdyh(requestDTO));
                        } else if (CommonConstantUtils.QLLX_CF.equals(qllx)) {
                            grdacxData.setCfList(commonService.listCfByBdcdyh(requestDTO));
                        } else if (CommonConstantUtils.QLLX_YY.equals(qllx)) {
                            grdacxData.setYyList(commonService.listYyByBdcdyh(requestDTO));
                        } else if (CommonConstantUtils.QLLX_YG_DM.equals(qllx)) {
                            grdacxData.setYgList(commonService.listYgByBdcdyh(requestDTO));
                        } else if (CommonConstantUtils.QLLX_JZQ.equals(qllx)) {
                            grdacxData.setJzqList(commonService.listJzqByBdcdyh(requestDTO));
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取单元锁定数据
     *
     * @param grdacxRequestBody
     * @param grdacxModel
     */
    private void setDysd(GrdacxRequestBody grdacxRequestBody, GrdacxModel grdacxModel) {
        if (grdacxModel != null && CollectionUtils.isNotEmpty(grdacxModel.getData())
                && grdacxRequestBody != null && grdacxRequestBody.getWithDysd() != null && grdacxRequestBody.getWithDysd()) {
            //查档对象数据
            for (GrdacxData grdacxData : grdacxModel.getData()) {
                if (grdacxData.gtMainBdcXm() != null
                        && StringUtils.isNotBlank(grdacxData.gtMainBdcXm().getBdcdyh())) {

                    List<BdcDysdDO> sdList = new ArrayList<>();
                    if (StringUtils.isBlank(grdacxRequestBody.getXzqlQszt())) {
                        // 限制权利 权属状态为空时 查询 锁定列表
                        sdList = commonService.listDysdByBdcdyh(grdacxData.gtMainBdcXm().getBdcdyh(),
                                CommonConstantUtils.SDZT_SD);
                    } else {
                        String[] qsztArr = grdacxRequestBody.getXzqlQszt().split(",");
                        List<String> qsztList = Arrays.asList(qsztArr);
                        if (qsztList.contains(CommonConstantUtils.QSZT_VALID + "")
                                || qsztList.contains(CommonConstantUtils.QSZT_TEMPORY + "")) {
                            List<BdcDysdDO> tempList = commonService.listDysdByBdcdyh(grdacxData.gtMainBdcXm().getBdcdyh(),
                                    CommonConstantUtils.SDZT_SD);
                            if (CollectionUtils.isNotEmpty(tempList)) {
                                sdList.addAll(tempList);
                            }
                        }
                        // 历史状态 查询解锁状态
                        if (qsztList.contains(CommonConstantUtils.QSZT_HISTORY + "")) {
                            List<BdcDysdDO> tempList = commonService.listDysdByBdcdyh(grdacxData.gtMainBdcXm().getBdcdyh(),
                                    CommonConstantUtils.SDZT_JS);
                            if (CollectionUtils.isNotEmpty(tempList)) {
                                sdList.addAll(tempList);
                            }
                        }
                    }
                    if (null == sdList) {
                        sdList = new ArrayList<>();
                    }
                    grdacxData.setSdList(sdList);
                    grdacxData.setDysdList(sdList);
                }
            }
        }
    }

    /**
     * 获取证书锁定数据
     *
     * @param grdacxRequestBody
     * @param grdacxModel
     */
    private void setZssd(GrdacxRequestBody grdacxRequestBody, GrdacxModel grdacxModel) {
        if (grdacxModel != null && CollectionUtils.isNotEmpty(grdacxModel.getData())
                && grdacxRequestBody != null && grdacxRequestBody.getWithZssd() != null && grdacxRequestBody.getWithZssd()) {
            //查档对象数据
            for (GrdacxData grdacxData : grdacxModel.getData()) {
                if (grdacxData.gtMainBdcXm() != null
                        && StringUtils.isNotBlank(grdacxData.gtMainBdcXm().getXmid())) {
                    List<BdcZssdDO> zssdList = new ArrayList<>();
                    if (StringUtils.isBlank(grdacxRequestBody.getXzqlQszt())) {
                        // 限制权利 权属状态为空时 查询 锁定列表
                        zssdList = commonService.listZssdByXmid(grdacxData.gtMainBdcXm().getXmid(),
                                CommonConstantUtils.SDZT_SD);
                    } else {
                        String[] qsztArr = grdacxRequestBody.getXzqlQszt().split(",");
                        List<String> qsztList = Arrays.asList(qsztArr);
                        if (qsztList.contains(CommonConstantUtils.QSZT_VALID + "")
                                || qsztList.contains(CommonConstantUtils.QSZT_TEMPORY + "")) {
                            List<BdcZssdDO> tempList = commonService.listZssdByXmid(grdacxData.gtMainBdcXm().getXmid(),
                                    CommonConstantUtils.SDZT_SD);
                            if (CollectionUtils.isNotEmpty(tempList)) {
                                zssdList.addAll(tempList);
                            }
                        }
                        // 历史状态 查询解锁状态
                        if (qsztList.contains(CommonConstantUtils.QSZT_HISTORY + "")) {
                            List<BdcZssdDO> tempList = commonService.listZssdByXmid(grdacxData.gtMainBdcXm().getXmid(),
                                    CommonConstantUtils.SDZT_JS);
                            if (CollectionUtils.isNotEmpty(tempList)) {
                                zssdList.addAll(tempList);
                            }
                        }
                    }
                    grdacxData.setZssdList(zssdList);
                    //当单元锁定和证书锁定都为0时，才是没有锁定，其他情况均为有锁定情况
                    if (CollectionUtils.isEmpty(grdacxData.getSdList())) {
                        grdacxData.setSdList(zssdList);
                    } else {
                        List sd = new ArrayList<>(grdacxData.getSdList());
                        sd.addAll(zssdList);
                        grdacxData.setSdList(sd);
                    }

                }
            }
        }
    }

    /**
     * 获取产权数据
     *
     * @param grdacxRequestBody
     * @param grdacxModel
     */
    private void setFdcq(GrdacxRequestBody grdacxRequestBody, GrdacxModel grdacxModel) {
        if (CheckParameter.checkAnyParameter(grdacxRequestBody, "qlrmc", "qlrzjh", "zl", "likeZl", "qlrmcmh",
                "slbh", "likeBdcdyh", "qlrmcmh", "qlrzjhmh",
                "likebdcqzh", "bdcqzhmh", "bdcqzh", "zhlsh", "ntbdcqzh", "bdcdybh", "xmid", "qlrzjhList", "bdcdyh")) {
       /* if (StringUtils.isNotBlank(grdacxRequestBody.getQlrmc())
                || StringUtils.isNotBlank(grdacxRequestBody.getXmid())
                || StringUtils.isNotBlank(grdacxRequestBody.getSlbh())
                || CollectionUtils.isNotEmpty(grdacxRequestBody.getQlrzjhList())) {*/
            List<BdcFdcqDO> fdcqList = bdcdjMapper.queryBdcFdcq(grdacxRequestBody);
            if (CollectionUtils.isNotEmpty(fdcqList)) {
                for (BdcFdcqDO fdcq : fdcqList) {
                    BdcFdcqQlxxBO fdcqQlxxBO = new BdcFdcqQlxxBO();
                    BdcQjgldmQO qjgldmQO = new BdcQjgldmQO();
                    qjgldmQO.setBdcdyh(fdcq.getBdcdyh());
                    String qjgldm = bdcXmfbFeignService.queryQjgldm(qjgldmQO);
                    //开始判断zrzh和fjh是否有值，无值查询权籍库
                    FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(fdcq.getBdcdyh(), qjgldm);
                    if (StringUtils.isBlank(fdcq.getZh())) {
                        FwLjzDO fwLjzDO = new FwLjzDO();
                        if (null != fwHsDO) {
                            //查询权籍数据进行赋值 fw_ljz
                            fwLjzDO = fwLjzFeginService.queryLjzByFwDcbIndex(fwHsDO.getFwDcbIndex(), qjgldm);

                        } else {
                            //意味这个是独幢，或者多幢
                            fwLjzDO = fwLjzFeginService.queryLjzByBdcdyh(fdcq.getBdcdyh(), qjgldm);

                        }
                        if (null != fwLjzDO) {
                            fdcq.setZh(fwLjzDO.getZrzh());
                        }

                    }
                    if (StringUtils.isBlank(fdcq.getFjh())) {
                        //查询权籍数据进行赋值  fw_hs
                        if (null != fwHsDO) {
                            fdcq.setFjh(fwHsDO.getFjh());
                        }
                    }
                    //63828 【连云港市】外网申请获取产权证信息更改接口取值  因类型不一致，使用myzcs进行赋值对照
                    if (szcqz) {
                        if (null == fdcq.getSzc()) {
                            //查询权籍数据进行赋值  fw_hs
                            if (null != fwHsDO) {
                                fdcq.setSzmyc(fwHsDO.getCh());
                            }
                        } else {
                            fdcq.setSzmyc(String.valueOf(fdcq.getSzc()));
                        }
                    }

                    // 增加土地面积
                    addTdMj(fdcq);
                    fdcqQlxxBO.setQlxx(fdcq);
                    if (StringUtils.isNotBlank(fdcq.getXmid())
                            && grdacxRequestBody.getCqWithXm()) {
                        BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(fdcq.getXmid());
                        fdcqQlxxBO.setBdcXmDO(bdcXmDO);
                    }
                    GrdacxData data = new GrdacxData();
                    // 查询宗地基本信息
                    if (StringUtils.isNotBlank(fdcq.getBdcdyh())
                            && grdacxRequestBody.getQueryFdcqZdjbxx()) {
                        BdcBdcdjbZdjbxxDO zdjbxxDO = bdcDjbxxFeignService.queryBdcBdcdjbZdjbxxByZdzhh(fdcq.getBdcdyh().substring(0, 19));
                        fdcqQlxxBO.setZdjbxxDO(zdjbxxDO);
                    }
                    // 匹配的土地证号
                    String fcxmid = fdcq.getXmid();
                    List<BdcFctdPpgxDO> bdcFctdPpgxDOS = bdcPpFeignService.queryBdcFctdPpgx(fcxmid);
                    if (CollectionUtils.isNotEmpty(bdcFctdPpgxDOS)) {
                        String tdcqxmid = bdcFctdPpgxDOS.get(0).getTdcqxmid();
                        BdcXmDO tdBdcXmDO = commonService.getBdcXmByXmid(tdcqxmid);
                        if (Objects.nonNull(tdBdcXmDO)) {
                            data.setTdzh(tdBdcXmDO.getBdcqzh());
                        }
                    }
                    // 查询权利人
                    if (StringUtils.isNotBlank(fdcq.getXmid())
                            && grdacxRequestBody.getQueryFdcqQlr()) {
                        List<BdcQlrDO> bdcQlrList = commonService.listBdcQlrByXmid(fdcq.getXmid(), Constants.QLRLB_QLR);
                        if (CollectionUtils.isNotEmpty(bdcQlrList)) {
                            fdcqQlxxBO.setBdcQlrDOList(bdcQlrList);
                        }
                    }
                    data.setBdcFdcqQlxxBO(fdcqQlxxBO);
                    //根据不动产单元号获取权籍库的坐落
                    if (StringUtils.isNotBlank(fdcq.getBdcdyh())) {
                        BdcdyResponseDTO bdcdyResponseDTO = bdcdyFeignService.queryBdcdy(fdcq.getBdcdyh(), "", qjgldm);
                        if(null != bdcdyResponseDTO){
                            data.setQjzl(bdcdyResponseDTO.getZl());
                        }
                    }
                    if (grdacxModel.getData() == null) {
                        grdacxModel.setData(new ArrayList<>());
                    }
                    grdacxModel.getData().add(data);
                }
            }
        }
    }

    /**
     * 根据xmid查询证书表权利其他状况
     *
     * @param xmid
     * @return
     * @Date 2021/7/27
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public String queryQlqtzkByxmid(String xmid) {
        String qlqtzk = "";
        if (StringUtil.isNotBlank(xmid)) {
            //根据xmid查询证书信息
            List<BdcZsDO> zsDOList = bdcZsFeignService.queryBdcZsByXmid(xmid);
            if (CollectionUtils.isNotEmpty(zsDOList)) {
                qlqtzk = zsDOList.get(0).getQlqtzk();
            }
        }
        return qlqtzk;
    }

    /**
     * BdcFdcqDO设置 tdsyqmj（土地使用权面积）、fttdmj(分摊土地面积)、dytdmj（独用土地面积）
     *
     * @param fdcqDO 住房信息
     */
    private void addTdMj(BdcFdcqDO fdcqDO) {
        if (fdcqDO.getTdsyqmj() == null && fdcqDO.getFttdmj() == null && fdcqDO.getDytdmj() == null) {
            // 查bdc_jsydsyq
            List<BdcJsydsyqDO> bdcJsydsyqDOList = bdcdjMapper.listJsydsyqDOByXmid(fdcqDO.getXmid());
            if (CollectionUtils.isNotEmpty(bdcJsydsyqDOList)) {
                BdcJsydsyqDO jsydsyqDO = bdcJsydsyqDOList.get(0);
                fdcqDO.setTdsyqmj(jsydsyqDO.getSyqmj());
                fdcqDO.setFttdmj(jsydsyqDO.getFttdmj());
                fdcqDO.setDytdmj(jsydsyqDO.getDytdmj());
            } else {
                // 查fw_hs
                FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(fdcqDO.getBdcdyh(), "");
                if (fwHsDO != null) {
                    fdcqDO.setFttdmj(fwHsDO.getFttdmj());
                    fdcqDO.setDytdmj(fwHsDO.getDytdmj());
                    if (fwHsDO.getFttdmj() != null && fwHsDO.getDytdmj() == null) {
                        fdcqDO.setTdsyqmj(fwHsDO.getFttdmj());
                    }
                    if (fwHsDO.getFttdmj() == null && fwHsDO.getDytdmj() != null) {
                        fdcqDO.setTdsyqmj(fwHsDO.getDytdmj());
                    }
                    if (fwHsDO.getFttdmj() != null && fwHsDO.getDytdmj() != null) {
                        fdcqDO.setTdsyqmj(fwHsDO.getFttdmj() + fwHsDO.getDytdmj());
                    }
                } else {
                    LOGGER.info("未查询到土地面积相关数据，住房信息：{}", fdcqDO.toString());
                }
            }
        }

    }


}
