package cn.gtmap.realestate.register.service.impl.xxbl;

import cn.gtmap.gtc.feign.common.exception.GtFeignException;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.CategoryProcessDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.domain.BdcBlShDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.register.XxblDbDTO;
import cn.gtmap.realestate.common.core.enums.BdcBlShEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.ex.UserInformationAccessException;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZhGzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.RegisterWorkflowFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.register.service.xxbl.BdcXxblShService;
import cn.gtmap.realestate.register.util.Constants;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.util.*;

/**
 * 不动产信息补录审核服务实现类
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.1, 2020/03/16 18:17
 */
@Service
public class BdcXxblShServiceImpl implements BdcXxblShService {

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcXxblShServiceImpl.class);

    /**
     * 当前限定类名
     */
    private static final String CLASS_NAME = BdcXxblShServiceImpl.class.getName();
    @Autowired
    protected MessageProvider messageProvider;
    @Autowired
    private BdcGzZhGzFeignService bdcGzZhGzFeignService;
    @Autowired
    private BdcDbxxFeignService bdcDbxxFeignService;
    @Autowired
    private ProcessTaskClient processTaskClient;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private RegisterWorkflowFeignService registerWorkflowFeignService;

    /**
     * 信息补录 注销权力时更新权利附记，默认不更新
     */
    @Value("${xxbl.zxql.updateFj:false}")
    private String updateZxqlFj;

    /**
     * 生成补录审核信息
     *
     * @param bdcBlShDO 不动产补录审核对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public void generateBlShxx(BdcBlShDO bdcBlShDO) {
        String blshid = UUIDGenerator.generate16();
        bdcBlShDO.setBlshid(blshid);
        bdcBlShDO.setQsjdid(blshid);
        // 1. 添加受理人信息
        UserDto currentUser = getUserDto();
        bdcBlShDO.setSlrid(currentUser.getId());
        // 生成补录审核信息，默认顺序号为 1
        bdcBlShDO.setSxh(1);
        // 补录状态为 3 ，表示未转发
        bdcBlShDO.setBlzt(Constants.BLZT_WZF);
        LOGGER.warn("记录审核信息: {}", JSON.toJSONString(bdcBlShDO));
        entityMapper.insertSelective(bdcBlShDO);
    }

    @Override
    public void forwardBlShxx(BdcBlShDO bdcBlShDO) {
        BdcBlShDO yblshDO = entityMapper.selectByPrimaryKey(BdcBlShDO.class, bdcBlShDO.getBlshid());
        // 顺序号为 1，并且审核开始时间为不空，表示是第二个节点
        if (yblshDO.getSxh() == 1 && yblshDO.getShkssj() != null && Constants.BLSHLX_XJ.equals(yblshDO.getBlshlx())) {
            // 更新原有的结束时间
            yblshDO.setShjssj(new Date());
            // 状态修改为审核结束
            yblshDO.setBlzt(Constants.BLZT_END);
            entityMapper.updateByPrimaryKeySelective(yblshDO);
            // 生成新的一条审核记录
            bdcBlShDO.setBlshid(UUIDGenerator.generate16());
            bdcBlShDO.setQsjdid(yblshDO.getQsjdid());
            // 新生成的受理人是当前用户，也就是上一手的审核人员
            bdcBlShDO.setSlrid(yblshDO.getShryid());
            bdcBlShDO.setXmid(yblshDO.getXmid());
            bdcBlShDO.setGzlslid(yblshDO.getGzlslid());
            bdcBlShDO.setShkssj(new Date());
            // 审核中
            bdcBlShDO.setBlzt(Constants.BLZT_ONGOING);
            bdcBlShDO.setBllx(yblshDO.getBllx());
            bdcBlShDO.setBlshlx(yblshDO.getBlshlx());
            bdcBlShDO.setSxh(2);
            entityMapper.insertSelective(bdcBlShDO);
        } else {
            bdcBlShDO.setBlzt(Constants.BLZT_ONGOING);
            // 记录审核开始时间，审核人员等信息前端传递，在 controller 层判断
            bdcBlShDO.setShkssj(new Date());
            entityMapper.updateByPrimaryKeySelective(bdcBlShDO);
        }
    }

    /**
     * 新增数据审核登簿前验证
     *
     * @param gzlslid
     */
    @Override
    public List<BdcGzyzVO> dbYz(String gzlslid) throws Exception {
        UserDto yzr = userManagerUtils.getCurrentUser();//验证人；
        String yzrid = null == yzr ? null : yzr.getId();
        String yzrzh = null == yzr ? null : yzr.getUsername();

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS) || StringUtils.isBlank(bdcXmDOS.get(0).getGzldymc())) {
            throw new AppException("流程名称字段缺失数据！");
        }
        List<CategoryProcessDto> categoryProcessDtos = processTaskClient.listAllCategoryProcess();
        String processKey = queryProcessDefKey(URLDecoder.decode(bdcXmDOS.get(0).getGzldymc(), "UTF-8"), categoryProcessDtos);
        if (StringUtils.isBlank(processKey)) {
            throw new AppException("无法获取到对应的工作流定义id");
        }
        List<BdcGzYzTsxxDTO> listBdcGzYzTsxx = null;
        if (CollectionUtils.isNotEmpty(bdcXmDOS)) {
            BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
            // 设置登簿验证组合规则processKey_DBYZ
            bdcGzYzQO.setZhbs(StringUtils.replace(CommonConstantUtils.processKey_DBYZ, "processKey", processKey));
            List<Map<String, Object>> paramList = new ArrayList<>();
            bdcGzYzQO.setParamList(paramList);
            bdcGzYzQO.setYzrid(yzrid);
            bdcGzYzQO.setYzrzh(yzrzh);
            //单元号去重
            Set<String> setDyh = new HashSet<>();
            //循环生成条件
            for (BdcXmDO bdcXmDO : bdcXmDOS) {
                if (!setDyh.contains(bdcXmDO.getBdcdyh())) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("bdcdyh", bdcXmDO.getBdcdyh());
                    param.put("xmid", bdcXmDO.getXmid());
                    param.put("slbh", bdcXmDO.getSlbh());
                    param.put("gzlslid", bdcXmDO.getGzlslid());
                    paramList.add(param);
                    setDyh.add(bdcXmDO.getBdcdyh());
                }
            }
            try {
                listBdcGzYzTsxx = bdcGzZhGzFeignService.listBdcGzYzTsxxOfAnyParam(bdcGzYzQO);
            } catch (Exception ex) {
                if (dealGzyzException(ex)) {
                    return Collections.emptyList();
                }
            }
        }
        return BdcGzyzTsxxUtils.checkTsxx(listBdcGzYzTsxx);
    }

    /**
     * @param ex 规则验证异常
     * @return {boolean} 是否通过验证
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 处理规则验证的异常
     */
    private boolean dealGzyzException(Exception ex) {
        //获取规则的时候会抛出异常，当code为  时表示未配置验证项直接返回空集合
        GtFeignException gte;
        if (ex.getCause() instanceof GtFeignException) {
            gte = (GtFeignException) ex.getCause();
            if (gte != null) {
                String responseBody = gte.getMsgBody();
                Map bodyMap = JSON.parseObject(responseBody, Map.class);
                if (MapUtils.getInteger(bodyMap, "code") != null && StringUtils.isNotBlank(MapUtils.getString(bodyMap, "msg"))) {
                    Integer errorCode = MapUtils.getInteger(bodyMap, "code");
                    if (errorCode == 101) {
                        return true;
                    } else {
                        throw new AppException("调用验证服务异常");
                    }
                }
            }
        } else {
            throw new AppException("调用验证服务异常");
        }
        LOGGER.error("流程规则验证异常抛出", ex);
        return false;
    }

    /**
     * 办结补录审核信息
     *
     * @param blshid
     */
    @Override
    public void endBlShxx(String blshid) {
        BdcBlShDO bdcBlShDO = entityMapper.selectByPrimaryKey(BdcBlShDO.class, blshid);
        bdcBlShDO.setBlshid(blshid);
        // 设置 补录状态为 审核结束
        bdcBlShDO.setBlzt(Constants.BLZT_END);
        bdcBlShDO.setShjssj(new Date());
        entityMapper.updateByPrimaryKeySelective(bdcBlShDO);
        // 修改项目的权属状态
        changeAjzt(bdcBlShDO.getGzlslid());
        // 更新注销权利的附记
        if(Boolean.valueOf(updateZxqlFj)) {
            registerWorkflowFeignService.updateZxqlfj(bdcBlShDO.getGzlslid());
        }
    }

    private void changeAjzt(String gzlslid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            throw new AppException("未找到对应的项目数据！");
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);
        // 判断是否需要修改案件状态
        if (!CommonConstantUtils.AJZT_YB_DM.equals(bdcXmDO.getAjzt())) {
            bdcDbxxFeignService.changeAjzt(gzlslid);
        }
        // 权属状态为 0 的数据修改
        if (CommonConstantUtils.QSZT_TEMPORY.equals(bdcXmDO.getQszt())) {
            XxblDbDTO xxblDbDTO = new XxblDbDTO();
            xxblDbDTO.setDbr(bdcXmDO.getDbr());
            xxblDbDTO.setDjjg(bdcXmDO.getDjjg());
            xxblDbDTO.setDjsj(bdcXmDO.getDjsj());
            xxblDbDTO.setGzlslid(gzlslid);
            bdcDbxxFeignService.updateBdcDbxxAndQszt(xxblDbDTO);
        }
    }

    @Override
    public boolean backBlShxx(String blshid) {
        // 退回就将当前的审核信息的 审核开始时间、审核人员 ID 和审核人员姓名全部置空即可
        BdcBlShDO bdcBlShDO = entityMapper.selectByPrimaryKey(BdcBlShDO.class, blshid);
        UserDto userDto = getUserDto();
        if (StringUtils.equals(userDto.getId(), bdcBlShDO.getShryid())) {
            if (bdcBlShDO.getSxh() == 2 && StringUtils.equals(bdcBlShDO.getSlrid(), userDto.getId())) {
                // 中间一个审核人员，退回时删除当前的审核信息，修改上一手的信息。
                entityMapper.delete(bdcBlShDO);
                bdcBlShDO = entityMapper.selectByPrimaryKey(BdcBlShDO.class, bdcBlShDO.getQsjdid());
            }
            // 补录状态设置为 退回
            bdcBlShDO.setBlzt(Constants.BLZT_BACK);
            bdcBlShDO.setShkssj(null);
            bdcBlShDO.setShryid(null);
            bdcBlShDO.setShryxm(null);
            entityMapper.updateByPrimaryKeyNull(bdcBlShDO);
            return true;
        } else {
            return false;
        }

    }

    /**
     * 判断是否正在审核
     *
     * @param bdcBlShDO 审核信息
     * @return {BdcBlShEnum.value}
     */
    @Override
    public Integer checkIsSh(BdcBlShDO bdcBlShDO) {
        if (StringUtils.isBlank(bdcBlShDO.getXmid())) {
            throw new MissingArgumentException("审核信息缺少 XMID");
        }
        Example example = new Example(BdcBlShDO.class);
        // 未通过审核的项目
        example.createCriteria().andEqualTo("xmid", bdcBlShDO.getXmid())
                .andNotEqualTo("blzt", Constants.BLZT_END);

        List<BdcBlShDO> bdcBlShDOS = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(bdcBlShDOS)) {
            UserDto userDto = getUserDto();
            String currentUserId = userDto.getId();
            for (BdcBlShDO blShDO : bdcBlShDOS) {
                //1.不管是市级还是县级，状态为审核中的 都不允许修改
                if (Constants.BLZT_ONGOING.equals(blShDO.getBlzt())) {
                    return BdcBlShEnum.ONGOING.getValue();
                }
                //补录状态为退回
                if (Constants.BLZT_BACK.equals(blShDO.getBlzt())) {
                    if (blShDO.getSxh().equals(1) && StringUtils.equals(blShDO.getSlrid(), currentUserId)) {
                        //初审退回顺序号为1，且只有本人可以修改
                        return BdcBlShEnum.ONGOING_PERSONAL.getValue();
                    }
                    //复审退回，顺序号为2
                    if (blShDO.getSxh().equals(2)) {
                        return BdcBlShEnum.ONGOING.getValue();
                    }
                }
                if (Constants.BLZT_WZF.equals(blShDO.getBlzt())) {
                    if (StringUtils.equals(blShDO.getSlrid(), currentUserId)) {
                        //未转发只有本人可以修改
                        return BdcBlShEnum.ONGOING_PERSONAL.getValue();
                    } else {
                        return BdcBlShEnum.MODIFY.getValue();
                    }
                }
            }
        } else {
            // 未在审核
            return BdcBlShEnum.NOT.getValue();
        }
        // 正在审核不允许打开
        return BdcBlShEnum.ONGOING.getValue();
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcBlShDO]
     * @return cn.gtmap.realestate.common.core.domain.BdcBlShDO
     * @description 根据项目id和受理人id查询可转发的补录记录
     */
    @Override
    public BdcBlShDO queryBdcBlshDO(BdcBlShDO bdcBlShDO) {
        UserDto userDto = getUserDto();
        String currentUserId = userDto.getId();

        List blztList = new ArrayList<>();
        blztList.add(Constants.BLZT_WZF);
        blztList.add(Constants.BLZT_BACK);

        Example example = new Example(BdcBlShDO.class);
        example.createCriteria().andEqualTo("xmid", bdcBlShDO.getXmid())
        .andEqualTo("slrid", currentUserId)
        .andIn("blzt", blztList);

        List<BdcBlShDO> bdcBlShDOS = entityMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(bdcBlShDOS)){
            LOGGER.error("没有查询到可转发的记录！");
            throw new AppException("补录信息有误，请联系管理员！");
        }
        if(bdcBlShDOS.size() > 1){
            throw new AppException("补录信息有误，请联系管理员！");
        }
        return bdcBlShDOS.get(0);
    }

    /**
     * @param xmid xmid
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 删除补录审核信息 <br>
     * <strong>删除项目对应的所有审核信息，包括历史数据</strong>
     * 项目信息被删除后，不需要保留审核信息。
     */
    @Override
    public void deleteBlShxx(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            LOGGER.info("{}: 删除补录审核信息中止，原因：参数 xmid 为空！", CLASS_NAME);
            return;
        }
        LOGGER.debug("{}: 开始删除补录审核信息，对应 xmid: {} ", CLASS_NAME, xmid);
        // 删除对应项目的全部审核信息
        Example example = new Example(BdcBlShDO.class);
        example.createCriteria().andEqualTo("xmid", xmid);
        entityMapper.deleteByExample(example);
        LOGGER.debug("{}: 完成删除补录审核信息，对应 xmid: {} ", CLASS_NAME, xmid);
    }


    /**
     * @param gzlslid gzlslid
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 批量删除补录审核信息 <br>
     * <strong>删除项目对应的所有审核信息，包括历史数据</strong>
     * 项目信息被删除后，不需要保留审核信息。
     */
    @Override
    public void deleteBlShxxs(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            LOGGER.info("{}: 删除补录审核信息中止，原因：参数 gzlslid 为空！", CLASS_NAME);
            return;
        }
        LOGGER.debug("{}: 开始删除补录审核信息，对应 gzlslid: {} ", CLASS_NAME, gzlslid);
        // 删除对应项目的全部审核信息
        Example example = new Example(BdcBlShDO.class);
        example.createCriteria().andEqualTo("gzlslid", gzlslid);
        int result = entityMapper.deleteByExampleNotNull(example);
        LOGGER.debug("{}: 完成删除补录审核信息，删除 {} 条，对应 gzlslid: {} ", CLASS_NAME, result, gzlslid);
    }

    /**
     * 获取用户信息
     * 增加对于用户信息是否为空的验证
     *
     * @return {UserDto} 用户信息 DTO
     */
    private UserDto getUserDto() {
        UserDto currentUser = userManagerUtils.getCurrentUser();
        if (null == currentUser) {
            throw new UserInformationAccessException(messageProvider.getMessage("message.nouser"));
        }
        return currentUser;
    }

    /**
     * 根据 工作流定义名称获取 工作流定义key
     *
     * @param gzldymc             工作流定义名称
     * @param categoryProcessDtos 返回
     * @return {String} 工作流定义key
     */
    private String queryProcessDefKey(String gzldymc, List<CategoryProcessDto> categoryProcessDtos) {
        for (CategoryProcessDto categoryProcessDto : categoryProcessDtos) {
            List<ProcessDefData> processList = categoryProcessDto.getProcessList();
            if (CollectionUtils.isEmpty(processList) && CollectionUtils.isNotEmpty(categoryProcessDto.getChildren())) {
                String processDefKey = queryProcessDefKey(gzldymc, categoryProcessDto.getChildren());
                if (StringUtils.isNotBlank(processDefKey)) {
                    return processDefKey;
                }
            } else {
                for (ProcessDefData processDefData : processList) {
                    if (StringUtils.equals(processDefData.getName(), gzldymc)) {
                        return processDefData.getProcessDefKey();
                    }
                }
            }
        }
        return StringUtils.EMPTY;
    }


    /**
     * 根据更新信息补录审核信息
     * @param bdcBlShDO  信息补录审核信息
     */
    public int updateBlshxx(BdcBlShDO bdcBlShDO){
        if (!CheckParameter.checkAnyParameter(bdcBlShDO)){
            throw new AppException("传入参数为空！");
        }
        Example example= new Example(BdcBlShDO.class,false);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("blshid",bdcBlShDO.getBlshid());
        return entityMapper.updateByExampleSelectiveNotNull(bdcBlShDO,example);
    }

    @Override
    public List<BdcBlShDO> queryBlshxxByXmid(String xmid) {
        if(StringUtils.isBlank(xmid)){
            throw new AppException("传入参数为空！");
        }
        Example example= new Example(BdcBlShDO.class,false);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("xmid",xmid);
        return entityMapper.selectByExample(example);
    }

}
