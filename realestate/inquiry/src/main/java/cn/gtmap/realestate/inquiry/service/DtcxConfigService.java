package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxjgDO;
import cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxtjDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.DtcxConfigCheckDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDtcxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/3/19
 * @description
 */
public interface DtcxConfigService{
    /**
     * 新增或更新动态查询
     *
     * @param bdcDtcxDTO
     * @param fuzhiCx
     * @return
     */
    void saveAllDtcxCxxx(BdcDtcxDTO bdcDtcxDTO,boolean fuzhiCx);

    /**
     * 通过SQL获取参数和结果列名
     *
     * @param cxsql
     * @return
     */
    Map getKeysAndParams(String cxsql);

    /**
     * 逻辑删除查询
     *
     * @param cxid
     * @return
     */
    void deleteDtcxCx(String cxid);

    /**
     * 检查查询条件
     *
     * @param sql
     * @param cxtjList
     * @return
     */
    DtcxConfigCheckDTO checkCxtj(String sql,List<DtcxCxtjDO> cxtjList);

    /**
     * 检查查询结果
     *
     * @param sql
     * @param cxjgList
     * @return
     */
    DtcxConfigCheckDTO checkCxjg(String sql,List<DtcxCxjgDO> cxjgList);

    /**
     * 获取查询结果List
     *
     * @param cxid
     * @return
     */
    List<DtcxCxjgDO> getCxjgList(String cxid);

    /**
     * 获取已创建的动态查询
     *
     * @return
     */
    Page<BdcDtcxDTO> listDtcxPage(BdcDtcxQO qo,Pageable pageable);

    /**
     * 通过查询代号获取当前代号查询的所有配置信息，包括查询基本信息、查询条件、结果的配置
     *
     * @param cxdh 查询代号，查询标识唯一值
     * @return cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO
     * @date 2019.03.22 15:09
     * @author hanyaning
     */
    BdcDtcxDTO getConfigsByCxdh(String cxdh);
}
