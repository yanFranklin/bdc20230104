package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.BdcLzrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlLzrDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/1/15
 * @description 不动产受理领证人数据服务
 */
public interface BdcSlLzrService {

    /**
     * 根据领证人ID获取不动产受理领证人
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: lzrid 领证人ID
     * @return: BdcSlLzrDO 不动产受理领证人DO
     */
    BdcSlLzrDO queryBdcSlLzrByLzrid(String lzrid);

    /**
     * 根据项目ID获取不动产受理领证人
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: xmid 项目ID
     * @return: List<BdcSlLzrDO> 不动产受理领证人DO集合
     */
    List<BdcSlLzrDO> listBdcSlLzrByXmid(String xmid);

    /**
     * 新增不动产受理领证人
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: bdcSlLzrDO 不动产受理领证人DO
     * @return: BdcSlLzrDO 不动产受理领证人DO
     */
    BdcSlLzrDO insertBdcSlLzr(BdcSlLzrDO bdcSlLzrDO);

    /**
     * 更新不动产受理领证人
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: bdcSlLzrDO 不动产受理领证人DO
     * @return: Integer 更新状态
     */
    Integer updateBdcSlLzr(BdcSlLzrDO bdcSlLzrDO);

    /**
     * 根据领证人ID删除不动产受理领证人
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: lzrid 领证人ID
     * @return: Integer 删除状态
     */
    Integer deleteBdcSlLzrByLzrid(String lzrid);

    /**
     * 根据项目ID删除不动产受理领证人
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: xmid 项目ID
     * @return: Integer 删除状态
     */
    Integer deleteBdcSlLzrByXmid(String xmid);

    /**
     * 根据项目ID删除不动产受理领证人
     *
     * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @param: xmid 项目ID
     * @return: Integer 删除状态
     */
    Integer updatePlBdcLzr(String json, String processInsId, String djxl) throws Exception;

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  生成领证人信息
     */
    List<BdcLzrDO> initLzrxx(BdcSlxxDTO bdcSlxxDTO, String bdcgzlslid);



}
