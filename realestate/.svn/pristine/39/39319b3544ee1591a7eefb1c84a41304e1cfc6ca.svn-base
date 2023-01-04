package cn.gtmap.realestate.init.service.other.impl;

import cn.gtmap.realestate.common.core.annotations.Zd;
import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcQjtbxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcSjdzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.building.DjxxFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.dto.SynchLpbxxDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcBdcdyService;
import cn.gtmap.realestate.init.core.service.BdcQllxService;
import cn.gtmap.realestate.init.core.service.BdcXmLsgxService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.other.BdcSynchService;
import cn.gtmap.realestate.init.service.other.BdcYwsjHxService;
import cn.gtmap.realestate.init.service.other.InitDataService;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.util.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.metadata.ClassMappingMetadata;
import org.dozer.metadata.FieldMappingMetadata;
import org.dozer.metadata.MappingMetadata;
import org.hibernate.validator.constraints.NotBlank;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.persistence.Table;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/12/5.
 * @description
 */
@Service
@Validated
public class BdcSynchServiceImpl implements BdcSynchService {
    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private BdcXmLsgxService bdcXmLsgxService;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private InitDataService initDataService;
    @Autowired
    private InitBeanFactory initBeanFactory;
    @Autowired
    private BdcYwsjHxService bdcYwsjHxService;
    @Autowired
    private InitServiceQoService initServiceQoService;
    @Autowired
    private DjxxFeignService djxxFeignService;
    @Resource(name = "initDozerMapper")
    private DozerBeanMapper initDozerMapper;
    @Autowired
    private BdcQllxService bdcQllxService;
    @Autowired
    private BdcZdCache bdcZdCache;
    @Autowired
    private BdcBdcdyService bdcBdcdyService;

    /**
     * 是否以原项目为主
     */
    @Value("#{'${init.yxmyzh:}'.split(',')}")
    private List<String> yxmyzh;

    /**
     * 同步权籍权利人和义务人的集合
     */
    @Value("#{'${init.synchqlr:}'.split(',')}")
    private List<String> synchqlr;


    /**
     * 同步流程的原证号信息
     * @param gzlslid
     * @throws Exception
     */
    @Override
    public void synchLcYzh(@NotBlank(message = "工作流实例ID不能为空") String gzlslid) throws Exception {
        List<BdcXmDO> xmList=bdcXmService.listBdcXm(gzlslid);
        if(CollectionUtils.isNotEmpty(xmList)){
            for(BdcXmDO bdcXmDO:xmList){
                synchXmYzh(bdcXmDO);
            }
        }
    }

    /**
     * 同步项目的原证号信息
     * @param xmid
     * @throws Exception
     */
    @Override
    public void synchXmYzh(@NotBlank(message = "项目ID不能为空") String xmid) throws Exception {
        BdcXmDO bdcXm=bdcXmService.queryBdcXmByPrimaryKey(xmid);
        synchXmYzh(bdcXm);
    }

    /**
     * 同步项目的原证号信息
     * @param bdcXmDO
     * @throws Exception
     */
    @Override
    public void synchXmYzh(BdcXmDO bdcXmDO) throws Exception {
        if(bdcXmDO!=null){
            List<BdcXmLsgxDO> list=bdcXmLsgxService.queryBdcXmLsgxByXmid(bdcXmDO.getXmid(),null);
            if(CollectionUtils.isNotEmpty(list)){
                List<String> ycqzhList=new ArrayList<>();
                for(BdcXmLsgxDO bdcXmLsgxDO:list){
                    if(StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid())){
                        BdcXmDO ybdcXm=bdcXmService.queryBdcXmByPrimaryKey(bdcXmLsgxDO.getYxmid());
                        if(ybdcXm!=null){
                            //外联项目
                            if(CommonConstantUtils.SF_S_DM.equals(bdcXmLsgxDO.getWlxm())){
                                 //将产权的原证号拼接上(正常情况只有土地)
                                 if(bdcXmDO.getQllx()!=null && Arrays.asList(CommonConstantUtils.BDC_ZS_QLLX).contains(bdcXmDO.getQllx())
                                         && StringUtils.isNotBlank(ybdcXm.getBdcqzh()) && !ycqzhList.contains(ybdcXm.getBdcqzh())){
                                         ycqzhList.add(ybdcXm.getBdcqzh());
                                 }
                            }else{
                                String yzh=ybdcXm.getBdcqzh();
                                //登记小类在此配置中的读取原项目的原产权证号
                                if(CollectionUtils.isNotEmpty(yxmyzh) && yxmyzh.contains(bdcXmDO.getDjxl())){
                                    yzh=ybdcXm.getYcqzh();
                                }
                                if(StringUtils.isNotBlank(yzh) && !ycqzhList.contains(yzh)){
                                    ycqzhList.add(0,yzh);
                                }
                            }
                        }
                    }
                }
                bdcXmDO.setYcqzh(StringUtils.join(ycqzhList, ","));
                entityMapper.updateByPrimaryKeySelective(bdcXmDO);
            }
        }
    }

    /**
     * 流程同步权籍数据
     * @param gzlslid
     * @throws Exception
     */
    @Override
    public void synchLpbDataToLc(@NotBlank(message = "工作流实例ID不能为空") String gzlslid) throws Exception {
        List<BdcXmDO> xmList=bdcXmService.listBdcXm(gzlslid);
        List<BdcCshFwkgSlDO> listCshSl=bdcXmService.listBdCshSl(gzlslid);
        if(CollectionUtils.isNotEmpty(xmList) && CollectionUtils.isNotEmpty(listCshSl)){
            synchLpbData(xmList,listCshSl,true,null,true);
        }
    }

    /**
     * 项目同步权籍数据
     * @param xmid
     * @throws Exception
     */
    @Override
    public void synchLpbDataToXm(@NotBlank(message = "项目ID不能为空") String xmid) throws Exception {
        BdcXmDO bdcXmDO=entityMapper.selectByPrimaryKey(BdcXmDO.class,xmid);
        BdcCshFwkgSlDO bdcCshFwkgSlDO=entityMapper.selectByPrimaryKey(BdcCshFwkgSlDO.class,xmid);
        if(bdcXmDO!=null && bdcCshFwkgSlDO!=null){
            List<BdcXmDO> xmList=new ArrayList<>();
            xmList.add(bdcXmDO);
            List<BdcCshFwkgSlDO> fwkgList=new ArrayList<>();
            fwkgList.add(bdcCshFwkgSlDO);
            synchLpbData(xmList,fwkgList,true,null,null);
        }
    }

    /**
     * 项目同步权籍数据后的数据
     * @param xmid
     * @throws Exception
     */
    @Override
    public InitServiceDTO querySynchLpbData(@NotBlank(message = "项目ID不能为空") String xmid) throws Exception {
        InitServiceDTO initServiceDTO=null;
        BdcXmDO bdcXmDO=entityMapper.selectByPrimaryKey(BdcXmDO.class,xmid);
        BdcCshFwkgSlDO bdcCshFwkgSlDO=entityMapper.selectByPrimaryKey(BdcCshFwkgSlDO.class,xmid);
        if(bdcXmDO!=null && bdcCshFwkgSlDO!=null){
            List<BdcXmDO> xmList=new ArrayList<>();
            xmList.add(bdcXmDO);
            List<BdcCshFwkgSlDO> fwkgList=new ArrayList<>();
            fwkgList.add(bdcCshFwkgSlDO);
            Map<String, InitServiceDTO> mapData=synchLpbData(xmList,fwkgList,false,null,null);
            if(MapUtils.isNotEmpty(mapData)){
                initServiceDTO=mapData.get(xmid);
            }
        }
        return initServiceDTO;
    }
    @Override
    public InitServiceDTO querySynchYxmData(@NotBlank(message = "项目ID不能为空") String xmid) throws Exception {
        InitServiceDTO initServiceDTO=null;
        BdcXmDO bdcXmDO=entityMapper.selectByPrimaryKey(BdcXmDO.class,xmid);
        BdcCshFwkgSlDO bdcCshFwkgSlDO=entityMapper.selectByPrimaryKey(BdcCshFwkgSlDO.class,xmid);
        if(bdcXmDO!=null && bdcCshFwkgSlDO!=null){
            List<BdcXmDO> xmList=new ArrayList<>();
            xmList.add(bdcXmDO);
            List<BdcCshFwkgSlDO> fwkgList=new ArrayList<>();
            fwkgList.add(bdcCshFwkgSlDO);
            Map<String, InitServiceDTO> mapData=synchYxmData(xmList,fwkgList,false,null);
            if(MapUtils.isNotEmpty(mapData)){
                initServiceDTO=mapData.get(xmid);
            }
        }
        return initServiceDTO;
    }
    /**
     * 项目获取权籍数据对照的信息
     *
     * @param xmid
     * @return List<BdcQjtbxxDTO>
     * @throws Exception
     */
    @Override
    public List<BdcQjtbxxDTO> queryLpbDataDzxx(@NotBlank(message = "项目ID不能为空") String xmid) throws Exception {
        //对照后的数据
        List<BdcQjtbxxDTO> list=new ArrayList<>();
        //获取数据
        InitServiceDTO source=initDataService.queryYwsj(xmid);
        InitServiceDTO target=querySynchLpbData(xmid);
        if(source!=null || target!=null){
            if(CollectionUtils.isNotEmpty(target.getFieldNameSet())) {
                // 权籍对象字段值为空，但是登记字段有值的字段集合需要重新设置空，避免页面展示时候权籍为空数据展示了登记数据的问题
                // @see cn.gtmap.realestate.init.service.qlxx.InitBdcQlDataAbstractService.initQlxx
                BdcQl bdcQl = target.getBdcQl();
                Field[] fields = bdcQl.getClass().getDeclaredFields();
                for(Field field : fields) {
                    if(target.getFieldNameSet().contains(field.getName())) {
                        field.setAccessible(true);
                        field.set(bdcQl, null);
                    }
                }
            }

            //转换成普通对象
            SynchLpbxxDTO sourceXmxx=new SynchLpbxxDTO();
            BeanUtils.copyProperties(source, sourceXmxx);
            SynchLpbxxDTO targetXmxx=new SynchLpbxxDTO();
            BeanUtils.copyProperties(target, targetXmxx);
            //不覆盖时清空楼盘表相关数据
            if(!initBeanFactory.isLpbsjCoverDjb()){
                sourceXmxx.clearLbpData();
                targetXmxx.clearLbpData();
            }
            Boolean isFw=bdcBdcdyService.judgeIsFwByBdcdyh(sourceXmxx.getBdcXm().getBdcdyh());
            list=dealDTO(sourceXmxx,targetXmxx,isFw);
        }
        return list;
    }

    @Override
    public List<BdcSjdzDTO> queryLpbAndYxmDataDzxx(@NotBlank(message = "项目ID不能为空") String xmid) throws Exception {
        //对照后的数据
        List<BdcSjdzDTO> list=new ArrayList<>();
        //获取数据
        InitServiceDTO bdcValue=initDataService.queryYwsj(xmid);
        InitServiceDTO lpbValue=querySynchLpbData(xmid);
        InitServiceDTO yxmValue=querySynchYxmData(xmid);
        if(bdcValue!=null && (lpbValue!=null ||yxmValue!=null)){
            //转换成普通对象
            SynchLpbxxDTO bdcXmxx=new SynchLpbxxDTO();
            BeanUtils.copyProperties(bdcValue, bdcXmxx);
            SynchLpbxxDTO lpbXmxx=new SynchLpbxxDTO();
            if(lpbValue != null){
                BeanUtils.copyProperties(lpbValue, lpbXmxx);
            }
            SynchLpbxxDTO yxmXmxx=new SynchLpbxxDTO();
            if(yxmValue != null){
                BeanUtils.copyProperties(yxmValue, yxmXmxx);
            }

            Boolean isFw=bdcBdcdyService.judgeIsFwByBdcdyh(bdcValue.getBdcXm().getBdcdyh());
            list=dealSjdzDTO(bdcXmxx,lpbXmxx,yxmXmxx,isFw);
        }
        return list;
    }

    /**
     * 根据前台传递的对照信息进行同步
     * @param list
     * @param xmid
     * @throws Exception
     */
    @Override
    public void synchLpbDzDataToXm(List<BdcQjtbxxDTO> list, String xmid) throws Exception {
        if(CollectionUtils.isNotEmpty(list) && StringUtils.isNotBlank(xmid)){
            //获取配置
            MappingMetadata mappingMetadata = initDozerMapper.getMappingMetadata();
            //获取项目
            BdcXmDO bdcXmDO=entityMapper.selectByPrimaryKey(BdcXmDO.class,xmid);
            if(bdcXmDO!=null){
                //创建参数对象
                InitServiceQO initServiceQO = initServiceQoService.creatInitServiceQO();
                initServiceQO.setBdcXm(bdcXmDO);
                BdcXmFbDO bdcXmFb =entityMapper.selectByPrimaryKey(BdcXmFbDO.class,xmid);
                if(bdcXmFb != null){
                    initServiceQO.setBdcXmFb(bdcXmFb);
                }
                //赋值开关
                initServiceQO.setSfdzbflpbsj(true);
                //判断是否是房屋
                Boolean isFw=bdcBdcdyService.judgeIsFwByBdcdyh(bdcXmDO.getBdcdyh());
                //获取
                List<Class> classList=new ArrayList<>();
                Field[] fields = SynchLpbxxDTO.class.getDeclaredFields();
                for (Field resultField : fields) {
                    if(BdcQl.class.equals(resultField.getType())){
                        BdcQl bdcQl=bdcQllxService.queryQllxDO(xmid);
                        if(bdcQl!=null){
                            classList.add(bdcQl.getClass());
                        }
                    }else{
                        classList.add(resultField.getType());
                    }
                }
                //存储dozer目标的字段配置
                Set<String> dozerFieldName =new HashSet<>();
                for(BdcQjtbxxDTO bdcQjtbxxDTO:list){
                    dozerFieldName.add(bdcQjtbxxDTO.getField());
                }
                // 此处 获取 原对象为权籍不动产单元对象的对照关系
                List<ClassMappingMetadata> metaMappingList =null;
                if(isFw){
                    metaMappingList= mappingMetadata.getClassMappingsBySource(initServiceQO.getBdcdyDTO().getClass());
                }
                if(metaMappingList==null){
                    metaMappingList=new ArrayList<>();
                }
                if(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()!=null){
                    metaMappingList.addAll(mappingMetadata.getClassMappingsBySource(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO().getClass()));
                }
                if(CollectionUtils.isNotEmpty(metaMappingList)){
                    Map<String,String> mapingMap=new HashMap<>();
                    //获取配置
                    List<String> listMapping=initDozerMapper.getMappingFiles();
                    if(CollectionUtils.isNotEmpty(listMapping)){
                        for(String str:listMapping){
                            mapingMap.put(str.substring(str.lastIndexOf('/')+1),str);
                        }
                    }
                    Set<String> bdcdySet=new HashSet<>();
                    Set<String> djxxSet=new HashSet<>();
                    for(ClassMappingMetadata classMappingMetadata:metaMappingList){
                        Set<String> set=null;
                        //单元信息
                        if(isFw && classMappingMetadata.getSourceClass().equals(initServiceQO.getBdcdyDTO().getClass())){
                            set=bdcdySet;
                        }else{
                            set=djxxSet;
                        }
                        //在数据对象中的
                        if(classList.contains(classMappingMetadata.getDestinationClass())){
                            //解析后的dozer的函数param
                            Map<String,String> paramMap=getDozerXmlConvertParam(classMappingMetadata.getSourceClass(),classMappingMetadata.getDestinationClass(),mapingMap);
                            List<FieldMappingMetadata> fieldList=classMappingMetadata.getFieldMappings();
                            if(CollectionUtils.isNotEmpty(fieldList)){
                                //获取目标的字段名
                                for(FieldMappingMetadata fieldMappingMetadata:fieldList){
                                    //包含配置的话做处理
                                    if(dozerFieldName.contains(fieldMappingMetadata.getDestinationName())){
                                        String params=paramMap.get(fieldMappingMetadata.getDestinationName());
                                        set.addAll(Arrays.asList(params.split(",")));
                                    }
                                }
                            }
                        }
                    }
                    //配置处理后开始处理QO中的数据
                    clearWdzQjxx(djxxSet,initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO());
                    if(isFw){
                        // 在未对照的数据清除前，先判断是否项目类多幢，则需要先保留多幢信息
                        List<FwLjzDO> ljzList = dealFwLjz(initServiceQO);
                        // 处理权籍对象 把未对照的删掉
                        clearWdzQjxx(bdcdySet,initServiceQO.getBdcdyDTO());
                        // 添加多幢信息
                        if(CollectionUtils.isNotEmpty(ljzList)){
                            initServiceQO.getBdcdyResponseDTO().setLjzList(ljzList);
                        }
                    }
                    List<BdcXmDO> xmList=new ArrayList<>();
                    xmList.add(bdcXmDO);
                    List<BdcCshFwkgSlDO> fwkgList=new ArrayList<>();
                    BdcCshFwkgSlDO bdcCshFwkgSlDO=entityMapper.selectByPrimaryKey(BdcCshFwkgSlDO.class,bdcXmDO.getXmid());
                    fwkgList.add(bdcCshFwkgSlDO);
                    synchLpbData(xmList,fwkgList,true,initServiceQO,null);
                }
            }
        }
    }

    /**
     * 判断是否未项目类多幢，是项目类多幢则获取逻辑幢数据
     */
    private List<FwLjzDO> dealFwLjz(InitServiceQO initServiceQO){
        List<FwLjzDO> ljzList = new ArrayList<>();
        if(initServiceQO.getBdcdyDTO() instanceof  BdcdyResponseDTO){
            BdcdyResponseDTO bdcdyResponseDTO = (BdcdyResponseDTO) initServiceQO.getBdcdyDTO();
            if(CommonConstantUtils.FWLX_DUOZH.toString().equals(bdcdyResponseDTO.getBdcdyfwlx())){
                if(CollectionUtils.isNotEmpty(bdcdyResponseDTO.getLjzList())){
                    ljzList = bdcdyResponseDTO.getLjzList();
                }
            }
        }
        return ljzList;
    }

    /**
     * 清空流程原产权证号(后生成的)和不动产权证号
     *
     * @param gzlslid
     * @throws Exception
     */
    @Override
    public void clearBdcqzhAndYcqzh(@NotBlank(message = "工作流实例ID不能为空") String gzlslid) throws Exception {
        BdcXmDO param=new BdcXmDO();
        param.setGzlslid(gzlslid);
        BdcXmDO val=new BdcXmDO();
        val.setBdcqzh(StringUtils.EMPTY);
        int lclx=bdcXmService.bdcXmLx(gzlslid);
        boolean iszh = CommonConstantUtils.LCLX_PLZH.equals(lclx) || CommonConstantUtils.LCLX_ZH.equals(lclx);
        if (iszh) {
            val.setYcqzh(StringUtils.EMPTY);
        }
        entityMapper.updateByExampleSelectiveNotNull(val, entityMapper.objToExample(param));
        if (iszh) {
            synchLcYzh(gzlslid);
        }
    }

    /**
     * @param xmid
     * @throws Exception
     */
    @Override
    public void clearBdcqzhAndYcqzhByXmid(String xmid) throws Exception {
        BdcXmDO param = new BdcXmDO();
        param.setXmid(xmid);
        BdcXmDO val = new BdcXmDO();
        val.setBdcqzh(StringUtils.EMPTY);
        entityMapper.updateByExampleSelectiveNotNull(val, entityMapper.objToExample(param));
    }

    /**
     * 同步权籍数据
     *
     * @param xmList
     * @param listCshSl
     * @param rk
     * @throws Exception
     */
    private Map<String, InitServiceDTO> synchLpbData(List<BdcXmDO> xmList, List<BdcCshFwkgSlDO> listCshSl, boolean rk, InitServiceQO initServiceQO, Boolean tbqjsj) throws Exception {
        if (CollectionUtils.isNotEmpty(xmList)) {
            List<InitServiceQO> listQO = new ArrayList<>();
            //初始化实例集合
            if(CollectionUtils.isEmpty(listCshSl) || listCshSl.size()!=xmList.size()){
                throw new AppException("项目实例信息与项目信息不匹配,请查看!");
            }else{
                Map<String, BdcCshFwkgSlDO> cshslMap = listCshSl.stream().collect(Collectors.toMap(BdcCshFwkgSlDO::getId, a -> a,(k1, k2)->k1));
                //存储djh和地籍信息对照关系
                Map<String,DjxxResponseDTO> mapDjxx=new HashMap<>();
                //新的QO对象
                InitServiceQO creatQO=initServiceQO;
                for(BdcXmDO bdcXmDO:xmList){
                    boolean jzdjxx=false;
                    if(initServiceQO==null){
                        creatQO = initServiceQoService.creatInitServiceQO();
                        creatQO.setBdcXm(bdcXmDO);
                        jzdjxx=true;
                    }
                    BdcXmFbDO bdcXmFbDO =entityMapper.selectByPrimaryKey(BdcXmFbDO.class,bdcXmDO.getXmid());
                    if(bdcXmFbDO != null){
                        creatQO.setBdcXmFb(bdcXmFbDO);
                    }
                    creatQO.setSfzqlpbsj(true);
                    BdcCshFwkgSlDO bdcCshFwkgSlDO=cshslMap.get(bdcXmDO.getXmid());
                    //全部读取权籍数据
                    bdcCshFwkgSlDO.setQlsjly(Constants.QLSJLY_LPB);
                    //权利人和义务人的数据来源对比
                    if(bdcCshFwkgSlDO.getQlrsjly()!=null && !Constants.QLRSJLY_LPB.equals(bdcCshFwkgSlDO.getQlrsjly()) &&
                            CollectionUtils.isNotEmpty(synchqlr) && synchqlr.contains(bdcXmDO.getDjxl())){
                        bdcCshFwkgSlDO.setQlrsjly(Constants.QLRSJLY_LPB);
                    }
                    if(bdcCshFwkgSlDO.getYwrsjly()!=null && !Constants.QLRSJLY_LPB.equals(bdcCshFwkgSlDO.getYwrsjly()) &&
                            CollectionUtils.isNotEmpty(synchqlr) && synchqlr.contains(bdcXmDO.getDjxl())){
                        bdcCshFwkgSlDO.setYwrsjly(Constants.QLRSJLY_LPB);
                    }
                    creatQO.setBdcCshFwkgSl(bdcCshFwkgSlDO);
                    //获取地籍信息
                    if(jzdjxx){
                        getLpbDjxx(creatQO,mapDjxx);
                    }
                    creatQO.setSfdbqj(true);
                    if(Objects.isNull(tbqjsj)){
                        tbqjsj = false;
                    }
                    creatQO.setSfymtbqjsj(tbqjsj);
                    listQO.add(creatQO);
                }
                if(CollectionUtils.isNotEmpty(listQO)){
                    //初始化所有业务数据
                    InitResultDTO initResultDTO=initDataService.init(listQO,rk);
                    if(initResultDTO!=null){
                        xmList=initResultDTO.getBdcXmList();
                    }
                    //不入库的话直接返回
                    if(!rk){
                        return listQO.get(0).getServiceDataMap();
                    }
                    //回写信息到平台
                    if(CollectionUtils.isNotEmpty(xmList)){
                        bdcYwsjHxService.saveBdcYwsj(xmList.get(0).getGzlslid());
                    }
                }
            }
        }
        return null;
    }
    private Map<String, InitServiceDTO> synchYxmData(List<BdcXmDO> xmList,List<BdcCshFwkgSlDO> listCshSl,boolean rk,InitServiceQO initServiceQO) throws Exception {
        if(CollectionUtils.isNotEmpty(xmList)){
            List<InitServiceQO> listQO = new ArrayList<>();
            //初始化实例集合
            if(CollectionUtils.isEmpty(listCshSl) || listCshSl.size()!=xmList.size()){
                throw new AppException("项目实例信息与项目信息不匹配,请查看!");
            }else{
                Map<String, BdcCshFwkgSlDO> cshslMap = listCshSl.stream().collect(Collectors.toMap(BdcCshFwkgSlDO::getId, a -> a,(k1, k2)->k1));
                //新的QO对象
                InitServiceQO creatQO=initServiceQO;
                for(BdcXmDO bdcXmDO:xmList){
                    if(initServiceQO==null){
                        String yxmid = bdcXmService.queryYxmid(bdcXmDO.getXmid());
                        if(StringUtils.isBlank(yxmid)){
                            continue;
                        }
                        creatQO = initServiceQoService.creatInitServiceQO();
                        creatQO.setBdcXm(bdcXmDO);
                        creatQO.setYxmid(yxmid);
                    }
                    BdcCshFwkgSlDO bdcCshFwkgSlDO=cshslMap.get(bdcXmDO.getXmid());
                    //全部读取权籍数据
                    bdcCshFwkgSlDO.setQlsjly(Constants.QLSJLY_YXM);
                    creatQO.setBdcCshFwkgSl(bdcCshFwkgSlDO);
                    listQO.add(creatQO);
                }
                if(CollectionUtils.isNotEmpty(listQO)){
                    //初始化所有业务数据
                    InitResultDTO initResultDTO=initDataService.init(listQO,rk);
                    if(initResultDTO!=null){
                        xmList=initResultDTO.getBdcXmList();
                    }
                    //不入库的话直接返回
                    if(!rk){
                        return listQO.get(0).getServiceDataMap();
                    }
                    //回写信息到平台
                    if(CollectionUtils.isNotEmpty(xmList)){
                        bdcYwsjHxService.saveBdcYwsj(xmList.get(0).getGzlslid());
                    }
                }
            }
        }
        return null;
    }
    /**
     *获取djxx
     * @param initServiceQO
     * @param mapDjxx
     * @return
     */
    private DjxxResponseDTO getLpbDjxx(InitServiceQO initServiceQO, Map<String,DjxxResponseDTO> mapDjxx){
        //地籍信息对象
        DjxxResponseDTO djxxResponseDTO=null;
        //正常不动产单元号调用权籍数据
        if(StringUtils.isNotBlank(initServiceQO.getBdcdyh()) && initServiceQO.getBdcdyh().length()==28){
            String djh=initServiceQO.getBdcdyh().substring(0,19);
            if(mapDjxx.containsKey(djh)){
                djxxResponseDTO=mapDjxx.get(djh);
            }else{
                djxxResponseDTO = djxxFeignService.queryDjxx(initServiceQO.getBdcdyh(),"",initServiceQO.getQjgldm());
                if (djxxResponseDTO == null) {
                    djxxResponseDTO = djxxFeignService.queryHDjxxByBdcdyh(initServiceQO.getBdcdyh(),initServiceQO.getQjgldm());
                    if (djxxResponseDTO == null) {
                        throw new AppException("没有获取到对应的调查表权籍数据！"+initServiceQO.getBdcdyh());
                    }
                }
                mapDjxx.put(djh,djxxResponseDTO);
            }
        }else{
            //非正规的赋值空对象
            djxxResponseDTO=new DjxxResponseDTO();
        }
        initServiceQO.setDjxxResponseDTO(djxxResponseDTO);
        return djxxResponseDTO;
    }

    /**
     * 组织对照后的数据
     * @param source
     * @param target
     * @return
     * @throws IllegalAccessException
     */
    private List<BdcQjtbxxDTO> dealDTO(SynchLpbxxDTO source, SynchLpbxxDTO target,Boolean isFw) throws IllegalAccessException {
        List<BdcQjtbxxDTO> list=new ArrayList<>();
        //获取配置
        MappingMetadata mappingMetadata = initDozerMapper.getMappingMetadata();
        //创建参数对象
        InitServiceQO initServiceQO = initServiceQoService.creatInitServiceQO();
        initServiceQO.setBdcXm(source.getBdcXm());
        initServiceQO.setBdcXmFb(source.getBdcXmFb());
        List<Class> classList=new ArrayList<>();
        Field[] fields = SynchLpbxxDTO.class.getDeclaredFields();
        for (Field resultField : fields) {
            resultField.setAccessible(true);
            Object targetObj=resultField.get(target);
            if(targetObj!=null){
                classList.add(targetObj.getClass());
            }
        }
        //存储dozer目标的字段配置
        Set<String> dozerFieldName =new HashSet<>();
        // 此处 获取 原对象为权籍不动产单元对象的对照关系
        List<ClassMappingMetadata> metaMappingList = null;
        if(isFw){
            metaMappingList=mappingMetadata.getClassMappingsBySource(initServiceQO.getBdcdyDTO().getClass());
        }
        if(metaMappingList==null){
            metaMappingList=new ArrayList<>();
        }
        if(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()!=null){
            metaMappingList.addAll(mappingMetadata.getClassMappingsBySource(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO().getClass()));
        }
        if(CollectionUtils.isNotEmpty(metaMappingList)){
            for(ClassMappingMetadata classMappingMetadata:metaMappingList){
                //在数据对象中的
                if(classList.contains(classMappingMetadata.getDestinationClass())){
                    List<FieldMappingMetadata> fieldList=classMappingMetadata.getFieldMappings();
                    if(CollectionUtils.isNotEmpty(fieldList)){
                        //获取目标的字段名
                        for(FieldMappingMetadata fieldMappingMetadata:fieldList){
                            dozerFieldName.add(fieldMappingMetadata.getDestinationName());
                        }
                    }
                }
            }
        }
        //要封装的结构
        Field[] lpbxxFields = SynchLpbxxDTO.class.getDeclaredFields();
        for (Field resultField : lpbxxFields) {
            resultField.setAccessible(true);
            Object sourceObj=resultField.get(source);
            Object targetObj=resultField.get(target);
            if(sourceObj==null && targetObj==null){
                continue;
            }
            Class resultClass=null;
            String table;
            String tableDesc;
            if(sourceObj!=null){
                resultClass=sourceObj.getClass();
                table=sourceObj.getClass().getAnnotation(Table.class).name();
                tableDesc=sourceObj.getClass().getAnnotation(ApiModel.class).description();
            }else{
                resultClass=targetObj.getClass();
                table=targetObj.getClass().getAnnotation(Table.class).name();
                tableDesc=targetObj.getClass().getAnnotation(ApiModel.class).description();
            }
            //取单个对象的所有字段
            Field[] dtoFields =resultClass.getDeclaredFields();
            for (Field dtoField : dtoFields) {
                //在配置中的读取
                if(dozerFieldName.contains(dtoField.getName())){
                    dozerFieldName.remove(dtoField.getName());
                    dtoField.setAccessible(true);
                    BdcQjtbxxDTO bdcQjtbxxDTO=new BdcQjtbxxDTO();
                    bdcQjtbxxDTO.setTable(table);
                    bdcQjtbxxDTO.setTableDesc(tableDesc);
                    bdcQjtbxxDTO.setField(dtoField.getName());
                    String sourceVal;
                    String targetVal;
                    Zd zd=dtoField.getAnnotation(Zd.class);
                    if(zd!=null){
                        sourceVal= sourceObj==null ? StringUtils.EMPTY : StringUtils.defaultIfBlank(bdcZdCache.getFeildValue(zd.tableClass().getSimpleName(),dtoField.get(sourceObj),zd.tableClass()),dealDataFormat(dtoField.get(sourceObj)));
                        targetVal= targetObj==null ? StringUtils.EMPTY : StringUtils.defaultIfBlank(bdcZdCache.getFeildValue(zd.tableClass().getSimpleName(),dtoField.get(targetObj),zd.tableClass()),dealDataFormat(dtoField.get(targetObj)));
                    }else{
                        sourceVal= sourceObj==null ? StringUtils.EMPTY : dealDataFormat(dtoField.get(sourceObj));
                        targetVal= targetObj==null ? StringUtils.EMPTY : dealDataFormat(dtoField.get(targetObj));
                    }
                    bdcQjtbxxDTO.setFieldDesc(dtoField.getAnnotation(ApiModelProperty.class).value());
                    bdcQjtbxxDTO.setSourceVal(sourceVal);
                    bdcQjtbxxDTO.setTargetVal(targetVal);
                    bdcQjtbxxDTO.setSfxd(StringUtils.equals(bdcQjtbxxDTO.getSourceVal(),bdcQjtbxxDTO.getTargetVal()) ? CommonConstantUtils.SF_S_DM : CommonConstantUtils.SF_F_DM);
                    list.add(bdcQjtbxxDTO);
                }
            }
        }
        return list;
    }
    /**
     * 组织对照后的数据
     * @param
     * @param
     * @return
     * @throws IllegalAccessException
     */
    private List<BdcSjdzDTO> dealSjdzDTO(SynchLpbxxDTO bdcValue, SynchLpbxxDTO lpbValue, SynchLpbxxDTO yxmValue,Boolean isFw) throws IllegalAccessException {
        List<BdcSjdzDTO> list=new ArrayList<>();
        //获取配置
        MappingMetadata mappingMetadata = initDozerMapper.getMappingMetadata();
        //创建参数对象
        InitServiceQO initServiceQO = initServiceQoService.creatInitServiceQO();
        initServiceQO.setBdcXm(bdcValue.getBdcXm());
        List<Class> classList=new ArrayList<>();
        Field[] fields = SynchLpbxxDTO.class.getDeclaredFields();
        for (Field resultField : fields) {
            resultField.setAccessible(true);
            Object targetObj=resultField.get(lpbValue);
            if(targetObj ==null){
                targetObj=resultField.get(yxmValue);
            }
            if(targetObj !=null){
                classList.add(targetObj.getClass());
            }
        }
        //存储dozer目标的字段配置
        Set<String> dozerFieldName =new HashSet<>();
        // 此处 获取 原对象为权籍不动产单元对象的对照关系
        List<ClassMappingMetadata> metaMappingList = null;
        if(isFw){
            metaMappingList=mappingMetadata.getClassMappingsBySource(initServiceQO.getBdcdyDTO().getClass());
        }
        if(metaMappingList==null){
            metaMappingList=new ArrayList<>();
        }
        if(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO()!=null){
            metaMappingList.addAll(mappingMetadata.getClassMappingsBySource(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO().getClass()));
        }
        // 原权利对照
        if(bdcValue.getBdcQl()!=null){
            metaMappingList.addAll(mappingMetadata.getClassMappingsBySource(bdcValue.getBdcQl().getClass()));
        }
        if(CollectionUtils.isNotEmpty(metaMappingList)){
            for(ClassMappingMetadata classMappingMetadata:metaMappingList){
                //在数据对象中的
                if(classList.contains(classMappingMetadata.getDestinationClass())){
                    List<FieldMappingMetadata> fieldList=classMappingMetadata.getFieldMappings();
                    if(CollectionUtils.isNotEmpty(fieldList)){
                        //获取目标的字段名
                        for(FieldMappingMetadata fieldMappingMetadata:fieldList){
                            dozerFieldName.add(fieldMappingMetadata.getDestinationName());
                        }
                    }
                }
            }
        }
        //要封装的结构
        Field[] lpbxxFields = SynchLpbxxDTO.class.getDeclaredFields();
        for (Field resultField : lpbxxFields) {
            resultField.setAccessible(true);
            Object bdcObj=resultField.get(bdcValue);
            Object lpbObj=resultField.get(lpbValue);
            Object yxmObj=resultField.get(yxmValue);
            if(bdcObj==null && lpbObj==null && yxmObj==null){
                continue;
            }
            Class resultClass=null;
            String table;
            String tableDesc;
            if(bdcObj!=null){
                resultClass=bdcObj.getClass();
                table=bdcObj.getClass().getAnnotation(Table.class).name();
                tableDesc=bdcObj.getClass().getAnnotation(ApiModel.class).description();
            }else if(lpbObj!=null){
                resultClass=lpbObj.getClass();
                table=lpbObj.getClass().getAnnotation(Table.class).name();
                tableDesc=lpbObj.getClass().getAnnotation(ApiModel.class).description();
            }else{
                resultClass=yxmObj.getClass();
                table=yxmObj.getClass().getAnnotation(Table.class).name();
                tableDesc=yxmObj.getClass().getAnnotation(ApiModel.class).description();
            }
            //取单个对象的所有字段
            Field[] dtoFields =resultClass.getDeclaredFields();
            for (Field dtoField : dtoFields) {
                //在配置中的读取
                if(dozerFieldName.contains(dtoField.getName())){
                    dozerFieldName.remove(dtoField.getName());
                    dtoField.setAccessible(true);
                    BdcSjdzDTO bdcSjdzDTO=new BdcSjdzDTO();
                    bdcSjdzDTO.setTable(table);
                    bdcSjdzDTO.setTableDesc(tableDesc);
                    bdcSjdzDTO.setField(dtoField.getName());
                    String bdcVal;
                    String lpbVal;
                    String yxmVal;
                    Zd zd=dtoField.getAnnotation(Zd.class);
                    if(zd!=null){
                        bdcVal= bdcObj==null ? StringUtils.EMPTY : StringUtils.defaultIfBlank(bdcZdCache.getFeildValue(zd.tableClass().getSimpleName(),dtoField.get(bdcObj),zd.tableClass()),dealDataFormat(dtoField.get(bdcObj)));
                        lpbVal= lpbObj==null ? StringUtils.EMPTY : StringUtils.defaultIfBlank(bdcZdCache.getFeildValue(zd.tableClass().getSimpleName(),dtoField.get(lpbObj),zd.tableClass()),dealDataFormat(dtoField.get(lpbObj)));
                        yxmVal= yxmObj==null ? StringUtils.EMPTY : StringUtils.defaultIfBlank(bdcZdCache.getFeildValue(zd.tableClass().getSimpleName(),dtoField.get(yxmObj),zd.tableClass()),dealDataFormat(dtoField.get(yxmObj)));
                    }else{
                        bdcVal= bdcObj==null ? StringUtils.EMPTY : dealDataFormat(dtoField.get(bdcObj));
                        lpbVal= lpbObj==null ? StringUtils.EMPTY : dealDataFormat(dtoField.get(lpbObj));
                        yxmVal= yxmObj==null ? StringUtils.EMPTY : dealDataFormat(dtoField.get(yxmObj));
                    }
                    bdcSjdzDTO.setFieldDesc(dtoField.getAnnotation(ApiModelProperty.class).value());
                    bdcSjdzDTO.setBdcVal(bdcVal);
                    bdcSjdzDTO.setLpbVal(lpbVal);
                    bdcSjdzDTO.setYxmVal(yxmVal);
                    bdcSjdzDTO.setSfxd(StringUtils.equals(bdcSjdzDTO.getBdcVal(),bdcSjdzDTO.getLpbVal()) && StringUtils.equals(bdcSjdzDTO.getBdcVal(),bdcSjdzDTO.getYxmVal())
                            ? CommonConstantUtils.SF_S_DM : CommonConstantUtils.SF_F_DM);
                    list.add(bdcSjdzDTO);
                }
            }
        }
        return list;
    }

    /**
     * 处理返回数据
     * @param obj
     * @return
     */
    private String dealDataFormat(Object obj){
        String result=StringUtils.EMPTY;
        if(obj!=null){
            if(obj instanceof Date){
                result=DateUtils.formateYmdZw((Date) obj);
            }else{
                result=obj.toString();
            }
        }
        return result;
    }


    /**
     * 解析dozer的配置xml
     * @param source
     * @param target
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    private Map<String,String> getDozerXmlConvertParam(Class source,Class target,Map<String,String> mapingMap) throws JDOMException, IOException {
        Map<String,String> map=new HashMap<>();
        SAXBuilder builder = new SAXBuilder();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        org.springframework.core.io.Resource resource=resolver.getResource(mapingMap.get("Init"+target.getSimpleName()+".xml"));
        //获得文档对象
        Document document =builder.build(resource.getURI().toString());
        //取的根元素
        Element root = document.getRootElement();
        Namespace namespace=root.getNamespace();
        List<Element> listElement=root.getChildren("mapping",namespace);
        if(CollectionUtils.isNotEmpty(listElement)){
            for(Element element:listElement){
                String sour=element.getChild("class-a",namespace).getText();
                String dest=element.getChild("class-b",namespace).getText();
                //全相等的情况记录配置
                if(StringUtils.equals(sour,source.getName()) && StringUtils.equals(dest,target.getName())){
                    List<Element> fieldElements= element.getChildren("field",namespace);
                    if(CollectionUtils.isNotEmpty(fieldElements)){
                        for(Element ele:fieldElements){
                            String a=ele.getChild("a",namespace).getText();
                            String b=ele.getChild("b",namespace).getText();
                            String param=ele.getAttributeValue("custom-converter-param");
                            //不为空并且非数字的
                            if(StringUtils.isNotBlank(param) && !NumberUtils.isNumber(param.replaceAll(",",""))){
                                map.put(b,param+","+a);
                            }else{
                                map.put(b,a);
                            }
                        }
                    }
                }
            }
        }
        return  map;
    }

    /**
     * 处理权籍对象 把未对照的删掉
     * @param zdSet
     * @param obj
     */
    private void  clearWdzQjxx(Set<String> zdSet,Object obj) throws IllegalAccessException {
        if(obj!=null){
            Field[] djxxFields=obj.getClass().getDeclaredFields();
            for(Field field : djxxFields){
                if(zdSet.isEmpty() || !zdSet.contains(field.getName())){
                    field.setAccessible(true);
                    field.set(obj,null);
                }
            }
        }
    }
}
