package cn.gtmap.realestate.engine.service.impl.check;

import cn.gtmap.realestate.common.core.dto.engine.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.engine.core.dto.BdcGzBdsTsxxDTO;
import cn.gtmap.realestate.engine.core.service.BdcGzYwService;
import cn.gtmap.realestate.engine.core.service.BdcGzZhGzService;
import cn.gtmap.realestate.engine.service.BdcGzZgzCheckService;
import cn.gtmap.realestate.engine.service.BdcGzZhgzCheckService;
import cn.gtmap.realestate.engine.util.AsyncUtil;
import cn.gtmap.realestate.engine.util.DataUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/4
 * @description 不动产规则校验逻辑实现
 */
@Service
public class BdcGzZhgzCheckServiceImpl implements BdcGzZhgzCheckService {
    /** 日志处理 */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGzZhgzCheckServiceImpl.class);

    /** 子规则校验逻辑 */
    @Autowired
    private BdcGzZgzCheckService bdcGzZgzCheckService;

    /** 组合规则 */
    @Autowired
    private BdcGzZhGzService bdcGzZhGzService;

    /** 规则业务处理 */
    @Autowired
    private BdcGzYwService bdcGzYwService;

    @Autowired
    private AsyncUtil asyncUtil;

    /**
     * 控制验证请求数量（批量分批次每次最多5000，为保证并发时候单个请求响应，空余10个凭证）
     * 2万级别要求系统VM设置： -Xms1024m -Xmx2048m -Xmn1024m -XX:MetaspaceSize=50M -XX:MaxMetaspaceSize=1024m
     */
    private static Semaphore semaphore = new Semaphore(20010);


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzYzQO  规则验证参数
     * @return {BdcGzZhgzTsxxDTO} 提示信息
     * @description 组合规则验证，获取对应提示信息（参数bdcGzYzQO传值：zhbs、paramMap）
     */
    @Override
    public BdcGzZhgzTsxxDTO getZhgzYzTsxx(BdcGzYzQO bdcGzYzQO) {
        if(null == bdcGzYzQO || StringUtils.isBlank(bdcGzYzQO.getZhbs())){
            throw new NullPointerException("规则子系统——获取组合规则校验结果逻辑报错，原因：参数为空！");
        }

        bdcGzYzQO.setParamList(new ArrayList<>(1));
        bdcGzYzQO.getParamList().add(bdcGzYzQO.getParamMap());

        // 调用批量参数验证
        List<BdcGzYzTsxxDTO> result = this.listBdcGzYzTsxxOfAnyParam(bdcGzYzQO);
        if(CollectionUtils.isEmpty(result)) {
            return null;
        }
        return result.get(0);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzYzQO 验证查询参数
     * @return {List} 批量验证提示信息
     * @description  批量组合规则验证，获取对应提示信息
     *    说明：
     *    1、例如传递多个BDCDYH进行查封、抵押验证等；
     *    2、规则子系统本身业务无关性，但是为了方便不动产系统调用封装该服务；
     *    3、参数bdcGzYzQO传值：zhbs、bdcGzYzsjDTOList；
     */
    @Override
    public List<BdcGzYzTsxxDTO> listBdcGzYzTsxx(BdcGzYzQO bdcGzYzQO) {
        if(null == bdcGzYzQO || StringUtils.isBlank(bdcGzYzQO.getZhbs()) || CollectionUtils.isEmpty(bdcGzYzQO.getBdcGzYzsjDTOList())){
            throw new NullPointerException("规则子系统——批量组合规则验证：参数为空！");
        }

        // 转换参数形式
        List<Map<String, Object>> paramsMapList = new ArrayList<>(bdcGzYzQO.getBdcGzYzsjDTOList().size());
        try {
            for(BdcGzYzsjDTO bdcGzYzsjDTO : bdcGzYzQO.getBdcGzYzsjDTOList()){
                // 1、封装组合规则验证查询参数
                Map<String, Object> paramMap = new HashMap<>(2);
                Field[] fields = bdcGzYzsjDTO.getClass().getDeclaredFields();
                if(null != fields && fields.length > 0) {
                    for(Field field : fields) {
                        field.setAccessible(true);
                        Object val = field.get(bdcGzYzsjDTO);
                        if(null != val) {
                            paramMap.put(field.getName(), String.valueOf(val));
                        }
                    }
                }
                paramsMapList.add(paramMap);
            }
        } catch (IllegalAccessException e) {
            LOGGER.error("规则转换参数异常，参数{}", JSON.toJSONString(bdcGzYzQO), e);
            throw new AppException("规则转换参数异常");
        }
        bdcGzYzQO.setParamList(paramsMapList);

        // 调用批量参数验证
        return this.listBdcGzYzTsxxOfAnyParam(bdcGzYzQO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzYzQO 验证查询参数
     * @return {List} 批量验证提示信息
     * @description 批量规则验证（传入任意参数）
     * 说明：
     * 1、参数bdcGzYzQO传值：zhbs、paramList
     */
    @Override
    public List<BdcGzYzTsxxDTO> listBdcGzYzTsxxOfAnyParam(BdcGzYzQO bdcGzYzQO) {
        if(null == bdcGzYzQO || StringUtils.isBlank(bdcGzYzQO.getZhbs()) || CollectionUtils.isEmpty(bdcGzYzQO.getParamList())){
            throw new NullPointerException("规则子系统——批量组合规则验证：参数为空！");
        }

        // 获取组合规则关联的子规则信息
        BdcGzZhgzDTO bdcGzZhgzDTO = bdcGzZhGzService.listBdcZgzByZhbs(bdcGzYzQO.getZhbs());
        // 排除掉不需要验证的子规则
        bdcGzYwService.filterNotNeedCheckZgz(bdcGzYzQO, bdcGzZhgzDTO);
        if(CollectionUtils.isEmpty(bdcGzZhgzDTO.getBdcGzZgzDTOList())) {
            return Collections.emptyList();
        }

        // 参数数量
        int paramListSize = bdcGzYzQO.getParamList().size();
        // 子规则数量
        int zgzListSize = bdcGzZhgzDTO.getBdcGzZgzDTOList().size();

        //返回结果，组合规则验证信息
        List<BdcGzYzTsxxDTO> bdcGzYzTsxxDTOList = new ArrayList<>();

        try {
            if (paramListSize * zgzListSize < 5000) {
                bdcGzYzTsxxDTOList = this.acquireSemaphore(bdcGzYzQO.getParamList(), bdcGzZhgzDTO);
            } else {
                // 为避免批量验证数量过多，分批次验证
                // 每一批次参数数量
                int partSize = 5000 / zgzListSize;
                // 多少批次
                int parts = paramListSize / partSize;
                // 分批获取
                for (int i = 0; i <= parts; i++) {
                    int start = i * partSize;
                    if (start > paramListSize - 1) {
                        break;
                    }
                    int end = (i + 1) * partSize - 1;
                    if (end > paramListSize - 1) {
                        end = paramListSize - 1;
                    }

                    List<Map<String, Object>> subList = bdcGzYzQO.getParamList().subList(start, end + 1);
                    bdcGzYzTsxxDTOList.addAll(this.acquireSemaphore(subList, bdcGzZhgzDTO));
                }

                // 去重：避免每个批次之间处理的结果存在重复的
                if (CollectionUtils.isNotEmpty(bdcGzYzTsxxDTOList)) {
                    bdcGzYzTsxxDTOList = bdcGzYzTsxxDTOList.stream().distinct().collect(Collectors.toList());
                }
            }
            //此处保存日志，传入参数为验证参数、未通过的规则验证信息、组合规则DTO（包含组合规则关联的所有的子规则信息）
            asyncUtil.saveBdcGzyzLog(bdcGzYzQO, bdcGzYzTsxxDTOList, bdcGzZhgzDTO);
            return bdcGzYzTsxxDTOList;
        } catch (Exception e) {
            String exception = e.toString();
            asyncUtil.saveGzyzLogException(bdcGzYzQO, bdcGzZhgzDTO,exception);
            throw e;
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param paramMapList 验证查询参数
     * @param bdcGzZhgzDTO 组合规则
     * @return {List} 批量验证提示信息
     * @description 获取信号量凭证进行控流  批量规则验证（传入任意参数）
     */
    public List<BdcGzYzTsxxDTO> acquireSemaphore(List<Map<String, Object>> paramMapList, BdcGzZhgzDTO bdcGzZhgzDTO) {
        boolean hasAcquired = false;
        int size = paramMapList.size() * bdcGzZhgzDTO.getBdcGzZgzDTOList().size();
        try {
            LOGGER.debug("规则验证尝试获取信号量，可用凭证数量{}", semaphore.availablePermits());
            hasAcquired = semaphore.tryAcquire(size, 5, TimeUnit.MINUTES);
            LOGGER.debug("规则验证获取信号量成功，数量 {}，剩余凭证数量{}", size, semaphore.availablePermits());

            if(hasAcquired){
                // 进行业务处理
                return this.listBdcGzYzTsxxOfAnyParam(paramMapList, bdcGzZhgzDTO);
            }
            throw new AppException("规则验证异常，请重试！");
        } catch (InterruptedException e) {
            LOGGER.error("规则验证获取信号量凭证异常， {}", e.getMessage());
            throw new AppException("规则验证异常！");
        } finally {
            if(hasAcquired){
                semaphore.release(size);
            }
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param paramMapList 验证查询参数
     * @param bdcGzZhgzDTO 组合规则
     * @return {List} 批量验证提示信息
     * @description 批量规则验证（传入任意参数）
     */
    public List<BdcGzYzTsxxDTO> listBdcGzYzTsxxOfAnyParam(List<Map<String, Object>> paramMapList, BdcGzZhgzDTO bdcGzZhgzDTO) {
        //1、获取子规则数据流查询结果
        List<BdcGzZgzTsxxDTO> queryResultList = bdcGzZgzCheckService.getZgzSjlResult(bdcGzZhgzDTO.getBdcGzZgzDTOList(), paramMapList);
        if(CollectionUtils.isEmpty(queryResultList)){
            return Collections.emptyList();
        }

        //2、筛选出动态代码执行结果
        List<BdcGzZgzTsxxDTO> javaCodeResult = queryResultList.stream().filter(item -> CollectionUtils.isNotEmpty(item.getTsxx())).collect(Collectors.toList());

        //3、执行规则表达式验证
        List<BdcGzZgzTsxxDTO> checkExpResult = this.executeCheckExpression(bdcGzZhgzDTO, queryResultList);
        if(CollectionUtils.isEmpty(checkExpResult)){
            checkExpResult = new ArrayList<>(paramMapList.size());
        }

        //4、合并两种方式验证结果集合
        List<BdcGzZgzTsxxDTO> checkResult = checkExpResult;
        if(CollectionUtils.isNotEmpty(javaCodeResult)){
            checkResult.addAll(javaCodeResult);
        }

        // 处理返回提示信息
        return this.resolveReturnTipInfo(bdcGzZhgzDTO, checkResult);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZhgzDTO 组合规则
     * @param queryResultList 数据流查询结果
     * @return {List} 验证提示信息
     * @description  执行规则表达式验证
     */
    private List<BdcGzZgzTsxxDTO> executeCheckExpression(BdcGzZhgzDTO bdcGzZhgzDTO, List<BdcGzZgzTsxxDTO> queryResultList){
        // 获取子规则对应的图形校验表达式转换结果
        List<BdcGzBdsTsxxDTO> zgzBdsList = bdcGzZgzCheckService.getZgzBdsDrools(bdcGzZhgzDTO.getBdcGzZgzDTOList());
        if(CollectionUtils.isEmpty(zgzBdsList)){
            return Collections.emptyList();
        }

        try {
            // 按照规则ID分组，每组内容为对应规则的数据流查询结果
            Map<String, List<BdcGzZgzTsxxDTO>>  queryResultMap = queryResultList.stream().collect(Collectors.groupingBy(BdcGzZgzTsxxDTO::getGzid));

            // 每个验证表达式进行批量验证
            List<BdcGzZgzTsxxDTO> result = new ArrayList<>(queryResultList.size());
            for(BdcGzBdsTsxxDTO checkExp : zgzBdsList){
                // 获取验证表达式所在子规则的数据流查询结果
                List<BdcGzZgzTsxxDTO> list = queryResultMap.get(checkExp.getGzid());
                // 验证表达式进行批量验证
                result.addAll(checkExp.execute(list));
            }
            return result;
        } catch (Exception e) {
            LOGGER.error("组合规则：{}，验证异常，{}！", bdcGzZhgzDTO.getZhid(), e);
            throw new AppException(ErrorCode.RULE_EXP_EXEC_ERROR, "组合规则" + bdcGzZhgzDTO.getZhid()  + "验证异常!");
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZhgzDTO 组合规则
     * @param zgzTsxxList 子规则验证结果
     * @return {List} 验证结果
     * @description 处理组合规则对应的验证返回结果
     */
    private List<BdcGzYzTsxxDTO> resolveReturnTipInfo(BdcGzZhgzDTO bdcGzZhgzDTO, List<BdcGzZgzTsxxDTO> zgzTsxxList) {
        // 1、元素提示信息内容进行参数变量替换 2、去重并按照参数分组规则验证结果
        Map<Map, List<BdcGzZgzTsxxDTO>> paramZgzMap = zgzTsxxList.stream().peek(item->{
            if(CollectionUtils.isNotEmpty(item.getTsxx())){
                List<String> tsxxList = item.getTsxx();
                List<String> list = new ArrayList<>(tsxxList.size());
                for(String tsxx: tsxxList){
                    if(StringUtils.isNotBlank(tsxx)){
                        list.add(DataUtil.resolveTipInfo(tsxx, item.getSjljg(), item.getParam()));
                    }
                }
                item.setTsxx(list);
            }
        }).distinct().collect(Collectors.groupingBy(BdcGzZgzTsxxDTO::getParam));

        if(MapUtils.isEmpty(paramZgzMap)){
            return Collections.emptyList();
        }

        // 整理返回结果数据，按照 参数-组合规则-多个子规则提示信息 结构
        List<BdcGzYzTsxxDTO> result = new ArrayList<>();
        for(Map.Entry entry : paramZgzMap.entrySet()){
            result.add(new BdcGzYzTsxxDTO.Builder().addBdcGzZhgzDTO(bdcGzZhgzDTO).addResultEntry(entry).build());
        }
        return result;
    }
}