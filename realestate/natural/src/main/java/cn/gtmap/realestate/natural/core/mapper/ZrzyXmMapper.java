package cn.gtmap.realestate.natural.core.mapper;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyGgmbDo;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtLcpzDO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyXmDTO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyDbxxQO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyXmListQO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 */
@Component
public interface ZrzyXmMapper {

    /**
     * 查询项目列表
     *
     * @param zrzyXmListQO
     * @return
     */
    List<ZrzyXmDTO> listXmDtoByPage(ZrzyXmListQO zrzyXmListQO);

    /**
     * 查询流程配置
     * @param gzlslid
     * @return
     */
    List<ZrzyXtLcpzDO> listZrzlcshPz(@Param("gzlslid") String gzlslid);


    /**
     * @param zrzyDbxxQO 登簿信息QO
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据工作流实例ID更新当前所有项目的登簿信息和权属状态
     */
    int updateZrzyXmDbxxAndQsztByGzlslid(ZrzyDbxxQO zrzyDbxxQO);


    /**
     * @param zrzyDbxxQO 登簿信息QO
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新当前需要注销的原项目的权属状态
     */
    int updateYxmQsztByGzlslid(ZrzyDbxxQO zrzyDbxxQO);


    /**
     * 查询公告模板
     * @param gzlslid
     * @return
     */
    List<ZrzyGgmbDo> listGgmbByXmid(@Param("xmid") String xmid);

    int insertGgmb(@Param("mbxxid") String mbxxid,
                               @Param("xmid") String xmid,
                               @Param("mbnr") String mbnr);




    int updateGgmbByXmid(@Param("xmid") String xmid,
                               @Param("mbnr") String mbnr);

}