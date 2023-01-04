package cn.gtmap.realestate.supervise.web;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.supervise.core.domain.BdcLfDczjjgZgqkDO;
import cn.gtmap.realestate.supervise.core.domain.BdcLfDczjjgZjxxDO;
import cn.gtmap.realestate.supervise.core.qo.LfZjxxQO;
import cn.gtmap.realestate.supervise.service.LfDczjjgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/09/07
 * @description 廉防6：督查质检监管服务类
 */
@RestController
@RequestMapping("/rest/v1.0/dczjjg")
public class LfDczjjgRestController extends BaseController{
    @Autowired
    private LfDczjjgService lfDczjjgService;


    /**
     * 质检信息查询（分页台账）
     * @param pageable 分页参数
     * @param zjxxQO 业务查询参数
     * @return 质检信息
     */
    @GetMapping("/zjxx")
    public Object listZjxxTableData(@LayuiPageable Pageable pageable, LfZjxxQO zjxxQO) {
        Page<BdcLfDczjjgZjxxDO> zjxxTableData = lfDczjjgService.listZjxxTableData(pageable, zjxxQO);
        return super.addLayUiCode(zjxxTableData);
    }

    /**
     * 查询指定质检信息记录
     * @param id 质检信息ID
     * @return 质检信息
     */
    @GetMapping("/zjxx/{id}")
    public BdcLfDczjjgZjxxDO getZjxxById(@PathVariable("id") String id) {
        return lfDczjjgService.getZjxxById(id);
    }

    /**
     * 保存质检信息
     * @param bdcLfDczjjgZjxxDO 质检信息
     * @return {String} 质检信息ID
     */
    @PostMapping("/zjxx")
    public String saveZjxx(@RequestBody BdcLfDczjjgZjxxDO bdcLfDczjjgZjxxDO) {
        return lfDczjjgService.saveZjxx(bdcLfDczjjgZjxxDO);
    }

    /**
     * 删除质检信息
     * @param zjxxDOList 质检信息
     * @return {Integer} 删除记录
     */
    @DeleteMapping("/zjxx")
    public Integer deleteZjxxs(@RequestBody List<BdcLfDczjjgZjxxDO> zjxxDOList) {
        return lfDczjjgService.deleteZjxxs(zjxxDOList);
    }

    /**
     * 上传质检报告文件
     * @param file 质检报告
     * @param zjxxid 质检信息ID
     * @return {StorageDto} 大云附件存储信息
     */
    @PostMapping("/zjxx/file/{zjxxid}")
    public StorageDto uploadZjxxFile(@RequestBody MultipartFile file, @PathVariable("zjxxid")String zjxxid) {
        return lfDczjjgService.uploadZjxxFile(file, zjxxid);
    }

    /**
     * 保存整改情况信息
     * @param zgqkDO 整改信息
     * @return {String} 整改情况ID
     */
    @PostMapping("/zgqk")
    public String saveZgqk(@RequestBody BdcLfDczjjgZgqkDO zgqkDO) {
        return lfDczjjgService.saveZgqk(zgqkDO);
    }

    /**
     * 删除整改情况信息
     * @param zgqkDOList 整改信息
     * @return {Integer} 删除记录数
     */
    @DeleteMapping("/zgqk")
    public Integer deleteZgqk(@RequestBody List<BdcLfDczjjgZgqkDO> zgqkDOList) {
        return lfDczjjgService.deleteZgqk(zgqkDOList);
    }

    /**
     * 上传整改报告文件
     * @param file 整改报告
     * @param zgqkid 整改信息ID
     * @return {StorageDto} 大云附件存储信息
     */
    @PostMapping("/zgbg/file/{zgqkid}")
    public StorageDto uploadZgbgFile(@RequestBody MultipartFile file, @PathVariable("zgqkid")String zgqkid) {
        return lfDczjjgService.uploadZgbgFile(file, zgqkid);
    }

    /**
     * 查询质检记录关联的所有整改情况
     * @param zjxxid 质检信息ID
     * @return {List} 整改情况
     */
    @GetMapping("/zjxx/{zjxxid}/zjqks")
    public List<BdcLfDczjjgZgqkDO> listZgqkOfZjxx(@PathVariable("zjxxid")String zjxxid) {
        return lfDczjjgService.listZgqkOfZjxx(zjxxid);
    }
}
