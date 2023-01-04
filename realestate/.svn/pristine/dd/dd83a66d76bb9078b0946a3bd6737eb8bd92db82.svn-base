package cn.gtmap.realestate.natural.service;

import cn.gtmap.realestate.common.core.dto.natural.ZrzyCshDTO;

import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/27
 * @description 自然资源初始化服务
 */
public interface ZrzyInitService {

    /**
     * @param zrzyCshDTO 初始化选择对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 自然资源业务信息初始化
     */
    String ywxxCsh(ZrzyCshDTO zrzyCshDTO);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 回写信息到平台
     */
    void saveZrzyYwsj(String gzlslid, Map<String, Object> params, Boolean update);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 回写信息到平台
     */
    void cshZrzyYwsj(String gzlslid);


}
