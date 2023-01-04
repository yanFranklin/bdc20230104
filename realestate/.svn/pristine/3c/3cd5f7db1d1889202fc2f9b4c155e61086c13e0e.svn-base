package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.realestate.common.matcher.TaskHandleClientMatcher;
import cn.gtmap.gtc.workflow.domain.manage.BackTaskDto;
import cn.gtmap.gtc.workflow.domain.manage.ForwardTaskDto;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.accept.core.service.BdcDsfdjJhxxService;
import cn.gtmap.realestate.common.core.domain.BdcShxxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcDsfdjJhxxDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.register.BdcShxxQO;
import cn.gtmap.realestate.common.core.service.EntityService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcShxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CheckParameter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/6/5
 * @description 第三方登记交互信息服务
 */
@Service
public class BdcDsfdjJhxxServiceImpl implements BdcDsfdjJhxxService {

    private static Logger LOGGER = LoggerFactory.getLogger(BdcDsfdjJhxxServiceImpl.class);


    private static final String DSFTZ_ERROR_MSG = "数据库触发器调用更新待办接口异常，";
    /**
     * 第三方登记状态：内网审核
     */
    private static final int DSFDJZT_SHTG = 4;
    /**
     * 第三方登记状态：内网退件
     */
    private static final int DSFDJZT_TJ = 2;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    private EntityService entityService;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private DozerBeanMapper acceptDozerMapper;

    @Autowired
    BdcShxxFeignService bdcShxxFeignService;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private TaskHandleClientMatcher taskHandleClient;

    @Override
    public List<BdcDsfdjJhxxDO> initDsfdjJhxx(String gzlslid){
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("缺失工作流实例ID!");
        }
        BdcXmQO bdcXmQO =new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            LOGGER.error("未生成项目数据！工作流实例id{}", gzlslid);
            return new ArrayList<>(1);
        }
        //查询是否已存在交互信息
        BdcDsfdjJhxxDO bdcDsfdjJhxxQO =new BdcDsfdjJhxxDO();
        bdcDsfdjJhxxQO.setBdcslbh(bdcXmDOList.get(0).getSlbh());
        bdcDsfdjJhxxQO.setBdcxmid(bdcXmDOList.get(0).getXmid());
        List<BdcDsfdjJhxxDO> ydsfdjJhxxList =listBdcDsfdjJhxx(bdcDsfdjJhxxQO);
        if(CollectionUtils.isNotEmpty(ydsfdjJhxxList)){
            return ydsfdjJhxxList;

        }

        String shrxm ="";
        // 查询审核信息
        BdcShxxQO bdcShxxQO = new BdcShxxQO();
        bdcShxxQO.setGzlslid(gzlslid);
        List<BdcShxxDO> bdcShxxDOList = bdcShxxFeignService.queryBdcShxx(bdcShxxQO);
        if(CollectionUtils.isNotEmpty(bdcShxxDOList)){
            shrxm =bdcShxxDOList.get(0).getShryxm();
        }
        List<BdcDsfdjJhxxDO> bdcDsfdjJhxxDOList =new ArrayList<>();

        for(BdcXmDO bdcXmDO:bdcXmDOList){
            BdcDsfdjJhxxDO bdcDsfdjJhxxDO =new BdcDsfdjJhxxDO();
            acceptDozerMapper.map(bdcXmDO, bdcDsfdjJhxxDO);
            bdcDsfdjJhxxDO.setBdcsprxm(shrxm);
            bdcDsfdjJhxxDO.setFclx(bdcXmDO.getGzldyid()+bdcXmDO.getDjxl());
            bdcDsfdjJhxxDOList.add(bdcDsfdjJhxxDO);
            //获取第三方字典值
            BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
            bdcZdDsfzdgxDO.setZdbs("DSF_ZD_FCLX");
            bdcZdDsfzdgxDO.setBdczdz(bdcDsfdjJhxxDO.getFclx());
            bdcZdDsfzdgxDO.setDsfxtbs("dsfjhxx");
            BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
            if(result != null) {
                bdcDsfdjJhxxDO.setFclx(result.getDsfzdz());
            }
            bdcDsfdjJhxxDO.setDsfdjzt(0);
            bdcDsfdjJhxxDO.setBdcshsj(new Date());
        }
        if(CollectionUtils.isNotEmpty(bdcDsfdjJhxxDOList)){
            entityMapper.insertBatchSelective(bdcDsfdjJhxxDOList);
        }
        return bdcDsfdjJhxxDOList;


    }

    @Override
    public void updateDsfdjJhxxByBdcslbh(JSONObject jsonObject,String bdcslbh){
        if(jsonObject ==null ||StringUtils.isBlank(bdcslbh)){
            throw new AppException("更新第三方登记交互信息缺失参数");
        }
        BdcDsfdjJhxxDO bdcDsfdjJhxxDO =new BdcDsfdjJhxxDO();
        bdcDsfdjJhxxDO.setBdcslbh(bdcslbh);
        List<BdcDsfdjJhxxDO> bdcDsfdjJhxxDOList =listBdcDsfdjJhxx(bdcDsfdjJhxxDO);
        if(CollectionUtils.isEmpty(bdcDsfdjJhxxDOList)){
            throw new AppException("当前不动产受理编号未查询到交互信息"+bdcslbh);
        }
        for(BdcDsfdjJhxxDO dsfdjJhxxDO:bdcDsfdjJhxxDOList){
            jsonObject.put("bdcxmid",dsfdjJhxxDO.getBdcxmid());
            entityService.updateByJsonEntity(JSONObject.toJSONString(jsonObject),BdcDsfdjJhxxDO.class);
        }

    }

    @Override
    public List<BdcDsfdjJhxxDO> listBdcDsfdjJhxx(BdcDsfdjJhxxDO bdcDsfdjJhxxDO){
        if (!CheckParameter.checkAnyParameter(bdcDsfdjJhxxDO)) {
            throw new AppException("查询第三方登记交互信息缺失参数");
        }
        return entityMapper.selectByObj(bdcDsfdjJhxxDO);

    }

    @Override
    public void dsfTzModifyTaskStatus(BdcDsfdjJhxxDO bdcDsfdjJhxxDO) {
        LOGGER.info("请求参数为：{}", JSON.toJSONString(bdcDsfdjJhxxDO));
        if(StringUtils.isAnyBlank(bdcDsfdjJhxxDO.getBdcxmid(), bdcDsfdjJhxxDO.getBdcslbh())){
            throw new AppException(DSFTZ_ERROR_MSG + "未获取到不动产项目ID。");
        }
        String bdcXmId = bdcDsfdjJhxxDO.getBdcxmid();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(bdcXmId);
        bdcXmQO.setSlbh(bdcDsfdjJhxxDO.getBdcslbh());
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new AppException(DSFTZ_ERROR_MSG + "未获取到不动产项目信息。xmid:" + bdcXmId);
        }
        String gzlslid = bdcXmDOList.get(0).getGzlslid();
        List<TaskData> taskList = processTaskClient.processLastTasks(bdcXmDOList.get(0).getGzlslid());
        if(CollectionUtils.isEmpty(taskList)){
            throw new AppException(DSFTZ_ERROR_MSG + "未获取到流程任务信息。gzlslid:" + gzlslid);
        }
        String taskId = taskList.get(0).getTaskId();
        // 第三方更新登记状态为 时，办结任务
        if(bdcDsfdjJhxxDO.getDsfdjzt().equals(DSFDJZT_SHTG)){
            ForwardTaskDto forwardTaskDto = new ForwardTaskDto();
            forwardTaskDto.setTaskId(taskId);
            taskHandleClient.processEnd(forwardTaskDto);
        }
        // 第三方更新登记状态为 时（审核不通过），返回退回状态、退回人和退回原因，退回至预审节点
        if(bdcDsfdjJhxxDO.getDsfdjzt().equals(DSFDJZT_TJ)){
            BackTaskDto backTaskDto = new BackTaskDto();
            backTaskDto.setTaskId(taskId);
            backTaskDto.setOpinion(bdcDsfdjJhxxDO.getDsfyj());
            taskHandleClient.backToFirst(backTaskDto);
        }
    }

    @Override
    public void deleteDsfdjJhxx(String gzlslid){
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("缺失工作流实例ID!");
        }
        BdcXmQO bdcXmQO =new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new AppException("未生成项目!工作流实例ID"+gzlslid);
        }
        Example example = new Example(BdcDsfdjJhxxDO.class);
        example.createCriteria().andEqualTo("bdcslbh", bdcXmDOList.get(0).getSlbh());
        entityMapper.deleteByExampleNotNull(example);

    }

}
