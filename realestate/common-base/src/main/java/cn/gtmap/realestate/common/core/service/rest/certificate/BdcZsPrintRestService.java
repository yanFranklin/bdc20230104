package cn.gtmap.realestate.common.core.service.rest.certificate;

import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.BdcZsPrintDTO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/20
 * @description 不动产证书打印
 */
public interface BdcZsPrintRestService {
    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @param bdcPrintDTO 打印参数
     * @return 打印xml结果
     * @description 获取和证书相关的附属清单的打印xml
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/zs/fsqd/print/xml")
    String zsFsqdPrintXml(@RequestBody BdcPrintDTO bdcPrintDTO);

    /**
     * @param bdcPrintDTO 打印参数对象
     * @return String  获取打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取项目所有证书的打印XML
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/zs/print/{xmid}/{zslx}/xml")
    String zsPrintXml(@RequestBody BdcPrintDTO bdcPrintDTO);

    /**
     * @param bdcPrintDTO 打印参数对象
     * @return String  获取打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取单个证书的打印XML
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/zs/print/singleXml")
    String singleZsPrintXml(@RequestBody BdcPrintDTO bdcPrintDTO);

    /**
     * @param bdcPrintDTO 打印参数对象
     * @return String 打印的xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取证书批量打印的xml
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/zs/print/batchXml")
    String batchZsPrintXml(@RequestBody BdcPrintDTO bdcPrintDTO);

    /**
     * @param zsid     证书ID
     * @param response
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据zsid查询证书二维码内容，生成二维码图片
     */
    @GetMapping(value = "/realestate-certificate/rest/v1.0/zs/print/{zsid}/ewm")
    @ApiImplicitParams({@ApiImplicitParam(name = "zsid", value = "证书ID", required = true, dataType = "String", paramType = "path")})
    void ewmStream(@PathVariable(name = "zsid") String zsid, HttpServletResponse response);

    /**
     * @author  <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param  listZsidsStr 选中的记录数据
     * @return {String} 保存的Redis key
     * @description  保存勾选的证书id，保存至Redis中
     */
    @PostMapping("/realestate-certificate/rest/v1.0/zs/print/batchzsprint")
    String saveListZsidsToRedis(@RequestParam("listZsidsStr") String listZsidsStr);

    /**
     * @param bdcZsPrintDTO
     * @return {String} 保存的Redis key
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 把勾选的打印zsid，保存至Redis中
     */
    @PostMapping("/realestate-certificate/rest/v1.0/zs/print/saveZsidsToRedis")
    String saveZsidsToRedis(@RequestBody BdcZsPrintDTO bdcZsPrintDTO);

    /**
     * 从redis中获取保存的zsids
     * @param key
     * @return
     */
    @RequestMapping("/realestate-certificate/rest/v1.0/zs/print/getZsidsByKey")
    List<String> getZsidsByKey(@RequestParam("key") String key);
}
