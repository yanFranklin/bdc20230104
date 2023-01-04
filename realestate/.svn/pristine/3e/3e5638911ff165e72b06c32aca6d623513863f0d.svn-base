package cn.gtmap.realestate.common.core.service.rest.init;

import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlqtzkFjQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 不动产证书相关接口
 *
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/14.
 * @description
 */
public interface BdcZsInitRestService {

    /**
     * 初始化不动产权证
     * @param processInsId
     * @param zsyl 是否预览
     * @throws Exception
     * @return 集合
     */
    @PostMapping(path = "/init/rest/v1.0/{processInsId}/bdcqzs/sc")
    List<BdcZsDO> initBdcqzs(@PathVariable("processInsId") String processInsId, @RequestParam(value = "zsyl",required = false)boolean zsyl) throws Exception;
    /**
     * 初始化不动产权证（数据补录）  不生成权利其他状况和附记
     * @param processInsId
     * @param zsyl 是否预览
     * @throws Exception
     * @return 集合
     */
    @PostMapping(path = "/init/rest/v1.0/{processInsId}/bdcqzs/sc/sjbl")
    List<BdcZsDO> initBdcqzsSjbl(@PathVariable("processInsId") String processInsId, @RequestParam(value = "zsyl",required = false)boolean zsyl) throws Exception;

    /**
     * 初始化不动产权证
     * @param xmid
     * @param zsyl  是否预览
     * @throws Exception
     * @return 集合
     */
    @PostMapping(path = "/init/rest/v1.0/{xmid}/bdcqz/sc")
    List<BdcZsDO> initBdcqz(@PathVariable("xmid") String xmid,@RequestParam(value = "zsyl",required = false)boolean zsyl) throws Exception;

    /**
     * 通过传入项目id更新权利其他状况以及附记
     * @param xmid 项目id
     * @param mode 模式(1:全部更新 2:仅更新权利其他状况 3:仅更新证书附记)
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 通过传入项目id更新权利其他状况以及附记
     */
    @PostMapping(path = "/init/rest/v1.0/qlqtzkfj/{xmid}/{mode}")
    void updateQlqtzkFj(@PathVariable("xmid") String xmid, @PathVariable("mode") String mode);

    /**
     * 通过传入工作流实例ID更新权利其他状况以及附记
     * @param processInsId 工作流实例ID
     * @param mode 模式(1:全部更新 2:仅更新权利其他状况 3:仅更新证书附记)
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入工作流实例ID更新权利其他状况以及附记
     */
    @PostMapping(path = "/init/rest/v1.0/qlqtzkfj/{processInsId}/{mode}/list")
    void updateQlqtzkFjPl(@PathVariable("processInsId") String processInsId, @PathVariable("mode") String mode);

    /**
     * 初始化不动产权证数量
     * @param processInsId
     * @throws Exception
     * @return 数量
     */
    @PostMapping(path = "/init/rest/v1.0/{processInsId}/bdcqzs/sc/sl")
    int initLcBdcqzSl(@PathVariable("processInsId") String processInsId) throws Exception;

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: processInsId 流程实例id
     * @return: int 证书数量
     * @description 通过流程实例id获取当前流程的拥有房地产权利类型的证书数量
     */
    @PostMapping(path = "/init/rest/v1.0/bdcqzs/{processInsId}/count/fdcq")
    int countLcBdcqzWithFdcq(@PathVariable("processInsId") String processInsId) throws Exception;

    /**
     * 初始化不动产权证数量
     * @param xmid
     * @throws Exception
     * @return 数量
     */
    @PostMapping(path = "/init/rest/v1.0/{xmid}/bdcqz/sc/sl")
    int initXmBdcqzSl(@PathVariable("xmid") String xmid) throws Exception;

    /**
     * 通过传入processInsId清空项目表的权利其他状况(清空抵押项目)
     * @param processInsId 工作流实例id
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入processInsId清空项目表的权利其他状况(清空抵押项目)
     */
    @PostMapping(path = "/init/rest/v1.0/clear/xmqlqtzk/{processInsId}")
    void clearDyaXmQlqtzkAndYzh(@PathVariable("processInsId") String processInsId);

    /**
     * 通过传入processInsId将原不动产权证号追加到项目和证书的权利其他状况里(南通  抵押权处理)
     * @param processInsId 工作流实例id
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入processInsId将原不动产权证号追加到项目和证书的权利其他状况里
     */
    @PostMapping(path = "/init/rest/v1.0/ybdcqzh/to/qlqtzk/{processInsId}")
    void initYbdcqzhToQlqtzk(@PathVariable("processInsId") String processInsId);
    /**
     * 通过传入processInsId将原不动产权证号追加到项目和证书的权利其他状况里(海门 抵押权处理)
     * @param processInsId 工作流实例id
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @description 通过传入processInsId将原不动产权证号追加到项目和证书的权利其他状况里（大于等于两条显示详见抵押清单）
     */
    @PostMapping(path = "/init/rest/v1.0/onlyone/ybdcqzh/to/qlqtzk/{processInsId}")
    void initOnlyOneYbdcqzhToQlqtzk(@PathVariable("processInsId") String processInsId);

    /**
     * 通过传入processInsId将原不动产权证号追加到项目和证书的权利其他状况里(南通海门抵押权通用处理,通过配置区分,上两个接口的合并处理)
     * @param processInsId 工作流实例id
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 通过传入processInsId将原不动产权证号追加到项目和证书的权利其他状况里
     */
    @PostMapping(path = "/init/rest/v1.0/common/ybdcqzh/to/qlqtzk/{processInsId}")
    void initCommonYbdcqzhToQlqtzk(@PathVariable("processInsId") String processInsId);
    /**
     * 通过传入项目id清空权利附记
     * @param xmid 项目id
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入项目id清空权利附记
     */
    @PostMapping(path = "/init/rest/v1.0/clear/qlfj/{xmid}")
    void clearQlFj(@PathVariable("xmid") String xmid);


    /**
     * 通过传入工作流实例ID生成权利附记
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入工作流实例ID生成权利附记
     */
    @PostMapping(path = "/init/rest/v1.0/sc/qlfj/{gzlslid}")
    void updateQlFj(@PathVariable("gzlslid") String gzlslid);

    /**
     * 通过传入项目id获取权利其他状况以及附记
     * @param xmid 项目id
     * @param mode 模式(2:权利其他状况 3:附记)
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入项目id获取权利其他状况以及附记
     */
    @GetMapping(path = "/init/rest/v1.0/qlqtzkfj/{xmid}/{mode}")
    String queryQlqtzkFj(@PathVariable("xmid") String xmid, @PathVariable("mode") String mode);

    /**
     * @param bdcQlqtzkFjQO 权利其他和附记操作QO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据证书信息更新项目的权利其他状况或权利附记
     */
    @PutMapping(path = "/init/rest/v1.0/qlqtzkAndFj")
    void updateQlqtzkAndFj(@RequestBody BdcQlqtzkFjQO bdcQlqtzkFjQO);

    /**
     * 查询生成的不动产权证
     * @param xmid
     * @return 集合
     */
    @GetMapping(path = "/init/rest/v1.0/{xmid}/bdcqz/list")
    List<BdcZsDO> queryBdcqz(@PathVariable("xmid") String xmid);


    /**
     * 通过传入processInsId删除证书
     *
     * @param processInsId 工作流实例id
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入processInsId删除证书
     */
    @DeleteMapping(path = "/init/rest/v1.0/delete/zs/{processInsId}")
    void deleteBdcZs(@PathVariable("processInsId") String processInsId) throws Exception;

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description xmid 删除证书及相关关系
     * @date : 2022/12/27 10:45
     */
    @DeleteMapping("/init/rest/v1.0/delete/zs/xmid")
    void deleteBdczsByXmid(@RequestParam(value = "xmid", required = true) String xmid) throws Exception;

    /**
     * 通过传入processInsId djxl 生成原产权证号
     *
     * @param processInsId 工作流实例id
     * @param djxl         等级小类
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 通通过传入processInsId djxl 生成原产权证号
     */
    @PostMapping(path = "/init/rest/v1.0/delete/zs/{processInsId}")
    String initYcqzhPl(@PathVariable("processInsId") String processInsId,
                       @RequestParam(value = "djxl", required = false) String djxl) throws Exception;

    /**
     * 通过传入的工作流实例ID，追加附记内容至上一手的附记中去
     *
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(path = "/init/rest/v1.0/fj/zjsys/{processInsId}")
    void zjsysFj(@PathVariable("processInsId") String processInsId);

    /**
     * 初始化不动产权证（数据补录）  不生成权利其他状况和附记
     *
     * @param processInsId
     * @param zsyl         是否预览
     * @return 集合
     * @throws Exception
     */
    @PostMapping(path = "/init/rest/v1.0/{processInsId}/bdcqzs/sc/xzlc")
    List<BdcZsDO> initBdcqzsByXz(@PathVariable("processInsId") String processInsId, @RequestParam("xmid") String xmid, @RequestParam(value = "zsyl", required = false) boolean zsyl) throws Exception;

}
