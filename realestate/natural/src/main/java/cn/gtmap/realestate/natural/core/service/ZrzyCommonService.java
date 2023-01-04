package cn.gtmap.realestate.natural.core.service;

import cn.gtmap.realestate.common.core.dto.natural.*;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyDbxxQO;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description 自然资源公共服务
 */
public interface ZrzyCommonService {

    /**
     * @param zrzySlymYwxxDTO 自然资源业务填报信息DTO
     * @param xmid            项目ID
     * @return 自然资源业务填报信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID获取自然资源业务填报信息
     */
    ZrzySlymYwxxDTO queryZrzySlymYwxxDTO(String xmid, ZrzySlymYwxxDTO zrzySlymYwxxDTO);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据获取到的数据生成业务数据
     */
    ZrzyInitServiceDTO initYwxx(ZrzyInitQO zrzyInitQO, ZrzyInitServiceDTO zrzyInitServiceDTO);

    /**
     * @param zrzyZrzkYwxxDTO 自然资源业务填报信息DTO
     * @param xmid            项目ID
     * @return 自然资源业务填报信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID获取自然状况信息
     */
    default ZrzyZrzkYwxxDTO queryZrzyZrzkYwxxDTO(String xmid, ZrzyZrzkYwxxDTO zrzyZrzkYwxxDTO) {
        return zrzyZrzkYwxxDTO;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存业务数据
     */
    void saveYwxx(ZrzyInitResultDTO zrzyInitResultDTO);

    /**
     * 删除业务数据
     *
     * @param xmid
     */
    void deleteYwxx(String xmid);

}
