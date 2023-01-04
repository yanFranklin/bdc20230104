package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxCsDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZszmDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.nantong.BdcPrintParamQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFczmQO;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/7/17
 * @description 住房查询：房产证明打印服务
 */
public interface BdcFczmService {
    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcZfxxCsDTO 参数信息
     * @return {String} 缓存KEY值
     * @description  将住房查询证明请求的参数--权利人信息缓存到Redis
     */
    String saveZfxxQlrxxToRedis(BdcZfxxCsDTO bdcZfxxCsDTO);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  key 权利人信息Redis键
     * @return {String} XML数据
     * @description  获取打印住房查询证明的XML数据
     */
    String getPrintXmlOfZfxx(String key);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param list [{"bdcdyh":"1","qszt":"1","gzlslid":"1"},{"bdcdyh":"1","qszt":"1","gzlslid":"1"}]
     * @return {String} redis key
     * @description 缓存房产档案不动产单元号到redis
     */
    String saveBdcFcdaBdcdyhToRedis(List<BdcFczmQO> list);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} XML数据
     * @description 获取打印房产档案的XML数据
     */
    String getPrintXmlOfFcda(String key);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZszmDTOList 选中的记录数据
     * @return {String} 保存的Redis key
     * @description 根据已选要打印抵押查封证明的产权信息，获取对应的不动产单元，保存至Redis中
     */
    String getBdcdyhRedisKeyOfDyacfzm(List<BdcZszmDTO> bdcZszmDTOList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 不动产单元号对应Redis键
     * @return {String} XML数据
     * @description 获取抵押查封证明打印的XML数据
     */
    String getPrintXmlOfDyacfzm(String key);

    /**
     * 获取盐城打印清单的XML数据
     *
     * @param key reids缓存Key
     * @return {String} XML数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    String getPrintXmlOfDyqd(String key);

    /**
     * 缓存盐城综合查询打印有无土地信息证明参数
     * @param bdcQlrQO 权利人信息
     * @return BdcPrintParamQO 缓存参数等信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    BdcPrintParamQO saveYwtdxxzmParamToRedis(BdcQlrQO bdcQlrQO);

    /**
     * @param key 参数缓存Redis键值
     * @return {String} XML数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取盐城综合查询打印有无土地信息证明的XML数据
     */
    String getPrintXmlOfYwtdxxzm(String key);
}
