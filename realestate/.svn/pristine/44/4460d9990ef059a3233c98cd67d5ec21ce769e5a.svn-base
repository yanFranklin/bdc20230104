package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.register.BdcQzxxDO;
import cn.gtmap.realestate.common.core.service.rest.register.BdcQzxxRestService;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.register.service.BdcQzxxService;
import cn.gtmap.realestate.register.web.main.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/12/23 17:09
 * @description 评价器签字controller
 */
@RestController
@Api(tags = "不动产评价器签名服务接口")
public class BdcQzxxRestController extends BaseController implements BdcQzxxRestService {

    @Autowired
    StorageClientMatcher storageClient;

    @Autowired
    BdcQzxxService bdcQzxxService;

    /*BASE64Encoder和BASE64Decoder这两个方法是sun公司的内部方法，并没有在java api中公开过，所以使用这些方法是不安全的，
     * 将来随时可能会从中去除，所以相应的应该使用替代的对象及方法，建议使用apache公司的API]
     */
    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();

    /**
     * @param bdcQzxxDO bdcQzxxDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 保存签字信息
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "新增不动产评价器签字信息", notes = "新增不动产评价器签字信息")
    @ApiImplicitParam(name = "bdcQzxxDO", value = "不动产评价器签字信息", required = true, dataType = "BdcQzxxDO")
    public BdcQzxxDO insertBdcQzxx(@RequestBody BdcQzxxDO bdcQzxxDO) {
        return bdcQzxxService.insertBdcQzxx(bdcQzxxDO);
    }

    /**
     * @param bdcQzxxDO 评价器签字Do
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 更新不动产评价器签字信息
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "更新不动产评价器签字信息", notes = "更新不动产评价器签字信息")
    @ApiImplicitParam(name = "bdcQzxxDO", value = "不动产评价器签字信息", required = true, dataType = "BdcQzxxDO")
    public Integer updateBdcQzxx(@RequestBody BdcQzxxDO bdcQzxxDO) {
        return bdcQzxxService.updateBdcQzxx(bdcQzxxDO);
    }

    /**
     * 获取pdf文件，进行转码
     *
     * @param pdf
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "pdf转码", notes = "pdf转码")
    public String pdfBase64(String pdf) {
        String base64String = getPDFBinary(new File(pdf));
        return base64String;
    }

    /**
     * 查询签字信息
     *
     * @param bdcQzxxDO bdcQzxxDO
     * @return BdcQzxxDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "查询不动产评价器签字信息", notes = "查询不动产评价器签字信息")
    @ApiImplicitParam(name = "bdcQzxxDO", value = "不动产评价器签字信息", required = true, dataType = "BdcQzxxDO")
    public List<BdcQzxxDO> queryBdcQzxx(@RequestBody BdcQzxxDO bdcQzxxDO) {
        return bdcQzxxService.queryBdcQzxx(bdcQzxxDO);
    }

    /**
     * @param bdcQzxxDO
     * @return
     * @Date 2020/7/30
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "新增或保存签字信息", notes = "新增或保存签字信息")
    @ApiImplicitParam(name = "bdcQzxxDO", value = "新增或保存签字信息", required = true, dataType = "BdcQzxxDO")
    public Object SaveOrUpdateQzxx(@RequestBody BdcQzxxDO bdcQzxxDO) {
        Integer count = 0;
        List<BdcQzxxDO> bdcQzxxDO1 = bdcQzxxService.queryBdcQzxx(bdcQzxxDO);
        if (CollectionUtils.isNotEmpty(bdcQzxxDO1)) {
            bdcQzxxDO.setId(bdcQzxxDO1.get(0).getId());
            count = bdcQzxxService.updateBdcQzxx(bdcQzxxDO);
            return count;
        } else {
            return bdcQzxxService.insertBdcQzxx(bdcQzxxDO);
        }
    }

    /**
     * 组织和上传签字文件到大云
     *
     * @param bdcQzxxDO
     * @Date 2020/7/30
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "组织和上传签字文件到大云", notes = "组织和上传签字文件到大云")
    @ApiImplicitParam(name = "bdcQzxxDO", value = "签字DO", required = true, dataType = "BdcQzxxDO")
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
     * 存量签字数据上传到文件中心服务
     *
     * @param slbh
     * @return 更新数量
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 14:03 2020/8/3
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "存量签字数据上传到文件中心服务", notes = "存量签字数据上传到文件中心服务")
    public Integer clsjUpload(String slbh, Integer bdlx) {
        LOGGER.info("处理数据开始{},{}", slbh, bdlx);
        Integer count = 0;
        BdcQzxxDO bdcQzxxDO = new BdcQzxxDO();
        if (StringUtils.isNotBlank(slbh)) {
            bdcQzxxDO.setSlbh(slbh);
        }
        if (null != bdlx) {
            bdcQzxxDO.setBdlx(bdlx);
        }
        bdcQzxxDO.setQznr("1");
        List<BdcQzxxDO> bdcQzxxDOList = bdcQzxxService.queryBdcQzxx(bdcQzxxDO);

        if (CollectionUtils.isNotEmpty(bdcQzxxDOList)) {
            LOGGER.info("要处理数据的条数{}" + bdcQzxxDOList.size());
            for (BdcQzxxDO bdcQzxxDO1 : bdcQzxxDOList) {
                if (StringUtils.isNotBlank(bdcQzxxDO1.getQznr())) {
                    this.uploadQzxx(bdcQzxxDO1);
                    LOGGER.info("处理数据后保存的数据" + bdcQzxxDO1.toString());
                    count += updateBdcQzxx(bdcQzxxDO1);
                    LOGGER.info("处理数据后保存的fid" + bdcQzxxDO1.getFid());
                }
            }

        }
        return count;
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