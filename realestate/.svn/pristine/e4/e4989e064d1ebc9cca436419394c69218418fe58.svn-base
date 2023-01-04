package cn.gtmap.realestate.natural.service;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyGgmbDo;
import cn.gtmap.realestate.common.core.dto.natural.ZrzySlymYwxxDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyZrzkYwxxDTO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description 自然资源业务信息服务
 */
public interface ZrzyYwxxService {

    /**
     * @param gzlslid 工作流实例ID
     * @return 自然资源业务填报信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取自然资源业务填报信息
     */
    ZrzySlymYwxxDTO queryZrzySlymYwxxDTO(String gzlslid);

//    /**
//      * @param xmid 项目ID
//      * @return 自然资源业务信息
//      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
//      * @description 根据项目ID查询自然资源业务信息
//      */
//    ZrzyYwxxDTO queryZrzywxxDTO(String xmid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 自然资源自然状况信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取自然状况信息
     */
    ZrzyZrzkYwxxDTO queryZrzyZrzkYwxxDTO(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 自然资源自然状况信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取模板信息
     */
    List<ZrzyGgmbDo> queryZrzyGgmbxx(String gzlslid);
}
