package cn.gtmap.realestate.natural.service;

import cn.gtmap.realestate.common.core.domain.BdcJgLzrGxDO;
import cn.gtmap.realestate.common.core.domain.BdcXtJgDO;
import cn.gtmap.realestate.common.core.dto.init.BdcJgLzrGxDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcXtjgLzrQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * 不动产系统机构配置 service
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0
 */
public interface ZrzyXtJgService {

    /**
     * 获取全部信息银行
     *@param  jglb
     * @return list
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    List<BdcXtJgDO> listBdcXtJg(Integer jglb);

    /**
     * 根据系统机构查询参数，获取不动产系统机构信息
     * @param bdcXtJgDO 不动产系统机构DO
     * @return 系统机构信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcXtJgDO> listBdcXtJg(BdcXtJgDO bdcXtJgDO);

    /**
     * 根据机构名称查询出对应机构信息
     *
     * @param jgmc 机构名称
     * @return
     */
    BdcXtJgDO queryJgByJgMc(String jgmc, String jglb);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取按月结算银行信息
     */
    List<BdcXtJgDO> listAyjsBdcXtJg(String yhmc);

    /**
     * 根据银行名称查询是否按月结算银行（模糊匹配）
     * @param yhmc 银行名称
     * @return 月结银行
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcXtJgDO> listAyjsBdcXtJgLike(String yhmc);

    /**
     * 根据机构id查询配置的领证人信息
     * @param jgid
     * @return
     */
    List<BdcJgLzrGxDO> queryJgLzrByJgid(String jgid);

    /**
     * 查询机构领证人分页
     * @param bdcXtjgLzrQO
     * @param pageable
     * @return
     */
    Page<BdcJgLzrGxDTO> queryJgLzrByPage(Pageable pageable, BdcXtjgLzrQO bdcXtjgLzrQO);

    /**
     * 删除机构领证人
     * @param gxid
     */
    void deleteJglzr(String gxid);

    /**
     * 新增机构领证人
     * @param bdcJgLzrGxDO
     */
    void insertBdcLzr(BdcJgLzrGxDO bdcJgLzrGxDO);

    /**
     * 批量修改系统机构是否可用
     * @param yhmcList  银行名称
     * @param sfky  是否可用（1：可用 0：不可用）
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void batchModifyXtJgSfky(List<String> yhmcList, int sfky);

    /**
     * 新增机构
     * @param bdcXtJgDO
     */
    void insertBdcXtJg(BdcXtJgDO bdcXtJgDO);
    /**
     * @param jgmclist 机构名称集合
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description
     */
    List<BdcXtJgDO> queryBatchBdcXtJg(List<String> jgmclist);
}
