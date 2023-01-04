package cn.gtmap.realestate.accept.core.mapper;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshFwkgDataDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlDeleteCsDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcXmGdxxDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/4/22
 * @description 受理项目
 */
@Repository
public interface BdcSlXmMapper {

    /**
     * @param zsxhMap 参数
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新受理项目的证书序号
     */
    int updateSlxmZsxhPl(Map<String, Object> zsxhMap);

    /**
     * @param  map 参数
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description   批量更新受理项目收费信息
     */
    int updateSlxmSfxxPl(Map<String, Object> map);

    /**
     * @param  map 参数
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description   批量更新受理项目收费信息
     */
    List<BdcSlXmDO> querySlxmByLsgx(Map map);

    /**
     * @param bdcSlDeleteCsDTO 受理删除参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据参数批量删除受理项目信息
     */
    void batchDeleteBdcSlXm(BdcSlDeleteCsDTO bdcSlDeleteCsDTO);

    /**
     * @param jbxxid 基本信息ID
     * @return 发证状态信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 通过基本信息ID查询受理发证状态信息
     */
    List<BdcSlCshFwkgDataDTO> querySlFzztByJbxxid(String jbxxid);

    /**
     * @param map 更新内容以及更新条件
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新受理项目信息
     */
    int batchUpdateBdcSlXm(Map map);

    /**
     * @param xmidList 项目ID集合
     * @return 受理项目集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID集合查询受理项目集合
     */
    List<BdcSlXmDO> listBdcSlXmByXmids(@Param("xmidList") List<String> xmidList);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询受理项目的规划用途数据
     * @date : 2022/9/13 11:20
     */
    List<String> listGwcGhyt(@Param("jbxxid") String jbxxid);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 计算受理项目数据的定着物面积和
     * @date : 2022/9/13 11:22
     */
    String sumGwcDzwmj(@Param("jbxxid") String jbxxid, @Param("djxl") String djxl);

    /**
     * @param bdcSlXmQO 查询参数
     * @return 不动产受理项目
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 根据受理编号获取所有不动产受理项目
     */
    List<BdcSlXmDO> queryBdcSlXmDOList(BdcSlXmQO bdcSlXmQO);

    List<BdcXmGdxxDTO> listBdcGdxxHf(HashMap map);

}
