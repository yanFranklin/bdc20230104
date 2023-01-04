package cn.gtmap.realestate.common.core.dto.building;

import cn.gtmap.realestate.common.core.domain.building.FwKDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-06
 * @description 宗地Tree结构查询  包括 宗地 和 宗地下 自然幢列表
 */
@ApiModel(value = "ZdTreeResponseDTO", description = "宗地Tree结构查询")
public class ZdTreeResponseDTO {

    /**
     * 宗地基本信息
     */
    @ApiModelProperty(value = "宗地基本信息")
    private ZdDjdcbDO zdDjdcbDO;

    /**
     * 自然幢列表
     */
    @ApiModelProperty(value = "自然幢列表")
    private List<ZdTreeZrzResponseDTO> zrzList;


    public ZdDjdcbDO getZdDjdcbDO() {
        return zdDjdcbDO;
    }

    public void setZdDjdcbDO(ZdDjdcbDO zdDjdcbDO) {
        this.zdDjdcbDO = zdDjdcbDO;
    }


    public List<ZdTreeZrzResponseDTO> getZrzList() {
        return zrzList;
    }

    public void setZrzList(List<ZdTreeZrzResponseDTO> zrzList) {
        this.zrzList = zrzList;
    }
}
