package cn.gtmap.realestate.etl.service;

import cn.gtmap.realestate.common.core.dto.accept.BdcNtFjParamDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/4/2
 * @description 房产交易服务
 */
public interface FcjyDataService {


    /**
     * @param map 合同参数
     * @return 存量房备案分页信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询存量房备案信息分页
     */
    Page<Map> bdcClfHtxxByPageJson(Pageable pageable,Map<String,String> map);

    /**
     * @param map 合同参数
     * @return 存量房备案分页信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询存量房备案信息分页
     */
    Page<Map> bdcSpfHtxxByPageJson(Pageable pageable,Map<String,String> map);

    /**
     * @param fwfclx 房屋房产类型
     * @param gzlslid 工作流实例ID
     * @param htbh 合同编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 上传房产交易附件到流程
     */
    void uploadFcjyfj(String fwfclx,String gzlslid,String htbh,BdcNtFjParamDTO bdcNtFjParamDTO);
}
