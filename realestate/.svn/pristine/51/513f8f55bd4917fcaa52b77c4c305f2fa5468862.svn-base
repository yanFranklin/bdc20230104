package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxCsDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZszmDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.nantong.BdcPrintParamQO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcDyqdDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFczmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZfxxQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcFczmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcRedisFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZfxxCxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/11/2
 * @description  房产证明打印相关处理逻辑
 */
@RestController
@RequestMapping("/rest/v1.0")
public class BdcFczmController extends BaseController {
    /**
     * 审核登簿服务地址
     */
    @Value("${url.register-ui}")
    protected String registerUi;

    /**
     * 打印服务接口
     */
    @Autowired
    private BdcPrintFeignService bdcPrintFeignService;
    /**
     * 房产证明服务
     */
    @Autowired
    private BdcFczmFeignService bdcFczmFeignService;
    /**
     * 房产信息查询接口
     */
    @Autowired
    private BdcZfxxCxFeignService bdcZfxxCxFeignService;
    /**
     * Redis调用服务
     */
    @Autowired
    private BdcRedisFeignService bdcRedisFeignService;
    /**
     * 用户服务
     */
    @Autowired
    private UserManagerUtils userManagerUtils;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZszmDTOList 选中的记录数据
     * @return {String} 保存的Redis key
     * @description 根据已选要打印抵押查封证明的产权信息，获取对应的不动产单元，保存至Redis中
     */
    @PostMapping("/print/dyacfzm/bdcdyh")
    public String saveDyacfzmBdcdyhToRedis(@RequestBody List<BdcZszmDTO> bdcZszmDTOList) {
        if (CollectionUtils.isEmpty(bdcZszmDTOList)) {
            throw new NullPointerException("查询子系统：出具抵押查封证明未选中要导出数据");
        }
        return bdcFczmFeignService.getBdcdyhRedisKeyOfDyacfzm(bdcZszmDTOList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 不动产单元号对应Redis键
     * @return {String} XML数据
     * @description 获取抵押查封证明打印的XML数据
     */
    @GetMapping("/print/dyacfzm/{key}/xml")
    public String getPrintXmlOfDyacfzm(@PathVariable("key") String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("查询子系统：出具抵押查封证明未选中要导出数据");
        }
        return bdcFczmFeignService.getPrintXmlOfDyacfzm(key);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZfxxCsDTO 参数
     * @return {String} 缓存KEY值
     * @description 将住房查询证明请求的参数--权利人信息缓存到Redis（因为数据可能很多，直接GET传fr3可能出现问题）
     */
    @PostMapping("/print/zfxx/qlrxx")
    public String saveZfxxQlrxxToRedis(@RequestBody BdcZfxxCsDTO bdcZfxxCsDTO) {
        if (null == bdcZfxxCsDTO || CollectionUtils.isEmpty(bdcZfxxCsDTO.getQlrQOList())) {
            throw new NullPointerException("查询子系统：住房查询证明打印请求参数为空！");
        }

        bdcZfxxCsDTO.setEwmurl(registerUi + "/rest/v1.0/print/ewm/");
        return bdcFczmFeignService.saveZfxxQlrxxToRedis(bdcZfxxCsDTO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 权利人信息Redis键
     * @return {String} XML数据
     * @description 获取打印住房查询证明的XML数据
     */
    @GetMapping("/print/zfxx/{key}/xml")
    public String getPrintXmlOfZfxx(@PathVariable("key") String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("查询子系统：住房查询证明打印请求参数为空");
        }
        return bdcFczmFeignService.getPrintXmlOfZfxx(key);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} XML数据
     * @description 获取打印房产档案的XML数据
     */
    @GetMapping("/print/zfxx/fcda/{key}/xml")
    public String getPrintXmlOfZfxxFcda(@PathVariable("key") String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("查询子系统：打印房产档案请求参数为空");
        }
        return bdcFczmFeignService.getPrintXmlOfFcda(key);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param list [{"bdcdyh":"1","qszt":"1","gzlslid":"1"},{"bdcdyh":"1","qszt":"1","gzlslid":"1"}]
     * @return {String} redis key
     * @description 缓存房产档案不动产单元号到redis
     */
    @PostMapping("/zfxx/fcda/bdcdyh")
    public String saveBdcFcdaBdcdyhToRedis(@RequestBody List<BdcFczmQO> list) {
        if (CollectionUtils.isEmpty(list)) {
            throw new NullPointerException("查询子系统：获取房产档案信息参数为空！");
        }
        return bdcFczmFeignService.saveBdcFcdaBdcdyhToRedis(list);
    }

    /**
     * @param key 查询参数
     * @return String xml结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取查询申请书的打印xml
     */
    @GetMapping("/print/cxsqs/{key}/xml")
    public String getPrintXmlOfCxsqs(@PathVariable("key") String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("查询子系统：查询申请书打印请求参数为空");
        }
        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> sqsidListMap = new ArrayList<>(1);
        Map<String, Object> map = new HashMap<>(1);
        map.put("sqsid", key);
        sqsidListMap.add(map);
        // 打印模板：bdcCxsqs.fr3
        paramMap.put("bdcCxsqs", sqsidListMap);

        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * 获取证书附表打印xml 用的是证书附表预览模板和数据源 zsfbyl
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @param
     * @return
     */
    @GetMapping("/print/fyOrDyawqd/xml")
    public String getZsfbXml(@RequestParam("gzlslid") String gzlslid,
                             @RequestParam("dylx")String dylx){
        if(StringUtils.isBlank(gzlslid) ){
            throw new MissingArgumentException("打印证书附表缺失参数！");
        }
        // 封装参数
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        List<Map> bdcdyhListMap = new ArrayList<>(1);
        Map<String, Object> map = new HashMap<>(1);
        map.put("gzlslid", gzlslid);
        bdcdyhListMap.add(map);

        if(ArrayUtils.contains(CommonConstantUtils.Sl_DJ_DYLX,dylx)){
            // 打印模板：bdcdyList.fr3  配置数据源类型 zsfbyl
            paramMap.put("bdcdyList", bdcdyhListMap);
        }
        if(CommonConstantUtils.DYWQD_DYLX.equals(dylx)){
            paramMap.put("dyawqd",bdcdyhListMap);
        }

        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param qlrxx  权利人信息
     * @return {List}  房产信息
     * @description 查询指定权利人对应的房产信息
     */
    @PostMapping("/zhcx/yfwfzm/zfxx")
    public List<BdcZfxxDTO> getZfxx(@RequestBody List<BdcQlrQO> qlrxx){
        if(CollectionUtils.isEmpty(qlrxx)){
            throw  new NullPointerException("查询子系统：查询房产信息权利人参数为空");
        }

        BdcZfxxQO bdcZfxxQO = new BdcZfxxQO();
        bdcZfxxQO.setQlrxx(qlrxx);
        bdcZfxxQO.setCxly("1");
        return bdcZfxxCxFeignService.listBdcNtZfxxDTO(bdcZfxxQO);
    }

    @PostMapping("/zhcx/yfwfzm/excelSearchPrint")
    public List<BdcZfxxDTO> excelSearchPrint(@RequestBody List<BdcQlrQO> qlrxx){
        if(CollectionUtils.isEmpty(qlrxx)){
            throw  new NullPointerException("查询子系统：查询房产信息权利人参数为空");
        }
        BdcZfxxQO bdcZfxxQO = new BdcZfxxQO();
        bdcZfxxQO.setQlrxx(qlrxx);
        bdcZfxxQO.setCxly("1");
        bdcZfxxQO.setSfghyt("N");
        return bdcZfxxCxFeignService.listBdcNtZfxxDTO(bdcZfxxQO);
    }

    /**
     * 盐城综合查询打印清单缓存选择记录数据
     * @param bdcDyqdDTOList 勾选数据
     * @return reids缓存Key
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/zhcx/dyqd/key")
    public String saveZhcxDyqdDataToRedis(@RequestBody List<BdcDyqdDTO> bdcDyqdDTOList) {
        if(CollectionUtils.isEmpty(bdcDyqdDTOList)) {
            throw new AppException("未定义参数");
        }

        String key = CommonConstantUtils.YC_ZHCX_DYQD_SJ + UUIDGenerator.generate16();
        bdcRedisFeignService.addHashValue(key, "data", JSON.toJSONString(bdcDyqdDTOList), Long.valueOf("600"));

        UserDto userDto = userManagerUtils.getCurrentUser();
        if(null != userDto) {
            bdcRedisFeignService.addHashValue(key, "useralias", userDto.getAlias(), Long.valueOf("600"));
        }
        return key;
    }

    /**
     * 获取盐城打印清单的XML数据
     * @param key reids缓存Key
     * @return {String} XML数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @GetMapping("/print/dyqd/{key}/xml")
    public String getPrintXmlOfDyqd(@PathVariable("key") String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("查询子系统：打印清单请求参数为空");
        }
        return bdcFczmFeignService.getPrintXmlOfDyqd(key);
    }

    /**
     * 缓存盐城综合查询打印有无土地信息证明参数
     * @param bdcQlrQO 权利人信息
     * @return BdcPrintParamQO 缓存参数等信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/zhcx/ywtdxxzm/param")
    public BdcPrintParamQO saveYwtdxxzmParamToRedis(@RequestBody BdcQlrQO bdcQlrQO) {
        if(null == bdcQlrQO || StringUtils.isBlank(bdcQlrQO.getQlrmc())) {
            throw new AppException("未定义参数");
        }

        return bdcFczmFeignService.saveYwtdxxzmParamToRedis(bdcQlrQO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 参数缓存Redis键值
     * @return {String} XML数据
     * @description 获取盐城综合查询打印有无土地信息证明的XML数据
     */
    @GetMapping("/print/yancheng/ywtdxxzm/{key}/xml")
    public String getPrintXmlOfYwtdxxzm(@PathVariable("key") String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("未获取到参数缓存信息");
        }
        return bdcFczmFeignService.getPrintXmlOfYwtdxxzm(key);
    }
}
