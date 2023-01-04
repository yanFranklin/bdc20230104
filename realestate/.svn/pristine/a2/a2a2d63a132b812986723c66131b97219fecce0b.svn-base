package cn.gtmap.interchange.service.impl;


import cn.gtmap.interchange.core.domain.*;
import cn.gtmap.interchange.core.dto.QlygParamModel;
import cn.gtmap.interchange.core.mapper.gx.GxTsqzkLogMapper;
import cn.gtmap.interchange.core.mapper.gx.GxTsqzkYzsbxxMapper;
import cn.gtmap.interchange.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.interchange.core.support.mybatis.mapper.Example;
import cn.gtmap.interchange.service.LogService;
import cn.gtmap.interchange.util.Constants;
import cn.gtmap.interchange.util.UuidUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    @Qualifier("gxEntityMapper")
    private EntityMapper gxEntityMapper;
    @Autowired
    private GxTsqzkYzsbxxMapper gxTsqzkYzsbxxMapper;
    @Autowired
    private GxTsqzkLogMapper gxTsqzkLogMapper;


    /**
     * 推送前插入日志，保证即使推送失败也可以生效
     *
     * @param applyList
     */
    @Override
    public void saveTsLog(List<InfApply> applyList) {
        Example djfDjYwxxExample = new Example(DjfDjYwxx.class);
        Example infApplyProcessExample = new Example(InfApplyProcess.class);
        String ywh = null;
        String slh = null;
        String dqjd = null;
        GxTsqzkLog gxTsqzkLog;
        List<GxTsqzkLog> gxTsqzkLogList = Lists.newArrayList();
        Example gxTsqzkLogExample = new Example(GxTsqzkLog.class);
        if (CollectionUtils.isNotEmpty(applyList)) {
            for (InfApply infApply : applyList) {
                djfDjYwxxExample.clear();
                gxTsqzkLogExample.clear();
                infApplyProcessExample.clear();
                djfDjYwxxExample.createCriteria().andEqualTo("yzwbh", infApply.getInternal_no());
                infApplyProcessExample.createCriteria().andEqualTo("internal_no", infApply.getInternal_no());
                infApplyProcessExample.setOrderByClause("create_date");
                List<DjfDjYwxx> djfDjYwxxeList = gxEntityMapper.selectByExample(djfDjYwxxExample);
                if (CollectionUtils.isNotEmpty(djfDjYwxxeList)) {
                    ywh = djfDjYwxxeList.get(0).getYwh();
                    slh = djfDjYwxxeList.get(0).getSlh();
                    List<InfApplyProcess> infApplyProcessList = gxEntityMapper.selectByExample(infApplyProcessExample);
                    if (CollectionUtils.isNotEmpty(infApplyProcessList)) {
                        dqjd = infApplyProcessList.get(infApplyProcessList.size() - 1).getEvent_name();
                    }
                }
                //无实际业务数据的情况
                if (StringUtils.isBlank(ywh)) {
                    ywh = infApply.getInternal_no();
                    slh = ywh;
                    dqjd = "无业务数据";
                }
                gxTsqzkLogExample.createCriteria().andEqualTo("yzwbh", infApply.getInternal_no());
                gxTsqzkLog = new GxTsqzkLog();
                gxTsqzkLog.setDqjd(dqjd);
                gxTsqzkLog.setId(ywh);
                gxTsqzkLog.setTssj(new Date());
                gxTsqzkLog.setSlh(slh);
                gxTsqzkLog.setYzwbh(infApply.getInternal_no());
                gxTsqzkLog.setTszt(Constants.TSZT_TSZ);
                gxTsqzkLogList.add(gxTsqzkLog);
                //先删除，后插入
                gxEntityMapper.deleteByExample(gxTsqzkLogExample);
            }
            gxEntityMapper.insertBatchSelective(gxTsqzkLogList);
        }
    }

    /**
     * 从前置库提取数据到中间库日志
     *
     * @param yzwbh
     */
    @Override
    public void saveTsLog(String yzwbh) {
        String ywh = UuidUtils.generate16();
        GxTsqzkLog gxTsqzkLog = new GxTsqzkLog();
        gxTsqzkLog.setDqjd("外部受理");
        gxTsqzkLog.setId(ywh);
        gxTsqzkLog.setTssj(new Date());
        gxTsqzkLog.setSlh(ywh);
        gxTsqzkLog.setYzwbh(yzwbh);
        gxEntityMapper.insertSelective(yzwbh);
    }

    public void updateTsLog(InfApply infApply, GxTsqzkLog newLog) {
        updateTsLog(infApply, newLog, null, true);
    }

    @Override
    public void updateTsLog(InfApply infApply, GxTsqzkLog newLog, List<GxTsqzkYzsbxx> gxTsqzkYzsbxxList, boolean updateTsxx) {
        Example djfDjYwxxExample = new Example(DjfDjYwxx.class);
        Example infApplyProcessExample = new Example(InfApplyProcess.class);
        String ywh = null;
        String slh = null;
        Example gxTsqzkLogExample = new Example(GxTsqzkLog.class);
        djfDjYwxxExample.clear();
        djfDjYwxxExample.createCriteria().andEqualTo("yzwbh", infApply.getInternal_no());
        infApplyProcessExample.clear();
        infApplyProcessExample.createCriteria().andEqualTo("internal_no", infApply.getInternal_no());
        infApplyProcessExample.setOrderByClause("create_date");
        List<DjfDjYwxx> djfDjYwxxeList = gxEntityMapper.selectByExample(djfDjYwxxExample);
        if (CollectionUtils.isNotEmpty(djfDjYwxxeList)) {
            ywh = djfDjYwxxeList.get(0).getYwh();
            slh = djfDjYwxxeList.get(0).getSlh();
        }
        gxTsqzkLogExample.createCriteria().andEqualTo("yzwbh", infApply.getInternal_no());
        List<GxTsqzkLog> gxTsqzkLogList = gxEntityMapper.selectByExampleNotNull(gxTsqzkLogExample);

        if (CollectionUtils.isNotEmpty(gxTsqzkLogList)) {
            GxTsqzkLog gxTsqzkLog = gxTsqzkLogList.get(0);
            if (StringUtils.isBlank(ywh)) {
                ywh = gxTsqzkLog.getId();
                slh = gxTsqzkLog.getSlh();
            }
            gxTsqzkLog.setId(ywh);

            /*
            记录修改前日志
             */
            String newLogid = UuidUtils.generate16();
            StringBuffer chainLog = new StringBuffer();
            StackTraceElement[] classArray = new Exception().getStackTrace();
            for (int i = 0; i < classArray.length; i++) {
                String classname = classArray[i].getClassName();
                String methodname = classArray[i].getMethodName();
                int lineNumber = classArray[i].getLineNumber();
                if (classname.contains("oneWeb") && !classname.contains("$") && !"invoke".equals(methodname)) {
                    chainLog.append(classname).append(".").append(methodname).append("(").append(lineNumber).append(")->");
                }
            }
            GxTsqzkLogTemp gxTsqzkLogTemp = JSONObject.parseObject(JSONObject.toJSONString(gxTsqzkLog), GxTsqzkLogTemp.class);
            if (newLog.getTssj() != null) {
                gxTsqzkLogTemp.setTssj(newLog.getTssj());
            } else {
                gxTsqzkLogTemp.setTssj(new Date());
            }

            gxTsqzkLogTemp.setId(newLogid);
            gxTsqzkLogTemp.setChainlog(chainLog.toString());
            gxTsqzkLogTemp.setYlogid(gxTsqzkLog.getId());


            if (newLog.getTssj() != null) {
                gxTsqzkLog.setTssj(newLog.getTssj());
            }
            if (updateTsxx) {
                gxTsqzkLog.setSblx(newLog.getSblx());
                gxTsqzkLog.setTszt(newLog.getTszt());
                gxTsqzkLog.setSbyy(StringUtils.substring(newLog.getSbyy(), 0, 400));
            }
            gxTsqzkLog.setYzzt(newLog.getYzzt());
            gxTsqzkLog.setSlh(slh);
            gxTsqzkLog.setYzwbh(infApply.getInternal_no());

            //先删除，后插入
            gxEntityMapper.deleteByExample(gxTsqzkLogExample);
            gxEntityMapper.insert(gxTsqzkLog);
            Example gxTsqzkYzsbxxExample = new Example(GxTsqzkYzsbxx.class);
            gxTsqzkYzsbxxExample.createCriteria().andEqualTo("logid", gxTsqzkLog.getId());

            //批量插入验证信息
            if (null != gxTsqzkYzsbxxList) {
                List<GxTsqzkYzsbxx> yGxTsqzkYzsbxx = gxEntityMapper.selectByExample(gxTsqzkYzsbxxExample);
                if (CollectionUtils.isNotEmpty(yGxTsqzkYzsbxx)) {
                    List<GxTsqzkYzsbxxTemp> gxTsqzkYzsbxxTempList = JSON.parseObject(JSONObject.toJSONString(yGxTsqzkYzsbxx), new TypeReference<List<GxTsqzkYzsbxxTemp>>() {
                    });
                    for (GxTsqzkYzsbxxTemp gxTsqzkYzsbxxTemp : gxTsqzkYzsbxxTempList) {
                        gxTsqzkYzsbxxTemp.setLogid(newLogid);
                        gxTsqzkYzsbxxTemp.setId(UuidUtils.generate16());
                    }
                    gxEntityMapper.insert(gxTsqzkLogTemp);
                    gxEntityMapper.insertBatchSelective(gxTsqzkYzsbxxTempList);
                }
                gxEntityMapper.deleteByExampleNotNull(gxTsqzkYzsbxxExample);
            }
            if (CollectionUtils.isNotEmpty(gxTsqzkYzsbxxList)) {
                for (GxTsqzkYzsbxx gxTsqzkYzsbxx : gxTsqzkYzsbxxList) {
                    gxTsqzkYzsbxx.setId(UuidUtils.generate16());
                    gxTsqzkYzsbxx.setLogid(gxTsqzkLog.getId());
                }
                gxEntityMapper.insertBatchSelective(gxTsqzkYzsbxxList);
            }
        }

    }

    /**
     * 通过日志id获取失败原因信息
     *
     * @param logid
     * @return
     */
    @Override
    public HashMap getSbyyByLogid(String logid) {
        HashMap map = Maps.newHashMap();
        GxTsqzkLog gxTsqzkLog = gxEntityMapper.selectByPrimaryKey(GxTsqzkLog.class, logid);
        List<GxTsqzkYzsbxx> gxTsqzkYzsbxxList = Lists.newArrayList();
        gxTsqzkYzsbxxList.addAll(gxTsqzkYzsbxxMapper.getGxTsqzkYzsbxxListByLogid(logid));
        String sbyy = "";
        if (gxTsqzkLog != null && StringUtils.isNotBlank(gxTsqzkLog.getSbyy())) {
            sbyy = gxTsqzkLog.getSbyy();
        }
        map.put("sbyy", sbyy);
        map.put("yzxx", gxTsqzkYzsbxxList);
        return map;
    }

    /**
     * 获取未推送业务号
     *
     * @return
     */
    @Override
    public List<QlygParamModel> getWtsYwhByLog() {
        List<Object> tszts = Lists.newArrayList();
        tszts.add(Constants.TSZT_WTS);
        Map param = Maps.newHashMap();
        param.put("tszts", tszts);
        List<QlygParamModel> ywhList = Lists.newArrayList();
        List<QlygParamModel> temp = gxTsqzkLogMapper.getYzwxxByTszt(param);
        if (CollectionUtils.isNotEmpty(temp)) {
            ywhList.addAll(temp);
        }
        return ywhList;
    }

    /**
     * 获取验证失败业务号
     *
     * @return
     */
    @Override
    public List<QlygParamModel> getYzsbYwhByLog() {
        List<Object> tszts = Lists.newArrayList();
        List<Object> yzzts = Lists.newArrayList();
        List<Object> sblxs = Lists.newArrayList();
        tszts.add(Constants.TSZT_FAIL);
        yzzts.add(Constants.TSZT_FAIL);
        sblxs.add(Constants.SBLX_YZSB);
        Map param = Maps.newHashMap();
        param.put("tszts", tszts);
        param.put("yzzts", yzzts);
        param.put("sblxs", sblxs);
        List<QlygParamModel> ywhList = Lists.newArrayList();
        List<QlygParamModel> temp = gxTsqzkLogMapper.getYzwxxByTszt(param);
        if (CollectionUtils.isNotEmpty(temp)) {
            ywhList.addAll(temp);
        }
        return ywhList;
    }

    /**
     * 更新数据库日志记录
     *
     * @param yzwbhs
     * @param gxTsqzkYzsbxxYzwbhRel
     */
    @Override
    public void updateTsLog(InfApply infapply, List<String> yzwbhs, HashMap<String, List<GxTsqzkYzsbxx>> gxTsqzkYzsbxxYzwbhRel, List<String> yzlx) {
        Example gxTsQzkLogExample = new Example(GxTsqzkLog.class);
        Example tBmCaseprocessExample = new Example(TBmCaseprocess.class);
        Example gxTsqzkYzsbxxExample = new Example(GxTsqzkYzsbxx.class);
        List<GxTsqzkLog> gxTsqzkLogListTemp = Lists.newArrayList();
        if (yzlx == null) {
            yzlx = Lists.newArrayList();
        }
        if (CollectionUtils.isNotEmpty(yzwbhs) && MapUtils.isNotEmpty(gxTsqzkYzsbxxYzwbhRel)) {
            List<GxTsqzkLog> gxTsqzkLogList;
            GxTsqzkLog gxTsqzkLog;
            List<TBmCaseprocess> tBmCaseprocessList;
            List<GxTsqzkYzsbxx> gxTsqzkYzsbxxList;
            List<GxTsqzkYzsbxx> gxTsqzkYzsbxxListTemp = Lists.newArrayList();
            List<GxTsqzkYzsbxx> savedGxTsqzkYzsbxxList;
            String dqjd;
            boolean matched;
            for (String yzwbh : yzwbhs) {
                gxTsQzkLogExample.clear();
                gxTsQzkLogExample.createCriteria().andEqualTo("yzwbh", yzwbh);
                gxTsqzkLogList = gxEntityMapper.selectByExample(gxTsQzkLogExample);
                gxTsqzkYzsbxxList = gxTsqzkYzsbxxYzwbhRel.get(yzwbh);
                if (CollectionUtils.isNotEmpty(gxTsqzkLogList)) {
                    gxTsqzkLog = gxTsqzkLogList.get(0);
                    gxTsqzkYzsbxxExample.clear();
                    gxTsqzkYzsbxxExample.createCriteria().andEqualTo("logid", gxTsqzkLog.getId());
                    savedGxTsqzkYzsbxxList = gxEntityMapper.selectByExample(gxTsqzkYzsbxxExample);
                    for (GxTsqzkYzsbxx newGxTsqzkYzsbxx : gxTsqzkYzsbxxList) {
                        matched = false;
                        if (CollectionUtils.isNotEmpty(savedGxTsqzkYzsbxxList)) {
                            for (GxTsqzkYzsbxx savedGxTsqzkYzsbxx : savedGxTsqzkYzsbxxList) {
                                if (StringUtils.equalsIgnoreCase(savedGxTsqzkYzsbxx.getYzlx(), newGxTsqzkYzsbxx.getYzlx())) {
                                    matched = true;
                                    break;
                                }
                            }
                        }
                        if (!matched) {
                            newGxTsqzkYzsbxx.setId(UuidUtils.generate16());
                            newGxTsqzkYzsbxx.setLogid(gxTsqzkLog.getId());
                            gxTsqzkYzsbxxListTemp.add(newGxTsqzkYzsbxx);
                        }
                    }
                } else {
                    tBmCaseprocessExample.clear();
                    tBmCaseprocessExample.setOrderByClause("tachestartdatetime");
                    tBmCaseprocessExample.createCriteria().andEqualTo("caseno", yzwbh);
                    tBmCaseprocessList = gxEntityMapper.selectByExample(tBmCaseprocessExample);
                    if (CollectionUtils.isNotEmpty(tBmCaseprocessList)) {
                        dqjd = tBmCaseprocessList.get(tBmCaseprocessList.size() - 1).getNodename();
                    } else {
                        dqjd = "无";
                    }
                    gxTsqzkLog = new GxTsqzkLog();
                    gxTsqzkLog.setDqjd(dqjd);
                    gxTsqzkLog.setId(yzwbh);
                    gxTsqzkLog.setTssj(new Date());
                    gxTsqzkLog.setSlh(yzwbh);
                    gxTsqzkLog.setYzwbh(yzwbh);
                    for (GxTsqzkYzsbxx gxTsqzkYzsbxx : gxTsqzkYzsbxxList) {
                        gxTsqzkYzsbxx.setLogid(gxTsqzkLog.getId());
                        gxTsqzkYzsbxx.setId(UuidUtils.generate16());
                    }
                    gxTsqzkYzsbxxListTemp.addAll(gxTsqzkYzsbxxList);
                }
                gxTsqzkLog.setTszt(Constants.TSZT_FAIL);
                gxTsqzkLog.setYzzt(Constants.TSZT_FAIL);
                gxTsqzkLog.setSblx(Constants.SBLX_YZSB);
                gxTsqzkLog.setSbyy(Constants.SBYY_YZSB);
                gxTsqzkLogListTemp.add(gxTsqzkLog);
            }
            if (CollectionUtils.isNotEmpty(gxTsqzkYzsbxxListTemp)) {
                gxEntityMapper.insertBatchSelective(gxTsqzkYzsbxxListTemp);
            }
        } else if (infapply != null) {
            List<GxTsqzkLog> gxTsqzkLogList;
            GxTsqzkLog gxTsqzkLog;
            List<TBmCaseprocess> tBmCaseprocessList;
            List<GxTsqzkYzsbxx> savedGxTsqzkYzsbxxList;
            String dqjd;
            if (CollectionUtils.isEmpty(yzlx)) {
                yzlx.add(Constants.YZXX_TJWTSJG);
                yzlx.add(Constants.YZXX_XMCQWBJ);
            }
            String yzwbh = infapply.getInternal_no();
            gxTsQzkLogExample.clear();
            gxTsQzkLogExample.createCriteria().andEqualTo("yzwbh", yzwbh);
            gxTsqzkLogList = gxEntityMapper.selectByExample(gxTsQzkLogExample);
            boolean yztg = true;
            if (CollectionUtils.isNotEmpty(gxTsqzkLogList)) {
                gxTsqzkLog = gxTsqzkLogList.get(0);
                gxTsqzkYzsbxxExample.clear();
                gxTsqzkYzsbxxExample.createCriteria().andEqualTo("logid", gxTsqzkLog.getId());
                savedGxTsqzkYzsbxxList = gxEntityMapper.selectByExample(gxTsqzkYzsbxxExample);
                if (CollectionUtils.isNotEmpty(savedGxTsqzkYzsbxxList)) {
                    for (GxTsqzkYzsbxx savedGxTsqzkYzsbxx : savedGxTsqzkYzsbxxList) {
                        if (!yzlx.contains(savedGxTsqzkYzsbxx.getYzlx())) {
                            yztg = false;
                            break;
                        }
                    }
                }
                if (yztg) {
                    gxTsqzkLog.setTszt(Constants.TSZT_WTS);
                    gxTsqzkLog.setYzzt(Constants.TSZT_SUCCESS);
                    gxTsqzkLog.setSblx("");
                    gxTsqzkLog.setSbyy("");
                }
                gxTsqzkLogListTemp.add(gxTsqzkLog);
            } else {
                tBmCaseprocessExample.clear();
                tBmCaseprocessExample.setOrderByClause("tachestartdatetime");
                tBmCaseprocessExample.createCriteria().andEqualTo("caseno", yzwbh);
                tBmCaseprocessList = gxEntityMapper.selectByExample(tBmCaseprocessExample);
                if (CollectionUtils.isNotEmpty(tBmCaseprocessList)) {
                    dqjd = tBmCaseprocessList.get(tBmCaseprocessList.size() - 1).getNodename();
                } else {
                    dqjd = "无";
                }
                gxTsqzkLog = new GxTsqzkLog();
                gxTsqzkLog.setDqjd(dqjd);
                gxTsqzkLog.setId(yzwbh);
                gxTsqzkLog.setTssj(new Date());
                gxTsqzkLog.setSlh(yzwbh);
                gxTsqzkLog.setYzwbh(yzwbh);
                gxTsqzkLog.setTszt(Constants.TSZT_WTS);
                gxTsqzkLog.setYzzt(Constants.TSZT_SUCCESS);
                gxTsqzkLog.setSblx(null);
                gxTsqzkLog.setSbyy(null);
            }
            gxTsqzkYzsbxxExample.clear();
            gxTsqzkYzsbxxExample.createCriteria().andEqualTo("logid", gxTsqzkLog.getId()).andIn("yzlx", new ArrayList<Object>(yzlx));
            gxEntityMapper.deleteByExampleNotNull(gxTsqzkYzsbxxExample);
        } else {
//            gxTsqzkYzsbxxExample.createCriteria().andIn("yzlx", new ArrayList<Object>(yzlx));
//            List<GxTsqzkYzsbxx> yzsbxxList =  gxEntityMapper.selectByExampleNotNull(gxTsqzkYzsbxxExample);
//            if (CollectionUtils.isNotEmpty(yzsbxxList)){
//
//            }
        }
        // 更新推送日志
        if (CollectionUtils.isNotEmpty(gxTsqzkLogListTemp)) {
            gxEntityMapper.batchSaveSelective(gxTsqzkLogListTemp);
        }
    }

    @Override
    public List<QlygParamModel> queryYzwxx(Map<String, Object> queryMap) {
        return gxTsqzkLogMapper.queryYzwxx(queryMap);
    }
}
