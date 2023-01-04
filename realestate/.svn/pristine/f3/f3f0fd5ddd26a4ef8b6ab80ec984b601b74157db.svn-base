package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.bo.accessnewlog.Access;
import cn.gtmap.realestate.common.core.bo.accessnewlog.Register;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.exchange.core.domain.BdcQlrDO;
import cn.gtmap.realestate.exchange.core.domain.BdcXmDO;
import cn.gtmap.realestate.exchange.core.domain.zd.BdcExchangeZddz;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zdd on 2015/11/24.
 * 需要单独查询登记业务的方法   后面可以用server的不动产业务服务替代
 */
public interface BdcdjMapper {

    /**
     * zdd 根据项目ID  查找项目信息
     *
     * @param proid
     * @return
     */
    BdcXmDO queryBdcXm(String proid);

    /**
     * 根据项目ID  查找bdcdyh
     *
     * @param xmid
     * @return
     */
    Map<String, Object> getBdcdyhByXmid(@Param(value = "xmid") String xmid);

    /**
     * zdd 关联项目关系表 查询上次不动产登记信息
     *
     * @param proid
     * @return
     */
    List<HashMap<String, String>> queryPreInfo(String proid);

    /**
     * @param
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 序列获取标识码
     */
    Integer getBsm();

    /**
     * @param map
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取证书数量
     */
    Integer getZsNum(Map map);

    /**
     * @param
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取所有字典类型
     */
    List<String> getzdlx();

    /**
     * @param map
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 登记字典代码和国标字典代码对照
     */
    BdcExchangeZddz getBdcExchangeZddz(Map map);

    /**
     * @return
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 登记字典代码和国标字典list全查询
     */
    List<BdcExchangeZddz> getBdcExchangeZddzList();


    /*  *//**
     * @param bdcdyid 不动产单元Id
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取不动产单元号
     *//*
    Map getBdcdyh(String bdcdyid);*/


    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取当天各业务的登簿数量
     */
    List<Map<String, String>> getDjsl(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取当天各业务的登簿数量 部分地区存在非省份的自定义的qxdm，并且需要查看登簿日志量，通过xm表的qxdm统计登簿量和接入量
     */
    List<Map<String, String>> getXnDjsl(Map map);

    /**
     * 获取登簿数据详情
     *
     * @param map
     * @return
     * @Date 2022/4/27
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<Register> getRegisterDetails(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 获取当天各业务的登簿数量去除xmly为2的数据
     */
    List<Map<String, String>> getDjslWithOutHistoryData(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 获取当天各业务的登簿数量去除xmly为2的数据 部分地区存在非省份的自定义的qxdm，并且需要查看登簿日志量，通过xm表的qxdm统计登簿量和接入量
     */
    List<Map<String, String>> getXnDjslWithOutHistoryData(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 获取当天各业务的接入成功的数量
     */
    List<Map<String, String>> getAccessSl(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 获取当天各业务的接入成功的数量 部分地区存在非省份的自定义的qxdm，并且需要查看登簿日志量，通过xm表的qxdm统计登簿量和接入量
     */
    List<Map<String, String>> getXnAccessSl(Map map);


    /**
     * 上报接入数量详情
     *
     * @param map
     * @return
     * @Date 2022/4/27
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<Access> getAccessDetails(Map map);

    /**
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 获取所有的行政区代码
     */
    List<Map<String, String>> queryBdcZdQx();


    /**
     * @return 修改数量
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 批量更新上报日志BDC_JR_SJJL的数据
     */
    int batchUpdateAccessLogCgbs(Map<String, Object> map);

    /**
     * @return 修改数量
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 批量更新上报日志BDC_GJ_SJJL的数据
     */
    int batchUpdateAccessLogCgbsNational(Map<String, Object> map);


    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    List<BdcQlrDO> listBdcQlrByZsid(String zsid);

    /**
     * 插入BDC_JR_DBRZJL表数据，（用于添加xnjrbw字段，正常的插入请使用do对象插入）
     *
     * @param map
     * @return
     */
    int insertBdcJrDbrzjlWithXnjrbw(Map map);

    /**
     * 查询虚拟报文
     *
     * @param id
     * @return
     */
    String queryBdcJrDbrzjlXnjrbwWithXnjrbwById(String id);

    /**
     * @param map
     * @return List<BdcZsDO>
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据map获取证书列表
     */
    List<BdcZsDO> listBdcZs(Map map);
}
