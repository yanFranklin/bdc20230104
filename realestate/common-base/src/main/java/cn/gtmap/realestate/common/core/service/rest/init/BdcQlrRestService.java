package cn.gtmap.realestate.common.core.service.rest.init;

import cn.gtmap.realestate.common.core.domain.BdcDsQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcQlrGroupDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcQlrIdsDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcDsQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/11/8
 * @description 查询不动产权利人接口
 */
public interface BdcQlrRestService {
    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcQlrQO
     *@return List<bdcQLrDO>
     *@description 根据查询参数返回不动产权利人信息
     */
    @PostMapping(path = "/init/rest/v1.0/qlr/list")
    List<BdcQlrDO> listBdcQlr(@RequestBody BdcQlrQO bdcQlrQO);

    /**
     * 根据查询参数查询权利人信息，支持按模糊类型查询
     * @param bdcQlrQO 查询参数
     * @return List<bdcQlrDO> 权利人信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(path = "/init/rest/v1.0/qlr/list/mhlx")
    List<BdcQlrDO> listBdcQlrWithMhlx(@RequestBody BdcQlrQO bdcQlrQO);

    /**
     *@author <a href="mailto:chenyucehng@gtmap.cn">chenyucehng</a>
     *@param bdcDsQlrQO
     *@return List<bdcDsQLrDO>
     *@description 根据查询参数返回不动产第三权利人信息
     */
    @PostMapping(path = "/init/rest/v1.0/qlr/listDsQlr")
    List<BdcDsQlrDO> listBdcDsQlr(@RequestBody BdcDsQlrQO bdcDsQlrQO);

    /**
     * 根据 gzlslid 返回全部的不动产权利人信息
     * @param gzlslid 工作流实例 ID
     * @return List<bdcQLrDO> 权利人集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(path = "/init/rest/v1.0/qlr/list/{gzlslid}")
    List<BdcQlrDO> listAllBdcQlr(@PathVariable("gzlslid") String gzlslid, @RequestParam(value = "qlrlb",required = false) String qlrlb, @RequestParam(value = "djxl",required = false) String djxl);


    /**
     * 根据 slbh 返回全部的不动产权利人信息
     * @param slbh
     * @return List<bdcQLrDO> 权利人集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    @GetMapping(path = "/init/rest/v1.0/qlr/list/slbh/{slbh}")
    List<BdcQlrDO> listAllBdcQlrBySlbh(@PathVariable("slbh") String slbh, @RequestParam(value = "qlrlb",required = false) String qlrlb);


    /**
     * 根据 bdcdyh 返回全部的现势产权权利人
     * @param bdcdyh 不动产单元号
     * @return List<bdcQLrDO> 权利人集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    @GetMapping(path = "/init/rest/v1.0/qlr/list/xscq/{bdcdyh}")
    List<BdcQlrDO> listCqQlr(@PathVariable("bdcdyh") String bdcdyh);

    /**
     * 根据 qlridlist 批量删除权利人信息
     *
     * @param qlridlist 权利人id 集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @DeleteMapping(path = "/init/rest/v1.0/qlrs/qlrids")
    void deleteBatchQlr(@RequestBody List<String> qlridlist);

    /**
     * 根据 qlridlist 批量删除数据，新增 qlridlist.size() 条 bdcQlrDO 数据
     *
     * @param bdcQlrIdsDTO 权利人id DTO 对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PutMapping(path = "/init/rest/v1.0/qlrs/qlrids")
    List<BdcQlrDO> updateBatchQlr(@RequestBody BdcQlrIdsDTO bdcQlrIdsDTO);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcQlrDO
     *@description 插入新的权利人信息
     */
    @PostMapping(path = "/init/rest/v1.0/qlr")
    BdcQlrDO insertBdcQlr(@RequestBody BdcQlrDO bdcQlrDO);


    /**
     *@author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     *@param bdcDsQlrDO
     *@description 插入新的第三权利人信息
     */
    @PostMapping(path = "/init/rest/v1.0/insertDsQlr")
    BdcDsQlrDO insertBdcDsQlr(@RequestBody BdcDsQlrDO bdcDsQlrDO);

    /**
     *@author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     *@param bdcDsQlrDOList
     *@description 插入新的第三权利人信息
     */
    @PostMapping(path = "/init/rest/v1.0/insertDsQlr/pl")
    int insertBdcDsQlrPl(@RequestBody List<BdcDsQlrDO> bdcDsQlrDOList);

    /**
     *@author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     *@param bdcDsQlrDO
     *@description 更新权利人信息
     */
    @PutMapping(path = "/init/rest/v1.0/updateDsQlr")
    int updateBdcDsQlr(@RequestBody BdcDsQlrDO bdcDsQlrDO);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcQlrDO
     *@description 更新权利人信息
     */
    @PutMapping(path = "/init/rest/v1.0/qlr")
    int updateBdcQlr(@RequestBody BdcQlrDO bdcQlrDO);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param qlrid
     *@description 删除权利人信息
     */
    @DeleteMapping(path = "/init/rest/v1.0/qlr/{qlrid}")
    void deleteBdcQlr(@PathVariable("qlrid") String qlrid);

   /**
    * @param xmid 项目ID
    * @param qlrlb 权利人类别
    * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
    * @description 删除权利人
    */
    @DeleteMapping(path = "/init/rest/v1.0/qlrsbyxmid/{xmid}")
    void delQlr(@PathVariable("xmid") String xmid, @RequestParam(value = "qlrlb",required = false) String qlrlb);

    /**
     *@author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     *@param qlrid
     *@description 删除第三权利人信息
     */
    @DeleteMapping(path = "/init/rest/v1.0/DsQlr/{qlrid}")
    void deleteBdcDsQlr(@PathVariable("qlrid") String qlrid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据xmid和权利人类别删除第三权利人信息
     */
    @DeleteMapping(path = "/init/rest/v1.0/dsqlr/xmid/{xmid}")
    void delBdcDsQlr(@PathVariable("xmid") String xmid, @RequestParam(value = "qlrlb",required = false) String qlrlb);

    /**
     * 批量业务
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcQlrDO
     *@param processInsId
     * @param djxl 非必填
     *@description 批量插入新的权利人信息(包括权利人和义务人,根据权利人对象做判断)
     */
    @PostMapping(path = "/init/rest/v1.0/qlrs/list")
    List<BdcQlrDO> insertBatchBdcQlr(@RequestBody BdcQlrDO bdcQlrDO,@RequestParam("processInsId")String processInsId,@RequestParam(value = "djxl",required = false)String djxl);

    /**
     * 根据项目ID集合批量插入权利人
     *
     * @param bdcQlrIdsDTO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     */
    @PutMapping(path = "/init/rest/v1.0/qlrs/list/xmids")
    List<BdcQlrDO> insertBatchBdcQlrByXmids(@RequestBody BdcQlrIdsDTO bdcQlrIdsDTO);


    /**
     * 批量业务
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcQlrDO
     *@param processInsId
     * @param djxl 非必填
     *@description 批量更新权利人信息 根据权利人ID获取原权利人对象内的名称、证件号、类别去判定更新
     */
    @PutMapping(path = "/init/rest/v1.0/qlrs")
    List<BdcQlrDO> updateBatchBdcQlr(@RequestBody BdcQlrDO bdcQlrDO,@RequestParam("processInsId")String processInsId,@RequestParam(value = "djxl",required = false)String djxl);


    /**
     * 批量业务
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcQlrList
     *@param processInsId
     *@param qlrlb   1：权利人  2：义务人  空的话包含1和2
     *@description 批量更新权利人信息   删除原来类别人员进行新增操作
     */
    @PutMapping(path = "/init/rest/v1.0/qlrlist")
    List<BdcQlrDO> updateBatchBdcQlr(@RequestBody List<BdcQlrDO> bdcQlrList,@RequestParam("processInsId")String processInsId,@RequestParam(value = "qlrlb",required = false)String qlrlb);

    /**
     * 批量业务
     *
     * @param processInsId
     * @param qlrlb        1:权利人  2：义务人 不传递权利人和义务人全删掉
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 批量删除权利人信息
     */
    @DeleteMapping(path = "/init/rest/v1.0/qlrs/{processInsId}")
    void deleteBatchBdcQlr(@PathVariable("processInsId") String processInsId, @RequestParam(value = "qlrlb", required = false) String qlrlb);

    /**
     * 通过权利人名称和证件号批量删除流程内权利人信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param qlr
     *@param qlrzjh 非必填
     *@param qlrlb  1:权利人  2：义务人 不传递权利人和义务人全删掉
     * @param djxl  非必填
     *@param processInsId 工作流实例id
     *@description 通过权利人名称和证件号批量删除流程内权利人信息
     *@return 删除数量
     */
    @DeleteMapping(path = "/init/rest/v1.0/qlrs")
    void deleteBdcQlrsByQlrxx(@RequestParam("qlr") String qlr,@RequestParam(value = "qlrzjh",required = false) String qlrzjh ,@RequestParam("processInsId") String processInsId,@RequestParam(value = "qlrlb",required = false) String qlrlb,@RequestParam(value = "djxl",required = false) String djxl,@RequestBody List<String> xmidList);

    /**
     * @param gzlslid 工作流实例ID
     * @param qlrlb 权利人类别
     * @param djxl 登记小类
     * @return 权利人或义务人组织
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量发一本证的义务人或者权利人组织
     */
    @GetMapping(path = "/init/rest/v1.0/qlr/qlrsybzs")
    String queryQlrsYbzs(@RequestParam("gzlslid") String gzlslid, @RequestParam("qlrlb") String qlrlb,@RequestParam(value = "djxl",required = false) String djxl);

    /**
     * @param bdcDjxxUpdateQO 登记信息更新对象
     * @return  更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  批量更新不动产权利人
     */
    @PostMapping(path = "/init/rest/v1.0/qlr/jsonStr")
    int updateBatchBdcQlr(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO) throws Exception;

    /**
     * @param gzlslid 工作流实例
     * @return 返回根据证件号分组的权利人义务人分组对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 对权利人/义务人根据证件号进行分组
     */
    @PostMapping(path = "/init/rest/v1.0/qlr/groupQlrYwr/{gzlslid}")
    List<BdcQlrGroupDTO> groupQlrYwrByZjh(@PathVariable("gzlslid") String gzlslid, @RequestParam(value = "qlrlb",required = false) String qlrlb, @RequestParam(value = "djxl",required = false) String djxl,@RequestBody(required = false) String xmidList);

    /**
     * @param obj 权利人信息
     * @param type 修改类型：update:更新,insert:新增，DELETE:删除
     * @return 权利人信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 通过当前权利人获得与其组合同步修改的所有权利人信息
     */
    @PostMapping(path = "/init/rest/v1.0/qlr/zhqlr")
    List<JSONObject> listZhBdcQlr(@RequestBody JSONObject obj, @RequestParam(value = "type") String type);

    /**
     * @param obj 权利人信息
     * @param type 修改类型：update:更新,insert:新增，DELETE:删除
     * @return 权利人信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量权利人信息 -- 当前仅用于更新第三权利人,对上一手下一手的权利人不做处理
     */
    @PostMapping(path = "/init/rest/v1.0/qlr/plqlr")
    List<BdcQlrDO> listPlBdcQlr(@RequestBody JSONObject obj, @RequestParam(value = "type") String type);

    /**
     * 根据条件查询权利人信息
     *
     * @param bdcQlrDO
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @PostMapping(path = "/init/rest/v1.0/qlr/list/search")
    List<BdcQlrDO> listBdcQlrByCondition(@RequestBody BdcQlrDO bdcQlrDO);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/2/7 10:29
     */
    @DeleteMapping(path = "/init/rest/v1.0/dsqlrs")
    void deleteDsQlrsByQlrxx(@RequestParam("qlr") String qlr, @RequestParam(value = "qlrzjh", required = false) String qlrzjh, @RequestParam("processInsId") String processInsId, @RequestParam(value = "qlrlb", required = false) String qlrlb, @RequestParam(value = "djxl", required = false) String djxl, @RequestBody(required = false) List<String> xmidList);

    /**
     * @param qllx 权利类型
     * @param qlrlb 权利人类别
     * @return 权利人特征
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取权利人特征默认值
     */
    @GetMapping(path = "/init/rest/v1.0/qlr/qlrtz/mrz")
    Integer getQlrtzMrz(@RequestParam(value = "qllx")Integer qllx,@RequestParam(value = "qlrlb")String qlrlb);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description
      */
    @PostMapping(path = "/init/rest/v1.0/qlr/list/xmidList")
    List<BdcQlrDO> listBdcQlrByXmidList(@RequestBody List<String> xmidList, @RequestParam(value = "qlrlb",required = false) String qlrlb);

    /**
     * 查询证书（证明）关联的权利人（义务人）信息
     * @param zsid 证书ID
     * @param qlrlb 权利人类别
     * @return {List} 权利人信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping(path = "/init/rest/v1.0/qlr/zszm")
    List<BdcQlrDO> listBdcQlrByZsid(@RequestParam("zsid")String zsid, @RequestParam("qlrlb")String qlrlb);
}
