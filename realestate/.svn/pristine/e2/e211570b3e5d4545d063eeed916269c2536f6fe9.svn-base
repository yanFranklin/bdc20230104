package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcFcdaDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcFwqlDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcFwJbxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFwqlQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZfxxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.yancheng.BdcFwQsxxQO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/7/9
 * @description 住房信息查询逻辑层接口定义
 */
public interface BdcZfxxCxService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZfxxQO 查询参数
     * @return {List} 住房信息
     * @description 根据权利人名称、证件号查询房产信息
     */
    List<BdcZfxxDTO> listBdcZfxxDTO(BdcZfxxQO bdcZfxxQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZfxxQO 查询参数
     * @return {List} 住房信息
     * @description 根据权利人名称、证件号查询房产信息
     */
    List<BdcZfxxDTO> listBdcNtZfxxDTO(BdcZfxxQO bdcZfxxQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyh 不动产单元号
     * @param qjgldm 权籍管理代码
     * @return {List} 房产档案信息
     * @description 根据不动产单元号查询房产档案信息
     */
    BdcFcdaDTO getBdcFcdaDTO(String bdcdyh,String qjgldm);
    /**
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @param bdcFwqlQO 查询参数
     * @return {List} 房屋权利
     * @description 自助查询机查询房屋权利
     */
    List<BdcFwqlDTO> listBdcFwqlDTO(BdcFwqlQO bdcFwqlQO);

    /**
     * （盐城）查询房屋权属信息（以物为主）
     * @param bdcFwQsxxQO 查询参数
     * @return {List} 房屋权属信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    List<BdcFwJbxxDTO> listBdcFwQsxxDTO(BdcFwQsxxQO bdcFwQsxxQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZfxxQO 查询参数
     * @return {List} 住房信息
     * @description （盐城）根据权利人名称、证件号查询历史房产信息（例如三年内在不动产登记系统中由于买卖转移登记而注销的产权信息）
     */
    List<BdcZfxxDTO> listBdcLsZfxxDTO(BdcZfxxQO bdcZfxxQO);


    /**
     * 自助查询机查询合同信息
     * @param bdcZfxxQO
     * @return
     */
    public List<BdcZfxxDTO> listBdcHtxx(BdcZfxxQO bdcZfxxQO);
}
