package cn.gtmap.realestate.common.core.service.rest.init;

import cn.gtmap.realestate.common.core.domain.BdcFctdPpgxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXnbdcdyhGxDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcLzQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/2/28.
 * @description 业务初始化系统对外匹配接口
 */
public interface BdcPpRestService {
    /**
     * 通过传入参数进行落宗处理
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcLzQO 单元号落宗QO
     *@description
     */
    @PostMapping(value = "/init/rest/v1.0/lz")
    void lz(@RequestBody BdcLzQO bdcLzQO) throws Exception;

    /**
     * 通过传入参数进行取消落宗处理
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcdyh 不动产单元号
     *@param qxlzxmid 取消落宗的项目ID
     *@description
     */
    @PostMapping(value = "/init/rest/v1.0/qxlz/{bdcdyh}/{qxlzxmid}")
    void qxlz(@PathVariable(name = "bdcdyh") String bdcdyh,@PathVariable(name = "qxlzxmid") String qxlzxmid,@RequestParam(name = "qjgldm", required = false) String qjgldm) throws Exception;

    /**
     * 房产证和土地证进行匹配记录关系
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param fcxmid 房产项目id
     *@param tdxmid 土地项目id
     *@description
     */
    @PostMapping(value = "/init/rest/v1.0/pptd")
    void pptd(@RequestParam(name = "fcxmid") String fcxmid,
              @RequestParam(name = "tdxmid") String tdxmid
              ) throws Exception;

    /**
     * 房产证和土地证进行匹配记录关系(存ip)
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param fcxmid 房产项目id
     *@param tdxmid 土地项目id
     *@description
     */
    @PostMapping(value = "/init/rest/v1.0/pptdwithip")
    void pptdwithip(@RequestParam(name = "fcxmid") String fcxmid,
              @RequestParam(name = "tdxmid") String tdxmid,
              @RequestParam(name = "requestClientRealIP",required = false) String requestClientRealIP
    ) throws Exception;


    /**
     * 取消房产证和土地证匹配关系
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param fcxmid 房产项目id
     *@return
     *@description
     */
    @DeleteMapping(value = "/init/rest/v1.0/qxpptd")
    void qxpptd(@RequestParam(name = "fcxmid") String fcxmid) throws Exception;

    /**
     * 根据房产项目ID去获取匹配关系
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param fcxmid
     *@return List<BdcFctdPpgxDO>（返回多个时存在数据问题，前端判断处理）
     *@description
     */
    @GetMapping(path = "/init/rest/v1.0/ppgx/{fcxmid}")
    List<BdcFctdPpgxDO> queryBdcFctdPpgx(@PathVariable(name = "fcxmid") String fcxmid);

    /**
     * 根据房产项目ID去获取匹配关系
     *@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     *@param tdxmid
     *@return List<BdcFctdPpgxDO>（返回多个时存在数据问题，前端判断处理）
     *@description
     */
    @GetMapping(path = "/init/rest/v1.0/ppgx/td/{tdxmid}")
    List<BdcFctdPpgxDO> queryBdcFctdPpgxByTdxmid(@PathVariable(name = "tdxmid") String tdxmid);


    /**
     * 根据虚拟单元号项目id去获取虚拟单元号关系
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param xndyhxmid
     *@return List<BdcXnbdcdyhGxDO>  多条时存在数据错误
     *@description
     */
    @GetMapping(path = "/init/rest/v1.0/lsh/xndyhgx/{xndyhxmid}")
    List<BdcXnbdcdyhGxDO> queryBdcXnbdcdyhGxByXmid(@PathVariable(name = "xndyhxmid") String xndyhxmid);

    /**
     * 根据条件查询
     *
     * @param bdcXnbdcdyhGxDO
     * @return List<BdcXnbdcdyhGxDO>  多条时存在数据错误
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description
     */
    @PostMapping(path = "/init/rest/v1.0/lsh/xndyhgx/search")
    List<BdcXnbdcdyhGxDO> queryBdcXnbdcdyhByCondition(@RequestBody BdcXnbdcdyhGxDO bdcXnbdcdyhGxDO);

    /**
     * @param bdcXmDOList,bdcdyh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新单元号信息, 更新权利表，项目表，证书表单元号
     * @date : 2021/12/30 17:11
     */
    @PostMapping(path = "/init/rest/v1.0/ppgx/remove")
    void updateBdcdyh(@RequestBody List<BdcXmDO> bdcXmDOList, @RequestParam(value = "bdcdyh") String bdcdyh, @RequestParam(value = "qjgldm",required = false) String qjgldm) throws Exception;



    /**
     * 通过传入参数进行落宗处理
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcLzQO 单元号落宗QO
     *@description
     */
    @PostMapping(value = "/init/rest/v1.0/ls/lz")
    void lslz(@RequestBody BdcLzQO bdcLzQO) throws Exception;

    /**
     * 通过传入参数进行取消落宗处理
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcdyh 不动产单元号
     *@param qxlzxmid 取消落宗的项目ID
     *@description
     */
    @PostMapping(value = "/init/rest/v1.0/ls/qxlz/{bdcdyh}/{qxlzxmid}")
    void qxlslz(@PathVariable(name = "bdcdyh") String bdcdyh,@PathVariable(name = "qxlzxmid") String qxlzxmid,@RequestParam(name = "qjgldm", required = false) String qjgldm) throws Exception;

    /**
     * @param bdcXnbdcdyhGxDOS 关系表数据
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 插入单元号匹配关系
     */
    @PostMapping(value = "/init/rest/v1.0/ppgx/xndyhgx")
    void insertXndyhGx(@RequestBody List<BdcXnbdcdyhGxDO> bdcXnbdcdyhGxDOS);
}
