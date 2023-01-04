package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.register.BdcQzxxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.register.BdcQzxxFeginService;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2020/1/2 15:53
 * @description 审核登簿系统签字控制
 */
@RestController
@RequestMapping("/rest/v1.0/qzxx")
public class BdcQzxxConttroller {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());


    @Autowired
    StorageClientMatcher storageClient;

    @Autowired
    BdcQzxxFeginService qzxxFeginService;

    /*BASE64Encoder和BASE64Decoder这两个方法是sun公司的内部方法，并没有在java api中公开过，所以使用这些方法是不安全的，
     * 将来随时可能会从中去除，所以相应的应该使用替代的对象及方法，建议使用apache公司的API]
     */
    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();

    @GetMapping("/getPdfBase64")
    public String getPdfBase64(@RequestParam(name = "pdf") String pdf) {
        if (StringUtils.isNotBlank(pdf)) {
            String base64String = getPDFBinary(new File(pdf));
            return base64String;
        }
        return null;
    }

    @PostMapping("/saveQzxx")
    public Object saveQzxx(@RequestBody BdcQzxxDO bdcQzxxDO) {
        Integer count = 0;
        if (null != bdcQzxxDO) {
            if (StringUtils.isBlank(bdcQzxxDO.getXmid())) {
                throw new MissingArgumentException("缺少xmid参数");
            }
            if (StringUtils.isBlank(bdcQzxxDO.getGzlslid())) {
                throw new MissingArgumentException("缺少gzlslid参数");
            }
            if (StringUtils.isBlank(bdcQzxxDO.getSlbh())) {
                throw new MissingArgumentException("缺少受理编号参数");
            }
            if (null == bdcQzxxDO.getBdlx()) {
                throw new MissingArgumentException("缺少表单类型参数");
            }
            if (null == bdcQzxxDO.getQzrlb()) {
                throw new MissingArgumentException("缺少签字人类别参数");
            }
            List<BdcQzxxDO> bdcQzxxDOList = qzxxFeginService.queryBdcQzxx(bdcQzxxDO);
            if (CollectionUtils.isNotEmpty(bdcQzxxDOList)) {
                bdcQzxxDO.setId(bdcQzxxDOList.get(0).getId());
                if (StringUtils.isNotBlank(bdcQzxxDOList.get(0).getFid())) {
                    StorageDto storageDto = storageClient.findById(bdcQzxxDOList.get(0).getFid());
                    if (Objects.nonNull(storageDto)) {
                        boolean del = storageClient.deleteStorages(Stream.of(bdcQzxxDOList.get(0).getFid()).collect(Collectors.toList()));
                        LOGGER.warn("发证记录签字图片文件删除结果：{}。删除信息为：{}", del, JSONObject.toJSONString(storageDto));
                    }
                }
                this.uploadQzxx(bdcQzxxDO);
                count = qzxxFeginService.updateBdcQzxx(bdcQzxxDO);
                LOGGER.warn("更新图片到大云！{}", bdcQzxxDO.getFid());
                return count;
            } else {
                this.uploadQzxx(bdcQzxxDO);
                LOGGER.warn("新增{}", bdcQzxxDO.getSlbh());
                LOGGER.warn("新增fid{}", bdcQzxxDO.getFid());
                return qzxxFeginService.insertBdcQzxx(bdcQzxxDO);
            }
        }
        return count;
    }

    /**
     * @param bdcQzxxDO
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询签字信息
     */
    @PostMapping("/queryQzxx")
    public BdcQzxxDO queryQzxx(@RequestBody BdcQzxxDO bdcQzxxDO) {
        if (null != bdcQzxxDO) {
            if (StringUtils.isBlank(bdcQzxxDO.getXmid())) {
                throw new MissingArgumentException("缺少xmid参数");
            }
            if (StringUtils.isBlank(bdcQzxxDO.getGzlslid())) {
                throw new MissingArgumentException("缺少gzlslid参数");
            }
            if (StringUtils.isBlank(bdcQzxxDO.getSlbh())) {
                throw new MissingArgumentException("缺少受理编号参数");
            }
            if (null == bdcQzxxDO.getBdlx() || "".equals(bdcQzxxDO.getBdlx())) {
                throw new MissingArgumentException("缺少表单类型参数");
            }
            if (null == bdcQzxxDO.getQzrlb() || "".equals(bdcQzxxDO.getQzrlb())) {
                throw new MissingArgumentException("缺少签字人类别参数");
            }
            List<BdcQzxxDO> bdcQzxxDOList = qzxxFeginService.queryBdcQzxx(bdcQzxxDO);
            if (CollectionUtils.isNotEmpty(bdcQzxxDOList)) {
                return bdcQzxxDOList.get(0);
            }
        }
        return null;
    }

    @PostMapping("/queryQzxxFile")
    public String queryQzxxFile(@RequestBody BdcQzxxDO bdcQzxxDO) {
        if (null != bdcQzxxDO) {
            if (StringUtils.isBlank(bdcQzxxDO.getXmid())) {
                throw new MissingArgumentException("缺少xmid参数");
            }
            if (StringUtils.isBlank(bdcQzxxDO.getGzlslid())) {
                throw new MissingArgumentException("缺少gzlslid参数");
            }
            if (StringUtils.isBlank(bdcQzxxDO.getSlbh())) {
                throw new MissingArgumentException("缺少受理编号参数");
            }
            if (null == bdcQzxxDO.getBdlx() || "".equals(bdcQzxxDO.getBdlx())) {
                throw new MissingArgumentException("缺少表单类型参数");
            }
            if (null == bdcQzxxDO.getQzrlb() || "".equals(bdcQzxxDO.getQzrlb())) {
                throw new MissingArgumentException("缺少签字人类别参数");
            }
            List<BdcQzxxDO> bdcQzxxDOList = qzxxFeginService.queryBdcQzxx(bdcQzxxDO);
            if (CollectionUtils.isNotEmpty(bdcQzxxDOList)) {
                BaseResultDto baseResultDto = storageClient.downloadBase64(bdcQzxxDOList.get(0).getFid());
                if (Objects.nonNull(baseResultDto)) {
                    return baseResultDto.getMsg();
                }
            }
        }
        return null;
    }


    public void uploadQzxx(@RequestBody BdcQzxxDO bdcQzxxDO) {
        StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID_QZXX, CommonConstantUtils.SPACEID_QZXX, CommonConstantUtils.WJMC_QZXX, null);
        MultipartFile multipartFile = Base64Utils.base64ToMultipart(CommonConstantUtils.BASE64_QZ_IMAGE + bdcQzxxDO.getQznr());
        if (Objects.nonNull(storageDto)) {
            try {
                MultipartDto multipartDto = this.getUploadParamDto("", storageDto, multipartFile, CommonConstantUtils.WJZX_CLIENTID_QZXX);
                StorageDto storage = storageClient.multipartUpload(multipartDto);
                if (Objects.nonNull(storage)) {
                    bdcQzxxDO.setFid(storage.getId());
                    //字段暂时不去掉，但是不保存内容
                    bdcQzxxDO.setQznr("");
                    LOGGER.info("上传成功！！fid{}", bdcQzxxDO.getFid());
                }
            } catch (IOException e) {
                LOGGER.error("发证记录组织签名图片文件上传错误{}", e.getMessage());
            }
        }


    }

    /**
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/7/31
     * @description 组织文件上传参数
     */
    public MultipartDto getUploadParamDto(String userName, StorageDto storageDto, MultipartFile file, String client) throws IOException {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(storageDto.getId());
        multipartDto.setClientId(client);
        if (file != null) {
            multipartDto.setData(file.getBytes());
            multipartDto.setOwner(userName);
            multipartDto.setContentType(file.getContentType());
            multipartDto.setSize(file.getSize());
            multipartDto.setOriginalFilename(file.getOriginalFilename());
            multipartDto.setName(file.getName());
        }
        return multipartDto;
    }

    /**
     * 将PDF转换成base64编码
     * 1.使用BufferedInputStream和FileInputStream从File指定的文件中读取内容；
     * 2.然后建立写入到ByteArrayOutputStream底层输出流对象的缓冲输出流BufferedOutputStream
     * 3.底层输出流转换成字节数组，然后由BASE64Encoder的对象对流进行编码
     * *
     */
    public static String getPDFBinary(File file) {
        FileInputStream fin = null;
        BufferedInputStream bin = null;
        ByteArrayOutputStream baos = null;
        BufferedOutputStream bout = null;

        try {
            //建立读取文件的文件输出流
            fin = new FileInputStream(file);
            //在文件输出流上安装节点流（更大效率读取）
            bin = new BufferedInputStream(fin);
            // 创建一个新的 byte 数组输出流，它具有指定大小的缓冲区容量
            baos = new ByteArrayOutputStream();
            //创建一个新的缓冲输出流，以将数据写入指定的底层输出流
            bout = new BufferedOutputStream(baos);
            byte[] buffer = new byte[1024];
            int len = bin.read(buffer);
            while (len != -1) {
                bout.write(buffer, 0, len);
                len = bin.read(buffer);
            }
            //刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
            bout.flush();
            byte[] bytes = baos.toByteArray();
            //sun公司的API
            String pdfBasse64 = encoder.encodeBuffer(bytes).trim();
            return pdfBasse64;
            //apache公司的API
            //return Base64.encodeBase64String(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fin.close();
                bin.close();
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
