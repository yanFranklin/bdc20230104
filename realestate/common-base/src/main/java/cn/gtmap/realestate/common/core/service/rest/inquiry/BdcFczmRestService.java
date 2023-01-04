package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxCsDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZszmDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.nantong.BdcPrintParamQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFczmQO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/11/02
 * @description 不动产房产证明相关处理接口
 */
public interface BdcFczmRestService {
    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcZfxxCsDTO 参数信息
     * @return {String} 缓存KEY值
     * @description  将住房查询证明请求的参数--权利人信息缓存到Redis
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/print/zfxx/qlrzjh")
    String saveZfxxQlrxxToRedis(@RequestBody BdcZfxxCsDTO bdcZfxxCsDTO);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  key 权利人信息Redis键
     * @return {String} XML数据
     * @description  获取打印住房查询证明的XML数据
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/print/zfxx/{key}/xml")
    String getPrintXmlOfZfxx(@PathVariable("key") String key);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param list [{"bdcdyh":"1","qszt":"1","gzlslid":"1"},{"bdcdyh":"1","qszt":"1","gzlslid":"1"}]
     * @return {String} redis key
     * @description 缓存房产档案不动产单元号到redis
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/print/zfxx/fcda/bdcdyh")
    String saveBdcFcdaBdcdyhToRedis(@RequestBody List<BdcFczmQO> list);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} XML数据
     * @description 获取打印房产档案的XML数据
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/print/zfxx/fcda/{key}/xml")
    String getPrintXmlOfFcda(@PathVariable("key") String key);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcZszmDTOList 选中的记录数据
     * @return {String} 保存的Redis key
     * @description  根据已选要打印抵押查封证明的产权信息，获取对应的不动产单元，保存至Redis中
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/print/dyacfzm/bdcdyh")
    String getBdcdyhRedisKeyOfDyacfzm(@RequestBody List<BdcZszmDTO> bdcZszmDTOList);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  key 不动产单元号对应Redis键
     * @return {String} XML数据
     * @description  获取抵押查封证明打印的XML数据
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/print/dyacfzm/{key}/xml")
    String getPrintXmlOfDyacfzm(@PathVariable("key") String key);

    /**
     * 获取盐城打印清单的XML数据
     * @param key reids缓存Key
     * @return {String} XML数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/print/zhcx/dyqd/{key}/xml")
    String getPrintXmlOfDyqd(@PathVariable("key") String key);

    /**
     * 缓存盐城综合查询打印有无土地信息证明参数
     * @param bdcQlrQO 权利人信息
     * @return BdcPrintParamQO 缓存参数等信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zhcx/ywtdxxzm/param")
    BdcPrintParamQO saveYwtdxxzmParamToRedis(@RequestBody BdcQlrQO bdcQlrQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 参数缓存Redis键值
     * @return {String} XML数据
     * @description 获取盐城综合查询打印有无土地信息证明的XML数据
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/print/yancheng/ywtdxxzm/{key}/xml")
    String getPrintXmlOfYwtdxxzm(@PathVariable("key")String key);
}
