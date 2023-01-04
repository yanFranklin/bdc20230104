package cn.gtmap.realestate.supervise.web;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.supervise.core.domain.BdcLfFjxxDO;
import cn.gtmap.realestate.supervise.service.LfFileService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/10/15
 * @description 廉防附件管理
 */
@RestController
@RequestMapping("/rest/v1.0/files")
public class LfFilesRestController {
    @Autowired
    private LfFileService lfFileService;


    @GetMapping("/all/{ywid}")
    public List<BdcLfFjxxDO> listLfFiles(@PathVariable("ywid")String ywid) {
        return lfFileService.listLfFiles(ywid);
    }

    /**
     * 上传附件
     * @param file 附件
     * @param ywid 业务ID
     * @return {StorageDto} 大云附件存储信息
     */
    @PostMapping("/upload/{ywid}")
    public StorageDto uploadZgbgFile(@RequestBody MultipartFile file, @PathVariable("ywid")String ywid) {
        return lfFileService.uploadFile(file, ywid);
    }

    /**
     * 删除附件
     * @param fjxxDOList 附件
     * @return {Integer} 删除记录
     */
    @DeleteMapping
    public Integer deleteWgxxs(@RequestBody List<BdcLfFjxxDO> fjxxDOList) {
        return lfFileService.deleteFiles(fjxxDOList);
    }

    /**
     * 下载文件
     * @param response
     * @param wjlj 文件路径
     * @param wjmc 文件名称
     */
    @GetMapping(value = "/download")
    public void exportPdf(HttpServletResponse response, @RequestParam("wjlj")String wjlj, @RequestParam("wjmc")String wjmc) {
        if(StringUtils.isBlank(wjlj)) {
            throw new MissingArgumentException("下载文件失败，未指定下载文件路径");
        }

        if(StringUtils.isBlank(wjmc)) {
            wjmc = "廉防附件材料";
        }

        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            String file = URLEncoder.encode(wjmc, "utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + file);
            IOUtils.write(IOUtils.toByteArray(new URL(wjlj)), outputStream);
        } catch (Exception e) {
            throw new AppException("下载文件失败，请重试");
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

}
