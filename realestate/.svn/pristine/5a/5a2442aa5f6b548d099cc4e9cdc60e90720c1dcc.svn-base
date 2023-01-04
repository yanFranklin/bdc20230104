package cn.gtmap.realestate.natural.core.service.zrzkxx;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyZrzk;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description 自然资源自然状况服务
 */
public interface ZrzyZrzkService {

    /**
      * @param xmid 项目ID
      * @return 自然状况信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据项目ID和自然状况类型获取对应的自然状况信息
      */
    <T extends ZrzyZrzk> List<T> listZrzyZrzkList(String xmid,Class<?> zrzkClass);

    /**
     * @param zkxxid 状况信息ID
     * @param zrzklx 自然状况类型
     * @return 自然状况信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据主键获取自然状况信息
     */
    ZrzyZrzk queryZrzk(String zkxxid,String zrzklx);
}
