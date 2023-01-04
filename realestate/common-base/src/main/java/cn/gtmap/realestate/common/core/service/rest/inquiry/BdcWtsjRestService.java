package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.BdcWtsjDO;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/4/11
 * @description 问题数据
 */
public interface BdcWtsjRestService {

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 查询问题数据
      */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/wtsj")
    List<BdcWtsjDO> queryBdcWtsj(@RequestBody BdcWtsjDO bdcWtsjDO);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 添加问题数据
      */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/wtsj/add")
    int addWtBdcdy(@RequestBody List<BdcWtsjDO> bdcWtsjDOList);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 问题数据编辑解决
      */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/wtsj/jj")
    int jjBdcWtsj(@RequestBody List<BdcWtsjDO> bdcWtsjDOList,
                  @RequestParam("jjfa") String jjfa);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 更新问题数据
      */
    @PatchMapping(value = "/realestate-inquiry/rest/v1.0/wtsj")
    int updateWtsj(@RequestBody BdcWtsjDO bdcWtsjDO);

}
