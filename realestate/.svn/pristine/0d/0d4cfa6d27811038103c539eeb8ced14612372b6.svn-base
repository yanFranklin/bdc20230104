package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcDsfdjJhxxDO;
import cn.gtmap.realestate.common.core.dto.accept.DecryptDto;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/6/5
 * @description 不动产与第三方交互信息表
 */
public interface BdcDsfdjJhxxRestService {

    /**
     * @param processInsId 工作流实例ID
     * @return 第三方交互信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  新增写入第三方交互信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/dsfjhxx/init")
    List<BdcDsfdjJhxxDO> initDsfdjJhxx(@RequestParam(value = "processInsId") String processInsId);

    /**
     * @param jsonObject 需要更新的对象
     * @param bdcslbh 不动产受理编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  更新第三方交互信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/dsfjhxx/update")
    void updateDsfdjJhxxByBdcslbh(@RequestBody JSONObject jsonObject, @RequestParam(value = "bdcslbh")String bdcslbh);

    /**
     * 数据库触发器调用接口
     * 第三方系统更新BDC_DSFDJ_JHXX中DSFDJZT时，调用该接口更新任务状态
     * @param bdcDsfdjJhxxDO 不动产与第三方登记系统交互信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/dsfjhxx/tz")
    void dsfTzModifyTaskStatus(@RequestBody BdcDsfdjJhxxDO bdcDsfdjJhxxDO);

    /**
     * @param processInsId 工作流示例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除不动产与第三方交互信息
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/dsfjhxx")
    void deleteDsfdjJhxx(@RequestParam(value = "processInsId") String processInsId);


    /**
     * 数据库解密函数调用接口
     * @param decryptDto
     * @return
     */
    @PostMapping("/realestate-accept/rest/v1.0/dsfjhxx/decrypt")
    String dsfDecrypt(@RequestBody DecryptDto decryptDto);

    /**
     * 数据库加密函数调用接口
     * @param decryptDto
     * @return
     */
    @PostMapping("/realestate-accept/rest/v1.0/dsfjhxx/encrypt")
    String dsfEncrypt(@RequestBody DecryptDto decryptDto);
}
