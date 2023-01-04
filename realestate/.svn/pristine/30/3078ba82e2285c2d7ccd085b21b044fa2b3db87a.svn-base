package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.domain.BdcFgfDO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/12/17
 * @description 登记房改房服务
 */
public interface BdcFgfRestService {

    /**
     * @param bdcFgfDOList 批量的房改房信息
     * @return int 执行的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存房改房项目信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/fgf", method = RequestMethod.POST)
    int saveFgfxm(@RequestBody List<BdcFgfDO> bdcFgfDOList);

    /**
     * @param bdcFgfDOList 批量的房改房信息
     * @return int 执行的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新房改房项目信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/fgf", method = RequestMethod.PUT)
    int updateFgfxm(@RequestBody List<BdcFgfDO> bdcFgfDOList);

    /**
     * @param gzlslid 工作流实例ID
     * @return List<BdcFgfDO>  查询到的房改房信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程的房改房信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/fgf/{gzlslid}", method = RequestMethod.GET)
    List<BdcFgfDO> getLcFgfxm(@PathVariable(name = "gzlslid") String gzlslid);
}

