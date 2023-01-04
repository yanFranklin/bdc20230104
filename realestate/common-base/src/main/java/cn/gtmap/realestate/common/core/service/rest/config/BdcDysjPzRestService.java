package cn.gtmap.realestate.common.core.service.rest.config;

import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.core.dto.config.BdcDysjPzDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/5/13
 * @description 打印数据
 */
public interface BdcDysjPzRestService {
    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取打印数据
     */
    @GetMapping("/realestate-config/rest/v1.0/dysjpPz/page")
    Page<BdcDysjPzDO> listBdcDysjPz(Pageable pageable, @RequestParam(name = "dysjZbPzParamJson", required = false) String dysjZbPzParamJson);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取打印主表配置
     */
    @GetMapping("/realestate-config/rest/v1.0/dysjpPz")
    BdcDysjPzDO queryBdcDysjPzDO(@RequestParam(name = "id") String id);

    /**
     * @param dylxList 打印类型
     * @return BdcDysjPzDO 打印数据信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据打印类型获取打印主表配置信息
     */
    @PostMapping("/realestate-config/rest/v1.0/dysjPz")
    Map queryBdcDysjPzByDylx(@RequestBody List<String> dylxList);


    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存打印数据
     */
    @PutMapping("/realestate-config/rest/v1.0/dysjpz")
    int saveBdcDysjPzDO(@RequestBody BdcDysjPzDO bdcDysjPzDO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改打印数据
     */
    @PostMapping("/realestate-config/rest/v1.0/dysjpz")
    int updateBdcDysjPzDO(@RequestBody BdcDysjPzDO bdcDysjPzDO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除打印数据
     */
    @DeleteMapping("/realestate-config/rest/v1.0/dysjpz")
    int deleteBdcDysjPzDO(@RequestBody List<BdcDysjPzDO> bdcDysjPzDO);

    //子表

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取打印子表数据
     */
    @GetMapping("/realestate-config/rest/v1.0/dysjZbPz/page")
    Page<BdcDysjZbPzDO> listBdcDysjZbPz(Pageable pageable, @RequestParam(name = "dysjZbPzParamJson", required = false) String dysjZbPzParamJson);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取打印子表配置
     */
    @GetMapping("/realestate-config/rest/v1.0/dysjZbPz")
    BdcDysjZbPzDO queryBdcDysjZbPzDO(@RequestParam(name = "id") String id);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存打印子表数据
     */
    @PutMapping("/realestate-config/rest/v1.0/dysjZbPz")
    int saveBdcDysjZbPzDO(@RequestBody BdcDysjZbPzDO bdcDysjZbPzDO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改打印子表数据
     */
    @PostMapping("/realestate-config/rest/v1.0/dysjZbPz")
    int updateBdcDysjZbPzDO(@RequestBody BdcDysjZbPzDO bdcDysjZbPzDO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除打印子表数据
     */
    @DeleteMapping("/realestate-config/rest/v1.0/dysjZbPz")
    int deleteBdcDysjZbPzDO(@RequestBody List<BdcDysjZbPzDO> bdcDysjZbPzDO);

    /**
     * @param bdcDysjZbPzDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取子表配置数量
     */
    @PostMapping("/realestate-config/rest/v1.0/dysjZbPz/count")
    int countBdcDysjZbPz(@RequestBody BdcDysjZbPzDO bdcDysjZbPzDO);

    /**
     * @param dylx 打印类型
     * @return BdcDysjPzDTO 打印配置信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据打印类型查询主表和子表的配置信息，如果打印类型在库里由重复，则会给出报错信息
     */
    @GetMapping("/realestate-config/rest/v1.0/dysjZbPz/pzxx/{dylx}")
    BdcDysjPzDTO getPzxx(@PathVariable(value = "dylx") String dylx);

    /**
     * @param bdcDysjPzDTO 打印配置数据DTO
     * @return int 更新/保存的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存或更新打印配置信息
     */
    @PostMapping("/realestate-config/rest/v1.0/dysjZbPz/pzxx")
    BdcDysjPzDTO saveOrUpdatePzxx(@RequestBody BdcDysjPzDTO bdcDysjPzDTO);

    /**
     * @param xml xml信息
     * @return String  redisKey 保存到redis中的key值
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将xml信息保存到redis中，设置有效期为30秒
     */
    @PostMapping("/realestate-config/rest/v1.0/dysjZbPz/pzxx/xml")
    String sendXmlToRedis(@RequestBody String xml);

    /**
     * @param redisKey redis健
     * @return String xml信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 从redis中获取保存的xml信息
     */
    @GetMapping("/realestate-config/rest/v1.0/dysjZbPz/print/xml/{redisKey}")
    String getXmlFromRedis(@PathVariable(value = "redisKey") String redisKey);


    @GetMapping("/realestate-config/rest/v1.0/dysjZbPz/pzxx/checkDylx")
    int checkDylx(@RequestParam(name = "dylx") String dylx);
}
