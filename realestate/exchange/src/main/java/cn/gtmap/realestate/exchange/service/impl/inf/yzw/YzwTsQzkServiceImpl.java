package cn.gtmap.realestate.exchange.service.impl.inf.yzw;

import cn.gtmap.realestate.common.core.domain.exchange.yzw.InfApplyDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.exchange.core.domain.yzw.yancheng.*;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwTsQzkService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2020/11/30
 * @description 推送到前置库实现类
 */
@Service("yzwTsQzkServiceImpl")
public class YzwTsQzkServiceImpl implements YzwTsQzkService {
    private static final Logger LOGGER = LoggerFactory.getLogger(YzwTsQzkServiceImpl.class);

    @Autowired
    @Qualifier("sjptEntityMapper")
    private EntityMapper sjptMapper;
    @Autowired
    @Qualifier("yzwEntityMapper")
    private EntityMapper yzwMapper;

    /**
     * 推送道前置库
     *
     * @param infApplyDO@return
     * @Date 2020/11/30
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @Transactional(value = "yzwTransactionManager", rollbackFor = Exception.class)
    public HashMap<String, Object> tsQzk(InfApplyDO infApplyDO) {
        HashMap resultMap = Maps.newHashMap();
        Example tBmCasematerialExample = new Example(TBmCasematerialDO.class);
        Example tBmWlxxExample = new Example(TBmWlxxDO.class);
        Example tBmCaseprocessExample = new Example(TBmCaseprocessDO.class);
        Example tBmCaseacceptExample = new Example(TBmCaseacceptDO.class);
        Example tBmJgzzExample = new Example(TBmJgzzDO.class);
        Example tBmCaseresultExample = new Example(TBmCaseresultDO.class);

        tBmCasematerialExample.createCriteria().andEqualTo("caseno", infApplyDO.getInternalNo());
        tBmWlxxExample.createCriteria().andEqualTo("internalNo", infApplyDO.getInternalNo());
        tBmCaseprocessExample.createCriteria().andEqualTo("caseno", infApplyDO.getInternalNo());
        tBmCaseacceptExample.createCriteria().andEqualTo("caseno", infApplyDO.getInternalNo());
        tBmJgzzExample.createCriteria().andEqualTo("caseno", infApplyDO.getInternalNo());
        tBmCaseresultExample.createCriteria().andEqualTo("caseno", infApplyDO.getInternalNo());

        TBmCasebaseinfoDO tBmCasebaseinfo = sjptMapper.selectByPrimaryKey(TBmCasebaseinfoDO.class, infApplyDO.getInternalNo());
        List<TBmCasematerialDO> tBmCasematerialList = sjptMapper.selectByExampleNotNull(tBmCasematerialExample);
        List<TBmWlxxDO> tBmWlxxList = sjptMapper.selectByExampleNotNull(tBmWlxxExample);
        List<TBmCaseprocessDO> tBmCaseprocessList = sjptMapper.selectByExampleNotNull(tBmCaseprocessExample);
        List<TBmCaseacceptDO> tBmCaseacceptList = sjptMapper.selectByExampleNotNull(tBmCaseacceptExample);
        List<TBmJgzzDO> tBmJgzzList = sjptMapper.selectByExampleNotNull(tBmJgzzExample);
        List<TBmCaseresultDO> tBmCaseresultList = sjptMapper.selectByExampleNotNull(tBmCaseresultExample);

        List<TBmCasebaseinfoDO> tBmCasebaseinfoListTemp = Lists.newArrayList();
        List<TBmCasematerialDO> tBmCasematerialListTemp = Lists.newArrayList();
        List<TBmWlxxDO> tBmWlxxListTemp = Lists.newArrayList();
        List<TBmCaseprocessDO> tBmCaseprocessListTemp = Lists.newArrayList();
        List<TBmCaseacceptDO> tBmCaseacceptListTemp = Lists.newArrayList();
        List<TBmJgzzDO> tBmJgzzListTemp = Lists.newArrayList();
        List<TBmCaseresultDO> tBmCaseresultListTemp = Lists.newArrayList();

        //查询前置库是否有值

        //结果已推送
        boolean jgyts = false;
        //受理已推送
        boolean slyts = false;
        if (tBmCasebaseinfo != null) {
            if (yzwMapper.selectByPrimaryKey(TBmCasebaseinfoDO.class, tBmCasebaseinfo.getCaseno()) == null) {
                tBmCasebaseinfoListTemp.add(tBmCasebaseinfo);
            }
        }
        if (CollectionUtils.isNotEmpty(tBmCasematerialList)) {
            for (TBmCasematerialDO tBmCasematerial : tBmCasematerialList) {
                if (yzwMapper.selectByPrimaryKey(TBmCasematerialDO.class, tBmCasematerial.getOnlymark()) == null) {
                    tBmCasematerialListTemp.add(tBmCasematerial);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(tBmWlxxList)) {
            for (TBmWlxxDO tBmWlxx : tBmWlxxList) {
                if (yzwMapper.selectByPrimaryKey(TBmWlxxDO.class, tBmWlxx.getNo()) == null) {
                    tBmWlxxListTemp.add(tBmWlxx);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(tBmCaseprocessList)) {
            for (TBmCaseprocessDO tBmCaseprocess : tBmCaseprocessList) {
                if (yzwMapper.selectByPrimaryKey(TBmCaseprocessDO.class, tBmCaseprocess.getOnlymark()) == null) {
                    tBmCaseprocessListTemp.add(tBmCaseprocess);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(tBmCaseacceptList)) {
            for (TBmCaseacceptDO tBmCaseaccept : tBmCaseacceptList) {
                if (yzwMapper.selectByPrimaryKey(TBmCaseacceptDO.class, tBmCaseaccept.getOnlymark()) == null) {
                    tBmCaseacceptListTemp.add(tBmCaseaccept);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(tBmJgzzList)) {
            for (TBmJgzzDO tBmJgzz : tBmJgzzList) {
                if (yzwMapper.selectByPrimaryKey(TBmJgzzDO.class, tBmJgzz.getOnlymark()) == null) {
                    tBmJgzzListTemp.add(tBmJgzz);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(tBmCaseresultList)) {
            for (TBmCaseresultDO tBmCaseresult : tBmCaseresultList) {
                if (yzwMapper.selectByPrimaryKey(TBmCaseresultDO.class, tBmCaseresult.getOnlymark()) == null) {
                    tBmCaseresultListTemp.add(tBmCaseresult);
                }
            }
        }

        //开始推送
        if (CollectionUtils.isNotEmpty(tBmCasebaseinfoListTemp)) {
            yzwMapper.insertBatchSelective(tBmCasebaseinfoListTemp);
        }
        if (CollectionUtils.isNotEmpty(tBmCasematerialListTemp)) {
            yzwMapper.insertBatchSelective(tBmCasematerialListTemp);
        }
        if (CollectionUtils.isNotEmpty(tBmWlxxListTemp)) {
            yzwMapper.insertBatchSelective(tBmWlxxListTemp);
        }
        if (CollectionUtils.isNotEmpty(tBmCaseprocessListTemp)) {
            yzwMapper.insertBatchSelective(tBmCaseprocessListTemp);
        }
        if (CollectionUtils.isNotEmpty(tBmCaseacceptListTemp)) {
            yzwMapper.insertBatchSelective(tBmCaseacceptListTemp);
        }
        if (CollectionUtils.isNotEmpty(tBmJgzzListTemp)) {
            yzwMapper.insertBatchSelective(tBmJgzzListTemp);
        }
        if (CollectionUtils.isNotEmpty(tBmCaseresultListTemp)) {
            yzwMapper.insertBatchSelective(tBmCaseresultListTemp);
        }
        if (CollectionUtils.isNotEmpty(tBmCaseresultListTemp)) {
            // 本次推送结束即会推入前置库
            jgyts = true;
        } else if (CollectionUtils.isNotEmpty(tBmCaseresultList)) {
            // 实际已推送
            jgyts = true;
        }
        if (CollectionUtils.isNotEmpty(tBmCaseacceptListTemp)) {
            // 本次推送结束即会推入前置库
            slyts = true;
        } else if (CollectionUtils.isNotEmpty(tBmCaseacceptList)) {
            // 实际已推送
            slyts = true;
        }
        resultMap.put("jgyts", jgyts);
        resultMap.put("slyts", slyts);
        return resultMap;
    }
    //


}
