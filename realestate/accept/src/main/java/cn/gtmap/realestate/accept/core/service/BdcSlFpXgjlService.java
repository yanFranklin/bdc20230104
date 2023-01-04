package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlFpxxXgjlDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 2021/11/19
 * @description 不动产受理发票修改记录接口服务
 */
public interface BdcSlFpXgjlService {

    /**
     * 分页查询不动产受理发票修改记录信息
     * @param pageable 分页参数
     * @param json 查询参数
     * @return 不动产受理发票修改信息List
     */
    Page<BdcSlFpxxXgjlDO> listBdcSlFpXgjlByPage(Pageable pageable, String json);

    /**
     * 查询不动产受理发票修改记录信息
     * @param bdcSlFpxxXgjlDO 查询参数
     * @return 不动产受理发票修改信息List
     */
    List<BdcSlFpxxXgjlDO> listBdcSlFpXgjl(BdcSlFpxxXgjlDO bdcSlFpxxXgjlDO);

    /**
     * 保存不动产受理发票信息修改记录
     * @param bdcSlFpxxXgjlDO 不动产受理发票信息修改记录信息DO
     */
    void saveBdcSlFpxxXgjl(BdcSlFpxxXgjlDO bdcSlFpxxXgjlDO);


}
