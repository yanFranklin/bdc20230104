package cn.gtmap.realestate.init.service.other.impl;

import cn.gtmap.realestate.common.config.accept.QcjdConfig;
import cn.gtmap.realestate.common.config.mq.enums.RabbitMqEnum;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.EntityNotFoundException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.*;
import cn.gtmap.realestate.init.core.thread.DeleteThread;
import cn.gtmap.realestate.init.core.thread.InitZsThread;
import cn.gtmap.realestate.init.service.InitService;
import cn.gtmap.realestate.init.service.other.InitBdcZsService;
import cn.gtmap.realestate.init.service.other.InitDataDealService;
import cn.gtmap.realestate.init.service.rabbitmq.HxQlFjMQSender;
import cn.gtmap.realestate.init.service.tshz.InitBdcZsTsService;
import cn.gtmap.realestate.init.service.zsxx.InitBdcqzAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import cn.gtmap.realestate.init.util.DozerUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/17.
 * @description
 */
@Service
public class InitBdcZsServiceImpl implements InitBdcZsService {

    private static final String BDCQZH = "不动产权证号:";

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 权利其他状况原产权证号多个显示详见抵押物清单
      */
    @Value("${qlqtzk.ycqzh.dgxjdywqd:false}")
    private Boolean dgxjdywqd;

    private static final Logger LOGGER = LoggerFactory.getLogger(InitBdcZsServiceImpl.class);
    @Autowired
    InitBeanFactory initBeanFactory;
    @Autowired
    InitDataDealService initDataDealService;
    @Autowired
    BdcQlrService bdcQlrService;
    @Autowired
    BdcXmService bdcXmService;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    ThreadEngine threadEngine;
    @Autowired
    DozerUtils dozerUtils;
    @Autowired
    BdcBdcdyService bdcBdcdyService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    private BdcQllxService bdcQllxService;
    @Autowired
    private BdcZsService bdcZsService;
    @Autowired
    private HxQlFjMQSender hxQlFjMqService;
    @Autowired
    private BdcXmLsgxService bdcXmLsgxService;
    @Autowired
    private QcjdConfig qcjdConfig;

    /**
     * 初始化不动产证书
     *@param zsyl  是否证书预览
     *@param  zssl 证书数量
     * @param cshFwkgSlList 每个项目的初始化实例
     * @param plybz  是否是批量一本证逻辑，按项目处理的要传,gzlslid的会自己判断
     * @param sjbl 数据补录 （不生成权利其他状况和附记）
     * @return List<BdcZsDO>
     */
    @Override
    public List<BdcZsDO> initBdcZs(List<BdcCshFwkgSlDO> cshFwkgSlList,boolean zsyl,boolean zssl,Boolean plybz,boolean sjbl) throws Exception {
        List<BdcZsDO> zsList;
        if (CollectionUtils.isNotEmpty(cshFwkgSlList)) {
            String xmid=cshFwkgSlList.get(0).getId();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS");
            if(!zsyl){
                LOGGER.info("生成证书开始:{} {}", simpleDateFormat.format(new Date()),xmid);
            }
            //证书和权利数据集合
            Map<String, InitServiceDTO> zsDataMap = new ConcurrentHashMap<>();
            //批量组合业务 挂接证书的项目id和zsxh等数据
            List<BdcCshFwkgSlDO> zhXmGjzsList = new CopyOnWriteArrayList<>();
            //存储zsxh和证书对象的值
            Map<String, List<BdcZsDO>> zsidMap = new ConcurrentHashMap<>();
            //存储zsid和证书模板对象的关系
            Map<String, BdcZsDO> xmidZsmbMap = new ConcurrentHashMap<>();
            //存储项目集合
            List<BdcXmDO> xmList = new ArrayList<>();
            //组合需生成证书的项目
            Map<Integer, String> sczsMap = queryZhzsgx(cshFwkgSlList);
            //判断是否不走多线程计数限制
            boolean sfbjs=cshFwkgSlList.size() > 1 &&  sczsMap.size()!=1 ? false : true;
            //线程池对象
            Map<String,Object> taskMap=new ConcurrentHashMap<>();
            //循环初始化
            List<CommonThread> listThread = new ArrayList();
            //要删除的数据
            List<Object> deleteList= new CopyOnWriteArrayList<>();
            //循环生成证书
            for (BdcCshFwkgSlDO bdcCshFwkgSl : cshFwkgSlList) {
                InitZsThread initZsThread=new InitZsThread(bdcCshFwkgSl,zhXmGjzsList,zsidMap,sczsMap,zsDataMap,initBeanFactory,zssl,zsyl,sjbl,xmidZsmbMap);
                initZsThread.setSfbjs(sfbjs);
                initZsThread.setTaskMap(taskMap);
                listThread.add(initZsThread);
                //存入xmlist做删除
                BdcXmDO bdcXmDO = new BdcXmDO();
                bdcXmDO.setXmid(bdcCshFwkgSl.getId());
                xmList.add(bdcXmDO);
            }
            List<InitService> listInitService = initBeanFactory.getTrafficMode(cshFwkgSlList.get(0), InitBdcqzAbstractService.class);
            //删除证书数据
            if (CollectionUtils.isNotEmpty(listInitService) && !zsyl && !zssl) {
                DeleteThread deleteThread=new DeleteThread(listInitService.get(0),xmList,deleteList);
                deleteThread.setTaskMap(taskMap);
                deleteThread.setSfbjs(sfbjs);
                listThread.add(deleteThread);
            }
            if(CollectionUtils.isNotEmpty(listThread)){
                //多线程处理操作
                threadEngine.excuteThread(listThread, true,taskMap);
                if(taskMap.containsKey("msg")){
                    throw new AppException(taskMap.get("msg")!=null ? taskMap.get("msg").toString() : "生成证书失败!");
                }
            }
            //转换证书服务生成的数据
            InitResultDTO initZsDTO = initDataDealService.dealServiceDTO(zsDataMap);
            //如果是返回证书数量不做以下处理
            if(zssl){
               return initZsDTO.getBdcZsList();
            }
            //存入要删除的数据
            initZsDTO.setDeleteList(deleteList);
            //需要挂接证书id的不为空的话进行处理
            if (CollectionUtils.isNotEmpty(zhXmGjzsList)) {
                //排序
                zhXmGjzsList.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
                //是否为批量一本证
                boolean plYbz=false;
                //判断是否为批量一本证
                if(plybz!=null){
                    plYbz=plybz;
                }else if(zhXmGjzsList.size()+1==cshFwkgSlList.size()){
                    plYbz=true;
                }
                //抵押存储同序号的所有义务人
                Map<Integer,Set<String>> mapYwr=new HashMap<>();
                //已经回写过证书id的权利人数据(不管一个证书对应多个人,这种情况不是分别持证，此集合是分别持证情况用的)
                Map<String,BdcQlrDO> map= new HashMap<>();
                if(CollectionUtils.isNotEmpty(initZsDTO.getBdcQlrList())){
                    for(BdcQlrDO bdcQlrDO:initZsDTO.getBdcQlrList()){
                        //有证书id并且是权利人的
                        if(CommonConstantUtils.QLRLB_QLR.equals(bdcQlrDO.getQlrlb()) && StringUtils.isNotBlank(bdcQlrDO.getZsid())){
                            map.put(bdcQlrDO.getZsid(),bdcQlrDO);
                        }
                    }
                }
                List<CommonThread> updateQlList = new ArrayList<>();
                for (int i=0;i<zhXmGjzsList.size();i++) {
                    BdcCshFwkgSlDO bdcCshFwkgSl=zhXmGjzsList.get(i);
                    List<BdcZsDO> bdcZsDOList = zsidMap.get(bdcCshFwkgSl.getZsxh().toString());
                    if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                        for(int j=0;j<bdcZsDOList.size();j++){
                            BdcZsDO bdcZsDO = bdcZsDOList.get(j);
                            if(bdcZsDO!=null  && StringUtils.isNotBlank(bdcZsDO.getZsid())){
                                //非预览的走权利人zsid和证书项目关系处理逻辑
                                if(!zsyl) {
                                    if(plYbz){
                                        //只需处理一次
                                        if(i==0){
                                            BdcXmDO bdcXmDO=bdcXmService.queryBdcXmByPrimaryKey(bdcCshFwkgSl.getId());
                                            if(bdcXmDO!=null){
                                                if(bdcZsDOList.size()>1){
                                                    //分别持证才处理
                                                    if(map.get(bdcZsDO.getZsid())!=null){
                                                        bdcQlrService.updateQlrZsid(bdcXmDO.getGzlslid(),bdcZsDO.getZsid(),map.get(bdcZsDO.getZsid()).getQlrmc(),map.get(bdcZsDO.getZsid()).getZjh());
                                                    }
                                                }else{
                                                    bdcQlrService.updateQlrZsid(bdcXmDO.getGzlslid(),bdcZsDO.getZsid(),null,null);
                                                }
                                            }
                                        }
                                    }else{
                                        //处理权利人关联数据
                                        BdcQlrDO bdcQlrDO = new BdcQlrDO();
                                        bdcQlrDO.setXmid(bdcCshFwkgSl.getId());
                                        //分别持证才处理
                                        if(bdcZsDOList.size()>1 && map.get(bdcZsDO.getZsid())!=null){
                                            bdcQlrDO.setQlrmc(map.get(bdcZsDO.getZsid()).getQlrmc());
                                            bdcQlrDO.setZjh(map.get(bdcZsDO.getZsid()).getZjh());
                                        }
                                        bdcQlrDO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                                        List<BdcQlrDO> list = bdcQlrService.queryBdcQlrByQlrxx(bdcQlrDO, null);
                                        if (CollectionUtils.isNotEmpty(list)) {
                                            for (BdcQlrDO qlrDO : list) {
                                                qlrDO.setZsid(bdcZsDO.getZsid());
                                                initZsDTO.getBdcQlrList().add(qlrDO);
                                            }
                                        }
                                    }
                                    //处理发一本证的其他证书项目关系
                                    dealXmZsgx(bdcZsDO.getZsid(),bdcCshFwkgSl.getId(),initZsDTO);
                                    //权利不初始化附记并且要回写的做处理  批量生成一本证处理非主房权利的附记
                                    // 分别持证只回写一次
                                    if(j==0 && !initBeanFactory.isInitFj() && initBeanFactory.isHxFj()){
                                        // 更新权利附记放入队列
                                        Map<String,Object> message=new HashMap();
                                        message.put("zsfj",bdcZsDO.getFj());
                                        message.put("xmid",bdcCshFwkgSl.getId());
                                        hxQlFjMqService.send(RabbitMqEnum.Exchange.CONTRACT_DIRECT.getCode(), RabbitMqEnum.QueueEnum.HXQLFJQUEUE.getCode(), JSONObject.toJSONString(message));
                                    }
                                }
                                //批量抵押发一本证的处理义务人
                                if(CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcCshFwkgSl.getQllx())){
                                    //批量发一本证的单独处理
                                    if(plYbz){
                                        //只需处理一次
                                        if(i==0){
                                            BdcXmDO bdcXmDO=bdcXmService.queryBdcXmByPrimaryKey(bdcCshFwkgSl.getId());
                                            if(bdcXmDO!=null){
                                                bdcZsDO.setYwr(bdcQlrService.queryQlrsYbzs(bdcXmDO.getGzlslid(),CommonConstantUtils.QLRLB_YWR,null));
                                            }
                                        }
                                    }else{
                                        List<BdcQlrDO> listYwr=bdcQlrService.listBdcQlrOrderBySxh(bdcCshFwkgSl.getId(),CommonConstantUtils.QLRLB_YWR);
                                        if(CollectionUtils.isNotEmpty(listYwr)){
                                            for(BdcQlrDO ywr:listYwr){
                                                Set<String> ywrSet;
                                                if(mapYwr.get(bdcCshFwkgSl.getZsxh())!=null){
                                                    ywrSet=mapYwr.get(bdcCshFwkgSl.getZsxh());
                                                }else{
                                                    ywrSet=new LinkedHashSet<>();
                                                    mapYwr.put(bdcCshFwkgSl.getZsxh(),ywrSet);
                                                }
                                                if(StringUtils.isNotBlank(ywr.getQlrmc())){
                                                    ywrSet.add(ywr.getQlrmc());
                                                }
                                            }
                                        }
                                    }
                                }
                                //处理批量发一本证的坐落和不动产单元号加等  批量一本证的只需处理一次
                                if((plYbz && i==0) || !plYbz){
                                    dealPlZsxx(bdcZsDO, bdcCshFwkgSl, cshFwkgSlList);
                                }
                            }
                        }
                    }
                }
                //循环处理批量抵押一本证的义务人  暂时没按证件号过滤
                for(Integer key : mapYwr.keySet()){
                    List<BdcZsDO> bdcZsDOList = zsidMap.get(key.toString());
                    if(CollectionUtils.isNotEmpty(bdcZsDOList)){
                        for(BdcZsDO bdcZsDO:bdcZsDOList){
                            Set<String> ywrSet=mapYwr.get(key);
                            if(bdcZsDO!=null && CollectionUtils.isNotEmpty(ywrSet)){
                                String ywr=StringUtils.isNotBlank(bdcZsDO.getYwr()) ? bdcZsDO.getYwr() : "";
                                String[] ywrs=ywr.split(" ");
                                if(ywrs!=null && ywrs.length>0){
                                    for(String str:ywrs){
                                        if(StringUtils.isNotBlank(str)){
                                            ywrSet.add(str);
                                        }
                                    }
                                }
                                StringBuilder stringBuilder=new StringBuilder();
                                for(String val:ywrSet){
                                    if(StringUtils.isNotBlank(val)){
                                        if(stringBuilder.length()+val.length()<30){
                                            stringBuilder.append(val).append(" ");
                                        }else{
                                            stringBuilder.setLength(stringBuilder.length()-1);
                                            stringBuilder.append(CommonConstantUtils.SUFFIX_PL);
                                            break;
                                        }
                                    }
                                }
                                bdcZsDO.setYwr(stringBuilder.toString());
                            }
                        }
                    }
                }
            }
            //循环对照赋值模板数据
            for(BdcZsDO bdcZsDO:initZsDTO.getBdcZsList()){
                if(xmidZsmbMap.containsKey(bdcZsDO.getZsid())) {
                    //防止模版是同一个对象，特殊处理后出现覆盖的情况
                    BdcZsDO bdcZsMb = new BdcZsDO();
                    BeanUtils.copyProperties(xmidZsmbMap.get(bdcZsDO.getZsid()), bdcZsMb);
                    //对模版做特殊处理，会有一些比如拼接的情况
                    dozerUtils.sourceBeanDateSpecialConvert(bdcZsMb, bdcZsDO);
                    xmidZsmbMap.put(bdcZsDO.getZsid(),bdcZsMb);
                    dozerUtils.sameBeanDateConvert(bdcZsMb, bdcZsDO, false);
                }
            }
            //证书特殊服务处理
            if (initZsDTO != null && CollectionUtils.isNotEmpty(initBeanFactory.getZsServices())) {
                InitServiceQO initServiceQO = new InitServiceQO();
                for(String beanName:initBeanFactory.getZsServices()){
                    try {
                        InitBdcZsTsService initBdcZsTsService= (InitBdcZsTsService) Container.getBean(beanName);
                        initBdcZsTsService.tshz(initZsDTO,zsyl,initServiceQO);
                    }catch (NoSuchBeanDefinitionException e){
                        throw  new AppException("证书特殊服务中不存在此配置："+beanName);
                    }
                }
            }
            zsList = initZsDTO.getBdcZsList();
            //非预览的走数据库服务
            if(!zsyl){
                //插入证书相关数据
                initDataDealService.dealResultDTO(initZsDTO, Constants.DATA_TYPE_ZS);
                LOGGER.info("生成证书结束:{} ,xmid:{},zsid:{}", simpleDateFormat.format(new Date()), xmid, JSONObject.toJSONString(initZsDTO));
            }
        }else{
            throw new EntityNotFoundException("未查询到项目的初始化实例信息!");
        }
        return zsList;
    }

    /**
     * 通过传入参数更新项目表
     *
     * @param param 参数
     * @param value 数值
     */
    @Override
    public void updateXmVal(BdcXmDO param, BdcXmDO value) {
        if(param!=null && value!=null){
            Example example=entityMapper.objToExample(param);
            entityMapper.updateByExampleSelectiveNotNull(value,example);
        }
    }

    @Override
    public int countBdcZsWithFdcq(String gzlslid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        final List<BdcXmDO> bdcXmDOList = bdcXmService.listBdcXm(bdcXmQO);
        int zsCount = 0;
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            Map<String,Object> zsMap = new HashMap<>();
            for(BdcXmDO bdcXmDO:bdcXmDOList){
                final BdcCshFwkgSlDO bdcCshFwkgSlDO = this.bdcXmService.queryCshFwkgSl(bdcXmDO.getXmid());
                if(null != bdcCshFwkgSlDO){
                    // 当证书序号为空时，则生成一本证书
                    if(null == bdcCshFwkgSlDO.getZsxh()){
                        BdcQl bdcQl = this.bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
                        if(bdcQl instanceof BdcFdcqDO){
                            zsMap.put(bdcCshFwkgSlDO.getId(),bdcXmDO);
                        }
                    }else{
                        //判断相同序号的证书序号是否存在于zsMap中
                        if(!zsMap.containsKey(bdcCshFwkgSlDO.getZsxh().toString())){
                            BdcQl bdcQl = this.bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
                            if(bdcQl instanceof BdcFdcqDO){
                                zsMap.put(bdcCshFwkgSlDO.getZsxh().toString(),bdcXmDO);
                            }
                        }
                    }
                }
            }
            for(String key:zsMap.keySet()){
                zsCount++;
            }
        }
        return zsCount;
    }
    
    @Override
    public String initYcqzhPl(List<BdcXmDO> bdcXmDOList) {
        
        StringBuilder stringBuilder=new StringBuilder();
        
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            // 组织原产权证号
            Set<String> ycqzhSet = bdcXmDOList.stream().map(p -> p.getYcqzh()).collect(Collectors.toSet());
            List<String> ycqzhList = new ArrayList<>(ycqzhSet);
            if (ycqzhList.size() > 3) {
                stringBuilder.append("等");
            }
            if (ycqzhSet.size() < 4) {
                for (int i = 0; i < ycqzhList.size(); i++) {
                    stringBuilder.insert(0, StringUtils.defaultString(ycqzhList.get(i))).insert(0, "、");
                }
            }
            if (ycqzhList.size() > 3) {
                stringBuilder.insert(0, StringUtils.defaultString(ycqzhList.get(0))).insert(0, "、");
            }
    
            //截取
            stringBuilder.deleteCharAt(0);
        }
        return stringBuilder.toString();
    }

    @Override
    public String initYcqzhOne(List<BdcXmDO> bdcXmDOList) {
        StringBuilder stringBuilder=new StringBuilder();
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            // 组织原产权证号
            Set<String> ycqzhSet = bdcXmDOList.stream().map(p -> p.getYcqzh()).collect(Collectors.toSet());
            List<String> ycqzhList = new ArrayList<>(ycqzhSet);
            if (ycqzhList.size() > 1) {
                stringBuilder.append("详见抵押物清单");
            }else{
                stringBuilder.append(ycqzhList.get(0));
            }
        }
        return stringBuilder.toString();
    }

    public void updateCommonQlqtzk(String processInsId){
        updateQlqtzk(processInsId,dgxjdywqd);

    }

    @Override
    public void updateQlqtzk(String processInsId,boolean onlyOne) {
        List<BdcCshFwkgSlDO> list= bdcXmService.listBdCshSl(processInsId);
        if(CollectionUtils.isNotEmpty(list)){
            //分组处理
            Map<Integer, List<BdcCshFwkgSlDO>> cshFwkgSlDataMap=new HashMap<>();
            for(BdcCshFwkgSlDO bdcCshFwkgSlDO:list){
                //抵押权做处理
                if(CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcCshFwkgSlDO.getQllx())){
                    List<BdcCshFwkgSlDO> cshFwkgSlDOList;
                    if(cshFwkgSlDataMap.containsKey(bdcCshFwkgSlDO.getZsxh())){
                        cshFwkgSlDOList=cshFwkgSlDataMap.get(bdcCshFwkgSlDO.getZsxh());
                    }else{
                        cshFwkgSlDOList = new ArrayList<>();
                        cshFwkgSlDataMap.put(bdcCshFwkgSlDO.getZsxh(),cshFwkgSlDOList);
                    }
                    cshFwkgSlDOList.add(bdcCshFwkgSlDO);
                }
            }
            if(MapUtils.isNotEmpty(cshFwkgSlDataMap)){
                for(Integer key:cshFwkgSlDataMap.keySet()){
                    if(key!=null){
                        //定义项目结合
                        List<BdcXmDO> bdcXmDOList=new ArrayList<>();
                        StringBuilder stringBuilder=new StringBuilder();
                        //循环相加原证号
                        for(int i=0;i<cshFwkgSlDataMap.get(key).size();i++){
                            BdcXmDO bdcXmDO=bdcXmService.queryBdcXmByPrimaryKey(cshFwkgSlDataMap.get(key).get(i).getId());
                            if(bdcXmDO!=null){
                                bdcXmDOList.add(bdcXmDO);
                            }
                        }
                        // 获取原产权证号
                        if(onlyOne){
                            stringBuilder.append(initYcqzhOne(bdcXmDOList));
                        }else{
                            stringBuilder.append(initYcqzhPl(bdcXmDOList));
                        }
                        //循环更新
                        for(BdcXmDO bdcXmDO:bdcXmDOList){
                            //清除原有添加的
                            if(StringUtils.isNotBlank(bdcXmDO.getBfqlqtzk()) &&bdcXmDO.getBfqlqtzk().contains(BDCQZH)){
                                String result =StringToolUtils.subString(bdcXmDO.getBfqlqtzk(),BDCQZH,"\n",true);
                                if(StringUtils.isNotBlank(result)){
                                    bdcXmDO.setBfqlqtzk(bdcXmDO.getBfqlqtzk().replace(result,""));
                                }
                            }
                            bdcXmDO.setBfqlqtzk(BDCQZH+stringBuilder.toString()+"\n"+StringUtils.defaultString(bdcXmDO.getBfqlqtzk()));
                            bdcXmService.updateBdcXm(bdcXmDO);
                        }
                        //更新证书
                        List<BdcZsDO> listZs = bdcZsService.queryBdcZsByXmid(bdcXmDOList.get(0).getXmid());
                        if(CollectionUtils.isNotEmpty(listZs)){
                            for(BdcZsDO bdcZsDO:listZs){
                                //清除原有添加的
                                if(StringUtils.isNotBlank(bdcZsDO.getQlqtzk()) &&bdcZsDO.getQlqtzk().contains(BDCQZH)){
                                    String result =StringToolUtils.subString(bdcZsDO.getQlqtzk(),BDCQZH,"\n",true);
                                    if(StringUtils.isNotBlank(result)){
                                        bdcZsDO.setQlqtzk(bdcZsDO.getQlqtzk().replace(result,""));
                                    }
                                }
                                bdcZsDO.setQlqtzk(BDCQZH+stringBuilder.toString()+"\n"+StringUtils.defaultString(bdcZsDO.getQlqtzk()));
                                bdcZsService.updateBdcZs(bdcZsDO);
                            }
                        }
                    }else{
                        //单独更新
                        for(BdcCshFwkgSlDO bdcCshFwkgSlDO:cshFwkgSlDataMap.get(key)){
                            //更新项目
                            BdcXmDO bdcXmDO=bdcXmService.queryBdcXmByPrimaryKey(bdcCshFwkgSlDO.getId());
                            if(bdcXmDO!=null){
                                //清除原有添加的
                                if(StringUtils.isNotBlank(bdcXmDO.getBfqlqtzk()) &&bdcXmDO.getBfqlqtzk().contains(BDCQZH)){
                                    String result =StringToolUtils.subString(bdcXmDO.getBfqlqtzk(),BDCQZH,"\n",true);
                                    if(StringUtils.isNotBlank(result)){
                                        bdcXmDO.setBfqlqtzk(bdcXmDO.getBfqlqtzk().replace(result,""));
                                    }
                                }
                                bdcXmDO.setBfqlqtzk(BDCQZH+StringUtils.defaultString(bdcXmDO.getYcqzh())+"\n"+StringUtils.defaultString(bdcXmDO.getBfqlqtzk()));
                                bdcXmService.updateBdcXm(bdcXmDO);
                                //更新证书
                                List<BdcZsDO> listZs = bdcZsService.queryBdcZsByXmid(bdcXmDO.getXmid());
                                if(CollectionUtils.isNotEmpty(listZs)){
                                    for(BdcZsDO bdcZsDO:listZs){
                                        //清除原有添加的
                                        if(StringUtils.isNotBlank(bdcZsDO.getQlqtzk()) &&bdcZsDO.getQlqtzk().contains(BDCQZH)){
                                            String result =StringToolUtils.subString(bdcZsDO.getQlqtzk(),BDCQZH,"\n",true);
                                            if(StringUtils.isNotBlank(result)){
                                                bdcZsDO.setQlqtzk(bdcZsDO.getQlqtzk().replace(result,""));
                                            }
                                        }
                                        bdcZsDO.setQlqtzk(BDCQZH+StringUtils.defaultString(bdcXmDO.getYcqzh())+"\n"+StringUtils.defaultString(bdcZsDO.getQlqtzk()));
                                        bdcZsService.updateBdcZs(bdcZsDO);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 通过传入的工作流实例ID，追加附记内容至上一手的附记中去
     * @param processInsId  工作流实例ID
     */
    @Override
    public void zjsysFj(String processInsId) {
        if(StringUtils.isBlank(processInsId)){
            throw new AppException("缺失参数工作流实例ID");
        }
        List<BdcXmDO> bdcXmDOList = this.bdcXmService.listBdcXm(processInsId);
        if(CollectionUtils.isNotEmpty(bdcXmDOList)){
            for(BdcXmDO bdcXmDO: bdcXmDOList){
                // 获取所有项目的所有项目历史关系
                BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
                bdcXmLsgxQO.setXmid(bdcXmDO.getXmid());
                List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcXmLsgxService.listBdcXmLsgx(bdcXmLsgxQO);
                if(CollectionUtils.isNotEmpty(bdcXmLsgxDOList)){
                    for(BdcXmLsgxDO lsgx:bdcXmLsgxDOList){
                        if(StringUtils.isNotBlank(lsgx.getYxmid())){
                            BdcXmDO yxmxx = this.bdcXmService.queryBdcXmByPrimaryKey(lsgx.getYxmid());
                            // 查询上一手查封信息中的权利信息并追加进查封的附记中
                            if(null != yxmxx && CommonConstantUtils.QLLX_CF.equals(yxmxx.getQllx())){
                                BdcQl bdcQl = this.bdcQllxService.queryQllxDO(yxmxx.getXmid());
                                bdcQl.setFj(getZjContent(bdcQl.getFj(), bdcXmDO));
                                this.bdcQllxService.updateBdcQl(bdcQl);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取追加的附记内容
     */
    private String getZjContent(String fj, BdcXmDO bdcXmDO){
        String zhxx = String.format("查封证号：%s", bdcXmDO.getYcqzh());
        String slbhxx = String.format("  已强制转移  受理编号：%s", bdcXmDO.getSlbh());
        String qlfj = Optional.ofNullable(fj).orElse("");
        String content = "";
        // 比对原附记中是否存在查封证号，拥有查封证号时不再追加
        if(qlfj.indexOf(zhxx)>-1){
            content = qlfj + slbhxx;
        }else{
            content = qlfj + zhxx + slbhxx;
        }
        return content;
    }

    /**
     * 获取组合证书需生成证书的主项目
     *
     * @param cshFwkgSlList
     * @return Map<Integer,BdcCshFwkgSlDO>
     */
    private  Map<Integer, String> queryZhzsgx(List<BdcCshFwkgSlDO> cshFwkgSlList) {
        Map<Integer, String> map = new HashMap<>();
        Map<Integer, String> mapZf = new HashMap<>();
        Map<Integer, String> mapFw = new HashMap<>();
        if (CollectionUtils.isNotEmpty(cshFwkgSlList)) {
            //排序
            cshFwkgSlList.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
            for (BdcCshFwkgSlDO bdcCshFwkgSl : cshFwkgSlList) {
                if (bdcCshFwkgSl.getZsxh() != null) {
                    if (CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSl.getSfzf())) {
                        mapZf.put(bdcCshFwkgSl.getZsxh(), bdcCshFwkgSl.getId());
                    }
                    map.put(bdcCshFwkgSl.getZsxh(), bdcCshFwkgSl.getId());
                    //不存在的话做处理 查询此序号是房子的
                    if(!mapFw.containsKey(bdcCshFwkgSl.getZsxh())){
                        BdcXmDO bdcXmDO=bdcXmService.queryBdcXmByPrimaryKey(bdcCshFwkgSl.getId());
                        if(bdcXmDO!=null && bdcBdcdyService.judgeIsFwByBdcdyh(bdcXmDO.getBdcdyh())){
                            mapFw.put(bdcCshFwkgSl.getZsxh(),bdcCshFwkgSl.getId());
                        }
                    }
                }
            }
            //先用房屋的做覆盖  再用主房的做覆盖
            map.putAll(mapFw);
            map.putAll(mapZf);
        }
        return map;
    }

    /**
     * 处理字段加"等"
     * @param bdcZsDO
     */
    private  void dealPlZsxx(BdcZsDO bdcZsDO, BdcCshFwkgSlDO bdcCshFwkgSl, List<BdcCshFwkgSlDO> cshFwkgSlList) {
        String deng=CommonConstantUtils.SUFFIX_PL;
        if(bdcZsDO!=null){
            //处理坐落
            if(StringUtils.isNotBlank(bdcZsDO.getZl())){
                String zl=bdcZsDO.getZl();
                if(!StringUtils.equals(zl.substring(zl.length()-1),deng)){
                    bdcZsDO.setZl(zl+deng);
                }
                // 去除等字，坐落获取房屋的坐落
                if(qcjdConfig.qcjdByXmid(bdcCshFwkgSl.getId())){
                    BdcXmDO bdcXmDO = this.bdcXmService.queryBdcXmByPrimaryKey(bdcCshFwkgSl.getId());
                    List<BdcXmDO> bdcXmDOList = this.bdcXmService.listBdcXm(bdcXmDO.getGzlslid());
                    bdcZsDO.setZl(qcjdConfig.getNoTdzZlByBdcXm(bdcXmDOList));
                }
            }
            //处理不动产单元
            if(StringUtils.isNotBlank(bdcZsDO.getBdcdyh())){
                String bdcdyh=bdcZsDO.getBdcdyh();
                if(!StringUtils.equals(bdcdyh.substring(bdcdyh.length()-1),deng) && !qcjdConfig.qcjdByXmid(bdcCshFwkgSl.getId())){
                    bdcZsDO.setBdcdyh(bdcdyh+deng);

                    // 常州土地承包经营权证书多个不动产单元号需要加 等*个
                    if(CommonConstantUtils.SYSTEM_VERSION_CZ.equals(initBeanFactory.getVersion()) && CommonConstantUtils.QLLX_TDCBNYDSYQ.equals(bdcCshFwkgSl.getQllx())) {
                        Integer zsxh = bdcCshFwkgSl.getZsxh();
                        if(null != zsxh) {
                            // 初始化开发实例表中证书序号字段相同的表示生成一本证，这里从实例表中找出当前证书序号的实例数据，即证书关联的项目数量
                           int zsXmSize = 0;
                           for(BdcCshFwkgSlDO kg : cshFwkgSlList) {
                               if(null != kg && zsxh.equals(kg.getZsxh())) {
                                   zsXmSize++;
                               }
                           }

                           if(zsXmSize > 0) {
                               bdcZsDO.setBdcdyh(bdcdyh + deng + zsXmSize + "个");
                           }
                        }
                    }

                    // 去除等字，坐落获取房屋的坐落
                    if(qcjdConfig.qcjdByXmid(bdcCshFwkgSl.getId())){
                        BdcXmDO bdcXmDO = this.bdcXmService.queryBdcXmByPrimaryKey(bdcCshFwkgSl.getId());
                        List<BdcXmDO> bdcXmDOList = this.bdcXmService.listBdcXm(bdcXmDO.getGzlslid());
                        bdcZsDO.setBdcdyh(qcjdConfig.getNoTdzBdcdyhByBdcXm(bdcXmDOList));
                    }

                }
            }
        }
    }

    /**
     * 处理发一本证的其他证书项目关系
     * @param zsid
     * @param xmid
     * @param initDTO
     */
    private  void dealXmZsgx(String zsid,String xmid,InitResultDTO initDTO) {
        BdcXmZsGxDO bdcXmZsGxDO = new BdcXmZsGxDO();
        bdcXmZsGxDO.setGxid(UUIDGenerator.generate16());
        bdcXmZsGxDO.setXmid(xmid);
        bdcXmZsGxDO.setZsid(zsid);
        initDTO.getBdcXmZsGxList().add(bdcXmZsGxDO);
    }

}
