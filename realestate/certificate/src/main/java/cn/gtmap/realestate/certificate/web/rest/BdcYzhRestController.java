package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.realestate.certificate.core.service.BdcYzhScService;
import cn.gtmap.realestate.certificate.core.service.BdcYzhService;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhsymxDO;
import cn.gtmap.realestate.common.core.dto.BdcYzxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhExcelDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhZtDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcYzhFzlTjDTO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYzhQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYzhSyqkQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcYzhTjQO;
import cn.gtmap.realestate.common.core.service.rest.certificate.BdcYzhRestService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/3
 * @description 不动产印制号服务Controller
 */
@RestController
@Api(tags = "不动产印制号服务接口")
public class BdcYzhRestController implements BdcYzhRestService {
    @Autowired
    BdcYzhService bdcYzhService;

    @Autowired
    BdcYzhScService bdcYzhScService;

    /**
     * @param yzhid 印制号主键
     * @return BdcYzhDTO 返回印制号DTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 主键获取印制号
     */
    @Override
    @ApiIgnore
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据印制号ID查询印制号以及印制号明细信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "yzhid", value = "印制号ID", dataType = "string", paramType = "path")})
    public BdcYzhDTO queryBdcYzh(@PathVariable(name = "yzhid", required = true) String yzhid) {
        return bdcYzhService.queryBdcYzhAndYzhmx(yzhid);
    }

    /**
     * @param zsid     证书ID
     * @param bdcYzhQO 印制号查询QO
     * @return BdcYzhDTO> 印制号DTO对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 为证书获取印制号并更新到证书
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "为证书获取印制号并更新到证书",extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "zsid", value = "证书ID", dataType = "string", paramType = "path")
            , @ApiImplicitParam(name = "bdcYzhQO", value = "印制号查询QO", dataType = "BdcYzhQO", paramType = "body")})
    @Override
    public BdcYzhDTO queryBdcZsYzh(@PathVariable(name = "zsid") String zsid, @RequestBody BdcYzhQO bdcYzhQO) {
        return bdcYzhService.queryBdcZsYzh(zsid, bdcYzhQO);
    }

    /**
     * @param bdcYzhQOList 印制号查询QOList
     * @return List<BdcYzhDTO> 获取结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 证书批量获取印制号
     */
    @ApiOperation(value = "证书批量获取印制号并更新到证书")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYzhQO", value = "印制号查询QO", dataType = "BdcYzhQO", paramType = "body")})
    @Override
    public List<BdcYzhDTO> queryBatchZsYzh(@RequestBody List<BdcYzhQO> bdcYzhQOList) {
        return bdcYzhService.queryBatchZsYzh(bdcYzhQOList);
    }

    /**
     * @param num      需要获取的印制号的数量
     * @param bdcYzhQO 印制号查询参数
     * @return List<BdcYzhDTO> 返回印制号信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取所需数量的印制号
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取所需数量的印制号")
    @ApiImplicitParams({@ApiImplicitParam(name = "num", value = "需要获取的印制号的数量", dataType = "int", paramType = "path")})
    @Override
    public List<BdcYzhDTO> queryBatchYzh(@PathVariable(name = "num") int num, @RequestBody BdcYzhQO bdcYzhQO) {
        return bdcYzhScService.queryBatchYzh(num, bdcYzhQO);
    }

    /**
     * @param bdcYzhQO 印制号查询对象
     * @return List<BdcYzhDTO> 返回印制号DTOList
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 条件查询印制号
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据条件查询印制号")
    @ApiIgnore
    @Override
    public List<BdcYzhDTO> queryListBdcYzh(@RequestBody BdcYzhQO bdcYzhQO) {
        return bdcYzhService.queryListBdcYzh(bdcYzhQO);
    }

    /**
     * @param bdcYzhSyqkQO 不动产印制号使用情况QO
     * @return BdcYzhsymxDO 生成的印制号使用明细
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新不动产印制号使用情况, 生成印制号使用明细
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新印制号使用情况,生成使用明细")
    public BdcYzhsymxDO updateBdcYzhSyqk(@RequestBody BdcYzhSyqkQO bdcYzhSyqkQO) {
        return bdcYzhService.updateBdcYzhSyqk(bdcYzhSyqkQO);
    }

    /**
     * @param bdcYzhSyqkQO 不动产印制号使用情况QO
     * @return BdcYzhsymxDO 生成的印制号使用明细
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 还原被手动修改的印制号信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "还原被手动修改的印制号信息")
    public Integer revertYzhAndSyqk(@RequestBody BdcYzhSyqkQO bdcYzhSyqkQO) {
        return bdcYzhService.revertYzhAndSyqk(bdcYzhSyqkQO);
    }

    /**
     * @param bdcYzhQO 印制号查询QO
     * @return BdcGzyzTsxxDTO 验证提示信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 验证印制号是否可用
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "验证印制号是否可用,不可用返回null")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYzhQO", value = "印制号验证参数", dataType = "BdcYzhQO", paramType = "body")})
    public BdcYzxxDTO queryYzhYzxx(@RequestBody BdcYzhQO bdcYzhQO) {
        return bdcYzhService.queryYzhYzxx(bdcYzhQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "验证印制号是否可用,不可用返回验证不通过原因")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYzhQO", value = "印制号验证参数", dataType = "BdcYzhQO", paramType = "body")})
    public BdcYzxxDTO queryYzhYztsxx(@RequestBody BdcYzhQO bdcYzhQO) {
        return bdcYzhService.queryYzhYztsxx(bdcYzhQO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param qzysxlh 权证印刷序列号（印制号）
     * @param userName 用户账号
     * @description 验证某个用户将使用的印制号状态，例如是否可用
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "验证某个用户将使用的印制号状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "qzysxlh", value = "权证印刷序列号", dataType = "String", paramType = "param"),
            @ApiImplicitParam(name = "userName", value = "用户账号", dataType = "String", paramType = "param")})
    public BdcYzhZtDTO checkYzhStatus(@RequestParam("qzysxlh")String qzysxlh, @RequestParam("userName")String userName) {
        return bdcYzhService.checkYzhStatus(qzysxlh, userName);
    }


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param processInsId 工作流实例ID
     * @param zsid 证书ID
     * @return {String} 虚拟印制号
     * @description 当领证方式为电子证照时，获取虚拟印制号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("当领证方式为电子证照时，获取虚拟印制号")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = false, paramType = "query"),
            @ApiImplicitParam(name = "zsid", value = "证书ID", required = false, paramType = "query")})
    public String getXnyzh(@RequestParam(required = false) String processInsId, @RequestParam(required = false) String zsid) {
        return bdcYzhService.getXnyzh(processInsId, zsid);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zsid 证书ID
     * @description 清除虚拟印制号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("清除虚拟印制号")
    @ApiImplicitParam(name = "zsid", value = "证书ID", required = false, paramType = "query")
    public int updateXnyzhToNull(@RequestParam("zsid")String zsid) {
        return bdcYzhService.updateXnyzhToNull(zsid);
    }

    /**
     * @author: <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param processInsId 工作流实例ID
     * @param bdcYzhQO 证书证明ID集合
     * @return: {String} 领证方式
     * @description 批量获取虚拟印制号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("批量获取虚拟印制号")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = false, paramType = "query"),
            @ApiImplicitParam(name = "bdcYzhQO", value = "证书证明ID集合", required = false, paramType = "body")})
    public List<BdcZsDO> getBatchXnyzh(@RequestParam("processInsId")String processInsId, @RequestBody BdcYzhQO bdcYzhQO) {
        return bdcYzhService.getBatchXnyzh(processInsId, bdcYzhQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("印制号废证量统计")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcYzhTjQO", value = "印制号统计QO", required = true, paramType = "body")})
    public List<BdcYzhFzlTjDTO> yzhFzlTj(@RequestBody BdcYzhTjQO bdcYzhTjQO) {
        return bdcYzhService.yzhFzlTj(bdcYzhTjQO);
    }

    /**
     * @author: <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @param yzhidList 印制号主键列表
     * @return BdcYzhExcelDTO 返回印制号ExcelDTO
     * @description 主键获取印制号信息及明细
     */
    @ApiOperation(value = "根据印制号ID批量查询印制号信息以及印制号明细信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "yzhidList", value = "印制号ID", dataType = "List<String>", paramType = "param")})
    @Override
    public List<BdcYzhExcelDTO> queryBdcYzhSymx(@RequestBody List<String> yzhidList) {
        return bdcYzhService.queryBdcYzhSymx(yzhidList);
    }

}