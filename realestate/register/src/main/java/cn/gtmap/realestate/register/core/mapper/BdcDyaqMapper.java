package cn.gtmap.realestate.register.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.dto.analysis.BdcDyaXxDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDjbDyxxDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDjbFwxxDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDyaqQlrDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcdySumDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDyaQo;
import cn.gtmap.realestate.register.core.dto.BdcDyaqDTO;
import cn.gtmap.realestate.register.core.dto.BdcGyqkDTO;
import cn.gtmap.realestate.register.core.qo.DbxxQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/4/28
 * @description 抵押权查询Mapper
 */
public interface BdcDyaqMapper {
    /**
     * @param bdcGyqkDTO 更新参数
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新权利共有情况
     */
    int updateQlGyqkPl(BdcGyqkDTO bdcGyqkDTO);

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 执行数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新当前权利的登簿信息和权属状态
     */
    int udpateBdcQlDbxxAndQsztPl(DbxxQO dbxxQO);

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新上原权利的权属状态和注销登簿信息
     */
    int udpateBdcQlZxDbxxAndQsztPl(DbxxQO dbxxQO);

    /**
     * @param map
     * @return 面积和
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 计算抵押土地面积之和（同一宗地只收一个费用）
     */
    BdcdySumDTO calculatedDyaqTdMj(Map map);

    /**
     * @param map
     * @return 面积和
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 计算抵押土地面积之和（不区分宗地求和）
     */
    BdcdySumDTO calculatedDyaqTdMjAll(Map map);

    /**
     * @param map
     * @return 面积和
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 计算房地产权面积之和
     */
    BdcdySumDTO calculatedDyaqFcMj(Map map);

    /**
     * @param map
     * @return 面积和
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 计算房地产权面积之和
     */
    List<BdcdySumDTO> calculatedDyaqMjGyBdclx(Map map);
    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @param bdcXmQO 项目查询QO
     * @return List<BdcDyaqDO> 原抵押信息
     * @description 查询当前流程的原抵押信息
     */
    List<BdcDyaqDO> queryYdyaxx(BdcXmQO bdcXmQO);

    /**
     * @param bdcDyaqDTO 抵押更新参数
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新原抵押信息的抵押注销申请人
     */
    int saveYdyaxxZxsqrPl(BdcDyaqDTO bdcDyaqDTO);

    /**
     * @param
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新抵押权的权属状态
     */
    int udpateBdcDyaqQsztPl(DbxxQO dbxxQO);

    /**
     * @param bdcXmQO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据单元号查询是否办理过抵押权
     * @date : 2020/8/3 14:06
     */
    List<BdcDyaqDO> listExistDyaq(BdcXmQO bdcXmQO);

    /**
     * @param bdcDyaQo
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据权利人信息查询抵押权数据
     * @date : 2021/9/14 9:07
     */
    List<BdcDyaqQlrDTO> listBdcDyaqByqlrxx(BdcDyaQo bdcDyaQo);

    /**
     * @param dbxxQO 登簿信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新权利登簿人
     */
    int updateBdcQlDbrPl(DbxxQO dbxxQO);

    /**
     * @param dbxxQO 登簿信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新注销权利登簿人
     */
    int updateZxQlDbrPl(DbxxQO dbxxQO);

}
