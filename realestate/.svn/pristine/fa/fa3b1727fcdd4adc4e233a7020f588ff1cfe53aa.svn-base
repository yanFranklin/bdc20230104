package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.ZdJtcyDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/11
 * @description 宗地家庭成员服务
 */
public interface ZdJtcyRestService {

    /**
      * @param djh 地籍号
      * @return 宗地家庭成员信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据地籍号获取宗地家庭成员信息
      */
    @GetMapping("/building/rest/v1.0/zdjtcy/{djh}")
    List<ZdJtcyDO> listZdJtcyByDjh(@PathVariable(name = "djh") String djh);
}
