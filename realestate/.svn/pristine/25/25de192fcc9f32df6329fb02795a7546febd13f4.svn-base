package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.init.BdcOfdToImgRestService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.init.core.service.BdcOfdToImgService;
import cn.gtmap.realestate.init.core.service.BdcZsService;
import cn.gtmap.realestate.init.util.Base64Util;
import cn.gtmap.realestate.init.web.BaseController;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.ofdrw.converter.ImageMaker;
import org.ofdrw.reader.OFDReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: realestate
 * @description: ofd转图片restController
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-24 09:21
 **/
@RestController
@Api("ofd转图片的服务")
public class BdcOfdToImgRestController extends BaseController implements BdcOfdToImgRestService {

//    @Autowired
//    StorageClientMatcher storageClient;
    @Autowired
    private BdcOfdToImgService bdcOfdToImgService;

    /**
     * @param storageid
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description ofd转图片
     * @date : 2022/8/24 17:54
     */
    @ApiOperation(value = "ofd转图片")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "storageid", value = "id", dataType = "String", required = false,paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public String ofdtoimg(@RequestParam(name = "storageid",required = false) String storageid) {
//        if(StringUtils.isBlank(storageid)){
//            throw new MissingArgumentException("参数storageid不能为空");
//        }
//        String imgbase64 = "";
//        MultipartDto multipartDto = storageClient.download(storageid);
//        String ofdBase64Str= Base64Util.encodeByteToBase64Str(multipartDto.getData());
//        List<String> imgList=ofd2img(ofdBase64Str,"jpg");
//        if(imgList != null && imgList.size()>0){
//            imgbase64 =  imgList.get(0);
//        }
//        return imgbase64;
        return bdcOfdToImgService.ofdtoimg(storageid);
    }

//    public List<String> ofd2img(String ofdBase64Str, String format) {
//        List<String> list = new ArrayList<>(2);
//        if (StringUtils.isNotEmpty(ofdBase64Str)) {
//            try (OFDReader reader = new OFDReader(new ByteArrayInputStream(Base64Util.decodeBase64StrToByte(ofdBase64Str)))) {
//                ImageMaker imageMaker = new ImageMaker(reader, 15);
//                for (int i = 0; i < imageMaker.pageSize(); i++) {
//                    // 4. 指定页码转换图片
//                    BufferedImage image = imageMaker.makePage(i);
//                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                    // 5. 存储为指定格式图片
//                    ImageIO.write(image, format, bos);
//                    bos.flush();
//                    byte[] bytes = bos.toByteArray();
//                    if (null != bytes) {
//                        list.add(Base64Util.encodeByteToBase64Str(bytes));
//                        bos.close();
//                    }
//                }
//            } catch (IOException e) {
////                logger.error("ofd2img:IOException", e);
//            }
//        }
//
//        return list;
//    }
}
