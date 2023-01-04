package cn.gtmap.realestate.common.core.service.rest.init;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.init.BdcDyaqDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcQlxxRequestDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/11/8
 * @description 业务初始化权利信息相关接口
 */
public interface BdcQllxRestService {
    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   qllx 权利类型
     * @return  {BdcQl} 不动产权利空对象
     * @description 通过qllx确认权利
     */
    @GetMapping(value = "/init/rest/v1.0/qrqllx/{qllx}")
    BdcQl makeSureQllx(@PathVariable("qllx") String qllx);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   xmid 项目ID
     * @return  {BdcQl} 不动产权利
     * @description 通过项目id查询权利基本信息
     */
    @GetMapping(value = "/init/rest/v1.0/qlxx/{xmid}")
    BdcQl queryQlxx(@PathVariable("xmid") String xmid);

    /**
     * @param xmid 项目ID
     * @return {BdcQl} 不动产权利
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  通过项目ID查询原权利基本信息
     */
    @GetMapping(value = "/init/rest/v1.0/qlxx/yqlxx/{xmid}")
    BdcQl queryBdcYqlxx(@PathVariable("xmid") String xmid);

    /**
     * @param xmids 项目ID
     * @return {BdcQl} 不动产权利
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  通过项目ID查询原权利基本信息
     */
    @GetMapping(value = "/init/rest/v1.0/qlxx/yqlxxs")
    List<BdcQl> queryBdcYqlxxs(@RequestParam("xmids") String xmids);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   slbh 受理编号
     * @return  {List} 权利信息
     * @description 获取当前流程生成的所有权利信息
     */
    @GetMapping(value = "/init/rest/v1.0/qlxx/list/slbh")
    List<BdcQl> listQlxxBySlbh(@RequestParam(value = "slbh") String slbh);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   processInsId 工作流实例ID
     * @return  {List} 权利信息
     * @description 获取当前流程生成的所有权利信息
     */
    @GetMapping(value = "/init/rest/v1.0/qlxx/list/processInsId")
    List<BdcQl> listQlxxByProcessInsId(@RequestParam(value = "processInsId") String processInsId);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   processInsId 工作流实例ID
     * @return  {List} 权利类型
     * @description 获取当前流程生成的所有权利类型
     */
    @GetMapping(value = "/init/rest/v1.0/qllx/list/processInsId")
    List<String> listQllxByProcessInsId(@RequestParam(value = "processInsId") String processInsId);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   qlid 权利id
     * @return  {List} 项目内多幢项目信息
     * @description 获取当前房地产权的项目内多幢项目信息(根据幢号和总层数排序)
     */
    @GetMapping(value = "/init/rest/v1.0/qlxx/fdcqxm/{qlid}/list")
    List<BdcFdcqFdcqxmDO> listFdcqXm(@PathVariable("qlid") String qlid);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   qlid 权利id
     * @return  {List} 建筑物区分所有权业主共有部分登记信息_共有部分信息
     * @description 获取建筑物区分所有权业主共有部分登记信息_共有部分信息
     */
    @GetMapping(value = "/init/rest/v1.0/qlxx/fdcq3/gyxx/{qlid}/list")
    List<BdcFdcq3GyxxDO> listFdcq3Gyxx(@PathVariable("qlid") String qlid);


    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   slbh 受理编号
     * @return {List} 注销权利信息
     * @description 获取当前流程所注销的所有权利信息
     */
    @GetMapping(value = "/init/rest/v1.0/zxqlxx/list/slbh")
    List<BdcQl> listZxQlxxBySlbh(@RequestParam(value = "slbh") String slbh);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   processInsId 工作流实例ID
     * @return {List} 注销权利信息
     * @description 获取当前流程所注销的所有权利信息
     */
    @GetMapping(value = "/init/rest/v1.0/zxqlxx/list/processInsId")
    List<BdcQl> listZxQlxxByProcessInsId(@RequestParam(value = "processInsId") String processInsId);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   processInsId 工作流实例ID
     * @return {List} 注销权利类型
     * @description 获取当前流程所注销的所有权利类型
     */
    @GetMapping(value = "/init/rest/v1.0/zxqllx/list/processInsId")
    List<String> listZxQllxByProcessInsId(@RequestParam(value = "processInsId") String processInsId);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   slbh 受理编号
     * @return {List} 注销权利信息
     * @description 获取当前流程需还原的已注销的所有权利信息
     */
    @GetMapping(value = "/init/rest/v1.0/hyyzxql/list/slbh")
    List<BdcQl> listHyYzxQlxxBySlbh(@RequestParam(value = "slbh") String slbh);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   processInsId 工作流实例ID
     * @return {List} 注销权利信息
     * @description 获取当前流程需还原的已注销的所有权利信息
     */
    @GetMapping(value = "/init/rest/v1.0/hyyzxql/list/processInsId")
    List<BdcQl> listHyYzxQlxxByProcessInsId(@RequestParam(value = "processInsId") String processInsId);


    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   bdcTdsyqDO 权利信息
     * @return 更新数量
     * @description 修改BDC_TDSYQ信息
     */
    @PutMapping(value = "/init/rest/v1.0/qlxx/tdsyq")
    int updateTdsyq(@RequestBody BdcTdsyqDO bdcTdsyqDO);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   bdcJsydsyqDO 权利信息
     * @return 更新数量
     * @description 修改BDC_JSYDSYQ信息
     */
    @PutMapping(value = "/init/rest/v1.0/qlxx/jsydsyq")
    int updateJsydsyq(@RequestBody BdcJsydsyqDO bdcJsydsyqDO);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   bdcFdcqDO 权利信息
     * @return 更新数量
     * @description 修改BDC_FDCQ信息
     */
    @PutMapping(value = "/init/rest/v1.0/qlxx/fdcq")
    int updateFdcq(@RequestBody BdcFdcqDO bdcFdcqDO);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   bdcTdcbnydsyqDO 权利信息
     * @return 更新数量
     * @description 修改BDC_TDCBNYDSYQ信息
     */
    @PutMapping(value = "/init/rest/v1.0/qlxx/tdcbnydsyq")
    int updateTdcbnydsyq(@RequestBody BdcTdcbnydsyqDO bdcTdcbnydsyqDO);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   bdcLqDO 权利信息
     * @return 更新数量
     * @description 修改BDC_LQ信息
     */
    @PutMapping(value = "/init/rest/v1.0/qlxx/lq")
    int updateLq(@RequestBody BdcLqDO bdcLqDO);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   bdcHysyqDO 权利信息
     * @return 更新数量
     * @description 修改BDC_HYSYQ信息
     */
    @PutMapping(value = "/init/rest/v1.0/qlxx/hysyq")
    int updateHysyq(@RequestBody BdcHysyqDO bdcHysyqDO);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   bdcGjzwsyqDO 权利信息
     * @return 更新数量
     * @description 修改BDC_GJZWSYQ信息
     */
    @PutMapping(value = "/init/rest/v1.0/qlxx/gjzwsyq")
    int updateGjzwsyq(@RequestBody BdcGjzwsyqDO bdcGjzwsyqDO);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   bdcDyiqDO 权利信息
     * @return 更新数量
     * @description 修改BDC_DYIQ信息
     */
    @PutMapping(value = "/init/rest/v1.0/qlxx/dyiq")
    int updateDyiq(@RequestBody BdcDyiqDO bdcDyiqDO);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   bdcQtxgqlDO 权利信息
     * @return 更新数量
     * @description 修改BDC_QTXGQL信息
     */
    @PutMapping(value = "/init/rest/v1.0/qlxx/qtxgql")
    int updateQtxgql(@RequestBody BdcQtxgqlDO bdcQtxgqlDO);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   bdcFdcq3DO 权利信息
     * @return 更新数量
     * @description 修改BDC_FDCQ3信息
     */
    @PutMapping(value = "/init/rest/v1.0/qlxx/fdcq3")
    int updateFdcq3(@RequestBody BdcFdcq3DO bdcFdcq3DO);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   bdcDyaqDO 权利信息
     * @return 更新数量
     * @description 修改BDC_DYAQ信息
     */
    @PutMapping(value = "/init/rest/v1.0/qlxx/dyaq")
    int updateDyaq(@RequestBody BdcDyaqDO bdcDyaqDO);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   bdcYgDO 权利信息
     * @return 更新数量
     * @description 修改BDC_YG信息
     */
    @PutMapping(value = "/init/rest/v1.0/qlxx/yg")
    int updateYg(@RequestBody BdcYgDO bdcYgDO);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   bdcYyDO 权利信息
     * @return 更新数量
     * @description 修改BDC_YY信息
     */
    @PutMapping(value = "/init/rest/v1.0/qlxx/yy")
    int updateYy(@RequestBody BdcYyDO bdcYyDO);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   bdcCfDO 权利信息
     * @return 更新数量
     * @description 修改BDC_CF信息
     */
    @PutMapping(value = "/init/rest/v1.0/qlxx/cf")
    int updateCf(@RequestBody BdcCfDO bdcCfDO);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   bdcFdcqFdcqxmDO 权利信息
     * @return 更新数量
     * @description 修改BDC_FDCQ_FDCQXM信息
     */
    @PutMapping(value = "/init/rest/v1.0/qlxx/fdcqxm")
    int updateFdcqxm(@RequestBody BdcFdcqFdcqxmDO bdcFdcqFdcqxmDO);

    /**
     * 根据权利ID批量更新房地产权（项目内多幢）项目信息
     * @param qlid 权利ID
     * @param bdcFdcqFdcqxmDO 权利信息
     * @return 更新数量
     * @author  <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PutMapping(value = "/init/rest/v1.0/qlxx/fdcqxm/{qlid}")
    int updateFdcqxmByQlid(@PathVariable(value = "qlid") String qlid, @RequestBody BdcFdcqFdcqxmDO bdcFdcqFdcqxmDO);

    /**
     * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param   bdcFdcq3GyxxDO 权利信息
     * @return 更新数量
     * @description 修改BDC_FDCQ3_GYXX信息
     */
    @PutMapping(value = "/init/rest/v1.0/qlxx/fdcq3/gyxx")
    int updateFdcq3Gyxx(@RequestBody BdcFdcq3GyxxDO bdcFdcq3GyxxDO);


    /**
     * 根据项目ID获取附属设施集合数据
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param xmid
     *@return List<BdcFwfsssDO> 附属设施集合
     *@description
     */
    @GetMapping(value = "/init/rest/v1.0/{xmid}/fsss/list")
    List<BdcFwfsssDO> listFwfsss(@PathVariable(value = "xmid") String xmid);

    /**
     * @param bdcDjxxUpdateQO 登记信息更新对象
     * @return  更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  批量更新不动产权利
     */
    @PostMapping(path = "/init/rest/v1.0/qlxx/jsonStr")
    int updateBatchBdcQl(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO) throws Exception;


    /**
     * 根据bdcXmQO获取不动产项目集合数据
     *@author <a href="mailto:yanjiaqiang@gtmap.cn">yanjiaqiang</a>
     *@param bdcXmQO
     *@return List<BdcXmDO>
     *@description
     */
    @PostMapping(value = "/init/rest/v1.0/getBdcXmByQllx")
    List<BdcXmDO> getBdcXmByQllx(@RequestBody BdcXmQO bdcXmQO);

    /**
     * @author  <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @param   xmidList 项目ID
     * @return  {List} 权利信息
     * @description 根据项目ID获取生成的所有权利信息
     */
    @PostMapping(value = "/init/rest/v1.0/qlxx/list/xmids")
    List<BdcQl> listQlxxByXmids(@RequestBody List<String> xmidList);

    /**
     * @param bdcQl 权利信息
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新同一个证书的权利附记
     */
    @PutMapping(value = "/init/rest/v1.0/qlxx/zsqlfj")
    int updateBdcZsQlFj(@RequestBody BdcQl bdcQl);

    /**
     * 根据xmid获取规划用途 先查权利表，非房地产权，再取xm表
     * @param xmid
     * @return
     */
    @GetMapping(value = "/init/rest/v1.0/qlxx/getGhytByXmid/{xmid}")
    String  getGhytByXmid(@PathVariable("xmid") String xmid);

    /**
     * @param processInsId
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 同步抵押权的bdbzzqse到zgzqse字段
     */
    @PutMapping("/init/rest/v1.0/updateBdcDyaqZgzqse")
    void updateBdcDyaqZgzqse(@RequestParam(value = "processInsId") String processInsId);

    /**
     * @param xmids
     * @return {List} 限制信息
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 根据不动产单元号获取限制信息
     */
    @PostMapping(value = "/init/rest/v1.0/qlxx/xzxx/list/xmids")
    List<BdcQl> listXzxxByXmids( @RequestBody List<String> xmids);

    /**
     * @param bdcdyhs 项目ID
     * @return {List} 限制信息
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 根据不动产单元号获取限制信息
     */
    @PostMapping(value = "/init/rest/v1.0/qlxx/list/bdcdyhs")
    List<BdcQl> listXzxxByBdcdyhs(@RequestBody List<String> bdcdyhs);

    /**
     * 更新在建建筑物抵押范围
     * 土地证坐落+所有不动产单元的房间号（顿号分隔）+房屋及对应的建设用地使用权
     *
     * @param xmidList xmidList
     * @param gzldyid  gzldyid
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/init/rest/v1.0/zjjwdyfw")
    int updateZjjwdyfw(@RequestBody List<String> xmidList, @RequestParam(value = "gzldyid") String gzldyid);

    /**
     *
     * @param zl
     * @return
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 根据坐落获取房地产权信息
     */
    @GetMapping("/init/rest/v1.0/queryFdcqByZl")
    Map queryFdcqByZl(@RequestParam(value = "zl", required = false) String zl);

    /**
     * 查询权利人权利信息
     * @param bdcQlxx 查询参数
     * @return List<BdcQl> 权利信息
     */
    @RequestMapping(value = "/init/rest/v1.0/qlr/qlxx",method = RequestMethod.POST)
    List<BdcQl> getBdcQlxxByQlr(@RequestBody BdcQlxxRequestDTO bdcQlxx);



}
