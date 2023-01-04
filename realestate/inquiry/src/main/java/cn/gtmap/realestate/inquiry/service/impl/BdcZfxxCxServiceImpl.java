package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaQlrDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaSpfDO;
import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcFwJbxxDTO;
import cn.gtmap.realestate.common.core.enums.BdcZfxxCxlyEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFwqlQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFwqlQlrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZfxxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.yancheng.BdcFwQsxxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.yancheng.BdcFwQsxxQlrQO;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcPpFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CardNumberTransformation;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.inquiry.core.mapper.BdcZfxxMapper;
import cn.gtmap.realestate.inquiry.service.BdcZfxxCxService;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/7/9
 * @description 住房信息查询逻辑层
 */
@Service
public class BdcZfxxCxServiceImpl implements BdcZfxxCxService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZfxxCxServiceImpl.class);
    /**
     * ORM操作
     */
    @Autowired
    private BdcZfxxMapper bdcZfxxMapper;

    @Autowired
    private FwHsFeignService fwHsFeignService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    BdcPpFeignService bdcPpFeignService;
    /**
     * 登记系统  规划用途（房屋用途）
     */
    @Value("${zfcx.djxt:}")
    private String ghyt1;
    /**
     * 自助查询机过滤 规划用途（房屋用途）
     */
    @Value("${zfcx.zzcxj:}")
    private String ghyt2;
    /**
     * （南通）自助交互机过滤 规划用途（房屋用途）
     */
    @Value("${zfcx.zzjhj:}")
    private String ghyt3;

    /**
     * 合肥大数据局 规划用途（房屋用途）
     */
    @Value("${zfcx.hfBigDataCompany:}")
    private String hfBigDataCompany;

    /**
     * 过滤宅基地数据
     */
    @Value("${zfcx.glzjdsj:false}")
    private Boolean glzjdsj;

    /**
     * 是否读取新建商品房合同备案数据
     */
    @Value("${zfcx.sfdqhtba:false}")
    private Boolean sfdqhtba;

    /**
     * 过滤用途特殊配置: 原有需求 35017，默认空不配置，现成通用逻辑
     */
    @Value("${zfcx.pcghyt:}")
    private String pcghyt;

    /**
     * 有房无房证明 权利类型过滤
     */
    @Value("${zfcx.qllx:}")
    private String zfcxQllx;
    /**
     * 有房无房证明  bdbzqse取值判断
     */
    @Value("${zfcx.dyfsForbdbzzqse:}")
    private String dyfsForbdbzzqse;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Autowired
    EntityMapper entityMapper;


    /**
     * @param bdcZfxxQO 查询参数
     * @return {List} 住房信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据权利人名称、证件号查询房产信息
     */
    @Override
    public List<BdcZfxxDTO> listBdcZfxxDTO(BdcZfxxQO bdcZfxxQO) {
        if (null == bdcZfxxQO || CollectionUtils.isEmpty(bdcZfxxQO.getQlrxx())) {
            throw new AppException(ErrorCode.NPE_EX, "住房查询参数为空");
        }
        List<BdcQlrQO> bdcQlrQOList = new ArrayList<>();
        // 证件号必要条件
        for (BdcQlrQO bdcQlrQO : bdcZfxxQO.getQlrxx()) {
            if (StringUtils.isBlank(bdcQlrQO.getZjh())) {
                throw new AppException(ErrorCode.INQUIRY_ZFXX_PARAM_NO_ZJH, "住房查询参数证件号为空");
            } else if (StringUtils.isBlank(bdcQlrQO.getQlrmc())) {
                throw new AppException(ErrorCode.INQUIRY_ZFXX_PARAM_NO_QLRMC, "住房查询参数权利人为空");
            } else {
                if (18 == bdcQlrQO.getZjh().length()) {
                    if (CardNumberTransformation.idCard18(bdcQlrQO.getZjh())) {
                        BdcQlrQO bdcQlrQO1 = new BdcQlrQO();
                        String zjh15 = CardNumberTransformation.idCard18to15(bdcQlrQO.getZjh());
                        bdcQlrQO1.setQlrmc(bdcQlrQO.getQlrmc());
                        //18位身份证只用证件号查
                        bdcQlrQO.setQlrmc(null);
                        bdcQlrQOList.add(bdcQlrQO);
                        //18位转15位后，用15位+姓名查询
                        bdcQlrQO1.setZjh(zjh15);
                        bdcQlrQOList.add(bdcQlrQO1);
                    } else {
                        bdcQlrQOList.add(bdcQlrQO);
                    }

                } else if (15 == bdcQlrQO.getZjh().length()) {
                    if (CardNumberTransformation.idCard15(bdcQlrQO.getZjh())) {
                        BdcQlrQO bdcQlrQO2 = new BdcQlrQO();
                        String zjh18 = CardNumberTransformation.idCard15to18(bdcQlrQO.getZjh());
                        //15位直接证件号加姓名查询

                        //15位转18位后，只用证件号去查询
                        bdcQlrQO2.setZjh(zjh18);
                        bdcQlrQO2.setQlrmc(null);

                        bdcQlrQOList.add(bdcQlrQO);
                        bdcQlrQOList.add(bdcQlrQO2);
                    } else {
                        bdcQlrQOList.add(bdcQlrQO);
                    }
                } else {
                    bdcQlrQOList.add(bdcQlrQO);
                }

            }
        }
        bdcZfxxQO.setQlrxx(bdcQlrQOList);

        // 证件号转为大写
        for (BdcQlrQO bdcQlrQO : bdcZfxxQO.getQlrxx()) {
            bdcQlrQO.setZjh(StringUtils.upperCase(bdcQlrQO.getZjh()));
        }

        if (StringUtils.equals(BdcZfxxCxlyEnum.DJXT.getCode(), bdcZfxxQO.getCxly())) {
            // 如果是当前登记系统按照配置的房屋用途和空
            bdcZfxxQO.setGhyt(this.ghyt1);
        } else if (StringUtils.equals(BdcZfxxCxlyEnum.HFBIGDATACOMPANY.getCode(), bdcZfxxQO.getCxly())) {
            //如果是合肥大数据局，按照配置的规划用途查询
            bdcZfxxQO.setGhyt(this.hfBigDataCompany);
        } else {
            // 自助查询机
            bdcZfxxQO.setCxly("2");
            bdcZfxxQO.setGhyt(this.ghyt2);
        }

        // 明确声明不需要过滤规划用途的
        if(CommonConstantUtils.NO.equals(bdcZfxxQO.getSfghyt())) {
            bdcZfxxQO.setCxly(null);
            bdcZfxxQO.setGhyt(null);
        }

        List<BdcZfxxDTO> zfxxDTOList = bdcZfxxMapper.listBdcZfxxDTO(bdcZfxxQO);

        if (true == glzjdsj) {
            // 需要过滤掉宅基地数据
            return this.filterZjdsj(zfxxDTOList);
        }
        return zfxxDTOList;
    }

    /**
     * @param bdcZfxxQO 查询参数
     * @return {List} 住房信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据权利人名称、证件号查询房产信息（南通、盐城、标准版通用逻辑）
     */
    @Override
    public List<BdcZfxxDTO> listBdcNtZfxxDTO(BdcZfxxQO bdcZfxxQO) {
        if (null == bdcZfxxQO || CollectionUtils.isEmpty(bdcZfxxQO.getQlrxx())) {
            throw new AppException(ErrorCode.NPE_EX, "南通住房查询接口报错：住房查询权利人参数为空");
        }

        for (BdcQlrQO bdcQlrQO : bdcZfxxQO.getQlrxx()) {
            if (StringUtils.isBlank(bdcQlrQO.getQlrmc()) && StringUtils.isBlank(bdcQlrQO.getZjh())) {
                throw new AppException(ErrorCode.NPE_EX, "南通住房查询接口报错：权利人名称和证件号同时为空！");
            }

            // 南通证件号允许根据名称查询
            if (StringUtils.isNotBlank(bdcQlrQO.getZjh())) {
                bdcQlrQO.setZjh(replaceBlank(bdcQlrQO.getZjh()));
                // 转换身份证号
                bdcQlrQO.setZjh(Stream.of(bdcQlrQO.getZjh()).map(e ->
                        CardNumberTransformation.zjhTransformation(e)).collect(Collectors.joining(",")));

                // 互联网的需要逗号分隔查询，非互联网的单个查询
                if (StringUtils.equals("3", bdcZfxxQO.getCxly())) {
                    bdcQlrQO.setZjhList(Arrays.asList(bdcQlrQO.getZjh().split(",")));
                    bdcQlrQO.setZjh(null);
                }

            }

            //权利人名称与证件号内容去除空格
            if (StringUtils.isNotBlank(bdcQlrQO.getQlrmc())) {
                bdcQlrQO.setQlrmc(replaceBlank(bdcQlrQO.getQlrmc()));
            }
        }

        // 处理规划用途查询参数
        this.setGhyt(bdcZfxxQO);
        // 权利类型过滤
        bdcZfxxQO.setQllx(this.zfcxQllx);

        List<BdcZfxxDTO> zfxxDTOList = bdcZfxxMapper.listBdcNtZfxxDTO(bdcZfxxQO);

        if(CollectionUtils.isNotEmpty(zfxxDTOList)) {
            // 增加土地面积，土地用途
            addTdMj(zfxxDTOList);
            addTdYt(zfxxDTOList);
        }

        if (glzjdsj) {
            // 需要过滤掉宅基地数据
            zfxxDTOList = this.filterZjdsj(zfxxDTOList);
        }

        // 连云港读取新建商品房合同信息
        if(sfdqhtba) {
            List<BdcZfxxDTO> htbaxx = bdcZfxxMapper.lisSpfhtbhxx(bdcZfxxQO);
            LOGGER.debug("读取合同备案信息，查询到数据{}条，参数：{}", CollectionUtils.size(htbaxx), JSON.toJSONString(bdcZfxxQO));
            if(CollectionUtils.isNotEmpty(htbaxx)) {
                zfxxDTOList = (List<BdcZfxxDTO>) CollectionUtils.union(zfxxDTOList, htbaxx);
                LOGGER.debug("合并合同备案信息，数据{}条，参数：{}", CollectionUtils.size(zfxxDTOList), JSON.toJSONString(bdcZfxxQO));
            }
        }

        return zfxxDTOList;
    }

    /**
     * BdcZfxxDTO获取 tdsyqmj（土地使用权面积）、fttdmj(分摊土地面积)、dytdmj（独用土地面积）
     *
     * @param zfxxDTOList 住房信息
     */
    private void addTdMj(List<BdcZfxxDTO> zfxxDTOList) {
        for (BdcZfxxDTO bdcZfxxDTO : zfxxDTOList) {
            if (bdcZfxxDTO.getTdsyqmj() == null && bdcZfxxDTO.getFttdmj() == null && bdcZfxxDTO.getDytdmj() == null) {
                // 查bdc_jsydsyq
                List<BdcJsydsyqDO> bdcJsydsyqDOList = bdcZfxxMapper.listJsydsyqDOByXmid(bdcZfxxDTO.getXmid());
                if (CollectionUtils.isNotEmpty(bdcJsydsyqDOList)) {
                    BdcJsydsyqDO jsydsyqDO = bdcJsydsyqDOList.get(0);
                    bdcZfxxDTO.setTdsyqmj(jsydsyqDO.getSyqmj());
                    bdcZfxxDTO.setFttdmj(jsydsyqDO.getFttdmj());
                    bdcZfxxDTO.setDytdmj(jsydsyqDO.getDytdmj());
                } else {
                    // 查fw_hs
                    FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(bdcZfxxDTO.getBdcdyh(), "");
                    if (fwHsDO != null) {
                        bdcZfxxDTO.setFttdmj(fwHsDO.getFttdmj());
                        bdcZfxxDTO.setDytdmj(fwHsDO.getDytdmj());
                        if (fwHsDO.getFttdmj() != null && fwHsDO.getDytdmj() == null) {
                            bdcZfxxDTO.setTdsyqmj(fwHsDO.getFttdmj());
                        }
                        if (fwHsDO.getFttdmj() == null && fwHsDO.getDytdmj() != null) {
                            bdcZfxxDTO.setTdsyqmj(fwHsDO.getDytdmj());
                        }
                        if (fwHsDO.getFttdmj() != null && fwHsDO.getDytdmj() != null) {
                            bdcZfxxDTO.setTdsyqmj(fwHsDO.getFttdmj() + fwHsDO.getDytdmj());
                        }
                    } else {
                        LOGGER.info("未查询到土地面积相关数据，住房信息：{}", bdcZfxxDTO.toString());
                    }
                }
            }
        }
    }

    /**
     * 不动产权证和未匹配土地证的老房产读取项目表的zdzhyt；若老房产证匹配过土地证的，则读取土地证的zdzhyt
     *
     * @param zfxxDTOList 住房信息
     */
    private void addTdYt(List<BdcZfxxDTO> zfxxDTOList) {
        for (BdcZfxxDTO bdcZfxxDTO : zfxxDTOList) {
            //是否是土地证或者未匹配过土地证
            //查询匹配关系
            List<BdcFctdPpgxDO> bdcFctdPpgxDOList = bdcPpFeignService.queryBdcFctdPpgx(bdcZfxxDTO.getXmid());
            String xmid = "";
            //只有老的房产证才会去匹配，如果已经匹配过了则取匹配过后的，如果还没有匹配则取当前的，
            // 不需要额外判断产证的类型。如果是新产证不会出现匹配，如果是老产证可能已匹配可能未匹配，所以只判断匹配关系
            if(CollectionUtils.isEmpty(bdcFctdPpgxDOList)){
                //不动产权证和未匹配土地证的老房产读取项目表的zdzhyt
                xmid = bdcZfxxDTO.getXmid();
            } else {
                xmid = bdcFctdPpgxDOList.get(0).getTdcqxmid();
            }
            List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXmByXmids(Arrays.asList(xmid));
            if(CollectionUtils.isNotEmpty(bdcXmDOS)){
                bdcZfxxDTO.setTdyt(bdcXmDOS.get(0).getZdzhyt());
            }
        }
    }

    /**
     * @param zfxxDTOList 住房信息数据
     * @return {List} 过滤后住房信息数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 过滤掉宅基地住房信息数据
     */
    private List<BdcZfxxDTO> filterZjdsj(List<BdcZfxxDTO> zfxxDTOList) {
        if (CollectionUtils.isEmpty(zfxxDTOList)) {
            return Collections.emptyList();
        }

        List<BdcZfxxDTO> result = new ArrayList<>();
        for (BdcZfxxDTO bdcZfxxDTO : zfxxDTOList) {
            if (null == bdcZfxxDTO) {
                continue;
            }

            // 判断是否是宅基地不动产单元，如果是就返回，不是就返回
            if (!BdcdyhToolUtils.checkBdcdyhIsZjd(bdcZfxxDTO.getBdcdyh())) {
                result.add(bdcZfxxDTO);
            }
        }
        return result;
    }


    /**
     * @param bdcdyh 不动产单元号
     * @param qjgldm 权籍管理代码
     * @return {List} 房产档案信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据不动产单元号查询房产档案信息
     * @modify wyh 添加权籍管理代码查询条件
     */
    @Override
    public BdcFcdaDTO getBdcFcdaDTO(String bdcdyh,String qjgldm) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new AppException(ErrorCode.NPE_EX, "根据不动产单元号查询房产档案信息：查询参数不动产单元号为空");
        }

        BdcFcdaDTO bdcFcdaDTO = new BdcFcdaDTO();
        bdcFcdaDTO.setBdcdyh(bdcdyh);
        if(StringUtils.isNotBlank(qjgldm)){
            bdcFcdaDTO.setQjgldm(Arrays.asList(qjgldm.split(",")));
        }
        // 查询住房信息
        BdcZfxxQO bdcZfxxQO = new BdcZfxxQO();
        bdcZfxxQO.setBdcdyh(bdcdyh);
        bdcZfxxQO.setQjgldm(qjgldm);
        List<BdcZfxxDTO> zfxxList = bdcZfxxMapper.listBdcZfxxDTO(bdcZfxxQO);
        //在建工程抵押也要出房产证明，但是此时无相关房产数据，限制放开，现场会修改相关数据源
/*        if(CollectionUtils.isEmpty(zfxxList)){
            LOGGER.warn("根据不动产单元号{}未查询到房产档案信息", bdcdyh);
            return null;
        }*/
        bdcFcdaDTO.setZfxx(zfxxList);

        // 查询房产基本信息
        List<BdcFdcqDTO> fdcqDTOList = bdcZfxxMapper.listBdcFdcqDTO(bdcdyh,qjgldm);
        if (CollectionUtils.isEmpty(fdcqDTOList)) {
            bdcFcdaDTO.setFdcq(null);
        } else {
            // 不动产单元只能有一条现势权利
            bdcFcdaDTO.setFdcq(fdcqDTOList.get(0));
        }

        List<BdcFcdaDyaqDTO> dyalist = bdcZfxxMapper.listBdcFcdaDyaqDTO(bdcdyh,qjgldm);
        if(CommonConstantUtils.SYSTEM_VERSION_HF.equals(dyfsForbdbzzqse)){
            if (CollectionUtils.isNotEmpty(dyalist)) {
                for (BdcFcdaDyaqDTO bdcFcdaDyaqDTO : dyalist) {
                    if("2".equals(bdcFcdaDyaqDTO.getDyfs().toString())){
                        bdcFcdaDyaqDTO.setBdbzzqse(bdcFcdaDyaqDTO.getZgzqe());
                    }
                }
            }
            bdcFcdaDTO.setDyxx(dyalist);
        }else {
            // 查询抵押信息
            bdcFcdaDTO.setDyxx(bdcZfxxMapper.listBdcFcdaDyaqDTO(bdcdyh,qjgldm));
        }



        // 查询查封信息
        bdcFcdaDTO.setCfxx(bdcZfxxMapper.listBdcFcdaCfxx(bdcdyh,qjgldm));
        // 查询异议信息
        bdcFcdaDTO.setYyxx(bdcZfxxMapper.listBdcFcdaYyxx(bdcdyh,qjgldm));

        //查询居住权信息，居住权人信息
        bdcFcdaDTO.setJzqxx(listJzxxByBdcdyh(bdcdyh,qjgldm));
        return bdcFcdaDTO;
    }

    /**
     * @param bdcdyh
     * @return jzqxxList
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     */
    public List<BdcJzqxxDTO> listJzxxByBdcdyh(String bdcdyh,String qjgldm) {
        List<BdcJzqxxDTO> jzqxxList = new ArrayList<>();
        if (StringUtils.isNotBlank(bdcdyh)) {
            List<Integer> qsztList = new ArrayList<>();
            qsztList.add(CommonConstantUtils.QSZT_VALID);
            List<BdcJzqDO> bdcJzqDOS = bdcZfxxMapper.listBdcJzqxx(bdcdyh, qjgldm);
            if (CollectionUtils.isNotEmpty(bdcJzqDOS)) {
                for (BdcJzqDO jzqDO : bdcJzqDOS) {
                    BdcJzqxxDTO bdcJzqxxDTO = new BdcJzqxxDTO();
                    bdcJzqxxDTO.setJzfw(jzqDO.getJzfw());
                    bdcJzqxxDTO.setJzqksqx(DateUtil.formatDate(jzqDO.getJzqkssj()));
                    bdcJzqxxDTO.setJzqjsqx(DateUtil.formatDate(jzqDO.getJzqjssj()));
                    bdcJzqxxDTO.setDjsj(DateUtil.formatDate(jzqDO.getDjsj()));
                    bdcJzqxxDTO.setFj(jzqDO.getFj());
                    if (StringUtils.isNotBlank(jzqDO.getXmid())) {
                        String xmid = jzqDO.getXmid();
                        //查项目信息,取项目表不动产权证号
                        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXmByXmids(Arrays.asList(xmid));
                        if (CollectionUtils.isNotEmpty(bdcXmDOS)) {
                            bdcJzqxxDTO.setBdczmh(bdcXmDOS.get(0).getBdcqzh());
                            bdcJzqxxDTO.setJzhtbh(bdcXmDOS.get(0).getJyhth());
                        }

                        //居住权业务的权利人信息
                        List<BdcJzqrxxDTO> bdcJzqrxxDTOS = new ArrayList<>();
                        BdcQlrQO bdcQlrQO = new BdcQlrQO();
                        bdcQlrQO.setXmid(xmid);
                        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                        List<BdcQlrDO> qlrList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                        if (CollectionUtils.isNotEmpty(qlrList)) {
                            Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
                            for (BdcQlrDO bdcQlrDO : qlrList) {
                                BdcJzqrxxDTO bdcJzqrxxDTO = new BdcJzqrxxDTO();
                                bdcJzqrxxDTO.setJzqrmc(bdcQlrDO.getQlrmc());
                                bdcJzqrxxDTO.setJzqrzjh(bdcQlrDO.getZjh());
                                if (Objects.nonNull(bdcQlrDO.getZjzl())) {
                                    bdcJzqrxxDTO.setJzqrzjzldm(bdcQlrDO.getZjzl().toString());
                                    //grdacxJzqr对象添加证件种类名称
                                    bdcJzqrxxDTO.setJzqrzjzlmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcQlrDO.getZjzl(), zdMap.get("zjzl")));
                                }
                                bdcJzqrxxDTOS.add(bdcJzqrxxDTO);
                            }
                            bdcJzqxxDTO.setJzqrxx(bdcJzqrxxDTOS);
                        }
                    }
                    jzqxxList.add(bdcJzqxxDTO);
                }
            }
        }
        return jzqxxList;
    }

    @Override
    public List<BdcFwqlDTO> listBdcFwqlDTO(BdcFwqlQO bdcFwqlQO) {
        if (null == bdcFwqlQO || CollectionUtils.isEmpty(bdcFwqlQO.getQlrxx())) {
            throw new AppException(ErrorCode.NPE_EX, "房屋权属查询接口报错：查询权利人参数为空");
        }

        for (BdcFwqlQlrQO bdcQlrQO : bdcFwqlQO.getQlrxx()) {
            if (StringUtils.isBlank(bdcQlrQO.getQlrmc()) && StringUtils.isBlank(bdcQlrQO.getZjh())) {
                throw new AppException(ErrorCode.NPE_EX, "房屋权属查询接口报错：权利人名称和证件号同时为空！");
            }

            // 证件号允许根据名称查询
            if (StringUtils.isNotBlank(bdcQlrQO.getZjh())) {
                // 转换身份证号
                bdcQlrQO.setZjh(Stream.of(bdcQlrQO.getZjh()).map(e ->
                        CardNumberTransformation.zjhTransformation(e)).collect(Collectors.joining(",")));
            }
        }
        return bdcZfxxMapper.listBdcFwqlDTO(bdcFwqlQO);
    }

    /**
     * （盐城）查询房屋权属信息（以物为主）
     *
     * @param bdcFwQsxxQO 查询参数
     * @return {List} 房屋权属信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public List<BdcFwJbxxDTO> listBdcFwQsxxDTO(BdcFwQsxxQO bdcFwQsxxQO) {
        if (null == bdcFwQsxxQO || CollectionUtils.isEmpty(bdcFwQsxxQO.getQlrxx())) {
            throw new AppException(ErrorCode.NPE_EX, "查询房屋权属信息报错：查询权利人参数为空");
        }

        // 处理下权利人查询参数
        for (BdcFwQsxxQlrQO bdcFwQsxxQlrQO : bdcFwQsxxQO.getQlrxx()) {
            if (StringUtils.isBlank(bdcFwQsxxQlrQO.getQlr()) && StringUtils.isBlank(bdcFwQsxxQlrQO.getSfzh())) {
                throw new AppException(ErrorCode.NPE_EX, "查询房屋权属信息报错：权利人名称和证件号同时为空！");
            }

            if (StringUtils.isNotBlank(bdcFwQsxxQlrQO.getSfzh())) {
                // 转换身份证号（同时查询15、18位身份证，用逗号拼接）
                bdcFwQsxxQlrQO.setSfzh(Stream.of(bdcFwQsxxQlrQO.getSfzh()).map(e -> CardNumberTransformation.zjhTransformation(e)).collect(Collectors.joining(",")));
            }
        }

        // 处理规划用途查询参数
        this.setGhyt(bdcFwQsxxQO);
        List<BdcFwJbxxDTO> fwJbxxList = bdcZfxxMapper.listBdcFwQsxxDTO(bdcFwQsxxQO);
        if (CollectionUtils.isNotEmpty(fwJbxxList)) {
            int index = 1;
            for (BdcFwJbxxDTO fwJbxx : fwJbxxList) {
                fwJbxx.setXh(index++);
            }
        }

        return fwJbxxList;
    }

    /**
     * @param bdcZfxxQO 查询参数
     * @return {List} 住房信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description （盐城）根据权利人名称、证件号查询历史房产信息（例如三年内在不动产登记系统中由于买卖转移登记而注销的产权信息）
     */
    @Override
    public List<BdcZfxxDTO> listBdcLsZfxxDTO(BdcZfxxQO bdcZfxxQO) {
        if (null == bdcZfxxQO || CollectionUtils.isEmpty(bdcZfxxQO.getQlrxx())) {
            throw new AppException(ErrorCode.NPE_EX, "住房查询接口报错：住房查询权利人参数为空");
        }

        for (BdcQlrQO bdcQlrQO : bdcZfxxQO.getQlrxx()) {
            if (StringUtils.isBlank(bdcQlrQO.getQlrmc()) && StringUtils.isBlank(bdcQlrQO.getZjh())) {
                throw new AppException(ErrorCode.NPE_EX, "住房查询接口报错：权利人名称和证件号同时为空！");
            }

            // 允许根据名称查询
            if (StringUtils.isNotBlank(bdcQlrQO.getZjh())) {
                bdcQlrQO.setZjh(replaceBlank(bdcQlrQO.getZjh()));
                // 转换身份证号
                bdcQlrQO.setZjh(Stream.of(bdcQlrQO.getZjh()).map(e -> CardNumberTransformation.zjhTransformation(e)).collect(Collectors.joining(",")));
            }

            //权利人名称与证件号内容去除空格
            if (StringUtils.isNotBlank(bdcQlrQO.getQlrmc())) {
                bdcQlrQO.setQlrmc(replaceBlank(bdcQlrQO.getQlrmc()));
            }
        }

        // 处理规划用途查询参数
        this.setGhyt(bdcZfxxQO);
        List<BdcZfxxDTO> zfxxDTOList = bdcZfxxMapper.listBdcLsZfxxDTO(bdcZfxxQO);

        if (true == glzjdsj) {
            // 需要过滤掉宅基地数据
            return this.filterZjdsj(zfxxDTOList);
        }
        return zfxxDTOList;
    }

    /**
     * 自助查询机查询合同信息
     *
     * @param bdcZfxxQO
     * @return
     */
    @Override
    public List<BdcZfxxDTO> listBdcHtxx(BdcZfxxQO bdcZfxxQO) {
        List<BdcZfxxDTO> bdcZfxxDTOS = new ArrayList<>();
        if(CollectionUtils.isEmpty(bdcZfxxQO.getQlrxx())){
            return bdcZfxxDTOS;
        }
        List<String> zjhList = bdcZfxxQO.getQlrxx().stream()
                .filter(bdcQlrQO -> StringUtils.isNotBlank(bdcQlrQO.getZjh()))
                .map(BdcQlrQO::getZjh).collect(Collectors.toList());

        List<String> qlrmcList = bdcZfxxQO.getQlrxx().stream()
                .filter(bdcQlrQO -> StringUtils.isNotBlank(bdcQlrQO.getQlrmc()))
                .map(BdcQlrQO::getQlrmc).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(zjhList) && CollectionUtils.isEmpty(qlrmcList)){
            return bdcZfxxDTOS;
        }
        try {
            bdcZfxxDTOS = bdcZfxxMapper.listBdcHtZfxxDTO(bdcZfxxQO);
        }catch (Exception e){
            LOGGER.info("查询住房信息错误{}:{}", JSON.toJSONString(bdcZfxxQO),e.getMessage());
        }
        return bdcZfxxDTOS;
    }

    /**
     * 设置查询房产信息规划用途过滤参数值
     *
     * @param bdcZfxxQO 查询参数实体
     */
    private void setGhyt(BdcZfxxQO bdcZfxxQO) {
        if (StringUtils.isNotBlank(pcghyt)) {
            if (Boolean.parseBoolean(pcghyt)) {
                // true 指定类型不展示
                bdcZfxxQO.setPcghyt("Y");
            } else {
                // false 默认展示所有的，为了契合之前逻辑，则将查询条件中的sfghyt设置为 N
                bdcZfxxQO.setSfghyt("N");
            }
        }

        if ("N".equals(bdcZfxxQO.getSfghyt())) {
            // 明确声明不需要过滤规划用途
            bdcZfxxQO.setCxly(null);
            bdcZfxxQO.setGhyt(null);
        } else {
            // 其它都按照默认配置
            if (StringUtils.equals(BdcZfxxCxlyEnum.DJXT.getCode(), bdcZfxxQO.getCxly())) {
                // 登记系统：配置的房屋用途、空
                bdcZfxxQO.setGhyt(this.ghyt1);
            } else if (StringUtils.equals(BdcZfxxCxlyEnum.ZZJHJ.getCode(), bdcZfxxQO.getCxly())) {
                // 南通自助交互机
                bdcZfxxQO.setGhyt(this.ghyt3);
            } else {
                // 都默认走自助查询机逻辑
                bdcZfxxQO.setCxly(BdcZfxxCxlyEnum.ZZCXJ.getCode());
                bdcZfxxQO.setGhyt(this.ghyt2);
            }
        }
    }

    /**
     * 设置查询权属信息规划用途过滤参数值
     *
     * @param bdcFwQsxxQO 查询参数实体
     */
    private void setGhyt(BdcFwQsxxQO bdcFwQsxxQO) {
        if (StringUtils.isNotBlank(pcghyt)) {
            if (Boolean.parseBoolean(pcghyt)) {
                // true 指定类型不展示
                bdcFwQsxxQO.setPcghyt("Y");
            } else {
                // false 默认展示所有的，为了契合之前逻辑，则将查询条件中的sfghyt设置为 N
                bdcFwQsxxQO.setSfghyt("N");
            }
        }

        if ("N".equals(bdcFwQsxxQO.getSfghyt())) {
            // 明确声明不需要过滤规划用途
            bdcFwQsxxQO.setCxly(null);
            bdcFwQsxxQO.setGhyt(null);
        } else {
            // 自助查询机
            bdcFwQsxxQO.setCxly("2");
            bdcFwQsxxQO.setGhyt(this.ghyt2);
        }
    }

    private String replaceBlank(String value) {
        return value.replaceAll("\\s*", "");
    }
}
