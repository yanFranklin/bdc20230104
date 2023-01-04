package cn.gtmap.realestate.engine.service.impl.expression;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzBdsTsxxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.engine.core.bo.BdcGzBdsYsBO;
import cn.gtmap.realestate.engine.core.bo.BdcGzBdsYszcBO;
import cn.gtmap.realestate.engine.service.BdcGzBdsService;
import cn.gtmap.realestate.engine.service.BdcGzSjlYsService;
import cn.gtmap.realestate.engine.util.Constants;
import cn.gtmap.realestate.engine.util.KnowledgeBaseUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.definition.KiePackage;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/8
 * @description 不动产规则表达式逻辑处理类
 */
@Service
public class  BdcGzBdsServiceImpl implements BdcGzBdsService{
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGzBdsServiceImpl.class);
    /**
     * 当前限定类名
     */
    private static final String CLASS_NAME = BdcGzBdsServiceImpl.class.getName();
    /**
     * 验证表达式元素处理工厂
     */
    @Autowired
    private BdcGzBdsYsFactory bdcGzBdsYsFactory;
    /**
     * KieBases容器
     */
    private KieContainer kContainer = null;
    /**
     * Redis操作
     */
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 缓存每个规则验证表达式对应规则kbase
     */
    private Map<String, KieBase> kbaseMap = new ConcurrentHashMap<>(100);


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 初始化执行规则表达式编译，避免后续重复编译
     */
    @PostConstruct
    public void init(){
        KnowledgeBaseUtil.createKnowledgeBase("rules/ruleExpression.drl");
        KieServices ks = KieServices.Factory.get();
        kContainer = ks.getKieClasspathContainer();

        // 清空Redis中规则表达式缓存内容，减少无效数据
        redisUtils.deleteKey(CommonConstantUtils.REDIS_RULE_EXPRESSION);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzBdsTsxxDO  表达式、提示信息
     * @param zgzSjlResult 数据流执行结果
     * @return {String} 规则校验内容
     * @description  生成进行规则引擎校验的DRL内容
     */
    @Override
    public <T> String getDroolsDRL(BdcGzBdsTsxxDO bdcGzBdsTsxxDO, Map<String, T> zgzSjlResult) {
        String droolsExpression = redisUtils.getHashValue(CommonConstantUtils.REDIS_RULE_EXPRESSION, bdcGzBdsTsxxDO.getBdsid());

        if(StringUtils.isBlank(droolsExpression) || "null".equals(droolsExpression)){
            // 转换子规则配置的结果校验表达式为对应的Drools表达式
            droolsExpression = this.resolveDroolsExpression(bdcGzBdsTsxxDO.getGzbds(), zgzSjlResult);

            if(StringUtils.isBlank(droolsExpression)){
                return null;
            }
            LOGGER.debug("{}：规则子系统规则校验——转换后规则表达式为：\n {}" , CLASS_NAME, droolsExpression);

            // 缓存至redis（无需设置超时时间），需要注意在规则配置时及时更新内容
            redisUtils.addHashValue(CommonConstantUtils.REDIS_RULE_EXPRESSION, bdcGzBdsTsxxDO.getBdsid(), droolsExpression);
        }

        // 拼接生成 Drools DRL规则内容
        return MessageFormat.format(Constants.RULE_EXPRESSION, droolsExpression, Constants.RULE_RESULT, bdcGzBdsTsxxDO.getTsxx());
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param drool 验证表达式转换为drools的表达式
     * @param bdsid 验证表达式ID
     * @return {InternalKnowledgeBase} Drools验证
     * @description 获取每个规则验证表达式对应的kbase对象
     */
    @Override
    public KieBase getDroolsKbase(String drool, String bdsid) {
        if(!kbaseMap.isEmpty() && (kbaseMap.size() > Constants.KIE_MAP_MAX_SIZE)){
            LOGGER.warn("规则kbase缓存Map已达上限，重新初始化！");
            kbaseMap = new ConcurrentHashMap<>(100);
        }

        if(kbaseMap.isEmpty() || !kbaseMap.containsKey(bdsid)){
            if(StringUtils.isBlank(drool)){
                return null;
            }

            InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
            KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
            try {
                kbuilder.add(ResourceFactory.newByteArrayResource(drool.getBytes("utf-8")), ResourceType.DRL);
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("规则验证：规则表达式加载出错，表达式ID {}", bdsid);
                throw new AppException(ErrorCode.RULE_CHECK_EXP_ERROR, "规则表达式（" + bdsid + "）加载出错");
            }

            Collection<KiePackage> pkgs = kbuilder.getKnowledgePackages();
            kbase.addPackages(pkgs);

            if(kbaseMap.isEmpty()){
                kbaseMap = new ConcurrentHashMap<>(100);
            }
            kbaseMap.put(bdsid, kbase);
            LOGGER.debug("规则kbase缓存Map大小：{}", kbaseMap.size());
            return kbase;
        }
        return kbaseMap.get(bdsid);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzbds 规则表达式
     * @param zgzSjlResult 数据流结果集
     * @return {String} Drools表达式
     * @description 将数据流结果判断表达式解析成对应的Drools引擎表达式
     */
    @Override
    public <T> String resolveDroolsExpression(String gzbds, Map<String, T> zgzSjlResult) {
        if(StringUtils.isBlank(gzbds)){
            return null;
        }

        String[] expressionArray = gzbds.split("\\s+");

        // 1、获取操作符元素簇集合
        List<BdcGzBdsYsBO> itemsList = this.getOperatorsExpList(expressionArray, zgzSjlResult);
        // 2、合成drools表达式
        return this.generateDroolsExpression(expressionArray, itemsList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param expArray 数据流校验表达式数组
     * @param zgzSjlResult 数据流结果集
     * @return  {List} 操作符元素簇集合
     * @description  获取数据流校验表达式中操作符元素簇集合（调用 Drools引擎文件 ruleExpression.drl进行处理）
     */
    private <T> List<BdcGzBdsYsBO> getOperatorsExpList(String[] expArray, Map<String, T> zgzSjlResult){
        List<BdcGzBdsYsBO> operatorsExpList = new LinkedList<>();

        KieSession kSession = kContainer.newKieSession("ksession-rules");
        try {
            // 调用规则引擎分析配置的校验表达式
            for (int i = 1; i < expArray.length; i++) {
                if(!StringToolUtils.existItemEquals(expArray[i], Constants.OPERATORS)){
                    continue;
                }

                // 操作符元素簇信息
                BdcGzBdsYsBO bdcGzBdsYsBO = this.getBdcGzBdcYsBO(expArray, zgzSjlResult, i);
                kSession.insert(bdcGzBdsYsBO);
                kSession.fireAllRules();

                if (StringUtils.isNotBlank(bdcGzBdsYsBO.getExpression())) {
                    operatorsExpList.add(bdcGzBdsYsBO);
                }
            }
        }catch (Exception e){
            LOGGER.error("{}：规则子系统规则校验：处理规则表达式发生错误，{}", CLASS_NAME, e.getMessage());
            throw new AppException("规则子系统规则校验：处理规则表达式时发生错误");
        } finally {
            if(null != kSession){
                kSession.dispose();
                kSession = null;
            }
        }

        return operatorsExpList;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param expArray 数据流校验表达式数组
     * @param zgzSjlResult 数据流结果集合
     * @param index 元素顺序
     * @return  {BdcGzBdsYsBO}  元素BO对象
     * @description  获取操作符元素簇相关信息
     */
    private <T> BdcGzBdsYsBO getBdcGzBdcYsBO(String[] expArray, Map<String, T> zgzSjlResult, int index) {
        BdcGzBdsYsBO bdcGzBdsYsBO = new BdcGzBdsYsBO();
        bdcGzBdsYsBO.setCurOperator(expArray[index]);
        bdcGzBdsYsBO.setBdcGzBdsYszcBO(new BdcGzBdsYszcBO());

        if(index - 1 >= 0) {
            bdcGzBdsYsBO.setStartIndex(index - 1);
            bdcGzBdsYsBO.setPreElement(expArray[index - 1]);

            BdcGzSjlYsService bdcGzSjlYsService = bdcGzBdsYsFactory.getProcessor(expArray[index - 1]);
            bdcGzSjlYsService.setPreElementItems(bdcGzBdsYsBO);
            bdcGzSjlYsService.setPreElementType(bdcGzBdsYsBO);

            if(MapUtils.isEmpty(zgzSjlResult)){
                bdcGzBdsYsBO.setPreElementObj(null);
            } else {
                bdcGzSjlYsService.setPreElementValObj(bdcGzBdsYsBO, zgzSjlResult);
            }
        }

        if(index + 1 < expArray.length){
            bdcGzBdsYsBO.setNextElement(expArray[index + 1]);
        }

        return bdcGzBdsYsBO;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param expArray 数据流校验表达式数组
     * @param itemsList 操作符元素簇集合
     * @return  {String} Drools表达式
     * @description  生成规则条件对应的 Drools表达式
     */
    private String generateDroolsExpression(String[] expArray, List<BdcGzBdsYsBO> itemsList) {
        StringBuilder builder = new StringBuilder();
        int index = 0;
        while(index < expArray.length){
            boolean isOperatorItem = false;

            // 判断元素是否在操作符元素簇集合中，存在则从中取
            for(BdcGzBdsYsBO bdcGzBdsYsBO : itemsList){
                if(index == bdcGzBdsYsBO.getStartIndex()) {
                    isOperatorItem = true;
                    builder.append(bdcGzBdsYsBO.getExpression()).append(" ");
                    index += bdcGzBdsYsBO.getLength();
                    break;
                }
            }

            // 元素不在操作符元素簇集合中，则直接从数据流结果校验表达式中取
            if(!isOperatorItem){
                builder.append(expArray[index]).append(" ");
                index += 1;
            }
        }
        return builder.toString().replaceAll("并且", " && ").replaceAll("或", " || ");
    }
}
