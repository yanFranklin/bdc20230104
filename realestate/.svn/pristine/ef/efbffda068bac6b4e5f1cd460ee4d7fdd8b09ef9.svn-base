package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlFghysfDTO;
import cn.gtmap.realestate.common.core.qo.config.BdcPdfDyQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFgyhsfFeignService;
import cn.gtmap.realestate.common.core.support.request.DypzController;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.etl.core.vo.PopupFileDataVO;
import cn.gtmap.realestate.etl.service.OldSystemFileService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "常州老系统文件管理接口")
@RequestMapping("/realestate-etl/rest/v1.0/old/system")
public class OldSystemFileRestController {

    private static final Logger logger = LoggerFactory.getLogger(OldSystemFileRestController.class);

    @Autowired
    private OldSystemFileService oldSystemFileService;
    @Autowired
    RedisUtils redisUtils;

    @Autowired
    DypzController dypzController;
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    private BdcSlFgyhsfFeignService bdcSlFgyhsfFeignService;
    /**
     * 附件目录隐藏的文件夹以及附件
     */
    @Value("#{'${fjml.hide.wjj:}'.split(',')}")
    private List<String> fjmlHideWjj;
    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 获取对应的老系统文件目录
     */
    @ResponseBody
    @PostMapping("/fetch/catalog")
    public CommonResponse<List<PopupFileDataVO>> fetchCatalog(@RequestParam String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            try {
                return oldSystemFileService.fetchCataLog(gzlslid);
            }catch (Exception e){
                logger.error("获取文件目录信息失败:",e);
                return CommonResponse.fail("获取文件目录信息失败:"+e.getMessage());
            }
        }
        return CommonResponse.fail("入参gzlslid为空");
    }

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 获取对应的老系统文件
     * @param fileName  1000.JPG   20000.jpg
     */
    @ResponseBody
    @GetMapping("/download/file")
    public void downloadFile(@RequestParam String path, @RequestParam String fileName, HttpServletResponse response) throws Exception {
        if (StringUtils.isNotBlank(path) && StringUtils.isNotBlank(fileName)) {
            oldSystemFileService.downloadFile(path, fileName, response);
        } else {
            throw new RuntimeException("入参缺失");
        }
    }

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 把勾选的数据放到redis，返回redis 的key值，设置生存时长20s
     * @date : 2021/8/16 10:15
     */
    @ResponseBody
    @PostMapping("/sjcl/redis")
    public String setFjclToRedis(@RequestBody String json) {
        return oldSystemFileService.setSjclUrlToRedis(json);
    }

    /**
     * @param key
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/8/16 14:04
     */
    @GetMapping("/print")
    public String printFjcl(String key) {
        return oldSystemFileService.getSjclXml(key);
    }

    /**
     * @param dylxList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description common 包的打印配置设置
     * @date : 2021/9/13 8:58
     */
    @ResponseBody
    @PostMapping("/dypz")
    public Map getBdcDysjPz(@RequestBody List<String> dylxList) {
        return dypzController.getBdcDysjPz(dylxList);
    }

    /**
     * @param bdcPdfDyQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 生成pdf 数据
     * @date : 2021/9/13 9:00
     */
    @ResponseBody
    @PostMapping("/pdf/param/redis")
    public String sendXmlToRedis(@RequestBody BdcPdfDyQO bdcPdfDyQO) {
        return redisUtils.addStringValue(UUIDGenerator.generate16(), JSONObject.toJSONString(bdcPdfDyQO), 60);
    }

    @ResponseBody
    @GetMapping("/pdf/{redisKey}")
    public void getPdfViewData(HttpServletResponse response, @PathVariable(value = "redisKey") String redisKey) {
        dypzController.getPdfViewData(response, redisKey, "");
    }

    @ResponseBody
    @PostMapping("/fgf/lssjcl")
    public CommonResponse<List<StorageDto>> listAllStorages(@RequestParam(value="gzlslid") String gzlslid,@RequestParam(value="sflsfj") String sflsfj){
        if(StringUtils.isNotBlank(gzlslid)){
            if(StringUtils.isNotBlank(sflsfj)){
                List<StorageDto> storageDtoList = storageClient.queryMenus("clientId", gzlslid, "", null, null, null,null,null);
                this.filterStorageFiles(storageDtoList);
                return CommonResponse.ok(storageDtoList);
            }else{
                BdcSlFghysfDTO bdcSlFghysfDTO = bdcSlFgyhsfFeignService.getBdcSlFghysfDTO(gzlslid);
                if(bdcSlFghysfDTO != null && bdcSlFghysfDTO.getYxmxx() != null){
                    String spaceId = bdcSlFghysfDTO.getYxmxx().getGzlslid();
                    List<StorageDto> storageDtoList = storageClient.queryMenus("clientId", spaceId, "", null, null, null,null,null);
                    this.filterStorageFiles(storageDtoList);
                    return CommonResponse.ok(storageDtoList);
                }
                return CommonResponse.fail("未获取到原项目信息");
            }
        }
        return CommonResponse.fail("入参gzlslid为空");
    }

    /**
     * 递归方法，根据配置过滤文件夹不展示
     * @param storageDtoList 附件信息
     */
    public void filterStorageFiles(List<StorageDto> storageDtoList){
        if(CollectionUtils.isNotEmpty(fjmlHideWjj) && CollectionUtils.isNotEmpty(storageDtoList)){
            Iterator<StorageDto> it = storageDtoList.iterator();
            while(it.hasNext()){
                StorageDto storageDto = it.next();
                if(fjmlHideWjj.contains(storageDto.getName())){
                    it.remove();
                }else{
                    if(CollectionUtils.isNotEmpty(storageDto.getChildren())){
                        this.filterStorageFiles(storageDto.getChildren());
                    }
                }
            }
        }
    }

}
