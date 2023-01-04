package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcDsfdjJhxxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.config.Encrypt.AESutil;
import cn.gtmap.realestate.common.core.domain.accept.BdcDsfdjJhxxDO;
import cn.gtmap.realestate.common.core.dto.accept.DecryptDto;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcDsfdjJhxxRestService;
import cn.gtmap.realestate.common.util.Base64Utils;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/6/5
 * @description 不动产与第三方登记交互信息REST接口服务
 */
@RestController
@Api(tags = "不动产与第三方登记交互信息RESR接口服务")
public class BdcDsfdjJhxxRestController extends BaseController implements BdcDsfdjJhxxRestService {

    @Autowired
    BdcDsfdjJhxxService bdcDsfdjJhxxService;

    @Autowired
    AESutil aeSutil;

    @Value("${oracle.encode:GBK}")
    private String encode;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "新增写入第三方交互信息", notes = "新增写入第三方交互信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "流程实例ID", required = true, dataType = "String", paramType = "query")
    })
    public List<BdcDsfdjJhxxDO> initDsfdjJhxx(@RequestParam(value = "processInsId") String processInsId){
        return bdcDsfdjJhxxService.initDsfdjJhxx(processInsId);


    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新第三方交互信息", notes = "更新第三方交互信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jsonObject", value = "更新的字段集合", required = true, dataType = "JSONObject", paramType = "query"),
            @ApiImplicitParam(name = "bdcslbh", value = "不动产受理编号", required = true, dataType = "String", paramType = "query")
    })
    public void updateDsfdjJhxxByBdcslbh(@RequestBody JSONObject jsonObject, @RequestParam(value = "bdcslbh")String bdcslbh){
        bdcDsfdjJhxxService.updateDsfdjJhxxByBdcslbh(jsonObject, bdcslbh);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "第三方系统更新BDC_DSFDJ_JHXX中DSFDJZT时，更新任务状态",notes = "第三方系统更新BDC_DSFDJ_JHXX中DSFDJZT时，更新任务状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcDsfdjJhxxDO", value = "第三方交互信息", required = true, dataType = "BdcDsfdjJhxxDO", paramType = "body")
    })
    public void dsfTzModifyTaskStatus(@RequestBody BdcDsfdjJhxxDO bdcDsfdjJhxxDO) {
        bdcDsfdjJhxxService.dsfTzModifyTaskStatus(bdcDsfdjJhxxDO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "删除不动产与第三方交互信息",notes = "删除不动产与第三方交互信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流示例 ID", required = true, dataType = "String", paramType = "query")
    })
    public void deleteDsfdjJhxx(@RequestParam(value = "processInsId") String processInsId){
        bdcDsfdjJhxxService.deleteDsfdjJhxx(processInsId);

    }

    /**
     * 数据库解密函数调用接口
     *
     * @param decryptDto
     * @return
     */
    @Override
    public String dsfDecrypt(@RequestBody DecryptDto decryptDto) {
        String decrypt = aeSutil.decrypt(decryptDto.getDecryptkey());
        try {
            //转成数据的编码格式
            Base64Utils.encodeByteToBase64Str(decrypt.getBytes());
            String gbk = new String(decrypt.getBytes(encode), encode);
            return Base64Utils.encodeByteToBase64Str(gbk.getBytes(encode));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decrypt;
    }

    /**
     * 数据库加密函数调用接口
     *
     * @param decryptDto
     * @return
     */
    @Override
    public String dsfEncrypt(@RequestBody DecryptDto decryptDto) {
        return aeSutil.encrypt(decryptDto.getEncryptkey());
    }
}
