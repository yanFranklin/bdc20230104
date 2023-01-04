package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.init.BdcCqBgLsDTO;
import cn.gtmap.realestate.common.core.dto.init.LsgxModelDTO;
import cn.gtmap.realestate.common.core.dto.init.LsgxXzqlModelDTO;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 *历史关系服务
 *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 *@version 1.0
 *@description
 */
public interface LsgxModelService {

    /**
     * 通过Bdcdyh获得同不动产单元id的所有不动产产权
     * @param bdcdyh 不动产单元号
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    List<LsgxModelDTO> getBdcCqLsgxListByBdcdyh(@NotBlank(message = "参数不能为空") String bdcdyh) throws ReflectiveOperationException;


    /**
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcXm
     *@param bdcQl
     *@param bdcXmLsgxDO
     *@return LsgxModelDTO
     *@description 根据项目信息 初始化LsgxModel
     */
    LsgxModelDTO initLsgxModel(BdcXmDO bdcXm, BdcQl bdcQl, BdcXmLsgxDO bdcXmLsgxDO);

    /**
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param xmid
     *@param all 是否所有层
     *@return LsgxXzqlModelDTO
     *@description 根据项目id获取下层的限制权利
     */
    LsgxXzqlModelDTO initLsgxXzqlModel(@NotBlank(message = "参数不能为空")String xmid, boolean all,Integer wlxm);

    /**
     * @param bdcdyh 不动产单元号
     * @return 产权变更历史
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据不动产单元号获取产权变更历史
     */
    List<BdcCqBgLsDTO> listBdcCqBgLs(String bdcdyh);

    /**
     * @param  xmid 现势产权项目ID
     * @param  all 是否所有层
     * @param wlxm 是否外联项目
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目id通过历史关系查询上下层产权权利
     */
    List<LsgxModelDTO> initLsgxCqModel(@NotBlank(message = "参数不能为空")String xmid, boolean all,Integer wlxm);

    /**
     * @param xmid 项目ID
     * @param type all:向上向下(包含本手) next:向下
     * @return LsgxXzqlModelDTO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目id通过历史关系查询上下层的限制权利
     */
    LsgxXzqlModelDTO initPrevNextLsgxXzqlModel(@NotBlank(message = "参数不能为空")String xmid, String type,Integer wlxm);
}
