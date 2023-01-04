package cn.gtmap.realestate.init.service.xmxx.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.building.DjDcbResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.GzwDcbResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.JyqDkDcbResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.NydDjdcbResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxlDjyyQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQjgldmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.BeansResolveUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcBdcdyService;
import cn.gtmap.realestate.init.core.service.BdcDjxlDjyyGxService;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.xmxx.InitBdcXmAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import cn.gtmap.realestate.init.util.DozerUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.metadata.ClassMappingMetadata;
import org.dozer.metadata.FieldMappingMetadata;
import org.dozer.metadata.MappingMetadata;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 初始化不动产项目信息的服务实现
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/8.
 * @description
 */
@Service
public class InitBdcXmServiceImpl extends InitBdcXmAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitBdcXmServiceImpl.class);
    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private BdcBdcdyService bdcBdcdyService;
    @Autowired
    private InitServiceQoService initServiceQoService;
    @Autowired
    private BdcDjxlDjyyGxService bdcDjxlDjyyGxService;

    @Resource(name = "initDozerMapper")
    private DozerBeanMapper initDozerMapper;
    /**
     * 原证号读取的登记小类
     */
    @Value("#{'${init.yxmyzh}'.split(',')}")
    private List<String> yxmyzh;
    /**
     * sqfbcz赋默认值（否）的登记小类
     */
    @Value("#{'${djxl.sqfbczsetf:}'.split(',')}")
    private List<String> sqfbczsetf;

    /**
     * 没有默认登记原因时，是否生成登记原因,默认为true
     */
    @Value("${init.djyy.bmrsfsc:true}")
    private boolean bmrsfscdjyy;

    @Override
    public String getVal() {
        return Constants.DEFAULT;
    }

    @Override
    public InitServiceDTO init(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        BdcXmDO bdcXmDO = initBdcXm(initServiceQO,initServiceDTO);
        if (initServiceDTO == null) {
            initServiceDTO = new InitServiceDTO();
        }
        initServiceDTO.setBdcXm(bdcXmDO);
        return initServiceDTO;
    }

    @Override
//    @AuditLog(event = "初始化不动产项目信息",response = true,names = {"initServiceQO"})
    public BdcXmDO initBdcXm(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) {
        BdcXmDO newBdcXmDO = new BdcXmDO();
        newBdcXmDO.setXmid(initServiceQO.getXmid());
        boolean synlpb=initServiceQO.isSfzqlpbsj();
        // 当不动产项目信息存在时
        if (initServiceQO.getBdcXm() != null) {
            // 传入参数赋值
            dozerUtils.sameBeanDateConvert(initServiceQO.getBdcXm(), newBdcXmDO, false);
            //非抓取楼盘表数据做处理
            if(!synlpb){
                //项目表默认值赋值
                dozerUtils.initBeanDateConvert(initServiceQO.getBdcXm(), newBdcXmDO, "default");
                //不生成权利的
                if(!CommonConstantUtils.SF_S_DM.equals(initServiceQO.getBdcCshFwkgSl().getSfscql())){
                    newBdcXmDO.setSqfbcz(CommonConstantUtils.SF_F_DM);
                }

                List<String> sqfbczValue = this.getSqfbczValue(initServiceQO);
                if(CollectionUtils.isNotEmpty(sqfbczValue) && sqfbczValue.contains(newBdcXmDO.getDjxl())){
                    newBdcXmDO.setSqfbcz(CommonConstantUtils.SF_F_DM);
                }
            }
        } else {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        String qlsjly=initServiceQO.getBdcCshFwkgSl().getQlsjly();
        //数据来源为空的给予默认以原项目为主
        if(StringUtils.isBlank(qlsjly)){
            qlsjly="1,2";
        }
        for(String ly:qlsjly.split(",")){
            if(StringUtils.equals(Constants.QLSJLY_LPB,ly)){
                readLpbData(newBdcXmDO,initServiceQO);
            }else if(StringUtils.equals(Constants.QLSJLY_WLZS_HFTDZ,ly)){
                readWlzsData(newBdcXmDO,initServiceQO);
            }else{
                readYxmData(newBdcXmDO,initServiceQO);
            }
        }
        //赋值不动产单元唯一编号
        dealBdcdywybh(newBdcXmDO,initServiceQO);

        //检测权籍中无值但是目标对象中有值的
        if(initServiceQO.isSfymtbqjsj()) {
            convertNull(initServiceQO, initServiceDTO, newBdcXmDO);
        }

        //非抓取楼盘表数据做处理
        if(!synlpb){
            // 根据配置信息为不动产项目设置
            setBdcXmByConfig(newBdcXmDO, initServiceQO);
            //证明类的赋值分别持证为否
            if(ArrayUtils.contains(CommonConstantUtils.BDC_ZM_QLLX,newBdcXmDO.getQllx())){
                newBdcXmDO.setSqfbcz(CommonConstantUtils.SF_F_DM);
            }
        }
        //受理项目不为空的话做对照
        if(initServiceQO.getBdcSlXmDO()!=null){
            //有上一手，档案归属地，区县代码取上一手
            if(StringUtils.isNotBlank(initServiceQO.getYxmid())){
                initServiceQO.getBdcSlXmDO().setDagsd(null);
            }
            dozerUtils.initBeanDateConvert(initServiceQO.getBdcSlXmDO(),newBdcXmDO);
        }
        //第三方信息不为空的话做对照
        if(initServiceQO.getDsfSlxxDTO()!=null){
            dozerUtils.initBeanDateConvert(initServiceQO.getDsfSlxxDTO(),newBdcXmDO);
        }



        //更新项目信息
        initServiceQO.setBdcXm(newBdcXmDO);
        //更新权利类型信息
        initServiceQO.getBdcCshFwkgSl().setQllx(newBdcXmDO.getQllx());
        //增量初始化获取zsxh
        if(CommonConstantUtils.SF_S_DM.equals(initServiceQO.getBdcCshFwkgSl().getSfzlcsh()) && MapUtils.isNotEmpty(initServiceQO.getCshFwkgSlDataMap())){
            List<BdcCshFwkgSlDO> list=initServiceQO.getCshFwkgSlDataMap().get(newBdcXmDO.getDjxl());
            if(CollectionUtils.isNotEmpty(list)){
                initServiceQO.getBdcCshFwkgSl().setZsxh(list.get(0).getZsxh());
            }
        }
        return newBdcXmDO;
    }

    /**
     * 同步权籍时有可能会要求如果权籍为null但是登记不为null则覆盖
     * 1.新建对象单独做dozer对照
     * 2.获取使用的dozer对照中有的对照的字段
     * 3.根据字段对比正常对照的对象和只对照了权籍数据的对象之间的差别
     * 4.将差异字段存储
     *
     * @param initServiceQO
     * @param initServiceDTO
     * @param newBdcXmDO
     */
    private void convertNull(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO, BdcXmDO newBdcXmDO) {
        LOGGER.info("对比权籍为空的数据:{},{},当前数据{}", JSON.toJSONString(initServiceQO),
                JSON.toJSONString(initServiceDTO),
                JSON.toJSONString(newBdcXmDO));
        String qlsjly=initServiceQO.getBdcCshFwkgSl().getQlsjly();
        if(StringUtils.isBlank(qlsjly)){
            return;
        }
        try {
            for(String ly:qlsjly.split(",")){
                if(StringUtils.equals(Constants.QLSJLY_LPB,ly)){
                    //防止这些值为null造成dozer不对照比如bdcdyh
                    //或者是一些不做修改的敏感的字段如qllx
                    BdcXmDO newQjBdcXmDO = new BdcXmDO();
                    newQjBdcXmDO.setXmid(newBdcXmDO.getXmid());
                    newQjBdcXmDO.setGzlslid(newBdcXmDO.getGzlslid());
                    newQjBdcXmDO.setSlbh(newBdcXmDO.getSlbh());
                    newQjBdcXmDO.setQllx(newBdcXmDO.getQllx());
                    newQjBdcXmDO.setBdcdyh(newBdcXmDO.getBdcdyh());
                    newQjBdcXmDO.setXmly(newBdcXmDO.getXmly());
                    newQjBdcXmDO.setQszt(newBdcXmDO.getQszt());
                    readLpbData(newQjBdcXmDO,initServiceQO);
                    LOGGER.info("对比权籍为空的数据,单权籍对比后的数据为{}",
                            JSON.toJSONString(newQjBdcXmDO));
                    MappingMetadata mappingMetadata = initDozerMapper.getMappingMetadata();
                    Boolean isFw = bdcBdcdyService.judgeIsFwByBdcdyh(newBdcXmDO.getBdcdyh());
                    // 此处 获取 原对象为权籍不动产单元对象的对照关系
                    List<ClassMappingMetadata> metaMappingList = new ArrayList<>();
                    // 根据土地信息为不动产项目赋值
                    if (initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() != null) {
                        metaMappingList.addAll(mappingMetadata.getClassMappingsBySource(
                                initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO().getClass())
                        );
                    }
                    if (isFw) {
                        //赋值房屋户室信息 根据不动产单元为不动产项目设置
                        if(initServiceQO.getBdcdyDTO() instanceof GzwDcbResponseDTO
                                && initServiceQO.getGzwDcbResponseDTO().getGzwDcbDO() != null){
                            metaMappingList.addAll(mappingMetadata.getClassMappingsBySource(
                                            initServiceQO.getGzwDcbResponseDTO().getGzwDcbDO().getClass()
                                    )
                            );
                        }else {
                            metaMappingList.addAll(mappingMetadata.getClassMappingsBySource(
                                            initServiceQO.getBdcdyDTO().getClass()
                                    )
                            );
                        }
                    }else{
                        //非房屋的做一些宗地信息对照
                        if (initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() != null) {
                            metaMappingList.addAll(mappingMetadata.getClassMappingsBySource(
                                            initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO().getClass()
                                    )
                            );
                        }
                    }
                    LOGGER.info("对比权籍为空的数据,使用权籍对照的字段为权籍对照的{}",
                            JSON.toJSONString(metaMappingList));
                    Set<String> fieldNameList = BeansResolveUtils.getSourceNullButTargetedNotNullFieldWithDozer(newQjBdcXmDO,
                            newBdcXmDO,
                            metaMappingList);
                    initServiceDTO.getXmFieldNameSet().addAll(fieldNameList);
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("对比权籍为空的数据错误：{}", e.getMessage());
        }
    }

    /**
     * 获取不同区划对应分别持证配置
     * @param initServiceQO
     * @return {List} 分别持证默认设置否的登记小类
     */
    private List<String> getSqfbczValue(InitServiceQO initServiceQO) {
        BdcQjgldmQO bdcQjgldmQO = new BdcQjgldmQO();
        bdcQjgldmQO.setXmid(initServiceQO.getXmid());
        bdcQjgldmQO.setBdcdyh(initServiceQO.getBdcdyh());
        return (List<String>) Container.getBean(BdcConfigUtils.class).getConfigValueByQjgldm("djxl.sqfbczsetf", sqfbczsetf, initServiceQO.getQjgldm(), bdcQjgldmQO);
    }

    /**
     * 对不动产信息进行配置
     *
     * @param bdcXmDO
     * @return BdcXmDO
     */
    private BdcXmDO setBdcXmByConfig(BdcXmDO bdcXmDO,InitServiceQO initServiceQO) {
        if (StringUtils.isNotBlank(bdcXmDO.getDjxl())) {
            //存储配置
            Map<String, Map<String, Object>> map=initServiceQO.getLcpzMap();
            //判断是否有当前登记小类的登记类型关系数据
            if(map.get("djlx")!=null && map.get("djlx").containsKey(bdcXmDO.getDjxl())){
                bdcXmDO.setDjlx(Integer.parseInt(map.get("djlx").get(bdcXmDO.getDjxl()).toString()));
            }else{
                // 获取登记类型
                BdcDjlxDjxlGxDO bdcDjlxYwlxGx = new BdcDjlxDjxlGxDO();
                bdcDjlxYwlxGx.setDjxl(bdcXmDO.getDjxl());
                List<BdcDjlxDjxlGxDO> bdcDjlxYwlxGxList = entityMapper.selectByObj(bdcDjlxYwlxGx);
                if (CollectionUtils.isNotEmpty(bdcDjlxYwlxGxList)) {
                    bdcXmDO.setDjlx(bdcDjlxYwlxGxList.get(0).getDjlx());
                    //存入
                    if(map.get("djlx")!=null && bdcDjlxYwlxGxList.get(0).getDjlx()!=null){
                        map.get("djlx").put(bdcXmDO.getDjxl(),bdcDjlxYwlxGxList.get(0).getDjlx());
                    }
                }else{
                    throw new AppException("登记小类和登记类型的关系表缺少配置,请查看!");
                }
            }

            if(StringUtils.isBlank(bdcXmDO.getDjyy())){
                //判断是否有当前登记小类的登记原因关系数据
                if(map.get("djyy")!=null && map.get("djyy").containsKey(bdcXmDO.getDjxl())){
                    if(map.get("djyy").get(bdcXmDO.getDjxl())!=null){
                        bdcXmDO.setDjyy(map.get("djyy").get(bdcXmDO.getDjxl()).toString());
                    }
                }else{
                    // 获取登记原因
                    BdcDjxlDjyyQO bdcYwlxDjyyGx = new BdcDjxlDjyyQO();
                    bdcYwlxDjyyGx.setDjxl(bdcXmDO.getDjxl());
                    bdcYwlxDjyyGx.setGzldyid(bdcXmDO.getGzldyid());
                    List<BdcDjxlDjyyGxDO> bdcYwlxDjyyGxList = bdcDjxlDjyyGxService.listBdcDjxlDjyyGxWithParam(bdcYwlxDjyyGx);
                    if (CollectionUtils.isNotEmpty(bdcYwlxDjyyGxList)) {
                        for(BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO:bdcYwlxDjyyGxList){
                            if(CommonConstantUtils.SF_S_DM.equals(bdcDjxlDjyyGxDO.getSfmr())){
                                bdcXmDO.setDjyy(StringUtils.defaultString(bdcDjxlDjyyGxDO.getDjyy(), StringUtils.EMPTY));
                                break;
                            }else if(bmrsfscdjyy){
                                //不配置默认是否生成登记原因
                                bdcXmDO.setDjyy(StringUtils.defaultString(bdcDjxlDjyyGxDO.getDjyy(), StringUtils.EMPTY));
                            }
                        }
                        //存入
                        if(map.get("djyy")!=null &&StringUtils.isNotBlank(bdcXmDO.getDjyy())){
                            map.get("djyy").put(bdcXmDO.getDjxl(),bdcXmDO.getDjyy());
                        }
                    }
                }
            }

        }
        return bdcXmDO;
    }


    /**
     * 读取信息从楼盘表数据中
     * @param newBdcXmDO
     * @param initServiceQO
     * @return
     */
    private BdcXmDO readLpbData(BdcXmDO newBdcXmDO, InitServiceQO initServiceQO){
        //房屋的话就清掉宗地bdcdywybh
        Boolean isFw = bdcBdcdyService.judgeIsFwByBdcdyh(newBdcXmDO.getBdcdyh());
        // 根据土地信息为不动产项目赋值
        if (initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() != null) {

            dozerUtils.initBeanDateConvert(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO(), newBdcXmDO);
        }
        if (isFw) {
            //赋值房屋户室信息 根据不动产单元为不动产项目设置
            if(initServiceQO.getBdcdyDTO() instanceof GzwDcbResponseDTO &&initServiceQO.getGzwDcbResponseDTO().getGzwDcbDO() != null){
                dozerUtils.initBeanDateConvert(initServiceQO.getGzwDcbResponseDTO().getGzwDcbDO(),newBdcXmDO);
            }else {
                dozerUtils.initBeanDateConvert(initServiceQO.getBdcdyDTO(), newBdcXmDO);
            }
        }else{
            //非房屋的做一些宗地信息对照
            if (initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() != null) {
                dozerUtils.initBeanDateConvert(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO(), newBdcXmDO,initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO().getClass().getSimpleName());
            }
        }
        if(CommonConstantUtils.QLLX_TDCBNYDSYQ.equals(newBdcXmDO.getQllx()) ||CommonConstantUtils.QLLX_NYDJYQ.equals(newBdcXmDO.getQllx())){
            //获取承包方或经营方信息 给项目表jyhth赋值
            dealJyhth(initServiceQO,null,newBdcXmDO);
        }
        return newBdcXmDO;
    }

    /**
     * 读取信息从原项目数据中
     * @param newBdcXmDO
     * @param initServiceQO
     * @return
     */
    private BdcXmDO readYxmData(BdcXmDO newBdcXmDO, InitServiceQO initServiceQO){
        // 当原项目id不为空时
        if (StringUtils.isNotBlank(initServiceQO.getYxmid())) {
            readYxmDataByYxmid(initServiceQO.getYxmid(),newBdcXmDO,initServiceQO);
        }else if(CommonConstantUtils.QLSJLY_YTQL.equals(initServiceQO.getBdcCshFwkgSl().getQlsjly())){
            readYxmDataByYxmid(initServiceQoService.getYtqlXmid(initServiceQO),newBdcXmDO,initServiceQO);
        }
        return newBdcXmDO;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 从原项目数据中读取信息
     */
    private BdcXmDO readYxmDataByYxmid(String yxmid,BdcXmDO newBdcXmDO, InitServiceQO initServiceQO){
        BdcXmDO yBdcXm =initServiceQoService.queryYbdcxm(yxmid,initServiceQO);
        if (yBdcXm != null) {
            // 通过dozer对数据进行赋值
            dozerUtils.initBeanDateConvert(yBdcXm, newBdcXmDO);
            //登记小类在此配置中的读取原项目的原产权证号
            if(CollectionUtils.isNotEmpty(yxmyzh) && yxmyzh.contains(newBdcXmDO.getDjxl())){
                //判断上一手是不是产权,产权读取bdcqzh，非产权读取ycqzh
                dozerUtils.initBeanDateConvert(yBdcXm, newBdcXmDO, "yxmyzh");
                if (!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, yBdcXm.getQllx())) {
                    newBdcXmDO.setYcqzh(yBdcXm.getBdcqzh());
                }
            }
            //不生成权利的算注销
            if(!CommonConstantUtils.SF_S_DM.equals(initServiceQO.getBdcCshFwkgSl().getSfscql())){
                dozerUtils.initBeanDateConvert(yBdcXm, newBdcXmDO, "zxxm");
            }
            //单元号不等时取权籍数据
            if(!StringUtils.equals(yBdcXm.getBdcdyh(),newBdcXmDO.getBdcdyh())){
                //虚拟的清空
                if(BdcdyhToolUtils.checkXnbdcdyh(newBdcXmDO.getBdcdyh())){
                    newBdcXmDO.setBdcdywybh(null);
                }else{
                    //房屋的话就清掉宗地bdcdywybh
                    Boolean isFw = bdcBdcdyService.judgeIsFwByBdcdyh(newBdcXmDO.getBdcdyh());
                    if(isFw){
                        dozerUtils.initBeanDateConvert(initServiceQO.getBdcdyDTO(), newBdcXmDO, initServiceQO.getBdcdyDTO().getClass().getSimpleName());
                    }else{
                        if (initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() != null) {
                            dozerUtils.initBeanDateConvert(initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO(), newBdcXmDO,initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO().getClass().getSimpleName());
                        }
                    }
                }
            }
            if(CommonConstantUtils.QLLX_TDCBNYDSYQ.equals(newBdcXmDO.getQllx()) ||CommonConstantUtils.QLLX_NYDJYQ.equals(newBdcXmDO.getQllx())) {
                //获取承包方或经营方信息 给项目表jyhth赋值
                dealJyhth(initServiceQO, yBdcXm.getJyhth(), newBdcXmDO);
            }
        }
        return newBdcXmDO;
    }


    /**
     * 读取信息从外联证书数据中
     * @param newBdcXmDO
     * @param initServiceQO
     * @return
     */
    private BdcXmDO readWlzsData(BdcXmDO newBdcXmDO, InitServiceQO initServiceQO){
        // 当原项目id不为空时
        List<BdcXmLsgxDO> bdcXmLsgxList = initServiceQO.getBdcXmLsgxList();
        if (CollectionUtils.isNotEmpty(bdcXmLsgxList)){
            List<BdcXmLsgxDO> wlXmLsgxList = bdcXmLsgxList
                    .stream()
                    .filter(bdcXmLsgxDO -> 1 == bdcXmLsgxDO.getWlxm()).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(wlXmLsgxList)){
                return newBdcXmDO;
            }
            BdcXmLsgxDO bdcXmLsgxDO = wlXmLsgxList.get(0);
            /**
             * 尝试从外联的证书中获取数据放到当前的项目中
             */
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(bdcXmLsgxDO.getYxmid());
            List<BdcXmDO> bdcXmDOS = bdcXmService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOS)){
                BdcXmDO bdcXmDO = bdcXmDOS.get(0);
                newBdcXmDO.setZdzhmj(bdcXmDO.getZdzhmj());
                newBdcXmDO.setYcqzh(bdcXmDO.getBdcqzh());
            }
        }
        return newBdcXmDO;
    }


    /**
     * 读取信息从楼盘表数据中
     * @param newBdcXmDO
     * @param initServiceQO
     * @return
     */
    private BdcXmDO dealBdcdywybh(BdcXmDO newBdcXmDO, InitServiceQO initServiceQO){
        //空的做处理
        if(StringUtils.isBlank(newBdcXmDO.getBdcdywybh())){
            // 虚拟单元号是F和L的生成bdcdywybh
            newBdcXmDO.setBdcdywybh(BdcdyhToolUtils.dealBdcdywybh(newBdcXmDO.getBdcdyh()));
        }
        initServiceQO.setBdcdywybh(newBdcXmDO.getBdcdywybh());
        return newBdcXmDO;
    }


    /**
     * 承包经营权,经营权处理项目表交易合同号
     * 1.如果是读取上手数据 对比上手jyhth和权籍的承包合同号或经营合同号是否一致，不一致当前jyhth取权籍，一致空着
     * 2.如果是取权籍数据，则直接取承包合同号或经营合同号
     * @param initServiceQO
     * @param newBdcXmDO
     * @return
     */
    private BdcXmDO dealJyhth(InitServiceQO initServiceQO, String yxmJyhth,BdcXmDO newBdcXmDO){
        if(initServiceQO.getDjxxResponseDTO() != null){
            // 获取权籍承包合同号
            DjDcbResponseDTO djDcbResponseDTO = initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO();
            if(djDcbResponseDTO instanceof NydDjdcbResponseDTO && CollectionUtils.isNotEmpty(((NydDjdcbResponseDTO) djDcbResponseDTO).getCbzdCbfDOList())
                    && ((NydDjdcbResponseDTO) djDcbResponseDTO).getCbzdCbfDOList().get(0) != null){
               String cbhtbh = ((NydDjdcbResponseDTO) djDcbResponseDTO).getCbzdCbfDOList().get(0).getCbhtbh();
               if(StringUtils.isNotBlank(cbhtbh)  && !cbhtbh.equals(yxmJyhth)){
                   newBdcXmDO.setJyhth(cbhtbh);
               }
            }else if(djDcbResponseDTO instanceof JyqDkDcbResponseDTO &&CollectionUtils.isNotEmpty(((JyqDkDcbResponseDTO) djDcbResponseDTO).getJyqDkQlrDOList()) && ((JyqDkDcbResponseDTO) djDcbResponseDTO).getJyqDkQlrDOList().get(0) != null){
                //经营权取权利人合同编号
                String htbh = ((JyqDkDcbResponseDTO) djDcbResponseDTO).getJyqDkQlrDOList().get(0).getHtbh();
                if(StringUtils.isNotBlank(htbh)  && !htbh.equals(yxmJyhth)){
                    newBdcXmDO.setJyhth(htbh);
                }
            }
        }
        return newBdcXmDO;
    }


}
