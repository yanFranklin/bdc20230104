package cn.gtmap.realestate.portal.ui.service;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.portal.BdcZfhyzDTO;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;

import java.util.List;

/**
 * @author <a href ="mailto:lixin@gtmap.cn">lixin</a>
 * @version 1.0, 2020/01/09
 * @description 规则验证处理服务
 */
public interface BdcGzyzDealService {

    /**
     * 验证规则 「listBdcGzYzTsxxOfAnyParam」
     * @param gzlslid gzlslid
     * @param checkMb 检查模板
     * @param processKey 流程key
     * @param userDto 用户信息
     * @return {List} 批量验证提示信息
     */
    List<BdcGzYzTsxxDTO> yzgz(String gzlslid, String checkMb, String processKey,UserDto userDto);

    /**
     * 平台验证
     *
     * @param taskid taskid
     * @return {List} 验证提示信息
     */
    List<BdcGzyzVO> ptyz(String taskid);

    /**
     * @param gzlslid 工作流实例ID
     * @param taskid 任务ID
     * @param processKey 流程key
     * @param userDto 用户信息
     * @return 验证提示信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 转发验证
     */
    List<BdcGzyzVO> zfyz(String gzlslid, String processKey, String taskid, UserDto userDto);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 转发后验证
      */
    List<BdcGzyzVO> zfhyz(BdcZfhyzDTO bdcZfhyzDTO);


}
