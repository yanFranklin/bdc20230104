package cn.gtmap.realestate.portal.ui.service;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.portal.ui.core.dto.WorkFlowDTO;

import java.util.List;

/**
 * 不动产签名验证业务类
 *
 * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019/10/06
 */
public interface BdcSignService {
    /**
     * 签名验证并且签名
     *
     * @param workFlowDTOList 工作流 DTO 集合
     * @return boolean 是否签名成功
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    boolean signCheck(List<WorkFlowDTO> workFlowDTOList);

    /**
     * 签名验证并且签名<br/>
     * 用于多线程无法获取到用户信息的场景
     *
     * @param workFlowDTOList 工作流 DTO 集合
     * @return boolean 是否签名成功
     * @author <a href="mailto:foxfocus@163.com">fox</a>
     */
    boolean signWithUser(List<WorkFlowDTO> workFlowDTOList, UserDto userDto);
}
