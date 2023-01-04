package cn.gtmap.realestate.supervise.web;

import cn.gtmap.gtc.workflow.clients.manage.ProcessDefinitionClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.supervise.core.domain.*;
import cn.gtmap.realestate.supervise.core.dto.BdcLfSqxxDTO;
import cn.gtmap.realestate.supervise.core.dto.BdcLfYcbjyjCkbjDTO;
import cn.gtmap.realestate.supervise.core.dto.BdcLfYcbjyjCqbjDTO;
import cn.gtmap.realestate.supervise.core.dto.BdcLfYcbjyjFgzsjbjDTO;
import cn.gtmap.realestate.supervise.core.qo.*;
import cn.gtmap.realestate.supervise.service.LfYcbjyjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/09/22
 * @description 廉防1：异常办件预警服务类
 */
@RestController
@RequestMapping("/rest/v1.0/ycbjyj")
public class LfYcbjyjRestController extends BaseController{
    @Autowired
    private LfYcbjyjService lfYcbjyjService;

    @Autowired
    private ProcessDefinitionClient processDefinitionClient;


    /**
     * 分页查询超期办件数据
     * @param pageable 分页参数
     * @param cqbjQO 查询参数
     * @return {Page} 超期办件信息
     */
    @GetMapping("/cqbj")
    public Object listCqbjData(@LayuiPageable Pageable pageable, LfCqbjQO cqbjQO) {
        Page<BdcLfYcbjyjCqbjDTO> cqbjData = lfYcbjyjService.listCqbjData(pageable, cqbjQO);
        return super.addLayUiCode(cqbjData);
    }

    /**
     * 分页查询超快办件数据
     * @param pageable 分页参数
     * @param ckbjQO 查询参数
     * @return {Page} 超快办件信息
     */
    @GetMapping("/ckbj")
    public Object listCkbjData(@LayuiPageable Pageable pageable, LfCkbjQO ckbjQO) {
        Page<BdcLfYcbjyjCkbjDTO> ckbjData = lfYcbjyjService.listCkbjData(pageable, ckbjQO);
        return super.addLayUiCode(ckbjData);
    }

    /**
     * 分页查询非工作时间办件数据
     * @param pageable 分页参数
     * @param fgzsjbjQO 查询参数
     * @return {Page} 非工作时间办件信息
     */
    @GetMapping("/fgsjbj")
    public Object listFgsjbjData(@LayuiPageable Pageable pageable, LfFgzsjbjQO fgzsjbjQO) {
        Page<BdcLfYcbjyjFgzsjbjDTO> fgzsjbjData = lfYcbjyjService.listFgsjbjData(pageable, fgzsjbjQO);
        return super.addLayUiCode(fgzsjbjData);
    }

    /**
     * 查询异常办件审核信息
     * @param type 异常办件类型
     * @param id 主键ID
     * @return 异常信息
     */
    @GetMapping("/{type}/{id}")
    public Object queryYcbjShxx(@PathVariable("type")String type, @PathVariable("id")String id) {
        return lfYcbjyjService.queryYcbjShxx(type, id);
    }

    /**
     * 保存异常办件审核信息
     * @param data 审核信息数据
     * @param type 异常办件类型
     * @return 异常信息ID
     */
    @PostMapping("/{type}/shxx")
    public String saveYcbjShxx(@RequestBody Map<String, Object> data, @PathVariable("type")String type) {
        return lfYcbjyjService.saveYcbjShxx(type, data);
    }

    /**
     * 获取所有流程
     * @return
     */
    @GetMapping("/process")
    private List<ProcessDefData> listProcess() {
        return processDefinitionClient.getAllProcessDefData();
    }

    /**
     * 获取异常情况申报信息
     * @param processInsId 工作流实例ID
     * @param id 申报信息ID
     * @return {BdcLfYcqksbDO} 申报信息
     */
    @GetMapping("/ycqksb/xmxx")
    public BdcLfYcqksbDO getYcqksb(@RequestParam(value = "processInsId", required = false)String processInsId, @RequestParam(value = "id", required = false) String id) {
        return lfYcbjyjService.getYcqksb(processInsId, id);
    }

    /**
     * 保存申报信息
     * @param sbxx 申报信息
     * @return {String} 申报信息ID
     */
    @PostMapping("/ycqksb")
    public String saveYcqksb(@RequestBody BdcLfYcqksbDO sbxx) {
        return lfYcbjyjService.saveYcqksb(sbxx);
    }

    /**
     * 异常申报信息查询（分页台账）
     * @param pageable 分页参数
     * @param lfYcqksbQO 业务查询参数
     * @return 异常申报信息
     */
    @GetMapping("/ycqksb/page")
    public Object listYcqksbTableData(@LayuiPageable Pageable pageable, LfYcqksbQO lfYcqksbQO) {
        Page<BdcLfYcqksbDO> ycqksbTableData = lfYcbjyjService.listYcqksbTableData(pageable, lfYcqksbQO);
        return super.addLayUiCode(ycqksbTableData);
    }

    /**
     * 分页查询异常原因数据
     * @param pageable 分页参数
     * @param ycyyQO 查询参数
     * @return {Page} 异常原因数据
     */
    @GetMapping("/ycyy")
    public Object listYcyyData(@LayuiPageable Pageable pageable, LfYcyyQO ycyyQO) {
        Page<BdcLfYcbjyjYcyyDO> ycyyData = lfYcbjyjService.listYcyyData(pageable, ycyyQO);
        return super.addLayUiCode(ycyyData);
    }

    /**
     * 查询指定异常原因记录
     * @param id 异常原因ID
     * @return 异常原因
     */
    @GetMapping("/ycyy/{id}")
    public BdcLfYcbjyjYcyyDO getYcxxById(@PathVariable("id") String id) {
        return lfYcbjyjService.getYcxxById(id);
    }

    /**
     * 保存异常原因
     * @param ycyyDO 异常原因
     * @return {String} 异常原因ID
     */
    @PostMapping("/ycyy")
    public String saveYcxx(@RequestBody BdcLfYcbjyjYcyyDO ycyyDO) {
        return lfYcbjyjService.saveYcxx(ycyyDO);
    }

    /**
     * 删除异常原因
     * @param ycyyDOList 异常原因
     * @return {Integer} 删除记录
     */
    @DeleteMapping("/ycyy")
    public Integer deleteYcxxs(@RequestBody List<BdcLfYcbjyjYcyyDO> ycyyDOList) {
        return lfYcbjyjService.deleteYcxxs(ycyyDOList);
    }

    /**
     * 分页查询项目数据
     * @param pageable 分页参数
     * @param bdcXmQO 查询参数
     * @return {Page} 异常原因数据
     */
    @GetMapping("/xmxx")
    public Object listXmxxData(@LayuiPageable Pageable pageable, BdcXmQO bdcXmQO) {
        Page<BdcXmDO> xmxxData = lfYcbjyjService.listXmxxData(pageable, bdcXmQO);
        return super.addLayUiCode(xmxxData);
    }
}
