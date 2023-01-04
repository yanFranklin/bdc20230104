package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcZdytjDO;
import cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxjgDO;
import cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxtjDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZdttjTbxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.DtcxConfigCheckDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDtcxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZdytjQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/3/19
 * @description
 */
public interface BdcZdytjConfigService {
    /**
     * 新增或更新动态查询
     *
     * @param bdcZdytjDO
     * @return
     */
    void saveAllDtcxCxxx(BdcZdytjDO bdcZdytjDO);

    /**
     * 逻辑删除查询
     *
     * @param cxid
     * @return
     */
    void deleteDtcxCx(String cxid);


    /**
     * 获取已创建的动态查询
     *
     * @return
     */
    Page<BdcZdytjDO> listZdytjPage(BdcZdytjQO zdytjQO, Pageable pageable);

    /**
     * 通过查询代号获取当前代号查询的所有配置信息，包括查询基本信息、查询条件、结果的配置
     *
     * @param cxdh 查询代号，查询标识唯一值
     * @return cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO
     * @date 2019.03.22 15:09
     * @author hanyaning
     */
    BdcZdytjDO getConfigsByCxdh(String cxdh);

    /**
     * 测试配置的sql是否能正常运行
     *
     * @param bdcZdytjDO 查询sql
     * @date 2019.03.22 15:09
     * @author wutao
     */
    Boolean checkSql(BdcZdytjDO bdcZdytjDO);

    /**
     * 通过查询代号获取当前代号查询的所有配置信息，包括查询基本信息、查询条件、结果的配置
     *
     * @param cxdh 查询代号，查询标识唯一值
     * @return cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO
     * @date 2019.03.22 15:09
     * @author hanyaning
     */
   BdcZdttjTbxxDTO getTbxxByCxdh(String cxdh);
}
