package cn.gtmap.realestate.common.core.service.rest.accept;


import cn.gtmap.realestate.common.core.domain.accept.BdcSlFpxxXgjlDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/11/22
 * @description  不动产发票修改记录 Rest 接口服务
 */
public interface BdcSlFpXgjlRestService {

    /**
     * 分页查询不动产受理发票修改信息
     * @param pageable 分页参数
     * @param json 不动产受理发票修改信息
     * @return 不动产受理发票修改信息DQ集合
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/fpxgjl/page")
    Page<BdcSlFpxxXgjlDO> listBdcSlFpXgjlByPage(Pageable pageable, @RequestParam(value = "json", required = false) String json);

    /**
     * 查询不动产发票信息修改记录信息
     * @param bdcSlFpxxXgjlDO 不动产受理发票修改信息
     * @return 不动产受理发票修改信息集合
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/fpxgjl/list")
    List<BdcSlFpxxXgjlDO> listBdcSlFpXgjl(@RequestBody BdcSlFpxxXgjlDO bdcSlFpxxXgjlDO);

    /**
     * 保存或新增不动产受理发票修改记录
     * @param bdcSlFpxxXgjlDO 不动产受理发票信息修改记录信息DO
     */
    @PostMapping("/realestate-accept/rest/v1.0/fpxgjl/bc")
    void saveBdcSlFpxxXgjl(@RequestBody BdcSlFpxxXgjlDO bdcSlFpxxXgjlDO);

}
