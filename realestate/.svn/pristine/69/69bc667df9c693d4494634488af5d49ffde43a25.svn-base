package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.realestate.certificate.config.PropsConfig;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.certificate.service.BdcZsPrintService;
import cn.gtmap.realestate.certificate.web.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.BdcZsPrintDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.building.ZdFeignService;
import cn.gtmap.realestate.common.core.service.rest.certificate.BdcZsPrintRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.QRcodeUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/23
 * @description 证书打印
 */
@RestController
@Api(tags = "不动产证书打印服务接口")
public class BdcZsPrintRestController extends BaseController implements BdcZsPrintRestService {
    @Autowired
    PropsConfig propsConfig;
    @Autowired
    BdcZsPrintService bdcZsPrintService;
    @Autowired
    BdcZsService bdcZsService;
    @Autowired
    ZdFeignService zdFeignService;
    /**
     * Redis操作
     */
    @Autowired
    private RedisUtils redisUtils;

    @Value("${zmdewm.bdcqzh:false}")
    private Boolean zmdewmBdcqzh;

    //配置证书二维码图片生成
    @Value("${zsewm.dzqzewm:false}")
    private Boolean zsewmDzqzewm;

    /**
     * @param bdcPrintDTO 打印参数
     * @return 打印xml结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取和证书相关的附属清单的打印xml
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取和证书相关的附属清单的打印xml")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcPrintDTO", value = "打印参数DO", required = true, dataType = "BdcPrintDTO", paramType = "body")})
    @Override
    public String zsFsqdPrintXml(@RequestBody BdcPrintDTO bdcPrintDTO) {
        return bdcZsPrintService.zsFsqdPrintXml(bdcPrintDTO);
    }

    /**
     * @param bdcPrintDTO 证书打印参数
     * @return String  获取打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取项目所有证书的打印XML
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取项目所有证书的打印XML")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcPrintDTO", value = "打印参数DO", required = true, dataType = "BdcPrintDTO", paramType = "body")})
    @Override
    public String zsPrintXml(@RequestBody BdcPrintDTO bdcPrintDTO) {
        return bdcZsPrintService.zsPrintXml(bdcPrintDTO);
    }

    /**
     * @param bdcPrintDTO 证书打印参数
     * @return String  获取打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取单个证书的打印XML
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取单个证书的打印XML")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZsPrintQO", value = "证书打印QO", required = true, dataType = "BdcZsPrintQO", paramType = "body")})
    @Override
    public String singleZsPrintXml(@RequestBody BdcPrintDTO bdcPrintDTO) {
        return bdcZsPrintService.singleZsPrintXml(bdcPrintDTO);
    }

    /**
     * @param bdcPrintDTO 证书打印参数
     * @return String 打印的xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取证书批量打印的xml
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取证书批量打印的xml")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZsPrintQO", value = "证书打印QO", required = true, dataType = "BdcZsPrintQO", paramType = "body")})
    @Override
    public String batchZsPrintXml(@RequestBody BdcPrintDTO bdcPrintDTO) {
        return bdcZsPrintService.batchZsPrintXml(bdcPrintDTO);
    }

    /**
     * @param zsid     证书ID
     * @param response
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据zsid查询证书二维码内容，生成二维码图片
     */
    @GetMapping(value = "/realestate-certificate/rest/v1.0/zs/print/{zsid}/ewm")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取证书二维码", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "zsid", value = "证书ID", required = true, dataType = "String", paramType = "path")})
    @Override
    public void ewmStream(@PathVariable(name = "zsid") String zsid, HttpServletResponse response) {
        String content = "";
        if (StringUtils.isNotBlank(zsid)) {
            content = zsid;
            BdcZsDO bdcZsDO = bdcZsService.queryBdcZs(zsid);
            LOGGER.info("证书二维码content内容{}",content);
            if (null != bdcZsDO && StringUtils.isNotBlank(bdcZsDO.getEwmnr())) {
                content = bdcZsDO.getEwmnr();
                LOGGER.info("证书二维码content组成:ewmnr 内容{}",content);
            } else {
                List<BdcXmDO> bdcXmDOList = bdcZsService.queryZsXmByZsid(zsid);
                if (CollectionUtils.isNotEmpty(bdcXmDOList) && StringUtils.isNotBlank(bdcXmDOList.get(0).getSlbh())) {
                    content = bdcXmDOList.get(0).getSlbh();
                    LOGGER.info("证书二维码content组成:slbh 内容:{}",content);
                }
            }
            if (Boolean.TRUE.equals(zmdewmBdcqzh)) {
                content = bdcZsDO.getBdcqzh();
                LOGGER.info("证书二维码content组成:bdcqzh 内容:{}",content);
            }
            if (Boolean.TRUE.equals(zsewmDzqzewm)){
                List<BdcXmDO> bdcXmDOList = bdcZsService.queryZsXmByZsid(zsid);
                String slbh = "";
                if (CollectionUtils.isNotEmpty(bdcXmDOList) && (null != bdcXmDOList.get(0))) {
                    slbh = bdcXmDOList.get(0).getSlbh();
                }
                content = bdcZsDO.getZzid()+CommonConstantUtils.TS_FH_1+bdcZsDO.getBdcdyh()+CommonConstantUtils.TS_FH_1+ slbh;
                LOGGER.info("证书二维码content组成:zzid&bdcdyh&slbh 内容:{}",content);
            }
        }
        response.setContentType("image/jpg;charset=utf-8");
        response.addHeader("Content-Disposition", "attachment; filename=test.jpg");
        QRcodeUtils.encoderQRCode(content, null, response);
    }

    /**
     * @author  <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param  listZsidsStr 选中的记录数据
     * @return {String} 保存的Redis key
     * @description  保存勾选的证书id，保存至Redis中
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("批量打印勾选的证书证明id集合，保存至Redis中")
    @ApiImplicitParam(name = "listZsids", value = "选中的记录ids", required = true, paramType = "body")
    public String saveListZsidsToRedis(@RequestParam String listZsidsStr) {
        // 保存至Redis
        return redisUtils.addStringValue(CommonConstantUtils.REDIS_BATCH_ZSZM_PRINT + UUIDGenerator.generate(),
                listZsidsStr, 360);
    }

    /**
     * @param bdcZsPrintDTO
     * @return {String} 保存的Redis key
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 把勾选的打印zsid，保存至Redis中
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("批量打印勾选的证书证明id集合，保存至Redis中")
    @ApiImplicitParam(name = "bdcZsPrintDTO", value = "打印传参", required = true, paramType = "body")
    public String saveZsidsToRedis(@RequestBody BdcZsPrintDTO bdcZsPrintDTO) {
        if(bdcZsPrintDTO == null || StringUtils.isBlank(bdcZsPrintDTO.getListZsidsStr())){
            throw new MissingArgumentException("缺失必要参数");
        }
        // 保存至Redis
        return redisUtils.addStringValue(CommonConstantUtils.REDIS_BATCH_ZSZM_PRINT + UUIDGenerator.generate(),
                bdcZsPrintDTO.getListZsidsStr(), 360);
    }

    /**
     * 获取保存的redis中的zsids
     * @param key
     * @return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取保存的redis中的zsids")
    @ApiImplicitParam(name = "key", value = "redis key", required = true, paramType = "body")
    public List<String> getZsidsByKey(String key){
        String zsidStr = redisUtils.getStringValue(key);
        redisUtils.deleteKey(key);
        if(StringUtils.isNotEmpty(zsidStr)){
            List<String> listZsid = Arrays.asList(StringUtils.split(zsidStr, CommonConstantUtils.ZF_YW_DH));
            return listZsid;
        }else{
            return new ArrayList<>();
        }
    }
}
