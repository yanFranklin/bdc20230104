package cn.gtmap.realestate.accept.service.impl;


import cn.gtmap.realestate.accept.config.FwtccxConfig;
import cn.gtmap.realestate.accept.core.service.BdcSlFwtcxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlJtcyService;
import cn.gtmap.realestate.accept.core.service.BdcSlSqrService;
import cn.gtmap.realestate.accept.core.service.BdcSlXmService;
import cn.gtmap.realestate.accept.service.BdcSlFwCxService;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.accept.BdcFwxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.CompareFwtcxxResultDTO;
import cn.gtmap.realestate.common.core.dto.accept.CompareFwtcxxResultDataDTO;
import cn.gtmap.realestate.common.core.dto.accept.CompareResultSqrDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.CompareFwtcxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZfxxQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZfxxCxFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.CommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/7/10
 * @description 房屋查询
 */
@Service
public class BdcSlFwCxServiceImpl implements BdcSlFwCxService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BdcSlSqrService bdcSlSqrService;
    @Autowired
    private BdcSlJtcyService bdcSlJtcyService;
    @Autowired
    BdcZfxxCxFeignService bdcZfxxCxFeignService;
    @Autowired
    BdcSlFwtcxxService bdcSlFwtcxxService;
    @Autowired
    BdcSlXmService bdcSlXmService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    FwtccxConfig fwtccxConfig;

    /**
     * 房屋套次统计（规划用途过滤）
     */
    @Value("${fwtccx.ghyt:}")
    private String fwtcghyt;

    /**
     * 房屋套次统计 舒城房屋套次计算的时候过滤掉农房的数据（农房权利类型为6）
     */
    @Value("${fwtccx.pcqllx:}")
    private String fwtcpcqllx;

    @Override
    public BdcFwxxDTO listFwxxByXmid(String xmid) {
        BdcFwxxDTO bdcFwxxDTO = new BdcFwxxDTO();
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("缺少参数：xmid");
        }
        //转入方
        List<BdcSlFwtcxxDO> bdcZrfZfxxDTOList = listBdcZfxxDTO(xmid, CommonConstantUtils.QLRLB_QLR,false);
        bdcFwxxDTO.setBdcZrfZfxxDTOList(bdcZrfZfxxDTOList);
        //转出方
        List<BdcSlFwtcxxDO> bdcZcfZfxxDTOList = listBdcZfxxDTO(xmid, CommonConstantUtils.QLRLB_YWR,false);
        bdcFwxxDTO.setBdcZcfZfxxDTOList(bdcZcfZfxxDTOList);
        return bdcFwxxDTO;
    }



    /**
     * @param xmid  项目ID
     * @param sqrlb 申请人类别
     * @param isYz  是否验证比较
     * @return 住房信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID和申请人住房信息
     */
    @Override
    public List<BdcSlFwtcxxDO> listBdcZfxxDTO(String xmid, String sqrlb,Boolean isYz) {
        Boolean sffc =false;
        //房屋查询单展现数据
        List<BdcSlFwtcxxDO> bdcSlFwtcxxDOList =new ArrayList<>();
        //查询当前流程的申请人信息
        List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrService.listBdcSlSqrByXmid(xmid, sqrlb);
        if (CollectionUtils.isEmpty(bdcSlSqrDOList)) {
            logger.warn("未查询到对应的申请人信息，查询中止！");
            return Collections.emptyList();
        }
        BdcSlXmDO bdcSlXmDO =bdcSlXmService.queryBdcSlXmByXmid(xmid);
        for (BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList) {
            //申请人是否进行房查
            Boolean sqrfwcxFlag =true;
            // 转出方家庭满五唯一为否,不进行房查
            if(CommonConstantUtils.QLRLB_YWR.equals(bdcSlSqrDO.getSqrlb()) &&CommonConstantUtils.SF_F_DM.equals(bdcSlSqrDO.getJtmwwyzz())){
                sqrfwcxFlag =false;
            }
            // 房屋套次为其他套,不进行房查
            if(StringUtils.isNotBlank(bdcSlSqrDO.getFwtc()) &&(StringUtils.equals(CommonConstantUtils.FWTC_QT,bdcSlSqrDO.getFwtc()))){
                sqrfwcxFlag =false;
            }
            // 2020-02-11 增加转出方类型非个人，不进行房查
            if(CommonConstantUtils.QLRLB_YWR.equals(bdcSlSqrDO.getSqrlb()) && !CommonConstantUtils.QLRLX_GR.equals(bdcSlSqrDO.getSqrlx()) ){
                sqrfwcxFlag = false;
            }
            if (sqrfwcxFlag) {
                //清除原有查询结果再新增
                bdcSlFwtcxxService.deleteBdcSlFwtcxxByXmid(xmid, sqrlb,bdcSlSqrDO.getSqrid());
                //以家庭为单位组织权利人查询数据
                List<BdcQlrQO> bdcQlrQOList = generateBdcQlrQO(bdcSlSqrDO);
                if (CollectionUtils.isNotEmpty(bdcQlrQOList)) {
                    BdcZfxxQO bdcZfxxQO = new BdcZfxxQO();
                    bdcZfxxQO.setQlrxx(bdcQlrQOList);
                    bdcZfxxQO.setCxly("1");
                    if(bdcSlXmDO != null &&StringUtils.isNotBlank(bdcSlXmDO.getQjgldm())) {
                        bdcZfxxQO.setQjgldm(MapUtils.getString(fwtccxConfig.getQjgldm(), bdcSlXmDO.getQjgldm()));
                    }
                    if (StringUtils.isNotBlank(fwtcpcqllx)){
                        bdcZfxxQO.setPcqllx(fwtcpcqllx);
                    }
                    List<BdcZfxxDTO> bdcSqrZfxxDTOList = bdcZfxxCxFeignService.listBdcZfxxDTO(bdcZfxxQO);
                    //处理房查结果(去重)
                    List<BdcZfxxDTO> bdcChangeSqrZfxxDTOList =changeZfxxDTOToCxjgDO(bdcSqrZfxxDTOList);
                    List<BdcZfxxDTO> fwtczfxxList =new ArrayList<>();
                    if(CollectionUtils.isNotEmpty(bdcChangeSqrZfxxDTOList)) {
                        for(BdcZfxxDTO bdcZfxxDTO:bdcChangeSqrZfxxDTOList){
                            //套次统计还需进行用途过滤
                            if(StringUtils.isNotBlank(fwtcghyt) &&CommonUtil.indexOfStrs(fwtcghyt.split(CommonConstantUtils.ZF_YW_DH), bdcZfxxDTO.getGhytdm())){
                                fwtczfxxList.add(bdcZfxxDTO);
                            }
                            //将数据对照到房屋套次信息表
                            BdcSlFwtcxxDO bdcSlFwtcxxDO =new BdcSlFwtcxxDO();
                            BeanUtils.copyProperties(bdcZfxxDTO, bdcSlFwtcxxDO);
                            bdcSlFwtcxxDO.setSqrid(bdcSlSqrDO.getSqrid());
                            bdcSlFwtcxxDOList.add(bdcSlFwtcxxDO);
                        }
                    }
                    //套次赋值
                    if(isYz &&!StringUtils.equals(fwtczfxxList.size()+"",bdcSlSqrDO.getSbfwtc()) &&StringUtils.equals(CommonConstantUtils.QLRLB_QLR,bdcSlSqrDO.getSqrlb())){
                        throw new AppException(bdcSlSqrDO.getSqrmc() +"房屋套次为:" +fwtczfxxList.size() +"与申报房屋套次信息:"+(StringUtils.isNotBlank(bdcSlSqrDO.getSbfwtc()) ? bdcSlSqrDO.getSbfwtc() : "0")+"不一致,请确认");

                    }
                    if (fwtczfxxList.size() > 2) {
                        //大于2直接赋值其他套
                        bdcSlSqrDO.setFwtc(CommonConstantUtils.FWTC_QT);
                    } else {
                        bdcSlSqrDO.setFwtc(String.valueOf(fwtczfxxList.size()));
                    }
                    bdcSlSqrService.updateBdcSlSqr(bdcSlSqrDO);
                    sffc =true;

                }
            }
        }
        //更新是否房查
        if (sffc) {
            if(bdcSlXmDO != null) {
                bdcSlXmDO.setSffc(CommonConstantUtils.SF_S_DM);
                bdcSlXmService.updateBdcSlXm(bdcSlXmDO);
            }
        }


        //总的查询结果根据不动产单元去重
        //查出所有不动产单元号为空,为空不过滤
        List<BdcSlFwtcxxDO> nullbcdyhFwtcList = bdcSlFwtcxxDOList.stream().filter(bdcSlFwtcxxDO -> StringUtils.isBlank(bdcSlFwtcxxDO.getBdcdyh())).collect(Collectors.toList());
        bdcSlFwtcxxDOList.removeAll(nullbcdyhFwtcList);
        List<BdcSlFwtcxxDO> bdcSlFwtcxxDOS = bdcSlFwtcxxDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getBdcdyh()))), ArrayList::new));
        if(CollectionUtils.isNotEmpty(nullbcdyhFwtcList)) {
            bdcSlFwtcxxDOS.addAll(nullbcdyhFwtcList);
        }

        //房查套次信息保存入库
        if(CollectionUtils.isNotEmpty(bdcSlFwtcxxDOS)) {
            bdcSlFwtcxxService.addBdcSlFwtcxx(bdcSlFwtcxxDOS, xmid, sqrlb);
        }
        return bdcSlFwtcxxDOS;
    }


    @Override
    public List<BdcSlFwtcxxDO> listBdcZfxxDTONT(String xmid, String sqrlb, Boolean isYz) {
        String yzmsg ="";
        //南通套次逻辑,页面填写申报房屋套次
        Boolean sffc =false;
        //房屋查询单展现数据
        List<BdcSlFwtcxxDO> bdcSlFwtcxxDOList =new ArrayList<>();
        //查询当前流程的申请人信息
        List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrService.listBdcSlSqrByXmid(xmid, sqrlb);
        if (CollectionUtils.isEmpty(bdcSlSqrDOList)) {
            logger.warn("未查询到对应的申请人信息，查询中止！");
            return Collections.emptyList();
        }
        for (BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList) {
            //申请人是否进行房查
            Boolean sqrfwcxFlag =true;
            // 转出方家庭满五唯一为否,不进行房查
            if(CommonConstantUtils.QLRLB_YWR.equals(bdcSlSqrDO.getSqrlb()) &&CommonConstantUtils.SF_F_DM.equals(bdcSlSqrDO.getJtmwwyzz())){
                sqrfwcxFlag =false;
            }
            // 房屋申报房屋套次为其他套,不进行房查
            if(StringUtils.isNotBlank(bdcSlSqrDO.getSbfwtc()) &&(StringUtils.equals(CommonConstantUtils.FWTC_QT,bdcSlSqrDO.getSbfwtc()))){
                sqrfwcxFlag =false;
            }
            if (sqrfwcxFlag) {
                //清除原有查询结果再新增
                bdcSlFwtcxxService.deleteBdcSlFwtcxxByXmid(xmid, sqrlb,bdcSlSqrDO.getSqrid());
                //以家庭为单位组织权利人查询数据
                List<BdcQlrQO> bdcQlrQOList = generateBdcQlrQO(bdcSlSqrDO);
                if (CollectionUtils.isNotEmpty(bdcQlrQOList)) {
                    BdcZfxxQO bdcZfxxQO = new BdcZfxxQO();

                    // 南通需求权利人名称 不再作为房查条件
                    for(BdcQlrQO bdcQlrQO : bdcQlrQOList){
                        bdcQlrQO.setQlrmc(null);
                    }

                    bdcZfxxQO.setQlrxx(bdcQlrQOList);
                    bdcZfxxQO.setCxly("1");
                    List<BdcZfxxDTO> bdcSqrZfxxDTOList = bdcZfxxCxFeignService.listBdcNtZfxxDTO(bdcZfxxQO);
                    //处理房查结果(去重)
                    List<BdcZfxxDTO> bdcChangeSqrZfxxDTOList =changeZfxxDTOToCxjgDO(bdcSqrZfxxDTOList);
                    List<BdcZfxxDTO> fwtczfxxList =new ArrayList<>();
                    if(CollectionUtils.isNotEmpty(bdcChangeSqrZfxxDTOList)) {
                        for(BdcZfxxDTO bdcZfxxDTO:bdcChangeSqrZfxxDTOList){
                            //套次统计还需进行用途过滤
                            if(StringUtils.isNotBlank(fwtcghyt) &&CommonUtil.indexOfStrs(fwtcghyt.split(CommonConstantUtils.ZF_YW_DH), bdcZfxxDTO.getGhytdm())){
                                fwtczfxxList.add(bdcZfxxDTO);
                            }
                            //将数据对照到房屋套次信息表
                            BdcSlFwtcxxDO bdcSlFwtcxxDO =new BdcSlFwtcxxDO();
                            BeanUtils.copyProperties(bdcZfxxDTO, bdcSlFwtcxxDO);
                            bdcSlFwtcxxDO.setSqrid(bdcSlSqrDO.getSqrid());
                            bdcSlFwtcxxDOList.add(bdcSlFwtcxxDO);
                        }
                    }

                    // 页面填报的房屋套次
                    int sbfwtc = Integer.parseInt(Optional.ofNullable(bdcSlSqrDO.getSbfwtc()).orElse("0"));
                    int fwcount = fwtczfxxList.size();
                    if(isYz &&StringUtils.equals(CommonConstantUtils.QLRLB_QLR,bdcSlSqrDO
                            .getSqrlb())){
                        //房屋套次查询结果大于1,只要申报房屋套次为其他套即可
                        //填报房屋套次>房查实际套次,验证通过不提示,只有房查套次大于填报套次需要验证提示
                        if(fwcount > sbfwtc &&!StringUtils.equals(CommonConstantUtils.FWTC_QT,String.valueOf(sbfwtc))){
                            bdcSlSqrDO.setSbfwtc("");
                            yzmsg+=bdcSlSqrDO.getSqrmc() +"及其家庭成员房屋套次为:" +fwcount +"与申报房屋套次信息:"+bdcSlSqrDO.getSbfwtc()+"不一致,请确认;";
                        }
                    }
                    if (fwcount > 1) {
                        //大于1直接赋值其他套
                        bdcSlSqrDO.setFwtc(CommonConstantUtils.FWTC_QT);
                    } else {
                        bdcSlSqrDO.setFwtc(String.valueOf(fwcount));
                    }
                    bdcSlSqrService.updateBdcSlSqr(bdcSlSqrDO);
                    sffc =true;

                }
            }
        }
        //更新是否房查
        if (sffc) {
            BdcSlXmDO bdcSlXmDO =bdcSlXmService.queryBdcSlXmByXmid(xmid);
            if(bdcSlXmDO != null) {
                bdcSlXmDO.setSffc(CommonConstantUtils.SF_S_DM);
                bdcSlXmService.updateBdcSlXm(bdcSlXmDO);
            }
        }


        //总的查询结果根据不动产单元去重
        //查出所有不动产单元号为空,为空不过滤
        List<BdcSlFwtcxxDO> nullbcdyhFwtcList = bdcSlFwtcxxDOList.stream().filter(bdcSlFwtcxxDO -> StringUtils.isBlank(bdcSlFwtcxxDO.getBdcdyh())).collect(Collectors.toList());
        bdcSlFwtcxxDOList.removeAll(nullbcdyhFwtcList);
        List<BdcSlFwtcxxDO> bdcSlFwtcxxDOS = bdcSlFwtcxxDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getBdcdyh()))), ArrayList::new));
        if(CollectionUtils.isNotEmpty(nullbcdyhFwtcList)) {
            bdcSlFwtcxxDOS.addAll(nullbcdyhFwtcList);
        }

        //房查套次信息保存入库
        //先清除原有查询结果再新增
        if(CollectionUtils.isNotEmpty(bdcSlFwtcxxDOS)) {
            bdcSlFwtcxxService.addBdcSlFwtcxx(bdcSlFwtcxxDOS, xmid, sqrlb);
        }
        if(StringUtils.isNotBlank(yzmsg)){
            throw new AppException("房屋套次验证不通过："+yzmsg);
        }
        return bdcSlFwtcxxDOS;
    }

    @Override
    public CompareFwtcxxResultDTO compareFwtcxx(CompareFwtcxxQO compareFwtcxxQO) {
        if(StringUtils.isBlank(compareFwtcxxQO.getXmid()) &&StringUtils.isBlank(compareFwtcxxQO.getSqrid())){
            throw new AppException("项目ID,申请人ID不能均为空");
        }
        if(StringUtils.isNotBlank(compareFwtcxxQO.getSqrid()) &&StringUtils.isNotBlank(compareFwtcxxQO.getSbfwtc())){
            //保存填报的申报房屋套次信息
            BdcSlSqrDO sqr =new BdcSlSqrDO();
            sqr.setSqrid(compareFwtcxxQO.getSqrid());
            sqr.setSbfwtc(compareFwtcxxQO.getSbfwtc());
            bdcSlSqrService.updateBdcSlSqr(sqr);
        }

        CompareFwtcxxResultDTO resultDTO = new CompareFwtcxxResultDTO();
        String yzmsg ="";
        //南通套次逻辑,页面填写申报房屋套次
        //房屋查询单展现数据
        List<BdcSlFwtcxxDO> bdcSlFwtcxxDOList =new ArrayList<>();
        //查询申请人信息
        List<BdcSlSqrDO> bdcSlSqrDOList =new ArrayList<>();
        if(StringUtils.isNotBlank(compareFwtcxxQO.getSqrid())){
            BdcSlSqrDO bdcSlSqrDO = bdcSlSqrService.queryBdcSlSqrBySqrid(compareFwtcxxQO.getSqrid());
            if(bdcSlSqrDO != null){
                bdcSlSqrDOList.add(bdcSlSqrDO);
                compareFwtcxxQO.setXmid(bdcSlSqrDO.getXmid());
                compareFwtcxxQO.setSqrlb(bdcSlSqrDO.getSqrlb());
            }
        }else {
            bdcSlSqrDOList = bdcSlSqrService.listBdcSlSqrByXmid(compareFwtcxxQO.getXmid(), compareFwtcxxQO.getSqrlb());
        }
        if (CollectionUtils.isEmpty(bdcSlSqrDOList)) {
            throw new AppException("未查询到对应的申请人信息,比对终止!");
        }
        List<CompareFwtcxxResultDataDTO> resultDataDTOList = new ArrayList<>();
        for (BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList) {
            CompareFwtcxxResultDataDTO compareFwtcxxResultDataDTO = new CompareFwtcxxResultDataDTO();
            //申请人是否进行房查
            Boolean sqrfwcxFlag =true;
            // 转出方家庭满五唯一为否,不进行房查
            if(CommonConstantUtils.QLRLB_YWR.equals(bdcSlSqrDO.getSqrlb()) &&CommonConstantUtils.SF_F_DM.equals(bdcSlSqrDO.getJtmwwyzz())){
                sqrfwcxFlag =false;
            }
            if (sqrfwcxFlag) {
                //清除原有查询结果再新增
                bdcSlFwtcxxService.deleteBdcSlFwtcxxByXmid(compareFwtcxxQO.getXmid(), compareFwtcxxQO.getSqrlb(),bdcSlSqrDO.getSqrid());
                //以家庭为单位组织权利人查询数据
                List<BdcQlrQO> bdcQlrQOList = generateBdcQlrQO(bdcSlSqrDO);
                if (CollectionUtils.isNotEmpty(bdcQlrQOList)) {
                    BdcZfxxQO bdcZfxxQO = new BdcZfxxQO();

                    // 南通需求权利人名称 不再作为房查条件
                    for(BdcQlrQO bdcQlrQO : bdcQlrQOList){
                        bdcQlrQO.setQlrmc(null);
                    }

                    bdcZfxxQO.setQlrxx(bdcQlrQOList);
                    bdcZfxxQO.setCxly("1");
                    List<BdcZfxxDTO> bdcSqrZfxxDTOList = bdcZfxxCxFeignService.listBdcNtZfxxDTO(bdcZfxxQO);
                    //处理房查结果(去重)
                    List<BdcZfxxDTO> bdcChangeSqrZfxxDTOList =changeZfxxDTOToCxjgDO(bdcSqrZfxxDTOList);
                    List<BdcZfxxDTO> fwtczfxxList =new ArrayList<>();
                    if(CollectionUtils.isNotEmpty(bdcChangeSqrZfxxDTOList)) {
                        for(BdcZfxxDTO bdcZfxxDTO:bdcChangeSqrZfxxDTOList){
                            //套次统计还需进行用途过滤
                            if(StringUtils.isNotBlank(fwtcghyt) &&CommonUtil.indexOfStrs(fwtcghyt.split(CommonConstantUtils.ZF_YW_DH), bdcZfxxDTO.getGhytdm())){
                                fwtczfxxList.add(bdcZfxxDTO);
                            }
                            //将数据对照到房屋套次信息表
                            BdcSlFwtcxxDO bdcSlFwtcxxDO =new BdcSlFwtcxxDO();
                            BeanUtils.copyProperties(bdcZfxxDTO, bdcSlFwtcxxDO);
                            bdcSlFwtcxxDO.setSqrid(bdcSlSqrDO.getSqrid());
                            bdcSlFwtcxxDOList.add(bdcSlFwtcxxDO);
                        }
                    }

                    // 页面填报的房屋套次
                    int sbfwtc = Integer.parseInt(Optional.ofNullable(bdcSlSqrDO.getSbfwtc()).orElse("0"));
                    int fwcount = fwtczfxxList.size();
                    // 南通：房屋套次与申报套次比对时。当义务人情况，页面选择首套（sbfwtc=0），但房查一套（fwcount=1），从而申报套次加1
                    if(CommonConstantUtils.QLRLB_YWR.equals(bdcSlSqrDO.getSqrlb())){
                        sbfwtc++;
                    }
                    //房屋套次查询结果大于1,只要申报房屋套次为其他套即可
                    //填报房屋套次>房查实际套次,验证通过不提示,只有房查套次大于填报套次需要验证提示
                    if(fwcount > sbfwtc &&!StringUtils.equals(CommonConstantUtils.FWTC_QT,String.valueOf(sbfwtc))){
                        yzmsg+=bdcSlSqrDO.getSqrmc() +"及其家庭成员房屋套次为:" +fwcount +"与申报房屋套次信息:"+bdcSlSqrDO.getSbfwtc()+"不一致,请确认;";
                        bdcSlSqrDO.setFwtcbdjg(CommonConstantUtils.SF_F_DM);
                    }else{
                        bdcSlSqrDO.setFwtcbdjg(CommonConstantUtils.SF_S_DM);
                    }
                    if (fwcount > 1) {
                        //大于1直接赋值其他套
                        bdcSlSqrDO.setFwtc(CommonConstantUtils.FWTC_QT);
                    } else {
                        bdcSlSqrDO.setFwtc(String.valueOf(fwcount));
                    }
                    bdcSlSqrService.updateBdcSlSqr(bdcSlSqrDO);
                    compareFwtcxxResultDataDTO.setFilterYtFwtcxx(fwtczfxxList);
                }
            }

            //总的查询结果根据不动产单元去重
            //查出所有不动产单元号为空,为空不过滤
            List<BdcSlFwtcxxDO> nullbcdyhFwtcList = bdcSlFwtcxxDOList.stream().filter(bdcSlFwtcxxDO -> StringUtils.isBlank(bdcSlFwtcxxDO.getBdcdyh())).collect(Collectors.toList());
            bdcSlFwtcxxDOList.removeAll(nullbcdyhFwtcList);
            List<BdcSlFwtcxxDO> bdcSlFwtcxxDOS = bdcSlFwtcxxDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getBdcdyh()))), ArrayList::new));
            if(CollectionUtils.isNotEmpty(nullbcdyhFwtcList)) {
                bdcSlFwtcxxDOS.addAll(nullbcdyhFwtcList);
            }
            //房查套次信息保存入库
            //先清除原有查询结果再新增
            if(CollectionUtils.isNotEmpty(bdcSlFwtcxxDOS)) {
                bdcSlFwtcxxService.addBdcSlFwtcxx(bdcSlFwtcxxDOS, compareFwtcxxQO.getXmid(), compareFwtcxxQO.getSqrlb());
            }
            if(StringUtils.isNotBlank(yzmsg)){
                compareFwtcxxResultDataDTO.setCode("0002");
                compareFwtcxxResultDataDTO.setMsg("房屋套次验证不通过:" + yzmsg);
            }else{
                compareFwtcxxResultDataDTO.setCode("0000");
            }
            CompareResultSqrDTO sqrDTO = new CompareResultSqrDTO();
            sqrDTO.setSqrid(bdcSlSqrDO.getSqrid());
            sqrDTO.setSqrmc(bdcSlSqrDO.getSqrmc());
            sqrDTO.setSqrzjzl(Objects.nonNull(bdcSlSqrDO.getZjzl()) ? String.valueOf(bdcSlSqrDOList.get(0).getZjzl()) : "");
            sqrDTO.setSqrzjh(bdcSlSqrDO.getZjh());
            sqrDTO.setSqrlb(bdcSlSqrDO.getSqrlb());
            compareFwtcxxResultDataDTO.setSqrDTO(sqrDTO);
            compareFwtcxxResultDataDTO.setBdcSlFwtcxxDOS(bdcSlFwtcxxDOS);
            resultDataDTOList.add(compareFwtcxxResultDataDTO);
        }
        resultDTO.setResultDataDTOList(resultDataDTOList);
        return resultDTO;
    }

    @Override
    public void drFwtcxx(CompareFwtcxxQO compareFwtcxxQO){
        if(StringUtils.isBlank(compareFwtcxxQO.getSqrid())){
            throw new AppException("缺失申请人ID");
        }
        //更新申报房屋套次和比对结果
        BdcSlSqrDO bdcSlSqrDO =new BdcSlSqrDO();
        bdcSlSqrDO.setSbfwtc(compareFwtcxxQO.getSbfwtc());
        bdcSlSqrDO.setFwtcbdjg(CommonConstantUtils.SF_S_DM);
        bdcSlSqrDO.setSqrid(compareFwtcxxQO.getSqrid());
        bdcSlSqrService.updateBdcSlSqr(bdcSlSqrDO);
    }

    /**
     * @param bdcSlSqrDO 申请人对象
     * @return 住房查询权利人查询参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID和申请人类别组织住房查询权利人查询参数
     */
    private List<BdcQlrQO> generateBdcQlrQO(BdcSlSqrDO bdcSlSqrDO) {
        //组织的所有权利人信息
        List<BdcQlrQO> bdcQlrQOList = new ArrayList<>();
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setQlrmc(bdcSlSqrDO.getSqrmc());
        bdcQlrQO.setZjh(bdcSlSqrDO.getZjh());
        bdcQlrQOList.add(bdcQlrQO);
        //获取对应的家庭成员信息
        List<BdcSlJtcyDO> bdcSlJtcyDOList = bdcSlJtcyService.listBdcSlJtcyBySqrid(bdcSlSqrDO.getSqrid());
        if (CollectionUtils.isNotEmpty(bdcSlJtcyDOList)) {
            for (BdcSlJtcyDO bdcSlJtcyDO : bdcSlJtcyDOList) {
                BdcQlrQO bdcJtcyQlrQO = new BdcQlrQO();
                bdcJtcyQlrQO.setQlrmc(bdcSlJtcyDO.getJtcymc());
                bdcJtcyQlrQO.setZjh(bdcSlJtcyDO.getZjh());
                bdcQlrQOList.add(bdcJtcyQlrQO);
            }
        }

        return bdcQlrQOList;
    }

    /**
     * @param bdcSqrZfxxDTOList 住房信息查询结果
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理转换房查结果
     */
    private List<BdcZfxxDTO> changeZfxxDTOToCxjgDO(List<BdcZfxxDTO> bdcSqrZfxxDTOList){
        List<BdcZfxxDTO> bdcZfxxDTOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcSqrZfxxDTOList)) {
            //不动产单元相同为一组
            Map<String,BdcZfxxDTO> bdcdyhMap=new HashMap<>();
            for(BdcZfxxDTO bdcZfxxDTO:bdcSqrZfxxDTOList){
                //根据单元号进行去重
                //存在
                if(bdcdyhMap.containsKey(bdcZfxxDTO.getBdcdyh())){
                    //判断数据是否需要加等
                    BdcZfxxDTO ybdcZfxxDTO =bdcdyhMap.get(bdcZfxxDTO.getBdcdyh());
                    if(StringUtils.isNotBlank(ybdcZfxxDTO.getBdcqzh()) &&!ybdcZfxxDTO.getBdcqzh().contains(bdcZfxxDTO.getBdcqzh())){
                        ybdcZfxxDTO.setBdcqzh(ybdcZfxxDTO.getBdcqzh() +CommonConstantUtils.SUFFIX_PL);
                    }
                    //权利人和证件号加等
                    ybdcZfxxDTO.setQlrmc(StringUtils.isNotBlank(ybdcZfxxDTO.getQlrmc()) ? ybdcZfxxDTO.getQlrmc()+CommonConstantUtils.SUFFIX_PL +"":bdcZfxxDTO.getQlrmc());
                    ybdcZfxxDTO.setQlrzjh(StringUtils.isNotBlank(ybdcZfxxDTO.getQlrzjh()) ? ybdcZfxxDTO.getQlrzjh()+CommonConstantUtils.SUFFIX_PL +"":bdcZfxxDTO.getQlrzjh());

                }else{
                    bdcdyhMap.put(bdcZfxxDTO.getBdcdyh(),bdcZfxxDTO);
                }
            }
            for (Map.Entry<String, BdcZfxxDTO> entry : bdcdyhMap.entrySet()) {
                BdcZfxxDTO bdcZfxxDTO =entry.getValue();
                bdcZfxxDTOList.add(bdcZfxxDTO);
            }
        }
        return bdcZfxxDTOList;

    }






}
