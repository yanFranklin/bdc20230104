package cn.gtmap.realestate.common.core.service.rest.etl;

import cn.gtmap.realestate.common.core.dto.register.DzzzQzkDzDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="mailto:fh@gtmap.cn">fh</a>
 * @version 1.0  2021/4/2
 * @description 电子证照信息入前置库
 */
public interface DzzzQzkTsRestService {

    /**
     * 将推送数据插入前置库
     */
    @PostMapping("/realestate-etl/rest/v1.0/zzxx/save")
    void insertDzzzQzk(@RequestBody DzzzQzkDzDTO dzzzQzkDzDTO);

    /**
     * 通过zzid更新
     */
    @PostMapping("/realestate-etl/rest/v1.0/zzxx/update")
    void updateYdzzzByZzid(@RequestBody String zzid);


}
