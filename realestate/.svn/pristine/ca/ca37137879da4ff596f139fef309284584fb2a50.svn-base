package cn.gtmap.realestate.inquiry.web.rest.changzhou;

import cn.gtmap.realestate.common.core.qo.inquiry.BdcCxzmdPdfQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.changzhou.BdcXxcxRestService;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcCxzmdPdfVO;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcStfzmCxPdfVO;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcdjbcxPdfVO;
import cn.gtmap.realestate.inquiry.service.changzhou.BdcXxcxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @date 2020/8/25 15:07
 * @description 常州查询证明接口处理
 */
@RestController
@Api(tags = "常州登记3.0信息查询")
public class BdcXxcxRestController implements BdcXxcxRestService {
    @Autowired
    private BdcXxcxService bdcXxcxService;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcCxzmdPdfQO 查询参数
     * @return {BdcCxzmdPdfVO} 返回PDF数据
     * @description 查询权利人房产证明等内容并生成对应PDF文件
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询权利人房产证明等内容并生成对应PDF文件")
    @ApiImplicitParam(name = "bdcCxzmdPdfQO", value = "参数信息", required = true, paramType = "body")
    public BdcCxzmdPdfVO generateCxzmdPdf(@RequestBody BdcCxzmdPdfQO bdcCxzmdPdfQO) {
        return bdcXxcxService.generateCxzmdPdf(bdcCxzmdPdfQO);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcCxzmdPdfQO 查询参数
     * @return {BdcCxzmdPdfVO} 返回PDF数据
     * @description 查询汇总pdf
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询权利人房产证明等内容并生成对应PDF文件 汇总信息")
    @ApiImplicitParam(name = "bdcCxzmdPdfQO", value = "参数信息", required = true, paramType = "body")
    public BdcCxzmdPdfVO generateCxzmdHzPdf(@RequestBody BdcCxzmdPdfQO bdcCxzmdPdfQO) {
        return bdcXxcxService.generateCxzmdHzPdf(bdcCxzmdPdfQO);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcCxzmdPdfQO 查询参数
     * @return {BdcCxzmdPdfVO} 返回PDF数据
     * @description 查询明细pdf
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询权利人房产证明等内容并生成对应PDF文件 明细信息")
    @ApiImplicitParam(name = "bdcCxzmdPdfQO", value = "参数信息", required = true, paramType = "body")
    public BdcCxzmdPdfVO generateCxzmdMxPdf(@RequestBody BdcCxzmdPdfQO bdcCxzmdPdfQO) {
        return bdcXxcxService.generateCxzmdMxPdf(bdcCxzmdPdfQO);
    }

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @param bdcCxzmdPdfQO 查询参数
     * @return {BdcCxzmdPdfVO} 返回PDF数据
     * @description 房屋套次查询pdf接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("房屋套次查询pdf接口")
    @ApiImplicitParam(name = "bdcCxzmdPdfQO", value = "参数信息", required = true, paramType = "body")
    public BdcdjbcxPdfVO generateBdcdjbcxPdf(@RequestBody BdcCxzmdPdfQO bdcCxzmdPdfQO) {
        return bdcXxcxService.generateBdcdjbcxPdf(bdcCxzmdPdfQO);
    }

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @param bdcCxzmdPdfQO 查询参数
     * @return {BdcCxzmdPdfVO} 返回不动产登记簿查询PDF数据
     * @description 不动产登记簿查询pdf接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("不动产登记簿查询pdf接口")
    @ApiImplicitParam(name = "bdcCxzmdPdfQO", value = "参数信息", required = true, paramType = "body")
    public BdcStfzmCxPdfVO generateStfzmPdf(@RequestBody BdcCxzmdPdfQO bdcCxzmdPdfQO) {
        return bdcXxcxService.generateStfzmPdf(bdcCxzmdPdfQO);
    }
}
