package cn.gtmap.realestate.accept.service;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/21
 * @description 基本信息服务
 */
public interface BdcJbxxService {

    /**
     * @param bdcSlCshDTO 受理初始化信息DTO
     * @return 不动产受理基本信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理基本信息
     */
    BdcSlJbxxDO insertBdcSlJbxx(BdcSlCshDTO bdcSlCshDTO);

    /**
     * @param bdcSlCshDTO 受理初始化信息DTO
     * @return 不动产受理基本信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 存在规则例外保存不动产受理基本信息
     */
    BdcSlJbxxDO saveGzlwBdcSlJbxx(BdcSlCshDTO bdcSlCshDTO);



    /**
     * @param slbh 受理编号
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    String dealSameSlbh(String slbh);

    /**
     * @return 商品房工作流定义id
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 商品房工作流定义id
     */
    String spfGzldyid();

    /**
     * @param userDto 用户信息
     * @return 登记结构
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据用户信息获取用户登记机构
     */
    String queryDjjgByUser(UserDto userDto);

}
