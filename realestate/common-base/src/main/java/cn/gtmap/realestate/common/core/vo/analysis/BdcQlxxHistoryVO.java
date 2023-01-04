package cn.gtmap.realestate.common.core.vo.analysis;

import cn.gtmap.realestate.common.core.dto.init.LsgxModelDTO;
import cn.gtmap.realestate.common.core.dto.init.LsgxXzqlModelDTO;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2019/3/5
 * @description 权利历史关系数据
 */
public class BdcQlxxHistoryVO {

    private LsgxModelDTO lsgxModelDTO;

    private LsgxXzqlModelDTO lsgxXzqlModelDTO;

    public LsgxModelDTO getLsgxModelDTO() {
        return lsgxModelDTO;
    }

    public void setLsgxModelDTO(LsgxModelDTO lsgxModelDTO) {
        this.lsgxModelDTO = lsgxModelDTO;
    }

    public LsgxXzqlModelDTO getLsgxXzqlModelDTO() {
        return lsgxXzqlModelDTO;
    }

    public void setLsgxXzqlModelDTO(LsgxXzqlModelDTO lsgxXzqlModelDTO) {
        this.lsgxXzqlModelDTO = lsgxXzqlModelDTO;
    }
}
