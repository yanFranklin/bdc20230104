package cn.gtmap.realestate.common.core.dto.building;

import cn.gtmap.realestate.common.core.domain.building.ZdJzwsuqgydcDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/16
 * @description 地籍数据DTO
 */
@ApiModel(value = "DjxxResponseDTO", description = "地籍数据实体")
public class DjxxResponseDTO {
    /**
     * 地籍调查表DTO
     */
    @ApiModelProperty(value = "地籍调查表DTO")
    private DjDcbResponseDTO djDcbResponseDTO;
    /**
     * 权利人list
     */
    @ApiModelProperty(value = "权利人")
    private List<DjxxQlrDTO> qlrList;
    /**
     * 林木所有权人list
     */
    @ApiModelProperty(value = "林木所有权人")
    private List<DjxxQlrDTO> lmsuqrList;
    /**
     * 林木使用权人list
     */
    @ApiModelProperty(value = "林木使用权人")
    private List<DjxxQlrDTO> lmsyqrList;


    /**
     * 宗地建筑物所有权共有调查
     */
    @ApiModelProperty(value = "宗地建筑物所有权共有调查")
    private List<ZdJzwsuqgydcDO> zdJzwsuqgydcDOList;


    public DjDcbResponseDTO getDjDcbResponseDTO() {
        return djDcbResponseDTO;
    }

    public void setDjDcbResponseDTO(DjDcbResponseDTO djDcbResponseDTO) {
        this.djDcbResponseDTO = djDcbResponseDTO;
    }

    public List<DjxxQlrDTO> getQlrList() {
        return qlrList;
    }

    public void setQlrList(List<DjxxQlrDTO> qlrList) {
        this.qlrList = qlrList;
    }

    public List<DjxxQlrDTO> getLmsuqrList() {
        return lmsuqrList;
    }

    public void setLmsuqrList(List<DjxxQlrDTO> lmsuqrList) {
        this.lmsuqrList = lmsuqrList;
    }

    public List<DjxxQlrDTO> getLmsyqrList() {
        return lmsyqrList;
    }

    public void setLmsyqrList(List<DjxxQlrDTO> lmsyqrList) {
        this.lmsyqrList = lmsyqrList;
    }

    public List<ZdJzwsuqgydcDO> getZdJzwsuqgydcDOList() {
        return zdJzwsuqgydcDOList;
    }

    public void setZdJzwsuqgydcDOList(List<ZdJzwsuqgydcDO> zdJzwsuqgydcDOList) {
        this.zdJzwsuqgydcDOList = zdJzwsuqgydcDOList;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DjxxResponseDTO{");
        sb.append("djDcbResponseDTO=").append(djDcbResponseDTO);
        sb.append(", qlrList=").append(qlrList);
        sb.append(", lmsuqrList=").append(lmsuqrList);
        sb.append(", lmsyqrList=").append(lmsyqrList);
        sb.append(", zdJzwsuqgydcDOList=").append(zdJzwsuqgydcDOList);
        sb.append('}');
        return sb.toString();
    }
}