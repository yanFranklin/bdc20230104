package cn.gtmap.realestate.init.service.other.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.init.BdcCopyReplaceYwxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.utils.AnnotationsUtils;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.*;
import cn.gtmap.realestate.init.core.thread.*;
import cn.gtmap.realestate.init.service.InitService;
import cn.gtmap.realestate.init.service.other.InitDataDealService;
import cn.gtmap.realestate.init.service.other.InitDataService;
import cn.gtmap.realestate.init.util.Constants;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/29.
 * @description
 */
@Service
public class InitDataServiceImpl implements InitDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitBeanFactory.class);

    /**
     * 撤销登记
     */
    @Value("#{'${cxdj.gzldyid:}'.split(',')}")
    private List<String> cxdjDyids;

    /**
     * 48716 【盐城】换证和遗失补证业务自动带入上一手附记内容
     */
    @Value("#{'${csh.fjyql.gzldyids:}'.split(',')}")
    private List<String> fjyqlgzl;

    /**
     * 初始化拼接第三方传的附记内容
     */
    @Value("#{'${csh.fjpjdsf.gzldyids:}'.split(',')}")
    private List<String> fjpjdsfgzl;

    /**
     * 同步权籍时对于权籍为空的情况是否要覆盖
     */
    @Value("${synchLpbDataToLc.nullcover:false}")
    private Boolean synchLpbDataToLcCover;

    @Autowired
    private InitBeanFactory initBeanFactory;
    @Autowired
    private InitDataDealService initDataDealService;
    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private BdcXtQlqtzkFjPzService bdcXtQlqtzkFjPzService;
    @Autowired
    private BdcQllxService bdcQllxService;
    @Autowired
    private ThreadEngine threadEngine;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcCshFwkgService bdcCshFwkgService;
    @Autowired
    private BdcCxYwxxService bdcCxYwxxService;
    @Autowired
    BdcDlrService bdcDlrService;

    /**
     * 初始化线程池定义
     */
    private ExecutorService executor = new ThreadPoolExecutor(
            // 核心线程数量
            20,
            // 最大线程数
            100,
            // 超时300秒
            300, TimeUnit.SECONDS,
            // 最大允许等待5000个任务
            new ArrayBlockingQueue<>(5000),
            // 过多任务直接主线程处理
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    /**
     * 初始化总体逻辑
     *
     * @param listQO
     * @param rk     是否入库
     * @return InitResultDTO 初始化后的数据
     */
    @Override
    public InitResultDTO init(List<InitServiceQO> listQO, boolean rk) throws Exception {
        InitResultDTO initResultDTO = null;
        if (CollectionUtils.isNotEmpty(listQO)) {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS");
            //初始化服务类
            List<Class> clzList = initBeanFactory.getInitServices();
            //初始化数据集合
            Map<String, InitServiceDTO> serviceDataMap = new ConcurrentHashMap<>();
            //业务循环获取bdcdy信息来处理 登记簿加载问题
            initBdcdjb(listQO);
            //处理多线程分组
            Multimap<String,InitServiceQO> threadMap= initThreadGroup(listQO);
            //要删除的数据
            List<Object> deleteList=new CopyOnWriteArrayList<>();
            LOGGER.info("多线程处理业务数据开始:{}", simpleDateFormat.format(new Date()));
            //多线程初始化
            excuteThread(clzList,threadMap,serviceDataMap,listQO,deleteList);
            LOGGER.info("多线程处理业务数据结束:{}", simpleDateFormat.format(new Date()));
            //处理权籍为空的数据
            if(synchLpbDataToLcCover && listQO.get(0).isSfymtbqjsj()) {
                LOGGER.info("处理权籍为空的数据开始:{}", simpleDateFormat.format(new Date()));
                //转换所有业务生成的数据
                initDataDealService.convertNullValue(serviceDataMap);
                LOGGER.info("处理权籍为空的数据结束:{}", simpleDateFormat.format(new Date()));
            }

            //转换所有业务生成的数据
            initResultDTO = initDataDealService.dealServiceDTO(serviceDataMap);
            LOGGER.info("转换生成数据结束:{}", simpleDateFormat.format(new Date()));
            //存入要删除的数据
            initResultDTO.setDeleteList(deleteList);
            if(rk){
                //插入数据
                if(listQO.get(0).isSfzqlpbsj()){
                    if(listQO.get(0).isSfdzbflpbsj()){
                        initDataDealService.dealResultDTO(initResultDTO,Constants.DATA_TYPE_LPB_DZ);
                    }else{
                        initDataDealService.dealResultDTO(initResultDTO,Constants.DATA_TYPE_LPB);
                    }
                }else{
                    initDataDealService.dealResultDTO(initResultDTO,Constants.DATA_TYPE_INIT);
                }
                LOGGER.info("数据入库结束:{} 是否抓取楼盘表数据:{}  删除数据:{}", simpleDateFormat.format(new Date()),listQO.get(0).isSfzqlpbsj(),deleteList.size());
            }
        }
        return initResultDTO;
    }

    /**
     * 初始化权利附记
     * @param listQO
     */
    @Override
    public void initQlfj(List<BdcXmDO> listXm, List<InitServiceQO> listQO) {
        if (CollectionUtils.isNotEmpty(listXm) && (initBeanFactory.isInitFj()
                || initBeanFactory.isDqXmQlqtzk()
                || CommonConstantUtils.SYSTEM_VERSION_BB.equals(initBeanFactory.getVersion())
                || CollectionUtils.isNotEmpty(fjyqlgzl))) {
            boolean sfbjs = listXm.size() <= 1;
            //循环初始化附记
            List<CommonThread> listThread = new ArrayList();
            //业务循环
            for (BdcXmDO bdcXmDO : listXm) {
                InitQlfjThread initQlfjThread = new InitQlfjThread(bdcXtQlqtzkFjPzService, bdcQllxService, bdcXmDO,
                        initBeanFactory, entityMapper, listQO, fjyqlgzl,bdcXmService,fjpjdsfgzl
                        );
                initQlfjThread.setSfbjs(sfbjs);
                listThread.add(initQlfjThread);
            }
            //多线程处理操作
            threadEngine.excuteThread(listThread, true, null);
        }
    }

    /**
     * 根据项目数据删除数据服务
     *@param sfPlDel 是否批量删除
     * @param xmList
     * @return true/false
     */
    @Override
    @Transactional
    public Boolean deleteYwsj(List<BdcXmDO> xmList,Boolean sfPlDel) throws Exception {
        Boolean success;
        if (CollectionUtils.isNotEmpty(xmList)) {
            List<BdcZsDO> deleteZsList =new ArrayList<>();
            List<Object> deleteDataList = initDataDealService.queryDeleteData(xmList,false,false,sfPlDel,true);
            if (CollectionUtils.isNotEmpty(deleteDataList)) {

                //删除代理人
                bdcDlrService.deleteLcDlr(xmList.get(0).getGzlslid());
                //删除数据
                for (Object obj : deleteDataList) {
                    if (obj instanceof BdcZsDO) {
                        deleteZsList.add((BdcZsDO) obj);
                    }
                    Method method = AnnotationsUtils.getAnnotationsName(obj);
                    String id = null;
                    try {
                        if (method.invoke(obj) != null) {
                            id = method.invoke(obj).toString();
                        }
                    } catch (Exception e) {
                        LOGGER.error(null, e);
                    }
                    entityMapper.deleteByPrimaryKey(obj.getClass(),id);
                }
                //判断是否为撤销登记
                boolean isCxdj =CollectionUtils.isNotEmpty(cxdjDyids) &&cxdjDyids.contains(xmList.get(0).getGzldyid());
                if(isCxdj) {
                    //还原撤销登记证书号
                    bdcCxYwxxService.revertZhzt(deleteZsList);
                }

            }
        }
        success = true;
        return success;
    }

    /**
     * 根据项目id数据查询数据服务
     * @param xmid
     * @return InitServiceDTO
     */
    @Override
    public InitServiceDTO queryYwsj(String xmid) throws Exception {
        InitServiceDTO initServiceDTO=null;
        if(StringUtils.isNotBlank(xmid)){
            BdcXmDO bdcXmDO=bdcXmService.queryBdcXmByPrimaryKey(xmid);
            if(bdcXmDO!=null){
                initServiceDTO=new InitServiceDTO();
                //暂时用删除服务配置
                List<Class> clzList = initBeanFactory.getDeleteServices();
                //加载类循环
                for (Class clz : clzList) {
                    List<InitService> list = initBeanFactory.getTrafficMode(bdcCshFwkgService.getDefaultSl(), clz);
                    //对应实现循环处理
                    if (CollectionUtils.isNotEmpty(list)) {
                        list.get(0).query(bdcXmDO,initServiceDTO);
                    }
                }
            }
        }
        return initServiceDTO;
    }

    /**
     * 根据项目id数据更新业务数据服务
     * @param xmid
     * @param bdcYwxxDTO
     */
    @Override
    public BdcYwxxDTO saveOrUpdateYwsj(String xmid, BdcYwxxDTO bdcYwxxDTO,boolean isInsert) throws Exception {
        InitServiceDTO initServiceDTO=null;
        if(!isInsert && StringUtils.isNotBlank(xmid) ){
            BdcXmDO bdcXmDO = bdcXmService.queryBdcXmByPrimaryKey(xmid);
            if (bdcXmDO == null) {
                throw new AppException("没有对应的项目数据,无法更新！");
            }
        }
        try {
            initServiceDTO = new InitServiceDTO();
            BeanUtils.copyProperties(bdcYwxxDTO, initServiceDTO);
            List<InitServiceDTO> list = new ArrayList<>();
            list.add(initServiceDTO);
            initDataDealService.dealResultDTO(initDataDealService.dealServiceDTO(list), Constants.DATA_TYPE_PT);
        }catch (Exception e){
            LOGGER.error("插入或更新业务数据异常：",e);
            // 发生异常 返回空对象
            bdcYwxxDTO = new BdcYwxxDTO();
        }
        return bdcYwxxDTO;
    }

    @Override
    public List<BdcYwxxDTO>  saveOrUpdateYwsjPl(String xmid,List<BdcYwxxDTO> bdcYwxxDTOList,boolean isInsert){
        InitServiceDTO initServiceDTO=null;
        if(!isInsert && StringUtils.isNotBlank(xmid) ){
            BdcXmDO bdcXmDO = bdcXmService.queryBdcXmByPrimaryKey(xmid);
            if (bdcXmDO == null) {
                throw new AppException("没有对应的项目数据,无法更新！");
            }
        }
        try {
            List<InitServiceDTO> list = new ArrayList<>();
            for(BdcYwxxDTO bdcYwxxDTO:bdcYwxxDTOList) {
                initServiceDTO = new InitServiceDTO();
                BeanUtils.copyProperties(bdcYwxxDTO, initServiceDTO);
                list.add(initServiceDTO);
            }
            initDataDealService.dealResultDTO(initDataDealService.dealServiceDTO(list), Constants.DATA_TYPE_PT);
        }catch (Exception e){
            LOGGER.error("插入或更新业务数据异常：",e);
            // 发生异常 返回空对象
            bdcYwxxDTOList = new ArrayList<>();
        }
        return bdcYwxxDTOList;

    }

    @Override
    public List<BdcXmDO> queryYwxxByBdcdywybh(String bdcdywybh) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setBdcdywybh(bdcdywybh);
        bdcXmQO.setQszt(CommonConstantUtils.QSZT_VALID);
        return bdcXmService.listBdcXm(bdcXmQO);
    }

    @Override
    public InitResultDTO initCxYwxx(List<InitServiceQO> listQO,boolean rk) throws Exception{
        InitResultDTO initResultDTO = null;
        if (CollectionUtils.isNotEmpty(listQO)) {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS");
            //初始化数据集合
            Map<String, InitServiceDTO> serviceDataMap = new ConcurrentHashMap<>();
            LOGGER.info("多线程处理撤销业务数据开始:{}", simpleDateFormat.format(new Date()));
            //多线程初始化
            excuteCxdjThread(serviceDataMap,listQO);
            LOGGER.info("多线程处理撤销业务数据结束:{}", simpleDateFormat.format(new Date()));
            //转换所有业务生成的数据
            initResultDTO = initDataDealService.dealServiceDTO(serviceDataMap);
            LOGGER.info("转换生成撤销业务数据结束:{}", simpleDateFormat.format(new Date()));
            if(rk){
                initDataDealService.dealResultDTO(initResultDTO,Constants.DATA_TYPE_INIT);
                LOGGER.info("撤销业务数据入库结束:{}", simpleDateFormat.format(new Date()));
            }
        }
        return initResultDTO;

    }

    @Override
    public BdcYwxxDTO copyYwxx(BdcCopyReplaceYwxxDTO bdcCopyReplaceYwxxDTO){
        if(StringUtils.isBlank(bdcCopyReplaceYwxxDTO.getQueryXmid())){
            throw new AppException(ErrorCode.MISSING_ARG, "复制业务信息失败,缺失必要参数XMID");
        }
        if(StringUtils.isBlank(bdcCopyReplaceYwxxDTO.getBdcdyh())){
            //单元号不替换,不生成登记薄数据
            bdcCopyReplaceYwxxDTO.setCopyDjb(false);
        }
        InitServiceDTO initServiceDTO;
        //查询业务信息
        try {
            initServiceDTO = queryYwsj(bdcCopyReplaceYwxxDTO.getQueryXmid());
        }catch (Exception e){
            LOGGER.error("查询业务信息操作异常",e);
            throw new AppException(ErrorCode.SERVER_EX, "查询业务信息操作异常");
        }
        if(Boolean.FALSE.equals(bdcCopyReplaceYwxxDTO.isCopyZs())) {
            initServiceDTO.setBdcZsList(null);
        }
        if(Boolean.FALSE.equals(bdcCopyReplaceYwxxDTO.isCopyDjb())) {
            initServiceDTO.setBdcdjb(null);
            initServiceDTO.setBdcBdcdjbZdjbxx(null);
            initServiceDTO.setBdcBdcdjbZhjbxx(null);
            initServiceDTO.setBdcBdcdjbZdjbxxZdbhqkList(null);
            initServiceDTO.setBdcBdcdjbZhjbxxYhydzbList(null);
            initServiceDTO.setBdcBdcdjbZhjbxxYhzkList(null);
            initServiceDTO.setBdcBdcdjbZhjbxxZhbhqkList(null);
        }

        String newxmid = StringUtils.isNotBlank(bdcCopyReplaceYwxxDTO.getXmid()) ?bdcCopyReplaceYwxxDTO.getXmid():UUIDGenerator.generate16();
        //替换数据
        replaceYwxx(initServiceDTO,newxmid,bdcCopyReplaceYwxxDTO.getBdcdyh(),bdcCopyReplaceYwxxDTO.getGzlslid(),bdcCopyReplaceYwxxDTO.getSlbh());
        BdcYwxxDTO bdcYwxxDTO =new BdcYwxxDTO();
        BeanUtils.copyProperties(initServiceDTO, bdcYwxxDTO);
        return bdcYwxxDTO;
    }

    @Override
    public List<BdcYwxxDTO> copyAndReplaceYwxx(List<BdcCopyReplaceYwxxDTO> bdcCopyReplaceYwxxDTOList){
        List  bdcYwxxDTOList =new ArrayList<>();
        if(CollectionUtils.isNotEmpty(bdcCopyReplaceYwxxDTOList) &&bdcCopyReplaceYwxxDTOList.size() >10) {
            for(BdcCopyReplaceYwxxDTO bdcCopyReplaceYwxxDTO:bdcCopyReplaceYwxxDTOList) {
                //多线程处理
                List<Future<Object>> tasksList = new ArrayList<>(bdcCopyReplaceYwxxDTOList.size());
                CopyYwxxThread copyYwxxThread = new CopyYwxxThread(this, bdcCopyReplaceYwxxDTO);
                tasksList.add(executor.submit(copyYwxxThread));
                try {
                    for (Future<Object> task : tasksList) {
                        bdcYwxxDTOList.add(task.get());
                    }
                    return bdcYwxxDTOList;
                } catch (Exception e) {
                    LOGGER.error("复制业务信息处理失败", e);
                    throw new AppException("复制业务信息处理失败");
                }
            }
        }else{
            for(BdcCopyReplaceYwxxDTO bdcCopyReplaceYwxxDTO:bdcCopyReplaceYwxxDTOList){
                bdcYwxxDTOList.add(copyYwxx(bdcCopyReplaceYwxxDTO));

            }

        }
        return bdcYwxxDTOList;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 替换业务信息部分字段
     */
    private void replaceYwxx(InitServiceDTO initServiceDTO,String xmid,String bdcdyh,String gzlslid,String slbh){
        //替换主键、项目ID,不动产单元号等信息
        if(initServiceDTO !=null){
            try {
                if (initServiceDTO.getBdcCshFwkgSlDO() != null) {
                    initServiceDTO.getBdcCshFwkgSlDO().setId(xmid);
                }
                Field[] fields = InitServiceDTO.class.getDeclaredFields();
                if (fields != null && fields.length > 0) {
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Object val = field.get(initServiceDTO);
                        //判断是否是list集合
                        if (val != null && field.getType() == List.class) {
                            List ywxxList = (List) val;
                            if (CollectionUtils.isNotEmpty(ywxxList)) {
                                for (int i = 0; i < ywxxList.size(); i++) {
                                    setObjectValue(ywxxList.get(i), xmid, bdcdyh,gzlslid,slbh);
                                    //特殊处理一些外键字段
                                    if ((ywxxList.get(i) instanceof BdcFdcqFdcqxmDO || ywxxList.get(i) instanceof BdcFdcq3GyxxDO) && initServiceDTO.getBdcQl() != null) {
                                        ((BdcFdcqFdcqxmDO) ywxxList.get(i)).setQlid(initServiceDTO.getBdcQl().getQlid());
                                    }
                                }
                            }
                        } else if (val != null) {
                            setObjectValue(val, xmid,bdcdyh,gzlslid,slbh);
                        }
                    }
                }
            }catch (Exception e){
            }
        }
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 对象修改xmid,bdcdyh,主键数据 (后续考虑支持任意字段替换)
     */
    private void setObjectValue(Object val, String xmid,String bdcdyh,String gzlslid,String slbh) throws Exception {
        Field[] ywxxFields = val.getClass().getDeclaredFields();
        for (Field f : ywxxFields) {
            f.setAccessible(true);
            if (StringUtils.equals(f.getName(), "xmid")) {
                f.set(val, xmid);
            }else if (StringUtils.isNotBlank(bdcdyh) &&StringUtils.equals(f.getName(), "bdcdyh")) {
                f.set(val, bdcdyh);
            } else if (StringUtils.isNotBlank(gzlslid) &&StringUtils.equals(f.getName(), "gzlslid")) {
                f.set(val, gzlslid);
            } else if (StringUtils.isNotBlank(slbh) &&StringUtils.equals(f.getName(), "slbh")) {
                f.set(val, slbh);
            } else if (f.isAnnotationPresent(Id.class)) {
                //获取实体主键,重新赋值
                String newId = UUIDGenerator.generate16();
                f.set(val, newId);
            }
        }
    }

    /**
     * 多线程分组
     * @param listQO
     * @return
     */
    private Multimap<String,InitServiceQO> initThreadGroup(List<InitServiceQO> listQO) {
        Multimap<String,InitServiceQO> map= ArrayListMultimap.create();
        //与登记小类的关系表对象存储
        Map<String,Map<String,Object>> lcpzMap=new ConcurrentHashMap<>();
        lcpzMap.put("djlx",new ConcurrentHashMap<>());
        lcpzMap.put("djyy",new ConcurrentHashMap<>());
        //业务循环 第一层
        for (InitServiceQO initServiceQO : listQO) {
            initServiceQO.setLcpzMap(lcpzMap);
            map.put(initServiceQO.getXmid(),initServiceQO);
        }
        //业务循环 第二层
        for (InitServiceQO initServiceQO : listQO) {
            if(StringUtils.isNotBlank(initServiceQO.getYxmid()) && CollectionUtils.isNotEmpty(map.get(initServiceQO.getYxmid()))){
                //放入同一个集合里
                map.put(initServiceQO.getYxmid(),initServiceQO);
                //移除之前存储的数据
                map.remove(initServiceQO.getXmid(),initServiceQO);
            }
        }
        return map;
    }


    /**
     * 是否初始化登记薄处理
     * @param listQO
     * @return
     */
    private void initBdcdjb(List<InitServiceQO> listQO) {
        Map<String, String> djhMap = new HashMap<>();
        for (InitServiceQO initServiceQO : listQO) {
            if (StringUtils.isNotBlank(initServiceQO.getBdcdyh()) && initServiceQO.getBdcdyh().length() == 28 && !BdcdyhToolUtils.checkXnbdcdyh(initServiceQO.getBdcdyh())) {
                String djh = initServiceQO.getBdcdyh().substring(0, 19);
                if (djhMap.get(djh) != null) {
                    initServiceQO.setSfCshDjb(false);
                } else {
                    djhMap.put(djh, initServiceQO.getXmid());
                    initServiceQO.setSfCshDjb(true);
                }
            } else {
                initServiceQO.setSfCshDjb(false);
            }
        }
    }


    /**
     * 多线程处理服务
     * @param clzList
     * @param threadMap
     * @param serviceDataMap
     * @param listQO
     * @param deleteList
     */
    private void excuteThread(List<Class> clzList,Multimap<String,InitServiceQO> threadMap,Map<String, InitServiceDTO> serviceDataMap,List<InitServiceQO> listQO,List<Object> deleteList){
        //线程池对象
        Map<String,Object> taskMap=new ConcurrentHashMap<>();
        //循环初始化
        List<CommonThread> listThread = new ArrayList();
        //整合后结果
        Map<String, Collection<InitServiceQO>> map=threadMap.asMap();
        //判断是否走多线程计数限制
        boolean sfbjs = map.size() <= 1;
        for(Collection<InitServiceQO> groupList:map.values()){
            InitThread initThread=new InitThread(groupList,serviceDataMap,initBeanFactory,clzList);
            initThread.setTaskMap(taskMap);
            initThread.setSfbjs(sfbjs);
            listThread.add(initThread);
        }
        //判断增量初始化
        if (listQO.get(0) != null) {
            InitServiceQO initQO = listQO.get(0);
            //同步权籍走批量删除,不走线程
            if(!initQO.isSfzqlpbsj()){
                Boolean delete = initQO.getBdcCshFwkgSl() != null && !CommonConstantUtils.SF_S_DM.equals(initQO.getBdcCshFwkgSl().getSfzlcsh()) && initQO.getBdcXm() != null;
                //删除数据
                if (delete) {
                    DeleteThread deleteThread=new DeleteThread(initDataDealService,bdcXmService.listBdcXm(initQO.getBdcXm().getGzlslid()),deleteList);
                    deleteThread.setTaskMap(taskMap);
                    deleteThread.setSfbjs(sfbjs);
                    listThread.add(deleteThread);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(listThread)){
            //多线程处理操作
            threadEngine.excuteThread(listThread, true,taskMap);
            if(taskMap.containsKey("msg")){
                throw new AppException(taskMap.get("msg")!=null ? taskMap.get("msg").toString() : "初始化失败!");
            }
        }
    }

    /**
     * 多线程处理服务
     * @param serviceDataMap
     * @param listQO
     */
    private void excuteCxdjThread(Map<String, InitServiceDTO> serviceDataMap,List<InitServiceQO> listQO){
        //线程池对象
        Map<String,Object> taskMap=new ConcurrentHashMap<>();
        //循环初始化
        List<CommonThread> listThread = new ArrayList<>();

        //判断是否走多线程计数限制
        boolean sfbjs = listQO.size() <= 1;
        for(InitServiceQO initServiceQO:listQO){
            InitCxdjThread initCxdjThread=new InitCxdjThread(serviceDataMap,initServiceQO,bdcCxYwxxService);
            initCxdjThread.setTaskMap(taskMap);
            initCxdjThread.setSfbjs(sfbjs);
            listThread.add(initCxdjThread);
        }

        if(CollectionUtils.isNotEmpty(listThread)){
            //多线程处理操作
            threadEngine.excuteThread(listThread, true,taskMap);
            if(taskMap.containsKey("msg")){
                throw new AppException(taskMap.get("msg")!=null ? taskMap.get("msg").toString() : "初始化撤销业务失败!");
            }
        }
    }


}
