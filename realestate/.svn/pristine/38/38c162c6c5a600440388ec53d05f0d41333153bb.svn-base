package cn.gtmap.realestate.common.core.service.rest.natural;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyXmDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyZsDO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyZsQO;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyYzhVO;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyZsVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/11/14
 * @description 不动产证书业务服务接口定义
 */
public interface ZrzyZsRestService {
    /**
     * @param zrzyZsQO 证书查询对象
     * @return List<BdcZsDO> 不动产权证list
     * @author <a href="mailto:bianwen@gtmap.cn">wangyinghao</a>
     * @description 获取不动产权证书列表
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/zs/list", method = RequestMethod.POST)
    List<ZrzyZsDO> listZrzyZs(@RequestBody ZrzyZsQO zrzyZsQO);

    /**
     * 初始化不动产权证
     *
     * @param processInsId
     * @param zsyl         是否预览
     * @return 集合
     * @throws Exception
     */
    @PostMapping(path = "/realestate-natural/rest/v1.0/zs/{processInsId}/sc")
    ZrzyZsDO initZrzyqzs(@PathVariable("processInsId") String processInsId,
                         @RequestParam(value = "zsyl", required = false) boolean zsyl) throws Exception;

    /**
     * 生成产权证号
     *
     * @param processInsId
     * @throws Exception
     */
    @PostMapping(path = "/realestate-natural/rest/v1.0/zs/{processInsId}/cqzh")
    void generateCqzh(@PathVariable("processInsId") String processInsId) throws Exception;


    /**
     * @param bdcYzhVO 证书印制号模板
     * @return {int} 操作数据记录数
     * @author <a href="mailto:zhuyong@gtmap.cn">wangyinghao</a>
     * @description 生成证书印制号
     */
    @PostMapping("/realestate-config/rest/v1.0/zsyzh")
    int generateBdcZsyzh(@RequestBody ZrzyYzhVO zrzyYzhVO);

    /**
     * @param zsid 证书ID
     * @return {BdcZsDo} 不动产证书
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取不动产权证书
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/zs/{zsid}", method = RequestMethod.GET)
    ZrzyZsVO queryZrzyZs(@PathVariable(value = "zsid") String zsid);

    /**
     * @param zsid 证书ID
     * @return List<BdcXmDO> 项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前证书相关的项目信息
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/zsXm/{zsid}", method = RequestMethod.GET)
    List<ZrzyXmDO> queryZsXmByZsid(@PathVariable(value = "zsid") String zsid);


    /**
     * 更新证书
     *
     * @param zrzyZsDO
     * @return
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/zs", method = RequestMethod.POST)
    int updateZrzyZs(@RequestBody ZrzyZsDO zrzyZsDO);


    /**
     * @param zrzyZsQO 证书查询对象
     * @param page     分页查询页数
     * @param size     分页查询行数
     * @param sort     分页查询排序
     * @return Page<BdcZsDO>
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产权证书分页
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/zs/page", method = RequestMethod.POST)
    Page<ZrzyZsVO> zrzyZsByPageJson(@RequestParam(name = "page") int page,
                                    @RequestParam(name = "size") int size,
                                    @RequestParam(name = "sort", required = false) Sort sort,
                                    @RequestBody ZrzyZsQO zrzyZsQO);


    /**
     * @param gzlslid 工作流实例
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @return　 List<String>　zsid列表
     * @description 查询当前流程所有的zsid
     */
    @RequestMapping(value = "/realestate-natural/rest/v1.0/zsid/{gzlslid}/list", method = RequestMethod.GET)
    List<String> queryGzlZsid(@PathVariable(name = "gzlslid") String gzlslid);
}
