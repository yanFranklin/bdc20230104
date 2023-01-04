package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.dto.inquiry.nantong.BdcPrintParamDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.nantong.BdcPrintParamQO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZfxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcNtFczmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZfxxCxFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.inquiry.ui.util.InquiryUIConstants;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/11/2
 * @description （南通）房产证明打印相关处理逻辑
 */
@RestController
@RequestMapping("/rest/v1.0")
public class BdcNtFczmController extends BaseController {
    /**
     * 格式化日期
     */
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");
    /**
     * 房产证明处理服务
     */
    @Autowired
    private BdcNtFczmFeignService bdcNtFczmFeignService;
    /**
     * 编号服务接口
     */
    @Autowired
    private BdcBhFeignService bdcBhFeignService;
    /**
     * 住房信息查询
     */
    @Autowired
    private BdcZfxxCxFeignService bdcZfxxCxFeignService;
    /**
     * 人员信息
     */
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;

    /**
     * 审核登簿服务地址
     */
    @Value("${url.register-ui}")
    private String registerUi;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param fczm  查询人信息
     * @return {String} Redis key
     * @description （南通）保存有房无房证明打印需要的部分信息
     */
    @PostMapping("/zhcx/nantong/yfwfzm")
    public String saveNtYfwfzmXx(@RequestBody BdcFczmDTO fczm){
        if(null == fczm){
            throw  new NullPointerException("查询子系统：查询房产证明参数为空");
        }

        BdcFczmDTO bdcFczmDTO;

        if(CommonConstantUtils.QHKZM.equals(fczm.getType())) {
            // 迁户口证明
            bdcFczmDTO = this.getQhkzmDTO();
        } else {
            // 有房无房证明
            bdcFczmDTO = this.getFczmDTO();
        }

        bdcFczmDTO.setCxsqr(fczm.getCxsqr());
        bdcFczmDTO.setSfzh(fczm.getSfzh());
        bdcFczmDTO.setType(fczm.getType());
        bdcFczmDTO.setCxsd(fczm.getCxsd());//有房无房、有房无房（企业）模板中的查询时点；
        bdcFczmDTO.setDyldTime(fczm.getCxsd());//打印留档日志保存时间；
        return bdcNtFczmFeignService.saveNtFcdaXx(bdcFczmDTO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param qlrxx  权利人信息
     * @return {String} 有房 YF; 无房 WF；
     * @description 查询是否有房
     */
    @PostMapping("/zhcx/nantong/yfwfzm/zfxx")
    public String getZfxx(@RequestBody List<BdcQlrQO> qlrxx, @RequestParam(value = "moduleCode", required = false)String moduleCode){
        if(CollectionUtils.isEmpty(qlrxx)){
            throw  new NullPointerException("查询子系统：查询房产信息参数为空");
        }

        BdcZfxxQO bdcZfxxQO = new BdcZfxxQO();
        bdcZfxxQO.setQlrxx(qlrxx);
        bdcZfxxQO.setCxly("1");
        bdcZfxxQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("zhcx","", moduleCode));
        List<BdcZfxxDTO> zfxxDTOList = bdcZfxxCxFeignService.listBdcNtZfxxDTO(bdcZfxxQO);

        if(CollectionUtils.isEmpty(zfxxDTOList)){
            return "WF";
        } else {
            return "YF";
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param qlrxx  权利人信息
     * @return {String} 是 Y; 否 N；
     * @description 根据权利人证件号查询房产信息，判断权利人名称是否多个人名拼接（需求：26020）
     */
    @PostMapping("/zhcx/nantong/yfwfzm/zfxx/qlrxx")
    public String getZfxxQlrxx(@RequestBody List<BdcQlrQO> qlrxx){
        if(CollectionUtils.isEmpty(qlrxx)){
            return "N";
        }

        BdcZfxxQO bdcZfxxQO = new BdcZfxxQO();
        bdcZfxxQO.setQlrxx(qlrxx);
        bdcZfxxQO.setCxly("1");
        List<BdcZfxxDTO> zfxxDTOList = bdcZfxxCxFeignService.listBdcNtZfxxDTO(bdcZfxxQO);
        if(CollectionUtils.isEmpty(zfxxDTOList)){
            return "N";
        }

        for(BdcZfxxDTO bdcZfxxDTO : zfxxDTOList){
            if(null != bdcZfxxDTO){
                String qlrmc = bdcZfxxDTO.getQlrmc();
                if(StringToolUtils.containsTargetStr(qlrmc, ",", "，", "、")){
                    return "Y";
                }
            }
        }
        return "N";
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} FR3打印的XML数据
     * @description 获取（南通）有房无房证明打印信息
     */
    @GetMapping("/print/nantong/yfwfzm/{key}/xml")
    public String getNtYfwfzmXml(@PathVariable("key")String key){
        if(StringUtils.isBlank(key)){
            throw  new NullPointerException("查询子系统：有房无房证明打印参数为空");
        }
        return bdcNtFczmFeignService.getNtYfwfzmXml(key);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFczmDTO 房产证明信息
     * @return {String} redis key
     * @description 保存（南通）房产证明信息至缓存
     */
    @PostMapping("/print/nantong/fcda")
    public String saveNtFcdaXx(@RequestBody BdcFczmDTO bdcFczmDTO){
        if(null == bdcFczmDTO || StringUtils.isBlank(bdcFczmDTO.getBh())){
            throw  new NullPointerException("查询子系统：查询房产证明参数为空");
        }
        return bdcNtFczmFeignService.saveNtFcdaXx(bdcFczmDTO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFczmDTOList 房产证明信息
     * @return {String} redis key
     * @description 批量保存（南通）房产证明信息至缓存
     */
    @PostMapping("/print/nantong/fcda/pl")
    public String saveNtFcdaXxPl(@RequestBody List<BdcFczmDTO> bdcFczmDTOList){
        if(CollectionUtils.isEmpty(bdcFczmDTOList)){
            throw  new NullPointerException("查询子系统：查询房产证明参数为空");
        }
        return bdcNtFczmFeignService.saveNtFcdaXxPl(bdcFczmDTOList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} FR3打印的XML数据
     * @description 获取（南通）房产证明打印信息
     */
    @GetMapping("/print/nt/fczm/{key}/xml")
    public String getNtFczmXml(@PathVariable("key")String key){
        if(StringUtils.isBlank(key)){
            throw  new NullPointerException("查询子系统：房产证明打印参数为空");
        }
        return bdcNtFczmFeignService.getNtFczmXml(key);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} FR3打印的XML数据
     * @description 获取（南通）房产证明批量打印信息
     */
    @GetMapping("/print/nt/fczm/pl/{key}/xml")
    public String getNtFczmPlXml(@PathVariable("key")String key){
        if(StringUtils.isBlank(key)){
            throw  new NullPointerException("查询子系统：房产证明打印参数为空");
        }
        return bdcNtFczmFeignService.getNtFczmPlXml(key);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param list 打印请求参数
     * @return {BdcQszmDyDTO} 缓存的Redis KEY以及查询号信息
     * @description 缓存权属证明不动产单元号到redis
     */
    @PostMapping("/print/nantong/qszm/bdcdyh")
    public BdcQszmDyDTO saveBdcFcdaBdcdyhToRedis(@RequestBody List<BdcQszmQO> list) {
        if (CollectionUtils.isEmpty(list)) {
            throw new NullPointerException("查询子系统：打印权属证明参数为空！");
        }
        return bdcNtFczmFeignService.saveNtFcdaBdcdyhToRedis(list);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} XML数据
     * @description 获取打印权属证明的XML数据
     */
    @GetMapping("/print/nantong/qszm/{key}/xml")
    public String getPrintXmlOfQszm(@PathVariable("key") String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("查询子系统：打印权属证明参数为空");
        }
        return bdcNtFczmFeignService.getPrintXmlOfQszm(key);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcDyzmDTOList 选中的记录数据
     * @return {String} 保存的Redis key
     * @description （南通）将要打印的抵押证明参数信息保存至Redis中
     */
    @PostMapping("/print/nantong/dyzm/csxx")
    public BdcDyzmDyDTO saveDyzmCsxxToRedis(@RequestBody List<BdcDyzmDTO> bdcDyzmDTOList) {
        if (CollectionUtils.isEmpty(bdcDyzmDTOList)) {
            throw new NullPointerException("查询子系统：出具抵押抵押证明未选中要导出数据");
        }
        return bdcNtFczmFeignService.saveDyzmCsxxToRedis(bdcDyzmDTOList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 不动产单元号对应Redis键
     * @return {String} XML数据
     * @description （南通）获取抵押查封证明打印的XML数据
     */
    @GetMapping("/print/nantong/dyzm/{key}/xml")
    public String getPrintXmlOfDyzm(@PathVariable("key") String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("查询子系统：出具抵押证明未选中要导出数据");
        }
        return bdcNtFczmFeignService.getPrintXmlOfDyzm(key);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {BdcFczmDTO} 房产证明信息
     * @description 获取房产证明编号信息
     */
    @GetMapping("/zhcx/nantong/fczm")
    public BdcFczmDTO getFczmDTO(){
        return this.getFczmParam(InquiryUIConstants.BDC_NT_ZSZM_BH_XLH, "有房无房证明");
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {BdcFczmDTO} 房产证明信息
     * @description 获取迁户口证明编号信息
     */
    private BdcFczmDTO getQhkzmDTO(){
        return this.getFczmParam(InquiryUIConstants.BDC_HM_QHKZM_BH_XLH, "迁户口证明");
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {BdcFczmDTO} 房产证明参数信息
     * @description 获取证明编号信息
     */
    private BdcFczmDTO getFczmParam(String type, String typeInfo) {
        // 编号序列号
        Integer bhxlh = bdcBhFeignService.queryLsh(type, InquiryUIConstants.DAY);

        BdcFczmDTO bdcFczmDTO = new BdcFczmDTO();
        bdcFczmDTO.setBh(dateTimeFormatter.format(LocalDateTime.now()) + StringToolUtils.appendZero(String.valueOf(bhxlh), 6));
        bdcFczmDTO.setZmsj(StringToolUtils.getChineseDate());

        // 获取经办人信息即当前用户
        try {
            UserDto userDto = userManagerUtils.getCurrentUser();
            if(null != userDto && StringUtils.isNotBlank(userDto.getAlias())) {
                bdcFczmDTO.setJbr(userDto.getAlias());
            }
        } catch (Exception e) {
            LOGGER.error("{}打印获取经办人信息错误，对应打印编号：{}", typeInfo, bdcFczmDTO.getBh());
        }

        return bdcFczmDTO;
    }

    /**
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @param xmid 项目ID
     * @return {String} 房产交易价格
     * @description 获取房产交易价格
     */
    @GetMapping("/zhcx/nantong/fczm/fdcq/jyjg")
    public Double getFdcqJyjg(@RequestParam("xmid") String xmid){
        if(StringUtils.isBlank(xmid)){
            return null;
        }
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
        if (bdcQl instanceof BdcFdcqDO){
            return ((BdcFdcqDO) bdcQl).getJyjg();
        }

        return null;
    }


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmid 项目ID
     * @return {String} 房产出售时间
     * @description 获取房产出售时间
     */
    @GetMapping("/zhcx/nantong/fczm/fdcq/cssj")
    public String getFdcqCssj(@RequestParam("xmid") String xmid){
        return bdcNtFczmFeignService.getFdcqCssj(xmid);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {String} 用户信息
     * @description 获取当前用户名称（不在后台打印时候获取，避免因为忽略权限而获取不到用户信息）
     */
    @GetMapping("/zhcx/nantong/fczm/userinfo")
    public String getUserName(){
        String userName = "";
        try{
            userName = userManagerUtils.getCurrentUser().getAlias();
        } catch (Exception e){
            LOGGER.error("不动产查询子系统：获取当前经办人信息出错{}", e.getMessage());
        }
        return userName;
    }

    /**
     * @param bdcdyh 不动产单元号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/8/21 9:44
     */
    @GetMapping("zhcx/nantong/blzt/sfzzbl")
    public Object queryZzbl (String bdcdyh, String xmid) {
        if(StringUtils.isBlank(bdcdyh)) {
            throw new AppException("获取不动产单元是否正在办理缺失对应的不动产单元号");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setBdcdyh(bdcdyh);
        bdcXmQO.setAjzt(CommonConstantUtils.AJZT_ZB_DM);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList) && StringUtils.isNotBlank(xmid)) {
            /**
             * @param bdcdyh
             * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
             * @description 增加判断逻辑，如果存在被关联为外联证书，如果外联证书的主数据正在办理，那么该条数据也显示正在办理
             * @date : 2020/9/18 10:49
             */
            //根据xmid找历史关系且是外联证书的数据
            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
            bdcXmLsgxQO.setYxmid(xmid);
            bdcXmLsgxQO.setWlxm(1);
            List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
            if(CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
                bdcXmQO = new BdcXmQO(bdcXmLsgxDOList.get(0).getXmid());
                bdcXmQO.setAjzt(CommonConstantUtils.AJZT_ZB_DM);
                bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            }
        }
        return bdcXmDOList.size();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcPrintParamDTO 打印请求参数
     * @return {BdcQszmDyDTO} 缓存的Redis KEY以及查询号信息
     * @description 缓存打印利害关系人、律师查询参数到redis
     */
    @PostMapping("/print/nantong/lhgxrlscx/param")
    public BdcPrintParamQO saveLhgxrLscxPrintParam(@RequestBody BdcPrintParamDTO bdcPrintParamDTO) {
        if (null == bdcPrintParamDTO || CollectionUtils.isEmpty(bdcPrintParamDTO.getXmids())) {
            throw new NullPointerException("查询子系统：打印利害关系人、律师查询参数为空！");
        }

        bdcPrintParamDTO.setRegisterUi(registerUi);
        return bdcNtFczmFeignService.saveLhgxrLscxPrintParam(bdcPrintParamDTO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 参数缓存Redis键值
     * @return {String} XML数据
     * @description （南通）获取打印利害关系人、律师查询的XML数据
     */
    @GetMapping("/print/nantong/lhgxrlscx/{key}/xml")
    public String getPrintXmlOfLhgxrLscx(@PathVariable("key") String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("查询子系统：出具抵押证明未选中要导出数据");
        }
        return bdcNtFczmFeignService.getPrintXmlOfLhgxrLscx(key);
    }
}
