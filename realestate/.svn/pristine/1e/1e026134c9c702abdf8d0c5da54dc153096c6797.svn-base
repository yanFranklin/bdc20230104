package cn.gtmap.realestate.common.core.service.rest.inquiry.yancheng;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/12/16
 * @description 盐城征迁系统服务接口
 */
public interface BdcZqRestService {
    /**
     * 保存注销申请信息
     * @param bdcZqZxsqDO 注销申请信息
     * @return String 注销申请信息ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/zq/zxsq")
    String saveZxsq(@RequestBody BdcZqZxsqDO bdcZqZxsqDO);

    /**
     * 更新注销申请
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcZqZxsqDTO
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/zq/zxsq/update")
    void updateZxsq(@RequestBody BdcZqZxsqDTO bdcZqZxsqDTO);

    /**
     * 获取注销申请信息
     * @param sqxxid 注销申请信息ID
     * @return BdcZqZxsqDO 注销申请信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/zq/zxsq")
    BdcZqZxsqDO getZxsq(@RequestParam("sqxxid")String sqxxid);

    /**
     * 解冻
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcZqDjDOS
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/zq/dj/update")
    void batchUpdateDj(@RequestBody List<BdcZqDjDO> bdcZqDjDOS);

    /**
     * 新建附件文件夹
     * @param sqxxid 注销申请信息ID
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/zq/zxsq/fjcl")
    String createZxsqFjcl(@RequestParam("sqxxid")String sqxxid);

    /**
     * 锁定不动产单元和证书
     * @param bdcZqZxsqDO 注销申请信息
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zq/sd")
    void sdBdcdyZs4Zx(@RequestBody BdcZqZxsqDO bdcZqZxsqDO);

    /**
     * 征迁审批完，在登记查看注销申请台账 新建注销项目
     * @param bdcZqZxsqDTO 注销申请信息
     * @return BdcXmDO 项目信息
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zq/xjzxxm")
    BdcXmDO createProcess(@RequestBody BdcZqZxsqDTO bdcZqZxsqDTO);

    /**
     * 获取征迁冻结文号
     * @return String 冻结文号
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/zq/djwh")
    String getDjwh();

    /**
     * 征迁在审核通过台账退回功能请求
     * @param bdcZqThxxDO 退回信息
     * @return 退回信息ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zq/zxsq/th")
    String zxsqTh(@RequestBody BdcZqThxxDO bdcZqThxxDO);

    /**
     * 征迁流程删除退回信息同步接口
     * @param bdcZqLcthDTO 删除退回信息
     * @return 退回信息ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zq/zxsq/lcth")
    String zxsqLcth(@RequestBody BdcZqLcthDTO bdcZqLcthDTO);

    /**
     * 图形冻结选中不动产单元处理接口
     * @param djDOList 冻结单元信息
     * @return ResponseHeadEntityDTO 返回信息
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zq/djbdcdy")
    ResponseHeadEntityDTO djBdcdy(@RequestBody List<BdcZqDjDO> djDOList);

    /**
     * 单元冻结（全部冻结）
     * @param bdcZqDjDTO 参数信息
     * @return 返回状态
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zq/djbdcdy/qbdj")
    ResponseHeadEntityDTO dydjQbdj(@RequestBody BdcZqDjDTO bdcZqDjDTO);

    /**
     * 组织“产权查询指定区域不动产信息汇总”Excel导出参数
     * @param bdcZqXxhzDTO 参数信息
     * @return ExcelExportDTO 导出Excel相关参数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zq/cqcx/xxhz")
    ExcelExportDTO xxhzExcel(@RequestBody BdcZqXxhzDTO bdcZqXxhzDTO);

    /**
     * 组织“指定区域内的现势建设用地使用权”Excel导出参数
     * @param bdcZqXxhzDTO 参数信息
     * @return ExcelExportDTO 导出Excel相关参数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zq/cqcx/xxhz/jdydsyq")
    ExcelExportDTO xxhzJsydsyqExcel(@RequestBody BdcZqXxhzDTO bdcZqXxhzDTO);

    /**
     * 批量新增注销申请信息
     * @param bdcZqZxsqDOS 注销申请信息
     * @return String 注销申请编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zq/batch/zxsq")
    String batchInsertZxsq(@RequestBody List<BdcZqZxsqDO> bdcZqZxsqDOS);

    /**
     * @param bdcdyh 不动产单元号
     * @return 注销申请信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据bdcdyh获取注销申请信息
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/zq/zxsq/bdcdyh")
    BdcZqZxsqDO queryZxsqByBdcdyh(@RequestParam("bdcdyh")String bdcdyh);
}
