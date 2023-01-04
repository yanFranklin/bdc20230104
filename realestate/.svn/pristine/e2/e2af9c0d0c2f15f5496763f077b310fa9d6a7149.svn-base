package cn.gtmap.realestate.common.core.service.rest.analysis.count;

import cn.gtmap.realestate.common.core.dto.analysis.count.BdcCountDTO;
import cn.gtmap.realestate.common.core.dto.analysis.count.ZsyzhDTO;
import cn.gtmap.realestate.common.core.qo.analysis.count.ZsyzhtjQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: <a href="@mailto:hynkoala@163.com">hanyaning</a>
 * @version: V1.0, 2019.03.05
 * @description: 证书印制号统计rest接口类
 */
public interface ZsyzhtjRestService {

    /**
     * 统计证书印制号使用情况，返回当前各种类型使用情况的印制号数目
     * 例如：已领用：10；未打证：20..
     *
     * @param zsyzhtjQO
     * @return cn.gtmap.realestate.common.core.dto.analysis.BdcCountDTO
     * @date 2019.03.05 15:00
     * @author hanyaning
     */
    @PostMapping(value = "/realestate-analysis/rest/v1.0/count/yzhtj/syqk")
    List<BdcCountDTO> listZsyzhSyqk(@RequestBody ZsyzhtjQO zsyzhtjQO);

    /**
     * 获取证书印制号使用情况page页
     *
     * @param zsyzhtjQO
     * @return Page<cn.gtmap.realestate.common.core.dto.analysis.count.ZsyzhDto>
     * @date 2019.03.05 19:57
     * @author hanyaning
     */
    @PostMapping(value = "/realestate-analysis/rest/v1.0/count/yzhtj/page")
    Page<ZsyzhDTO> listZsyzhPage(@RequestBody ZsyzhtjQO zsyzhtjQO, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam(value = "sort", required = false) Sort sort);

}