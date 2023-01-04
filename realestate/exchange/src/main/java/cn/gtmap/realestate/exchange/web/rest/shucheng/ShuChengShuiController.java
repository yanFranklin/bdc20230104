package cn.gtmap.realestate.exchange.web.rest.shucheng;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.service.rest.exchange.ShuChengShuiService;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.service.shucheng.ShuChengSqdService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description 舒城过户
 * @Date 2022/5/11 9:09
 **/
@RestController
public class ShuChengShuiController implements ShuChengShuiService {

    @Autowired
    private ShuChengSqdService shuChengSqdService;

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //舒城水过户
     * @Date 2022/5/11 9:10
     * @modify wangyinghao 舒城科星水过户扩展了多地使用
     **/
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据工作流主键进行水过户操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流主键", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true, dataType = "String", paramType = "query")
    })
    @DsfLog(logEventName = "根据工作流主键进行水过户操作(舒城)", dsfFlag = "SDQ", requester = "BDC", responser = "SDQ")
    public void sgh(@RequestParam("processInsId") String processInsId,
                    @RequestParam(name = "userId", required = false) String userId,
                    @RequestParam(name = "qjgldm", required = false) String qjgldm
    ) {
        shuChengSqdService.sgh(processInsId, userId,qjgldm);
    }

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //检查舒城水过户是否欠费
     * @Date 2022/5/11 9:10
     * @modify wangyinghao 舒城科星水过户扩展了多地使用
     **/
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据工作流主键检查水过户是否欠费")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流主键", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true, dataType = "String", paramType = "query")
    })
    @DsfLog(logEventName = "根据工作流主键检查水过户是否欠费(舒城)", dsfFlag = "SDQ", requester = "BDC", responser = "SDQ")
    public CommonResponse<Boolean> sghjc(@RequestParam("processInsId") String processInsId,
                                         @RequestParam(name = "userId", required = false) String userId,
                                         @RequestParam(name = "qjgldm", required = false) String qjgldm

    ) {
        return shuChengSqdService.sghjc(processInsId, userId,qjgldm);
    }

    /**
     * @param processInsId
     * @Author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Description 舒城昆仑燃气过户-工作流事件
     * @Date 2022/7/11 9:10
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据工作流主键进行昆仑燃气过户操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流主键", required = true, dataType = "String", paramType = "query")
    })
    @DsfLog(logEventName = "根据工作流主键进行昆仑燃气过户操作(舒城)", dsfFlag = "KLRANGQI", requester = "BDC", responser = "KLRANGQI")
    public void klRqgh(String processInsId) {
        shuChengSqdService.klRqgh(processInsId);

    }
}
