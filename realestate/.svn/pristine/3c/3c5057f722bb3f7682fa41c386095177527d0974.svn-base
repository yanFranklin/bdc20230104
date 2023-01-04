package cn.gtmap.realestate.exchange.service.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.exchange.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.rest.building.FwqsxxRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.*;
import cn.gtmap.realestate.exchange.core.mapper.server.*;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Validated
public class BdcGjbwServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGjbwServiceImpl.class);

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    CommonService commonService;

    @Autowired
    KttFwCMapper kttFwCMapper;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Autowired
    private KttFwLjzMapper kttFwLjzMapper;

    @Autowired
    private KttFwHMapper kttFwHMapper;

    @Autowired
    private QlfQlTdsyqMapper qlfQlTdsyqMapper;

    @Autowired
    private QlfQlJsydsyqMapper jsydsyqMapperImpl;

    @Autowired
    KttFwZrzMapper kttFwZrzMapper;

    @Autowired
    KttZdjbxxMapper kttZdjbxxMapper;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    BdcZsFeignService bdcZsFeignService;

    @Autowired
    ZttGyQlrMapper zttGyQlrMapper;

    @Autowired
    FwqsxxRestService fwqsxxRestService;

    public JSONArray queryFwC(String bdcdyh) {
        try {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setBdcdyh(bdcdyh);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            //bdcdyh = "340124162011GB00026F00010001";
            List<KttFwCDO> kttFwCList = fwqsxxRestService.queryQsxxKttFwCList(bdcdyh);
            JSONArray fwCJSONArray = new JSONArray();
            if (CollectionUtils.isNotEmpty(kttFwCList)) {
                kttFwCList.forEach(KttFwCDO -> {
                    JSONObject upperfwC = convertKeyToUppercase(JSONObject.parseObject(JSONObject.toJSONString(KttFwCDO)));
                    upperfwC.put("SEQID_W", "");
                    if(Objects.nonNull(bdcXmDOList.get(0).getDjsj())) {
                        upperfwC.put("CREATEDATETIME", DateUtils.formateYmdhms(bdcXmDOList.get(0).getDjsj()));
                    }else if(Objects.nonNull(bdcXmDOList.get(0).getSlsj())){
                        upperfwC.put("CREATEDATETIME", DateUtils.formateYmdhms(bdcXmDOList.get(0).getSlsj()));
                    }
                    fwCJSONArray.add(upperfwC);
                });
            }
            return fwCJSONArray;
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public JSONArray queryFwH(String bdcdyh) {
        try {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setBdcdyh(bdcdyh);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);

            BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(bdcXmDOList.get(0).getXmid());
            boolean sfdz = false;
            if (bdcXmDO != null && CommonConstantUtils.BDCDYFWLX_DUZH.equals(bdcXmDO.getBdcdyfwlx())) {
                sfdz = true;
            }
            //bdcdyh = "340104000000GB00000F31340011";
            List<KttFwHDO> kttFwHList = fwqsxxRestService.queryQsxxKttFwHList(bdcdyh, sfdz);
            for (KttFwHDO kttFwHDO : kttFwHList) {
                List<Map> zdMapList = bdcZdFeignService.queryBdcZd("fwyt");
                if (CollectionUtils.isNotEmpty(zdMapList)) {
                    for (Map zdMap : zdMapList) {
                        if (StringUtils.equals(kttFwHDO.getFwyt1(), MapUtils.getString(zdMap, "DM"))) {
                            if (Objects.nonNull(zdMap.get("MC"))) {
                                kttFwHDO.setFwyt1(zdMap.get("MC").toString());
                            }
                        }
                        if (StringUtils.equals(kttFwHDO.getFwyt2(), MapUtils.getString(zdMap, "DM"))) {
                            if (Objects.nonNull(zdMap.get("MC"))) {
                                kttFwHDO.setFwyt2(zdMap.get("MC").toString());
                            }
                        }
                        if (StringUtils.equals(kttFwHDO.getFwyt3(), MapUtils.getString(zdMap, "DM"))) {
                            if (Objects.nonNull(zdMap.get("MC"))) {
                                kttFwHDO.setFwyt3(zdMap.get("MC").toString());
                            }
                        }
                    }
                }
            }

            JSONArray fwHJSONArray = new JSONArray();
            if (CollectionUtils.isNotEmpty(kttFwHList)) {
                kttFwHList.forEach(KttFwHOldDO -> {
                    JSONObject upperFwh = convertKeyToUppercase(JSONObject.parseObject(JSONObject.toJSONString(KttFwHOldDO)));
                    upperFwh.put("SEQID_W", "");
                    if(Objects.nonNull(bdcXmDOList.get(0).getDjsj())) {
                        upperFwh.put("CREATEDATETIME", DateUtils.formateYmdhms(bdcXmDOList.get(0).getDjsj()));
                    }
                    upperFwh.put("JZWZT", null);
                    upperFwh.put("FWCX", null);
                    upperFwh.put("FWXZMC", null);
                    upperFwh.put("HX", null);
                    upperFwh.put("FWLXMC", null);
                    upperFwh.put("ZDDM", null);
                    fwHJSONArray.add(upperFwh);
                });
            }
            return fwHJSONArray;
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public JSONArray queryLjz(String bdcdyh) {
        try {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setBdcdyh(bdcdyh);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            //bdcdyh = "340104483021GB00041F0011";
            List<KttFwLjzDO> kttFwLjzDOS = fwqsxxRestService.queryQsxxKttFwLjzList(bdcdyh);
            JSONArray ljzJSONArray = new JSONArray();
            if (CollectionUtils.isNotEmpty(kttFwLjzDOS)) {
                kttFwLjzDOS.forEach(kttFwLjzOldDO -> {
                    JSONObject upperLjz = convertKeyToUppercase(JSONObject.parseObject(JSONObject.toJSONString(kttFwLjzOldDO)));
                    upperLjz.put("SEQID_W", "");
                    upperLjz.put("CREATEDATETIME", DateUtils.formateYmdhms(bdcXmDO.getDjsj()));
                    upperLjz.put("FWYT2", "");
                    upperLjz.put("FWYT3", "");
                    upperLjz.put("YTMC", "");
                    upperLjz.put("BZ", bdcXmDO.getBz());
                    upperLjz.put("FWJG2", "");
                    upperLjz.put("FWJG3", "");
                    ljzJSONArray.add(convertKeyToUppercase(upperLjz));
                });
            }
            return ljzJSONArray;
        } catch (Exception e) {
            return new JSONArray();
        }
    }

    public JSONArray queryZrz(String bdcdyh) {
        try {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setBdcdyh(bdcdyh);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            //bdcdyh = "340102201001GB00005F99990001";
            List<Map> mapList = fwqsxxRestService.queryQsxxKttFwZrzList(bdcdyh);
            JSONArray zrzJSONArray = new JSONArray();
            if (CollectionUtils.isNotEmpty(mapList)) {
                for (Map zrzMap : mapList) {
                    String jgsj = MapUtils.getString(zrzMap, "JGRQ");
                    zrzMap.remove("JGRQ");
                    KttFwZrzOldDO zrz = JSONObject.parseObject(JSONObject.toJSONString(zrzMap), KttFwZrzOldDO.class);
                    if (StringUtils.isBlank(jgsj)) {
                        Date jgsjDate = DateUtils.formatDate(jgsj);
                        zrz.setJgrq(jgsjDate);
                    } else {
                        zrz.setJgrq(bdcXmDO.getDjsj());
                    }
                    String bsm = bdcdjMapper.getBsm().toString();
                    if (bsm.length() > 10) {
                        //大于十位取十位，不足则补全，作为唯一标识码固定为10位
                        bsm = bsm.substring(bsm.length() - 10);
                    } else {
                        bsm = String.format("%010d", Integer.valueOf(bsm));
                    }
                    zrz.setBsm(Integer.valueOf(bsm));
                    JSONObject upperZrz = convertKeyToUppercase(JSONObject.parseObject(JSONObject.toJSONString(zrz)));
                    upperZrz.put("SEQID_W", "");
                    upperZrz.put("YSDM", "6001030110");
                    if (Objects.nonNull(bdcXmDO.getDjsj())) {
                        upperZrz.put("CREATEDATETIME", DateUtils.formateYmdhms(bdcXmDO.getDjsj()));
                    } else {
                        upperZrz.put("CREATEDATETIME", null);
                    }
                    zrzJSONArray.add(upperZrz);
                }
            }
            return zrzJSONArray;
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public JSONArray queryZdjbxx(String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            return new JSONArray();
        }
        try {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setBdcdyh(bdcdyh);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);

            JSONArray zdjbxxArray = new JSONArray();
            Map paramMap = new HashMap();
            paramMap.put("bdcdyh", bdcdyh);
            paramMap.put("ywh", bdcXmDOList.get(0).getXmid());
            List<KttZdjbxxOldDO> kttZdjbxxList = kttZdjbxxMapper.queryKttZdjbxxListOld(paramMap);
            if (CollectionUtils.isNotEmpty(kttZdjbxxList)) {
                Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
                for (KttZdjbxxOldDO kttZdjbxx : kttZdjbxxList) {
                    String bsm = this.bdcdjMapper.getBsm().toString();
                    if (bsm.length() > 10) {
                        //作为唯一标识码固定为10位
                        bsm = bsm.substring(bsm.length() - 10);
                    } else {
                        bsm = String.format("%010d", Integer.valueOf(bsm));
                    }
                    kttZdjbxx.setBsm(Integer.valueOf(bsm));
                    JSONObject upperZdjbxx = convertKeyToUppercase(JSONObject.parseObject(JSONObject.toJSONString(kttZdjbxx)));
                    //字典转换
                    if (upperZdjbxx.containsKey("YT")) {
                        String yt = StringToolUtils.convertBeanPropertyValueOfZdByString(upperZdjbxx.getString("YT"), zdMap.get("tdyt"));
                        upperZdjbxx.put("YT", yt);
                        upperZdjbxx.put("YTMC", yt);
                    }
                    zdjbxxArray.add(upperZdjbxx);
                }
            }
            return zdjbxxArray;
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }


    public List<ZttGyQlrDO> queryZttGyQlr(List<String> xmids, String qlrlb) {
        List<ZttGyQlrDO> zttGyQlrDOS = new ArrayList<>();
        HashMap<String, String> map = new HashMap();
        for (String xmid : xmids) {
            map.put("ywh", xmid);
            map.put("qlrlb", qlrlb);
            zttGyQlrDOS.addAll(bdcdjMapper.queryAllZttGyQlrList(map));
        }
        return zttGyQlrDOS;
    }


    /**
     * @return com.alibaba.fastjson.JSONObject
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [destinObject]
     * @description 将JSONObject的KEY改为大写
     */
    private JSONObject convertKeyToUppercase(JSONObject destinObject) {
        if (destinObject == null || CollectionUtils.isEmpty(destinObject.keySet())) {
            return new JSONObject();
        }
        Set<String> keys = destinObject.keySet();
        JSONObject upperCaseDestObject = new JSONObject();
        for (String key : keys) {
            upperCaseDestObject.put(key.toUpperCase(), destinObject.get(key));
        }
        return upperCaseDestObject;
    }

    public <T> JSONArray dozerMapList(List sourceList, String mapId, Class<T> clazz) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return new JSONArray(0);
        }
        JSONArray destinationList = new JSONArray();
        try {
            for (Object sourceObject : sourceList) {
                if (sourceObject == null) {
                    continue;
                }
                T destinObject = clazz.newInstance();
                dozerBeanMapper.map(sourceObject, destinObject, mapId);
                JSONObject upperCaseDestObject = convertKeyToUppercase(JSONObject.parseObject(JSONObject.toJSONString(destinObject)));
                dealSpecialField(sourceObject, upperCaseDestObject);
                destinationList.add(upperCaseDestObject);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("字段对照时出现异常，mapId:" + mapId);
            return new JSONArray(0);
        }
        return destinationList;
    }

    /**
     * @return com.alibaba.fastjson.JSONObject
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [sourceObject, upperCaseDestObject]
     * @description 处理各种权利特殊字段
     */
    private JSONObject dealSpecialField(Object sourceObject, JSONObject upperCaseDestObject) {
        JSONObject sourceJsonObject = JSONObject.parseObject(JSONObject.toJSONString(sourceObject));
        if (StringUtils.isBlank(sourceJsonObject.getString("xmid"))) {
            LOGGER.warn("该对象缺少xmid,无法处理权利特殊字段！该对象为：" + JSONObject.toJSONString(sourceObject));
            return upperCaseDestObject;
        }

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(sourceJsonObject.getString("xmid"));
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            // 添加业务号 为项目id
            upperCaseDestObject.put("YWH", bdcXmDO.getXmid());

            if (sourceObject instanceof BdcFdcqDO) {
                upperCaseDestObject.put("BDCQZH", bdcXmDO.getBdcqzh());
                List<Map> zdMapList = bdcZdFeignService.queryBdcZd("bdclx");
                String bdclx = "";
                if (CollectionUtils.isNotEmpty(zdMapList) && bdcXmDO != null && bdcXmDO.getBdclx() != null) {
                    for (Map map : zdMapList) {
                        if (StringUtils.equals(bdcXmDO.getBdclx().toString(), MapUtils.getString(map, "DM"))) {
                            if (Objects.nonNull(map.get("MC"))) {
                                bdclx = map.get("MC").toString();
                            }
                        }
                    }
                }
                upperCaseDestObject.put("BDCLX", bdclx);
                BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) sourceObject;
                if (bdcFdcqDO.getQszt() != null && CommonConstantUtils.QSZT_VALID.equals(bdcFdcqDO.getQszt())) {
                    upperCaseDestObject.put("QLXZ", bdcXmDO.getQlxz());
                }
            }

            if (sourceObject instanceof BdcDyaqDO) {
                upperCaseDestObject.put("BDCDJZMH", bdcXmDO.getBdcqzh());
                // 增加抵押人（"DYR"），为抵押的义务人。
                addQlrAndDyxx(upperCaseDestObject, bdcXmDO);
            }
            if (sourceObject instanceof BdcYgDO) {
                BdcYgDO bdcYgDO = (BdcYgDO) sourceObject;
                upperCaseDestObject.put("BDCDJZMH", bdcXmDO.getBdcqzh());
                upperCaseDestObject.put("YWR", bdcXmDO.getYwr());
                upperCaseDestObject.put("YWRZJZL", bdcXmDO.getYwrzjzl());
                upperCaseDestObject.put("YWRZJH", bdcXmDO.getYwrzjh());
                List<Map> zdMapList = bdcZdFeignService.queryBdcZd("bdclx");
                String bdclx = "";
                if (CollectionUtils.isNotEmpty(zdMapList) && bdcXmDO != null && bdcXmDO.getBdclx() != null) {
                    for (Map map : zdMapList) {
                        if (StringUtils.equals(bdcXmDO.getBdclx().toString(), MapUtils.getString(map, "DM"))) {
                            if (Objects.nonNull(map.get("MC"))) {
                                bdclx = map.get("MC").toString();
                            }
                        }
                    }
                }
                upperCaseDestObject.put("BDCLX", bdclx);
                // 当预告登记种类是预抵押（3和4）, 预告权利中增加返回ZWLXQSSJ，ZWLXJSSJ，DYFS三个字段
                if (bdcYgDO.getYgdjzl() != null && (bdcYgDO.getYgdjzl().equals(3) || bdcYgDO.getYgdjzl().equals(4))) {
                    upperCaseDestObject.put("DYFS", bdcYgDO.getDyfs());
                    if (null != bdcYgDO.getZwlxqssj()) {
                        upperCaseDestObject.put("ZWLXQSSJ", DateFormatUtils.format(bdcYgDO.getZwlxqssj(), DateUtils.sdf_ymdhms));
                    }
                    if (null != bdcYgDO.getZwlxjssj()) {
                        upperCaseDestObject.put("ZWLXJSSJ", DateFormatUtils.format(bdcYgDO.getZwlxjssj(), DateUtils.sdf_ymdhms));
                    }
                }
                addQlrAndDyxx(upperCaseDestObject, bdcXmDO);
            }

            if (sourceObject instanceof BdcYyDO) {
                upperCaseDestObject.put("BDCDJZMH", bdcXmDO.getBdcqzh());
            }

            if (sourceObject instanceof BdcJsydsyqDO) {
                if (StringUtils.isNotBlank(upperCaseDestObject.getString("BDCDYH")) && upperCaseDestObject.getString("BDCDYH").length() >= 19) {
                    upperCaseDestObject.put("ZDDM", upperCaseDestObject.getString("BDCDYH").substring(0, 19));
                }
                List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(((BdcJsydsyqDO) sourceObject).getXmid());
                if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                    upperCaseDestObject.put("BDCQZH", bdcZsDOList.get(0).getBdcqzh());
                }
            }
        }
        return upperCaseDestObject;
    }

    private void addQlrAndDyxx(JSONObject upperCaseDestObject, BdcXmDO bdcXmDO) {
        BdcQlrQO qlrQO = new BdcQlrQO();
        qlrQO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
        qlrQO.setXmid(bdcXmDO.getXmid());
        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(qlrQO);
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            StringBuilder stringBuilder = new StringBuilder();
            bdcQlrDOList.forEach(bdcQlrDO -> stringBuilder.append(bdcQlrDO.getQlrmc()).append(","));
            String dyr = stringBuilder.toString();
            if (StringUtils.isNotBlank(dyr)) {
                dyr = dyr.substring(0, dyr.length() - 1);
                upperCaseDestObject.put("DYR", dyr);
            }

            qlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcQlrDO> qlrs = bdcQlrFeignService.listBdcQlr(qlrQO);
            if (CollectionUtils.isNotEmpty(qlrs)) {
                String qlrmc = qlrs.stream().map(BdcQlrDO::getQlrmc).collect(Collectors.joining(","));
                String zjh = qlrs.stream().map(BdcQlrDO::getZjh).collect(Collectors.joining(","));
                String zjzl = qlrs.stream().map(bdcQlrDO -> Objects.nonNull(bdcQlrDO.getZjzl()) ? bdcQlrDO.getZjzl().toString() : "")
                        .collect(Collectors.joining(","));
                upperCaseDestObject.put("DYQRMC", qlrmc);
                upperCaseDestObject.put("DYQRZJH", zjh);
                upperCaseDestObject.put("DYQRZJZL", zjzl);
            } else {
                upperCaseDestObject.put("DYQRMC", null);
                upperCaseDestObject.put("DYQRZJH", null);
                upperCaseDestObject.put("DYQRZJZL", null);
            }
        }
        upperCaseDestObject.put("DYWJZ", null);
        upperCaseDestObject.put("DYQJDYWZR", null);
    }
}
