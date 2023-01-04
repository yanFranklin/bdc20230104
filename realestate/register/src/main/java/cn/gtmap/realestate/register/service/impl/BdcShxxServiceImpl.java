package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.gtc.workflow.domain.manage.UserTaskDto;
import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcShxxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtMryjDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.BdcUrlDTO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcShxxPdfDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.register.BdcShxxQO;
import cn.gtmap.realestate.common.core.service.BdcDysjPzService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcDjxlPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcXtMryjFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcShxxVO;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.register.core.mapper.BdcShxxMapper;
import cn.gtmap.realestate.register.service.BdcDypzService;
import cn.gtmap.realestate.register.service.BdcShbdPrintService;
import cn.gtmap.realestate.register.service.BdcShxxService;
import cn.gtmap.realestate.register.util.Constants;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.tools.ant.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

import static cn.gtmap.realestate.common.util.DateUtils.sdf_ymdhms;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/10/30
 * @description 审核信息服务实现类
 */
@Service
public class BdcShxxServiceImpl implements BdcShxxService {
    protected final Logger LOGGER = LoggerFactory.getLogger(BdcShxxServiceImpl.class);
    /**
     * 审核意见操作结果-同意
     */
    private static final Integer SHYJCZJG_TY = 1;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcRegisterConfigServiceImpl bdcRegisterConfigService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    WorkFlowUtils workFlowUtils;
    @Autowired
    BdcXtMryjFeignService bdcXtMryjFeignService;
    @Autowired
    BdcShxxMapper bdcShxxMapper;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    ProcessTaskClient processTaskClient;
    @Autowired
    BdcDjxlPzFeignService bdcDjxlPzFeignService;
    @Autowired
    BdcShbdPrintService bdcShbdPrintService;
    @Autowired
    PdfController pdfController;
    @Autowired
    BdcDypzService bdcDypzService;

    /**
     * 打印文件路径
     */
    @Value("${print.path:}")
    private String printPath;


    /**
     * @param bdcShxx BdcShxxDO bdcShxx
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 新增审核信息，初始化审核信息
     */
    @Override
    public BdcShxxDO insertBdcShxx(BdcShxxDO bdcShxx) {
        Integer sxh = getSxhByGzlslid(bdcShxx.getGzlslid());

        Date shkssj = new Date();
        if (null != bdcShxx.getQmsj()) {
            shkssj = bdcShxx.getQmsj();
        }
        bdcShxx.setShkssj(shkssj);
        bdcShxx.setCzjg(SHYJCZJG_TY);
        bdcShxx.setSxh(sxh);
        entityMapper.insertSelective(bdcShxx);
        return bdcShxx;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return Integer sxh
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程审核信息的顺序号
     */
    private Integer getSxhByGzlslid(String gzlslid) {
        Example example = new Example(BdcShxxDO.class);
        example.createCriteria().andEqualTo(CommonConstantUtils.GZLSLID, gzlslid);
        List<BdcShxxDO> bdcShxxDOList = entityMapper.selectByExampleNotNull(example);
        if (CollectionUtils.isEmpty(bdcShxxDOList)) {
            return 1;
        } else {
            return bdcShxxDOList.size() + 1;
        }
    }

    /**
     * @param bdcShxx BdcShxxDO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新指定节点的审核信息
     */
    @Override
    public int updateBdcShxx(BdcShxxDO bdcShxx) {
        if (StringUtils.isBlank(bdcShxx.getShxxid())) {
            throw new MissingArgumentException("缺失主键shxxid！");
        }
        return entityMapper.updateByPrimaryKeySelective(bdcShxx);
    }



    /**
     * @param bdcShxxQO BdcShxxDO
     * @return List<BdcShxxDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据条件查询审核信息，（暂时只实现根据shxxid/gzlslid/xmid精确查询）
     */
    @Override
    public List<BdcShxxDO> queryBdcShxx(BdcShxxQO bdcShxxQO) {
        if (null != bdcShxxQO) {
            Example example = new Example(BdcShxxDO.class);
            example.setOrderByClause("sxh ASC");
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(bdcShxxQO.getShxxid())) {
                criteria.andEqualTo("shxxid", bdcShxxQO.getShxxid());
            }
            if (StringUtils.isNotBlank(bdcShxxQO.getJdmc())) {
                criteria.andEqualTo("jdmc", bdcShxxQO.getJdmc());
            }
            if (StringUtils.isNotBlank(bdcShxxQO.getGzlslid())) {
                criteria.andEqualTo(CommonConstantUtils.GZLSLID, bdcShxxQO.getGzlslid());
            }
            if (StringUtils.isNotBlank(bdcShxxQO.getXmid())) {
                criteria.andEqualTo("xmid", bdcShxxQO.getXmid());
            }
            if (StringUtils.isNotBlank(bdcShxxQO.getShryxm())) {
                criteria.andEqualTo("shryxm", bdcShxxQO.getShryxm());
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
    public int updateShxxList(List<BdcShxxDO> paramList) {
        int result = 0;
        if (CollectionUtils.isNotEmpty(paramList)) {
            for (BdcShxxDO bdcShxxDO : paramList) {
                result += updateBdcShxx(bdcShxxDO);
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
        BdcShxxDO bdcShxxDO = entityMapper.selectByPrimaryKey(BdcShxxDO.class, shxxid);
        if (null != bdcShxxDO) {
            bdcShxxDO.setQmid("");
            bdcShxxDO.setShyj("");
            bdcShxxDO.setQmsj(null);
            return entityMapper.updateByPrimaryKeyNull(bdcShxxDO);
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
        if (CollectionUtils.isEmpty(shxxidList)) {
            throw new MissingArgumentException("缺失主键审核信息ID值，shxxid");
        }
        int result = 0;
        List<List> lists = ListUtils.subList(shxxidList, 500);
        for (List list : lists) {
            result += bdcShxxMapper.batchDeleteShxxSign(list);
        }
        return result;
    }

    /**
     * @param shxxid 审核信息ID
     * @return BdcShxxDO 审核信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据主键查询审核信息
     */
    @Override
    public BdcShxxDO queryBdcShxxById(String shxxid) {
        if (StringUtils.isBlank(shxxid)) {
            throw new MissingArgumentException("缺失shxxid");
        }
        return entityMapper.selectByPrimaryKey(BdcShxxDO.class, shxxid);
    }

    /**
     * @param bdcShxxDO 审核信息对象
     * @return int 操作数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据主键查询shxx，没有则保存，有则更新
     */
    @Override
    public int saveOrUpdateBdcShxx(BdcShxxDO bdcShxxDO) {
        if (StringUtils.isBlank(bdcShxxDO.getShxxid())) {
            throw new MissingArgumentException("缺失shxxid");
        }
        BdcShxxDO bdcShxxDOTemp = queryBdcShxxById(bdcShxxDO.getShxxid());
        if (null != bdcShxxDOTemp && StringUtils.isNotBlank(bdcShxxDOTemp.getShxxid())) {
            return updateBdcShxx(bdcShxxDO);
        }
        insertBdcShxx(bdcShxxDO);
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
        BdcShxxDO bdcShxxDO = entityMapper.selectByPrimaryKey(BdcShxxDO.class, shxxid);
        if (null != bdcShxxDO) {
            bdcShxxDO.setQmid("");
            bdcShxxDO.setShyj("");
            bdcShxxDO.setQmsj(null);
            bdcShxxDO.setShjssj(new Date());
            return entityMapper.updateByPrimaryKeyNull(bdcShxxDO);
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
        LOGGER.info("开始更新审核结束时间，shxxid:{}",shxxid);
        if (StringUtils.isBlank(shxxid)) {
            throw new MissingArgumentException("缺失主键审核信息ID");
        }
        BdcShxxDO bdcShxxDO = new BdcShxxDO();
        bdcShxxDO.setShxxid(shxxid);
        bdcShxxDO.setShjssj(new Date());
        return entityMapper.updateByPrimaryKeySelective(bdcShxxDO);
    }

    /**
     * @param gzlslid     工作流实例ID
     * @param bdcXtMryjDO 系统默认意见
     * @return String 最终获得的默认意见
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据配置的sql生成默认意见
     */
    @Override
    public String generateMryjBySql(String gzlslid, BdcXtMryjDO bdcXtMryjDO) {
        if (StringUtils.isBlank(gzlslid) || bdcXtMryjDO == null || StringUtils.isBlank(bdcXtMryjDO.getYj())) {
            throw new MissingArgumentException("缺失工作流实例ID或默认意见未配置或未配置意见！");
        }

        Map configParam = new HashMap(2);
        configParam.put("sql", bdcXtMryjDO.getYj());
        configParam.put("gzlslid", gzlslid);
        List<Map> configList = bdcRegisterConfigService.executeConfigSql(configParam);
        if (CollectionUtils.isNotEmpty(configList) && MapUtils.isNotEmpty(configList.get(0))) {
            Map<String, String> result = configList.get(0);
            String mryj = "";
            for (Map.Entry entry : result.entrySet()) {
                mryj = entry.getValue().toString();
            }
            return mryj;
        }
        return null;
    }

    /**
     * @param bdcShxxQOParam 审核信息查询对象
     * @return 返回审核节点信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取签名意见，调用平台服务获取当前工作流配置的审核节点信息（出现异常则生成默认的初审，复审，核定节点信息），根据节点信息获取审核信息
     */
    @Override
    public List<BdcShxxVO> queryShJdxx(BdcShxxQO bdcShxxQOParam) {
        String gzlslid = bdcShxxQOParam.getGzlslid();
        String taskId = bdcShxxQOParam.getShxxid();
        String currentJdmc = bdcShxxQOParam.getJdmc();
        Boolean onlyCurrentJd = bdcShxxQOParam.getOnlyCurrentJd();
        if (StringUtils.isEmpty(gzlslid) || StringUtils.isBlank(taskId) || StringUtils.isBlank(currentJdmc)) {
            throw new MissingArgumentException("缺失gzlslid或taskId或当前jdmc！");
        }
        BdcShxxQO bdcShxxQO = new BdcShxxQO();
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

        List<BdcShxxVO> shxxVoList = Lists.newArrayListWithCapacity(userTaskDataList.size());
        userTaskDataList.forEach(taskData -> {
            bdcShxxQO.setGzlslid(gzlslid);
            bdcShxxQO.setJdmc(taskData.getName());
            // 如果是当前节点，则增加主键查询条件
            if (StringUtils.equals(currentJdmc, taskData.getName())) {
                bdcShxxQO.setShxxid(taskId);
            }

            List<BdcShxxDO> bdcShxxDOList = this.queryBdcShxx(bdcShxxQO);

            BdcShxxVO bdcShxxVO = new BdcShxxVO();
            if (CollectionUtils.isNotEmpty(bdcShxxDOList)) {
                bdcShxxDOList.sort(Comparator.comparing(BdcShxxDO::getSxh));
                // 要获取审核sxh最大的那条数据
                Collections.reverse(bdcShxxDOList);
                BdcShxxDO bdcShxxDO = bdcShxxDOList.get(0);
                bdcShxxVO.setShxxid(bdcShxxDO.getShxxid());
                bdcShxxVO.setShyj(bdcShxxDO.getShyj());
                if (bdcShxxDO.getQmsj() != null) {
                    bdcShxxVO.setQmsj(DateUtils.format(bdcShxxDO.getQmsj(), "yyyy年MM月dd日"));
                }
                bdcShxxVO.setQmid(bdcShxxDO.getQmid());
                // 拼接签名ID
                if (StringUtils.isNotEmpty(bdcShxxDO.getQmid())) {
                    bdcShxxVO.setQmtpdz(bdcShxxQOParam.getSignImageUrl() + bdcShxxDO.getQmid());
                }
            } else if (StringUtils.equals(currentJdmc, taskData.getName())) {
                // 数据库中还未保存审核信息，则初始化当前节点的审核信息
                bdcShxxVO.setShxxid(taskId);
            }
            bdcShxxVO.setJdid(taskData.getId());
            bdcShxxVO.setJdmc(taskData.getName());
            shxxVoList.add(bdcShxxVO);
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
    public BdcShxxVO queryMryj(String shxxid) {
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

        BdcShxxVO bdcShxxVO = new BdcShxxVO();
        bdcShxxVO.setJdid(jdid);
        bdcShxxVO.setJdmc(jdmc);
        bdcShxxVO.setShxxid(shxxid);

        // 查询当前节点的审核信息
        BdcShxxDO bdcShxxDO = this.queryBdcShxxById(shxxid);
        // 当前节点没有生成审核信息，或者审核信息为空，或者有审核信息，但是审核意见为空
        if (null == bdcShxxDO || StringUtils.isBlank(bdcShxxDO.getShxxid()) || StringUtils.isBlank(bdcShxxDO.getShyj())) {
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
            bdcShxxVO.setShyj(mryj);
        }
        return bdcShxxVO;
    }

    /**
     * @param bdcShxxDO 审核信息DO
     * @return 签名信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前人的签名信息
     */
    @Override
    public BdcShxxVO getShxxSign(BdcShxxDO bdcShxxDO) {
        if (StringUtils.isEmpty(bdcShxxDO.getJdmc()) || StringUtils.isEmpty(bdcShxxDO.getShxxid())) {
            throw new MissingArgumentException("jdmc,shxxid");
        }
        UserDto userDto = new UserDto();
        if(StringUtils.isNotBlank(bdcShxxDO.getShryxm())){
            //传递用户名，以该用户名获取用户信息
            userDto = userManagerUtils.getUserByName(bdcShxxDO.getShryxm());
        }
        if (null == userDto || StringUtils.isBlank(userDto.getUsername())) {
            userDto = userManagerUtils.getCurrentUser();
        }
        LOGGER.info("userManagerUtils.getCurrentUser,审核信息签名获取到登录信息：{}", userDto);
        if(userDto ==null){
            throw new AppException("用户信息获取失败");
        }
        String signId = storageClient.userSign(userDto.getUsername());
        if (StringUtils.isEmpty(signId)) {
            throw new AppException("获取签名id失败");
        }
        // 签名时保存审核信息，且shxxid为当前taskId，由初始化审核页面的时候控制
        bdcShxxDO.setShryxm(userDto.getUsername());
        bdcShxxDO.setShryid(userDto.getId());
        bdcShxxDO.setQmid(signId);
        bdcShxxDO.setQmsj(new Date());
        this.saveOrUpdateBdcShxx(bdcShxxDO);

        BdcShxxVO bdcShxxVO = new BdcShxxVO();
        bdcShxxVO.setQmid(signId);
        bdcShxxVO.setQmsj(DateUtils.format(bdcShxxDO.getQmsj(), "yyyy年MM月dd日"));
        return bdcShxxVO;
    }

    /**
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzlslid 工作流实例ID
     * @return {List} 生成的审核信息
     * @description  生成流程项目所有节点审核信息，意见内容采用默认意见
     */
    @Override
    public List<BdcShxxDO> generateShxxOfProInsId(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("生成项目所有节点审核信息异常，原因：未传递参数 gzlslid");
        }

        // 获取项目信息
        BdcXmDO bdcXmDO = this.getBdcXmxx(gzlslid);
        // 获取流程环节信息
        List<TaskData> userTasks = this.getUserTasks(gzlslid);
        // 获取用户信息
        UserDto userDTO = this.getUserInfo(bdcXmDO);
        // 获取签名信息
        String signId = this.getSignId(bdcXmDO.getGzlslid(), userDTO.getUsername());

        // 缓存审核意见，留作方法返回结果
        List<BdcShxxDO> shxxList = new ArrayList<>();

        // 根据流程环节依次生成审核意见信息并保存到库
        for(TaskData task : userTasks) {
            BdcShxxDO bdcShxxDO = this.generateBdcShxx(bdcXmDO, task, userDTO, signId);
            shxxList.add(this.insertBdcShxx(bdcShxxDO));
        }

        LOGGER.info("生成流程项目所有节点审核信息操作共生成{}条审核记录，对应gzlslid：{}", shxxList.size(), gzlslid);
        return shxxList;
    }

    /**
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzlslid 工作流实例ID
     * @description  获取项目信息
     */
    private BdcXmDO getBdcXmxx(String gzlslid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);

        if(CollectionUtils.isEmpty(bdcXmList) || null == bdcXmList.get(0)) {
            LOGGER.warn("生成流程项目所有节点审核信息操作中止，原因：未查询到项目信息，参数gzlslid：{}", gzlslid);
            throw new AppException("生成项目所有节点审核信息异常，原因：未查询到项目信息");
        }
        return bdcXmList.get(0);
    }


    /**
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzlslid 工作流实例ID
     * @description  获取当前流程实例中所有的审核环节信息
     */
    private List<TaskData> getUserTasks(String gzlslid) {
        // 获取流程定义的审核环节信息
        List<UserTaskDto> userTasks = workFlowUtils.listShjdxx(gzlslid);
        if(CollectionUtils.isEmpty(userTasks)) {
            LOGGER.warn("生成流程项目所有节点审核信息操作中止，原因：未查询到大云流程环节信息，参数gzlslid：{}", gzlslid);
            throw new AppException("生成项目所有节点审核信息异常，原因：未查询到大云流程环节信息");
        }

        LOGGER.info("获取流程定义的审核环节信息:{}",JSONObject.toJSONString(userTasks));
        // 获取当前流程实例已经生成的环节信息
        List<TaskData> tasks = processTaskClient.listProcessTask(gzlslid);
        if(CollectionUtils.isEmpty(tasks)) {
            LOGGER.warn("生成流程项目所有节点审核信息操作中止，原因：未查询到当前流程实例已经生成的环节信息，参数gzlslid：{}", gzlslid);
            throw new AppException("生成项目所有节点审核信息异常，原因：未查询到当前流程实例已经生成的环节信息");
        }
        LOGGER.info("获取当前流程实例已经生成的环节信息:{}",JSONObject.toJSONString(tasks));
        // 根据名称匹配出当前流程实例中审核节点
        List<TaskData> result = new ArrayList<>();
        for(TaskData taskData : tasks) {
            for(UserTaskDto userTaskDto : userTasks) {
                if(StringUtils.equals(taskData.getTaskName(), userTaskDto.getName())) {
                    result.add(taskData);
                }
            }
        }
        return result;
    }

    /**
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzlslid 工作流实例ID
     * @param userName 用户账号
     * @description  获取签名信息
     */
    private String getSignId(String gzlslid, String userName) {
        String signId = storageClient.userSign(userName);
        if (StringUtils.isBlank(signId)) {
            LOGGER.warn("生成流程项目所有节点审核信息操作中止，原因：签名失败，参数gzlslid：{}", gzlslid);
            throw new AppException("生成项目所有节点审核信息异常，原因：签名失败");
        }
        return signId;
    }

    /**
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcXmDO 项目信息
     * @description  获取用户信息
     */
    private UserDto getUserInfo(BdcXmDO bdcXmDO) {
        // 默认采用受理人员
        UserDto userDto = userManagerUtils.getUserByUserid(bdcXmDO.getSlrid());

        if(null == userDto || StringUtils.isBlank(userDto.getUsername())) {
            LOGGER.warn("生成流程项目所有节点审核信息操作中止，原因：获取用户信息失败，参数gzlslid：{}", bdcXmDO.getGzlslid());
            throw new AppException("生成项目所有节点审核信息异常，原因：获取用户信息失败");
        }
        return userDto;
    }

    /**
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcXmDO 项目信息
     * @param taskData 工作流环节信息
     * @param signId 签名ID
     * @description  生成当前指定环节的审核意见信息
     */
    private BdcShxxDO generateBdcShxx(BdcXmDO bdcXmDO, TaskData taskData, UserDto userDTO, String signId) {
        // 获取当前环节配置的默认意见
        String comment = this.getXtMryj(bdcXmDO.getGzldyid(), taskData.getTaskName(), bdcXmDO.getGzlslid());

        BdcShxxDO bdcShxxDO = new BdcShxxDO();

        if (StringUtils.isNotBlank(taskData.getTaskId())) {
            bdcShxxDO.setShxxid(taskData.getTaskId());
        }else{
            bdcShxxDO.setShxxid(UUIDGenerator.generate16());
        }

        bdcShxxDO.setGzlslid(bdcXmDO.getGzlslid());
        bdcShxxDO.setJdmc(taskData.getTaskName());
        bdcShxxDO.setShryxm(userDTO.getUsername());
        bdcShxxDO.setShryid(userDTO.getId());
        bdcShxxDO.setShkssj(bdcXmDO.getDjsj());
        bdcShxxDO.setShjssj(new Date());
        bdcShxxDO.setShyj(comment);
        bdcShxxDO.setQmsj(new Date());
        bdcShxxDO.setQmid(signId);

        return bdcShxxDO;
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
        BdcXtMryjDO bdcXtMryjDO = bdcXtMryjFeignService.queryMryj(null, gzldyKey, jdmc);
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

    /**
     * @param taskId  当前流程ID
     * @param gzlslid 工作流实例ID
     * @param jdmc    节点名称
     * @return String mryj
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 判断当前节点是否是退回重签节点，如果是退回重签节点，需要继承上一次的签名信息,并将信息保存到库中
     */
    private String extendsYshxx(String taskId, String gzlslid, String jdmc) {
        if (StringUtils.isBlank(taskId) || StringUtils.isBlank(gzlslid) || StringUtils.isBlank(jdmc)) {
            return "";
        }
        BdcShxxQO bdcShxxQO = new BdcShxxQO();
        bdcShxxQO.setGzlslid(gzlslid);
        bdcShxxQO.setJdmc(jdmc);
        bdcShxxQO.setShryxm(userManagerUtils.getCurrentUserName());
        List<BdcShxxDO> bdcShxxDOList = this.queryBdcShxx(bdcShxxQO);
        if (CollectionUtils.isNotEmpty(bdcShxxDOList)) {
            bdcShxxDOList.sort(Comparator.comparing(BdcShxxDO::getSxh));
            // 要获取审核sxh最大的那条数据
            Collections.reverse(bdcShxxDOList);
            BdcShxxDO bdcShxxDOTemp = bdcShxxDOList.get(0);
            bdcShxxDOTemp.setShkssj(new Date());
            bdcShxxDOTemp.setShxxid(taskId);
            bdcShxxDOTemp.setShjssj(null);
            this.saveOrUpdateBdcShxx(bdcShxxDOTemp);
            return bdcShxxDOTemp.getShyj();
        }
        return "";
    }

    /**
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param gzlslids 工作流实例ID集合
     * @param userName 当前用户
     * @return boolean
     * @description  初审和二审是否是一样的审核人
     */
    @Override
    public boolean hasSameShr(List<String> gzlslids,String userName){
        if(CollectionUtils.isNotEmpty(gzlslids)){
            UserDto userDto = null;
            if(StringUtils.isBlank(userName)){
                userName = userManagerUtils.getCurrentUserName();
            }
            userDto = userManagerUtils.getUserByName(userName);
            if(null == userDto){
                throw new AppException("检验初审和二审审核人是否一致，无法获取当前用户信息");
            }
            Map map = new HashMap();
            map.put("gzlslids",gzlslids);
            List<BdcShxxDO> list = bdcShxxMapper.queryCsShr(map);
            String gzlslidStr = "";
            if(CollectionUtils.isNotEmpty(list)){
                for(BdcShxxDO bdcShxxDO : list){
                    // 当任意一个初审和当前认领人是一样的时候，直接返回true
                    // 有的会存在 第一个审核人是错的 退回后 审核人变了，倒序后只比较sxh最大的一个即可
                    if(gzlslidStr.indexOf(bdcShxxDO.getGzlslid()) == -1 && StringUtils.equals(bdcShxxDO.getShryxm(),userDto.getUsername())
                            && StringUtils.equals(bdcShxxDO.getShryid(),userDto.getId())){
                        return true;
                    }
                    gzlslidStr += bdcShxxDO.getGzlslid();
                }
            }
        }else{
            throw new AppException("检验初审和二审审核人是否一致，gzlslid集合为空");
        }
        return false;
    }

    /**
     * @param processInsId
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除审核意见和签名信息
     */
    @Override
    public void deleteShxxPdf(String processInsId) {
        if(StringUtils.isNotBlank(processInsId)){
            List<StorageDto> storageDtoList = storageClient.listStoragesByName("clientId", processInsId, null, "审核信息", null, 0);
            if(CollectionUtils.isNotEmpty(storageDtoList)){
                List<String> list = new ArrayList<>();
                list.add(storageDtoList.get(0).getId());
                boolean result = storageClient.deleteStorages(list);
                LOGGER.info("审核信息目录文件删除结果：{}", result);
            }

        }else{
            LOGGER.info("删除审核信息pdf,缺失processInsId");
        }
    }

    @Override
    public List<BdcShxxDO> zdqm(String processInsId,String currentUserName){
        if(StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("自动签名异常，原因：未传递参数 gzlslid");
        }
        List<BdcShxxDO> bdcShxxDOList =new ArrayList<>();
        // 获取流程环节信息
        List<TaskData> userTasks = this.getUserTasks(processInsId);
        if(CollectionUtils.isNotEmpty(userTasks)){
            for(TaskData taskData:userTasks){
                BdcShxxQO bdcShxxQO = new BdcShxxQO();
                bdcShxxQO.setShxxid(taskData.getTaskId());
                List<BdcShxxDO> bdcShxxDOS = queryBdcShxx(bdcShxxQO);
                // 审核信息已经签名，跳出循环不自动签名
                if (CollectionUtils.isNotEmpty(bdcShxxDOS) && StringUtils.isNotBlank(bdcShxxDOS.get(0).getQmid())
                        && StringUtils.isNotBlank(bdcShxxDOS.get(0).getShyj()) && bdcShxxDOS.get(0).getQmsj() != null) {
                    continue;
                }

                BdcShxxDO bdcShxxDO = new BdcShxxDO();
                bdcShxxDO.setGzlslid(processInsId);
                bdcShxxDO.setShxxid(taskData.getTaskId());
                bdcShxxDO.setJdmc(taskData.getTaskName());
                //获取默认意见
                String mryj =getXtMryj(taskData.getProcessKey(), taskData.getTaskName(),processInsId);
                bdcShxxDO.setShyj(mryj);
                String shrxm =taskData.getTaskAssigin();
                if(StringUtils.isBlank(shrxm)){
                    shrxm =currentUserName;
                }
                if(StringUtils.isBlank(shrxm)){
                    throw new AppException("未获取到签名人信息");
                }
                bdcShxxDO.setShryxm(shrxm);
                UserDto userDto = userManagerUtils.getUserByName(bdcShxxDO.getShryxm());
                if (userDto != null) {
                    bdcShxxDO.setShryid(userDto.getId());
                }
                // 获取签名信息
                String signId = this.getSignId(processInsId, shrxm);
                bdcShxxDO.setQmid(signId);
                bdcShxxDO.setQmsj(new Date());
                Date shkssj = new Date();
                if (null != bdcShxxDO.getQmsj()) {
                    shkssj = bdcShxxDO.getQmsj();
                }
                bdcShxxDO.setShkssj(shkssj);
                saveOrUpdateBdcShxx(bdcShxxDO);
                bdcShxxDOList.add(bdcShxxDO);
            }
        }
        LOGGER.info("自动签名共生成{}条审核记录，对应gzlslid：{}", bdcShxxDOList.size(), processInsId);
        return bdcShxxDOList;

    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcShxxQOList 审核信息参数
     * @return List<BdcShxxPdfDTO> 审批表信息
     * @description 获取打印审批表PDF
     */
    @Override
    public List<BdcShxxPdfDTO> getBdcSpbPdf(List<BdcShxxQO> bdcShxxQOList) {
        if(CollectionUtils.isEmpty(bdcShxxQOList)) {
            throw new AppException("缺失受理编号参数");
        }

        for(BdcShxxQO bdcShxxQO : bdcShxxQOList) {
            if (null == bdcShxxQO || StringUtils.isBlank(bdcShxxQO.getSlbh())) {
                throw new AppException("缺失受理编号参数");
            }
        }

        try {
            List<BdcShxxPdfDTO> bdcShxxPdfDTOList = new ArrayList<>();
            for (BdcShxxQO bdcShxxQO : bdcShxxQOList) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setSlbh(bdcShxxQO.getSlbh());
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isEmpty(bdcXmDOList) || null == bdcXmDOList.get(0)) {
                    continue;
                }

                // 获取打印类型
                String dylx = Constants.DYLX_BDC_SQ_SPB;
                BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzFeignService.queryBdcDjxlPzByGzldyidAndDjxl(bdcXmDO.getGzldyid(), bdcXmDO.getDjxl());
                if (Objects.nonNull(bdcDjxlPzDO) && StringUtils.isNotBlank(bdcDjxlPzDO.getSpbdylx())) {
                    dylx = bdcDjxlPzDO.getSpbdylx();
                }

                // 是否批量流程
                int xmlx = bdcXmFeignService.makeSureBdcXmLx(bdcXmDO.getGzlslid());
                if (xmlx == CommonConstantUtils.LCLX_PL || xmlx == CommonConstantUtils.LCLX_PLZH) {
                    dylx += "pl";
                }

                // 调用打印数据源生成XML
                BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
                bdcPrintDTO.setDylx(dylx);
                bdcPrintDTO.setGzlslid(bdcXmDO.getGzlslid());
                bdcPrintDTO.setBdcUrlDTO(new BdcUrlDTO());

                List<String> jdmcList = new ArrayList();
                List<UserTaskDto> userTaskDataList = workFlowUtils.listShjdxx(bdcXmDO.getGzlslid());
                userTaskDataList.forEach(taskData -> jdmcList.add(taskData.getName()));
                bdcPrintDTO.setJdmcList(jdmcList);
                if(CommonConstantUtils.DJLX_ZXDJ_DM.equals(bdcXmDO.getDjlx())) {
                    bdcPrintDTO.setZxlc(true);
                }
                String xml = bdcShbdPrintService.bdPrintXml(bdcPrintDTO);

                // 生成PDF
                OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
                String wordName = dylx + ".docx";
                pdfExportDTO.setFileName("审核信息表");
                pdfExportDTO.setXmlData(xml);
                // 读取打印数据源上传的模板
                BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
                bdcDysjPzDO.setDylx(dylx);
                List<BdcDysjPzDO> dysjPzDOList = bdcDypzService.listBdcDysjPz(bdcDysjPzDO);
                if(CollectionUtils.isNotEmpty(dysjPzDOList) && null != dysjPzDOList.get(0) && StringUtils.isNotBlank(dysjPzDOList.get(0).getPdfpath())) {
                    pdfExportDTO.setModelName(dysjPzDOList.get(0).getPdfpath());
                } else {
                    pdfExportDTO.setModelName(printPath + wordName);
                }
                // 证明文件先保留，不删除
                String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);

                // 返回数据
                BdcShxxPdfDTO bdcShxxPdfDTO = new BdcShxxPdfDTO();
                bdcShxxPdfDTO.setSlbh(bdcXmDO.getSlbh());
                bdcShxxPdfDTO.setXml(xml);
                bdcShxxPdfDTO.setDysj(DateFormatUtils.format(new Date(), sdf_ymdhms));
                bdcShxxPdfDTO.setPdf(Base64Utils.encodeByteToBase64Str(FileUtils.readFileToByteArray(new File(pdfFilePath))));
                bdcShxxPdfDTOList.add(bdcShxxPdfDTO);
            }
            return bdcShxxPdfDTOList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("获取打印审批表PDF失败");
        }
    }
}
