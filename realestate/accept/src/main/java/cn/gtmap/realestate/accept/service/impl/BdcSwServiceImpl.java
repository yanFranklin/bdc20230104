package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.accept.core.service.*;
import cn.gtmap.realestate.accept.service.*;
import cn.gtmap.realestate.accept.util.CommonUtil;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.swxx.DzpxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.swxx.SwxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.YrbFhmResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfskxxhq.request.YrbClfskxxhqRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfskxxhq.request.YrbClfskxxhqTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfskxxhq.response.ClfskxxhqResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfskxxhq.response.YrbClfskxxhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.ewmjkxx.request.YrbEwmjkxxRequest;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.ewmjkxx.request.YrbEwmjkxxTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.ewmjkxx.response.YrbEwmjkxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.ewmjkxx.response.YrbEwmjkxxResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjydjkxxhq.request.FcjydjkxxhqRequest;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjydjkxxhq.request.FcjydjkxxhqTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjydjkxxhq.response.FcjydjkxxhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjydjkxxhq.response.FcjydjkxxhqResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjydjkxxhq.response.JkxxResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjydjkxxhq.response.JkxxgridlbResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyewmjkxx.request.YrbFcjyEwmRequest;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyewmjkxx.request.YrbFcjyEwmTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyewmjkxx.response.YrbFcjyEwmDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyewmjkxx.response.YrbFcjyEwmResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjysbxx.request.YrbFcjysbxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjysbxx.request.YrbFcjysbxxTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjysbxx.response.YrbFcjysbxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjysbxx.response.YrbFcjysbxxFjxxResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjysbxxqr.request.YrbFcjysbxxQrRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjysbxxqr.request.YrbFcjysbxxQrTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyyjk.request.YrbFcjyyjkRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyyjk.request.YrbFcjyyjkTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyyjk.response.YrbFcjyyjkDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyyjk.response.YrbFcjyyjkResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qslxdhq.request.YrbQslxdhqRequest;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qslxdhq.request.YrbQslxdhqTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qslxdhq.response.YrbQslxdhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qslxdhq.response.YrbQslxdhqResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswspzhq.request.YrbQswspzhqRequest;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswspzhq.request.YrbQswspzhqTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswspzhq.response.YrbQswspzhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswspzhq.response.YrbYrbQswspzhqResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswsxxhq.request.YrbQswsxxhqRequest;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswsxxhq.request.YrbQswsxxhqTzxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswsxxhq.response.YrbQswsxxhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.rwzt.request.YrbRwztRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.rwzt.request.YrbRwztTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.rwzt.response.YrbRwztDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.sbztcx.request.YrbSbztcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.sbztcx.request.YrbSbztcxTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.sbztcx.response.YrbSbztcxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.sbztcx.response.YrbSbztcxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.yxzlxxjs.request.YrbYwwjxx;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.yxzlxxjs.request.YrbYxzlTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.yxzlxxjs.request.YrbYxzlxxjsList;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfskxxhq.request.YrbZlfskxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfskxxhq.request.YrbZlfskxxTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfskxxhq.response.YrbZlfjsxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfskxxhq.response.YrbZlfskxxResponse;
import cn.gtmap.realestate.common.core.dto.exchange.swxx.PlQuerySwxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.swxx.QuerySwxxHsxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.swxx.QuerySwxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.swxx.ReceiveSwxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.enums.BdcSfztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.BaseQO;
import cn.gtmap.realestate.common.core.qo.accept.*;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcGzyzFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwLjzFeginService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSwJkmxxVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSwxxVO;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/24
 * @description 税务信息
 */
@Service
public class BdcSwServiceImpl extends BaseController implements BdcSwService {
    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSwServiceImpl.class);

    private static final String SBTXH = "申报提醒函";

    private static final String SJGSDQ_QXDM = "qxdm";

    private static final String SJGSDQ_SSXZ = "ssxz";

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcSlJbxxService bdcSlJbxxService;
    @Autowired
    BdcSlXmService bdcSlXmService;
    @Autowired
    BdcSlFwxxService bdcSlFwxxService;
    @Autowired
    BdcSlJyxxService bdcSlJyxxService;
    @Autowired
    BdcSlSqrService bdcSlSqrService;
    @Autowired
    BdcSlHsxxService bdcSlHsxxService;
    @Autowired
    BdcSlHsxxMxService bdcSlHsxxMxService;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcSlSjclService bdcSlSjclService;
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    BdcSfService bdcSfService;
    @Autowired
    BdcSlSfxxService bdcSlSfxxService;
    @Autowired
    BdcSlSfssDdxxService bdcSlSfssDdxxService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    private FwHsFeignService fwHsFeignService;
    @Autowired
    private BdcSlJyxxCezsService bdcSlJyxxCezsService;
    @Autowired
    Set<BdcSwTsclService> bdcSwTsclServiceSet;
    @Autowired
    private BdcSlJtcyService bdcSlJtcyService;
    @Autowired
    private BdcSjclService bdcSjclService;
    @Autowired
    private BdcSlYcslDjywDzbService bdcSlYcslDjywDzbService;
    @Autowired
    private BdcGzyzService bdcGzyzService;
    @Autowired
    private BdcZsInitFeignService bdcZsInitFeignService;
    @Autowired
    BdcYcslManageService bdcYcslManageService;
    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    @Autowired
    BdcSlTdcrjService bdcSlTdcrjService;

    @Autowired
    BdcSlSysService bdcSlSysService;

    @Autowired
    FwLjzFeginService fwLjzFeginService;

    @Autowired
    BdcSlXmLsgxService bdcSlXmLsgxService;

    @Autowired
    BdcGzyzFeignService bdcGzyzFeignService;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Autowired
    BdcCzrzFeignService bdcCzrzFeignService;

    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;

    @Autowired
    private Repo repo;

    @Resource(name = "dozerSameNullFMapper")
    private DozerBeanMapper dozerMapperF;
    @Resource(name = "acceptDozerMapper")
    DozerBeanMapper acceptDozerMapper;

    /**
     * 商品房流程
     */
    @Value("#{'${spf.gzldyid:}'.split(',')}")
    protected List<String> spfdyidList;

    /**
     * 税务访问大云附件地址映射 IP 和 端口
     */
    @Value("${swjk.fj.ip_port:}")
    protected String swFjIpPort;

    /**
     * 申报提醒函下载地址映射 IP 和 端口
     */
    @Value("${swjk.sbtxh.ip_port:59.203.19.50:9790}")
    protected String sbtxhIpPort;

    /**
     * 接收税务信息是否计算合计
     */
    @Value("${jsswxx.jshj:false}")
    protected Boolean jsswhj;

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  版本控制
     */
    @Value("${data.version:}")
    private String dataversion;
    /**
     * 合肥税务接口德宽版本与英塔版本并行区分标识
     */
    @Value("${swjk.version:dk}")
    private String swjkVersion;

    /**
     * 南通推送税务需要根据房屋用途确定当前建筑面积是阁楼面积还是车库面积等
     * 字典类型顺序按照如下顺序：车库;储藏室;阁楼;  剩下的未配置的为权属转移面积
     * 例如： 1,2,3; 4,5; 6,7
     */
    @Value("${swjk.tsswFdcqGhyt:}")
    private String tsswFdcqGhyt;

    /**
     * 南通推送税务,推送曾用名
     */
    @Value("${swjk.tscym:false}")
    private boolean tscym;

    /**
     * 调用税务A014获取申报单接口，获取的申报单文件下载至附件材料下的文件夹名称
     */
    @Value("${yrbj.sbdwjjmc:申报单}")
    private String sbdwjjmc;
    /**
     * 推送税务进行申报单确认时，读取附件的文件夹目录
     */
    @Value("${yrbj.sbdqrwjjmc:}")
    private String sbdqrwjjmc;
    /**
     * 获取税务税费凭证文件，下载到附件材料下的文件夹名称
     */
    @Value("${yrbj.qswjjmc:税费缴纳凭证}")
    private String qspzwjjmc;

    // 数据归属地区版本
    @Value("${sjgsdq.version:}")
    private String sjgsdqVersion;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  数据归属地区取值来源，qxdm:按照区县代码对照取值 ssxz:按照所属乡镇对照取值
     */
    @Value("${sjgsdq.sjly:}")
    private String sjgsdqSjly;

    // 数据采集人员代码， 南通配置（yrbsl），常州为受理人
    @Value("${yrbj.lrrdm:}")
    private String lrrdm;

    // 【A020】契税完税凭证获取，获取到文件上传的文件夹名称
    @Value("${yrbj.qswspzwjjmc:电子契税完税凭证}")
    private String qswspzwjjmc;

    // 【A021】契税完税联系单获取，获取到文件上传的文件夹名称
    @Value("${yrbj.qslxdwjjmc:电子契税联系单}")
    private String qslxdwjjmc;

    @Value("${ycsl.xtid:}")
    private String ycslXtid;
    /**
    * 接收到互联网推送的已缴税后需要办结的工作流定义ID
    **/
    @Value("#{'${ycsl.yjsbjgzldyid:}'.split(',')}")
    protected List<String> yjsbjgzldyidList;

    @Value("#{'${spfzy.gzldyid:}'.split(',')}")
    private List<String> spfzygzldyidList;
    @Value("#{'${clfzy.gzldyid:}'.split(',')}")
    private List<String> clfzygzldyidList;
    @Value("#{${hqswxx.gsdqbmmcdz:null}}")
    private Map<String, String> gsdqbmmcdz;

    @Value("#{${swxx.qxdmdz:null}}")
    private Map<String, String> qxdmdzMap;
    /**
    * 盐城获取契税完税凭证A20接口特殊处理，参数中的收件编号传合同编号
    **/
    @Value("${swxx.sjbhtscl:false}")
    private boolean sjbhtscl;
    private static final List<Integer> sssplyList = new ArrayList<>(4);

    static {
        sssplyList.add(CommonConstantUtils.SPLY_YCSL);
        sssplyList.add(CommonConstantUtils.SPLY_WWSQ_YCSL);
    }

    @Override
    public Object tsSwxx(String gzlslid, String beanName) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        bdcXmQO.setSplys(sssplyList);
        // 查询涉税的项目信息
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new AppException("工作流实例id: " + gzlslid + ",涉税项目不存在！");
        }
        // 批量业务
        if (beanName.indexOf("pl") > 0) {
            if (bdcXmDOList.size() == 1) {
                // 并案业务，一手涉税，一手不涉税，走单个逻辑
                beanName = beanName.replace("_pl", "");
                return requestExchangeForTssw(gzlslid, beanName);
            } else {
                return plRequestExchangeForTssw(gzlslid, beanName);
            }
        }
        // 单个
        return requestExchangeForTssw(gzlslid, beanName);
    }

    /**
     * 批量推送业务信息到税务 德宽
     * @param gzlslid 工作流实例id
     * @param beanName
     * @return PlQuerySwxxResponseDTO
     */
    private Object plRequestExchangeForTssw(String gzlslid, String beanName) {
        List<TsswDataDTO> tsswDataDTOList = getAllDjTsswData(gzlslid, beanName);
        // 分组
        Map<String, List<TsswDataDTO>> tsDataMap =tsswDataDTOList.stream().collect(Collectors.groupingBy(TsswDataDTO::getBs));
        if (tsDataMap.keySet().size() == 2) {
            // 商品房+存量房
            return plzhRequestExchangeForTssw(gzlslid,tsDataMap);
        }
        // response 为 jsonobject 类型 可强转为响应实体方便业务处理
        LOGGER.info("批量推送业务信息到税务(德宽),接口请求参数：{}",JSON.toJSONString(tsswDataDTOList));
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, tsswDataDTOList);

        // 保存税务推送返回的 fwuuid
        if (Objects.nonNull(response)) {
            PlQuerySwxxResponseDTO responseDTO = JSON.parseObject(JSON.toJSONString(response), PlQuerySwxxResponseDTO.class);
            if (Objects.nonNull(responseDTO) && CollectionUtils.isNotEmpty(responseDTO.getSwxxResponseDTOS())) {
                LOGGER.info("税务批量推送业务信息到税务(德宽)接口调用成功！工作流实例id：{},响应内容：{}", gzlslid, responseDTO);
                List<String> fwuuids = new ArrayList<>();
                for (QuerySwxxResponseDTO querySwxxResponseDTO : responseDTO.getSwxxResponseDTOS()) {
                    // 保存fwwuid到bdc_sl_xm
                    if ("200".equals(querySwxxResponseDTO.getResponseCode()) && StringUtils.isNotBlank(querySwxxResponseDTO.getFwuuid())){
                        String fwuuid = querySwxxResponseDTO.getFwuuid();
                        if (StringUtils.isNotBlank(querySwxxResponseDTO.getXmid())){
                            BdcSlXmDO bdcSlXmDO = bdcSlXmService.queryBdcSlXmByXmid(querySwxxResponseDTO.getXmid());
                            bdcSlXmDO.setFwuuid(fwuuid);
                            bdcSlXmService.updateBdcSlXm(bdcSlXmDO);
                        }
                        fwuuids.add(fwuuid);
                    }
                }
                // fwuuid逗号拼接保存到bdcSlJbxxDO
                if (CollectionUtils.isNotEmpty(fwuuids)) {
                    BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
                    bdcSlJbxxDO.setFwuuid(String.join(",", fwuuids));
                    this.bdcSlJbxxService.updateBdcSlJbxx(bdcSlJbxxDO);
                }
            }
        }
        return response;
    }

    /**
     * 批量组合推送业务信息到税务，商品房+存量房 德宽
     * @param gzlslid 工作流实例id
     * @param tsDataMap
     * @return PlQuerySwxxResponseDTO
     */
    private Object plzhRequestExchangeForTssw(String gzlslid, Map<String, List<TsswDataDTO>> tsDataMap) {

        PlQuerySwxxResponseDTO plResponseDTO = new PlQuerySwxxResponseDTO();
        List<QuerySwxxResponseDTO> allResponseList = new ArrayList<>();

        // 商品房推送,1商品房，2存量房
        List<TsswDataDTO> spfDataList = tsDataMap.get("1");
        List<TsswDataDTO> clfDataList = tsDataMap.get("2");
        List<QuerySwxxResponseDTO> spfResponseList = plzhTssw(spfDataList,"spf");
        List<QuerySwxxResponseDTO> clfResponseList = plzhTssw(clfDataList,"clf");
        allResponseList.addAll(spfResponseList);
        allResponseList.addAll(clfResponseList);

        if (CollectionUtils.isNotEmpty(allResponseList)){
            plResponseDTO.setSwxxResponseDTOS(allResponseList);
            LOGGER.info("税务批量推送商品房和存量房业务信息到税务(德宽)接口调用成功！工作流实例id：{},响应内容：{}", gzlslid, plResponseDTO);
            List<String> fwuuids = new ArrayList<>();
            for (QuerySwxxResponseDTO querySwxxResponseDTO : allResponseList) {
                if ("200".equals(querySwxxResponseDTO.getResponseCode()) && StringUtils.isNotBlank(querySwxxResponseDTO.getFwuuid())){
                    // 保存fwwuid到bdc_sl_xm
                    String fwuuid = querySwxxResponseDTO.getFwuuid();
                    if (StringUtils.isNotBlank(querySwxxResponseDTO.getXmid())){
                        BdcSlXmDO bdcSlXmDO = bdcSlXmService.queryBdcSlXmByXmid(querySwxxResponseDTO.getXmid());
                        bdcSlXmDO.setFwuuid(fwuuid);
                        bdcSlXmService.updateBdcSlXm(bdcSlXmDO);
                    }
                    fwuuids.add(fwuuid);
                }
            }
            // fwuuid逗号拼接保存到bdcSlJbxxDO
            if (CollectionUtils.isNotEmpty(fwuuids)){
                BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
                bdcSlJbxxDO.setFwuuid(String.join(",", fwuuids));
                this.bdcSlJbxxService.updateBdcSlJbxx(bdcSlJbxxDO);
            }
        }
        return plResponseDTO;
    }

    /**
     * 批量组合推送，商品房+存量房
     * @param tsswDataDTOList
     * @param flag 存量房，商品房
     * @return
     */
    private List<QuerySwxxResponseDTO> plzhTssw(List<TsswDataDTO> tsswDataDTOList,String flag){
        String beanName = "";
        List<QuerySwxxResponseDTO> responseList = new ArrayList<>();
        // 商品房或存量房
        if ("spf".equals(flag)){
            beanName = "tsSwxxSpf_dk_pl";
        }else{
            beanName = "tsSwxxClf_dk_pl";
        }
        LOGGER.info("推送其中批量{}业务信息到税务(德宽),beanName:{},接口请求参数：{}", flag,beanName, JSON.toJSONString(tsswDataDTOList));
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, tsswDataDTOList);
        PlQuerySwxxResponseDTO spfPlResponse = JSON.parseObject(JSON.toJSONString(response), PlQuerySwxxResponseDTO.class);
        responseList.addAll(spfPlResponse.getSwxxResponseDTOS());
        return responseList;
    }

    /**
     * 单个调用exchange推送税务
     * @param gzlslid
     * @param beanName
     * @return
     */
    private Object requestExchangeForTssw(String gzlslid, String beanName) {
        // 参数可使用MAP类型传递，也可以定义请求实体传递
        TsswDataDTO tsswDataDTO = getDjTsswData(gzlslid, beanName,"");
        // response 为 jsonobject 类型 可强转为响应实体方便业务处理
        LOGGER.info("单个推送业务信息到税务,工作流实例id:{},beanName:{},接口请求参数：{}", gzlslid, beanName, JSON.toJSONString(tsswDataDTO));
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, tsswDataDTO);

        // 保存税务推送返回的 fwuuid
        if (Objects.nonNull(response)) {
            QuerySwxxResponseDTO querySwxxResponseDTO = JSON.parseObject(JSON.toJSONString(response), QuerySwxxResponseDTO.class);
            LOGGER.info("单个推送业务信息到税务成功,工作流实例id:{},beanName:{}，响应内容：{}", gzlslid, beanName, JSON.toJSONString(querySwxxResponseDTO));
            if(Objects.nonNull(querySwxxResponseDTO) && StringUtils.isNotBlank(querySwxxResponseDTO.getFwuuid())){
                String fwuuid = querySwxxResponseDTO.getFwuuid();
                // 保存到bdc_sl_xm
                if (StringUtils.isNotBlank(querySwxxResponseDTO.getXmid())){
                    BdcSlXmDO bdcSlXmDO = bdcSlXmService.queryBdcSlXmByXmid(querySwxxResponseDTO.getXmid());
                    bdcSlXmDO.setFwuuid(fwuuid);
                    bdcSlXmService.updateBdcSlXm(bdcSlXmDO);
                }
                // 保存到bdc_sl_jbxx
                BdcSlJbxxDO bdcSlJbxxDO = tsswDataDTO.getBdcSlJbxx();
                bdcSlJbxxDO.setFwuuid(fwuuid);
                this.bdcSlJbxxService.updateBdcSlJbxx(bdcSlJbxxDO);
            }
        }
        return response;
    }

    @Override
    public QuerySwxxResponseDTO getSwxx(BdcSwxxQO bdcSwxxQO){
        if (StringUtils.isBlank(bdcSwxxQO.getXmid()) || StringUtils.isBlank(bdcSwxxQO.getSlbh())) {
            throw new MissingArgumentException(messageProvider.getMessage("受理编号:" + bdcSwxxQO.getSlbh() + ",税务查询缺失有效参数"));
        }
        String beanName;
        if(StringUtils.isNotBlank(bdcSwxxQO.getBeanName())){
            beanName = bdcSwxxQO.getBeanName();
        }else if(swjkVersion.equals(CommonConstantUtils.SYSTEM_SW_DK)){
            beanName = "swCxxx_dk";
        }else{
            beanName = "swCxxx";
        }
        bdcSwxxQO.setBeanName(beanName);
        LOGGER.info("查询税务接口版本为{},beanName为{}",swjkVersion, beanName);
        return requestQueryExchange(bdcSwxxQO);

    }

    /**
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description  税务信息批量查询
     */
    @Override
    public Object getPlSwxx(BdcSwxxQO bdcSwxxQO) {
        if (StringUtils.isBlank(bdcSwxxQO.getSlbh())) {
            throw new MissingArgumentException(messageProvider.getMessage("受理编号:" + bdcSwxxQO.getSlbh() + ",税务查询缺失有效参数"));
        }
        String beanName;
        if(StringUtils.isNotBlank(bdcSwxxQO.getBeanName())){
            beanName = bdcSwxxQO.getBeanName();
        }else if(swjkVersion.equals(CommonConstantUtils.SYSTEM_SW_DK)){
            beanName = "swCxxx_dk";
        }else{
            beanName = "swCxxx";
        }
        bdcSwxxQO.setBeanName(beanName);
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(bdcSwxxQO.getSlbh());
        bdcXmQO.setSplys(sssplyList);
        // 查询涉税的项目信息
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new AppException("受理编号: " + bdcSwxxQO.getSlbh() + ",涉税项目不存在！");
        }
        LOGGER.info("批量查询税务接口版本为{},beanName为{},涉税项目数量:{}", swjkVersion, beanName, bdcXmDOList.size());
        if (bdcXmDOList.size() == 1) {
            bdcSwxxQO.setXmid(bdcXmDOList.get(0).getXmid());
            return requestQueryExchange(bdcSwxxQO);
        }
        return plRequestQueryExchange(bdcSwxxQO);
    }

    /**
     * 通过工作流实例ID获取税务信息，并推送税务信息
     */
    @Override
    public Object tsjsxx(String gzlslid, String beanName, String xmid) {
        if(StringUtils.isAnyBlank(gzlslid,beanName)){
            throw new MissingArgumentException(messageProvider.getMessage("推送计税信息时，缺失必要参数：工作实例ID或接口标识符。"));
        }
        List<TsswDataDTO> tsswDataDTOList = getFdjTsswData(gzlslid, null, null);
        if(CollectionUtils.isEmpty(tsswDataDTOList)){
            throw new MissingArgumentException("未获取到可以推送的计税信息");
        }
        TsswDataDTO tsswDataDTO = tsswDataDTOList.get(0);
        // 项目id不为空时，获取对应项目id的税务信息
        if(StringUtils.isNotBlank(xmid)){
            for(TsswDataDTO tsswData: tsswDataDTOList){
                if(tsswData.getXmid().equals(xmid)){
                    tsswDataDTO = tsswData;
                }
            }
        }
        //获取权利信息
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(tsswDataDTO.getXmid());
        if(bdcQl instanceof BdcFdcqDO){
            BdcFdcqDO bdcFdcqDO =(BdcFdcqDO) bdcQl;
            tsswDataDTO.setBdcFdcqDO(bdcFdcqDO);

            // 处理面积
            this.resolveMj(tsswDataDTO, bdcFdcqDO);
        }

        // 获取附件材料
        if(CommonConstantUtils.SYSTEM_VERSION_NT.equals(dataversion)) {
            this.getNtTsswSjcl(tsswDataDTO, gzlslid);
        } else if(CommonConstantUtils.SYSTEM_VERSION_CZ.equals(dataversion)){
            this.getCzTsswSjcl(tsswDataDTO, gzlslid);
        }else{
            this.getDjTsswSjcl(tsswDataDTO, gzlslid);
        }

        LOGGER.info("推送计税信息数据：{}",JSONObject.toJSONString(tsswDataDTO));


        //特殊处理推送业务信息
        tsclTsYwxx(tsswDataDTO);

        if(LOGGER.isInfoEnabled()){
            LOGGER.info("推送处理后的税务信息{}，{}", beanName, JSON.toJSONString(tsswDataDTO));
        }
        Object response =  exchangeInterfaceFeignService.requestInterface(beanName, tsswDataDTO);
        if(LOGGER.isInfoEnabled()){
            LOGGER.info("推送税务信息返回值{}", response);
        }
        // 推送税务成功后，更新推送税务状态
        if (Objects.nonNull(response)) {
            Map<String, String> map = (Map<String, String>) response;
            if(map.containsKey("success") && map.get("success").equals("1")){
                modifyTsswzt(gzlslid, xmid);
            }
        }
        return response;
    }

    /**
     * 通过工作流实例ID获取税务信息，并推送税务信息
     * @param gzlslid  工作流实例ID
     * @param xmid 项目ID
     * @return 推送数据结果
     */
    @Override
    public Object ntYcslTsjsxx(String gzlslid, String xmid, String fclx) {
        if(StringUtils.isNotBlank(gzlslid)) {
            List<TsswDataDTO> tsswDataDTOList = getFdjTsswData(gzlslid, null, null);
            TsswDataDTO tsswDataDTO = tsswDataDTOList.get(0);
            // 项目id不为空时，获取对应项目id的税务信息
            if (StringUtils.isNotBlank(xmid)) {
                for (TsswDataDTO tsswData : tsswDataDTOList) {
                    if (tsswData.getXmid().equals(xmid)) {
                        tsswDataDTO = tsswData;
                    }
                }
            }
            //推送系统id 按照不同的区县代码配置
            //根据qxdm获取配置值
            if (StringUtils.isNotBlank(ycslXtid)) {
                Map<String, String> xtidMap = JSON.parseObject(ycslXtid, Map.class);
                String xtid = MapUtils.getString(xtidMap, tsswDataDTO.getBdcSlJbxx().getQxdm(), "");
                tsswDataDTO.setTsxtid(xtid);
            }
            //业务id qxdm_gzlslid
            tsswDataDTO.setYthywid(tsswDataDTO.getBdcSlJbxx().getQxdm() + "_" + gzlslid);
            // 处理婚姻信息和房屋套次
            this.getHyxx(tsswDataDTO);

            //4.附件信息
            tsswDataDTO.setFjclList(getTsswFjxx(gzlslid));
            LOGGER.info("当前流程实例id{}房产类型{}推送税务入参{}", gzlslid, fclx, JSON.toJSONString(tsswDataDTO));
            String beanName = "";
            if (StringUtils.equals("clf", fclx)) {
                beanName = "btsw_clfjyxx";
            } else {
                beanName = "btsw_zlfjyxx";
            }
            return exchangeInterfaceFeignService.requestInterface(beanName, tsswDataDTO);
        }
        return null;
    }

    /**
     * 推送税务后，更新核税信息表中的 ytsswzt
     */
    private void modifyTsswzt(String gzlslid, String xmid){
        if(StringUtils.isNotBlank(xmid)){
            BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
            bdcSlHsxxDO.setXmid(xmid);
            bdcSlHsxxDO.setYtsswzt(CommonConstantUtils.SF_S_DM);
            this.bdcSlHsxxService.updateBdcSlHsxxByXmidAndNsrsbh(bdcSlHsxxDO);
        }else{
            if(StringUtils.isNotBlank(gzlslid)){
                BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
                bdcSlHsxxDO.setYtsswzt(CommonConstantUtils.SF_S_DM);
                this.bdcSlHsxxService.batchUpdateBdcSlHsxx(bdcSlHsxxDO, gzlslid);
            }
        }
    }

    private String getLcGzldyid(String gzlslid){
        String gzldyid = "";
        // 获取受理项目的工作流定义ID
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        if(Objects.nonNull(bdcSlJbxxDO)){
            gzldyid = bdcSlJbxxDO.getGzldyid();
        }else{
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                gzldyid = bdcXmDOList.get(0).getGzldyid();
            }
        }
        return gzldyid;
    }

    /**
     * 判断当前流程是否一窗受理流程
     */
    private boolean isYcslLc(String gzldyid){
        if(StringUtils.isNotBlank(gzldyid)){
            return this.bdcSlYcslDjywDzbService.checkIsYcslLc(gzldyid);
        }
        return false;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param tsswDataDTO 推送税务实体
     * @param bdcFdcqDO 房地产权
     * @description 南通推送税务处理车库、阁楼等面积
     */
    private void resolveMj(TsswDataDTO tsswDataDTO, BdcFdcqDO bdcFdcqDO) {
        if(null == bdcFdcqDO || !CommonConstantUtils.SYSTEM_VERSION_NT.equals(dataversion) || StringUtils.isBlank(tsswFdcqGhyt)) {
            return;
        }

        String[] ghytArray = tsswFdcqGhyt.split(";");
        if(null == ghytArray || ghytArray.length != 3) {
            return;
        }

        // 车库;储藏室;阁楼;  剩下的未配置的为权属转移面积
        String[] ckGhyt = ghytArray[0].split(",");
        String[] ccsGhyt = ghytArray[1].split(",");
        String[] glGhyt = ghytArray[2].split(",");

        String ghyt = String.valueOf(bdcFdcqDO.getGhyt());

        if(ArrayUtils.contains(ckGhyt, ghyt)) {
            tsswDataDTO.setCkmj(bdcFdcqDO.getJzmj());
        }
        else if(ArrayUtils.contains(ccsGhyt, ghyt)) {
            tsswDataDTO.setCcsmj(bdcFdcqDO.getJzmj());
        }
        else if(ArrayUtils.contains(glGhyt, ghyt)) {
            tsswDataDTO.setGlmj(bdcFdcqDO.getJzmj());
        }
        else {
            tsswDataDTO.setQszymj(bdcFdcqDO.getJzmj());
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param tsswDataDTO 推送税务实体
     * @description 南通推送税务 买卖双方数组内（csfnsrxxGridlb、zrfnsrxxGridlb）需新增婚姻状况、配偶姓名和配偶身份证
     */
    private void getHyxx(TsswDataDTO tsswDataDTO) {
        if (CollectionUtils.isNotEmpty(tsswDataDTO.getSqrList())) {
            List<BdcSlSqrSwDTO> sqrSwList = new ArrayList<>();
            // 默认电话与地址
            String defaultDh = "",
                    defaultTxdz = "";
            //处理权利比例

            List<BdcSlSqrDO> bdcSlSqrDOList =new ArrayList<>();
            List<BdcSlSqrDO> qlrList = tsswDataDTO.getSqrList().stream().filter(qlr -> StringUtils.equals(CommonConstantUtils.QLRLB_QLR,qlr.getSqrlb())).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(qlrList)) {
                dealQlbl(qlrList);
                bdcSlSqrDOList.addAll(qlrList);
            }
            List<BdcSlSqrDO> ywrList = tsswDataDTO.getSqrList().stream().filter(qlr -> StringUtils.equals(CommonConstantUtils.QLRLB_YWR,qlr.getSqrlb())).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(ywrList)) {
                dealQlbl(ywrList);
                bdcSlSqrDOList.addAll(ywrList);
            }
            tsswDataDTO.setSqrList(bdcSlSqrDOList);


            for(BdcSlSqrDO bdcSlSqrDO : tsswDataDTO.getSqrList()) {
                // 这里新建实体复制原有的转让方出让方信息，没有直接更改TsswDataDTO中的sqrList实体，
                // 一个是为了避免改动造成其它地方影响，另外一个为了方便exchange dozer对照
                BdcSlSqrSwDTO sqrSwDTO = new BdcSlSqrSwDTO();
                BeansResolveUtils.clonePropsValueWithParentProps(bdcSlSqrDO, sqrSwDTO);

                // 婚姻状况代码处理
                if(CommonConstantUtils.HYZK_YH_MC.equals(bdcSlSqrDO.getHyzk())) {
                    sqrSwDTO.setHyzkdm(1);
                } else {
                    // 默认未婚
                    sqrSwDTO.setHyzk("未婚");
                    sqrSwDTO.setHyzkdm(3);
                }
                //djxl
                if(tsswDataDTO.getBdcSlXm() != null) {
                    sqrSwDTO.setDjxl(tsswDataDTO.getBdcSlXm().getDjxl());
                }

                // 获取当前人的配偶信息
                if(null != sqrSwDTO && StringUtils.isNotBlank(sqrSwDTO.getSqrid())) {
                    BdcSlJtcyQO bdcSlJtcyQO =new BdcSlJtcyQO();
                    bdcSlJtcyQO.setYsqrgx("配偶");
                    bdcSlJtcyQO.setSqrid(sqrSwDTO.getSqrid());
                    List<BdcSlJtcyDO> poJtcyList = bdcSlJtcyService.listBdcSlJtcy(bdcSlJtcyQO);

                    if(CollectionUtils.isNotEmpty(poJtcyList) && null != poJtcyList.get(0)){
                        sqrSwDTO.setPoxm(poJtcyList.get(0).getJtcymc());
                        sqrSwDTO.setPosfzh(poJtcyList.get(0).getZjh());
                    } else {
                        sqrSwDTO.setPoxm(" ");
                        sqrSwDTO.setPosfzh(" ");
                    }
                    if(CommonConstantUtils.SYSTEM_VERSION_CZ.equals(dataversion)){
                        //权利比例*100 取整数
                        if(StringUtils.isNotBlank(bdcSlSqrDO.getQlbl())) {
                            Double qlbl =NumberUtils.toDouble(String.valueOf(NumberUtils.toDouble(CommonUtil.getQlbrStr(bdcSlSqrDO.getQlbl())))) * 100;
                            if(NumberUtils.isNumber(qlbl.toString()) && qlbl.toString().contains(".")){
                                sqrSwDTO.setQlbl(qlbl.toString().split("\\.")[0]);
                            }
                        }

                        // 向申请人信息中追加家庭成员信息
                        bdcSlJtcyQO.setYsqrgx(null);
                        List<BdcSlJtcyDO> jtcyxxList = bdcSlJtcyService.listBdcSlJtcy(bdcSlJtcyQO);
                        if(CollectionUtils.isEmpty(jtcyxxList)){
                            jtcyxxList =new ArrayList<>();
                            //未婚时需传递本人作为家庭成员
                            if(!CommonConstantUtils.HYZK_YH_MC.equals(bdcSlSqrDO.getHyzk())) {
                                BdcSlJtcyDO bdcSlJtcyDO =new BdcSlJtcyDO();
                                bdcSlJtcyDO.setJtcymc(bdcSlSqrDO.getSqrmc());
                                bdcSlJtcyDO.setZjh(bdcSlSqrDO.getZjh());
                                bdcSlJtcyDO.setZjzl(bdcSlSqrDO.getZjzl());
                                bdcSlJtcyDO.setYsqrgx("本人");
                                jtcyxxList.add(bdcSlJtcyDO);

                            }

                        }
                        sqrSwDTO.setBdcSlJtcyDOList(jtcyxxList);
                        if (Objects.nonNull(tsswDataDTO.getBdcSlJyxx())) {
                            sqrSwDTO.setQcqsjsje(tsswDataDTO.getBdcSlJyxx().getQcqsjsje());
                        }
                        if (Objects.nonNull(tsswDataDTO.getBdcFdcqDO())) {
                            sqrSwDTO.setJyjg(tsswDataDTO.getBdcFdcqDO().getJyjg());
                        }
                    }
                    if (Objects.nonNull(tsswDataDTO.getBdcSlJyxx())) {
                        sqrSwDTO.setFczfzsj(tsswDataDTO.getBdcSlJyxx().getFczfzsj());
                    }
                    if (StringUtils.isNotBlank(bdcSlSqrDO.getQlbl())) {
                        Double qlbl = NumberUtils.toDouble(String.valueOf(NumberUtils.toDouble(CommonUtil.getQlbrStr(bdcSlSqrDO.getQlbl())))) * 100;
                        if (NumberUtils.isNumber(qlbl.toString()) && qlbl.toString().contains(".")) {
                            sqrSwDTO.setQlblbfs(qlbl.toString().split("\\.")[0]);
                        }
                    }
                }

                // 房屋套次空设置默认值避免dozer对照时候空无法进入转换器设置默认值
                if(StringUtils.isBlank(sqrSwDTO.getFwtc())) {
                    sqrSwDTO.setFwtc("-1");
                }

                // 设置电话与通讯地址默认值
                if(StringUtils.isNotBlank(sqrSwDTO.getDh())){
                    defaultDh = sqrSwDTO.getDh();
                }
                if(StringUtils.isNotBlank(sqrSwDTO.getTxdz())){
                    defaultTxdz = sqrSwDTO.getTxdz();
                }
                sqrSwList.add(sqrSwDTO);
            }
            List<BdcSlSqrSwDTO> qlrswList = sqrSwList.stream().filter(qlr -> StringUtils.equals(CommonConstantUtils.QLRLB_QLR,qlr.getSqrlb())).collect(Collectors.toList());
            List<BdcSlSqrSwDTO> ywrswlist = sqrSwList.stream().filter(qlr -> StringUtils.equals(CommonConstantUtils.QLRLB_YWR,qlr.getSqrlb())).collect(Collectors.toList());
            dealZcqrbz(qlrswList);
            dealZcqrbz(ywrswlist);

            // 没有通讯地址时，设置通讯地址为坐落
            if(StringUtils.isBlank(defaultTxdz) && null!= tsswDataDTO.getBdcSlXm()){
                defaultTxdz = tsswDataDTO.getBdcSlXm().getZl();
            }
            this.handlerSqrxx(defaultDh, defaultTxdz, sqrSwList);
            tsswDataDTO.setSqrSwList(sqrSwList);
            tsswDataDTO.setSqrList(null);
        }
    }

    /**
      * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
      * @description  常州推送税务信息,增加项目附表信息
      */
    private void getXmAndXmfbxx(TsswDataDTO tsswDataDTO){
        if(StringUtils.isNotBlank(tsswDataDTO.getXmid())){
            BdcXmDO bdcXmDO = this.bdcXmFeignService.queryBdcXmByXmid(tsswDataDTO.getXmid());
            if(Objects.nonNull(bdcXmDO)){
                tsswDataDTO.setBdcxm(bdcXmDO);
            }
            BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
            bdcXmFbQO.setXmid(tsswDataDTO.getXmid());
            List<BdcXmFbDO> bdcXmFbDOList = this.bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
            if(CollectionUtils.isNotEmpty(bdcXmFbDOList)){
                tsswDataDTO.setBdcxmfb(bdcXmFbDOList.get(0));
            }
        }
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  如东推送核税,增加未成年子女信息
     */
    private void getWcnxx(TsswDataDTO tsswDataDTO){
        if(CollectionUtils.isNotEmpty(tsswDataDTO.getSqrSwList())){
            for(BdcSlSqrSwDTO bdcSlSqrSwDTO:tsswDataDTO.getSqrSwList()){
                BdcSlJtcyQO bdcSlJtcyQO =new BdcSlJtcyQO();
                bdcSlJtcyQO.setYsqrgx(CommonConstantUtils.YSQRGX_WCNZN_MC);
                bdcSlJtcyQO.setSqrid(bdcSlSqrSwDTO.getSqrid());
                List<BdcSlJtcyDO> znJtcyList = bdcSlJtcyService.listBdcSlJtcy(bdcSlJtcyQO);
                if(CollectionUtils.isNotEmpty(znJtcyList)){
                    bdcSlSqrSwDTO.setBdcSlJtcyDOList(znJtcyList);
                }
            }
        }
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  如东推送核税,获取上手交易信息
     */
    private void getYjyxx(TsswDataDTO tsswDataDTO){
        if(StringUtils.isNotBlank(tsswDataDTO.getXmid())){
            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList =bdcSlXmLsgxService.listBdcSlXmLsgx(tsswDataDTO.getXmid(),"",CommonConstantUtils.SF_F_DM);
            if(CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList) &&StringUtils.isNotBlank(bdcSlXmLsgxDOList.get(0).getYxmid())){
                List<BdcSlJyxxDO> yJyxxList =bdcSlJyxxService.listBdcSlJyxxByXmid(bdcSlXmLsgxDOList.get(0).getYxmid());
                if(CollectionUtils.isNotEmpty(yJyxxList)){
                    BdcSlJyxxDO yjyxx =yJyxxList.get(0);
                    if(CollectionUtils.isNotEmpty(tsswDataDTO.getSqrSwList())){
                        for(BdcSlSqrSwDTO bdcSlSqrSwDTO:tsswDataDTO.getSqrSwList()){
                            if(yjyxx.getJyje() !=null) {
                                bdcSlSqrSwDTO.setQcqsjsje(yjyxx.getJyje());
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * 处理申请人信息联系电话和地址
     * 1、多个权利人情况，其中一个人有通讯地址，其他人设置为这个人的通讯地址和电话
     * 2、权利人都没有通讯地址的情况，通讯地址设置为合同坐落
     */
    private void handlerSqrxx(String dh, String txdz, List<BdcSlSqrSwDTO> sqrSwList){
        if(CommonConstantUtils.SYSTEM_VERSION_CZ.equals(dataversion)){
            for(BdcSlSqrSwDTO sqrxx:sqrSwList){
                if(StringUtils.isBlank(sqrxx.getDh())){
                    sqrxx.setDh(dh);
                }
                if(StringUtils.isBlank(sqrxx.getTxdz())){
                    sqrxx.setTxdz(txdz);
                }
            }
        }
    }

    /**
     * 将家庭成员信息转换为税务接口可识别的信息类
     */
    private List<BdcSlSqrJtcySwDTO> convertToSwJtcyxx(List<BdcSlJtcyDO> jtcyxxList){
        List<BdcSlSqrJtcySwDTO> bdcSlSqrJtcySwDTOList = new ArrayList<>(10);
        if(CollectionUtils.isNotEmpty(jtcyxxList)){
            for(BdcSlJtcyDO bdcSlJtcyDO:jtcyxxList){
                BdcSlSqrJtcySwDTO bdcSlSqrJtcySwDTO = new BdcSlSqrJtcySwDTO(
                        bdcSlJtcyDO.getZjh(), String.valueOf(bdcSlJtcyDO.getZjzl()), bdcSlJtcyDO.getJtcymc(), bdcSlJtcyDO.getYsqrgx());
                bdcSlSqrJtcySwDTOList.add(bdcSlSqrJtcySwDTO);
            }
        }
        return bdcSlSqrJtcySwDTOList;
    }

    /**
     * @param xmid 项目ID
     * @param slbh 受理编号
     * @param gxlx 更新类型 1：先删除原有税务信息，重新插入税务信息 2.根据纳税人识别号更新核税信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 税务系信息查询合肥德宽版
     * @date : 2020/8/11 16:00
     */
    @Override
    @Transactional
    public QuerySwxxResponseDTO getSwxxDk(String xmid, String slbh, String gxlx) {
        if (StringUtils.isBlank(xmid) || StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException(messageProvider.getMessage("受理编号:" + slbh + ",税务查询缺失有效参数"));
        }
        String beanName = "swCxxx_dk";
        BdcSwxxQO bdcSwxxQO =new BdcSwxxQO();
        bdcSwxxQO.setXmid(xmid);
        bdcSwxxQO.setSlbh(slbh);
        bdcSwxxQO.setGxlx(gxlx);
        bdcSwxxQO.setBeanName(beanName);
        return requestQueryExchange(bdcSwxxQO);
    }

    @Override
    public Object qxzfSwxx(String xmid, String slbh) {
        String beanName = "swQxzf";
        return requestQxzfSwxxByExchange(xmid,slbh,beanName);
    }

    /**
     * @param xmid 项目ID
     * @param slbh 受理编号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 取消作废税务信息  合肥德宽版系统
     * @date : 2020/8/11 14:48
     */
    @Override
    public Object qxzfSwxxDK(String xmid, String slbh) {
        String beanName = "swQxzf_dk";
        return requestQxzfSwxxByExchange(xmid,slbh,beanName);
    }

    /**
     * @param slbh 受理编号
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 批量取消作废税务信息 -合肥德宽版税务系统
     * @date : 2022/9/26
     */
    @Override
    public Object plQxzfSwxxDK(String slbh) {
        String beanName = "swQxzf_dk";
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(slbh);
        bdcXmQO.setSplys(sssplyList);
        // 查询涉税的项目信息
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new AppException("受理编号: " + slbh + ",涉税项目不存在！");
        }
        if (bdcXmDOList.size() == 1) {
            return requestQxzfSwxxByExchange(bdcXmDOList.get(0).getXmid(), slbh, beanName);
        }
        return plRequestQxzfSwxxByExchange(slbh,beanName);
    }

    @Override
    public Object qxzfLcSwxx(String gzlslid) {
        LOGGER.info("调用流程取消作废接口开始,工作流实例ID：{},税务接口版本{}", gzlslid,swjkVersion);
        if (StringUtils.isNotBlank(gzlslid)) {
            BdcXmQO bdcXmQO =new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList) && CheckWwsqOrYcslUtils.checkIsYcsl(bdcXmDOList.get(0).getSply())) {
                if(StringUtils.equals(swjkVersion,CommonConstantUtils.SYSTEM_SW_DK)) {
                    return qxzfSwxxDK(bdcXmDOList.get(0).getXmid(), bdcXmDOList.get(0).getSlbh());
                }
                return qxzfSwxx(bdcXmDOList.get(0).getXmid(), bdcXmDOList.get(0).getSlbh());
            }
        }
        return null;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 推送税务数据
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 组织批量推送税务数据
     */
    private List<TsswDataDTO> getAllDjTsswData(String gzlslid, String beanName) {
        List<TsswDataDTO> tsswDataDTOList = new ArrayList<>();
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (bdcSlJbxxDO == null) {
            throw new AppException("工作流实例ID：" + gzlslid + "未找到相应的受理基本信息");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        bdcXmQO.setSplys(sssplyList);
        // 查询涉税的项目信息
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            for (int i = 0; i < bdcXmDOList.size(); i++) {
                TsswDataDTO tsswDataDTO = getDjTsswData(gzlslid, beanName, bdcXmDOList.get(i).getXmid());
                if (tsswDataDTO != null && StringUtils.isNotBlank(tsswDataDTO.getXmid())) {
                    SwMergeDTO swMergeDTO = new SwMergeDTO();
                    swMergeDTO.setMergezs(String.valueOf(bdcXmDOList.size()));
                    // 并案id，格式（A-B，同一并案业务 A 相同，B递增。如 1-1、1-2、1-3）
                    swMergeDTO.setMergeid("hf" + bdcSlJbxxDO.getSlbh() + "-" + (i + 1));
                    tsswDataDTO.setSwMergeDTO(swMergeDTO);
                    tsswDataDTOList.add(tsswDataDTO);
                }
            }
        }

        return tsswDataDTOList;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 推送税务数据
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 组织推送税务数据
     */
    private TsswDataDTO getDjTsswData(String gzlslid, String beanName,String xmid) {
        TsswDataDTO tsswDataDTO = new TsswDataDTO();
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (bdcSlJbxxDO == null) {
            throw new AppException("工作流实例ID：" + gzlslid + "未找到相应的受理基本信息");
        }
        List<BdcSlXmDO> bdcSlXmDOList = new ArrayList<>();
        // 批量
        if (StringUtils.isNotBlank(xmid)) {
            BdcSlXmDO bdcSlXmDO = bdcSlXmService.queryBdcSlXmByXmid(xmid);
            if (bdcSlXmDO != null) {
                bdcSlXmDOList.add(bdcSlXmDO);
                // bs 1（商品房）2（存量房）,登记原因为空，bs默认为0
                if (StringUtils.isNotBlank(bdcSlXmDO.getDjyy())){
                    tsswDataDTO.setBs(bdcSlXmDO.getDjyy().contains("商品房")? "1":"2");
                }else{
                    tsswDataDTO.setBs("0");
                }
            }
        } else {
            bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid(), "");
        }
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {

                //判断当前项目存在交易信息,存在就以当前项目数据组织数据，暂不考虑批量流程
                List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxService.listBdcSlJyxxByXmid(bdcSlXmDO.getXmid());
                bdcSlJyxxDOList = bdcSlJyxxDOList.stream().filter(bdcSlJyxxDO -> StringUtils.isNotBlank(bdcSlJyxxDO.getJyfs())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
                    BdcSlFwxxDO bdcSlFwxxDO =new BdcSlFwxxDO();
                    List<BdcSlFwxxDO> bdcSlFwxxDOList = bdcSlFwxxService.listBdcSlFwxxByXmid(bdcSlXmDO.getXmid());
                    if(CollectionUtils.isNotEmpty(bdcSlFwxxDOList)){
                        bdcSlFwxxDO =bdcSlFwxxDOList.get(0);
                    }

                    List<BdcSlSqrDO> bdcSlSqrDOList = new ArrayList<>();
                    List<BdcSlSqrDO> bdcSlZrfDOList = bdcSlSqrService.listBdcSlSqrByXmid(bdcSlXmDO.getXmid(), CommonConstantUtils.QLRLB_QLR);
                    if (CollectionUtils.isNotEmpty(bdcSlZrfDOList)) {
                        // 德宽版本不需要处理共有比例，后期等德宽接口稳定后，需要修改
                        if(beanName.indexOf("dk") == -1){
                            //处理共有比例
                            dealQlbl(bdcSlZrfDOList);
                        }
                        //处理共有人标记
                        dealGyrbj(bdcSlZrfDOList);

                        bdcSlSqrDOList.addAll(bdcSlZrfDOList);
                    }
                    List<BdcSlSqrDO> bdcSlZcfDOList = bdcSlSqrService.listBdcSlSqrByXmid(bdcSlXmDO.getXmid(), CommonConstantUtils.QLRLB_YWR);
                    if (CollectionUtils.isNotEmpty(bdcSlZcfDOList)) {
                        // 德宽版本不需要处理共有比例，后期等德宽接口稳定后，需要修改
                        if(beanName.indexOf("dk") == -1) {
                            //处理共有比例
                            dealQlbl(bdcSlZcfDOList);

                        }
                        //处理共有人标记
                        dealGyrbj(bdcSlZcfDOList);
                        bdcSlSqrDOList.addAll(bdcSlZcfDOList);
                    }

                    tsswDataDTO.setXmid(bdcSlXmDO.getXmid());
                    tsswDataDTO.setSlbh(bdcSlJbxxDO.getSlbh());
                    tsswDataDTO.setFwuuid(bdcSlJbxxDO.getFwuuid());

                    BdcXmQO qo = new BdcXmQO();
                    qo.setXmid(bdcSlXmDO.getXmid());
                    List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(qo);
                    if (CollectionUtils.isNotEmpty(bdcXmDOS) && null != bdcXmDOS.get(0)) {
                        // 30330 推送数据时直接获取项目的数据避免，部分数据错误
                        dozerMapperF.map(bdcXmDOS.get(0), bdcSlJbxxDO);
                        dozerMapperF.map(bdcXmDOS.get(0), bdcSlXmDO);
                    }
                    //存入税务推送时间
                    bdcSlXmDO.setTsswsj(new Date());
                    bdcSlXmService.updateBdcSlXm(bdcSlXmDO);
                    tsswDataDTO.setBdcSlJbxx(bdcSlJbxxDO);
                    tsswDataDTO.setBdcSlFwxx(bdcSlFwxxDO);
                    tsswDataDTO.setBdcSlXm(bdcSlXmDO);
                    // 25567 【合肥】推送税务信息字段处理20191127 wzj
                    BdcSlJyxxDO bdcSlJyxxDO = bdcSlJyxxDOList.get(0);
                    if (bdcSlJyxxDO.getJyje() != null) {
                        bdcSlJyxxDO.setJyje(bdcSlJyxxDO.getJyje() * 10000);
                        //处理单价,单价：合同金额/建筑面积
                        if (CollectionUtils.isNotEmpty(bdcSlFwxxDOList) && bdcSlFwxxDOList.get(0).getJzmj() != null) {
                            bdcSlJyxxDO.setDj(NumberUtil.formatDigit(bdcSlJyxxDO.getJyje() / bdcSlFwxxDOList.get(0).getJzmj(), 2));
                        }
                    }
                    //合同编号处理,非网签（合同编号为空且合同登记时间不为空）合同编号取受理编号
                    if(StringUtils.isBlank(bdcSlJyxxDO.getHtbh()) &&(bdcSlJyxxDO.getHtdjsj() != null ||bdcSlJyxxDO.getHtbasj() != null)){
                        bdcSlJyxxDO.setHtbh(bdcSlJbxxDO.getSlbh());
                        //同步非网签数据的合同编号信息、交易金额信息至受理库
                        BdcSlJyxxDO jyxx = new BdcSlJyxxDO();
                        jyxx.setHtbh(bdcSlJbxxDO.getSlbh());
                        jyxx.setJyje(this.getDjJyje(bdcSlXmDO.getXmid()));
                        this.bdcSlJyxxService.updateSlJyxxByXmid(bdcSlJyxxDO.getXmid(),JSONObject.toJSONString(jyxx),null);
                        // 同步非网签数据的合同编号信息至登记库项目表的jyhth
                        this.syncJyHtbhToXmJyhth(bdcSlXmDO.getXmid(), bdcSlJbxxDO.getSlbh());
                    }
                    tsswDataDTO.setBdcSlJyxx(bdcSlJyxxDOList.get(0));

                    tsswDataDTO.setSqrList(bdcSlSqrDOList);
                    //交易差额征收信息
                    List<BdcSlJyxxCezsDO> bdcSlJyxxCezsDOList = this.bdcSlJyxxCezsService.listBdcSlJyxxCezsByXmid(bdcSlXmDO.getXmid());
                    if(CollectionUtils.isNotEmpty(bdcSlJyxxCezsDOList)){
                        tsswDataDTO.setBdcSlJyxxCezsDO(bdcSlJyxxCezsDOList.get(0));
                    }
                    //获取权利信息
                    BdcQl bdcQl =bdcQllxFeignService.queryQlxx(bdcSlXmDO.getXmid());
                    LOGGER.info("获取权利信息，参数：{},实体内容：{}", gzlslid, bdcQl != null?bdcQl:"权利信息为空");
                    if(bdcQl instanceof BdcFdcqDO){
                        BdcFdcqDO bdcFdcqDO =(BdcFdcqDO) bdcQl;
                        tsswDataDTO.setBdcFdcqDO(bdcFdcqDO);
                        //建筑年份截取竣工时间前4位,并判断是否可转换为数字
                        if(StringUtils.isNotBlank(bdcFdcqDO.getJgsj()) &&bdcFdcqDO.getJgsj().length() >=4){
                            String nf =StringUtils.substring(bdcFdcqDO.getJgsj(),0,4);
                            if(NumberUtils.isNumber(nf) && !nf.contains(".")){
                                bdcSlFwxxDO.setJznf(Integer.parseInt(nf));
                            }
                        }
                    }
                    //土地出让金
                    List<BdcSlTdcrjDO> bdcSlTdcrjDOList =bdcSlTdcrjService.listBdcSlTdcrjByXmid(bdcSlXmDO.getXmid());
                    if(CollectionUtils.isNotEmpty(bdcSlTdcrjDOList)){
                        tsswDataDTO.setBdcSlTdcrjDOList(bdcSlTdcrjDOList);
                    }
                    break;
                }
            }
        }
        //特殊处理字段
        tsclTsDjYwxx(tsswDataDTO,gzlslid);
        LOGGER.info("税务推送接口参数：{},实体内容：{}", gzlslid, JSONObject.toJSONString(tsswDataDTO));
        return tsswDataDTO;
    }

    /**
     * @param
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取推送税务收件材料
     */
    private void getDjTsswSjcl(TsswDataDTO tsswDataDTO,String gzlslid){
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjclByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
            List<TsswDataFjclDTO> tsswDataFjclDTOList = new ArrayList<>();
            for (BdcSlSjclDO bdcSlSjclDO : bdcSlSjclDOList) {
                if (StringUtils.isNotBlank(bdcSlSjclDO.getClmc())) {
                    //查询文件夹下文件
                    List<StorageDto> list = storageClient.listStoragesByName("clientId", gzlslid, null, bdcSlSjclDO.getClmc(), 1, 0);
                    if (CollectionUtils.isNotEmpty(list)) {
                        List<StorageDto> listFile = storageClient.listAllSubsetStorages(list.get(0).getId(), StringUtils.EMPTY, 1, null,0,null);
                        if (CollectionUtils.isNotEmpty(listFile)) {
                            for (StorageDto storage : listFile) {
                                TsswDataFjclDTO tsswDataFjclDTO = new TsswDataFjclDTO();
                                tsswDataFjclDTO.setClmc(bdcSlSjclDO.getClmc());
                                tsswDataFjclDTO.setWjzxid(storage.getId());
                                tsswDataFjclDTOList.add(tsswDataFjclDTO);
                            }
                        }
                    }
                }
            }
            tsswDataDTO.setFjclList(tsswDataFjclDTOList);
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  （南通）获取推送税务收件材料
     */
    private void getNtTsswSjcl(TsswDataDTO tsswDataDTO, String gzlslid){
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjclByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
            return;
        }

        List<TsswDataFjclDTO> tsswDataFjclDTOList = new ArrayList<>();
        for (BdcSlSjclDO bdcSlSjclDO : bdcSlSjclDOList) {
            if (StringUtils.isBlank(bdcSlSjclDO.getClmc())) {
                continue;
            }

            TsswDataFjclDTO tsswDataFjclDTO = new TsswDataFjclDTO();
            tsswDataFjclDTO.setClmc(bdcSlSjclDO.getClmc());

            //查询文件夹下文件
            List<StorageDto> list = storageClient.listStoragesByName("clientId", gzlslid, null, bdcSlSjclDO.getClmc(), 1, 0);
            if (CollectionUtils.isEmpty(list)) {
                continue;
            }

            List<StorageDto> listFile = storageClient.listAllSubsetStorages(list.get(0).getId(), StringUtils.EMPTY, 1, null,0,null);
            if (CollectionUtils.isEmpty(listFile)) {
                continue;
            }

            List<TsswDataFjclXxDTO> fjclXxDTOList = new ArrayList<>();
            for (StorageDto storage : listFile) {
                TsswDataFjclXxDTO fjclXxDTO = new TsswDataFjclXxDTO();
                fjclXxDTO.setFjmc(storage.getName());

                // 直接推送到税务附件过大会导致传输慢，因此改为传附件URL地址，由税务异步调用下载附件，字段还是继续沿用base64字段
                String url = storage.getDownUrl();
                if(StringUtils.isNotBlank(url) && StringUtils.isNotBlank(swFjIpPort)) {
                    // 大云附件地址例如：http://192.168.2.87:8030/storage/rest/files/download/ff8080817399496301740064a45a0363
                    url = "http://" + swFjIpPort + url.substring(url.indexOf("/storage"));
                }
                fjclXxDTO.setBase64(url);

                fjclXxDTOList.add(fjclXxDTO);
            }

            tsswDataFjclDTO.setFjxx(fjclXxDTOList);
            tsswDataFjclDTOList.add(tsswDataFjclDTO);
        }

        tsswDataDTO.setFjclList(tsswDataFjclDTOList);
    }

    /**
     * 获取文件夹下文件
     */
    private List<TsswDataFjclXxDTO> getFjClxx(List<StorageDto> listFile){
        List<TsswDataFjclXxDTO> fjclXxDTOList = new ArrayList<>();
        for (StorageDto storage : listFile) {
            TsswDataFjclXxDTO fjclXxDTO = new TsswDataFjclXxDTO();
            fjclXxDTO.setFjmc(storage.getName());

            // 直接推送到税务附件过大会导致传输慢，因此改为传附件URL地址，由税务异步调用下载附件，字段还是继续沿用base64字段
            String url = storage.getDownUrl();
            if(StringUtils.isNotBlank(url) && StringUtils.isNotBlank(swFjIpPort)) {
                // 大云附件地址例如：http://192.168.2.87:8030/storage/rest/files/download/ff8080817399496301740064a45a0363
                url = "http://" + swFjIpPort + url.substring(url.indexOf("/storage"));
            }
            fjclXxDTO.setBase64(url);

            fjclXxDTOList.add(fjclXxDTO);
        }
        return fjclXxDTOList;
    }

    private void getCzTsswSjcl(TsswDataDTO tsswDataDTO, String gzlslid){
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjclByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
            return;
        }

        List<TsswDataFjclXxDTO> fjclXxDTOList = new ArrayList<>(10);
        for (BdcSlSjclDO bdcSlSjclDO : bdcSlSjclDOList) {
            if (StringUtils.isBlank(bdcSlSjclDO.getClmc())) {
                continue;
            }
            //只推送收取部门含有税务的收件材料
            if(StringUtils.isBlank(bdcSlSjclDO.getSqbm()) || (!StringUtils.contains(bdcSlSjclDO.getSqbm(),CommonConstantUtils.SJCL_SQBM_SW_MC))){
                continue;
            }

            TsswDataFjclDTO tsswDataFjclDTO = new TsswDataFjclDTO();
            tsswDataFjclDTO.setClmc(bdcSlSjclDO.getClmc());

            //查询文件夹下文件
            List<StorageDto> list = storageClient.listStoragesByName("clientId", gzlslid, null, bdcSlSjclDO.getClmc(), 1, 0);
            if (CollectionUtils.isEmpty(list)) {
                continue;
            }

            List<StorageDto> listFile = storageClient.listAllSubsetStorages(list.get(0).getId(), StringUtils.EMPTY, 1, null,0,null);
            if (CollectionUtils.isEmpty(listFile)) {
                continue;
            }

            List<TsswDataFjclXxDTO> fjclXxList= this.getFjClxx(listFile);

            fjclXxDTOList.addAll(fjclXxList);
        }
        // 将所有文件夹下的文件组织为一个List
        List<TsswDataFjclDTO> tsswDataFjclDTOList = new ArrayList<>();
        TsswDataFjclDTO tsswDataFjclDTO = new TsswDataFjclDTO();
        tsswDataFjclDTO.setFjxx(fjclXxDTOList);
        tsswDataFjclDTOList.add(tsswDataFjclDTO);
        tsswDataDTO.setFjclList(tsswDataFjclDTOList);
    }

    private Integer getFwSzc(BdcSlXmDO bdcSlXmDO, String szmyc){
        Integer szc = null;
        if(Objects.nonNull(bdcSlXmDO) && StringUtils.isNotBlank(bdcSlXmDO.getBdcdyh())) {
            FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(bdcSlXmDO.getBdcdyh(),bdcSlXmDO.getQjgldm());
            if (Objects.nonNull(fwHsDO) && Objects.nonNull(fwHsDO.getQsmyc())) {
                szc = fwHsDO.getQsmyc();
            }
        }
        if(Objects.isNull(szc) && StringUtils.isNotBlank(szmyc)){
            if(NumberUtils.isNumber(szmyc) && !szmyc.contains(CommonConstantUtils.ZF_YW_JH)){
                szc = Integer.parseInt(szmyc);
            }else{
                szc = cn.gtmap.realestate.common.util.CommonUtil.getNumberByStr(szmyc);
            }
        }
        return szc;
    }

    /*
     *  非网签数据，推送税务时，将受理编号同步给bdc_xm的jyhth
     */
    private void syncJyHtbhToXmJyhth(final String xmid, final String htbh){
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setXmid(xmid);
        bdcXmDO.setJyhth(htbh);
        this.bdcXmFeignService.updateBdcXm(bdcXmDO);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 推送税务数据
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 组织推送税务数据(非登记数据)
     */
    private List<TsswDataDTO> getFdjTsswData(String gzlslid,String htbh, Boolean onlyFdcq) {
        List<TsswDataDTO> tsswDataDTOList =new ArrayList<>();
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (bdcSlJbxxDO == null) {
            throw new AppException("工作流实例ID：" + gzlslid + "未找到相应的受理基本信息");
        }
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid(), "");
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                // 判断是否过滤其他权利数据。过滤时之推送房地产权的数据
                if(Objects.nonNull(onlyFdcq) && onlyFdcq
                        && Objects.nonNull(bdcSlXmDO.getQllx()) && !ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, bdcSlXmDO.getQllx())){
                    continue;
                }
                //判断当前项目存在交易信息,存在就以当前项目数据组织数据，暂不考虑批量流程
                List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxService.listBdcSlJyxxByXmid(bdcSlXmDO.getXmid());
                if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
                    if(StringUtils.isBlank(htbh) ||StringUtils.equals(htbh,bdcSlJyxxDOList.get(0).getHtbh())) {
                        TsswDataDTO tsswDataDTO = new TsswDataDTO();
                        List<BdcSlFwxxDO> bdcSlFwxxDOList = bdcSlFwxxService.listBdcSlFwxxByXmid(bdcSlXmDO.getXmid());
                        List<BdcSlSqrDO> bdcSlSqrDOList = new ArrayList<>();
                        List<BdcSlSqrDO> bdcSlZrfDOList = bdcSlSqrService.listBdcSlSqrByXmid(bdcSlXmDO.getXmid(), CommonConstantUtils.QLRLB_QLR);
                        if (CollectionUtils.isNotEmpty(bdcSlZrfDOList)) {
                            bdcSlSqrDOList.addAll(bdcSlZrfDOList);
                            tsswDataDTO.setQlrGyfs(Objects.nonNull(bdcSlZrfDOList.get(0).getGyfs()) ? String.valueOf(bdcSlZrfDOList.get(0).getGyfs()) : "0");
                            //设置申请人的份额数值 单独所有是100，共同功有50 ，按份共有取权利比例
                            setBdfe(bdcSlZrfDOList);
                        }
                        List<BdcSlSqrDO> bdcSlZcfDOList = bdcSlSqrService.listBdcSlSqrByXmid(bdcSlXmDO.getXmid(), CommonConstantUtils.QLRLB_YWR);
                        if (CollectionUtils.isNotEmpty(bdcSlZcfDOList)) {
                            bdcSlSqrDOList.addAll(bdcSlZcfDOList);
                            tsswDataDTO.setYwrGyfs(Objects.nonNull(bdcSlZcfDOList.get(0).getGyfs()) ? String.valueOf(bdcSlZcfDOList.get(0).getGyfs()) :"0");
                            setBdfe(bdcSlZrfDOList);
                        }
                        tsswDataDTO.setSlbh(bdcSlJbxxDO.getSlbh());
                        tsswDataDTO.setXmid(bdcSlXmDO.getXmid());
                        if (StringUtils.isBlank(bdcSlJyxxDOList.get(0).getXmmc())) {
                            FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(bdcSlXmDO.getBdcdyh(), bdcSlXmDO.getQjgldm());
                            if (Objects.nonNull(fwHsDO)) {
                                FwLjzDO fwLjzDO = fwLjzFeginService.queryLjzByFwDcbIndex(fwHsDO.getFwDcbIndex(), bdcSlXmDO.getQjgldm());
                                if (Objects.nonNull(fwLjzDO) && StringUtils.isNotBlank(fwLjzDO.getZldz())) {
                                    bdcSlJyxxDOList.get(0).setXmmc(fwLjzDO.getZldz());
                                } else {
                                    bdcSlJyxxDOList.get(0).setXmmc(bdcSlXmDO.getZl());
                                }
                            } else {
                                bdcSlJyxxDOList.get(0).setXmmc(bdcSlXmDO.getZl());
                            }
                        }
                        tsswDataDTO.setBdcSlJbxx(bdcSlJbxxDO);
                        //设置幢号、单元号、房间号
                        if (CollectionUtils.isNotEmpty(bdcSlFwxxDOList)) {
                            if (StringUtils.isBlank(bdcSlFwxxDOList.get(0).getFwdh()) || StringUtils.isBlank(bdcSlFwxxDOList.get(0).getFjh()) || StringUtils.isBlank(bdcSlFwxxDOList.get(0).getDyh())) {
                                FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(bdcSlXmDO.getBdcdyh(), bdcSlXmDO.getQjgldm());
                                if (Objects.nonNull(fwHsDO)) {
                                    //查逻辑幢
                                    if (StringUtils.isBlank(bdcSlFwxxDOList.get(0).getFwdh())) {
                                        FwLjzDO fwLjzDO = fwLjzFeginService.queryLjzByFwDcbIndex(fwHsDO.getFwDcbIndex(), "");
                                        if (Objects.nonNull(fwLjzDO)) {
                                            bdcSlFwxxDOList.get(0).setFwdh(StringUtils.isNotBlank(fwLjzDO.getDh()) ? fwLjzDO.getDh() : "1");
                                        } else {
                                            bdcSlFwxxDOList.get(0).setFwdh("1");
                                        }
                                    }
                                    if (StringUtils.isBlank(bdcSlFwxxDOList.get(0).getFjh())) {
                                        bdcSlFwxxDOList.get(0).setFjh(StringUtils.isNotBlank(fwHsDO.getFjh()) ? fwHsDO.getFjh() : "1");
                                    } else {
                                        bdcSlFwxxDOList.get(0).setFjh("101");
                                    }
                                    if (StringUtils.isBlank(bdcSlFwxxDOList.get(0).getDyh())) {
                                        bdcSlFwxxDOList.get(0).setDyh(StringUtils.isNotBlank(fwHsDO.getDyh()) ? fwHsDO.getDyh() : "1");
                                    } else {
                                        bdcSlFwxxDOList.get(0).setDyh("1");
                                    }
                                } else {
                                    bdcSlFwxxDOList.get(0).setFwdh("1");
                                    bdcSlFwxxDOList.get(0).setDyh("1");
                                    bdcSlFwxxDOList.get(0).setFjh("101");
                                }
                            }
                            if(StringUtils.isBlank(bdcSlFwxxDOList.get(0).getJzcx())) {
                                //建筑朝向没有值默认02
                                bdcSlFwxxDOList.get(0).setJzcx("02");
                            }
                        }
                        tsswDataDTO.setBdcSlFwxx(CollectionUtils.isNotEmpty(bdcSlFwxxDOList) && null != bdcSlFwxxDOList.get(0) ? bdcSlFwxxDOList.get(0) : new BdcSlFwxxDO());
                        tsswDataDTO.setBdcSlXm(bdcSlXmDO);
                        tsswDataDTO.setBdcSlJyxx(bdcSlJyxxDOList.get(0));

                        //契税权属转移用途代码 取fwyt
                        tsswDataDTO.setQsqszyytDm(CollectionUtils.isNotEmpty(bdcSlFwxxDOList) && null !=bdcSlFwxxDOList.get(0) && bdcSlFwxxDOList.get(0).getFwyt() != null ? bdcSlFwxxDOList.get(0).getFwyt().toString() : "");
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA);
                        if (Objects.nonNull(bdcSlJyxxDOList.get(0).getHtdjsj())) {
                            tsswDataDTO.setHtqdsj(DateUtils.formateTime(bdcSlJyxxDOList.get(0).getHtdjsj(), dateTimeFormatter));
                        }

                        // 处理申请人表中的 是否直系亲属 null设置成0 便于对照
                        if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                            for (BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList) {
                                bdcSlSqrDO.setSfzxqs(this.handlerSwSfzxqs(tsswDataDTO.getHtqdsj(), bdcSlSqrDO));
                            }
                        }
                        tsswDataDTO.setSqrList(bdcSlSqrDOList);

                        // 房屋类型 住房 Y 非住房 N 取房屋类型
                        String sfptzfbz = "N";
                        // 权属转移对象
                        String qsqszydxDm = dealQsqszydxDm(tsswDataDTO);
                        if (CollectionUtils.isNotEmpty(bdcSlFwxxDOList) && Objects.nonNull(bdcSlFwxxDOList.get(0))) {
                            BdcSlFwxxDO bdcSlFwxxDO = bdcSlFwxxDOList.get(0);
                            Integer fwlx = bdcSlFwxxDO.getFwlx();
                            if (Objects.nonNull(fwlx) && 1 == fwlx) {
                                sfptzfbz = "Y";
                            }
                        }
                        tsswDataDTO.setSfptzfbz(sfptzfbz);
                        tsswDataDTO.setQsqszydxDm(qsqszydxDm);
                        tsswDataDTO.setXmids(bdcSlXmDOList.stream().map(BdcSlXmDO::getXmid).collect(Collectors.toList()));
                        tsswDataDTOList.add(tsswDataDTO);
                    }
                }
            }
        }

        return tsswDataDTOList;
    }

    // 处理税务的是否直系亲属标志， 2022-03-01之前返回0，1 2022-03-01之后返回3，4
    private int handlerSwSfzxqs(String htdjsj, BdcSlSqrDO bdcSlSqrDO){
        if(StringUtils.isNotBlank(htdjsj)){
            Integer sfzxqs = bdcSlSqrDO.getSfzxqs();
            Date compare = DateUtils.formatDate("2022-02-28");
            Date htdjsjDate = DateUtils.formatDate(htdjsj);
            if (org.apache.commons.lang3.time.DateUtils.truncatedCompareTo(htdjsjDate, compare, Calendar.MILLISECOND) > 0) {
                // 2022-03-01之后返回3，4
                if(Objects.nonNull(bdcSlSqrDO.getZrfcsfgx())){
                    sfzxqs = bdcSlSqrDO.getZrfcsfgx();
                }
                if(null == sfzxqs || 0 == sfzxqs){
                    return 3;
                }else if(sfzxqs == 1){
                    return 4;
                }else{
                    return sfzxqs;
                }
            } else {
                // 2022-03-01之前返回0，1
                return null == sfzxqs ? 0 : sfzxqs;
            }
        }
        return 0;
    }
    /**
     * @param responseDTO 税务信息查询响应结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理税务查询信息
     */
    @Override
    public void handleQuerySwxxResponse(QuerySwxxResponseDTO responseDTO, String xmid,String gxlx) {
        List<QuerySwxxHsxxDTO> querySwxxHsxxDTOList = responseDTO.getHsxxList();

        // 获取最外层的完税状态，并转成int
        String wsztStr = responseDTO.getWszt();
        int wszt = 0;
        if (StringUtils.isNotEmpty(wsztStr)) {
            wszt = Integer.parseInt(wsztStr);
        }
        // 企业简易征收标志
        String qyjyzsbz = responseDTO.getQyjyzsbz();

        dealHsxxAndMx(querySwxxHsxxDTOList, xmid, gxlx, wszt, qyjyzsbz);

    }

    @Override
    public Map updateWsztByHtbh(String htbh, String wszt) {
        Map resultMap = new HashMap();
        if (StringUtils.isBlank(htbh) || StringUtils.isBlank(wszt)) {
            resultMap.put("msg", "缺失参数htbh或wszt");
            return resultMap;
        }
        List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxService.listBdcSlJyxxByHtbh(htbh);
        if (CollectionUtils.isEmpty(bdcSlJyxxDOList)) {
            resultMap.put("msg", "该合同编号" + htbh + "未查询到对应的交易信息");
            return resultMap;
        }
        BdcSlJyxxDO bdcSlJyxxDO = bdcSlJyxxDOList.get(0);
        BdcSlHsxxDO cxbdcSlHsxxDO = new BdcSlHsxxDO();
        cxbdcSlHsxxDO.setXmid(bdcSlJyxxDO.getXmid());
        List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxService.listBdcSlHsxx(cxbdcSlHsxxDO);
        if (CollectionUtils.isEmpty(bdcSlHsxxDOList)) {
            resultMap.put("msg", "该合同编号" + htbh + "未查询到对应的核税信息");
            return resultMap;
        }
        for (BdcSlHsxxDO bdcSlHsxxDO : bdcSlHsxxDOList) {
            bdcSlHsxxDO.setWszt(Integer.parseInt(wszt));
            bdcSlHsxxService.updateBdcSlHsxx(bdcSlHsxxDO);
        }
        resultMap.put("msg", "success");
        return resultMap;

    }

    @Override
    public Boolean checkSfwsByGzlslid(String gzlslid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            //只有项目表审批系统业务号不为空,审批来源为一窗受理的并且为产权的流程才需调用接口验证，其余默认已完税
            if (StringUtils.isNotBlank(bdcXmDO.getSpxtywh()) && !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDO.getQllx()) && CheckWwsqOrYcslUtils.checkIsYcsl(bdcXmDO.getSply())) {
                String beanName = "ycsl_hsztbyxmid";
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("xmid", bdcXmDO.getXmid());
                Object response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
                if (response != null && ((Map) response).get("wszt") != null) {
                    String wszt = ((Map) response).get("wszt").toString();
                    return StringUtils.equals(Constants.WSZT_YWS, wszt);
                }
            }
        }
        return true;

    }

    /**
     * @param gzlslid 工作流实例ID
     * @return QuerySwxxResponseDTO 完税信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询商品房完税状态(不查询返回null)
     */
    @Override
    public List<QuerySwxxResponseDTO> querySpfwszt(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException(messageProvider.getMessage("工作流实例ID:" + gzlslid + ",商品房完税状态查询，缺失有效参数"));
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("当前流程未查询到项目信息！");
        }
        List<QuerySwxxResponseDTO> querySwxxResponseDTOList = new ArrayList<>();
        for (BdcXmDO bdcXmDO : bdcXmDOList) {
            QuerySwxxResponseDTO responseDTO = this.getSpfXmWszt(bdcXmDO,CommonConstantUtils.SW_GXLX_INSERT_UPDATE);
            if (null != responseDTO) {
                querySwxxResponseDTOList.add(responseDTO);
            }
        }
        return querySwxxResponseDTOList;
    }

    /**
     * @param bdcXmDO 项目信息
     * @return QuerySwxxResponseDTO 接口返回信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取商品房项目完税状态
     */
    @Override
    public QuerySwxxResponseDTO getSpfXmWszt(BdcXmDO bdcXmDO,String gxlx) {
        if (StringUtils.isAnyBlank(bdcXmDO.getXmid(), bdcXmDO.getSlbh(), bdcXmDO.getSwfybh())) {
            throw new MissingArgumentException("存在参数为空！");
        }
        //需求29287 商品房只需传买方证件号,存量房需要传买卖双方证件号
        List<Map> zjhList = new ArrayList<>();
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(bdcXmDO.getXmid());
        if (CollectionUtils.isNotEmpty(spfdyidList) && StringUtils.isNotBlank(bdcXmDO.getGzldyid()) && spfdyidList.contains(bdcXmDO.getGzldyid())) {
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        }
        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        if(CollectionUtils.isEmpty(bdcQlrDOList)){
            throw new AppException("权利人集合为空");
        }

        for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
            if (StringUtils.isNotBlank(bdcQlrDO.getZjh())) {
                Map map = new HashMap();
                map.put("zjh", bdcQlrDO.getZjh());
                zjhList.add(map);
            }
        }

        // 德宽版本税务接口 证件号分权利人与业务人证件号
        List<String> qlrzjhList =  bdcQlrDOList.stream().filter(t->CommonConstantUtils.QLRLB_QLR.equals(t.getQlrlb()))
                .map(BdcQlrDO::getZjh).collect(Collectors.toList());
        List<String> ywrzjhList =  bdcQlrDOList.stream().filter(t->CommonConstantUtils.QLRLB_YWR.equals(t.getQlrlb()))
                .map(BdcQlrDO::getZjh).collect(Collectors.toList());
        // 获取权利信息中的幢号 与 房间号
        BdcQl bdcQl = this.bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
        String dh = "";
        String fjh = "";
        if(bdcQl instanceof BdcFdcqDO){
            BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
            dh = bdcFdcqDO.getZh();
            fjh = bdcFdcqDO.getFjh();
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("xmid", bdcXmDO.getXmid());
        paramMap.put("slbh", bdcXmDO.getSlbh());
        paramMap.put("fwuuid", this.getFwuuidBySlbh(bdcXmDO.getSlbh()));
        paramMap.put("zl", bdcXmDO.getZl());
        paramMap.put("fybh", bdcXmDO.getSwfybh());
        paramMap.put("zjhList", zjhList);
        paramMap.put("qlrzjhList", qlrzjhList);
        paramMap.put("ywrzjhList", ywrzjhList);
        paramMap.put("dh", dh);
        paramMap.put("fjh", fjh);
        paramMap.put("mj", bdcXmDO.getDzwmj());
        LOGGER.warn("spfwszt接口查询参数：{}", paramMap);

        String beanName  = "";
        if(swjkVersion.equals(CommonConstantUtils.SYSTEM_SW_DK)){
            beanName = "spfwszt_dk";
        }else{
            beanName = "spfwszt";
        }
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
        if (response != null) {
            QuerySwxxResponseDTO responseDTO = JSONObject.parseObject(JSONObject.toJSONString(response), QuerySwxxResponseDTO.class);
            // 只有在完税状态下,保存完税信息，并返回完税信息
            if (responseDTO != null && CommonConstantUtils.WSZT_YWS.equals(responseDTO.getWszt())) {
                LOGGER.warn("商品房完税状态查询接口调用成功！受理编号：{},响应内容：{}", bdcXmDO.getSlbh(), responseDTO);
                // 处理返回信息
                // 申报提醒函上传
                uploadSbtxh(responseDTO,bdcXmDO.getSlbh());
                handleQuerySwxxResponse(responseDTO, bdcXmDO.getXmid(),gxlx);
            }
            return responseDTO;
        }
        return null;
    }



    /**
     * @description: 组织税务的查询参数
     * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @date: 2022/11/23 17:26
     * @param bdcXmDOList
     * @param slbh 受理编号
     * @return: cn.gtmap.realestate.common.core.qo.accept.BdcSwxxQO
     **/
    private BdcSwxxQO getBdcSwxxQO(List<BdcXmDO> bdcXmDOList, String slbh) {
        BdcSwxxQO bdcSwxxQO = new BdcSwxxQO();
        bdcSwxxQO.setTdbz(this.getTdbz(slbh, null) ? "1" : "0");
        if (bdcXmDOList.size() == 1) {
            // 单个
            bdcSwxxQO.setFwuuid(this.getFwuuidBySlbh(slbh));
            bdcSwxxQO.setMergebz("0");
        } else {
            // 批量
            String fwuuids = this.getFwuuidBySlbh(slbh);
            if (StringUtils.isNotBlank(fwuuids)) {
                String fwuuid = fwuuids.split(",")[0];
                bdcSwxxQO.setFwuuid(fwuuid);
            }
            bdcSwxxQO.setMergebz("1");
        }
        bdcSwxxQO.setBeanName("swCxxx_dk");
        return bdcSwxxQO;
    }

    /**
     * 根据受理编号获取不动产受理基本信息中的 fwuuid （税务唯一标识）
     */
    private String getFwuuidBySlbh(String slbh){
        if(StringUtils.isNotBlank(slbh)){
            BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxService.queryBdcSlJbxxBySlbh(slbh, "");
            if(Objects.nonNull(bdcSlJbxxDO) && StringUtils.isNotBlank(bdcSlJbxxDO.getFwuuid())){
                return bdcSlJbxxDO.getFwuuid();
            }else{
                LOGGER.error("获取税务fwuuid异常，未获取到受理基本信息，受理编号：{}", slbh);
            }
        }else{
            LOGGER.error("获取税务fwuuid异常，未获取到受理编号。");
        }
        return "";
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return String 最终结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取税务三要素核税信息
     */
    @Override
    public String getSwsysHsxx(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("gzlslid");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            return "项目信息为空！";
        }
        for(BdcXmDO bdcXmDO:bdcXmDOList){
            if (!CheckWwsqOrYcslUtils.checkIsYcsl(bdcXmDO.getSply()) || StringUtils.isBlank(bdcXmDO.getJyhth())) {
                return "非一窗的件，或者交易合同号为空！";
            }
            this.queryAndSaveSysHsxx(bdcXmDO.getJyhth(), bdcXmDO.getXmid());
        }
        return "更新核税信息数据成功";
    }

    /**
     * 查询三要素信息，并更新核税信息
     */
    private void queryAndSaveSysHsxx(String htbh, String xmid){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("htbh", htbh);
        paramMap.put("xmid", xmid);
        // 调用外部服务获取税费三要素接口，返回值为[{"swjgdm":"","nsrsbh":"","sphm":""},{"swjgdm":"","nsrsbh":"","sphm":""}]
        Object response = exchangeInterfaceFeignService.requestInterface("swsys", paramMap);
        LOGGER.info("查询三要素接口调用成功！合同编号：{},项目ID:{},响应内容：{}", htbh, xmid, response);
        if(response != null) {
            JSONArray resultArray = JSONObject.parseArray(JSONObject.toJSONString(response));
            // 根据三要素接口返回信息更新核税信息数据
            for (int i = 0; i < resultArray.size(); i++) {
                JSONObject resultObj = resultArray.getJSONObject(i);
                String nsrsbh = resultObj.getString("nsrsbh");
                // 根据纳税人识别号获取对应的权利人信息
                BdcSlSqrQO bdcSlSqrQO = new BdcSlSqrQO();
                bdcSlSqrQO.setZjh(nsrsbh);
                bdcSlSqrQO.setXmid(xmid);
                List<BdcSlSqrDO> bdcSlSqrDOS = bdcSlSqrService.queryBdcSlSqrBySqrQO(bdcSlSqrQO);
                if (CollectionUtils.isNotEmpty(bdcSlSqrDOS)) {
                    this.modifyHsxxBySqrxx(xmid, resultObj.getString("swjgdm"),
                            resultObj.getString("sphm"), bdcSlSqrDOS);
                }
            }
        }
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return Boolean 建行订单查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 建行订单查询并缴库入库
     */
    @Override
    public Object yhddcxAndJkrk(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失工作流实例ID！");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList) || CollectionUtils.size(bdcXmDOList) != 1) {
            return null;
        }
        String cmdtyOrdrNo;
        String xmid = bdcXmDOList.get(0).getXmid();
        Integer qlrlb;
        BdcSfSsxxQO bdcSfSsxxQO =new BdcSfSsxxQO();
        bdcSfSsxxQO.setXmid(xmid);
        bdcSfSsxxQO.setCxbz(CommonConstantUtils.SFSSXX_CXBZ_SF_SS);
        bdcSfSsxxQO.setSqrlb(CommonConstantUtils.QLRLB_QLR);
        BdcSfSsxxDTO bdcSfSsxxDTO = bdcSfService.queryBdcSfSsxxDTO(bdcSfSsxxQO);
        if (null != bdcSfSsxxDTO && (CollectionUtils.size(bdcSfSsxxDTO.getBdcSfxxDTOS()) > 0 || CollectionUtils.size(bdcSfSsxxDTO.getBdcSwxxDTOS()) > 0)) {
            cmdtyOrdrNo = xmid + CommonConstantUtils.QLRLB_QLR;
            qlrlb = Integer.parseInt(CommonConstantUtils.QLRLB_QLR);
        } else {
            bdcSfSsxxQO.setSqrlb(CommonConstantUtils.QLRLB_YWR);
            bdcSfSsxxDTO = bdcSfService.queryBdcSfSsxxDTO(bdcSfSsxxQO);
            if (null != bdcSfSsxxDTO && (CollectionUtils.size(bdcSfSsxxDTO.getBdcSfxxDTOS()) > 0 || CollectionUtils.size(bdcSfSsxxDTO.getBdcSwxxDTOS()) > 0)) {
                cmdtyOrdrNo = xmid + CommonConstantUtils.QLRLB_YWR;
                qlrlb = Integer.parseInt(CommonConstantUtils.QLRLB_YWR);
            } else {
                throw new MissingArgumentException("登记中未查询到税费信息！");
            }
        }
        Map paramMap = new HashMap(1);
        paramMap.put("Cmdty_Ordr_No", cmdtyOrdrNo);
        Object response = exchangeInterfaceFeignService.requestInterface("jsyh_cxjfjg", paramMap);
        LOGGER.warn("建行缴费状态查询，入参{}，查询结果{}", JSONObject.toJSONString(paramMap), response);

        if (response != null) {
            Map responseDTO = JSONObject.parseObject(JSONObject.toJSONString(response));
            if (MapUtils.isNotEmpty(responseDTO) && StringUtils.isNotBlank(MapUtils.getString(responseDTO, "Py_Ordr_No"))) {
                // 已推送成功，则保存推送状态到受理收费信息
                updateSlSfxxYhJkrkzt(xmid, qlrlb, CommonConstantUtils.SF_S_DM);
                LOGGER.warn("支付订单号{}", MapUtils.getString(responseDTO, "Py_Ordr_No"));
                return responseDTO;
            }
        }
        return this.yhjkrk(bdcSfSsxxDTO);
    }

    /**
     * @param bdcSfSsxxDTO
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 银行缴库入库
     */
    @Override
    public Object yhjkrk(BdcSfSsxxDTO bdcSfSsxxDTO) {
        Map responseDTO = new HashMap();
        responseDTO.put("yhjkrkzt", CommonConstantUtils.SF_F_DM);
        // 再次推送建设银行缴库入库
        Object response = exchangeInterfaceFeignService.requestInterface("jsyh_jkjk", bdcSfSsxxDTO);
        LOGGER.warn("建行银行缴库入库，入参{}，查询结果{}", JSONObject.toJSONString(bdcSfSsxxDTO), response);
        if (response != null) {
            responseDTO.putAll(JSONObject.parseObject(JSONObject.toJSONString(response)));
            if (MapUtils.isNotEmpty(responseDTO)) {
                Boolean ifUpdate = false;
                if (responseDTO.containsKey("jsmx")) {
                    // 缴税明细
                    JSONArray jsmxList = (JSONArray) responseDTO.get("jsmx");
                    if (CollectionUtils.isNotEmpty(jsmxList) && CollectionUtils.size(jsmxList) > 0
                            && StringUtils.equals(((JSONObject) jsmxList.get(0)).getString("jsjg"), "0")) {
                        ifUpdate = true;
                    }
                }
                if (responseDTO.containsKey("fsmx")) {
                    // 非税明细
                    JSONArray fsmxList = (JSONArray) responseDTO.get("fsmx");
                    if (CollectionUtils.isNotEmpty(fsmxList) && CollectionUtils.size(fsmxList) > 0
                            && StringUtils.equals(((JSONObject) fsmxList.get(0)).getString("fsjg"), "0")) {
                        ifUpdate = true;
                    }
                }
                // 已推送成功，则保存推送状态到受理收费信息
                if (ifUpdate) {
                    updateSlSfxxYhJkrkzt(bdcSfSsxxDTO.getXmid(), Integer.parseInt(bdcSfSsxxDTO.getQlrlb()), CommonConstantUtils.SF_S_DM);
                    responseDTO.put("yhjkrkzt", CommonConstantUtils.SF_S_DM);
                }
                return responseDTO;
            }
        }
        return responseDTO;
    }

    @Override
    public CommonResponse ykqTsJkrk(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
            bdcXmFbQO.setGzlslid(gzlslid);
            List<BdcXmFbDO> bdcXmFbDOList = this.bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
            if (CollectionUtils.isNotEmpty(bdcXmFbDOList)) {
                BdcXmFbDO bdcXmFbDO = bdcXmFbDOList.get(0);
                // 判断当前流程的审批来源是否为 一窗税费托管， 否则不进行推送缴库操作
                if (Objects.equals(CommonConstantUtils.SF_S_DM, bdcXmFbDO.getSfsftg())) {
                    if(!this.isJkrk(gzlslid)){
                        BdcXmDO bdcXmDO = this.bdcXmFeignService.queryBdcXmByXmid(bdcXmFbDO.getXmid());
                        if(Objects.isNull(bdcXmDO)){
                            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产项目信息");
                        }
                        BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);

                        // 1、先获取税费三要素信息
                        Map paramMap = new HashMap();
                        paramMap.put("slbh",  bdcSlJbxxDO.getSlbh());
                        Object sfsysResponse = exchangeInterfaceFeignService.requestInterface("wwsq_cxswsys", paramMap);
                        LOGGER.info("wwsq_cxswsys 获取税费三要素接口返回值：{},对应受理编号：{}", sfsysResponse, bdcSlJbxxDO.getSlbh());
                        if(Objects.nonNull(sfsysResponse)){
                            Map<String, Object> param = new HashMap<>(4);
                            param.put("bdcdyh", bdcXmDO.getBdcdyh());
                            param.put("nwslbh", bdcSlJbxxDO.getSlbh());
                            param.put("sfze", this.bdcSlSfssDdxxService.computeSfze(gzlslid, CommonConstantUtils.QLRLB_QLR));
                            param.put("qhdm", Optional.ofNullable(bdcSlJbxxDO.getQxdm()).orElse(bdcXmDO.getQxdm()));
                            LOGGER.info("请求一卡清缴库入库参数：{}", param.toString());
                            Object response = this.exchangeInterfaceFeignService.requestInterface("ykq_tsjk", param);
                            LOGGER.info("请求一卡清缴库入库返回值信息：{}", response.toString());
                            if (Objects.nonNull(response)) {
                                JSONObject reuslt = JSONObject.parseObject(JSON.toJSONString(response));
                                JSONObject data = JSONObject.parseObject(reuslt.get("data").toString());
                                if (StringUtils.equals("00", data.getString("code"))) {
                                    {
                                        // 更新缴库入库状态
                                        BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
                                        bdcSlHsxxDO.setYhjkrkzt(CommonConstantUtils.SF_S_DM);
                                        bdcSlHsxxDO.setWszt(CommonConstantUtils.SF_S_DM);
                                        this.bdcSlHsxxService.batchUpdateBdcSlHsxx(bdcSlHsxxDO, gzlslid);

                                        // 更新收费信息表，缴费状态和缴库入库状态
                                        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
                                        if(CollectionUtils.isNotEmpty(bdcSlSfxxDOList)){
                                            for(BdcSlSfxxDO sfxx: bdcSlSfxxDOList){
                                                sfxx.setSfzt(BdcSfztEnum.YJF.key());
                                                sfxx.setYhjkrkzt(CommonConstantUtils.SF_S_DM);
                                            }
                                            this.bdcSlSfxxService.batchUpdateBdcSlSfxx(bdcSlSfxxDOList);
                                        }
                                    }
                                    this.addYkqTsJkrkLog(gzlslid, JSON.toJSONString(param), CommonConstantUtils.SF_S_DM);
                                    return CommonResponse.ok(data.getString("code"));
                                } else {
                                    this.addYkqTsJkrkLog(gzlslid, JSON.toJSONString(param), CommonConstantUtils.SF_F_DM);
                                    return CommonResponse.fail("一卡清缴库状态接口返回失败," + data.getString("msg"));
                                }
                            } else {
                                this.addYkqTsJkrkLog(gzlslid, JSON.toJSONString(param), CommonConstantUtils.SF_F_DM);
                                LOGGER.error("未获取到一卡清缴库入库返回值信息");
                                return CommonResponse.fail("未获取到一卡清缴库入库返回值信息");
                            }
                        }else{
                            LOGGER.error("未获取到税费三要素接口返回值信息");
                            return CommonResponse.fail("未获取到税费三要素接口返回值信息");
                        }
                    }else{
                        return CommonResponse.ok("00");
                    }
                }
            }
        }
        return null;
    }

    /**
     * 判断当前流程是否缴库入库 true 已缴库 false 未缴库
     */
    private boolean isJkrk(String gzlslid){
        List<BdcSlHsxxDO> bdcSlHsxxDOList = this.bdcSlHsxxService.listBdcSlHsxxByGzlslid(gzlslid, null);
        if(CollectionUtils.isNotEmpty(bdcSlHsxxDOList)){
            boolean jkrk = true;
            for(BdcSlHsxxDO hsxx : bdcSlHsxxDOList){
                if(!CommonConstantUtils.SF_S_DM.equals(hsxx.getYhjkrkzt())){
                    jkrk = false;
                }
            }
            return jkrk;
        }
        return false;
    }

    // 添加缴库入库推送日志
    private void addYkqTsJkrkLog(String gzlslid, String czcs, Integer czzt) {
        BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
        bdcCzrzDO.setGzlslid(gzlslid);
        bdcCzrzDO.setCzcs(czcs);
        bdcCzrzDO.setCzlx(BdcCzrzLxEnum.CZRZ_CZLX_YKQJKRK.key());
        bdcCzrzDO.setCzzt(czzt);
        bdcCzrzDO.setCzmc("ykq_tsjk");
        bdcCzrzDO.setCzr(this.userManagerUtils.getCurrentUserName());
        bdcCzrzDO.setCzsj(new Date());
        bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
    }

    @Override
    public CommonResponse ykqZhjq(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数：工作流实例ID");
        }
        boolean sfsftg = false;
        BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
        bdcXmFbQO.setGzlslid(gzlslid);
        List<BdcXmFbDO> bdcXmFbDOList = this.bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
        if (CollectionUtils.isNotEmpty(bdcXmFbDOList)) {
            BdcXmFbDO bdcXmFbDO = bdcXmFbDOList.get(0);
            // 判断当前流程的审批来源是否为 一窗税费托管， 否则不进行推送缴库操作
            if (Objects.equals(CommonConstantUtils.SF_S_DM, bdcXmFbDO.getSfsftg())) {
                sfsftg = true;
            }
        }
        if(!sfsftg){
            return CommonResponse.fail("非税费托管流程，不进行账户结清操作。");
        }
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(bdcXmDTOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产项目信息，工作流实例ID为：" + gzlslid);
        }
        if(this.isJkrk(gzlslid)){
            Map<String, Object> param = new HashMap<>(1);
            param.put("bdcdyh", bdcXmDTOList.get(0).getBdcdyh());
            param.put("nwslbh", bdcXmDTOList.get(0).getSlbh());
            LOGGER.info("请求一卡清账户结清参数：{}", param.toString());
            Object response = this.exchangeInterfaceFeignService.requestInterface("ykq_zhjq", param);
            LOGGER.info("请求一卡清账户结清返回值信息：{}", response.toString());
            if (Objects.nonNull(response)) {
                JSONObject reuslt = JSONObject.parseObject(JSON.toJSONString(response));
                JSONObject data = JSONObject.parseObject(reuslt.get("data").toString());
                if (StringUtils.equals("00", data.getString("code"))) {
                    return CommonResponse.ok(data);
                } else {
                    return CommonResponse.fail("一卡清账户结清接口返回失败");
                }
            } else {
                return CommonResponse.fail("未获取到一卡清账户结清接口返回值信息");
            }
        }else{
            LOGGER.info("缴库入库状态未成功, 不执行账户结清");
            return CommonResponse.fail("缴库入库状态未成功。");
        }
    }

    @Override
    public TsswDataDTO getTsswDataDTO(TsswDataQO tsswDataQO) {
        if (!CheckParameter.checkAnyParameter(tsswDataQO, "slbh", "htbh", "gzlslid")) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER) + JSONObject.toJSONString(tsswDataQO));
        }
        String gzlslid = "";
        if (StringUtils.isNotBlank(tsswDataQO.getHtbh())) {
            List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxService.listBdcSlJyxxByHtbh(tsswDataQO.getHtbh());
            if(CollectionUtils.isNotEmpty(bdcSlJyxxDOList)){
                BdcSlXmDO bdcSlXmDO =bdcSlXmService.queryBdcSlXmByXmid(bdcSlJyxxDOList.get(0).getXmid());
                if(bdcSlXmDO != null){
                    BdcSlJbxxDO bdcSlJbxxDO =bdcSlJbxxService.queryBdcSlJbxxByJbxxid(bdcSlXmDO.getJbxxid());
                    if(bdcSlJbxxDO != null){
                        gzlslid =bdcSlJbxxDO.getGzlslid();
                    }
                }
            }

        }else if(StringUtils.isNotBlank(tsswDataQO.getSlbh())){
            BdcSlJbxxDO bdcSlJbxxDO =bdcSlJbxxService.queryBdcSlJbxxBySlbh(tsswDataQO.getSlbh(),CommonConstantUtils.JBXX_TYPE_YCSL);
            if(bdcSlJbxxDO != null){
                gzlslid =bdcSlJbxxDO.getGzlslid();
            }

        } else if (StringUtils.isNotBlank(tsswDataQO.getGzlslid())) {
            gzlslid = tsswDataQO.getGzlslid();
        }
        List<TsswDataDTO> tsswDataDTOList = getFdjTsswData(gzlslid, tsswDataQO.getHtbh(), null);
        //组装处理数据
        BdcSwTsclService bdcSwTsclService = InterfaceCodeBeanFactory.getBean(bdcSwTsclServiceSet, dataversion);
        if (null != bdcSwTsclService) {
            return bdcSwTsclService.handleTsswData(tsswDataDTOList);

        }
        if (CollectionUtils.isEmpty(tsswDataDTOList)) {
            return null;
        }
        return tsswDataDTOList.get(0);

    }

    @Override
    public void saveOrUpdateSwxxDTO(List<BdcSwxxDTO> bdcSwxxDTOList, String xmid){
        if (CollectionUtils.isNotEmpty(bdcSwxxDTOList)) {
            //删除已有的核税信息
            bdcSlHsxxService.deleteSwxx(xmid);
            //根据权利人列表分组
            List<QuerySwxxHsxxDTO> qlrSwxxList = new ArrayList<>();
            List<QuerySwxxHsxxDTO> ywrSwxxList = new ArrayList<>();
            for (BdcSwxxDTO bdcSwxxDTO : bdcSwxxDTOList) {
                QuerySwxxHsxxDTO querySwxxHsxxDTO =new QuerySwxxHsxxDTO();
                BeanUtils.copyProperties(bdcSwxxDTO, querySwxxHsxxDTO);
                BdcSlHsxxDO bdcSlHsxxDO = querySwxxHsxxDTO.getBdcSlHsxxDO();
                if(bdcSlHsxxDO != null) {
                    if (CommonConstantUtils.QLRLB_QLR.equals(bdcSlHsxxDO.getSqrlb())) {
                        qlrSwxxList.add(querySwxxHsxxDTO);
                    } else if (CommonConstantUtils.QLRLB_YWR.equals(bdcSlHsxxDO.getSqrlb())) {
                        ywrSwxxList.add(querySwxxHsxxDTO);
                    }
                }
            }
            BdcSwxxDTO qlrSwxxDTO =dealHsxxMx(qlrSwxxList, xmid,CommonConstantUtils.QLRLB_QLR);
            BdcSwxxDTO ywrSwxxDTO =dealHsxxMx(ywrSwxxList, xmid,CommonConstantUtils.QLRLB_YWR);
            List<BdcSlHsxxMxDO> bdcSlHsxxMxDOList =new ArrayList<>();
            if(qlrSwxxDTO != null &&qlrSwxxDTO.getBdcSlHsxxDO() != null){
                entityMapper.insertSelective(qlrSwxxDTO.getBdcSlHsxxDO());
                if(CollectionUtils.isNotEmpty(qlrSwxxDTO.getBdcSlHsxxMxDOList())){
                    bdcSlHsxxMxDOList.addAll(qlrSwxxDTO.getBdcSlHsxxMxDOList());
                }
            }
            if(ywrSwxxDTO != null &&ywrSwxxDTO.getBdcSlHsxxDO() != null){
                entityMapper.insertSelective(ywrSwxxDTO.getBdcSlHsxxDO());
                if(CollectionUtils.isNotEmpty(ywrSwxxDTO.getBdcSlHsxxMxDOList())){
                    bdcSlHsxxMxDOList.addAll(ywrSwxxDTO.getBdcSlHsxxMxDOList());
                }

            }
            if(CollectionUtils.isNotEmpty(bdcSlHsxxMxDOList)) {
                entityMapper.insertBatchSelective(bdcSlHsxxMxDOList);
            }
        }
    }

    @Override
    public void saveSwxxDTOByHtbh(List<BdcSwxxDTO> bdcSwxxDTOList, String htbh){
        if(StringUtils.isBlank(htbh)){
            throw new MissingArgumentException("htbh");
        }
        List<BdcSlJyxxDO> bdcSlJyxxDOList =bdcSlJyxxService.listBdcSlJyxxByHtbh(htbh);
        if(CollectionUtils.isNotEmpty(bdcSlJyxxDOList)){
            saveOrUpdateSwxxDTO(bdcSwxxDTOList,bdcSlJyxxDOList.get(0).getXmid());
        }
    }

    @Override
    public void updateSwsysByNsrbhAndSlbh(List<BdcSlHsxxDO> bdcSlHsxxDOList,String slbh){
        if(CollectionUtils.isEmpty(bdcSlHsxxDOList) ||StringUtils.isBlank(slbh)){
            throw new MissingArgumentException("缺失需要更新的核税信息或查询参数受理编号");
        }
        bdcSlHsxxDOList = bdcSlHsxxDOList.stream().filter(t-> StringUtils.isNotBlank(t.getNsrsbh())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(bdcSlHsxxDOList)){
            throw new AppException("核税信息为空！");
        }
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxBySlbh(slbh);
        if(CollectionUtils.isEmpty(bdcXmDTOList)){
            throw new AppException("项目信息为空！");
        }

        for(BdcXmDTO bdcxm:bdcXmDTOList){
            for(BdcSlHsxxDO bdcSlHsxxDO:bdcSlHsxxDOList){
                bdcSlHsxxDO.setXmid(bdcxm.getXmid());
                bdcSlHsxxService.updateBdcSlHsxxByXmidAndNsrsbh(bdcSlHsxxDO);
            }
        }
    }

    /**
     * @param xmid  项目ID
     * @param qlrlb 权利人类别
     * @param sfSdm 是否成功状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新受理收费信息银行缴库入库状态
     */
    private void updateSlSfxxYhJkrkzt(String xmid, Integer qlrlb, Integer sfSdm) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("缺失参数xmid！");
        }
        if (null == qlrlb) {
            throw new MissingArgumentException("缺失参数qlrlb！");
        }
        BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
        bdcSlSfxxDO.setYhjkrkzt(sfSdm);
        Example example = new Example(BdcSlSfxxDO.class);
        example.createCriteria().andEqualTo("xmid", xmid).andEqualTo("qlrlb", qlrlb);

        int result = entityMapper.updateByExampleSelectiveNotNull(bdcSlSfxxDO, example);
        LOGGER.warn("更新受理收费信息银行缴库入库状态,更新数据量：{}，更新参数：xmid->{},qlrlb->{}", result, xmid, qlrlb);
    }

    /**
     * 根据申请人信息修改核税信息
     */
    private void modifyHsxxBySqrxx(String xmid, String swjgdm, String sphm, List<BdcSlSqrDO> bdcSlSqrDOS) {
        for (BdcSlSqrDO sqr : bdcSlSqrDOS) {
            BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
            bdcSlHsxxDO.setXmid(xmid);
            bdcSlHsxxDO.setSwjgdm(swjgdm);
            bdcSlHsxxDO.setSphm(sphm);
            bdcSlHsxxDO.setNsrsbh(sqr.getZjh());
            bdcSlHsxxDO.setSqrlb(sqr.getSqrlb());
            int result = bdcSlHsxxService.updateBdcSlHsxxByXmidAndNsrsbh(bdcSlHsxxDO);
            LOGGER.warn("获取三要素，更新成功申请人核税信息结果{}条，更新参数：{}", result, bdcSlHsxxDO);
        }
    }

    /**
     * @param bdcSlSqrDOList 权利人或义务人集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 合肥需求，特殊处理权利比例
     */
    private void dealQlbl(List<BdcSlSqrDO> bdcSlSqrDOList) {
        //保证共有比例之和为1
        if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
            if (!CommonConstantUtils.GYFS_AFGY.equals(bdcSlSqrDOList.get(0).getGyfs())) {
                //格式化小数
                DecimalFormat df = new DecimalFormat("0.00");
                String qlbl = df.format((float) 1 / bdcSlSqrDOList.size());
                for (int i = 0; i < bdcSlSqrDOList.size(); i++) {
                    BdcSlSqrDO bdcSlSqrDO = bdcSlSqrDOList.get(i);
                    if (i < (bdcSlSqrDOList.size() - 1)) {
                        bdcSlSqrDO.setQlbl(qlbl);
                    } else {
                        bdcSlSqrDO.setQlbl(df.format(1 - (Double.parseDouble(qlbl) * (bdcSlSqrDOList.size() - 1))));
                    }
                }
            }
        }
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  处理主产权人标识,主产权人有且仅有一个
     */
    private void dealZcqrbz(List<BdcSlSqrSwDTO> bdcSlSqrSwDTOS){
        boolean zcqrbz =false;
        if(CollectionUtils.isNotEmpty(bdcSlSqrSwDTOS)){
            for(BdcSlSqrSwDTO bdcSlSqrSwDTO:bdcSlSqrSwDTOS){
                if(bdcSlSqrSwDTO.getSxh() == 1){
                    if(Boolean.FALSE.equals(zcqrbz)){
                        bdcSlSqrSwDTO.setZcqrbz(CommonConstantUtils.SF_S_DM);
                        zcqrbz =true;
                    }else{
                        bdcSlSqrSwDTO.setZcqrbz(CommonConstantUtils.SF_F_DM);
                    }
                }else{
                    bdcSlSqrSwDTO.setZcqrbz(CommonConstantUtils.SF_F_DM);
                }
            }
            if(Boolean.FALSE.equals(zcqrbz)){
                bdcSlSqrSwDTOS.get(0).setZcqrbz(CommonConstantUtils.SF_S_DM);
            }
        }
    }

    /**
     * 处理共同共有情况下的上次取得房屋成本
     */
    private void dealQcqsjsje(List<BdcSlSqrSwDTO> sqrList,double cb,Integer gyfs){
        LOGGER.info("处理dealQcqsjsje开始，参数分别为：{}，{}，{}",sqrList,cb,gyfs);
        if(CommonConstantUtils.GYFS_GTGY.equals(gyfs) || CommonConstantUtils.GYFS_DDSY.equals(gyfs)){
            // 权利人个数
            int count = sqrList.size();
            if(count > 0){
                // 总成本/个数 = 平均值（近似值）
                double avgZxJe = cb/count;
                avgZxJe = (double) Math.round(avgZxJe * 10000) / 10000;
                for(int i=0; i <sqrList.size();i++){
                    sqrList.get(i).setQcqsjsje(avgZxJe);
                    if(i == sqrList.size() - 1){
                        // 除了最后一个的的总成本
                        double besideLastOneTotalCb = avgZxJe*(sqrList.size() - 1);
                        // 总正本 - 除了最后一个的的总成本 = 剩下最后一个的成本
                        double lastCb = cb - besideLastOneTotalCb;
                        lastCb = (double) Math.round(lastCb * 10000) / 10000;
                        sqrList.get(i).setQcqsjsje(lastCb);
                    }
                }
            }
        }else{
            for(int i=0; i <sqrList.size();i++){
                String qlbl = sqrList.get(i).getQlbl();
                double qlblDouble = Double.parseDouble(qlbl);
                // 是百分比的权力比例
                if(qlblDouble > 1){
                    qlblDouble = qlblDouble/100;
                }
                double currentCb  = (double) Math.round((cb*qlblDouble) * 10000) / 10000;
                sqrList.get(i).setQcqsjsje(currentCb);
            }
        }
    }

    /**
     * @param bdcSlSqrDOList 权利人或义务人集合
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 处理共有人标记
     * 如果共有方式为单独所有/其他共有，则直接推送0（0不是共有人；1 共有人）；
     * 如果共有方式为共同共有，则将其中一个推送0，其余则推送1；
     * 如果共有方式为按份共有，则比例大的推送0，其余推送1
     */
    private void dealGyrbj(List<BdcSlSqrDO> bdcSlSqrDOList) {
        if (CollectionUtils.isNotEmpty(bdcSlSqrDOList) && StringUtils.isEmpty(bdcSlSqrDOList.get(0).getGyrbj())) {
            if (CommonConstantUtils.GYFS_DDSY.equals(bdcSlSqrDOList.get(0).getGyfs()) ||CommonConstantUtils.GYFS_QTGY.equals(bdcSlSqrDOList.get(0).getGyfs())) {
                // 单独共有,其他共有
                bdcSlSqrDOList.get(0).setGyrbj(CommonConstantUtils.GYRBJ_CQR);
                String qlbl = CommonUtil.getQlbrStr(bdcSlSqrDOList.get(0).getQlbl());
                bdcSlSqrDOList.get(0).setQlbl(qlbl);
            } else if (CommonConstantUtils.GYFS_GTGY.equals(bdcSlSqrDOList.get(0).getGyfs())) {
                // 共同共有
                orderQlbl(bdcSlSqrDOList);
            } else if (CommonConstantUtils.GYFS_AFGY.equals(bdcSlSqrDOList.get(0).getGyfs())) {
                // 按份共有时 取权利比例大者 赋值为产权人，其余为共有人
                // 先降序排序，再取第一个为产权人，其余为共有人
                try {
                    for (int i = 0; i < bdcSlSqrDOList.size(); i++) {
                        for (int j = bdcSlSqrDOList.size() - 1; j > i; j--) {
                            float qlbl1 = CommonUtil.getQlbr(bdcSlSqrDOList.get(j).getQlbl());
                            float qlbl2 = CommonUtil.getQlbr(bdcSlSqrDOList.get(j - 1).getQlbl());
                            if (qlbl1 >= qlbl2) {
                                //互换位置
                                BdcSlSqrDO bdcSlSqrDO = bdcSlSqrDOList.get(j);
                                bdcSlSqrDOList.set(j, bdcSlSqrDOList.get(j - 1));
                                bdcSlSqrDOList.set(j - 1, bdcSlSqrDO);
                            }
                        }
                    }
                    // 排序后 权利比例大的在前面 小的在后面，取第一个赋值产权人即可
                    orderQlbl(bdcSlSqrDOList);
                } catch (Exception e) {
                    LOGGER.error("按份共有的情况下处理gyrbj出错，请检查当前流程下的qlbl,xmid:{}" ,bdcSlSqrDOList.get(0).getXmid(), e);
                }
            }

            // 转换qlbl为小数
        }
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  特殊处理推送登记业务信息
     */
    private void tsclTsDjYwxx(TsswDataDTO tsswDataDTO,String gzlslid){
        if(tsswDataDTO != null){
            //申请人模块
            if(CollectionUtils.isNotEmpty(tsswDataDTO.getSqrList())){
                for(BdcSlSqrDO bdcSlSqrDO:tsswDataDTO.getSqrList()){
                    //申请人备注默认读取坐落字段
                    if(tsswDataDTO.getBdcSlXm() != null){
                        bdcSlSqrDO.setBz(tsswDataDTO.getBdcSlXm().getZl());
                    }
                }
            }
            //基本信息模块
            BdcSlJbxxDO bdcSlJbxxDO =tsswDataDTO.getBdcSlJbxx();
            if(bdcSlJbxxDO !=null &&StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_BB, dataversion)) {
                //qxdm默认赋值
                bdcSlJbxxDO.setQxdm("340300");
            }
            //BUG22893 所在层推送fw_hs 中qsmyc,没值读取所在名义层
            if(tsswDataDTO.getBdcFdcqDO() != null) {
                Integer szc = this.getFwSzc(tsswDataDTO.getBdcSlXm(), tsswDataDTO.getBdcFdcqDO().getSzmyc());
                tsswDataDTO.getBdcFdcqDO().setSzc(szc);
            }
            //特殊字段
            //土地标志
            if(tsswDataDTO.getBdcSlXm() !=null &&CommonConstantUtils.DYBDCLX_CTD.equals(tsswDataDTO.getBdcSlXm().getBdclx())){
                tsswDataDTO.setTdbz(CommonConstantUtils.SF_S_DM.toString());
            }else{
                tsswDataDTO.setTdbz(CommonConstantUtils.SF_F_DM.toString());
            }
            //组织推送税务收件材料信息
            getDjTsswSjcl(tsswDataDTO,gzlslid);
        }

    }

    private void orderQlbl(List<BdcSlSqrDO> bdcSlSqrDOList) {
        for (int i = 0; i < bdcSlSqrDOList.size(); i++) {
            if (i == 0) {
                bdcSlSqrDOList.get(i).setGyrbj(CommonConstantUtils.GYRBJ_CQR);
            } else {
                bdcSlSqrDOList.get(i).setGyrbj(CommonConstantUtils.GYRBJ_GYR);
            }
            String qlbl = CommonUtil.getQlbrStr(bdcSlSqrDOList.get(i).getQlbl());
            bdcSlSqrDOList.get(i).setQlbl(qlbl);
        }
    }

    /**
     * @param xmid 项目id
     * @param querySwxxHsxxDTOList  税务信息
     * @param sqrlb 申请人类别
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理买方或者卖方税务信息
     */
    private BdcSwxxDTO dealHsxxMx(List<QuerySwxxHsxxDTO> querySwxxHsxxDTOList, String xmid,String sqrlb) {
        BdcSwxxDTO bdcSwxxDTO =new BdcSwxxDTO();
        BdcSlHsxxDO bdcSlHsxxDO =new BdcSlHsxxDO();
        if(StringUtils.isNotBlank(xmid) &&StringUtils.isNotBlank(sqrlb)){
            BdcSlHsxxDO bdcSlHsxxQO =new BdcSlHsxxDO();
            bdcSlHsxxQO.setXmid(xmid);
            bdcSlHsxxQO.setSqrlb(sqrlb);
            List<BdcSlHsxxDO> bdcSlHsxxDOList =bdcSlHsxxService.listBdcSlHsxx(bdcSlHsxxQO);
            if(CollectionUtils.isNotEmpty(bdcSlHsxxDOList)){
                bdcSlHsxxDO =bdcSlHsxxDOList.get(0);

            }
        }
        List<BdcSlHsxxMxDO> bdcSlHsxxMxDOList = new ArrayList<>();
        //应纳税额合计
        Double ynsehj = 0.00;
        //减免税额合计
        Double jmsehj = 0.00;
        //实际应征合计
        Double sjyzhj = 0.00;
        if (CollectionUtils.isNotEmpty(querySwxxHsxxDTOList)) {
            BdcSlHsxxDO hsxx = querySwxxHsxxDTOList.get(0).getBdcSlHsxxDO();
            if (null != hsxx) {
                dozerMapperF.map(hsxx, bdcSlHsxxDO);
                if(StringUtils.isBlank(bdcSlHsxxDO.getHsxxid())) {
                    bdcSlHsxxDO.setHsxxid(UUIDGenerator.generate16());
                }
                for (QuerySwxxHsxxDTO querySwxxHsxxDTO : querySwxxHsxxDTOList) {
                    if (CollectionUtils.isNotEmpty(querySwxxHsxxDTO.getBdcSlHsxxMxDOList())) {
                        for (BdcSlHsxxMxDO bdcSlHsxxMxDO : querySwxxHsxxDTO.getBdcSlHsxxMxDOList()) {
                            bdcSlHsxxMxDO.setHsxxmxid(UUIDGenerator.generate16());
                            bdcSlHsxxMxDO.setHsxxid(bdcSlHsxxDO.getHsxxid());

                            // 以下三个字段 不再计算，直接取原接口数据即可
                            if (null != bdcSlHsxxMxDO.getYnse()) {
                                ynsehj = NumberUtil.formatDigit(ynsehj +bdcSlHsxxMxDO.getYnse(),2);
                            } else {
                                bdcSlHsxxMxDO.setYnse(0.00);
                            }
                            if (null != bdcSlHsxxMxDO.getJmse()) {
                                jmsehj = NumberUtil.formatDigit(jmsehj + bdcSlHsxxMxDO.getJmse(),2);
                            } else {
                                bdcSlHsxxMxDO.setJmse(0.00);
                            }
                            if (null != bdcSlHsxxMxDO.getSjnse()) {
                                sjyzhj = NumberUtil.formatDigit(sjyzhj + bdcSlHsxxMxDO.getSjnse(),2);
                            } else {
                                bdcSlHsxxMxDO.setSjnse(0.00);
                            }
                        }
                        bdcSlHsxxMxDOList.addAll(querySwxxHsxxDTO.getBdcSlHsxxMxDOList());
                    }
                }
                if(Boolean.TRUE.equals(jsswhj)){
                    bdcSlHsxxDO.setSjyzhj(NumberUtil.formatDigit(sjyzhj, 2));
                    bdcSlHsxxDO.setYnsehj(NumberUtil.formatDigit(ynsehj, 2));
                    bdcSlHsxxDO.setJmsehj(NumberUtil.formatDigit(jmsehj, 2));
                }

                bdcSlHsxxDO.setXmid(xmid);

                //保存接受税务的时间
                if(Objects.isNull(bdcSlHsxxDO.getJssksj())){
                    bdcSlHsxxDO.setJssksj(new Date());
                }
                bdcSwxxDTO.setBdcSlHsxxDO(bdcSlHsxxDO);

                if (CollectionUtils.isNotEmpty(bdcSlHsxxMxDOList)) {
                    bdcSwxxDTO.setBdcSlHsxxMxDOList(bdcSlHsxxMxDOList);
                }
            }
        }
        return bdcSwxxDTO;
    }

    /**
     * @param xmid 不动产项目ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 登记交易金额
     * @description 获取登记库的交易价格信息
     */
    private Double getDjJyje(String xmid){
        if(StringUtils.isNotBlank(xmid)){
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
            if(bdcQl instanceof BdcFdcqDO){
                BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                return bdcFdcqDO.getJyjg();
            }
            if(bdcQl instanceof BdcYgDO){
                BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
                return bdcYgDO.getJyje();
            }
        }
        return null;
    }

    /**
     * 单个查询税务
     * @param bdcSwxxQO
     * @return
     */
    private QuerySwxxResponseDTO requestQueryExchange(BdcSwxxQO bdcSwxxQO) {
        if (StringUtils.isBlank(bdcSwxxQO.getFwuuid())){
            bdcSwxxQO.setFwuuid(this.getFwuuidBySlbh(bdcSwxxQO.getSlbh()));
        }
        // 判断是否是土地
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(bdcSwxxQO.getSlbh());
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            if (bdcXmDO.getBdclx() == 1) {
                bdcSwxxQO.setTdbz("1");
            }
        }
        LOGGER.info("税务查询接口请求参数：{}", bdcSwxxQO);
        Object response = exchangeInterfaceFeignService.requestInterface(bdcSwxxQO.getBeanName(), bdcSwxxQO);
        if (response != null) {
            QuerySwxxResponseDTO responseDTO = JSONObject.parseObject(JSONObject.toJSONString(response), QuerySwxxResponseDTO.class);
            if (responseDTO != null && CommonConstantUtils.RESPONSE_SUCCESS.equals(responseDTO.getResponseCode())) {
                LOGGER.info("税务查询接口调用成功！受理编号：{},响应内容：{}", bdcSwxxQO.getSlbh(), responseDTO);
                //处理返回信息
                if(!StringUtils.equals(CommonConstantUtils.SW_GXLX_BGX,bdcSwxxQO.getGxlx())) {
                    // 申报提醒函上传
                    uploadSbtxh(responseDTO,bdcSwxxQO.getSlbh());
                    handleQuerySwxxResponse(responseDTO, bdcSwxxQO.getXmid(), bdcSwxxQO.getGxlx());
                }
            }
            return responseDTO;
        }
        return null;
    }

    /**
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description  税务信息批量查询
     */
    private QuerySwxxResponseDTO plRequestQueryExchange(BdcSwxxQO bdcSwxxQO) {
        QuerySwxxResponseDTO responseDTO = new QuerySwxxResponseDTO();
        // 获取bdc_sl_jbxx中的fwuuid
        String fwuuids = this.getFwuuidBySlbh(bdcSwxxQO.getSlbh());
        if (StringUtils.isNotBlank(fwuuids)){
            // 批量流程的fwuuid由逗号拼接，取其中一个fwuuid
            String fwuuid = fwuuids.split(",")[0];
            bdcSwxxQO.setFwuuid(fwuuid);
            // mergebz并案业务标识传1，非并案业务该字段可不传或为0
            bdcSwxxQO.setMergebz("1");
            // 判断是否是土地
            bdcSwxxQO.setTdbz(this.getTdbz(bdcSwxxQO.getSlbh(), null) ? "1" : "0");
            LOGGER.info("税务批量查询接口请求参数：{}", bdcSwxxQO);
            Object response = exchangeInterfaceFeignService.requestInterface(bdcSwxxQO.getBeanName(), bdcSwxxQO);
            if (response != null) {
                responseDTO = JSONObject.parseObject(JSONObject.toJSONString(response), QuerySwxxResponseDTO.class);
                if (responseDTO != null && CommonConstantUtils.RESPONSE_SUCCESS.equals(responseDTO.getResponseCode())) {
                    LOGGER.info("税务批量查询接口调用成功！受理编号：{},响应内容：{}", bdcSwxxQO.getSlbh(), responseDTO);
                    // 处理返回信息
                    if(!StringUtils.equals(CommonConstantUtils.SW_GXLX_BGX,bdcSwxxQO.getGxlx())) {
                        BdcXmQO bdcXmQO = new BdcXmQO();
                        bdcXmQO.setSlbh(bdcSwxxQO.getSlbh());
                        bdcXmQO.setSplys(sssplyList);
                        // 查询涉税的项目信息
                        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
                        if (CollectionUtils.isNotEmpty(bdcXmDOList)){
                            // 申报提醒函上传
                            uploadSbtxh(responseDTO,bdcSwxxQO.getSlbh());
                            for (BdcXmDO bdcXmDO:bdcXmDOList){
                                handleQuerySwxxResponse(responseDTO, bdcXmDO.getXmid(), bdcSwxxQO.getGxlx());
                            }
                        }
                    }
                }
            }
        }
        return responseDTO;
    }

    /**
     * 是否是土地项目
     * @param slbh 受理编号
     * @param xmidList
     * @return 土地项目返回true
     */
    private Boolean getTdbz(String slbh,List<String> xmidList){
        // 判断是否是土地
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(slbh);
        if (CollectionUtils.isNotEmpty(xmidList)){
            bdcXmQO.setXmidList(xmidList);
        }
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            if (CommonConstantUtils.BDCLX_TD_DM.equals(bdcXmDO.getBdclx().toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 单个取消作废
     * @param xmid 项目id
     * @param slbh 受理编号
     * @param beanName
     * @return
     */
    private Object requestQxzfSwxxByExchange(String xmid, String slbh, String beanName) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("xmid", xmid);
        paramMap.put("slbh", slbh);
        String fwuuid = this.getFwuuidBySlbh(slbh);
        paramMap.put("fwuuid", fwuuid);
        // 判断是否是土地
        String tdbz = this.getTdbz(slbh, null) ? "1" : "0";
        paramMap.put("tdbz", tdbz);
        LOGGER.info("开始调用取消作废接口,请求参数为：{}", JSON.toJSONString(paramMap));
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
        if (response != null) {
            LOGGER.info("调用取消作废接口成功,受理编号：{},接口返回内容:{}", slbh,response);
            QuerySwxxResponseDTO responseDTO = JSONObject.parseObject(JSONObject.toJSONString(response), QuerySwxxResponseDTO.class);
            if (responseDTO != null && CommonConstantUtils.RESPONSE_SUCCESS.equals(responseDTO.getResponseCode())) {
                //删除已有的核税信息
                bdcSlHsxxService.deleteSwxx(xmid);
                // 删除申报提醒函附件
                deleteSbtxhFj(xmid);
            }
        }
        return response;
    }

    /**
     * @param slbh 受理编号
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 批量取消作废税务信息 -合肥德宽版税务系统
     * @date : 2022/9/26
     */
    private Object plRequestQxzfSwxxByExchange(String slbh, String beanName) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("slbh", slbh);
        String fwuuids = this.getFwuuidBySlbh(slbh);
        if (StringUtils.isBlank(fwuuids)){
            throw new AppException("获取税务fwuuid异常，未获取到受理基本信息，受理编号："+ slbh);
        }
        paramMap.put("fwuuid", fwuuids.split(",")[0]);
        // 作废并案所有业务时，查询“1”，单独作废业务时，可不传或者传“0”
        paramMap.put("mergebz","1");
        // 判断是否是土地
        String tdbz = this.getTdbz(slbh, null) ? "1" : "0";
        paramMap.put("tdbz", tdbz);
        LOGGER.info("开始调用批量取消作废接口,请求参数为：{}", JSON.toJSONString(paramMap));
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
        if (response != null) {
            LOGGER.info("调用批量取消作废接口成功,受理编号：{},接口返回内容:{}", slbh,response);
            QuerySwxxResponseDTO responseDTO = JSONObject.parseObject(JSONObject.toJSONString(response), QuerySwxxResponseDTO.class);
            if (responseDTO != null && CommonConstantUtils.RESPONSE_SUCCESS.equals(responseDTO.getResponseCode())) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setSlbh(slbh);
                bdcXmQO.setSplys(sssplyList);
                // 查询涉税的项目信息
                List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)){
                    for(BdcXmDO bdcXmDO:bdcXmDOList){
                        //删除已有的核税信息
                        bdcSlHsxxService.deleteSwxx(bdcXmDO.getXmid());
                        // 删除申报提醒函附件
                        deleteSbtxhFj(bdcXmDO.getXmid());
                    }
                }
            }
        }
        return response;
    }

    /**
     * @param receiveSwxxRequestDTO 需要更新的税务信息集合
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 接受德宽提供的税务信息
     */
    @Override
    public Map insertOrUpdateSwxx(ReceiveSwxxRequestDTO receiveSwxxRequestDTO){
        Map map = new HashMap();
        map.put("status", CommonConstantUtils.RESPONSE_FAIL);
        map.put("msg","请求失败");

        if (receiveSwxxRequestDTO != null && StringUtils.isNotBlank(receiveSwxxRequestDTO.getWszt())
                && CollectionUtils.isNotEmpty(receiveSwxxRequestDTO.getHsxxList())
                && StringUtils.isNotBlank(receiveSwxxRequestDTO.getSlbh())) {
            map = dealReceiveSwxxRequest(receiveSwxxRequestDTO);
        }else{
            map.put("msg","请求失败,参数异常");
            return map;
        }
        return map;
    }

    // 根据外网受理编号更新完税状态
    @Override
    public CommonResponse jsDsfJszt(ReceiveSwxxRequestDTO receiveSwxxRequestDTO) {
        if(StringUtils.isAnyBlank(receiveSwxxRequestDTO.getWwslbh(), receiveSwxxRequestDTO.getWszt())){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数，外网受理编号或完税状态");
        }
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setSpxtywh(receiveSwxxRequestDTO.getWwslbh());
        List<BdcSlXmDO> bdcSlXmDOList = this.bdcSlXmService.listBdcSlXm(bdcSlXmQO);
        if(CollectionUtils.isEmpty(bdcSlXmDOList)){
            throw new AppException(ErrorCode.CUSTOM, "未获取到不动产受理项目信息，外网受理编号为：" +receiveSwxxRequestDTO.getWwslbh());
        }
        BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxService.queryBdcSlJbxxByJbxxid(bdcSlXmDOList.get(0).getJbxxid());
        if(Objects.isNull(bdcSlJbxxDO)){
            throw new AppException(ErrorCode.CUSTOM, "未获取到不动产基本信息，外网受理编号为：" +receiveSwxxRequestDTO.getWwslbh());
        }
        String gzlslid = bdcSlJbxxDO.getGzlslid();
        Integer wszt = Integer.parseInt(receiveSwxxRequestDTO.getWszt());
        try{
            this.bdcSlHsxxService.updateBatchWszt(wszt, gzlslid);
        }catch(Exception e) {
            LOGGER.info("更新缴税状态失败，外网受理编号为：" + receiveSwxxRequestDTO.getWwslbh()
                    + "完税状态：" + receiveSwxxRequestDTO.getWszt() + ",错误信息：" + e.getMessage());
            throw new AppException("更新缴税状态失败!");
        }
        if(CollectionUtils.isNotEmpty(yjsbjgzldyidList) && yjsbjgzldyidList.contains(bdcSlJbxxDO.getGzldyid())&&CommonConstantUtils.WSZT_YWS.equals(wszt.toString())){
            BdcTsDjxxRequestDTO bdcTsDjxxRequestDTO = new BdcTsDjxxRequestDTO();
            bdcTsDjxxRequestDTO.setGzlslid(gzlslid);
            bdcTsDjxxRequestDTO.setJbxxid(bdcSlJbxxDO.getJbxxid());
            BdcYcslPzDTO bdcYcslPzDTO = new BdcYcslPzDTO();
            bdcYcslPzDTO.setAutoComplete(true);
            bdcYcslPzDTO.setGyslbh(true);
            bdcYcslPzDTO.setAutoTurn(false);
            bdcTsDjxxRequestDTO.setBdcYcslPzDTO(bdcYcslPzDTO);
            try {
                bdcYcslManageService.initTsBdcDjxx(bdcTsDjxxRequestDTO);
            } catch (Exception e) {
                LOGGER.error("登记流程创建失败，工作流实例id：{},jbxxid:{},错误信息:{}", gzlslid,bdcSlJbxxDO.getJbxxid(),e.getMessage());
            }
        }
        return CommonResponse.ok();
    }

    @Override
    public Object getAndHandleWsxx(String gzlslid) throws IOException {
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("缺失参数工作流实例ID");
        }
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(bdcSlXmDOList)){
            throw new MissingArgumentException("未获取到项目信息");
        }
        bdcSlXmDOList = bdcSlXmDOList.stream().filter(t-> ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, t.getQllx())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(bdcSlXmDOList)){
            throw new MissingArgumentException("未获取到房屋项目信息");
        }
        List<String> xmids = bdcSlXmDOList.stream().map(BdcSlXmDO::getXmid).collect(Collectors.toList());
        // 查询合同备案号
        BaseQO baseQO =new BaseQO();
        baseQO.setIds(xmids);
        List<BdcSlJyxxDO> bdcSlJyxxDOList = this.bdcSlJyxxService.listBdcSlJyxxByXmids(baseQO);
        if(CollectionUtils.isEmpty(bdcSlJyxxDOList)){
            throw new MissingArgumentException("未获取到合同备案号信息");
        }

        Map<String, String> param = new HashMap(2);
        param.put("htbh", bdcSlJyxxDOList.get(0).getHtbh());
        LOGGER.info("调用完税信息接口 swCxxx_cz , 请求参数：{}", param);
        Integer wszt = this.getSpxx("swCxxx_cz", param, gzlslid, bdcSlJyxxDOList.get(0).getXmid());
        if(Objects.isNull(wszt)){
            // 调用获取税票未获取税票信息时，调用【A021】契税联系单获取 接口
            wszt = this.getQslxdSpxx(gzlslid, bdcSlJyxxDOList.get(0).getHtbh(), bdcSlJyxxDOList.get(0).getXmid());
        }
        return wszt;
    }

    // 获取税票信息，当存在税票信息时，wszt = 1 (已完税)， wszt = null(未获取到税票信息内容)，获取到税票信息后，上传大云附件材料。
    private Integer getSpxx(String beanName, Map param, String gzlslid, String xmid) throws IOException {
        Integer wszt = null;
        LOGGER.info("调用完税信息接口{} , 请求参数：{}", beanName, param);
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, param);
        if(Objects.nonNull(response)) {
            // 完税状态标识符，用于通知页面更新完税状态
            SwxxResponseDTO swxxResponseDTO = JSONObject.parseObject(JSONObject.toJSONString(response), SwxxResponseDTO.class);
            if (CollectionUtils.isNotEmpty(swxxResponseDTO.getSwxxList())) {
                int i = 1;
                for (DzpxxDTO dzpxxDTO : swxxResponseDTO.getSwxxList()) {
                    // 上传税票信息至文件中心
                    String fileName = String.format("契税完税证明（%s）", i++);
                    this.uploadInvoiceToFolder(gzlslid, dzpxxDTO.getBase64Str(), fileName, xmid);
                }
                this.updateWsztByGzlslid(gzlslid);
                wszt = 1;
            }
        }
        return wszt;
    }

    // 调用【A021】契税联系单接口，获取税票信息
    private Integer getQslxdSpxx(String gzlslid, String htbh, String xmid){
        Integer wszt = null;
        BdcXmDO bdcXmDO = this.bdcXmFeignService.queryBdcXmByXmid(xmid);
        if(Objects.isNull(bdcXmDO)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到项目信息");
        }
        String bdcdyh = bdcXmDO.getBdcdyh();
        if(StringUtils.isBlank(bdcdyh)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产单元号");
        }
        // 组织接口请求参数
        YrbQslxdhqTaxbizml yrbQslxdhqTaxbizml = new YrbQslxdhqTaxbizml();
        {
            YrbQslxdhqRequest yrbQslxdhqRequest = new YrbQslxdhqRequest();
            // 数据归属地区，要求不动产单元号前6位，在对照税务字典信息
            yrbQslxdhqRequest.setSJGSDQ(bdcdyh.substring(0, 6));
            yrbQslxdhqRequest.setHTBH(htbh);
            yrbQslxdhqRequest.setSJRQ(DateUtils.formateDateToString(new Date(),DateUtils.DATE_FORMAT_2));
            yrbQslxdhqRequest.setSJBH(htbh);
            if(bdcGzyzFeignService.checkSpfLc(bdcXmDO.getGzldyid())){
                yrbQslxdhqRequest.setZLFCLFBZ("1");
            }else{
                yrbQslxdhqRequest.setZLFCLFBZ("2");
            }
            // 纳税人识别号，获取权利人证件号
            yrbQslxdhqRequest.setNSRSBH(this.getQlrZjhByXmid(xmid));
            yrbQslxdhqTaxbizml.setFcjyqslxdlist(yrbQslxdhqRequest);
        }
        LOGGER.info("契税完税凭证接口请求参数：{}", yrbQslxdhqTaxbizml);

        Object response = exchangeInterfaceFeignService.swRequestInterface("sw_qslxdhq", yrbQslxdhqTaxbizml);
        if(Objects.nonNull(response)){
            BdcCommonResponse bdcCommonResponse = JSON.parseObject(JSON.toJSONString(response), BdcCommonResponse.class);
            if(StringUtils.equals("1",bdcCommonResponse.getCode())){
                YrbQslxdhqDTO yrbQslxdhqDTO = JSON.parseObject(JSON.toJSONString(bdcCommonResponse.getData()), YrbQslxdhqDTO.class);
                LOGGER.info("契税完税凭证接口调用成功！合同编号：{},响应内容：{}", htbh, yrbQslxdhqDTO);

                if(Objects.nonNull(yrbQslxdhqDTO) && Objects.nonNull(yrbQslxdhqDTO.getFcjyqslxdlist())
                        && CollectionUtils.isNotEmpty(yrbQslxdhqDTO.getFcjyqslxdlist().getQslxdfjxxlist())){
                    //附件信息
                    List<YrbFcjysbxxFjxxResponse> qswsfjxxlist  = yrbQslxdhqDTO.getFcjyqslxdlist().getQslxdfjxxlist();
                    int i= 0;
                    for (YrbFcjysbxxFjxxResponse yrbFcjysbxxFjxxResponse : qswsfjxxlist) {
                        try {
                            String fileName = String.format("契税完税证明（%s）", i++);
                            this.uploadInvoiceToFolder(gzlslid, yrbFcjysbxxFjxxResponse.getWjsj(), fileName, xmid);
                        } catch (IOException e) {
                            LOGGER.error("当前流程{}上传契税完税凭证附件失败", gzlslid, e);
                        }
                    }
                    this.updateWsztByGzlslid(gzlslid);
                    wszt = 1;
                }
            }
        }
        return wszt;
    }

    // 根据项目id获取权利人证件号
    private String getQlrZjhByXmid(String xmid){
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> bdcQlrDOList = this.bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        if(CollectionUtils.isEmpty(bdcQlrDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少权利人信息");
        }
        return bdcQlrDOList.get(0).getZjh();
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 推送附件材料到德宽税务
     * @date : 2020/10/27 10:48
     */
    @Override
    public void tsFjcl(String gzlslid) {
        TsswDataDTO tsswDataDTO = new TsswDataDTO();
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (bdcSlJbxxDO == null) {
            throw new AppException("工作流实例ID：" + gzlslid + "未找到相应的受理基本信息");
        }
        BdcXmQO bdcXmQO =new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOList) &&CommonConstantUtils.DYBDCLX_CTD.equals(bdcXmDOList.get(0).getBdclx())){
            tsswDataDTO.setTdbz(CommonConstantUtils.SF_S_DM.toString());
        }else{
            tsswDataDTO.setTdbz(CommonConstantUtils.SF_F_DM.toString());
        }
        tsswDataDTO.setSlbh(bdcSlJbxxDO.getSlbh());
        tsswDataDTO.setFwuuid(bdcSlJbxxDO.getFwuuid());
        tsswDataDTO.setXmid("");
        getDjTsswSjcl(tsswDataDTO,gzlslid);
        LOGGER.info("推送税务附件材料传参{}",JSON.toJSONString(tsswDataDTO));
        exchangeInterfaceFeignService.requestInterface("swTsFjclPl_dk",JSON.parseObject(JSON.toJSONString(tsswDataDTO)));
    }

    /**
     * 更新完税状态
     */
    private void updateWsztByGzlslid(String gzlslid){
        this.bdcSlHsxxService.updateBatchWszt(Integer.valueOf(CommonConstantUtils.WSZT_YWS),gzlslid);
    }
    /**
     * 上传PDF格式的税票信息至流程的“税票”文件夹下
     */
    private void uploadInvoiceToFolder(String gzlslid, String base64Content, String fileName,String xmid) throws IOException {
        if(StringUtils.isNotBlank(base64Content)) {
            BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(gzlslid, "", fileName, "契税完税证明", CommonConstantUtils.WJHZ_PDF);
            if (!base64Content.startsWith("data:")) {
                base64Content = CommonConstantUtils.BASE64_QZ_PDF + base64Content;
            }
            bdcPdfDTO.setBase64str(base64Content);
            bdcPdfDTO.setXmid(xmid);
            bdcUploadFileUtils.uploadBase64File(bdcPdfDTO);
        }
    }

    /**
     * @param receiveSwxxRequestDTO 税务信息推送参数
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 处理德宽推过来的税务信息
     */
    @Transactional
    public Map dealReceiveSwxxRequest(ReceiveSwxxRequestDTO receiveSwxxRequestDTO) {
        LOGGER.info("接收德宽税务信息实体为：{}",JSONObject.toJSONString(receiveSwxxRequestDTO));
        Map map = new HashMap();
        map.put("status",CommonConstantUtils.RESPONSE_FAIL);
        map.put("msg","请求失败");

        // 每个件只会推送一次 这里不考虑批量多个hsxx实体的情况
        if(receiveSwxxRequestDTO.getHsxxList().get(0).getBdcSlHsxxDO() == null ){
            map.put("msg","请求失败，接收德宽税务信息集合不存在核税信息实体");
            return map;
        }

        if(StringUtils.isBlank(receiveSwxxRequestDTO.getHsxxList().get(0).getBdcSlHsxxDO().getNsrsbh())){
            map.put("msg","请求失败，接收德宽税务信息核税信息实体中纳税人识别号为空");
            return map;
        }

        List<QuerySwxxHsxxDTO> swxxHsxxDTOList = receiveSwxxRequestDTO.getHsxxList();

        // 获取最外层的完税状态，并转成int
        String wsztStr = receiveSwxxRequestDTO.getWszt();
        int wszt = 0;
        if (StringUtils.isNotEmpty(wsztStr)) {
            wszt = Integer.parseInt(wsztStr);
        }

        String slbh = receiveSwxxRequestDTO.getSlbh();
        String nsrsbh = receiveSwxxRequestDTO.getHsxxList().get(0).getBdcSlHsxxDO().getNsrsbh();
        String xmid = "";

        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxBySlbh(slbh,"");
        if(bdcSlJbxxDO != null){
            String jbxxid = bdcSlJbxxDO.getJbxxid();
            BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
            bdcSlXmQO.setJbxxid(jbxxid);
            List<BdcSlXmDO> listSlxm = bdcSlXmService.listBdcSlXm(bdcSlXmQO);
            if(CollectionUtils.isNotEmpty(listSlxm)){
                for(BdcSlXmDO bdcSlXmDO : listSlxm){
                    BdcSlSqrQO bdcSlSqrQO = new BdcSlSqrQO();
                    bdcSlSqrQO.setZjh(nsrsbh);
                    bdcSlSqrQO.setXmid(bdcSlXmDO.getXmid());
                    List<BdcSlSqrDO> bdcSlSqrDOS = bdcSlSqrService.queryBdcSlSqrBySqrQO(bdcSlSqrQO);
                    // 根据slbh和nsrsbh找到xmid
                    if(CollectionUtils.isNotEmpty(bdcSlSqrDOS)){
                        xmid = bdcSlXmDO.getXmid();
                        break;
                    }
                }
            }
        }
        if(StringUtils.isNotBlank(xmid)){
            return dealHsxxAndMx(swxxHsxxDTOList,xmid,CommonConstantUtils.SW_GXLX_CXSC,wszt,"");
        }else{
            map.put("msg","请求失败,根据slbh和nsrsbh无法找到对应的xmid,"+ slbh + "," + nsrsbh);
            return map;
        }
    }

    /**
     * 处理hsxx和明细
     *
     * @param swxxHsxxDTOList 税务信息集合
     * @param xmid            项目id
     * @param gxlx            更新类型
     * @param wszt            完税状态
     * @param qyjyzsbz        企业简易征收标志
     */
    public Map dealHsxxAndMx(List<QuerySwxxHsxxDTO> swxxHsxxDTOList, String xmid, String gxlx, int wszt, String qyjyzsbz) {
        Map map = new HashMap();
        map.put("status",CommonConstantUtils.RESPONSE_FAIL);
        map.put("msg","请求失败,未能更新税务信息");
        if (CollectionUtils.isNotEmpty(swxxHsxxDTOList)) {
            String fwuuid = getFwuuid(xmid);
            // 根据房源编码查完税消息时，税务没有返回fwuuid，不用过滤
            if (StringUtils.isNotBlank(fwuuid) && StringUtils.isNotBlank(swxxHsxxDTOList.get(0).getBdcSlHsxxDO().getFwuuid())){
                swxxHsxxDTOList = swxxHsxxDTOList.stream().filter(s -> s.getBdcSlHsxxDO().getFwuuid().equals(fwuuid)).collect(Collectors.toList());
            }
            //根据权利人列表分组
            List<QuerySwxxHsxxDTO> qlrSwxxHsxxList = new ArrayList<>();
            List<QuerySwxxHsxxDTO> ywrSwxxHsxxList = new ArrayList<>();
            for (QuerySwxxHsxxDTO querySwxxHsxxDTO : swxxHsxxDTOList) {
                BdcSlHsxxDO bdcSlHsxxDO = querySwxxHsxxDTO.getBdcSlHsxxDO();
                // 为每一个核税信息实体赋值
                bdcSlHsxxDO.setWszt(wszt);
                if(CommonConstantUtils.WSZT_YWS.equals(String.valueOf(wszt))){
                    //已完税同时更新税务表缴库入库状态
                    bdcSlHsxxDO.setYhjkrkzt(CommonConstantUtils.SF_S_DM);
                }
                if (CommonConstantUtils.QLRLB_QLR.equals(bdcSlHsxxDO.getSqrlb())) {
                    qlrSwxxHsxxList.add(querySwxxHsxxDTO);
                } else if (CommonConstantUtils.QLRLB_YWR.equals(bdcSlHsxxDO.getSqrlb())) {
                    ywrSwxxHsxxList.add(querySwxxHsxxDTO);
                }
                //处理税票附件
                if(CollectionUtils.isNotEmpty(querySwxxHsxxDTO.getSwspFjDTOS())){
                    BdcXmQO bdcXmQO =new BdcXmQO();
                    bdcXmQO.setXmid(xmid);
                    List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
                    if(CollectionUtils.isNotEmpty(bdcXmDOList)) {
                        bdcSjclService.saveSwspfj(querySwxxHsxxDTO.getSwspFjDTOS(), bdcXmDOList.get(0).getGzlslid(), querySwxxHsxxDTO.getSwspfjWjjmc());
                    }
                }
            }
            BdcSwxxDTO qlrSwxxDTO =dealHsxxMx(qlrSwxxHsxxList, xmid,CommonConstantUtils.QLRLB_QLR);
            BdcSwxxDTO ywrSwxxDTO =dealHsxxMx(ywrSwxxHsxxList, xmid,CommonConstantUtils.QLRLB_YWR);
            List<BdcSlHsxxDO> bdcSlHsxxDOList = new ArrayList<>();
            List<BdcSlHsxxMxDO> bdcSlHsxxMxDOList = new ArrayList<>();
            if (qlrSwxxDTO != null &&qlrSwxxDTO.getBdcSlHsxxDO() !=null) {
                bdcSlHsxxDOList.add(qlrSwxxDTO.getBdcSlHsxxDO());
                if (CollectionUtils.isNotEmpty(qlrSwxxDTO.getBdcSlHsxxMxDOList())) {
                    bdcSlHsxxMxDOList.addAll(qlrSwxxDTO.getBdcSlHsxxMxDOList());
                }
            }
            if (ywrSwxxDTO != null &&ywrSwxxDTO.getBdcSlHsxxDO() != null) {
                bdcSlHsxxDOList.add(ywrSwxxDTO.getBdcSlHsxxDO());
                if (CollectionUtils.isNotEmpty(ywrSwxxDTO.getBdcSlHsxxMxDOList())) {
                    bdcSlHsxxMxDOList.addAll(ywrSwxxDTO.getBdcSlHsxxMxDOList());
                }
            }
            // 保存企业简易征收标志
            if (StringUtils.isNotBlank(qyjyzsbz) && CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                for (BdcSlHsxxDO bdcSlHsxxDO : bdcSlHsxxDOList) {
                    bdcSlHsxxDO.setQyjyzsbz(Integer.parseInt(qyjyzsbz));
                }
            }
            if(StringUtils.equals(CommonConstantUtils.SW_GXLX_INSERT_UPDATE,gxlx)){
                //查询是否存在税务信息
                BdcSlHsxxDO bdcSlHsxxDO =new BdcSlHsxxDO();
                bdcSlHsxxDO.setXmid(xmid);
                List<BdcSlHsxxDO> yhsxxList = bdcSlHsxxService.listBdcSlHsxx(bdcSlHsxxDO);
                if(CollectionUtils.isNotEmpty(yhsxxList)){
                    //存在税务信息做更新
                    gxlx =CommonConstantUtils.SW_GXLX_UPDATE;
                }else{
                    //不存在直接新增
                    gxlx =CommonConstantUtils.SW_GXLX_CXSC;
                }
            }
            if(StringUtils.equals(CommonConstantUtils.SW_GXLX_CXSC,gxlx)) {
                int countHx = 0;
                countHx =bdcSlHsxxService.recreateHsxx(xmid,bdcSlHsxxDOList,bdcSlHsxxMxDOList);
                if(countHx > 0){
                    map.put("status",CommonConstantUtils.RESPONSE_SUCCESS);
                    map.put("msg","请求成功");
                }else if(countHx == 0){
                    map.put("msg","插入核税信息失败");
                }
            }else if(StringUtils.equals(CommonConstantUtils.SW_GXLX_UPDATE,gxlx)){
                BdcXmQO bdcXmQO =new BdcXmQO();
                bdcXmQO.setXmid(xmid);
                List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
                if(CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    //更新核税信息
                    updateSwsysByNsrbhAndSlbh(bdcSlHsxxDOList,bdcXmDOList.get(0).getSlbh());
                    map.put("status",CommonConstantUtils.RESPONSE_SUCCESS);
                    map.put("msg","请求成功");
                }
            }
        }
        return map;
    }

    /**
     * 根据sply和processInsId判断当前流程是否完税 完税页面要给出相应提示
     *
     * @param processInsId
     * @param sply
     * @return 是否需要提示 boolean
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @Override
    public Object showSpWsXx(String processInsId, String sply) {
        LOGGER.info("判断是否完税参数：{}，{}",processInsId,sply);
        if(StringUtils.isNotBlank(sply) && StringUtils.isNotBlank(processInsId)){
            boolean isYth = CheckWwsqOrYcslUtils.checkIsYcsl(Integer.parseInt(sply));
            if(isYth){
                // 查询流程内的税务是否完税
                String slbh = bdcXmFeignService.querySlbh(processInsId);
                List<BdcSlHsxxDO> listHsxx = bdcSlHsxxService.listBdcSlHsxxBySlbh(slbh);
                if(CollectionUtils.isNotEmpty(listHsxx)){
                    Integer wszt = listHsxx.get(0).getWszt();
                    // 已完税并且是一体化的流程
                    if(wszt == Integer.parseInt(CommonConstantUtils.WSZT_YWS)){
                        return true;
                    }
                }
            }
        }else{
            return false;
        }
        return false;
    }

    @Override
    public Object tshsxx(String gzlslid) {
        if(StringUtils.isAnyBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "推送核税信息时，缺失必要参数：工作实例ID。");
        }
        List<TsswDataDTO> tsswDataDTOList = this.getFdjTsswData(gzlslid, null, true);
        if(CollectionUtils.isEmpty(tsswDataDTOList)){
            throw new MissingArgumentException("未获取到可以推送的计税信息");
        }
        TsswDataDTO tsswDataDTO = tsswDataDTOList.get(0);

        String gzldyid = getLcGzldyid(gzlslid);
        if(StringUtils.isNotBlank(gzldyid)){
            // 判断一窗流程还是登记流程
            if(!isYcslLc(gzldyid)) {
                // 获取登记税务信息
                this.handleDjHsxx(gzlslid, tsswDataDTO);
            }

            // 添加流程标识， 1（商品房）2（存量房）
            if(bdcGzyzService.checkSpfLc(gzldyid)){
                tsswDataDTO.setBs("1");
            }else{
                tsswDataDTO.setBs("2");
            }

        }

        // 添加附记信息
        if(StringUtils.isNotBlank(tsswDataDTO.getXmid())){
            String fj = bdcZsInitFeignService.queryQlqtzkFj(tsswDataDTO.getXmid(), CommonConstantUtils.XT_QLQTZK_FJ_MODE_FJ);
            tsswDataDTO.setFj(fj);
        }

        //处理权利比例
        List<BdcSlSqrDO> bdcSlSqrDOList = new ArrayList<>();
        List<BdcSlSqrDO> qlrList = tsswDataDTO.getSqrList().stream().filter(qlr -> StringUtils.equals(CommonConstantUtils.QLRLB_QLR,qlr.getSqrlb())).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(qlrList)) {
            dealcym(qlrList);
            bdcSlSqrDOList.addAll(qlrList);
        }

        List<BdcSlSqrDO> ywrList = tsswDataDTO.getSqrList().stream().filter(qlr -> StringUtils.equals(CommonConstantUtils.QLRLB_YWR,qlr.getSqrlb())).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(ywrList)) {
            dealcym(ywrList);
            bdcSlSqrDOList.addAll(ywrList);
        }
        tsswDataDTO.setSqrList(bdcSlSqrDOList);

        // 处理婚姻信息和房屋套次
        this.getHyxx(tsswDataDTO);
        //未成年子女信息
        this.getWcnxx(tsswDataDTO);
        //获取原交易信息
        this.getYjyxx(tsswDataDTO);
        //建筑年份截取竣工时间前4位,并判断是否可转换为数字
        if(tsswDataDTO.getBdcSlFwxx() != null &&StringUtils.isNotBlank(tsswDataDTO.getBdcSlFwxx().getJgsj()) &&tsswDataDTO.getBdcSlFwxx().getJgsj().length() >=4){
            String nf =StringUtils.substring(tsswDataDTO.getBdcSlFwxx().getJgsj(),0,4);
            if(NumberUtils.isNumber(nf) && !nf.contains(CommonConstantUtils.ZF_YW_JH)){
                tsswDataDTO.getBdcSlFwxx().setJznf(Integer.parseInt(nf));
            }
        }

        if(LOGGER.isInfoEnabled()){
            LOGGER.info("推送税务信息{}，{}", "rd_tsswxx", JSON.toJSONString(tsswDataDTO));
        }

        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(tsswDataDTO));

        Object response =  exchangeInterfaceFeignService.requestInterface("rd_tsswxx", jsonObject);
        if(LOGGER.isInfoEnabled()){
            LOGGER.info("推送税务信息返回值{}", response);
        }
        return response;
    }

    @Override
    public Integer insertSwsys(List<BdcSlSysxxDO> bdcSlSysxxDOList,String slbh){
        if(CollectionUtils.isEmpty(bdcSlSysxxDOList)){
            throw new AppException("需要保存的三要素集合为空");
        }
        if(StringUtils.isBlank(slbh)){
            throw new AppException("受理编号为空");
        }
        List<BdcSlHsxxDO> bdcSlHsxxDOList = this.bdcSlHsxxService.listBdcSlHsxxByGzlslid("",slbh);
        if(CollectionUtils.isEmpty(bdcSlHsxxDOList)){
            throw new AppException("核税信息为空");
        }
        Map<String,BdcSlHsxxDO> hsxxMap =new HashMap();
        for(BdcSlHsxxDO bdcSlHsxxDO:bdcSlHsxxDOList){
            if(StringUtils.isNotBlank(bdcSlHsxxDO.getSqrlb())) {
                hsxxMap.put(bdcSlHsxxDO.getSqrlb(), bdcSlHsxxDO);
            }
        }
        List<BdcSlSysxxDO> bdcSlSysxxDOS =new ArrayList<>();
        //匹配核税信息
        for(BdcSlSysxxDO bdcSlSysxxDO:bdcSlSysxxDOList){
            if(StringUtils.isNotBlank(bdcSlSysxxDO.getSqrlb()) &&hsxxMap.containsKey(bdcSlSysxxDO.getSqrlb()) &&StringUtils.isNotBlank(bdcSlSysxxDO.getDzsphm())){
                BdcSlSysxxDO querySysxx =bdcSlSysService.queryBdcSlSysxxByDzsphm(bdcSlSysxxDO.getDzsphm());
                if(querySysxx == null ||StringUtils.isBlank(querySysxx.getSysid())) {
                    bdcSlSysxxDO.setSysid(UUIDGenerator.generate16());
                    bdcSlSysxxDO.setHsxxid(hsxxMap.get(bdcSlSysxxDO.getSqrlb()).getHsxxid());
                    bdcSlSysxxDO.setXmid(hsxxMap.get(bdcSlSysxxDO.getSqrlb()).getXmid());
                    bdcSlSysxxDOS.add(bdcSlSysxxDO);
                }
            }else{
                throw  new AppException("查询三要素数据不全,申请人类别或电子税票号码有误");
            }
        }
        if(CollectionUtils.isNotEmpty(bdcSlSysxxDOS)) {
            return entityMapper.insertBatchSelective(bdcSlSysxxDOS);
        }
        return 0;


    }

    @Override
    public List<BdcSlSysxxDO> listSysxxBySlbh(String slbh) {
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException("缺失受理编号");
        }
        return bdcSlSysService.listBdcSlSysxx("", slbh, "");


    }

    /**
     * @param gzlslid
     * @param beanName
     * @param xmid
     * @param fwlx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 一人办件税务信息
     * @date : 2022/8/8 9:31
     */
    @Override
    public Object getYrbjSwxx(String gzlslid, String beanName, String xmid, String fwlx) {
        if (StringUtils.isAnyBlank(gzlslid, beanName)) {
            throw new MissingArgumentException(messageProvider.getMessage("推送计税信息时，缺失必要参数：工作实例ID或接口标识符。"));
        }
        List<TsswDataDTO> tsswDataDTOList = getFdjTsswData(gzlslid, null, null);
        if (CollectionUtils.isEmpty(tsswDataDTOList)) {
            throw new MissingArgumentException("未获取到可以推送的计税信息");
        }
        TsswDataDTO tsswDataDTO = tsswDataDTOList.get(0);
        // 项目id不为空时，获取对应项目id的税务信息
        if (StringUtils.isNotBlank(xmid)) {
            for (TsswDataDTO tsswData : tsswDataDTOList) {
                if (tsswData.getXmid().equals(xmid)) {
                    tsswDataDTO = tsswData;
                }
            }
        }
        // 设置数据归属地区 和 录入人员代码信息
        tsswDataDTO.setSjgsdq(this.getSwSjgsdq(tsswDataDTO.getBdcSlJbxx(), null, tsswDataDTO.getBdcSlFwxx()));
        if(StringUtils.isNotBlank(lrrdm)){
            tsswDataDTO.setLrrdm(lrrdm);
        }else{
            tsswDataDTO.setLrrdm(tsswDataDTO.getBdcSlJbxx().getSlr());
        }

        // 根据合同签订时间、进行字典值对照设契税权属转移对象
        if(StringUtils.equals(fwlx,"1")){ // 增量房
            this.dealQsqszydxDmYrb(tsswDataDTO, "DSF_ZD_QSZYDX_ZLF");
        }else{ // 存量房
            this.dealQsqszydxDmYrb(tsswDataDTO, "DSF_ZD_QSZYDX");
        }

        // 根据合同签订时间、进行字典值对照设置权属转移用途
        this.dealQszyytDm(tsswDataDTO);

        //处理是商品房还是存量房
        String gzldyid = getLcGzldyid(gzlslid);

        if (StringUtils.isNotBlank(gzldyid)) {
            // 判断一窗流程还是登记流程
            if (!isYcslLc(gzldyid)) {
                // 获取登记税务信息
                this.handleDjHsxx(gzlslid, tsswDataDTO);
            }
            // 添加流程标识， 1（商品房）2（存量房）
            if (bdcGzyzService.checkSpfLc(gzldyid)) {
                tsswDataDTO.setBs("1");
            } else {
                tsswDataDTO.setBs("2");
            }
        }

        // 处理婚姻信息和房屋套次
        this.getHyxx(tsswDataDTO);
        //未成年子女信息
        this.getWcnxx(tsswDataDTO);
        //添加登记项目和项目附表信息
        this.getXmAndXmfbxx(tsswDataDTO);
        // 处理合同编号，对于有地区不做网签备案的，默认使用slbh
        this.handlerTsswHtbh(tsswDataDTO);

        LOGGER.info("推送计税信息数据：{}", JSON.toJSONString(tsswDataDTO));

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("流程实例id{}推送处理后的税务信息{}，{}", gzlslid, beanName, JSON.toJSONString(tsswDataDTO));
        }
        Object response = exchangeInterfaceFeignService.swRequestInterface(beanName, tsswDataDTO);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("流程实例id{}推送税务信息返回值{}", gzlslid, response);
        }
        // 推送税务成功后，更新推送税务状态
        if (Objects.nonNull(response)) {
            BdcCommonResponse<YrbFhmResponse> yrbFhmResponseDTO = JSON.parseObject(JSON.toJSONString(response), BdcCommonResponse.class);
            YrbFhmResponse yrbFhmResponse = JSON.parseObject(JSON.toJSONString(yrbFhmResponseDTO.getData()), YrbFhmResponse.class);
            if (StringUtils.equals("1", yrbFhmResponseDTO.getCode())) {
                modifyTsswzt(gzlslid, xmid);
            }
            if (Objects.nonNull(yrbFhmResponse)) {
                String jyuuid = yrbFhmResponse.getJyuuid();
                String fwuuid = yrbFhmResponse.getFwuuid();
                BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
                if (Objects.nonNull(bdcSlJbxxDO)) {
                    bdcSlJbxxDO.setFwuuid(fwuuid);
                    bdcSlJbxxDO.setJyuuid(jyuuid);
                    bdcSlJbxxService.updateBdcSlJbxx(bdcSlJbxxDO);
                }
            }
        }
        // 推送税务附件信息，单独推送
        pushFjxx(gzlslid, fwlx, "sw_yxzlxxjs");
        return response;
    }

    /**
     * 处理合同编号信息，当配置推送税务时不做合同备案操作的时， bdc_sl_jyxx表的htbh 使用 slbh 进行税务信息推送
     */
    private void handlerTsswHtbh(TsswDataDTO tsswDataDTO){
        if(Objects.nonNull(tsswDataDTO)){
            String qxdm = tsswDataDTO.getBdcxm().getQxdm();
            if(StringUtils.isNotBlank(qxdm)){
                // 推送税务是否进行合同备案， true：进行合同备案， false：不进行合同备案，使用slbh作为htbh推送税务
                boolean swxxHtba = (boolean) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm("swxx.sfhtba", true, qxdm);
                if(!swxxHtba){
                    if(Objects.isNull(tsswDataDTO.getBdcSlJyxx())){
                        BdcSlJyxxDO bdcSlJyxxDO = new BdcSlJyxxDO();
                        bdcSlJyxxDO.setHtbh(tsswDataDTO.getSlbh());
                        tsswDataDTO.setBdcSlJyxx(bdcSlJyxxDO);
                    }else{
                        if(StringUtils.isBlank(tsswDataDTO.getBdcSlJyxx().getHtbh())){
                            tsswDataDTO.getBdcSlJyxx().setHtbh(tsswDataDTO.getSlbh());
                        }
                    }
                }
            }
        }
    }

    /**
     * @param gzlslid@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取税务申报单信息
     * @date : 2022/8/8 11:14
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void getSwSbdxx(String gzlslid, String fwlx, String htbh) {
        //1. 获取增量房税收信息 A004 接口， 并更新 bdc_sl_hsxx 表
        BdcSwxxQO bdcSwxxQO = new BdcSwxxQO(gzlslid, htbh, fwlx);
        if(StringUtils.equals(fwlx, "1")){
            YrbZlfjsxxDTO yrbZlfjsxxDTO = this.getZlfJsxx(bdcSwxxQO);
            if(Objects.nonNull(yrbZlfjsxxDTO) && CollectionUtils.isNotEmpty(yrbZlfjsxxDTO.getZlfskxxhqlist())){
                //2. 获取房产交易申报单并上传文件夹 A014 接口
                String nsrsbh = yrbZlfjsxxDTO.getZlfskxxhqlist().get(0).getNsrsbh();
                this.sbdwj(bdcSwxxQO, nsrsbh);
            }
        }else{
            YrbClfskxxhqDTO yrbClfskxxhqDTO = this.getClfJsxx(bdcSwxxQO);
            if(Objects.nonNull(yrbClfskxxhqDTO) && CollectionUtils.isNotEmpty(yrbClfskxxhqDTO.getClfskxxhqlist())){
                //2. 获取房产交易申报单并上传文件夹 A014 接口
                String nsrsbh = yrbClfskxxhqDTO.getClfskxxhqlist().get(0).getNsrsbh();
                this.sbdwj(bdcSwxxQO, nsrsbh);
            }
        }
    }

    /**
     *  获取房产交易申报单并上传文件夹 【A014】
     */
    private void sbdwj(BdcSwxxQO bdcSwxxQO, String nsrsbh){
        String gzlslid = bdcSwxxQO.getGzlslid();
        BdcSwFcjyDTO bdcSwFcjyDTO = this.generateSwRequestParam(bdcSwxxQO);
        YrbFcjysbxxTaxbizml yrbFcjysbxxTaxbizml = new YrbFcjysbxxTaxbizml();
        YrbFcjysbxxRequestDTO yrbFcjysbxxRequestDTO = new YrbFcjysbxxRequestDTO();
        yrbFcjysbxxRequestDTO.setFWUUID(bdcSwFcjyDTO.getFwuuid());
        yrbFcjysbxxRequestDTO.setHTBH(bdcSwFcjyDTO.getHtbh());
        yrbFcjysbxxRequestDTO.setJYUUID(bdcSwFcjyDTO.getJyuuid());
        yrbFcjysbxxRequestDTO.setSJBH(bdcSwFcjyDTO.getSjbh());
        yrbFcjysbxxRequestDTO.setSJGSDQ(bdcSwFcjyDTO.getSjgsdq());
        yrbFcjysbxxRequestDTO.setSJRQ(bdcSwFcjyDTO.getSjrq());
        yrbFcjysbxxRequestDTO.setZLFCLFBZ(bdcSwFcjyDTO.getZlfclfbz());
        if(StringUtils.isNotBlank(nsrsbh)){
            yrbFcjysbxxRequestDTO.setNSRSBH(nsrsbh);
        }else{
            yrbFcjysbxxRequestDTO.setNSRSBH(this.getNsrsbh(gzlslid, bdcSwxxQO.getQlrlb()));
        }
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            yrbFcjysbxxRequestDTO.setTDFWDZ(bdcSlXmDOList.get(0).getZl());
        }
        ClassHandleComonUtil.headModelSetDefaultValueToNullFieldXmlElemen(yrbFcjysbxxRequestDTO);
        yrbFcjysbxxTaxbizml.setFcjysbxxlist(yrbFcjysbxxRequestDTO);
        LOGGER.warn("流程实例id：{}，获取交易申报单接口入参：{}", gzlslid, JSON.toJSONString(yrbFcjysbxxTaxbizml));
        Object sbdResponse = exchangeInterfaceFeignService.swRequestInterface("sw_fcjysbxx", yrbFcjysbxxTaxbizml);
        LOGGER.warn("流程实例id：{}，获取交易申报单返回数据：{}", gzlslid, StringUtils.left(JSON.toJSONString(sbdResponse), 4000));
        if (Objects.nonNull(sbdResponse)) {
            BdcCommonResponse<YrbFcjysbxxDTO> yrbFcjysbxxDTOResponse = JSON.parseObject(JSON.toJSONString(sbdResponse), BdcCommonResponse.class);
            YrbFcjysbxxDTO yrbFcjysbxxDTO = JSON.parseObject(JSON.toJSONString(yrbFcjysbxxDTOResponse.getData()), YrbFcjysbxxDTO.class);
            if (Objects.nonNull(yrbFcjysbxxDTO) && CollectionUtils.isNotEmpty(yrbFcjysbxxDTO.getFcjysbfjxxlist())) {
                for (YrbFcjysbxxFjxxResponse yrbFcjysbxxFjxxResponse : yrbFcjysbxxDTO.getFcjysbfjxxlist()) {
                    //返回pdf base64 数据，转pdf 上传大云
                    JSONObject wjsjObj = JSON.parseObject(yrbFcjysbxxFjxxResponse.getWjsj());
                    String base64 = wjsjObj.getString("YXWJ");
                    if (StringUtils.isNotBlank(base64)) {
                        if (!base64.startsWith("data:")) {
                                base64 = CommonConstantUtils.BASE64_QZ_PDF + base64;
                        }
                        try {
                            bdcUploadFileUtils.uploadBase64File(base64, gzlslid, sbdwjjmc, "", ".pdf");
                        } catch (IOException e) {
                            LOGGER.error("当前流程实例ID:{}，上传申报单附件失败", gzlslid, e);
                        }
                    }
                }
            }
        }
    }

    @Override
    public YrbClfskxxhqDTO getClfJsxx(BdcSwxxQO bdcSwxxQO) {
        BdcSwFcjyDTO bdcSwFcjyDTO = this.generateSwRequestParam(bdcSwxxQO);
        YrbClfskxxhqRequestDTO yrbClfskxxhqRequestDTO = new YrbClfskxxhqRequestDTO();
        yrbClfskxxhqRequestDTO.setSJBH(bdcSwFcjyDTO.getSjbh());
        yrbClfskxxhqRequestDTO.setSJRQ(DateUtils.formateDateToString(bdcSwFcjyDTO.getSjrq(),DateUtils.DATE_FORMAT_2));
        yrbClfskxxhqRequestDTO.setZLFCLFBZ(bdcSwFcjyDTO.getZlfclfbz());
        yrbClfskxxhqRequestDTO.setSJGSDQ(bdcSwFcjyDTO.getSjgsdq());
        yrbClfskxxhqRequestDTO.setHTBH(bdcSwFcjyDTO.getHtbh());
        yrbClfskxxhqRequestDTO.setJYUUID(bdcSwFcjyDTO.getJyuuid());
        yrbClfskxxhqRequestDTO.setFWUUID(bdcSwFcjyDTO.getFwuuid());
        yrbClfskxxhqRequestDTO.setNSRSBH(this.getNsrsbh(bdcSwxxQO.getGzlslid(), bdcSwxxQO.getQlrlb()));
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(bdcSwxxQO.getGzlslid());
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            yrbClfskxxhqRequestDTO.setFWZLDZ(bdcSlXmDOList.get(0).getZl());
        }
        YrbClfskxxhqTaxbizml yrbClfskxxhqTaxbizml = new YrbClfskxxhqTaxbizml();
        yrbClfskxxhqTaxbizml.setClfskxxhqlist(yrbClfskxxhqRequestDTO);
        LOGGER.warn("流程实例id：{}，合同编号：{}，获取存量房计税信息接口入参：{}", bdcSwxxQO.getGzlslid(), bdcSwxxQO.getHtbh(), JSON.toJSONString(yrbClfskxxhqTaxbizml));
        Object response = exchangeInterfaceFeignService.swRequestInterface("sw_clfskxxhq", yrbClfskxxhqTaxbizml);
        LOGGER.warn("流程实例id：{}，合同编号：{}，获取存量房计税信息接口返回值：{}", bdcSwxxQO.getGzlslid(), bdcSwxxQO.getHtbh(), JSON.toJSONString(response));

        if (Objects.nonNull(response)) {
            BdcCommonResponse<YrbClfskxxhqDTO> commonResponse = JSON.parseObject(JSON.toJSONString(response), BdcCommonResponse.class);
            YrbClfskxxhqDTO yrbClfskxxhqDTO = JSON.parseObject(JSON.toJSONString(commonResponse.getData()), YrbClfskxxhqDTO.class);

            if (Objects.nonNull(yrbClfskxxhqDTO) && Objects.nonNull(yrbClfskxxhqDTO.getClfskxxhqlist())) {
                // 根据合同编号获取交易信息
                BdcSlJyxxQO bdcSlJyxxQO = new BdcSlJyxxQO();
                bdcSlJyxxQO.setHtbh(bdcSwFcjyDTO.getHtbh());
                bdcSlJyxxQO.setGzlslid(bdcSwxxQO.getGzlslid());
                List<BdcSlJyxxDO> bdcSlJyxxDOList = this.bdcSlJyxxService.listBdcSlJyxx(bdcSlJyxxQO);
                // 获取申请人信息
                List<BdcSlSqrDO> bdcSlSqrDOList = this.bdcSlSqrService.listBdcSlSqrByXmid(bdcSlJyxxDOList.get(0).getXmid(), null);
                if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList) && CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                    //1.先删除之前税务信息
                    bdcSlHsxxService.deleteSwxx(bdcSlJyxxDOList.get(0).getXmid());

                    // 将返回的税款信息，根据权利人证件号对照nsrsbh来区分，权利人税款和义务人税款信息
                    Map<String, String> sqrMap = bdcSlSqrDOList.stream().collect(Collectors.toMap(BdcSlSqrDO::getZjh, BdcSlSqrDO::getSqrlb));
                    List<ClfskxxhqResponse> clfskxxhqlist = yrbClfskxxhqDTO.getClfskxxhqlist();
                    Map<String, List<ClfskxxhqResponse>> clfSkxxMap = clfskxxhqlist.stream().collect(Collectors.groupingBy(ClfskxxhqResponse::getNsrsbh));

                    List<ClfskxxhqResponse> qlrSkxxList = new ArrayList<>(10);
                    List<ClfskxxhqResponse> ywrSkxxList = new ArrayList<>(10);
                    for(String nsrsbh : clfSkxxMap.keySet()){
                        if(sqrMap.containsKey(nsrsbh)){
                            String qlrlb = sqrMap.get(nsrsbh);
                            if(CommonConstantUtils.QLRLB_QLR.equals(qlrlb)){
                                qlrSkxxList.addAll(clfSkxxMap.get(nsrsbh));
                            }
                            if(CommonConstantUtils.QLRLB_YWR.equals(qlrlb)){
                                ywrSkxxList.addAll(clfSkxxMap.get(nsrsbh));
                            }
                        }
                    }
                    if(CollectionUtils.isNotEmpty(qlrSkxxList)){
                        this.hanlderClfSkxx(CommonConstantUtils.QLRLB_QLR, bdcSlJyxxDOList.get(0).getXmid(), qlrSkxxList);
                    }

                    if (CollectionUtils.isNotEmpty(ywrSkxxList)) {
                        this.hanlderClfSkxx(CommonConstantUtils.QLRLB_YWR, bdcSlJyxxDOList.get(0).getXmid(), ywrSkxxList);
                    }
                }
                return yrbClfskxxhqDTO;
            }
        }
        return null;
    }

    // 处理存量房计税信息，生成hsxx 和hsxxmx 内容
    private void hanlderClfSkxx(String qlrlb, String xmid, List<ClfskxxhqResponse> clfskxxhqlist){
        BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
        bdcSlHsxxDO.setHsxxid(UUIDGenerator.generate16());
        bdcSlHsxxDO.setXmid(xmid);
        bdcSlHsxxDO.setSqrlb(qlrlb);
        bdcSlHsxxDO.setJssksj(new Date());
        //只有=5才能点击获取申报单
        bdcSlHsxxDO.setWszt(5);
        bdcSlHsxxDO.setHszt(5);
        double ynsehj = 0.00;
        double jmsehj = 0.00;
        double sjyzhj = 0.00;
        List<BdcSlHsxxMxDO> bdcSlHsxxMxDOList = new ArrayList<>(CollectionUtils.size(clfskxxhqlist));
        for (ClfskxxhqResponse clfskxxhqResponse : clfskxxhqlist) {
            BdcSlHsxxMxDO bdcSlHsxxMxDO = new BdcSlHsxxMxDO();
            bdcSlHsxxMxDO.setHsxxmxid(UUIDGenerator.generate16());
            bdcSlHsxxMxDO.setHsxxid(bdcSlHsxxDO.getHsxxid());
            bdcSlHsxxMxDO.setNsrmc(clfskxxhqResponse.getNsrmc());
            bdcSlHsxxMxDO.setNsrsbh(clfskxxhqResponse.getNsrsbh());
            bdcSlHsxxMxDO.setSl(clfskxxhqResponse.getSl());
            bdcSlHsxxMxDO.setYsx(clfskxxhqResponse.getYsx());
            bdcSlHsxxMxDO.setJcx(clfskxxhqResponse.getJcx());
            bdcSlHsxxMxDO.setMxzl(clfskxxhqResponse.getZspmmc());
            bdcSlHsxxMxDO.setYnse(clfskxxhqResponse.getYnse());
            bdcSlHsxxMxDO.setYbtse(clfskxxhqResponse.getYbtse());
            bdcSlHsxxMxDO.setZsxm(clfskxxhqResponse.getZsxmmc());
            bdcSlHsxxMxDOList.add(bdcSlHsxxMxDO);
            //计算合计信息
            //应纳税额合计
            if (null != bdcSlHsxxMxDO.getYnse()) {
                ynsehj = NumberUtil.formatDigit(ynsehj + bdcSlHsxxMxDO.getYnse(), 2);
            } else {
                bdcSlHsxxMxDO.setYnse(0.00);
            }
            //减免税额合计
            if (null != bdcSlHsxxMxDO.getJmse()) {
                jmsehj = NumberUtil.formatDigit(jmsehj + bdcSlHsxxMxDO.getJmse(), 2);
            } else {
                bdcSlHsxxMxDO.setJmse(0.00);
            }
            if (null != bdcSlHsxxMxDO.getYbtse()) {
                sjyzhj = NumberUtil.formatDigit(sjyzhj + bdcSlHsxxMxDO.getYbtse(), 2);
            } else {
                //实际纳税额计算 应收额-减免额=实际纳税额
                if (Objects.nonNull(bdcSlHsxxMxDO.getYnse()) && Objects.nonNull(bdcSlHsxxMxDO.getJmse())) {
                    double ynse = NumberUtil.subtract(bdcSlHsxxMxDO.getYnse(), bdcSlHsxxMxDO.getJmse(), 2);
                    bdcSlHsxxMxDO.setYnse(ynse);
                    sjyzhj = NumberUtil.formatDigit(sjyzhj + ynse, 2);
                }
            }
            bdcSlHsxxDO.setNsrsbh(clfskxxhqResponse.getNsrsbh());
        }
        bdcSlHsxxDO.setYnsehj(ynsehj);
        bdcSlHsxxDO.setJmsehj(jmsehj);
        bdcSlHsxxDO.setSjyzhj(sjyzhj);
        entityMapper.insertSelective(bdcSlHsxxDO);
        if (CollectionUtils.isNotEmpty(bdcSlHsxxMxDOList)) {
            //新增明细
            entityMapper.insertBatchSelective(bdcSlHsxxMxDOList);
        }
    }

    @Override
    public YrbZlfjsxxDTO getZlfJsxx(BdcSwxxQO bdcSwxxQO) {
        BdcSwFcjyDTO bdcSwFcjyDTO = this.generateSwRequestParam(bdcSwxxQO);
        YrbZlfskxxRequestDTO yrbZlfskxxRequestDTO = new YrbZlfskxxRequestDTO();
        yrbZlfskxxRequestDTO.setFWUUID(bdcSwFcjyDTO.getFwuuid());
        yrbZlfskxxRequestDTO.setHTBH(bdcSwFcjyDTO.getHtbh());
        yrbZlfskxxRequestDTO.setJYUUID(bdcSwFcjyDTO.getJyuuid());
        yrbZlfskxxRequestDTO.setSJBH(bdcSwFcjyDTO.getSjbh());
        yrbZlfskxxRequestDTO.setSJRQ(bdcSwFcjyDTO.getSjrq());
        yrbZlfskxxRequestDTO.setZLFCLFBZ(bdcSwFcjyDTO.getZlfclfbz());
        yrbZlfskxxRequestDTO.setSJGSDQ(bdcSwFcjyDTO.getSjgsdq());
        ClassHandleComonUtil.headModelSetDefaultValueToNullFieldXmlElemen(yrbZlfskxxRequestDTO);
        YrbZlfskxxTaxbizml yrbZlfskxxTaxbizml = new YrbZlfskxxTaxbizml();
        yrbZlfskxxTaxbizml.setZlfskxxhqlist(yrbZlfskxxRequestDTO);

        LOGGER.warn("一人办件流程实例id：{}，合同编号：{}，获取申报单信息接口入参：{}", bdcSwxxQO.getGzlslid(), bdcSwxxQO.getHtbh(), JSON.toJSONString(yrbZlfskxxTaxbizml));
        Object response = exchangeInterfaceFeignService.swRequestInterface("sw_zlfskxxhq", yrbZlfskxxTaxbizml);
        LOGGER.warn("一人办件流程实例id：{}，合同编号：{}，获取申报单信息接口返回数据：{}", bdcSwxxQO.getGzlslid(), bdcSwxxQO.getHtbh(), JSON.toJSONString(response));
        if (Objects.nonNull(response)) {
            BdcCommonResponse<YrbZlfjsxxDTO> yrbZlfjsxxDTOBdcCommonResponse = JSON.parseObject(JSON.toJSONString(response), BdcCommonResponse.class);
            YrbZlfjsxxDTO yrbZlfjsxxDTO = JSON.parseObject(JSON.toJSONString(yrbZlfjsxxDTOBdcCommonResponse.getData()), YrbZlfjsxxDTO.class);
            if (Objects.nonNull(yrbZlfjsxxDTO) && Objects.nonNull(yrbZlfjsxxDTO.getZlfjsjglist())) {
                LOGGER.warn("一人办流程实例id：{}， 获取申报单税务信息返回合同编号：{}",  bdcSwxxQO.getGzlslid(), yrbZlfjsxxDTO.getZlfjsjglist().getHtbh());
                // 根据合同编号获取交易信息
                BdcSlJyxxQO bdcSlJyxxQO = new BdcSlJyxxQO();
                bdcSlJyxxQO.setHtbh(bdcSwFcjyDTO.getHtbh());
                bdcSlJyxxQO.setGzlslid(bdcSwxxQO.getGzlslid());
                List<BdcSlJyxxDO> bdcSlJyxxDOList = this.bdcSlJyxxService.listBdcSlJyxx(bdcSlJyxxQO);
                if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
                    //1.先删除之前税务信息
                    bdcSlHsxxService.deleteSwxx(bdcSlJyxxDOList.get(0).getXmid());
                    List<YrbZlfskxxResponse> yrbZlfskxxResponseList = yrbZlfjsxxDTO.getZlfskxxhqlist();
                    if (CollectionUtils.isNotEmpty(yrbZlfskxxResponseList)) {
                        BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
                        bdcSlHsxxDO.setHsxxid(UUIDGenerator.generate16());
                        bdcSlHsxxDO.setXmid(bdcSlJyxxDOList.get(0).getXmid());
                        bdcSlHsxxDO.setSqrlb(CommonConstantUtils.QLRLB_QLR);
                        bdcSlHsxxDO.setJssksj(new Date());
                        //只有=5才能点击获取申报单
                        bdcSlHsxxDO.setWszt(5);
                        bdcSlHsxxDO.setHszt(5);
                        List<BdcSlHsxxMxDO> bdcSlHsxxMxDOList = new ArrayList<>(CollectionUtils.size(yrbZlfskxxResponseList));
                        double ynsehj = 0.00;
                        double jmsehj = 0.00;
                        double sjyzhj = 0.00;
                        for (YrbZlfskxxResponse yrbZlfskxxResponse : yrbZlfskxxResponseList) {
                            BdcSlHsxxMxDO bdcSlHsxxMxDO = new BdcSlHsxxMxDO();
                            bdcSlHsxxMxDO.setHsxxmxid(UUIDGenerator.generate16());
                            bdcSlHsxxMxDO.setHsxxid(bdcSlHsxxDO.getHsxxid());
                            bdcSlHsxxMxDO.setNsrmc(yrbZlfskxxResponse.getNsrmc());
                            bdcSlHsxxMxDO.setNsrsbh(yrbZlfskxxResponse.getNsrsbh());
                            bdcSlHsxxMxDO.setSl(yrbZlfskxxResponse.getSl());
                            bdcSlHsxxMxDO.setYsx(yrbZlfskxxResponse.getYsx());
                            bdcSlHsxxMxDO.setJcx(yrbZlfskxxResponse.getJcx());
                            bdcSlHsxxMxDO.setMxzl(yrbZlfskxxResponse.getZspmmc());
                            bdcSlHsxxMxDO.setYnse(yrbZlfskxxResponse.getYnse());
                            bdcSlHsxxMxDO.setYbtse(yrbZlfskxxResponse.getYbtse());
                            bdcSlHsxxMxDO.setZsxm(yrbZlfskxxResponse.getZsxmmc());
                            bdcSlHsxxMxDOList.add(bdcSlHsxxMxDO);
                            //计算合计信息
                            //应纳税额合计
                            if (null != bdcSlHsxxMxDO.getYnse()) {
                                ynsehj = NumberUtil.formatDigit(ynsehj + bdcSlHsxxMxDO.getYnse(), 2);
                            } else {
                                bdcSlHsxxMxDO.setYnse(0.00);
                            }
                            //减免税额合计
                            if (null != bdcSlHsxxMxDO.getJmse()) {
                                jmsehj = NumberUtil.formatDigit(jmsehj + bdcSlHsxxMxDO.getJmse(), 2);
                            } else {
                                bdcSlHsxxMxDO.setJmse(0.00);
                            }
                            if (null != bdcSlHsxxMxDO.getYbtse()) {
                                sjyzhj = NumberUtil.formatDigit(sjyzhj + bdcSlHsxxMxDO.getYbtse(), 2);
                            } else {
                                //实际纳税额计算 应收额-减免额=实际纳税额
                                if (Objects.nonNull(bdcSlHsxxMxDO.getYnse()) && Objects.nonNull(bdcSlHsxxMxDO.getJmse())) {
                                    double ynse = NumberUtil.subtract(bdcSlHsxxMxDO.getYnse(), bdcSlHsxxMxDO.getJmse(), 2);
                                    bdcSlHsxxMxDO.setYnse(ynse);
                                    sjyzhj = NumberUtil.formatDigit(sjyzhj + ynse, 2);
                                }
                            }
                            bdcSlHsxxDO.setNsrsbh(yrbZlfskxxResponse.getNsrsbh());
                        }
                        bdcSlHsxxDO.setYnsehj(ynsehj);
                        bdcSlHsxxDO.setJmsehj(jmsehj);
                        bdcSlHsxxDO.setSjyzhj(sjyzhj);
                        entityMapper.insertSelective(bdcSlHsxxDO);
                        if (CollectionUtils.isNotEmpty(bdcSlHsxxMxDOList)) {
                            //新增明细
                            entityMapper.insertBatchSelective(bdcSlHsxxMxDOList);
                        }
                    }
                }
                return yrbZlfjsxxDTO;
            }
        }
        return null;
    }

    /**
     * @param gzlslid@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 确认申报单
     * @date : 2022/8/8 11:40
     */
    @Override
    public Object qrSbd(String gzlslid, String fwlx) {
        BdcSwxxQO bdcSwxxQO = new BdcSwxxQO();
        bdcSwxxQO.setGzlslid(gzlslid);
        bdcSwxxQO.setFwlx(fwlx);
        BdcSwFcjyDTO bdcSwFcjyDTO = this.generateSwRequestParam(bdcSwxxQO);

        YrbFcjysbxxQrTaxbizml yrbFcjysbxxQrTaxbizml = new YrbFcjysbxxQrTaxbizml();
        YrbFcjysbxxQrRequestDTO yrbFcjysbxxQrRequestDTO = new YrbFcjysbxxQrRequestDTO();
        yrbFcjysbxxQrRequestDTO.setFWUUID(bdcSwFcjyDTO.getFwuuid());
        yrbFcjysbxxQrRequestDTO.setSJBH(bdcSwFcjyDTO.getSjbh());
        yrbFcjysbxxQrRequestDTO.setHTBH(bdcSwFcjyDTO.getHtbh());
        yrbFcjysbxxQrRequestDTO.setJYUUID(bdcSwFcjyDTO.getJyuuid());
        yrbFcjysbxxQrRequestDTO.setNSRQRXX("1");
        yrbFcjysbxxQrRequestDTO.setSJGSDQ(bdcSwFcjyDTO.getSjgsdq());
        yrbFcjysbxxQrRequestDTO.setSJRQ(bdcSwFcjyDTO.getSjrq());
        yrbFcjysbxxQrRequestDTO.setZLFCLFBZ(bdcSwFcjyDTO.getZlfclfbz());
        ClassHandleComonUtil.headModelSetDefaultValueToNullFieldXmlElemen(yrbFcjysbxxQrRequestDTO);
        yrbFcjysbxxQrTaxbizml.setFcjysbqrxxlist(yrbFcjysbxxQrRequestDTO);
        //整理附件信息
        String wjjmc = sbdwjjmc;
        if(StringUtils.isNotBlank(sbdqrwjjmc)){
            wjjmc = sbdqrwjjmc;
        }
        yrbFcjysbxxQrTaxbizml.setFcjysbqrfjxxlist(getFjxx(gzlslid, wjjmc, "0201", fwlx));
        //推送申报确认单A015接口
        LOGGER.warn("当前流程实例id：{}，一人办件流程推送申报确认单接口传参：{}", gzlslid, JSON.toJSONString(yrbFcjysbxxQrTaxbizml));
        Object response = exchangeInterfaceFeignService.swRequestInterface("sw_fcjysbxxqr", yrbFcjysbxxQrTaxbizml);
        LOGGER.warn("当前流程实例id：{}，一人办件推送税务附件结果：{}", gzlslid, JSON.toJSONString(response));
        return response;
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取税票
     * @date : 2022/8/8 14:15
     */
    @Override
    public CommonResponse getSpxx(String gzlslid, String jszt, String fwlx, String htbh) {
        if (StringUtils.isAnyBlank(gzlslid, htbh)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID或合同编号。");
        }
        //jszt=10 的时候直接获取契税完税凭证
        BdcSwxxQO bdcSwxxQO = new BdcSwxxQO(gzlslid, htbh, fwlx);
        if (StringUtils.equals("10", jszt)) {
            bdcSwxxQO.setGxlx(CommonConstantUtils.SW_GXLX_BGX);
            return this.yrbQswspz(bdcSwxxQO);
        } else {
            List<YrbEwmjkxxDTO> yrbEwmjkxxDTOList = this.swjkzt(bdcSwxxQO);
            if(CollectionUtils.isNotEmpty(yrbEwmjkxxDTOList)){
                for(YrbEwmjkxxDTO yrbEwmjkxxDTO:yrbEwmjkxxDTOList){
                    if (StringUtils.equals("1", yrbEwmjkxxDTO.getKkjg().getJkzt())) {
                        return CommonResponse.fail("获取到缴款状态为：未缴费，无法获取税票。");
                    }
                }
                bdcSwxxQO.setGxlx(CommonConstantUtils.SW_GXLX_BGX);
                return this.yrbQswspz(bdcSwxxQO);
            }
            return CommonResponse.fail("获取税务缴款状态失败");
        }
    }

    // 一人办获取税票信息
    private CommonResponse yrbQswspz(BdcSwxxQO bdcSwxxQO){
        bdcSwxxQO.setGxlx(CommonConstantUtils.SW_GXLX_BGX);
        if(StringUtils.isBlank(bdcSwxxQO.getBeanName())){
            bdcSwxxQO.setBeanName("sw_qswspzhq");
        }
        Object qswspzxx = this.getQswspz(bdcSwxxQO);
        if(Objects.isNull(qswspzxx)){
            this.getSfjnpz(bdcSwxxQO.getGzlslid(), bdcSwxxQO.getFwlx(), bdcSwxxQO.getHtbh());
            return CommonResponse.fail("请至窗口打印税票");
        }
        return CommonResponse.ok();
    }

    @Override
    public CommonResponse hqqsws(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数：工作流实例ID");
        }
        return this.getSfjnpz(gzlslid, null, null);
    }

    /**
     * 调用【A019】接口，查询缴税状态，并更新核税信息表中，完税状态与核税状态值
     */
    private List<YrbEwmjkxxDTO> swjkzt(BdcSwxxQO bdcSwxxQO){
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(bdcSwxxQO.getGzlslid());
        if(CollectionUtils.isEmpty(bdcSlXmDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产受理项目信息");
        }
        String xmid = bdcSlXmDOList.get(0).getXmid();
        BdcSwFcjyDTO bdcSwFcjyDTO = this.generateSwRequestParam(bdcSwxxQO);
        List<BdcSlYjkxxDO> bdcSlYjkxxDOList = this.queryBdcSlYjkxxByXmid(xmid);

        List<YrbEwmjkxxDTO> yrbEwmjkxxDTOList = new ArrayList<>(10);
        if (CollectionUtils.isNotEmpty(bdcSlYjkxxDOList)) {
            for (BdcSlYjkxxDO bdcSlYjkxxDO : bdcSlYjkxxDOList) {
                YrbEwmjkxxDTO yrbEwmjkxxDTO = this.queryEwmJkxx(bdcSwFcjyDTO, bdcSlYjkxxDO);
                if (Objects.nonNull(yrbEwmjkxxDTO) && Objects.nonNull(yrbEwmjkxxDTO.getKkjg())) {
                    yrbEwmjkxxDTOList.add(yrbEwmjkxxDTO);
                    if (StringUtils.equals("1", yrbEwmjkxxDTO.getKkjg().getJkzt())) {
                        LOGGER.error("纳税人识别号：{} 的缴款状态为未缴款", bdcSlYjkxxDO.getNsrsbh());
                    } else {
                        //更新wszt=10
                        BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
                        bdcSlHsxxQO.setXmid(bdcSlXmDOList.get(0).getXmid());
                        bdcSlHsxxQO.setSqrlb(bdcSlYjkxxDO.getQlrlb());
                        List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxService.listBdcSlHsxxByHsxxQo(bdcSlHsxxQO);
                        if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                            for (BdcSlHsxxDO bdcSlHsxxDO : bdcSlHsxxDOList) {
                                bdcSlHsxxDO.setWszt(10);
                                bdcSlHsxxDO.setHszt(10);
                            }
                            entityMapper.batchSaveSelective(bdcSlHsxxDOList);
                        }
                    }
                }
            }
        }
        return yrbEwmjkxxDTOList;
    }

    private YrbEwmjkxxDTO queryEwmJkxx(BdcSwFcjyDTO bdcSwFcjyDTO, BdcSlYjkxxDO bdcSlYjkxxDO){
        YrbEwmjkxxTaxbizml yrbEwmjkxxTaxbizml = new YrbEwmjkxxTaxbizml();
        List<YrbEwmjkxxRequest> yrbEwmjkxxRequestList = new ArrayList<>(10);
        YrbEwmjkxxRequest yrbEwmjkxxRequest = new YrbEwmjkxxRequest();
        yrbEwmjkxxRequest.setHTBH(bdcSwFcjyDTO.getHtbh());
        yrbEwmjkxxRequest.setLRRDM(bdcSwFcjyDTO.getLrrdm());
        yrbEwmjkxxRequest.setSJBH(bdcSwFcjyDTO.getSjbh());
        yrbEwmjkxxRequest.setSJGSDQ(bdcSwFcjyDTO.getSjgsdq());
        yrbEwmjkxxRequest.setYBTSE(bdcSlYjkxxDO.getYbtse());
        yrbEwmjkxxRequest.setDZSPHM(bdcSlYjkxxDO.getDzsphm());
        yrbEwmjkxxRequest.setPZXH(bdcSlYjkxxDO.getPzxh());
        ClassHandleComonUtil.headModelSetDefaultValueToNullFieldXmlElemen(yrbEwmjkxxRequest);
        yrbEwmjkxxRequestList.add(yrbEwmjkxxRequest);
        yrbEwmjkxxTaxbizml.setFcjyzfquerylist(yrbEwmjkxxRequestList);
        LOGGER.warn("当前流程实例ID：{}，缴税状态！=10，调用A019接口入参：{}", bdcSwFcjyDTO.getGzlslid(), JSON.toJSONString(yrbEwmjkxxTaxbizml));
        Object response = exchangeInterfaceFeignService.swRequestInterface("sw_queryewmjkxx", yrbEwmjkxxTaxbizml);
        LOGGER.warn("当前流程实例ID：{}，缴税状态！=10，调用A019接口返回值：{}", bdcSwFcjyDTO.getGzlslid(), JSON.toJSONString(response));
        if (Objects.nonNull(response)) {
            CommonResponse yrbEwmjkxxResDTO = JSON.parseObject(JSON.toJSONString(response), CommonResponse.class);
            YrbEwmjkxxDTO yrbEwmjkxxDTO = JSON.parseObject(JSON.toJSONString(yrbEwmjkxxResDTO.getData()), YrbEwmjkxxDTO.class);
            return yrbEwmjkxxDTO;
        }
        return null;
    }

    /**
     * 根据项目ID 查询应缴款信息内容
     */
    private List<BdcSlYjkxxDO> queryBdcSlYjkxxByXmid(String xmid){
        Example example = new Example(BdcSlYjkxxDO.class);
        example.createCriteria().andEqualTo("xmid", xmid);
        List<BdcSlYjkxxDO> bdcSlYjkxxDOList = entityMapper.selectByExampleNotNull(example);
        return bdcSlYjkxxDOList;
    }

    /**
     * @param gzlslid
     * @param htbh
     * @param fwlx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取缴税二维码
     * @date : 2022/8/10 18:14
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<BdcSwJkmxxVO> getJsewm(String gzlslid, String htbh, String fwlx) {
        //A017接口
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
            if (CollectionUtils.isEmpty(bdcSlXmDOList)) {
                throw new AppException("未找到项目信息");
            }
            // 调用【A017】接口获取待缴信息
            BdcSwxxQO bdcSwxxQO = new BdcSwxxQO(gzlslid, htbh, fwlx);
            BdcSwFcjyDTO bdcSwFcjyDTO = this.generateSwRequestParam(bdcSwxxQO);
            List<BdcSlYjkxxDO> bdcSlYjkxxDOList = this.jkxxQd(bdcSlXmDOList.get(0).getXmid(), bdcSwFcjyDTO);

            List<BdcSwJkmxxVO> bdcSwJkmxxVOList = new ArrayList<>(10);
            if(CollectionUtils.isNotEmpty(bdcSlYjkxxDOList)){
                // 过滤缴款状态为待缴款的信息
                bdcSlYjkxxDOList = bdcSlYjkxxDOList.stream().filter(t-> StringUtils.equals(t.getJkzt(), "0")).collect(Collectors.toList());
                if(CollectionUtils.isEmpty(bdcSlYjkxxDOList)){
                    throw new AppException(ErrorCode.MISSING_ARG, "未获取到需要待缴款信息");
                }
                List<BdcSlSqrDO> bdcSlSqrDOList = this.bdcSlSqrService.listBdcSlSqrByXmid(bdcSlXmDOList.get(0).getXmid(), null);
                if(CollectionUtils.isEmpty(bdcSlSqrDOList)){
                    throw new AppException(ErrorCode.MISSING_ARG, "未获取到受理申请人信息");
                }
                Map<String, String> zjhAndSqrlbMap = bdcSlSqrDOList.stream().collect(Collectors.toMap(BdcSlSqrDO::getZjh, BdcSlSqrDO::getSqrlb, (v1, v2)->v1));
                for(BdcSlYjkxxDO bdcSlYjkxxDO: bdcSlYjkxxDOList){
                    // 调用【A018】接口获取缴款二维码
                    YrbFcjyEwmResponse yrbFcjyEwmResponse = this.jkxxEwm(bdcSwFcjyDTO, bdcSlYjkxxDO);
                    BdcSwJkmxxVO bdcSwJkmxxVO = new BdcSwJkmxxVO();
                    BeanUtils.copyProperties(bdcSlYjkxxDO, bdcSwJkmxxVO);
                    bdcSwJkmxxVO.setJsewm(yrbFcjyEwmResponse.getEwm());
                    if(zjhAndSqrlbMap.containsKey(bdcSwJkmxxVO.getNsrsbh())){
                        bdcSwJkmxxVO.setQlrlb(zjhAndSqrlbMap.get(bdcSwJkmxxVO.getNsrsbh()));
                    }
                    bdcSwJkmxxVOList.add(bdcSwJkmxxVO);
                }
                if(CollectionUtils.isNotEmpty(bdcSwJkmxxVOList)){
                   Map<String, BdcSwJkmxxVO> result = bdcSwJkmxxVOList.stream().collect(Collectors.toMap(BdcSwJkmxxVO::getQlrlb, bdcSwJkmxxVO->bdcSwJkmxxVO));
                   if(result.containsKey(CommonConstantUtils.QLRLB_QLR)){
                       updateBdcSlHxxJsewm(result, gzlslid, CommonConstantUtils.QLRLB_QLR);
                   }
                    if(result.containsKey(CommonConstantUtils.QLRLB_YWR)){
                       updateBdcSlHxxJsewm(result, gzlslid, CommonConstantUtils.QLRLB_YWR);
                    }
                }
            }
            return bdcSwJkmxxVOList;
        }
        return null;
    }

    // 更新不动产受理核税信息缴税二维码
    private void updateBdcSlHxxJsewm(Map<String, BdcSwJkmxxVO> result, String gzlslid, String sqrlb){
        BdcSwJkmxxVO bdcSwJkmxxVO = result.get(sqrlb);
        String fjid = this.uploadJsewmImg(bdcSwJkmxxVO.getJsewm(), gzlslid + sqrlb);
        List<BdcSlHsxxDO> bdcSlHsxxDOList = this.bdcSlHsxxService.listBdcSlHsxxByGzlslidAndSqrlb(gzlslid, sqrlb);
        if(CollectionUtils.isNotEmpty(bdcSlHsxxDOList)){
            BdcSlHsxxDO bdcSlHsxxDO = bdcSlHsxxDOList.get(0);
            bdcSlHsxxDO.setJsewm(fjid);
            this.bdcSlHsxxService.updateBdcSlHsxx(bdcSlHsxxDO);
        }
    }

    // 上传缴税二维码至大云附件中心
    public String uploadJsewmImg(String base64, String spaceId) {
        if(!base64.startsWith("data:")){
            base64 = "data:application/png;base64," + base64;
        }
        // 创建文件夹目录
        StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, spaceId, "缴税二维码图片", null);
        MultipartFile multipartFile = Base64Utils.base64ToMultipart(base64);
        if (Objects.nonNull(storageDto)) {
            try {
                MultipartDto multipartDto = new MultipartDto();
                multipartDto.setNodeId(storageDto.getId());
                multipartDto.setClientId(CommonConstantUtils.WJZX_CLIENTID);
                multipartDto.setData(multipartFile.getBytes());
                multipartDto.setOwner("");
                multipartDto.setContentType(multipartFile.getContentType());
                multipartDto.setSize(multipartFile.getSize());
                multipartDto.setOriginalFilename(multipartFile.getOriginalFilename());
                multipartDto.setName(multipartFile.getName());
                StorageDto storage = storageClient.multipartUpload(multipartDto);
                return storage.getId();
            } catch (IOException e) {
                LOGGER.error("缴税二维码图片文件上传错误{}", e.getMessage());
            }
        }
        return "";
    }

    /**
     * 获取增量房买方、存量房买卖双方的待缴款信息【A017】
     */
    private List<BdcSlYjkxxDO> jkxxQd(String xmid, BdcSwFcjyDTO bdcSwFcjyDTO) {
        YrbFcjyyjkTaxbizml yrbFcjyyjkTaxbizml = new YrbFcjyyjkTaxbizml();
        YrbFcjyyjkRequestDTO yrbFcjyyjkRequestDTO = new YrbFcjyyjkRequestDTO();
        yrbFcjyyjkRequestDTO.setHTBH(bdcSwFcjyDTO.getHtbh());
        yrbFcjyyjkRequestDTO.setSJBH(bdcSwFcjyDTO.getSjbh());
        yrbFcjyyjkRequestDTO.setSJGSDQ(bdcSwFcjyDTO.getSjgsdq());
        yrbFcjyyjkRequestDTO.setLRRDM(bdcSwFcjyDTO.getLrrdm());
        yrbFcjyyjkRequestDTO.setZLFCLFBZ(bdcSwFcjyDTO.getZlfclfbz());
        ClassHandleComonUtil.headModelSetDefaultValueToNullFieldXmlElemen(yrbFcjyyjkRequestDTO);
        yrbFcjyyjkTaxbizml.setFcjyyjskList(yrbFcjyyjkRequestDTO);
        LOGGER.warn("当前流程实例ID：{}，合同编号：{}，获取缴税二维码调用A017接口入参：{}", bdcSwFcjyDTO.getGzlslid(), bdcSwFcjyDTO.getHtbh(), JSON.toJSONString(yrbFcjyyjkTaxbizml));
        Object response = exchangeInterfaceFeignService.swRequestInterface("sw_queryFcjyyjkList", yrbFcjyyjkTaxbizml);
        LOGGER.warn("当前流程实例ID：{}，合同编号：{}，获取缴税二维码调用A017接口返回数据：{}",  bdcSwFcjyDTO.getGzlslid(), bdcSwFcjyDTO.getHtbh(), JSON.toJSONString(response));
        if(null != response){
            BdcCommonResponse yrbFcjyyjkResponseDTO = JSON.parseObject(JSON.toJSONString(response), BdcCommonResponse.class);
            YrbFcjyyjkDTO yrbFcjyyjkDTO = JSON.parseObject(JSON.toJSONString(yrbFcjyyjkResponseDTO.getData()), YrbFcjyyjkDTO.class);
            List<BdcSlYjkxxDO> bdcSlYjkxxDOList = new ArrayList<>(10);
            if (Objects.nonNull(yrbFcjyyjkDTO)) {
                if (CollectionUtils.isNotEmpty(yrbFcjyyjkDTO.getFcjyyjsklist())) {
                    for (YrbFcjyyjkResponse yrbFcjyyjkResponse : yrbFcjyyjkDTO.getFcjyyjsklist()) {
                        BdcSlYjkxxDO bdcSlYjkxxDO = new BdcSlYjkxxDO();
                        BeanUtils.copyProperties(yrbFcjyyjkResponse, bdcSlYjkxxDO);
                        bdcSlYjkxxDO.setJkxxid(UUIDGenerator.generate16());
                        bdcSlYjkxxDO.setXmid(xmid);
                        bdcSlYjkxxDOList.add(bdcSlYjkxxDO);
                    }
                    this.entityMapper.insertBatchSelective(bdcSlYjkxxDOList);
                }
            }
            return bdcSlYjkxxDOList;
        }
        return null;
    }

    /**
     * 获取增量房买方、存量房买卖双方的缴款码信息【A018】
     */
    private YrbFcjyEwmResponse jkxxEwm(BdcSwFcjyDTO bdcSwFcjyDTO, BdcSlYjkxxDO bdcSlYjkxxDO){
        List<YrbFcjyEwmRequest> yrbFcjyEwmRequestList = new ArrayList<>(10);
        YrbFcjyEwmTaxbizml yrbFcjyEwmTaxbizml = new YrbFcjyEwmTaxbizml();
        YrbFcjyEwmRequest yrbFcjyEwmRequest = new YrbFcjyEwmRequest();
        yrbFcjyEwmRequest.setDJXH(bdcSlYjkxxDO.getDjxh());
        yrbFcjyEwmRequest.setHTBH(bdcSwFcjyDTO.getHtbh());
        yrbFcjyEwmRequest.setLRRDM(bdcSwFcjyDTO.getLrrdm());
        yrbFcjyEwmRequest.setNSRMC(bdcSlYjkxxDO.getNsrmc());
        yrbFcjyEwmRequest.setNSRSBH(bdcSlYjkxxDO.getNsrsbh());
        yrbFcjyEwmRequest.setPZXH(bdcSlYjkxxDO.getPzxh());
        yrbFcjyEwmRequest.setSJGSDQ(bdcSwFcjyDTO.getSjgsdq());
        yrbFcjyEwmRequest.setSKSSQQ(bdcSlYjkxxDO.getSkssqq());
        yrbFcjyEwmRequest.setSKSSQZ(bdcSlYjkxxDO.getSkssqz());
        yrbFcjyEwmRequest.setYBTSE(bdcSlYjkxxDO.getYbtse());
        yrbFcjyEwmRequest.setYZPZMXXH(bdcSlYjkxxDO.getYzpzmxxh());
        yrbFcjyEwmRequest.setYZPZZLDM(bdcSlYjkxxDO.getYzpzzldm());
        yrbFcjyEwmRequest.setZGSWSKFJDM(bdcSlYjkxxDO.getZgswskfjdm());
        yrbFcjyEwmRequest.setSJBH(bdcSwFcjyDTO.getSjbh());
        ClassHandleComonUtil.headModelSetDefaultValueToNullFieldXmlElemen(yrbFcjyEwmRequest);
        yrbFcjyEwmRequestList.add(yrbFcjyEwmRequest);
        yrbFcjyEwmTaxbizml.setFcjyewmlist(yrbFcjyEwmRequestList);
        LOGGER.warn("流程实例id{}合同编号{}获取缴税二维码调用A018接口入参{}", bdcSwFcjyDTO.getGzlslid(), bdcSwFcjyDTO.getHtbh(), JSON.toJSONString(yrbFcjyEwmTaxbizml));
        Object ewmResponse = exchangeInterfaceFeignService.swRequestInterface("sw_getFcjyewmJkxx", yrbFcjyEwmTaxbizml);
        LOGGER.warn("流程实例id{}合同编号{}获取缴税二维码调用A018接口返回值{}", bdcSwFcjyDTO.getGzlslid(), bdcSwFcjyDTO.getHtbh(), StringUtils.left(JSON.toJSONString(ewmResponse), 3000));
        if (Objects.nonNull(ewmResponse)) {
            CommonResponse yrbFcjyEwmResponse = JSON.parseObject(JSON.toJSONString(ewmResponse), CommonResponse.class);
            YrbFcjyEwmDTO yrbFcjyEwmDTO = JSON.parseObject(JSON.toJSONString(yrbFcjyEwmResponse.getData()), YrbFcjyEwmDTO.class);
            if (Objects.nonNull(yrbFcjyEwmDTO) && Objects.nonNull(yrbFcjyEwmDTO.getKkewm())) {
                //更新电子税票号
                if(Objects.nonNull(bdcSlYjkxxDO)){
                    bdcSlYjkxxDO.setDzsphm(yrbFcjyEwmDTO.getKkewm().getDzsphm());
                    entityMapper.saveOrUpdate(bdcSlYjkxxDO, bdcSlYjkxxDO.getJkxxid());
                }
                return yrbFcjyEwmDTO.getKkewm();
            }
        }
        return null;
    }

    // 获取税务契税完税凭证【A020】
    @Override
    public Object getQswspz(BdcSwxxQO bdcSwxxQO) {
        YrbQswspzhqDTO yrbQswspzhqDTO = this.swWspz(bdcSwxxQO);
        if(Objects.nonNull(yrbQswspzhqDTO)){
            handleQswspzResponse(yrbQswspzhqDTO,bdcSwxxQO.getXmid(), bdcSwxxQO.getGzlslid(), bdcSwxxQO.getGxlx(), bdcSwxxQO.getQlrlb());
        }
        return yrbQswspzhqDTO;
    }

    /**
     * 获取税务契税完税凭证【A020】
     */
    private YrbQswspzhqDTO swWspz(BdcSwxxQO bdcSwxxQO){
        BdcSwFcjyDTO bdcSwFcjyDTO = this.generateSwRequestParam(bdcSwxxQO);
        YrbQswspzhqRequest yrbQswspzhqRequest = new YrbQswspzhqRequest();
        yrbQswspzhqRequest.setHTBH(bdcSwFcjyDTO.getHtbh());
        yrbQswspzhqRequest.setSJRQ(DateUtils.formateDateToString(bdcSwFcjyDTO.getSjrq(),DateUtils.DATE_FORMAT_2));
        yrbQswspzhqRequest.setZLFCLFBZ(bdcSwFcjyDTO.getZlfclfbz());
        yrbQswspzhqRequest.setSJBH(bdcSwFcjyDTO.getSjbh());
        yrbQswspzhqRequest.setSJGSDQ(bdcSwFcjyDTO.getSjgsdq());
        if(StringUtils.isNotBlank(bdcSwxxQO.getNsrsbh())){
            yrbQswspzhqRequest.setNSRSBH(bdcSwxxQO.getNsrsbh());
        }else{
            yrbQswspzhqRequest.setNSRSBH(this.getNsrsbh(bdcSwxxQO.getGzlslid(), bdcSwxxQO.getQlrlb()));
        }
        YrbQswspzhqTaxbizml yrbQswspzhqTaxbizml = new YrbQswspzhqTaxbizml();
        yrbQswspzhqTaxbizml.setFcjyqswspzlist(yrbQswspzhqRequest);
        LOGGER.info("契税完税凭证接口请求参数：{}", yrbQswspzhqTaxbizml);
        Object response = exchangeInterfaceFeignService.swRequestInterface(bdcSwxxQO.getBeanName(), yrbQswspzhqTaxbizml);
        LOGGER.info("契税完税凭证接口调用成功！合同编号：{},响应内容：{}", bdcSwxxQO.getHtbh(), StringUtils.left(JSON.toJSONString(response), 4000));
        if(Objects.nonNull(response)){
            BdcCommonResponse bdcCommonResponse = JSON.parseObject(JSON.toJSONString(response), BdcCommonResponse.class);
            if(StringUtils.equals("1",bdcCommonResponse.getCode())){
                YrbQswspzhqDTO yrbQswspzhqDTO = JSON.parseObject(JSON.toJSONString(bdcCommonResponse.getData()), YrbQswspzhqDTO.class);
                return yrbQswspzhqDTO;
            }
        }
        return null;
    }

    // //sw_qslxdhq 契税联系单获取【A021】
    @Override
    public Object getQslxd(BdcSwxxQO bdcSwxxQO) {
        BdcSwFcjyDTO bdcSwFcjyDTO = this.generateSwRequestParam(bdcSwxxQO);
        YrbQslxdhqRequest yrbQslxdhqRequest = new YrbQslxdhqRequest();
        yrbQslxdhqRequest.setHTBH(bdcSwFcjyDTO.getHtbh());
        yrbQslxdhqRequest.setSJRQ(DateUtils.formateDateToString(bdcSwFcjyDTO.getSjrq(),DateUtils.DATE_FORMAT_2));
        yrbQslxdhqRequest.setZLFCLFBZ(bdcSwFcjyDTO.getZlfclfbz());
        yrbQslxdhqRequest.setSJBH(bdcSwFcjyDTO.getHtbh());
        if(StringUtils.isNotBlank(bdcSwxxQO.getNsrsbh())){
            yrbQslxdhqRequest.setNSRSBH(bdcSwxxQO.getNsrsbh());
        }else{
            yrbQslxdhqRequest.setNSRSBH(this.getNsrsbh(bdcSwxxQO.getGzlslid(), bdcSwxxQO.getQlrlb()));
        }
        YrbQslxdhqTaxbizml yrbQslxdhqTaxbizml = new YrbQslxdhqTaxbizml();
        yrbQslxdhqTaxbizml.setFcjyqslxdlist(yrbQslxdhqRequest);
        LOGGER.info("契税完税凭证接口请求参数：{}", yrbQslxdhqTaxbizml);
        Object response = exchangeInterfaceFeignService.requestInterface(bdcSwxxQO.getBeanName(), yrbQslxdhqTaxbizml);
        if(Objects.nonNull(response)){
            BdcCommonResponse bdcCommonResponse = JSON.parseObject(JSON.toJSONString(response), BdcCommonResponse.class);
            if(StringUtils.equals("1",bdcCommonResponse.getCode())){
                YrbQslxdhqDTO yrbQslxdhqDTO = JSON.parseObject(JSON.toJSONString(bdcCommonResponse.getData()), YrbQslxdhqDTO.class);
                LOGGER.info("契税联系单接口调用成功！合同编号：{},响应内容：{}", bdcSwxxQO.getHtbh(), yrbQslxdhqDTO);
                if(!StringUtils.equals(CommonConstantUtils.SW_GXLX_BGX, bdcSwxxQO.getGxlx())){
                    handleQslxdResponse(yrbQslxdhqDTO,bdcSwxxQO.getXmid());
                }else{
                    // 不更新业务数据时，只单独上传文件内容
                    List<YrbFcjysbxxFjxxResponse> qswsfjxxlist  = yrbQslxdhqDTO.getFcjyqslxdlist().getQslxdfjxxlist();
                    if(CollectionUtils.isNotEmpty(qswsfjxxlist)){
                        for (YrbFcjysbxxFjxxResponse yrbFcjysbxxFjxxResponse : qswsfjxxlist) {
                            handleQslxdFjxx(yrbFcjysbxxFjxxResponse, bdcSwxxQO.getGzlslid());
                        }
                    }
                }
                return yrbQslxdhqDTO;
            }
        }
        return null;
    }

    // 上传契税联系单至收件材料
    private void handleQslxdFjxx(YrbFcjysbxxFjxxResponse yrbFcjysbxxFjxxResponse, String gzlslid){
        Map<String,String> yxwj = (Map<String, String>) JSON.parse(yrbFcjysbxxFjxxResponse.getWjsj());
        String base64 = yxwj == null ? StringUtils.EMPTY : yxwj.get("YXWJ");
        if (StringUtils.isNotBlank(base64)) {
            if (!base64.startsWith("data:")) {
                base64 = CommonConstantUtils.BASE64_QZ_PDF + base64;
            }
            try {
                bdcUploadFileUtils.uploadBase64File(base64, gzlslid, qslxdwjjmc, "契税联系单", ".pdf");
            } catch (IOException e) {
                LOGGER.error("当前流程{}上传契税完税凭证附件失败", gzlslid, e);
            }
        }
    }

    @Override
    public void handleQswspzResponse(YrbQswspzhqDTO yrbQswspzhqDTO, String xmid, String gxlx){
        this.handleQswspzResponse(yrbQswspzhqDTO, xmid, "", gxlx, CommonConstantUtils.QLRLB_QLR);
    }

    private void handleQswspzResponse(YrbQswspzhqDTO yrbQswspzhqDTO, String xmid, String gzlslid, String gxlx, String qlrlb){
        if (StringUtils.isBlank(qlrlb)) {
            qlrlb = CommonConstantUtils.QLRLB_QLR;
        }
        YrbYrbQswspzhqResponse fcjyqswspzlist = yrbQswspzhqDTO.getFcjyqswspzlist();
        String hsxxid = UUIDGenerator.generate16();
        //附件信息
        List<YrbFcjysbxxFjxxResponse> qswsfjxxlist  = yrbQswspzhqDTO.getQswsfjxxlist();
        List<BdcSlHsxxMxDO> bdcSlHsxxMxDOList = new ArrayList<>(10);
        if(CollectionUtils.isNotEmpty(qswsfjxxlist)){
            if(StringUtils.isBlank(gzlslid)){
                BdcXmDO bdcXmDO = bdcXmFeignService.queryBdcXmByXmid(xmid);
                if(bdcXmDO != null){
                    gzlslid = bdcXmDO.getGzlslid();
                }
            }
            for (YrbFcjysbxxFjxxResponse yrbFcjysbxxFjxxResponse : qswsfjxxlist) {
                BdcSlHsxxMxDO bdcSlHsxxMxDO = new BdcSlHsxxMxDO();
                bdcSlHsxxMxDO.setHsxxmxid(UUIDGenerator.generate16());
                bdcSlHsxxMxDO.setHsxxid(hsxxid);
                bdcSlHsxxMxDO.setFjid(yrbFcjysbxxFjxxResponse.getFjid());
                bdcSlHsxxMxDO.setFjlx(yrbFcjysbxxFjxxResponse.getFjlx());
                bdcSlHsxxMxDOList.add(bdcSlHsxxMxDO);
                Map<String,String> yxwj = (Map<String, String>) JSON.parse(yrbFcjysbxxFjxxResponse.getWjsj());
                String base64 = yxwj == null?StringUtils.EMPTY:yxwj.get("YXWJ");
                // 上传文件至大云
                if (StringUtils.isNotBlank(base64)) {
                    if (!base64.startsWith("data:")) {
                        base64 = CommonConstantUtils.BASE64_QZ_PDF + base64;
                    }
                    try {
                        bdcUploadFileUtils.uploadBase64File(base64, gzlslid, qswspzwjjmc, "", ".pdf");
                    } catch (IOException e) {
                        LOGGER.error("当前流程{}上传契税完税凭证附件失败", gzlslid, e);
                    }
                }
            }
            if(!StringUtils.equals(CommonConstantUtils.SW_GXLX_BGX, gxlx)){
                Example example = new Example(BdcSlHsxxDO.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("xmid", xmid);
                criteria.andEqualTo("hsxxlx", "1");
                List<BdcSlHsxxDO> bdcSlHsxxDOList = entityMapper.selectByExample(example);
                BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
                // 添加核税信息
                if(CollectionUtils.isNotEmpty(bdcSlHsxxDOList)){
                    bdcSlHsxxDO = bdcSlHsxxDOList.get(0);
                }else{
                    bdcSlHsxxDO.setHsxxid(hsxxid);
                    bdcSlHsxxDO.setXmid(xmid);
                    bdcSlHsxxDO.setSqrlb(CommonConstantUtils.QLRLB_QLR);
                    bdcSlHsxxDO.setSjbh(fcjyqswspzlist.getSjbh());
                    bdcSlHsxxDO.setHtbh(fcjyqswspzlist.getHtbh());
                    bdcSlHsxxDO.setWsfs(fcjyqswspzlist.getWsfs());
                    bdcSlHsxxDO.setHsxxlx("1");
                    entityMapper.insertSelective(bdcSlHsxxDO);
                }
                // 添加核税明细
                if(CollectionUtils.isNotEmpty(bdcSlHsxxMxDOList)){
                    for (BdcSlHsxxMxDO bdcSlHsxxMxDO : bdcSlHsxxMxDOList) {
                        bdcSlHsxxMxDO.setHsxxid(bdcSlHsxxDO.getHsxxid());
                    }
                    entityMapper.insertBatchSelective(bdcSlHsxxMxDOList);
                }
            }
        }
    }

    @Override
    public void handleQslxdResponse(YrbQslxdhqDTO yrbQslxdhqDTO, String xmid){
        Example example = new Example(BdcSlHsxxDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("xmid", xmid);
        criteria.andEqualTo("hsxxlx", "2");
        List<BdcSlHsxxDO> bdcSlHsxxDOList = entityMapper.selectByExample(example);
        BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
        YrbQslxdhqResponse yrbQslxdhqDTOFcjyqslxdlist = yrbQslxdhqDTO.getFcjyqslxdlist();
        if(CollectionUtils.isNotEmpty(bdcSlHsxxDOList)){
            bdcSlHsxxDO = bdcSlHsxxDOList.get(0);
        }else{
            bdcSlHsxxDO.setHsxxid(UUIDGenerator.generate16());
            bdcSlHsxxDO.setXmid(xmid);
            bdcSlHsxxDO.setSqrlb(CommonConstantUtils.QLRLB_QLR);
            bdcSlHsxxDO.setSjbh(yrbQslxdhqDTOFcjyqslxdlist.getSjbh());
            bdcSlHsxxDO.setHtbh(yrbQslxdhqDTOFcjyqslxdlist.getHtbh());
            bdcSlHsxxDO.setHsxxlx("2");
            entityMapper.insertSelective(bdcSlHsxxDO);
        }

        List<BdcSlHsxxMxDO> bdcSlHsxxMxDOList = new ArrayList<>(8);
        //附件信息
        List<YrbFcjysbxxFjxxResponse> qswsfjxxlist  = yrbQslxdhqDTOFcjyqslxdlist.getQslxdfjxxlist();
        if(CollectionUtils.isNotEmpty(qswsfjxxlist)){
            BdcXmDO bdcXmDO = bdcXmFeignService.queryBdcXmByXmid(xmid);
            String gzlslid = "";
            if(bdcXmDO != null){
                gzlslid = bdcXmDO.getGzlslid();
            }
            for (YrbFcjysbxxFjxxResponse yrbFcjysbxxFjxxResponse : qswsfjxxlist) {
                BdcSlHsxxMxDO bdcSlHsxxMxDO = new BdcSlHsxxMxDO();
                bdcSlHsxxMxDO.setHsxxmxid(UUIDGenerator.generate16());
                bdcSlHsxxMxDO.setHsxxid(bdcSlHsxxDO.getHsxxid());
                bdcSlHsxxMxDO.setFjid(yrbFcjysbxxFjxxResponse.getFjid());
                bdcSlHsxxMxDO.setFjlx(yrbFcjysbxxFjxxResponse.getFjlx());
                bdcSlHsxxMxDOList.add(bdcSlHsxxMxDO);
                this.handleQslxdFjxx(yrbFcjysbxxFjxxResponse, gzlslid);
            }
            if (CollectionUtils.isNotEmpty(bdcSlHsxxMxDOList)) {
                entityMapper.insertBatchSelective(bdcSlHsxxMxDOList);
            }
        }
    }

    /**
     * @param gzlslid@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 接口5推确认结果
     * @date : 2022/9/21 11:48
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void qrswjg(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            //接口入参ythywid qxdm_gzlslid
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
            if (Objects.nonNull(bdcSlJbxxDO)) {
                String ythywid = bdcSlJbxxDO.getQxdm() + "_" + gzlslid;
                Map paramMap = new HashMap(1);
                paramMap.put("ythywid", ythywid);
                LOGGER.info("一窗受理业务调用查询税务确认结果入参{}", paramMap);
                Object response = exchangeInterfaceFeignService.requestInterface("btsw_sendHsxxqr", paramMap);
                LOGGER.info("流程实例id{}一窗受理业务调用查询税务确认结果返回值{}", gzlslid, JSON.toJSONString(response));
                if (Objects.nonNull(response)) {
                    Map resultMap = (Map) response;
                    String code = MapUtils.getString(resultMap, "code", "");
                    Map data = MapUtils.getMap(resultMap, "data", null);
                    //返回1 表示调用成功，直接调用接口10
                    if (StringUtils.equals("1", code)) {
                        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
                        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                            bdcSlXmDOList = bdcSlXmDOList.stream().filter(bdcSlXmDO -> !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcSlXmDO.getQllx())).collect(Collectors.toList());
                            for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                                //删除已交款信息
                                Example example = new Example(BdcSlYjkxxDO.class);
                                example.createCriteria().andEqualTo("xmid", bdcSlXmDO.getXmid());
                                entityMapper.deleteByExampleNotNull(example);
                                //1.保存接口返回值到bdc_sl_yjkxx表
                                JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(data.get("jkxx")));
                                for (Object object : jsonArray) {
                                    Map map = (Map) object;
                                    BdcSlYjkxxDO bdcSlYjkxxDO = new BdcSlYjkxxDO();
                                    bdcSlYjkxxDO.setXmid(bdcSlXmDO.getXmid());
                                    bdcSlYjkxxDO.setJkxxid(UUIDGenerator.generate16());
                                    bdcSlYjkxxDO.setQlrlb(StringUtils.equals(CommonConstantUtils.QLRLB_QLR, MapUtils.getString(map, "zrfcsfbz", "")) ? CommonConstantUtils.QLRLB_QLR : CommonConstantUtils.QLRLB_YWR);
                                    bdcSlYjkxxDO.setPzxh(MapUtils.getString(map, "pzxh", ""));
                                    bdcSlYjkxxDO.setDzsphm(MapUtils.getString(map, "dzsphm", ""));
                                    entityMapper.insertSelective(bdcSlYjkxxDO);
                                }
                            }
                            List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxService.listBdcSlJyxxByXmid(bdcSlXmDOList.get(0).getXmid());
                            if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
                                swWsxx(gzlslid, bdcSlJyxxDOList.get(0).getHtbh());
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param gzlslid
     * @param htbh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取税务完税信息
     * @date : 2022/9/21 14:05
     */
    @Override
    public void swWsxx(String gzlslid, String htbh) {
        if (StringUtils.isNoneBlank(gzlslid, htbh)) {
            //查询当前流程的权利人证件号，取一个即可
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                //过滤非限制权力的信息
                bdcSlXmDOList = bdcSlXmDOList.stream().filter(bdcSlXmDO -> !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcSlXmDO.getQllx())).collect(Collectors.toList());
                String qlrZjh = getQlrZjh(bdcSlXmDOList.get(0).getXmid(),CommonConstantUtils.QLRLB_QLR);
                if (StringUtils.isNotBlank(qlrZjh)) {
                    BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
                    if (Objects.nonNull(bdcSlJbxxDO)) {
                        //查询接口入参
                        Map paramMap = new HashMap(3);
                        paramMap.put("htbh", htbh);
                        paramMap.put("csfsfzjhm", qlrZjh);
                        if (MapUtils.isNotEmpty(qxdmdzMap)) {
                            paramMap.put("xzqhszDm", qxdmdzMap.get(bdcSlJbxxDO.getQxdm()));
                        } else {
                            paramMap.put("xzqhszDm", bdcSlJbxxDO.getQxdm());
                        }
                        LOGGER.info("当前流程{}获取税务完税信息接口入参{}", gzlslid, JSON.toJSONString(paramMap));
                        Object response = exchangeInterfaceFeignService.requestInterface("btsw_yhyjjf", paramMap);
                        LOGGER.info("当前流程{}获取税务信息返回值{}", gzlslid, JSON.toJSONString(response));
                        if (Objects.nonNull(response)) {
                            Map resultMap = (Map) response;
                            Object dataObj = resultMap.get("data");
                            Map dataMap = (Map) dataObj;
                            List<YcslYhdkkxxData> ycslYhdkkxxDataList = JSON.parseArray(JSON.toJSONString(dataMap.get("yhdkkxxData")), YcslYhdkkxxData.class);
                            if (CollectionUtils.isNotEmpty(ycslYhdkkxxDataList)) {
                                for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                                    //删除之前的核税信息，重新新增
                                    bdcSlHsxxService.deleteSwxx(bdcSlXmDO.getXmid());
                                    List<BdcSlHsxxDO> bdcSlHsxxDOList = new ArrayList<>(ycslYhdkkxxDataList.size());
                                    for (YcslYhdkkxxData ycslYhdkkxxData : ycslYhdkkxxDataList) {
                                        BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
                                        bdcSlHsxxDO.setHsxxid(UUIDGenerator.generate16());
                                        bdcSlHsxxDO.setHtbh(ycslYhdkkxxData.getHtbh());
                                        bdcSlHsxxDO.setJypzh(ycslYhdkkxxData.getDjxh());
                                        bdcSlHsxxDO.setNsrmc(ycslYhdkkxxData.getNsrmc());
                                        bdcSlHsxxDO.setNsrsbh(ycslYhdkkxxData.getNsrsbh());
                                        bdcSlHsxxDO.setHdjsjg(ycslYhdkkxxData.getYbtse());
                                        bdcSlHsxxDO.setYnsehj(ycslYhdkkxxData.getYbtse());
                                        bdcSlHsxxDO.setSjyzhj(ycslYhdkkxxData.getYbtse());
                                        bdcSlHsxxDO.setJkrksj(new Date());
                                        bdcSlHsxxDO.setXmid(bdcSlXmDO.getXmid());
                                        if (!StringUtils.equals("1", ycslYhdkkxxData.getZrfcsfbz())) {
                                            bdcSlHsxxDO.setSqrlb("2");
                                        } else {
                                            bdcSlHsxxDO.setSqrlb("1");
                                        }
                                        bdcSlHsxxDO.setSphm(ycslYhdkkxxData.getDzsphm());
                                        bdcSlHsxxDO.setSwjgdm(ycslYhdkkxxData.getSkssjgdm());
                                        bdcSlHsxxDOList.add(bdcSlHsxxDO);
                                    }
                                    entityMapper.batchSaveSelective(bdcSlHsxxDOList);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 一窗受理获取税票
     * @date : 2022/9/21 14:23
     */
    @Override
    public String getSwSp(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return null;
        }
        String code = "";
        //接口5 返回的凭证序号存在bdc_sl_yjkxx 表
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            List<BdcSlYjkxxDO> bdcSlYjkxxDOList = this.queryBdcSlYjkxxByXmid(bdcSlXmDOList.get(0).getXmid());
            if (CollectionUtils.isNotEmpty(bdcSlYjkxxDOList)) {
                Map pzxhMap = new HashMap(1);
                for (BdcSlYjkxxDO bdcSlYjkxxDO : bdcSlYjkxxDOList) {
                    if (StringUtils.isNotBlank(bdcSlYjkxxDO.getPzxh()) && !pzxhMap.containsKey(bdcSlYjkxxDO.getPzxh())) {
                        Map paramMap = new HashMap(1);
                        paramMap.put("pzxh", bdcSlYjkxxDO.getPzxh());
                        pzxhMap.put(bdcSlYjkxxDO.getPzxh(), bdcSlYjkxxDO.getPzxh());
                        Object response = exchangeInterfaceFeignService.requestInterface("btsw_kjDzspxx", paramMap);
                        LOGGER.info("当前流程实例id{}获取税票结果{}", gzlslid, JSON.toJSONString(response));
                        if (Objects.nonNull(response)) {
                            Map resultMap = (Map) response;
                            Object data = resultMap.get("data");
                            code = MapUtils.getString(resultMap, "code");
                            String dzspPdf = MapUtils.getString((Map) data, "dzspPdf", "");
                            if (StringUtils.isNotBlank(dzspPdf)) {
                                if (!dzspPdf.startsWith("data:")) {
                                    dzspPdf = CommonConstantUtils.BASE64_QZ_PDF + dzspPdf;
                                }
                                try {
                                    bdcUploadFileUtils.uploadBase64File(dzspPdf, gzlslid, "电子税票", "", ".pdf");
                                } catch (IOException e) {
                                    LOGGER.error("当前流程{}上传申报单附件失败", gzlslid, e);
                                }
                            }
                        }
                    }
                }
            } else {
                throw new AppException("当前流程实例id" + gzlslid + "未获取到税务确认结果");
            }
        }
        return code;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object getYhsfxx(BdcSwxxQO bdcSwxxQO) {
        String htbh = bdcSwxxQO.getHtbh();
        String nsrsbh = bdcSwxxQO.getNsrsbh();
        String gzlslid = bdcSwxxQO.getGzlslid();
        String qlrlb = bdcSwxxQO.getQlrlb();
        String sjgsdq = bdcSwxxQO.getSjgsdq();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        List<BdcYhsfxxDTO> bdcYhSfxxDTOList = new ArrayList<>(16);
        int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        if (lclx == 2 || lclx == 4) {
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                bdcXmDOList = bdcXmDOList.stream().filter(xmDO -> ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, xmDO.getQllx())).collect(Collectors.toList());
            }
        }
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            // 作废原有的税费信息
            if (StringUtils.isNotBlank(bdcXmDO.getXmid()) && StringUtils.isNotBlank(qlrlb)) {
                List<BdcSlHsxxDO> bdcSlHsxxDOListAll = new ArrayList<>(4);
                // 查询hsxxlx为0的数据
                BdcSlHsxxQO param = new BdcSlHsxxQO();
                param.setXmid(bdcXmDO.getXmid());
                param.setSqrlb(qlrlb);
                param.setHsxxlx("0");
                List<BdcSlHsxxDO> bdcSlHsxxDOList1 = bdcSlHsxxService.listBdcSlHsxxByHsxxQo(param);
                if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList1)) {
                    bdcSlHsxxDOListAll.addAll(bdcSlHsxxDOList1);
                }
                // 查询hsxxlx为空的数据
                Example example = new Example(BdcSlHsxxDO.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("xmid", bdcXmDO.getXmid());
                criteria.andEqualTo("sqrlb", qlrlb);
                criteria.andIsNull("hsxxlx");
                List<BdcSlHsxxDO> bdcSlHsxxDOList2 = entityMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList2)) {
                    bdcSlHsxxDOListAll.addAll(bdcSlHsxxDOList2);
                }
                if (CollectionUtils.isNotEmpty(bdcSlHsxxDOListAll)) {
                    bdcSlHsxxDOListAll.forEach(hsxx -> {
                        if (CommonConstantUtils.SF_S_DM.equals(hsxx.getWszt())) {
                            throw new AppException("核税信息ID:" + hsxx.getHsxxid() + "已完税，无法删除原有税费信息");
                        }
                    });
                    LOGGER.info("删除原有一次支付税费信息，项目ID：{}，权利人类别：{}，核税信息类型：null和0", bdcXmDO.getXmid(), qlrlb);
                    bdcSlHsxxService.deleteBdcSlHsxx(param);
                    entityMapper.deleteByExample(example);
                }
            }

            // 获取请求参数信息
            BdcSwFcjyDTO bdcSwFcjyDTO = this.generateSwRequestParam(bdcSwxxQO);
            FcjydjkxxhqTaxbizml fcjydjkxxhqTaxbizml = new FcjydjkxxhqTaxbizml();
            FcjydjkxxhqRequest fcjydjkxxhqRequest = new FcjydjkxxhqRequest();

            fcjydjkxxhqRequest.setSJBH(bdcSwFcjyDTO.getHtbh());
            fcjydjkxxhqRequest.setSJRQ(DateUtils.formateTime(bdcSwFcjyDTO.getSjrq(), DateUtils.DATE_FORMAT_2));
            // 淮安需要通过页面传输数据归属地区
            if (StringUtils.isNotBlank(sjgsdq)) {
                fcjydjkxxhqRequest.setSJGSDQ(sjgsdq);
            } else {
                fcjydjkxxhqRequest.setSJGSDQ(bdcSwFcjyDTO.getSjgsdq());
            }
            fcjydjkxxhqRequest.setHTBH(bdcSwFcjyDTO.getHtbh());
            fcjydjkxxhqRequest.setZLFCLFBZ(bdcSwFcjyDTO.getZlfclfbz());
            if (StringUtils.isNotBlank(nsrsbh)) {
                fcjydjkxxhqRequest.setNSRSBH(nsrsbh);
            } else {
                List<BdcQlrDO> bdcQlrDOS = this.getQlrxx(bdcXmDO.getXmid(), qlrlb);
                if (CollectionUtils.isNotEmpty(bdcQlrDOS)) {
                    String zjh = bdcQlrDOS.get(0).getZjh();
                    fcjydjkxxhqRequest.setNSRSBH(zjh);
                }
            }

            fcjydjkxxhqTaxbizml.setFcjydjkxxhqRequest(fcjydjkxxhqRequest);
            LOGGER.info("查询银行税费信息数据接口请求参数：{}", fcjydjkxxhqTaxbizml);
            Object response = exchangeInterfaceFeignService.swRequestInterface("sw_fcjydjkxxhq", fcjydjkxxhqTaxbizml);
            LOGGER.info("查询银行税费信息数据接口返回：{}", response);
            if (Objects.isNull(response)) {
                throw new AppException("查询银行税费信息数据接口返回为空");
            }
            BdcCommonResponse bdcCommonResponse = JSON.parseObject(JSON.toJSONString(response), BdcCommonResponse.class);
            if(StringUtils.equals(BdcCommonResponse.SUCCESS_CODE, bdcCommonResponse.getCode())) {
                FcjydjkxxhqDTO fcjydjkxxhqDTO = JSON.parseObject(JSON.toJSONString(bdcCommonResponse.getData()), FcjydjkxxhqDTO.class);
                FcjydjkxxhqResponse jkxxgrid = fcjydjkxxhqDTO.getJkxxgrid();
                if (Objects.isNull(jkxxgrid)) {
                    throw new AppException("获取税费信息异常，返回信息为空");
                }
                String fhxx = jkxxgrid.getFhxx();
                JkxxgridlbResponse jkxxgridlb = jkxxgrid.getJkxxgridlb();
                if (Objects.isNull(jkxxgridlb)) {
                    throw new AppException("获取税费信息异常，返回信息：" + fhxx);
                }

                List<JkxxResponse> jkxxList = jkxxgridlb.getJkxx();
                if (CollectionUtils.isEmpty(jkxxList)) {
                    throw new AppException("获取税费信息异常，返回信息：" + fhxx);
                }

                // 税费信息页面展示
                for (JkxxResponse jkxxResponse : jkxxList) {
                    BdcYhsfxxDTO bdcYhsfxxDTO = new BdcYhsfxxDTO();
                    BeanUtils.copyProperties(jkxxResponse, bdcYhsfxxDTO);
                    bdcYhSfxxDTOList.add(bdcYhsfxxDTO);
                }
                // 获取税费缴款信息后，按税费三要素（sphm、swjgdm、nsrsbh）进行分组，分组后合并应缴税额
                this.groupSfJkxxAndSaveBdcSlHsxx(jkxxList, qlrlb, bdcXmDO.getXmid(), htbh);
            } else {
                throw new AppException("获取税费信息异常，返回信息：" + bdcCommonResponse.getMessage());
            }
        }
        return bdcYhSfxxDTOList;
    }

    /**
     * 获取税费缴款信息后，按税费三要素（sphm、swjgdm、nsrsbh）进行分组，分组后合并应缴税额
     */
    private void groupSfJkxxAndSaveBdcSlHsxx(List<JkxxResponse> jkxxList, String qlrlb, String xmid, String htbh){
        Map<String, List<JkxxResponse>> result = jkxxList.stream().collect(Collectors.groupingBy(t-> t.getDzsphm()+ t.getSkssswjgdm()+t.getNsrsbh()));
        List<BdcSlHsxxDO> bdcSlHsxxDOList = new ArrayList<>(jkxxList.size());
        for(String key : result.keySet()){
            List<JkxxResponse> jkxxResponseList = result.get(key);
            if(CollectionUtils.isNotEmpty(jkxxResponseList)){
                Double sehj = jkxxResponseList.stream().filter(t -> t.getYbtse() != null).mapToDouble(JkxxResponse::getYbtse).sum();
                JkxxResponse jkxx = jkxxResponseList.get(0);
                // 新增税费信息
                BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
                bdcSlHsxxDO.setHsxxid(UUIDGenerator.generate16());
                bdcSlHsxxDO.setSphm(jkxx.getDzsphm());
                bdcSlHsxxDO.setSwjgdm(jkxx.getSkssswjgdm());
                bdcSlHsxxDO.setNsrsbh(jkxx.getNsrsbh());
                bdcSlHsxxDO.setNsrmc(jkxx.getNsrmc());
                bdcSlHsxxDO.setYnsehj(sehj);
                bdcSlHsxxDO.setSjyzhj(sehj);
                bdcSlHsxxDO.setSqrlb(qlrlb);
                bdcSlHsxxDO.setXmid(xmid);
                bdcSlHsxxDO.setSjbh(htbh);
                bdcSlHsxxDO.setJypzh(htbh);
                bdcSlHsxxDO.setJssksj(new Date());
                bdcSlHsxxDO.setHsxxlx("0");
                bdcSlHsxxDO.setWszt(CommonConstantUtils.SF_F_DM);
                bdcSlHsxxDOList.add(bdcSlHsxxDO);
            }
        }
        if(CollectionUtils.isNotEmpty(bdcSlHsxxDOList)){
            entityMapper.batchSaveSelective(bdcSlHsxxDOList);
        }
    }

    /**
     * 获取权利人信息
     * @param xmid  项目ID
     * @param qlrlb  权利人类别
     */
    private List<BdcQlrDO> getQlrxx(String xmid, String qlrlb){
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        bdcQlrQO.setQlrlb(qlrlb);
        return bdcQlrFeignService.listBdcQlr(bdcQlrQO);
    }

    /*
     * 一人办件推送附件信息
     * */
    private void pushFjxx(String gzlslid, String fwlx, String beanName) {
        try{
            BdcSwxxQO bdcSwxxQO = new BdcSwxxQO();
            bdcSwxxQO.setGzlslid(gzlslid);
            bdcSwxxQO.setFwlx(fwlx);
            BdcSwFcjyDTO bdcSwFcjyDTO = this.generateSwRequestParam(bdcSwxxQO);
            YrbYxzlxxjsList yrbYxzlxxjsList = new YrbYxzlxxjsList();
            YrbYxzlTaxbizml yrbYxzlTaxbizml = new YrbYxzlTaxbizml();
            yrbYxzlxxjsList.setFWUUID(bdcSwFcjyDTO.getFwuuid());
            yrbYxzlxxjsList.setSJRQ(bdcSwFcjyDTO.getSjrq());
            yrbYxzlxxjsList.setSJGSDQ(bdcSwFcjyDTO.getSjgsdq());
            yrbYxzlxxjsList.setZLFCLFBZ(bdcSwFcjyDTO.getZlfclfbz());
            yrbYxzlxxjsList.setJYUUID(bdcSwFcjyDTO.getJyuuid());
            yrbYxzlxxjsList.setSJBH(bdcSwFcjyDTO.getSjbh());
            yrbYxzlxxjsList.setHTBH(bdcSwFcjyDTO.getHtbh());
            yrbYxzlxxjsList.setYwwjxxList(getFjxx(gzlslid, "", "", fwlx));
            ClassHandleComonUtil.headModelSetDefaultValueToNullFieldXmlElemen(yrbYxzlxxjsList);
            yrbYxzlTaxbizml.setYrbYxzlxxjsList(yrbYxzlxxjsList);
            LOGGER.warn("当前流程实例id{},一人办件流程推送附件材料接口传参{}", gzlslid, JSON.toJSONString(yrbYxzlxxjsList));
            Object response = exchangeInterfaceFeignService.swRequestInterface(beanName, yrbYxzlTaxbizml);
            LOGGER.warn("当前流程实例id{}一人办件推送税务附件结果{}", gzlslid, JSON.toJSONString(response));
        }catch(Exception e){
            LOGGER.error("推送附件信息失败，错误信息：{}", e.getMessage());
        }
    }

    // 获取附件信息
    private List<YrbYwwjxx> getFjxx(String gzlslid, String clmc, String zldm, String fwlx) {
        List<YrbYwwjxx> yrbYwwjxxList = new ArrayList<>(1);
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjcl(gzlslid, clmc);
        if(CommonConstantUtils.SYSTEM_VERSION_CZ.equals(dataversion)){
            bdcSlSjclDOList = bdcSlSjclDOList.stream()
                    .filter(t-> StringUtils.isNotBlank(t.getSqbm()) && StringUtils.contains(t.getSqbm(),CommonConstantUtils.SJCL_SQBM_SW_MC))
                    .collect(Collectors.toList());
        }

        if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
            for (BdcSlSjclDO bdcSlSjclDO : bdcSlSjclDOList) {
                //查找大云附件信息
                List<StorageDto> storageDtoList = storageClient.listAllSubsetStorages(bdcSlSjclDO.getWjzxid(), "", 1, 1, 0, null);
                if (CollectionUtils.isNotEmpty(storageDtoList)) {
                    for (StorageDto storageDto : storageDtoList) {
                        YrbYwwjxx yrbYwwjxx = new YrbYwwjxx();
                        yrbYwwjxx.setSFBB("1");
                        yrbYwwjxx.setYXWJBH(storageDto.getId());
                        String url = storageDto.getDownUrl();
                        if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(swFjIpPort)) {
                            // 大云附件地址例如：http://192.168.2.87:8030/storage/rest/files/download/ff8080817399496301740064a45a0363
                            url = "http://" + swFjIpPort + url.substring(url.indexOf("/storage"));
                        }
                        yrbYwwjxx.setYXWJDZ(url);
                        yrbYwwjxx.setYXWJFILENAME(bdcSlSjclDO.getClmc());
                        yrbYwwjxx.setYXWJMC(bdcSlSjclDO.getClmc());
                        yrbYwwjxx.setYXWJLX("PDF");
                        if (StringUtils.isNotBlank(zldm)) {
                            yrbYwwjxx.setZLDM(zldm);
                        } else {
                            BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
                            if(StringUtils.equals(fwlx, "2")){ //
                                bdcZdDsfzdgxDO.setZdbs("DSF_ZD_YRBZLDM");
                            }else{
                                bdcZdDsfzdgxDO.setZdbs("DSF_ZD_YRBZLDM_ZLF");
                            }
                            bdcZdDsfzdgxDO.setBdczdz(bdcSlSjclDO.getClmc());
                            bdcZdDsfzdgxDO.setDsfxtbs("sw");
                            BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
                            if (result != null) {
                                yrbYwwjxx.setZLDM(result.getDsfzdz());
                            }
                        }
                        ClassHandleComonUtil.headModelSetDefaultValueToNullFieldXmlElemen(yrbYwwjxx);
                        yrbYwwjxxList.add(yrbYwwjxx);
                    }
                }
            }
        }
        return yrbYwwjxxList;
    }


    private List<TsswDataFjclDTO> getTsswFjxx(String gzlslid){
        List<TsswDataFjclDTO> tsswDataFjclDTOList = new ArrayList<>(1);
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjcl(gzlslid, "");
        if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
            for (BdcSlSjclDO bdcSlSjclDO : bdcSlSjclDOList) {
                //查找大云附件信息
                List<StorageDto> storageDtoList = storageClient.listAllSubsetStorages(bdcSlSjclDO.getWjzxid(), "", 1, 1, 0, null);
                if (CollectionUtils.isNotEmpty(storageDtoList)) {
                    for (StorageDto storageDto : storageDtoList) {
                        TsswDataFjclDTO tsswDataFjclDTO = new TsswDataFjclDTO();
                        tsswDataFjclDTO.setClmc(bdcSlSjclDO.getClmc());
                        String url = storageDto.getDownUrl();
                        if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(swFjIpPort)) {
                            // 大云附件地址例如：http://192.168.2.87:8030/storage/rest/files/download/ff8080817399496301740064a45a0363
                            url = "http://" + swFjIpPort + url.substring(url.indexOf("/storage"));
                        }
                        tsswDataFjclDTO.setWjzxid(storageDto.getId());
                        tsswDataFjclDTO.setFjlx(String.valueOf(bdcSlSjclDO.getSjlx()));
                        tsswDataFjclDTO.setXzdz(url);
                        ClassHandleComonUtil.headModelSetDefaultValueToNullFieldXmlElemen(tsswDataFjclDTO);
                        tsswDataFjclDTOList.add(tsswDataFjclDTO);
                    }
                }
            }
        }
        return tsswDataFjclDTOList;
    }

    /**
     * 契税完税凭证获取【A009】， 并将 pdf 文件上传大云
     */
    private CommonResponse getSfjnpz(String gzlslid, String fwlx, String htbh) {
        BdcSwxxQO bdcSwxxQO = new BdcSwxxQO(gzlslid, htbh, fwlx);
        BdcSwFcjyDTO bdcSwFcjyDTO = this.generateSwRequestParam(bdcSwxxQO);

        //调用A009接口
        YrbQswsxxhqTzxbizml yrbQswsxxhqTzxbizml = new YrbQswsxxhqTzxbizml();
        YrbQswsxxhqRequest yrbQswsxxhqRequest = new YrbQswsxxhqRequest();
        yrbQswsxxhqRequest.setFWUUID(bdcSwFcjyDTO.getFwuuid());
        yrbQswsxxhqRequest.setHTBH(bdcSwFcjyDTO.getHtbh());
        yrbQswsxxhqRequest.setJYUUID(bdcSwFcjyDTO.getJyuuid());
        yrbQswsxxhqRequest.setSJBH(bdcSwFcjyDTO.getSjbh());
        yrbQswsxxhqRequest.setSJGSDQ(bdcSwFcjyDTO.getSjgsdq());
        yrbQswsxxhqRequest.setZLFCLFBZ(bdcSwFcjyDTO.getZlfclfbz());
        yrbQswsxxhqRequest.setSJRQ(bdcSwFcjyDTO.getSjrq());

        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            BdcSlHsxxDO bdcSlHsxxQO = new BdcSlHsxxDO();
            bdcSlHsxxQO.setXmid(bdcSlXmDOList.get(0).getXmid());
            bdcSlHsxxQO.setSqrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxService.listBdcSlHsxx(bdcSlHsxxQO);
            if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                yrbQswsxxhqRequest.setNSRMC(bdcSlHsxxDOList.get(0).getNsrmc());
                yrbQswsxxhqRequest.setNSRSBH(bdcSlHsxxDOList.get(0).getNsrsbh());
            }
            yrbQswsxxhqRequest.setTDFWDZ(bdcSlXmDOList.get(0).getZl());
        }

        ClassHandleComonUtil.headModelSetDefaultValueToNullFieldXmlElemen(yrbQswsxxhqRequest);
        yrbQswsxxhqTzxbizml.setFcjyskxxlist(yrbQswsxxhqRequest);
        LOGGER.warn("当前流程{}获取税费缴纳凭证接口入参{}", gzlslid, JSON.toJSONString(yrbQswsxxhqTzxbizml));
        Object response = exchangeInterfaceFeignService.swRequestInterface("sw_qswsxxhq", yrbQswsxxhqTzxbizml);
        LOGGER.warn("当前流程{}获取税费缴纳凭证接口返回值{}", gzlslid, StringUtils.left(JSON.toJSONString(yrbQswsxxhqTzxbizml), 4000));
        if (Objects.nonNull(response)) {
            CommonResponse<YrbQswsxxhqDTO> yrbQswsxxhqResponseDTO = JSON.parseObject(JSON.toJSONString(response), CommonResponse.class);
            YrbQswsxxhqDTO yrbQswsxxhqDTO = JSON.parseObject(JSON.toJSONString(yrbQswsxxhqResponseDTO.getData()), YrbQswsxxhqDTO.class);
            if (CollectionUtils.isNotEmpty(yrbQswsxxhqDTO.getQswsfjxxlist())) {
                for (YrbFcjysbxxFjxxResponse yrbFcjysbxxFjxxResponse : yrbQswsxxhqDTO.getQswsfjxxlist()) {
                    String base64 = yrbFcjysbxxFjxxResponse.getWjsj();
                    if (StringUtils.isNotBlank(base64)) {
                        if (!base64.startsWith("data:")) {
                            base64 = CommonConstantUtils.BASE64_QZ_PDF + base64;
                        }
                        try {
                            bdcUploadFileUtils.uploadBase64File(base64, gzlslid, qspzwjjmc, "", ".pdf");
                        } catch (IOException e) {
                            LOGGER.error("当前流程{}上传申报单附件失败", gzlslid, e);
                        }
                    }
                    return CommonResponse.ok();
                }
            }
        }
        return CommonResponse.fail("未获取到税票信息");
    }

    /**
     * 登记流程时，获取登记流程的税务信息
     */
    private TsswDataDTO handleDjHsxx(String gzlslid, TsswDataDTO tsswDataDTO) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        bdcXmQO.setXmid(tsswDataDTO.getXmid());
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            for(BdcXmDO bdcXmDO : bdcXmDOList){
                // 没有房屋信息时，添加房屋信息
                Double jyjg = 0.0;
                if(Objects.isNull(tsswDataDTO.getBdcSlFwxx())|| StringUtils.isBlank(tsswDataDTO.getBdcSlFwxx().getFwxxid())){
                    BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
                    if(bdcQl instanceof BdcFdcqDO){
                        BdcFdcqDO bdcFdcqDO =(BdcFdcqDO) bdcQl;
                        tsswDataDTO.setBdcFdcqDO(bdcFdcqDO);
                        // 处理面积
                        this.resolveMj(tsswDataDTO, bdcFdcqDO);
                        if(Objects.nonNull(bdcFdcqDO.getJyjg())){
                            jyjg = bdcFdcqDO.getJyjg();
                        }
                        BdcSlFwxxDO bdcSlFwxxDO = new BdcSlFwxxDO();
                        acceptDozerMapper.map(bdcFdcqDO, bdcSlFwxxDO,"fdcqToSlFwxx");
                        acceptDozerMapper.map(bdcXmDO, bdcSlFwxxDO, "bdcXmToSlFwxx");
                        tsswDataDTO.setBdcSlFwxx(bdcSlFwxxDO);
                    }
                }
                // 没有交易信息时，添加交易信息
                if((Objects.isNull(tsswDataDTO.getBdcSlJyxx())|| StringUtils.isBlank(tsswDataDTO.getBdcSlJyxx().getJyxxid()))
                        && StringUtils.isNotBlank(bdcXmDO.getJyhth())){
                    BdcSlJyxxDO bdcSlJyxxDO = new BdcSlJyxxDO();
                    bdcSlJyxxDO.setHtbh(bdcXmDO.getJyhth());
                    bdcSlJyxxDO.setJyje(jyjg);
                }
            }
        }
        return tsswDataDTO;
    }

    /**
     * @param tsswDataDTO 推送税务信息对象
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 特殊处理推送业务信息
     */
    private void tsclTsYwxx(TsswDataDTO tsswDataDTO){
        //上次交易价格
        if(CommonConstantUtils.SYSTEM_VERSION_CZ.equals(dataversion)){
            if(tsswDataDTO.getBdcSlJyxx() != null){
                BdcQl ybdcQl = bdcQllxFeignService.queryBdcYqlxx(tsswDataDTO.getXmid());
                LOGGER.info("查询上一手权利数据：{}",JSONObject.toJSONString(ybdcQl));
                if(ybdcQl instanceof BdcFdcqDO &&((BdcFdcqDO) ybdcQl).getJyjg() != null){
                    tsswDataDTO.getBdcSlJyxx().setQcqsjsje(((BdcFdcqDO) ybdcQl).getJyjg());
                }
            }

            // 常州原产权证号获取项目表中的数据
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(tsswDataDTO.getXmid());
            List<BdcXmDO> bdcXmDOLsit = this.bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOLsit)){
                tsswDataDTO.setBdcxm(bdcXmDOLsit.get(0));
            }
        }
        // 处理婚姻信息和房屋套次
        this.getHyxx(tsswDataDTO);

        // 新需求 处理完后 需要把上次取得价格在共同共有的情况下均分 常州
        if(CommonConstantUtils.SYSTEM_VERSION_CZ.equals(dataversion) && CollectionUtils.isNotEmpty(tsswDataDTO.getSqrSwList())){
            List<BdcSlSqrSwDTO> qlrList = tsswDataDTO.getSqrSwList().stream().filter(sqrSwDTO -> StringUtils.equals(CommonConstantUtils.QLRLB_YWR,sqrSwDTO.getSqrlb())).collect(Collectors.toList());
            if(tsswDataDTO.getBdcSlJyxx().getQcqsjsje() == null){
                qlrList.forEach(BdcSlSqrSwDTO -> BdcSlSqrSwDTO.setQcqsjsje(0d));
            }else if( CollectionUtils.isNotEmpty(qlrList) && tsswDataDTO.getBdcSlJyxx() != null ){
                // 单独所有和共同共有
                if(qlrList.get(0).getGyfs().equals(CommonConstantUtils.GYFS_GTGY) || qlrList.get(0).getGyfs().equals(CommonConstantUtils.GYFS_DDSY)){
                    dealQcqsjsje(qlrList,tsswDataDTO.getBdcSlJyxx().getQcqsjsje(),CommonConstantUtils.GYFS_GTGY);
                }else if(qlrList.get(0).getGyfs().equals(CommonConstantUtils.GYFS_AFGY)){
                    dealQcqsjsje(qlrList,tsswDataDTO.getBdcSlJyxx().getQcqsjsje(),CommonConstantUtils.GYFS_AFGY);
                }else{
                    qlrList.forEach(BdcSlSqrSwDTO -> BdcSlSqrSwDTO.setQcqsjsje(0d));
                }
            }
        }

        if (Objects.nonNull(tsswDataDTO.getBdcFdcqDO())) {
            String qsqszydxDm =dealQsqszydxDm(tsswDataDTO);
            // 房屋类型 住房 Y 非住房 N 取房屋类型
            String sfptzfbz = "N";
            if (Objects.nonNull(tsswDataDTO.getBdcFdcqDO().getFwlx()) && 1 == tsswDataDTO.getBdcFdcqDO().getFwlx()) {
                sfptzfbz = "Y";
            }
            tsswDataDTO.setSfptzfbz(sfptzfbz);
            tsswDataDTO.setQsqszydxDm(qsqszydxDm);
            //契税权属转移用途代码 取ghyt
            tsswDataDTO.setQsqszyytDm(tsswDataDTO.getBdcFdcqDO().getGhyt() != null?tsswDataDTO.getBdcFdcqDO().getGhyt().toString(): "");
        }

    }

    /**
     * @param bdcSlSqrDOList 权利人或义务人集合
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 有曾用名的把曾用名推送
     */
    private void dealcym(List<BdcSlSqrDO> bdcSlSqrDOList) {
        if(tscym){
            if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                List<BdcSlSqrDO> bdcSlSqrCymDOList = new ArrayList<>();
                bdcSlSqrDOList.forEach(bdcSlSqrDO -> {
                    if(StringUtils.isNotBlank(bdcSlSqrDO.getCym())){
                        BdcSlSqrDO bdcSlSqrDOCym = new BdcSlSqrDO();
                        BeanUtils.copyProperties(bdcSlSqrDO,bdcSlSqrDOCym);
                        bdcSlSqrDOCym.setSqrmc(bdcSlSqrDO.getCym());
                        bdcSlSqrCymDOList.add(bdcSlSqrDOCym);
                    }
                });
                if(CollectionUtils.isNotEmpty(bdcSlSqrCymDOList)){
                    bdcSlSqrDOList.addAll(bdcSlSqrCymDOList);
                }
            }
        }
    }

    //南通需求50047：二手房
    //1.合同签订时间是2021年8月31日后的，住房的权属转移对象代码为20107，非住房的权属转移对象代码为20105；
    //2.合同签订时间是2021年8月31日前(包含8月31日)，住房权属转移对象代码为20106，非住房权属转移对象代码为20105。
    public String dealQsqszydxDm(TsswDataDTO tsswDataDTO) {
        if(tsswDataDTO != null){
            if(tsswDataDTO.getBdcSlJyxx() !=null &&(tsswDataDTO.getBdcSlFwxx() != null ||tsswDataDTO.getBdcFdcqDO() != null) &&tsswDataDTO.getBdcSlJyxx().getHtdjsj() !=null){
                Date htdjsj =tsswDataDTO.getBdcSlJyxx().getHtdjsj();
                Integer fwlx =tsswDataDTO.getBdcFdcqDO() != null ?tsswDataDTO.getBdcFdcqDO().getFwlx():tsswDataDTO.getBdcSlFwxx().getFwlx();
                if (Objects.nonNull(fwlx) && 1 == fwlx) {
                    //住房
                    Date compare = DateUtils.formatDate("2021-08-31");
                    if (org.apache.commons.lang3.time.DateUtils.truncatedCompareTo(htdjsj, compare, Calendar.MILLISECOND) > 0) {
                        return "20107";
                    } else {
                        return "20106";
                    }
                } else {
                    return "20105";
                }
            }
        }
        return "";

    }

    /*
     * 设置权属转移用途的取值
     * */
    public void dealQsqszydxDmYrb(TsswDataDTO tsswDataDTO, String zdbs) {
        if (tsswDataDTO != null) {
            if (tsswDataDTO.getBdcSlJyxx() != null && (tsswDataDTO.getBdcSlFwxx() != null || tsswDataDTO.getBdcFdcqDO() != null) && tsswDataDTO.getBdcSlJyxx().getHtdjsj() != null) {
                Date htdjsj = tsswDataDTO.getBdcSlJyxx().getHtdjsj();
                //住房
                Date compare = DateUtils.formatDate("2021-08-31");
                BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
                bdcZdDsfzdgxDO.setBdczdz(String.valueOf(tsswDataDTO.getBdcSlFwxx().getFwyt()));
                bdcZdDsfzdgxDO.setDsfxtbs("SW");
                if (org.apache.commons.lang3.time.DateUtils.truncatedCompareTo(htdjsj, compare, Calendar.MILLISECOND) > 0) {
                    // 9.1之后的对照
                    bdcZdDsfzdgxDO.setZdbs(zdbs);
                } else {
                    // 9.1 之前的
                    bdcZdDsfzdgxDO.setZdbs(zdbs + "_OLD");
                }
                BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
                if (Objects.nonNull(result)) {
                    tsswDataDTO.setQsqszydxDm(result.getDsfzdz());
                }
            }
        }
    }

    /*
     * 设置权属转移用途的取值
     * */
    public void dealQszyytDm(TsswDataDTO tsswDataDTO) {
        if (tsswDataDTO != null) {
            if (tsswDataDTO.getBdcSlJyxx() != null && (tsswDataDTO.getBdcSlFwxx() != null || tsswDataDTO.getBdcFdcqDO() != null) && tsswDataDTO.getBdcSlJyxx().getHtdjsj() != null) {
                Date htdjsj = tsswDataDTO.getBdcSlJyxx().getHtdjsj();
                //住房
                Date compare = DateUtils.formatDate("2021-08-31");
                BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
                bdcZdDsfzdgxDO.setBdczdz(String.valueOf(tsswDataDTO.getBdcSlFwxx().getFwyt()));
                bdcZdDsfzdgxDO.setDsfxtbs("SW");
                if (org.apache.commons.lang3.time.DateUtils.truncatedCompareTo(htdjsj, compare, Calendar.MILLISECOND) > 0) {
                    // 9.1之后的对照
                    bdcZdDsfzdgxDO.setZdbs("DSF_ZD_QSZYYT");
                } else {
                    // 9.1 之前的
                    bdcZdDsfzdgxDO.setZdbs("DSF_ZD_QSZYYT_OLD");
                }
                BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
                if (Objects.nonNull(result)) {
                    tsswDataDTO.setQsqszyytDm(result.getDsfzdz());
                }
            }
        }
    }

    /**
     * 根据url下载申报提醒函附件，上传大云
     *
     * @param responseDTO 税务明细返回参数
     * @param slbh        受理编号
     */
    private void uploadSbtxh(QuerySwxxResponseDTO responseDTO, String slbh) {
        List<QuerySwxxHsxxDTO> querySwxxHsxxDTOList = responseDTO.getHsxxList();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(slbh);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new AppException("未查询到项目信息");
        }
        String gzlslid = bdcXmDOList.get(0).getGzlslid();
        // 申报提醒函
        if (CollectionUtils.isNotEmpty(querySwxxHsxxDTOList)) {
            // 申报提醒函附件地址集合，买方和卖方的，一个流程两个附件
            List<String> urlList = new ArrayList<>();
            for (QuerySwxxHsxxDTO querySwxxHsxxDTO : querySwxxHsxxDTOList) {
                String url = querySwxxHsxxDTO.getSbtxhfj();
                if (StringUtils.isNotBlank(url)){
                    urlList.add(url);
                }
            }
            if (CollectionUtils.isNotEmpty(urlList)) {
                // 去重
                urlList = urlList.stream().distinct().collect(Collectors.toList());
                for (String url : urlList) {
                    // 上传申报提醒函
                    String fileName = "申报提醒函_" + System.currentTimeMillis() + ".png";
                    // 处理url
                    String oldIpPort = url.substring(url.indexOf("//") + 2, url.indexOf('/', url.indexOf("//") + 2));
                    url = url.replace(oldIpPort, sbtxhIpPort);
                    BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(gzlslid, "", fileName, SBTXH, CommonConstantUtils.WJHZ_PNG);
                    bdcPdfDTO.setPdfUrl(url);
                    try {
                        bdcUploadFileUtils.uploadPdfByUrl(bdcPdfDTO);
                    } catch (Exception e) {
                        LOGGER.error("申报提醒函上传大云失败，下载url地址:{} ，异常信息：{}", url, e);
                    }
                }
            }
        }
    }

    /**
     * 删除申报提醒函附件
     * @param xmid 项目id
     */
    private void deleteSbtxhFj(String xmid){
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)){
            throw new AppException("未查询到项目信息");
        }
        BdcXmDO bdcXmDO = bdcXmDOList.get(0);
        // 删除收件材料,文件夹和文件
        List<BdcSlSjclDO> sjclList = bdcSlSjclService.listBdcSlSjcl(bdcXmDO.getGzlslid(),SBTXH);
        if (CollectionUtils.isNotEmpty(sjclList)){
            bdcSjclService.deleteSjcl(sjclList.get(0).getSjclid());
        }
    }

    /**
     * @param bdcSlSqrDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 设置权利人的变动份额
     * @date : 2022/9/19 15:53
     */
    private void setBdfe(List<BdcSlSqrDO> bdcSlSqrDOList) {
        if(CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
            for(BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList) {
                if(Objects.nonNull(bdcSlSqrDO.getGyfs())) {
                    if (bdcSlSqrDO.getGyfs() == 0) {
                        bdcSlSqrDO.setQlbl("100");
                    } else if (bdcSlSqrDO.getGyfs() == 1) {
                        bdcSlSqrDO.setQlbl("50");
                    } else if(StringUtils.isBlank(bdcSlSqrDO.getQlbl())){
                        bdcSlSqrDO.setQlbl("100");
                    }
                }
            }
        }

    }


    @Override
    public Page<BdcSwxxVO> listSwxxByPage(Pageable pageable, String bdcSwxxQOStr) {
        Map map = JSONObject.parseObject(bdcSwxxQOStr, HashMap.class);
        Page<BdcSwxxVO> bdcSwxxVOS = repo.selectPaging("listSwxxByPage", map, pageable);
        return bdcSwxxVOS;
    }

    private String getFwuuid(String xmid){
        String fwuuid = "";
        BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
        bdcSlHsxxDO.setXmid(xmid);
        List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxService.listBdcSlHsxx(bdcSlHsxxDO);
        if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)){
            fwuuid  = bdcSlHsxxDOList.get(0).getFwuuid();
        }
        return fwuuid;
    }

    // 【A003】任务状态接收接口
    @Override
    public Object tzSwRwzt(BdcSwxxQO bdcSwxxQO) {
        BdcSwFcjyDTO bdcSwFcjyDTO = this.generateSwRequestParam(bdcSwxxQO);

        YrbRwztRequestDTO yrbRwztRequestDTO = new YrbRwztRequestDTO();
        yrbRwztRequestDTO.setSJBH(bdcSwFcjyDTO.getSjbh());
        yrbRwztRequestDTO.setSJRQ(DateUtils.formateDateToString(bdcSwFcjyDTO.getSjrq(),DateUtils.DATE_FORMAT_2));
        yrbRwztRequestDTO.setZLFCLFBZ(bdcSwFcjyDTO.getZlfclfbz());
        yrbRwztRequestDTO.setSJGSDQ(bdcSwFcjyDTO.getSjgsdq());
        yrbRwztRequestDTO.setLRRDM(bdcSwFcjyDTO.getLrrdm());
        yrbRwztRequestDTO.setHTBH(bdcSwFcjyDTO.getHtbh());
        yrbRwztRequestDTO.setFWUUID(bdcSwFcjyDTO.getJyuuid());
        yrbRwztRequestDTO.setFWUUID(bdcSwFcjyDTO.getFwuuid());
        yrbRwztRequestDTO.setRWZT(bdcSwFcjyDTO.getRwzt());
        YrbRwztTaxbizml yrbRwztTaxbizml = new YrbRwztTaxbizml();
        yrbRwztTaxbizml.setRwjshouselist(yrbRwztRequestDTO);
        LOGGER.warn("流程实例id{}, 合同编号{}, 任务状态接收A003接口入参{}", bdcSwxxQO.getGzlslid(), bdcSwxxQO.getHtbh(), JSON.toJSONString(yrbRwztTaxbizml));
        Object response = exchangeInterfaceFeignService.swRequestInterface("sw_rwzt", yrbRwztTaxbizml);
        LOGGER.warn("流程实例id{}, 合同编号{}, 任务状态接收A003接口返回值{}", bdcSwxxQO.getGzlslid(), bdcSwxxQO.getHtbh(), StringUtils.left(JSON.toJSONString(response), 3000));
        if (Objects.nonNull(response)) {
            CommonResponse yrbrwztResponse = JSON.parseObject(JSON.toJSONString(response), CommonResponse.class);
            YrbRwztDTO yrbRwztDTO = JSON.parseObject(JSON.toJSONString(yrbrwztResponse.getData()), YrbRwztDTO.class);
            if (Objects.nonNull(yrbRwztDTO) && Objects.nonNull(yrbRwztDTO.getRwjshouselist())) {
                return yrbRwztDTO.getRwjshouselist();
            }
        }
        return null;
    }

    @Override
    public void getSwxxSbdwj(BdcSwxxQO bdcSwxxQO) {
        this.sbdwj(bdcSwxxQO, null);
    }

    @Override
    public List<BdcSlYjkxxDO> getFcjyJkxxQd(BdcSwxxQO bdcSwxxQO) {
        // 【A017】接口获取待缴款信息
        BdcSwFcjyDTO bdcSwFcjyDTO = this.generateSwRequestParam(bdcSwxxQO);
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(bdcSwxxQO.getGzlslid());
        if (CollectionUtils.isEmpty(bdcSlXmDOList)) {
            throw new AppException(ErrorCode.CUSTOM, "未找到项目信息");
        }
        List<BdcSlYjkxxDO> bdcSlYjkxxDOList = this.jkxxQd(bdcSlXmDOList.get(0).getXmid(), bdcSwFcjyDTO);
        if(CollectionUtils.isNotEmpty(bdcSlYjkxxDOList)){
            bdcSlYjkxxDOList = bdcSlYjkxxDOList.stream().filter(t->StringUtils.equals(t.getJkzt(), "0")).collect(Collectors.toList());
        }
        return bdcSlYjkxxDOList;
    }

    @Override
    public Object getFcjyJkEwm(BdcSwxxQO bdcSwxxQO) {
        // 1、获取数据库存储的待缴款信息
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(bdcSwxxQO.getGzlslid());
        if (CollectionUtils.isEmpty(bdcSlXmDOList)) {
            throw new AppException(ErrorCode.CUSTOM, "未获取到不动产受理项目信息。");
        }
        List<BdcSlYjkxxDO> bdcSlYjkxxDOList = this.queryBdcSlYjkxxByXmid(bdcSlXmDOList.get(0).getXmid());
        if(CollectionUtils.isEmpty(bdcSlYjkxxDOList)){
            throw new AppException(ErrorCode.CUSTOM, "未获取到税务待缴信息内容。");
        }
        BdcSwFcjyDTO bdcSwFcjyDTO = this.generateSwRequestParam(bdcSwxxQO);
        YrbFcjyEwmTaxbizml yrbFcjyEwmTaxbizml = new YrbFcjyEwmTaxbizml();
        List<YrbFcjyEwmRequest> yrbFcjyEwmRequestList = new ArrayList<>(bdcSlYjkxxDOList.size());
        for (BdcSlYjkxxDO bdcSlYjkxxDO : bdcSlYjkxxDOList) {
            YrbFcjyEwmRequest yrbFcjyEwmRequest = new YrbFcjyEwmRequest();
            yrbFcjyEwmRequest.setDJXH(bdcSlYjkxxDO.getDjxh());
            yrbFcjyEwmRequest.setHTBH(bdcSwFcjyDTO.getHtbh());
            yrbFcjyEwmRequest.setLRRDM(bdcSwFcjyDTO.getLrrdm());
            yrbFcjyEwmRequest.setNSRMC(bdcSlYjkxxDO.getNsrmc());
            yrbFcjyEwmRequest.setNSRSBH(bdcSlYjkxxDO.getNsrsbh());
            yrbFcjyEwmRequest.setPZXH(bdcSlYjkxxDO.getPzxh());
            yrbFcjyEwmRequest.setSJGSDQ(bdcSwFcjyDTO.getSjgsdq());
            yrbFcjyEwmRequest.setSKSSQQ(bdcSlYjkxxDO.getSkssqq());
            yrbFcjyEwmRequest.setSKSSQZ(bdcSlYjkxxDO.getSkssqz());
            yrbFcjyEwmRequest.setYBTSE(bdcSlYjkxxDO.getYbtse());
            yrbFcjyEwmRequest.setYZPZMXXH(bdcSlYjkxxDO.getYzpzmxxh());
            yrbFcjyEwmRequest.setYZPZZLDM(bdcSlYjkxxDO.getYzpzzldm());
            yrbFcjyEwmRequest.setZGSWSKFJDM(bdcSlYjkxxDO.getZgswskfjdm());
            yrbFcjyEwmRequest.setSJBH(bdcSwFcjyDTO.getSjbh());
            ClassHandleComonUtil.headModelSetDefaultValueToNullFieldXmlElemen(yrbFcjyEwmRequest);
            yrbFcjyEwmRequestList.add(yrbFcjyEwmRequest);
        }
        yrbFcjyEwmTaxbizml.setFcjyewmlist(yrbFcjyEwmRequestList);
        LOGGER.warn("流程实例id{}合同编号{}获取缴税二维码调用A018接口入参{}", bdcSwxxQO.getGzlslid(), bdcSwxxQO.getHtbh(), JSON.toJSONString(yrbFcjyEwmTaxbizml));
        Object ewmResponse = exchangeInterfaceFeignService.swRequestInterface("sw_getFcjyewmJkxx", yrbFcjyEwmTaxbizml);
        LOGGER.warn("流程实例id{}合同编号{}获取缴税二维码调用A018接口返回值{}", bdcSwxxQO.getGzlslid(), bdcSwxxQO.getHtbh(), StringUtils.left(JSON.toJSONString(ewmResponse), 3000));
        if (Objects.nonNull(ewmResponse)) {
            CommonResponse yrbFcjyEwmResponse = JSON.parseObject(JSON.toJSONString(ewmResponse), CommonResponse.class);
            YrbFcjyEwmDTO yrbFcjyEwmDTO = JSON.parseObject(JSON.toJSONString(yrbFcjyEwmResponse.getData()), YrbFcjyEwmDTO.class);
            if (Objects.nonNull(yrbFcjyEwmDTO) && Objects.nonNull(yrbFcjyEwmDTO.getKkewm())) {
                //更新电子税票号
                if (CollectionUtils.isNotEmpty(bdcSlYjkxxDOList)) {
                    //保存已缴款信息
                    for (BdcSlYjkxxDO bdcSlYjkxxDO : bdcSlYjkxxDOList) {
                        bdcSlYjkxxDO.setDzsphm(yrbFcjyEwmDTO.getKkewm().getDzsphm());
                    }
                    entityMapper.batchSaveSelective(bdcSlYjkxxDOList);
                }
                return yrbFcjyEwmDTO.getKkewm();
            }
        }
        return null;
    }

    @Override
    public YrbEwmjkxxResponse getSwJkzt(BdcSwxxQO bdcSwxxQO) {
        // 调用【A019】接口，查询缴税状态，并更新核税信息表中，完税状态与核税状态值
        List<YrbEwmjkxxDTO> yrbEwmjkxxDTOList = this.swjkzt(bdcSwxxQO);
        if(CollectionUtils.isEmpty(yrbEwmjkxxDTOList)){
            throw new AppException(ErrorCode.CUSTOM, "调用税务接口获取缴款状态信息失败，返回值信息为空");
        }
        for(YrbEwmjkxxDTO yrbEwmjkxxDTO: yrbEwmjkxxDTOList){
            if (StringUtils.equals("1", yrbEwmjkxxDTO.getKkjg().getJkzt())) {
               return yrbEwmjkxxDTO.getKkjg();
            }
        }
        return yrbEwmjkxxDTOList.get(0).getKkjg();
    }

    @Override
    public CommonResponse hqswsbzt(BdcSwxxQO bdcSwxxQO) {
        BdcSwFcjyDTO bdcSwFcjyDTO = this.generateSwRequestParam(bdcSwxxQO);
        YrbSbztcxTaxbizml yrbSbztcxTaxbizml = new YrbSbztcxTaxbizml();
        YrbSbztcxRequestDTO yrbSbztcxRequestDTO = new YrbSbztcxRequestDTO();
        yrbSbztcxRequestDTO.setSJBH(bdcSwFcjyDTO.getSjbh());
        yrbSbztcxRequestDTO.setSJRQ(DateUtils.formateDateToString(new Date(),DateUtils.DATE_FORMAT_2));
        yrbSbztcxRequestDTO.setZLFCLFBZ(bdcSwFcjyDTO.getZlfclfbz());
        yrbSbztcxRequestDTO.setSJGSDQ(bdcSwFcjyDTO.getSjgsdq());
        yrbSbztcxRequestDTO.setHTBH(bdcSwFcjyDTO.getHtbh());
        yrbSbztcxRequestDTO.setFWUUID(bdcSwFcjyDTO.getFwuuid());
        yrbSbztcxRequestDTO.setJYUUID(bdcSwFcjyDTO.getJyuuid());
        yrbSbztcxRequestDTO.setNSRSBH(this.getNsrsbh(bdcSwxxQO.getGzlslid(), CommonConstantUtils.QLRLB_QLR));
        ClassHandleComonUtil.headModelSetDefaultValueToNullFieldXmlElemen(yrbSbztcxRequestDTO);
        List<YrbSbztcxRequestDTO> yrbSbztcxRequestDTOList = new ArrayList<>(1);
        yrbSbztcxRequestDTOList.add(yrbSbztcxRequestDTO);
        yrbSbztcxTaxbizml.setSbztxxlist(yrbSbztcxRequestDTOList);
        LOGGER.warn("当前流程实例ID：{}，调用A010,获取申报状态接口入参：{}", bdcSwFcjyDTO.getGzlslid(), JSON.toJSONString(yrbSbztcxTaxbizml));
        Object response = exchangeInterfaceFeignService.swRequestInterface("sw_sbztcx", yrbSbztcxTaxbizml);
        LOGGER.warn("当前流程实例ID：{}，调用A010,获取申报状态接口返回值：{}", bdcSwFcjyDTO.getGzlslid(), JSON.toJSONString(response));
        if(Objects.nonNull(response)){
            BdcCommonResponse<YrbSbztcxDTO> commonResponse = JSON.parseObject(JSON.toJSONString(response), BdcCommonResponse.class);
            if(StringUtils.equals("1",commonResponse.getCode()) && Objects.nonNull(commonResponse.getData())){
                YrbSbztcxDTO yrbSbztcxDTO = JSON.parseObject(JSON.toJSONString(commonResponse.getData()), YrbSbztcxDTO.class);
                List<BdcSlHsxxDO> bdcSlHsxxDOList = new ArrayList<>(10);
                List<YrbSbztcxResponseDTO> yrbSbztcxResponseDTOList = yrbSbztcxDTO.getSbztxxlist();
                if(CollectionUtils.isNotEmpty(yrbSbztcxResponseDTOList)){
                    for(YrbSbztcxResponseDTO yrbSbztcxResponseDTO: yrbSbztcxResponseDTOList){
                        bdcSlHsxxDOList.addAll(this.handlerSwSbzt(yrbSbztcxResponseDTO));
                    }
                }
                return CommonResponse.ok(bdcSlHsxxDOList);
            }
        }
        return CommonResponse.fail("调用税务A010接口获取申报状态返回值为空。");
    }

    // 处理税务申报状态
    private List<BdcSlHsxxDO> handlerSwSbzt(YrbSbztcxResponseDTO yrbSbztcxResponseDTO){
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setZdbs("SW_SBZT");
        bdcZdDsfzdgxDO.setDsfzdz(yrbSbztcxResponseDTO.getShzt());
        bdcZdDsfzdgxDO.setDsfxtbs("SW");
        BdcZdDsfzdgxDO zdDsfzdgxDO = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
        // // 税务推送作废状态时，更新hsxx 退回原因
        String zfyy = "";
        if(StringUtils.equals("30", yrbSbztcxResponseDTO.getShzt())){
            try {
                zfyy = URLDecoder.decode(yrbSbztcxResponseDTO.getBz(), "GBK") ;
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("税务作废原因转码失败，转码内容为：{}", yrbSbztcxResponseDTO.getBz());
            }
        }

        List<BdcSlHsxxDO> bdcSlHsxxDOList = new ArrayList<>(10);
        if (null != zdDsfzdgxDO && StringUtils.isNotBlank(yrbSbztcxResponseDTO.getSjbh())) {
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxBySlbh(yrbSbztcxResponseDTO.getSjbh(), "");
            if (null != bdcSlJbxxDO && StringUtils.isNotBlank(bdcSlJbxxDO.getGzlslid())) {
                List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid(), null);
                if(CollectionUtils.isNotEmpty(bdcSlXmDOList)){
                    try {
                        BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
                        bdcSlHsxxDO.setXmid(bdcSlXmDOList.get(0).getXmid());
                        List<BdcSlHsxxDO> hsxxList = this.bdcSlHsxxService.listBdcSlHsxx(bdcSlHsxxDO);
                        if(CollectionUtils.isNotEmpty(hsxxList)){
                            for(BdcSlHsxxDO hsxx: hsxxList){
                                hsxx.setWszt(Integer.valueOf(zdDsfzdgxDO.getBdczdz()));
                                hsxx.setThyy(zfyy);
                                this.bdcSlHsxxService.updateBdcSlHsxx(hsxx);
                            }
                            bdcSlHsxxDOList.addAll(hsxxList);
                        }else{
                            BdcSlHsxxDO hsxx = new BdcSlHsxxDO();
                            hsxx.setXmid(bdcSlXmDOList.get(0).getXmid());
                            hsxx.setSqrlb(CommonConstantUtils.QLRLB_QLR);
                            hsxx.setWszt(Integer.valueOf(zdDsfzdgxDO.getBdczdz()));
                            hsxx.setThyy(zfyy);
                            this.bdcSlHsxxService.insertBdcSlHsxx(hsxx);
                            bdcSlHsxxDOList.add(hsxx);
                        }
                    } catch (Exception e) {
                        LOGGER.error("更新完税状态失败！原因：{}" , e.getMessage());
                    }
                }
            }
        }
        return bdcSlHsxxDOList;
    }

    /**
     *  公用方法：生成税务请求参数
     */
    private BdcSwFcjyDTO generateSwRequestParam(BdcSwxxQO bdcSwxxQO){
        if(StringUtils.isBlank(bdcSwxxQO.getGzlslid())){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数：工作流实例ID。");
        }
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(bdcSwxxQO.getGzlslid());
        if(Objects.isNull(bdcSlJbxxDO)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到受理基本信息。");
        }
        BdcSwFcjyDTO bdcSwFcjyDTO = new BdcSwFcjyDTO();
        bdcSwFcjyDTO.setHtbh(bdcSwxxQO.getHtbh());
        bdcSwFcjyDTO.setGzlslid(bdcSwxxQO.getGzlslid());
        if(sjbhtscl){
            bdcSwFcjyDTO.setSjbh(bdcSwxxQO.getHtbh());
        }else{
            bdcSwFcjyDTO.setSjbh(bdcSlJbxxDO.getSlbh());
        }
        bdcSwFcjyDTO.setLrrdm(bdcSlJbxxDO.getSlr());
        bdcSwFcjyDTO.setFwuuid(bdcSlJbxxDO.getFwuuid());
        bdcSwFcjyDTO.setJyuuid(bdcSlJbxxDO.getJyuuid());
        if(Objects.nonNull(bdcSlJbxxDO.getSlsj())){
            bdcSwFcjyDTO.setSjrq(bdcSlJbxxDO.getSlsj());
        }else{
            bdcSwFcjyDTO.setSjrq(new Date());
        }

        // 添加项目信息
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(bdcSwxxQO.getGzlslid());
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        BdcXmDO bdcXmDO = null;
        if(CollectionUtils.isNotEmpty(bdcXmDOList)){
            bdcXmDO = bdcXmDOList.get(0);
        }
        // 设置数据归属地区
        if(StringUtils.isNotBlank(bdcSwxxQO.getSjgsdq())){
            bdcSwFcjyDTO.setSjgsdq(bdcSwxxQO.getSjgsdq());
        }else{
            bdcSwFcjyDTO.setSjgsdq(this.getSwSjgsdq(bdcSlJbxxDO, bdcXmDO,null));
        }
        // 设置录入人员代码
        if(StringUtils.isNotBlank(lrrdm)){
            bdcSwFcjyDTO.setLrrdm(lrrdm);
        }else{
            bdcSwFcjyDTO.setLrrdm(bdcSlJbxxDO.getSlr());
        }
        // 设置增量房存量房标志
        bdcSwFcjyDTO.setZlfclfbz(this.getSwZlfClfBz(bdcSlJbxxDO.getGzldyid(), bdcSwxxQO.getFwlx()));
        if(StringUtils.isNotBlank(bdcSwxxQO.getHtbh())){
            bdcSwFcjyDTO.setHtbh(bdcSwxxQO.getHtbh());
        }else{
            // 获取合同编号
            List<BdcSlXmDO> bdcSlXmDOList = this.bdcSlXmService.listBdcSlXmByGzlslid(bdcSwxxQO.getGzlslid());
            if(CollectionUtils.isEmpty(bdcSlXmDOList)){
                throw new AppException(ErrorCode.MISSING_ARG, "未获取到受理项目信息。");
            }
            List<BdcSlJyxxDO> bdcSlJyxxDOList = this.bdcSlJyxxService.listBdcSlJyxxByXmid(bdcSlXmDOList.get(0).getXmid());
            if(CollectionUtils.isEmpty(bdcSlJyxxDOList)){
                throw new AppException(ErrorCode.MISSING_ARG, "未获取到交易信息。");
            }
            bdcSwFcjyDTO.setHtbh(bdcSlJyxxDOList.get(0).getHtbh());
        }
        // 案件状态，需要exchange进行字典转换
        bdcSwFcjyDTO.setRwzt(String.valueOf(bdcSlJbxxDO.getAjzt()));

        LOGGER.info("生成税务请求参数数据：{}", JSON.toJSONString(bdcSwFcjyDTO));
        return bdcSwFcjyDTO;
    }

    /**
     * 获取数据归属地区
     * <p>南通： 传jddm,由exchange的BDC_ZD_DSFZDGXDZB字典表中配置，ZDBS：DSF_ZD_SJGSDQ; DSFXTBS: SW; BDCZDZ: qxdm值; DSFZDZ: 数据归属地区值</p>
     * <p>盐城： 获取 hqswxx.gsdqbmmcdz 配置的Map内容，根据用户所在部门作为Key获取对应信息</p>
     * <p>常州： 传ssxz，由exchange的BDC_ZD_DSFZDGXDZB字典表中配置，ZDBS：DSF_ZD_SJGSDQ; DSFXTBS: SW; BDCZDZ: ssxz值; DSFZDZ: 数据归属地区值</p>
     * <p>淮安： 传qxdm，由exchange的BDC_ZD_DSFZDGXDZB字典表中配置，ZDBS：DSF_ZD_SJGSDQ; DSFXTBS: SW; BDCZDZ: qxdm值; DSFZDZ: 数据归属地区值</p>
     */
    private String getSwSjgsdq(BdcSlJbxxDO bdcSlJbxxDO, BdcXmDO bdcXmDO,BdcSlFwxxDO bdcSlFwxxDO){
        if(MapUtils.isNotEmpty(gsdqbmmcdz)){
            String userName = userManagerUtils.getCurrentUserName();
            String organizationByUserName = userManagerUtils.getOrganizationByUserName(userName);
            LOGGER.info("获取数据归属地区,配置的参数gsdqbmmcdz：{}",JSON.toJSONString(gsdqbmmcdz));
            LOGGER.info("获取数据归属地区,用户名为:{},获取到的用户部门organizationByUserName：{}",userName,organizationByUserName);
            Set<String> keySet = gsdqbmmcdz.keySet();
            String sjgsdqVal = "";
            for (String key : keySet) {
                if(StringUtils.isNotBlank(gsdqbmmcdz.get(key)) && gsdqbmmcdz.get(key).contains(organizationByUserName)){
                    sjgsdqVal = key;
                    break;
                }
            }
            LOGGER.info("获取数据归属地区,获取到的数据归属地值为：{}",sjgsdqVal);
            return sjgsdqVal;
        }

        // 常州版本 或者配置按所属乡镇取值得
        if((StringUtils.isNotBlank(sjgsdqVersion) &&CommonConstantUtils.SYSTEM_VERSION_CZ.equals(sjgsdqVersion))||StringUtils.equals(SJGSDQ_SSXZ,sjgsdqSjly)){
            if(Objects.nonNull(bdcSlFwxxDO) && StringUtils.isNotBlank(bdcSlFwxxDO.getJddm())){
                return bdcSlFwxxDO.getJddm();
            }
            if(Objects.nonNull(bdcSlJbxxDO)){
                List<BdcSlXmDO> bdcSlSfxmDOList = this.bdcSlXmService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid(), "");
                if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
                    List<BdcSlFwxxDO> bdcSlFwxxDOList = this.bdcSlFwxxService.listBdcSlFwxxByXmid(bdcSlSfxmDOList.get(0).getXmid());
                    if(CollectionUtils.isNotEmpty(bdcSlFwxxDOList)){
                        if(StringUtils.isNotBlank(bdcSlFwxxDOList.get(0).getJddm())){
                            return bdcSlFwxxDOList.get(0).getJddm();
                        }
                    }
                }
            }
        }
        if(Objects.nonNull(bdcXmDO) && StringUtils.isNotBlank(bdcXmDO.getQxdm())){
            return bdcXmDO.getQxdm();
        }
        if(Objects.nonNull(bdcSlJbxxDO) && StringUtils.isNotBlank(bdcSlJbxxDO.getQxdm())){
            return bdcSlJbxxDO.getQxdm();
        }
        return "";
    }

    /**
     * 获取税务增量房存量房标志
     */
    private String getSwZlfClfBz(String gzldyid, String fwlx){
        if(StringUtils.isNotBlank(fwlx)){
            return fwlx;
        }
        if(CollectionUtils.isNotEmpty(spfzygzldyidList) && CollectionUtils.isNotEmpty(clfzygzldyidList) ){
            if(spfzygzldyidList.contains(gzldyid)){
                return "1"; // 增量房
            }
            if(clfzygzldyidList.contains(gzldyid)){
                return "2"; // 存量房
            }
        }
        if(this.bdcGzyzService.checkSpfLc(gzldyid)){
            return "1"; // 增量房
        }else{
            return "2"; // 存量房
        }
    }

    /**
     * 获取纳税人识别号内容，优先获取 BDC_SL_HSXX 表中的 nsrsbh , 未获取到内容则读取对应权利人类别的证件号
     */
    private String getNsrsbh(String gzlslid, String qlrlb){
        if (StringUtils.isBlank(qlrlb)) {
            qlrlb = CommonConstantUtils.QLRLB_QLR;
        }
        String nsrsbh = "";
        List<BdcSlHsxxDO> bdcSlHsxxDOList = this.bdcSlHsxxService.listBdcSlHsxxByGzlslidAndSqrlb(gzlslid, qlrlb);
        if(CollectionUtils.isNotEmpty(bdcSlHsxxDOList)){
            nsrsbh = bdcSlHsxxDOList.get(0).getNsrsbh();
        }
        // 核税信息表中没有nsrsbh时，获取对应权利人类别证件号信息
        if(StringUtils.isBlank(nsrsbh)){
            List<BdcSlXmDO> bdcSlXmDOList = this.bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
            if(CollectionUtils.isNotEmpty(bdcSlXmDOList)){
                List<BdcSlSqrDO> bdcSlSqrDOList= this.bdcSlSqrService.listBdcSlSqrByXmid(bdcSlXmDOList.get(0).getXmid(), qlrlb);
                if(CollectionUtils.isNotEmpty(bdcSlSqrDOList)){
                    nsrsbh = bdcSlSqrDOList.get(0).getZjh();
                }
            }
        }
        return nsrsbh;
    }

    /**
     * 获取权利人证件号，优先获取 BDC_SL_QLR 表中的 证件号 , 未获取到取BDC_QLR的
     */
    private String getQlrZjh(String xmid,String qlrlb){
        if (StringUtils.isBlank(qlrlb)) {
            qlrlb = CommonConstantUtils.QLRLB_QLR;
        }
        List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrService.listBdcSlSqrByXmid(xmid, qlrlb);
        if(CollectionUtils.isEmpty(bdcSlSqrDOList)){
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            bdcQlrQO.setQlrlb(qlrlb);
            List<BdcQlrDO> bdcQlrDOList = this.bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if(CollectionUtils.isEmpty(bdcQlrDOList)){
                throw new AppException(ErrorCode.MISSING_ARG, "缺少权利人信息");
            }
            return bdcQlrDOList.get(0).getZjh();
        }
        return bdcSlSqrDOList.get(0).getZjh();
    }
}
