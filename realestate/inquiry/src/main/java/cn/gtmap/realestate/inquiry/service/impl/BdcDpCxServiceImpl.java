package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.AccessStatsDto;
import cn.gtmap.gtc.sso.domain.dto.QueryLogCondition;
import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcDpMxtjQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcDpTjQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.PageUtils;
import cn.gtmap.realestate.inquiry.config.BdcDpCxTjConfig;
import cn.gtmap.realestate.inquiry.service.BdcDpCxService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import cn.gtmap.realestate.inquiry.core.mapper.BdcDpCxMapper;


import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/04/07/11:23
 * @Description:
 */
@Service
public class BdcDpCxServiceImpl implements BdcDpCxService {

    private static Logger logger = LoggerFactory.getLogger(BdcDpCxServiceImpl.class);

    public static final String YEAR = "1";
    public static final String MONTH = "2";
    public static final String WEEK = "3";
    public static final String DAY = "4";
    /**
    * 登记类型统计图表，统计类型筛选
    **/
    private static final String DJLXTB_TJLX_DJLX = "1";
    /**
     * 登记小类统计图表，统计类型筛选
     **/
    private static final String DJLXTB_TJLX_DJXL = "2";

    @Autowired
    private LogMessageClient logMessageClient;
    @Autowired
    BdcDpCxMapper bdcDpCxMapper;

    @Autowired
    BdcDpCxTjConfig bdcDpCxTjConfig;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    private Repo repo;

    public  static List<Map> GXBMBZ_ZDLIST = new CopyOnWriteArrayList<>();

    /**
    * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
    * @param bdcDpTjQO
    * @return
    * @description 不动产大屏今日登记类型统计
    **/
    @Override
    public List<BdcDpCxTjDTO> listJrdjlx(BdcDpTjQO bdcDpTjQO) {
        if(StringUtils.isNotBlank(bdcDpTjQO.getQxmc())){
            Map<String,String> qxdmdzMap = bdcDpCxTjConfig.getQxdmmcdz();
            if(MapUtils.isEmpty(qxdmdzMap)){
                throw new AppException("配置的区县代码为空");
            }
            Set<String> keys = qxdmdzMap.keySet();
            String qxmc = bdcDpTjQO.getQxmc();
            for (String key : keys) {
                if(qxmc.equals(qxdmdzMap.get(key))){
                    bdcDpTjQO.setQxdm(key);
                    break;
                }
            }
        }
        return bdcDpCxMapper.listJrdjlx(bdcDpTjQO);
    }

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏发证量数据接口
     */
    @Override
    public BdcDpFzslTjDTO listFzslTj(BdcDpTjQO bdcDpTjQO) {
        dealQueryTime(bdcDpTjQO);
        Map<String,String> qxdmdzMap = bdcDpCxTjConfig.getQxdmmcdz();
        logger.info("配置的区县代码名称对照:{}", JSON.toJSONString(qxdmdzMap));
        List<Map> result = new ArrayList<>();
        if(StringUtils.isNotBlank(bdcDpTjQO.getQxmc())){
            Set<String> keys = qxdmdzMap.keySet();
            String qxmc = bdcDpTjQO.getQxmc();
            for (String key : keys) {
                if(qxmc.equals(qxdmdzMap.get(key))){
                    bdcDpTjQO.setQxdm(key);
                    break;
                }
            }
        }
        if(!MapUtils.isEmpty(qxdmdzMap)){
            List<BdcDpCxTjDTO> fzslTjList = bdcDpCxMapper.listFzslTj(bdcDpTjQO);
            Set<String> keySet = qxdmdzMap.keySet();
            for (String s : keySet) {
                Map resultMap = new HashMap();
                resultMap.put("qxmc",qxdmdzMap.get(s));
                resultMap.put("zssl",0);
                resultMap.put("zmsl",0);
                for(BdcDpCxTjDTO bdcDpCxTjDTO : fzslTjList){
                    if(bdcDpCxTjDTO.getQxdm().equals(s) && bdcDpCxTjDTO.getZslx().equals(CommonConstantUtils.ZSLX_ZS.toString())){
                        resultMap.put("zssl",bdcDpCxTjDTO.getSl());
                    }
                    if(bdcDpCxTjDTO.getQxdm().equals(s) && bdcDpCxTjDTO.getZslx().equals(CommonConstantUtils.ZSLX_ZM.toString())){
                        resultMap.put("zmsl",bdcDpCxTjDTO.getSl());
                    }
                }
                result.add(resultMap);
            }

        }
        List<Object>  xAxisData = new ArrayList<>();
        //证书数量
        List<Integer>  zsslList = new ArrayList<>();
        //证明数量
        List<Integer>  zmslList = new ArrayList<>();
        //发证量占比
        List<BigDecimal> fzlzb = new ArrayList<>();
        for (Map map : result) {
            xAxisData.add(map.get("qxmc"));
            zsslList.add(Integer.parseInt(map.get("zssl").toString()));
            zmslList.add(Integer.parseInt(map.get("zmsl").toString()));

        }
        Integer zszsl = zsslList.stream().reduce(Integer::sum).orElse(0);
        Integer zmzsl = zmslList.stream().reduce(Integer::sum).orElse(0);
        Integer fzzs = zszsl+zmzsl;
        for (int i = 0; i < zmslList.size(); i++) {
            Integer qxfzs = zmslList.get(i)+zsslList.get(i);
            if(qxfzs == 0){
                fzlzb.add(new BigDecimal(0));
            }else{
                fzlzb.add(new BigDecimal(qxfzs).divide(new BigDecimal(fzzs),3,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
            }
        }
        BdcDpFzslTjDTO bdcDpFzslTjDTO = new BdcDpFzslTjDTO();
        bdcDpFzslTjDTO.setFzlzb(fzlzb);
        bdcDpFzslTjDTO.setxAxisData(xAxisData);
        bdcDpFzslTjDTO.setZmslList(zmslList);
        bdcDpFzslTjDTO.setZsslList(zsslList);
        bdcDpFzslTjDTO.setFzzs(fzzs);
        return bdcDpFzslTjDTO;
    }

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏抵押融资数据接口
     */
    @Override
    public BdcDyrzTjDTO getTyrzTj(BdcDpTjQO bdcDpTjQO) {
        if(StringUtils.isNotBlank(bdcDpTjQO.getKssj()) || StringUtils.isNotBlank(bdcDpTjQO.getJssj())){
            bdcDpTjQO.setNf(StringUtils.EMPTY);
        }
        if(StringUtils.isNotBlank(bdcDpTjQO.getQxmc())){
            Map<String,String> qxdmdzMap = bdcDpCxTjConfig.getQxdmmcdz();
            logger.info("配置的区县代码名称对照:{}", JSON.toJSONString(qxdmdzMap));
            Set<String> keys = qxdmdzMap.keySet();
            List<String> qxdmList = new ArrayList<>();
            String qxmc = bdcDpTjQO.getQxmc();
            for (String key : keys) {
                if(qxmc.equals(qxdmdzMap.get(key))){
                    qxdmList.add(key);
                }
            }
            bdcDpTjQO.setQxdmList(qxdmList);
        }
        List<BdcDpCxTjDTO> dyzje = bdcDpCxMapper.listDyzje(bdcDpTjQO);
        List<Map> dyjeByMonth = bdcDpCxMapper.listDyjeByMonth(bdcDpTjQO);
        BdcDyrzTjDTO bdcDyrzTjDTO = new BdcDyrzTjDTO();
        bdcDyrzTjDTO.setDyzje(dyzje);
        bdcDyrzTjDTO.setZztsdyjeList(dyjeByMonth);
        return bdcDyrzTjDTO;
    }

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取住宅统计数据接口
     */
    @Override
    public BdcDpCxZzTjDTO getZzTj(BdcDpTjQO bdcDpTjQO) {
        Map<String,String> qxdmdzMap = bdcDpCxTjConfig.getQxdmmcdz();
        if(MapUtils.isEmpty(qxdmdzMap)){
            throw new AppException("配置的区县代码为空");
        }
        Set<String> keys = qxdmdzMap.keySet();
        bdcDpTjQO.setQxdmList(new ArrayList<>(keys));
        if(StringUtils.isNotBlank(bdcDpTjQO.getQxmc())){
            ArrayList<String>  qxdmList = new ArrayList<>();
            String qxmc = bdcDpTjQO.getQxmc();
            for (String key : keys) {
                if(qxmc.equals(qxdmdzMap.get(key))){
                    qxdmList.add(key);
                    bdcDpTjQO.setQxdm(key);
                    break;
                }
            }
            bdcDpTjQO.setQxdmList(qxdmList);
        }

        bdcDpTjQO.setQszt(CommonConstantUtils.QSZT_VALID.toString());
        Date startTime = new Date();
        List<BdcDpCxTjDTO> bdcDpCxTjDTOS = bdcDpCxMapper.listZzTj(bdcDpTjQO);
        long time = System.currentTimeMillis() - startTime.getTime();
        logger.info("区县住宅统计查询用时，{}" ,time);
        bdcDpTjQO.setDybdclxList(Arrays.asList(CommonConstantUtils.DYBDCLX_FDYT.toString(),CommonConstantUtils.DYBDCLX_CTD.toString()));
        List<BdcDpCxTjDTO> dyzjeList = bdcDpCxMapper.listDyzje(bdcDpTjQO);
        if(CollectionUtils.isNotEmpty(bdcDpCxTjDTOS)){
            bdcDpCxTjDTOS = bdcDpCxTjDTOS.stream().filter(o -> keys.contains(o.getQxdm())).collect(Collectors.toList());
            for (BdcDpCxTjDTO bdcDpCxTjDTO : bdcDpCxTjDTOS) {
                if(bdcDpCxTjDTO.getMj()==null){
                    bdcDpCxTjDTO.setMj(BigDecimal.ZERO);
                }
                if(bdcDpCxTjDTO.getTs() == null){
                    bdcDpCxTjDTO.setTs(BigDecimal.ZERO);
                }
            }
        }

        BigDecimal mjSum = bdcDpCxTjDTOS.stream().map(x -> x.getMj()).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal tcSum = bdcDpCxTjDTOS.stream().map(x -> x.getTs()).reduce(BigDecimal.ZERO,BigDecimal::add);
        BdcDpCxZzTjDTO bdcDpCxZzTjDTO = new BdcDpCxZzTjDTO();
        if(mjSum.compareTo(BigDecimal.ZERO)==0){
            bdcDpCxZzTjDTO.setZzzmj(BigDecimal.ZERO);
            bdcDpCxZzTjDTO.setZzjtmj(BigDecimal.ZERO);
        }else{
            bdcDpCxZzTjDTO.setZzzmj(mjSum.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP));
            bdcDpCxZzTjDTO.setZzjtmj(mjSum.divide(tcSum,2,BigDecimal.ROUND_HALF_UP));
        }
        if(tcSum.compareTo(BigDecimal.ZERO)==0) {
            bdcDpCxZzTjDTO.setZzzts(BigDecimal.ZERO);
        }else{
            bdcDpCxZzTjDTO.setZzzts(tcSum.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP));
        }
        bdcDpCxZzTjDTO.setZzfbqkList(bdcDpCxTjDTOS);
        bdcDpCxTjDTOS.forEach(bdcDpCxTjDTO -> {
            bdcDpCxTjDTO.setMj(bdcDpCxTjDTO.getMj().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP));
            bdcDpCxTjDTO.setQxmc(qxdmdzMap.get(bdcDpCxTjDTO.getQxdm()));
        });


        if(CollectionUtils.isNotEmpty(dyzjeList)){
            bdcDpCxZzTjDTO.setDyzje(dyzjeList.stream().map(x -> x.getJe()).reduce(BigDecimal.ZERO,BigDecimal::add));
        }else{
            bdcDpCxZzTjDTO.setDyzje(BigDecimal.ZERO);
        }
        return bdcDpCxZzTjDTO;
    }

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取共享次数情况接口
     */
    @Override
    public BdcDpGxcsTjDTO getGxcsTj(BdcDpTjQO bdcDpTjQO) {
        BdcDpGxcsTjDTO bdcDpGxcsTjDTO = new BdcDpGxcsTjDTO();
        List<Map> mapList = bdcDpCxMapper.countBdcDsfLogByGxbmbz(bdcDpTjQO);
        // 转换查询条件
        List<QueryLogCondition> conditions = new ArrayList<>();
        Map<String, Object> gxbmMap = new HashMap<>();
        gxbmMap = initGxbmMap(JSONObject.class);

        Long begin = getQueryTime(bdcDpTjQO.getKssj());
        // 结束时间
        Long end = getQueryTime(bdcDpTjQO.getKssj());
        // 获取总数
        AccessStatsDto accessStatsDto = logMessageClient.statisticLog("gxbmbz", "DSFJK", null, begin, end, conditions);
        for (String key : gxbmMap.keySet()) {
            Map<String, Integer> details = accessStatsDto.getDetails();
            if (details.containsKey(key)) {
                ((JSONObject) gxbmMap.get(key)).put("ZS", details.get(key));
            } else {
                ((JSONObject) gxbmMap.get(key)).put("ZS", 0);
            }
        }
        //共享总次数
        BigDecimal gxzcs = new BigDecimal(0);
        List<Map> gzList = new ArrayList<>();
        Iterator<Map.Entry<String, Object>> iterator = gxbmMap.entrySet().iterator();
        Map<String, Map> dbMap = mapList.stream().collect(Collectors.toMap(map -> (String) map.get("GXBMBZ"), map -> map));
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            JSONObject value = (JSONObject) entry.getValue();
            if (dbMap.get(key) != null) {
                BigDecimal zs = (BigDecimal) dbMap.get(key).get("ZS");
                value.put("ZS", value.getInteger("ZS") + zs.intValue());
                value.put("GXBMBZ", key);
            } else {
                value.put("GXBMBZ", key);
            }
            gzList.add(value);
            gxzcs = gxzcs.add(new BigDecimal(value.getInteger("ZS")));
        }
        List<Map> sortByZsList = gzList.stream().sorted((map1,map2)->((Integer)map2.get("ZS")).compareTo((Integer)map1.get("ZS"))).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(sortByZsList)){
            if (sortByZsList.size() > 5){
                bdcDpGxcsTjDTO.setGxxq(sortByZsList.subList(0,4));
            }else{
                bdcDpGxcsTjDTO.setGxxq(sortByZsList);
            }
        }
        bdcDpGxcsTjDTO.setGxzcs(gxzcs);
        return bdcDpGxcsTjDTO;
    }

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取上报情况接口
     */
    @Override
    public Object getSbqk(BdcDpTjQO bdcDpTjQO) {
        Map result = new HashMap();
        Map pfjkParams = new HashMap();
        if(StringUtils.isBlank(bdcDpTjQO.getKssj())){
            bdcDpTjQO.setKssj(DateUtils.formateDateToString(new Date(),DateUtils.DATE_FORMAT_2));
        }
        if(StringUtils.isBlank(bdcDpTjQO.getJssj())){
            bdcDpTjQO.setJssj(DateUtils.formateDateToString(new Date(),DateUtils.DATE_FORMAT_2));
        }
        pfjkParams.put("kssj",bdcDpTjQO.getKssj());
        pfjkParams.put("jssj",bdcDpTjQO.getJssj());
        logger.info("查询上报情况调用省级平台得分情况参数：{}",pfjkParams);
        Object response = exchangeInterfaceFeignService.sjptRequestInterface("sjpt_zlpf", pfjkParams);
        List<SjzlpfResponseDTO> qxpfList = getQxpfList(response,true);
        //上报量
        BigDecimal sbl = qxpfList.stream().map(x -> new BigDecimal(x.getJrdbl())).reduce(BigDecimal.ZERO,BigDecimal::add);
        //上报失败量
        BigDecimal sbsbl = qxpfList.stream().map(x ->new BigDecimal(x.getWcgjrdbl())).reduce(BigDecimal.ZERO,BigDecimal::add);
        //接入量
        BigDecimal jrl = qxpfList.stream().map(x ->new BigDecimal(x.getJrywl())).reduce(BigDecimal.ZERO,BigDecimal::add);
        result.put("sbl",sbl);
        result.put("sbsbl",sbsbl);
        result.put("jrl",jrl);
        bdcDpTjQO.setCgbs("2");
        //失败数量
        Map sbsl = bdcDpCxMapper.getJrsbjl(bdcDpTjQO);
        result.put("jrsb",sbsl.get("SL"));
        bdcDpTjQO.setCgbs("0");
        //未响应数量
        Map wxysl = bdcDpCxMapper.getJrsbjl(bdcDpTjQO);
        result.put("wxysl",wxysl.get("SL"));
        //已响应数量
        BigDecimal yxysl  = BigDecimal.ZERO;
        if(jrl.compareTo(BigDecimal.ZERO)==0){
            yxysl = BigDecimal.ZERO;
        }else{
            yxysl = jrl.subtract((BigDecimal) wxysl.get("SL"));
        }
        result.put("yxysl",yxysl);
        return result;
    }

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取规则检查情况接口
     */
    @Override
    public Object getGzjcgk(BdcDpTjQO bdcDpTjQO) {
        Map result = new HashMap();
        bdcDpTjQO.setJcdj("1");
        BdcDpCxTjDTO jczs = bdcDpCxMapper.getJcxmsl(bdcDpTjQO);
        BdcDpCxTjDTO zmcws = bdcDpCxMapper.getZmgzjcsl(bdcDpTjQO);
        result.put("jczs",jczs.getSl());
        result.put("zmcws",zmcws.getSl());
        return result;
    }

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取本期得分情况接口
     */
    @Override
    public Object getBqdfqk(BdcDpTjQO bdcDpTjQO) {
        Map resultMap = new HashMap();
        HashMap paramMap = new HashMap();
        //开始时间和结束时间为空，则根据月份，
        if(StringUtils.isBlank(bdcDpTjQO.getKssj()) && StringUtils.isBlank(bdcDpTjQO.getJssj()) ){
            if(StringUtils.isNotBlank(bdcDpTjQO.getYf())){
                Date kssj = getBeginTime(Integer.parseInt(DateUtils.getCurrYear()),Integer.parseInt(bdcDpTjQO.getYf()));
                Date jssj = getEndTime(Integer.parseInt(DateUtils.getCurrYear()),Integer.parseInt(bdcDpTjQO.getYf()));
                paramMap.put("kssj",DateUtils.formateDateToString(kssj,DateUtils.DATE_FORMAT_2));
                paramMap.put("jssj",DateUtils.formateDateToString(jssj,DateUtils.DATE_FORMAT_2));
            }
        }else{
            paramMap.put("kssj",bdcDpTjQO.getKssj());
            paramMap.put("jssj",bdcDpTjQO.getJssj());
        }
        logger.info("调用省级平台得分情况参数11：{}",paramMap);
        Object response = exchangeInterfaceFeignService.sjptRequestInterface("sjpt_zlpf", paramMap);
        List<SjzlpfResponseDTO> qxpfList = getQxpfList(response,true);
        if(CollectionUtils.isNotEmpty(qxpfList)){
            qxpfList = qxpfList.stream().sorted(Comparator.comparing(SjzlpfResponseDTO::getDf,(x,y)->{
                BigDecimal a = new BigDecimal(x);
                BigDecimal b = new BigDecimal(y);
                if(a.subtract(b).compareTo(BigDecimal.ZERO)>0){
                    return -1;
                }else{
                  return 1;
                }
            })).collect(Collectors.toList());
            //前三名得分情况集合
            List<Map<String,String>> qsmdfqk = new ArrayList<>();
            //接入量集合
            List<String> jrlList = new ArrayList<>();
            //区县名称集合
            List<String> xAxisData = new ArrayList<>();
            //成功率
            List cgl = new ArrayList();
            //上报量
            List sbl = new ArrayList();
            for (int i = 0; i < qxpfList.size(); i++) {
                HashMap map = new HashMap();
                map.put("df",qxpfList.get(i).getDf());
                map.put("qxmc",qxpfList.get(i).getQhmc());
                qsmdfqk.add(map);
                double value = Double.parseDouble(qxpfList.get(i).getWcgjrzb().replace("%",""));
                jrlList.add(qxpfList.get(i).getJrywl());
                xAxisData.add(qxpfList.get(i).getQhmc());
                sbl.add(qxpfList.get(i).getJrdbl());
                cgl.add(new BigDecimal(100).subtract(new BigDecimal(value)).setScale(2,BigDecimal.ROUND_HALF_UP));
            }
            resultMap.put("qsmdfqk",qsmdfqk);
            resultMap.put("jrl",jrlList);
            resultMap.put("cgl",cgl);
            resultMap.put("sbl",sbl);
            resultMap.put("xAxisData",xAxisData);
            return resultMap;
        }

        return null;
    }

    /**
     * @param bdcDpMxtjQO 查询参数
     * @param pageable 分页信息
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏登记类型详细台账获取表格信息接口
     */
    @Override
    public Page<BdcDpDjlxtjMxDTO> getDjlxtjMx(Pageable pageable,BdcDpMxtjQO bdcDpMxtjQO) {
        String json = JSONObject.toJSONString(bdcDpMxtjQO);
        Map<String, Object> map = PageUtils.pageableSort(pageable, JSONObject.parseObject(json, HashMap.class));
        return repo.selectPaging("getDjlxtjMxByPage", map, pageable);
    }

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @date 2022/7/1 18:12
     * @param bdcDpMxtjQO
     * @return List<BdcDpCxTjDTO>
     * @description  大屏今日登记类型图表统计
     **/
    @Override
    public List<BdcDpDjlxtjMxDTO> getDjlxtjTb(BdcDpMxtjQO bdcDpMxtjQO) {
        //按照登记类型分组
        if(DJLXTB_TJLX_DJLX.equals(bdcDpMxtjQO.getTjlx())){
            return bdcDpCxMapper.listDjlx(bdcDpMxtjQO);
        }else if(DJLXTB_TJLX_DJXL.equals(bdcDpMxtjQO.getTjlx())){
            return bdcDpCxMapper.listDjxl(bdcDpMxtjQO);
        }else{
            return bdcDpCxMapper.listDjxlXmly(bdcDpMxtjQO);
        }

    }

    /**
    * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
    * @date 2022/7/1 18:12
    * @param bdcDpTjQO
    * @return List<BdcDpCxTjDTO>
    * @description  大屏今日登记类型穿透效果，按照 “流程名称/登记小类”统计，
    **/
    @Override
    public Object getLctjByDjlx(BdcDpTjQO bdcDpTjQO) {
        if(StringUtils.isNotBlank(bdcDpTjQO.getQxmc())){
            Map<String,String> qxdmdzMap = bdcDpCxTjConfig.getQxdmmcdz();
            if(MapUtils.isEmpty(qxdmdzMap)){
                throw new AppException("配置的区县代码为空");
            }
            Set<String> keys = qxdmdzMap.keySet();
            String qxmc = bdcDpTjQO.getQxmc();
            for (String key : keys) {
                if(qxmc.equals(qxdmdzMap.get(key))){
                    bdcDpTjQO.setQxdm(key);
                    break;
                }
            }
        }
        return bdcDpCxMapper.getLctjByDjlx(bdcDpTjQO);
    }

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @date 2022/7/5 11:37
     * @param bdcDpMxtjQO
     * @return 发证量统计
     * @description
     **/
    @Override
    public List<BdcDpFzltjMxDTO> getFzltjTb(BdcDpMxtjQO bdcDpMxtjQO) {
        return bdcDpCxMapper.getFzltjTb(bdcDpMxtjQO);
    }

    /**
     * @param bdcDpMxtjQO 查询参数
     * @param pageable 分页信息
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏发证量详细台账获取表格信息接口
     */
    @Override
    public Page<BdcDpFzltjMxDTO> getFztjMx(Pageable pageable,BdcDpMxtjQO bdcDpMxtjQO) {
        return repo.selectPaging("getFztjMx", bdcDpMxtjQO, pageable);
    }

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @date 2022/7/5 11:37
     * @param bdcDpTjQO
     * @return 抵押融资详细台账的图表统计接口
     * @description
     **/
    @Override
    public Object getDyjetjTb(BdcDpTjQO bdcDpTjQO) {
        List<Map> maps = bdcDpCxMapper.listDyjeByMonth(bdcDpTjQO);
        return maps;
    }

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @date 2022/7/5 11:37
     * @param bdcDpMxtjQO
     * @return 抵押融资详细台账的表格统计接口
     * @description
     **/
    @Override
    public Page<BdcDpDyjeMxDTO> getDyjetjMx(Pageable pageable,BdcDpMxtjQO bdcDpMxtjQO) {
        String json = JSONObject.toJSONString(bdcDpMxtjQO);
        Map<String, Object> map = PageUtils.pageableSort(pageable, JSONObject.parseObject(json, HashMap.class));
        return repo.selectPaging("getDyjetjMxByPage", bdcDpMxtjQO, pageable);
    }

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取省级接入质量评分接口
     */
    @Override
    public List<SjzlpfResponseDTO> getQualityScore(BdcDpTjQO bdcDpTjQO) {
        HashMap paramMap = new HashMap();
        paramMap.put("kssj",bdcDpTjQO.getKssj());
        paramMap.put("jssj",bdcDpTjQO.getJssj());
        if(StringUtils.isNotBlank(bdcDpTjQO.getYf())){
            Date kssj = getBeginTime(Integer.parseInt(DateUtils.getCurrYear()),Integer.parseInt(bdcDpTjQO.getYf()));
            Date jssj = getEndTime(Integer.parseInt(DateUtils.getCurrYear()),Integer.parseInt(bdcDpTjQO.getYf()));
            paramMap.put("kssj",DateUtils.formateDateToString(kssj,DateUtils.DATE_FORMAT_2));
            paramMap.put("jssj",DateUtils.formateDateToString(jssj,DateUtils.DATE_FORMAT_2));
        }
        logger.info("调用省级平台得分情况参数：{}",paramMap);
        Object response = exchangeInterfaceFeignService.sjptRequestInterface("sjpt_zlpf", paramMap);
        List<SjzlpfResponseDTO> qxpfList = getQxpfList(response,false);
        return qxpfList;
    }


    /**
    * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
    * @date 2022/5/13 10:49
    * @param
    * @return
    * @description
    **/
    private List<SjzlpfResponseDTO> getQxpfList(Object response,boolean getChildren){
        List<SjzlpfResponseDTO> list = new ArrayList<>();
        JSONArray rows = null;
        if (response != null) {
            logger.info("质量评分接口调用成功，响应内容：{}", JSONObject.toJSONString(response));
            JSONObject content = JSONObject.parseObject(JSONObject.toJSONString(response));
            String jsonString = JSONObject.toJSONString(response);
            JSONObject headJsonObject = content.getJSONObject("head");
            if(!"0000".equals(headJsonObject.getString("code"))){
                throw new AppException(headJsonObject.getString("msg"));
            }
            if (content.getJSONObject("data") != null) {
                rows = content.getJSONObject("data").getJSONArray("rows");
                if(rows!= null){

                    List<SjzlpfResponseDTO> sjzlpfResponseDTOS = JSONArray.parseArray(rows.toJSONString(), SjzlpfResponseDTO.class);
                    List<SjzlpfResponseDTO> children = sjzlpfResponseDTOS.get(0).getChildren();
                    if(getChildren){
                        if(CollectionUtils.isNotEmpty(children)){
                            return children;
                        }
                    }else{
                        if(CollectionUtils.isNotEmpty(children)){
                            sjzlpfResponseDTOS.addAll(children);
                            return sjzlpfResponseDTOS;
                        }
                    }

                }
            }
        }
        return list;
    }

    /**
     * @return 大屏查询统计配置信息
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取大屏查询统计配置信息
     */
    @Override
    public Object getBdcDpConfig() {
        return bdcDpCxTjConfig;
    }


    private Map<String, Object> initGxbmMap(Class valueClass) {
        Map<String, Object> gxbmMap = new HashMap();
        if (CollectionUtils.isEmpty(GXBMBZ_ZDLIST)) {
                GXBMBZ_ZDLIST = bdcZdFeignService.queryBdcZd("gxbmbz");
        }
        try {
            if (CollectionUtils.isNotEmpty(GXBMBZ_ZDLIST)) {
                for (Map zdMap : GXBMBZ_ZDLIST) {
                    gxbmMap.put(MapUtils.getString(zdMap, "DM"), valueClass.newInstance());
                    ((JSONObject) gxbmMap.get(MapUtils.getString(zdMap, "DM"))).put("MC", MapUtils.getString(zdMap, "MC"));
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return gxbmMap;
    }

    /**
     * 处理查询时间
     *
     * @param bdcDpTjQO
     */
    public static void dealQueryTime(BdcDpTjQO bdcDpTjQO) {
        if (StringUtils.isNotBlank(bdcDpTjQO.getKssj()) || StringUtils.isNotBlank(bdcDpTjQO.getJssj())) {
            return;
        }
        if (Objects.isNull(bdcDpTjQO.getSjfw())) {
            return;
        }
        //近一年
        if (bdcDpTjQO.getSjfw().equals(YEAR)) {
            bdcDpTjQO.setKssj(DateUtils.formateDateToString(DateUtils.getFirstDayOfYear(),DateUtils.DATE_FORMAT_2));
            bdcDpTjQO.setJssj(DateUtils.formateDateToString(new Date(),DateUtils.DATE_FORMAT_2));
        }

        if (bdcDpTjQO.getSjfw().equals(MONTH)) {
            bdcDpTjQO.setKssj(DateUtils.formateDateToString(DateUtils.getFirstDayOfMonth(new Date()),DateUtils.DATE_FORMAT_2));
            bdcDpTjQO.setJssj(DateUtils.formateDateToString(new Date(),DateUtils.DATE_FORMAT_2));
        }

        if (bdcDpTjQO.getSjfw().equals(WEEK)) {
            bdcDpTjQO.setKssj(DateUtils.formateDateToString(DateUtils.getFirstDayOfWeek(new Date()),DateUtils.DATE_FORMAT_2));
            bdcDpTjQO.setJssj(DateUtils.formateDateToString(new Date(),DateUtils.DATE_FORMAT_2));
        }

        if (bdcDpTjQO.getSjfw().equals(DAY)) {
            bdcDpTjQO.setKssj(DateUtils.formateDateToString(new Date(),DateUtils.DATE_FORMAT_2));
            bdcDpTjQO.setJssj(DateUtils.formateDateToString(new Date(),DateUtils.DATE_FORMAT_2));
        }
    }

    public static Date getBeginTime(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate localDate = yearMonth.atDay(1);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }

    public static Date getEndTime(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * @param dateStr
     * @return java.lang.Long
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取查询时间
     */
    private Long getQueryTime(String dateStr) {
        Long temp = null;
        if (StringUtils.isNotBlank(dateStr)) {
            Date beginDate = DateUtils.formatDate(dateStr);
            if (beginDate != null) {
                temp = beginDate.getTime();
            }
        }
        return temp;
    }
}
