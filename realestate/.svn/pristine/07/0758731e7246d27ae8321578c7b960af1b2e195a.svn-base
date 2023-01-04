package cn.gtmap.realestate.inquiry.ui.web.rest.bengbu;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcZjDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZjFeignService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author wangyinghao
 * @version 1.0, 2022-07-28
 * @description 市级公证处查询相关方法
 */
@RestController
@RequestMapping(value = "/rest/v1.0/gzcgz")
public class BdcGzcGzController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGzcGzController.class);
    /**
     * 公证书存储路径
     */
    @Value("${gscgz.gzspath:}")
    private String path;

    /**
     * 公证书下载
     *
     * @param gzsbh    公证书编号
     * @param response
     */
    @GetMapping("/gzs/pdf/download")
    public void gzsDownload(@RequestParam("gzsbh") String gzsbh, HttpServletResponse response) {
        if (StringUtils.isAnyBlank(gzsbh)) {
            throw new AppException("参数错误！");
        }
        //获取公证书信息
        try (OutputStream outputStream = response.getOutputStream()) {
            String fileName = URLDecoder.decode(gzsbh, "utf-8");

            File file = new File(path + fileName);
            FileInputStream inputStream = new FileInputStream(file);
            // 浏览器下载
            String downloadFile = URLEncoder.encode(fileName, "utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + downloadFile);
            outputStream.write(IOUtils.readFully(inputStream, Math.toIntExact(file.length())));
            outputStream.flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 公证书预览
     *
     * @param gzsbh    公证书编号
     * @param response
     */
    @GetMapping("/gzs/pdf/{gzsbh}")
    public void gzsPrev(@PathVariable("gzsbh") String gzsbh, HttpServletResponse response) {
        if (StringUtils.isAnyBlank(gzsbh)) {
            throw new AppException("参数错误！");
        }
        //获取公证书信息
        try (OutputStream outputStream = response.getOutputStream()) {
            String fileName = URLDecoder.decode(gzsbh, "utf-8");
            File file = new File(path + fileName);
            LOGGER.info("公证文件预览{}",path + fileName);
            FileInputStream inputStream = new FileInputStream(file);
            // 浏览器下载
            String downloadFile = URLEncoder.encode(fileName, "utf-8");
            response.setContentType("application/pdf");
            outputStream.write(IOUtils.readFully(inputStream, Math.toIntExact(file.length())));
            outputStream.flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
