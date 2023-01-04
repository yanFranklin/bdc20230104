package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.core.domain.etl.HtbaQlrDO;
import cn.gtmap.realestate.common.core.dto.accept.FcjyBaxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.rest.etl.HtbaQlrRestService;
import cn.gtmap.realestate.common.core.vo.etl.HtbaMsrVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.etl.service.HtbaQlrService;
import cn.gtmap.realestate.etl.web.main.BaseController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: realestate
 * @description: 合同备案权利人rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-15 19:14
 **/
@RestController
@Validated
@RequestMapping("/realestate-etl/rest/v1.0/htbaqlr")
public class HtbaQlrRestController extends BaseController implements HtbaQlrRestService {

    @Autowired
    HtbaQlrService htbaQlrService;

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新权利人信息
     * @date : 2020/12/15 18:25
     */
    @ResponseBody
    @PatchMapping("")
    public Object updateBaQlr(@RequestBody String json) {
        if (StringUtils.isBlank(json)) {
            throw new AppException("保存权利人信息失败");
        }
        return htbaQlrService.saveOrUpdatHtbaQlr(json);
    }

    /**
     * @param qlrid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据权利人id删除备案权利人
     * @date : 2020/12/15 19:12
     */
    @ResponseBody
    @DeleteMapping("/{qlrid}")
    public Object deleteHtbaQlr(@PathVariable("qlrid") String qlrid) {
        if (StringUtils.isBlank(qlrid)) {
            throw new AppException("删除权利人没有对应的权利人id");
        }
        return htbaQlrService.deleteHtbaQlr(qlrid);
    }

    /**
     * @param fwhsindex
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据房屋户室index查询买受人
     * @date : 2020/12/21 17:49
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据房屋户室index查询买受人", notes = "根据房屋户室index查询买受人服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "bdcdyh", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/bdcdyh")
    public List<HtbaQlrDO> listHtbaQlr(@RequestParam("bdcdyh") String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new AppException("查询买受人缺失不动产单元号");
        }
        return htbaQlrService.listHtbaQlr(bdcdyh);
    }

    /**
     * @param bdcdyh
     * @return
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 商品房转移登记根据不动产单元号查询买受人（盐城）
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据房bdcdyh查询买受人", notes = "根据房bdcdyh查询买受人服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/{bdcdyh}")
    public FcjyBaxxDTO spfzyMsr(@PathVariable(name = "bdcdyh") String bdcdyh) {
        return htbaQlrService.spfzyMsr(bdcdyh, CommonConstantUtils.QLRLB_QLR);
    }

    /**
     * @param bdcdyhList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量查询买受人信息
     * @date : 2021/3/3 17:11
     */
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据房单元号批量查询买受人", notes = "根据房单元号批量查询买受人服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyhList", value = "bdcdyhList", dataType = "body")
    })
    @Override
    @PostMapping("/bdcdyhs")
    public List<HtbaMsrVO> listMsr(@RequestParam List<String> bdcdyhList,@RequestParam String bazt) {
        return htbaQlrService.listMsr(bdcdyhList,bazt);
    }
}
