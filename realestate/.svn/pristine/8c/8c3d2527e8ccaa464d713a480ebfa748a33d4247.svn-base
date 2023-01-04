package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.service.rest.register.BdcGzyzRestService;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.register.service.BdcGzyzService;
import cn.gtmap.realestate.register.web.main.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/12/13
 * @description 规则验证RestController
 */
@RestController
@Api(tags = "规则验证")
public class BdcGzyzRestController extends BaseController implements BdcGzyzRestService {
    @Autowired
    BdcGzyzService bdcGzyzService;


    /**
     * @param gzlslid 工作流实例ID
     * @return List 查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 规则验证-房改房-是否允许办理查询
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "规则验证-房改房-是否允许办理查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "string", paramType = "path")})
    @Override
    public BdcGzyzVO checkFgfSfyxbl(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcGzyzService.checkFgfSfyxbl(gzlslid);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 验证结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 规则验证-税务-建设银行缴库入库状态（未入库，则补退）
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "规则验证-税务-建设银行缴库入库状态（未入库，则补退）")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "string", paramType = "path")})

    @Override
    public BdcGzyzVO checkYhjkrk(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcGzyzService.checkYhjkrk(gzlslid);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 返回验证结果信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 登簿前验证税费缴库入库状态
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "登簿前验证税费缴库入库状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "string", paramType = "path")})
    @Override
    public BdcGzyzVO checkSfjkrk(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcGzyzService.checkSfjkrk(gzlslid);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 验证结果信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 登簿前验证线下缴费是否已上传税费缴纳凭证
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "登簿前验证线下缴费是否已上传税费缴纳凭证")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "string", paramType = "path")})
    @Override
    public BdcGzyzVO checkXxJfPz(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcGzyzService.checkXxJfPz(gzlslid);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 验证结果信息
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 验证抵押权首次登记的抵押权人是否全部是签约银行
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "验证抵押权首次登记的抵押权人是否全部是签约银行")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "path")})
    @Override
    public Map<String, String> checkSfxyjg(@PathVariable(value = "processInsId") String gzlslid) {
        return bdcGzyzService.checkSfxyjg(gzlslid);
    }
}
