package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.certificate.*;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.certificate.core.dto.BdcJjd;
import cn.gtmap.realestate.certificate.core.dto.BdcJjdXx;
import cn.gtmap.realestate.certificate.core.mapper.BdcYjdMapper;
import cn.gtmap.realestate.certificate.core.service.BdcXmService;
import cn.gtmap.realestate.certificate.service.BdcYjdService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.ErrorCodeConstants;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.BdcUrlDTO;
import cn.gtmap.realestate.common.core.dto.certificate.yjd.BdcYjdXmFilesDTO;
import cn.gtmap.realestate.common.core.dto.certificate.yjd.BdcYjdXmxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.yjd.ResultCode;
import cn.gtmap.realestate.common.core.enums.YjdYjztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYjdQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/3/4
 * @description 移交单业务实现类
 */
@Service
public class BdcYjdServiceImpl implements BdcYjdService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcYjdServiceImpl.class);

    // modified by chenyucheng 修改移交单编号方式,年份只要年 月日不要
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");

    @Autowired
    BdcYjdMapper bdcYjdMapper;
    @Autowired
    Repo repo;
    @Autowired
    MessageProvider messageProvider;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    BdcPrintFeignService bdcPrintFeignService;
    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    private BdcBhFeignService bdcBhFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    /**
     * 海门档案移交地址
     */
    @Value("${haimen.daurl:}")
    private String daurl;

    /**
     * @param bdcYjdQO 移交单查询QO
     * @return 查询结果，移交单是主表
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询移交单信息，以及当前移交单的多有项目
     */
    @Override
    public List<BdcYjdDTO> listYjdAndXm(BdcYjdQO bdcYjdQO) {
        List<BdcYjdDTO> bdcYjdDTOList = new ArrayList();
        List<BdcYjdDO> bdcYjdDOList = bdcYjdMapper.listYjd(bdcYjdQO);
        if (CollectionUtils.isNotEmpty(bdcYjdDOList)) {
            for (BdcYjdDO bdcYjdDO : bdcYjdDOList) {
                BdcYjdDTO bdcYjdDTO = new BdcYjdDTO();
                BeanUtils.copyProperties(bdcYjdDO, bdcYjdDTO);
                bdcYjdDTOList.add(bdcYjdDTO);
            }
        }
        return bdcYjdDTOList;
    }

    /**
     * @param bdcYjdQO 移交单查询QO
     * @return 查询结果，项目是主表
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询拥有移交单的项目
     */
    @Override
    public List<BdcXmYjdDTO> listXmOwnYjd(BdcYjdQO bdcYjdQO) {
        List<BdcXmYjdDTO> bdcXmYjdDTOList = bdcYjdMapper.listXmOwnYjd(bdcYjdQO);
        if (CollectionUtils.isNotEmpty(bdcXmYjdDTOList)) {
            for (BdcXmYjdDTO bdcXmYjdDTO : bdcXmYjdDTOList) {

                BdcYjdQO bdcYjdQOTemp = new BdcYjdQO();
                bdcYjdQOTemp.setXmid(bdcXmYjdDTO.getXmid());
                // 根据项目ID查询移交单信息
                List<BdcYjdDO> bdcYjdDOList = bdcYjdMapper.listYjd(bdcYjdQOTemp);
                bdcXmYjdDTO.setBdcYjdDOList(bdcYjdDOList);
            }
        }
        return bdcXmYjdDTOList;
    }

    /**
     * @param bdcYjdQO 移交单查询QO
     * @return 移交单对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成移交单编号
     */
    @Override
    public BdcYjdDO generateYjdBh(BdcYjdQO bdcYjdQO) {
        // modified by zhuyong 修改移交单编号方式
        // 获取移交单类型
        String yjdLx = this.getYjdLx(bdcYjdQO.getListXmid());

        // 当前用户只能看到 本组织的信息，用区县代码过滤
        String qxdm = "";
        List<OrganizationDto> listOrgDto = userManagerUtils.getCurrentUser().getOrgRecordList();
        if(CollectionUtils.isNotEmpty(listOrgDto)){
            qxdm = listOrgDto.get(0).getRegionCode();
        }
        // 获取流水号
        Integer lsh = bdcBhFeignService.queryLsh("BDC_YJD_" + yjdLx+"_" + qxdm, "ALL");
        // 生成编号
        String yjdBh = qxdm + "_" + yjdLx + LocalDateTime.now().format(formatter) + StringToolUtils.appendZero(String
                .valueOf
                (lsh), 6);

        BdcYjdDO bdcYjdDO = new BdcYjdDO();
        bdcYjdDO.setYjdid(UUIDGenerator.generate16());
        bdcYjdDO.setYjdbh(yjdBh);
        bdcYjdDO.setYjr(bdcYjdQO.getYjr());
        bdcYjdDO.setYjsj(new Date());
        entityMapper.insertSelective(bdcYjdDO);
        return bdcYjdDO;
    }

    /**
     * @param bdcYjdQO 移交单查询QO
     * @return BdcYjdDO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成并保存移交单信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BdcYjdDO generateAndSaveYjdxx(BdcYjdQO bdcYjdQO) {
        BdcYjdDO bdcYjdDO;
        if (StringUtils.isNotBlank(bdcYjdQO.getYjdid()) && StringUtils.isNotBlank(bdcYjdQO.getYjdbh())) {
            // 已有移交单编号，则不再生成
            bdcYjdDO = new BdcYjdDO();
            bdcYjdDO.setYjdid(bdcYjdQO.getYjdid());
            bdcYjdDO.setYjdbh(bdcYjdQO.getYjdbh());
        } else {
            // 重新生成移交单信息
            bdcYjdDO = generateYjdBh(bdcYjdQO);
        }
        List<BdcXmYjdGxDO> bdcXmYjdGxDOList = new ArrayList();
        if (null != bdcYjdDO && StringUtils.isNotBlank(bdcYjdDO.getYjdid())) {
            String yjdid = bdcYjdDO.getYjdid();
            for (int i = 0; i < bdcYjdQO.getListXmid().size(); i++) {
                String xmid = bdcYjdQO.getListXmid().get(i);
                BdcXmYjdGxDO bdcXmYjdGxDO = new BdcXmYjdGxDO();
                bdcXmYjdGxDO.setGxid(UUIDGenerator.generate16());
                bdcXmYjdGxDO.setXmid(xmid);
                bdcXmYjdGxDO.setYjdid(yjdid);
                bdcXmYjdGxDO.setSxh(i+1);
                bdcXmYjdGxDOList.add(bdcXmYjdGxDO);
            }
            entityMapper.insertBatchSelective(bdcXmYjdGxDOList);
        }

        return bdcYjdDO;
    }

    /**
     * @param yjdid 移交单ID
     * @param dylx
     * @param bdcUrlDTO
     * @return String
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取移交单打印Xml
     */
    @Override
    public String yjdPrintXml(String yjdid, String dylx, BdcUrlDTO bdcUrlDTO) {
        String xml = "";
        if (StringUtils.isNotBlank(yjdid)) {
            BdcYjdDO bdcYjdDO = entityMapper.selectByPrimaryKey(BdcYjdDO.class, yjdid);
            Map<String, List<Map>> paramMap = new HashMap();

            List<Map> maps = new ArrayList();
            Map<String, String> map = new HashMap();
            map.put("yjdid", yjdid);
            map.put("ewm", bdcUrlDTO.getRegisterUiUrl() + "/rest/v1.0/print/ewm/" + bdcYjdDO.getYjdbh());

            maps.add(map);
            paramMap.put(dylx, maps);
            xml = bdcPrintFeignService.print(paramMap);
        }
        return xml;
    }

    /**
     * @param bdcYjdDO 移交单DO
     * @return 更新结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新移交单
     */
    @Override
    public int updateBdcYjd(BdcYjdDO bdcYjdDO) {
        if (StringUtils.isBlank(bdcYjdDO.getYjdid())) {
            throw new MissingArgumentException("缺失移交单主键ID！");
        }
        return entityMapper.updateByPrimaryKeyNull(bdcYjdDO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmidList 项目ID集合
     * @return  {String} 移交单类型： ZS 证书 ； ZM  证明 ； QT 其它 ； ZSZX 房屋注销;  DYZX 抵押注销
     * @description 根据项目ID获取对应的移交单类型
     */
    private String getYjdLx(List<String> xmidList){
        if(CollectionUtils.isEmpty(xmidList)){
            throw new AppException(ErrorCodeConstants.YJDBH_CREAT_ERROR, "移交单生成失败，参数没有项目信息");
        }

        // 查询项目信息
        List<BdcXmDO> bdcXmDOList = new ArrayList<>();
        String djXmid = "";
        for (String xmid : xmidList) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                djXmid = xmid;
                break;
            }
        }

        //所有的xmid都没有查询到,则尝试到受理中查询
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            List<BdcSlXmDO> bdcSlXmDOS = bdcSlXmFeignService.listBdcSlXmByXmids(xmidList);
            if(CollectionUtils.isEmpty(bdcSlXmDOS)){
                throw new AppException(ErrorCodeConstants.YJDBH_CREAT_ERROR, "移交单生成失败，没有项目信息");
            }
            //代表全都是只登记不登簿的数据
            return "QT";
        }

        // 查询证书表判断证书、证明类型
        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(djXmid);
        if(CollectionUtils.isNotEmpty(bdcZsDOList) && null != bdcZsDOList.get(0).getZslx()){
            Integer zslx = bdcZsDOList.get(0).getZslx();
            if(1 == zslx.intValue()){
                return "ZS";
            } else if(2 == zslx.intValue()){
                return "ZM";
            }
        }

        // 判断是不是房屋注销、抵押注销
        BdcXmDO bdcXmDO = bdcXmDOList.get(0);
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
        if (null == bdcQl || StringUtils.isBlank(bdcQl.getQlid())) {
            // 没有对应权利说明是注销
            if(4 == bdcXmDO.getQllx()) {
                return "ZSZX";
            } else if(CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())) {
                return "DYZX";
            }
        }
        return "QT";
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable 分页信息
     * @param bdcYjdQO 移交单项目信息查询参数
     * @description  查询流程项目信息（用于海门新增移交单场景，根据流程移交）
     */
    @Override
    public Page<BdcYjdXmxxDTO> listBdcYjdXmxx(Pageable pageable, BdcYjdQO bdcYjdQO) {
        List<String> ListSlbh = new ArrayList<>();
        if(StringUtils.isNotBlank(bdcYjdQO.getSlbh())){
            List listslbh = Arrays.asList(bdcYjdQO.getSlbh().split(","));
            ListSlbh.addAll(listslbh);
            bdcYjdQO.setSlbh(null);
        }
        if(StringUtils.isNotBlank(bdcYjdQO.getSlbhsd())){
            List listslbh = Arrays.asList(bdcYjdQO.getSlbhsd().split(","));
            ListSlbh.addAll(listslbh);
            bdcYjdQO.setSlbhsd(null);
        }
        bdcYjdQO.setListSlbh(ListSlbh);
        // 这里用集合查询，SQL中的slbh用于查看项目时候查询

        Page<BdcYjdXmxxDTO> yjdPage = repo.selectPaging("listBdcYjdXmxxByPageOrder", bdcYjdQO, pageable);
        if (null == yjdPage || CollectionUtils.isEmpty(yjdPage.getContent())) {
            return yjdPage;
        }

        // 按照用户扫描的受理编号顺序排序
        // 前台一页设置100条，用户一般最多一次扫描二三十条，当前逻辑层排序满足需求，不走SQL
        if(CollectionUtils.isNotEmpty(bdcYjdQO.getListSlbh()) && bdcYjdQO.getListSlbh().size() > 1){
            // 缓存排序后的移交单信息
            List<BdcYjdXmxxDTO> yjdList = new ArrayList<>(yjdPage.getContent().size());

            // 按照受理编号分组，因为查询时候按照工作流分组，所以这里一条受理编号对应的List数量大小就是1
            Map<String, List<BdcYjdXmxxDTO>> yjdMap = yjdPage.getContent().stream().collect(Collectors.groupingBy(BdcYjdXmxxDTO::getSlbh));
            if(MapUtils.isEmpty(yjdMap)) {
                return yjdPage;
            }

            for(String slbh : bdcYjdQO.getListSlbh()) {
                List<BdcYjdXmxxDTO> yjdXmxxDTOList = yjdMap.get(slbh);
                if(CollectionUtils.isEmpty(yjdXmxxDTOList)) {
                    continue;
                }

                yjdList.add(yjdXmxxDTOList.get(0));
            }
            return new PageImpl<>(yjdList, pageable, yjdList.size());
        }

        return yjdPage;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYjdXmxxDTO 项目信息
     * @return wjy : 没有移交过； yyj : 已经移交过
     * @description  核查当前项目是否已经移交过
     */
    @Override
    public String checkState(BdcYjdXmxxDTO bdcYjdXmxxDTO) {
        if(null == bdcYjdXmxxDTO || StringUtils.isBlank(bdcYjdXmxxDTO.getSlbh())) {
            throw new MissingArgumentException("项目数据为空");
        }

        List<BdcYjdDO> bdcYjdDOList = bdcYjdMapper.listBdcYjd(bdcYjdXmxxDTO.getSlbh());
        if(CollectionUtils.isNotEmpty(bdcYjdDOList)) {
            return "yyj";
        }
        return "wjy";
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param slbhList 受理编号集合
     * @return 移交单信息
     * @description  （海门）生成移交单
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BdcYjdDO generateBdcYjd(List<String> slbhList) {
        if(CollectionUtils.isEmpty(slbhList)) {
            throw new MissingArgumentException("未设置需要移交的项目");
        }

        // 1、生成交接单信息
        BdcYjdDO bdcYjdDO = new BdcYjdDO();
        bdcYjdDO.setYjdid(UUIDGenerator.generate16());
        bdcYjdDO.setYjdbh(bdcBhFeignService.queryCommonBh(Constants.HAIMEN_YJD, CommonConstantUtils.ZZSJFW_YEAR, 6, ""));
        bdcYjdDO.setYjsj(new Date());
        bdcYjdDO.setYjr(userManagerUtils.getUserAlias());
        // 初始状态：尚未交接
        bdcYjdDO.setYjzt(YjdYjztEnum.SWJJ.getCode());
        entityMapper.insertSelective(bdcYjdDO);

        // 2、生成移交单和项目的关系信息
        // 2.1、移交单顺序需要和用户扫描的受理编号顺序一致，这里需要根据受理编号处理下顺序
        /// 查询受理编号对应的项目信息
        List<BdcXmDO> bdcXmDOList = bdcYjdMapper.listBdcXm(slbhList);
        if(CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new AppException("当前移交操作未设置需要移交的项目！");
        }

        /// 按照受理编号分组
        Map<String, List<BdcXmDO>> xmMap = bdcXmDOList.stream().collect(Collectors.groupingBy(BdcXmDO::getSlbh));
        if(MapUtils.isEmpty(xmMap)) {
            throw new AppException("当前移交操作未设置需要移交的项目！");
        }

        /// 按照受理编号顺序取出对应项目
        List<BdcXmDO> bdcXmDOSortedList = new ArrayList<>(bdcXmDOList.size());
        for(String slbh : slbhList) {
            List<BdcXmDO> xmDOList = xmMap.get(slbh);
            if(CollectionUtils.isEmpty(xmDOList)) {
                continue;
            }
            bdcXmDOSortedList.addAll(xmDOList);
        }

        // 2.2、保存移交单和项目的关系数据
        List<BdcXmYjdGxDO> xmYjdGxDOList = new ArrayList();
        int index = 1;
        for(BdcXmDO bdcXmDO : bdcXmDOSortedList) {
            BdcXmYjdGxDO bdcXmYjdGxDO = new BdcXmYjdGxDO();
            bdcXmYjdGxDO.setGxid(UUIDGenerator.generate16());
            bdcXmYjdGxDO.setXmid(bdcXmDO.getXmid());
            bdcXmYjdGxDO.setYjdid(bdcYjdDO.getYjdid());
            bdcXmYjdGxDO.setSxh(index++);
            xmYjdGxDOList.add(bdcXmYjdGxDO);
        }
        entityMapper.insertBatchSelective(xmYjdGxDOList);

        LOGGER.info("海门生成移交单成功！对应的受理编号：{}" , slbhList.toString());
        return bdcYjdDO;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYjdDOList 移交单集合
     * @return  操作返回信息
     * @description （海门）进行移交单移交到档案
     */
    @Override
    public String executeYj(List<BdcYjdDO> bdcYjdDOList) {
        if (CollectionUtils.isEmpty(bdcYjdDOList)) {
            LOGGER.error("海门移交单移交失败，未设置需要移交的移交单");
            return "未设置需要移交的移交单";
        }

        if(StringUtils.isBlank(daurl)) {
            LOGGER.error("海门移交单移交失败，未设置档案移交地址 haimen.daurl");
            return "未设置档案移交地址 haimen.daurl，请联系管理员！";
        }

        String yjdbh = "";
        try {
            for (BdcYjdDO bdcYjdDO : bdcYjdDOList) {
                if(null == bdcYjdDO || StringUtils.isAnyBlank(bdcYjdDO.getYjdid(), bdcYjdDO.getYjdbh())) {
                    LOGGER.error("海门移交单移交失败，移交单信息为空");
                    return "移交单信息为空（ID、编号），请补充数据！";
                }
                yjdbh = bdcYjdDO.getYjdbh();

                // 核查状态
                if(this.stateCheck(bdcYjdDO.getYjdid())) {
                    LOGGER.error("海门移交单移交失败，移交单已经移交过{}！", yjdbh);
                    return "移交单" + yjdbh + "已经移交过";
                }

                // 转换交接单信息
                BdcJjd bdcJjd = this.resolveBdcJjd(bdcYjdDO);

                // 转换交接单关联的项目信息
                List<BdcJjdXx> bdcJjdXxList = this.resolveBdcJjdXx(bdcYjdDO);
                if(CollectionUtils.isEmpty(bdcJjdXxList)) {
                    LOGGER.error("海门移交单移交失败，没有关联的项目信息，对应移交单编号：{}", yjdbh);
                    return "当前移交单" + yjdbh + "没有关联的项目信息！";
                }

                // 调用档案接口移交
                this.postRequest(bdcJjd, bdcJjdXxList);

                // 更新交接状态：已交接，尚未接受
                this.updateYjdYjzt(bdcYjdDO.getYjdid());
            }

            LOGGER.info("海门移交单移交成功，包含移交单编号：{}", bdcYjdDOList.stream().map(p -> p.getYjdbh()).collect(Collectors.toList()).toString());
            return "移交成功";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("海门移交单移交失败，对应移交单编号：{}", yjdbh);
            return "编号" + yjdbh + "移交单移交失败！";
        }
    }

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @param slbhList 受理编号集合
     * @return  受理编号相关文件信息
     * @description （海门）提供受理编号相关文件信息给档案
     */
    @Override
    public List<BdcYjdXmFilesDTO> getFilesBySlbhList(List<String> slbhList) {
        //初始化返回信息
        List<BdcYjdXmFilesDTO> yjdXmInfoWithFiles = new ArrayList<>();
        //受理编号去重
        try {
            LOGGER.info("海门受理编号列表获取附件信息开始，参数："+slbhList.toString());
            Set<String> tempSlbhList = new HashSet<>();
            //获取受理编号相关的项目信息和流程实例信息
            List<BdcXmDO> bdcXmDOS = bdcXmService.listBdcXmFiles(slbhList);

            if (bdcXmDOS!=null && bdcXmDOS.size()>0){
                bdcXmDOS.forEach(bdcXmDO -> {
                    if (!tempSlbhList.contains(bdcXmDO.getSlbh())){
                        //获取对应流程实例编号的文件信息
                        yjdXmInfoWithFiles.add(getBdcYjdXmFilesDTO(bdcXmDO));
                    }
                });
            }
        }catch (Exception e){
            LOGGER.error("海门受理编号列表获取附件信息异常",e);
        }
        //返回
        LOGGER.info("海门受理编号列表获取附件信息结束");
        return yjdXmInfoWithFiles;

    }

    private BdcYjdXmFilesDTO getBdcYjdXmFilesDTO(BdcXmDO bdcXmDO) {
        BdcYjdXmFilesDTO bdcYjdXmFilesDTO = new BdcYjdXmFilesDTO();
        bdcYjdXmFilesDTO.setXmid(bdcXmDO.getXmid());
        bdcYjdXmFilesDTO.setSlbh(bdcXmDO.getSlbh());
        bdcYjdXmFilesDTO.setGzlslid(bdcXmDO.getGzlslid());
        //调用大云接口获取文件信息
        List<StorageDto> storageDtos = storageClient.queryMenus("clientId", bdcXmDO.getGzlslid(), "", null, null, null, null,null);
        storageDtos.removeIf(storageDto -> Constants.ELECTRONIC_LICENSE_FLODER_NAME.equals(storageDto.getName()));
        bdcYjdXmFilesDTO.setFileListJsonString(JSON.toJSONString(storageDtos));
        return bdcYjdXmFilesDTO;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param yjxx 移交信息
     * @return {ResultCode} 返回状态信息实体
     * @description （海门）提供给档案接口，档案系统接收档案后回调该接口进行更新接收人等信息
     */
    @Transactional
    @Override
    public ResultCode updateYjxx(List<BdcYjdDO> yjxx) {
        if(CollectionUtils.isEmpty(yjxx)) {
            return this.getResultCode(1, "未定义需要更新的移交单数据");
        }

        try{
            for(BdcYjdDO bdcYjdDO : yjxx) {
                if(null == bdcYjdDO){
                    return this.getResultCode(1, "移交单数据为空");
                }

                if(StringUtils.isBlank(bdcYjdDO.getYjdbh())){
                    return this.getResultCode(1, "移交单编号数据为空");
                }

                if(null == bdcYjdDO.getYjzt()){
                    return this.getResultCode(1, "交接状态未定义");
                }

                Example example = new Example(BdcYjdDO.class);
                example.createCriteria().andEqualTo("yjdbh", bdcYjdDO.getYjdbh());
                entityMapper.updateByExampleSelective(bdcYjdDO, example);

                LOGGER.info("海门档案系统回调登记更新状态成功：{}", bdcYjdDO.toString());
            }
        } catch (Exception e) {
            LOGGER.error("海门档案更新登记移交状态失败，{}", e.getMessage());
            return this.getResultCode(2, "处理异常");
        }
        return new ResultCode(0, "更新状态成功");
    }

    private ResultCode getResultCode(int code, String message) {
        LOGGER.error("海门档案更新登记移交状态失败，原因：{}", message);
        return new ResultCode(code, message);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param slbh 当前项目受理编号
     * @return {List} 上一手受理编号
     * @description （海门提供给档案接口）根据受理编号获取上一手项目受理编号
     */
    @Override
    public List<String> listPreSlbh(String slbh) {
        if(StringUtils.isBlank(slbh)) {
            throw new AppException("未定义受理编号参数");
        }

        List<BdcXmDO> bdcXmDOList = bdcYjdMapper.listPreXmxx(slbh);
        if(CollectionUtils.isEmpty(bdcXmDOList)) {
            return Collections.emptyList();
        }

        return bdcXmDOList.stream().map(BdcXmDO::getSlbh).collect(Collectors.toList());
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param slbh 当前项目受理编号
     * @return {List} 下一手受理编号
     * @description （海门提供给档案接口）根据受理编号获取下一手项目受理编号
     */
    @Override
    public List<String> listNextSlbh(String slbh) {
        if(StringUtils.isBlank(slbh)) {
            throw new AppException("未定义受理编号参数");
        }

        List<BdcXmDO> bdcXmDOList = bdcYjdMapper.listNextXmxx(slbh);
        if(CollectionUtils.isEmpty(bdcXmDOList)) {
            return Collections.emptyList();
        }

        return bdcXmDOList.stream().map(BdcXmDO::getSlbh).collect(Collectors.toList());
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcHaimenYhYjdDTO
     * @description 海门银行系统提供移交单接口
     */
    @Override
    public void saveHaimenYhYjd(BdcHaimenYhYjdDTO bdcHaimenYhYjdDTO) {

        if(CollectionUtils.isEmpty(bdcHaimenYhYjdDTO.getSlbhList())) {
            throw new MissingArgumentException("未获取到需要移交的项目");
        }


        // 1、生成交接单信息
        String yhYjdbh = bdcHaimenYhYjdDTO.getYjdbh();
        BdcYjdDO bdcYjdDO = new BdcYjdDO();
        bdcYjdDO.setYjdid(UUIDGenerator.generate16());
        bdcYjdDO.setYjdbh("yh" + yhYjdbh);
        bdcYjdDO.setYjsj(bdcHaimenYhYjdDTO.getYjsj());
        bdcYjdDO.setYjr(bdcHaimenYhYjdDTO.getYjr());
        // 初始状态：尚未交接
        bdcYjdDO.setYjzt(Integer.parseInt(bdcHaimenYhYjdDTO.getJjdzt()));
        entityMapper.insertSelective(bdcYjdDO);

        // 2、生成移交单和项目的关系信息
        // 2.1、移交单顺序需要和用户扫描的受理编号顺序一致，这里需要根据受理编号处理下顺序
        /// 查询受理编号对应的项目信息
        List<BdcXmDO> bdcXmDOList = bdcYjdMapper.listBdcXm(bdcHaimenYhYjdDTO.getSlbhList());
        if(CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new AppException("当前移交操作未设置需要移交的项目！");
        }

        /// 按照受理编号分组
        Map<String, List<BdcXmDO>> xmMap = bdcXmDOList.stream().collect(Collectors.groupingBy(BdcXmDO::getSlbh));
        if(MapUtils.isEmpty(xmMap)) {
            throw new AppException("当前移交操作未设置需要移交的项目！");
        }

        /// 按照受理编号顺序取出对应项目
        List<BdcXmDO> bdcXmDOSortedList = new ArrayList<>(bdcXmDOList.size());
        for(String slbh : bdcHaimenYhYjdDTO.getSlbhList()) {
            List<BdcXmDO> xmDOList = xmMap.get(slbh);
            if(CollectionUtils.isEmpty(xmDOList)) {
                continue;
            }
            bdcXmDOSortedList.addAll(xmDOList);
        }

        // 2.2、保存移交单和项目的关系数据
        List<BdcXmYjdGxDO> xmYjdGxDOList = new ArrayList();
        int index = 1;
        for(BdcXmDO bdcXmDO : bdcXmDOSortedList) {
            BdcXmYjdGxDO bdcXmYjdGxDO = new BdcXmYjdGxDO();
            bdcXmYjdGxDO.setGxid(UUIDGenerator.generate16());
            bdcXmYjdGxDO.setXmid(bdcXmDO.getXmid());
            bdcXmYjdGxDO.setYjdid(bdcYjdDO.getYjdid());
            bdcXmYjdGxDO.setSxh(index++);
            xmYjdGxDOList.add(bdcXmYjdGxDO);
        }
        entityMapper.insertBatchSelective(xmYjdGxDOList);
    }

    @Override
    public int updateYjdDyztByTaskid(List<String> taskids) {
        return bdcYjdMapper.updateYjdDyztByTaskid(taskids);
    }

    @Override
    public List<BdcYjdTaskGxDO> getYjdTaskGx(Map paramMap) {
        return bdcYjdMapper.getYjdTaskGx(paramMap);
    }

    /**
     * @param pageable
     * @param paramMap
     * @return
     */
    @Override
    public Page<BdcYjdGdxxDTO> listBdcYjdGdxx(Pageable pageable, HashMap<String, Object> paramMap) {
        //登记的数据
        List<BdcYjdGdxxDTO> bdcYjdGdxxDTOS = bdcYjdMapper.listBdcYjdGdxxByPage(paramMap);

        //查询受理的数据
        //查询出所有的xmid
        if(paramMap.containsKey("yjdid") && StringUtils.isNotBlank((String)paramMap.get("yjdid"))) {
            Example example = new Example(BdcXmYjdGxDO.class);
            example.createCriteria().andEqualTo("yjdid", paramMap.get("yjdid"));
            List<BdcXmYjdGxDO> bdcXmYjdGxDOList = entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(bdcXmYjdGxDOList)){
                List<String> xmids = bdcXmYjdGxDOList
                        .stream()
                        .map(BdcXmYjdGxDO::getXmid).collect(Collectors.toList());
                Map<String, BdcXmYjdGxDO> bdcXmYjdGxMap = bdcXmYjdGxDOList
                        .stream()
                        .collect(Collectors.toMap(BdcXmYjdGxDO::getXmid, o -> o));
                paramMap.put("xmidList",xmids);
                List<BdcXmGdxxDTO> bdcXmGdxxDTOS = bdcSlXmFeignService.listBdcGdxxHf(paramMap);
                if(CollectionUtils.isNotEmpty(bdcXmGdxxDTOS)){
                    for (BdcXmGdxxDTO bdcXmGdxxDTO : bdcXmGdxxDTOS) {
                        BdcYjdGdxxDTO bdcXmYjdGxDO = new BdcYjdGdxxDTO();
                        BeanUtils.copyProperties(bdcXmGdxxDTO,bdcXmYjdGxDO);
                        bdcXmYjdGxDO.setYjdid((String) paramMap.get("yjdid"));
                        if(bdcXmYjdGxMap.containsKey(bdcXmGdxxDTO.getXmid())) {
                            bdcXmYjdGxDO.setGxid(bdcXmYjdGxMap.get(bdcXmGdxxDTO.getXmid()).getGxid());
                            bdcYjdGdxxDTOS.add(bdcXmYjdGxDO);
                        }
                    }
                }
            }
        }

        if (CollectionUtils.isEmpty(bdcYjdGdxxDTOS)){
            return null;
        }

        //内存分页
        return PageUtils.listToPage(bdcYjdGdxxDTOS, pageable);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param yjdid 移交单ID
     * @return true：移交过  false：未移交过
     * @description （海门）核查当前移交单是否已经移交过
     */
    private boolean stateCheck(String yjdid) {
        BdcYjdDO yjd = entityMapper.selectByPrimaryKey(BdcYjdDO.class, yjdid);
        if(null != yjd.getYjzt()) {
            int yjzt = yjd.getYjzt().intValue();
            if(YjdYjztEnum.YJJ.getCode().intValue() == yjzt || YjdYjztEnum.JJCG.getCode().intValue() == yjzt) {
                return true;
            }
        }
        return false;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYjdDO 移交单信息
     * @return  兼容2.0档案移交实体信息
     * @description （海门）将当前移交单信息转换兼容为2.0中的交接单实体（因为档案系统不变，登记系统升级，3.0需要兼容）
     */
    private BdcJjd resolveBdcJjd(BdcYjdDO bdcYjdDO) {
        BdcJjd bdcJjd = new BdcJjd();
        bdcJjd.setJjdid(bdcYjdDO.getYjdid());
        bdcJjd.setJjdbh(bdcYjdDO.getYjdbh());
        bdcJjd.setJjr(bdcYjdDO.getYjr());
        bdcJjd.setJjrq(bdcYjdDO.getYjsj());
        bdcJjd.setJjdlx("手动交接单");
        return bdcJjd;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYjdDO 移交单信息
     * @return  兼容2.0档案移交项目实体信息
     * @description （海门）将当前移交单项目信息转换兼容为2.0中的交接单项目实体（因为档案系统不变，登记系统升级，3.0需要兼容）
     */
    private List<BdcJjdXx> resolveBdcJjdXx(BdcYjdDO bdcYjdDO) {
        // 查询移交单关联的项目信息
        Example example = new Example(BdcXmYjdGxDO.class);
        example.createCriteria().andEqualTo("yjdid", bdcYjdDO.getYjdid());
        List<BdcXmYjdGxDO> bdcXmYjdGxDOList = entityMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(bdcXmYjdGxDOList)) {
            return Collections.emptyList();
        }

        // 用于缓存 slbh, 按照流程归档，避免批量、组合等流程重复提交
        Set<String> slbhSet = new HashSet<>();

        int index = 0;
        List<BdcJjdXx> bdcJjdXxList = new ArrayList<>();

        // 处理移交单对应项目信息
        for(BdcXmYjdGxDO bdcXmYjdGxDO : bdcXmYjdGxDOList) {
            // 获取项目受理编号
            String slbh = this.getSlbhByXmid(bdcXmYjdGxDO.getXmid());
            if(slbhSet.contains(slbh)) {
                continue;
            }
            slbhSet.add(slbh);

            // 获取关联的项目信息
            BdcYjdXmxxDTO bdcYjdXmxxDTO = this.getYjdXmxx(slbh);

            // 转换实体
            BdcJjdXx bdcJjdXx = new BdcJjdXx();
            bdcJjdXx.setJjdxxid(bdcXmYjdGxDO.getGxid());
            bdcJjdXx.setJjdid(bdcXmYjdGxDO.getYjdid());
            bdcJjdXx.setSlh(slbh);
            bdcJjdXx.setProid(bdcXmYjdGxDO.getXmid());
            bdcJjdXx.setYwdjlx(bdcYjdXmxxDTO.getDjlx());
            bdcJjdXx.setSqr(bdcYjdXmxxDTO.getQlr());
            bdcJjdXx.setBdcdyh(bdcYjdXmxxDTO.getBdcdyh());
            bdcJjdXx.setBdcdyhs(bdcYjdXmxxDTO.getBdcdyh());
            bdcJjdXx.setZl(bdcYjdXmxxDTO.getZl());
            bdcJjdXx.setXl(String.valueOf(index++));
            bdcJjdXx.setCqzh(bdcYjdXmxxDTO.getBdcqzh());
            bdcJjdXxList.add(bdcJjdXx);
        }
        return bdcJjdXxList;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取指定项目的受理编号
     */
    private String getSlbhByXmid(String xmid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList) || null == bdcXmDOList.get(0)) {
            throw new MissingArgumentException("当前移交单关联的项目信息不存在，无法移交！");
        }

        return bdcXmDOList.get(0).getSlbh();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取移交单项目信息(一个流程对应的项目合并)
     */
    private BdcYjdXmxxDTO getYjdXmxx(String slbh) {
        BdcYjdQO bdcYjdQO = new BdcYjdQO();
        bdcYjdQO.setSlbh(slbh);
        bdcYjdQO.setSfcz(2);
        List<BdcYjdXmxxDTO> bdcYjdXmxxDTOList = bdcYjdMapper.listBdcYjdXmxxByPageOrder(bdcYjdQO);

        if(CollectionUtils.isEmpty(bdcYjdXmxxDTOList) || null == bdcYjdXmxxDTOList.get(0)) {
            throw new MissingArgumentException("当前移交单关联的项目信息不存在，无法移交！");
        }
        return bdcYjdXmxxDTOList.get(0);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcJjd 移交单信息
     * @param bdcJjdXxList 移交单关联的项目信息
     * @throws IOException
     * @description 请求档案进行移交
     */
    private void postRequest(BdcJjd bdcJjd, List<BdcJjdXx> bdcJjdXxList) throws IOException{
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

        PostMethod method = new PostMethod(daurl);
        String yjdJson = JSONObject.toJSONStringWithDateFormat(bdcJjd, "yyyy-MM-dd hh:MM:ss");
        LOGGER.info("海门档案移交移交单内容：{}", yjdJson);
        method.setParameter("bdcjjd", yjdJson);

        String yjdXmJson = JSON.toJSONString(bdcJjdXxList);
        LOGGER.info("海门档案移交移交项目内容：{}", yjdXmJson);
        method.setParameter("bdcjjdxx", yjdXmJson);

        httpClient.executeMethod(method);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  更新交接状态：已交接，尚未接受
     */
    private void updateYjdYjzt(String yjdid) {
        BdcYjdDO bdcYjdDO = new BdcYjdDO();
        bdcYjdDO.setYjzt(YjdYjztEnum.YJJ.getCode());
        bdcYjdDO.setYjdid(yjdid);
        entityMapper.updateByPrimaryKeySelective(bdcYjdDO);
    }

}
