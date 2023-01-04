package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlDeleteCsDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/24
 * @description 受理房屋信息
 */
public interface BdcSlFwxxService {

    /**
     * @param xmid 项目ID
     * @return 不动产受理房屋信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID获取不动产受理房屋信息
     */
    List<BdcSlFwxxDO> listBdcSlFwxxByXmid(String xmid);

    /**
     * @param xmidList 项目ID集合
     * @return 不动产受理项目
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @description 根据项目ID集合获取不动产受理房屋信息
     */
    List<BdcSlFwxxDO> listBdcSlFwxxByXmids(List<String> xmidList);

    /**
     * @param bdcDjxxUpdateQO 登记信息更新对象
     * @return 更新数量
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @description 批量更新受理房屋信息
     */
    Integer updateBatchBdcSlFwxx(BdcDjxxUpdateQO bdcDjxxUpdateQO);

    /**
     * @param fwxxid 房屋信息id
     * @return 不动产受理房屋信息息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据房屋信息id获取不动产受理房屋信息
     */
    BdcSlFwxxDO queryBdcSlFwxxByFwxxid(String fwxxid);

    /**
     * @param bdcSlFwxxDO 房屋信息
     * @return 不动产受理房屋信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产受理房屋信息
     */
    BdcSlFwxxDO insertBdcSlFwxx(BdcSlFwxxDO bdcSlFwxxDO);

    /**
     * @param fwxxid 房屋信息id
     * @return 删除数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理房屋信息
     */
    Integer deleteBdcSlFwxxByFwxxid(String fwxxid);

    /**
     * @param xmid 项目id
     * @return 删除数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理房屋信息
     */
    Integer deleteBdcSlFwxxByXmid(String xmid);

    /**
     * @param bdcSlFwxxDO 不动产受理房屋信息
     * @return 保存数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存受理房屋信息
     */
    Integer updateBdcSlFwxx(BdcSlFwxxDO bdcSlFwxxDO);

    /**
     * @param bdcSlFwxxDO 不动产受理房屋信息
     * @return 保存数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据项目ID更新不动产受理房屋信息
     */
    Integer updateBdcSlFwxxByXmid(BdcSlFwxxDO bdcSlFwxxDO);

    /**
     * @param bdcSlDeleteCsDTO 受理删除参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据参数批量删除受理房屋信息
     */
    void deleteBdcSlFwxx(BdcSlDeleteCsDTO bdcSlDeleteCsDTO);

    /**
     * 保存受理房屋信息
     * 先通过xmid查询受理房屋信息，有则进行更新，无则进行新增
     * @param bdcSlFwxxDO 不动产受理房屋信息DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void saveBdcSlFwxx(BdcSlFwxxDO bdcSlFwxxDO);

}
