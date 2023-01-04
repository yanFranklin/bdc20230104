package cn.gtmap.realestate.common.core.service.rest.certificate;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcZsXmDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/15
 * @description
 */
public interface BdcZsxmRestService {

    /**
     * 根据证书ID查询不动产项目信息
     *
     * @param zsid 证书ID
     * @return 不动产项目信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-certificate/rest/v1.0/zsxm/listBdcXmByZsid")
    List<BdcXmDO> listBdcXmByZsid(@RequestParam("zsid") String zsid);

    /**
     * 查询项目以及关联的证书信息
     * @param bdcXmDTOList 单元信息等
     * @return  项目以及关联的证书信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/realestate-certificate/rest/v1.0/zsxm/xmzs")
    List<BdcZsXmDTO> listBdcXmZs(@RequestBody List<BdcXmDTO> bdcXmDTOList);
}
