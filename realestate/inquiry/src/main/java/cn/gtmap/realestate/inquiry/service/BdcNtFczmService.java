package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.dto.inquiry.nantong.BdcPrintParamDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.nantong.BdcPrintParamQO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/11/02
 * @description 南通 房产证明处理逻辑接口
 */
public interface BdcNtFczmService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFczmDTO 房产证明信息
     * @return {String} redis key
     * @description 保存（南通）房产证明信息至缓存
     */
    String saveNtFcdaXx(BdcFczmDTO bdcFczmDTO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFczmDTOList 房产证明信息
     * @return {String} redis key
     * @description 批量保存（南通）房产证明信息至缓存
     */
    String saveNtFcdaXxPl(List<BdcFczmDTO> bdcFczmDTOList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} FR3打印的XML数据
     * @description 获取（南通）房产证明打印信息
     */
    String getNtFczmXml(String key);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} FR3打印的XML数据
     * @description 获取（南通）房产证明批量打印信息
     */
    String getNtFczmPlXml(String key);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} FR3打印的XML数据
     * @description 获取（南通）有房无房证明打印信息
     */
    String getNtYfwfzmXml(String key);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcDyzmDTOList 选中的记录数据
     * @return {String} 保存的Redis key
     * @description （南通）将要打印的抵押证明参数信息保存至Redis中
     */
    BdcDyzmDyDTO saveDyzmCsxxToRedis(List<BdcDyzmDTO> bdcDyzmDTOList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} 打印的数据内容
     * @description （南通）获取抵押证明打印对应的XML数据
     */
    String getPrintXmlOfDyzm(String key);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmid 项目ID
     * @return {String} 房产出售时间
     * @description 获取房产出售时间
     */
    String getFdcqCssj(String xmid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param list 打印请求参数
     * @return {BdcQszmDyDTO} 缓存的Redis KEY以及查询号信息
     * @description 缓存权属证明不动产单元号到redis
     */
    BdcQszmDyDTO saveNtFcdaBdcdyhToRedis(List<BdcQszmQO> list);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @return {String} XML数据
     * @description 获取打印权属证明的XML数据
     */
    String getPrintXmlOfQszm(String key);

    /**
     * @param bdcPrintParamDTO 打印请求参数
     * @return {BdcQszmDyDTO} 缓存的Redis KEY以及查询号信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 缓存打印利害关系人、律师查询参数到redis
     */
    BdcPrintParamQO saveLhgxrLscxPrintParam(BdcPrintParamDTO bdcPrintParamDTO);

    /**
     * @param key 参数缓存Redis键值
     * @return {String} XML数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description （南通）获取打印利害关系人、律师查询的XML数据
     */
    String getPrintXmlOfLhgxrLscx(String key);

    /**
     * 南通获取权属证明及对应PDF文件信息
     * @param bdcQszmQO 权利人证号、不动产权证号参数
     * @return {List} 权属证明信息
     * @throws Exception
     */
    List<Map<String, Object>> queryQszm(BdcQszmQO bdcQszmQO) throws Exception;

    /**
     * 提供给受理，生成有房无房证明XML的数据服务
     * @param {bdcFczmDTO} qlrmc 权利人名称， qlrzjh 权利人证件号
     * @return xml 打印有房无房的XMl数据
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    String generateYfwfzmXml(BdcFczmDTO bdcFczmDTO);
}
