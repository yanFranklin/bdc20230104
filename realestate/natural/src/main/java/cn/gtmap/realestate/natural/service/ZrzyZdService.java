package cn.gtmap.realestate.natural.service;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/23
 * @description 字典服务
 */
public interface ZrzyZdService {

    /**
      * @return 字典项集合
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 获取所有字典项
      */
    Map<String, List<Map>> listZrzyzd();
}
