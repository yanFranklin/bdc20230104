package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.init.core.service.BdcOfdToImgService;
import cn.gtmap.realestate.init.util.Base64Util;
import org.apache.commons.lang.StringUtils;
import org.ofdrw.converter.ImageMaker;
import org.ofdrw.reader.OFDReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/19
 * @description 不动产单元信息服务实现
 */
@Service
@Validated
public class BdcOfdToImgerviceImpl implements BdcOfdToImgService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcCzrzServiceImpl.class);

    @Autowired
    StorageClientMatcher storageClient;
    /**
     *@author <a href="mailto:wutao@gtmap.cn">wutao</a>
     *@param storageid
     *@return String
     *@description ofd转图片
     */
    @Override
    public String ofdtoimg(String storageid) {
        if(StringUtils.isBlank(storageid)){
            throw new MissingArgumentException("参数storageid不能为空");
        }
        LOGGER.info("参数storageid:"+storageid);
        String imgbase64 = "";
        MultipartDto multipartDto = storageClient.download(storageid);
        String ofdBase64Str= Base64Util.encodeByteToBase64Str(multipartDto.getData());
        List<String> imgList=ofd2img(ofdBase64Str,"jpg");
        if(imgList != null && imgList.size()>0){
            imgbase64 =  imgList.get(0);
        }
        return imgbase64;
    }

    public List<String> ofd2img(String ofdBase64Str, String format) {
        List<String> list = new ArrayList<>(2);
        if (StringUtils.isNotEmpty(ofdBase64Str)) {
            try (OFDReader reader = new OFDReader(new ByteArrayInputStream(Base64Util.decodeBase64StrToByte(ofdBase64Str)))) {
                ImageMaker imageMaker = new ImageMaker(reader, 15);
                for (int i = 0; i < imageMaker.pageSize(); i++) {
                    // 4. 指定页码转换图片
                    BufferedImage image = imageMaker.makePage(i);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    // 5. 存储为指定格式图片
                    ImageIO.write(image, format, bos);
                    bos.flush();
                    byte[] bytes = bos.toByteArray();
                    if (null != bytes) {
                        list.add(Base64Util.encodeByteToBase64Str(bytes));
                        bos.close();
                    }
                }
            } catch (IOException e) {
                throw new AppException("ofd转图片失败"+e);
            }
        }

        return list;
    }

}
