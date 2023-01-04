package cn.gtmap.realestate.supervise.web;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.supervise.core.domain.BdcLfSqxxDO;
import cn.gtmap.realestate.supervise.core.domain.BdcLfSqxxSqryDO;
import cn.gtmap.realestate.supervise.core.dto.BdcLfSqxxDTO;
import cn.gtmap.realestate.supervise.core.qo.BdcLfSqxxQO;
import cn.gtmap.realestate.supervise.service.LfZzqnjgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/09/10
 * @description 4 职责权能监管-授权信息管理台账
 */
@RestController
@RequestMapping("/rest/v1.0/zzqnjg")
public class LfZzqnjgRestController extends BaseController{
    @Autowired
    private LfZzqnjgService lfZzqnjgService;


    /**
     * 授权信息查询（分页台账）
     * @param pageable 分页参数
     * @param bdcLfSqxxQO 业务查询参数
     * @return 授权信息
     */
    @GetMapping("/sqxxs")
    public Object listSqxxTableData(@LayuiPageable Pageable pageable, BdcLfSqxxQO bdcLfSqxxQO) {
        Page<BdcLfSqxxDTO> sqxxTableData = lfZzqnjgService.listSqxxTableData(pageable, bdcLfSqxxQO);
        return super.addLayUiCode(sqxxTableData);
    }

    /**
     * 查询指定授权信息记录
     * @param id 授权信息ID
     * @return 授权信息
     */
    @GetMapping("/sqxx/{id}")
    public BdcLfSqxxDO getSqxxById(@PathVariable("id") String id) {
        return lfZzqnjgService.getSqxxById(id);
    }

    /**
     * 查询指定授权信息关联的授权用户
     * @param sqxxid 授权信息ID
     * @return 授权用户
     */
    @GetMapping("/sqxx/sqry/{sqxxid}")
    public List<BdcLfSqxxSqryDO> getSqryBySqxxid(@PathVariable("sqxxid") String sqxxid) {
        return lfZzqnjgService.getSqryBySqxxid(sqxxid);
    }

    /**
     * 保存授权信息
     * @param sqxx 授权信息
     * @return {String} 授权信息ID
     */
    @PostMapping("/sqxx")
    public String saveSqxx(@RequestBody BdcLfSqxxDTO sqxx) {
        return lfZzqnjgService.saveSqxx(sqxx);
    }

    /**
     * 删除授权信息
     * @param sqxxDOList 授权信息
     * @return {Integer} 删除记录
     */
    @DeleteMapping("/sqxx")
    public Integer deleteSqxxs(@RequestBody List<BdcLfSqxxDO> sqxxDOList) {
        return lfZzqnjgService.deleteSqxxs(sqxxDOList);
    }

    /**
     * 上传授权证明文件
     * @param file 授权报告
     * @param sqxxid 授权信息ID
     * @return {StorageDto} 大云附件存储信息
     */
    @PostMapping("/spwj/file/{sqxxid}")
    public StorageDto uploadSqxxFile(@RequestBody MultipartFile file, @PathVariable("sqxxid")String sqxxid) {
        return lfZzqnjgService.uploadSqxxFile(file, sqxxid);
    }
}
