package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzMlxxHjczMapper;
import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzMlxxHjlogMapper;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzMlxxDO;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzMlxxHjlogDO;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzMlxxHjService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/4/29
 * @description
 */
@Service
public class BdcDzzzMlxxHjServiceImpl implements BdcDzzzMlxxHjService {
    @Autowired
    private BdcDzzzMlxxHjczMapper bdcDzzzMlxxHjczMapper;
    @Autowired
    private BdcDzzzMlxxHjlogMapper bdcDzzzMlxxHjlogMapper;
    private String SFQCYC = "sfqcyc";
    private String HJSJSJ = "hjsjzj";
    private String YCZJLIST = "yczjlist";
    private String XZQDM = "xzqdm";
    private String SYSSJZJ = "syssjzj";

    @Override
    @Transactional
    public Map mlxxHj(BdcDzzzMlxxDO bdcDzzzMlxxDO, String ycmlid, Integer xzqts, String xzqdm) {
        // TODO 存储汇交数据
        // TODO 查询该县区总条数
        int zts = 0;

        // 获取数据库中的差值
        Map mapHjcz = new HashMap();
        mapHjcz.put(XZQDM, xzqdm);
        int cz = bdcDzzzMlxxHjczMapper.queryBdcDzzzMlxxHjcz(mapHjcz);

        // 获取最后一条log数据
        BdcDzzzMlxxHjlogDO lastBdcDzzzMlxxHjlog = bdcDzzzMlxxHjlogMapper.queryLastBdcDzzzMlxxHjlog(xzqdm);

        BdcDzzzMlxxHjlogDO bdcDzzzMlxxHjlog = new BdcDzzzMlxxHjlogDO();
        bdcDzzzMlxxHjlog.setMlid(bdcDzzzMlxxDO.getMlid());
        // 当存在上一条数据时
        if (lastBdcDzzzMlxxHjlog != null) {
            bdcDzzzMlxxHjlog.setStmlid(lastBdcDzzzMlxxHjlog.getMlid());
        }
        bdcDzzzMlxxHjlog.setXzqdm(xzqdm);
        bdcDzzzMlxxHjlog.setKhdzts(xzqts);
        bdcDzzzMlxxHjlog.setFwdzts(zts);

        Map resultMap = new HashMap();
        // 判断差值如何变化
        if (xzqts == zts + cz) {
            // 正常情况下 存储正常日志 返回正常状态
            bdcDzzzMlxxHjlog.setXzcz(0);
            // 插入日志

            saveOrUpdateHjlog(bdcDzzzMlxxHjlog);

            // 组织返回值
            resultMap.put(HJSJSJ, bdcDzzzMlxxHjlog.getMlid());
            resultMap.put(SYSSJZJ, bdcDzzzMlxxHjlog.getStmlid());
            resultMap.put("sjzt", "1");
            resultMap.put(SFQCYC, false);

            return resultMap;
        } else if (zts + cz > xzqts) {
            // 更新差值表
            Map updateCz = new HashMap();
            updateCz.put(XZQDM, xzqdm);
            updateCz.put("cz", xzqts - zts);
            bdcDzzzMlxxHjczMapper.updateBdcDzzzMlxxHjcz(updateCz);
            // 当差值减小时 清除异常信息 返回正常状态
            bdcDzzzMlxxHjlog.setXzcz(0);
            saveOrUpdateHjlog(bdcDzzzMlxxHjlog);
            // 清除异常状态数据主键
            List yczjList = new ArrayList();
            // 减少的差值
            int jscz = zts + cz - xzqts;

            qcYc(ycmlid, jscz, xzqdm, yczjList, resultMap);
            // 组织返回值
            resultMap.put(HJSJSJ, bdcDzzzMlxxHjlog.getMlid());
            resultMap.put(SYSSJZJ, bdcDzzzMlxxHjlog.getStmlid());
            resultMap.put("sjzt", "1");

            return resultMap;
        } else {
            // 当差值增大时 增加一场新信息 返回异常状态
            bdcDzzzMlxxHjlog.setXzcz(xzqts - zts - cz);
            saveOrUpdateHjlog(bdcDzzzMlxxHjlog);

            // 更新目录信息汇交差值
            Map mlxxHjcz = new HashMap();
            mlxxHjcz.put(XZQDM, xzqdm);
            mlxxHjcz.put("cz", xzqts - zts);
            bdcDzzzMlxxHjczMapper.updateBdcDzzzMlxxHjcz(mlxxHjcz);

            // 组织返回值
            resultMap.put(HJSJSJ, bdcDzzzMlxxHjlog.getMlid());
            resultMap.put(SYSSJZJ, bdcDzzzMlxxHjlog.getStmlid());
            resultMap.put("sjzt", "2");
            resultMap.put(SFQCYC, false);

            return resultMap;
        }
    }

    @Override
    @Transactional
    public Map mlxxPlHj(List<BdcDzzzMlxxDO> bdcDzzzMlxxList, Integer xzqts, String xzqdm) {
        Map resultMap = new HashMap();
        // 取该批次上传前最后一条数据
        BdcDzzzMlxxHjlogDO lastBdcDzzzMlxxHjlog = bdcDzzzMlxxHjlogMapper.queryLastBdcDzzzMlxxHjlog(xzqdm);

        String lastMlid = lastBdcDzzzMlxxHjlog.getMlid();
        int i = 1;
        List hjsjzjList = new ArrayList();
        for (BdcDzzzMlxxDO bdcDzzzMlxx : bdcDzzzMlxxList) {
            // TODO 插入所有数据
            // 保存上传数据主键
            hjsjzjList.add(bdcDzzzMlxx.getMlid());
            // 插入每条数据对应的log
            BdcDzzzMlxxHjlogDO bdcDzzzMlxxHjlog = new BdcDzzzMlxxHjlogDO();
            bdcDzzzMlxxHjlog.setMlid(bdcDzzzMlxx.getMlid());
            bdcDzzzMlxxHjlog.setStmlid(lastMlid);
            bdcDzzzMlxxHjlog.setXzqdm(xzqdm);
            bdcDzzzMlxxHjlog.setKhdzts(lastBdcDzzzMlxxHjlog.getKhdzts() + i);
            bdcDzzzMlxxHjlog.setFwdzts(lastBdcDzzzMlxxHjlog.getFwdzts() + i);
            bdcDzzzMlxxHjlog.setXzcz(0);

            saveOrUpdateHjlog(bdcDzzzMlxxHjlog);
            i++;
        }
        // TODO 获得数据条数
        int zts = 0;

        // 获取数据库中的差值
        Map mapHjcz = new HashMap();
        mapHjcz.put(XZQDM, xzqdm);
        int cz = bdcDzzzMlxxHjczMapper.queryBdcDzzzMlxxHjcz(mapHjcz);
        // 判断差值如何变化
        if (xzqts == zts + cz) {
            // 组织返回值
            resultMap.put(HJSJSJ, hjsjzjList);
            resultMap.put(SYSSJZJ, lastBdcDzzzMlxxHjlog.getMlid());
            resultMap.put("sjzt", "1");
            resultMap.put(SFQCYC, false);
        } else if (xzqts > zts + cz) {
            // 更新log表
            BdcDzzzMlxxHjlogDO lastInsertedBdcDzzzMlxxHjlog = bdcDzzzMlxxHjlogMapper.queryLastBdcDzzzMlxxHjlog(xzqdm);
            Map updateHjlog = new HashMap();
            updateHjlog.put("xzcz", xzqts - zts - cz);
            updateHjlog.put("mlid", lastInsertedBdcDzzzMlxxHjlog.getMlid());
            bdcDzzzMlxxHjlogMapper.updateBdcDzzzMlxxHjlogCz(updateHjlog);

            // 更新差值表
            Map updateHjcz = new HashMap();
            updateHjcz.put("cz", xzqts - zts);
            updateHjcz.put(XZQDM, xzqdm);
            bdcDzzzMlxxHjczMapper.updateBdcDzzzMlxxHjcz(updateHjcz);

            // 组织返回值
            resultMap.put(HJSJSJ, hjsjzjList);
            resultMap.put(SYSSJZJ, lastBdcDzzzMlxxHjlog.getMlid());
            resultMap.put("sjzt", "2");
            resultMap.put(SFQCYC, false);
        } else {
            // 更新差值表
            Map updateHjcz = new HashMap();
            updateHjcz.put("cz", xzqts - zts);
            updateHjcz.put(XZQDM, xzqdm);
            bdcDzzzMlxxHjczMapper.updateBdcDzzzMlxxHjcz(updateHjcz);

            // 组织返回值
            resultMap.put(HJSJSJ, hjsjzjList);
            resultMap.put(SYSSJZJ, lastBdcDzzzMlxxHjlog.getMlid());
            resultMap.put("sjzt", "1");
            List yczjList = new ArrayList();
            // 清除异常数据
            resultMap.put(SFQCYC, loopToEmptyCz(zts + cz - xzqts, xzqdm, yczjList));
            resultMap.put(YCZJLIST, yczjList);
        }

        return resultMap;
    }

    @Override
    @Transactional
    public String mlxxGd(Timestamp gdsjjzsj, String xzqdm) {
        // 查询第一条异常数据
        Map map = new HashMap();
        map.put(XZQDM, xzqdm);
        map.put("scsj", gdsjjzsj);
        BdcDzzzMlxxHjlogDO lastBdcDzzzMlxxHjlog = bdcDzzzMlxxHjlogMapper.queryFirstYcBdcDzzzMlxxHjlog(map);
        if (lastBdcDzzzMlxxHjlog != null) {

            // TODO 归档目录信息

            // 归档日志信息
            Map logGdMap = new HashMap();
            logGdMap.put(XZQDM, xzqdm);
            logGdMap.put("scsj", lastBdcDzzzMlxxHjlog.getScsj());
            bdcDzzzMlxxHjlogMapper.updateBdcDzzzMlxxHjlogGd(logGdMap);

            return lastBdcDzzzMlxxHjlog.getStmlid();
        } else {
            return "";
        }
    }

    /**
     * 通过循环去除多余差值
     *
     * @param jscz
     * @param xzqdm
     * @param yczjList
     */
    private Boolean loopToEmptyCz(int jscz, String xzqdm, List yczjList) {
        Boolean result = false;
        while (jscz > 0) {
            Map map = new HashMap();
            map.put(XZQDM, xzqdm);
            BdcDzzzMlxxHjlogDO firstYcMlxxHjlog = bdcDzzzMlxxHjlogMapper.queryFirstYcBdcDzzzMlxxHjlog(map);

            Map updateLogMap = new HashMap();
            updateLogMap.put("mlid", firstYcMlxxHjlog.getMlid());

            if (jscz >= firstYcMlxxHjlog.getXzcz()) {
                updateLogMap.put("xzcz", 0);
                yczjList.add(firstYcMlxxHjlog.getMlid());
                result = true;
                jscz = jscz - firstYcMlxxHjlog.getXzcz();
            } else {
                updateLogMap.put("xzcz", firstYcMlxxHjlog.getXzcz() - jscz);
                jscz = 0;
            }
            bdcDzzzMlxxHjlogMapper.updateBdcDzzzMlxxHjlogCz(updateLogMap);
        }
        return result;
    }

    /**
     * 清除异常数据
     *
     * @param ycmlid
     * @param jscz
     * @param xzqdm
     * @param yczjList
     * @param resultMap
     */
    private void qcYc(String ycmlid, int jscz, String xzqdm, List yczjList, Map resultMap) {
        if (StringUtils.isNotBlank(ycmlid)) {
            Map ycMap = new HashMap();
            ycMap.put("mlid", ycmlid);
            BdcDzzzMlxxHjlogDO ycBdcDzzzMlxxHjlog = bdcDzzzMlxxHjlogMapper.queryBdcDzzzMlxxHjlog(ycMap);
            if (ycBdcDzzzMlxxHjlog != null) {
                if (jscz < ycBdcDzzzMlxxHjlog.getXzcz()) {
                    // 当新增差值未归零时 仅更新日志信息
                    ycMap.put("xzcz", ycBdcDzzzMlxxHjlog.getXzcz() - jscz);
                    bdcDzzzMlxxHjlogMapper.updateBdcDzzzMlxxHjlogCz(ycMap);
                } else {
                    // 当新增差值归零时 更新日志信息 返回值中增加
                    ycMap.put("xzcz", 0);
                    bdcDzzzMlxxHjlogMapper.updateBdcDzzzMlxxHjlogCz(ycMap);
                    // 是否去掉特定主键异常状态
                    resultMap.put(SFQCYC, true);
                    yczjList.add(ycBdcDzzzMlxxHjlog.getMlid());

                    // 当新增差值归零仍有剩余时 从头开始查询异常数据
                    if (jscz > ycBdcDzzzMlxxHjlog.getXzcz()) {
                        int sycz = jscz - ycBdcDzzzMlxxHjlog.getXzcz();

                        loopToEmptyCz(sycz, xzqdm, yczjList);
                    }
                    resultMap.put(YCZJLIST, yczjList);
                }
            } else {
                resultMap.put(SFQCYC, loopToEmptyCz(jscz, xzqdm, yczjList));
                if (CollectionUtils.isNotEmpty(yczjList)) {
                    resultMap.put(YCZJLIST, yczjList);
                }
            }
        } else {
            resultMap.put(SFQCYC, loopToEmptyCz(jscz, xzqdm, yczjList));
            if (CollectionUtils.isNotEmpty(yczjList)) {
                resultMap.put(YCZJLIST, yczjList);
            }
        }
    }

    /**
     * 保存或更新日志信息
     *
     * @param bdcDzzzMlxxHjlog
     */
    private void saveOrUpdateHjlog(BdcDzzzMlxxHjlogDO bdcDzzzMlxxHjlog) {
        Map getOldDataMap = new HashMap();
        getOldDataMap.put("mlid", bdcDzzzMlxxHjlog.getMlid());
        BdcDzzzMlxxHjlogDO oldBdcDzzzMlxxHjlog = bdcDzzzMlxxHjlogMapper.queryBdcDzzzMlxxHjlog(getOldDataMap);
        if (oldBdcDzzzMlxxHjlog == null) {
            bdcDzzzMlxxHjlogMapper.insertBdcDzzzMlxxHjlog(bdcDzzzMlxxHjlog);
        } else {
            bdcDzzzMlxxHjlog.setXzcz(oldBdcDzzzMlxxHjlog.getXzcz() + bdcDzzzMlxxHjlog.getXzcz());
            bdcDzzzMlxxHjlogMapper.updateOldBdcDzzzMlxxHjlog(bdcDzzzMlxxHjlog);
        }
    }
}
