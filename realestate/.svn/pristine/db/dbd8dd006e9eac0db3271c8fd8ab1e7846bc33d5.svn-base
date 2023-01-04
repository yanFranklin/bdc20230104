package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.dto.building.ZlsxDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/27
 * @description 坐落刷新相关服务
 */
public interface ZlsxRestService {
    /**
     * @param zlsxDTO
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 坐落刷新
     */
    @PutMapping("/building/rest/v1.0/zlsx")
    void zlsxByRule(@RequestBody ZlsxDTO zlsxDTO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param zlsxDTO
     * @return void
     * @description 使用默认配置 做 坐落刷新
     */
    @PutMapping("/building/rest/v1.0/zlsx/byconfig")
    void zlsxByConfig(@RequestBody ZlsxDTO zlsxDTO);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return cn.gtmap.realestate.common.core.dto.building.ZlsxDTO
     * @description 查询默认配置
     */
    @GetMapping("/building/rest/v1.0/zlsx/getconfig")
    ZlsxDTO getConfig();

    /**
     * @param zlsxDTO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据dto查询有效的不动产单元号
     */
    @PostMapping("/building/rest/v1.0/zlsx/vaildbdcdyh/dto")
    List<String> listValidBdcdyhByDTO(@RequestBody ZlsxDTO zlsxDTO);
}
