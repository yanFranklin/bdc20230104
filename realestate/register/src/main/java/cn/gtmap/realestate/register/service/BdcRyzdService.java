package cn.gtmap.realestate.register.service;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcBdcqzhDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcQlrXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcSyqxPlDTO;
import cn.gtmap.realestate.register.core.dto.BdcRyzdDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/11/12
 * @description 不动产冗余字段逻辑处理定义接口
 */
public interface BdcRyzdService {
    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   xmid 项目ID
     * @return  {BdcRyzdDTO} 冗余字段
     * @description 获取冗余字段
     */
    BdcRyzdDTO getRyzd(String xmid);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   xmid 项目ID
     * @param qlrlb
     * @param bdcQlrDOList
     * @return  {BdcRyzdDTO} 冗余字段
     * @description 获取权利人冗余字段
     */
    BdcRyzdDTO getRyzdQlr(String xmid, Integer qlrlb, List<BdcQlrDO> bdcQlrDOList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcRyzdDTO 冗余字段
     * @description 根据项目ID关联处理不动产项目中存在的冗余字段
     */
    void updateRyzd(BdcRyzdDTO bdcRyzdDTO);

    /**
     * @param xmid       项目ID
     * @param listBdcqzh 项目生成的所有证号
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取需要更新的证号信息
     */
    BdcRyzdDTO getRyzdBdcqzh(String xmid, List<BdcBdcqzhDTO> listBdcqzh);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcqzhMap 项目、证号对应信息集合
     * @description 更新流程对应项目、权利人证号冗余字段
     */
    void updateRyzdBdcqzh(Map<String, List<BdcBdcqzhDTO>> bdcqzhMap);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcRyzdDTO 权利人冗余字段
     * @description 根据项目ID关联处理不动产项目中存在的权利人冗余字段
     */
    void updateRyzdQlrByXmid(BdcRyzdDTO bdcRyzdDTO);

    /**
     * @param xmid 项目ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新共有情况字段信息（权利人表和各个权利表）
     */
    void updateGyqk(String xmid);

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid    项目ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 首次登记，批量更新唯一权利人等值
     */
    void updateRyzdPl(String gzlslid, String xmid);

    /**
     * @param bdcQlrXmDTOList 权利人项目DTO对象List
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新权人相关冗余字段
     */
    void updateRyzdQlrXm(List<BdcQlrXmDTO> bdcQlrXmDTOList);

    /**
     * @param xmid 项目ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新项目的使用期限
     */
    void updateSyqx(String xmid);

    /**
     * @param bdcSyqxPlDTO 使用期限更新实体
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新项目的使用期限(批量)
     */
    void updateSyqxPl(@RequestBody BdcSyqxPlDTO bdcSyqxPlDTO) throws Exception;
}
