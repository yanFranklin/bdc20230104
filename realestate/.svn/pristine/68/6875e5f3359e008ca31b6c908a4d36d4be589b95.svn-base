package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.config.FdjywConfig;
import cn.gtmap.realestate.accept.core.service.BdcSlFdjywService;
import cn.gtmap.realestate.accept.core.service.BdcSlXmLsgxService;
import cn.gtmap.realestate.accept.core.service.BdcSlXmService;
import cn.gtmap.realestate.accept.core.service.BdcSlXzxxService;
import cn.gtmap.realestate.accept.service.BdcQllxService;
import cn.gtmap.realestate.common.core.domain.BdcCshFwkgDO;
import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXzxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXzxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCshXtPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: realestate
 * @description: 流程修正相关服务实现
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2021-05-25 17:24
 **/
@Service
public class BdcSlXzxxServiceImpl implements BdcSlFdjywService, BdcSlXzxxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlXzxxServiceImpl.class);
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    BdcSlXmService bdcSlXmService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    FdjywConfig fdjywConfig;

    @Autowired
    BdcSlXmLsgxService bdcSlXmLsgxService;

    @Autowired
    BdcCshXtPzFeignService bdcCshXtPzFeignService;

    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;
    @Autowired
    BdcQllxService bdcQllxService;

    /**
     * @param bdcSlXzxxDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查询流程修正信息
     * @date : 2021/9/23 17:21
     */
    @Override
    public BdcSlXzxxDO queryBdcSlXzxx(BdcSlXzxxDO bdcSlXzxxDO) {
        List<BdcSlXzxxDO> bdcSlXzxxDOList = new ArrayList<>(1);
        bdcSlXzxxDOList = entityMapper.selectByObj(bdcSlXzxxDO);
        if(CollectionUtils.isNotEmpty(bdcSlXzxxDOList)) {
            return bdcSlXzxxDOList.get(0);
        }
        return new BdcSlXzxxDO();
    }

    /**
     * @param bdcSlXzxxDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 保存流程修正信息数据
     * @date : 2021/9/23 17:29
     */
    @Override
    public BdcSlXzxxDO saveBdcSlXzxx(BdcSlXzxxDO bdcSlXzxxDO) {
        if(StringUtils.isBlank(bdcSlXzxxDO.getXzxxid())) {
            bdcSlXzxxDO.setXzxxid(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcSlXzxxDO);
        } else {
            entityMapper.updateByPrimaryKeySelective(bdcSlXzxxDO);
        }
        return bdcSlXzxxDO;
    }

    /**
     * @param Xzxxid
     * @param xmid
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除流程修正信息
     * @date : 2021/9/23 17:37
     */
    @Override
    public int deleteBdcSlXzxx(String Xzxxid, String xmid) {
        if(StringUtils.isNotBlank(Xzxxid)) {
            return entityMapper.deleteByPrimaryKey(BdcSlXzxxDO.class,Xzxxid);
        } else if(StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlXzxxDO.class);
            example.createCriteria().andEqualTo("xmid",xmid);
            return entityMapper.deleteByExample(example);
        } else {
            return -1;
        }
    }

    @Override
    public void deleteBdcSlXzxxByGzlslid(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcSlXzxxDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            entityMapper.deleteByExample(example);
        }
    }

    @Override
    public void cshBdcXzxx(String processInsId, BdcSlCshDTO bdcSlCshDTO) {
    }

    /**
     * @param bdcSlXzxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询修正信息
     * @date : 2021/9/27 19:23
     */
    @Override
    public List<BdcSlXzxxDO> listBdcSlXzxx(BdcSlXzxxQO bdcSlXzxxQO) {
        if (!CheckParameter.checkAnyParameter(bdcSlXzxxQO)) {
            throw new MissingArgumentException("缺失查询参数" + bdcSlXzxxQO.toString());
        }
        BdcSlXzxxDO bdcSlXzxxDO = new BdcSlXzxxDO();
        BeanUtils.copyProperties(bdcSlXzxxQO, bdcSlXzxxDO);
        List<BdcSlXzxxDO> bdcSlXzxxDOList = new ArrayList<>(1);
        bdcSlXzxxDOList = entityMapper.selectByObj(bdcSlXzxxDO);
        if (CollectionUtils.isNotEmpty(bdcSlXzxxDOList)) {
            return bdcSlXzxxDOList;
        }
        return Collections.emptyList();
    }

    /**
     * @param bdcSlCshDTO 受理初始化对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化非登记业务信息
     */
    @Override
    public void initFdjywxx(BdcSlCshDTO bdcSlCshDTO) {
        List<BdcSlXzxxDO> bdcSlXzxxDOList = new ArrayList<>(1);
        if (StringUtils.isNotBlank(bdcSlCshDTO.getGzlslid())) {
            //查询受理项目表数据
            List<BdcSlXmDO> bdcSlXmDOLsit = bdcSlXmService.listBdcSlXmByGzlslid(bdcSlCshDTO.getGzlslid());
            LOGGER.info("当前修正来源{}", bdcSlCshDTO.getXzly());
            if (CollectionUtils.isNotEmpty(bdcSlXmDOLsit) && !Objects.equals(CommonConstantUtils.XZLY_WSJ, bdcSlCshDTO.getXzly())) {
                for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOLsit) {
                    List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx(bdcSlXmDO.getXmid(), "", CommonConstantUtils.SF_F_DM);
                    if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                        BdcSlXzxxDO bdcSlXzxxDO = new BdcSlXzxxDO();
                        bdcSlXzxxDO.setYxmid(bdcSlXmLsgxDOList.get(0).getYxmid());
                        bdcSlXzxxDO.setBdcqzh(bdcSlXmDO.getYbdcqz());
                        bdcSlXzxxDO.setZl(bdcSlXmDO.getZl());
                        bdcSlXzxxDO.setGzlslid(bdcSlCshDTO.getGzlslid());
                        bdcSlXzxxDO.setQlr(bdcSlXmDO.getQlr());
                        bdcSlXzxxDO.setBlfs(2);
                        bdcSlXzxxDO.setXzxxid(UUIDGenerator.generate16());
                        bdcSlXzxxDO.setXzly(bdcSlCshDTO.getXzly());
                        bdcSlXzxxDO.setYlcjdmc(bdcSlCshDTO.getJdmc());
                        //修正来源是选择台账创建的，根据yxmid 判断是否有初始化开关实例数据，没有就新增一条
                        if (Objects.equals(CommonConstantUtils.XZLY_XZTZ, bdcSlCshDTO.getXzly())) {
                            //处理上一手的数据登记小类
                            String djxl = dealYxmDjxl(bdcSlXmLsgxDOList.get(0).getYxmid());
                            insertCshKgsl(bdcSlXmLsgxDOList.get(0).getYxmid(), djxl);
                            //判断是否有正在办理的流程数据
                            BdcXmQO bdcXmQO = new BdcXmQO();
                            bdcXmQO.setBdcdyh(bdcSlXmDO.getBdcdyh());
                            bdcXmQO.setAjzt(CommonConstantUtils.AJZT_ZB_DM);
                            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                                //存在在办任务，修正来源改变，上一手数据改为在办的信息数据
                                LOGGER.info("当前选择台账数据存在正在办理业务{},修正来源调整为4", bdcXmDOList.get(0).getSlbh());
                                //如果在办任务是组合流程只取第一条就不对了，要找到再办任务的项目历史关系，且yxmid和当前的yxmid 一致才行
                                for (BdcXmDO bdcXmDO : bdcXmDOList) {
                                    BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
                                    bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_F_DM);
                                    bdcXmLsgxQO.setXmid(bdcXmDO.getXmid());
                                    List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
                                    if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList) && StringUtils.equals(bdcXmLsgxDOList.get(0).getYxmid(), bdcSlXmLsgxDOList.get(0).getYxmid())) {
                                        LOGGER.info("当前选择台账数据存在正在办理业务xmid={},修正来源调整为4", bdcXmDO.getXmid());
                                        bdcSlXzxxDO.setYxmid(bdcXmDO.getXmid());
                                        bdcSlXzxxDO.setBdcqzh(bdcXmDO.getBdcqzh());
                                        bdcSlXzxxDO.setZl(bdcXmDO.getZl());
                                        bdcSlXzxxDO.setQlr(bdcXmDO.getQlr());
                                        bdcSlXzxxDO.setXzly(CommonConstantUtils.XZLY_XZTZ_ZZBL);
                                        break;
                                    }
                                }
                            }
                        }
                        //如果是流程内创建
                        if (Objects.equals(CommonConstantUtils.XZLY_LCNCJ, bdcSlCshDTO.getXzly())) {
                            //上一手是当前创建的流程，肯定是存在初始化开关实例的，需要判断现势数据展示的页面-上一手的上一手数据
                            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
                            bdcXmLsgxQO.setXmid(bdcSlXzxxDO.getYxmid());
                            List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
                            if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
                                LOGGER.info("当前修正来源{},现势数据xmid{}", 2, bdcXmLsgxDOList.get(0).getYxmid());
                                String djxl = dealYxmDjxl(bdcXmLsgxDOList.get(0).getYxmid());
                                insertCshKgsl(bdcXmLsgxDOList.get(0).getYxmid(), djxl);
                            }
                        }
                        bdcSlXzxxDOList.add(bdcSlXzxxDO);
                    }
                }
            } else {
                BdcSlXzxxDO bdcSlXzxxDO = new BdcSlXzxxDO();
                //没有受理项目表数据说明是直接创建修正流程信息
                bdcSlXzxxDO.setQlr(bdcSlCshDTO.getCdqlr());
                bdcSlXzxxDO.setBdcqzh(bdcSlCshDTO.getCdcqzh());
                bdcSlXzxxDO.setZl(bdcSlCshDTO.getCdzl());
                bdcSlXzxxDO.setBlfs(1);
                bdcSlXzxxDO.setGzlslid(bdcSlCshDTO.getGzlslid());
                bdcSlXzxxDO.setXzly(3);
                bdcSlXzxxDO.setXzxxid(UUIDGenerator.generate16());
                bdcSlXzxxDOList.add(bdcSlXzxxDO);
            }
        }
        if (CollectionUtils.isNotEmpty(bdcSlXzxxDOList)) {
            entityMapper.insertBatchSelective(bdcSlXzxxDOList);
        }
    }

    /**
     * @return 接口标识码，同一接口中的标识码不能出现重复
     * @author <a href ="mailto:zhaodongdong@gtmap.cn">zhaodongdong</a>
     * @description 获取实现类的标识码
     */
    @Override
    public Set<String> getInterfaceCode() {
        Set<String> set = new LinkedHashSet<>(2);
        List<String> cdxxGzldyidList = fdjywConfig.getFdjywlcDyidList("xzlc");
        if (CollectionUtils.isNotEmpty(cdxxGzldyidList)) {
            set.addAll(cdxxGzldyidList);
        } else {
            set.add("xzlc");
        }
        return set;
    }

    public void insertCshKgsl(String yxmid, String djxl) {
        BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(yxmid);
        if (Objects.isNull(bdcCshFwkgSlDO) || StringUtils.isBlank(bdcCshFwkgSlDO.getId())) {
            LOGGER.warn("修正流程的上一手数据xmid{}没有对应的初始化开关实例数据,手动新增,登记小类为{}", yxmid, djxl);
            if (StringUtils.isNotBlank(djxl)) {
                BdcCshFwkgDO bdcCshFwkgDO = bdcCshXtPzFeignService.queryBdcCshFwKgDO(djxl);
                if (Objects.nonNull(bdcCshFwkgDO)) {
                    // 手动插入一条新数据确保 重新生成证书 不抛出异常
                    BdcCshFwkgSlDO cshFwkgSlDO = new BdcCshFwkgSlDO();
                    cshFwkgSlDO.setYwrsjly(bdcCshFwkgDO.getYwrsjly());
                    cshFwkgSlDO.setSfsczs(bdcCshFwkgDO.getSfsczs());
                    cshFwkgSlDO.setDjxl(bdcCshFwkgDO.getDjxl());
                    cshFwkgSlDO.setSfzxyql(bdcCshFwkgDO.getSfzxyql());
                    cshFwkgSlDO.setQlsjly(bdcCshFwkgDO.getQlsjly());
                    cshFwkgSlDO.setSfscql(bdcCshFwkgDO.getSfscql());
                    cshFwkgSlDO.setZszl(bdcCshFwkgDO.getZszl());
                    cshFwkgSlDO.setId(yxmid);
                    bdcXmFeignService.insertBdcCshFwkgSl(cshFwkgSlDO);
                } else {
                    LOGGER.error("当前登记小类未配置初始化开关实例{}", djxl);
                }
            }
        }
    }

    public String dealYxmDjxl(String yxmid) {
        StringBuffer djxl = new StringBuffer();
        BdcXmQO bdcXmQO = new BdcXmQO(yxmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            LOGGER.error("修正上一手数据xmid{}对应的登记小类:{}", yxmid, bdcXmDOList.get(0).getDjxl());
            if (!StringUtils.isNumeric(bdcXmDOList.get(0).getDjxl())) {
                //如果登记小类不是数字,根据djlx+qllx+01 组成默认登记小类
                BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                Integer dyhQllx = bdcQllxService.getQllxByBdcdyh(bdcXmDO.getBdcdyh(), "1");
                if (Objects.isNull(dyhQllx)) {
                    dyhQllx = bdcXmDO.getQllx();
                    LOGGER.error("当前单元号{}，获取权利类型异常，默认使用项目表的权利类型{}", bdcXmDO.getBdcdyh(), bdcXmDO.getQllx());
                }
                if (Objects.equals(CommonConstantUtils.QLLX_DYAQ_DM, bdcXmDO.getQllx())) {
                    LOGGER.error("当前修正的流程为抵押权，默认修改登记小类为999");
                    djxl.append("999").append(String.format("%02d", dyhQllx)).append("01");
                    bdcXmDO.setDjxl(String.valueOf(djxl));
                } else {
                    bdcXmDO.setDjxl(String.valueOf(djxl.append(bdcXmDO.getDjlx()).append(String.format("%02d", dyhQllx)).append("01")));
                }
                LOGGER.error("修正上一手数据受理编号{}，项目id{}，对应登记小类非数字，程序自动转换为{}", bdcXmDO.getSlbh(), bdcXmDO.getXmid(), bdcXmDO.getDjxl());
                bdcXmFeignService.updateBdcXm(bdcXmDO);
            } else {
                return bdcXmDOList.get(0).getDjxl();
            }
        }
        return djxl.toString();
    }
}
