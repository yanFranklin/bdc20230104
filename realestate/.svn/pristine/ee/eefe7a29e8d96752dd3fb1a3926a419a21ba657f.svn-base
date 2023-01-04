package cn.gtmap.realestate.supervise.web;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.supervise.core.domain.BdcLfCxjzjsDO;
import cn.gtmap.realestate.supervise.core.qo.LfWgxwQO;
import cn.gtmap.realestate.supervise.service.LfCxjzjsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/09/10
 * @description 廉防7：诚信机制建设
 */
@RestController
@RequestMapping("/rest/v1.0/cxjzjs")
public class LfCxjzjsRestController extends BaseController {
    @Autowired
    private LfCxjzjsService lfCxjzjsService;


    /**
     * 违规信息查询（分页台账）
     * @param pageable 分页参数
     * @param lfWgxwQO 业务查询参数
     * @return 违规信息
     */
    @GetMapping("/wgxx")
    public Object listWgxxTableData(@LayuiPageable Pageable pageable, LfWgxwQO lfWgxwQO) {
        Page<BdcLfCxjzjsDO> WgxxTableData = lfCxjzjsService.listWgxxTableData(pageable, lfWgxwQO);
        return super.addLayUiCode(WgxxTableData);
    }

    /**
     * 查询指定违规信息记录
     * @param id 违规信息ID
     * @return 违规信息
     */
    @GetMapping("/wgxx/{id}")
    public BdcLfCxjzjsDO getWgxxById(@PathVariable("id") String id) {
        return lfCxjzjsService.getWgxxById(id);
    }

    /**
     * 保存违规信息
     * @param bdcLfCxjzjsDO 违规信息
     * @return {String} 违规信息ID
     */
    @PostMapping("/wgxx")
    public String saveWgxx(@RequestBody BdcLfCxjzjsDO bdcLfCxjzjsDO) {
        return lfCxjzjsService.saveWgxx(bdcLfCxjzjsDO);
    }

    /**
     * 保存审核信息
     * @param bdcLfCxjzjsDO 违规审核信息
     * @return {String} 违规信息ID
     */
    @PostMapping("/wgxx/shxx")
    public String saveWgxxShxx(@RequestBody BdcLfCxjzjsDO bdcLfCxjzjsDO) {
        return lfCxjzjsService.saveWgxxShxx(bdcLfCxjzjsDO);
    }

    /**
     * 删除违规信息
     * @param wgxxDOList 违规信息
     * @return {Integer} 删除记录
     */
    @DeleteMapping("/wgxx")
    public Integer deleteWgxxs(@RequestBody List<BdcLfCxjzjsDO> wgxxDOList) {
        return lfCxjzjsService.deleteWgxxs(wgxxDOList);
    }

    /**
     * 上传违规证明文件
     * @param file 违规报告
     * @param wgxxid 违规信息ID
     * @return {StorageDto} 大云附件存储信息
     */
    @PostMapping("/wgzmwj/file/{Wgxxid}")
    public StorageDto uploadWgxxFile(@RequestBody MultipartFile file, @PathVariable("Wgxxid")String wgxxid) {
        return lfCxjzjsService.uploadWgxxFile(file, wgxxid);
    }

}
