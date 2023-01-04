package cn.gtmap.realestate.building.service;

import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.building.YcScHsGlRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.YcScHsZzglRequestDTO;

import java.util.List;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2018/12/30
 * @description 预测实测户室关联
 */
public interface YcScHsGlService {
    /**
     * @param ychsIndexList
     * @return List<FwYchsDO>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 验证预测户室list是否可以关联
     */
    List<FwYchsDO> checkYchsCanGl(List<String> ychsIndexList);

    /**
     * @param schsIndexList
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 验证实测户室list是否可以关联
     */
    List<FwHsDO> checkSchsCanGl(List<String> schsIndexList);

    /**
     * @param ycScHsGlDTO
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 实测户室与预测户室关联
     */
    void ycscHsGl(YcScHsGlRequestDTO ycScHsGlDTO);

    /**
     * @param ycScHsGlDTO
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 实测户室与预测户室取消关联
     */
    void ycscHsQxGl(YcScHsGlRequestDTO ycScHsGlDTO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwDcbIndex
     * @return void
     * @description 整幢取消关联
     */
    void ycscZzQxgl(String fwDcbIndex);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ycScHsZzglRequestDTO
     * @return void
     * @description 整幢关联
     */
    void zzgl(YcScHsZzglRequestDTO ycScHsZzglRequestDTO);
}

