package cn.gtmap.realestate.inquiry.web.rest.xuancheng;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCfDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcCfxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.xuancheng.BdcLqtjDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCfxxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcJfxxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcLqtjQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcCfxxRestService;
import cn.gtmap.realestate.common.core.service.rest.inquiry.xuancheng.BdcLqCxRestService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.service.BdcCfxxService;
import cn.gtmap.realestate.inquiry.service.BdcLqcxService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0, 2022/11/21
 * @description 宣城林权类查询
 */
@RestController
public class BdcLqCxRestController implements BdcLqCxRestService {

    @Autowired
    private BdcLqcxService bdcLqcxService;

    /**
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @param bdcLqtjQO
     * @return
     * @description 林权统计查询
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("统计林权类数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcLqtjQO", value = "林权统计查询qo对象", required = true, paramType = "body")})
    public List<BdcLqtjDTO> listTjCx(@RequestBody BdcLqtjQO bdcLqtjQO) {
        return bdcLqcxService.listTjCx(bdcLqtjQO);
    }
}
