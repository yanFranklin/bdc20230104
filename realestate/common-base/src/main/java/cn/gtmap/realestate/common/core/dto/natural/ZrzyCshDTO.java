package cn.gtmap.realestate.common.core.dto.natural;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/27
 * @description 自然资源初始化选择对象DTO
 */
@Api(value = "ZrzyCshDTO", description = "自然资源初始化选择对象DTO")
public class ZrzyCshDTO {

    @ApiModelProperty(value="工作流定义ID")
    private String gzldyid;

    @ApiModelProperty(value="工作流定义名称")
    private String gzldymc;

    @ApiModelProperty(value="业务信息集合")
    List<ZrzyCshYwxxDTO> zrzyCshYwxxDTOList;

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getGzldymc() {
        return gzldymc;
    }

    public void setGzldymc(String gzldymc) {
        this.gzldymc = gzldymc;
    }

    public List<ZrzyCshYwxxDTO> getZrzyCshYwxxDTOList() {
        return zrzyCshYwxxDTOList;
    }

    public void setZrzyCshYwxxDTOList(List<ZrzyCshYwxxDTO> zrzyCshYwxxDTOList) {
        this.zrzyCshYwxxDTOList = zrzyCshYwxxDTOList;
    }
}
