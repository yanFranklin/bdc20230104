package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.service.rest.register.BdcPrintRestService;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcDysjPzVO;
import cn.gtmap.realestate.register.service.BdcPrintService;
import cn.gtmap.realestate.register.web.main.BaseController;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/14
 * @description 不动产打印服务接口
 */
@RestController
@Api(tags = "不动产打印服务接口")
public class BdcPrintRestController extends BaseController implements BdcPrintRestService {
    @Autowired
    BdcPrintService bdcPrintService;

    /**
     * @param paramMap
     * {
     *     "spb":[
     *         {
     *             "xmid":"",
     *             "sjxxid":""
     *         },
     *         {
     *             "xmid":"",
     *             "sjxxid":""
     *         }
     *     ],
     *     "sjd":[
     *         {
     *             "xmid":"",
     *             "sjxxid":""
     *         },
     *         {
     *             "xmid":"",
     *             "sjxxid":""
     *         }
     *     ]
     * }
     * @return fr3 xml
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 不动产打印服务
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "不动产信息打印", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "", value = "{\"spb\":[{\"xmid\":\"\",\"sjxxid\":\"\"},{\"xmid\":\"\",\"sjxxid\":\"\"}],\"sjd\":[{\"xmid\":\"\",\"sjxxid\":\"\"},{\"xmid\":\"\",\"sjxxid\":\"\"}]} 传入的参数为json，spb和对照配置表里的dylx字段，和xml模板(resource/print文件夹下)保持一致,其他参数和配置表里的cs(参数)字段对应", dataType = "String", paramType = "query")})
    public String print(@RequestBody Map<String, List<Map>> paramMap) {
        return bdcPrintService.print(paramMap);
    }

    /**
     * @param bdcDysjDTOList
     * @return xml
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description bdcDysjDTOList
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "不动产打印已有信息", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "", value = "{\"spb\":[{\"xmid\":\"\",\"sjxxid\":\"\"},{\"xmid\":\"\",\"sjxxid\":\"\"}],\"sjd\":[{\"xmid\":\"\",\"sjxxid\":\"\"},{\"xmid\":\"\",\"sjxxid\":\"\"}]} 传入的参数为json，spb和对照配置表里的dylx字段，和xml模板(resource/print文件夹下)保持一致,其他参数和配置表里的cs(参数)字段对应", dataType = "String", paramType = "query")})
    @Override
    public String printDatas(@RequestBody List<BdcDysjDTO> bdcDysjDTOList) {
        return bdcPrintService.printDatas(bdcDysjDTOList);
    }

    /**
     * @param bdcDysjPzVO 打印数据配置VO
     * @return 校验结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 校验打印配置
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "校验打印配置", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDysjPzVO", value = "校验打印配置", dataType = "bdcDysjPzVO", paramType = "body")})

    @Override
    public Object jypzxx(@RequestBody BdcDysjPzVO bdcDysjPzVO) {
        return bdcPrintService.jypzxx(bdcDysjPzVO);
    }
}
