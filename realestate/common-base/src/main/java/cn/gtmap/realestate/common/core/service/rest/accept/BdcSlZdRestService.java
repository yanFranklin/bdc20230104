package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.dto.config.BdcZdChangeDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.3, 2019/1/14
 * @description 不动产受理字典服务
 */
public interface BdcSlZdRestService {
    /**
     * @return  Map<String, List<Map>
     * @author <a href ="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 获取所有字典
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/bdcslzd/list")
    Map<String, List<Map>> listBdcSlzd();

    /**
     * @param zdmc 字段名称
     * @return List<Map>
     * @author <a href ="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据字段名称获取字典项
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/bdcslzd/{zdmc}")
    List<Map> queryBdcSlzd(@PathVariable(name = "zdmc") String zdmc);

    /**
     * @param entity
     * @return
     * @author <a href ="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 字典项代码转名称
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/bdcslzd/convertEntityToMc")
    Object convertEntityToMc(@RequestBody Object entity);
    /**
     * @param entity
     * @return
     * @author <a href ="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 字典项名称转代码
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/bdcslzd/convertEntityToDm")
    Object convertEntityToDm(@RequestBody Object entity);

    /**
     * @param convertMap
     * @return
     * @author <a href ="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 转换map中的字典项代码为名称
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/bdcslzd/convertMapToMc")
    Map convertMapToMc(@RequestBody Map convertMap);

    /**
     * @param convertMap
     * @return
     * @author <a href ="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 转换map中的字典项名称为代码
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/bdcslzd/convertMapToDm")
    Map convertMapToDm(@RequestBody Map convertMap);

    /**
     * 刷新不动产受理库字典数据
     *
     * @param zdmc  字典名称
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/bdcslzd/refresh/{zdmc}")
    void refreshBdcSlZd(@PathVariable(name = "zdmc", required = false) String zdmc);

    /**
     * 新增字典项
     * @param bdcZdChangeDTO
     * @return
     */
    @PostMapping("realestate-accept/rest/v1.0/bdcslzd/addItem")
    void addZdItem(@RequestBody BdcZdChangeDTO bdcZdChangeDTO);

    /**
     * 编辑字典项
     * @param bdcZdChangeDTO
     * @return
     */
    @PostMapping("realestate-accept/rest/v1.0/bdcslzd/editItem")
    void editZdItem(@RequestBody BdcZdChangeDTO bdcZdChangeDTO);

    /**
     * 删除字典项
     * @param bdcZdChangeDTO
     * @return
     */
    @PostMapping("realestate-accept/rest/v1.0/bdcslzd/delItem")
    void delZdItem(@RequestBody BdcZdChangeDTO bdcZdChangeDTO);
}
