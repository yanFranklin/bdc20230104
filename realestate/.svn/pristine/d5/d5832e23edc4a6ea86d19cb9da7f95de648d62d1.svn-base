package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.realestate.certificate.core.service.BdcXmService;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.certificate.web.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcZsXmDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.service.rest.certificate.BdcZsxmRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">huangjian</a>"
 * @version 1.0,
 * @description 证书项目服务
 */
@RestController
@Api(tags = "不动产证书项目服务接口")
public class BdcZsxmRestController extends BaseController implements BdcZsxmRestService {
    @Autowired
    BdcXmService bdcXmService;

    @Autowired
    private BdcZsService bdcZsService;



    /**
     * 根据证书ID查询不动产项目信息
     *
     * @param zsid 证书ID
     * @return 不动产项目信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public List<BdcXmDO> listBdcXmByZsid(@RequestParam("zsid") String zsid) {
        return bdcXmService.listBdcXmByZsid(zsid);
    }

    /**
     * 查询项目以及关联的证书信息
     * @param bdcXmDTOList 单元信息等
     * @return 项目以及关联的证书信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询项目以及关联的证书信息")
    @ApiImplicitParam(name = "bdcXmDTOList", value = "单元信息等", required = true, paramType = "body")
    public List<BdcZsXmDTO> listBdcXmZs(@RequestBody List<BdcXmDTO> bdcXmDTOList) {
        return bdcZsService.listBdcXmZs(bdcXmDTOList);
    }
}
