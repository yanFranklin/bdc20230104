package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.config.accept.BdcSlSfxmConfig;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhsymxDO;
import cn.gtmap.realestate.common.core.dto.BdcYzxxDTO;
import cn.gtmap.realestate.common.core.dto.ExcelExportDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdtResponseDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcZsQsztDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcLzxxDTO;
import cn.gtmap.realestate.common.core.enums.BdcSfztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.EntityNotFoundException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.ex.UserInformationAccessException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYzhQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYzhSyqkQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJyxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwHstFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.ZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.*;
import cn.gtmap.realestate.common.core.service.feign.config.BdcXtZsyzhFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.NationalAccessFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.register.*;
import cn.gtmap.realestate.common.core.service.rest.certificate.BdcZsPrintRestService;
import cn.gtmap.realestate.common.core.support.excel.ExcelController;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcSzxxVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcZsVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcZsqdVO;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.register.ui.core.vo.BdcZmdVO;
import cn.gtmap.realestate.register.ui.util.Constants;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/12/15
 * @description 证书服务页面
 */
@SuppressWarnings("AlibabaTransactionMustHaveRollback")
@RestController
@RequestMapping("/rest/v1.0")
public class BdcZsController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BdcZsController.class);

    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    BdcYzhFeignService bdcYzhFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcZsPirntFeignService bdcZsPirntFeignService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    BdcZsPrintRestService bdcZsPrintRestService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcXtZsyzhFeignService bdcXtZsyzhFeignService;
    @Autowired
    BdcEntityFeignService bdcEntityFeignService;
    @Autowired
    BdcZsInitFeignService bdcZsInitFeignService;
    @Autowired
    BdcFzjlFeignService bdcFzjlFeignService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    BdcDjbxxFeignService bdcDjbxxFeignService;
    @Autowired
    BdcSlJyxxFeignService bdcSlJyxxFeignService;
    @Autowired
    ZdFeignService zdFeignService;
    @Autowired
    RegisterWorkflowFeignService registerWorkflowFeignService;
    @Autowired
    BdcPrintFeignService bdcPrintFeignService;
    @Autowired
    BdcDbxxFeignService bdcDbxxFeignService;
    @Autowired
    NationalAccessFeignService nationalAccessFeignService;
    @Autowired
    FwHstFeignService fwHstFeignService;
    @Autowired
    ECertificateFeignService eCertificateFeignService;
    @Autowired
    BdcCshXtPzFeignService bdcCshXtPzFeignService;
    @Autowired
    private BdcYwsjHxFeignService bdcYwsjHxFeignService;
    @Autowired
    private BdcLzrFeignService bdcLzrFeignService;
    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;

    @Autowired
    BdcXtJgFeignService bdcXtJgFeignService;

    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;


    /*
     * 不需要收费项目的数据 key为fwxz，value为工作流定义id集合
     * */
    @Autowired
    BdcSlSfxmConfig bdcSlSfxmConfig;

    @Autowired
    private ExcelController excelController;


    /**
     * 排除需要验证缴费状态的工作流定义ID集合
     */
    @Value("${exclude.check.jfzt:}")
    private String excludeProcessDefKeys;

    /**
     * 打印证书证明，不验证发票号的工作流定义ID
     */
    @Value("#{'${dyzszm.nofph.gzldyid:}'.split(',')}")
    private List<String> dyzszmNoFphGzldyids;

    @Value("${html.version:standard}")
    private String htmlVersion;

    // 特殊共有方式处理逻辑
    @Value("${bengbu.tsgyfs:0}")
    private String tsgyfs;

    @Value("${nantong.ckOrcwZmdDzwyt:}")
    private String ckOrcwZmdDzwyt;

    /**
     * 证书打印户室图二维码是否浏览器下载
     */
    @Value("${hstewm.sfllqxz:true}")
    private Boolean sfllqxz;

    /**
     * 是否为淮安证书打印户室图 默认false
     */
    @Value("${hstewm.hahst:false}")
    private Boolean sfhahst;
    /**
     * 不区分用途的流程
     * （生成证明单时。会根据用途来判断生成首次证明单还是车库证明单，
     * 该配置用来过滤配置的流程不进行用途判断，只生成首次证明单）
     */
    @Value("${sczmd.ckcw.gzldyid:}")
    private String ckcwzmd;

    // 是否预览电子证照
    @Value("${sfyldzzz:false}")
    private String sfyldzzz;
    @Value("${fwszc:}")
    private String fwszc;
    @Value("#{'${zszmprint.sfsf.gzldyid:}'.split(',')}")
    private List<String> tssfyzGzldyidList;

    /**
     * 首次证明单证明内容
     */
    @Value("${sczmd.zmnr:}")
    private String sczmdzmnr;

    /**
     * 证书证明是否校验领证方式
     */
    @Value("${zszm.sfjylzfs: false}")
    private Boolean sfjylzfs;

    /*
     * 1. 默认证书id
     * 2. 不动产单元号
     * 3. 权利人名称+证号+坐落
     * */
    @Value("${zszm.ewmnr.version:1}")
    private String zszmEwmVersion;

    @Value("${xnyzh.hz:HF}")
    private String xnyzhHz ;

    /**
     * @param pageable
     * @param bdcZsQO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取证书列表
     */
    @GetMapping(value = "/zsxx/list/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public Object zslist(@LayuiPageable Pageable pageable, BdcZsQO bdcZsQO) {
        if (bdcZsQO == null || StringUtils.isEmpty(bdcZsQO.getGzlslid())) {
            throw new MissingArgumentException("gzlslid");
        }
        return listZsxx(pageable, bdcZsQO);
    }

    @GetMapping(value = "/zsxx/list/xm/{xmid}")
    @ResponseStatus(HttpStatus.OK)
    public Object zslistByXmid(@LayuiPageable Pageable pageable, BdcZsQO bdcZsQO) {
        if (bdcZsQO == null || StringUtils.isEmpty(bdcZsQO.getXmid())) {
            throw new MissingArgumentException("xmid");
        }
        return listZsxx(pageable, bdcZsQO);
    }

    private Object listZsxx(Pageable pageable, BdcZsQO bdcZsQO) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        Sort sort = "UNSORTED".equals(String.valueOf(pageable.getSort())) ? null : pageable.getSort();
        Page<BdcZsVO> bdcZsVOPage = bdcZsFeignService.bdcZsByPageJson(pageable.getPageNumber(), pageable.getPageSize(), sort, bdcZsQO);

        for (int i = 0; i < bdcZsVOPage.getContent().size(); i++) {
            // 转换证书类型字典项
            bdcZsVOPage.getContent().get(i).setZslxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsVOPage.getContent().get(i).getZslx(), zdMap.get(Constants.ZSLX)));
        }
        return super.addLayUiCode(bdcZsVOPage);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return Object 生成的证书信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成证书预览的信息
     */
    @GetMapping(value = "/zsxx/zsylList/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public Object zsylList(@PathVariable(name = "gzlslid") String gzlslid) throws Exception {
        List<BdcZsDO> bdcZsDOList = bdcZsInitFeignService.initBdcqzs(gzlslid, true);
        if (CollectionUtils.isEmpty(bdcZsDOList)) {
            return Collections.emptyList();
        }

        // 按照坐落正排序 added by zhuyong 20191029
        Collections.sort(bdcZsDOList, (zs1, zs2) -> {
            if (null == zs1 || null == zs2 || StringUtils.isBlank(zs1.getZl()) || StringUtils.isBlank(zs2.getZl())) {
                return 1;
            }
            return zs1.getZl().compareTo(zs2.getZl());
        });

        List<BdcZsVO> bdcZsVOList = new ArrayList(CollectionUtils.size(bdcZsDOList));
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();

        for (BdcZsDO bdcZsDO : bdcZsDOList) {
            BdcZsVO bdcZsVO = new BdcZsVO();
            BeanUtils.copyProperties(bdcZsDO, bdcZsVO);
            bdcZsVO.setZslxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsVO.getZslx(), zdMap.get(Constants.ZSLX)));
            bdcZsVO.setQllxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsVO.getQllx(), zdMap.get(Constants.QLLX)));
            //证书共有情况特殊处理
            if (StringUtils.equals("1", tsgyfs)) {
                try {
                    List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
                    if (CollectionUtils.isNotEmpty(bdcXmDTOList) && null != bdcXmDTOList.get(0)) {
                        BdcXmDTO bdcXmDTO = bdcXmDTOList.get(0);
                        bdcZsVO.setGyfsmc(bdcZsFeignService.dealZsGyqk(bdcZsDO, true, bdcXmDTO.getXmid()));
                    }
                } catch (Exception e) {
                    logger.error("证书特殊处理共有情况异常，请检查日志");
                }
            } else {
                bdcZsVO.setGyfsmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsVO.getGyfs(), zdMap.get(Constants.GYFS)));
            }
            bdcZsVOList.add(bdcZsVO);
        }
        return bdcZsVOList;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return BdcZsDO 不动产证书
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 验证流程中证书的印制号是否已经都保存，如果没有保存，返回未保存的证书信息
     */
    @GetMapping(value = "/zsxx/checkZsYzh/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public BdcZsDO checkZsYzh(@PathVariable(name = "gzlslid") String gzlslid) throws Exception {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("checkZsYzh缺失gzlslid！");
        }
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(gzlslid);
        // 只验证qszt是现势的证书
        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        bdcZsQO.setQsztList(qsztList);
        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
            for (BdcZsDO bdcZsDO : bdcZsDOList) {
                //证明单不验证印制号
                if (StringUtils.isBlank(bdcZsDO.getQzysxlh()) &&!CommonConstantUtils.ZSLX_ZMD.equals(bdcZsDO.getZslx())) {
                    return bdcZsDO;
                }
            }
        }
        return null;
    }

    @GetMapping(value = "/zsxx/zslx")
    @ResponseStatus(HttpStatus.OK)
    public List<String> queryGzlZslx(@RequestParam(name = "gzlslid", required = false) String gzlslid, @RequestParam(name = "xmid", required = false) String xmid) {
        if (StringUtils.isBlank(gzlslid) && StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("缺失gzlslid或xmid！");
        }
        return bdcZsFeignService.queryGzlZslx(gzlslid, xmid);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param bdcdyh  不动产单元号
     * @param zsyl    证书预览
     * @return BdcZmdVO 证明单信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取证明单信息（所有版本通用，字段只能多，不能少，取值需注意）
     */
    @GetMapping(value = "/zsxx/zmd")
    @ResponseStatus(HttpStatus.OK)
    public BdcZmdVO queryZmd(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "bdcdyh", required = false) String bdcdyh, @RequestParam(name = "zsyl") Boolean zsyl, @RequestParam(name = "zsid",required = false) String zsid) throws Exception {
        if (StringUtils.isBlank(gzlslid) && StringUtils.isBlank(bdcdyh)) {
            throw new MissingArgumentException("缺失gzlslid和bdcdyh！");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        bdcXmQO.setBdcdyh(bdcdyh);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList) || StringUtils.isBlank(bdcXmDOList.get(0).getXmid())) {
            throw new MissingArgumentException("未查询到项目信息！");
        }

        BdcXmDO bdcXmDO = getSczsXm(bdcXmDOList);
        String xmid = bdcXmDO.getXmid();
        // 避免参数中bdcdyh为空（通过跳转直接打开证明单页面），再赋值一次
        bdcdyh = bdcXmDO.getBdcdyh();
        logger.info("===>车库车位宗地用途1:{}",bdcXmDO.getZdzhyt());
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        BdcZmdVO bdcZmdVO = new BdcZmdVO();
        bdcZmdVO.setQszt(bdcXmDO.getQszt());
        bdcZmdVO.setZdzhyt(bdcXmDO.getZdzhyt());
        bdcZmdVO.setDbr(bdcXmDO.getDbr());
        bdcZmdVO.setZdzhmj(bdcXmDO.getZdzhmj());
        if (StringUtils.isNotBlank(bdcXmDO.getQlxz())) {
            bdcZmdVO.setTdqlxz(StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(bdcXmDO.getQlxz()), zdMap.get(Constants.QLXZ)));
        }
        bdcZmdVO.setTdcqzh(bdcXmDO.getBdcqzh());
        bdcZmdVO.setTdqllx(null == bdcXmDO.getQllx() ? "" : bdcXmDO.getQllx().toString());
        // 获取证书信息
        generateZmdFromZs(bdcZmdVO, xmid, zsyl,zsid);
        // 获取房地产权信息
        generateZmdFromFdcq(bdcZmdVO, xmid);
        // 获取土地产权信息
        generateZmdFromTdcq(bdcZmdVO, bdcdyh);

        List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxFeignService.listBdcSlJyxxByXmid(xmid);
        if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
            bdcZmdVO.setMjhdwjh(bdcSlJyxxDOList.get(0).getMjhdwjh());
            bdcZmdVO.setHtbah(bdcSlJyxxDOList.get(0).getHtbah());
        }
        // 设置土地基本信息隐藏
        tdjbxxHide(bdcZmdVO);
        // 转换字典项
        changeZmdZd(zdMap, bdcZmdVO);
        bdcZmdVO.setZdzhyt(bdcXmDO.getZdzhyt());
        logger.info("===>车库车位宗地用途2:{}",bdcZmdVO.getZdzhyt());
        //获取项目附表
        if(StringUtils.isNotBlank(xmid)) {
            BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
            bdcXmFbQO.setXmid(xmid);
            List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
            if(CollectionUtils.isNotEmpty(bdcXmFbDOList)){
                BdcXmFbDO bdcXmFbDO =bdcXmFbDOList.get(0);
                bdcZmdVO.setYsxkzh(bdcXmFbDO.getYsxkzh());
                bdcZmdVO.setSyqx(bdcXmFbDO.getSyqx());
            }
        }
        BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(xmid);
        if(bdcCshFwkgSlDO != null){
            bdcZmdVO.setSfzf(bdcCshFwkgSlDO.getSfzf());
        }
        bdcZmdVO.setZmnr(sczmdzmnr);
        return bdcZmdVO;
    }

    private void changeZmdZd(Map<String, List<Map>> zdMap, BdcZmdVO bdcZmdVO) {
        bdcZmdVO.setFwxz(StringToolUtils.convertBeanPropertyValueOfZd(StringUtils.isBlank(bdcZmdVO.getFwxz()) ? null : Integer.parseInt(bdcZmdVO.getFwxz()), zdMap.get(Constants.FWXZ)));
        bdcZmdVO.setDzwyt(StringToolUtils.convertBeanPropertyValueOfZd(StringUtils.isBlank(bdcZmdVO.getDzwyt()) ? null : Integer.parseInt(bdcZmdVO.getDzwyt()), zdMap.get(Constants.GHYT)));
        bdcZmdVO.setFwjg(StringToolUtils.convertBeanPropertyValueOfZd(StringUtils.isBlank(bdcZmdVO.getFwjg()) ? null : Integer.parseInt(bdcZmdVO.getFwjg()), zdMap.get(Constants.FWJG)));
        bdcZmdVO.setTdqllx(StringToolUtils.convertBeanPropertyValueOfZd(StringUtils.isBlank(bdcZmdVO.getTdqllx()) ? null : Integer.parseInt(bdcZmdVO.getTdqllx()), zdMap.get(Constants.QLLX)));

    }

    /**
     * @param bdcZmdVO 证明单信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 设置土地基本信息隐藏
     */
    private void tdjbxxHide(BdcZmdVO bdcZmdVO) {
        if (tdjbxxHide && StringUtils.isNotBlank(tdjbxxHideFwyt) && StringUtils.isNotBlank(tdjbxxElementName)) {
            String[] fwytArr = StringUtils.split(tdjbxxHideFwyt, CommonConstantUtils.ZF_YW_DH);
            String[] elementArr = StringUtils.split(tdjbxxElementName, CommonConstantUtils.ZF_YW_DH);
            Field[] fields = bdcZmdVO.getClass().getDeclaredFields();
            for (String fwyt : fwytArr) {
                if (StringUtils.equals(fwyt, bdcZmdVO.getDzwyt())) {
                    for (String element : elementArr) {
                        for (Field field : fields) {
                            if (StringUtils.equals(element, field.getName())) {
                                field.setAccessible(true);
                                try {
                                    field.set(bdcZmdVO, null);
                                } catch (IllegalAccessException e) {
                                    LOGGER.error("非法访问！", e);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param bdcZmdVO
     * @param bdcdyh   不动产单元号
     * @author <a href="mailto:yanjiaqiang@gtmap.cn">yanjiaqiang</a>
     * @description 首次证明单获取土地产权信息
     */
    private void generateZmdFromTdcq(BdcZmdVO bdcZmdVO, String bdcdyh) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        StringBuilder tembdcdyh = new StringBuilder(bdcdyh);
        tembdcdyh.replace(12, 14, "GB");
        bdcdyh = tembdcdyh.toString();
        bdcXmQO.setBdcdyh(StringUtils.substring(bdcdyh, 0, 19) + CommonConstantUtils.SUFFIX_ZD_BDCDYH);
        bdcXmQO.setQszt(CommonConstantUtils.QSZT_VALID);
        ArrayList qllxs = new ArrayList();
        qllxs.add("3");
        bdcXmQO.setQllxs(qllxs);
        List<BdcXmDO> bdcXmDOList = bdcQllxFeignService.getBdcXmByQllx(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            bdcZmdVO.setTdcqzh(bdcXmDO.getBdcqzh());
            bdcZmdVO.setTdqllx(null == bdcXmDO.getQllx() ? "" : bdcXmDO.getQllx().toString());
        }
    }

    /**
     * @param bdcZmdVO
     * @param xmid     项目ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 首次证明单获取房地产权信息
     */
    private void generateZmdFromFdcq(BdcZmdVO bdcZmdVO, String xmid) {
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
        if (!(bdcQl instanceof BdcFdcqDO)) {
            LOGGER.info("未查询到房地产权！");
            return;
        }
        BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
        bdcZmdVO.setFwxz(null == bdcFdcqDO.getFwxz() ? "" : bdcFdcqDO.getFwxz().toString());
        bdcZmdVO.setDzwyt(null == bdcFdcqDO.getGhyt() ? "" : bdcFdcqDO.getGhyt().toString());
        bdcZmdVO.setFwjg(null == bdcFdcqDO.getFwjg() ? "" : bdcFdcqDO.getFwjg().toString());

        bdcZmdVO.setZcs(bdcFdcqDO.getZcs());
        //36455 南通地区根据配置字段来set 所在层数值
        if (StringUtils.isNotBlank(fwszc)) {
            bdcZmdVO.setSzc(Objects.nonNull(ObjectUtils.getClassValue(bdcFdcqDO, fwszc)) ? ObjectUtils.getClassValue(bdcFdcqDO, fwszc).toString() : StringUtils.EMPTY);
        } else {
            bdcZmdVO.setSzc(Objects.nonNull(bdcFdcqDO.getSzc()) ? String.valueOf(bdcFdcqDO.getSzc()) : StringUtils.EMPTY);
        }
        bdcZmdVO.setSzmyc(bdcFdcqDO.getSzmyc());
        bdcZmdVO.setDzwmj(bdcFdcqDO.getJzmj());
        bdcZmdVO.setFtjzmj(bdcFdcqDO.getFtjzmj());
        bdcZmdVO.setTnjzmj(bdcFdcqDO.getZyjzmj());
        bdcZmdVO.setJgsj(bdcFdcqDO.getJgsj());
        bdcZmdVO.setSlbh(bdcFdcqDO.getSlbh());
        bdcZmdVO.setDjsj(bdcFdcqDO.getDjsj());
        bdcZmdVO.setDjjg(bdcFdcqDO.getDjjg());
        bdcZmdVO.setJzmj(bdcFdcqDO.getJzmj());
        bdcZmdVO.setDytdmj(bdcFdcqDO.getDytdmj());
        bdcZmdVO.setFttdmj(bdcFdcqDO.getFttdmj());
        bdcZmdVO.setTdsyjssj(bdcFdcqDO.getTdsyjssj());
        bdcZmdVO.setTdsyqmj(bdcFdcqDO.getTdsyqmj());
        if (StringUtils.isNotBlank(bdcFdcqDO.getBdcdyh())) {
            bdcZmdVO.setZdzhh(bdcFdcqDO.getBdcdyh().substring(0, 19));
        }
    }

    /**
     * @param bdcZmdVO 证明单
     * @param xmid     项目ID
     * @param zsyl
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据证书信息获取证明单内容
     */
    private void generateZmdFromZs(BdcZmdVO bdcZmdVO, String xmid, Boolean zsyl,String zsid) throws Exception {
        List<BdcZsDO> bdcZsDOList;
        if (zsyl) {
            bdcZsDOList = bdcZsInitFeignService.initBdcqz(xmid, true);
        } else if(StringUtils.isNotBlank(zsid)){
            bdcZsDOList =new ArrayList<>();
            //存在证书ID,优先根据zsid获取证书
            BdcZsDO bdcZsDO =bdcZsFeignService.queryBdcZs(zsid);
            if(bdcZsDO != null){
                bdcZsDOList.add(bdcZsDO);
            }
        }else{
            bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(xmid);
        }
        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
            BdcZsDO bdcZsDO = bdcZsDOList.get(0);
            bdcZmdVO.setZsid(bdcZsDO.getZsid());
            bdcZmdVO.setEwmnr(bdcZsDO.getEwmnr());
            bdcZmdVO.setBdcqzh(bdcZsDO.getBdcqzh());
            bdcZmdVO.setZl(bdcZsDO.getZl());
            bdcZmdVO.setBdcdyh(bdcZsDO.getBdcdyh());
            bdcZmdVO.setFj(bdcZsDO.getFj());
            bdcZmdVO.setNf(bdcZsDO.getNf());
            bdcZmdVO.setZhlsh(bdcZsDO.getZhlsh());
            bdcZmdVO.setQlr(bdcZsDO.getQlr());
            bdcZmdVO.setDyzt(bdcZsDO.getDyzt());

            bdcZmdVO.setSzr(bdcZsDO.getSzr());
            if (StringUtils.isBlank(bdcZsDO.getSzr())) {
                try {
                    UserDto userDto = userManagerUtils.getCurrentUser();
                    bdcZmdVO.setSzr(userDto.getAlias());
                } catch (Exception e) {
                    LOGGER.info("获取当前用户异常！", e);
                }
            }
        }
    }

    /**
     * @param zsid 证书ID
     * @return 项目的证书信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取证书信息
     */
    @GetMapping(value = "/zsxx/{zsid}")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "zsid", value = "证书ID", dataType = "string", paramType = "path", required = true)})
    public BdcZsVO queryZsxx(@PathVariable(name = "zsid") String zsid) {
        BdcZsVO bdcZsVO = new BdcZsVO();
        // 获取证书信息
        BdcZsDO bdcZsDO = bdcZsFeignService.queryBdcZs(zsid);
        generateBdcZsVO(bdcZsVO, bdcZsDO,false,"");
        return bdcZsVO;
    }

    /**
     * @description 查询项目信息中同一工作流实例ID不动产单元号数量（【常州】提示查看清册和附表）
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/6/21 21:23
     * @param gzlslid 工作流实例ID
     * @return int 同一工作流实例ID不动产单元号数量
     */
    @GetMapping(value = "/xmxx/{gzlslid}/bdcqzh/count")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "流程ID", dataType = "string", paramType = "path", required = true)})
    public int countBdcByGzlslidGroupBdcdyh(@PathVariable(name = "gzlslid") String gzlslid) {
        return bdcXmFeignService.countBdcByGzlslidGroupBdcdyh(gzlslid);
    }

    /**
     * @param bdcZsVO 页面传输对象
     * @param bdcZsDO 查询到的证书对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据证书查询结果，生成页面证书对象
     */
    private void generateBdcZsVO(BdcZsVO bdcZsVO, BdcZsDO bdcZsDO,boolean zsyl,String xmid) {
        if (null != bdcZsDO) {
            Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
            bdcZsDO.setBdcdyh(BdcdyhToolUtils.formatBdcdyh(bdcZsDO.getBdcdyh()));
            BeanUtils.copyProperties(bdcZsDO, bdcZsVO);

            // 处理蚌埠的共有方式
            if (StringUtils.equals("1", tsgyfs)) {
                String gyfs = "";
                try {
                    gyfs = bdcZsFeignService.dealZsGyqk(bdcZsDO,zsyl,xmid);
                    if(StringUtils.isNotBlank(gyfs)){
                        bdcZsVO.setGyfsmc(gyfs);
                    }
                } catch (Exception e) {
                    LOGGER.error("蚌埠处理共有方式异常，请检查日志");
                }
            }else {
                // 转换共有方式
                bdcZsVO.setGyfsmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsDO.getGyfs(), zdMap.get(Constants.GYFS)));
            }
            // 转换权利类型
            bdcZsVO.setQllxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsDO.getQllx(), zdMap.get(Constants.QLLX)));
            // 截取证流水号
            String bdcqzh = bdcZsDO.getBdcqzh();
            if (StringUtils.isNotBlank(bdcqzh)) {
                Integer start = StringUtils.indexOf(bdcqzh, CommonConstantUtils.DI);
                Integer end = StringUtils.indexOf(bdcqzh, CommonConstantUtils.HAO);
                bdcZsVO.setZhlsh(StringUtils.substring(bdcqzh, start + 1, end));
            }

            // 获取相关的项目信息
            BdcXmDO bdcXmDO = new BdcXmDO();
            List<BdcXmDO> bdcXmDOList = bdcZsFeignService.queryZsXmByZsid(bdcZsDO.getZsid());
            if (CollectionUtils.isNotEmpty(bdcXmDOList) && null != bdcXmDOList.get(0)) {
                bdcXmDO = bdcXmDOList.get(0);
                bdcZsVO.setDjjg(bdcXmDO.getDjjg());
                bdcZsVO.setQszt(bdcXmDO.getQszt());
                if(StringUtils.isNotBlank(bdcXmDO.getXmid())) {
                    BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
                    bdcXmFbQO.setXmid(bdcXmDO.getXmid());
                    List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
                    if (CollectionUtils.isNotEmpty(bdcXmFbDOList)) {
                        bdcZsVO.setQjgldm(bdcXmFbDOList.get(0).getQjgldm());
                    }
                }
            }
            bdcZsVO.setBdcXmDOList(bdcXmDOList);

            // 缮证日期，对应证书上面的颁发日期，标准版取的登簿时间，合肥取缮证时间
            Date bfrq = bdcXmDO.getDjsj();
            if (StringUtils.equals(Constants.VERSION_HEFEI, super.systemVersion)) {
                bfrq = bdcZsDO.getSzsj();
            }
            bdcZsVO.setSzrq(bfrq);
            bdcZsVO.setSzrqDay(bfrq);
            bdcZsVO.setSzrqMonth(bfrq);
            bdcZsVO.setSzrqYear(bfrq);
            //设置二维码内容
            if (StringUtils.isNotBlank(zszmEwmVersion)) {
                switch (zszmEwmVersion) {
                    case "2":
                        bdcZsVO.setEwmnr(StringToolUtils.resolveBeanToAppendStr(bdcXmDOList, "getBdcdyh", CommonConstantUtils.ZF_YW_DH));
                        break;
                    case "3":
                        if (zsyl) {
                            //用xmid 查权利人和坐落
                            if (StringUtils.isNotBlank(xmid)) {
                                BdcXmQO bdcXmQO = new BdcXmQO(xmid);
                                List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
                                if (CollectionUtils.isNotEmpty(bdcXmList)) {
                                    bdcZsVO.setEwmnr(bdcXmList.get(0).getQlr() + "、" + (StringUtils.isBlank(bdcXmList.get(0).getBdcqzh()) ? "" : bdcXmList.get(0).getBdcqzh()) + "、" + bdcXmList.get(0).getZl());
                                }
                            }
                        } else {
                            //直接取证书表信息
                            bdcZsVO.setEwmnr(bdcZsDO.getQlr() + "、" + (StringUtils.isBlank(bdcZsDO.getBdcqzh()) ? "" : bdcZsDO.getBdcqzh()) + "、" + bdcZsDO.getZl());
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid    项目ID，为空时取流程任意一个xmid
     * @return BdcZsVO 证书信息VO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询项目的预览证书信息
     */
    @GetMapping(value = "/xmZsyl/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path", required = true)})
    public List<BdcZsVO> queryXmZsylxx(@PathVariable(name = "gzlslid") String gzlslid, @RequestParam(name = "xmid", required = false) String xmid) throws Exception {
        if (StringUtils.isBlank(xmid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                BdcXmDO bdcXmDO = getSczsXm(bdcXmDOList);
                xmid = null == bdcXmDO ? "" : bdcXmDO.getXmid();
            }

        }
        List<BdcZsVO> bdcZsVOList = new ArrayList();
        if (StringUtils.isBlank(xmid)) {
            return bdcZsVOList;
        }
        List<BdcZsDO> bdcZsDOList = bdcZsInitFeignService.initBdcqz(xmid, true);
        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
            for (BdcZsDO bdcZsDO : bdcZsDOList) {
                BdcZsVO bdcZsVO = new BdcZsVO();
                bdcZsDO.setSzsj(new Date());
                bdcZsDO.setBdcdyh(BdcdyhToolUtils.formatBdcdyh(bdcZsDO.getBdcdyh()));
                generateBdcZsVO(bdcZsVO, bdcZsDO, true, xmid);
//                //获取权利人和义务人信息
//                generateQlrxx(bdcZsVO, bdcZsDO, xmid, djxl, gzlslid);
                bdcZsVOList.add(bdcZsVO);
            }
        }
        return bdcZsVOList;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return BdcZsVO 证书信息VO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据工作流实例ID查询单个证书信息
     */
    @GetMapping(value = "/zsxx/{gzlslid}/single")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path", required = true)})
    public BdcZsVO queryGzlZsxx(@PathVariable(name = "gzlslid") String gzlslid, @RequestParam(name = "zsyl", required = false) boolean zsyl) throws Exception {
        BdcZsVO bdcZsVO = new BdcZsVO();
        List<BdcZsDO> bdcZsDOList;
        if (zsyl) {
            bdcZsDOList = bdcZsInitFeignService.initBdcqzs(gzlslid, zsyl);
        } else {
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setGzlslid(gzlslid);
            bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
        }
        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
            String xmid="";
            List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
                xmid =bdcXmDTOList.get(0).getXmid();
            }
            generateBdcZsVO(bdcZsVO, bdcZsDOList.get(0),zsyl,xmid);
        }
        return bdcZsVO;
    }

    /**
     * @param bdcZsQO 证书查询QO
     * @return BdcZsQsztDTO 证书状态DTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 验证未登簿和已注销的证书
     */
    @PostMapping(value = "/zsxx/zsQszt")
    @ResponseStatus(HttpStatus.OK)
    public Object queryBdcZsQszt(@RequestBody BdcZsQO bdcZsQO) {
        // 特定权属状态的证书
        List<BdcZsQsztDTO> bdcZsQsztDTOList = bdcZsFeignService.queryBdcZsQszt(bdcZsQO);

        // 所有的证书
        bdcZsQO.setQsztList(null);
        List<BdcZsQsztDTO> bdcZsDOList = bdcZsFeignService.queryBdcZsQszt(bdcZsQO);

        if (CollectionUtils.isNotEmpty(bdcZsQsztDTOList) && CollectionUtils.size(bdcZsQsztDTOList) == CollectionUtils.size(bdcZsDOList)) {
            // 所有的证书都未登簿或已注销，返回-1
            return -1;
        } else {
            return bdcZsQsztDTOList;
        }
    }

    /**
     * @param zsid     证书ID
     * @param bdcYzhQO 印制号的查询参数
     * @return BdcYzhDTO 查询到的印制号信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 为单个证书获取印制号
     */
    @PostMapping(value = "/yzh/{zsid}")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "zsid", value = "证书ID", dataType = "String", paramType = "path", required = true),
            @ApiImplicitParam(name = "bdcYzhQO", value = "印制号查询QO", dataType = "BdcYzhQO", paramType = "body", required = true)})
    public BdcYzhDTO queryBdcZsYzh(@PathVariable(name = "zsid") String zsid, @RequestBody BdcYzhQO bdcYzhQO) {
        UserDto userDto = userManagerUtils.getCurrentUser();
        if (null == userDto) {
            throw new UserInformationAccessException("为单个证书" + zsid + "获取印制号");
        }
        bdcYzhQO.setSyr(userDto.getAlias());
        bdcYzhQO.setSyrid(userDto.getId());

        yzhLqfsAndSetValue(bdcYzhQO, userDto, null);

        return bdcYzhFeignService.queryBdcZsYzh(zsid, bdcYzhQO);
    }

    /**
     * @param bdcYzhQOList 印制号查询QOList
     * @return List<BdcYzhDTO> 获取结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 流程所有证书获取印制号
     */

    @PostMapping(value = "/yzh/batchZs")
    @ResponseStatus(HttpStatus.OK)
    public List<BdcYzhDTO> queryBatchZsYzh(@RequestBody List<BdcYzhQO> bdcYzhQOList) {
        UserDto userDto = userManagerUtils.getCurrentUser();
        if (null == userDto) {
            throw new UserInformationAccessException("证书批量获取印制号");
        }
        if (CollectionUtils.isEmpty(bdcYzhQOList) || StringUtils.isBlank(bdcYzhQOList.get(0).getQxdm())) {
            throw new MissingArgumentException("缺失印制号查询对象或缺失区县代码！");
        }
        String lqfs = bdcXtZsyzhFeignService.getZsyzhLqfs(bdcYzhQOList.get(0).getQxdm());

        // 组织查询参数
        for (BdcYzhQO bdcYzhQO : bdcYzhQOList) {
            bdcYzhQO.setSyr(userDto.getAlias());
            bdcYzhQO.setSyrid(userDto.getId());

            yzhLqfsAndSetValue(bdcYzhQO, userDto, lqfs);
        }

        return bdcYzhFeignService.queryBatchZsYzh(bdcYzhQOList);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param qxdm    区县代码
     * @return List<BdcYzhDTO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 为流程所有的证书获取印制号，默认所有证书的qxdm是一致的(区县代码默认为前端获取的参数 ， 为空时获取证书的qxdm)
     */
    @GetMapping(value = "/yzh/allZs/{gzlslid}")
    public Object queryAllZsYzh(@PathVariable(name = "gzlslid") String gzlslid, @RequestParam(name = "qxdm", required = false) String qxdm) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失工作流实例ID！");
        }
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(gzlslid);
        // 已经存在印制号的证书，将不再重复获取
        bdcZsQO.setSqlStr("a.qzysxlh is null");
        // 只为现势的项目的证书获取印制号
        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        bdcZsQO.setQsztList(qsztList);
        // 证书ID
        bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZS);
        List<String> zsidList = bdcZsFeignService.queryZsid(bdcZsQO);
        // 证明ID
        bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZM);
        List<String> zmidList = bdcZsFeignService.queryZsid(bdcZsQO);

        UserDto userDto = userManagerUtils.getCurrentUser();
        if (null == userDto) {
            throw new UserInformationAccessException("流程所有证书获取印制号,缺失用户信息");
        }

        String lqfs = "";
        List<BdcYzhQO> bdcYzhQOList = new ArrayList();

        // 封装证书获取印制号的查询参数
        if (CollectionUtils.isNotEmpty(zsidList)) {
            BdcZsDO bdcZsDO = bdcZsFeignService.queryBdcZs(zsidList.get(0));
            // 没有值获取证书的qxdm
            qxdm = StringUtils.isBlank(qxdm) ? bdcZsDO.getQxdm() : qxdm;
            lqfs = bdcXtZsyzhFeignService.getZsyzhLqfs(qxdm);

            bdcYzhQOList.add(generateYzhQO(userDto, zsidList, CommonConstantUtils.ZSLX_ZS, qxdm, lqfs));
        }
        // 封装证明获取印制号的查询参数
        if (CollectionUtils.isNotEmpty(zmidList)) {
            if (StringUtils.isBlank(qxdm)) {
                BdcZsDO bdcZsDO = bdcZsFeignService.queryBdcZs(zmidList.get(0));
                qxdm = bdcZsDO.getQxdm();
                lqfs = bdcXtZsyzhFeignService.getZsyzhLqfs(qxdm);
            }
            bdcYzhQOList.add(generateYzhQO(userDto, zmidList, CommonConstantUtils.ZSLX_ZM, qxdm, lqfs));
        }
        if (CollectionUtils.isEmpty(bdcYzhQOList)) {
            return 0;
        }
        return bdcYzhFeignService.queryBatchZsYzh(bdcYzhQOList);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 封装印制号获取参数对象
     */
    private BdcYzhQO generateYzhQO(UserDto userDto, List<String> zsidList, Integer zslx, String qxdm, String lqfs) {
        BdcYzhQO bdcYzhQO = new BdcYzhQO();
        bdcYzhQO.setZslx(zslx);
        bdcYzhQO.setQxdm(qxdm);
        bdcYzhQO.setSyqk(CommonConstantUtils.SYQK_WSY);
        bdcYzhQO.setSyr(userDto.getAlias());
        bdcYzhQO.setSyrid(userDto.getId());
        bdcYzhQO.setZsidList(zsidList);

        yzhLqfsAndSetValue(bdcYzhQO, userDto, lqfs);
        return bdcYzhQO;
    }


    /**
     * @param bdcYzhQO 印制号查询QO
     * @param userDto
     * @param lqfs
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 确认印制号的领取方式并修改参数值
     */
    private void yzhLqfsAndSetValue(BdcYzhQO bdcYzhQO, UserDto userDto, String lqfs) {
        if (null == userDto) {
            userDto = userManagerUtils.getCurrentUser();
        }
        if (null == userDto) {
            throw new UserInformationAccessException("未获取到当前用户信息！");
        }

        if (StringUtils.isBlank(lqfs)) {
            if (StringUtils.isBlank(bdcYzhQO.getQxdm())) {
                throw new MissingArgumentException("缺失区县代码！");
            }
            lqfs = bdcXtZsyzhFeignService.getZsyzhLqfs(bdcYzhQO.getQxdm());
        }

        if (StringUtils.isNotBlank(lqfs)) {
            // 按人员领取
            if (StringUtils.equals(lqfs, CommonConstantUtils.YZH_LQFS_RY)) {
                bdcYzhQO.setLqrid(userDto.getId());
            } else if (StringUtils.equals(lqfs, CommonConstantUtils.YZH_LQFS_BM)) {
                // 按领取部门领取
                if (CollectionUtils.isEmpty(userDto.getOrgRecordList())) {
                    throw new MissingArgumentException("系统所配印制号获取方式为：部门领取，但是当前用户没有所在部门的信息！");
                }
                bdcYzhQO.setLqbmid(userDto.getOrgRecordList().get(0).getId());
            }
        }
    }

    /**
     * @param bdcYzhQO 印制号查询对象
     * @return BdcYzhDTO 查询到的印制号信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据条件，精确查询印制号，只获取第一个数据
     */
    @PostMapping(value = "/yzh")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYzhQO", value = "印制号查询QO", dataType = "BdcYzhQO", paramType = "body", required = true)})
    public BdcYzhDTO querySingleYzh(@RequestBody BdcYzhQO bdcYzhQO) {
        yzhLqfsAndSetValue(bdcYzhQO, null, null);
        List<BdcYzhDTO> bdcYzhDTOList = bdcYzhFeignService.queryListBdcYzh(bdcYzhQO);
        if (CollectionUtils.isNotEmpty(bdcYzhDTOList) && null != bdcYzhDTOList.get(0)) {
            return bdcYzhDTOList.get(0);
        }
        return null;
    }

    /**
     * @param bdcYzhQO 印制号查询信息
     * @return BdcGzyzTsxxDTO 验证信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 验证印制号是否可用，可用时返回bdcGzyzTsxxDTO
     */
    @PostMapping(value = "/yzh/yzxx")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYzhQO", value = "印制号查询QO", dataType = "BdcYzhQO", paramType = "body", required = true)})
    public BdcYzxxDTO queryYzhYzxx(@RequestBody BdcYzhQO bdcYzhQO) {
        return bdcYzhFeignService.queryYzhYztsxx(bdcYzhQO);

    }

    /**
     * @param bdcYzhQO 印制号查询对象
     * @return Object 操作结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 作废印制号
     */
    @PostMapping(value = "/yzh/zfyzh")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYzhQO", value = "印制号查询QO", dataType = "BdcYzhQO", paramType = "body", required = true)})
    @Transactional(rollbackFor = Exception.class)
    public Object zsZfyzh(@RequestBody BdcYzhQO bdcYzhQO) {
        if (StringUtils.isBlank(bdcYzhQO.getZsid())) {
            throw new MissingArgumentException("缺失证书ID");
        }
        // 获取印制号的领取方式，并更新查询参数
        yzhLqfsAndSetValue(bdcYzhQO, null, null);
        BdcYzhDTO bdcYzhDTO = querySingleYzh(bdcYzhQO);
        if (null == bdcYzhDTO) {
            return "未查询到要作废的印制号信息！";
        }

        String zfyy = "证书印制号作废！";
        BdcYzhSyqkQO bdcYzhSyqkQO = new BdcYzhSyqkQO();
        bdcYzhSyqkQO.setSyqk(CommonConstantUtils.SYQK_ZF);
        bdcYzhSyqkQO.setSyyy(zfyy);
        bdcYzhSyqkQO.setYzhid(bdcYzhDTO.getYzhid());
        bdcYzhSyqkQO.setZsid(bdcYzhDTO.getZsid());
        // 清空证书里的印制号
        bdcYzhSyqkQO.setQzysxlh(null);
        BdcYzhsymxDO bdcYzhsymxDO = updateBdcYzhSyqk(bdcYzhSyqkQO);


        if (null == bdcYzhsymxDO) {
            return "更新作废状态失败！";
        } else {
            return true;
        }
    }

    /**
     * @param bdcYzhQO 印制号查询QO对象
     * @return Object 操作结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 还原印制号（还原印制号表中的使用情况，清空zsid。清空证书表中的qzysxlh）
     */
    @PostMapping(value = "/yzh/hyyzh")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYzhQO", value = "印制号查询QO", dataType = "BdcYzhQO", paramType = "body", required = true)})
    @Transactional(rollbackFor = Exception.class)
    public Object zsHyyzh(@RequestBody BdcYzhQO bdcYzhQO) {
        if (StringUtils.isBlank(bdcYzhQO.getZsid())) {
            throw new MissingArgumentException("缺失证书ID");
        }
        // 获取印制号的领取方式，并更新查询参数
        yzhLqfsAndSetValue(bdcYzhQO, null, null);
        BdcYzhDTO bdcYzhDTO = querySingleYzh(bdcYzhQO);
        if (null == bdcYzhDTO) {
            return "未查询到要还原的印制号信息！";
        }

        String zfyy = "证书印制号还原！";
        BdcYzhSyqkQO bdcYzhSyqkQO = new BdcYzhSyqkQO();
        bdcYzhSyqkQO.setSyqk(CommonConstantUtils.SYQK_YLY);
        bdcYzhSyqkQO.setSyyy(zfyy);
        bdcYzhSyqkQO.setYzhid(bdcYzhDTO.getYzhid());
        bdcYzhSyqkQO.setZsid(bdcYzhDTO.getZsid());
        // 清空证书里的印制号
        bdcYzhSyqkQO.setQzysxlh(null);
        BdcYzhsymxDO bdcYzhsymxDO = updateBdcYzhSyqk(bdcYzhSyqkQO);


        if (null == bdcYzhsymxDO) {
            return "更新作废状态失败！";
        } else {
            return true;
        }
    }


    /**
     * @param bdcYzhSyqkQO 印制号使用情况参数
     * @return BdcYzhsymxDO 印制号使用明细
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新印制号的使用情况
     */
    @PostMapping(value = "/yzhsyqk")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYzhSyqkQO", value = "印制号使用情况QO", dataType = "BdcYzhSyqkQO", paramType = "body", required = true)})
    public BdcYzhsymxDO updateBdcYzhSyqk(@RequestBody BdcYzhSyqkQO bdcYzhSyqkQO) {
        // 更新印制号的使用情况
        UserDto userDto = userManagerUtils.getCurrentUser();
        bdcYzhSyqkQO.setSyr(userDto.getAlias());
        bdcYzhSyqkQO.setSyrid(userDto.getId());
        return bdcYzhFeignService.updateBdcYzhSyqk(bdcYzhSyqkQO);
    }

    /**
     * @param bdcYzhSyqkQO 印制号使用情况参数
     * @return BdcYzhsymxDO 印制号使用明细
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 还原被手动修改的印制号信息
     */
    @PostMapping(value = "/hyYzhAndSyqk")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYzhSyqkQO", value = "印制号使用情况QO", dataType = "BdcYzhSyqkQO", paramType = "body", required = true)})
    public int hyYzhAndSyqk(@RequestBody BdcYzhSyqkQO bdcYzhSyqkQO) {
        // 更新印制号的使用情况
        UserDto userDto = userManagerUtils.getCurrentUser();
        bdcYzhSyqkQO.setSyr(userDto.getAlias());
        bdcYzhSyqkQO.setSyrid(userDto.getId());
        return bdcYzhFeignService.revertYzhAndSyqk(bdcYzhSyqkQO);
    }

    /**
     * @param bdcZsDOTemp 更新的证书信息
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新证书信息
     */
    @PatchMapping(value = "/zsxx")
    @ResponseStatus(HttpStatus.OK)
    public int updateBdcZs(@RequestBody BdcZsDO bdcZsDOTemp) {
        if (StringUtils.isBlank(bdcZsDOTemp.getZsid())) {
            throw new MissingArgumentException("缺失zsid！");
        }

        String qlqtzk = bdcZsDOTemp.getQlqtzk();
        String fj = bdcZsDOTemp.getFj();
        if (StringUtils.isBlank(qlqtzk) && StringUtils.isBlank(fj)) {
            return 0;
        }

        BdcZsDO bdcZsDO = new BdcZsDO();
        bdcZsDO.setZsid(bdcZsDOTemp.getZsid());
        bdcZsDO.setQlqtzk(qlqtzk);
        bdcZsDO.setFj(fj);
        return bdcZsFeignService.updateBdcZs(bdcZsDO);
    }

    /**
     * @param zsidList 指定证书
     * @param gzlslid  工作流实例ID
     * @return int
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 证书ID有值，则更新指定的证书，否则更新所有的证书信息
     */
    @PostMapping(value = "/zsxx/szr")
    @ResponseStatus(HttpStatus.OK)
    public BdcSzxxVO saveSzr(@RequestBody List<String> zsidList, @RequestParam(name = "gzlslid", required = false) String gzlslid) {
        if (CollectionUtils.isNotEmpty(zsidList)) {
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setZsidList(zsidList);
            bdcZsQO.setUserDto(userManagerUtils.getCurrentUser());
            BdcSzxxVO bdcSzxxVO = bdcFzjlFeignService.updateSzr(bdcZsQO);
            //南通版本需要回写缮证人字段到大云，用于项目列表展示和搜索
            if (StringUtils.equals(Constants.VERSION_NANTONG, htmlVersion)) {
                try {
                    Map<String, Object> paramMap = new HashMap<>(1);
                    paramMap.put("SZR", userManagerUtils.getCurrentUser().getAlias());
                    LOGGER.info("-=-=-={}", paramMap.get("SZR"));
                    LOGGER.info("-=-=-=gzlslid{}", gzlslid);
                    bdcYwsjHxFeignService.updateBdcYwsj(gzlslid, paramMap);
                } catch (Exception e) {
                    LOGGER.error("zs回写大云字段异常！gzlslid={},回写字段szr={}", gzlslid, userManagerUtils.getCurrentUser().getAlias());
                }
            }
            return bdcSzxxVO;
        } else {
            return bdcFzjlFeignService.updateSzr(gzlslid, userManagerUtils.getCurrentUserName());
        }
    }

    /**
     * @param zsid
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取宗地图
     */
    @GetMapping(value = "/zdxx/zdt")
    @ResponseStatus(HttpStatus.OK)
    public String queryBdcZdt(@RequestParam(name = "zsid",required = false) String zsid,@RequestParam(name = "bdcdyh",required = false) String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        ZdtResponseDTO zdtResponseDTO = new ZdtResponseDTO();
        if(StringUtils.isNotBlank(bdcdyh)){
            zdtResponseDTO = zdFeignService.queryZdtByBdcdyh(bdcdyh,qjgldm);
        }else{
            BdcZsDO bdcZsDO = bdcZsFeignService.queryBdcZs(zsid);
            if (bdcZsDO != null && StringUtils.isNotBlank(bdcZsDO.getBdcdyh())){
                zdtResponseDTO = zdFeignService.queryZdtByBdcdyh(bdcZsDO.getBdcdyh(),qjgldm);
            }else{
                throw new MissingArgumentException("缺失zsid！");
            }
        }
        if (null != zdtResponseDTO) {
            return zdtResponseDTO.getBase64();
        }
        return "";
    }

    /**
     * @param zsid 证书ID
     * @return String 户室图base64字符串码
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取户室图信息
     */
    @GetMapping(value = "/fwxx/hst")
    @ResponseStatus(HttpStatus.OK)
    public String queryBdcHst(@RequestParam(name = "zsid",required = false) String zsid,@RequestParam(name = "bdcdyh",required = false) String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        String res = "";
        if(StringUtils.isNotBlank(bdcdyh)){
            res = fwHstFeignService.queryHstBase64ForHsAndDz(bdcdyh, qjgldm);
        }else{
            BdcZsDO bdcZsDO = bdcZsFeignService.queryBdcZs(zsid);
            if (bdcZsDO != null && StringUtils.isNotBlank(bdcZsDO.getBdcdyh())){
                res = fwHstFeignService.queryHstBase64ForHsAndDz(bdcZsDO.getBdcdyh(), qjgldm);
            }else{
                throw new MissingArgumentException("缺失zsid！");
            }
        }
        return res;
    }

    /**
     * @param bdcdyh   不动产单元号
     * @param qjgldm   权籍管理代码
     * @param angel    旋转角度
     * @description 宗地图打印（原有zdt服务在IE模式下因为返回的地址有&符号会有问题，因此增加此接口，避免&符号）
     */
    @GetMapping(value = "zs/print/{dylx}/{bdcdyh}/{qjgldm}")
    public void zdt2(@PathVariable(name = "dylx") String dylx, @PathVariable(name = "bdcdyh") String bdcdyh, @PathVariable(name = "qjgldm") String qjgldm,
                     @RequestParam(name = "angel", required = false) Integer angel, HttpServletResponse response) {
        zdt(dylx, bdcdyh, angel, qjgldm, response);
    }

    /**
     * @param bdcdyh 不动产单元号
     * @param response
     * @param qjgldm
     * @param angel 旋转角度
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @GetMapping(value = "zs/print/{dylx}/{bdcdyh}")
    public void zdt(@PathVariable(name = "dylx") String dylx, @PathVariable(name = "bdcdyh") String bdcdyh,
                    @RequestParam(name = "angel", required = false) Integer angel,
                    @RequestParam(name = "qjgldm", required = false) String qjgldm,
                    HttpServletResponse response) {
            response.setContentType("image/jpg;charset=utf-8");
            BufferedImage image = null;
            ByteArrayInputStream bis = null;
            OutputStream os = null;
            try {
                String imageStr = "";
                if (StringUtils.equals(Constants.ZDT, dylx)) {
                    ZdtResponseDTO zdtResponseDTO = zdFeignService.queryZdtByBdcdyh(bdcdyh, null == qjgldm ? "" : qjgldm);
                    if (null != zdtResponseDTO) {
                        imageStr = zdtResponseDTO.getBase64();
                    }
                    response.addHeader("Content-Disposition", "attachment; filename=宗地图.jpg");
                } else if (StringUtils.equals(Constants.HST, dylx)) {
                    response.addHeader("Content-Disposition", "attachment; filename=户室图.jpg");
                    imageStr = fwHstFeignService.queryHstBase64ForHsAndDz(bdcdyh, null == qjgldm ? "" : qjgldm);
                }
                if (StringUtils.isBlank(imageStr)) {
                    throw new AppException("未获取到图像信息");
                }
                byte[] bytes = Base64.decodeBase64(imageStr.getBytes("UTF-8"));
                if(sfllqxz){
                    os = response.getOutputStream();
                    bis = new ByteArrayInputStream(bytes);
                    if (sfhahst){
                        if(Objects.nonNull(angel)){
                            Thumbnails.of(bis).rotate(90).size(680,950).outputFormat("jpg").toOutputStream(os);
                        }else {
                            Thumbnails.of(bis).size(680,950).outputFormat("jpg").toOutputStream(os);
                        }
                        os.flush();
                    }else {
                        image = ImageIO.read(bis);
                        Graphics2D gs = image.createGraphics();
                        gs.setBackground(Color.WHITE);
                        if(Objects.nonNull(angel)){
                            image = ImageUtils.Rotate(image, 90);
                        }
                        image.flush();
                        ImageIO.write(image, "jpg", response.getOutputStream());
                    }

                }else{
                    response.setCharacterEncoding("UTF-8");
                    response.reset();
                    response.setContentType("image/jpeg;charset=utf-8");
                    if (StringUtils.equals(Constants.ZDT, dylx)) {
                        response.setHeader("Content-Disposition", "inline; filename=宗地图.jpeg");
                    } else if (StringUtils.equals(Constants.HST, dylx)) {
                        response.setHeader("Content-Disposition", "inline; filename=户室图.jpeg");
                    }
                    os = response.getOutputStream();
                    os.write(bytes);
                    os.flush();
                }
            } catch (IOException e) {
                LOGGER.error("证书子系统系统：根据DJH查询图像异常", e);
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        LOGGER.error("证书子系统系统：根据DJH查询图像关闭流异常", e);
                    }
                }
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        LOGGER.error("证书子系统系统：根据DJH查询图像关闭流异常", e);
                    }
                }
            }

    }

    /**
     * @param zdzhh
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取分层分户图
     */
    @PostMapping(value = "/zdxx/fcfht")
    @ResponseStatus(HttpStatus.OK)
    public Object queryBdcFcfht(@RequestParam(name = "zdzhh") String zdzhh) {
        return null;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param glGzyz  过滤规则验证（前台忽略所有的验证信息后该值为true，直接登簿）
     * @return List<BdcGzyzVO> 规则验证信息
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 手动登簿前规则验证
     */
    @PostMapping(value = "/zs/sddb")
    @ResponseStatus(HttpStatus.OK)
    public Object sddb(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "glGzyz", required = false) Boolean glGzyz) {
        //验证证书是否存在临时状态
        BdcZsQO bdcZsQO =new BdcZsQO();
        bdcZsQO.setGzlslid(gzlslid);
        boolean existLszt =yzlszt(bdcZsQO);
        if(Boolean.FALSE.equals(existLszt)){
            LOGGER.error("不存在临时状态的证书,不允许手动登簿");
            List<BdcGzyzVO> bdcGzyzVOList =new ArrayList<>();
            BdcGzyzVO bdcGzyzVO =new BdcGzyzVO();
            bdcGzyzVO.setTsxx("证书已登簿");
            bdcGzyzVO.setYzlx(CommonConstantUtils.YZLX_ALERT);
            bdcGzyzVOList.add(bdcGzyzVO);
            return bdcGzyzVOList;
        }
        String userName = userManagerUtils.getCurrentUserName();
        if (glGzyz) {
            registerWorkflowFeignService.updateBdcDbxxAndQsztSyncQj(gzlslid, userName);
            return Collections.emptyList();
        }
        return bdcDbxxFeignService.updateDbxxQsztGzyzAOP(gzlslid, userName);
    }


    /**
     * @param gzlslid 工作流实例ID
     * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 上报
     */
    @PostMapping(value = "/access")
    @ResponseStatus(HttpStatus.OK)
    public void access(@RequestParam(name = "gzlslid") String gzlslid) {
        LOGGER.info("手动登簿触发上报开始，gzlslid为：{}", gzlslid);
        nationalAccessFeignService.autoAccessByProcessInsId(gzlslid);
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 验证是否有临时状态
     */
    @PostMapping(value = "/zs/yzlszt")
    @ResponseStatus(HttpStatus.OK)
    public boolean yzlszt(@RequestBody BdcZsQO bdcZsQO) {
        if (bdcZsQO == null || StringUtils.isBlank(bdcZsQO.getGzlslid())) {
            throw new MissingArgumentException("gzlslid");
        }
        List<BdcZsQsztDTO> bdcZsQsztDTOList = bdcZsFeignService.queryBdcZsQszt(bdcZsQO);
        if (CollectionUtils.isEmpty(bdcZsQsztDTOList)) {
            LOGGER.info("未查询到对应的项目信息,gzlslid:" + bdcZsQO.getGzlslid());
            throw new AppException("未查询到对应的项目信息");
        }
        bdcZsQsztDTOList = bdcZsQsztDTOList.stream().filter(bdcZsQsztDTO -> CommonConstantUtils.QSZT_TEMPORY.equals(bdcZsQsztDTO.getQszt())).collect(Collectors.toList());
        return CollectionUtils.size(bdcZsQsztDTOList) > 0;
    }

    /**
     * @param xmidsJosn 选中的记录数据
     * @return {String} 保存的Redis key
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 证书附表的xmids，保存至Redis中
     */
    @PostMapping("/xmZsyl/fbyl")
    public String saveListXmidsToRedis(@RequestBody String xmidsJosn) {
        String xmids = xmidsJosn.replace("\"","");
        return bdcZsFeignService.saveListXmidsToRedis(xmids);
    }

    /**
     * 根据一个xmid获取到证书相关的所有的项目id
     *
     * @param xmid
     * @return
     */
    @GetMapping(value = "/xmZsyl/getAllXmidByOneXmid/{xmid}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllXmidByOneXmid(@PathVariable(name = "xmid") String xmid) {
        return bdcXmFeignService.queryZsxmList(xmid);
    }

    /**
     * 根据bdcdyh和gzlslid获取当前证书相关的所有xmi拼接的字符串
     *
     * @param bdcXmQO 不动产项目查询QO
     * @param isAllLc 为true时，获取当前gzlslid下的所有的xmid
     * @return 通过一个xmid查询到同证书下的所有的xmid，以英文逗号分隔拼接
     */
    @PostMapping(value = "/zsxx/zsXmidStr")
    @ResponseStatus(HttpStatus.OK)
    public String getZsXmids(@RequestBody BdcXmQO bdcXmQO, @RequestParam(value = "isAllLc") boolean isAllLc) {
        if (isAllLc) {
            return this.getLcXmidsByGzlslid(bdcXmQO.getGzlslid());
        } else {
            return this.getZsXmidsByBdcdyhAndGzlslid(bdcXmQO);
        }
    }

    /**
     * 根据bdcdyh和gzlslid获取当前证书相关的所有xmi拼接的字符串
     *
     * @param bdcXmQO 不动产项目查询QO
     * @return 通过一个xmid查询到同证书下的所有的xmid，以英文逗号分隔拼接
     */
    @PostMapping(value = "/zsxx/zsXmidStr/xmxx")
    public String getZsXmidsByBdcdyhAndGzlslid(@RequestBody BdcXmQO bdcXmQO) {
        String zsQlr = bdcXmQO.getQlr();
        String zsYwr = bdcXmQO.getYwr();

        // 根据qllx和bdcdyh确定一个xmid
        BdcXmQO bdcXmQOTemp = new BdcXmQO();
        bdcXmQOTemp.setGzlslid(bdcXmQO.getGzlslid());
        bdcXmQOTemp.setBdcdyh(bdcXmQO.getBdcdyh());
        bdcXmQOTemp.setQllxs(bdcXmQO.getQllxs());
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQOTemp);
        if (CollectionUtils.isEmpty(bdcXmDOList) || StringUtils.isBlank(bdcXmDOList.get(0).getXmid())) {
            throw new MissingArgumentException("未查询到相关的xmid！");
        }
        String xmidParam = "";
        if (CollectionUtils.size(bdcXmDOList) == 1) {
            xmidParam = bdcXmDOList.get(0).getXmid();
        } else {
            // 同一个单元号，同种qllx下不同流程的组合情况，需要根据qlr和ywr确认xmid（如变更转移组合流程等）
            String[] qlrArr = StringUtils.split(zsQlr, CommonConstantUtils.ZF_KG_CHAR);
            String[] ywrArr = StringUtils.split(zsYwr, CommonConstantUtils.ZF_KG_CHAR);
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                int sameQlrNum = 0;
                int sameYwrNum = 0;
                for (String qlrmc : qlrArr) {
                    if (StringUtils.indexOf(bdcXmDO.getQlr(), qlrmc) <= -1) {
                        sameQlrNum = 0;
                        break;
                    } else {
                        sameQlrNum++;
                    }
                }
                if (Objects.nonNull(ywrArr) && ywrArr.length > 0) {
                    for (String ywrmc : ywrArr) {
                        if (StringUtils.indexOf(bdcXmDO.getYwr(), ywrmc) <= -1) {
                            sameYwrNum = 0;
                            break;
                        } else {
                            sameYwrNum++;
                        }
                    }
                }
                if ((sameQlrNum == qlrArr.length && Objects.isNull(ywrArr)) || (sameQlrNum == qlrArr.length && Objects.nonNull(ywrArr) && sameYwrNum == ywrArr.length)) {
                    xmidParam = bdcXmDO.getXmid();
                    break;
                }
            }

        }
        if (StringUtils.isBlank(xmidParam)) {
            throw new MissingArgumentException("未获取到符合要求的xmid！");
        }
        List<String> zsXmidList = bdcXmFeignService.queryZsxmList(xmidParam);
        return StringUtils.join(zsXmidList, CommonConstantUtils.ZF_YW_DH);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return String 拼接的字符串值
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程所有的xmid的拼接字符串结果
     */
    private String getLcXmidsByGzlslid(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失查询参数Gzlslid！");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        return StringToolUtils.resolveBeanToAppendStr(bdcXmDOList, "getXmid", CommonConstantUtils.ZF_YW_DH);
    }


    /**
     * 转移、变更、更正、换证等手动登簿时注销上一手电子证照
     * @param processInsId
     */
    @PostMapping(value = "/zs/zxDzzz")
    @ResponseStatus(HttpStatus.OK)
    public void zxDzzz(@RequestParam(name = "processInsId") String processInsId,@RequestParam(name = "zsid") String zsid) {
        if(StringUtils.isBlank(processInsId) && StringUtils.isBlank(zsid)){
            throw new AppException("缺失参数！");
        }

        eCertificateFeignService.zxHefeiDzzz(processInsId,zsid);
    }


    /**
     * @param bdcYzhQO 印制号查询对象
     * @return Object 操作结果
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 作废印制号 合肥特有
     */
    @PostMapping(value = "/yzh/zfyzh_hf")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYzhQO", value = "印制号查询QO", dataType = "BdcYzhQO", paramType = "body", required = true)})
    @Transactional(rollbackFor = Exception.class)
    public Object zsZfyzh_hf(@RequestBody BdcYzhQO bdcYzhQO) {
        if (StringUtils.isBlank(bdcYzhQO.getZsid())) {
            throw new MissingArgumentException("缺失证书ID");
        }
        if(StringUtils.endsWith(bdcYzhQO.getQzysxlh(), xnyzhHz)) {
            // 领证方式是电子证照时候，采用虚拟印制号，不走印制号表，点击作废印制号单独处理
            bdcYzhFeignService.updateXnyzhToNull(bdcYzhQO.getZsid());

            // 注销电子证照
            try{
                eCertificateFeignService.zxHefeiDzzz("", bdcYzhQO.getZsid());
            }catch (Exception e){
                LOGGER.error("注销电子证照异常，zsid:{}", bdcYzhQO.getZsid());
            }
            return true;
        }

        // 获取印制号的领取方式，并更新查询参数
        yzhLqfsAndSetValue(bdcYzhQO, null, null);
        BdcYzhDTO bdcYzhDTO = querySingleYzh(bdcYzhQO);
        if (null == bdcYzhDTO) {
            return "未查询到要作废的印制号信息！";
        }

        String zfyy = "证书印制号作废！";
        BdcYzhSyqkQO bdcYzhSyqkQO = new BdcYzhSyqkQO();
        bdcYzhSyqkQO.setSyqk(CommonConstantUtils.SYQK_ZF);
        bdcYzhSyqkQO.setSyyy(zfyy);
        bdcYzhSyqkQO.setYzhid(bdcYzhDTO.getYzhid());
        bdcYzhSyqkQO.setZsid(bdcYzhDTO.getZsid());
        // 清空证书里的印制号
        bdcYzhSyqkQO.setQzysxlh(null);
        BdcYzhsymxDO bdcYzhsymxDO = updateBdcYzhSyqk(bdcYzhSyqkQO);


        if (null == bdcYzhsymxDO) {
            return "更新作废状态失败！";
        } else {
            try{
                eCertificateFeignService.zxHefeiDzzz("", bdcYzhQO.getZsid());
            }catch (Exception e){
                LOGGER.info("注销电子证照异常，zsid:{}",bdcYzhQO.getZsid());
            }
            return true;
        }
    }

    /**
     * 制证
     * @param zsid
     */
    @PostMapping(value = "/zs/createDzzz")
    @ResponseStatus(HttpStatus.OK)
    public Object createDzzz(@RequestParam(name = "zsid") String zsid) {
        if(StringUtils.isBlank(zsid)){
            throw new AppException("缺失参数！");
        }
        String userName = userManagerUtils.getCurrentUserName();
        return eCertificateFeignService.createHefeiDzzz("", zsid, userName,CommonConstantUtils.JDZDMAP.get("dbzz"));
    }

    /**
     * 同步电子证照
     * @param zsid
     */
    @PostMapping(value = "/zs/syncDzzz")
    @ResponseStatus(HttpStatus.OK)
    public void syncDzzz(@RequestParam(name = "zsid") String zsid) {
        String bdcDzzzVersion = bdcDjbxxFeignService.getDzzzVersion();
        //TODO  增加蚌埠版本判断，后期多地市的话，做成开关
        if (!(StringUtils.equals(bdcDzzzVersion, CommonConstantUtils.DZZZ_HEFEI) || StringUtils.equals(bdcDzzzVersion, CommonConstantUtils.DZZZ_BENGBU))) {
            return;
        }
        if (StringUtils.isBlank(zsid)) {
            throw new AppException("缺失参数！");
        }
        BdcZsDO bdcZsDO = bdcZsFeignService.queryBdcZs(zsid);
        if (StringUtils.isNotBlank(bdcZsDO.getZzbs())) {
            try {
                // 先注销
                eCertificateFeignService.zxHefeiDzzz("", zsid);
            } catch (Exception e) {
                throw new AppException("注销电子证照异：" + e.getMessage());
            }

            // 再制证
            String userName = userManagerUtils.getCurrentUserName();
            eCertificateFeignService.createHefeiDzzz("", zsid, userName, CommonConstantUtils.JDZDMAP.get("dbzz"));
        }

    }



    /**
     *制证成功预览图片
     * @param url
     * @return
     * @throws MalformedURLException
     */
    @GetMapping(value = "/yldzzz")
    @ResponseStatus(HttpStatus.OK)
    public String queryBase64ByUrl(@RequestParam(name = "url") String url) throws Exception {
        if(StringUtils.isBlank(url)){
            throw new AppException("缺失参数");
        }
        url = URLDecoder.decode(url,"utf-8");
        LOGGER.info("电子证照预览地址：{}",url);
        url = url.replaceAll("&amp;","&");
        URL neturl = new URL(url);
        return Base64Utils.encodeDzzzImageToBase64(neturl);
    }

    /**
      * @param filterZslx 是否过滤证书类型
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description
      */
    @PostMapping("/judgeZmdType/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public Object judgeZmdType(@PathVariable(value = "gzlslid",required = true) String gzlslid,@RequestParam(name = "filterZslx",required = false) Boolean filterZslx) throws Exception{
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList) || Objects.isNull(bdcXmDOList.get(0))){
            return false;
        }

        if(Boolean.TRUE.equals(filterZslx)){
            //查出证书类型为证明单的
            List<BdcCshFwkgSlDO> bdcCshFwkgSlDOList =bdcXmFeignService.queryFwkgslByGzlslid(gzlslid);
            if(CollectionUtils.isNotEmpty(bdcCshFwkgSlDOList)){
                bdcCshFwkgSlDOList = bdcCshFwkgSlDOList.stream().filter(cshFwkgSlDO -> CommonConstantUtils.ZSLX_ZMD.equals(cshFwkgSlDO.getZszl())).collect(Collectors.toList());
            }
            if(CollectionUtils.isEmpty(bdcCshFwkgSlDOList) || Objects.isNull(bdcCshFwkgSlDOList.get(0))){
                LOGGER.warn("当前流程{}未找到初始化开关实例数据返回false", gzlslid);
                return false;
            }
            if(StringUtils.isNotBlank(bdcCshFwkgSlDOList.get(0).getId())) {
                BdcXmQO xmQO = new BdcXmQO();
                xmQO.setXmid(bdcCshFwkgSlDOList.get(0).getId());
                List<BdcXmDO> bdcXmDOS= bdcXmFeignService.listBdcXm(xmQO);
                if(CollectionUtils.isEmpty(bdcXmDOS)){
                    LOGGER.warn("当前流程{}根据xmid{}未找到项目信息返回false", gzlslid, bdcCshFwkgSlDOList.get(0).getId());
                    return false;
                }
                return checkCkCw(bdcXmDOList);
            }
        }
        return this.checkCkCw(bdcXmDOList);
    }

    /**
     * 判断项目用途是否为车库/车位
     */
    private boolean checkCkCw(List<BdcXmDO> bdcXmDOList) {
        if(CollectionUtils.isEmpty(bdcXmDOList) || Objects.isNull(bdcXmDOList.get(0))){
            return false;
        }
        if (Objects.nonNull(ckcwzmd) && Objects.nonNull(bdcXmDOList.get(0).getGzldyid()) && ckcwzmd.contains(bdcXmDOList.get(0).getGzldyid())) {
            LOGGER.warn("当前流程实例id{}定义id{}配置了不区分用途-直接生成车库车位证明单，返回true", bdcXmDOList.get(0).getGzlslid(), bdcXmDOList.get(0).getGzldyid());
            return true;
        }
        if (StringUtils.isEmpty(ckOrcwZmdDzwyt)) {
            LOGGER.warn("当前流程实例id{}定义id{}未配置车库车位用途，返回false", bdcXmDOList.get(0).getGzlslid(), bdcXmDOList.get(0).getGzldyid());
            return false;
        }
        Boolean result = false;
        List<String> ckOrcwZmdDzwytList = Arrays.asList(ckOrcwZmdDzwyt.split(","));
        for (BdcXmDO bdcXmDO:bdcXmDOList){
            Integer xmDzwyt = bdcXmDO.getDzwyt();
            if(Objects.nonNull(xmDzwyt)){
                if (ckOrcwZmdDzwytList.contains(xmDzwyt.toString())){
                    result = true;
                } else {
                    LOGGER.warn("当前流程实例id{}定义id{}存在定着物用途{}不符合车库车位，false", bdcXmDO.getGzlslid(), bdcXmDO.getGzldyid(), xmDzwyt);
                    return false;
                }
            }
        }
        return result;
    }


    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  判断当前证书是否为车库单独发证,单一车库或车位生成车库/车位证明单
     */
    @PostMapping("/checkZsIsCkzmd")
    @ResponseStatus(HttpStatus.OK)
    public Object checkZsIsCkzmd(@RequestBody BdcZsDO bdcZsDO,@RequestParam(name = "zsyl") Boolean zsyl,@RequestParam(value = "gzlslid",required = true) String gzlslid) throws Exception{
        //获取证书相关项目信息
        List<BdcXmDO> bdcXmDOList =new ArrayList<>();
        if(zsyl){
            BdcXmQO bdcXmQO =new BdcXmQO();
            if(StringUtils.isNotBlank(bdcZsDO.getBdcdyh()) && StringUtils.indexOf(bdcZsDO.getBdcdyh(),CommonConstantUtils.SUFFIX_PL) >-1) {
                bdcZsDO.setBdcdyh(bdcZsDO.getBdcdyh().replace(CommonConstantUtils.SUFFIX_PL,""));
            }
            bdcXmQO.setBdcdyh(bdcZsDO.getBdcdyh());
            List qllxList =new ArrayList();
            qllxList.add(bdcZsDO.getQllx());
            bdcXmQO.setQllxs(qllxList);
            bdcXmQO.setGzlslid(gzlslid);
            bdcXmQO.setQlr(bdcZsDO.getQlr());
            String xmids =getZsXmidsByBdcdyhAndGzlslid(bdcXmQO);
            if(StringUtils.isNotBlank(xmids)){
                List<String> xmidList =Arrays.asList(xmids.split(CommonConstantUtils.ZF_YW_DH));
                if(CollectionUtils.isEmpty(xmidList) || Objects.isNull(xmidList.get(0)) ||CollectionUtils.size(xmidList) != 1){
                    return false;
                }
                bdcXmQO =new BdcXmQO();
                bdcXmQO.setXmid(xmidList.get(0));
                bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
            }
        }else if(StringUtils.isNotBlank(bdcZsDO.getZsid())){
            bdcXmDOList = bdcZsFeignService.queryZsXmByZsid(bdcZsDO.getZsid());
        }
        if(CollectionUtils.isEmpty(bdcXmDOList) || Objects.isNull(bdcXmDOList.get(0)) ||CollectionUtils.size(bdcXmDOList) != 1){
            return false;
        }
        return this.checkCkCw(bdcXmDOList);
    }

    /**
     * @return 是否预览电子证照配置
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 是否预览电子证照配置
     */
    @GetMapping(value = "/zs/sfYlDzzz")
    @ResponseStatus(HttpStatus.OK)
    public String sfYlDzzz() {
        return sfyldzzz;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param processInsId 工作流实例ID
     * @param zsid 证书ID
     * @return {String} 虚拟印制号
     * @description 当领证方式为电子证照时，获取虚拟印制号
     */
    @GetMapping("/yzh/xnyzh")
    public String getXnyzh(@RequestParam(required = false) String processInsId, @RequestParam(required = false) String zsid) {
        if(StringUtils.isBlank(processInsId) && StringUtils.isBlank(zsid)) {
            throw new MissingArgumentException("获取虚拟印制号参数为空");
        }
        return bdcYzhFeignService.getXnyzh(processInsId, zsid);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param processInsId 工作流实例ID
     * @return {String} 领证方式
     * @description 获取领证方式
     */
    @GetMapping("/zs/lzfs")
    public Integer queryBdclzfs(@RequestParam("processInsId") String processInsId) {
        if(StringUtils.isBlank(processInsId)){
            throw new MissingArgumentException("获取领证方式参数为空");
        }

        return bdcLzrFeignService.getLzfsByGzlslid(processInsId);
    }

    /**
     * @author: <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param processInsId 工作流实例ID
     * @param bdcYzhQO 证书证明ID集合
     * @return: {String} 领证方式
     * @description 批量获取虚拟印制号
     */
    @PostMapping("/yzh/xnyzh/batch")
    public List<BdcZsDO> getBatchXnyzh(@RequestParam("processInsId") String processInsId, @RequestBody BdcYzhQO bdcYzhQO) {
        if(StringUtils.isBlank(processInsId) && (null == bdcYzhQO || CollectionUtils.isEmpty(bdcYzhQO.getZsidList()))){
            throw new MissingArgumentException("批量获取虚拟印制号参数为空");
        }
        return bdcYzhFeignService.getBatchXnyzh(processInsId, bdcYzhQO);
    }

    /**
     * 更新 lzfs
     * @param gzlslid
     * @param lzfs
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    @GetMapping("/lzrxx/lzfs")
    public void updateAllLzfsByGzlslid(@RequestParam("gzlslid") String gzlslid, @RequestParam("lzfs") String lzfs) {
        if (StringUtils.isAnyBlank(gzlslid, lzfs)) {
            throw new MissingArgumentException("gzlslid, lzfs");
        }

        bdcLzrFeignService.updateAllLzfsByGzlslid(gzlslid, lzfs);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [gzlslid]
     * @return boolean
     * @description 判断打印按钮是否需要验证缴费状态
     */
    @GetMapping("/needCheckJfzt")
    public boolean needCheckJfzt(@RequestParam("gzlslid") String gzlslid) {
        Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid), "缺少参数：工作流实例ID。");
        LOGGER.info("排除流程为：{},{}", excludeProcessDefKeys, bdcSlSfxmConfig.getNosfxm());
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDTOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (StringUtils.isNotBlank(excludeProcessDefKeys)) {
            String[] excludeProcessDefKeyArr = excludeProcessDefKeys.split(",");
            List<String> excludeProcessDefKeyList = Arrays.asList(excludeProcessDefKeyArr);
            if (CollectionUtils.isEmpty(bdcXmDTOList)) {
                LOGGER.error("未查询到不动产项目！该工作流实例id为：{}", gzlslid);
                throw new AppException("未查询到不动产项目！该工作流实例id为：" + gzlslid);
            }
            if (StringUtils.isNotBlank(bdcXmDTOList.get(0).getGzldyid()) && excludeProcessDefKeyList.contains(bdcXmDTOList.get(0).getGzldyid())) {
                return false;
            }
        }
        if (MapUtils.isNotEmpty(bdcSlSfxmConfig.getNosfxm()) && CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
            if (Objects.equals(CommonConstantUtils.LCLX_PT, lclx) || Objects.equals(CommonConstantUtils.LCLX_PL, lclx)) {
                BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDTOList.get(0).getXmid());
                if (bdcQl instanceof BdcFdcqDO) {
                    BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                    if (MapUtils.getString(bdcSlSfxmConfig.getNosfxm(), String.valueOf(bdcFdcqDO.getFwxz()), StringUtils.EMPTY).contains(bdcXmDTOList.get(0).getGzldyid())) {
                        return false;
                    }
                }
            } else {
                for (BdcXmDO bdcXmDO : bdcXmDTOList) {
                    BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
                    if (bdcQl instanceof BdcFdcqDO) {
                        BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                        if (MapUtils.getString(bdcSlSfxmConfig.getNosfxm(), String.valueOf(bdcFdcqDO.getFwxz()), StringUtils.EMPTY).contains(bdcXmDO.getGzldyid())) {
                            return false;
                        }
                    }
                }
            }

        }
        //特殊流程类业务根据是否分别持证判断，如果不是分别持证，不去验证，如果是分别持证，正常验证
        if (CollectionUtils.isNotEmpty(tssfyzGzldyidList) && tssfyzGzldyidList.contains(bdcXmDTOList.get(0).getGzldyid())) {
            LOGGER.info("特殊流程收费验证根据是否分别持证判断{}", tssfyzGzldyidList);
            if (Objects.equals(CommonConstantUtils.SF_F_DM, bdcXmDTOList.get(0).getSqfbcz())) {
                return false;
            }
        }
        return true;
    }

    @GetMapping("/getSfzt")
    public Object getSfzt(@RequestParam("gzlslid") String gzlslid, @RequestParam("zsid")String zsid) {
        Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid), "缺少参数：工作流实例ID。");
            Map resultMap = new HashMap(2);
        // 判断当前流程是否需要验证收费状态
        boolean sfyzjf = this.needCheckJfzt(gzlslid);
            if (!sfyzjf) {  //不需要验证缴费状态直接返回true
            resultMap.put("sfzt", true);
            resultMap.put("dyzt", true);
            return resultMap;
        }

        boolean sfzt = false;
        boolean dyzt = false;
        // 获取收费信息，存在证书id时，根据 zsid 查询收费信息。不存在时，根据工作流实例ID，查询收费信息
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.getBdcSlSfxx(gzlslid, zsid);
        if(CollectionUtils.isNotEmpty(bdcSlSfxxDOList)){
            /**
             * 1、根据配置验证流程是否验证发票号
             * 2、当流程需要验证发票号时，在判断收费项目为空，收费hj金额为空或0，备注不为空时，打印 zszm 时，不验证发票号
             */
            boolean sfyzFph = this.sfyzFph(gzlslid, bdcSlSfxxDOList);

            // 收费状态为已缴费，并且发票号不为空时，可以打印 zszm
            List<BdcSlSfxxDO> notSfList = bdcSlSfxxDOList.stream()
                    .filter(t-> !Objects.equals(BdcSfztEnum.YJF.key(), t.getSfzt())).collect(Collectors.toList());
            // 存在收费状态为未缴费情况，dyzt 和 sfzt 都为 false

            if(CollectionUtils.isEmpty(notSfList)){
                sfzt = true;
                if(sfyzFph){
                    for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                        if (StringUtils.isNotBlank(bdcSlSfxxDO.getFph())) {
                            dyzt = true;
                        }else{
                            dyzt = false;
                            break;
                        }
                    }
                }else{
                    dyzt = true;
                }
            }
        }

        resultMap.put("sfzt", sfzt);
        resultMap.put("dyzt", dyzt);
        return resultMap;
    }

    /**
     * 获取收费信息，存在证书id时，根据 zsid 查询收费信息。不存在时，根据工作流实例ID，查询收费信息
     */
    private List<BdcSlSfxxDO> getBdcSlSfxx(String gzlslid, String zsid){
        int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
        if(CommonConstantUtils.LCLX_ZH.equals(lclx) || CommonConstantUtils.LCLX_PLZH.equals(lclx)){
            if(StringUtils.isNotBlank(zsid)){
                List<BdcXmDO> bdcXmDOList = this.bdcZsFeignService.queryZsXmByZsid(zsid);
                if(CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    String zsXmDjxl = bdcXmDOList.get(0).getDjxl();
                    List<BdcSlSfxxDO> filterSfxxList = new ArrayList<>(bdcSlSfxxDOList.size());
                    for(BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList){
                        String djxl = this.getDjxl(bdcSlSfxxDO.getXmid());
                        if(zsXmDjxl.equals(djxl)){
                            filterSfxxList.add(bdcSlSfxxDO);
                        }
                    }
                    if(CollectionUtils.isNotEmpty(filterSfxxList)){
                        bdcSlSfxxDOList = filterSfxxList;
                    }
                }
            }
        }
        return bdcSlSfxxDOList;
    }

    /**
     * 判断当前流程是否验证发票号
     */
    private boolean sfyzFph(String gzlslid, List<BdcSlSfxxDO> bdcSlSfxxDOList){
        if(StringUtils.isNotBlank(gzlslid) && CollectionUtils.isNotEmpty(dyzszmNoFphGzldyids)){
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDTOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDTOList)
                    && dyzszmNoFphGzldyids.contains(bdcXmDTOList.get(0).getGzldyid())){
                return false;
            }
        }
        // 当流程需要验证发票号时，在判断收费项目为空，收费hj金额为空或0，备注不为空时，打印 zszm 时，不验证发票号
        boolean sfyzFph = true;
        for(BdcSlSfxxDO sfxx : bdcSlSfxxDOList){
            boolean noHj = (Objects.isNull(sfxx.getHj()) || sfxx.getHj().equals((double) 0));
            boolean sfyj = this.checkSfyj(sfxx.getJfrxm());
            if(sfyj || (noHj && StringUtils.isNotBlank(sfxx.getBz()))){
                sfyzFph = false;
            }else{
                sfyzFph = true;
                break;
            }
        }
        return sfyzFph;
    }

    private boolean checkSfyj(String jfrmc){
        if(StringUtils.isNotBlank(jfrmc)){
            List<BdcXtJgDO> bdcXtJgDOList = this.bdcXtJgFeignService.listAyjsBdcXtJgYhmc(jfrmc);
            if(CollectionUtils.isNotEmpty(bdcXtJgDOList)){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取登记小类
     */
    private String getDjxl(String xmid){
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> xmxxList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(xmxxList)){
            return xmxxList.get(0).getDjxl();
        }
        return "";
    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 判断抵押权人是否协议机构
      */
    @GetMapping("/sfxyjg")
    public Integer getSfxyjg(@RequestParam("zsid") String zsid){

        if(StringUtils.isNotBlank(zsid)){
            List<BdcXmDO> bdcXmDOList = bdcZsFeignService.queryZsXmByZsid(zsid);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                BdcQlrQO bdcQlrQO =new BdcQlrQO();
                bdcQlrQO.setXmid(bdcXmDOList.get(0).getXmid());
                bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                List<BdcQlrDO> bdcQlrDOList =bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
                    for(BdcQlrDO bdcQlrDO:bdcQlrDOList){
                        Integer sfxyjg =querySfxyjgByQlr(bdcQlrDO.getQlrmc());
                        if(CommonConstantUtils.SF_F_DM.equals(sfxyjg)){
                            return sfxyjg;
                        }
                    }
                    return CommonConstantUtils.SF_S_DM;
                }

            }
        }
        return null;
    }

    /**
     * 获取银行是否协议机构
     */
    private Integer querySfxyjgByQlr(String qlr){
        if(StringUtils.isNotBlank(qlr)){
            BdcXtJgDO bdcXtJgDO = new BdcXtJgDO();
            bdcXtJgDO.setSfxyjg(CommonConstantUtils.SF_S_DM);
            bdcXtJgDO.setJgmc(qlr);
            List<BdcXtJgDO> bdcXtJgDOList = bdcXtJgFeignService.queryBdcXtJg(bdcXtJgDO);
            if(CollectionUtils.isNotEmpty(bdcXtJgDOList)){
                return CommonConstantUtils.SF_S_DM;
            }else{
                return CommonConstantUtils.SF_F_DM;
            }
        }
        return null;
    }


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 解密户室图二维码
     * @date : 2020/12/24 16:01
     */
    @GetMapping("/zs/print/hst/jm")
    public void jmHstEwm(@RequestParam("bdcdyh") String bdcdyh,
                         @RequestParam(name = "qjgldm", required = false) String qjgldm,
                         HttpServletResponse httpServletResponse) {
        logger.info("证书二维码单元号{}", bdcdyh);
        String jmbdcdyh = DESUtils.decrypt(bdcdyh);
        logger.info("解密单元号{}", jmbdcdyh);
        zdt("hst", jmbdcdyh, null, qjgldm, httpServletResponse);
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 证书信息校验
     * @date : 2021/8/2 11:13
     */
    @GetMapping("/jyzsxx")
    public Object jyZsxx(BdcZsQO bdcZsQO) throws Exception {
        return  bdcZsFeignService.initJyZsxx(bdcZsQO);
    }

    /**
     * @param jgmc 机构名称
     * @return 机构配置
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 获取机构配置信息
     */
    @GetMapping(value = "/zsxx/xtjg/{jgmc}")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "jgmc", value = "机构名称", dataType = "string", paramType = "path", required = true)})
    public BdcXtJgDO queryXtJg(@PathVariable(name = "jgmc") String jgmc) {
        return this.bdcXtJgFeignService.queryJgByJgmc(jgmc,null);
    }

    /**
     * @param
     * @author <a href="mailto:wutao2@gtmap.cn">wutao</a>
     * @description 校验领证方式
     * @date : 2022/5/12 16:01
     */
    @PostMapping("/zs/jylzfs")
    @ResponseStatus(HttpStatus.OK)
    public String jylzfs(@RequestBody List<String> zsidList , @RequestParam("gzlslid") String gzlslid) {
        if(CollectionUtils.isEmpty(zsidList) && StringUtil.isBlank(gzlslid)){
            throw new MissingArgumentException("获取领证方式参数为空");
        }
        StringBuilder warningMessage = new StringBuilder();
        // 如果需要校验领取方式，才去查询
        if(sfjylzfs){
            // 如果传了证书id，则根据证书id去查询，否则根据工作流实例id全部
            if(CollectionUtils.isNotEmpty(zsidList)){
                for(String zsid : zsidList){
                    BdcLzxxDTO bdcLzxxDTO = bdcLzrFeignService.getLzfsByZsid(zsid);
                    if (null == bdcLzxxDTO || null == bdcLzxxDTO.getLzfs()) {
                        throw new AppException("证书id:"+zsid+"未查询到领证方式");
                    }
                    if (CommonConstantUtils.LZFS_ZZDZ.equals(bdcLzxxDTO.getLzfs())) {
                        warningMessage.append(bdcLzxxDTO.getBdcqzh() + "领证方式为“自助打证机”，无需打证，请核实！").append("<br>");
                    }
                    if (CommonConstantUtils.LZFS_DZZZ.equals(bdcLzxxDTO.getLzfs())) {
                        warningMessage.append(bdcLzxxDTO.getBdcqzh() + "领证方式为“电子证照”，无需打证，请核实！").append("<br>");
                    }
                }
            }else if(StringUtil.isNotBlank(gzlslid)){
                List<BdcLzxxDTO> listBdcLzxxDTO = bdcLzrFeignService.getAllLzfsByGzlslid(gzlslid);
                if (CollectionUtils.isNotEmpty(listBdcLzxxDTO)) {
                    for(BdcLzxxDTO bdcLzxxDTO : listBdcLzxxDTO){
                        if (CommonConstantUtils.LZFS_ZZDZ.equals(bdcLzxxDTO.getLzfs())) {
                            warningMessage.append(bdcLzxxDTO.getBdcqzh() + "领证方式为“自助打证机”，无需打证，请核实！").append("<br>");
                        }
                        if (CommonConstantUtils.LZFS_DZZZ.equals(bdcLzxxDTO.getLzfs())) {
                            warningMessage.append(bdcLzxxDTO.getBdcqzh() + "领证方式为“电子证照”，无需打证，请核实！").append("<br>");
                        }
                    }
                }else {
                    throw new AppException("工作流实例id:"+gzlslid+"未查询到领证方式");
                }
            }
        }
        return warningMessage + "";
    }

    /**
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @return {String} 虚拟印制号后缀
     * @description 获取虚拟印制号后缀
     */
    @GetMapping("/yzh/xnyzhhz")
    public String getXnyzhhz() {
        String result = "";
        if (StringUtils.isNotBlank(xnyzhHz)) {
            result = xnyzhHz;
        }
        return result;
    }

    /**
     * 过滤生成证书的项目
     *
     * <p>
     * 说明：
     * 1、解决问题
     * 例如：房屋所有权首次登记（业主共有部分）流程可能项目表2条记录，一个qllx=4，一个qllx=94，
     * 其中qllx=4生成证明单，qllx=94不生成，如果按照原有代码直接取xmList.get(0)可能获取到94的这个记录，导致预览证书、查看证书页面查询错误。
     * 2、注意点
     * 如果是生成多本证，那么页面会展示列表，查看单个时候会传xmid或者zsid数据；如果是所有项目生成一本证，那么按照直接取第一条也不会有问题，这个
     * 主要解决多个项目但是部分项目生成证书且生成一本的情况下预览、查看证书（证明、证明单）时候异常。
     *<p>
     *
     * @param bdcXmDOList 项目集合
     * @return {BdcXmDO} 生成证书的项目之一
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private BdcXmDO getSczsXm(List<BdcXmDO> bdcXmDOList) {
        if(CollectionUtils.isEmpty(bdcXmDOList)) {
            return null;
        }

        for(BdcXmDO bdcXmDO : bdcXmDOList) {
            BdcCshFwkgSlDO fwkgSlDO = bdcXmFeignService.queryFwkgsl(bdcXmDO.getXmid());
            if(null != fwkgSlDO && CommonConstantUtils.SF_S_DM.equals(fwkgSlDO.getSfsczs())) {
                return bdcXmDO;
            }
        }
        return bdcXmDOList.get(0);
    }
    /**
     * @author <a href="mailto:hongqin@gtmap.cn">hongqin</a>
     * @params [zsidList, gzlslid, xmid, response, excelExportDTO]
     * @return void
     * @description 查询证书清单数据一次性全部导出Excel
     */
    @PostMapping(value = "/zsxx/exportZsqd")
    public void exportZsqd(@RequestParam List<String> zsidList , @RequestParam("gzlslid") String gzlslid,HttpServletResponse response,@ModelAttribute ExcelExportDTO excelExportDTO) {
        List<BdcZsqdVO> resultList = new ArrayList<>();
        if(CollectionUtils.isEmpty(zsidList) && StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("未查询到证书清单！");
        }
        resultList = bdcZsFeignService.queryZsQdByZsid(zsidList,gzlslid);
        if(CollectionUtils.isEmpty(resultList)){
            throw new EntityNotFoundException("未查询到证书清单！");
        }
        excelExportDTO.setData(JSON.toJSONString(resultList));
        excelController.exportExcel(response,excelExportDTO);
    }
}