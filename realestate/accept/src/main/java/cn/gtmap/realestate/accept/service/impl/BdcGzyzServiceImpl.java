package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.gtc.feign.common.exception.GtFeignException;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.clients.manage.StatisticsProcessClient;
import cn.gtmap.gtc.workflow.domain.manage.StatisticsProcDto;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.accept.core.service.*;
import cn.gtmap.realestate.accept.service.BdcGzyzService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzsjDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZhgzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.access.QjsjjcDTO;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.zjjg.ZjjgRequest;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.zjjg.ZjjgResponseDto;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.init.DyhGzXmDTO;
import cn.gtmap.realestate.common.core.enums.BdcSfztEnum;
import cn.gtmap.realestate.common.core.enums.BdcZjjgDkztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcBahtQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlZjjgQO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.init.*;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.CbzdFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.ZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZhGzFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.NationalAccessFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcYgFeignService;
import cn.gtmap.realestate.common.core.service.feign.portal.BdcCheckFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.vo.portal.BdcBtxyzVO;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/7/22
 * @description 规则验证
 */
@Service
public class BdcGzyzServiceImpl implements BdcGzyzService {

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            // 核心线程数量
            20,
            // 最大线程数
            40,
            // 空闲超时一小时（调用频繁）
            3600, TimeUnit.SECONDS,
            // 阻塞队列
            new ArrayBlockingQueue<>(100),
            // 线程工厂
            new ThreadFactoryBuilder().setNameFormat("xsdjy-pool-%d").build(),
            // 过多任务直接主线程处理
            new ThreadPoolExecutor.CallerRunsPolicy()
    );


    @Autowired
    BdcGzZhGzFeignService bdcGzZhGzFeignService;
    @Autowired
    FwHsFeignService fwHsFeignService;
    @Autowired
    BdcSlXmService bdcSlXmService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcSlJbxxService bdcSlJbxxService;
    @Autowired
    BdcSlSqrService bdcSlSqrService;
    @Autowired
    BdcSlFwxxService bdcSlFwxxService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcSlYcslDjywDzbService bdcSlYcslDjywDzbService;
    @Autowired
    BdcSlXmLsgxService bdcSlXmLsgxService;
    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcSlSfxxService bdcSlSfxxService;
    @Autowired
    BdcPpFeignService bdcPpFeignService;
    @Autowired
    BdcdyZtFeignService bdcdyZtFeignService;
    @Autowired
    ZdFeignService zdFeignService;
    @Autowired
    CbzdFeignService cbzdFeignService;
    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;
    @Autowired
    ProcessTaskClient processTaskClient;
    @Autowired
    BdcCheckFeignService bdcCheckFeignService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    BdcSlHsxxService bdcSlHsxxService;
    @Autowired
    BdcSlJtcyService bdcSlJtcyService;
    @Autowired
    BdcSlSjclService bdcSlSjclService;
    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    BdcSlZjjgService bdcSlZjjgService;
    @Autowired
    StatisticsProcessClient statisticsProcessClient;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    BdcYgFeignService bdcYgFeignService;
    @Autowired
    BdcCshXtPzFeignService bdcCshXtPzFeignService;
    @Autowired
    NationalAccessFeignService nationalAccessFeignService;
    @Autowired
    BdcdyFeignService bdcdyFeignService;

    @Autowired
    BdcDyhGzFeignService bdcDyhGzFeignService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGzyzServiceImpl.class);


    /**
     * 房屋套次统计（规划用途过滤）
     */
    @Value("${fwtccx.ghyt:}")
    private String fwtcghyt;

    /**
     * 商品房流程
     */
    @Value("#{'${spf.gzldyid:}'.split(',')}")
    protected List<String> spfdyidList;

    /**
     * 外联土地证证书限制状态下，可以注销的流程
     */
    @Value("${wltdzlwxz.gzldyid:}")
    private String wltdzlwxz;


    /**
     * 不动产数据(非存量过渡数据)产权证号标识
     */
    @Value("#{'${pplzzt.bdcqzh.bs:不动产}'.split(',')}")
    private List<String> pplzztBdcqzhBsList;

    @Value("${cjyz.lwsh:false}")
    private Boolean cjyzlwsh;

    /**
    * 商品房首次登记工作流定义ID
    **/
    @Value("#{'${spfscdj.gzldyid:}'.split(',')}")
    private List<String> spfscdjGzldyids;

    @Override
    public List<Map<String, Object>> yzBdcgz(BdcGzYzQO bdcGzYzQO) {
        //验证人；
        UserDto yzr = userManagerUtils.getCurrentUser();
        String yzrid = null == yzr ? null : yzr.getId();
        String yzrzh = null == yzr ? null : yzr.getUsername();

        bdcGzYzQO.setYzrid(yzrid);
        bdcGzYzQO.setYzrzh(yzrzh);

        List<Map<String, Object>> resultList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(bdcGzYzQO.getBdcGzYzsjDTOList())) {
            //根据逻辑幢主键查询出所有的户室作为查询参数
            changeParamForLjz(bdcGzYzQO);
        }
        if (CollectionUtils.isNotEmpty(bdcGzYzQO.getParamList())) {
            List<BdcGzYzTsxxDTO> listBdcGzYzTsxx = new ArrayList<>();
            try {
                listBdcGzYzTsxx = bdcGzZhGzFeignService.listBdcGzYzTsxxOfAnyParam(bdcGzYzQO);
            } catch (Exception ex) {
                handlException(ex);
            }
            if (CollectionUtils.isNotEmpty(listBdcGzYzTsxx)) {
                for (BdcGzYzTsxxDTO bdcGzYzTsxxDTO : listBdcGzYzTsxx) {
                    if (CollectionUtils.isNotEmpty(bdcGzYzTsxxDTO.getZgzTsxxDTOList())) {
                        resultList.addAll(handleTsxx(bdcGzYzTsxxDTO.getZgzTsxxDTOList(), bdcGzYzTsxxDTO));
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(resultList)) {
            //验证优先级为3，且提示信息存在ZDDYH 标记的，要把提示信息去重处理
            return handelResult(resultList);
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * @param bdcGzYzQO 规则验证查询实体(支持任意参数)
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 除流程验证外其他验证, 如匹配验证, 页面按钮操作验证等
     * @date : 2020/7/10 14:15
     */
    @Override
    public List<Map<String, Object>> qtyz(BdcGzYzQO bdcGzYzQO) {
        Date startTime = new Date();
        LOGGER.info("规则验证开始,规则参数:{}",bdcGzYzQO);
        List<Map<String, Object>> resultList = Lists.newArrayList();
        BdcGzZhgzTsxxDTO bdcGzZhgzTsxxDTO = null;
        try {
            bdcGzZhgzTsxxDTO = bdcGzZhGzFeignService.getZhgzYzTsxx(bdcGzYzQO);
        } catch (Exception e) {
            handlException(e);
        }
        if (bdcGzZhgzTsxxDTO != null && CollectionUtils.isNotEmpty(bdcGzZhgzTsxxDTO.getZgzTsxxDTOList())) {
            resultList = handleTsxx(bdcGzZhgzTsxxDTO.getZgzTsxxDTOList(), null);
        }
        long time = System.currentTimeMillis() - startTime.getTime();
        LOGGER.info("规则验证结束,规则参数:{},验证耗时：{}",bdcGzYzQO,time);
        return resultList;
    }

    /**
     * @param jbxxid 基本信息id
     * @return 查封文号数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 创建时验证查封文号是否存在不重复的
     */
    @Override
    public Integer yzCfwhSl(String jbxxid) {
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(jbxxid, "");
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList) && CollectionUtils.size(bdcSlXmDOList) > 1) {
            //存在多个受理项目的时候根据ybdcqz去重，若去重后数量为1，说明查封文号相同
            List<BdcSlXmDO> xmList = bdcSlXmDOList.stream().filter(xm -> StringUtils.isNotBlank(xm.getYbdcqz())).collect(Collectors.toList());
            Set<BdcSlXmDO> bdcSlXmDOSet = new TreeSet<>(Comparator.comparing(BdcSlXmDO::getYbdcqz));
            bdcSlXmDOSet.addAll(xmList);
            return bdcSlXmDOSet.size();
        } else {
            return bdcSlXmDOList.size();
        }
    }

    /**
     * @param bdcdyh 不动产单元号
     * @param qllx   权利类型的逗号分隔字符串
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return: Boolean 验证结果 已生成权利返回 True 否则 False
     * @description 验证该不动产单元在登记系统是否已生成权利
     */
    @Override
    public List<BdcXmDO> checkBdcdyScQl(String bdcdyh, String qllx) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setBdcdyh(bdcdyh);
        bdcXmQO.setQszt(CommonConstantUtils.QSZT_VALID);
        final List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        return bdcXmDOList.stream().filter(t ->
                t.getQszt().equals(CommonConstantUtils.QSZT_VALID) && qllx.indexOf(String.valueOf(t.getQllx())) > -1)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean checkSfFwcxForYcslByXmid(String xmid) {
        //标志验证是否通过
        Boolean sfFwcx = false;
        String gzldyid = "";
        //判断是否为一窗融合流程,并且来源为一窗
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        //纯登记流程验证直接通过
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            gzldyid = bdcXmDOList.get(0).getGzldyid();
        }
        if (CollectionUtils.isNotEmpty(bdcXmDOList) && !CheckWwsqOrYcslUtils.checkIsYcsl(bdcXmDOList.get(0).getSply())) {
            return true;
        }

        List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrService.listBdcSlSqrByXmid(xmid, "");
        //房屋类型为商品房只需判断权利人是否均为其他套,存量房需判断权利人义务人均为其他套
        //判断申请人房屋套次是否均为其他套
        Boolean sqrFwtcIsQt = true;
        if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
            for (BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList) {
                if (!StringUtils.equals(CommonConstantUtils.FWTC_QT, bdcSlSqrDO.getFwtc())) {
                    if (CollectionUtils.isNotEmpty(spfdyidList) && StringUtils.isNotBlank(gzldyid) && spfdyidList.contains(gzldyid)) {
                        if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcSlSqrDO.getSqrlb())) {
                            sqrFwtcIsQt = false;
                            break;
                        }
                    } else {
                        sqrFwtcIsQt = false;
                        break;
                    }
                }
            }
        }
        //存在非其他套次并且用途为住宅需要进行房查验证
        if (!sqrFwtcIsQt) {
            List<BdcSlFwxxDO> bdcSlFwxxDOList = bdcSlFwxxService.listBdcSlFwxxByXmid(xmid);
            if (CollectionUtils.isNotEmpty(bdcSlFwxxDOList) && StringUtils.isNotBlank(fwtcghyt) && (bdcSlFwxxDOList.get(0).getFwyt() ==null ||CommonUtil.indexOfStrs(fwtcghyt.split(CommonConstantUtils.ZF_YW_DH), bdcSlFwxxDOList.get(0).getFwyt().toString()))) {
                BdcSlXmDO bdcSlXmDO = bdcSlXmService.queryBdcSlXmByXmid(xmid);
                if (bdcSlXmDO != null && CommonConstantUtils.SF_S_DM.equals(bdcSlXmDO.getSffc())) {
                    sfFwcx = true;
                }

            } else {
                sfFwcx = true;
            }

        } else {
            sfFwcx = true;
        }


        return sfFwcx;

    }

    @Override
    public BdcSlxxDTO checkStfFwtc(String xmid) {
        BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("xmid");
        }
        //判断是否为一窗融合流程,并且来源为一窗
        BdcXmQO xmQO = new BdcXmQO();
        xmQO.setXmid(xmid);
        List<BdcXmDO> xmDOList = bdcXmFeignService.listBdcXm(xmQO);
        //纯登记流程验证直接通过
        if (CollectionUtils.isNotEmpty(xmDOList) && !CheckWwsqOrYcslUtils.checkIsYcsl(xmDOList.get(0).getSply())) {
            return bdcSlxxDTO;
        }
        List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrService.listBdcSlSqrByXmid(xmid, CommonConstantUtils.QLRLB_QLR);
        if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
            for (BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList) {
                //申请首套房时需要进行首套房验证
                if (CommonConstantUtils.FWTC_ZERO.equals(bdcSlSqrDO.getFwtc())) {
                    //查询受理库
                    List<BdcSlSqrDO> bdcSlSqrDOS = bdcSlSqrService.listBdcSlSqrBySqrmcAndZjh(bdcSlSqrDO.getSqrmc(), bdcSlSqrDO.getZjh(), CommonConstantUtils.QLRLB_QLR);
                    if (CollectionUtils.isNotEmpty(bdcSlSqrDOS)) {
                        //排除当前流程
                        for (BdcSlSqrDO slSqrDO : bdcSlSqrDOS) {
                            if (!StringUtils.equals(slSqrDO.getXmid(), bdcSlSqrDO.getXmid())) {
                                BdcSlXmDO bdcSlXmDO = bdcSlXmService.queryBdcSlXmByXmid(slSqrDO.getXmid());
                                if (bdcSlXmDO != null) {
                                    BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByJbxxid(bdcSlXmDO.getJbxxid());
                                    if (bdcSlJbxxDO != null) {
                                        //组织返回数据
                                        bdcSlxxDTO.setBdcSlJbxx(bdcSlJbxxDO);
                                        List<BdcSlXmDTO> bdcSlXmDTOList = new ArrayList<>();
                                        BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
                                        bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);
                                        bdcSlXmDTOList.add(bdcSlXmDTO);
                                        bdcSlxxDTO.setBdcSlXmList(bdcSlXmDTOList);
                                        break;
                                    }
                                }

                            }
                        }
                    }
                    if (bdcSlxxDTO.getBdcSlJbxx() == null) {
                        //查询登记库
                        BdcQlrQO bdcQlrQO = new BdcQlrQO();
                        bdcQlrQO.setQlrmc(bdcSlSqrDO.getSqrmc());
                        bdcQlrQO.setZjh(bdcSlSqrDO.getZjh());
                        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                        //排除当前流程
                        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                            for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                                if (!StringUtils.equals(bdcQlrDO.getXmid(), xmid)) {
                                    BdcXmQO bdcXmQO = new BdcXmQO();
                                    bdcXmQO.setXmid(bdcQlrDO.getXmid());
                                    List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                                    //判断是否存在正在办理的出证业务
                                    if (CollectionUtils.isNotEmpty(bdcXmDOList) && !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDOList.get(0).getQllx()) && !CommonConstantUtils.AJZT_YB_DM.equals(bdcXmDOList.get(0).getAjzt())) {
                                        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
                                        BeanUtils.copyProperties(bdcXmDOList.get(0), bdcSlJbxxDO);
                                        //组织返回数据
                                        bdcSlxxDTO.setBdcSlJbxx(bdcSlJbxxDO);
                                        break;

                                    }
                                }

                            }

                        }
                    }
                }
            }
        }
        return bdcSlxxDTO;


    }

    @Override
    public BdcSlJbxxDO checkIsblQtfdjlc(String bdcdyh, String gzlslid) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new MissingArgumentException("bdcdyh");
        }
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setBdcdyh(bdcdyh);
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXm(bdcSlXmQO);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            //根据基本信息ID去重
            List<BdcSlXmDO> slXmDOList = bdcSlXmDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getJbxxid()))), ArrayList::new));
            for (BdcSlXmDO bdcSlXmDO : slXmDOList) {
                BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByJbxxid(bdcSlXmDO.getJbxxid());
                //流程为一窗流程,非当前流程，并且为在办
                if (bdcSlJbxxDO != null && StringUtils.isNotBlank(bdcSlJbxxDO.getGzldyid()) && bdcSlYcslDjywDzbService.checkIsYcslLc(bdcSlJbxxDO.getGzldyid()) && (StringUtils.isBlank(gzlslid) || !StringUtils.equals(gzlslid, bdcSlJbxxDO.getGzlslid())) && CommonConstantUtils.AJZT_ZB_DM.equals(bdcSlJbxxDO.getAjzt())) {

                    return bdcSlJbxxDO;
                }
            }
        }
        return null;


    }

    @Override
    public List<BdcGzYzTsxxDTO> fdjlcGzyz(String gzlslid, String zhbs) {
        UserDto yzr = userManagerUtils.getCurrentUser();//验证人；
        String yzrid = null == yzr ? null : yzr.getId();
        String yzrzh = null == yzr ? null : yzr.getUsername();


        List<BdcGzYzTsxxDTO> listBdcGzYzTsxx = null;
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (bdcSlJbxxDO != null) {
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid(), "");
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
                bdcGzYzQO.setZhbs(zhbs);
                List<Map<String, Object>> paramList = new ArrayList<>();
                bdcGzYzQO.setParamList(paramList);
                bdcGzYzQO.setYzrid(yzrid);
                bdcGzYzQO.setYzrzh(yzrzh);
                //循环生成条件
                for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                    Map<String, Object> param = new HashMap<>(4);
                    param.put("bdcdyh", bdcSlXmDO.getBdcdyh());
                    param.put("xmid", bdcSlXmDO.getXmid());
                    param.put("slbh", bdcSlJbxxDO.getSlbh());
                    param.put("gzlslid", gzlslid);
                    paramList.add(param);
                }
                try {
                    listBdcGzYzTsxx = bdcGzZhGzFeignService.listBdcGzYzTsxxOfAnyParam(bdcGzYzQO);
                }catch(Exception ex){
                    handlException(ex);
                }
            }
        }
        return listBdcGzYzTsxx;
    }

    /**
     * @param bdcGzYzQO 规则验证查询实体
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 逻辑幢
     */
    private void changeParamForLjz(BdcGzYzQO bdcGzYzQO) {
        if (CollectionUtils.isNotEmpty(bdcGzYzQO.getBdcGzYzsjDTOList())) {
            // 转换参数,根据逻辑幢主键获取所有户室作为查询参数
            List<Map<String, Object>> paramsMapList = new ArrayList<>();
            for (BdcGzYzsjDTO bdcGzYzsjDTO : bdcGzYzQO.getBdcGzYzsjDTOList()) {
                if (StringUtils.isNotBlank(bdcGzYzsjDTO.getFwDcbIndex())) {
                    List<FwHsDO> fwHsDOList = fwHsFeignService.listFwhsByFwDcbIndex(bdcGzYzsjDTO.getFwDcbIndex(), "");
                    if (CollectionUtils.isNotEmpty(fwHsDOList)) {
                        for (FwHsDO fwHsDO : fwHsDOList) {
                            if (StringUtils.isNotBlank(fwHsDO.getBdcdyh())) {
                                Map<String, Object> paramMap = new HashMap<>(1);
                                paramMap.put("bdcdyh", fwHsDO.getBdcdyh());
                                paramsMapList.add(paramMap);
                            }
                        }
                    }

                }
            }
            bdcGzYzQO.setParamList(paramsMapList);
        }

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理规则验证信息
     */
    private List<Map<String, Object>> handleTsxx(List<BdcGzZgzTsxxDTO> bdcGzZgzTsxxDTOList, BdcGzYzTsxxDTO bdcGzYzTsxxDTO) {
        List<Map<String, Object>> resultList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(bdcGzZgzTsxxDTOList)) {
            for (BdcGzZgzTsxxDTO bdcGzZgzTsxxDTO : bdcGzZgzTsxxDTOList) {
                if (bdcGzZgzTsxxDTO != null && CollectionUtils.isNotEmpty(bdcGzZgzTsxxDTO.getTsxx())) {
                    List<String> tsxxList = bdcGzZgzTsxxDTO.getTsxx();
                    // 提示信息去重
                    tsxxList = tsxxList.stream().distinct().collect(Collectors.toList());
                    // 多个提示信息时，页面上展示多条
                    for (String tsxx : tsxxList) {
                        if (StringUtils.isNotBlank(tsxx)) {
                            Map<String, Object> returnMap = new HashMap<>();
                            returnMap.put("msg", tsxx);
                            if (bdcGzZgzTsxxDTO.getYxj() != null) {
                                if (bdcGzZgzTsxxDTO.getYxj() == 1) {
                                    returnMap.put("yzlx", Constants.CONFIRM);
                                } else if (bdcGzZgzTsxxDTO.getYxj() == 2) {
                                    returnMap.put("yzlx", Constants.CONFIRMANDCREATE);
                                } else if (bdcGzZgzTsxxDTO.getYxj() == 3) {
                                    returnMap.put("yzlx", Constants.ALERT);
                                } else if (bdcGzZgzTsxxDTO.getYxj() == 4) {
                                    returnMap.put("yzlx", Constants.ALERT_EXCLUDE);
                                }
                            } else {
                                returnMap.put("yzlx", Constants.ALERT);
                            }
                            if (bdcGzZgzTsxxDTO.getSjljg() != null) {
                                returnMap.put("xzxx", getXzxxBySjljg(bdcGzZgzTsxxDTO.getSjljg()));
                                returnMap.put("sjljg", bdcGzZgzTsxxDTO.getSjljg());
                                returnMap.put("xmid", bdcGzZgzTsxxDTO.getSjljg().get("xmid"));
                            }
                            if (bdcGzZgzTsxxDTO.getGzid() != null) {
                                returnMap.put("gzid", bdcGzZgzTsxxDTO.getGzid());
                            }
                            if (bdcGzZgzTsxxDTO.getGzmc() != null) {
                                returnMap.put("gzmc", bdcGzZgzTsxxDTO.getGzmc());
                            }
                            returnMap.put("cjyzlwsh", cjyzlwsh);
                            if (bdcGzYzTsxxDTO != null && MapUtils.isNotEmpty(bdcGzYzTsxxDTO.getParamMap())) {
                                returnMap.putAll(bdcGzYzTsxxDTO.getParamMap());
                            }
                            resultList.add(returnMap);
                        }
                    }
                }
            }
        }
        return resultList;
    }

    public List<Map<String, Object>> handelResult(List<Map<String, Object>> resultList) {
        List<Map<String, Object>> results = new ArrayList<>(resultList.size());
        if (CollectionUtils.isNotEmpty(resultList)) {
            //取出优先级为3 且存在ZDDYH 标志的数据
            List<Map<String, Object>> duplicateResult = new ArrayList<>(resultList.size());
            List<Map<String, Object>> normalResult = new ArrayList<>(resultList.size());
            Set<String> tsxx = new HashSet<>(resultList.size());
            for (Map<String, Object> result : resultList) {
                String yzlx = result.get("yzlx").toString();
                String msg = result.get("msg").toString();
                if (StringUtils.equals(Constants.ALERT, yzlx) && StringUtils.endsWith(msg, "ZDDYH")) {
                    if (!tsxx.contains(msg)) {
                        tsxx.add(msg);
                        result.put("msg", msg.replace("ZDDYH", ""));
                        duplicateResult.add(result);
                    }
                } else {
                    normalResult.add(result);
                }
            }
            results.addAll(normalResult);
            results.addAll(duplicateResult);
        }
        return results;
    }

    /**
     * @param sjljg 数据流
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据数据流执行结果获取限制信息
     */
    private <T> Object getXzxxBySjljg(Map<String, T> sjljg) {
        for (Map.Entry<String, T> entry : sjljg.entrySet()) {
            Object obj = entry.getValue();
            // 规则数据流返回值中存在验证的返回结果RULERESULT，需要排除掉
            if (obj != null && !StringUtils.equals(entry.getKey(), "RULERESULT")) {
                if (obj instanceof Collection && CollectionUtils.isEmpty((Collection) obj)) {
                    continue;
                }
                return obj;
            }
        }
        return null;
    }

    /**
     * @param jbxxid 基本信息id
     * @return 是否选全
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 验证一证多房的情况下，是否选全
     */
    @Override
    public boolean checkYzdfIsInclude(String jbxxid) {
        // 查询基本信息
        LOGGER.info("创建验证一证多房规则的jbxxid值：{}", jbxxid);
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setJbxxid(jbxxid);
        List<BdcSlXmDO> listSlXm = bdcSlXmService.listBdcSlXm(bdcSlXmQO);
        if (CollectionUtils.isEmpty(listSlXm)) {
            throw new AppException("基本信息id未查询到受理项目，jbxxid" + jbxxid);
        }
        String bdcdyhStr = StringToolUtils.resolveBeanToAppendStr(listSlXm, "getBdcdyh", CommonConstantUtils.ZF_YW_DH);
        LOGGER.info("jbxxid:{}选择项目的bdcdyh合集：{}", jbxxid,bdcdyhStr);
        for (int i = 0; i < listSlXm.size(); i++) {
            String xmid = listSlXm.get(i).getXmid();
            String ybdcqzh = listSlXm.get(i).getYbdcqz();
            String bdcdyh = listSlXm.get(i).getBdcdyh();

            List<BdcSlXmLsgxDO> listSlXmLsgx = bdcSlXmLsgxService.listBdcSlXmLsgxByXmid(xmid);

            if (CollectionUtils.isNotEmpty(listSlXmLsgx)) {
                String yxmid = listSlXmLsgx.get(0).getYxmid();
                if (StringUtils.isNotEmpty(yxmid)) {
                    BdcXmQO bdcXmQO = new BdcXmQO();
                    bdcXmQO.setXmid(yxmid);

                    List<BdcXmDO> list = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if (CollectionUtils.isNotEmpty(list)) {
                        String slbh = list.get(0).getSlbh();
                        bdcXmQO.setXmid("");
                        bdcXmQO.setSlbh(slbh);
                        bdcXmQO.setBdcqzh(ybdcqzh);
                        bdcXmQO.setQszt(CommonConstantUtils.QSZT_VALID);


                        List<BdcXmDO> list2 = bdcXmFeignService.listBdcXm(bdcXmQO);

                        // 如果能查出来>1条，则说明是一证多房，break并返回false
                        if (list2 != null && list2.size() > 1) {
                            if (listSlXm.size() == 1) {
                                // 只勾选一条的话，则肯定是没有选全，直接return
                                return false;
                            }
                            // 一证多房的情况下 要检查其余的xm是否被选中
                            for (int j = 0; j < list2.size(); j++) {
                                // 如果不相等，则不是本条，要判断是否被选中
                                if (!bdcdyh.equals(list2.get(j).getBdcdyh()) && bdcdyhStr.indexOf(list2.get(j).getBdcdyh()) == -1) {
                                    // 不包含的话则说明没有被选中 直接return false
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证商品房交易信息权利人是否一致
     */
    @Override
    public boolean checkQlrSfyz(String jbxxid) {
        if (StringUtils.isBlank(jbxxid)) {
            throw new AppException("获取受理项目信息缺少jbxxid参数");
        }
        boolean result = true;
        List<List<String>> resQlrList = new ArrayList<>();
        //1.根据jbxxid找到所有的项目
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(jbxxid, "xmid");
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            //组合流程的话根据单元号去重
            Set<BdcSlXmDO> bdcSlXmDOSet = new TreeSet<>(Comparator.comparing(BdcSlXmDO::getBdcdyh));
            bdcSlXmDOSet.addAll(bdcSlXmDOList);
            for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOSet) {
                //从受理库查找权利人
                List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrService.listBdcSlSqrByXmid(bdcSlXmDO.getXmid(), CommonConstantUtils.QLRLB_QLR);
                if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                    LOGGER.info("交易带入的申请人信息:{}", bdcSlSqrDOList);
                    //只获取权利人名称和证件号
                    List<String> qlrList = new ArrayList<>();
                    for (BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList) {
                        qlrList.add(bdcSlSqrDO.getSqrmc());
                        qlrList.add(bdcSlSqrDO.getZjh());
                        resQlrList.add(qlrList);
                    }
                } else {
                    resQlrList.add(new ArrayList<>());
                }
            }
        }
        LOGGER.info("权利人信息:{}", resQlrList);
        //4.获取到所有的交易信息后判断权利人是否一致
        if (CollectionUtils.isNotEmpty(resQlrList)) {
            for (int i = 0; i < resQlrList.size(); i++) {
                List<String> firstList = resQlrList.get(0);
                List<String> otherList = resQlrList.get(i);
                if (CollectionUtils.isEqualCollection(firstList, otherList)) {
                    result = true;
                } else {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证是否线上缴费成功 成功状态 sfzt=2&& jkfs=2
     * 返回true说明线上缴费成功 否则false
     * @date : 2020/3/6 8:56
     */
    @Override
    public boolean checkSfxsJfcg(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("验证是否线上缴费成功缺失查询参数");
        }
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
        boolean yzresult = true;
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            //查出来的数据只要不满足sfzt=2 && jkfs=2 就是未缴费成功
            for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                if (!BdcSfztEnum.YJF.key().equals(bdcSlSfxxDO.getSfzt()) || !CommonConstantUtils.JKFS_XSJF.equals(bdcSlSfxxDO.getJkfs())) {
                    yzresult = false;
                    break;
                }
            }
        } else {
            yzresult = false;
        }
        return yzresult;
    }

    /**
     * @param jbxxid 基本信息id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取当前添加购物车的数据的匹配落宗状态
     * @date : 2020/3/13 15:17
     */
    @Override
    public Map checkPplzzt(String jbxxid, String xztzlx) {
        if (StringUtils.isBlank(jbxxid)) {
            throw new AppException("获取匹配落宗状态缺失必要参数");
        }
        LOGGER.info("基本信息id{},选择台账类型{}", jbxxid, xztzlx);
        Map map = Maps.newHashMap();
        if (!(StringUtils.equals(Constants.XZTZLX_CQZ, xztzlx) || StringUtils.equals(Constants.XZTZLX_CF, xztzlx))) {
            return null;
        }
        //获取添加到购物车的数据，包含yxmid
        Map slXmQO = new HashMap();
        slXmQO.put("jbxxid", jbxxid);
        List<BdcSlYwxxDTO> bdcSlYwxxDTOList = bdcSlXmService.listGwcData(slXmQO);
        if (CollectionUtils.isNotEmpty(bdcSlYwxxDTOList)) {
            for (BdcSlYwxxDTO bdcSlYwxxDTO : bdcSlYwxxDTOList) {
                String bdcdyh = bdcSlYwxxDTO.getBdcdyh();
                // 是否为房屋，房屋才进行匹配
                boolean isFwBdcdyh = CommonConstantUtils.BDCLX_TZM_F.equals(BdcdyhToolUtils.getDzwTzm(bdcdyh));
                if (StringUtils.isNotBlank(bdcSlYwxxDTO.getXmid())
                        && StringUtils.isNotBlank(bdcSlYwxxDTO.getBdcdyh())) {
                    if (isFwBdcdyh) {
                        // 匹配关系
                        List<BdcFctdPpgxDO> bdcFctdPpgxDOList = bdcPpFeignService.queryBdcFctdPpgx(bdcSlYwxxDTO.getXmid());
                        if (CollectionUtils.isNotEmpty(bdcFctdPpgxDOList)) {
                            if (bdcFctdPpgxDOList.size() > 1) {
                                map.put("mullz", bdcSlYwxxDTO.getXmid());
                            }
                            BdcFctdPpgxDO bdcFctdPpgxDO = bdcFctdPpgxDOList.get(0);
                            //设置匹配状态
                            if (null == bdcFctdPpgxDO) {
                                map.put("sfpp", "false");
                            } else {
                                map.put("sfpp", "true");
                            }
                        } else {
                            // 查封台账，xmly为1 的不提示是否匹配土地证
                            if (StringUtils.isNotBlank(xztzlx) && StringUtils.equals(xztzlx, Constants.XZTZLX_CF) && (CommonConstantUtils.XMLY_BDC_DM.equals(bdcSlYwxxDTO.getXmly()) || CommonConstantUtils.XMLY_CLBDC_DM.equals(bdcSlYwxxDTO.getXmly()))) {
                                map.put("sfpp", "true");
                            } else {
                                map.put("sfpp", "false");
                            }
                        }
                    }
                    // 落宗
                    List<BdcXnbdcdyhGxDO> bdcXnbdcdyhGxDOList = bdcPpFeignService.queryBdcXnbdcdyhGxByXmid(bdcSlYwxxDTO.getXmid());
                    if (CollectionUtils.isNotEmpty(bdcXnbdcdyhGxDOList)) {
                        if (bdcXnbdcdyhGxDOList.size() > 1) {
                            //存在多条匹配关系
                            map.put("mullz", bdcSlYwxxDTO.getXmid());
                        }
                        BdcXnbdcdyhGxDO bdcXnbdcdyhGxDO = bdcXnbdcdyhGxDOList.get(0);
                        //已匹配落宗的单元号，显示已落宗
                        if (bdcXnbdcdyhGxDO != null) {
                            map.put("sflz", "true");
                        } else {
                            map.put("sflz", "false");
                        }
                        //非虚拟单元号的产权证
                        if (!BdcdyhToolUtils.checkXnbdcdyh(bdcSlYwxxDTO.getBdcdyh())) {
                            //查封台账没有产权证号
                            if (StringUtils.isNotBlank(xztzlx) && Constants.XZTZLX_CQZ.equals(xztzlx)) {
                                String bdcqzh = bdcSlYwxxDTO.getYbdcqz();
                                boolean flag = checkContainsBdcqzhBs(bdcqzh);
                                if (flag || StringUtils.isBlank(bdcqzh)) {
                                    //不动产数据不显示是否落宗匹配和查看按钮
                                    map.put("sflz", null);
                                    map.put("sfpp", null);
                                    //不动产数据可以重新关联，显示重新关联按钮
                                    map.put("sfcxgl", true);
                                }
                            }
                        }
                    } else {
                        //未查询到落宗数据但是单元号正常
                        if (!BdcdyhToolUtils.checkXnbdcdyh(bdcSlYwxxDTO.getBdcdyh())) {
                            //查封台账没有产权证号
                            if (StringUtils.isNotBlank(xztzlx) && StringUtils.equals(xztzlx, Constants.XZTZLX_CQZ)) {
                                String bdcqzh = bdcSlYwxxDTO.getYbdcqz();
                                boolean flag = checkContainsBdcqzhBs(bdcqzh);
                                if (flag || StringUtils.isBlank(bdcqzh)) {
                                    //不动产数据不显示是否落宗匹配和查看按钮
                                    map.put("sflz", null);
                                    map.put("sfpp", null);
                                    //不动产数据可以重新关联，显示重新关联按钮
                                    map.put("sfcxgl", true);
                                } else {
                                    //存量数据显示落宗
                                    map.put("sflz", "true");
                                }
                            } else {
                                //存量数据显示落宗
                                map.put("sflz", "true");
                            }
                        } else {
                            map.put("sflz", "false");
                        }
                    }
                }
                if (!isFwBdcdyh) {
                    map.put("sfpp", "true");
                }
                //多条数据的话只要有一条存在未未匹配或者未落宗 或者多条匹配关系就返回
                if ((map.get("sflz") != null && map.get("sflz") == "false")
                        || map.get("sfpp") != null && map.get("sfpp") == "false"
                        || map.get("mullz") != null) {
                    break;
                }
            }
        }
        return map;
    }

    /**
     * @param jbxxid 基本信息id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证当前选择的项目是否外联了证书
     * @result 1 表示外联了证书 0 表示没有外联证书 2 表示外联了证书但没有注销
     * @date : 2020/4/15 9:19
     */
    @Override
    public int checkSfwlzs(String jbxxid) {
        int result = 1;
        if (StringUtils.isBlank(jbxxid)) {
            throw new AppException("验证是否存在外联证书确实必要参数");
        }
        //获取当前所有购物车的数据
        List<BdcSlYwxxDTO> bdcSlYwxxDTOList = bdcSlXmService.listBdcSLXmDTOByJbxxid(jbxxid);
        if (CollectionUtils.isNotEmpty(bdcSlYwxxDTOList)) {
            //根据xmid查找受理项目历史关系，看是否存在外联证书
            for (BdcSlYwxxDTO bdcSlYwxxDTO : bdcSlYwxxDTOList) {
                List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx(bdcSlYwxxDTO.getXmid(), "", CommonConstantUtils.SF_S_DM);
                if (CollectionUtils.isEmpty(bdcSlXmLsgxDOList)) {
                    result = 0;
                    break;
                } else {
                    for (BdcSlXmLsgxDO bdcSlXmLsgxDO : bdcSlXmLsgxDOList) {
                        //判断外联项目是否注销
                        if (!CommonConstantUtils.SF_S_DM.equals(bdcSlXmLsgxDO.getZxyql())) {
                            result = 2;
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 0: 没有外联证书；1：外联证书没有注销； 2：外联证书并注销
     */
    @Override
    public int checkWlzs(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException(ErrorCode.MISSING_ARG, "验证是否外联证书，缺失参数工作流实例ID");
        }
        int result = 0;
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
            bdcXmLsgxQO.setXmid(bdcXmDTOList.get(0).getXmid());
            bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_S_DM);
            List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
            if(CollectionUtils.isNotEmpty(bdcXmLsgxDOList)){
                result = 1;
                bdcXmLsgxDOList = bdcXmLsgxDOList.stream().filter(t -> Objects.equals(t.getZxyql(), CommonConstantUtils.SF_S_DM)).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(bdcXmLsgxDOList)){
                    result = 2;
                }
            }
        }
        return result;
    }

    @Override
    public boolean checkWltdzXzQl(String xmid, String processDefKey) {
        // 对比当前流程定义ID，是否在不验证证书限制状态的配置中。
        if (wltdzlwxz.indexOf(processDefKey) > -1) {
            return false;
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            return bdcdyZtFeignService.existXzqlByCqxmid(bdcXmDOList.get(0).getXmid(), Integer.parseInt(CommonConstantUtils.BDCLX_TD_DM));
        }
        return false;
    }

    /**
     * @param bdcdyh 不动产单元号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证权籍数据关系是否一致
     * @date : 2020/6/9 16:59
     */
    @Override
    public boolean checkQjGxSfyz(String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new AppException("查询权籍数据不动产单元唯一编号为空");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setBdcdyh(bdcdyh);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            String qjgldm = "";
            BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
            bdcXmFbQO.setXmid(bdcXmDOList.get(0).getXmid());
            List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
            if (CollectionUtils.isNotEmpty(bdcXmFbDOList)) {
                qjgldm = bdcXmFbDOList.get(0).getQjgldm();

            }
            //1.先判断房屋或者土地在权籍是否存在信息，如果存在再用唯一编号去查
            FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(bdcdyh, qjgldm);
            ZdDjdcbDO zdDjdcbDO = zdFeignService.queryZdByBdcdyh(bdcdyh, qjgldm);
            if (fwHsDO != null || zdDjdcbDO != null) {
                List<FwHsDO> fwHsDOList = fwHsFeignService.queryFwhsOnlyByFwbm(bdcXmDOList.get(0).getBdcdywybh(), qjgldm);
                ZdDjdcbDO qjZdxx = zdFeignService.queryZdByDjh(bdcXmDOList.get(0).getBdcdywybh(), qjgldm);
                if (CollectionUtils.isEmpty(fwHsDOList) && qjZdxx == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param jbxxid 基本信息id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证转移换证流程的登记小类是否选择了多个
     * @date : 2020/6/30 8:59
     */
    @Override
    public boolean checkZyhzDjxl(String jbxxid) {
        if (StringUtils.isBlank(jbxxid)) {
            throw new AppException("验证是否存在外联证书确实必要参数");
        }
        //获取当前所有购物车的数据
        List<BdcSlYwxxDTO> bdcSlYwxxDTOList = bdcSlXmService.listBdcSLXmDTOByJbxxid(jbxxid);
        if (CollectionUtils.isNotEmpty(bdcSlYwxxDTOList)) {
            HashSet<String> djxlSet = new HashSet<>(bdcSlYwxxDTOList.size());
            for (BdcSlYwxxDTO bdcSlYwxxDTO : bdcSlYwxxDTOList) {
                if (StringUtils.isNotBlank(bdcSlYwxxDTO.getDjxl())) {
                    djxlSet.add(bdcSlYwxxDTO.getDjxl());
                }
            }
            //登记小类不为空且去重后数量为2(转移加换证)
            if (CollectionUtils.isNotEmpty(djxlSet) && CollectionUtils.size(djxlSet) == 2) {
                List<String> djlxList = new ArrayList<>(2);
                for (String djxl : djxlSet) {
                    //判断前三位的登记类型
                    String djlx = djxl.substring(0, 3);
                    djlxList.add(djlx);
                }
                //必须包含转移和换证
                return djlxList.contains(CommonConstantUtils.DJLX_ZYDJ_DM.toString()) && djlxList.contains(CommonConstantUtils.DJLX_QTDJ_DM.toString());
            }
        }
        return false;
    }

    @Override
    public boolean checkSpfLc(String gzldyid) {
        if (CollectionUtils.isNotEmpty(spfdyidList) && StringUtils.isNotBlank(gzldyid) && spfdyidList.contains(gzldyid)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkCbfFbfSfyz(String jbxxid) {
        if (StringUtils.isBlank(jbxxid)) {
            throw new AppException("获取受理项目信息缺少jbxxid参数");
        }
        boolean result = true;
        List<String> resQlrList = new ArrayList<>();
        //1.根据jbxxid找到所有的项目
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(jbxxid, "");
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            Set<BdcSlXmDO> bdcSlXmDOSet = new TreeSet<>(Comparator.comparing(BdcSlXmDO::getBdcdyh));
            bdcSlXmDOSet.addAll(bdcSlXmDOList);
            if (bdcSlXmDOSet.size() > 1) {
                for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOSet) {
                    //获取权籍承包方信息
                    List<CbzdCbfDO> cbzdCbfDOList = cbzdFeignService.listCbfByCbzdDcbcbfrelIndex(bdcSlXmDO.getQjid(), bdcSlXmDO.getQjgldm());
                    List<String> qlrList = new ArrayList<>();
                    LOGGER.info("bdcdyh:{},承包方信息:{}", bdcSlXmDO.getBdcdyh(), cbzdCbfDOList);
                    if (CollectionUtils.isNotEmpty(cbzdCbfDOList)) {
                        //只获取承包方名称和证件号
                        for (CbzdCbfDO cbzdCbfDO : cbzdCbfDOList) {
                            if (Objects.nonNull(cbzdCbfDO)) {
                                qlrList.add(cbzdCbfDO.getCbfmc());
                                qlrList.add(cbzdCbfDO.getCbfzjhm());
                            } else {
                                qlrList.add("");
                            }
                        }
                    } else {
                        qlrList.add("");
                    }
                    //获取权籍发包方信息
                    List<CbzdFbfDO> cbzdFbfDOList = cbzdFeignService.listFbfByBdcdyh(bdcSlXmDO.getBdcdyh(), bdcSlXmDO.getQjgldm());
                    LOGGER.info("bdcdyh:{},发包方信息:{}", bdcSlXmDO.getBdcdyh(), cbzdFbfDOList);
                    if (CollectionUtils.isNotEmpty(cbzdFbfDOList)) {
                        //获取发包方名称和法人证件号
                        for (CbzdFbfDO cbzdFbfDO : cbzdFbfDOList) {
                            if (Objects.nonNull(cbzdFbfDO)) {
                                qlrList.add(cbzdFbfDO.getFbfmc());
                                qlrList.add(cbzdFbfDO.getFzrzjhm());
                            } else {
                                qlrList.add("");
                            }
                        }
                    } else {
                        qlrList.add("");
                    }
                    if (CollectionUtils.isNotEmpty(resQlrList)) {
                        if (!CollectionUtils.isEqualCollection(resQlrList, qlrList)) {
                            result = false;
                            break;
                        }
                    } else {
                        resQlrList.addAll(qlrList);
                    }

                }
            }
        }
        return result;


    }

    public Object handlException(Exception e) {
        //获取规则的时候会抛出异常，当code为  时表示未配置验证项直接返回空集合
        GtFeignException gte = null;
        if (e.getCause() instanceof GtFeignException) {
            gte = (GtFeignException) e.getCause();
            if (gte != null) {
                String responseBody = gte.getMsgBody();
                Map bodyMap = JSONObject.parseObject(responseBody, Map.class);
                if (MapUtils.getInteger(bodyMap, "code") != null && StringUtils.isNotBlank(MapUtils.getString(bodyMap, "msg"))) {
                    Integer errorCode = MapUtils.getInteger(bodyMap, "code");
                    if (errorCode == 101) {
                        return Collections.emptyList();
                    } else {
                        LOGGER.error("规则验证异常{}", bodyMap.get("detail"));
                        throw new AppException("规则验证异常，请联系管理员" + bodyMap.get("detail"));
                    }
                }
            }
        } else {
            LOGGER.error("规则验证异常！", e);
            throw new AppException("规则验证异常，请联系管理员");
        }
        return null;
    }


    /**
     * @param bdcdyh 不动产单元号
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 验证该xm是否满足省直房改办的状态
     */
    @Override
    public boolean checkSfszfgf(String bdcdyh) {
        LOGGER.info("验证省直房改房bdcdyh：{}", bdcdyh);
        if (StringUtils.isNotBlank(bdcdyh)) {
            List<BdcXmDO> listXm = bdcXmfbFeignService.listBdcFgfXmByBdcdyh(bdcdyh);
            if (CollectionUtils.isNotEmpty(listXm)) {
                LOGGER.info("验证省直房改房bdcdyh查询房改房状态数量：{}", listXm.size());
                BdcXmDO bdcXmDO = listXm.get(0);
                String xmid = bdcXmDO.getXmid();
                String bdcqzh = bdcXmDO.getBdcqzh();
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(xmid);
                bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                List<BdcQlrDO> listQlr = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                LOGGER.info("验证省直房改房查询权利人入参：{}", bdcQlrQO);
                if (CollectionUtils.isNotEmpty(listQlr)) {
                    for (BdcQlrDO bdcQlrDO : listQlr) {
                        LOGGER.info("验证省直房改房查询权利人：{}", bdcQlrDO);
                        Map map = new HashMap();
                        map.put("xm", bdcQlrDO.getQlrmc());
                        map.put("zjzl", bdcQlrDO.getZjzl());
                        map.put("sfzhm", bdcQlrDO.getZjh());
                        map.put("bdcqzh", bdcqzh);
                        LOGGER.info("调用房改房验证参数为：{}", map);
                        Object response = null;
                        try {
                            response = exchangeInterfaceFeignService.requestInterface("fgf_mdyz", map);
                        } catch (Exception e) {
                            return false;
                        }
                        if (null != response) {
                            Map responesMap = (Map) response;
                            LOGGER.info("调用房改房验证返回值为：{}", responesMap);
                            if (responesMap.containsKey("yxcode") && null != responesMap.get("yxcode")
                                    && responesMap.containsKey("yxdata") && null != responesMap.get("yxdata")) {
                                String yxcode = responesMap.get("yxcode").toString();
                                String yxdata = responesMap.get("yxdata").toString();
                                if (yxcode.equals(CommonConstantUtils.RESPONSE_SUCCESS) && "0".equals(yxdata)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    @Override
    public boolean checkCbfDkqbtj(String jbxxid) {
        if (StringUtils.isBlank(jbxxid)) {
            throw new AppException("获取受理项目信息缺少jbxxid参数");
        }
        boolean result = false;
        //1.根据jbxxid找到所有的项目
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(jbxxid, "");
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {

            List<BdcSlXmDO> slXmDOList = bdcSlXmDOList.stream().filter(bdcSlXmDO -> StringUtils.isBlank(bdcSlXmDO.getQjid())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(slXmDOList)) {
                throw new AppException("受理项目表存在qjid为空,请检查数据");
            }
            List<BdcSlXmDO> qlrXmList = bdcSlXmDOList.stream().filter(bdcSlXmDO -> StringUtils.isBlank(bdcSlXmDO.getQlr())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(qlrXmList)) {
                throw new AppException("受理项目表存在承包方为空,请检查数据");
            }
            Set<BdcSlXmDO> bdcSlXmDOSet = new TreeSet<>(Comparator.comparing(BdcSlXmDO::getQlr));
            bdcSlXmDOSet.addAll(bdcSlXmDOList);
            if (bdcSlXmDOSet.size() == 1) {
                Set<BdcSlXmDO> slXmDOSet = new TreeSet<>(Comparator.comparing(BdcSlXmDO::getQjid));
                slXmDOSet.addAll(bdcSlXmDOList);
                //承包权qjid存储关系表字段CBZD_DCBCBFREL_INDEX,根据qjid查询关系表获取承包方主键，获取当前承包方所有地块信息
                List<CbzdDcbcbfRelDO> cbzdDcbcbfRelDOList = cbzdFeignService.listCbfCbzdDcbcbfRelList(bdcSlXmDOList.get(0).getQjid(), bdcSlXmDOList.get(0).getQjgldm());
                if (CollectionUtils.isNotEmpty(cbzdDcbcbfRelDOList) && CollectionUtils.size(bdcSlXmDOList) == slXmDOSet.size()) {
                    for (CbzdDcbcbfRelDO cbzdDcbcbfRelDO : cbzdDcbcbfRelDOList) {
                        List<BdcSlXmDO> list = slXmDOSet.stream().filter(slXmDO -> slXmDO.getBdcdyh().equals(cbzdDcbcbfRelDO.getBdcdyh()) && slXmDO.getQjid().equals(cbzdDcbcbfRelDO.getCbzdDcbcbfrelIndex())).collect(Collectors.toList());
                        if (CollectionUtils.isEmpty(list)) {
                            return result;
                        }
                    }
                    result = true;

                }
            }
        }
        return result;


    }

    /**
     * @param jbxxid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证承包经营权在转移或者变更时是否外联了经营权证书
     * @date : 2020/11/4 17:21
     */
    @Override
    public boolean checkCbjyqSfwlzs(String jbxxid) {
        if (StringUtils.isBlank(jbxxid)) {
            throw new AppException("验证承包经营权查询受理项目信息缺失基本信息id");
        }
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(jbxxid, "xmid");
        List<BdcSlXmLsgxDO> allSlXmLsgxDOList = new ArrayList<>(bdcSlXmDOList.size());
        Map<String, String> xmlsgxMap = new HashMap<>(bdcSlXmDOList.size());
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            //获取当前受理项目表的所有外联证书
            for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx(bdcSlXmDO.getXmid(), "", CommonConstantUtils.SF_S_DM);
                if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                    allSlXmLsgxDOList.addAll(bdcSlXmLsgxDOList);
                }
            }
            if (CollectionUtils.isNotEmpty(allSlXmLsgxDOList)) {
                for (BdcSlXmLsgxDO bdcSlXmLsgxDO : allSlXmLsgxDOList) {
                    xmlsgxMap.put(bdcSlXmLsgxDO.getYxmid(), bdcSlXmLsgxDO.getYxmid());
                }
            }
            String qjid = bdcSlXmDOList.get(0).getQjid();
            //根据权籍id找到权籍的承包方信息，获取承包方名称和证件号
            if (StringUtils.isNotBlank(qjid)) {
                List<CbzdCbfDO> cbzdCbfDOList = cbzdFeignService.listCbfByCbzdDcbcbfrelIndex(qjid, bdcSlXmDOList.get(0).getQjgldm());
                if (CollectionUtils.isNotEmpty(cbzdCbfDOList)) {
                    //根据名称+证件号，关联xmid找到bdc_xm表数据，查找qszt=1 qllx为9 的数据
                    //该数据需要全部被当前受理项目外联
                    BdcQlrQO bdcQlrQO = new BdcQlrQO();
                    bdcQlrQO.setQlrmc(cbzdCbfDOList.get(0).getCbfmc());
                    bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                    bdcQlrQO.setZjh(cbzdCbfDOList.get(0).getCbfzjhm());
                    List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listCbjyqXm(bdcQlrQO);
                    if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                        for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                            if (!xmlsgxMap.containsKey(bdcXmDTO.getXmid())) {
                                return false;
                            }
                        }
                    }
                } else {
                    throw new AppException("没有查询到权籍承包方信息,权籍id为:" + qjid);
                }
            }
        } else {
            throw new AppException("验证承包经营权外联证书未查询到项目信息,基本信息id为:" + jbxxid);
        }
        return true;
    }

    /**
     * @param slXmDTO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 验证外网申请的数据是否和登记一致
     * @date : 2020/11/18 17:18
     */
    @Override
    public boolean checkWwsqDjSjSfyz(BdcSlXmDTO slXmDTO, String lclx) {
        if (slXmDTO == null) {
            throw new AppException("外网创建项目时，缺失slXmDTO");
        }
        LOGGER.info("处理其他规则验证参数，受理模型实体：{}", JSONObject.toJSONString(slXmDTO));
        if (slXmDTO.getBdcSlXm() == null) {
            throw new AppException("外网创建项目时，缺失bdcSlXm");
        }
        List<BdcSlSqrDO> listSqr = slXmDTO.getBdcSlSqrDOList();

        // 上一手项目id
        String yxmid = "";
        List<BdcSlXmLsgxDO> listLsgx = slXmDTO.getBdcSlXmLsgxList();
        if (CollectionUtils.isNotEmpty(listLsgx)) {
            yxmid = listLsgx.get(0).getYxmid();
        } else {
            throw new AppException("外网创建项目时，缺失历史关系");
        }

        LOGGER.info("验证外网数据正确性，yxmid:{}", yxmid);

        // 验证抵押人是否一致
        if (CollectionUtils.isNotEmpty(listSqr)) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(yxmid);
            if (StringUtils.equals("dydj", lclx)) {
                bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            } else {
                bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
            }

            List listQlr = bdcQlrFeignService.listBdcQlr(bdcQlrQO);

            if (CollectionUtils.isEmpty(listQlr)) {
                LOGGER.info("验证外网数据正确性，登记库原权利人集合为空");
                return false;
            }
            int ydjQlrCount = listQlr.size();
            int wwsqSqrCount = 0;
            for (BdcSlSqrDO bdcSlSqrDO : listSqr) {
                if (StringUtils.equals(bdcSlSqrDO.getSqrlb(), CommonConstantUtils.QLRLB_YWR)) {
                    bdcQlrQO.setQlrmc(bdcSlSqrDO.getSqrmc());
                    listQlr = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                    LOGGER.info("验证外网数据正确性查询权利人参数:{}", JSONObject.toJSONString(bdcQlrQO));
                    if (CollectionUtils.isEmpty(listQlr)) {
                        LOGGER.info("验证外网数据正确性，查询权利人集合为空");
                        return false;
                    }
                    wwsqSqrCount++;
                }
            }
            if (wwsqSqrCount != ydjQlrCount) {
                LOGGER.info("验证外网数据正确性，登记库原权利人与外网权利人数量不一致");
                return false;
            }
        }

        // 验证原坐落是否存在
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(yxmid);
        String zl = slXmDTO.getBdcSlXm().getZl();
        if (StringUtils.isNotBlank(zl)) {
            bdcXmQO.setZl(zl);
        } else {
            throw new AppException("外网创建项目时，缺失zl");
        }
        List<BdcXmDO> listXm = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(listXm)) {
            return false;
        }


        // 验证原产权证号是否一致
        String ybdcqzh = slXmDTO.getBdcSlXm() == null ? "" : slXmDTO.getBdcSlXm().getYbdcqz();
        LOGGER.info("验证外网数据正确性，原产权证号:{}", ybdcqzh);

        bdcXmQO.setZl("");
        listXm = bdcXmFeignService.listBdcXm(bdcXmQO);

        if (StringUtils.isNotBlank(ybdcqzh) && CollectionUtils.isNotEmpty(listXm)) {
            String bdcqzh = listXm.get(0).getBdcqzh();
            if (StringUtils.isBlank(bdcqzh)) {
                LOGGER.info("验证外网数据正确性，登记系统的原产权证号为空");
                return false;
            }
            if (ybdcqzh.length() != bdcqzh.length()) {
                LOGGER.info("验证外网数据正确性，登记系统的原产权证号与外网的产权证号长度不一致");
                return false;
            }
            LOGGER.info("验证外网数据正确性登记的原产权证号:{}", bdcqzh);
            for (String singelYbdcqzh : ybdcqzh.split(",")) {
                if (bdcqzh.indexOf(singelYbdcqzh) == -1) {
                    LOGGER.info("验证外网数据正确性，原产权证号未查询到数据:{}", singelYbdcqzh);
                    return false;
                }
            }
        } else {
            throw new AppException("外网创建项目时，缺失ybdcqzh");
        }

        return true;
    }

    /**
     * 根据工作流实例ID，获取流程正在流转的任务，验证对应表单必填项
     *
     * @param gzlslid 工作流实例ID
     * @return 表单必填项验证结果
     */
    @Override
    public Object checkFormBtxYz(String gzlslid) {
        List<TaskData> taskDataList = processTaskClient.processLastTasks(gzlslid);
        TaskData taskData = taskDataList.get(0);
        if (Objects.nonNull(taskData)) {
            Object object = this.bdcCheckFeignService.feignBtxyz(taskData.getFormKey(), taskData.getProcessInstanceId(), taskData.getTaskId());
            if (Objects.nonNull(object)) {
                List<BdcBtxyzVO> btxyzVOList = JSONArray.parseArray(JSON.toJSONString(object), BdcBtxyzVO.class);
                if (CollectionUtils.isNotEmpty(btxyzVOList)) {
                    StringBuilder sb = new StringBuilder();
                    for (BdcBtxyzVO bdcBtxyzVO : btxyzVOList) {
                        if (CollectionUtils.isNotEmpty(bdcBtxyzVO.getYzxx())) {
                            for (String msg : bdcBtxyzVO.getYzxx()) {
                                sb.append(msg).append(";");
                            }
                        }
                    }
                    if (StringUtils.isNotBlank(sb.toString())) {
                        return sb.toString();
                    }
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * 接口返回值为： {"head":{"returncodems":"成功","returncode":"0000"},"data":{"mmhtzt":"1","mmhtztms":"未备案"}}
     */
    @Override
    public Object checkClfWqxxYz(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                Map<String, String> param = new HashMap<>(4);
                BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                param.put("cqzh", bdcXmDO.getBdcqzh());
                Object response = this.exchangeInterfaceFeignService.requestInterface("clfwqxx", param);
                if (Objects.nonNull(response)) {
                    LOGGER.info("clfwqxx 接口返回值信息为：{}，产权证号为{}", response, bdcXmDO.getBdcqzh());
                    Map resultMap = (Map) response;
                    Map dataMap = (Map) resultMap.get("data");
                    if (dataMap.containsKey("mmhtzt")) {
                        return dataMap.get("mmhtzt");
                    }
                }
            }
        }
        return null;
    }

    /**
     * 校验收费信息是否需要作废
     * 缴费书编码不为空，作废状态为空的情况需要作废
     */
    @Override
    public Object checkSfzfYz(String gzlslid) {
        boolean needZf = false;
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcSlSfxxDO> bdcSlSfxxDOS = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOS)) {
                for (BdcSlSfxxDO sfxx : bdcSlSfxxDOS) {
                    if (StringUtils.isNotBlank(sfxx.getJfsbm())
                            && (Objects.isNull(sfxx.getSfzf()) || sfxx.getSfzf() != 1)) {
                        needZf = true;
                        break;
                    }
                }
            }
        }
        return needZf;
    }

    /**
     * 校验收费信息是否需要冲红
     * 缴费书编码不为空，发票号不为空的情况需要冲红操作
     */
    @Override
    public Object checkSfchYz(String gzlslid) {
        boolean needCh = false;
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcSlSfxxDO> bdcSlSfxxDOS = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOS)) {
                for (BdcSlSfxxDO sfxx : bdcSlSfxxDOS) {
                    if (StringUtils.isNotBlank(sfxx.getJfsbm()) && StringUtils.isNotBlank(sfxx.getFph())) {
                        needCh = true;
                        break;
                    }
                }
            }
        }
        return needCh;
    }

    @Override
    public Boolean checkLcYtsswzt(String processInsId) {
        if (StringUtils.isNotBlank(processInsId)) {
            List<BdcSlHsxxDO> bdcSlHsxxDOList = this.bdcSlHsxxService.listBdcSlHsxxByGzlslid(processInsId, "");
            if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                boolean sfws = true;
                for (BdcSlHsxxDO bdcSlHsxxDO : bdcSlHsxxDOList) {
                    //已推送核税未通过的不提示
                    if (!(CommonConstantUtils.WSZT_YWS.equals(String.valueOf(bdcSlHsxxDO.getYtsswzt())) && CommonConstantUtils.WSZT_YWS.equals(bdcSlHsxxDO.getHszt()))) {
                        sfws = false;
                    }
                }
                return sfws;
            }
        }
        return null;
    }

    /**
     * 是否申报核税
     *
     * @param htbh
     * @return
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @Override
    public Boolean checkSfSbhs(String htbh) {
        if (StringUtils.isBlank(htbh)) {
            throw new MissingArgumentException("查询是否申报核税接口缺失htbh");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("htbh", htbh);
        LOGGER.info("调用sw_sfhsbyhtbh接口入参：{}", htbh);
        Object obj = exchangeInterfaceFeignService.requestInterface("sw_sfhsbyhtbh", jsonObject);
        if (null != obj) {
            LOGGER.info("调用sw_sfhsbyhtbh返回值：{}", JSONObject.toJSONString(obj));
            Map resultMap = (Map) obj;
            // 按照需求文档描述 当返回code为1时 代表已申报  0是未申报 其他为异常
            if (resultMap.containsKey("code") && resultMap.get("code") != null && StringUtils.equals("0", resultMap.get("code").toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取配置的pplzztBdcqzhBs 用于验证匹配落宗状态
     *
     * @return
     */
    @Override
    public List pplzztBdcqzhBs() {
        return pplzztBdcqzhBsList;
    }


    /**
     * @param bdcdyh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据单元号查询现势产权是否备案
     * @date : 2021/3/11 19:36
     */
    @Override
    public Object checkWqbaZt(String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new AppException("验证产权证是否备案查询产权证是否备案确实单元号");
        }
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXmXscq(Collections.singletonList(bdcdyh));
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            Map<String, String> param = new HashMap<>(4);
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            param.put("cqzh", bdcXmDO.getBdcqzh());
            Object response = this.exchangeInterfaceFeignService.requestInterface("clfwqxx", param);
            if (Objects.nonNull(response)) {
                LOGGER.info("clfwqxx 接口返回值信息为：{}，产权证号为{}", response, bdcXmDO.getBdcqzh());
                Map resultMap = (Map) response;
                Map dataMap = (Map) resultMap.get("data");
                if (dataMap.containsKey("mmhtzt")) {
                    return dataMap.get("mmhtzt");
                }
            }
        }
        return null;
    }

    @Override
    public boolean checkYcslBdxx(String xmid, Boolean spflc) {
        //比对结果
        boolean bdResult = true;
        if (StringUtils.isBlank(xmid)) {
            return false;
        }
        List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrService.listBdcSlSqrByXmid(xmid, "");
        if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
            //1.商品房验证权利人，二手房验证买卖双方
            //2.房屋套次为其他套验证通过
            //3.婚姻信息和套次信息比对通过验证通过
            for (BdcSlSqrDO slSqrDO : bdcSlSqrDOList) {
                if (!CommonConstantUtils.SF_S_DM.equals(slSqrDO.getHyxxbdjg()) || !CommonConstantUtils.SF_S_DM.equals(slSqrDO.getFwtcbdjg())) {
                    if (!StringUtils.equals(CommonConstantUtils.FWTC_QT, slSqrDO.getSbfwtc()) && (!Boolean.TRUE.equals(spflc) || StringUtils.equals(CommonConstantUtils.QLRLB_QLR, slSqrDO.getSqrlb()))) {
                        bdResult = false;
                        break;
                    }
                }
            }
        }
        if (!bdResult) {
            //验证不通过判断是否为非住宅
            List<BdcSlFwxxDO> bdcSlFwxxDOList = bdcSlFwxxService.listBdcSlFwxxByXmid(xmid);
            if (CollectionUtils.isNotEmpty(bdcSlFwxxDOList) && StringUtils.isNotBlank(fwtcghyt) && bdcSlFwxxDOList.get(0).getFwyt() != null && !CommonUtil.indexOfStrs(fwtcghyt.split(CommonConstantUtils.ZF_YW_DH), bdcSlFwxxDOList.get(0).getFwyt().toString())) {
                bdResult = true;
            }
        }
        return bdResult;
    }

    /**
     * 判断是否包含配置的不动产数据的产权证号标识
     *
     * @param bdcqzh
     * @return
     */
    public boolean checkContainsBdcqzhBs(String bdcqzh) {
        boolean flag = false;
        if (StringUtils.isBlank(bdcqzh)) {
            return flag;
        } else {
            for (String bs : pplzztBdcqzhBsList) {
                flag = bdcqzh.contains(bs);
                if (flag) {
                    return flag;
                }
            }
        }
        return flag;
    }


    /**
     * @param htbh 合同编号
     * @return 接口结果
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查询需要带入合同备案信息
     */
    @Override
    public Object drHtbaxx(String htbh, String gzldyid, String cxlx) {
        LOGGER.info("添加不动产单元规则带入合同备案信息，查询开始,合同编号{}查询类型{}！", htbh, cxlx);
        if (StringUtils.isBlank(htbh) || StringUtils.equals(htbh, CommonConstantUtils.BDC_GZ_RPC_RC_MRZ)) {
            LOGGER.info("没有合同编号，不执行查询备案信息的接口");
            return null;
        }

        if (StringUtils.isBlank(gzldyid)) {
            throw new MissingArgumentException("缺失参数gzldyid");
        }

        FcjyBaxxDTO fcjyBaxxDTO = new FcjyBaxxDTO();
        BdcBahtQO bdcBahtQO = new BdcBahtQO();
        bdcBahtQO.setHtbh(htbh);
        Object response = null;
        if ((CollectionUtils.isNotEmpty(spfdyidList) && spfdyidList.contains(gzldyid)) || (StringUtils.isNotBlank(cxlx) && StringUtils.equals("spf", cxlx))) {
            LOGGER.info("南通合同选择台账，规则服务，调用接口beanId:rd_spfhtxx,参数：{}", JSONObject.toJSONString(bdcBahtQO));
            response = exchangeInterfaceFeignService.requestInterface("rd_spfhtxx", bdcBahtQO);
        } else {
            LOGGER.info("南通合同选择台账，规则服务，调用接口beanId:rd_clfhtxx,参数：{}", JSONObject.toJSONString(bdcBahtQO));
            response = exchangeInterfaceFeignService.requestInterface("rd_clfhtxx", bdcBahtQO);
        }
        if (null != response) {
            LOGGER.info("南通合同选择台账，规则服务，调用接口返回值：{}", JSONObject.toJSONString(response));
            /*String json = test();
            JSONObject jsonObj = JSONObject.parseObject(json);*/
            JSONObject jsonObj = JSONObject.parseObject(JSONObject.toJSONString(response));
            JSONArray jsonArr = jsonObj.getJSONArray("data");
            if (CollectionUtils.isNotEmpty(jsonArr)) {
                JSONObject data = (JSONObject) jsonArr.get(0);
                JSONObject jsonObject = data.getJSONObject("jyxxDTO");
                fcjyBaxxDTO = JSONObject.parseObject(jsonObject.toJSONString(), FcjyBaxxDTO.class);
                return fcjyBaxxDTO;
            }
        }

        return fcjyBaxxDTO;
    }

    @Override
    public Object checkSjclSfsc(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjclByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
                // 过滤出必填的收件材料
                Map<String, BdcSlSjclDO> sjclMap = bdcSlSjclDOList.stream().filter(t -> CommonConstantUtils.SF_S_DM.equals(t.getSfbc()))
                        .filter(t -> StringUtils.isNotBlank(t.getWjzxid()))
                        .collect(Collectors.toMap(BdcSlSjclDO::getWjzxid, BdcSlSjclDO -> BdcSlSjclDO));

                if (MapUtils.isNotEmpty(sjclMap)) {
                    StringJoiner joiner = new StringJoiner(CommonConstantUtils.ZF_YW_DH);
                    Map<String, Long> map = storageClient.getFoldersCount(new ArrayList<>(sjclMap.keySet()), null, 1, null, 1);
                    if (MapUtils.isNotEmpty(map)) {
                        AtomicBoolean hasNullSjcl = new AtomicBoolean(false);
                        map.forEach((key, value) -> {
                            if (value == 0) {
                                hasNullSjcl.set(true);
                                String sjclmc = sjclMap.get(key).getClmc();
                                joiner.add(sjclmc);
                            }
                        });
                        if (hasNullSjcl.get()) {
                            return joiner.toString();
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断当前数据是否做过资金监管
     * @date : 2021/7/22 15:19
     */
    @Override
    public String checkSfzjjg(String xmid) {
        BdcSlZjjgQO bdcSlZjjgQO = new BdcSlZjjgQO();
        bdcSlZjjgQO.setCqxmid(xmid);
        List<BdcSlZjjgDO> bdcSlZjjgDOList = bdcSlZjjgService.listBdcSlZjjg(bdcSlZjjgQO);
        //如果为空默认不通过(没有做过资金监管)
        if (CollectionUtils.isEmpty(bdcSlZjjgDOList)) {
            return BdcZjjgDkztEnum.MYJG.getDm();
        }
        String hasZjjg = BdcZjjgDkztEnum.MYJG.getDm();
        for(BdcSlZjjgDO bdcSlZjjgDO:bdcSlZjjgDOList) {
            // 判断资金监管状态，非撤销状态则做过资金监管，并且要求流程已办结
            //用工作流实例id查是否办结,2 表示已办结
            Integer lczt = 0;
            if (StringUtils.isNotBlank(bdcSlZjjgDO.getGzlslid())) {
                StatisticsProcDto statisticsProcDto = statisticsProcessClient.queryProcessStaticsInfo(bdcSlZjjgDO.getGzlslid());
                if (Objects.nonNull(statisticsProcDto)) {
                    lczt = statisticsProcDto.getProcStatus();
                    LOGGER.warn("当前资金监管流程{}，流程状态{}", bdcSlZjjgDO.getGzlslid(), lczt);
                }
            }
            if (!Objects.equals(CommonConstantUtils.ZJJG_ZT_YCX, bdcSlZjjgDO.getZt()) && Objects.equals(2, lczt)) {
               if(StringUtils.equals("资金监管（有贷款）",bdcSlZjjgDO.getZjjgqrxx())){
                   hasZjjg = BdcZjjgDkztEnum.DDK.getDm();
               } else if(StringUtils.equals("资金监管（无贷款）",bdcSlZjjgDO.getZjjgqrxx())){
                   hasZjjg = BdcZjjgDkztEnum.WDK.getDm();
               }
            }
        }
        return hasZjjg;
    }

    /**
     * 验证资金监管是否办理撤销流程
     * <p>资金监管流程办理时，验证流程是否办理过资金监管撤销流程</p>
     */
    @Override
    public boolean sfblZjjgcx(String xmid) {
        if(StringUtils.isNotBlank(xmid)){
            BdcSlZjjgQO bdcSlZjjgQO = new BdcSlZjjgQO();
            bdcSlZjjgQO.setCqxmid(xmid);
            List<BdcSlZjjgDO> bdcSlZjjgDOList = bdcSlZjjgService.listBdcSlZjjg(bdcSlZjjgQO);
            if(CollectionUtils.isNotEmpty(bdcSlZjjgDOList)){
                for(BdcSlZjjgDO bdcSlZjjgDO:bdcSlZjjgDOList){
                    if(!Objects.equals(CommonConstantUtils.ZJJG_ZT_YCX, bdcSlZjjgDO.getZt())){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 验证是否资金监管,优先查询常州市存量房交易一体化平台
     *
     * @param xmid
     * @param htbh
     */
    @Override
    public String sfzjjgYthpt(String xmid, String htbh) {
        LOGGER.info("一体化资金监管平台,监管请求{}", htbh);
        if(StringUtils.isNotBlank(htbh) && htbh.equals("0")){
            return BdcZjjgDkztEnum.WXYZ.getDm();
        }
        ZjjgResponseDto zjjgResponseDto = null;
        if(StringUtils.isNotBlank(htbh)){
            //优先查询一体化平台
            ZjjgRequest zjjgRequest = new ZjjgRequest();
            zjjgRequest.setCxtjlx("03");
            zjjgRequest.setCxbh(htbh);
            try {
                Object zjj_zjjkbzcx = exchangeInterfaceFeignService.sjptRequestInterface("zjj_zjjkbzcx", zjjgRequest);
                LOGGER.info("一体化资金监管平台,监管请求{}，监管返回{}", JSON.toJSONString(zjjgRequest), JSON.toJSONString(zjj_zjjkbzcx));
                zjjgResponseDto = JSON.parseObject(JSON.toJSONString(zjj_zjjkbzcx),ZjjgResponseDto.class);
                LOGGER.info("一体化资金监管平台,监管请求{}，监管返回{}", JSON.toJSONString(zjjgRequest), JSON.toJSONString(zjjgResponseDto));
            }catch (Exception e){
                //没有返回值
                LOGGER.info("一体化资金监管平台,监管请求{}，监管返回错误{}",
                        JSON.toJSONString(zjjgRequest),
                        e.getMessage());
                return BdcZjjgDkztEnum.MYJG.getDm();
            }
        }
        //如果没有返回，查询本地,如果有返回则保存结果并返回
        if(Objects.isNull(zjjgResponseDto)
                || Objects.isNull(zjjgResponseDto.getCode())
                || (!zjjgResponseDto.getCode().equals("00000000"))

        ){
            //没有返回值
            return BdcZjjgDkztEnum.MYJG.getDm();
        }
        //保存返回值，直接返回结果
        zjjgResponseDto.getData().setHtbh(htbh);
        bdcSlZjjgService.saveYthptZjjg(zjjgResponseDto);
        String dkzt = zjjgResponseDto.getData().getDkzt();
        if(BdcZjjgDkztEnum.DDK.getDm().equals(dkzt)
                || BdcZjjgDkztEnum.WDK.getDm().equals(dkzt)){
            return dkzt;
        }
        //返回值非法
        return BdcZjjgDkztEnum.MYJG.getDm();
    }

    /**
     * @param yxmid 项目ID
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 验证土地证是否关联商品房首次登记
     * @result 1 表示关联了 0 表示没有关联
     */
    @Override
    public int sfglSpfscdj(String yxmid) {
        int result = 0;
        BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
        bdcXmLsgxQO.setYxmid(yxmid);
        bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_S_DM);
        List<BdcXmLsgxDO> bdcXmLsgxDOS = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
        if(CollectionUtils.isNotEmpty(bdcXmLsgxDOS)){
            List<String> xmidList = bdcXmLsgxDOS.stream().map(BdcXmLsgxDO::getXmid).collect(Collectors.toList());
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmidList(xmidList);
            List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOS)){
                bdcXmDOS = bdcXmDOS.stream().filter(bdcXmDO -> StringUtils.isNotBlank(bdcXmDO.getGzldyid())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(spfscdjGzldyids)){
                    for (BdcXmDO bdcXmDO : bdcXmDOS) {
                        if(spfscdjGzldyids.contains(bdcXmDO.getGzldyid())){
                            result = 1;
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }

    @Override
    public Object checkTdHsCzyg(String bdcdyh) {
        if(StringUtils.isBlank(bdcdyh)){
            throw new AppException(ErrorCode.MISSING_ARG, "验证该土地上的户室是否做过预告户室登记未获取到不动产单元号");
        }
        if(bdcdyh.contains(CommonConstantUtils.DZWTZM_TD)){
            List<BdcYgDO> bdcYgDOS = bdcYgFeignService.listBdcYgByDjh(bdcdyh.substring(0, 19));
            if(CollectionUtils.isNotEmpty(bdcYgDOS)){
                String bdcdyhListStr = bdcYgDOS.stream().map(bdcYgDO -> bdcYgDO.getBdcdyh()).collect(Collectors.joining(","));
                Map<String,String> result = new HashMap<>(2);
                result.put("sfczyg","true");
                result.put("czyghsdyh",bdcdyhListStr);
                return result;
            }

        }
        return null;
    }

    @Override
    public boolean checkXzYzdf(BdcXzXmDTO bdcXzXmDTO){
        if(StringUtils.isNotBlank(bdcXzXmDTO.getYxmid()) &&CollectionUtils.isNotEmpty(bdcXzXmDTO.getYxmidList())){
            List<String> xmidList =bdcXmFeignService.listYbzXmByXmid(bdcXzXmDTO.getYxmid(),CommonConstantUtils.QSZT_VALID);
            if(CollectionUtils.isNotEmpty(xmidList) &&xmidList.size() >1){
                //过滤
                List<String> filterXmid =new ArrayList<>();
                for(String xmid:xmidList){
                    //先比较选择的数据
                    if(!bdcXzXmDTO.getYxmidList().contains(xmid)){
                        filterXmid.add(xmid);
                    }
                }
                if(CollectionUtils.isEmpty(filterXmid)){
                    //一证多房数据全部包含在选择的项目之内
                    return true;
                }
                List<BdcSlXmDO> bdcSlXmDOList =bdcSlXmService.listBdcSlXmByJbxxid(bdcXzXmDTO.getJbxxid(),"");
                if(CollectionUtils.isEmpty(bdcSlXmDOList)){
                    //存在数据不包含在选择的项目之内
                    return false;
                }
                List<String> slxmidList =bdcSlXmDOList.stream().map(BdcSlXmDO::getXmid).collect(Collectors.toList());
                List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList =bdcSlXmLsgxService.listSlXmLsgxPl(slxmidList,null);
                if(CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)){
                    List<String> yxmidList =bdcSlXmLsgxDOList.stream().map(BdcSlXmLsgxDO::getYxmid).filter(t->StringUtils.isNotBlank(t)).collect(Collectors.toList());
                    if(CollectionUtils.isNotEmpty(yxmidList)) {
                        //比较已添加的数据
                        for (String xmid : filterXmid) {
                            //先比较选择的数据
                            if (!yxmidList.contains(xmid)) {
                                //一证多房数据存在即不在已添加也不在当前已选择的数据之内
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }else{
                LOGGER.info("非一证多房数据,验证一证多房数据是否全部选择直接通过:{}", bdcXzXmDTO);
                return true;
            }
        } else {
            LOGGER.info("非选择产权证,验证一证多房数据是否全部选择直接通过:{}", bdcXzXmDTO);
            return true;
        }
        return false;
    }

    /**
     * @param jbxxid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 权籍数据xsd校验
     * @date : 2022/11/21 9:29
     */
    @Override
    public Object qjsjXsdJy(String jbxxid) {
        //获取添加购物车的数据
        if (StringUtils.isNotBlank(jbxxid)) {
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(jbxxid, "");
            List<QjsjjcDTO> qjsjjcDTOList = new ArrayList<>(CollectionUtils.size(bdcSlXmDOList));
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                //根据登记小类去重
                ConcurrentHashMap djxlMap = new ConcurrentHashMap();
                List<BdcSlXmDO> djxlSlxmList = bdcSlXmDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcSlXmDO::getDjxl))), ArrayList::new));
                if (CollectionUtils.isNotEmpty(djxlSlxmList)) {
                    for (BdcSlXmDO djxlxm : djxlSlxmList) {
                        if (StringUtils.isNotBlank(djxlxm.getDjxl())) {
                            BdcDjlxDjxlGxDO bdcDjlxDjxlGxQO = new BdcDjlxDjxlGxDO();
                            bdcDjlxDjxlGxQO.setDjxl(djxlxm.getDjxl());
                            List<BdcDjlxDjxlGxDO> bdcDjlxDjxlGxDOList = bdcCshXtPzFeignService.listBdcDjlxDjxlGx(bdcDjlxDjxlGxQO);
                            if (CollectionUtils.isNotEmpty(bdcDjlxDjxlGxDOList)) {
                                djxlMap.put(djxlxm.getDjxl(), bdcDjlxDjxlGxDOList.get(0).getDjlx());
                            }
                        }
                    }
                }
                if (CollectionUtils.size(bdcSlXmDOList) > 1) {
                    //多线程处理
                    List<CompletableFuture<List<QjsjjcDTO>>> xsdjyFuterList = new ArrayList<>(org.apache.commons.collections4.CollectionUtils.size(qjsjjcDTOList));
                    for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                        CompletableFuture<List<QjsjjcDTO>> xsdjyFuture = CompletableFuture.supplyAsync(() -> {
                            qjsjjcDTOList.add(setQjsj(bdcSlXmDO, djxlMap));
                            return qjsjjcDTOList;
                        }, threadPoolExecutor);
                        xsdjyFuterList.add(xsdjyFuture);
                    }
                    CompletableFuture<Void> allFutures = CompletableFuture.allOf(xsdjyFuterList.toArray(new CompletableFuture[org.apache.commons.collections4.CollectionUtils.size(xsdjyFuterList)]));
                    try {
                        allFutures.get();
                    } catch (InterruptedException | ExecutionException e) {
                        LOGGER.error("执行权籍数据xsd校验异常", e);
                    }
                } else {
                    for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                        qjsjjcDTOList.add(setQjsj(bdcSlXmDO, djxlMap));
                    }
                }
                List<String> s = (List<String>) nationalAccessFeignService.checkQjsj(qjsjjcDTOList);
                return StringUtils.join(s, CommonConstantUtils.ZF_YW_FH);
            }
        }
        return "";
    }

    @Override
    public String checkDyhgzWlzs(BdcXzWlzsDTO bdcXzWlzsDTO){
        if(StringUtils.isNotBlank(bdcXzWlzsDTO.getWlxmid()) &&CollectionUtils.isNotEmpty(bdcXzWlzsDTO.getWlxmidList())) {
            //查询添加的数据是否正确
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(bdcXzWlzsDTO.getJbxxid(), "");
            if(CollectionUtils.isEmpty(bdcSlXmDOList) ||bdcSlXmDOList.size() !=1){
                //添加的数量不为1
                return "单元号更正只能选择一条正确的单元号";
            }
            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList =bdcSlXmLsgxService.listBdcSlXmLsgxByXmid(bdcSlXmDOList.get(0).getXmid());
            if(CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)){
                //选择的不是单元号
                return "请选择单元号进行单元号更正,不能选择证书或者查封数据";
            }

            //验证外联的数据是否正确
            List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXmByXmids(bdcXzWlzsDTO.getWlxmidList());
            if(CollectionUtils.isNotEmpty(bdcXmDOList)) {
                List<String> bdcdyhList = bdcXmDOList.stream().map(BdcXmDO::getBdcdyh).distinct().collect(Collectors.toList());
                if(bdcdyhList.size() !=1 ||StringUtils.isBlank(bdcdyhList.get(0))){
                    //单元号不一致
                    return "外联的证书单元号不一致,请检查数据";
                }
                List<BdcXmDO> cqXmList = bdcXmDOList.stream().filter(bdcXmDO ->  !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDO.getQllx()) &&CommonConstantUtils.QSZT_VALID.equals(bdcXmDO.getQszt())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(cqXmList)){
                    if(cqXmList.size() >2){
                        //多个现势产权
                        return "外联的证书存在多个现势产权,请先处理数据再进行单元号更正";
                    }else if(cqXmList.size() ==2 &&cqXmList.get(0).getQllx() !=null &&cqXmList.get(0).getQllx().equals(cqXmList.get(1).getQllx())){
                        return "外联的证书存在多个现势产权,请先处理数据再进行单元号更正";
                    }
                }
                BdcXmQO bdcXmQO =new BdcXmQO();
                bdcXmQO.setBdcdyh(bdcdyhList.get(0));
                List<BdcXmDO> allXmList =bdcXmFeignService.listBdcXm(bdcXmQO);
                //获取现势数据
                List<BdcXmDO> xsXmList = allXmList.stream().filter(bdcXmDO ->  CommonConstantUtils.QSZT_VALID.equals(bdcXmDO.getQszt())).collect(Collectors.toList());
                if(CollectionUtils.isEmpty(xsXmList) ||xsXmList.size() !=bdcXmDOList.size()){
                    //选择的数据和关联的数据量不匹配
                    return "需要更正的数据没有全部选择或者选择不正确,请检查外联的数据";
                }
                List<String> xmidList = bdcXmDOList.stream().map(BdcXmDO::getXmid).distinct().collect(Collectors.toList());
                for(BdcXmDO bdcXmDO:xsXmList){
                    if(!xmidList.contains(bdcXmDO.getXmid())){
                        //数据不匹配
                        return "需要更正的数据没有全部选择或者选择不正确,请检查外联的数据";
                    }
                }
            }
        }
        return null;
    }

    private QjsjjcDTO setQjsj(BdcSlXmDO bdcSlXmDO, ConcurrentHashMap djxlMap) {
        QjsjjcDTO qjsjjcDTO = new QjsjjcDTO();
        qjsjjcDTO.setBdcdyh(bdcSlXmDO.getBdcdyh());
        qjsjjcDTO.setBdclx(bdcSlXmDO.getBdclx());
        qjsjjcDTO.setQllx(bdcSlXmDO.getQllx());
        qjsjjcDTO.setXmid(bdcSlXmDO.getXmid());
        qjsjjcDTO.setZl(bdcSlXmDO.getZl());
        qjsjjcDTO.setYcqzh(bdcSlXmDO.getYbdcqz());
        qjsjjcDTO.setQjgldm(bdcSlXmDO.getQjgldm());
        qjsjjcDTO.setDzwyt(bdcSlXmDO.getYt());
        qjsjjcDTO.setBdcdyfwlx(bdcSlXmDO.getBdcdyfwlx());
        qjsjjcDTO.setDjlx(MapUtils.getInteger(djxlMap, bdcSlXmDO.getDjxl()));
        qjsjjcDTO.setDjxl(bdcSlXmDO.getDjxl());
        //登记机构
        qjsjjcDTO.setDjjg("不动产登记中心");
        LOGGER.info("开始查询bdcdyfwlx{}", bdcSlXmDO.getBdcdyh());
        BdcdyResponseDTO bdcdyResponseDTO = bdcdyFeignService.queryBdcdy(bdcSlXmDO.getBdcdyh(), "", bdcSlXmDO.getQjgldm());
        if (Objects.nonNull(bdcdyResponseDTO) && StringUtils.isNotBlank(bdcdyResponseDTO.getBdcdyfwlx())) {
            qjsjjcDTO.setBdcdyfwlx(Integer.parseInt(bdcdyResponseDTO.getBdcdyfwlx()));
        }
        return qjsjjcDTO;
    }


}
