package cn.gtmap.realestate.init.core.service.impl;


import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcLsxmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.mapper.BdcXmLsgxMapper;
import cn.gtmap.realestate.init.core.service.BdcXmLsgxService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/10/31
 * @description 项目历史关系服务
 */
@Service
@Validated
public class BdcXmLsgxServiceImpl implements BdcXmLsgxService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(BdcXmLsgxServiceImpl.class);

    @Autowired
    private BdcXmLsgxMapper bdcXmLsgxMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private EntityMapper entityMapper;

    /**
     * @param bdcXmLsgxQO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询项目的一级历史关系数据
     */
    @Override
    public List<BdcXmLsgxDO> listBdcXmLsgx(BdcXmLsgxQO bdcXmLsgxQO) {
        if (StringUtils.isBlank(bdcXmLsgxQO.getXmid()) && StringUtils.isBlank(bdcXmLsgxQO.getYxmid())){
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        BdcXmLsgxDO bdcXmLsgxDO = new BdcXmLsgxDO();
        BeanUtils.copyProperties(bdcXmLsgxQO,bdcXmLsgxDO);
        return entityMapper.selectByObj(bdcXmLsgxDO);
    }

    /**
     * 批量查询历史关系
     * @param bdcXmLsgxQO
     * @return
     */
    @Override
    public List<BdcXmLsgxDO> listBdcXmLsgxs(BdcXmLsgxQO bdcXmLsgxQO) {
        if (StringUtils.isBlank(bdcXmLsgxQO.getXmid())
                && StringUtils.isBlank(bdcXmLsgxQO.getYxmid())
                && CollectionUtils.isEmpty(bdcXmLsgxQO.getXmids())
        ){
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(bdcXmLsgxQO));
        return bdcXmLsgxMapper.queryBdcXmLsgxList(jsonObject);
    }

    @Override
    public List<BdcXmLsgxDO> queryBdcXmLsgxList(String slbh, String gzlslid, Integer zxyql) {
        if (StringUtils.isBlank(slbh) && StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        Map map=new HashMap<>();
        map.put("slbh",slbh);
        map.put("gzlslid",gzlslid);
        map.put("zxyql",zxyql);
        return bdcXmLsgxMapper.queryBdcXmLsgxList(map);
    }

    /**
     * 通过项目id去获取关系表
     * @param order 排序
     * @param xmid 不动产项目ID
     * @return
     */
    @Override
    public List<BdcXmLsgxDO> queryBdcXmLsgxByXmid(String xmid,String order) {
        if(StringUtils.isNotBlank(xmid)){
            Map map=new HashMap<>();
            map.put("xmid",xmid);
            map.put("order",order);
            return bdcXmLsgxMapper.queryBdcXmLsgxList(map);
        }
        return Collections.emptyList();
    }

    /**
     * 通过项目id去获取关系表
     *
     * @param xmids
     * @param wlxm  排序
     * @return
     */
    @Override
    public List<BdcXmLsgxDO> queryBdcXmLsgxByXmids(List<String> xmids, Integer wlxm) {
        if(CollectionUtils.isNotEmpty(xmids)){
            Map map=new HashMap<>();
            map.put("xmids",xmids);
            map.put("wlxm",wlxm);
            return bdcXmLsgxMapper.queryBdcXmLsgxList(map);
        }
        return Collections.emptyList();
    }

    /**
     * 通过项目id去获取下手数据的关系表
     *
     * @param xmid 不动产项目ID
     * @return
     */
    @Override
    public List<BdcXmLsgxDO> queryBdcXmNextLsgxByXmid(String xmid,String order,Integer wlxm) {
        if(StringUtils.isNotBlank(xmid)){
            Map map=new HashMap<>();
            map.put("yxmid",xmid);
            map.put("order",order);
            map.put("wlxm",wlxm);
            return bdcXmLsgxMapper.queryBdcXmLsgxList(map);
        }
        return Collections.emptyList();
    }

    /**
     * @param bdcXmLsgxDOList
     * @return
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 外联证书，批量插入不动产项目历史关系
     */
    @Override
    public void insertBatchBdcXmLsgx(List<BdcXmLsgxDO> bdcXmLsgxDOList) {
        if(CollectionUtils.isNotEmpty(bdcXmLsgxDOList)){
            for(BdcXmLsgxDO bdcXmLsgxDO : bdcXmLsgxDOList){
                if(bdcXmLsgxDO != null){
                    bdcXmLsgxDO.setGxid(UUIDGenerator.generate16());
                    entityMapper.insertSelective(bdcXmLsgxDO);
                }
            }
        }
    }

    /**
     * 嵌套获取下手项目关系信息
     *
     * @param xmid
     * @param list
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public List<BdcXmLsgxDO> nextBdcXmLsgx(String xmid, List<BdcXmLsgxDO> list, Integer wlxm,boolean isCq) {
        if (StringUtils.isBlank(xmid)) {
            return Lists.emptyList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        HashSet<String> xmidSet = new HashSet<>();
        return nextBdcXmLsgx(xmid, list, wlxm, xmidSet,isCq);
    }

    @Override
    public List<BdcXmLsgxDO> prevBdcXmLsgx(String xmid, List<BdcXmLsgxDO> list,Integer wlxm,boolean isCq){
        if (StringUtils.isBlank(xmid)) {
            return Lists.emptyList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        HashSet<String> xmidSet = new HashSet<>();
        return prevBdcXmLsgx(xmid, list, wlxm, xmidSet,isCq);
    }



    /**
     * 递归获取项目关系
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private List<BdcXmLsgxDO> nextBdcXmLsgx(String xmid, List<BdcXmLsgxDO> list, Integer wlxm, Set<String> hashSet,boolean isCq) {
        if (list == null) {
            list = new ArrayList<>();
        }
        if(CollectionUtils.isEmpty(hashSet)){
            LOGGER.info("添加一条日志，查看梳理历史关系闭环的起始 xmid：{}", xmid);
        }
        //查询上手项目id---
        List<BdcXmLsgxDO> listNextXm = queryBdcXmNextLsgxByXmid(xmid, "b.slsj asc", wlxm);
        if (CollectionUtils.isNotEmpty(listNextXm)) {
            for (BdcXmLsgxDO bdcXmLsgxDO : listNextXm) {
                if (!hashSet.add(bdcXmLsgxDO.getXmid())) {
                    LOGGER.error("数据历史关系存在问题 xmid {}，重复 xmid：{}", xmid, bdcXmLsgxDO.getXmid());
                    throw new AppException("数据历史关系链中存在闭环，请核实数据");
                }
                list.add(bdcXmLsgxDO);
                if (StringUtils.isNotBlank(bdcXmLsgxDO.getXmid())) {
                    BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, bdcXmLsgxDO.getXmid());
                    if(isCq){
                        //产权权利继续向下递归
                        if (bdcXmDO != null  && !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDO.getQllx())) {
                            nextBdcXmLsgx(bdcXmLsgxDO.getXmid(), list, wlxm, hashSet, isCq);
                        } else if (bdcXmDO != null && CommonConstantUtils.QLLX_YG_DM.equals(bdcXmDO.getQllx())) {
                            BdcYgDO bdcYgDO = entityMapper.selectByPrimaryKey(BdcYgDO.class, bdcXmDO.getXmid());
                            if(bdcYgDO ==null){
                                bdcYgDO =entityMapper.selectByPrimaryKey(BdcYgDO.class,bdcXmLsgxDO.getYxmid());
                            }
                            //预告特殊处理,预告计入产权,预抵押不计入产权
                            if (bdcYgDO != null && ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_ARR, bdcYgDO.getYgdjzl())) {
                                nextBdcXmLsgx(bdcXmLsgxDO.getXmid(), list, wlxm, hashSet, isCq);
                            }
                        }
                    }else {
                        //限制权利继续往下递归
                        if (bdcXmDO != null && ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDO.getQllx())) {
                            //如果是预告不再递归，预告不是限制权利
                            if (CommonConstantUtils.QLLX_YG_DM.equals(bdcXmDO.getQllx())) {
                                BdcYgDO bdcYgDO = entityMapper.selectByPrimaryKey(BdcYgDO.class, bdcXmDO.getXmid());
                                if(bdcYgDO ==null){
                                    bdcYgDO =entityMapper.selectByPrimaryKey(BdcYgDO.class,bdcXmLsgxDO.getYxmid());
                                }
                                if (bdcYgDO !=null && ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_ARR, bdcYgDO.getYgdjzl())) {
                                    continue;
                                }
                            }
                            nextBdcXmLsgx(bdcXmLsgxDO.getXmid(), list, wlxm, hashSet, isCq);
                        }
                    }
                }
            }
        }
        return  list;
    }

    /**
     * 递归获取项目关系
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private List<BdcXmLsgxDO> prevBdcXmLsgx(String xmid, List<BdcXmLsgxDO> list, Integer wlxm, Set<String> hashSet,boolean isCq) {
        if (list == null) {
            list = new ArrayList<>();
        }
        if(CollectionUtils.isEmpty(hashSet)){
            LOGGER.info("添加一条日志，查看梳理历史关系闭环的起始 xmid：{}", xmid);
        }
        if(StringUtils.isNotBlank(xmid)) {
            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
            bdcXmLsgxQO.setXmid(xmid);
            bdcXmLsgxQO.setWlxm(wlxm);
            List<BdcXmLsgxDO> listPrevXm = listBdcXmLsgxs(bdcXmLsgxQO);
            if (CollectionUtils.isNotEmpty(listPrevXm)) {
                for (BdcXmLsgxDO bdcXmLsgxDO : listPrevXm) {
                    if (!hashSet.add(bdcXmLsgxDO.getYxmid())) {
                        LOGGER.error("数据历史关系存在问题 xmid {}，重复 xmid：{}", xmid, bdcXmLsgxDO.getXmid());
                        throw new AppException("数据历史关系链中存在闭环，请核实数据");
                    }
                    list.add(bdcXmLsgxDO);
                    if (StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid())) {
                        BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, bdcXmLsgxDO.getYxmid());
                        if(isCq) {
                            //产权权利继续向上递归
                            if (bdcXmDO != null  && !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDO.getQllx())) {
                                prevBdcXmLsgx(bdcXmLsgxDO.getYxmid(), list, wlxm, hashSet,isCq);
                            } else if (bdcXmDO != null && CommonConstantUtils.QLLX_YG_DM.equals(bdcXmDO.getQllx())) {
                                BdcYgDO bdcYgDO = entityMapper.selectByPrimaryKey(BdcYgDO.class, bdcXmDO.getXmid());
                                //预告特殊处理,预告计入产权,预抵押不计入产权
                                if (bdcYgDO != null && ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_ARR, bdcYgDO.getYgdjzl())) {
                                    prevBdcXmLsgx(bdcXmLsgxDO.getYxmid(), list, wlxm, hashSet,isCq);
                                }
                            }
                        }else{
                            //限制权利继续向上递归
                            if (bdcXmDO != null  && ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDO.getQllx())) {
                                if (CommonConstantUtils.QLLX_YG_DM.equals(bdcXmDO.getQllx())) {
                                    BdcYgDO bdcYgDO = entityMapper.selectByPrimaryKey(BdcYgDO.class, bdcXmDO.getXmid());
                                    //预告特殊处理,预告计入产权,预抵押不计入产权
                                    if (bdcYgDO != null && !ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_ARR, bdcYgDO.getYgdjzl())) {
                                        prevBdcXmLsgx(bdcXmLsgxDO.getYxmid(), list, wlxm, hashSet,isCq);
                                    }
                                }else {
                                    prevBdcXmLsgx(bdcXmLsgxDO.getYxmid(), list, wlxm, hashSet, isCq);
                                }
                            }
                        }
                    }
                }
            }
        }
        return  list;
    }

    /**
     * 根据工作流实例ID删除相关的历史关系数据
     *
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public void deleteBdcXmLsgx(@NotBlank(message = "工作流实例ID不能为空")String gzlslid) {
        bdcXmLsgxMapper.deleteBdcXmLsgx(gzlslid);
    }

    /**
     * 根据关系id删除相关的历史关系数据
     *
     * @param gxid 历史关系ID
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public int deleteLsgxById(@NotBlank(message = "历史关系ID") String gxid) {
        return entityMapper.deleteByPrimaryKey(BdcXmLsgxDO.class,gxid);
    }

    /**
     * 根据关系ids删除相关的历史关系数据
     *
     * @param gxids 历史关系ID
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public int deleteLsgxByIds(String[] gxids) {
        int num=0;
        if(gxids!=null && gxids.length>0){
            for(String id:gxids){
                int delSize=deleteLsgxById(id);
                num+=delSize;
            }
        }
        return num;
    }

    @Override
    public void deleteBdcYxmLsgx(@NotBlank(message = "工作流实例ID不能为空") String gzlslid){
        bdcXmLsgxMapper.deleteBdcYxmLsgx(gzlslid);
    }

    @Override
    public List<BdcXmDO> getAllLsXmByLsgx(BdcLsxmQO bdcLsxmQO){
        if(StringUtils.isBlank(bdcLsxmQO.getXmid())){
            throw new AppException(ErrorCode.MISSING_ARG,"查询历史项目缺失项目ID");
        }
        return bdcXmLsgxMapper.getAllLsXmByLsgx(bdcLsxmQO);

    }
}
