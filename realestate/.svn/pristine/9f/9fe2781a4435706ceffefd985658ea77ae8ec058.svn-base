package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.register.BdcDjbQlDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcSyqxDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcSyqxPlDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.EntityNotFoundException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcCfQO;
import cn.gtmap.realestate.common.core.qo.register.BdcCfjgQO;
import cn.gtmap.realestate.common.core.qo.register.BdcFdcq3QO;
import cn.gtmap.realestate.common.core.qo.register.BdcZxQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.register.config.PropsConfig;
import cn.gtmap.realestate.register.core.dto.BdcGyqkDTO;
import cn.gtmap.realestate.register.core.dto.BdcQlNumDTO;
import cn.gtmap.realestate.register.core.dto.BdcRyzdDTO;
import cn.gtmap.realestate.register.core.qo.DbxxQO;
import cn.gtmap.realestate.register.core.service.*;
import cn.gtmap.realestate.register.core.service.impl.BdcCfServiceImpl;
import cn.gtmap.realestate.register.service.BdcQlxxService;
import cn.gtmap.realestate.register.service.BdcXmxxService;
import cn.gtmap.realestate.register.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/10/31
 * @description 权利信息业务实现类
 */
@Service
public class BdcQlxxServiceImpl implements BdcQlxxService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcQlxxServiceImpl.class);

    /**
     * 当前限定类名
     */
    private static final String CLASS_NAME = BdcQlxxServiceImpl.class.getName();
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcFdcq3GyxxService bdcFdcq3GyxxService;
    @Autowired
    BdcFdcq3Service bdcFdcq3Service;
    @Autowired
    BdcFdcqFdcqXmService bdcFdcqFdcqXmService;
    @Autowired
    PropsConfig propsConfig;
    @Autowired
    BeansResolveUtils beansResolveUtils;
    @Autowired
    Set<BdcQlService> bdcQlServiceSet;
    @Autowired
    Set<BdcXzQlService> bdcXzQlServiceSet;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    BdcCfServiceImpl bdcCfService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcXmxxService bdcXmxxService;

    @Value("${orderby.first:}")
    private String orderbyFirst;

    @Value("${orderby.second:}")
    private String orderbySecond;

    /**
     * @param bdcQl  权利对象
     * @param dbxxQO 登簿信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新权利的登簿信息和权属状态
     */
    @Override
    public void udpateBdcQlDbxxAndQszt(BdcQl bdcQl, DbxxQO dbxxQO) {
        if (null != bdcQl && StringUtils.isNotBlank(bdcQl.getQlid())) {
            // 更新权利登簿信息
            bdcQl.setDbr(dbxxQO.getDbr());
            bdcQl.setDjsj(dbxxQO.getDjsj());
            bdcQl.setDjjg(dbxxQO.getDjjg());
            // 更新项目的权属状态
            bdcQl.setQszt(dbxxQO.getQszt());
            // 空字段也更新，方便退回清空登簿信息
            entityMapper.updateByPrimaryKeyNull(bdcQl);
        }
    }

    /**
     * @param zys 总页数
     * @param ym  当前页码
     * @return String
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据总页数和起始页码，拼接页码显示的字符串
     */
    @Override
    public String generateYm(Integer zys, Integer ym) {
        Integer qsym = ym;
        char splitChar = '、';
        if (zys <= 0 || qsym <= 0) {
            return "";
        } else {
            // 起始页码
            StringBuilder ymStr = new StringBuilder(Integer.toString(qsym));
            for (int i = 1; i < zys; i++) {
                ++qsym;
                ymStr.append(splitChar).append(qsym);
            }
            return ymStr.toString();
        }
    }

    /**
     * @param totalQl 总数
     * @return Integer 页数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 计算所需页数
     */
    @Override
    public Integer calculateQlYs(Integer totalQl) {
        if (totalQl > 0) {
            return 1;
        }
        return 0;
    }

    /**
     * @param objectList 对象List
     * @return Integer list的总数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取所有对象list的个数
     */
    @Override
    public Integer getTotalQl(List objectList) {
        if (CollectionUtils.isNotEmpty(objectList)) {
            return CollectionUtils.size(objectList);
        }
        return 0;
    }

    /**
     * @param bdcdyh   房屋或者土地的不动产单元号
     * @param qsztList
     * @return Integer 计算所得的总页数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 计算产权的总页数（除建筑物区分业主中有部分）,将土地和非土地的区分开，是为了少查一些表
     */
    @Override
    public Integer calculateCqYs(String bdcdyh, List<Integer> qsztList) {
        // 房屋相关权利
        if (StringUtils.equals(BdcdyhToolUtils.getDzwTzm(bdcdyh), CommonConstantUtils.BDCLX_TZM_F)) {
            List<String> propsList = propsConfig.getFwCqBeans();
            List<BdcQlService> bdcFwcqServiceList = beansResolveUtils.listBeans(propsList);
            BdcQlNumDTO bdcQlNumDTO = new BdcQlNumDTO();
            for (BdcQlService bdcQlService : bdcFwcqServiceList) {
                // 除建筑物区分业主中有部分
                if (null != bdcQlService && !(bdcQlService instanceof BdcFdcq3Service)) {
                    bdcQlNumDTO = bdcQlService.countBdcQl(bdcQlNumDTO, bdcdyh, qsztList);
                    if (bdcQlNumDTO.getCqNum() > 0) {
                        return 1;
                    }
                }
            }
        } else {
            // 非房屋相关权利
            List<String> propsList = propsConfig.getFfwCqBeans();
            List<BdcQlService> bdcFfwcqServiceList = beansResolveUtils.listBeans(propsList);
            BdcQlNumDTO bdcQlNumDTO = new BdcQlNumDTO();
            for (BdcQlService bdcQlService : bdcFfwcqServiceList) {
                // 除建筑物区分业主中有部分
                if (null != bdcQlService && !(bdcQlService instanceof BdcFdcq3Service)) {
                    bdcQlNumDTO = bdcQlService.countBdcQl(bdcQlNumDTO, bdcdyh, qsztList);
                    if (bdcQlNumDTO.getCqNum() > 0) {
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return Integer 计算所得总页数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 计算建筑物区分业主共有部分页数
     */
    @Override
    public Integer calculateYzgyYs(String bdcdyh) {
        // 建筑物区分业主共有部分，只针对房屋进行查询
        if (StringUtils.equals(BdcdyhToolUtils.getDzwTzm(bdcdyh), CommonConstantUtils.BDCLX_TZM_F)) {
            List<BdcFdcq3GyxxDO> bdcFdcq3GyxxDOList = bdcFdcq3GyxxService.queryListFdcq3Gyxx(bdcdyh);
            if (CollectionUtils.isNotEmpty(bdcFdcq3GyxxDOList)) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * @param bdcdyh   房屋的不动产单元号
     * @param qsztList
     * @return List
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询房屋的产权
     */
    @Override
    public List<BdcQl> queryListBdcFwCq(String bdcdyh, List qsztList) {
        List<String> propsList = propsConfig.getFwCqBeans();
        List<BdcQlService> bdcQlServiceList = beansResolveUtils.listBeans(propsList);
        for (BdcQlService bdcQlService : bdcQlServiceList) {
            if (null != bdcQlService) {
                List<BdcQl> bdcQlList = bdcQlService.queryListBdcQl(bdcdyh, qsztList);
                if (CollectionUtils.isNotEmpty(bdcQlList)) {
                    return bdcQlList;
                }
            }
        }
        return new ArrayList();
    }

    /**
     * @param bdcdyh   土地的不动产单元号
     * @param qsztList
     * @return List
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询非房屋的产权（土地，林地，海域）
     */
    @Override
    public List<BdcQl> queryListBdcFfwCq(String bdcdyh, List<Integer> qsztList) {
        List<String> propsList = propsConfig.getFfwCqBeans();
        List<BdcQlService> bdcQlServiceList = beansResolveUtils.listBeans(propsList);
        for (BdcQlService bdcQlService : bdcQlServiceList) {
            if (null != bdcQlService) {
                List<BdcQl> bdcQlList = bdcQlService.queryListBdcQl(bdcdyh, qsztList);
                if (CollectionUtils.isNotEmpty(bdcQlList)) {
                    return bdcQlList;
                }
            }
        }
        return new ArrayList();
    }

    /**
     * @param bdcdyh   不动产单元号
     * @param qllx     权利类型
     * @param qsztList
     * @return listBdcQlxx
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元的指定类型的权利信息
     */
    @Override
    public List<BdcQl> listBdcQlxx(String bdcdyh, String qllx, List<Integer> qsztList) {
        if (StringUtils.isNotBlank(bdcdyh) && StringUtils.isNotBlank(qllx)) {
            BdcQlService bdcQlService = InterfaceCodeBeanFactory.getBean(bdcQlServiceSet, qllx);
            if (null != bdcQlService) {
                List<BdcQl> bdcQlList = bdcQlService.queryListBdcQl(bdcdyh, qsztList);
                if (CollectionUtils.isNotEmpty(bdcQlList)) {
                    return bdcQlList;
                }
            }
        }
        return new ArrayList();
    }

    /**
     * @param bdcdyh 不动产单元号
     * @param qllx   权利类型
     * @return list 注销权利信息
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 查询不动产单元的指定类型的注销权利信息
     */
    @Override
    public List<BdcQl> listBdcZxQlxx(String bdcdyh, String qllx) {
        if (StringUtils.isNotBlank(bdcdyh) && StringUtils.isNotBlank(qllx)) {
            BdcQlService bdcQlService = InterfaceCodeBeanFactory.getBean(bdcQlServiceSet, qllx);
            if (null != bdcQlService) {
                List<BdcQl> bdcQlList = bdcQlService.queryListBdcZxQl(bdcdyh);
                if (CollectionUtils.isNotEmpty(bdcQlList)) {
                    return bdcQlList;
                }
            }
        }
        return new ArrayList();
    }

    /**
     * @param qllx
     * @param map      查询参数
     * @param pageable
     * @return Page<BdcQl> 权利信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询登记簿各权利信息
     */
    @Override
    public Page<BdcQl> bdcQlxxByPageJson(String qllx, Map<String, Object> map, Pageable pageable) {
        if(StringUtils.isNotBlank(orderbyFirst)&&StringUtils.isNotBlank(orderbySecond)){
            map.put(Constants.ORDERBYFIRST,Constants.PREFIXORDERBY+orderbyFirst);
            map.put(Constants.ORDERBYSECOND,Constants.PREFIXORDERBY+orderbySecond);
        }
        BdcQlService bdcQlService = InterfaceCodeBeanFactory.getBean(bdcQlServiceSet, qllx);
        if (null != bdcQlService) {
            return bdcQlService.bdcQlByPageJson(map, pageable);
        }
        return null;
    }

    /**
     * @param xmid       项目ID
     * @param bdcGyqkDTO 共有情况对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新权利的共有情况
     */
    @Override
    public void updateQlGyqk(String xmid, BdcGyqkDTO bdcGyqkDTO) {
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
        if (null != bdcQl && StringUtils.isNotBlank(bdcQl.getQlid())) {
            bdcQl.setGyqk(bdcGyqkDTO.getQlGyqk());
            entityMapper.updateByPrimaryKeyNull(bdcQl);
        }
    }

    /**
     * @param bdcGyqkDTO 共有情况DTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新权利的共有情况（权利一致，权利人一致）
     */
    @Override
    public int updateQlGyqkPl(BdcGyqkDTO bdcGyqkDTO) {
        if ((StringUtils.isBlank(bdcGyqkDTO.getGzlslid()) && CollectionUtils.isEmpty(bdcGyqkDTO.getXmidList())) || StringUtils.isBlank(bdcGyqkDTO.getXmid())) {
            throw new MissingArgumentException("缺失工作流实例ID:" + bdcGyqkDTO.getGzlslid() + "或者缺失xmidList:" + bdcGyqkDTO.getXmidList() + ";或项目ID:" + bdcGyqkDTO.getXmid());
        }
        String xmid = bdcGyqkDTO.getXmid();
        if (StringUtils.isBlank(bdcGyqkDTO.getQlGyqk())) {
            LOGGER.debug("{}：停止批量更新权利的共有情况，获取到的共有情况为空!", CLASS_NAME);
        }
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
        if (bdcQl != null) {
            String className = String.valueOf(bdcQl.getClass().getSimpleName());
            BdcQlService bdcQlService = InterfaceCodeBeanFactory.getBean(bdcQlServiceSet, className);
            int result = 0;
            if(bdcQlService != null) {
                if (StringUtils.isNotBlank(bdcGyqkDTO.getGzlslid())) {
                    result = bdcQlService.updateQlGyqkPl(bdcGyqkDTO);
                } else if (CollectionUtils.isNotEmpty(bdcGyqkDTO.getXmidList())) {
                    List<List> xmidLists = ListUtils.subList(bdcGyqkDTO.getXmidList(), 800);
                    for (List<String> xmidList : xmidLists) {
                        bdcGyqkDTO.setXmidList(xmidList);
                        result += bdcQlService.updateQlGyqkPl(bdcGyqkDTO);
                    }
                }
            }
            LOGGER.info("更新的权利数量为：{}", result);
            return result;
        }
        return -1;
    }

    /**
     * @param dbxxQO 登簿信息QO
     * @param qllx
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新批量流程当前权利的登簿信息和权属状态
     */
    @Override
    public void udpateBdcQlDbxxAndQsztPl(DbxxQO dbxxQO, String qllx) {
        if (StringUtils.isBlank(dbxxQO.getGzlslid())) {
            throw new MissingArgumentException("缺失gzlslid！");
        }
        if (StringUtils.isBlank(qllx)) {
            LOGGER.info("未查询到权利信息！");
            return;
        }
        BdcQlService bdcQlService = InterfaceCodeBeanFactory.getBean(bdcQlServiceSet, qllx);
        if(bdcQlService == null){
            throw new AppException("当前权利类型"+qllx+"没有对应的实现类！");
        }
        int result = bdcQlService.udpateBdcQlDbxxAndQsztPl(dbxxQO);
        LOGGER.info("更新{}当前权利{},共{}条！", dbxxQO.getGzlslid(), qllx, result);
    }

    /**
     * @param dbxxQO 登簿信息QO
     * @param qllx
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新原权利的权属状态和注销登簿人
     */
    @Override
    public void updateYqlZxDbxxAndQsztPl(DbxxQO dbxxQO, String qllx) {
        if (StringUtils.isBlank(dbxxQO.getGzlslid())) {
            throw new MissingArgumentException("缺失gzlslid！");
        }
        if (StringUtils.isBlank(qllx)) {
            LOGGER.info("未查询到权利信息！");
            return;
        }
        // 正常权利注销时只更新权利的qszt信息
        BdcQlService bdcQlService = InterfaceCodeBeanFactory.getBean(bdcQlServiceSet, qllx);
        int result =0;
        if(bdcQlService != null) {
            // 正常流程只修改权属状态，注销类的限制流程（抵押注销，解封等）需要同时更新注销登簿人信息
            result = bdcQlService.udpateYqlZxDbxxAndQsztPl(dbxxQO);
        }else{
            LOGGER.error("gzlslid:{}当前qllx:{}没有对应的权利服务",dbxxQO.getGzlslid(),qllx);
            throw new AppException("当前qllx"+qllx+"没有对应的权利服务");
        }
        LOGGER.info("更新{}原权利{},共{}条！", dbxxQO.getGzlslid(), qllx, result);
    }

    /**
     * @param bdcQlList 权利信息
     * @return Map<String   ,       List       <       BdcQl>>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前权利的类型(应该按照生成的权利先后顺序返回)
     */
    @Override
    public List<String> classifyQlxx(List<BdcQl> bdcQlList) {
        List<String> qllxList = new ArrayList(CollectionUtils.size(bdcQlList));
        for (BdcQl bdcQl : bdcQlList) {
            if (bdcQl instanceof BdcYgDO && ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR, ((BdcYgDO) bdcQl).getYgdjzl()) && !qllxList.contains(CommonConstantUtils.YGDJ_YDY)) {
                qllxList.add(CommonConstantUtils.YGDJ_YDY);
            } else if (bdcQl != null && !qllxList.contains(bdcQl.getClass().getSimpleName())) {
                qllxList.add(String.valueOf(bdcQl.getClass().getSimpleName()));
            }
        }
        return qllxList;
    }

    /**
     * @param bdcCfjgQO 查封机关信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新查封信息的查封机关或解封机关信息
     */
    @Override
    public void updateCfjgOrJfjg(BdcCfjgQO bdcCfjgQO) {
        bdcCfService.updateCfjgOrJfjg(bdcCfjgQO);
    }

    /**
     * @param bdcFdcq3QO 建筑物所有权及业主共有信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 建筑物所有权及业主共有信息更新权利人
     */
    @Override
    public void updateFdcq3Qlr(BdcFdcq3QO bdcFdcq3QO) {
        bdcFdcq3Service.updateFdcq3Qlr(bdcFdcq3QO);
    }

    /**
     * @param bdcZxQO 注销信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据参数xmid，更新权利的注销信息
     */
    @Override
    public int updateBdcQlZxxxByXmid(BdcZxQO bdcZxQO) {
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcZxQO.getXmid());
        if (null == bdcQl) {
            LOGGER.info("xmid:{}未查询到权利信息！", bdcZxQO.getXmid());
            return -1;
        }
        return this.updateBdcQlZxxx(bdcQl, bdcZxQO);
    }

    /**
     * @param bdcQl   权利信息
     * @param bdcZxQO 注销信息
     * @return {@code int} 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 已知权利信息的时候，更新该权利的注销信息
     */
    @Override
    public int updateBdcQlZxxx(BdcQl bdcQl, BdcZxQO bdcZxQO) {
        String className = bdcQl.getClass().getSimpleName();
        BdcQlService bdcQlService = InterfaceCodeBeanFactory.getBean(bdcQlServiceSet, className);
        if (null != bdcQlService) {
            // 更新权属状态，注销人,注销时间，注销业务号等信息
            return bdcQlService.updateZxDbxx(bdcQl, bdcZxQO);
        }
        return -1;
    }

    /**
     * @param bdcDjbQlDTO
     * @param bdcdyh
     * @param qsztList
     * @return String 权利类型
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产单元的土地、海域、林权等其他权利类型值
     */
    @Override
    public BdcDjbQlDTO queryBdcQtCqQllx(BdcDjbQlDTO bdcDjbQlDTO, String bdcdyh, List<Integer> qsztList) {
        List<BdcQl> qtCqList = queryListBdcFfwCq(bdcdyh, qsztList);
        if (CollectionUtils.isNotEmpty(qtCqList)) {
            BdcQl bdcQl = qtCqList.get(0);
            // 获取产权对象名称
            bdcDjbQlDTO.setQtCq(bdcQl.getClass().getSimpleName());

            // 获取产权类型
            String xmid = bdcQl.getXmid();
            BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
            if (null != bdcXmDO) {
                bdcDjbQlDTO.setQtCqQllx(bdcXmDO.getQllx());
            } else {
                LOGGER.debug("不动产个单元{}的权利的项目ID{},未查询到项目信息！获取产权权利类型异常！", bdcdyh, xmid);
                throw new EntityNotFoundException("不动产单元号的权利未查询到对应的项目信息！");
            }
        }
        return bdcDjbQlDTO;
    }

    /**
     * @param bdcDjbQlDTO
     * @param bdcdyh
     * @param qsztList
     * @return String 权利类型
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产单元的房屋产权权利类型值
     */
    @Override
    public BdcDjbQlDTO queryBdcFwCqQllx(BdcDjbQlDTO bdcDjbQlDTO, String bdcdyh, List<Integer> qsztList) {
        List<BdcQl> fwCqList = queryListBdcFwCq(bdcdyh, qsztList);
        if (CollectionUtils.isNotEmpty(fwCqList)) {
            BdcQl bdcQl = fwCqList.get(0);
            // 获取产权对象名称
            bdcDjbQlDTO.setFwCq(bdcQl.getClass().getSimpleName());
            // 获取是否多幢
            if (bdcQl instanceof BdcFdcqDO) {
                BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                if (CommonConstantUtils.FWLX_DUOZH.equals(bdcFdcqDO.getBdcdyfwlx())) {
                    bdcDjbQlDTO.setSffdcqdz(Integer.toString(CommonConstantUtils.FWLX_DUOZH));
                }
                // 判断宗地是否有业主共有部分
                List<BdcFdcq3GyxxDO> bdcFdcq3GyxxDOList = bdcFdcq3GyxxService.queryListFdcq3Gyxx(bdcdyh);
                if (CollectionUtils.isNotEmpty(bdcFdcq3GyxxDOList)) {
                    bdcDjbQlDTO.setYzgy(true);
                }
            }

            // 获取产权类型
            String xmid = bdcQl.getXmid();
            BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
            if (null != bdcXmDO) {
                bdcDjbQlDTO.setFwQllx(bdcXmDO.getQllx());
            } else {
                LOGGER.debug("不动产个单元{}的权利的项目ID{},未查询到项目信息！获取产权权利类型异常！", bdcdyh, xmid);
                throw new EntityNotFoundException("不动产单元号的权利未查询到对应的项目信息！");
            }
        }
        return bdcDjbQlDTO;
    }

    /**
     * @param bdcdyh
     * @return List<BdcQl> 产权权利
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产单元号的产权权利
     */
    @Override
    public List<BdcQl> queryBdcCqlist(String bdcdyh) {
        List<BdcQl> cqqlList = new ArrayList();
        List<String> propsList = new ArrayList<>();
        List<String> tdPropsList = propsConfig.getFfwCqBeans();
        List<String> fwPropsList = propsConfig.getFwCqBeans();
        if (CollectionUtils.isNotEmpty(tdPropsList)) {
            propsList.addAll(tdPropsList);
        }
        if (CollectionUtils.isNotEmpty(fwPropsList)) {
            propsList.addAll(fwPropsList);
        }
        // 原遗留数据转换为登记数据后，一个不动产单元号会有土地产权和房屋产权两种产权
        List<BdcQlService> bdcQlServiceList = beansResolveUtils.listBeans(propsList);
        for (BdcQlService bdcQlService : bdcQlServiceList) {
            List<BdcQl> bdcQlListTemp = new ArrayList();
            if (null != bdcQlService) {
                bdcQlListTemp = bdcQlService.queryListBdcQl(bdcdyh, null);
            }
            if (CollectionUtils.isNotEmpty(bdcQlListTemp)) {
                cqqlList.addAll(bdcQlListTemp);
            }
        }
        return cqqlList;
    }

    /**
     * @param bdcZxQO         不动产注销信息
     * @author <a href ="mailto:zhangwentao@gtmap.cn">songhaowen</a>
     * @description 注销权利信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void zxQl(BdcZxQO bdcZxQO) {
        List<String> xmidList = bdcZxQO.getXmidList();
        if (CollectionUtils.isEmpty(xmidList)) {
            LOGGER.info("{}缺失xmidList！", CLASS_NAME);
            return;
        }
        for (String xmid : xmidList) {
            bdcXmxxService.updateBdcXmQszt(xmid, bdcZxQO.getQszt());

            bdcZxQO.setXmid(xmid);
            this.updateBdcQlZxxxByXmid(bdcZxQO);

            LOGGER.info("{}已完成项目{}：注销权利信息", CLASS_NAME, xmid);
        }
    }

    /**
     * @param gzlslid 当前流程的工作流实例ID
     * @param qszt    权属状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新当前项目的权属状态
     */
    @Override
    public void updateBdcQlQsztPl(String gzlslid, Integer qszt) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失gzlslid信息！");
        }
        //获取当前流程生成的权利的权利类型(返回的qllxList应该是按照业务逻辑根据项目生成的先后顺序排序的，预告应在预抵押前面)
        List<String> qllxList = bdcQllxFeignService.listQllxByProcessInsId(gzlslid);
        if (CollectionUtils.isEmpty(qllxList)) {
            LOGGER.warn("当前流程未生成新的权利！");
            return;
        }
        DbxxQO dbxxQO = new DbxxQO();
        dbxxQO.setGzlslid(gzlslid);
        dbxxQO.setQszt(qszt);
        for (String qllx : qllxList) {
            BdcQlService bdcQlService = InterfaceCodeBeanFactory.getBean(bdcQlServiceSet, qllx);
            int result = bdcQlService.updateBdcQlQsztPl(dbxxQO);
            LOGGER.info("更新当前权利{}的权属状态为{},共{}条！", qllx, qszt, result);
        }
    }

    @Override
    public void updateBdcQlRyzdQlr(BdcRyzdDTO bdcRyzdDTO){
        if (StringUtils.isBlank(bdcRyzdDTO.getXmid()) &&CollectionUtils.isEmpty(bdcRyzdDTO.getXmidList())) {
            LOGGER.warn("未更新权利表权利人冗余字段");
            return;
        }
        if(CollectionUtils.isNotEmpty(bdcRyzdDTO.getXmidList())){
            bdcRyzdDTO.setXmid(bdcRyzdDTO.getXmidList().get(0));
        }
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcRyzdDTO.getXmid());
        if (bdcQl != null) {
            String className = String.valueOf(bdcQl.getClass().getSimpleName());
            BdcQlService bdcQlService = InterfaceCodeBeanFactory.getBean(bdcQlServiceSet, className);
            if (bdcQlService != null) {
                if (CollectionUtils.isNotEmpty(bdcRyzdDTO.getXmidList())) {
                    List<List> xmLists = ListUtils.subList(bdcRyzdDTO.getXmidList(), 800);
                    for (List xmList : xmLists) {
                        bdcRyzdDTO.setXmidList(xmList);
                        bdcQlService.updateRyzdQlr(bdcRyzdDTO);
                    }
                } else {
                    bdcQlService.updateRyzdQlr(bdcRyzdDTO);
                }
            }
        }

    }

    /**
     * @param bdcCfQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/8/19 17:04
     */
    @Override
    public List<BdcCfDO> listBdcCfxx(BdcCfQO bdcCfQO) {
        return bdcCfService.listBdcCfxx(bdcCfQO);
    }

    @Override
    public BdcSyqxDTO generateSyqx(String xmid){
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("缺失参数xmid");
        }
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
        if (bdcQl != null) {
            String className = String.valueOf(bdcQl.getClass().getSimpleName());
            BdcQlService bdcQlService = InterfaceCodeBeanFactory.getBean(bdcQlServiceSet, className);
            if (bdcQlService != null) {
                return bdcQlService.generateQlSyqx(bdcQl);

            }
        }
        return null;

    }

    @Override
    public BdcSyqxDTO generateSyqxPl(BdcSyqxPlDTO bdcSyqxPlDTO){
        if(StringUtils.isNotBlank(bdcSyqxPlDTO.getQlclassName())) {
            BdcQlService bdcQlService = InterfaceCodeBeanFactory.getBean(bdcQlServiceSet, bdcSyqxPlDTO.getQlclassName());
            if (bdcQlService != null) {
                return bdcQlService.generateQlSyqxPl(bdcSyqxPlDTO.getJsonObject());

            }
        }
        return null;

    }

    @Override
    public void updateBdcQlDbxxPl(DbxxQO dbxxQO,String qllx){
        int result =0;
        if(StringUtils.isNotBlank(dbxxQO.getGzlslid()) &&StringUtils.isNotBlank(dbxxQO.getDbr())) {
            BdcQlService bdcQlService = InterfaceCodeBeanFactory.getBean(bdcQlServiceSet, qllx);
            if(bdcQlService != null) {
                result = bdcQlService.updateBdcQlDbrPl(dbxxQO);
            }
        }
        LOGGER.info("gzlslid:{}更新权利表登簿信息:{},共{}条", dbxxQO.getGzlslid(), dbxxQO, result);
    }

    @Override
    public void updateZxQlDbrPl(DbxxQO dbxxQO,String qllx){
        int result =0;
        if(StringUtils.isNotBlank(dbxxQO.getGzlslid()) &&StringUtils.isNotBlank(dbxxQO.getDbr())) {
            BdcQlService bdcQlService = InterfaceCodeBeanFactory.getBean(bdcQlServiceSet, qllx);
            if(bdcQlService !=null) {
                result = bdcQlService.updateZxQlDbrPl(dbxxQO);
            }
        }
        LOGGER.info("gzlslid:{}更新注销权利登簿人:{},共{}条", dbxxQO.getGzlslid(), dbxxQO, result);

    }

}
