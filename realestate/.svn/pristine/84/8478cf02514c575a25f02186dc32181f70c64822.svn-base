package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.inquiry.core.entity.*;
import cn.gtmap.realestate.inquiry.core.mapper.BdcZhxpCxMapper;
import cn.gtmap.realestate.inquiry.service.BdcZhpSjclService;
import cn.gtmap.realestate.inquiry.util.GetDateUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/10
 * @description 综合屏数据处理
 */
@Service
public class BdcZhpSjclServiceImpl implements BdcZhpSjclService {
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcZhxpCxMapper bdcZhxpCxMapper;
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZhpSjclServiceImpl.class);


    /**
     * {"DATE_TIME":"2019/8/26 10:35:07","DT_NAME":"要素大市场登记大厅","WAIT_NUMBER":"3","BUZ_WINDOW":[{"BUZ_CODE":"A","WINDOW":["1","2","3"],"BUZ_NAME":"一窗受理"},{"BUZ_CODE":"C","WINDOW":["1","2","3","4","5","6"],"BUZ_NAME":"单位不动产登记"},{"BUZ_CODE":"D","WINDOW":["3","4","5","6"],"BUZ_NAME":"个人业务"}],"QUEUE":[{"CALL_NUMBER":"A001","BUZ_CODE":"A","BUZ_NAME":"一窗受理"},{"CALL_NUMBER":"A002","BUZ_CODE":"A","BUZ_NAME":"一窗受理"},{"CALL_NUMBER":"A003","BUZ_CODE":"A","BUZ_NAME":"一窗受理"}]}
     */
    @Override
    public synchronized void dlxx(String jsonString) {
        if (StringUtils.isNotEmpty(jsonString)) {
            Map jsonMap = JSONObject.parseObject(jsonString, Map.class);

            // 查询日志信息
            Example example = new Example(BdcHjxxLogDO.class);
            example.createCriteria().andEqualTo("scsj", MapUtils.getString(jsonMap, "DATE_TIME"))
                    .andEqualTo("ts", MapUtils.getString(jsonMap, "WAIT_NUMBER"))
                    .andEqualTo("zxmc", MapUtils.getString(jsonMap, "DT_NAME"));
            List loglist = entityMapper.selectByExample(example);

            // 仅当数据库中没有日志信息时进行更新 并插入日志
            if (CollectionUtils.isEmpty(loglist)) {
                BdcHjxxLogDO bdcHjxxLog = new BdcHjxxLogDO();
                bdcHjxxLog.setLogid(UUIDGenerator.generate());
                bdcHjxxLog.setTs(MapUtils.getString(jsonMap, "WAIT_NUMBER"));
                bdcHjxxLog.setScsj(MapUtils.getString(jsonMap, "DATE_TIME"));
                bdcHjxxLog.setZxmc(MapUtils.getString(jsonMap, "DT_NAME"));

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date pcdate = null;
                try {
                    pcdate = simpleDateFormat.parse(MapUtils.getString(jsonMap, "DATE_TIME"));
                } catch (ParseException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                }

                dlsjcl(MapUtils.getString(jsonMap, "QUEUE"), MapUtils.getString(jsonMap, "DT_NAME"), MapUtils.getString(jsonMap, "BUZ_WINDOW"), pcdate, bdcHjxxLog);
            }
        }
    }

    //jsonString ： queue 呼叫队列； zxmc 中心名称，  buzWindowJson：窗口信息，  pcdate ： 推送时间  ，

    @Override
    public void dlsjcl(String jsonString, String zxmc, String buzWindowJson, Date pcdate, BdcHjxxLogDO bdcHjxxLog) {
        if (StringUtils.isNoneBlank(jsonString, zxmc) && pcdate != null && bdcHjxxLog != null) {
            JSONArray jsonArray = JSONObject.parseArray(jsonString);
            JSONArray buzWindowJsonArray = JSONObject.parseArray(buzWindowJson);

            if (CollectionUtils.isNotEmpty(jsonArray)) {
                // 根据中心名称 和 当前 00:00:00 - 23:59:59 时间,获取呼叫信息
                Example exampleHis = new Example(BdcHjxxDO.class);
                exampleHis.createCriteria().andEqualTo("zxmc", zxmc)
                        .andBetween("scsj", GetDateUtils.getStartTime(), GetDateUtils.getEndTime());
                exampleHis.setOrderByClause("hjh asc");
                List<BdcHjxxDO> bdcHjxxHisList = entityMapper.selectByExample(exampleHis);

                // 获取 BDC_ZXYW 中配置的中心配置 业务名称信息
                Example exampleZxyw = new Example(BdcZxywDO.class);
                exampleZxyw.createCriteria().andEqualTo("zxmc", zxmc);
                List<BdcZxywDO> bdcZxywDOList = entityMapper.selectByExample(exampleZxyw);
                List<String> ywmcList = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(bdcZxywDOList)) {
                    for (BdcZxywDO bdcZxywDO : bdcZxywDOList) {
                        ywmcList.add(bdcZxywDO.getYwmc());
                    }
                }

                List<BdcHjxxDO> bdcHjxxNewList = new ArrayList<>();
                List<BdcZxywDO> bdcZxywNewList = new ArrayList<>();
                List<BdcZxywDO> bdcZxywOldList = new ArrayList<>();

                // 业务名称业务编码对应关系
                List<Map> ywmcYwbmList = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(bdcHjxxHisList)) {
                    // 去除重复数据
                    for (int i = 0; i < jsonArray.size(); i++) {
                        Map map = jsonArray.getObject(i, Map.class);

                        // 处理业务名称、业务编码对应关系
                        boolean cfyw = true;
                        for (Map ywmcYwbm : ywmcYwbmList) {
                            if (StringUtils.equals(MapUtils.getString(ywmcYwbm, "BUZ_NAME"), MapUtils.getString(map, "BUZ_NAME"))) {
                                cfyw = false;
                                List ywbmList = (List) ywmcYwbm.get("BUZ_CODE");
                                if (!ywbmList.contains(MapUtils.getString(map, "BUZ_CODE"))) {
                                    ywbmList.add(MapUtils.getString(map, "BUZ_CODE"));
                                }
                                break;
                            }
                        }
                        if (cfyw) {
                            Map ywbmMap = new HashMap();
                            ywbmMap.put("BUZ_NAME", MapUtils.getString(map, "BUZ_NAME"));
                            List ywbmList = new ArrayList();
                            ywbmList.add(MapUtils.getString(map, "BUZ_CODE"));
                            ywbmMap.put("BUZ_CODE", ywbmList);
                            ywmcYwbmList.add(ywbmMap);
                        }

                        for (int j = 0; j < bdcHjxxHisList.size(); j++) {
                            BdcHjxxDO bdcHjxxHis = bdcHjxxHisList.get(j);
                            if (StringUtils.equals(MapUtils.getString(map, "CALL_NUMBER"), bdcHjxxHis.getHjh())) {
                                jsonArray.remove(map);
                                bdcHjxxHisList.remove(bdcHjxxHis);
                                i--;
                                break;
                            }
                        }
                    }
                }

                // 当存在之前不存在的数据
                if (CollectionUtils.isNotEmpty(jsonArray)) {
                    for (int i = 0; i < jsonArray.size(); i++) {

                        // 叫号队列数据
                        Map map = jsonArray.getObject(i, Map.class);

                        // 处理业务名称、业务编码对应关系
                        boolean cfyw = true;
                        for (Map ywmcYwbm : ywmcYwbmList) {
                            if (StringUtils.equals(MapUtils.getString(ywmcYwbm, "BUZ_NAME"), MapUtils.getString(map, "BUZ_NAME"))) {
                                cfyw = false;
                                List ywbmList = (List) ywmcYwbm.get("BUZ_CODE");
                                if (!ywbmList.contains(MapUtils.getString(map, "BUZ_CODE"))) {
                                    ywbmList.add(MapUtils.getString(map, "BUZ_CODE"));
                                }
                                break;
                            }
                        }
                        if (cfyw) {
                            Map ywbmMap = new HashMap();
                            ywbmMap.put("BUZ_NAME", MapUtils.getString(map, "BUZ_NAME"));
                            List ywbmList = new ArrayList();
                            ywbmList.add(MapUtils.getString(map, "BUZ_CODE"));
                            ywbmMap.put("BUZ_CODE", ywbmList);
                            ywmcYwbmList.add(ywbmMap);
                        }

                        BdcHjxxDO bdcHjxxDO = new BdcHjxxDO();
                        bdcHjxxDO.setHjid(UUIDGenerator.generate());
                        bdcHjxxDO.setHjh(MapUtils.getString(map, "CALL_NUMBER"));
                        bdcHjxxDO.setYwbm(MapUtils.getString(map, "CALL_NUMBER").substring(0, 1));
                        bdcHjxxDO.setYwmc(MapUtils.getString(map, "BUZ_NAME"));
                        bdcHjxxDO.setScsj(pcdate);
                        bdcHjxxDO.setHjzt(0);
                        bdcHjxxDO.setZxmc(zxmc);
                        bdcHjxxNewList.add(bdcHjxxDO);
                    }
                }

                // 处理业务信息
                if (CollectionUtils.isNotEmpty(buzWindowJsonArray) && CollectionUtils.isNotEmpty(ywmcYwbmList)) {
                    // 给对应的业务类型，添加窗口信息
                    for (Map ywmcYwbmMap : ywmcYwbmList) {
                        List ywbmList = (List) ywmcYwbmMap.get("BUZ_CODE");
                        for (int i = 0; i < buzWindowJsonArray.size(); i++) {
                            Map map = buzWindowJsonArray.getObject(i, Map.class);
                            if (ywbmList.contains(MapUtils.getString(map, "BUZ_CODE"))) {
                                List windowList = new ArrayList();
                                if (ywmcYwbmMap.get("WINDOW") != null) {
                                    windowList = (List) ywmcYwbmMap.get("WINDOW");
                                }
                                // 处理数据
                                JSONArray windowArray = JSONObject.parseArray(MapUtils.getString(map, "WINDOW"));
                                windowList = (List) CollectionUtils.union(windowList, windowArray);
                                ywmcYwbmMap.put("WINDOW", windowList);
                            }
                        }
                    }

                    for (Map ywmcYwbmMap : ywmcYwbmList) {
                        List<String> window = (List) ywmcYwbmMap.get("WINDOW");
                        StringBuilder ck = new StringBuilder();
                        if (window != null) {
                            for (String win : window) {
                                ck.append(win).append(",");
                            }
                        }
                        List<String> ywbmList = (List) ywmcYwbmMap.get("BUZ_CODE");
                        StringBuilder ywbm = new StringBuilder();
                        if (ywbmList != null) {
                            for (String bm : ywbmList) {
                                ywbm.append(bm).append(",");
                            }
                        }
                        if (!ywmcList.contains(MapUtils.getString(ywmcYwbmMap, "BUZ_NAME"))) {
                            BdcZxywDO bdcZxywDO = new BdcZxywDO();
                            bdcZxywDO.setYwmc(MapUtils.getString(ywmcYwbmMap, "BUZ_NAME"));
                            bdcZxywDO.setYwbm(ywbm.substring(0, ywbm.length() - 1));
                            bdcZxywDO.setZxmc(zxmc);
                            bdcZxywDO.setCk(ck.substring(0, ck.length() - 1));
                            bdcZxywDO.setCks(window.size());
                            bdcZxywDO.setZxywid(UUIDGenerator.generate());
                            bdcZxywNewList.add(bdcZxywDO);
                        } else {
                            BdcZxywDO bdcZxywDO = null;
                            for (BdcZxywDO zxywDO : bdcZxywDOList) {
                                if (StringUtils.equals(MapUtils.getString(ywmcYwbmMap, "BUZ_NAME"), zxywDO.getYwmc())) {
                                    bdcZxywDO = zxywDO;
                                }
                            }
                            if (bdcZxywDO != null) {
                                bdcZxywDO.setYwbm(ywbm.substring(0, ywbm.length() - 1));
                                bdcZxywDO.setCk(ck.substring(0, ck.length() - 1));
                                bdcZxywDO.setCks(window.size());
                            }
                            bdcZxywOldList.add(bdcZxywDO);
                        }
                    }
                }

                // 更新信息
                entityMapper.insertSelective(bdcHjxxLog);
                if (CollectionUtils.isNotEmpty(bdcHjxxNewList)) {
                    entityMapper.insertBatchSelective(bdcHjxxNewList);
                }
                if (CollectionUtils.isNotEmpty(bdcZxywNewList)) {
                    entityMapper.insertBatchSelective(bdcZxywNewList);
                }
                // 当存在可能已经过号的数据
                if (CollectionUtils.isNotEmpty(bdcHjxxHisList)) {
                    for (BdcHjxxDO bdcHjxxHis : bdcHjxxHisList) {
                        if (bdcHjxxHis.getScsj() != null && bdcHjxxHis.getScsj().before(pcdate)
                                && (bdcHjxxHis.getGxsj() == null || bdcHjxxHis.getGxsj().before(pcdate))) {
                            bdcHjxxHis.setGxsj(pcdate);
                            bdcHjxxHis.setHjzt(1);
                            entityMapper.updateByPrimaryKeySelective(bdcHjxxHis);
                        }
                    }
                }
                // 当存在中心业务更新时
                if (CollectionUtils.isNotEmpty(bdcZxywOldList)) {
                    for (BdcZxywDO bdcZxywDO : bdcZxywOldList) {
                        entityMapper.updateByPrimaryKeySelective(bdcZxywDO);
                    }
                }

                // 更新关系表信息
                updateRel(zxmc);

            }
        }
    }

    //   每次推送时候，我们会先根据 zxmc （中心名称）查询当天BDC_HJXX里面数据（a）。 然后将数据(a)里面移除 Queue 里面的 CALL_NUMBER 的数据， 最后数据(a)里面 scsj < DATE_TIME 和  gxsj < DATE_TIME 的数据更新为已叫号

    private void updateRel(String zxmc) {
        Example exampleZxyw = new Example(BdcZxywDO.class);
        exampleZxyw.createCriteria().andEqualTo("zxmc", zxmc);
        List<BdcZxywDO> bdcZxywDOList = entityMapper.selectByExample(exampleZxyw);

        Example exampleZxywYwbmRel = new Example(BdcZxywYwbmRelDO.class);
        exampleZxywYwbmRel.createCriteria().andEqualTo("zxmc", zxmc);
        List<BdcZxywYwbmRelDO> bdcZxywYwbmRelDOList = entityMapper.selectByExample(exampleZxywYwbmRel);
        // 新业务名称列表
        Map<String, List<String>> xywmcBmMap = new HashMap<>();
        Map<String, List<String>> xywmcCkMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(bdcZxywYwbmRelDOList)) {
            // 获得每个新业务名称对应的编码信息
            for (BdcZxywYwbmRelDO bdcZxywYwbmRelDO : bdcZxywYwbmRelDOList) {
                if (xywmcBmMap.get(bdcZxywYwbmRelDO.getXywmc()) == null) {
                    List<String> ywbm = new ArrayList<>();
                    ywbm.add(bdcZxywYwbmRelDO.getYwbm());
                    xywmcBmMap.put(bdcZxywYwbmRelDO.getXywmc(), ywbm);
                } else {
                    xywmcBmMap.get(bdcZxywYwbmRelDO.getXywmc()).add(bdcZxywYwbmRelDO.getYwbm());
                }
            }
            // 获得总窗口数
            for (BdcZxywDO bdcZxywDO : bdcZxywDOList) {
                for (Map.Entry<String, List<String>> xywmcBm : xywmcBmMap.entrySet()) {
                    if (xywmcBm.getValue().contains(bdcZxywDO.getYwbm())) {
                        List<String> ck;
                        if (xywmcCkMap.get(xywmcBm.getKey()) == null) {
                            ck = new ArrayList<>();
                        } else {
                            ck = xywmcCkMap.get(xywmcBm.getKey());
                        }
                        String[] cks = bdcZxywDO.getCk().split(",");
                        for (int i = 0; i < cks.length; i++) {
                            if (!ck.contains(cks[i])) {
                                ck.add(cks[i]);
                            }
                        }
                        xywmcCkMap.put(xywmcBm.getKey(), ck);
                        break;
                    }
                }
            }

            // 更新总窗口数信息
            for (BdcZxywYwbmRelDO bdcZxywYwbmRelDO : bdcZxywYwbmRelDOList) {
                if (xywmcCkMap.get(bdcZxywYwbmRelDO.getXywmc()) != null) {
                    bdcZxywYwbmRelDO.setCks(xywmcCkMap.get(bdcZxywYwbmRelDO.getXywmc()).size());
                    entityMapper.updateByPrimaryKeySelective(bdcZxywYwbmRelDO);
                }
            }
        }
    }

    @Override
    public void jhxx(String jsonString) {
        if (StringUtils.isNotEmpty(jsonString)) {
            Map jsonMap = JSONObject.parseObject(jsonString, HashMap.class);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date pcdate = null;
            try {
                pcdate = simpleDateFormat.parse(MapUtils.getString(jsonMap, "DATE_TIME"));
            } catch (ParseException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }

            // 查询窗口信息
            Example example = new Example(BdcCkxxDO.class);
            example.createCriteria().andEqualTo("zxmc", MapUtils.getString(jsonMap, "DT_NAME"))
                    .andEqualTo("ckh", MapUtils.getString(jsonMap, "WIN_NO"));
            List<BdcCkxxDO> bdcCkxxDOList = entityMapper.selectByExample(example);
            BdcCkxxDO bdcCkxxDO;

            // 获取已经被呼叫 且尚未结束的呼叫
            Date beginTime = GetDateUtils.getStartTime();
            Date endTime = GetDateUtils.getEndTime();
            Example exampleHjxxDone = new Example(BdcHjxxDO.class);
            exampleHjxxDone.createCriteria().andEqualTo("ckh",MapUtils.getString(jsonMap, "WIN_NO"))
                    .andEqualTo("zxmc", MapUtils.getString(jsonMap, "DT_NAME"))
                    .andBetween("scsj", beginTime, endTime).andIsNull("wcsj");
            List<BdcHjxxDO> bdcHjxxDoneList = entityMapper.selectByExample(exampleHjxxDone);
            // 更新对应呼叫信息
            // 更新对应呼叫信息
            if (CollectionUtils.isNotEmpty(bdcHjxxDoneList)){
                BdcHjxxDO bdcHjxxDone = bdcHjxxDoneList.get(0);
                bdcHjxxDone.setWcsj(new Date());
                entityMapper.updateByPrimaryKeySelective(bdcHjxxDone);
            }

            // 获取当前呼叫号
            BdcHjxxDO bdcHjxxDO;
            Example exampleHjxx = new Example(BdcHjxxDO.class);
            exampleHjxx.createCriteria().andEqualTo("hjh",MapUtils.getString(jsonMap, "CALL_NUMBER"))
                    .andEqualTo("zxmc", MapUtils.getString(jsonMap, "DT_NAME"))
                    .andBetween("scsj", beginTime, endTime);
            List<BdcHjxxDO> bdcHjxxDOList = entityMapper.selectByExample(exampleHjxx);
            // 更新对应呼叫信息
            if (CollectionUtils.isNotEmpty(bdcHjxxDOList)){
                bdcHjxxDO = bdcHjxxDOList.get(0);
                bdcHjxxDO.setCkh(MapUtils.getString(jsonMap, "WIN_NO"));
                entityMapper.updateByPrimaryKeySelective(bdcHjxxDO);
            }

            if (CollectionUtils.isNotEmpty(bdcCkxxDOList)) {
                bdcCkxxDO = bdcCkxxDOList.get(0);
                if (bdcCkxxDO.getGxsj() == null || bdcCkxxDO.getGxsj().before(pcdate)) {
                    bdcCkxxDO.setHjh(MapUtils.getString(jsonMap, "CALL_NUMBER"));
                    bdcCkxxDO.setGxsj(pcdate);
                    bdcCkxxDO.setCkzt(0);
                }
            } else {
                // 当窗口信息不存在时 创建窗口信息
                bdcCkxxDO = new BdcCkxxDO();
                bdcCkxxDO.setCkid(UUIDGenerator.generate());
                bdcCkxxDO.setCkh(MapUtils.getString(jsonMap, "WIN_NO"));
                bdcCkxxDO.setZxmc(MapUtils.getString(jsonMap, "DT_NAME"));
                bdcCkxxDO.setGxsj(pcdate);
                bdcCkxxDO.setCkzt(0);
                bdcCkxxDO.setHjh(MapUtils.getString(jsonMap, "CALL_NUMBER"));
            }
            entityMapper.saveOrUpdate(bdcCkxxDO, bdcCkxxDO.getCkid());
        }
    }

    @Override
    public void ztxx(String jsonString) {
        if (StringUtils.isNotEmpty(jsonString)) {
            Map jsonMap = JSONObject.parseObject(jsonString, HashMap.class);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date pcdate = null;
            try {
                pcdate = simpleDateFormat.parse(MapUtils.getString(jsonMap, "DATE_TIME"));
            } catch (ParseException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }

            // 查询窗口信息
            Example example = new Example(BdcCkxxDO.class);
            example.createCriteria().andEqualTo("zxmc", MapUtils.getString(jsonMap, "DT_NAME"))
                    .andEqualTo("ckh", MapUtils.getString(jsonMap, "WIN_NO"));
            List<BdcCkxxDO> bdcCkxxDOList = entityMapper.selectByExample(example);
            BdcCkxxDO bdcCkxxDO;
            if (CollectionUtils.isNotEmpty(bdcCkxxDOList)) {
                bdcCkxxDO = bdcCkxxDOList.get(0);
                if (bdcCkxxDO.getGxsj() == null || bdcCkxxDO.getGxsj().before(pcdate)) {
                    bdcCkxxDO.setCkzt(1);
                    bdcCkxxDO.setGxsj(pcdate);
                }
            } else {
                // 当窗口信息不存在时 创建窗口信息
                bdcCkxxDO = new BdcCkxxDO();
                bdcCkxxDO.setCkid(UUIDGenerator.generate());
                bdcCkxxDO.setCkh(MapUtils.getString(jsonMap, "WIN_NO"));
                bdcCkxxDO.setZxmc(MapUtils.getString(jsonMap, "DT_NAME"));
                bdcCkxxDO.setGxsj(pcdate);
                bdcCkxxDO.setCkzt(1);
            }
            entityMapper.saveOrUpdate(bdcCkxxDO, bdcCkxxDO.getCkid());
        }
    }

    @Override
    public void jcztxx(String jsonString) {
        if (StringUtils.isNotEmpty(jsonString)) {
            Map jsonMap = JSONObject.parseObject(jsonString, HashMap.class);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date pcdate = null;
            try {
                pcdate = simpleDateFormat.parse(MapUtils.getString(jsonMap, "DATE_TIME"));
            } catch (ParseException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }

            // 查询窗口信息
            Example example = new Example(BdcCkxxDO.class);
            example.createCriteria().andEqualTo("zxmc", MapUtils.getString(jsonMap, "DT_NAME"))
                    .andEqualTo("ckh", MapUtils.getString(jsonMap, "WIN_NO"));
            List<BdcCkxxDO> bdcCkxxDOList = entityMapper.selectByExample(example);
            BdcCkxxDO bdcCkxxDO;
            if (CollectionUtils.isNotEmpty(bdcCkxxDOList)) {
                bdcCkxxDO = bdcCkxxDOList.get(0);
                if (bdcCkxxDO.getGxsj() == null || bdcCkxxDO.getGxsj().before(pcdate)) {
                    bdcCkxxDO.setCkzt(0);
                    bdcCkxxDO.setGxsj(pcdate);
                }
            } else {
                // 当窗口信息不存在时 创建窗口信息
                bdcCkxxDO = new BdcCkxxDO();
                bdcCkxxDO.setCkid(UUIDGenerator.generate());
                bdcCkxxDO.setCkh(MapUtils.getString(jsonMap, "WIN_NO"));
                bdcCkxxDO.setZxmc(MapUtils.getString(jsonMap, "DT_NAME"));
                bdcCkxxDO.setGxsj(pcdate);
                bdcCkxxDO.setCkzt(0);
            }
            entityMapper.saveOrUpdate(bdcCkxxDO, bdcCkxxDO.getCkid());
        }
    }

    @Override
    public void pfxx(String jsonString) {
        if (StringUtils.isNotEmpty(jsonString)) {
            Map jsonMap = JSONObject.parseObject(jsonString, HashMap.class);

            // 查询是否存在呼叫信息
            Example exampleHis = new Example(BdcHjxxDO.class);
            exampleHis.createCriteria().andEqualTo("zxmc", MapUtils.getString(jsonMap, "DT_NAME"))
                    .andEqualTo("hjh", MapUtils.getString(jsonMap, "CALL_NUMBER"));
            List<BdcHjxxDO> bdcHjxxHisList = entityMapper.selectByExample(exampleHis);
            // 获取当前json传入时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date pcdate = null;
            try {
                pcdate = simpleDateFormat.parse(MapUtils.getString(jsonMap, "DATE_TIME"));
            } catch (ParseException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }

            // 处理呼叫信息
            BdcHjxxDO bdcHjxxDO;
            // 当呼叫信息存在时
            if (CollectionUtils.isNotEmpty(bdcHjxxHisList)) {
                bdcHjxxDO = bdcHjxxHisList.get(0);
                if (bdcHjxxDO.getScsj() != null && bdcHjxxDO.getScsj().before(pcdate)
                        && (bdcHjxxDO.getGxsj() == null || bdcHjxxDO.getGxsj().before(pcdate))) {
                    bdcHjxxDO.setPf(MapUtils.getInteger(jsonMap, "VALUE"));
                    bdcHjxxDO.setGxsj(pcdate);
                }
            } else {
                // 当呼叫信息不存在时
                bdcHjxxDO = new BdcHjxxDO();
                bdcHjxxDO.setHjid(UUIDGenerator.generate());
                bdcHjxxDO.setHjh(MapUtils.getString(jsonMap, "CALL_NUMBER"));
                bdcHjxxDO.setScsj(pcdate);
                bdcHjxxDO.setHjzt(0);
                bdcHjxxDO.setZxmc(MapUtils.getString(jsonMap, "DT_NAME"));
            }
            entityMapper.saveOrUpdate(bdcHjxxDO, bdcHjxxDO.getHjid());
        }
    }

    @Override
    @Scheduled(cron = "${zhp.cleartime}")
    public void qkhj() {
        bdcZhxpCxMapper.updateHjxx();
    }
}
