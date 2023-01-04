package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.inquiry.ui.util.FtpUtil;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/12/17
 * @description 附件下载
 */
@RestController
@RequestMapping("/rest/v1.0")
public class BdcFjxzController extends BaseController {
    // 服务器ip
    @Value("${fjxz.ip:}")
    private String ip;
    // 服务器端口
    @Value("${fjxz.port:0}")
    private int port;
    // 服务器用户名
    @Value("${fjxz.user:}")
    private String user;
    // 服务器密码
    @Value("${fjxz.password:}")
    private String password;
    // 查封文件路径
    @Value("${fjxz.cffjpath:}")
    private String cffjpath;
    // 土地房屋信息文件路径
    @Value("${fjxz.tdfwfjpath:}")
    private String tdfwfjpath;
    //激扬附件查看地址
    @Value("${jyfjck.url:}")
    private String jyfjckUrl;

    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    BdcXmFeignService bdcXmFeignService;

    /**
     * 通过查封文号获取查封附件
     *
     * @param cfwh 查封文号
     */
    @GetMapping("/fjxz/cf/fj/{cfwh}")
    public void getCffj(@PathVariable(name = "cfwh") String cfwh, HttpServletResponse response) {
        getFileFromServer(cffjpath, cfwh, response);
    }

    /**
     * 通过坐落获取土地房屋信息调拨附件
     *
     * @param zl 坐落
     */
    @GetMapping("/fjxz/tdfwxxdb/fj/{zl}")
    public void getTdfwxxdbfj(@PathVariable(name = "zl") String zl, HttpServletResponse response) {
        getFileFromServer(tdfwfjpath, zl, response);
    }

    /**
     * 通过文件名和路径 获取文件
     *
     * @param filePath
     * @param filename
     */
    private void getFileFromServer(String filePath, String filename, HttpServletResponse response) {
        FTPClient ftpClient = null;
        OutputStream toClient = null;
        InputStream in = null;
        String file = "";
        try {
            // 建立ftp连接
            ftpClient = FtpUtil.getFTPClient(ip, user, password, port);
            // 获取文件
            in = FtpUtil.downloadFtpFile(ftpClient, filePath, filename + ".jpg");
            if (in != null) {
                file = filename + ".jpg";
            }
            // 当jpg无法获得文件时
            if (in == null) {
                in = FtpUtil.downloadFtpFile(ftpClient, filePath, filename + ".pdf");
                if (in != null) {
                    file = filename + ".pdf";
                }
            }
            // 当pdf无法文件时
            if (in == null) {
                in = FtpUtil.downloadFtpFile(ftpClient, filePath, filename + ".doc");
                if (in != null) {
                    file = filename + ".doc";
                }
            }
            if (in == null) {
                in = FtpUtil.downloadFtpFile(ftpClient, filePath, filename + ".docx");
                if (in != null) {
                    file = filename + ".docx";
                }
            }
            // 当无法获得文件时
            if (in == null) {
                throw new AppException("未能获取文件");
            }

            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file, "UTF-8"));
            toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            // 写入文件
            int b;
            while ((b = in.read()) != -1) {
                toClient.write(b);
            }

        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            if (ftpClient != null) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    LOGGER.error("关闭FTP链接异常", e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    LOGGER.error("关闭流异常", ex);
                }
            }
            if (toClient != null) {
                try {
                    toClient.flush();
                    toClient.close();
                } catch (IOException ex) {
                    LOGGER.error("关闭流异常", ex);
                }
            }
        }
    }

    /**
     * 附件查看，根据文件夹名称获取文件树结构
     * @param slbh  受理编号
     * @param clientId 标识
     * @param name 文件夹名称
     * @return
     */
    @GetMapping("/fjcx/allfiles/{slbh}")
    public List<StorageDto> listAllStorages(@PathVariable(value="slbh")String slbh,
                                            @RequestParam(value="clientId") String clientId, @RequestParam(value= "name" ) String name ) {
        if(StringUtils.isBlank(slbh)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数受理编号");
        }
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxBySlbh(slbh);
        if(CollectionUtils.isEmpty(bdcXmDTOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到项目信息");
        }
        String gzlslid = bdcXmDTOList.get(0).getGzlslid();
        List<StorageDto> storageDtoList = storageClient.queryMenus(clientId, gzlslid, "", null, null, name, null,null);
        return storageDtoList;
    }

    /**
     * @param processInsId 工作流实例ID
     * @return 页面地址
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @description 连云港 激扬附件查看
     */
    @GetMapping("/jyfjck")
    @ResponseStatus(HttpStatus.OK)
    public void forwardSlymHtml(@RequestParam(name = "processInsId") String processInsId, HttpServletResponse response) throws IOException {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        if (StringUtils.isBlank(jyfjckUrl)) {
            throw new MissingArgumentException("激扬附件查看地址未配置！");
        }
        //根据工作流实例id查询受理编号
        String sjbh = bdcXmFeignService.querySlbh(processInsId);
        LOGGER.info("{} 根据gzlslid获取收件编号：{}", processInsId, sjbh);
        if (StringUtils.isNotBlank(sjbh)) {
            String url = jyfjckUrl + sjbh;
            LOGGER.info("激扬附件查看地址为：{}", url);
            response.sendRedirect(url);
        } else {
            //没有返回地址，抛出异常
            throw new AppException("缺失基本项目信息");
        }
    }
}
