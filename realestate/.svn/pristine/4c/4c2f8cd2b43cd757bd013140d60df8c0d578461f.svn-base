package cn.gtmap.realestate.natural.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.gtc.workflow.domain.manage.UserTaskDto;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyShxxDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXmDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtMryjDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyShxxQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcXtMryjFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyShxxVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import cn.gtmap.realestate.natural.core.service.ZrzyXmService;
import cn.gtmap.realestate.natural.service.ZrzyShxxService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author wyh
 * @version 1.0
 * @date 2021/11/22 14:57
 */
@Service
public class ZrzyShxxServiceImpl implements ZrzyShxxService {
    protected final Logger LOGGER = LoggerFactory.getLogger(ZrzyShxxServiceImpl.class);
    /**
     * 审核意见操作结果-同意
     */
    private static final Integer SHYJCZJG_TY = 1;
    @Autowired
    EntityMapper entityMapper;
    //    @Autowired
//    BdcRegisterConfigServiceImpl bdcRegisterConfigService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    WorkFlowUtils workFlowUtils;
    @Autowired
    BdcXtMryjFeignService bdcXtMryjFeignService;
    @Autowired
    ZrzyXmService zrzyXmService;
    @Autowired
    ProcessTaskClient processTaskClient;

    @Value("${default.mryj:}")
    private String mryj;

    /**
     * description 新增审核信息，初始化审核信息
     *
     * @param zrzyShxxDO BdcShxxDO
     * @return BdcShxxDO 返回保存的对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     */
    @Override
    public ZrzyShxxDO insertBdcShxx(ZrzyShxxDO zrzyShxxDO) {
        Integer sxh = getSxhByGzlslid(zrzyShxxDO.getGzlslid());

        Date shkssj = new Date();
        if (null != zrzyShxxDO.getQmsj()) {
            shkssj = zrzyShxxDO.getQmsj();
        }
        zrzyShxxDO.setShkssj(shkssj);
        zrzyShxxDO.setCzjg(SHYJCZJG_TY);
        zrzyShxxDO.setSxh(sxh);
        entityMapper.insertSelective(zrzyShxxDO);
        return zrzyShxxDO;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return Integer sxh
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程审核信息的顺序号
     */
    private Integer getSxhByGzlslid(String gzlslid) {
        Example example = new Example(ZrzyShxxDO.class);
        example.createCriteria().andEqualTo(CommonConstantUtils.GZLSLID, gzlslid);
        List<ZrzyShxxDO> zrzyShxxDOList = entityMapper.selectByExampleNotNull(example);
        if (CollectionUtils.isEmpty(zrzyShxxDOList)) {
            return 1;
        } else {
            return zrzyShxxDOList.size() + 1;
        }
    }

    /**
     * 更新指定节点的审核信息
     *
     * @param zrzyShxxDO BdcShxxDO
     * @return int 返回操作的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     */
    @Override
    public int updateBdcShxx(ZrzyShxxDO zrzyShxxDO) {
        if (StringUtils.isBlank(zrzyShxxDO.getShxxid())) {
            throw new MissingArgumentException("缺失主键shxxid！");
        }
        return entityMapper.updateByPrimaryKeySelective(zrzyShxxDO);
    }

    /**
     * 根据条件查询审核信息，（暂时只实现根据shxxid和gzlslid精确查询）
     *
     * @param zrzyShxxQO BdcShxxDO
     * @return List<BdcShxxDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     */
    @Override
    public List<ZrzyShxxDO> queryZrzyShxx(ZrzyShxxQO zrzyShxxQO) {
        if (null != zrzyShxxQO) {
            Example example = new Example(ZrzyShxxDO.class);
            example.setOrderByClause("sxh ASC");
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(zrzyShxxQO.getShxxid())) {
                criteria.andEqualTo("shxxid", zrzyShxxQO.getShxxid());
            }
            if (StringUtils.isNotBlank(zrzyShxxQO.getJdmc())) {
                criteria.andEqualTo("jdmc", zrzyShxxQO.getJdmc());
            }
            if (StringUtils.isNotBlank(zrzyShxxQO.getGzlslid())) {
                criteria.andEqualTo(CommonConstantUtils.GZLSLID, zrzyShxxQO.getGzlslid());
            }
            if (StringUtils.isNotBlank(zrzyShxxQO.getXmid())) {
                criteria.andEqualTo("xmid", zrzyShxxQO.getXmid());
            }
            if (StringUtils.isNotBlank(zrzyShxxQO.getShryxm())) {
                criteria.andEqualTo("shryxm", zrzyShxxQO.getShryxm());
            }
            return entityMapper.selectByExampleNotNull(example);
        }
        return new ArrayList<>();
    }

    /**
     * @param paramList 审核信息集合
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 依据主键更新多条审核信息数据
     */
    @Override
    public int updateShxxList(List<ZrzyShxxDO> paramList) {
        int result = 0;
        if (CollectionUtils.isNotEmpty(paramList)) {
            for (ZrzyShxxDO zrzyShxxDO : paramList) {
                result += updateBdcShxx(zrzyShxxDO);
            }
        }
        return result;
    }

    /**
     * @param shxxid
     * @return int
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 删除审核意见和签名信息
     */
    @Override
    public int deleteShxxSign(String shxxid) {
        if (StringUtils.isBlank(shxxid)) {
            throw new MissingArgumentException("缺失主键审核信息ID");
        }
        ZrzyShxxDO zrzyShxxDO = entityMapper.selectByPrimaryKey(ZrzyShxxDO.class, shxxid);
        if (null != zrzyShxxDO) {
            zrzyShxxDO.setQmid("");
            zrzyShxxDO.setShyj("");
            zrzyShxxDO.setQmsj(null);
            return entityMapper.updateByPrimaryKeyNull(zrzyShxxDO);
        }
        return -1;
    }

    /**
     * @param shxxidList
     * @return int
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量删除审核意见和签名信息
     */
    @Override
    public int deleteShxxSign(List<String> shxxidList) {
        return 0;
    }

    /**
     * @param shxxid 审核信息ID
     * @return BdcShxxDO 审核信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据主键查询审核信息
     */
    @Override
    public ZrzyShxxDO queryBdcShxxById(String shxxid) {
        if (StringUtils.isBlank(shxxid)) {
            throw new MissingArgumentException("缺失shxxid");
        }
        return entityMapper.selectByPrimaryKey(ZrzyShxxDO.class, shxxid);
    }

    /**
     * @param zrzyShxxDO 审核信息对象
     * @return int 操作数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据主键查询shxx，没有则保存，有则更新
     */
    @Override
    public int saveOrUpdateBdcShxx(ZrzyShxxDO zrzyShxxDO) {
        if (StringUtils.isBlank(zrzyShxxDO.getShxxid())) {
            throw new MissingArgumentException("缺失shxxid");
        }
        ZrzyShxxDO zrzyShxxDOTemp = queryBdcShxxById(zrzyShxxDO.getShxxid());
        if (null != zrzyShxxDOTemp && StringUtils.isNotBlank(zrzyShxxDOTemp.getShxxid())) {
            return updateBdcShxx(zrzyShxxDO);
        }
        insertBdcShxx(zrzyShxxDO);
        return 1;
    }

    /**
     * @param shxxid 审核信息id
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 流程退回删除审核意见和签名信息，并保存审核结束时间
     */
    @Override
    public int deleteSignAndSaveShjssj(String shxxid) {
        if (StringUtils.isBlank(shxxid)) {
            throw new MissingArgumentException("缺失主键审核信息ID");
        }
        ZrzyShxxDO zrzyShxxDO = entityMapper.selectByPrimaryKey(ZrzyShxxDO.class, shxxid);
        if (null != zrzyShxxDO) {
            zrzyShxxDO.setQmid("");
            zrzyShxxDO.setShyj("");
            zrzyShxxDO.setQmsj(null);
            zrzyShxxDO.setShjssj(new Date());
            return entityMapper.updateByPrimaryKeyNull(zrzyShxxDO);
        }
        return -1;
    }

    /**
     * @param shxxid 审核信息id
     * @return int 操作的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新审核结束时间（taskId和shxxid一致）
     */
    @Override
    public int updateShjssj(String shxxid) {
        LOGGER.info("开始更新审核结束时间，shxxid:{}", shxxid);
        if (StringUtils.isBlank(shxxid)) {
            throw new MissingArgumentException("缺失主键审核信息ID");
        }
        ZrzyShxxDO zrzyShxxDO = new ZrzyShxxDO();
        zrzyShxxDO.setShxxid(shxxid);
        zrzyShxxDO.setShjssj(new Date());
        return entityMapper.updateByPrimaryKeySelective(zrzyShxxDO);
    }

    /**
     * @param gzlslid     工作流实例ID
     * @param bdcXtMryjDO 系统默认意见
     * @return String 最终获得的默认意见
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据配置的sql生成默认意见
     */
    @Override
    public String generateMryjBySql(String gzlslid, ZrzyXtMryjDO bdcXtMryjDO) {
        if (StringUtils.isBlank(gzlslid) || bdcXtMryjDO == null || StringUtils.isBlank(bdcXtMryjDO.getYj())) {
            throw new MissingArgumentException("缺失工作流实例ID或默认意见未配置或未配置意见！");
        }

        Map configParam = new HashMap(2);
        configParam.put("sql", bdcXtMryjDO.getYj());
        configParam.put("gzlslid", gzlslid);
//        List<Map> configList = bdcRegisterConfigService.executeConfigSql(configParam);
//        if (CollectionUtils.isNotEmpty(configList) && MapUtils.isNotEmpty(configList.get(0))) {
//            Map<String, String> result = configList.get(0);
//            String mryj = "";
//            for (Map.Entry entry : result.entrySet()) {
//                mryj = entry.getValue().toString();
//            }
//            return mryj;
//        }
        return null;
    }

    /**
     * @param zrzyShxxQOParam 审核信息查询对象
     * @return 返回审核节点信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取签名意见，调用平台服务获取当前工作流配置的审核节点信息（出现异常则生成默认的初审，复审，核定节点信息），根据节点信息获取审核信息
     */
    @Override
    public List<ZrzyShxxVO> queryShJdxx(ZrzyShxxQO zrzyShxxQOParam) {
        String gzlslid = zrzyShxxQOParam.getGzlslid();
        String taskId = zrzyShxxQOParam.getShxxid();
        String currentJdmc = zrzyShxxQOParam.getJdmc();
        Boolean onlyCurrentJd = zrzyShxxQOParam.getOnlyCurrentJd();
        if (StringUtils.isEmpty(gzlslid) || StringUtils.isBlank(taskId) || StringUtils.isBlank(currentJdmc)) {
            throw new MissingArgumentException("缺失gzlslid或taskId或当前jdmc！");
        }
        ZrzyShxxQO zrzyShxxQO = new ZrzyShxxQO();
        // 调用平台接口，获取当前流程的节点，只获取formProperties 中sign 为true的节点
        List<UserTaskDto> userTaskDataListTemp = workFlowUtils.listShjdxx(gzlslid);
        List<UserTaskDto> userTaskDataList = new ArrayList(userTaskDataListTemp.size());

        //只返回当前一个签名节点的信息
        if (onlyCurrentJd && CollectionUtils.isNotEmpty(userTaskDataListTemp)) {
            for (UserTaskDto userTaskDto : userTaskDataListTemp) {
                if (StringUtils.equals(currentJdmc, userTaskDto.getName())) {
                    userTaskDataList.add(userTaskDto);
                    break;
                }
            }
        } else {
            userTaskDataList = userTaskDataListTemp;
        }

        List<ZrzyShxxVO> shxxVoList = Lists.newArrayListWithCapacity(userTaskDataList.size());
        userTaskDataList.forEach(taskData -> {
            zrzyShxxQO.setGzlslid(gzlslid);
            zrzyShxxQO.setJdmc(taskData.getName());
            // 如果是当前节点，则增加主键查询条件
            if (StringUtils.equals(currentJdmc, taskData.getName())) {
                zrzyShxxQO.setShxxid(taskId);
            }

            List<ZrzyShxxDO> zrzyShxxDOList = queryZrzyShxx(zrzyShxxQO);

            ZrzyShxxVO zrzyShxxVO = new ZrzyShxxVO();
            if (CollectionUtils.isNotEmpty(zrzyShxxDOList)) {
                zrzyShxxDOList.sort(Comparator.comparing(ZrzyShxxDO::getSxh));
                // 要获取审核sxh最大的那条数据
                Collections.reverse(zrzyShxxDOList);
                ZrzyShxxDO zrzyShxxDO = zrzyShxxDOList.get(0);
                zrzyShxxVO.setShxxid(zrzyShxxDO.getShxxid());
                zrzyShxxVO.setShyj(zrzyShxxDO.getShyj());
                if (zrzyShxxDO.getQmsj() != null) {
                    zrzyShxxVO.setQmsj(DateUtils.format(zrzyShxxDO.getQmsj(), "yyyy年MM月dd日"));
                }
                zrzyShxxVO.setQmid(zrzyShxxDO.getQmid());
                // 拼接签名ID
                if (StringUtils.isNotEmpty(zrzyShxxDO.getQmid())) {
                    zrzyShxxVO.setQmtpdz(zrzyShxxQOParam.getSignImageUrl() + zrzyShxxDO.getQmid());
                }
            } else if (StringUtils.equals(currentJdmc, taskData.getName())) {
                // 数据库中还未保存审核信息，则初始化当前节点的审核信息
                zrzyShxxVO.setShxxid(taskId);
            }
            zrzyShxxVO.setJdid(taskData.getId());
            zrzyShxxVO.setJdmc(taskData.getName());
            shxxVoList.add(zrzyShxxVO);
        });
        return shxxVoList;
    }

    /**
     * @param shxxid 任务Id
     * @return BdcShxxDO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程节点，最新的审核信息以及默认意见
     */
    @Override
    public ZrzyShxxVO queryMryj(String shxxid) {
        if (StringUtils.isBlank(shxxid)) {
            throw new MissingArgumentException("缺失shxxid参数！");
        }
        TaskData taskData = workFlowUtils.getTaskById(shxxid);
        if (null == taskData) {
            throw new MissingArgumentException("当前taskId：" + shxxid + "未查询到流程节点信息！");
        }
        String jdmc = taskData.getTaskName();
        String jdid = taskData.getTaskKey();
        String gzldyKey = taskData.getProcessKey();
        String gzlslid = taskData.getProcessInstanceId();

        ZrzyShxxVO zrzyShxxVO = new ZrzyShxxVO();
        zrzyShxxVO.setJdid(jdid);
        zrzyShxxVO.setJdmc(jdmc);
        zrzyShxxVO.setShxxid(shxxid);

        // 查询当前节点的审核信息
        ZrzyShxxDO zrzyShxxDO = this.queryBdcShxxById(shxxid);
        // 当前节点没有生成审核信息，或者审核信息为空，或者有审核信息，但是审核意见为空
        if (null == zrzyShxxDO
                || StringUtils.isBlank(zrzyShxxDO.getShxxid())
                || StringUtils.isBlank(zrzyShxxDO.getShyj())) {
            String mryj = "";
            try {
                mryj = extendsYshxx(shxxid, gzlslid, jdmc);
                if (StringUtils.isBlank(mryj)) {
                    // 获取系统默认意见
                    mryj = getXtMryj(gzldyKey, jdmc, gzlslid);
                }
            } catch (Exception e) {
                LOGGER.info("默认意见查询异常！", e);
            }
            // 继承原签名信息
            zrzyShxxVO.setShyj(mryj);
        }

        if(StringUtils.isBlank(zrzyShxxVO.getShyj())){
            zrzyShxxVO.setShyj(mryj);
        }
        return zrzyShxxVO;
    }

    /**
     * @param gzldyKey 大云工作流定义KEY
     * @param gzlslid  工作流实例ID
     * @param jdmc     节点名称
     * @return String mryj
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取系统配置的默认意见
     */
    private String getXtMryj(String gzldyKey, String jdmc, String gzlslid) {
        if (StringUtils.isBlank(gzldyKey) || StringUtils.isBlank(jdmc) || StringUtils.isBlank(gzlslid)) {
            return "";
        }
        // 获取默认意见
        ZrzyXtMryjDO bdcXtMryjDO = queryZrzyXtMryj(null, gzldyKey, jdmc);
        if (null != bdcXtMryjDO) {
            if (CommonConstantUtils.MRYJ_SJLX_SQL.equals(bdcXtMryjDO.getSjlx())) {
                String[] sqlArr = StringUtils.split(bdcXtMryjDO.getYj(), CommonConstantUtils.ZF_YW_FH);
                if (sqlArr.length > 0 && StringUtils.isNotBlank(sqlArr[0])) {
                    // 只执行一个sql
                    bdcXtMryjDO.setYj(sqlArr[0]);
                    return this.generateMryjBySql(gzlslid, bdcXtMryjDO);
                }
            } else {
                return bdcXtMryjDO.getYj();
            }
        }
        return "";
    }

    public ZrzyXtMryjDO queryZrzyXtMryj(String cjrid, String gzldyKey, String jdmc) {
        if (StringUtils.isEmpty(gzldyKey) || StringUtils.isEmpty(jdmc)) {
            throw new MissingArgumentException("gzldyKey,jdmc");
        }
        Example example = new Example(ZrzyXtMryjDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(cjrid)) {
            criteria.andEqualTo("cjrid", StringUtils.trim(cjrid));
        }
        criteria.andEqualTo("gzldyid", StringUtils.trim(gzldyKey));
        criteria.andLike("jdmc", jdmc);
        criteria.andEqualTo("yjlx", CommonConstantUtils.YJLX_MRYJ);
        List<ZrzyXtMryjDO> mryjDOS = entityMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(mryjDOS)) {
            return null;
        }
        return mryjDOS.get(0);
    }

    /**
     * @param taskId  当前流程ID
     * @param gzlslid 工作流实例ID
     * @param jdmc    节点名称
     * @return String mryj
     * @author <a href="mailto:zhangwentao@gtmap.cn">wangyinghao</a>
     * @description 判断当前节点是否是退回重签节点，如果是退回重签节点，需要继承上一次的签名信息,并将信息保存到库中
     */
    private String extendsYshxx(String taskId, String gzlslid, String jdmc) {
        if (StringUtils.isBlank(taskId) || StringUtils.isBlank(gzlslid) || StringUtils.isBlank(jdmc)) {
            return "";
        }
        ZrzyShxxQO zrzyShxxQO = new ZrzyShxxQO();
        zrzyShxxQO.setGzlslid(gzlslid);
        zrzyShxxQO.setJdmc(jdmc);
        zrzyShxxQO.setShryxm(userManagerUtils.getCurrentUserName());
        List<ZrzyShxxDO> zrzyShxxDOList = queryZrzyShxx(zrzyShxxQO);
        if (CollectionUtils.isNotEmpty(zrzyShxxDOList)) {
            zrzyShxxDOList.sort(Comparator.comparing(ZrzyShxxDO::getSxh));
            // 要获取审核sxh最大的那条数据
            Collections.reverse(zrzyShxxDOList);
            ZrzyShxxDO bdcShxxDOTemp = zrzyShxxDOList.get(0);
            bdcShxxDOTemp.setShkssj(new Date());
            bdcShxxDOTemp.setShxxid(taskId);
            bdcShxxDOTemp.setShjssj(null);
            saveOrUpdateBdcShxx(bdcShxxDOTemp);
            return bdcShxxDOTemp.getShyj();
        }
        return "";
    }

    /**
     * @param zrzyShxxDO 审核信息DO
     * @return 签名信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前人的签名信息
     */
    @Override
    public ZrzyShxxVO getShxxSign(ZrzyShxxDO zrzyShxxDO) {
        if (StringUtils.isEmpty(zrzyShxxDO.getJdmc()) || StringUtils.isEmpty(zrzyShxxDO.getShxxid())) {
            throw new MissingArgumentException("jdmc,shxxid");
        }
        UserDto userDto = new UserDto();
        if (StringUtils.isNotBlank(zrzyShxxDO.getShryxm())) {
            //传递用户名，以该用户名获取用户信息
            userDto = userManagerUtils.getUserByName(zrzyShxxDO.getShryxm());
        }
        if (null == userDto || StringUtils.isBlank(userDto.getUsername())) {
            userDto = userManagerUtils.getCurrentUser();
        }
        LOGGER.info("userManagerUtils.getCurrentUser,审核信息签名获取到登录信息：{}", userDto);
        if (userDto == null) {
            throw new AppException("用户信息获取失败");
        }
        String signId = storageClient.userSign(userDto.getUsername());
        if (StringUtils.isEmpty(signId)) {
            throw new AppException("获取签名id失败");
        }
        ZrzyXmDO zrzyXmDO = zrzyXmService.queryZrzyXmByGzlslid(zrzyShxxDO.getGzlslid());
        // 签名时保存审核信息，且shxxid为当前taskId，由初始化审核页面的时候控制
        zrzyShxxDO.setShryxm(userDto.getUsername());
//        zrzyShxxDO.setShryid(userDto.getId());
        zrzyShxxDO.setQmid(signId);
        zrzyShxxDO.setQmsj(new Date());
        zrzyShxxDO.setXmid(zrzyXmDO.getXmid());
        this.saveOrUpdateBdcShxx(zrzyShxxDO);

        ZrzyShxxVO zrzyShxxVO = new ZrzyShxxVO();
        zrzyShxxVO.setQmid(signId);
        zrzyShxxVO.setQmsj(DateUtils.format(zrzyShxxDO.getQmsj(), "yyyy年MM月dd日"));
        return zrzyShxxVO;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return {List} 生成的审核信息
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 生成流程项目所有节点审核信息，意见内容采用默认意见
     */
    @Override
    public List<ZrzyShxxDO> generateShxxOfProInsId(String gzlslid) {
        return null;
    }

    /**
     * @param processInsId
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除审核意见和签名信息
     */
    @Override
    public void deleteShxxPdf(String processInsId) {
        if (StringUtils.isNotBlank(processInsId)) {
            List<StorageDto> storageDtoList = storageClient.listStoragesByName("clientId", processInsId, null, "审核信息", null, 0);
            if (CollectionUtils.isNotEmpty(storageDtoList)) {
                List<String> list = new ArrayList<>();
                list.add(storageDtoList.get(0).getId());
                boolean result = storageClient.deleteStorages(list);
                LOGGER.info("审核信息目录文件删除结果：{}", result);
            }

        } else {
            LOGGER.info("删除审核信息pdf,缺失processInsId");
        }
    }
}
