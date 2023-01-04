package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.gtc.clients.AuthorityManagerClient;
import cn.gtmap.gtc.clients.UserManagerClient;
import cn.gtmap.gtc.sso.domain.dto.OperationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.dto.BdcDyawMjDTO;
import cn.gtmap.realestate.common.core.dto.BdcXmXmfbDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCfDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlWjscDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.swxx.QuerySwxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcHbBdcdyDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcLsgxXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcShxxDTO;
import cn.gtmap.realestate.common.core.enums.BdcDyabgTypeEnum;
import cn.gtmap.realestate.common.core.enums.BdcSfztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlBdcdyhQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlHsxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSwxxQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.init.*;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCfxxQO;
import cn.gtmap.realestate.common.core.qo.register.BdcBdcdyQO;
import cn.gtmap.realestate.common.core.qo.register.BdcHbBdcdyQO;
import cn.gtmap.realestate.common.core.qo.register.BdcLsgxQO;
import cn.gtmap.realestate.common.core.qo.register.BdcShxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlHsxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSwFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.AcceptBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwLjzFeginService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcCfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcRedisFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZszmCxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcShxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcBdcdyVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDyawqdVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcJfVO;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.register.ui.config.PropsConfig;
import cn.gtmap.realestate.register.ui.core.enums.DyawMjJsfsEnum;
import cn.gtmap.realestate.register.ui.core.vo.BdcXmVO;
import cn.gtmap.realestate.register.ui.util.Constants;
import cn.gtmap.realestate.register.ui.util.ExportUtils;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jxl.write.WriteException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static cn.gtmap.realestate.common.util.CommonConstantUtils.BDCLX_TZM_W;
import static cn.gtmap.realestate.register.ui.util.Constants.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/2/21
 * @description 不动产单元相关处理Controller
 */
@RestController
@RequestMapping("/rest/v1.0/bdcdy")
public class BdcBdcdyController extends BaseController {


    /**
     * 抵押物面积计算方式
     */
    @Value("${config.jsfs.dyawmj:2}")
    private String dyawmjJsfs;

    /**
     * 如果房屋+土地一起抵押，抵押土地面积取值为：不动产类型是土地的抵押土地面积之和；如果是房屋，抵押土地面积取值就是0
     * 这里定义特殊处理流程定义ID
     */
    @Value("${dyawqd.tddyamj.tscllc:}")
    private String tddyamjTscllc;

    /**
     * 版本信息
     */
    @Value("${html.version:}")
    private String version;

    @Autowired
    private BdcBdcdyFeignService bdcBdcdyFeignService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    private BdcLsgxFeignService bdcLsgxFeignService;

    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;

    @Autowired
    private BdcShxxFeignService bdcShxxFeignService;

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private BdcSwFeignService bdcSwFeignService;

    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;

    @Autowired
    private BdcZsFeignService bdcZsFeignService;

    @Autowired
    private PropsConfig propsConfig;

    @Autowired
    private BdcdyFeignService bdcdyFeignService;

    @Autowired
    private BdcSlHsxxFeignService bdcSlHsxxFeignService;

    @Autowired
    private BdcZszmCxFeignService bdcZszmCxFeignService;

    @Autowired
    private AcceptBdcdyFeignService acceptBdcdyFeignService;

    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    private BdcRedisFeignService bdcRedisFeignService;

    @Autowired
    private AuthorityManagerClient authorityManagerClient;

    @Autowired
    private UserManagerClient userManagerClient;

    @Autowired
    private BdcCfxxFeignService bdcCfxxFeignService;

    @Autowired
    FwLjzFeginService fwLjzFeginService;

    @Autowired
    private BdcLzrFeignService bdcLzrFeignService;

    @Autowired
    BdcdyZtFeignService bdcdyZtFeignService;

    /**
     * 批量流程
     */
    String LCLX_PL = "pllc";
    /**
     * 组合流程
     */
    String LCLX_ZH = "zhlc";
    /**
     * 普通流程
     */
    String LCLX_PT = "ptlc";
    /**
     * 批量组合流程
     */
    String LCLX_PLZH = "plzh";

    /**
     * 批量登记历史页面不动产单元显示顺序需要和受理一致
     */
    @Value("${bdcdyxssx.pldjls:false}")
    private Boolean bdcdyxssx;


    /**
     * @param gzlslid 工作流实例ID
     * @return List<BdcXmDO> 项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询项目信息
     */
    @GetMapping("/xmxx/{gzlslid}")
    public List<BdcXmDO> getBdcXmList(@PathVariable(name = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("判断验证是否完税，缺失gzlslid的值！");
        }

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        return bdcXmFeignService.listBdcXm(bdcXmQO);
    }

    /*
     * 查询清册信息
     * */
    @GetMapping("/xmxx/qsqc")
    public Object getBdcXm(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                if (StringUtils.isNotBlank(bdcXmDO.getBdcdyh()) && StringUtils.length(bdcXmDO.getBdcdyh()) > 19) {
                    List<FwLjzDO> fwLjzDOList = fwLjzFeginService.listLjzByDjhAndZrzh(bdcXmDO.getBdcdyh().substring(0, 19), "", "");
                    if (CollectionUtils.isNotEmpty(fwLjzDOList)) {
                        bdcXmDO.setZl(fwLjzDOList.get(0).getZldz());
                    }
                }
                return bdcXmDO;
            }
        }
        return new BdcXmDO();
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return Boolean 是否验证完税
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 判断当前流程是否验证完税（该方法用于判断当前流程是否需要登簿前的完税验证。按照现场的要求，只考虑单个流程的，
     * 不考虑批量和组合情况。）
     */
    @GetMapping("/sfyzws/{gzlslid}")
    public Boolean checkYzws(@PathVariable(name = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("判断验证是否完税，缺失gzlslid的值！");
        }
        Boolean yzws = false;

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("工作流实例ID：" + gzlslid + "未查询到项目信息！");
        }
        // 只要有一个项目的登记类型，登记小类和登记原因在配置项，并且未完税，yzws=true
        for(BdcXmDO bdcXmDO : bdcXmDOList){
            if (null == bdcXmDO.getDjlx() || null == bdcXmDO.getDjxl()) {
                throw new MissingArgumentException("判断验证是否完税，项目缺失djlx或djxl的值！");
            }
            String[] djyyArr = propsConfig.getWsyzDjyy(bdcXmDO.getDjlx(), bdcXmDO.getDjxl());
            LOGGER.warn("判断是否验证完税，当前项目的登记类型为{},登记小类为{}", bdcXmDO.getDjlx(), bdcXmDO.getDjxl());
            if (null == djyyArr) {
                // 没有相关配置信息，不进行验证
                yzws = false;
                continue;
            }
            // 登记小类下未配置登记原因，则不管登记原因是什么，都要验证
            // 登记小类下配置了登记原因，则只对和配置一样登记原因的项目进行验证
            if (djyyArr.length == 0 || ArrayUtils.contains(djyyArr, bdcXmDO.getDjyy())) {
                yzws = true;
            }
            // 转移相关类业务，若未完税则登簿验证房源编码功能需求过滤掉税费托管的业务
            if (CommonConstantUtils.DJLX_ZYDJ_DM.equals(null == bdcXmDO.getDjlx() ? "" : bdcXmDO.getDjlx())){
                yzws = false;
                break;
            }
            // 验证当前项目是否已完税
            BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
            bdcSlHsxxQO.setXmid(bdcXmDO.getXmid());
            List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxFeignService.listBdcSlHsxx(bdcSlHsxxQO);
            if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                for (BdcSlHsxxDO bdcSlHsxxDO : bdcSlHsxxDOList) {
                    if (CommonConstantUtils.WSZT_YWS.equals(null == bdcSlHsxxDO.getWszt() ? "" : bdcSlHsxxDO.getWszt().toString())) {
                        yzws = false;
                        break;
                    }
                }
            }
            if (yzws){
                return yzws;
            }
        }
        return yzws;
    }

    /**
     * @param bdcLsgxQO 历史关系QO
     * @return {BdcQl} 权利信息实体
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询项目关联的权利信息
     */
    @GetMapping("/lsgx")
    public Object queryBdcQl(@LayuiPageable Pageable pageable, BdcLsgxQO bdcLsgxQO) {
        Page<BdcLsgxXmDTO> bdcXmDOPage = bdcBdcdyFeignService.listBdcdyLsgx(pageable, JSON.toJSONString(bdcLsgxQO));
        return super.addLayUiCode(bdcXmDOPage);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return Boolean 完税状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 验证是否完税
     */
    @GetMapping(value = "/sfws/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean bdcSlWszt(@PathVariable(name = "gzlslid") String gzlslid) {
        return bdcSwFeignService.checkSfwsByGzlslid(gzlslid);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return Boolean 建行订单查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 建行订单查询，并且缴库入库
     */
    @GetMapping(value = "/sfws/jhddcx/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public Object jhddcx(@PathVariable(name = "gzlslid") String gzlslid) {
        return bdcSwFeignService.yhddcxAndJkrk(gzlslid);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return Object 完税状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询税务信息
     */
    @GetMapping(value = "/sfws/swCxxx")
    @ResponseStatus(HttpStatus.OK)
    public Object checkSfwsSwcx(@RequestParam(name = "gzlslid") String gzlslid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("当前流程未查询到项目信息！");
        }
        QuerySwxxResponseDTO responseDTO = new QuerySwxxResponseDTO();
        BdcSwxxQO bdcSwxxQO = new BdcSwxxQO();
        bdcSwxxQO.setGxlx(CommonConstantUtils.SW_GXLX_UPDATE);
        BdcXmDO bdcXmDO = bdcXmDOList.get(0);
        if (StringUtils.isNotBlank(bdcXmDO.getSpxtywh())) {
            bdcSwxxQO.setSlbh(bdcXmDO.getSlbh());
            if (bdcXmDOList.size() == 1) {
                bdcSwxxQO.setXmid(bdcXmDO.getXmid());
                responseDTO = bdcSwFeignService.getSwxx(bdcSwxxQO);
            } else {
                Object response = bdcSwFeignService.getPlSwxx(bdcSwxxQO);
                responseDTO = JSONObject.parseObject(JSONObject.toJSONString(response), QuerySwxxResponseDTO.class);
            }
        }
        if (responseDTO != null) {
            return responseDTO.getWszt();
        }
        return null;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return Boolean 完税状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 验证商品房完税状态
     */
    @GetMapping(value = "/sfws/spfwszt")
    @ResponseStatus(HttpStatus.OK)
    public Object checkSfwsSpfwszt(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "fybh", required = false) String fybh) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失工作流实例ID！");
        }

        if (StringUtils.isBlank(fybh)) {
            LOGGER.warn("gzlslid{},未输入房源编号！", gzlslid);
            return null;
        }

        // 保存税务房源编号
        BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();

        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setSwfybh(fybh);
        bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(bdcXmDO));

        Map map = new HashMap(1);
        map.put(CommonConstantUtils.GZLSLID, gzlslid);
        bdcDjxxUpdateQO.setWhereMap(map);

        bdcXmFeignService.updateBatchBdcXm(bdcDjxxUpdateQO);

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("当前流程未查询到项目信息！");
        }

        QuerySwxxResponseDTO responseDTO = bdcSwFeignService.getSpfXmWszt(bdcXmDOList.get(0), CommonConstantUtils.SW_GXLX_INSERT_UPDATE);
        // 只有在完税状态下,保存完税信息，并返回完税信息
        if (responseDTO != null) {
            return responseDTO.getWszt();
        }
        return null;
    }

    @GetMapping(value = "/jfxx/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean bdcSljfzt(@PathVariable(name = "gzlslid") String gzlslid) {
        // 是否缴费
        Boolean sfjf = true;
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                if ((null == bdcSlSfxxDO.getSfzt() || BdcSfztEnum.WJF.key().equals(bdcSlSfxxDO.getSfzt())
                        || BdcSfztEnum.BFJF.key().equals(bdcSlSfxxDO.getSfzt()))
                        && (bdcSlSfxxDO.getHj() != null && bdcSlSfxxDO.getHj() != 0)) {
                    sfjf = false;
                    break;
                }
            }
        }

        return sfjf;
    }

    /**
     * @param bdcXmDO  项目对象
     * @param pageable 分页对象
     * @return Object 需要展示的项目列表（当前流程的项目）
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询项目信息
     */
    @GetMapping(value = "/xmlist/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public Object bdcXmList(@LayuiPageable Pageable pageable, BdcXmDO bdcXmDO) {
        Sort sort = "UNSORTED".equals(String.valueOf(pageable.getSort())) ? null : pageable.getSort();
        Page<BdcXmDO> bdcXmDOPage = bdcBdcdyFeignService.listXmByPage(pageable.getPageNumber(), pageable.getPageSize(), sort, bdcXmDO);
        return addLayUiCode(bdcXmDOPage);
    }

    /**
     * @param bdcXmQO 项目查询QO
     * @return Object 分页查询信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前项目和原项目的单元信息，并去重
     */
    @GetMapping(value = "/xmOrYxm/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public Object getXmOrYxmDistinctByDyh(@LayuiPageable Pageable pageable, BdcXmQO bdcXmQO) {
        Sort sort = "UNSORTED".equals(String.valueOf(pageable.getSort())) ? null : pageable.getSort();
        Page<BdcXmXmfbDTO> bdcXmDOPage = bdcBdcdyFeignService.getXmOrYxmDistinctByDyhPage(pageable.getPageNumber(), pageable.getPageSize(), sort, bdcXmQO);
        return addLayUiCode(bdcXmDOPage);
    }

    /**
     * @param bdcBdcdyQO 项目对象
     * @param pageable   分页对象
     * @return Object 不动产单元列表
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元列表
     */
    @GetMapping(value = "/list/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public Object bdcdyList(@LayuiPageable Pageable pageable, BdcBdcdyQO bdcBdcdyQO) {
        Sort sort = "UNSORTED".equals(String.valueOf(pageable.getSort())) ? null : pageable.getSort();
        Page<BdcBdcdyVO> bdcdyVOPage = bdcBdcdyFeignService.listBdcdyByPage(pageable.getPageNumber(), pageable.getPageSize(), sort, bdcBdcdyQO);
        return addLayUiCode(bdcdyVOPage);
    }

    /*
     * 权属清册页面查询逻辑
     * */
    @GetMapping(value = "/list/qsqc/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public Object ListQsqcxx(@LayuiPageable Pageable pageable, BdcBdcdyQO bdcBdcdyQO) {
        bdcBdcdyQO.setSfxzql(false);
        Sort sort = "UNSORTED".equals(String.valueOf(pageable.getSort())) ? null : pageable.getSort();
        Page<BdcBdcdyVO> bdcdyVOPage = bdcBdcdyFeignService.listBdcdyByPage(pageable.getPageNumber(), pageable.getPageSize(), sort, bdcBdcdyQO);
        if (CollectionUtils.isNotEmpty(bdcdyVOPage.getContent())) {
            List<BdcBdcdyVO> bdcBdcdyVOList = new ArrayList<>(bdcdyVOPage.getContent());
            List<Map> tdytZdList = bdcZdFeignService.queryBdcZd("tdyt");
//            bdcBdcdyVOList.sort(Comparator.comparing(BdcBdcdyVO::getXmid));
            bdcBdcdyVOList = bdcBdcdyVOList.stream().filter(bdcBdcdyVO -> !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcBdcdyVO.getQllx())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(bdcBdcdyVOList)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                for (BdcBdcdyVO bdcBdcdyVO : bdcBdcdyVOList) {
                    String tdytMc = "";
                    String tdyt2Mc = "";
                    String tdyt3Mc = "";
                    String tdsyqssj = "";
                    String tdsyqssj2 = "";
                    String tdsyqssj3 = "";
                    String tdsyjssj = "";
                    String tdsyjssj2 = "";
                    String tdsyjssj3 = "";
                    if (StringUtils.isNotBlank(bdcBdcdyVO.getZdzhyt())) {
                        tdytMc = StringToolUtils.convertBeanPropertyValueOfZdByString(bdcBdcdyVO.getZdzhyt(), tdytZdList) + "<br>";
                    }
                    if (StringUtils.isNotBlank(bdcBdcdyVO.getZdzhyt2())) {
                        tdyt2Mc = StringToolUtils.convertBeanPropertyValueOfZdByString(bdcBdcdyVO.getZdzhyt2(), tdytZdList) + "<br>";
                    }
                    if (StringUtils.isNotBlank(bdcBdcdyVO.getZdzhyt3())) {
                        tdyt3Mc = StringToolUtils.convertBeanPropertyValueOfZdByString(bdcBdcdyVO.getZdzhyt3(), tdytZdList) + "<br>";
                    }
                    if (Objects.nonNull(bdcBdcdyVO.getTdsyqssj())) {
                        tdsyqssj = simpleDateFormat.format(bdcBdcdyVO.getTdsyqssj()) + " ";
                    }
                    if (Objects.nonNull(bdcBdcdyVO.getTdsyqssj2())) {
                        tdsyqssj2 = simpleDateFormat.format(bdcBdcdyVO.getTdsyqssj2()) + "<br>";
                    }
                    if (Objects.nonNull(bdcBdcdyVO.getTdsyqssj3())) {
                        tdsyqssj3 = simpleDateFormat.format(bdcBdcdyVO.getTdsyqssj3()) + " ";
                    }
                    if (Objects.nonNull(bdcBdcdyVO.getTdsyjssj())) {
                        tdsyjssj = simpleDateFormat.format(bdcBdcdyVO.getTdsyjssj()) + "<br>";
                    }
                    if (Objects.nonNull(bdcBdcdyVO.getTdsyjssj2())) {
                        tdsyjssj2 = simpleDateFormat.format(bdcBdcdyVO.getTdsyjssj2()) + " ";
                    }
                    if (Objects.nonNull(bdcBdcdyVO.getTdsyjssj3())) {
                        tdsyjssj3 = simpleDateFormat.format(bdcBdcdyVO.getTdsyjssj3());
                    }
//                    String ytsyqx = tdytMc + "<br>" + tdsyqssj + " " + tdsyjssj + "<br>" +
//                            tdyt2Mc + "<br>" + tdsyqssj2 + " " + tdsyjssj2 + "<br>" +
//                            tdyt3Mc + "<br>" + tdsyqssj3 + " " + tdsyjssj3;
                    String ytsyqx = tdytMc + tdsyqssj + tdsyjssj + tdyt2Mc + tdsyqssj2 + tdsyjssj2 + tdyt3Mc + tdsyqssj3 + tdsyjssj3;
                    bdcBdcdyVO.setYtSyqx(ytsyqx);
                }
            }
            return addLayUiCode(bdcdyVOPage);
        }
        return addLayUiCode(bdcdyVOPage);
    }


    /**
     * @param bdcXmDO  项目对象
     * @param pageable 分页对象
     * @return Object 不动产单元列表
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询抵押注销证明信息
     */
    @GetMapping(value = "/dyazx/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public Object dyaZxzmList(@LayuiPageable Pageable pageable, BdcXmDO bdcXmDO) {
        Sort sort = "UNSORTED".equals(String.valueOf(pageable.getSort())) ? null : pageable.getSort();
        Page<BdcXmDO> bdcXmDOPage = bdcBdcdyFeignService.listDyaZxByPage(pageable.getPageNumber(), pageable.getPageSize(), sort, bdcXmDO);
        return addLayUiCode(bdcXmDOPage);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 原抵押信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前注销流程的一条原抵押信息
     */
    @GetMapping(value = "/ydyaxx/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public BdcDyaqDO queryYdyaxx(@PathVariable(name = "gzlslid") String gzlslid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        // 获取注销流程的原抵押信息
        List<BdcDyaqDO> bdcDyaqDOList = bdcBdcdyFeignService.queryYdyaxx(bdcXmQO);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);

        BdcDyaqDO bdcDyaqDO = new BdcDyaqDO();
        if (CollectionUtils.isNotEmpty(bdcDyaqDOList)) {
            bdcDyaqDO = bdcDyaqDOList.get(0);
        }
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            // 获取当前注销流程的受理编号
            bdcDyaqDO.setSlbh(bdcXmDOList.get(0).getSlbh());
            // 获取当前注销流程的受理时间
            bdcDyaqDO.setZxdydjsj(bdcXmDOList.get(0).getSlsj());
        }

        return bdcDyaqDO;
    }

    /**
     * @param gzlslid   工作流实例ID
     * @param bdcDyaqDO 抵押信息
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新原抵押信息的抵押注销申请人
     */
    @PutMapping(value = "/ydyaxx/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public int saveYdyaxxZxsqr(@PathVariable(name = "gzlslid") String gzlslid, @RequestBody BdcDyaqDO bdcDyaqDO) {
        return bdcBdcdyFeignService.saveYdyaxxZxsqrPl(gzlslid, bdcDyaqDO);
    }


    /**
     * @param bdcXmDO  项目对象
     * @param pageable 分页对象
     * @return Object 抵押物清单
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询抵押物清单
     */
    @GetMapping(value = "/dyawqd/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public Object dyawqd(@LayuiPageable Pageable pageable, BdcXmDO bdcXmDO, @RequestParam(value = "dyabg", required = false) Integer dyabg) {
        // 通过xmid或者gzlslid确认当前流程或项目是否生成权利（生成为true）
        boolean sfscql = checkSfscql(bdcXmDO);

        // 获取项目的权利类型
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(bdcXmDO.getXmid());
        bdcXmQO.setGzlslid(bdcXmDO.getGzlslid());
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            bdcXmDO.setQllx(bdcXmDOList.get(0).getQllx());
            bdcXmDO.setBdcdyh(bdcXmDOList.get(0).getBdcdyh());
        }

        Page<BdcDyawqdVO> dyawqdVOPagePage;
        Sort sort = "UNSORTED".equals(String.valueOf(pageable.getSort())) ? null : pageable.getSort();
        if (sfscql) {
            dyawqdVOPagePage = bdcBdcdyFeignService.listDyawqdByPage(pageable.getPageNumber(), pageable.getPageSize(), sort, bdcXmDO);
        } else {
            dyawqdVOPagePage = bdcBdcdyFeignService.listYxmdyawqdByPage(pageable.getPageNumber(), pageable.getPageSize(), sort, bdcXmDO, dyabg);
        }
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        for (int i = 0; i < dyawqdVOPagePage.getContent().size(); i++) {
            dyawqdVOPagePage.getContent().get(i).setDzwytMc(StringToolUtils.convertBeanPropertyValueOfZd(dyawqdVOPagePage.getContent().get(i).getDzwyt(), zdMap.get(FWYT)));
            dyawqdVOPagePage.getContent().get(i).setZdzhytMc(StringToolUtils.convertBeanPropertyValueOfZd(dyawqdVOPagePage.getContent().get(i).getZdzhyt(), zdMap.get(TDYT)));
            dyawqdVOPagePage.getContent().get(i).setQlxzMc(StringToolUtils.convertBeanPropertyValueOfZd(dyawqdVOPagePage.getContent().get(i).getQlxz(), zdMap.get(QLXZ)));
        }
        return addLayUiCode(dyawqdVOPagePage);
    }

    /**
     * @param bdcXmDO 项目信息
     * @return true 生成 ，false 不生成
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据xmid或者gzlslid判断项目是否生成了权利信息
     */
    @GetMapping(value = "/sfscql")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkSfscql(BdcXmDO bdcXmDO) {
        if (StringUtils.isBlank(bdcXmDO.getXmid()) && StringUtils.isBlank(bdcXmDO.getGzlslid())) {
            throw new MissingArgumentException("缺失xmid或者gzlslid参数！");
        }

        if (StringUtils.isNotBlank(bdcXmDO.getXmid())) {
            BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(bdcXmDO.getXmid());
            if (bdcCshFwkgSlDO != null) {
                //生成权利
                if (CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSlDO.getSfscql())) {
                    return true;
                }
            } else {
                //老数据没有这个表的直接查询权利
                BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
                if (bdcQl != null) {
                    return true;
                }
            }
        } else if (StringUtils.isNotBlank(bdcXmDO.getGzlslid())) {
            //获取当前流程生成的权利的权利类型
            List<String> qllxList = bdcQllxFeignService.listQllxByProcessInsId(bdcXmDO.getGzlslid());
            // 流程生成权利
            if (CollectionUtils.isNotEmpty(qllxList)) {
                return true;
            }
        }
        return false;
    }

    @GetMapping(value = "/dyawqd/xmxx/{xmid}")
    @ResponseStatus(HttpStatus.OK)
    public Object dyawqd(@LayuiPageable Pageable pageable, @PathVariable("xmid") String xmid, @RequestParam(value = "dyabg", required = false) Integer dyabg) {
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setXmid(xmid);
        return this.dyawqd(pageable, bdcXmDO, dyabg);
    }

    @GetMapping(value = "/dyawqd/{gzlslid}/all")
    @ResponseStatus(HttpStatus.OK)
    public Object queryDyawqd(BdcXmDO bdcXmDO) {
        // 通过xmid或者gzlslid确认当前流程或项目是否生成权利（生成为true）
        boolean sfscql = checkSfscql(bdcXmDO);

        // 获取项目的权利类型
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(bdcXmDO.getXmid());
        bdcXmQO.setGzlslid(bdcXmDO.getGzlslid());
        bdcXmDO.setQllx(bdcXmFeignService.listBdcXm(bdcXmQO).get(0).getQllx());

        List<BdcDyawqdVO> bdcDyawqdVOList;
        if (sfscql) {
            bdcDyawqdVOList = bdcBdcdyFeignService.queryDyawqd(bdcXmDO);
        } else {
            // 抵押注销/预抵押注销的项目
            bdcDyawqdVOList = bdcBdcdyFeignService.queryYxmDyawqd(bdcXmDO);
        }
        /**
         * 尽量自定义实体类传值，这里使用map作为返回参数，只是觉得再定义一个实体没必要。这是一个错误的示范
         */
        Map dyawqd = new HashMap();
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        BdcDyawMjDTO bdcDyawMjDTO = new BdcDyawMjDTO(0.0, 0.0);
        Set<String> djhSet = new HashSet();
        // 暂时就当我们数据量少，可以一次性查询出来，循环计算
        for (int i = 0; i < bdcDyawqdVOList.size(); i++) {
            bdcDyawqdVOList.get(i).setDzwytMc(StringToolUtils.convertBeanPropertyValueOfZd(bdcDyawqdVOList.get(i).getDzwyt(), zdMap.get(FWYT)));
            bdcDyawqdVOList.get(i).setZdzhytMc(StringToolUtils.convertBeanPropertyValueOfZd(bdcDyawqdVOList.get(i).getZdzhyt(), zdMap.get(TDYT)));
            bdcDyawqdVOList.get(i).setQlxzMc(StringToolUtils.convertBeanPropertyValueOfZd(bdcDyawqdVOList.get(i).getQlxz(), zdMap.get(QLXZ)));

            BdcDyawqdVO bdcDyawqdVO = bdcDyawqdVOList.get(i);
            // 计算抵押物面积
            BdcDyawMjDTO result = calculateDyawmj(bdcDyawqdVO, djhSet);
            // 累加
            bdcDyawMjDTO.setTddymj(bdcDyawMjDTO.getTddymj() + result.getTddymj());
            bdcDyawMjDTO.setFwdymj(bdcDyawMjDTO.getFwdymj() + result.getFwdymj());
        }

        // 抵押土地面积，如果是产权，取所有不动产单元 产权的fttdmj+dytdmj之和
        if (DyawMjJsfsEnum.TYPE2.getDm().equals(dyawmjJsfs)) {
            if (CommonConstantUtils.BDCLX_TZM_F.equals(BdcdyhToolUtils.getDzwTzm(bdcDyawqdVOList.get(0).getBdcdyh()))) {
                bdcDyawMjDTO.setTddymj(this.getDyaTdmj(bdcXmQO));
            }
        }

        // 抵押土地面积特殊处理
        this.dyatdmj(bdcDyawqdVOList, bdcDyawMjDTO);

        // 抵押物总面积
        bdcDyawMjDTO.setDyawzmj(bdcDyawMjDTO.getFwdymj() + bdcDyawMjDTO.getDyawzmj());

        dyawqd.put("bdcDyawqdVOList", bdcDyawqdVOList);
        dyawqd.put("bdcDyawMjDTO", bdcDyawMjDTO);
        dyawqd.put("qllx", bdcXmDO.getQllx());
        return dyawqd;
    }

    /**
     * 抵押土地面积特殊处理：如果房屋+土地一起抵押，抵押土地面积取值为：不动产类型是土地的抵押土地面积之和；如果是房屋，抵押土地面积取值就是0
     * @param bdcDyawqdVOList 抵押信息
     * @param bdcDyawMjDTO 抵押面积实体
     */
    private void dyatdmj(List<BdcDyawqdVO> bdcDyawqdVOList, BdcDyawMjDTO bdcDyawMjDTO) {
        if (StringUtils.isNotBlank(tddyamjTscllc)) {
            List<String> lcList = Arrays.asList(tddyamjTscllc.split(CommonConstantUtils.ZF_YW_DH));

            double dyatdmj = 0;
            for (BdcDyawqdVO dyawqdVO : bdcDyawqdVOList) {
                if (BDCLX_TZM_W.equals(BdcdyhToolUtils.getDzwTzm(dyawqdVO.getBdcdyh())) && lcList.contains(dyawqdVO.getGzldyid())) {
                    if (null == dyawqdVO.getTddymj()) {
                        dyawqdVO.setTddymj(Double.valueOf("0"));
                    }
                    dyatdmj = NumberUtil.add(dyatdmj, dyawqdVO.getTddymj(), 2);
                }
            }

            bdcDyawMjDTO.setTddymj(dyatdmj);
        }
    }

    @GetMapping(value = "/dyawqd/xmxx/qllxandmj")
    @ResponseStatus(HttpStatus.OK)
    public Object queryDyawqdQllxandmj(@RequestParam("xmid") String xmid, @RequestParam(required = false) Integer dyabg) {
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setXmid(xmid);
        return this.queryDyawqdQllxandmj(bdcXmDO, dyabg);
    }

    @GetMapping(value = "/dyawqd/{gzlslid}/qllxandmj")
    @ResponseStatus(HttpStatus.OK)
    public Object queryDyawqdQllxandmj(BdcXmDO bdcXmDO, @RequestParam(required = false) Integer dyabg) {
        // 通过xmid或者gzlslid确认当前流程或项目是否生成权利（生成为true）
        boolean sfscql = checkSfscql(bdcXmDO);

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(bdcXmDO.getXmid());
        bdcXmQO.setGzlslid(bdcXmDO.getGzlslid());
        // 只获取预抵押/抵押项目的权利类型(针对批量组合流程)
        List<String> qllxs = new ArrayList();
        qllxs.add(CommonConstantUtils.QLLX_DYAQ_DM.toString());
        qllxs.add(CommonConstantUtils.QLLX_YG_DM.toString());
        bdcXmQO.setQllxs(qllxs);
        List<BdcXmDO> queryXmList =bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(queryXmList)) {
            bdcXmDO.setQllx(queryXmList.get(0).getQllx());
        }

        List<BdcDyawqdVO> bdcDyawqdVOList;
        if (sfscql) {
            bdcDyawqdVOList = bdcBdcdyFeignService.queryDyawqd(bdcXmDO);
        } else {
            // 不生成权利的情况，要区分抵押变更类型
            if (BdcDyabgTypeEnum.DYABG_TYPE1.getDm().equals(dyabg)) {
                bdcDyawqdVOList = bdcBdcdyFeignService.queryBgYxmDyawqd(bdcXmDO);
            } else {
                // 抵押注销/预抵押注销的项目
                bdcDyawqdVOList = bdcBdcdyFeignService.queryYxmDyawqd(bdcXmDO);
            }
        }
        /**
         * 尽量自定义实体类传值，这里使用map作为返回参数，只是觉得再定义一个实体没必要。这是一个错误的示范
         */
        Map dyawqd = new HashMap();
        Set<String> djhSet = new HashSet();
        BdcDyawMjDTO bdcDyawMjDTO = new BdcDyawMjDTO(0.0, 0.0);
        for (int i = 0; i < bdcDyawqdVOList.size(); i++) {
            // 计算抵押物面积
            BdcDyawMjDTO result = calculateDyawmj(bdcDyawqdVOList.get(i), djhSet);
            // 累加
            if(result.getTddymj() != null) {
                bdcDyawMjDTO.setTddymj(bdcDyawMjDTO.getTddymj() + result.getTddymj());
            }
            if(result.getFwdymj() != null) {
                bdcDyawMjDTO.setFwdymj(bdcDyawMjDTO.getFwdymj() + result.getFwdymj());
            }
        }

        // 抵押土地面积，如果是产权，取所有不动产单元 产权的fttdmj+dytdmj之和
        if (DyawMjJsfsEnum.TYPE2.getDm().equals(dyawmjJsfs)) {
            if (CommonConstantUtils.BDCLX_TZM_F.equals(BdcdyhToolUtils.getDzwTzm(bdcDyawqdVOList.get(0).getBdcdyh()))) {
                bdcDyawMjDTO.setTddymj(getDyaTdmj(bdcXmQO));
            }
        }

        // 抵押土地面积特殊处理
        this.dyatdmj(bdcDyawqdVOList, bdcDyawMjDTO);

        // 抵押物总面积
        bdcDyawMjDTO.setDyawzmj(bdcDyawMjDTO.getFwdymj() + bdcDyawMjDTO.getTddymj());
        List<Map> mjdwZdMap = bdcZdFeignService.queryBdcZd("mjdw");
        bdcDyawMjDTO.setMjdwmc(CollectionUtils.isNotEmpty(queryXmList) &&queryXmList.get(0).getMjdw() !=null ?StringToolUtils.convertBeanPropertyValueOfZd(queryXmList.get(0).getMjdw(),mjdwZdMap):"");

        dyawqd.put("allDataLength", bdcDyawqdVOList.size());
        dyawqd.put("bdcDyawMjDTO", bdcDyawMjDTO);
        dyawqd.put("qllx", bdcXmDO.getQllx());
        return dyawqd;
    }

    /**
     * @param bdcDyawqdVO  抵押权物信息
     * @param djhSet
     * @return 抵押物面积
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 计算房屋抵押面积，土地抵押面积
     */
    private BdcDyawMjDTO calculateDyawmj(BdcDyawqdVO bdcDyawqdVO, Set<String> djhSet) {
        BdcDyawMjDTO bdcDyawMjDTO = new BdcDyawMjDTO(0.0, 0.0);
        if (DyawMjJsfsEnum.TYPE1.getDm().equals(dyawmjJsfs)) {
            bdcDyawMjDTO.setFwdymj(bdcDyawqdVO.getFwdymj());
            bdcDyawMjDTO.setTddymj(bdcDyawqdVO.getTddymj());
        }
        if (DyawMjJsfsEnum.TYPE2.getDm().equals(dyawmjJsfs)) {
            if (CommonConstantUtils.BDCLX_TZM_F.equals(BdcdyhToolUtils.getDzwTzm(bdcDyawqdVO.getBdcdyh()))) {
                // 房屋
                // 合肥的是要取定着物面积为房屋面积，宗地宗海面积为土地面积
                bdcDyawMjDTO.setFwdymj(bdcDyawqdVO.getDzwmj());
            } else {
                bdcDyawMjDTO.setTddymj(bdcDyawqdVO.getZdzhmj());
            }
        }
        if (DyawMjJsfsEnum.TYPE3.getDm().equals(dyawmjJsfs)) {
            String djh = StringUtils.substring(bdcDyawqdVO.getBdcdyh(), 0, 18);
            if (!djhSet.contains(djh)) {
                bdcDyawMjDTO.setTddymj(bdcDyawqdVO.getZdzhmj());
                djhSet.add(djh);
            }
            bdcDyawMjDTO.setFwdymj(bdcDyawqdVO.getDzwmj());
        }
        if (DyawMjJsfsEnum.TYPE4.getDm().equals(dyawmjJsfs)) {
            if (CommonConstantUtils.BDCLX_TZM_F.equals(BdcdyhToolUtils.getDzwTzm(bdcDyawqdVO.getBdcdyh()))) {
                bdcDyawMjDTO.setFwdymj(bdcDyawqdVO.getDzwmj());
            } else {
                bdcDyawMjDTO.setTddymj(bdcDyawqdVO.getZdzhmj());
            }
        }

        return bdcDyawMjDTO;
    }

    /**
     * @param bdcXmQO 抵押项目
     * @return 抵押土地面积
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询抵押土地面积：如果是产权，取所有不动产单元 产权的 fttdmj + dytdmj 之和
     */
    private Double getDyaTdmj(BdcXmQO bdcXmQO) {
        return bdcBdcdyFeignService.getDyaTdmj(bdcXmQO);
    }

    @GetMapping(value = "/dyawqd/xmxx/all")
    @ResponseStatus(HttpStatus.OK)
    public Object queryDyawqd(@RequestParam("xmid") String xmid) {
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setXmid(xmid);
        return this.queryDyawqd(bdcXmDO);
    }

    /**
     * @param gzlslid 判断当前流程类型
     * @return Object 流程类型
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程的类型
     */
    @GetMapping(value = "/lclx/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public Object getLclx(@PathVariable(name = "gzlslid") String gzlslid) {
        int xmlx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        if (xmlx == CommonConstantUtils.LCLX_PL) {
            return LCLX_PL;
        } else if (xmlx == CommonConstantUtils.LCLX_ZH) {
            return LCLX_ZH;
        } else if (xmlx == CommonConstantUtils.LCLX_PLZH) {
            return LCLX_PLZH;
        } else {
            return LCLX_PT;
        }
    }

    /**
     * @return {BdcXmVO} 项目信息VO
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据项目ID获取项目基本信息
     */
    @GetMapping("/xmjbxx")
    public BdcXmVO getBdcXmjbxx(@RequestParam("xmid") String xmid) {
        BdcXmVO bdcXmVO = this.getBdcXmByXmid(xmid);
        if (null == bdcXmVO) {
            return null;
        }

        // 注销登记直接展示上一手权利信息
        if (CommonConstantUtils.DJLX_ZXDJ_DM == bdcXmVO.getDjlx().intValue()) {
            bdcXmVO.setYxmid(this.getYxmid(xmid));
        }

        if (98 == bdcXmVO.getQllx()) {
            // 对于查封项目判断是否为解封项目
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmVO.getXmid());
            if (null == bdcQl || StringUtils.isBlank(bdcQl.getQlid())) {
                bdcXmVO.setSfjf(1);
                // 获取原查封项目
                bdcXmVO.setYxmid(this.getYxmid(xmid));
            }
        }
        return bdcXmVO;
    }

    /**
     * @return {String} 原项目ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据项目ID获取上一手项目ID
     */
    private String getYxmid(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            return null;
        }
        BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
        bdcXmLsgxQO.setXmid(xmid);
        List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
        if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList) && null != bdcXmLsgxDOList.get(0)) {
            return bdcXmLsgxDOList.get(0).getYxmid();
        }
        return null;
    }

    /**
     * @return {BdcXmVO} 项目信息VO
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据项目ID获取项目信息
     */
    @GetMapping("/xmxx")
    public BdcXmVO getBdcXmxx(@RequestParam("xmid") String xmid) {
        BdcXmVO bdcXmVO = this.getBdcXmByXmid(xmid);
        if (null == bdcXmVO) {
            return null;
        }

        // 转换字典项
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        bdcXmVO.setQllxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmVO.getQllx(), zdMap.get(QLLX)));
        bdcXmVO.setDjlxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmVO.getDjlx(), zdMap.get(DJLX)));
        bdcXmVO.setGyfsmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmVO.getGyfs(), zdMap.get(GYFS)));
        if (StringUtils.isNotBlank(bdcXmVO.getQlxz())) {
            bdcXmVO.setQlxzmc(StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(bdcXmVO.getQlxz()), zdMap.get(QLXZ)));
        }

        return bdcXmVO;
    }

    /**
     * @return list
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据项目ID获取权利人信息
     */
    @GetMapping("/qlr")
    public Object getQlrxx(@RequestParam("xmid") String xmid) {
        if (StringUtils.isBlank(xmid)) {
            return null;
        }
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        return bdcQlrFeignService.listBdcQlr(bdcQlrQO);
    }

    /**
     * @return list
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 获取权利人信息
     */
    @GetMapping("/qlrxx")
    public Object getQlrxx(BdcQlrQO bdcQlrQO) {
        if (StringUtils.isBlank(bdcQlrQO.getXmid())) {
            return null;
        }
        return bdcQlrFeignService.listBdcQlr(bdcQlrQO);
    }

    /**
     * @return {BdcXmVO} 项目信息VO
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据项目ID获取项目信息
     */
    private BdcXmVO getBdcXmByXmid(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            return null;
        }

        // 查询项目信息
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList) || null == bdcXmDOList.get(0)) {
            return null;
        }
        BdcXmVO bdcXmVO = new BdcXmVO();
        BdcXmDO bdcXmDO = bdcXmDOList.get(0);
        BeansResolveUtils.clonePropsValueWithParentProps(bdcXmDO, bdcXmVO);
        return bdcXmVO;
    }

    /**
     * @return 判断是哪种档案
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping("/tellTdFromBdc")
    @ResponseStatus(HttpStatus.OK)
    public Object tellTdFromBdc(@RequestParam("xmid") String xmid) {
        Map config = bdcBdcdyFeignService.queryXmly(xmid);
        config.put("version", version);
        return config;
    }

    /**
     * @return 查询是否存在产权大证
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping("/cqdz")
    @ResponseStatus(HttpStatus.OK)
    public List<BdcLsgxXmDTO> cqdz(@RequestParam("xmid") String xmid) {
        return bdcBdcdyFeignService.cqdz(xmid);
    }

    /**
     * @param xmid    项目ID
     * @param gzlslid 工作流实例ID
     * @return 审核信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据项目ID或者工作流实例ID获取历史项目审核信息
     */
    @GetMapping("/lsxx/lcxx")
    public Object getBdcShxx(@RequestParam("xmid") String xmid, @RequestParam("gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid) || STR_NULL.equals(gzlslid) || STR_UNDEFINED.equals(gzlslid)) {
            gzlslid = this.getBdcXmGzlslidByXmid(xmid);
            if (StringUtils.isBlank(gzlslid)) {
                return super.addLayUiCode(new PageImpl(Collections.EMPTY_LIST, new PageRequest(0, 10), 0));
            }
        }

        // 查询审核信息
        BdcShxxQO bdcShxxQO = new BdcShxxQO();
        bdcShxxQO.setGzlslid(gzlslid);
        List<BdcShxxDO> bdcShxxDOList = bdcShxxFeignService.queryBdcShxx(bdcShxxQO);
        if (CollectionUtils.isEmpty(bdcShxxDOList)) {
            return super.addLayUiCode(new PageImpl(Collections.EMPTY_LIST, new PageRequest(0, 10), 0));
        }

        for (BdcShxxDO bdcShxxDO : bdcShxxDOList) {
            if (null != bdcShxxDO && StringUtils.isNotBlank(bdcShxxDO.getShryid())) {
                UserDto userDto = userManagerUtils.getUserByUserid(bdcShxxDO.getShryid());
                if (null != userDto && StringUtils.isNotBlank(userDto.getAlias())) {
                    // 优先显示中文名
                    bdcShxxDO.setShryxm(userDto.getAlias());
                }
            }
        }


        List<BdcShxxDTO> bdcShxxDTOList = new ArrayList<>();
        List<Integer> sxhList = new ArrayList<>();//存储退回意见的节点顺序号；（顺序号是唯一，且按实际操作顺序依次递增的）

        for (BdcShxxDO bdcShxxDO : bdcShxxDOList) {
            BdcShxxDTO bdcShxxDTO = new BdcShxxDTO();
            BeanUtils.copyProperties(bdcShxxDO,bdcShxxDTO);
            //real数据存储逻辑：a节点退回到b节点，a节点的czjg=2,退回意见保存在b节点的bz字段，
            if (Objects.nonNull(bdcShxxDTO.getCzjg()) && bdcShxxDTO.getCzjg() == 2) {
                //前台页面展示逻辑1：在a节点处，页面展示已退回;
                bdcShxxDTO.setSfth("已退回");
                sxhList.add(bdcShxxDTO.getSxh() + 1);
            }
            bdcShxxDTOList.add(bdcShxxDTO);
        }
        //前台页面展示逻辑2：在b节点展示退回意见；
        for (BdcShxxDTO bdcShxxDTO : bdcShxxDTOList) {
            if (sxhList.contains(bdcShxxDTO.getSxh())) {
                bdcShxxDTO.setThyj(bdcShxxDTO.getBz());
            }
        }

        // 封装分页信息展示
        Pageable page = new PageRequest(0, bdcShxxDOList.size());
        return super.addLayUiCode(new PageImpl(bdcShxxDTOList, page, bdcShxxDTOList.size()));
    }

    /**
     * @return {BdcXmVO} 项目信息VO
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据项目ID获取项目信息
     */
    private String getBdcXmGzlslidByXmid(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            return null;
        }

        // 查询项目信息
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList) || null == bdcXmDOList.get(0)) {
            return null;
        }
        return bdcXmDOList.get(0).getGzlslid();
    }

    @ResponseBody
    @RequestMapping("/exportBdcDyawqd")
    public String exportBdcDyawqd(HttpServletResponse response, BdcXmDO bdcXmDO, String exportCol) throws IOException, WriteException {
        if (StringUtils.isNoneBlank(exportCol)) {
            //处理表头数据
            exportCol = URLDecoder.decode(exportCol, "utf-8");
            LinkedHashMap exportColMap = JSONObject.parseObject(exportCol, LinkedHashMap.class);
            //创建导出文件名
            String fileName = "抵押物清单导出Excel表" + System.currentTimeMillis() + ".xls";

            // 通过xmid或者gzlslid确认当前流程或项目是否生成权利（生成为true）
            boolean sfscql = checkSfscql(bdcXmDO);

            List<BdcDyawqdVO> bdcDyawqdVOList;
            if (sfscql) {
                bdcDyawqdVOList = bdcBdcdyFeignService.queryDyawqd(bdcXmDO);
            } else {
                // 抵押注销/预抵押注销的项目
                bdcDyawqdVOList = bdcBdcdyFeignService.queryYxmDyawqd(bdcXmDO);
            }
            Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
            if (CollectionUtils.isNotEmpty(bdcDyawqdVOList)) {
                if (CommonConstantUtils.SYSTEM_VERSION_BB.equals(version)) {
                    // 蚌埠增加房屋性质信息
                    List<String> bdcdyhList = bdcDyawqdVOList.stream().map(BdcDyawqdVO::getBdcdyh).collect(Collectors.toList());
                    List<BdcDyawqdVO> fwxxList = this.listBdcDyawqdFwxx(bdcdyhList);
                    if (CollectionUtils.isNotEmpty(fwxxList)) {
                        for (BdcDyawqdVO dyawqdVO : bdcDyawqdVOList) {
                            for (BdcDyawqdVO fwxx : fwxxList) {
                                if (StringUtils.equals(dyawqdVO.getBdcdyh(), fwxx.getBdcdyh())) {
                                    dyawqdVO.setFwxzmc(fwxx.getFwxzmc());
                                }
                            }
                        }
                    }
                }

                for (BdcDyawqdVO bdcDyawqdVO : bdcDyawqdVOList) {
                    bdcDyawqdVO.setDzwytMc(StringToolUtils.convertBeanPropertyValueOfZd(bdcDyawqdVO.getDzwyt(), zdMap.get(FWYT)));
                    bdcDyawqdVO.setZdzhytMc(StringToolUtils.convertBeanPropertyValueOfZd(bdcDyawqdVO.getZdzhyt(), zdMap.get(TDYT)));
                    bdcDyawqdVO.setQlxzMc(StringToolUtils.convertBeanPropertyValueOfZd(bdcDyawqdVO.getQlxz(), zdMap.get(QLXZ)));

                    // 海门会根据宗地使用类型拼接 共有 字段
                    if (CommonConstantUtils.SYSTEM_VERSION_HM.equals(version)) {
                        if (CommonConstantUtils.ZDSYLX_GY.equals(bdcDyawqdVO.getZdsylx())) {
                            bdcDyawqdVO.setZdzhmjmc("共有" + bdcDyawqdVO.getZdzhmj());
                        } else {
                            bdcDyawqdVO.setZdzhmjmc(String.valueOf(bdcDyawqdVO.getZdzhmj()));
                        }
                    }
                }
                ExportUtils.exportExcel(fileName, exportColMap, JSON.parseArray(JSON.toJSONString(bdcDyawqdVOList), Map.class), response, zdMap);
            } else {
                return "无清单数据，导出失败";
            }
        }
        return "导出成功";
    }

    /**
     * @param gzlslid  gzlslid
     * @param pageable 分页对象
     * @return Object 解封清单
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 查询解封清单
     */
    @GetMapping(value = "/jfqd")
    @ResponseStatus(HttpStatus.OK)
    public Object jfqd(@LayuiPageable Pageable pageable, @RequestParam(name = "gzlslid") String gzlslid) {
        Page<BdcJfVO> bdcJfVOS = bdcBdcdyFeignService.listJfqdByPage(pageable, gzlslid);
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        for (int i = 0; i < bdcJfVOS.getContent().size(); i++) {
            bdcJfVOS.getContent().get(i).setCflxMc(StringToolUtils.convertBeanPropertyValueOfZd(bdcJfVOS.getContent().get(i).getCflx(), zdMap.get(CFLX)));
        }
        return addLayUiCode(bdcJfVOS);
    }

    /**
     * 通过工作流实例ID查询原项目证书信息（去重）
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [gzlslid] 工作流实例ID
     * @return: Object 不动产证书信息
     */
    @GetMapping(value = "/lsgx/ybdcqzh/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public Object queryYxmBdcqzh(@PathVariable(value = "gzlslid") String gzlslid, @RequestParam(value = "qllx", required = false) Integer qllx) {
        List<BdcZsDO> zsList;
        if (bdcdyxssx) {
            // 设置批量登记历史页面不动产单元显示顺序是否需要和受理一致
            zsList = this.bdcZsFeignService.queryYxmZsSortByXmid(gzlslid, qllx);
        } else {
            zsList = this.bdcZsFeignService.queryYxmBdcqzhByGzlslid(gzlslid, qllx);
        }

        // 查询当前流程下是否存在上一手不动产无证书号的情况，当没有证书号时，采用空来展示。
        List<BdcXmDO> xmList = this.bdcBdcdyFeignService.queryBdcXmByGzlslidAndZsid(gzlslid, "", qllx);
        if (CollectionUtils.isNotEmpty(xmList)) {
            BdcZsDO zsDO = new BdcZsDO();
            zsDO.setZsid("");
            zsDO.setBdcqzh("无房产证号");
            zsList.add(zsDO);
        }
        return zsList;
    }

    /**
     * 通过上一手的证书信息查询现不动产项目信息
     * <p>不动产项目表，不动产历史关系表，不动产项目证书关系表三表关联查询项目信息</p>
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [gzlslid, zsid] 工作流实例ID， 证书ID（上一手项目的证书ID）
     * @return: 不动产项目信息集合
     */
    @GetMapping(value = "/lsgx/bdcxm")
    @ResponseStatus(HttpStatus.OK)
    public Object queryBdcXmByGzlslidAndZsid(@RequestParam(value = "gzlslid") String gzlslid,
                                             @RequestParam(value = "zsid", required = false) String zsid,
                                             @RequestParam(value = "qllx", required = false) Integer qllx) {
        return this.bdcBdcdyFeignService.queryBdcXmByGzlslidAndZsid(gzlslid, zsid, qllx);
    }


    /**
     * @param bdcdyh 不动产单元号
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 根据不动产单元号获取权籍房屋信息
     */
    @ResponseBody
    @GetMapping("/fwbdcdy")
    public BdcdyResponseDTO queryFwBdcdyByBdcdyh(String bdcdyh,String qjgldm) {
        return bdcdyFeignService.queryBdcdy(bdcdyh, null,qjgldm);
    }

    /**
     * @param bdcHbBdcdyQO 合并不动产单元查询参数
     * @return 权利信息实体
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description （海门）查询待合并不动产单元信息
     */
    @GetMapping("/hb/bdcdy")
    public Object queryDhbBdcdyXx(@LayuiPageable Pageable pageable, BdcHbBdcdyQO bdcHbBdcdyQO) {
        Page<BdcHbBdcdyDTO> bdcHbBdcdyDTOPage = bdcBdcdyFeignService.queryDhbBdcdyXx(pageable, JSON.toJSONString(bdcHbBdcdyQO));
        return super.addLayUiCode(bdcHbBdcdyDTOPage);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param: [bdcGzYzQO] 规则验证查询参数
     * @return: {List} 验证结果
     * @description 合并不动产单元前规则验证
     */
    @PostMapping("/hb/bdcdy/gzyz")
    public List<Map<String, Object>> checkBdcdyhStatus(@RequestBody BdcGzYzQO bdcGzYzQO) {
        if (null == bdcGzYzQO || StringUtils.isBlank(bdcGzYzQO.getZhbs()) || CollectionUtils.isEmpty(bdcGzYzQO.getBdcGzYzsjDTOList())) {
            throw new AppException("验证异常：未指定组合规则标识或验证参数");
        }
        return this.bdcZszmCxFeignService.checkBdcdyhGz(bdcGzYzQO);
    }

    /**
     * @param bdcdyDTOList 待合并不动产单元信息
     * @param ppBdcdyh     匹配到的新记录待使用不动产单元号
     * @return {BdcHbBdcdyDTO} 新合并生成的不动产单元记录
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 执行不动产单元合并操作
     */
    @PostMapping("/hb/bdcdy/new/{ppBdcdyh}")
    public BdcHbBdcdyDTO hbBdcdy(@RequestBody List<BdcHbBdcdyDTO> bdcdyDTOList, @PathVariable(value = "ppBdcdyh") String ppBdcdyh) {
        if (StringUtils.isBlank(ppBdcdyh) || CollectionUtils.isEmpty(bdcdyDTOList)) {
            throw new AppException("验证异常：未指定需要进行合并的数据");
        }
        return bdcBdcdyFeignService.hbBdcdy(bdcdyDTOList, ppBdcdyh);
    }

    /**
     * @param bdcSlBdcdyhQO 查询参数
     * @return 土地及定着物类型不动产单元信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询土地及定着物类型不动产单元信息
     */
    @GetMapping("/ppbdcdy")
    public Object listPpBdcdyh(@LayuiPageable Pageable pageable, BdcSlBdcdyhQO bdcSlBdcdyhQO) {
        bdcSlBdcdyhQO.setQlr(StringUtils.deleteWhitespace(bdcSlBdcdyhQO.getQlr()));
        bdcSlBdcdyhQO.setBdcdyh(StringUtils.deleteWhitespace(bdcSlBdcdyhQO.getBdcdyh()));
        bdcSlBdcdyhQO.setZl(StringUtils.deleteWhitespace(bdcSlBdcdyhQO.getZl()));

        // 查询土地及定着物类型不动产单元信息

        Page<Map> bdcdyXxPage = acceptBdcdyFeignService.listTdAndDzwBdcdyByPage(pageable, "GB", "B", "F", "", JSON.toJSONString(bdcSlBdcdyhQO));
        if (null == bdcdyXxPage || CollectionUtils.isEmpty(bdcdyXxPage.getContent())) {
            return PageUtils.addLayUiCodeWithQjgldm(bdcdyXxPage,bdcSlBdcdyhQO.getQjgldm());
        }

        // 查询限制状态
        for (Map bdcdyXx : bdcdyXxPage.getContent()) {
            BdcdyhZtResponseDTO zt = bdcdyZtFeignService.commonQueryBdcdyhZtBybdcdyh(StringUtils.deleteWhitespace((String) bdcdyXx.get("bdcdyh")),bdcSlBdcdyhQO.getQjgldm());
            bdcdyXx.put("bdcdyZtDTO", zt);
        }

        return super.addLayUiCode(bdcdyXxPage);
    }

    /**
     * @param bdcdyhList 不动产单元号集合
     * @return {List} 房屋性质
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询抵押物清单列表单元的房屋信息
     */
    @PostMapping("/dyawqd/fwxx")
    public List<BdcDyawqdVO> listBdcDyawqdFwxx(@RequestBody List<String> bdcdyhList) {
        if (CollectionUtils.isEmpty(bdcdyhList)) {
            return Collections.emptyList();
        }
        return bdcBdcdyFeignService.listBdcDyawqdFwxx(bdcdyhList);
    }

    /**
     * @param gzlslid 当前项目id
     * @return 原不动产权证号
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询原不动产权证号（蚌埠注销抵押注销清单用到）
     */
    @GetMapping("/old/bdcqzh")
    public String getOldBdcqzh(@RequestParam String gzlslid) {
        LOGGER.info("查询原不动产权号入参：{}", gzlslid);
        if (StringUtils.isEmpty(gzlslid)) {
            return "";
        }
        // 获取当前流程关联的项目信息
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            LOGGER.info("查询到当前项目信息");
            //判断是否需要查询原不动产权证号 抵押权注销业务流程需要
            BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(bdcXmDOList.get(0).getXmid());
            if (bdcCshFwkgSlDO!=null && bdcCshFwkgSlDO.getSfscql() != 0){
                return "";
            }
            BdcYxmQO bdcYxmQO = new BdcYxmQO();
            bdcYxmQO.setGzlslid(gzlslid);
            bdcYxmQO.setXmidList(bdcXmDOList.stream().collect(ArrayList::new,
                    (list, item) -> list.add(item.getXmid()),
                    ArrayList::addAll));
            //和蚌埠现场确认是取上一手的项目不动产权证号
            List<BdcXmDO> oldBdcXmInfo = bdcXmFeignService.listYxmByYxmQO(bdcYxmQO);
            if (CollectionUtils.isNotEmpty(oldBdcXmInfo)) {
                LOGGER.info("查询到关联项目信息");
                return oldBdcXmInfo.get(0).getBdcqzh();
            }
        }
        LOGGER.info("查询原不动产权号失败");
        return "";
    }

    /**
     * 获取配置的落款单位配置
     * @return String 落款单位配置
     */
    @GetMapping("/pz/lkdw")
    public String getLkdw(){
       return this.bdcBdcdyFeignService.getLkdwConfig();
    }

    /**
     * 组织附件查看传递给CS客户端参数
     * @return 附件查看参数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @ResponseBody
    @GetMapping("/fjckcs")
    public Object fjckcs() {
        BdcSlWjscDTO bdcSlWjscDTO = new BdcSlWjscDTO();
        bdcSlWjscDTO.setToken(super.queryToken());
        bdcSlWjscDTO.setClientId("clientId");
        bdcSlWjscDTO.setsFrmOption("{\"CanRefresh\":1,\"CanCreateNewFolder\":0,\"CanDelete\":0,\"CanRename\":0,\"CanPrint\":1,\"CanDownload\":1,\"CanUpload\":0,\"CanTakePhoto\":0,\"CanScan\":0,\"CanEdit\":-1}");
        return bdcSlWjscDTO;
    }

    /**
     * 获取台账背景颜色配置
     * @return 配置信息
     */
    @GetMapping("/status/color")
    @ResponseBody
    public Object queryColorPz() {
        String xtPzColor = bdcRedisFeignService.getStringValue("xtPzColor");
        return JSONObject.parseObject(xtPzColor);
    }

    /**
     * @param
     * @return tzym 登记历史跳转前一页
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @description 查询登记按钮跳转页
     */
    @GetMapping("/lsgxtzym")
    public Object queryLsgxtzym(String tzym) {
        List<OperationDto> operationDtoList  = authorityManagerClient.findModuleAuthorityOperation(userManagerClient.getCurrentUser().getUsername(),tzym);
        String pageNext= Constants.BDCDYDJLSGXCQHTML;
        List<String> list = new ArrayList<>();
        //获取用户拥有的权限
        if (CollectionUtils.isNotEmpty(operationDtoList)) {
            for (int i = 0; i < operationDtoList.size(); i++) {
                OperationDto operationDto = operationDtoList.get(i);
                if (Constants.BDCDYDJLSGXCQHTML.equals(operationDto.getCode())) {
                   list.add(operationDto.getCode());
                }
                if (Constants.BDCDYDJLSGXXZQLHTML.equals(operationDto.getCode())) {
                    list.add(operationDto.getCode());
                }
            }
        }
        if (CollectionUtils.isNotEmpty(list)){
            pageNext = StringUtils.join(list,",");
        }
        return pageNext;
    }

    /**
     *
     * @param pageable
     * @param bdcCfxxQO
     * @return
     */
    @GetMapping("/cfdjls")
    public Object listCfdjls(@LayuiPageable Pageable pageable,  BdcCfxxQO bdcCfxxQO){
        if (StringUtils.isBlank(bdcCfxxQO.getBdcdyh())) {
            throw new AppException("查询查封信息缺失不动产单元号");
        }
        bdcCfxxQO.setBdcdyh(bdcCfxxQO.getBdcdyh());
        List<BdcCfDTO> bdcCfDTOList = bdcCfxxFeignService.listBdcCfxxByCfsx(bdcCfxxQO);
        return addLayUiCode(new PageImpl<>(bdcCfDTOList, pageable, CollectionUtils.size(bdcCfDTOList)));
    }

    /**
     *
     * @param gzlslid
     * @return
     * 检测是否领证人重复-证号是否生成
     */
    @GetMapping(value = "/sflzrcf")
    @ResponseStatus(HttpStatus.OK)
    public Object checkSflzrcf(@RequestParam(name = "gzlslid") String gzlslid) {
        //证号是否生成
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(gzlslid);
        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);
        if(CollectionUtils.isEmpty(bdcZsDOS)){
            throw new MissingArgumentException("当前流程未查询到证书信息！");
        }
        //检测是否都已经生成了证号
        if(bdcZsDOS.stream().anyMatch(bdcZsDO -> StringUtils.isBlank(bdcZsDO.getBdcqzh()))){
            return false;
        }

        //检测是否有重复的领证人
        List<BdcLzrDO> allBdcLzrDOS = bdcLzrFeignService.getBdcLzrDOByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(allBdcLzrDOS)) {
            Set<String>  lzrzjhSet = allBdcLzrDOS.stream().map(bdcLzrDO -> bdcLzrDO.getXmid() + bdcLzrDO.getLzrzjh()).collect(Collectors.toSet());
            if(lzrzjhSet.size() < allBdcLzrDOS.size()){
                return false;
            }
        }
        return true;
    }
}
