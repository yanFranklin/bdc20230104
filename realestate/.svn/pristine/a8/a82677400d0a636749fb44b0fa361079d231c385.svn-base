package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcNewPrintDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlDysjDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlPrintDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlZbDataDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2018/12/12,1.0
 * @description 获取打印xml
 */
public interface BdcSlPrintService {
    /**
     * @param bdcSlPrintDTO 打印传参
     * @return String  获取打印xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取fr3打印地址（南通使用）
     */
    String getFr3Url(BdcSlPrintDTO bdcSlPrintDTO);

    /**
     * @param bdcSlPrintDTO 打印传参
     * @return String  获取打印xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据流程类型获取fr3打印地址（合肥使用）
     */
    String getFr3UrlByLclx(BdcSlPrintDTO bdcSlPrintDTO);

    /**
     * @param processInsId 工作流实例id
     * @return String  获取打印xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取打印xml（获取登记数据组织xml打印）---合肥从登记组织数据源，直接传打印参数调用接口生成xml数据源
     */
    String packPrintXml(String processInsId, String dylx, String zxlc, String sjclids, String userName);

    /**
     * @param gzlslids 工作流实例id
     * @return String  获取批量打印xml
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 获取批量打印xml（获取登记数据组织xml打印）
     */
    String packPrintXmlPl(String gzlslids, String dylx, String zxlc, String sjclids, String userName);

    /**
     * @param processInsId 工作流实例id
     * @return String  获取打印xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取打印xml（获取登记数据组织xml打印）---蚌埠申请书打印分产权和抵押--合肥存量房买卖合同只展示产权的
     */
    String packPrintXmlToXmid(String processInsId, String dylx, String zxlc, String sjclids, String userName, String xmid);

    /**
     * @param gzlslid 工作流实例id
     * @return String  获取打印xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 直接传参调用接口获取打印的xml文件(从登记获取数据打印_南通)
     */
    String packPrintXmlToNt(String gzlslid, String dylx, String zxlc);

    /**
     * @param processInsId 工作流实例id
     * @return String  获取打印xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取打印xml（需要从受理库中获取数据）
     */
    String generatePrintData(String processInsId, String dylx, String qlrlb, String xmid, String sjclids);

    /**
     *
     * @param gzlslids
     * @param dylx
     * @param qlrlb
     * @param xmid
     * @param sjclids
     * @return
     */
    String generatePrintPlData(String gzlslids, String dylx, String qlrlb, String xmid, String sjclids);

    /**
     * @param gzlslid 工作流实例id
     * @return String  获取打印xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取打印xml（需要从受理库中获取数据,南通接口处理）
     */
    String generatePrintDataToNt(String gzlslid, String dylx, String qlrlb, String xmid,String userName);
    /**
     * @param map 自定义添加的打印参数
     * @return String  获取打印xml
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 获取打印xml
     */
    String generatePrintDataWithZdyData(Map map,String dylx);

    /**
     * @param gzlslid 工作流实例ID
     * @param dylx         tfsqs
     * @param sfxxid       收费信息ID
     * @return String  获取打印xml
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 不同数据源下获取打印的xml文件(参数中有收费信息ID)
     */
    String generatePrintDataWithSfxxid(String gzlslid, String dylx, String sfxxid) throws UnsupportedEncodingException;

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [processInsId, dylx, qlrlb, xmid, sjclids] 流程实例id，打印类型，权利人类别，项目id，收件材料id
     * @return: String 打印xml数据
     * @description获取打印xml,并将字典项code替换为字典名称（需要从受理库中获取数据）
     */
    String generatePrintDataAndReplaceZd(String processInsId, String dylx, String qlrlb, String xmid, String sjclids);

    /**
     * @param configParam 打印配置参数
     * @return List<Map> 执行结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 执行打印配置sql信息，获取执行结果
     */
    List<Map> executeConfigSql(Map configParam);

    /**
     * @param bdcSlPrintDTO 打印受理前台传参
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/4/16 17:06
     */
    BdcNewPrintDTO generatePrintDTO(BdcSlPrintDTO bdcSlPrintDTO);

    /**
     * @param bdcSlPrintDTO 批量打印受理前台传参
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description
     * @date : 2022/7/5 17:00
     */
    BdcNewPrintDTO generatePrintPlDTO(BdcSlPrintDTO bdcSlPrintDTO);

    /**
     * @param bdcXmDO 不动产项目信息
     * @param dylx    打印类型
     * @param lclx    流程类型 1.2.3.4
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/4/20 17:07
     */
    BdcSlDysjDTO generateDylxAndSl(BdcXmDO bdcXmDO, String dylx, Integer lclx);

    /**
     * 特殊业务打印需求服务（蚌埠）：获取申请人信息并调蚌埠大数据接口获取人员信息
     *
     * @param processInsId 工作流实例ID
     * @param qlrlb        权利人类别
     * @param xmid         项目ID
     * @return Object       人员信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    Object getQlrPrintDataForBb(String processInsId, String qlrlb, String xmid, String userName);

    /**
     * 获取人像信息
     *
     * @param processInsId
     * @return
     */
    Object packPrintRxtpDatasForBb(String processInsId);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取签名信息地址
     * @date : 2020/7/29 9:27
     */
    void queryQmxx(Integer bdlx, Integer qzrlb, String xmid, HttpServletResponse response);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询电子签名-南通
     * @date : 2022/3/16 11:17
     */
    void queryDzqm(String fjid, HttpServletResponse response);

    /**
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 常州查档打印获取校验查询二维码
     * @date : 2020/7/29 9:27
     */
    void queryJycx(String xmid, String dylx, HttpServletResponse response) throws Exception;


    /**
     * 非税开票发票图片流
     * @param id
     * @param response
     */
    void fskpImg(String id, HttpServletResponse response);


    /**
     * 查档流程签字图片流
     *
     * @param gzlslid
     * @param response
     * @return
     */
    void cdxxSignImg(String gzlslid, HttpServletResponse response);

    /**
     * 组装打印参数，添加审核信息xml信息
     *
     * @param map 打印参数
     * @return String  获取打印xml
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 获取打印xml
     */
    String generatePrintDataWithShxxData(Map map, String dylx);

    /**
     * @param gzlslid, dylx,  zxlc
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据打印配置表的打印类型生成xml
     * @date : 2021/7/23 10:04
     */
    String generateXmlFromDypz(String gzlslid, String dylx, String zxlc);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/7/23 10:27
     */
    String generateSlXmlFromDypz(String gzlslid, String dylx, String qlrlb, String xmid, String sjclids);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取常州批量收费缴费书打印
     * @date : 2021/11/8 21:08
     */
    String queryJfsXml(String gzlslid, String dylx);

    /**
     * @param bdcSlZbDataDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 执行打印配置sql
     * @date : 2021/11/16 14:02
     */
    List<Map> executSql(BdcSlZbDataDTO bdcSlZbDataDTO);

    /**
     * @param zjh 证件号
     * @param xm 姓名
     * @param dylx 打印类型
     * @author <a href="mailto:gaolining@gtmap.cn">sunyuzhuang</a>
     * @description 常州查档生成PDF接口获取校验查询二维码
     * @date : 2020/1/10 11:13
     */
    void cdxxscpdfJycxImage(String zjh, String xm, String dylx, HttpServletResponse response) throws Exception;


    /**
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 连云港组织打印数据源xml
     * @date : 2022/12/13 9:54
     */
    String generateSlMkXml(String gzlslid, String djxl, String zxlc);
}
