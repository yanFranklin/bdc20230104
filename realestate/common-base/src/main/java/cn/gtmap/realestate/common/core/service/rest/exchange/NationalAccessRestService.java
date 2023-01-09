package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.common.core.dto.exchange.access.QjsjjcDTO;
import cn.gtmap.realestate.common.core.dto.exchange.access.SbxzVO;
import cn.gtmap.realestate.common.core.qo.exchange.openapi.AccessByTimeIntervalQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-04-19
 * @description 自动上报 服务
 */
public interface NationalAccessRestService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param xmid 项目主键
     * @return void
     * @description 根据项目主键汇交当前项目
     */
    @GetMapping("/realestate-exchange/rest/v1.0/access/xmid")
    void autoAccessByXmid(@RequestParam(name = "xmid") String xmid);

    /**
     * @param xmidList 项目主键集合
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目主键集合汇交当前项目
     */
    @GetMapping("/realestate-exchange/rest/v1.0/access/xmidlist")
    void autoAccessByXmidList(@RequestParam(name = "xmidList") List<String> xmidList);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据xmid进行业务汇交，并更新销账状态
     * @date : 2022/12/13 16:47
     */
    @PostMapping("/realestate-exchange/rest/v1.0/access/sbxz")
    void autoAccessBySbxz(@RequestBody List<SbxzVO> sbxzVOList);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据项目主键集合汇交当前项目, 包括外联项目
     * @date : 2022/12/6 13:43
     */
    @PostMapping("/realestate-exchange/rest/v1.0/access/xmidList/wlxm")
    void autoAccessWithWlxmByXmidList(@RequestBody List<String> xmidList);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 市级项目接入汇交
     * @date : 2023/1/6 11:35
     */
    @PostMapping("/realestate-exchange/rest/v1.0/access/xmidList/city")
    void autoCityAccessByXmidList(@RequestBody List<String> xmidList);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 外联项目的接入汇交
     * @date : 2022/12/6 14:32
     */
    @PostMapping("/realestate-exchange/rest/v1.0/access/wlxmjr")
    void autoAccessWlxmByXmidList(@RequestBody List<String> xmidList);


    /**
     * @param
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 根据时间区间上报
     */
    @GetMapping("/realestate-exchange/rest/v1.0/access/time/interval")
    void autoAccessByTimeInterval(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("type") String type, @RequestParam(required = false, value = "xmly") String xmly);

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @param
     * @return void
     * @description 根据时间区间上报
     */
    @PostMapping("/realestate-exchange/rest/v1.0/access/time/interval/post")
    void autoAccessByTimeInterval(@RequestBody AccessByTimeIntervalQO accessByTimeIntervalQO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param xmid 项目主键
     * @return void
     * @description 根据项目主键汇交同一流程所有项目
     */
    @GetMapping("/realestate-exchange/rest/v1.0/access/all/xmid")
    void autoAccessAllXmByXmid(@RequestParam(name = "xmid") String xmid);

    /**
     * @param processInsId 工作流主键
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据工作流主键上交
     */
    @GetMapping("/realestate-exchange/rest/v1.0/access/proccessid")
    void autoAccessByProcessInsId(@RequestParam(name = "processInsId") String processInsId);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例id外联注销项目
     * @date : 2022/12/2 9:55
     */
    @GetMapping("/realestate-exchange/rest/v1.0/access/wlzxxm")
    void autoAccessWlzxXm(@RequestParam(name = "processInsId") String processInsId);

    /**
     * @param messageModel 汇交数据实体
     * @return boolean 汇交结果
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 汇交数据实体
     */
    @PostMapping("/realestate-exchange/rest/v1.0/access/model")
    boolean autoAccessByMessageModel(@RequestBody MessageModel messageModel);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param date yyyy-mm-dd格式日期
     * @return void
     * @description 手动触发上报登簿日志
     */
    @GetMapping("/realestate-exchange/rest/v1.0/access/dbrz")
    boolean accessLog(@RequestParam(name = "date") String date);

    /**
     * @param date yyyy-mm-dd格式日期
     * @return void
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 手动触发上报登簿日志
     */
    @GetMapping("/realestate-exchange/rest/v1.0/access/dbrz/nt")
    boolean accessLogNt(@RequestParam(name = "date") String date, @RequestParam("qxdm") String qxdm);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 市级登簿日志手动上报
     * @date : 2023/1/6 15:21
     */
    @GetMapping("/realestate-exchange/rest/v1.0/access/dbrz/city")
    boolean accessLogCity(@RequestParam(name = "date") String date, @RequestParam("qxdm") String qxdm);

    /**
     * @param id
     * @return Map
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description (南通)获取登簿日志明细信息
     */
    @GetMapping("/realestate-exchange/rest/v1.0/access/dbrz/mx")
    Map<String, Object> dbrzMx(@RequestParam(name = "id") String id);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 登簿日志明细预览
     * @date : 2022/10/13 9:16
     */
    @GetMapping("/realestate-exchange/rest/v1.0/access/dbrz/mxyl")
    Map<String, Object> dbrzMxyl(@RequestParam(name = "date") String date, @RequestParam("qxdm") String qxdm, @RequestParam(value = "type", required = false) String type);


    /**
     * @param processInsId 工作流主键
     * @return void
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据工作流主键上交, 生成审核信息
     */
    @GetMapping("/realestate-exchange/rest/v1.0/access/shxxWithAccess")
    void shxxWithAccess(@RequestParam(name = "processInsId") String processInsId);

    /**
     * @param xmidList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新销账状态
     * @date : 2022/6/22 10:35
     */
    @PatchMapping("/realestate-exchange/rest/v1.0/access/gxxzzt")
    void updateXzzt(@RequestBody List<String> xmidList, @RequestParam(name = "xzzt", required = false) String xzzt);

    /**
     * @param idList 主键集合
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据主键更新销账状态
     * @date : 2022/9/29 9:02
     */
    @PatchMapping("/realestate-exchange/rest/v1.0/access/xzzt/id")
    void updateXzztById(@RequestBody List<String> idList, @RequestParam(name = "xzzt", required = false) String xzzt);

    /**
     * @param xmidList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新未接入表数据clzt
     * @date : 2022/6/30 13:54
     */
    @PatchMapping("/realestate-exchange/rest/v1.0/access/gxwjr")
    void updateWjrZt(@RequestBody List<String> xmidList, @RequestParam(name = "clzt", required = false) Integer clzt);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取redis 的值
     * @date : 2022/11/10 17:28
     */
    @GetMapping("/realestate-exchange/rest/v1.0/access/redisval")
    Boolean queryRedisValue(@RequestParam(name = "redisKey") String redisKey);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证权籍数据KTT的是否符合XSD校验
     * @date : 2022/11/18 17:54
     */
    @PostMapping("/realestate-exchange/rest/v1.0/access/qjsjjc")
    Object checkQjsj(@RequestBody List<QjsjjcDTO> qjsjjcDTOList);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 执行更新销账状态，和定时任务对比一个逻辑
     * @date : 2022/11/25 8:38
     */
    @GetMapping("/realestate-exchange/rest/v1.0/access/gxxzzt")
    void updateXzzt();


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 刷新配置表数据
     * @date : 2022/11/28 11:35
     */
    @GetMapping("/realestate-exchange/rest/v1.0/access/refresh/xsdjypz")
    void refreshXsdjyPz();

}
