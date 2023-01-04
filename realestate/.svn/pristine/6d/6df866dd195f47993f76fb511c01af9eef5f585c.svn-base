package cn.gtmap.realestate.inquiry.web.rest.yancheng;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZqDjDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZqThxxDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZqZxsqDO;
import cn.gtmap.realestate.common.core.dto.ExcelExportDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcZqDjDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcZqLcthDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcZqXxhzDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcZqZxsqDTO;
import cn.gtmap.realestate.common.core.dto.pub.ResponseHeadEntityDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.rest.inquiry.yancheng.BdcZqRestService;
import cn.gtmap.realestate.inquiry.service.yancheng.BdcZqSdService;
import cn.gtmap.realestate.inquiry.service.yancheng.BdcZqService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/12/15
 * @description  盐城征迁系统服务接口
 */
@RestController
@Api(tags = "盐城征迁系统服务接口")
public class BdcZqRestController implements BdcZqRestService {
    @Autowired
    private BdcZqService bdcZqService;

    @Autowired
    private BdcZqSdService bdcZqSdService;


    /**
     * 保存注销申请信息
     * @param bdcZqZxsqDO 注销申请信息
     * @return String 注销申请信息ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存注销申请信息")
    @ApiImplicitParam(name = "bdcZqZxsqDO", value = "注销申请信息", required = true, paramType = "body")
    public String saveZxsq(@RequestBody BdcZqZxsqDO bdcZqZxsqDO) {
        return bdcZqService.saveZxsq(bdcZqZxsqDO);
    }

    /**
     * 更新注销申请
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcZqZxsqDTO
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("删除注销申请")
    @ApiImplicitParam(name = "bdxZqZxsqDTO", value = "参数信息", required = true, paramType = "body")
    public void updateZxsq(@RequestBody BdcZqZxsqDTO bdcZqZxsqDTO) {
        bdcZqService.updateZxsq(bdcZqZxsqDTO);
    }

    /**
     * 获取注销申请信息
     * @param sqxxid 注销申请信息ID
     * @return BdcZqZxsqDO 注销申请信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取注销申请信息")
    @ApiImplicitParam(name = "sqxxid", value = "注销申请信息ID", required = true, paramType = "param")
    public BdcZqZxsqDO getZxsq(@RequestParam("sqxxid")String sqxxid) {
        return bdcZqService.getZxsq(sqxxid);
    }

    /**
     * 解冻
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcZqDjDOS
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新冻结信息")
    @ApiImplicitParam(name = "bdcZqDjDTO", value = "参数信息", required = true, paramType = "body")
    public void batchUpdateDj(@RequestBody List<BdcZqDjDO> bdcZqDjDOS) {
        if(CollectionUtils.isEmpty(bdcZqDjDOS)){
            throw new AppException("冻结信息不能为空!");
        }
        bdcZqService.batchUpdateDj(bdcZqDjDOS);
    }

    /**
     * 新建附件文件夹
     * @param sqxxid 注销申请信息ID
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("新建附件文件夹")
    @ApiImplicitParam(name = "sqxxid", value = "注销申请信息ID", required = true, paramType = "body")
    public String createZxsqFjcl(@RequestParam("sqxxid")String sqxxid) {
        return bdcZqService.createZxsqFjcl(sqxxid);
    }

    /**
     * 锁定不动产单元和证书
     * @param bdcZqZxsqDO 注销申请信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("锁定不动产单元和证书")
    @ApiImplicitParam(name = "bdcZqZxsqDO", value = "注销申请信息", required = true, paramType = "body")
    public void sdBdcdyZs4Zx(@RequestBody BdcZqZxsqDO bdcZqZxsqDO) {
        bdcZqSdService.sdBdcdyZs4Zx(bdcZqZxsqDO);
    }

    /**
     * 征迁审批完，在登记查看注销申请台账 新建注销项目
     * @param bdcZqZxsqDTO 注销申请信息
     * @return BdcXmDO 项目信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("新建注销项目")
    @ApiImplicitParam(name = "bdcZqZxsqDO", value = "注销申请信息", required = true, paramType = "body")
    public BdcXmDO createProcess(@RequestBody BdcZqZxsqDTO bdcZqZxsqDTO) {
        return bdcZqService.createProcess(bdcZqZxsqDTO);
    }

    /**
     * 获取征迁冻结文号
     * @return String 冻结文号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取征迁冻结文号")
    public String getDjwh() {
        return bdcZqService.getDjwh();
    }

    /**
     * 图形冻结选中不动产单元处理接口
     * @param djDOList 冻结单元信息
     * @return ResponseHeadEntityDTO 返回信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("图形冻结选中不动产单元处理接口")
    @ApiImplicitParam(name = "djDOList", value = "冻结单元信息", required = true, paramType = "body")
    public ResponseHeadEntityDTO djBdcdy(@RequestBody List<BdcZqDjDO> djDOList) {
        return bdcZqService.djBdcdy(djDOList);
    }

    /**
     * 征迁在审核通过台账退回功能请求
     *
     * @param bdcZqThxxDO 退回信息
     * @return 退回信息ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("征迁在审核通过台账退回功能请求")
    @ApiImplicitParam(name = "bdcZqThxxDO", value = "退回信息", required = true, paramType = "body")
    public String zxsqTh(@RequestBody BdcZqThxxDO bdcZqThxxDO) {
        return bdcZqService.zxsqTh(bdcZqThxxDO);
    }

    /**
     * 征迁流程删除退回信息同步接口
     *
     * @param bdcZqLcthDTO 删除退回信息
     * @return 退回信息ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("征迁流程删除退回信息同步接口")
    @ApiImplicitParam(name = "bdcZqThxxDO", value = "删除退回信息", required = true, paramType = "body")
    public String zxsqLcth(@RequestBody BdcZqLcthDTO bdcZqLcthDTO) {
        return bdcZqService.zxsqLcth(bdcZqLcthDTO);
    }

    /**
     * 单元冻结（全部冻结）
     * @param bdcZqDjDTO 参数信息
     * @return 返回状态
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("征迁流程删除退回信息同步接口")
    @ApiImplicitParam(name = "bdcZqDjDTO", value = "参数信息", required = true, paramType = "body")
    public ResponseHeadEntityDTO dydjQbdj(@RequestBody BdcZqDjDTO bdcZqDjDTO) {
        return bdcZqService.dydjQbdj(bdcZqDjDTO);
    }

    /**
     * 组织“产权查询指定区域不动产信息汇总”Excel导出参数
     *
     * @param bdcZqXxhzDTO 参数信息
     * @return ExcelExportDTO 导出Excel相关参数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("组织“产权查询指定区域不动产信息汇总”Excel导出参数")
    @ApiImplicitParam(name = "bdcZqXxhzDTO", value = "参数信息", required = true, paramType = "body")
    public ExcelExportDTO xxhzExcel(@RequestBody BdcZqXxhzDTO bdcZqXxhzDTO) {
        return bdcZqService.xxhzExcel(bdcZqXxhzDTO);
    }

    /**
     * 组织“指定区域内的现势建设用地使用权”Excel导出参数
     *
     * @param bdcZqXxhzDTO 参数信息
     * @return ExcelExportDTO 导出Excel相关参数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("组织“指定区域内的现势建设用地使用权”Excel导出参数")
    @ApiImplicitParam(name = "bdcZqXxhzDTO", value = "参数信息", required = true, paramType = "body")
    public ExcelExportDTO xxhzJsydsyqExcel(@RequestBody BdcZqXxhzDTO bdcZqXxhzDTO) {
        return bdcZqService.xxhzJsydsyqExcel(bdcZqXxhzDTO);
    }

    /**
     * 批量新增注销申请信息
     * @param bdcZqZxsqDOS 注销申请信息
     * @return String 注销申请编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("批量新增注销申请信息")
    @ApiImplicitParam(name = "bdcZqZxsqDOS", value = "注销申请信息", required = true, paramType = "body")
    public String batchInsertZxsq(@RequestBody List<BdcZqZxsqDO> bdcZqZxsqDOS){
        return bdcZqService.batchInsertZxsq(bdcZqZxsqDOS);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据bdcdyh获取注销申请信息")
    @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, paramType = "param")
    public BdcZqZxsqDO queryZxsqByBdcdyh(@RequestParam("bdcdyh")String bdcdyh){
        return bdcZqService.queryZxsqByBdcdyh(bdcdyh);

    }
}
