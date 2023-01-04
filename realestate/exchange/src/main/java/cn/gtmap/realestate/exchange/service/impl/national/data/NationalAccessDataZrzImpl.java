package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.KttFwZrzDO;
import cn.gtmap.realestate.common.core.domain.exchange.QjsjDataModel;
import cn.gtmap.realestate.common.core.dto.exchange.access.QjsjjcDTO;
import cn.gtmap.realestate.common.core.service.feign.building.*;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KttFwZrzOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.KttFwZrzMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessQjsjService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 国家接入-自然幢信息
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
@Service
public class NationalAccessDataZrzImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService, NationalAccessQjsjService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataZrzImpl.class);

    @Autowired
    private KttFwZrzMapper kttFwZrzMapper;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Autowired
    private AccessBuildingFeignService accessBuildingFeignService;

    @Autowired
    private FwLjzFeginService fwLjzFeginService;

    @Autowired
    private FwHsFeignService fwHsFeignService;


    @Autowired
    private FwYcHsFeignService fwYcHsFeignService;
    @Autowired
    private FwXmxxFeignService fwXmxxFeignService;
    @Autowired
    private FwKFeignService fwKFeignService;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<Map> mapList = null;
            List<KttFwZrzDO> kttFwZrzList = new ArrayList<>();
            if (datasourceSwitch && !CommonConstantUtils.SYSTEM_VERSION_HF.equals(dataVersion)) {
                LOGGER.info("开始调用building工程queryKttFwZrzList:{}", ywh);
                Map<String, Object> bdcdyhAndQjgldm = bdcdjMapper.getBdcdyhByXmid(ywh);
                if (MapUtils.isNotEmpty(bdcdyhAndQjgldm) && bdcdyhAndQjgldm.containsKey("BDCDYH")) {
                    String bdcdyh = (String) bdcdyhAndQjgldm.get("BDCDYH");
                    String qjgldm = bdcdyhAndQjgldm.containsKey("QJGLDM") ? (String) bdcdyhAndQjgldm.get("QJGLDM") : null;
                    LOGGER.info("开始调用building工程queryKttFwZrzList,入参:{}", bdcdyhAndQjgldm);
                    mapList = kttFwZrzMapper.queryKttFwZrzFdcqList(map);
                    if (CollectionUtils.isNotEmpty(mapList)) {
                        for (Map zrzMap : mapList) {
                            //根据bdcdyFwlx判断房屋类型
                            String bdcdyFwlx = null != zrzMap.get("BDCDYFWLX") ? (String) zrzMap.get("BDCDYFWLX") : null;
                            String jgsj = MapUtils.getString(zrzMap, "JGRQ");
                            Date jgsjDate = DateUtils.formatDate(jgsj);
                            zrzMap.remove("JGRQ");
                            KttFwZrzDO zrz = JSONObject.parseObject(JSONObject.toJSONString(zrzMap), KttFwZrzDO.class);
                            zrz.setJgrq(jgsjDate);
                            String bsm = getBsmString();
                            zrz.setBsm(Integer.valueOf(bsm));
                            zrz.setZddm(bdcdyh.substring(0, 19));
                            //开始根据bdcdyfwlx判断,先查户室 ，没有在查ychs ,然后逻辑幢，自然幢查询，将信息补充进去
                            if (null != bdcdyFwlx) {
                                LOGGER.info("不动产房屋类型为：{}", bdcdyFwlx);
                                String dcb_index = "";
                                if (CommonConstantUtils.BDCDYFWLX_HS.toString().equals(bdcdyFwlx)) {
                                    //先实测，有就不查预测，否则再预测
                                    FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(bdcdyh, qjgldm);
                                    if (null != fwHsDO) {
                                        LOGGER.info("查到实测户室：fwhs_dcb_index:{}", fwHsDO.getFwDcbIndex());
                                        zrz.setGhyt(fwHsDO.getYtmc());
                                        zrz.setPzyt(fwHsDO.getPzyt());
                                        zrz.setSjyt(fwHsDO.getSjyt());
                                        dcb_index = fwHsDO.getFwDcbIndex();
                                    } else {
                                        //查预测
                                        FwYchsDO fwYchsDO = fwYcHsFeignService.queryFwYcHsByBdcdyh(bdcdyh, qjgldm);
                                        if (null != fwYchsDO) {
                                            LOGGER.info("查到预测户室：fwhs_dcb_index:{}", fwYchsDO.getFwDcbIndex());
                                            zrz.setGhyt(fwYchsDO.getYtmc());
                                            zrz.setPzyt(fwYchsDO.getPzyt());
                                            zrz.setSjyt(fwYchsDO.getSjyt());
                                            dcb_index = fwYchsDO.getFwDcbIndex();
                                        }
                                    }
                                    //查询逻辑幢和自然幢信息
                                    organizationZrzData(qjgldm, zrz, dcb_index, false);
                                } else if (CommonConstantUtils.FWLX_DUOZH.toString().equals(bdcdyFwlx)) {
                                    //项目内多幢，查fw_xmxx
                                    FwXmxxDO fwXmxxDO = fwXmxxFeignService.queryXmxxByBdcdyh(bdcdyh,
                                            qjgldm);
                                    if (null != fwXmxxDO) {
                                        //查询逻辑幢和自然幢信息
                                        FwLjzDO fwLjzDO = fwLjzFeginService.queryLjzByBdcdyh(bdcdyh, qjgldm);
                                        if (null != fwLjzDO) {
                                            zrz.setZrzh(fwLjzDO.getZrzh());
                                            zrz.setLddm(fwLjzDO.getDh());
                                            zrz.setLdzl(fwLjzDO.getZldz());
                                            zrz.setZydmj(fwLjzDO.getZydmj());
                                            //查询自然幢
                                            FwKDO fwKDO = fwKFeignService.queryFwKByLszdAndZrzh(fwLjzDO.getLszd(), fwLjzDO.getZrzh(), qjgldm);
                                            if (null != fwKDO) {
                                                zrz.setXmmc(fwKDO.getXmmc());
                                                zrz.setJzwmc(fwKDO.getFwmc());
                                                zrz.setJzwgd(fwKDO.getJzwgd());
                                                zrz.setZzdmj(fwKDO.getZdmj());
                                                zrz.setYcjzmj(fwKDO.getYcjzmj());
                                                zrz.setScjzmj(fwKDO.getScjzmj());
                                                zrz.setDscs(null != fwKDO.getDscs() ? String.valueOf(fwKDO.getDscs()) : null);
                                                zrz.setDxcs(null != fwKDO.getDxcs() ? String.valueOf(fwKDO.getDxcs()) : null);
                                                zrz.setDxsd(fwKDO.getDxsd());
                                                zrz.setZts(fwKDO.getZts());
                                                zrz.setBz(fwKDO.getBz());
                                            }
                                        }
                                        zrz.setXmmc(fwXmxxDO.getXmmc());
                                    }
                                } else {
                                    //独幢，直接查ljz和zrz信息
                                    FwLjzDO fwLjzDO = fwLjzFeginService.queryLjzByBdcdyh(bdcdyh, qjgldm);
                                    if (null != fwLjzDO) {
                                        zrz.setZrzh(fwLjzDO.getZrzh());
                                        zrz.setLddm(fwLjzDO.getDh());
                                        zrz.setLdzl(fwLjzDO.getZldz());
                                        zrz.setZydmj(fwLjzDO.getZydmj());
                                        //查询自然幢
                                        FwKDO fwKDO = fwKFeignService.queryFwKByLszdAndZrzh(fwLjzDO.getLszd(), fwLjzDO.getZrzh(), qjgldm);
                                        if (null != fwKDO) {
                                            zrz.setXmmc(fwKDO.getXmmc());
                                            zrz.setJzwmc(fwKDO.getFwmc());
                                            zrz.setJzwgd(fwKDO.getJzwgd());
                                            zrz.setZzdmj(fwKDO.getZdmj());
                                            zrz.setYcjzmj(fwKDO.getYcjzmj());
                                            zrz.setScjzmj(fwKDO.getScjzmj());
                                            zrz.setDscs(null != fwKDO.getDscs() ? String.valueOf(fwKDO.getDscs()) : null);
                                            zrz.setDxcs(null != fwKDO.getDxcs() ? String.valueOf(fwKDO.getDxcs()) : null);
                                            zrz.setDxsd(fwKDO.getDxsd());
                                            zrz.setZts(fwKDO.getZts());
                                            zrz.setBz(fwKDO.getBz());
                                        }
                                    }
                                }
                            }
                            kttFwZrzList.add(zrz);
                        }

                    }
                }
            } else {
                LOGGER.info("自然幢正常sql查询！");
                mapList = kttFwZrzMapper.queryKttFwZrzList(map);
                Map<String, BdcFdcqDO> collect = null;
                kttFwZrzList = dataHandle(mapList, kttFwZrzList, collect);
            }
            if (CollectionUtils.isNotEmpty(kttFwZrzList)) {
                if (newDefault) {
                    for (KttFwZrzDO kttFwZrzDO : kttFwZrzList) {
                        if (qxdmConvert) {
                            //需要根据qxdm进行对照
                            kttFwZrzDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", kttFwZrzDO.getQxdm()));
                        }
                        ClassHandleUtil.setDefaultValueToNullField(kttFwZrzDO);
                    }
                }
                setData(dataModel, kttFwZrzList);
            }
        }
        return dataModel;
    }

    /**
     * 组织数据
     *
     * @param
     * @return
     * @Date 2022/4/25
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public void organizationZrzData(String qjgldm, KttFwZrzDO zrz, String dcb_index, Boolean ljzxx) {
        FwLjzDO ljzDO = fwLjzFeginService.queryLjzByFwDcbIndex(dcb_index, qjgldm);
        if (null != ljzDO) {
            LOGGER.info("查到逻辑幢信息,bdcdyh:{}", ljzDO.getBdcdyh());
            zrz.setZrzh(ljzDO.getZrzh());
            zrz.setLddm(ljzDO.getDh());
            zrz.setLdzl(ljzDO.getZldz());
            zrz.setZydmj(ljzDO.getZydmj());
            if (Objects.nonNull(ljzxx) && ljzxx) {
                zrz.setJgrq(ljzDO.getJgrq());
                zrz.setZcs(String.valueOf(ljzDO.getFwcs()));
            }
            //查询自然幢
            FwKDO fwKDO = fwKFeignService.queryFwKByLszdAndZrzh(ljzDO.getLszd(), ljzDO.getZrzh(), qjgldm);
            if (null != fwKDO) {
                LOGGER.info("查到自然幢信息,lszd:{},zrzj:{}", fwKDO.getLszd(), fwKDO.getZrzh());
                zrz.setXmmc(fwKDO.getXmmc());
                zrz.setJzwmc(fwKDO.getFwmc());
                zrz.setJzwgd(fwKDO.getJzwgd());
                zrz.setZzdmj(fwKDO.getZdmj());
                zrz.setYcjzmj(fwKDO.getYcjzmj());
                zrz.setScjzmj(fwKDO.getScjzmj());
                zrz.setDscs(null != fwKDO.getDscs() ? String.valueOf(fwKDO.getDscs()) : null);
                zrz.setDxcs(null != fwKDO.getDxcs() ? String.valueOf(fwKDO.getDxcs()) : null);
                zrz.setDxsd(fwKDO.getDxsd());
                zrz.setZts(fwKDO.getZts());
                zrz.setBz(fwKDO.getBz());
            }

        }
    }

    /**
     * 获取标识码
     *
     * @param
     * @return
     * @Date 2022/4/25
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    private String getBsmString() {
        String bsm = bdcdjMapper.getBsm().toString();
        if (bsm.length() > 10) {
            //大于十位取十位，不足则补全，作为唯一标识码固定为10位
            bsm = bsm.substring(bsm.length() - 10);
        } else {
            bsm = String.format("%010d", Integer.valueOf(bsm));
        }
        return bsm;
    }

    private List<KttFwZrzDO> dataHandle(List<Map> mapList, List<KttFwZrzDO> kttFwZrzList, Map<String, BdcFdcqDO> collect) {
        if (CollectionUtils.isNotEmpty(mapList)) {
            for (Map zrzMap : mapList) {
                String jgsj = MapUtils.getString(zrzMap, "JGRQ");
                Date jgsjDate = DateUtils.formatDate(jgsj);
                zrzMap.remove("JGRQ");
                KttFwZrzDO zrz = JSONObject.parseObject(JSONObject.toJSONString(zrzMap), KttFwZrzDO.class);
                zrz.setJgrq(jgsjDate);
                String bsm = getBsmString();
                zrz.setBsm(Integer.valueOf(bsm));
                if (collect != null && collect.containsKey(zrz.getBdcdyh())) {
                    if (zrz.getJgrq() == null) {
                        try {
                            zrz.setJgrq(new SimpleDateFormat("yyyy-MM-dd").parse(collect.get(zrz.getBdcdyh()).getJgsj()));
                        } catch (Exception e) {
                            LOGGER.info("转换jgsj出错:", e);
                        }
                    }
                    if (StringUtils.isBlank(zrz.getZcs())) {
                        zrz.setZcs(collect.get(zrz.getBdcdyh()).getZcs().toString());
                    }
                    if (StringUtils.isBlank(zrz.getGhyt())) {
                        zrz.setGhyt(collect.get(zrz.getBdcdyh()).getGhyt().toString());
                    }
                    if (StringUtils.isBlank(zrz.getFwjg())) {
                        zrz.setFwjg(collect.get(zrz.getBdcdyh()).getFwjg().toString());
                    }
                    if (zrz.getZzdmj() == null) {
                        zrz.setZzdmj(collect.get(zrz.getBdcdyh()).getZzdmj());
                    }
                }
                kttFwZrzList.add(zrz);
            }

        }
        return kttFwZrzList;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<Map> mapList = kttFwZrzMapper.queryKttFwZrzListOld(map);
            Map<String, BdcFdcqDO> collect = null;
            List<KttFwZrzOldDO> kttFwZrzList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(mapList)) {
                for (Map zrzMap : mapList) {
                    String jgsj = MapUtils.getString(zrzMap, "JGRQ");
                    Date jgsjDate = DateUtils.formatDate(jgsj);
                    zrzMap.remove("JGRQ");
                    KttFwZrzOldDO zrz = JSONObject.parseObject(JSONObject.toJSONString(zrzMap), KttFwZrzOldDO.class);
                    zrz.setJgrq(jgsjDate);
                    String bsm = getBsmString();
                    zrz.setBsm(Integer.valueOf(bsm));
                    if (collect != null && collect.containsKey(zrz.getBdcdyh())) {
                        if (zrz.getJgrq() == null) {
                            try {
                                zrz.setJgrq(new SimpleDateFormat("yyyy-MM-dd").parse(collect.get(zrz.getBdcdyh()).getJgsj()));
                            } catch (Exception e) {
                                LOGGER.info("转换jgsj出错:", e);
                            }
                        }
                        if (StringUtils.isBlank(zrz.getZcs())) {
                            zrz.setZcs(collect.get(zrz.getBdcdyh()).getZcs().toString());
                        }
                        if (StringUtils.isBlank(zrz.getGhyt())) {
                            zrz.setGhyt(collect.get(zrz.getBdcdyh()).getGhyt().toString());
                        }
                        if (StringUtils.isBlank(zrz.getFwjg())) {
                            zrz.setFwjg(collect.get(zrz.getBdcdyh()).getFwjg().toString());
                        }
                        if (zrz.getZzdmj() == null) {
                            zrz.setZzdmj(collect.get(zrz.getBdcdyh()).getZzdmj());
                        }
                    }
                    kttFwZrzList.add(zrz);
                }
            }
            if (CollectionUtils.isNotEmpty(kttFwZrzList)) {

                setDataOld(dataModel, kttFwZrzList);
            }
        }
        return dataModel;
    }

    @Override
    public <T> List<T> getAccessData(String ywh) {
        return Collections.emptyList();
    }

    @Override
    public String getAccessDataTagName() {
        return "KTT_FW_ZRZ";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getKttFwZrzList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getKttFwZrzList();
    }

    /**
     * @param dataModel
     * @param dataList
     * @return java.util.List
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    public void setData(DataModel dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setKttFwZrzList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setKttFwZrzList(dataList);
        }
    }

    /**
     * @param qjsjjcDTO
     * @param dataModel
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 组织权籍数据
     * @date : 2022/11/22 9:13
     */
    @Override
    public QjsjDataModel getAccessQjsjModel(QjsjjcDTO qjsjjcDTO, QjsjDataModel dataModel) {
        if (Objects.isNull(qjsjjcDTO) || StringUtils.isBlank(qjsjjcDTO.getBdcdyh())) {
            return dataModel;
        }
        List<KttFwZrzDO> kttFwZrzList = new ArrayList<>();
        //先模拟一条空数据，只放单元号
        List<Map> mapList = new ArrayList<>(1);
        Map zrzDualMap = new HashMap(5);
        zrzDualMap.put("bdcdyh", qjsjjcDTO.getBdcdyh());
        zrzDualMap.put("bdcdyfwlx", qjsjjcDTO.getBdcdyfwlx());
        zrzDualMap.put("qxdm", StringUtils.substring(qjsjjcDTO.getBdcdyh(), 0, 6));
        mapList.add(zrzDualMap);
        for (Map zrzMap : mapList) {
            //根据bdcdyFwlx判断房屋类型
            String bdcdyFwlx = null != zrzMap.get("BDCDYFWLX") ? (String) zrzMap.get("BDCDYFWLX") : null;
            KttFwZrzDO zrz = JSONObject.parseObject(JSONObject.toJSONString(zrzMap), KttFwZrzDO.class);
            String bsm = getBsmString();
            zrz.setBsm(Integer.valueOf(bsm));
            zrz.setZddm(StringUtils.substring(qjsjjcDTO.getBdcdyh(), 0, 19));
            zrz.setDah("");
            zrz.setZt("1");
            //开始根据bdcdyfwlx判断,先查户室 ，没有在查ychs ,然后逻辑幢，自然幢查询，将信息补充进去
            if (null != bdcdyFwlx) {
                LOGGER.info("不动产房屋类型为：{}", bdcdyFwlx);
                String dcb_index = "";
                if (CommonConstantUtils.BDCDYFWLX_HS.toString().equals(bdcdyFwlx)) {
                    //先实测，有就不查预测，否则再预测
                    FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(qjsjjcDTO.getBdcdyh(), qjsjjcDTO.getQjgldm());
                    if (null != fwHsDO) {
                        LOGGER.info("查到实测户室：fwhs_dcb_index:{}", fwHsDO.getFwDcbIndex());
                        zrz.setGhyt(fwHsDO.getYtmc());
                        zrz.setPzyt(fwHsDO.getPzyt());
                        zrz.setSjyt(fwHsDO.getSjyt());
                        zrz.setYtmc(fwHsDO.getYtmc());
                        zrz.setFwjg(fwHsDO.getFwjg());
                        dcb_index = fwHsDO.getFwDcbIndex();
                    } else {
                        //查预测
                        FwYchsDO fwYchsDO = fwYcHsFeignService.queryFwYcHsByBdcdyh(qjsjjcDTO.getBdcdyh(), qjsjjcDTO.getQjgldm());
                        if (null != fwYchsDO) {
                            LOGGER.info("查到预测户室：fwhs_dcb_index:{}", fwYchsDO.getFwDcbIndex());
                            zrz.setGhyt(fwYchsDO.getYtmc());
                            zrz.setPzyt(fwYchsDO.getPzyt());
                            zrz.setSjyt(fwYchsDO.getSjyt());
                            zrz.setYtmc(fwYchsDO.getYtmc());
                            zrz.setFwjg("");
                            dcb_index = fwYchsDO.getFwDcbIndex();
                        }
                    }
                    //查询逻辑幢和自然幢信息
                    organizationZrzData(qjsjjcDTO.getQjgldm(), zrz, dcb_index, true);
                } else if (CommonConstantUtils.FWLX_DUOZH.toString().equals(bdcdyFwlx)) {
                    //项目内多幢，查fw_xmxx
                    FwXmxxDO fwXmxxDO = fwXmxxFeignService.queryXmxxByBdcdyh(qjsjjcDTO.getBdcdyh(),
                            qjsjjcDTO.getQjgldm());
                    if (null != fwXmxxDO) {
                        //查询逻辑幢和自然幢信息
                        FwLjzDO fwLjzDO = fwLjzFeginService.queryLjzByBdcdyh(qjsjjcDTO.getBdcdyh(), qjsjjcDTO.getQjgldm());
                        if (null != fwLjzDO) {
                            zrz.setZrzh(fwLjzDO.getZrzh());
                            zrz.setLddm(fwLjzDO.getDh());
                            zrz.setLdzl(fwLjzDO.getZldz());
                            zrz.setZydmj(fwLjzDO.getZydmj());
                            zrz.setJgrq(fwLjzDO.getJgrq());
                            zrz.setZcs(String.valueOf(fwLjzDO.getFwcs()));
                            //查询自然幢
                            FwKDO fwKDO = fwKFeignService.queryFwKByLszdAndZrzh(fwLjzDO.getLszd(), fwLjzDO.getZrzh(), qjsjjcDTO.getQjgldm());
                            if (null != fwKDO) {
                                zrz.setXmmc(fwKDO.getXmmc());
                                zrz.setJzwmc(fwKDO.getFwmc());
                                zrz.setJzwgd(fwKDO.getJzwgd());
                                zrz.setZzdmj(fwKDO.getZdmj());
                                zrz.setYcjzmj(fwKDO.getYcjzmj());
                                zrz.setScjzmj(fwKDO.getScjzmj());
                                zrz.setDscs(null != fwKDO.getDscs() ? String.valueOf(fwKDO.getDscs()) : null);
                                zrz.setDxcs(null != fwKDO.getDxcs() ? String.valueOf(fwKDO.getDxcs()) : null);
                                zrz.setDxsd(fwKDO.getDxsd());
                                zrz.setZts(fwKDO.getZts());
                                zrz.setBz(fwKDO.getBz());
                            }
                        }
                        zrz.setXmmc(fwXmxxDO.getXmmc());
                    }
                } else {
                    //独幢，直接查ljz和zrz信息
                    FwLjzDO fwLjzDO = fwLjzFeginService.queryLjzByBdcdyh(qjsjjcDTO.getBdcdyh(), qjsjjcDTO.getQjgldm());
                    if (null != fwLjzDO) {
                        zrz.setZrzh(fwLjzDO.getZrzh());
                        zrz.setLddm(fwLjzDO.getDh());
                        zrz.setLdzl(fwLjzDO.getZldz());
                        zrz.setZydmj(fwLjzDO.getZydmj());
                        zrz.setJgrq(fwLjzDO.getJgrq());
                        zrz.setZcs(String.valueOf(fwLjzDO.getFwcs()));
                        //查询自然幢
                        FwKDO fwKDO = fwKFeignService.queryFwKByLszdAndZrzh(fwLjzDO.getLszd(), fwLjzDO.getZrzh(), qjsjjcDTO.getQjgldm());
                        if (null != fwKDO) {
                            zrz.setXmmc(fwKDO.getXmmc());
                            zrz.setJzwmc(fwKDO.getFwmc());
                            zrz.setJzwgd(fwKDO.getJzwgd());
                            zrz.setZzdmj(fwKDO.getZdmj());
                            zrz.setYcjzmj(fwKDO.getYcjzmj());
                            zrz.setScjzmj(fwKDO.getScjzmj());
                            zrz.setDscs(null != fwKDO.getDscs() ? String.valueOf(fwKDO.getDscs()) : null);
                            zrz.setDxcs(null != fwKDO.getDxcs() ? String.valueOf(fwKDO.getDxcs()) : null);
                            zrz.setDxsd(fwKDO.getDxsd());
                            zrz.setZts(fwKDO.getZts());
                            zrz.setBz(fwKDO.getBz());
                        }
                    }
                }
            }
            kttFwZrzList.add(zrz);
        }

        if (CollectionUtils.isNotEmpty(kttFwZrzList)) {
            if (newDefault) {
                for (KttFwZrzDO kttFwZrzDO : kttFwZrzList) {
                    if (qxdmConvert) {
                        //需要根据qxdm进行对照
                        kttFwZrzDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", kttFwZrzDO.getQxdm()));
                    }
                    ClassHandleUtil.setDefaultValueToNullField(kttFwZrzDO);
                }
            }
            setQjData(dataModel, kttFwZrzList);
        }


        return dataModel;
    }

    /**
     * @param dataModel@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取查询的结果数据
     * @date : 2022/11/22 15:28
     */
    @Override
    public List getQjData(QjsjDataModel dataModel) {
        return dataModel.getKttFwZrzList();
    }

    /**
     * @param dataModel
     * @param dataList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description setQjsj
     * @date : 2022/11/24 14:05
     */
    @Override
    public void setQjData(QjsjDataModel dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setKttFwZrzList(dataList);
        }
    }
}
