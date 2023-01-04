package cn.gtmap.realestate.register.service;

import cn.gtmap.realestate.common.core.dto.register.BdcHbBdcdyDTO;
import cn.gtmap.realestate.common.core.qo.register.BdcHbBdcdyQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/08/12
 * @description  合并不动产单元服务处理Service接口定义
 */
public interface BdcHbBdcdyService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable  分页参数
     * @param bdcHbBdcdyQO  查询参数
     * @return {Page} 产权信息列表
     * @description  （海门）查询待合并不动产单元信息
     */
    Page<BdcHbBdcdyDTO> queryDhbBdcdyXx(Pageable pageable, BdcHbBdcdyQO bdcHbBdcdyQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyDTOList 待合并不动产单元信息
     * @param ppBdcdyh 匹配到的新记录待使用不动产单元号
     * @return {BdcHbBdcdyDTO} 新合并生成的不动产单元记录
     * @description 执行不动产单元合并操作
     */
    BdcHbBdcdyDTO hbBdcdy(List<BdcHbBdcdyDTO> bdcdyDTOList, String ppBdcdyh);
}
