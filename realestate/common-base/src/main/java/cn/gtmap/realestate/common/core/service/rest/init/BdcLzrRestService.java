package cn.gtmap.realestate.common.core.service.rest.init;

import cn.gtmap.realestate.common.core.domain.BdcLzrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.BdcLzrDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcLzxxDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcLzrQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: realestate
 * @description: 领证人rest服务service
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-01-17 16:12
 **/
public interface BdcLzrRestService {


    /**
     * @param bdcLzrQO 领证人查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询领证人
     * @date : 2020/1/17 16:19
     */
    @PostMapping(path = "/init/rest/v1.0/lzr/list")
    List<BdcLzrDO> listBdcLzr(@RequestBody BdcLzrQO bdcLzrQO);

    /**
     * @param bdcLzrDO 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增领证人
     * @date : 2020/1/17 16:23
     */
    @PostMapping(path = "/init/rest/v1.0/lzr")
    BdcLzrDO insertBdcLzr(@RequestBody BdcLzrDO bdcLzrDO);

    /**
     * @param bdcLzrDO 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量新增领证人
     * @date : 2020/2/11 16:23
     */
    @PostMapping(path = "/init/rest/v1.0/lzr/pl")
    List<BdcLzrDO> insertBatchBdcLzr(@RequestBody BdcLzrDO bdcLzrDO, @RequestParam("processInsId") String processInsId, @RequestParam(value = "djxl", required = false) String djxl);


    /**
     * @param bdcLzrDO 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新领证人信息
     * @date : 2020/1/17 16:23
     */
    @PutMapping(path = "/init/rest/v1.0/lzr")
    int updateBdcLzr(@RequestBody BdcLzrDO bdcLzrDO);


    /**
     * @param lzrid 领证人id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据领证人id删除
     * @date : 2020/1/17 16:23
     */
    @DeleteMapping(path = "/init/rest/v1.0/lzr/{lzrid}")
    void deleteBdcLzr(@PathVariable("lzrid") String lzrid);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcLzrDOList]
     * @return void
     * @description 批量删除领证人
     */
    @PostMapping(path = "/init/rest/v1.0/lzr/batchDelete")
    void batchDeleteBdcLzr(@RequestBody List<BdcLzrDO> bdcLzrDOList);

    /**
     * @param xmid 项目id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据xmid删除领证人信息
     * @date : 2020/1/17 16:23
     */
    @DeleteMapping(path = "/init/rest/v1.0/lzr/xmid/{xmid}")
    void deleteBdcLzrByXmid(@PathVariable("xmid") String xmid);

    /**
     * @param zsid 证书ID
     * @return List<BdcLzrDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据证书ID查询和证书相关的所有项目的受理领证人信息
     */
    @GetMapping(path = "/init/rest/v1.0/lzr/{zsid}")
    List<BdcLzrDO> getAllZsXmLzrByZsid(@PathVariable(value = "zsid") String zsid);

    /**
     * @param bdcDjxxUpdateQO 登记信息更新对象
     * @return 更新数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量更新不动产领证人
     */
    @PostMapping(path = "/init/rest/v1.0/lzr/jsonStr")
    int updateBatchBdcLzr(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO) throws Exception;


    /**
     * @param lzrmc 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量删除不动产领证人
     */
    @DeleteMapping(path = "/init/rest/v1.0/lzr/lzrxx")
    void deleteBdcLzrsByLzrxx(@RequestParam("lzrmc") String lzrmc, @RequestParam(value = "lzrzjh", required = false) String lzrzjh, @RequestParam("gzlslid") String gzlslid, @RequestParam(value = "djxl", required = false) String djxl);

    /**
     * @param bdcLzrDTO 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 合肥批量/批量组合流程方法
     */
    @PostMapping(path = "/init/rest/v1.0/lzr/lzrxx/pllc")
    Integer updateLzrxxToHfPllc(@RequestBody BdcLzrDTO bdcLzrDTO) throws Exception;


    /**
     * @param bdcLzrDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/2/21 9:55
     */
    @PostMapping(path = "/init/rest/v1.0/lzr/lzrxx/jdlc")
    BdcLzrDO updateLzrxxToHfJdlc(@RequestBody BdcLzrDTO bdcLzrDTO);

    /**
     * @param bdcLzrDOList 领证人集合
     * @return 新增个数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量新增领证人
     */
    @PostMapping(path = "/init/rest/v1.0/lzr/insertpl")
    int insertBatchBdcLzr(@RequestBody List<BdcLzrDO> bdcLzrDOList);

    /**
     * 更新lzfs
     *
     * @param gzlslid
     * @param lzfs
     */
    @GetMapping(path = "/init/rest/v1.0/lzr/lzrxx/updateAllLzfsByGzlslid")
    void updateAllLzfsByGzlslid(@RequestParam("gzlslid") String gzlslid, @RequestParam("lzfs") String lzfs);

    /**
     * 查询lzfs
     *
     * @param gzlslid
     */
    @GetMapping(path = "/init/rest/v1.0/lzr/lzrxx/getLzfsByGzlslid")
    Integer getLzfsByGzlslid(@RequestParam("gzlslid") String gzlslid);

    /**
     * 查询lzfs
     *
     * @param gzlslid
     */
    @GetMapping(path = "/init/rest/v1.0/lzr/lzrxx/getAllLzfsByGzlslid")
    List<BdcLzxxDTO> getAllLzfsByGzlslid(@RequestParam("gzlslid") String gzlslid);

    /**
     * 查询lzfs
     *
     * @param zsid
     */
    @GetMapping(path = "/init/rest/v1.0/lzr/lzrxx/getLzfsByZsid")
    BdcLzxxDTO getLzfsByZsid(@RequestParam("zsid") String zsid);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcXmDOList]
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcLzrDO>
     * @description 根据项目查询领证人
     */
    @PostMapping(path = "/init/rest/v1.0/lzr/lzrxx/getBdcLzrDOByXm")
    List<BdcLzrDO> getBdcLzrDOByXm(@RequestBody List<BdcXmDO> bdcXmDOList);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcLzrDOList]
     * @return int
     * @description 批量更新领证人
     */
    @PostMapping(path = "/init/rest/v1.0/lzr/lzrxx/batchUpdateBdcLzr")
    int batchUpdateBdcLzr(@RequestBody List<BdcLzrDO> bdcLzrDOList);

    /**
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcLzrDO>
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcXmDOList]
     * @description 根据项目查询领证人
     */
    @PostMapping(path = "/init/rest/v1.0/lzr/lzrxx/getBdcLzrDOByGzlslid")
    List<BdcLzrDO> getBdcLzrDOByGzlslid(@RequestParam("gzlslid") String gzlslid);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据领证人信息删除领证人
     * @date : 2022/3/25 13:27
     */
    @DeleteMapping("/init/rest/v1.0/lzr/lzrxx/delLzrxx")
    void deleteLzrxx(@RequestBody String json);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新领证人后回写领证人数据到证书表
     * @date : 2022/8/9 9:26
     */
    @GetMapping("/init/rest/v1.0/lzr/lzrxx/hxzs")
    void hxLzrxxToZs(@RequestParam(value = "gzlslid") String gzlslid);

}
