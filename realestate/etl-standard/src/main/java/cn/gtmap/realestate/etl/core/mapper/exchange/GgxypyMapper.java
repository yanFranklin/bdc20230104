package cn.gtmap.realestate.etl.core.mapper.exchange;

import cn.gtmap.realestate.common.core.domain.BdcJsydsyqDO;
import cn.gtmap.realestate.etl.core.dto.ggxypt.GrdacxRequestBody;
import cn.gtmap.realestate.etl.core.dto.ggxypt.qy.QyCqDTO;
import cn.gtmap.realestate.etl.core.dto.ggxypt.qy.QyDyaDTO;
import cn.gtmap.realestate.etl.core.dto.ggxypt.zrr.ZrrCqDTO;
import cn.gtmap.realestate.etl.core.dto.ggxypt.zrr.ZrrDyaDTO;
import cn.gtmap.realestate.etl.core.qo.ggxypt.qy.GgxyptQyBdccqQO;
import cn.gtmap.realestate.etl.core.qo.ggxypt.qy.GgxyptQyDyQO;
import cn.gtmap.realestate.etl.core.qo.ggxypt.zrr.ZrrBdcCqQO;
import cn.gtmap.realestate.etl.core.qo.ggxypt.zrr.ZrrBdcDyaQO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GgxypyMapper {


    /**
     * @param qyBdccqQO
     * @author <a href="mailto:gaolining@gtmap.cn">huangjian</a>
     * @description 查询企业产权信息
     * @date : 2022/7/28 11:30
     */
    List<QyCqDTO> queryQycqList(GgxyptQyBdccqQO qyBdccqQO);

    /**
     * @param qyDyQO
     * @author <a href="mailto:gaolining@gtmap.cn">huangjian</a>
     * @description 查询企业抵押权信息
     * @date : 2022/7/28 11:30
     */
    List<QyDyaDTO> queryQyDyaList(GgxyptQyDyQO qyDyQO);

    /**
     * @param qyBdccqQO
     * @author <a href="mailto:gaolining@gtmap.cn">huangjian</a>
     * @description 查询企业产权信息
     * @date : 2022/7/28 11:30
     */
    List<ZrrCqDTO> queryZrrCqList(ZrrBdcCqQO zrrBdcCqQO);

    /**
     * @param qyDyQO
     * @author <a href="mailto:gaolining@gtmap.cn">huangjian</a>
     * @description 查询个人抵押权信息
     * @date : 2022/7/28 11:30
     */
    List<ZrrDyaDTO> queryZrrDyaList(ZrrBdcDyaQO zrrBdcDyaQO);

    /**
     * 查询建设用地使用权实体
     *
     * @param grdacxRequestBody
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcJsydsyqDO>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 查询建设用地使用权实体
     */
    List<BdcJsydsyqDO> queryBdcJsydsyq(GrdacxRequestBody grdacxRequestBody);

}
