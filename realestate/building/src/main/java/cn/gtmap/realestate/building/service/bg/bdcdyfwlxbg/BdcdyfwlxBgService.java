package cn.gtmap.realestate.building.service.bg.bdcdyfwlxbg;


import cn.gtmap.realestate.common.core.dto.building.FwlxBgRequestDTO;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-02-26
 * @description 不动产单元房屋类型变更服务
 */
public interface BdcdyfwlxBgService {


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return void
     * @description 执行变更
     */
    void executeBg(FwlxBgRequestDTO requestDTO);
}

