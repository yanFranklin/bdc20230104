package cn.gtmap.realestate.common.core.service.rest.init;

import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.init.BdcCqBgLsDTO;
import cn.gtmap.realestate.common.core.dto.init.LsgxModelDTO;
import cn.gtmap.realestate.common.core.dto.init.LsgxXzqlModelDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/2/28.
 * @description 历史关系接口
 */
public interface BdcLsgxRestService {

    /**
     *根据bdcdyh查询主线所有产权(只查询产权信息)
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcdyh
     *@return 树结构
     *@description
     */
    @GetMapping(value = "/init/rest/v1.0/lsgx/getCqList/{bdcdyh}")
    List<LsgxModelDTO> getCqList(@PathVariable(name = "bdcdyh") String bdcdyh) throws ReflectiveOperationException;


    /**
     * @param xmid   项目ID
     * @return 结构数据
     * @author lst
     * @description 根据项目ID查询一层限制权力信息
     */
    @GetMapping(value = "/init/rest/v1.0/lsgx/getXzqlList/{xmid}")
    LsgxXzqlModelDTO getXzqlList(@PathVariable(name = "xmid") String xmid,@RequestParam(name = "wlxm",required = false) Integer wlxm);


    /**
     * @param xmid   项目ID
     * @return 结构数据
     * @author lst
     * @description 根据xmid查询所有的限制权力信息
     */
    @GetMapping(value = "/init/rest/v1.0/lsgx/getAllXzqlList/{xmid}")
    LsgxXzqlModelDTO getAllXzqlList(@PathVariable(name = "xmid") String xmid,@RequestParam(name = "wlxm",required = false) Integer wlxm);

    /**
     * @param xmid   项目ID
     * @param position   位置   top  bottom
     * @return
     * @author lst
     * @description 根据xmid和扩展位置查询主线扩展产权(只查询产权信息)
     */
    @GetMapping(value = "/init/rest/v1.0/lsgx/getChangeBdcdyhList/{xmid}/{position}")
    List<LsgxModelDTO> getChangeBdcdyhList(@PathVariable(name = "position") String position, @PathVariable(name = "xmid") String xmid) throws ReflectiveOperationException;


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcXmLsgxQO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO>
     * @description  查询项目的一级历史关系数据
     */
    @PostMapping(value = "/init/rest/v1.0/lsgx/list")
    List<BdcXmLsgxDO> listXmLsgxByXmid(@RequestBody BdcXmLsgxQO bdcXmLsgxQO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcXmLsgxQO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO>
     * @description  批量查询项目的一级历史关系数据
     */
    @PostMapping(value = "/init/rest/v1.0/lsgx/batchlist")
    List<BdcXmLsgxDO> listXmLsgxByXmids(@RequestBody BdcXmLsgxQO bdcXmLsgxQO);

    /**
     * @param slbh
     * @param gzlslid
     * @param zxyql
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 查询项目的一级历史关系数据
     */
    @PostMapping(value = "/init/rest/v1.0/lsgx/listbySlid")
    List<BdcXmLsgxDO> listXmLsgxBySlid(@RequestParam(name = "gzlslid") String gzlslid);

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param gxids
     * @return 数量
     * @description  删除历史关系
     */
    @DeleteMapping(value = "/init/rest/v1.0/lsgx/delete")
    int deleteLsgxs(String[] gxids);



    /**
     * 嵌套获取下手项目关系信息(获取限制权利)
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param xmid
     *@param list
     *@return
     *@description
     */
    @PostMapping(value = "/init/rest/v1.0/lsgx/nextBdcXmLsgx")
    List<BdcXmLsgxDO> nextBdcXmLsgx(@RequestParam(name = "xmid") String xmid, @RequestBody List<BdcXmLsgxDO> list,@RequestParam(name = "wlxm",required = false) Integer wlxm);

    /**
     * @param bdcdyh 不动产单元号
     * @return 产权变更历史
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据不动产单元号获取产权变更历史
     */
    @GetMapping(path = "/init/rest/v1.0/cqbgls/{bdcdyh}")
    List<BdcCqBgLsDTO> listBdcCqBgLs(@PathVariable("bdcdyh") String bdcdyh);

}
