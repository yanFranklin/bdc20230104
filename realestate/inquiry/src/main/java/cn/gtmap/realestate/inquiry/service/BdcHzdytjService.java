package cn.gtmap.realestate.inquiry.service;

import java.util.List;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-07-10
 * @description 汇总抵押统计
 */
public interface BdcHzdytjService {


    /**
     * @param bdcHzdytjQOJson 查询Qo
     * @return list
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 汇总抵押统计
     */
    List listBdcHzdytj(String bdcHzdytjQOJson);

    /**
     * @param bdcHzdytjQOJson 查询Qo
     * @return list
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 汇总抵押统计 蚌埠
     */
    List listBdcHzdytjBb(String bdcHzdytjQOJson);

    /**
     * @param bdcHzdytjQOJson 查询Qo
     * @return list
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 月报表抵押统计
     */
    List listBdcYbbdytj(String bdcHzdytjQOJson);

    /**
     * @param bdcHzdytjQOJson 查询Qo
     * @return list
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 汇总抵押统计count值
     */
    List listBdcCount(String bdcHzdytjQOJson);

    /**
     * @param bdcHzdytjQOJson 查询Qo
     * @return list
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 汇总抵押统计count值 蚌埠版
     */
    List listBdcCountBb(String bdcHzdytjQOJson);

    /**
     * @param bdcHzdytjQOJson 查询Qo
     * @return list
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 汇总抵押统计已竣工统计值
     */
    List listYjgCount(String bdcHzdytjQOJson);

    /**
     * @param bdcHzdytjQOJson 查询Qo
     * @return list
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 汇总抵押统计已竣工统计值 蚌埠版
     */
    List listYjgCountBb(String bdcHzdytjQOJson);



}
