package cn.gtmap.realestate.natural.web.rest;

import cn.gtmap.realestate.common.core.dto.natural.ZrzyDysjDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyPrintDTO;
import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyPrintRestService;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyDysjPzVO;
import cn.gtmap.realestate.natural.core.service.ZrzyPrintCoreService;
import cn.gtmap.realestate.natural.service.ZrzyPrintService;
import cn.gtmap.realestate.natural.web.main.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
public class ZrzyPrintRestController extends BaseController implements ZrzyPrintRestService {
    @Autowired
    ZrzyPrintCoreService zrzyPrintCoreService;

    @Autowired
    ZrzyPrintService zrzyPrintService;

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
    @ApiOperation(value = "不动产信息打印")
    @ApiImplicitParams({@ApiImplicitParam(name = "", value = "{\"spb\":[{\"xmid\":\"\",\"sjxxid\":\"\"},{\"xmid\":\"\",\"sjxxid\":\"\"}],\"sjd\":[{\"xmid\":\"\",\"sjxxid\":\"\"},{\"xmid\":\"\",\"sjxxid\":\"\"}]} 传入的参数为json，spb和对照配置表里的dylx字段，和xml模板(resource/print文件夹下)保持一致,其他参数和配置表里的cs(参数)字段对应", dataType = "String", paramType = "query")})
    public String print(@RequestBody Map<String, List<Map>> paramMap) {
        return zrzyPrintCoreService.print(paramMap);
    }

    /**
     * @param zyzyDysjDTOList
     * @return xml
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description bdcDysjDTOList
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "不动产打印已有信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "", value = "{\"spb\":[{\"xmid\":\"\",\"sjxxid\":\"\"},{\"xmid\":\"\",\"sjxxid\":\"\"}],\"sjd\":[{\"xmid\":\"\",\"sjxxid\":\"\"},{\"xmid\":\"\",\"sjxxid\":\"\"}]} 传入的参数为json，spb和对照配置表里的dylx字段，和xml模板(resource/print文件夹下)保持一致,其他参数和配置表里的cs(参数)字段对应", dataType = "String", paramType = "query")})
    @Override
    public String printDatas(@RequestBody List<ZrzyDysjDTO> zyzyDysjDTOList) {
        return zrzyPrintCoreService.printDatas(zyzyDysjDTOList);
    }

    /**
     * @param zrzyDysjPzVO 打印数据配置VO
     * @return 校验结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 校验打印配置
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("校验打印配置")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDysjPzVO", value = "校验打印配置", dataType = "bdcDysjPzVO", paramType = "body")})
    @Override
    public Object jypzxx(@RequestBody ZrzyDysjPzVO zrzyDysjPzVO) {
        return zrzyPrintCoreService.jypzxx(zrzyDysjPzVO);
    }

    /**
     * @param zrzyPrintDTO 打印参数对象
     * @return String  获取打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取单个证书的打印XML
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("校验打印配置")
    @ApiImplicitParams({@ApiImplicitParam(name = "zrzyPrintDTO", value = "打印配置", dataType = "zrzyPrintDTO", paramType = "body")})
    @Override
    public String singleZsPrintXml(@RequestBody ZrzyPrintDTO zrzyPrintDTO) {
        return zrzyPrintService.singleZsPrintXml(zrzyPrintDTO);
    }
}
