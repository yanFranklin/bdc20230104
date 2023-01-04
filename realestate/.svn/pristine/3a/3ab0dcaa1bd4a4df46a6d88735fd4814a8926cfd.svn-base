package cn.gtmap.realestate.common.core.service.rest.config;

import cn.gtmap.realestate.common.core.domain.BdcTsywPzDO;
import cn.gtmap.realestate.common.core.domain.BdcTsywZdyzdxDO;
import cn.gtmap.realestate.common.core.dto.config.BdcTsywPzDTO;
import cn.gtmap.realestate.common.core.qo.config.BdcTsywPzQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/3/5
 * @description 不动产YML配置服务接口
 */
public interface BdcTsywPzRestService {

    /**
     * @param pzid 配置ID
     * @return YML配置对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据主键获取YML配置
     */
    @GetMapping("/realestate-config/rest/v1.0/ymlpz")
    BdcTsywPzDO queryBdcTsywPzById(@RequestParam(name = "pzid") String pzid);

    /**
     * @param bdcTsywPzQO 不动产YML配置查询QO
     * @return 配置集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询YML配置
     */
    @PostMapping("/realestate-config/rest/v1.0/list/ymlpz")
    List<BdcTsywPzDO> listBdcTsywPz(@RequestBody BdcTsywPzQO bdcTsywPzQO);

    /**
     * @param bdcTsywPzQOStr 不动产YML配置查询QO
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 分页查询YML配置
     */
    @PostMapping("/realestate-config/rest/v1.0/ymlpz/page")
    Page<BdcTsywPzDO> listBdcTsywPzByPage(Pageable pageable, @RequestParam(name = "bdcTsywPzQOStr", required = false) String bdcTsywPzQOStr);

    /**
     * @param bdcTsywPzQOStr 不动产YML配置查询QO
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 不分页查询YML配置
     */
    @PostMapping("/realestate-config/rest/v1.0/ymlpz/list")
    List<BdcTsywPzDO> bdcTsywPzList(@RequestParam(name = "bdcTsywPzQOStr", required = false) String bdcTsywPzQOStr);

    /**
     * @param bdcTsywPzDO 更新YML配置信息
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据主键更新YM配置信息
     */
    @PutMapping("/realestate-config/rest/v1.0/ymlpz")
    int updateBdcTsywPz(@RequestBody BdcTsywPzDO bdcTsywPzDO);

    /**
     * @param bdcTsywPzDO YML配置信息
     * @return YML配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 新增特殊业务配置信息
     */
    @PostMapping("/realestate-config/rest/v1.0/ymlpz")
    BdcTsywPzDO insertBdcTsywPz(@RequestBody BdcTsywPzDO bdcTsywPzDO);

    /**
     * @param bdcTsywPzDOList YML配置信息
     * @return YML配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量新增特殊业务配置信息
     */
    @PostMapping("/realestate-config/rest/v1.0/tsywpz/pl")
    void insertBdcTsywPzPl(@RequestBody List<BdcTsywPzDO> bdcTsywPzDOList);

    /**
     * @param bdcTsywZdyzdxDO 查询对象
     * @return 自定义字典项
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询配置自定义字典项
     */
    @PostMapping("/realestate-config/rest/v1.0/ymlpz/zdyzdx")
    List<BdcTsywZdyzdxDO> listZdyzdx(@RequestBody BdcTsywZdyzdxDO bdcTsywZdyzdxDO);


    /**
     * 获取redis中缓存的各个模块的配置内容，将配置内容持久化至数据库
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return: void
     */
    @PostMapping("/realestate-config/rest/v1.0/ymlpz/init")
    void initYmlData();

    /**
     * @param pzid 配置ID
     * @return 特殊业务配置DTO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 通过pzid获取特殊业务配置DTO
     */
    @GetMapping("/realestate-config/rest/v1.0/tsywpz/{pzid}")
    BdcTsywPzDTO queryBdcTsywPzDTO(@PathVariable("pzid") String pzid);

    /**
     * @param bdcTsywPzDTO
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存特殊业务配置DTO
     */
    @PostMapping("/realestate-config/rest/v1.0/tsywpz/dto")
    String saveBdcTsywPzDTO(@RequestBody BdcTsywPzDTO bdcTsywPzDTO);

    /**
     * @param bdcTsywPzDO 当前配置项
     * @return 其他配置项
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据字典项标识获取当前配置项以外的配置项
     */
    @PostMapping("/realestate-config/rest/v1.0/tsywpz/list/otherByZdxbs")
    List<BdcTsywPzDO> listOtherTsywPzByZdxbs(@RequestBody BdcTsywPzDO bdcTsywPzDO);

    /**
     * @param bdcTsywPzDOList YML配置信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 批量更新特殊业务配置功能模块
     */
    @PostMapping("/realestate-config/rest/v1.0/ymlpz/pl/gnmk")
    void batchModifyTsywpzGnmk(@RequestBody List<BdcTsywPzDO> bdcTsywPzDOList);

    /**
     * @param ids 特殊业务配置内容ID集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 批量删除特殊业务配置内容
     */
    @DeleteMapping("/realestate-config/rest/v1.0/ymlpz/pl")
    void batchDeleteBdcTsywPz(@RequestBody List<String> ids);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 同步共享接口目录字典项
     */
    @PostMapping("/realestate-config/rest/v1.0/ymlpz/gxjkml")
    void syncGxjkmlZdyzdx();

}
