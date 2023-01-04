package cn.gtmap.realestate.common.core.service.rest.register;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/27
 * @description 审核登簿工作流rest服务接口
 */
public interface RegisterWorkflowRestService {
    /**
     * @param processInsId    工作流实例id
     * @param currentUserName 当前账户
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 登簿时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/dbxxAndQszt", method = RequestMethod.POST)
    void updateBdcDbxxAndQsztSyncQj(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName);

    /**
     * @param processInsId    工作流实例id
     * @param currentUserName 当前账户
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 登簿时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/dbxxAndQszt/{processInsId}/{currentUserName}", method = RequestMethod.POST)
    void updateBdcDbxxAndQsztSyncQj2(@PathVariable(value = "processInsId") String processInsId, @PathVariable(value = "currentUserName") String currentUserName);

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 登簿退回登簿信息和权属状态
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/dbxxAndQszt", method = RequestMethod.DELETE)
    void revertBdcDbxxAndQsztSyncQj(@RequestParam(value = "processInsId") String processInsId);

    /**
     * @param gzlslid 工作流实例ID
     * @param syncQj  是否同步权籍
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 退回登簿信息和权属状态，调用端可控制是否更新权籍信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/dbxxAndQszt/{gzlslid}", method = RequestMethod.PUT)
    void revertBdcDbxxAndQsztSyncQj(@PathVariable(value = "gzlslid") String gzlslid, @RequestParam(value = "syncQj") boolean syncQj);

    /**
     * @param processInsId    工作流实例ID
     * @param currentUserName 当前用户名
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 登簿时更新部分解封解压的规则例外审核状态
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/updateBdcGzlwShzt", method = RequestMethod.POST)
    void updateBdcGzlwShzt(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName);


    /**
     * @param processInsId 工作流实例ID
     * @param qszt         权属状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新原注销权利的登簿信息和权属状态
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/dbxx/yzxql", method = RequestMethod.PUT)
    void updateYzxqlDbxxAndQszt(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "qszt") Integer qszt);
    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   processInsId 工作流实例ID
     * @description  更新案件状态为2已完成状态
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/dbxx/ajzt", method = RequestMethod.PUT)
    void changeAjzt(@RequestParam(value = "processInsId") String processInsId);

    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @param processInsId 工作流实例ID
     * @description 同步权籍不动产单元状态(不包含锁定)
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/qjzt", method = RequestMethod.PUT)
    void synQjBdcdyzt(@RequestParam("processInsId") String processInsId);

    /**
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @param processInsId 工作流实例ID
     * @description 同步权籍不动产单元状态2(不包含锁定)
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/qjzt/{processInsId}", method = RequestMethod.PUT)
    void synQjBdcdyzt2(@PathVariable(value = "processInsId") String processInsId);

    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @param processInsId
     * @return
     * @description 同步权籍基本信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/qjjbxx", method = RequestMethod.PUT)
    void synQjJbxx(@RequestParam("processInsId") String processInsId);

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 合并 同步权籍基本信息和不动产单元状态（不包含锁定）服务
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/qjjbxxAndBdcdyzt", method = RequestMethod.POST)
    void synQjJbxxAndBdcdyzt(@RequestParam(name = "processInsId") String processInsId);

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 招行流程抵押流程增加回传功能
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/zsyhDyHcfw", method = RequestMethod.POST)
    void zsyhDyHcfw(@RequestParam(name = "processInsId") String processInsId);

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 招行流程抵押注销流程增加回传功能
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/zsyhDyzxHcfw", method = RequestMethod.POST)
    void zsyhDyzxHcfw(@RequestParam(name = "processInsId") String processInsId);


    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 现省直房改房上市交易成功后，把不动产登记的数据再推送给房改办进行登记
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/djSzfgb", method = RequestMethod.POST)
    void djSzfgb(@RequestParam(name = "processInsId") String processInsId);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [processInsId]
     * @return void
     * @description 更新当前流程所注销权利的附记 通过工作流配置
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/updateZxqlfj", method = RequestMethod.POST)
    void updateZxqlfj(@RequestParam(name = "processInsId") String processInsId);

    /**
     * 撤销流程，修改权属状态和案件状态
     *
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/cancelProcess", method = RequestMethod.GET)
    void cancelProcessQsztAndAjzt(@RequestParam(name = "processInsId") String processInsId);


    /**
     * @param processInsId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 创建档案交接流程
     * @date : 2021/6/9 9:25
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/cjdajj", method = RequestMethod.POST)
    void cjDajjLc(@RequestParam(name = "processInsId") String processInsId, @RequestParam(name = "currentUserName", required = false) String currentUserName) throws Exception;


    /**
     * @param processInsId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 档案交接流程转发事件
     * @date : 2021/6/15 9:58
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/zfdajj", method = RequestMethod.POST)
    void zfDajj(@RequestParam(name = "processInsId") String processInsId, @RequestParam(name = "currentUserName") String currentUserName, @RequestParam(name = "taskId") String taskId);


    /**
     * @param processInsId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 档案交接流程退回事件
     * @date : 2021/6/15 9:58
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/thdajj", method = RequestMethod.POST)
    void thDajj(@RequestParam(name = "processInsId") String processInsId, @RequestParam(name ="opinion", required = false) String opinion);

    /**
     * @param processInsId 工作流实例ID
     * @param currentUserName
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 档案接收节点办结时保存档案交接接收人
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/workflow/jsr")
    void saveDajjJsr(@RequestParam(name = "processInsId") String processInsId, @RequestParam(name = "currentUserName") String currentUserName);

    /**
     * @param processInsId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 档案交接流程退回事件
     * @date : 2021/6/15 9:58
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/bjdajj", method = RequestMethod.POST)
    void bjDajj(@RequestParam(name = "processInsId") String processInsId);

    /**
     * 变更登记（带抵押）流程登簿时候追加抵押证明附记内容、重新生成抵押证明电子证照
     * @param processInsId    工作流实例id
     * @param currentUserName 当前用户
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/bgddy", method = RequestMethod.POST)
    void appendFjAndRebuildDzzz(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName", required = false) String currentUserName);

    /**
     * 推送公告台账
     *
     * @param processInsId 工作流实例id
     * @author <a href ="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/pushBdcGgtz", method = RequestMethod.POST)
    void pushBdcGgtz(@RequestParam(name = "processInsId") String processInsId);

    /**
     * @param processInsId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新锁定状态为锁定-1
     * @date : 2021/8/19 15:00
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/sfcjsdzt/sd", method = RequestMethod.POST)
    void updateSdztSd(@RequestParam(name = "processInsId") String processInsId);

    /**
     * @param processInsId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新锁定状态为正常 0
     * @date : 2021/8/19 15:00
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/sfcjsdzt/js", method = RequestMethod.POST)
    void updateSdztJs(@RequestParam(name = "processInsId") String processInsId);

    /**
     * @param processInsId
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取行政代码
     * @date : 2021/9/27
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/getxzdm", method = RequestMethod.GET)
    Map<String, String> getXzdm(@RequestParam(name = "processInsId") String processInsId);

    /**
     * @param processInsId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新锁定状态为正常 0-用于司法裁决流程删除
     * @date : 2021/9/29 11:27
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/sfcjsdzt/js/lcsc", method = RequestMethod.DELETE)
    void updateSdztJsForDelete(@RequestParam(name = "processInsId") String processInsId);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 撤销登记单元锁定状态改为解锁
     * @date : 2022/9/1 13:52
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/workflow/sfcjsdzt/js/cxdj")
    void updateCxdjDysdzt(@RequestParam(name = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName);

    /**
     * @param processInsId 工作流实例id
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 冻结/解冻时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djdbxx/qszt", method = RequestMethod.POST)
    void updateQsztSyncQjForDj(@RequestParam(value = "processInsId") String processInsId);

    /**
     * @param processInsId  工作流实例id
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 核定该宗地上每一户室所占份额，户室所占份额=户室实测建筑面积/宗地上所有已登记的实测总建筑面积
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/hdhsfe", method = RequestMethod.POST)
    void syncHdhsfe(@RequestParam(value = "processInsId") String processInsId);

    /**
      * @param processInsId 工作流实例ID
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 登簿将预查封转为现势查封
      */
    @PostMapping(value = "/realestate-register/rest/v1.0/workflow/cflx")
    void changeYcfToCf(@RequestParam(value = "processInsId") String processInsId);

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 登簿将预查封转为现势查封,生成新的查封记录，注销原有的预查封
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/workflow/ycftocf")
    void initCfFromYcf(@RequestParam(value = "processInsId") String processInsId);


    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 商品房首次登记更新外联证书的权利附记
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/workflow/updateSpfscdjWlzsQlfj")
    void updateSpfscdjWlzsQlfj(@RequestParam(value = "processInsId") String processInsId) throws Exception;

    /**
     * @param processInsId 工作流实例ID
     * @return 返回是否自动转发或自动办结，满足要求返回code=1
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 自动转发或自动办结验证地址
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/workflow/zdzfzdbj")
    Map<String, String> checkZdzfzdbj(@RequestParam(name = "processInsId") String processInsId);

    /**
     * @param processInsId 工作流实例ID
     * @param currentNodeName 当前自动转发节点名称，如A节点自动转发到B节点，即为B节点名称
     * @return 返回指定人员用户名code=0,username=
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取节点自动转发指定人员
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/workflow/zdzf/slr")
    Map<String, String> getZdzfSlr(@RequestParam(name = "processInsId") String processInsId,@RequestParam(name = "currentNodeName") String currentNodeName);
    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 删除信息补录
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/workflow/deleteXxbl")
    void deleteXxbl(@RequestParam(value = "processInsId") String processInsId) throws Exception;

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 恢复信息补录
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/workflow/hfXxbl")
    void hfXxbl(@RequestParam(value = "processInsId") String processInsId) throws Exception;

    /**
     * @param processInsId 工作流实例ID
     * @param currentNodeName 节点名称
     * @return 返回指定人员用户名code=0,username=
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取外网申请退回件原审核人员
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/workflow/wwsq/yshry")
    Map<String, String> getWwwsqYshry(@RequestParam(name = "processInsId") String processInsId,@RequestParam(name = "currentNodeName") String currentNodeName);

    /**
     * @param processInsId 工作流实例ID
     * @param currentUserName 当前用户名
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新外网件登簿人
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/workflow/wwsq/dbr")
    void updateWwsqDbr(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName);

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
     * @description 办理在建建筑抵押时，更新户室所在的土地证附记
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/workflow/updateZjjzTdzfj")
    void updateZjjzTdzfj(@RequestParam(value = "processInsId") String processInsId) throws Exception;

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 登簿更新关联单元号土地抵押释放信息
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/workflow/tddysfxx")
    void updateTddysfxx(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName) throws Exception;


    /**
     * 登记系统房屋发生转移类登记后，将合同的状态修改为注销
     *
     * @param processInsId
     * @param currentUserName
     * @throws Exception
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/workflow/updateHtbazt")
    void updateHtbazt(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName) throws Exception;

    /**
     * @param processInsId  工作流实例ID
     * @param spjg 审批结果 通过1/不通过0,删除传0，默认为1
     * @param spjd 审批节点  1受理 2办结
     * @param reason 删除原因
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 推送工改系统办件状态
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/workflow/ggxt/bjzt")
    void tzggxtBjzt(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "spjg",required = false) String spjg, @RequestParam(value = "spjd",required = false) String spjd, @RequestParam(value = "reason", required = false) String reason) throws Exception;

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 推送工改系统电子证照信息
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/workflow/ggxt/dzzzxx")
    void tsggztDzzzxx(@RequestParam(value = "processInsId") String processInsId) throws Exception;

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 外联产权，产权选择注销，新流程在登簿的时候将产权上的现势限制权利挂到当前新产权上，单元号变更为新产权的单元号
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/workflow/wlcqzx/xzql")
    void wlcqzx(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName) throws Exception;

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 登簿时更新林权流转状态
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/workflow/lqlzzt")
    void updateLqlzzt(@RequestParam(value = "processInsId") String processInsId) throws Exception;




}
