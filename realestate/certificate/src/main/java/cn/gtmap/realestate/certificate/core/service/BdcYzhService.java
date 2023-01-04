package cn.gtmap.realestate.certificate.core.service;

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

import java.util.List;


/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/4
 * @description 不动产印制号查询服务接口类
 */
public interface BdcYzhService {
    /**
     * @param yzhid 印制号ID
     * @return BdcYzhDTO 印制号DTO类
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 依据主键查询印制号信息
     */
    BdcYzhDTO queryBdcYzhAndYzhmx(String yzhid);

    /**
     * @param bdcYzhQO 印制号查询对象
     * @return List<BdcYzhDTO> 返回印制号DTOList
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 条件查询印制号
     */
    List<BdcYzhDTO> queryListBdcYzh(BdcYzhQO bdcYzhQO);

    /**
     * @param bdcYzhSyqkQO 不动产印制号使用情况QO对象
     * @return BdcYzhsymxDO 生成的印制号使用明细
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新不动产印制号使用情况
     */
    BdcYzhsymxDO updateBdcYzhSyqk(BdcYzhSyqkQO bdcYzhSyqkQO);

    /**
     * @param bdcYzhSyqkQO 不动产印制号使用情况QO对象
     * @return BdcYzhsymxDO 生成的印制号使用明细
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 还原手动修改的印制号信息
     */
    Integer revertYzhAndSyqk(BdcYzhSyqkQO bdcYzhSyqkQO);

    /**
     * @param zsid     证书ID
     * @param bdcYzhQO 印制号查询QO
     * @return BdcYzhDTO> 印制号DTO对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 为证书获取印制号并更新到证书
     */
    BdcYzhDTO queryBdcZsYzh(String zsid, BdcYzhQO bdcYzhQO);

    /**
     * @param bdcYzhQOList 印制号查询QOList
     * @return List<BdcYzhDTO> 印制号获取结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量获取证书印制号
     */
    List<BdcYzhDTO> queryBatchZsYzh(List<BdcYzhQO> bdcYzhQOList);

    /**
     * @param bdcYzhQO 印制号查询QO
     * @return BdcGzyzTsxxDTO 验证提示信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 验证印制号是否可用, 不可用返回null
     */
    BdcYzxxDTO queryYzhYzxx(BdcYzhQO bdcYzhQO);

    /**
     * @param bdcYzhQO 印制号查询QO
     * @return 验证提示信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  验证印制号是否可用,不可用返回验证不通过原因
     */
    BdcYzxxDTO queryYzhYztsxx(BdcYzhQO bdcYzhQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param qzysxlh 权证印刷序列号（印制号）
     * @param userName 用户账号
     * @description 验证某个用户将使用的印制号状态，例如是否可用
     */
    BdcYzhZtDTO checkYzhStatus(String qzysxlh, String userName);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param processInsId 工作流实例ID
     * @param zsid 证书ID
     * @return {String} 虚拟印制号
     * @description 当领证方式为电子证照时，获取虚拟印制号
     */
    String getXnyzh(String processInsId, String zsid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zsid 证书ID
     * @description 清除虚拟印制号
     */
    int updateXnyzhToNull(String zsid);

    /**
     * @author: <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param processInsId 工作流实例ID
     * @param bdcYzhQO 证书证明ID集合
     * @return: {String} 领证方式
     * @description 批量获取虚拟印制号
     */
    List<BdcZsDO> getBatchXnyzh(String processInsId, BdcYzhQO bdcYzhQO);

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcYzhTjQO 印制号统计QO
     * @return: 统计结果
     * @description 印制号废证量统计
     */
    List<BdcYzhFzlTjDTO> yzhFzlTj(BdcYzhTjQO bdcYzhTjQO);

    /**
     * @author: <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @param yzhidList 印制号主键列表
     * @return BdcYzhExcelDTO 返回印制号ExcelDTO
     * @description 主键获取印制号信息及明细
     */
    List<BdcYzhExcelDTO> queryBdcYzhSymx(List<String> yzhidList) ;


}
