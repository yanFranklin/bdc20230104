package cn.gtmap.realestate.common.core.service.rest.init;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgDO;
import cn.gtmap.realestate.common.core.domain.BdcDjlxDjxlGxDO;
import cn.gtmap.realestate.common.core.domain.BdcDjxlDjyyGxDO;
import cn.gtmap.realestate.common.core.domain.BdcDjxlQllxGxDO;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcDjlxDjxlQllxVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/5/31
 * @description 初始化相关配置 服务
 */
public interface BdcCshXtPzRestService {

    /********************初始化服务开关  配置*********************************/

    /**
     * 根据业务类型获取该流程初始化服务的开关信息
     *
     * @param djxl 登记小类
     * @return {BdcCshFwkgDO} 初始化开关实体
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/init/rest/v1.0/pz/cshFwkg/{djxl}")
    BdcCshFwkgDO queryBdcCshFwKgDO(@PathVariable("djxl") String djxl);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询
     */
    @PostMapping(value = "/init/rest/v1.0/pz/cshFwkg/list")
    List<BdcCshFwkgDO> listBdcCshFwkg(@RequestBody BdcCshFwkgDO bdcCshFwkgDO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增
     */
    @PutMapping(value = "/init/rest/v1.0/pz/cshFwkg")
    int insertBdcCshFwkg(@RequestBody BdcCshFwkgDO bdcCshFwkgDO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改
     */
    @PostMapping(value = "/init/rest/v1.0/pz/cshFwkg")
    int updateBdcCshFwkg(@RequestBody BdcCshFwkgDO bdcCshFwkgDO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除
     */
    @DeleteMapping(value = "/init/rest/v1.0/pz/cshFwkg")
    int deleteBdcCshFwkg(@RequestBody List<BdcCshFwkgDO> bdcCshFwkgDOList);



    /**********************登记类型、登记小类 关系*******************************/

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询
     */
    @PostMapping(value = "/init/rest/v1.0/pz/djlxDjxl/list")
    List<BdcDjlxDjxlGxDO> listBdcDjlxDjxlGx(@RequestBody BdcDjlxDjxlGxDO bdcDjlxDjxlGxDO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增
     */
    @PutMapping(value = "/init/rest/v1.0/pz/djlxDjxl")
    int insertBdcDjlxDjxlGx(@RequestBody BdcDjlxDjxlGxDO bdcDjlxDjxlGxDO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改
     */
    @PostMapping(value = "/init/rest/v1.0/pz/djlxDjxl")
    int updateBdcDjlxDjxlGx(@RequestBody BdcDjlxDjxlGxDO bdcDjlxDjxlGxDO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除
     */
    @DeleteMapping(value = "/init/rest/v1.0/pz/djlxDjxl")
    int deleteBdcDjlxDjxlGx(@RequestBody List<BdcDjlxDjxlGxDO> bdcDjlxDjxlGxDOList);


    /*********************登记小类、权利类型 关系********************************/

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询
     */
    @PostMapping(value = "/init/rest/v1.0/pz/djxlQllx/list")
    List<BdcDjxlQllxGxDO> listBdcDjxlQllxGx(@RequestBody BdcDjxlQllxGxDO bdcDjxlQllxGxDO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增
     */
    @PutMapping(value = "/init/rest/v1.0/pz/djxlQllx")
    int insertBdcDjxlQllxGx(@RequestBody BdcDjxlQllxGxDO bdcDjxlQllxGxDO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改
     */
    @PostMapping(value = "/init/rest/v1.0/pz/djxlQllx")
    int updateBdcDjxlQllxGx(@RequestBody BdcDjxlQllxGxDO bdcDjxlQllxGxDO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除
     */
    @DeleteMapping(value = "/init/rest/v1.0/pz/djxlQllx")
    int deleteBdcDjxlQllxGx(@RequestBody List<BdcDjxlQllxGxDO> bdcDjxlQllxGxDO);



    /************************登记原因登记*****************************/
    /**
     * @param bdcDjxlDjyyGxDOJson
     * @param pageable
     * @return  Page<BdcDjxlDjyyGxDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 分页获取 登记小类、登记原因关系
     */
    @GetMapping(value = "/init/rest/v1.0/pz/djyy")
    Page<BdcDjxlDjyyGxDO> listBdcDjxlDjyyPage(Pageable pageable,@RequestParam(value = "bdcDjxlDjyyGxDOJson",required = false)String bdcDjxlDjyyGxDOJson);
    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询
     */
    @PostMapping(value = "/init/rest/v1.0/pz/djxlDjyy/list")
    List<BdcDjxlDjyyGxDO> listBdcDjxlDjyyGx(@RequestBody BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO);

    /**
     * 查询登记原因配置顺序最大号
     * @param djxl 登记小类
     * @return 登记原因顺序最大号
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a></a>
     */
    @GetMapping(value = "/init/rest/v1.0/pz/djxlDjyy/max/count")
    int queryDjyyMaxCount(@RequestParam(name = "djxl") String djxl);

    /**
     * 根据登记小类查询登记原因（不传查所有）
     * @param djxls
     * @return
     * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 查询
     */
    @PostMapping(value = "/init/rest/v1.0/pz/djyys/list")
    List<String> listDjyys(@RequestBody(required = false) List<String> djxls);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增
     */
    @PutMapping(value = "/init/rest/v1.0/pz/djxlDjyy")
    int insertBdcDjxlDjyyGx(@RequestBody BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改
     */
    @PostMapping(value = "/init/rest/v1.0/pz/djxlDjyy")
    int updateBdcDjxlDjyyGx(@RequestBody BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO);

    /**
     * 批量更新登记原因配置
     * @param bdcDjxlDjyyGxDOList 登记原因集合
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/init/rest/v1.0/pz/djxlDjyy/pl")
    void batchUpdateBdcDjxlDjyyGx(@RequestBody List<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOList);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除
     */
    @DeleteMapping(value = "/init/rest/v1.0/pz/djxlDjyy")
    int deleteBdcDjxlDjyyGx(@RequestBody List<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOList);

    /**
     * @param bdcDjlxDjxlQllxJson
     * @param pageable
     * @return  Page<BdcDjlxDjxlQllxVO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 分页获取 登记类型、登记小类、登记原因、权利类型
     */
    @GetMapping(value = "/init/rest/v1.0/pz/bdcDjlxDjxlQllx")
    Page<BdcDjlxDjxlQllxVO> listBdcDjlxDjxlQllx(Pageable pageable,@RequestParam(value = "bdcDjlxDjxlQllxJson",required = false)String bdcDjlxDjxlQllxJson);
}
