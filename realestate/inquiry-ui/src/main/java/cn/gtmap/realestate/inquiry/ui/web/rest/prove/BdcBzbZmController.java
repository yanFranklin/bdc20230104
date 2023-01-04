package cn.gtmap.realestate.inquiry.ui.web.rest.prove;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcQszmDyDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcQszmQO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZszmDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcdyxxDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcBzbZmFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/03/02
 * @description （标准版）综合查询证明相关处理逻辑请求
 */
@RestController
@RequestMapping("/rest/v1.0")
public class BdcBzbZmController extends BaseController {
    /**
     * 房产证明处理服务
     */
    @Autowired
    private BdcBzbZmFeignService bdcBzbZmFeignService;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcQlrQO  权利人信息
     * @return {String} Redis key
     * @description  保存有房无房证明打印参数信息
     */
    @PostMapping("/zhcx/bzb/yfwfzm")
    public String saveBzbYfwfzmXx(@RequestBody BdcQlrQO  bdcQlrQO){
        if(null == bdcQlrQO || StringUtils.isBlank(bdcQlrQO.getQlrmc())){
            throw  new NullPointerException("查询子系统：查询房产证明参数为空");
        }
        return bdcBzbZmFeignService.saveBzbYfwfzmXx(bdcQlrQO);
    }

    /**
     * 有房无房验证证件号是否必填
     * @param qlrmc 权利人名称
     * @return
     */
    @GetMapping("/zhcx/checkZjh")
    public Boolean checkZjh(@RequestParam(value = "qlrmc") String qlrmc){
        if (StringUtils.isBlank(qlrmc)) {
            throw new NullPointerException("权利人名称不可为空！");
        }
        return bdcBzbZmFeignService.zjhYz(qlrmc);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 权利人信息Redis键
     * @return {String} XML数据
     * @description 获取打印房产证明的XML数据
     */
    @GetMapping("/print/zhcx/bzb/yfwfzm/{key}/xml")
    public String getPrintXmlOfZfxx(@PathVariable("key") String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("查询子系统：房产证明打印请求参数为空");
        }
        return bdcBzbZmFeignService.getPrintXmlOfZfxx(key);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcQszmQOList 打印请求参数
     * @return {BdcQszmDyDTO} 缓存的Redis KEY以及查询号信息
     * @description 缓存权属证明参数信息到redis
     */
    @PostMapping("/zhcx/bzb/qszm")
    public BdcQszmDyDTO saveBdcQszmParamToRedis(@RequestBody List<BdcQszmQO> bdcQszmQOList) {
        if (CollectionUtils.isEmpty(bdcQszmQOList)) {
            throw new NullPointerException("查询子系统：打印权属证明参数为空！");
        }
        return bdcBzbZmFeignService.saveBdcQszmParamToRedis(bdcQszmQOList);
    }

    /**
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcdyxxDTOList 打印请求参数
     * @return {BdcQszmDyDTO} 缓存的Redis KEY以及查询号信息
     * @description 缓存已登记不动产单元查询参数信息到redis
     */
    @PostMapping("/bdcdycx/save")
    public String saveDjbdcdyhParamToRedis(@RequestBody List<BdcdyxxDTO> bdcdyxxDTOList) {
        if (CollectionUtils.isEmpty(bdcdyxxDTOList)) {
            throw new NullPointerException("查询子系统：不动产单元查询打印参数为空！");
        }
        return bdcBzbZmFeignService.saveDjbdcdyhParamToRedis(bdcdyxxDTOList);
    }


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @param bdclx 不动产类型
     * @return {String} XML数据
     * @description 获取打印权属证明的XML数据
     */
    @GetMapping("/print/zhcx/bzb/qszm/{key}/{bdclx}/xml")
    public String getPrintXmlOfQszm(@PathVariable("key") String key, @PathVariable("bdclx")String bdclx) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("查询子系统：打印权属证明参数为空");
        }
        return bdcBzbZmFeignService.getPrintXmlOfQszm(key, bdclx);
    }

    @GetMapping("/print/bdcdycx/{key}/{bdclx}/xml")
    public String getPrintXmlOfBdcdycx(@PathVariable("key") String key, @PathVariable("bdclx")String bdclx) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("查询子系统：不动产单元查询参数为空");
        }
        return bdcBzbZmFeignService.getPrintXmlOfBdcdycx(key, bdclx);
    }

    /**
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcZszmDTOList 打印查档信息请求参数
     * @return {BdcQszmDyDTO} 缓存的Redis KEY以及查询号信息
     * @description 缓存权属证明参数信息到redis
     */
    @PostMapping("/zhcx/bzb/cdxxcxjg")
    public Object saveBdcCdxxParamToRedis(@RequestBody List<BdcZszmDTO> bdcZszmDTOList){
        if (CollectionUtils.isEmpty(bdcZszmDTOList)) {
            throw new NullPointerException("查询子系统：打印查档结果参数为空！");
        }
        return bdcBzbZmFeignService.saveBdcCdxxParamToRedis(bdcZszmDTOList);
    }

    /**
     * 获取打印查档结果的XML数据
     * @param key redis key
     * @param dylx 打印类型
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/print/zhcx/bzb/cdxxcx/{key}/{dylx}/xml")
    public String getPrintXmlOfCdxxJg(@PathVariable("key") String key, @PathVariable("dylx")String dylx) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("查询子系统：打印查档结果参数为空");
        }
        return bdcBzbZmFeignService.getPrintXmlOfCdxxJg(key, dylx);
    }
}
