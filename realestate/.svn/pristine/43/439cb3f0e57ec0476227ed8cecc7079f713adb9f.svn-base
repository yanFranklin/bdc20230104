package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/03/02
 * @description （标准版）综合查询证明相关处理逻辑
 */
public interface BdcBzbZhcxZmService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcQlrQO 权利人信息
     * @return {String} Redis key
     * @description （标准版）保存有房无房证明打印参数信息
     */
    String saveBzbYfwfzmXx(BdcQlrQO bdcQlrQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {String} 编号
     * @description 获取有房无房证明编号
     */
    String getYfwfzmBh();

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 权利人信息Redis键
     * @return {String} XML数据
     * @description 获取打印房产证明的XML数据
     */
    String getPrintXmlOfZfxx(String key);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcQszmQOList 打印请求参数
     * @return {BdcQszmDyDTO} 缓存的Redis KEY以及查询号信息
     * @description 缓存权属证明参数信息到redis
     */
    BdcQszmDyDTO saveBdcQszmParamToRedis(List<BdcQszmQO> bdcQszmQOList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key redis key
     * @param bdclx 不动产类型
     * @return {String} XML数据
     * @description 获取打印权属证明的XML数据
     */
    String getPrintXmlOfQszm(String key, String bdclx);


    /**
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zxy</a>
     * @param  bdcdyxxDTOList 打印请求参数
     * @return {BdcdyxxDTO} 缓存的Redis KEY
     * @description 缓存权属证明参数信息到redis
     */
    String saveDjbdcdyhParamToRedis(List<BdcdyxxDTO> bdcdyxxDTOList);

    /**
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zxy</a>
     * @param key redis key
     * @param bdclx 不动产类型
     * @return {String} XML数据
     * @description 获取打印不动产单元查询的XML数据
     */
    String getPrintXmlOfBdcdycx(String key, String bdclx);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取权属证明查询号
     */
    String getQszmCxh();

    /**
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcZszmDTOList 打印查档信息请求参数
     * @return {BdcZszmDTO} 缓存的Redis KEY以及查询号信息
     * @description 缓存查档信息参数信息到redis
     */
    Object saveBdcCdxxParamToRedis(List<BdcZszmDTO> bdcZszmDTOList);

    /**
     * 获取打印查档结果的XML数据
     *
     * @param key  redis key
     * @param dylx 打印类型
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    String getPrintXmlOfCdxxJg(String key, String dylx);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param qlrQOList 权利人信息
     * @return {List} 房产信息
     * @description 查询权利人房产证明并生成PDF
     */
    List<BdcFczmPdfDTO> getFczmPdf(List<BdcQlrQO> qlrQOList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcQszmQOList 权属证明参数
     * @return WwsqResponse 权属证明
     * @description 查询指定单元权属证明并生成PDF
     */
    List<BdcQszmPdfDTO> getQszmPdf(List<BdcQszmQO> bdcQszmQOList);

    /**
     * @author <a href="mailto:hongqin@gtmap.cn">hongqin</a>
     * @return String 查询范围
     * @return
     */
    String getCxfw();


}
