package cn.gtmap.realestate.exchange.service.impl.inf.sjpt;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.exchange.sjpt.BdcExchangeZddz;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcQlxxRequestDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeYg;
import cn.gtmap.realestate.exchange.core.domain.sjpt.PeCxDO;
import cn.gtmap.realestate.exchange.core.dto.sjpt.SjptCxjgThreadDTO;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.PeCommitCxsqjg;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.PeCommitCxsqjgWithEmptyString;
import cn.gtmap.realestate.exchange.core.mapper.sjpt.GxCxqqMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.sjpt.thread.QueryCxjgThread;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.sjpt.QueryCxjgService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import cn.gtmap.realestate.exchange.util.constants.SjptConstants;
import cn.gtmap.realestate.exchange.util.enums.SjptCxqlEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-26
 * @description
 */
public abstract class QueryCxjgAbstractServiceImpl implements QueryCxjgService {

    @Autowired
    protected EntityMapper sjptEntityMapper;

    @Autowired
    protected EntityMapper entityMapper;

    @Autowired
    private GxCxqqMapper gxCxqqMapper;

    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;

    @Resource(name = "exchangeDozerMapper")
    protected DozerBeanMapper dozerBeanMapper;

    @Autowired
    protected CommonService commonService;

    @Autowired
    protected BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    protected BdcXmFeignService bdcXmFeignService;

    protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * 单个权利人有大批量产权数据是否启用批量处理逻辑（例如开发商有2万套房子）
     */
    @Value("${sjpt.sjplcl:false}")
    protected boolean sjplcl;

    /**
     * 省级查询-实时查询不动产权证号分隔符号
     * 例如：{"BdcFdcqDO":"、","BdcGjzwsyqDO":"、","BdcHysyqDO":"、","BdcJsydsyqDO":"、","BdcLqDO":"、","BdcTdcbnydsyqDO":"、","BdcTdsyqDO":"、","BdcDyaqDO":"、","BdcYgDO":"、"}
     */
    @Value("#{${sjcx.sscx.bdcqzh.fgfh:null}}")
    private Map<String, String> sscxBdcqzhFgfh = new HashMap<>();

    /**
     * 省级查询-批量查询不动产权证号分隔符号
     * 例如：{"BdcFdcqDO":"、","BdcGjzwsyqDO":"、","BdcHysyqDO":"、","BdcJsydsyqDO":"、","BdcLqDO":"、","BdcTdcbnydsyqDO":"、","BdcTdsyqDO":"、","BdcDyaqDO":"、","BdcYgDO":"、"}
     */
    @Value("#{${sjcx.plcx.bdcqzh.fgfh:null}}")
    private Map<String, String> plcxBdcqzhFgfh = new HashMap<>();

    /**
     * 省厅查询结果数据处理共享线程池定义
     */
    private ExecutorService executor = new ThreadPoolExecutor(
            // 核心线程数量
            20,
            // 最大线程数
            50,
            // 超时300秒
            300, TimeUnit.SECONDS,
            // 最大允许等待500个任务
            new ArrayBlockingQueue<>(500),
            // 过多任务直接主线程处理
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param cxsqdh 查询申请单号
     * @param wsbh 文书编号
     * @return
     * @description 查询区县库权利数据
     */
    @Override
    public List queryCxjg(String cxsqdh, String wsbh) {
        Example example = new Example(this.getGxQllxClass());
        example.createCriteria().andEqualTo("cxsqdh", cxsqdh).andEqualTo("wsbh",wsbh);
        List gxPeList = sjptEntityMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(gxPeList)) {
            return new ArrayList();
        }
        return gxPeList;
    }

    /**
     * @param cxsqdh 查询申请单号
     * @param wsbh   文书编号
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 删除区县库权利数据
     */
    @Override
    public void deleteCxjg(String cxsqdh,String wsbh) {
        if(StringUtils.isNotBlank(cxsqdh)){
            Example example = new Example(this.getGxQllxClass());
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("cxsqdh", cxsqdh);
            if(StringUtils.isNotBlank(wsbh)){
                criteria.andEqualTo("wsbh", wsbh);
            }
            sjptEntityMapper.deleteByExampleNotNull(example);
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param peCxDO
     * @return java.util.List<java.lang.Integer>
     * @description 根据CXFW 判断 需要查询的权属状态
     */
    private String[] getQsztByCxfw(PeCxDO peCxDO){
        String[] qsztArray = new String[]{CommonConstantUtils.QSZT_VALID.toString()};
        if(SjptConstants.CXFW_XSANDLS.equals(peCxDO.getCxfw())){
            qsztArray = new String[]{CommonConstantUtils.QSZT_HISTORY.toString(),
                    CommonConstantUtils.QSZT_VALID.toString()};
        }
        return qsztArray;
    }

    /**
     * @param peCxDO         查询参数对象
     * @param peCommitCxsqjg 查询结果对象
     * @param type 区分是实时查询还是批量查询（sscx 实时查询  plcx 批量查询）
     * @return PeCommitCxsqjg
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 执行查询登记数据
     */
    @Override
    public PeCommitCxsqjg cxsj(PeCxDO peCxDO, PeCommitCxsqjg peCommitCxsqjg, String type) throws InstantiationException, IllegalAccessException {
        String className = getGxQllxClass().getSimpleName();
        // 根据 人信息 查权利
        BdcQlxxRequestDTO bdcQlxxRequestDTO = new BdcQlxxRequestDTO();
        bdcQlxxRequestDTO.setQllxs(getQllxDm());
        bdcQlxxRequestDTO.setQlrlb(getQlrlb());
        bdcQlxxRequestDTO.setQlrmc(peCxDO.getQlrmc());
        bdcQlxxRequestDTO.setQlrzjh(peCxDO.getQlrzjhArr());
        bdcQlxxRequestDTO.setQszts(getQsztByCxfw(peCxDO));
        bdcQlxxRequestDTO.setBdcdyh(peCxDO.getBdcdyh());
        bdcQlxxRequestDTO.setBdcqzh(peCxDO.getBdcqzh());
        LOGGER.info("省厅查询：查询产权开始{}查询申请单号{}", className, peCxDO.getCxsqdh());
        List<BdcQl> bdcQlList = bdcQllxFeignService.getBdcQlxxByQlr(bdcQlxxRequestDTO);
        List gxQlList = fillCxsj(peCxDO,bdcQlList, type);
        if(CollectionUtils.isNotEmpty(gxQlList)){
            this.setQlList(peCommitCxsqjg,gxQlList);
        }
        LOGGER.info("省厅查询：查询产权结束{},查询权利数量{}，查询申请单号{}", className, CollectionUtils.size(gxQlList), peCxDO.getCxsqdh());
        return peCommitCxsqjg;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param peCxDO
     * @param bdcQlList
     * @param type 区分是实时查询还是批量查询（sscx 实时查询  plcx 批量查询）
     * @return java.util.List
     * @description 填充 查询结果 （项目、共有人、限制权利）
     */
    protected List fillCxsj(PeCxDO peCxDO,List<BdcQl> bdcQlList, String type) throws IllegalAccessException, InstantiationException {
        if(CollectionUtils.size(bdcQlList) >= SjptConstants.SJPT_CXJG_MAX_NUM && sjplcl) {
            LOGGER.info("省厅查询填充查询结果采用批量处理方案，对应查询申请单号：{}", peCxDO.getCxsqdh());
            return fillCxsjPl(peCxDO, bdcQlList, type);
        }

        List gxQlList = new ArrayList();
        if(CollectionUtils.isNotEmpty(bdcQlList)) {
            //去重
            bdcQlList = bdcQlList.stream().distinct().collect(Collectors.toList());
            LOGGER.warn("省厅查询去重后权利数据{}，对应查询申请单号：{}", JSON.toJSONString(bdcQlList), peCxDO.getCxsqdh());
            for (BdcQl bdcQl : bdcQlList) {
                if (null == bdcQl || StringUtils.isBlank(bdcQl.getXmid())) {
                    continue;
                }
                // 接口强转为 BDCQL
                Object ql = getBdcQllxClass().cast(bdcQl);
                // 实例化 GXQL 实体
                Object gxQl = getGxQllxClass().newInstance();
                BeanUtils.copyProperties(peCxDO, gxQl);
                // 1、 权利信息做对照
                dozerBeanMapper.map(ql,gxQl,SjptConstants.DOZER_MAPPING_ID_QLPREFIX + getGxQllxClass().getSimpleName());

                // 2、判断 当前权利是否需要 查共有人
                if(checkNeedGyr()){
                    // 填充 共有属性
                    fillGyr(bdcQl.getXmid(),peCxDO,gxQl);
                }
                // 3、 填充 项目信息
                BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(bdcQl.getXmid());
                if(bdcXmDO != null){
                    // 替换不动产权证号分隔符
                    String bdcqzhFgfh = getBdcqzhFgfh(type);
                    if(StringUtils.isNotBlank(bdcqzhFgfh)) {
                        bdcXmDO.setBdcqzh(bdcXmDO.getBdcqzh().replaceAll(CommonConstantUtils.ZF_YW_DH, bdcqzhFgfh));
                    }

                    dozerBeanMapper.map(bdcXmDO,gxQl,SjptConstants.DOZER_MAPPING_ID_XMPREFIX + getGxQllxClass().getSimpleName());
                }
                //填充ywr信息
                if (bdcXmDO != null){
                    fillywr(bdcXmDO.getXmid(),gxQl);
                }

                // 4、 判断 当前权利是否需要 查限制权利
                if(checkNeedXzql() && bdcXmDO != null && StringUtils.isNotBlank(bdcXmDO.getBdcdyh())){
                    // 填充 是否抵押 是否查封
                    fillXzql(bdcXmDO.getBdcdyh(),gxQl);
                }
                if(CheckParameter.checkAnyParameter(gxQl)){
                    // 转换字典项
                    gxQlList.add(gxQlZddz(gxQl));
                }
            }
        }
        return gxQlList;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param peCxDO 查询参数
     * @param bdcQlList 权利信息
     * @param type 区分是实时查询还是批量查询（sscx 实时查询  plcx 批量查询）
     * @return java.util.List 返回省厅权利信息
     * @description 批量填充查询结果 （项目、共有人、限制权利）
     */
    protected List fillCxsjPl(PeCxDO peCxDO, List<BdcQl> bdcQlList, String type) {
        if(CollectionUtils.isEmpty(bdcQlList)) {
            return Collections.emptyList();
        }

        // 去重
        bdcQlList = bdcQlList.stream().distinct().collect(Collectors.toList());
        LOGGER.warn("省厅查询：单权利人大批量产权数据处理，去重后权利数据数量{}，对应查询申请单号：{}", CollectionUtils.size(bdcQlList), peCxDO.getCxsqdh());
        List<String> xmidList = bdcQlList.stream().map(BdcQl::getXmid).collect(Collectors.toList());

        // A、------  先批量查询出需要的登记数据 --------
        // 批量查询权利关联的权利人信息
        Map<String, List<BdcQlrDO>> xmidQlrsMap = new HashMap<>();
        if (checkNeedGyr()) {
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlrByXmidList(xmidList, getQlrlb());
            LOGGER.info("省厅查询：单权利人大批量产权数据共有人处理,查询权利人共{}条记录，对应查询申请单号：{}", CollectionUtils.size(bdcQlrDOList), peCxDO.getCxsqdh());
            if(CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                xmidQlrsMap = bdcQlrDOList.stream().collect(Collectors.groupingBy(BdcQlrDO::getXmid));
            }
        }

        // 如果是预告批量查询义务人
        Map<String, List<BdcQlrDO>> xmidYwrsMap = new HashMap<>();
        if(StringUtils.equals(getQllxEnum().getGxQlClass().getSimpleName(), GxPeYg.class.getSimpleName())) {
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlrByXmidList(xmidList, CommonConstantUtils.QLRLB_YWR);
            LOGGER.info("省厅查询：单权利人大批量产权数据查询义务人,查询义务人共{}条记录，对应查询申请单号：{}", CollectionUtils.size(bdcQlrDOList), peCxDO.getCxsqdh());
            if(CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                xmidYwrsMap = bdcQlrDOList.stream().collect(Collectors.groupingBy(BdcQlrDO::getXmid));
            }
        }

        // 批量查询项目信息
        Map<String, List<BdcXmDO>> xmidXmMap = new HashMap<>();
        List<BdcXmDO> xmDOList = commonService.listBdcXmByXmids(xmidList);
        LOGGER.info("省厅查询：单权利人大批量产权数据处理,查询项目共{}条记录，对应查询申请单号：{}", CollectionUtils.size(xmDOList), peCxDO.getCxsqdh());
        if(CollectionUtils.isNotEmpty(xmDOList)) {
            xmidXmMap = xmDOList.stream().collect(Collectors.groupingBy(BdcXmDO::getXmid));
        }

        // 批量查询查封、抵押信息
        Map<String, List<BdcCfDO>> bdcdyhCfMap = new HashMap<>();
        Map<String, List<BdcDyaqDO>> bdcdyhDyaMap = new HashMap<>();
        if (checkNeedXzql()) {
            List<String> bdcdyhList = xmDOList.stream().map(BdcXmDO::getBdcdyh).collect(Collectors.toList());

            List<BdcCfDO> bdcCfDOList = commonService.listQlByBdcdyhs(bdcdyhList, new BdcCfDO());
            LOGGER.info("省厅查询：单权利人大批量产权数据处理,查询查封共{}条记录，对应查询申请单号：{}", CollectionUtils.size(bdcCfDOList), peCxDO.getCxsqdh());
            if (CollectionUtils.isNotEmpty(bdcCfDOList)) {
                bdcdyhCfMap = bdcCfDOList.stream().collect(Collectors.groupingBy(BdcCfDO::getBdcdyh));
            }

            List<BdcDyaqDO> bdcDyaDOList = commonService.listQlByBdcdyhs(bdcdyhList, new BdcDyaqDO());
            LOGGER.info("省厅查询：单权利人大批量产权数据处理,查询抵押共{}条记录，对应查询申请单号：{}", CollectionUtils.size(bdcDyaDOList), peCxDO.getCxsqdh());
            if(CollectionUtils.isNotEmpty(bdcDyaDOList)) {
                bdcdyhDyaMap = bdcDyaDOList.stream().collect(Collectors.groupingBy(BdcDyaqDO::getBdcdyh));
            }
        }

        // 线程池批量转换数据实体
        List gxQlList = new ArrayList<>();
        List<Future<Object>> tasksList = new ArrayList<>(bdcQlList.size());
        for(BdcQl bdcQl : bdcQlList) {
            SjptCxjgThreadDTO param = new SjptCxjgThreadDTO();
            param.setBdcQl(bdcQl);
            param.setPeCxDO(peCxDO);
            param.setBdcQlClass(getBdcQllxClass());
            param.setBdcGxQlClass(getGxQllxClass());
            param.setDozerBeanMapper(dozerBeanMapper);
            param.setEntityMapper(entityMapper);
            param.setNeedGyr(checkNeedGyr());
            param.setNeedXzql(checkNeedXzql());
            param.setXmidQlrsMap(xmidQlrsMap);
            param.setXmidYwrsMap(xmidYwrsMap);
            param.setXmidXmMap(xmidXmMap);
            param.setBdcdyhCfMap(bdcdyhCfMap);
            param.setBdcdyhDyaMap(bdcdyhDyaMap);
            param.setBdcqzhFgfh(getBdcqzhFgfh(type));

            QueryCxjgThread queryCxjgThread = new QueryCxjgThread(param);
            tasksList.add(executor.submit(queryCxjgThread));
        }

        try {
            for (Future<Object> task : tasksList) {
                gxQlList.add(task.get());
            }
            return gxQlList;
        } catch (Exception e) {
            LOGGER.error("省厅查询处理失败", e);
            throw new AppException("省厅查询处理失败");
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param gxQl
     * @return java.lang.Object
     * @description 处理权利中的 字典项
     */
    public Object gxQlZddz(Object gxQl){
        if(gxQl != null){
            JSONObject tempJson = JSONObject.parseObject(JSONObject.toJSONString(gxQl));
            if(tempJson != null){
                for(String key : tempJson.keySet()){
                    try{
                        String value = tempJson.getString(key);
                        BdcExchangeZddz bdcExchangeZddz = getBdcExchangeZd(key,value);
                        if(bdcExchangeZddz != null){
                            tempJson.put(key,bdcExchangeZddz.getStddm());
                        }
                    }catch (Exception e){

                    }
                }
                return JSONObject.toJavaObject(tempJson,gxQl.getClass());
            }
        }
        return gxQl;
    }


    /**
     * @param key
     * @param value
     * @return cn.gtmap.realestate.common.core.domain.exchange.sjpt.BdcExchangeZddz
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    private BdcExchangeZddz getBdcExchangeZd(String key,String value){
        Example example = new Example(BdcExchangeZddz.class);
        example.createCriteria().andEqualTo("bdcdjdm",value)
                .andEqualTo("zdlx",key);
        List<BdcExchangeZddz> bdcExchangeZddzList = entityMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(bdcExchangeZddzList)){
            return bdcExchangeZddzList.get(0);
        }else{
            return null;
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @param gxQl
     * @return void
     * @description 填充限制权利
     */
    protected void fillXzql(String bdcdyh,Object gxQl){
        List<BdcQl> cfList = commonService.listXsQlByBdcdyh(bdcdyh,CommonConstantUtils.QLLX_CF.toString());
        executeFillCfDya(cfList, gxQl, "sfcf");

        List<BdcQl> dyaList = commonService.listXsQlByBdcdyh(bdcdyh,CommonConstantUtils.QLLX_DYAQ_DM.toString());
        executeFillCfDya(dyaList, gxQl, "sfdy");
    }

    /**
     * 执行填充限制权利（查封、抵押）数据
     * @param qlList 限制权利信息
     * @param gxQl 目标实体
     * @param cfdybs 查封、抵押标识
     */
    public static void executeFillCfDya(List qlList,Object gxQl, String cfdybs) {
        if(CollectionUtils.isNotEmpty(qlList)){
            CommonUtil.setFieldValue(gxQl,cfdybs, CommonConstantUtils.SF_S_DM.toString());
        }else{
            CommonUtil.setFieldValue(gxQl,cfdybs, CommonConstantUtils.SF_F_DM.toString());
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param xmid
     * @param peCxDO
     * @return java.lang.String
     * @description 填充项目共有人
     */
    protected void fillGyr(String xmid,PeCxDO peCxDO,Object gxQl) {
        List<BdcQlrDO> bdcQlrList = commonService.listBdcQlrByXmid(xmid,getQlrlb());
        if(CollectionUtils.isNotEmpty(bdcQlrList)){
            this.executeFillGyr(peCxDO, gxQl, bdcQlrList);
        }
    }

    /**
     * 执行填充共有人
     * @param peCxDO 查询参数
     * @param gxQl 目标实体
     * @param bdcQlrList 权利人信息
     */
    public static void executeFillGyr(PeCxDO peCxDO, Object gxQl, List<BdcQlrDO> bdcQlrList) {
        String gyqk = bdcQlrList.get(0).getGyqk();
        Integer gyfs = bdcQlrList.get(0).getGyfs();
        if (bdcQlrList.size() > 1) {
            StringBuilder gyrSb = new StringBuilder("");
            for (BdcQlrDO bdcQlrDO : bdcQlrList) {
                if (!StringUtils.equals(bdcQlrDO.getZjh(), peCxDO.getQlrzjh())) {
                    gyrSb.append(bdcQlrDO.getQlrmc()).append(",").
                            append(bdcQlrDO.getZjh()).append(";");
                }
            }
            String gyr = gyrSb.toString();
            if (gyr.endsWith(";")) {
                gyr = gyr.substring(0, gyr.length() - 1);
            }
            // 保存共有人
            CommonUtil.setFieldValue(gxQl, "gyr", gyr);
        }
        // 保存共有情况
        CommonUtil.setFieldValue(gxQl, "gyqk", gyqk);
        // 保存共有情况
        CommonUtil.setFieldValue(gxQl, "gyfs", gyfs != null ? gyfs.toString() : "");
    }

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @param xmid
     * @return java.lang.String
     * @description 填充项目ywr
     */
    private void fillywr(String xmid,Object gxQl) {
        Example example = new Example(BdcQlrDO.class);
        example.createCriteria().andEqualTo("xmid", xmid)
                .andEqualTo("qlrlb", CommonConstantUtils.QLRLB_YWR);
        List<BdcQlrDO> ywrList = entityMapper.selectByExample(example);
        executeFillYwr(gxQl, ywrList);
    }

    /**
     * 执行填充义务人信息
     * @param gxQl 目标实体
     * @param ywrList 义务人信息
     */
    public static void executeFillYwr(Object gxQl, List<BdcQlrDO> ywrList) {
        if(CollectionUtils.isNotEmpty(ywrList) && gxQl instanceof GxPeYg){
            String ywr = ywrList.get(0).getQlrmc();
            Integer ywrzjzl = ywrList.get(0).getZjzl();
            String ywrzjh = ywrList.get(0).getZjh();

            //保存ywr
            CommonUtil.setFieldValue(gxQl,"ywr",ywr);
            //保存ywr证件种类
            CommonUtil.setFieldValue(gxQl,"ywrzjzl",ywrzjzl!=null?ywrzjzl.toString():"");
            //保存ywr证件号
            CommonUtil.setFieldValue(gxQl,"ywrzjh",ywrzjh);
        }
    }


    /**
     * @param peCommitCxsqjg 查询结果对象
     * @param cxsqdh         查询申请单号
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 保存入库
     */
    @Override
    public void saveCxjg(PeCommitCxsqjg peCommitCxsqjg, String cxsqdh) {
        deleteCxjg(cxsqdh,peCommitCxsqjg.getWsbh());
        List qlList = this.getQlList(peCommitCxsqjg);
        if(CollectionUtils.isNotEmpty(qlList)){
            CommonUtil.insertBatchSelectiveBySplitedList(sjptEntityMapper, qlList);
        }
    }

    abstract public SjptCxqlEnum getQllxEnum();

    @Override
    abstract public void setQlList(PeCommitCxsqjg peCommitCxsqjg,List qlList);

    @Override
    abstract public List getQlList(PeCommitCxsqjg peCommitCxsqjg);

    abstract public String getQlrlb();

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.lang.Class
     * @description 获取 共享权利 类
     */
    protected Class getGxQllxClass(){
        return getQllxEnum().getGxQlClass();
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.lang.String[]
     * @description 获取 不动产权利代码数组
     */
    protected String[] getQllxDm(){
        return getQllxEnum().getQllxArr();
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.lang.Class
     * @description 获取 不动产权利类
     */
    protected Class getBdcQllxClass(){
        return getQllxEnum().getBdcQlClass();
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return boolean
     * @description 判断是否需要共有人
     */
    protected boolean checkNeedGyr(){
        return getQllxEnum().isNeedGyr();
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return boolean
     * @description 判断是否需要限制权利
     */
    protected boolean checkNeedXzql(){
        return getQllxEnum().isNeedXzql();
    }

    protected static List<JSONObject> getDefaultEmptyStringJsonArray(List qlList) {
        if (qlList!=null && qlList.size()>0){
            List<JSONObject> jsonObjects = JSON.parseArray(JSON.toJSONString(qlList), JSONObject.class);
            for (int i = 0; i < jsonObjects.size(); i++) {
                JSONObject jsonObject = jsonObjects.get(i);
                Set<String> keys = jsonObject.keySet();
                keys.forEach(key -> {
                    jsonObject.putIfAbsent(key, "");
                });
            }
            return jsonObjects;
        }
        return new ArrayList<>();
    }

    @Override
    public void setQlListWithDefaultValueEmptyString(PeCommitCxsqjgWithEmptyString peCommitCxsqjg, List qlList) {

    }

    /**
     * 获取不动产权证号分隔符号
     * @param type  type 区分是实时查询还是批量查询（sscx 实时查询  plcx 批量查询）
     * @return 分隔符号
     */
    private String getBdcqzhFgfh(String type) {
        Map<String, String> fgfhMap = Constants.SJCX_SSCX.equals(type) ? sscxBdcqzhFgfh : plcxBdcqzhFgfh;
        if(MapUtils.isNotEmpty(fgfhMap)) {
            String fgfh = fgfhMap.get(this.getBdcQllxClass().getSimpleName());
            return fgfh;
        }
        return null;
    }
}
