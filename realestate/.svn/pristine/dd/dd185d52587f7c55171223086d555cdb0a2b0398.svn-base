package cn.gtmap.realestate.config.service;

import cn.gtmap.realestate.common.core.domain.BdcFphDO;
import cn.gtmap.realestate.common.core.domain.BdcFphSymxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.config.BdcFphSymxDTO;
import cn.gtmap.realestate.common.core.qo.config.BdcFphQO;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcFphVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-09-06
 * @description
 */
public interface BdcXtFphService {

    /**
     * @param pageable     分页对象
     * @param fphParamJson 查询条件
     * @return {Page} 发票号配置分页数据
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询发票号配置数据列表
     */
    Page<BdcFphVO> listBdcFph(Pageable pageable, String fphParamJson);

    /**
     * @param bdcFphQO 发票号模板
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 生成发票号
     */
    int generateBdcFph(BdcFphQO bdcFphQO);

    /**
     * @param bdcFphDO 发票号配置实体
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存发票号配置配置
     */
    int saveBdcFph(BdcFphDO bdcFphDO);

    /**
     * 修改发票号信息
     * @param bdcFphDO 发票号配置实体
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void updateBdcFphxx(BdcFphDO bdcFphDO);

    /**
     * @param bdcFphDOList 发票号配置集合
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除发票号配置
     */
    int deleteBdcFph(List<BdcFphDO> bdcFphDOList);

    /**
     * @param bdcFphSymxDO 发票号使用明细
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 作废发票号
     */
    void deleteBdcFph(BdcFphSymxDO bdcFphSymxDO);

    /**
     * 作废发票号
     * @param fph 发票号
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void zfBdcFph(String fph);

    /**
     * 取消发票号
     * @param fph 发票号
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void qxBdcFph(String fph);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取发票号
     */
    List<BdcSlSfxxDO> getBdcFph(List<BdcSlSfxxDO> bdcSlSfxxDOList, String slbh,List<BdcFphDO> bdcFphDOList);

    List<BdcSlSfxxDO> getBdcFph(List<BdcSlSfxxDO> bdcSlSfxxDOList, String slbh, String fplb, List<BdcFphDO> bdcFphDOList);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    List<BdcFphDO> queryBdcFphDOList(List<BdcSlSfxxDO> bdcSlSfxxDOS);

    /**
     * 获取不动产发票号
     * @param count 发票号数量
     * @return 发票号信息
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcFphDO> getBdcFphDOList(int count);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改发票号使用状态 并且 修改 fphsymxid 和slbh
     */
    int updateBdcFphSyzt(BdcFphDO bdcFphDO);
	
	/**
	 * @param
	 * @return
	 * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
	 * @description 获取当前发票号使用状态
	 */
	 Integer getBdcFphSyzt(String fph);

    /**
     * @param bdcFphDOList
     * @return
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 根据发票号查询当前发票使用情况
     */
    Integer syqkEdit(List<BdcFphDO> bdcFphDOList);

    /**
     * @param
     * @return
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 判断是有可以获取该发票号
     */
    Boolean checkYyFph(BdcFphQO bdcFphQO);
    
    /**
     * @param fph
     * @return
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 通过发票号获取发票信息
     */
    BdcFphDO getBdcFphDO(String fph);

    /**
     * @param fphid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取发票号使用明细
     * @date : 2020/11/26 18:32
     */
    List<BdcFphSymxDTO> listFphSymx(String fphid);
}
