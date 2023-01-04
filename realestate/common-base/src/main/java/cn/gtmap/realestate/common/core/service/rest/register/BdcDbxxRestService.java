package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.register.XxblDbDTO;
import cn.gtmap.realestate.common.core.qo.register.BdcHfQO;
import cn.gtmap.realestate.common.core.qo.register.BdcZxQO;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/7
 * @description 登簿信息接口
 */
public interface BdcDbxxRestService {
    /**
     * @param xxblDbDTO    信息补录登簿对象
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 登簿时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/dbxxAndQszt/xxbl", method = RequestMethod.POST)
    void updateBdcDbxxAndQszt(@RequestBody XxblDbDTO xxblDbDTO);


    /**
     * @param bdcZxQO 项目ID
     * @return {code int} 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新项目和权利的注销信息，同步权籍
     * qszt{@code 1}清空注销人和注销时间，对登簿人和登记时间不做修改；{@code 2}同时更新注销人和注销时间
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/xmid", method = RequestMethod.PUT)
    int updateXmAndQlZxxxAndSynQjBdcdyzt(@RequestBody BdcZxQO bdcZxQO);


    /**
     * @param bdcZxQO         不动产注销信息
     * @param currentUserName 当前账户
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 更新权利的注销信息，并同步权籍单元号的状态
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/zxql", method = RequestMethod.POST)
    int zxQl(@RequestBody BdcZxQO bdcZxQO, @RequestParam(value = "currentUserName") String currentUserName);

    /**
     * 恢复权利，并同步权籍单元号的状态
     *
     * @param bdcHfQO 不动产恢复信息
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/hfql", method = RequestMethod.POST)
    int hfQl(@RequestBody BdcHfQO bdcHfQO);

    /**
     * @param gzlslid 工作流实例ID
     * @param qszt    权属状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新原注销权利的登簿信息和权属状态
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/{gzlslid}/yzxql/{qszt}", method = RequestMethod.GET)
    void updateYzxqlDbxxAndQszt(@PathVariable(name = "gzlslid") String gzlslid, @PathVariable(name = "qszt") Integer qszt);

    /**
     * @param processInsId    工作流实例id
     * @param currentUserName 当前账户
     * @author <a href ="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description AOP规则验证后，验证通过则登簿时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/gzyzAOP", method = RequestMethod.POST)
    List<BdcGzyzVO> updateDbxxQsztGzyzAOP(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   gzlslid 工作流实例ID
     * @description 更新案件状态为2已完成状态，并更新项目结束时间
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/{gzlslid}/ajzt", method = RequestMethod.GET)
    void changeAjzt(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @param gzlslid 工作流实例ID
     * @description 同步权籍不动产单元状态(不包含锁定)
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/{gzlslid}/qjzt", method = RequestMethod.PUT)
    void synQjBdcdyzt(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @param gzlslid
     * @return
     * @description 同步权籍基本信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/{gzlslid}/qjjbxx", method = RequestMethod.PUT)
    void synQjJbxx(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * @param bdcXmDOList 需要同步的项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 不动产项目同步权籍基本信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/qjjbxx", method = RequestMethod.POST)
    void syncBdcdyxxByBdcXm(@RequestBody List<BdcXmDO> bdcXmDOList);

    /**
     * @param bdcdyhList 不动产单元号
     * @param sdzt   锁定状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将登记的锁定状态同步到权籍
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/bdcdyh/sd", method = RequestMethod.POST)
    void synQjBdcdyztSd(@RequestBody List<String> bdcdyhList, @RequestParam(name = "sdzt") Integer sdzt);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 撤销流程，修改权属状态和案件状态
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/{gzlslid}/cancelProcess", method = RequestMethod.PUT)
    void cancelProcessQsztAndAjzt(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcZxQl]
     * @return void
     * @description 直接传入需要注销的权利 更新附记注销信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/updateZxqlfj", method = RequestMethod.POST)
    void generateAndUpdateZxqlFj(@RequestBody BdcQl bdcZxQl);

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @params processInsId 工作流实例ID
     * @description 商品房首次登记更新外联证书的权利附记
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dbxx/updateSpfscdjWlzsQlfj", method = RequestMethod.POST)
    void updateSpfscdjWlzsQlfj(@RequestParam(value = "processInsId") String processInsId) throws Exception;
}
