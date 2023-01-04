package cn.gtmap.realestate.natural.core.service;

import cn.gtmap.realestate.common.core.dto.certificate.BdcBdcqzhDTO;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/4
 * @description  不动产权证号相关逻辑处理接口定义
 */
public interface ZrzyDjcqzhService {
//    /**
//     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
//     * @param   xmid 项目ID
//     * @return  {List} 不动产权证号信息集合
//     * @description  生成单个项目不动产项目证书（明）号
//     */
//    List<BdcBdcqzhDTO> generateBdcqzh(String xmid);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   processInsId 流程实例ID
     * @return  {List} 不动产权证号信息集合
     * @description  流程关联项目生成证书（明）号
     */
    void generateCqzhOfPro(String processInsId);
}
