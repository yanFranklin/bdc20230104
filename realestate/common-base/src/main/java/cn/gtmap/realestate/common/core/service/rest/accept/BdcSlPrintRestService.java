package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.dto.accept.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2018/12/12,1.0
 * @description 不动产受理打印服务
 */
public interface BdcSlPrintRestService {
    /**
     * @param bdcSlPrintDTO 打印传参
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 南通版本打印获取打印地址
     */
    @PostMapping("/realestate-accept/rest/v1.0/print")
    String print(@RequestBody BdcSlPrintDTO bdcSlPrintDTO);

    /**
     * @param bdcSlPrintDTO 打印传参
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 合肥版本根据流程类型打印获取打印地址
     */
    @PostMapping("/realestate-accept/rest/v1.0/print/lclx")
    String printByLclx(@RequestBody BdcSlPrintDTO bdcSlPrintDTO);

    /**
     * @param processInsId 工作流实例id
     * @param dylx         sjd/sqs
     * @return String  获取打印xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 相同数据源下获取打印的xml文件
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/{processInsId}/{dylx}/{zxlc}/xml")
    String packPrintXml(@PathVariable(name = "processInsId") String processInsId, @PathVariable(name = "dylx") String dylx, @PathVariable(name = "zxlc", required = false) String zxlc, @RequestParam(name = "sjclids", required = false) String sjclids, @RequestParam(name = "userName", required = false) String userName);

    /**
     * @param gzlslids 工作流实例ids
     * @param dylx         sjd/sqs
     * @return String  获取打印xml
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 相同数据源下获取打印的xml文件批量
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/{gzlslids}/{dylx}/{zxlc}/xml/pl")
    String packPrintXmlPl(@PathVariable(name = "gzlslids") String gzlslids, @PathVariable(name = "dylx") String dylx, @PathVariable(name = "zxlc", required = false) String zxlc, @RequestParam(name = "sjclids", required = false) String sjclids, @RequestParam(name = "userName", required = false) String userName);

    /**
     * @param processInsId 工作流实例id
     * @param dylx         sqs
     * @return String  获取打印xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 相同数据源下获取打印的xml文件---蚌埠申请书打印分产权和抵押
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/{processInsId}/{dylx}/{zxlc}/xml/bb")
    String packPrintXmlToXmid(@PathVariable(name = "processInsId") String processInsId, @PathVariable(name = "dylx") String dylx, @PathVariable(name = "zxlc", required = false) String zxlc, @RequestParam(name = "sjclids", required = false) String sjclids, @RequestParam(name = "userName", required = false) String userName, @RequestParam(name = "xmid", required = false) String xmid);

    /**
     * @param processInsId 工作流实例ID
     * @param dylx         sjd/sqs
     * @return String  获取打印xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 不同数据源下获取打印的xml文件
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/{processInsId}/{dylx}/{qlrlb}/{xmid}/xml/datas")
    String packPrintDatas(@PathVariable(name = "processInsId") String processInsId, @PathVariable(name = "dylx") String dylx, @PathVariable(name = "qlrlb") String qlrlb, @PathVariable(name = "xmid") String xmid, @RequestParam(name = "sjclids", required = false) String sjclids);

    /**
     * @param gzlslids 工作流实例IDs
     * @param dylx         sjd/sqs
     * @return String  获取打印xml
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 批量获取打印的xml文件
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/{gzlslids}/{dylx}/xml/datas/pl")
    String packPrintPlDatas(@PathVariable(name = "gzlslids") String gzlslids, @PathVariable(name = "dylx") String dylx, @RequestParam(name = "qlrlb", required = false) String qlrlb, @RequestParam(name = "xmid", required = false) String xmid, @RequestParam(name = "sjclids", required = false) String sjclids);

    /**
     * @param ewmnr  二维码内容
     * @param ewmurl 用于生成URL地址的二维码
     * @param tmlx 条码类型
     * @return String  获取打印xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 不同数据源下获取打印的xml文件
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/{ewmnr}/ewm")
    void packEwmPic(@PathVariable(name = "ewmnr") String ewmnr,
                    HttpServletResponse response,
                    @RequestParam(name = "ewmnr", required = false) String ewmurl,
                    @RequestParam(name = "tmlx", required = false) String tmlx
    );

    /**
     * @param gzlslid 工作流实例ID
     * @return 面积计算结果
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 面积计算
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/countmj/{gzlslid}")
    BdcMjDTO countMj(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param processInsId 工作流实例id
     * @param dylx         打印类型
     * @param zxlc         是否注销流程
     * @return xml
     * 相同数据源下获取打印的xml文件(从登记获取数据打印_南通)
     * @description
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/{processInsId}/{dylx}/{zxlc}/xml/nt")
    String generateSlXmlToNt(@PathVariable(name = "processInsId") String processInsId, @PathVariable(name = "dylx") String dylx, @PathVariable(name = "zxlc", required = false) String zxlc);


    /**
     * @param processInsId
     * @param dylx
     * @param zxlc
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 申请书只需打印一份，从bdc_djxl_pz 表获取配置的打印类型
     * @date : 2021/7/23 9:50
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/{processInsId}/{dylx}/{zxlc}/xml/dypz")
    String generateDjXmlFromDypz(@PathVariable(name = "processInsId") String processInsId, @PathVariable(name = "dylx") String dylx, @PathVariable(name = "zxlc", required = false) String zxlc);


    /**
     * @param processInsId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 收件单打印一份，从bdc_djxl_pz 表获取打印类型
     * @date : 2021/7/23 10:26
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/{processInsId}/{dylx}/{qlrlb}/{xmid}/xml/dypz/sl")
    String generateSlXmlFromDypz(@PathVariable(name = "processInsId") String processInsId, @PathVariable(name = "dylx") String dylx, @PathVariable(name = "qlrlb") String qlrlb, @PathVariable(name = "xmid") String xmid, @RequestParam(name = "sjclids", required = false) String sjclids);

    /**
     * @param processInsId 工作流实例ID
     * @param dylx         sjd/sqs
     * @return String  获取打印xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 不同数据源下获取打印的xml文件(收件单)
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/{processInsId}/{dylx}/{qlrlb}/{xmid}/xml/datas/nt")
    String packPrintDatasToNt(@PathVariable(name = "processInsId") String processInsId, @PathVariable(name = "dylx") String dylx, @PathVariable(name = "qlrlb") String qlrlb, @PathVariable(name = "xmid") String xmid, @RequestParam(name = "userName", required = false) String userName);

    /**
     * @param processInsId 工作流实例ID
     * @param dylx         tfsqs
     * @param sfxxid       收费信息ID
     * @return String  获取打印xml
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 不同数据源下获取打印的xml文件(参数中有收费信息ID)
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/{processInsId}/{dylx}/{sfxxid}/sfxx/xml")
    String packPrintDatasWithSfxxId(@PathVariable(name = "processInsId") String processInsId, @PathVariable(name = "dylx") String dylx, @PathVariable(name = "sfxxid") String sfxxid) throws UnsupportedEncodingException;

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [processInsId, dylx, qlrlb, xmid]
     * @return: java.lang.String
     * @description 受理数据源下获取打印的xml数据，将受理查询出来的字典项dm数据替换为字典项名称数据
     * <p>sql语句查询时，使用字符串拼接的方式将字典项字段对应的值，以（字典名称@字典dm值）fwlx@1的形式进行返回，在生成xml数据将此类型的数据进行替换为字典项的名称</p>
     * <p>例如： select id,name,value,concat('fwlx@',dzwyt) as dzwyt from a </p>
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/zd/{processInsId}/{dylx}/{qlrlb}/{xmid}/xml/datas")
    String packPrintDatasAndReplaceZd(@PathVariable(name = "processInsId") String processInsId, @PathVariable(name = "dylx") String dylx, @PathVariable(name = "qlrlb") String qlrlb, @PathVariable(name = "xmid") String xmid, @RequestParam(name = "sjclids", required = false) String sjclids);

    /**
     * @param bdlx 签字表单类型
     * @param qzrlb 签字人类别
     * @param xmid 项目ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取签名信息
     * @date : 2020/1/10 11:13
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/qmxx")
    void queryQmxxImage(HttpServletResponse response, @RequestParam(name = "bdlx", required = false) Integer bdlx, @RequestParam(name = "qzrlb", required = false) Integer qzrlb, @RequestParam(name = "xmid", required = false) String xmid);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取电子签名
     * @date : 2022/3/16 11:16
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/dzqm")
    void queryDzqm(HttpServletResponse response, @RequestParam(name = "bdlx", required = true) String fjid);

    /**
     * @param configParam 打印配置参数
     * @return List<Map> 执行结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 执行打印配置sql信息，获取执行结果
     */
    @PostMapping("/realestate-accept/rest/v1.0/print/config/mapper")
    List<Map> executeConfigSql(@RequestBody Map configParam);

    /**
     * @param bdcSlPrintDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新模式获取打印生成xml数据源地址
     * @date : 2020/4/16 17:00
     */
    @PostMapping("/realestate-accept/rest/v1.0/print/newmode")
    BdcNewPrintDTO generatePrintDTO(@RequestBody BdcSlPrintDTO bdcSlPrintDTO);

    /**
     * @param bdcSlPrintDTO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 新模式（支持批量）获取打印生成xml数据源地址
     * @date : 2022/7/5 17:00
     */
    @PostMapping("/realestate-accept/rest/v1.0/print/newmode/pl")
    BdcNewPrintDTO generatePrintPlDTO(@RequestBody BdcSlPrintDTO bdcSlPrintDTO);


    /**
     * @param bdcSlPrintDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取打印特殊参数
     * @date : 2020/4/20 17:22
     */
    @PostMapping("/realestate-accept/rest/v1.0/print/tscs")
    BdcSlDysjDTO getTsDycs(@RequestBody BdcSlPrintDTO bdcSlPrintDTO);

    /**
     * 特殊业务打印需求服务（蚌埠）：获取申请人信息并调蚌埠大数据接口获取人员信息
     *
     * @param processInsId 工作流实例ID
     * @param qlrlb        权利人类别
     * @param xmid         项目ID
     * @return Object       人员信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/bengbu/qlr/{processInsId}/{qlrlb}/{xmid}/{userName}/datas")
    Object packPrintQlrDatasForBb(@PathVariable(name = "processInsId") String processInsId,
                                  @PathVariable(name = "qlrlb") String qlrlb,
                                  @PathVariable(name = "xmid") String xmid,
                                  @PathVariable(name = "userName") String userName);

    /**
     * 获取redis中存储的base64code字符转换后的文件流图片
     *
     * @param rediskey 权利人ID 对应存储在redis上的key
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 图片文件流
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/zp/{rediskey}")
    void getPrintZp(@PathVariable(name = "rediskey") String rediskey, HttpServletResponse response);

    /**
     * 获取redis中存储的base64code字符转换后的文件流图片
     *
     * @param rediskey 权利人ID 对应存储在redis上的key
     * @param type 文件格式
     * @param scale 文件缩放大小
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 图片文件流
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/zp/{type}/{scale}/{rediskey}")
    void getPrintZp(@PathVariable(name = "rediskey") String rediskey, @PathVariable(name = "type") String type,
                    @PathVariable(name = "scale") float scale, HttpServletResponse response);

    /**
     * 特殊业务打印需求服务（蚌埠）：获取人像信息
     *
     * @param gzlslid 工作流实例ID
     * @return Object       人人像信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/bengbu/rxtp/{gzlslid}/datas")
    Object packPrintRxtpDatasForBb(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * @param xmid 项目id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 常州查档打印获取校验查询二维码
     * @date : 2020/1/10 11:13
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/jycx/{xmid}")
    void queryJycxImage(@PathVariable(name = "xmid") String xmid, @RequestParam(name = "dylx" ,required = false) String dylx, HttpServletResponse response) throws Exception;

    /**
     * 组装打印参数，并添加自定义xml数据
     * Map为需要传入到xml数据的数据，采用 a=a1&b=b1&c=c1的方式跟在请求链接后面
     * @param map 需要传入到xml中的数据
     * @return 打印参数
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/zdy/{dylx}/xml/datas")
    String packPrintDataWithZdyData(@RequestParam Map map ,@PathVariable(name = "dylx") String dylx);

    /**
     * 生成人证对比PDF文件
     * @param slbh 受理编号
     * @return pdf文件地址
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/scrzdb/{slbh}/{gzlslid}")
    String scrzdb(@PathVariable(name = "slbh") String slbh, @PathVariable(name = "gzlslid") String gzlslid);
    /**
     * 获取放置在服务器中打印模板路径下正确与错误的图片
     * @param type 类型（success:正确的图片；error:错误的图片）
     * @return 图片的文件流
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/image/{type}")
    void getSuccessOrErrorImage(@PathVariable(name = "type") String type, HttpServletResponse response);

    /**
     * 非税开电子发票图片流
     * @param id
     * @return
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/print/fskp/{id}")
    void fskpImg(@PathVariable("id") String id,HttpServletResponse response);

    /**
     * 查档流程签字图片流
     * @param gzlslid
     * @return
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/print/cdxxSignImg/{gzlslid}")
    void cdxxSignImg(@PathVariable("gzlslid") String gzlslid,HttpServletResponse response);

    /**
     * 组装打印参数，添加审核信息xml信息
     *
     * @param map 需要传入到xml中的数据
     * @return 打印参数
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/shxx/{dylx}/xml/datas")
    String packPrintDataWithShxxData(@RequestParam Map map, @PathVariable(name = "dylx") String dylx);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 常州批量收费缴费书打印接口
     * @date : 2021/11/8 20:53
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/{gzlslid}/{dylx}/jfs/xml")
    String getCzJfsXml(@PathVariable("gzlslid") String gzlslid, @PathVariable("dylx") String dylx);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 子表打印数据源查询结果
     * @date : 2021/11/16 10:39
     */
    @PostMapping("/realestate-accept/rest/v1.0/print/zbData")
    List<Map> queryZbData(@RequestBody BdcSlZbDataDTO bdcSlZbDataDTO);

    /**
     * @param zjh 证件号
     * @param xm 姓名
     * @author <a href="mailto:gaolining@gtmap.cn">sunyuzhuang</a>
     * @description 常州查档生成PDF接口获取校验查询二维码
     * @date : 2020/1/10 11:13
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/jycx/cdxxscpdfJycxImage")
    void cdxxscpdfJycxImage(@RequestParam(name = "zjh") String zjh, @RequestParam(name = "xm") String xm,  @RequestParam(name = "dylx" ,required = false) String dylx, HttpServletResponse response) throws Exception;

    /**
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 连云港组织打印数据源xml
     * @date : 2022/12/13 9:54
     */
    @GetMapping("/realestate-accept/rest/v1.0/print/lyg/xml")
    Map generateSlMkXml(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "djxl") String djxl, @RequestParam(name = "zxlc", required = false) String zxlc);

}
