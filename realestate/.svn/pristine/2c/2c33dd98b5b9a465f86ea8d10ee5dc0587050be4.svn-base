package cn.gtmap.realestate.exchange.service.inf;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmResponseDTO;
import cn.gtmap.realestate.common.core.dto.accept.WwsqZhlcSjclDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTO;
import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-05
 * @description 附件材料
 */
public interface FjclService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcXmDOList
     * @param  fjclList
     * @return void
     * @description 上传并保存附件材料
     */
    void uploadAndSaveFjcl(List<BdcXmDO> bdcXmDOList, List<FjclDTO> fjclList);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param gzlslid
     * @param fjclList
     * @return void
     * @description 根据工作流实例ID执行上传附件操作
     */
    void uploadAndSaveFjcl(String gzlslid, List<FjclDTO> fjclList);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param gzlslid
     * @param fjclArray
     * @return void
     * @description 异步上传
     */
    void asynUploadWithGzlslid(String gzlslid, JSONArray fjclArray);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param wwsqCjBdcXmResponseDTO
     * @param fjclList
     * @return void
     * @description
     */
    void asynUploadAndSaveFjcl(WwsqCjBdcXmResponseDTO wwsqCjBdcXmResponseDTO, List<FjclDTO> fjclList);

    /**
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @return void
     * @description 异步上传
     */
    void asynUploadAndSaveFjclForZhlc(List<WwsqZhlcSjclDTO> zhlcSjclDTOList);


    /**
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @return void
     * @description 根据工作流实例ID执行上传附件操作
     */
    void uploadAndSaveFjclForZhlc(List<WwsqZhlcSjclDTO> zhlcSjclDTOList);
}
