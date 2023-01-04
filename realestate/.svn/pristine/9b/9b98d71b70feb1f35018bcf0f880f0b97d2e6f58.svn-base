package cn.gtmap.realestate.common.core.service.rest.certificate;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/4
 * @description 不动产印制号
 */
public interface BdcYzhRestService {

    /**
     * @param yzhid 印制号主键
     * @return BdcYzhDTO 返回印制号DTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 主键获取印制号
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/yzh/{yzhid}", method = RequestMethod.GET)
    BdcYzhDTO queryBdcYzh(@PathVariable(name = "yzhid") String yzhid);

    /**
     * @param zsid     证书ID
     * @param bdcYzhQO 印制号查询QO
     * @return BdcYzhDTO> 印制号DTO对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 为证书获取印制号并更新到证书
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/{zsid}/yzh", method = RequestMethod.POST)
    BdcYzhDTO queryBdcZsYzh(@PathVariable(name = "zsid") String zsid, @RequestBody BdcYzhQO bdcYzhQO);

    /**
     * @param bdcYzhQOList 印制号查询QOList
     * @return List<BdcYzhDTO> 获取结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 证书批量获取印制号
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/yzh/batchZs", method = RequestMethod.POST)
    List<BdcYzhDTO> queryBatchZsYzh(@RequestBody List<BdcYzhQO> bdcYzhQOList);

    /**
     * @param num      需要获取的印制号的数量
     * @param bdcYzhQO 印制号查询参数
     * @return List<BdcYzhDTO> 返回印制号信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取所需数量的印制号, 数量以路径参数num为主
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/yzh/{num}", method = RequestMethod.POST)
    List<BdcYzhDTO> queryBatchYzh(@PathVariable(name = "num") int num, @RequestBody BdcYzhQO bdcYzhQO);

    /**
     * @param bdcYzhQO 印制号查询对象
     * @return List<BdcYzhDTO> 返回印制号DTOList
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 条件查询印制号
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/yzh", method = RequestMethod.POST)
    List<BdcYzhDTO> queryListBdcYzh(@RequestBody BdcYzhQO bdcYzhQO);

    /**
     * @param bdcYzhSyqkQO 不动产印制号使用情况QO
     * @return BdcYzhsymxDO 生成的印制号使用明细
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新不动产印制号使用情况, 生成印制号使用明细
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/yzh", method = RequestMethod.PATCH)
    BdcYzhsymxDO updateBdcYzhSyqk(@RequestBody BdcYzhSyqkQO bdcYzhSyqkQO);

    /**
     * @param bdcYzhSyqkQO 不动产印制号使用情况QO
     * @return BdcYzhsymxDO 生成的印制号使用明细
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 还原被手动修改的印制号信息
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/revertYzhAndSyqk", method = RequestMethod.PATCH)
    Integer revertYzhAndSyqk(@RequestBody BdcYzhSyqkQO bdcYzhSyqkQO);

    /**
     * @param bdcYzhQO 印制号查询QO
     * @return BdcGzyzTsxxDTO 验证提示信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 验证印制号是否可用
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/yzh/yzxx", method = RequestMethod.POST)
    BdcYzxxDTO queryYzhYzxx(@RequestBody BdcYzhQO bdcYzhQO);

    /**
     * @param bdcYzhQO 印制号查询QO
     * @return 验证提示信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  验证印制号是否可用,不可用返回验证不通过原因
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/yzh/yztsxx")
    BdcYzxxDTO queryYzhYztsxx(@RequestBody BdcYzhQO bdcYzhQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param qzysxlh 权证印刷序列号（印制号）
     * @param userName 用户账号
     * @description 验证某个用户将使用的印制号状态，例如是否可用
     */
    @GetMapping("/realestate-certificate/rest/v1.0/yzh/status")
    BdcYzhZtDTO checkYzhStatus(@RequestParam("qzysxlh")String qzysxlh, @RequestParam("userName")String userName);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param processInsId 工作流实例ID
     * @param zsid 证书ID
     * @return {String} 虚拟印制号
     * @description 当领证方式为电子证照时，获取虚拟印制号
     */
    @GetMapping("/realestate-certificate/rest/v1.0/yzh/xnyzh")
    String getXnyzh(@RequestParam("processInsId") String processInsId, @RequestParam("zsid") String zsid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zsid 证书ID
     * @description 清除虚拟印制号
     */
    @PutMapping("/realestate-certificate/rest/v1.0/yzh/xnyzh/clear")
    int updateXnyzhToNull(@RequestParam("zsid")String zsid);

    /**
     * @author: <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param processInsId 工作流实例ID
     * @param bdcYzhQO 证书证明ID集合
     * @return: {String} 领证方式
     * @description 批量获取虚拟印制号
     */
    @PostMapping("/realestate-certificate/rest/v1.0/yzh/xnyzh/batch")
    List<BdcZsDO> getBatchXnyzh(@RequestParam("processInsId")String processInsId, @RequestBody BdcYzhQO bdcYzhQO);


    /**
     * 印制号废证量统计
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcYzhTjQO 不动产印制号统计QO
     * @return 统计结果
     */
    @PostMapping("/realestate-certificate/rest/v1.0/yzh/fzl/tj")
    List<BdcYzhFzlTjDTO> yzhFzlTj(@RequestBody BdcYzhTjQO bdcYzhTjQO);

    /**
     * @param yzhidList 印制号主键列表
     * @return BdcYzhExcelDTO 返回印制号ExcelDTO
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description 主键获取印制号信息及明细
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/yzh/listSymx", method = RequestMethod.POST)
    List<BdcYzhExcelDTO> queryBdcYzhSymx(@RequestBody List<String> yzhidList);

}