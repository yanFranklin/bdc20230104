package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.qo.BaseQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-08-07
 * @description 不动产 锁定、解锁业务类
 */
public interface BdcSdRestService {
    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 锁定不动产单元
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcdy/sd")
    int sdBdcdy(@RequestBody List<BdcDysdDO> bdcDysdDOList);

    /**
     * 生成不动产单元锁定信息（锁定状态为：0 正常）
     * @return
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcdy/sc/sdxx")
    void scSdBdcdyxx(@RequestBody List<BdcDysdDO> bdcDysdDOList);
    /**
     * @param
     * @return
     * @author <a href ="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @description 锁定不动产权证(暂时只给初始化生成锁定信息使用，不传锁定原因)
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdczs/sdnosdyy")
    void sdBdczsNoSdyy(@RequestBody List<BdcZssdDO> bdcZssdDOList);
    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 锁定不动产权证
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdczs/sd")
    int sdBdczs(@RequestBody List<BdcZssdDO> bdcZssdDOList,
                @RequestParam("sdyy") String sdyy);

    /**
     * 更新证书锁定 <br>
     * <p>
     * 由于重新生成证书，所以先对于将原证书锁定信息关联到新生成的证书上 <br/>
     *
     * @param bdcZssdDOList 证书锁定集合
     * @return 更新数据条数
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PutMapping(value = "/realestate-inquiry/rest/v1.0/bdczs/sd")
    int updateSdZs(@RequestBody List<BdcZssdDO> bdcZssdDOList);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 解锁不动产单元
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcdy/js")
    int jsBdcdy(@RequestBody List<BdcDysdDO> bdcDysdDOList,
                @RequestParam("jsyy") String jsyy);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 解锁不动产权证
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdczs/js")
    int jsBdczs(@RequestBody List<BdcZssdDO> bdcZssdDOList,
                @RequestParam("sdyy") String sdyy);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询不动产单元锁定
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcdy")
    List<BdcDysdDO> queryBdcdySd(@RequestBody BdcDysdDO bdcDysdDO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询不动产证书锁定
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdczs")
    List<BdcZssdDO> queryBdczsSd(@RequestBody BdcZssdDO bdcZssdDO);

    /**
     * @param bdcDysdDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存不动产单元锁定备注
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcdy/bz")
    int saveBdcdysdBz(@RequestBody BdcDysdDO bdcDysdDO);

    /**
     * @param bdcZssdDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存不动产证书锁定备注
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdczs/bz")
    int saveBdczssdBz(@RequestBody BdcZssdDO bdcZssdDO);

    /**
     * @param bdcZssdDOList 证书锁定集合
     * @return {int} 删除证书数目
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 删除证书锁定信息 <br>
     * 删除补录修改流程时需要同步删除证书锁定信息。
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdczs/sd/blsc")
    int deleteBdczsSd(@RequestBody List<BdcZssdDO> bdcZssdDOList);

    /**
     * 根据gzlslid删除证书锁定数据
     *
     * @param processInsId
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @description 业务初始化生成的证书锁定数据 删除业务时需要删除锁定数据
     */
    @DeleteMapping(path = "/realestate-inquiry/rest/v1.0/zssd/processInsId")
    int deleteBdczsSdByGzlslid(@RequestParam(value = "processInsId") String processInsId);

    /**
     * 根据工作流实例ID查询证书锁定信息
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return List<BdcZssdDO> 证书锁定DO集合
     */
    @GetMapping(value= "/realestate-inquiry/rest/v1.0/bdczs/sd/{gzlslid}")
    List<BdcZssdDO> queryBdczsSdByGzlslid(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * 根据工作流实例ID查询单元锁定信息
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return List<BdcDysdDO> 单元锁定DO集合
     */
    @GetMapping(value= "/realestate-inquiry/rest/v1.0/bdcdy/sd/{gzlslid}")
    List<BdcDysdDO> queryBdcDySdByGzlslid(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * 通过工作流实例ID修改证书锁定数据
     * @param bdcZssdDO 证书锁定DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PutMapping(value = "/realestate-inquiry/rest/v1.0/bdczs/sd/gzlslid")
    void updateZssdByGzlslid(@RequestBody BdcZssdDO bdcZssdDO);

    /**
     * 通过工作流实例ID修改单元锁定数据
     * @param bdcDysdDO 单元锁定DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PutMapping(value = "/realestate-inquiry/rest/v1.0/bdcdy/sd/gzlslid")
    void updateDysdByGzlslid(@RequestBody BdcDysdDO bdcDysdDO);

    /**
     * 锁定/冻结 不动产证书或不动产单元数据
     * <p>大云调用接口，冻结流程办结时触发</p>
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdczs/sd/dj")
    void bdczsSdDj(@RequestParam(name="processInsId") String processInsId);

    /**
     * 解锁/解冻 不动产证书或不动产单元数据
     * <p>大云调用接口，解冻流程办结时触发</p>
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdczs/sd/jd")
    void bdczsSdJd(@RequestParam(name="processInsId") String processInsId);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * 根据bdcdyh解锁不动产单元
     * @param bdcDysdDO
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcdy/jsByBdcdyh")
    void jsBdcdyhByBdcdyh(@RequestBody BdcDysdDO bdcDysdDO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * 根据cqzh解锁不动产证书
     * @param bdcZssdDO
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdczs/jsBycqzh")
    void jsBdczsByCqzh(@RequestBody BdcZssdDO bdcZssdDO);

    /**
     * 根据主键ID查询锁定信息
     * @param sdxxid 主键ID
     * @return Object 单元或者证书锁定信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcsd/{sdxxid}")
    Object queryBdcSdxxById(@PathVariable("sdxxid") String sdxxid);

    /**
     * 根据cqzh列表查询证书锁定信息
     * @param baseQO
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdczssd/search")
    List<BdcZssdDO> queryBdcZssdByCqzhs(@RequestBody BaseQO baseQO);


    /**
     * 解锁原项目的不动产证书
     * <p>大云调用接口，用于业务审核通过后，自动解锁原项目的不动产权证书的不良记录</p>
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdczs/bljl/js")
    void jsBdcZsByYcqzh(@RequestParam(name="processInsId") String processInsId);

    /**
     * @param zssdIdList 证书锁定ID集合
     * @return {int} 删除证书数目
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 删除证书锁定信息
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdczs/sd/plsc")
    int batchDeleteBdcZssd(@RequestBody List<String> zssdIdList);

    /**
     * 通过gzlslid获取历史关系，通过yxmid获取锁定/冻结数据
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @return 证书锁定信息
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdczs/sd/yxmZssd/{gzlslid}")
    List<BdcZssdDO> queryYxmZssd(@PathVariable(name = "gzlslid") String gzlslid, @RequestParam(value ="sdzt", required = false) Integer sdzt);

    /**
     * 通过gzlslid获取历史关系，通过yxmid获取单元锁定/冻结数据
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 单元锁定信息
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcdy/sd/yxmdysd/{gzlslid}")
    List<BdcDysdDO> queryYxmDysd(@PathVariable(name = "gzlslid") String gzlslid, @RequestParam(value ="sdzt", required = false) Integer sdzt);

    /**
     * 通过xmid获取锁定/冻结数据
     * @param xmid xmid
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @return 证书锁定信息
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdczs/sd/xmid/{xmid}")
    List<BdcZssdDO> queryBdczsSdByXmid(@PathVariable(name = "xmid") String xmid);
}
