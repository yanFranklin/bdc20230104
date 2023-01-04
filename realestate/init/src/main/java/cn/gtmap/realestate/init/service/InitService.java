package cn.gtmap.realestate.init.service;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;

import java.util.List;

/**
 * init父接口
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/1.
 * @description
 */
public  interface InitService {

    /**
     * 抽象方法 设置开关字段
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param
     *@return  设置开关字段
     *@description
     */
    String getCode();

    /**
     * 抽象方法 设置对照开关值
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param
     *@return  对照开关值
     *@description
     */
    String getVal();

    /**
     * 抽象方法 设置版本区分
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param
     *@return 设置版本区分
     *@description
     */
    String getVersion();


    /**
     * 初始化父接口
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param initServiceQO 初始化所需数据结构
     *@param initServiceDTO 返回数据
     *@return   返回数据结构
     *@description
     */
    InitServiceDTO init(InitServiceQO initServiceQO,InitServiceDTO initServiceDTO) throws Exception;

    /**
     * 删除数据父接口
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param list 要删除的项目
     *@param sfzqlpbsj 是否抽取楼盘表数据
     *@param sfdzbflpbsj 是否对照部分楼盘表数据
     *@param plDel 是否批量业务删除
     *@return   返回数据结构
     *@description
     */
    List<Object> delete(List<BdcXmDO> list,Boolean sfzqlpbsj,Boolean sfdzbflpbsj,Boolean plDel) throws Exception;


    /**
     * 当前项目业务数据查询接口
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcXmDO 查询的项目信息
     *@param initServiceDTO 返回数据
     *@return   返回数据结构
     *@description
     */
    InitServiceDTO query(BdcXmDO bdcXmDO,InitServiceDTO initServiceDTO) throws Exception;

}
