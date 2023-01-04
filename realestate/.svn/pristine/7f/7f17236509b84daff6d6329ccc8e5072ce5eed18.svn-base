package cn.gtmap.realestate.common.core.service.rest.init;

import cn.gtmap.realestate.common.core.domain.BdcJtcyDO;
import cn.gtmap.realestate.common.core.dto.init.BdcJtcySaveDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcJtcyQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/8/4
 * @description 不动产家庭成员接口
 */
public interface BdcJtcyRestService {

    /**
     * @param bdcJtcyQO 不动产家庭成员封装查询对象
     * @return 不动产家庭成员信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据查询参数返回不动产家庭成员信息
     */
    @PostMapping(path = "/init/rest/v1.0/jtcy/list")
    List<BdcJtcyDO> listBdcJtcy(@RequestBody BdcJtcyQO bdcJtcyQO);

    /**
     * @param bdcQlrQO 权利人查询参数
     * @return 不动产家庭成员信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据权利人参数获取家庭成员列表
     */
    @PostMapping(path = "/init/rest/v1.0/jtcy/listbyqlr")
    List<BdcJtcyDO> listBdcJtcyByQlr(@RequestBody BdcQlrQO bdcQlrQO);

    /**
     * @param jtcyid 家庭成员ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据家庭成员ID和流程ID删除或生成新版本家庭成员信息
     */
    @DeleteMapping(path = "/init/rest/v1.0/jtcy/{jtcyid}/{gzlslid}")
    void deleteBdcJtcyByGzlslid(@PathVariable("jtcyid") String jtcyid,@PathVariable("gzlslid") String gzlslid);

    /**
     * @param bdcJtcySaveDTO 家庭成员批量保存对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  批量保存家庭成员集合
     */
    @PostMapping(path = "/init/rest/v1.0/jtcy/saveBatchJtcyxx")
    void saveBatchJtcyxx(@RequestBody BdcJtcySaveDTO bdcJtcySaveDTO);

    /**
     * @param qlrid 权利人ID
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量删除当前流程家庭成员
     * 需要根据qlrid找到当前流程同名的所有同名qlrid,删除权利人户口簿关系以及判断是否需要删除家庭成员
     */
    @DeleteMapping(path = "/init/rest/v1.0/jtcy/jtcys/{qlrid}/{gzlslid}")
    void deleteBatchJtcyByGzlslid(@PathVariable("qlrid") String qlrid,@PathVariable("gzlslid") String gzlslid);

    /**
     * 根据户主的证件号信息查询家庭成员信息
     * @param zjh  户主证件号信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 家庭成员集合
     */
    @GetMapping(path = "/init/rest/v1.0/jtcy/jtcys/jtcyxx/{zjh}")
    List<BdcJtcyDO> queryJtcyxxByHzzjh(@PathVariable("zjh") String zjh);


}
