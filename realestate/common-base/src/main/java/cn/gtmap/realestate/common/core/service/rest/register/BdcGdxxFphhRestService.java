package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.certificate.BdcGdxxFphhDO;
import cn.gtmap.realestate.common.core.qo.register.BdcGdxxDahQO;
import cn.gtmap.realestate.common.core.qo.register.BdcGdxxFphhQO;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcDjlxDjxlQllxVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
 * @version 1.0, 2021/10/30
 * @description 档案分配盒号
 */
public interface BdcGdxxFphhRestService {

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @return
     * @description 分配盒号，查询结果中全部未分配的，进行分配操作
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/gdxxfphh/fphh", method = RequestMethod.POST)
    String fphh(@RequestBody BdcGdxxFphhQO bdcGdxxFphhQO);

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @return
     * @description 查询是否分配盒号，数据返回不为空，则给提示语
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/gdxxfphh/sffphh", method = RequestMethod.POST)
    List<BdcGdxxFphhDO> sffphh(@RequestBody BdcGdxxFphhQO bdcGdxxFphhQO);


    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @return
     * @description 页面下拉框需要展示盒号
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/listhh", method = RequestMethod.GET)
    List<Integer> listhh(@RequestParam(required = false,name = "xzdm") String xzdm,
                         @RequestParam(required = false,name = "xzmc") String xzmc,
                         @RequestParam(required = false,name = "nf") String nf);

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @param bdcGdxxFphhDO
     * @return
     * @description 新增归档分配盒子信息
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/gdxxfphh/xzfphh")
    int insertBdcGdxxFphh(@RequestBody BdcGdxxFphhDO bdcGdxxFphhDO);

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @param bdcGdxxFphhDO
     * @return
     * @description 根据xmid更新归档信息,存在则更新不存在则插入
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/gdxxfphh/upfphh")
    int updateBdcGdxxFphh(@RequestBody BdcGdxxFphhDO bdcGdxxFphhDO);

    /**
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @param bdcGdxxFphhDOList
     * @return
     * @description 页面批量更新盒号
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/gdxxfphh/batchupfphh")
    void batchUpdateGdxxFphh(List<BdcGdxxFphhDO> bdcGdxxFphhDOList);

    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @param bdcGdxxFphhQO
     * @return List<BdcGdxxFphhDO>
     * @description 查询归档信息集合
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/gdxxfphh/list",method = RequestMethod.GET)
    List<BdcGdxxFphhDO> listBdcGdxxFphh(@RequestBody BdcGdxxFphhQO bdcGdxxFphhQO);

    /**
     *
     * @param pageable
     * @return  Page<BdcDjlxDjxlQllxVO>
     * @author <a href ="mailto:wangyongming@gtmap.cn">wangyongming</a>
     * @description 分页获取 全参数
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/gdxxfphh/pagelistall")
    Object listBdcGdxxFphhPageAll(Pageable pageable, @RequestParam(name = "paramJSON", required = false) String paramJSON);

    /**
     *
     * @param gdxxid
     * @return  Page<BdcDjlxDjxlQllxVO>
     * @author <a href ="mailto:wangyongming@gtmap.cn">wangyongming</a>
     * @description 根据项目ID搜索基础信息
     */
    @GetMapping("/realestate-register/rest/v1.0/gdxxfphh/xghh")
    BdcGdxxFphhDO getDaIdById(@RequestParam(name="gdxxid")String gdxxid);

    /**
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGdxxFphhDO 档案信息
     * @return String 档案信息记录主键ID
     * @description 保存档案号
     */
    @PostMapping("/realestate-register/rest/v1.0/gdxxfphh/dah")
    String saveDah(@RequestBody BdcGdxxFphhDO bdcGdxxFphhDO);

    /**
     *
     * @param bdcGdxxFphhQO
     * @return  List<BdcGdxxFphhDO>
     * @author <a href ="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description
     */
    @PostMapping("/realestate-register/rest/v1.0/getBdcGdxxFphhDOList")
    List<BdcGdxxFphhDO> getBdcGdxxFphhDOList(@RequestBody BdcGdxxFphhQO bdcGdxxFphhQO);

    /**
     *
     * @param bdcGdxxFphhQO
     * @return
     */
    @PostMapping("/realestate-register/rest/v1.0/gdxxfphh/getMaxLsh")
    String getMaxLsh(@RequestBody BdcGdxxFphhQO bdcGdxxFphhQO);

}
