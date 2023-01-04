package cn.gtmap.realestate.engine.service.impl.check;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzBdsTsxxDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzSjlCsDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzSjlDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzTsxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.engine.core.dto.BdcGzBdsTsxxDTO;
import cn.gtmap.realestate.engine.core.exception.CheckException;
import cn.gtmap.realestate.engine.thread.RuleCheckThreadPool;
import cn.gtmap.realestate.engine.service.BdcGzBdsService;
import cn.gtmap.realestate.engine.service.BdcGzCsService;
import cn.gtmap.realestate.engine.service.BdcGzDmService;
import cn.gtmap.realestate.engine.service.BdcGzZgzCheckService;
import cn.gtmap.realestate.engine.service.impl.dataflow.BdcGzSjlResolverFactory;
import cn.gtmap.realestate.engine.util.Constants;
import cn.gtmap.realestate.engine.util.DataUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/5/3
 * @description 子规则验证逻辑
 */
@Service
public class BdcGzZgzCheckServiceImpl implements BdcGzZgzCheckService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGzZgzCheckServiceImpl.class);
    /**
     * 数据流结果校验表达式逻辑
     */
    @Autowired
    private BdcGzBdsService bdcGzBdsService;
    /**
     * 数据流处理
     */
    @Autowired
    private BdcGzSjlResolverFactory sjlResolverFactory;
    /**
     * 规则参数处理
     */
    @Autowired
    private BdcGzCsService bdcGzCsService;
    /**
     * 动态代码处理
     */
    @Autowired
    private BdcGzDmService bdcGzDmService;

    /**
     * 线程池
     */
    @Autowired
    private RuleCheckThreadPool threadPool;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTOList 组合规则对应的子规则集合
     * @param paramMapList 验证参数
     * @description 获取子规则在不同参数条件下执行得到的数据流结果
     *
     *    参数1    子规则1   结果1
     *    参数1    子规则2   结果2
     *
     *    参数2    子规则1   结果3
     *    参数2    子规则2   结果4
     */
    @Override
    public List<BdcGzZgzTsxxDTO> getZgzSjlResult(List<BdcGzZgzDTO> bdcGzZgzDTOList, List<Map<String, Object>> paramMapList){
        if(CollectionUtils.isEmpty(bdcGzZgzDTOList) || CollectionUtils.isEmpty(paramMapList)){
            return Collections.emptyList();
        }

        // 当前查询子任务数量，即 一个子规则、一个参数 对应一个任务
        int num = paramMapList.size() * bdcGzZgzDTOList.size();
        LOGGER.debug("当前验证获取数据流执行结果共{}个任务，其中参数量{}，子规则数量{}", num, paramMapList.size(), bdcGzZgzDTOList.size());

        // 线程任务结果集合
        List<Future<BdcGzZgzTsxxDTO>> tasksList = new ArrayList<>(num);

        // 向线程池提交获取数据流查询
        for(Map<String, Object> paramMap : paramMapList){
            // 规则内容是相同的，不同参数须用不同实例子规则，新建对象
            List<BdcGzZgzDTO> zgzDTOList = JSON.parseArray(JSON.toJSONString(bdcGzZgzDTOList), BdcGzZgzDTO.class);
            bdcGzCsService.matchParamValue(zgzDTOList, paramMap);

            for(BdcGzZgzDTO bdcGzZgzDTO : zgzDTOList){
                tasksList.add(threadPool.submitTask(bdcGzZgzDTO, paramMap));
            }
        }

        try {
            //子规则数据流查询结果
            List<BdcGzZgzTsxxDTO> bdcGzZgzTsxxDTOList = new ArrayList<>(num);

            for(Future<BdcGzZgzTsxxDTO> task : tasksList) {
                bdcGzZgzTsxxDTOList.add(task.get());
            }
            return bdcGzZgzTsxxDTOList;
        }
        catch (Exception exception) {
            exception.printStackTrace();

            if(exception instanceof ExecutionException && exception.getCause() instanceof CheckException) {
                CheckException checkException = (CheckException)exception.getCause();
                if(checkException.getCause() instanceof  AppException) {
                    AppException appException = (AppException) checkException.getCause();
                    throw new AppException(appException.getCode(), appException.getMessage());
                }
                throw new AppException(ErrorCode.RULE_CHECK_ERROR, "子规则：" + checkException.getGzid() + " 验证异常，请核查规则配置！");
            }
            throw new AppException(ErrorCode.RULE_CHECK_ERROR, "子规则验证异常，请核查规则配置！");
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDOList 子规则集合
     * @return {List} 规则提示信息
     * @description 获取多个子规则校验结果信息（如果校验通过无需提示则返回空）
     */
    @Override
    public List<BdcGzZgzTsxxDTO> getZgzCheckResult(List<BdcGzZgzDTO> bdcGzZgzDOList) {
        if(CollectionUtils.isEmpty(bdcGzZgzDOList)){
            LOGGER.debug("规则子系统执行验证中止：参数子规则集合为空！");
            return Collections.emptyList();
        }

        List<BdcGzZgzTsxxDTO> bdcGzTsxxList = new ArrayList<>(bdcGzZgzDOList.size());
        for(BdcGzZgzDTO bdcGzZgzDTO : bdcGzZgzDOList){
            BdcGzZgzTsxxDTO bdcGzZgzTsxxDTO = this.getZgzCheckResult(bdcGzZgzDTO);
            if(null != bdcGzZgzTsxxDTO && CollectionUtils.isNotEmpty(bdcGzZgzTsxxDTO.getTsxx())){
                bdcGzTsxxList.add(bdcGzZgzTsxxDTO);
            }
        }
        return bdcGzTsxxList;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTO 子规则
     * @return {List} 规则提示信息
     * @description 获取单个子规则校验结果信息
     */
    @Override
    public <T> BdcGzZgzTsxxDTO getZgzCheckResult(BdcGzZgzDTO bdcGzZgzDTO){
        if(null == bdcGzZgzDTO){
            LOGGER.debug("规则子系统执行验证中止：子规则参数为空！");
            return null;
        }

        // 1、获取子规则关联数据流执行结果
        Map<String, T> zgzSjlResult ;
        try {
            zgzSjlResult = sjlResolverFactory.getBdcZgzSjlResult(bdcGzZgzDTO);
        } catch (Exception e) {
            LOGGER.error("子规则：{} 验证异常", bdcGzZgzDTO.getGzid(), e.toString());
            throw new AppException(ErrorCode.RULE_CHECK_ERROR, "子规则验证异常：" + bdcGzZgzDTO.getGzid());
        }

        // 2、对结果进行规则判断处理获取提示信息
        if(MapUtils.isEmpty(zgzSjlResult)){
            LOGGER.debug("规则子系统执行验证中止：子规则{}-{}数据流执行结果为空！", bdcGzZgzDTO.getGzid(), bdcGzZgzDTO.getGzmc());
            return null;
        }
        List<String> tsxxList = new ArrayList<>(20);
        // 2.1、动态代码验证规则
        if(StringUtils.isNotBlank(bdcGzZgzDTO.getJydm())){
            List<String> dmTsxx = bdcGzDmService.executeJavaCode(bdcGzZgzDTO, zgzSjlResult);
            if(CollectionUtils.isNotEmpty(dmTsxx)){
                tsxxList.addAll(dmTsxx);
            }
        }
        // 2.2、图形方式设置规则验证
        if(CollectionUtils.isNotEmpty(bdcGzZgzDTO.getBdcGzBdsTsxxDOList())){
            for(BdcGzBdsTsxxDO bdcGzBdsTsxxDO : bdcGzZgzDTO.getBdcGzBdsTsxxDOList()){
                String droolsDRL = bdcGzBdsService.getDroolsDRL(bdcGzBdsTsxxDO, zgzSjlResult);
                if(StringUtils.isBlank(droolsDRL)){
                    continue;
                }

                tsxxList.add(this.executeDrools(zgzSjlResult, droolsDRL, bdcGzBdsTsxxDO.getGzbds()));
                // 需要清空提示信息结果，避免后续规则校验不通过但是map中仍然缓存该值
                zgzSjlResult.remove(Constants.RULE_RESULT);
            }
        }

        return this.resolveTsxxList(bdcGzZgzDTO, zgzSjlResult, tsxxList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTOList 组合规则对应的子规则集合
     * @param bdcGzYzQO 验证参数
     * @description 获取子规则在不同参数条件下执行得到的数据流结果
     */
    @Override
    public List<BdcGzZgzTsxxDTO> getZgzSjlResult(List<BdcGzZgzDTO> bdcGzZgzDTOList, BdcGzYzQO bdcGzYzQO){
        if(null == bdcGzYzQO){
            return Collections.emptyList();
        }
       return this.getZgzSjlResult(bdcGzZgzDTOList, bdcGzYzQO.getParamList());
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTOList 组合规则对应的子规则集合
     * @description 获取子规则验证表达式转换为Drools表达式信息
     */
    @Override
    public List<BdcGzBdsTsxxDTO> getZgzBdsDrools(List<BdcGzZgzDTO> bdcGzZgzDTOList) {
        List<BdcGzBdsTsxxDTO> bdcGzBdsTsxxDTOList = new ArrayList<>(bdcGzZgzDTOList.size() * 3);
        for(BdcGzZgzDTO bdcGzZgzDTO : bdcGzZgzDTOList){
            if(null == bdcGzZgzDTO || CollectionUtils.isEmpty(bdcGzZgzDTO.getBdcGzBdsTsxxDOList())){
                continue;
            }

            for(BdcGzBdsTsxxDO bdcGzBdsTsxxDO : bdcGzZgzDTO.getBdcGzBdsTsxxDOList()){
                String drool = bdcGzBdsService.getDroolsDRL(bdcGzBdsTsxxDO, null);
                KieBase kbase = bdcGzBdsService.getDroolsKbase(drool, bdcGzBdsTsxxDO.getBdsid());

                bdcGzBdsTsxxDTOList.add(new BdcGzBdsTsxxDTO.Builder(bdcGzBdsTsxxDO).drool(drool).kbase(kbase).build());
            }
        }
        return bdcGzBdsTsxxDTOList;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param droolsDRL  校验内容
     * @param zgzSjlResult 数据流执行结果
     * @param gzbds 规则表达式
     * @return {String} 提示信息
     * @description  调用规则引擎执行相关校验
     */
    private <T> String executeDrools(Map<String, T> zgzSjlResult, String droolsDRL, String gzbds) {
        KieSession kSession = null;
        String errorMsg = "校验规则时发生错误，请核查配置！";
        try{
            KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
            kb.add(ResourceFactory.newByteArrayResource(droolsDRL.getBytes("utf-8")), ResourceType.DRL);
            KnowledgeBuilderErrors errors = kb.getErrors();
            for (KnowledgeBuilderError error : errors) {
                LOGGER.error("规则子系统执行规则时规则表达式存在错误！{}，{}", error.getMessage(), error);
                errorMsg = "规则表达式存在错误：" + gzbds;
                throw new AppException();
            }
            InternalKnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
            kBase.addPackages(kb.getKnowledgePackages());
            kSession = kBase.newKieSession();
            kSession.insert(zgzSjlResult);
            kSession.fireAllRules();
        } catch (Exception e) {
            LOGGER.error("规则子系统规则校验：校验规则时发生错误，{}", e.getMessage());
            throw new AppException(errorMsg);
        } finally {
            if(null != kSession){
                kSession.dispose();
            }
        }
        return  (String) zgzSjlResult.get(Constants.RULE_RESULT);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTO  子规则
     * @param zgzSjlResult 数据流执行结果
     * @param tsxxList 提示信息
     * @description  处理提示信息，替换其中的变量信息，返回最终提示内容
     */
    private <T> BdcGzZgzTsxxDTO resolveTsxxList(BdcGzZgzDTO bdcGzZgzDTO, Map<String, T> zgzSjlResult , List<String> tsxxList) {
        List<String> list = new ArrayList<>(tsxxList.size());
        Map<String, T> paramsMap = this.getParamMap(bdcGzZgzDTO);

        for(String tsxx : tsxxList){
            if(StringUtils.isBlank(tsxx)){
                continue;
            }
            tsxx = DataUtil.resolveTipInfo(tsxx, zgzSjlResult, paramsMap);
            list.add(tsxx);
        }

        return new BdcGzZgzTsxxDTO.Builder<T>(bdcGzZgzDTO).sjljg(zgzSjlResult).paramMap(paramsMap).tsxx(list).build();
    }


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTO  子规则
     * @return {Map}  参数信息
     * @description 获取当前子规则对应的所有参数集合
     */
    private <T> Map<String, T> getParamMap(BdcGzZgzDTO bdcGzZgzDTO) {
        Map<String, T> paramMap = new HashMap<>(20);

        // 获取数据流集合
        List<BdcGzSjlDTO> sjlDTOList = bdcGzZgzDTO.getBdcGzSjlDTOList();
        if(CollectionUtils.isEmpty(sjlDTOList)){
            return MapUtils.EMPTY_MAP;
        }

        // 循环数据流，获取对应参数
        for(BdcGzSjlDTO sjlDTO: sjlDTOList){
            if(null != sjlDTO && CollectionUtils.isNotEmpty(sjlDTO.getBdcGzSjlCsDOList())){
                List<BdcGzSjlCsDO> csDOList = sjlDTO.getBdcGzSjlCsDOList();
                for(BdcGzSjlCsDO csDO : csDOList){
                    if(null != csDO){
                        paramMap.put(csDO.getSjlcsmc(), (T) csDO.getSjlcsz());
                    }
                }
            }
        }
        return paramMap;
    }
}
