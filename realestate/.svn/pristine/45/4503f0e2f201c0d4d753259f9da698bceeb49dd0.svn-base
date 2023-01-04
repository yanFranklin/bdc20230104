package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.domain.BdcBlShDO;
import cn.gtmap.realestate.common.core.dto.register.BlShPageResponseDTO;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 不动产信息补录相关服务定义
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/6/11 18:22
 */
public interface BdcXxblShRestService {
    /**
     * 补录审核页面后台服务
     *
     * @param pageable 分页对象
     * @param bdcBlShQoStr bdcBlShQo 的 json 串
     * @param version 系统版本
     * @return Page<BlShPageResponseDTO> 分页查询对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/blxx/shxx")
    Page<BlShPageResponseDTO> listBlShByPageJson(Pageable pageable, @RequestParam(name = "bdcBlShQoStr", required = false) String bdcBlShQoStr, @RequestParam(name="version") String version);

    /**
     * 生成补录审核信息
     *
     * @param bdcBlShDO 不动产补录审核对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/blxx/blsh")
    void generateBlShxx(@RequestBody BdcBlShDO bdcBlShDO);

    /**
     * 转发补录审核信息
     *
     * @param bdcBlShDO 不动产补录审核对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/blxx/blsh/forward")
    void forwardBlShxx(@RequestBody BdcBlShDO bdcBlShDO);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcBlShDOList]
     * @return void
     * @description 批量转发补录审核信息
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/blxx/blsh/forward/pl")
    void plForwardBlShxx(@RequestBody List<BdcBlShDO> bdcBlShDOList);

    /**
     * 办结修改数据的审核信息
     *
     * @param gzlslid
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/blsh/dbyz")
    List<BdcGzyzVO> dbYz(@RequestParam("gzlslid") String gzlslid) throws Exception;

    /**
     * 办结补录审核信息
     *
     * @param blshid 补录审核 ID
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/blsh/end")
    void endBlShxx(@RequestParam("blshid") String blshid);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcBlShDOList]
     * @return void
     * @description 批量办结补录审核信息
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/blxx/blsh/end/pl")
    void plEndBlShxx(@RequestBody List<BdcBlShDO> bdcBlShDOList);

    /**
     * 退回补录审核信息
     *
     * @param blshid 补录审核ID
     * @return {boolean} false:无法转发
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/blxx/blsh/back")
    boolean backBlShxx(@RequestParam("blshid") String blshid);

    /**
     * 判断是否正在审核
     *
     * @param bdcBlShDO 不动产补录审核对象
     * @return {boolean} 0: 正在审核， 1：未在审核， 2 表示正在审核但是本人打开
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/blxx/blsh/issh")
    Integer checkIsSh(@RequestBody BdcBlShDO bdcBlShDO);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcBlShDO]
     * @return cn.gtmap.realestate.common.core.domain.BdcBlShDO
     * @description 根据项目id和受理人id查询可转发的补录记录
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/blxx/blsh/queryBdcBlshDO")
    BdcBlShDO queryBdcBlshDO(@RequestBody BdcBlShDO bdcBlShDO);

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @params [bdcBlShDO]
     * @return cn.gtmap.realestate.common.core.domain.BdcBlShDO
     * @description 更新信息补录审核信息
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/blxx/blsh/updateBlshxx")
    Integer updateBlshxx(@RequestBody BdcBlShDO bdcBlShDO);

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @params xmid
     * @description 根据xmid查询信息补录审核信息
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/blxx/blsh/queryBlshxxByXmid")
    List<BdcBlShDO> queryBlshxxByXmid(@RequestParam("xmid") String xmid);


}
