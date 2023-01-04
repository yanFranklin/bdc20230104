package cn.gtmap.realestate.register.service.workflow.impl;

import cn.gtmap.gtc.workflow.clients.manage.ProcessDefinitionClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.annotations.WokrFlowLog;
import cn.gtmap.realestate.common.core.domain.register.BdcGzlJkDO;
import cn.gtmap.realestate.common.core.domain.register.BdcGzlSjDO;
import cn.gtmap.realestate.common.core.domain.register.BdcGzlsjJkGxDO;
import cn.gtmap.realestate.common.core.dto.register.BdcGzlSjJkDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcGzlsjPlDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcWorkFlowDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.register.BdcGzlQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.register.core.mapper.BdcGzlMapper;
import cn.gtmap.realestate.register.service.workflow.BdcWorkFlowService;
import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: realestate
 * @description: 工作流事件统一执行实现类
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-24 14:21
 **/
@Service
public class BdcWorkFlowServiceImpl implements BdcWorkFlowService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcWorkFlowServiceImpl.class);

    private static final Map<String, String> LCMCMAP = new HashMap<>(10);

    private static final Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            // 核心线程数量
            Runtime.getRuntime().availableProcessors(),
            // 最大线程数
            Runtime.getRuntime().availableProcessors() * 2,
            // 空闲超时一小时（调用频繁）
            600, TimeUnit.SECONDS,
            // 阻塞队列
            new ArrayBlockingQueue<>(100),
            // 线程工厂
            new ThreadFactoryBuilder().setNameFormat("workflow-pool-%d").build(),
            // 过多任务直接主线程处理
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcGzlMapper bdcGzlMapper;
    @Autowired
    RestRpcUtils restRpcUtils;

    @Autowired
    @Lazy
    BdcWorkFlowService bdcWorkFlowService;
    /**
     * 用户权限
     */
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    ProcessDefinitionClient processDefinitionClient;

    /**
     * @param gzlsjlx        工作流事件类型
     * @param bdcWorkFlowDTO 执行接口需要的入参
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/3/24 14:21
     */
    @Override
    public void executWorkFlowEvent(Integer gzlsjlx, BdcWorkFlowDTO bdcWorkFlowDTO) {
        //1.根据数据流类型 判断属于哪种事件，转发前，转发，转发后等等
        Date startTime = new Date();
        List<Map> gzlsjlxMap = bdcZdFeignService.queryBdcZd("gzlsjlx");
        String gzldyid = bdcWorkFlowDTO.getProcessDefKey();
        String gzlbs = "";
        for (Map map : gzlsjlxMap) {
            if (Objects.equals(map.get("DM"), gzlsjlx)) {
                gzlbs = gzldyid + "_" + map.get("SJBS");
                break;
            }
        }
        if (StringUtils.isNotBlank(gzlbs)) {
            //根据事件标识查询数据库所有关联的接口，分好类，并调用rpc执行
            BdcGzlQO bdcGzlQO = new BdcGzlQO();
            bdcGzlQO.setSjbs(gzlbs);
            bdcGzlQO.setJdmc(bdcWorkFlowDTO.getJdmc());
            List<BdcGzlSjJkDTO> bdcGzlSjJkDTOList = bdcGzlMapper.listGzlSjJk(bdcGzlQO);
            if (CollectionUtils.isNotEmpty(bdcGzlSjJkDTOList)) {
                //先执行异步方法
                //bdcWorkFlowDTO转换成map
                //先获取token，当前转发事件用同一个token不需要请求多次
                String token = tokenUtils.getAccessToken();
                LOGGER.warn("开始执行流程事件流程实例id={},当前节点{},当前获取token{},CPU核数={}", bdcWorkFlowDTO.getProcessInsId(), bdcWorkFlowDTO.getCurrentNodeName(), token, Runtime.getRuntime().availableProcessors());
                bdcWorkFlowDTO.setAccess_token(token);
                String json = JSON.toJSONString(bdcWorkFlowDTO);
                Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
                executeAllMethord(bdcGzlSjJkDTOList, paramMap);
                long time = System.currentTimeMillis() - startTime.getTime();
                LOGGER.warn("流程事件执行结束。流程实例id={}，节点名称{}此次执行耗时{}", bdcWorkFlowDTO.getProcessInsId(), bdcWorkFlowDTO.getCurrentNodeName(), time);
            } else {
                LOGGER.warn("当前流程实例id{}事件标识{}未获取到转发事件", bdcWorkFlowDTO.getProcessInsId(), gzlbs);
            }
        }


    }

    /**
     * @param bdcGzlSjDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增工作流事件
     * @date : 2022/3/30 15:26
     */
    @Override
    public BdcGzlSjDO insertBdcGzlSj(BdcGzlSjDO bdcGzlSjDO) {
        if (StringUtils.isBlank(bdcGzlSjDO.getSjid())) {
            bdcGzlSjDO.setSjid(UUIDGenerator.generate16());
        }
        entityMapper.insertSelective(bdcGzlSjDO);
        return bdcGzlSjDO;
    }

    /**
     * @param bdcGzlQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询工作流事件信息
     * @date : 2022/3/30 15:48
     */
    @Override
    public List<BdcGzlSjDO> listBdcGzlsj(BdcGzlQO bdcGzlQO) {
        List<BdcGzlSjDO> bdcGzlSjDOList = new ArrayList<>(1);
        if (!CheckParameter.checkAnyParameter(bdcGzlQO, "sjid", "sjbs", "jdmc")) {
            throw new AppException("查询工作流事件必须传入一个参数");
        }
        if (StringUtils.isNotBlank(bdcGzlQO.getSjid())) {
            BdcGzlSjDO bdcGzlSjDO = entityMapper.selectByPrimaryKey(BdcGzlSjDO.class, bdcGzlQO.getSjid());
            if (Objects.nonNull(bdcGzlSjDO) && StringUtils.isNotBlank(bdcGzlSjDO.getSjid())) {
                bdcGzlSjDOList.add(bdcGzlSjDO);
            }
        } else {
            bdcGzlSjDOList = bdcGzlMapper.listBdcGzlsjByPage(bdcGzlQO);
        }
        return bdcGzlSjDOList;
    }

    /**
     * @param sjid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除工作流事件，同时需要删除工作流事件和接口的关系表数据
     * @date : 2022/3/30 16:01
     */
    @Override
    public void deleteBdcGzlsj(String sjid) {
        if (StringUtils.isNotBlank(sjid)) {
            entityMapper.deleteByPrimaryKey(BdcGzlSjDO.class, sjid);
            //删除BDC_GZL_SJ_JK_GX 表的信息
            Example example = new Example(BdcGzlsjJkGxDO.class);
            example.createCriteria().andEqualTo("sjid", sjid);
            entityMapper.deleteByExampleNotNull(example);
        }
    }

    /**
     * @param bdcGzlJkDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增工作流接口
     * @date : 2022/3/30 17:13
     */
    @Override
    public int insertBdcGzlJk(BdcGzlJkDO bdcGzlJkDO) {
        if (StringUtils.isBlank(bdcGzlJkDO.getJkid())) {
            bdcGzlJkDO.setJkid(UUIDGenerator.generate16());
        }
        return entityMapper.insertSelective(bdcGzlJkDO);
    }

    /**
     * @param jkid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除工作流接口
     * @date : 2022/3/30 17:13
     */
    @Override
    public void deleteBdcGzlJk(String jkid) {
        if (StringUtils.isNotBlank(jkid)) {
            entityMapper.deleteByPrimaryKey(BdcGzlJkDO.class, jkid);
        }
    }

    /**
     * @param bdcGzlsjJkGxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增工作流接口关系
     * @date : 2022/3/30 17:19
     */
    @Override
    public int insertBdcGzlJkGx(BdcGzlsjJkGxDO bdcGzlsjJkGxDO) {
        if (StringUtils.isBlank(bdcGzlsjJkGxDO.getGxid())) {
            bdcGzlsjJkGxDO.setGxid(UUIDGenerator.generate16());
        }
        return entityMapper.insertSelective(bdcGzlsjJkGxDO);
    }

    /**
     * @param bdcGzlQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询工作流接口
     * @date : 2022/4/7 16:33
     */
    @Override
    public List<BdcGzlJkDO> listBdcGzljk(BdcGzlQO bdcGzlQO) {
        List<BdcGzlJkDO> bdcGzlJkDOList = new ArrayList<>(1);
        if (StringUtils.isNotBlank(bdcGzlQO.getJkid())) {
            BdcGzlJkDO bdcGzlJkDO = entityMapper.selectByPrimaryKey(BdcGzlJkDO.class, bdcGzlQO.getJkid());
            if (Objects.nonNull(bdcGzlJkDO) && StringUtils.isNotBlank(bdcGzlJkDO.getJkid())) {
                bdcGzlJkDOList.add(bdcGzlJkDO);
            }
        } else {
            bdcGzlJkDOList = bdcGzlMapper.listBdcGzljkByPage(bdcGzlQO);
        }
        return bdcGzlJkDOList;
    }

    /**
     * @param bdcGzlQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询工作流事件和接口关联关系
     * @date : 2022/4/11 14:53
     */
    @Override
    public List<BdcGzlsjJkGxDO> listBdcGzlSjJkGx(BdcGzlQO bdcGzlQO) {
        if (StringUtils.isNotBlank(bdcGzlQO.getSjid())) {
            Example example = new Example(BdcGzlsjJkGxDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("sjid", bdcGzlQO.getSjid());
            if (StringUtils.isNotBlank(bdcGzlQO.getJkid())) {
                criteria.andEqualTo("jkid", bdcGzlQO.getJkid());
            }
            example.setOrderByClause("sxh");
            return entityMapper.selectByExampleNotNull(example);
        }
        return Collections.emptyList();
    }

    /**
     * @param sjid
     * @param jkid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/4/12 17:07
     */
    @Override
    public void saveGzlsjGx(String sjid, String jkid) {
        if (StringUtils.isNotBlank(sjid) && StringUtils.isNotBlank(jkid)) {
            BdcGzlsjJkGxDO bdcGzlsjJkGxDO = new BdcGzlsjJkGxDO();
            bdcGzlsjJkGxDO.setGxid(UUIDGenerator.generate16());
            bdcGzlsjJkGxDO.setSjid(sjid);
            bdcGzlsjJkGxDO.setJkid(jkid);
            //计算顺序号
            int sxh = bdcGzlMapper.queryMaxSxh(sjid);
            bdcGzlsjJkGxDO.setSxh(sxh + 1);
            entityMapper.insertSelective(bdcGzlsjJkGxDO);
        }
    }

    /**
     * @param sjid
     * @param jkid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/4/12 17:07
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGzlsjGx(String sjid, String jkid) {
        if (StringUtils.isNoneBlank(sjid, jkid)) {
            Example example = new Example(BdcGzlsjJkGxDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("sjid", sjid);
            criteria.andEqualTo("jkid", jkid);
            //1先找到当前接口的顺序号是几
            int sxh = 0;
            String gxid = "";
            List<BdcGzlsjJkGxDO> bdcGzlsjJkGxDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcGzlsjJkGxDOList)) {
                if (Objects.nonNull(bdcGzlsjJkGxDOList.get(0).getSxh())) {
                    sxh = bdcGzlsjJkGxDOList.get(0).getSxh();
                    gxid = bdcGzlsjJkGxDOList.get(0).getGxid();
                }
            }
            //2. 删除当前关联的接口
            if (StringUtils.isNotBlank(gxid)) {
                entityMapper.deleteByPrimaryKey(BdcGzlsjJkGxDO.class, gxid);
            }
            //3.查询删除后关联的接口，更新顺序号
            example = new Example(BdcGzlsjJkGxDO.class);
            example.createCriteria().andEqualTo("sjid", sjid);
            List<BdcGzlsjJkGxDO> bdcGzlsjJkGxDOS = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcGzlsjJkGxDOS)) {
                List<BdcGzlsjJkGxDO> gzlsjJkGxDOList = new ArrayList<>(CollectionUtils.size(bdcGzlsjJkGxDOS));
                for (BdcGzlsjJkGxDO bdcGzlsjJkGxDO : bdcGzlsjJkGxDOS) {
                    if (Objects.nonNull(bdcGzlsjJkGxDO.getSxh()) && bdcGzlsjJkGxDO.getSxh() > sxh) {
                        //比当前删除接口顺序大的，顺序号减1
                        bdcGzlsjJkGxDO.setSxh(bdcGzlsjJkGxDO.getSxh() - 1);
                        gzlsjJkGxDOList.add(bdcGzlsjJkGxDO);
                    }
                }
                if (CollectionUtils.isNotEmpty(gzlsjJkGxDOList)) {
                    entityMapper.batchSaveSelective(gzlsjJkGxDOList);
                }
            }
        }

    }

    /**
     * @param sjid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除工作流事件关系
     * @date : 2022/4/13 16:39
     */
    @Override
    public void deleteGzlsjGx(String sjid) {
        if (StringUtils.isNotBlank(sjid)) {
            Example example = new Example(BdcGzlsjJkGxDO.class);
            example.createCriteria().andEqualTo("sjid", sjid);
            entityMapper.deleteByExampleNotNull(example);
        }
    }

    /**
     * @param bdcGzlsjJkGxDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量更新关联关系
     * @date : 2022/4/14 15:19
     */
    @Override
    public void batchUpdateGzlsjGx(List<BdcGzlsjJkGxDO> bdcGzlsjJkGxDOList) {
        if (CollectionUtils.isNotEmpty(bdcGzlsjJkGxDOList)) {
            entityMapper.batchSaveSelective(bdcGzlsjJkGxDOList);
        }
    }

    /**
     * @param bdcGzlSjJkDTO 接口信息
     * @param paramMap      接口参数
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 执行同步方法
     * @date : 2022/3/31 9:36
     */
    /*
     * 自定义注解拦截当前方法
     * */
    @WokrFlowLog(name = "工作流事件执行日志")
    @Override
    public void executeSyncMethord(BdcGzlSjJkDTO bdcGzlSjJkDTO, Map<String, Object> paramMap) {
        //顺序号排序依次循环执行
        if (Objects.nonNull(bdcGzlSjJkDTO)) {
            //是否忽略异常，忽略异常的直接捕获不抛出，否则直接抛出
            if (Objects.equals(0, bdcGzlSjJkDTO.getSfhlyc())) {
                executeRpcUrl(bdcGzlSjJkDTO, paramMap);
                LOGGER.warn("同步接口执行结束{}", bdcGzlSjJkDTO.getJkmc());
            } else {
                try {
                    executeRpcUrl(bdcGzlSjJkDTO, paramMap);
                } catch (Exception e) {
                    LOGGER.error("执行同步工作流接口{}发现异常{}", bdcGzlSjJkDTO.getJkmc(), e);
                } finally {
                    LOGGER.warn("忽略异常同步接口执行结束{}", bdcGzlSjJkDTO.getJkmc());
                }
            }
        }
    }


    /**
     * @param bdcGzlSjJkDTO 接口信息
     * @param paramMap      接口参数
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 执行异步方法
     * @date : 2022/3/31 9:37
     */
    /*
     * 自定义注解拦截当前方法
     * */
    @WokrFlowLog(name = "工作流事件执行日志")
    @Override
    public void executeAsyncMethord(BdcGzlSjJkDTO bdcGzlSjJkDTO, Map<String, Object> paramMap) {
        //线程执行
        if (Objects.nonNull(bdcGzlSjJkDTO)) {
            //异步接口忽略异常信息
            CompletableFuture.runAsync(() -> {
                        LOGGER.warn("开始执行异步接口==={}", bdcGzlSjJkDTO.getJkmc());
                        try {
                            executeRpcUrl(bdcGzlSjJkDTO, paramMap);
                        } catch (Exception e) {
                            LOGGER.error("执行异步接口异常{}", bdcGzlSjJkDTO.getJkmc(), e);
                        }
                    }
                    , threadPoolExecutor);
            LOGGER.warn("异步接口执行结束==={}", bdcGzlSjJkDTO.getJkmc());
        }
    }

    /**
     * @param bdcGzlsjPlDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量保存工作流事件（勾选多个流程或者多个节点）
     * @date : 2022/4/21 15:21
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchSaveGzlsj(BdcGzlsjPlDTO bdcGzlsjPlDTO) {
        if (CollectionUtils.isNotEmpty(bdcGzlsjPlDTO.getSjbsList())) {
            //事件标识和选中的节点名称去查询数据库数据。能查询到的就更新，查不到的新增
            List<Map> gzlsjlxMap = bdcZdFeignService.queryBdcZd("gzlsjlx");
            List<String> sjbsList = bdcGzlsjPlDTO.getSjbsList();
            String sjlxName = "";
            //每一次批量保存只能选则一个事件操作
            if (CollectionUtils.isNotEmpty(sjbsList)) {
                String sjlxdm = sjbsList.get(0).split("_")[1];
                for (Map map : gzlsjlxMap) {
                    if (Objects.equals(map.get("SJBS"), sjlxdm)) {
                        sjlxName = MapUtils.getString(map, "MC", "");
                        break;
                    }
                }
            }
            for (String sjbs : bdcGzlsjPlDTO.getSjbsList()) {
                BdcGzlQO bdcGzlQO = new BdcGzlQO();
                bdcGzlQO.setSjbs(sjbs);
                if (CollectionUtils.isNotEmpty(bdcGzlsjPlDTO.getJdmcList())) {
                    for (String jdmc : bdcGzlsjPlDTO.getJdmcList()) {
                        bdcGzlQO.setJdmc(jdmc);
                        List<BdcGzlSjDO> bdcGzlSjDOList = listBdcGzlsj(bdcGzlQO);
                        batchSave(bdcGzlSjDOList, jdmc, bdcGzlsjPlDTO, sjbs, sjlxName);
                    }
                } else {
                    List<BdcGzlSjDO> bdcGzlSjDOList = listBdcGzlsj(bdcGzlQO);
                    batchSave(bdcGzlSjDOList, "", bdcGzlsjPlDTO, sjbs, sjlxName);
                }
            }
        }
    }

    /**
     * @param bdcGzlSjJkDTOList 所有接口信息
     * @param paramMap          接口参数信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 接口执行
     * @date : 2022/4/8 8:36
     */
    private void executeAllMethord(List<BdcGzlSjJkDTO> bdcGzlSjJkDTOList, Map<String, Object> paramMap) {
        if (CollectionUtils.isNotEmpty(bdcGzlSjJkDTOList)) {
            for (BdcGzlSjJkDTO bdcGzlSjJkDTO : bdcGzlSjJkDTOList) {
                //参数处理，增加token
                //1.占位符数据例如： /realestate-accept/rest/v1.0/{processInsId}/abc
                //2.参数形式；/realestate-accept/rest/v1.0/abc?processInsId=
                //3.最后增加token access_token=
                handleUrl(bdcGzlSjJkDTO, paramMap);
                //1.判断异步还是同步
                //异步开启future执行
                if (Objects.equals(0, bdcGzlSjJkDTO.getSftb())) {
                    bdcWorkFlowService.executeAsyncMethord(bdcGzlSjJkDTO, paramMap);
                } else {
                    bdcWorkFlowService.executeSyncMethord(bdcGzlSjJkDTO, paramMap);
                }
            }
        }
    }

    /**
     * @param bdcGzlSjJkDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description rpc方法的执行
     * @date : 2022/3/31 15:19
     */
    private void executeRpcUrl(BdcGzlSjJkDTO bdcGzlSjJkDTO, Map<String, Object> paramMap) {
        //参数处理，增加token
        //1.占位符数据例如： /realestate-accept/rest/v1.0/{processInsId}/abc
        //2.参数形式；/realestate-accept/rest/v1.0/abc?processInsId=
        //3.最后增加token access_token=
//        handleUrl(bdcGzlSjJkDTO, paramMap);
        LOGGER.warn("执行url地址{}，请求方式{}，应用名称{}", bdcGzlSjJkDTO.getJkmc(), bdcGzlSjJkDTO.getQqfs(), bdcGzlSjJkDTO.getYymc());
        switch (bdcGzlSjJkDTO.getQqfs()) {
            case "GET":
                restRpcUtils.getRpcRequest(bdcGzlSjJkDTO.getYymc(), bdcGzlSjJkDTO.getJkmc());
                break;
            case "POST":
                restRpcUtils.postRpcRequest(bdcGzlSjJkDTO.getYymc(), bdcGzlSjJkDTO.getJkmc(), paramMap);
                break;
            case "DELETE":
                restRpcUtils.deleteRpcRequest(bdcGzlSjJkDTO.getYymc(), bdcGzlSjJkDTO.getJkmc(), null);
                break;
            case "PUT":
                restRpcUtils.putRpcRequest(bdcGzlSjJkDTO.getYymc(), bdcGzlSjJkDTO.getJkmc(), paramMap);
                break;
            case "PATCH":
                restRpcUtils.patchRpcRequest(bdcGzlSjJkDTO.getYymc(), bdcGzlSjJkDTO.getJkmc(), paramMap);
                break;
            default:
                break;
        }
    }

    private void handleUrl(BdcGzlSjJkDTO bdcGzlSjJkDTO, Map<String, Object> paramMap) {
        if (Objects.nonNull(bdcGzlSjJkDTO)) {
            String url = bdcGzlSjJkDTO.getJkmc();
            //解析url判断占位符
            if (StringUtils.isNotBlank(url) && MapUtils.isNotEmpty(paramMap)) {
                // 匹配URL地址中参数
                for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                    url = url.replaceAll("\\{" + entry.getKey() + "}", String.valueOf(entry.getValue()));
                    //get请求增加后缀参数
                    if (url.contains("?")) {
                        url += "&" + entry.getKey() + "=" + entry.getValue();
                    } else {
                        url += "?" + entry.getKey() + "=" + entry.getValue();
                    }
                }

                try {
                    Matcher matcher = pattern.matcher(url);
                    String tmp = "";
                    while (matcher.find()) {
                        tmp = matcher.group();
                        url = url.replaceAll(tmp, URLEncoder.encode(tmp, "UTF-8"));
                    }
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("url地址{}转译异常{}", url, e.toString());
                }
                bdcGzlSjJkDTO.setJkmc("http://" + bdcGzlSjJkDTO.getYymc().trim() + url.trim());
            }
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void batchSave(List<BdcGzlSjDO> bdcGzlSjDOList, String jdmc, BdcGzlsjPlDTO bdcGzlsjPlDTO, String sjbs, String sjlxmc) {
        if (CollectionUtils.isNotEmpty(bdcGzlSjDOList)) {
            for (BdcGzlSjDO bdcGzlSjDO : bdcGzlSjDOList) {
                //更新关联关系
                //删除之前多所有的关联关系
                deleteGzlsjGx(bdcGzlSjDO.getSjid());
                if (CollectionUtils.isNotEmpty(bdcGzlsjPlDTO.getJkidList())) {
                    for (String jkid : bdcGzlsjPlDTO.getJkidList()) {
                        saveGzlsjGx(bdcGzlSjDO.getSjid(), jkid);
                    }
                }
            }
        } else {
            //新增工作流事件和关联关系
            BdcGzlSjDO bdcGzlSjDO = new BdcGzlSjDO();
            bdcGzlSjDO.setSjid(UUIDGenerator.generate16());
            //如果是批量保存的时候事件名称默认为【流程名称】+节点名称+事件类型
            String gzldyid = sjbs.split("_")[0];
            if (!LCMCMAP.containsKey(gzldyid)) {
                ProcessDefData processDefData = processDefinitionClient.getProcessDefByProcessDefKey(gzldyid);
                if (Objects.nonNull(processDefData)) {
                    LCMCMAP.put(processDefData.getProcessDefKey(), processDefData.getName());
                }
            }
            bdcGzlSjDO.setSjmc("【" + MapUtils.getString(LCMCMAP, gzldyid, "") + "】" + jdmc + sjlxmc);
            bdcGzlSjDO.setSjbs(sjbs);
            bdcGzlSjDO.setJdmc(jdmc);
            bdcGzlSjDO.setCjsj(new Date());
            bdcGzlSjDO.setCjr(userManagerUtils.getUserAlias());
            entityMapper.insertSelective(bdcGzlSjDO);
            //新增关联关系
            if (CollectionUtils.isNotEmpty(bdcGzlsjPlDTO.getJkidList())) {
                for (String jkid : bdcGzlsjPlDTO.getJkidList()) {
                    saveGzlsjGx(bdcGzlSjDO.getSjid(), jkid);
                }
            }
        }
    }
}
