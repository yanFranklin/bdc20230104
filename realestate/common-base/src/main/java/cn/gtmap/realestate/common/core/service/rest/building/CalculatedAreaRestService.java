package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.dto.building.FttdmjRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.LjzJzmjRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/14
 * @description
 */
public interface CalculatedAreaRestService {
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param ljzJzmjRequestDTO
     * @return void
     * @description 计算逻辑幢建筑面积
     */
    @PutMapping("/building/rest/v1.0/calculatedarea/ljzjzmj")
    void calculatedLjzJzmj(@RequestBody LjzJzmjRequestDTO ljzJzmjRequestDTO);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param ljzJzmjRequestDTO
     * @return void
     * @description 计算逻辑幢建筑面积(通过配置)
     */
    @PutMapping("/building/rest/v1.0/calculatedarea/ljzjzmj/byconfig")
    void calculatedLjzJzmjByConfig(@RequestBody LjzJzmjRequestDTO ljzJzmjRequestDTO);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fttdmjRequestDTO
     * @return void
     * @description 计算分摊土地面积
     */
    @PutMapping("/building/rest/v1.0/calculatedarea/fttdmj")
    Integer calculatedFttdmj(@RequestBody FttdmjRequestDTO fttdmjRequestDTO);
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fttdmjRequestDTO
     * @return void
     * @description 计算分摊土地面积
     */
    @PutMapping("/building/rest/v1.0/calculatedarea/fttdmj/byconfig")
    void calculatedFttdmjByConfig(@RequestBody FttdmjRequestDTO fttdmjRequestDTO);


    /**
     * @param fttdmjRequestDTO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据dto查询有效的不动产单元号
     */
    @PostMapping("/building/rest/v1.0/calculatedarea/vaildbdcdyh/fttdmjdto")
    List<String> listValidBdcdyhByDTO(@RequestBody FttdmjRequestDTO fttdmjRequestDTO);

    /**
     * @param ljzJzmjRequestDTO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据dto查询有效的不动产单元号
     */
    @PostMapping("/building/rest/v1.0/calculatedarea/vaildbdcdyh/ljzzjmjdto")
    List<String> listValidBdcdyhByDTO(@RequestBody LjzJzmjRequestDTO ljzJzmjRequestDTO);
}
