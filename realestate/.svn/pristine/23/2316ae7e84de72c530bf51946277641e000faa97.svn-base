package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.realestate.common.core.service.rest.certificate.CertificateWorkflowRestService;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcSzxxVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/1/21
 * @description
 */
@RestController
@Api(tags = "证书归档子系统工作流服务")
public class CertificateWorkflowRestController implements CertificateWorkflowRestService {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CertificateWorkflowRestController.class);

    @Autowired
    BdcFzjlRestController bdcFzjlRestController;
    @Autowired
    BdcGdxxRestController bdcGdxxRestController;

    /**
     * @param processInsId  工作流实例
     * @param currentUserName 当前用户名/账户名
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新发证人信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新发证人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", dataType = "string", paramType = "query")
            , @ApiImplicitParam(name = "currentUserName", value = "用户名/账户名", dataType = "string", paramType = "query")})
    @Override
    public int updateFzr(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName) {
        return bdcFzjlRestController.updateFzr(processInsId, currentUserName);
    }

    /**
     * @param processInsId    工作流实例ID
     * @param currentUserName 当前用户名/账户名
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存缮证人，缮证时间
     */
    @ApiOperation("保存缮证人，缮证时间")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", dataType = "string", paramType = "query")
            , @ApiImplicitParam(name = "currentUserName", value = "用户名/账户名", dataType = "string", paramType = "query")})
    @Override
    public BdcSzxxVO updateSzr(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName) {
        return bdcFzjlRestController.updateSzr(processInsId, currentUserName);
    }

    /**
     * @param processInsId@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 归档不动产信息
     */
    @ApiOperation("归档不动产信息")
    @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", dataType = "string", paramType = "query")
    @Override
    public void gdBdcXx(@RequestParam(value = "processInsId") String processInsId,@RequestParam(value = "currentUserName")String currentUserName) {
        try {
            bdcGdxxRestController.postArchiveByPz(processInsId, null, null,currentUserName);
        } catch (Exception e) {
            LOGGER.error("归档不动产信息操作异常！", e);
        }
    }
}
