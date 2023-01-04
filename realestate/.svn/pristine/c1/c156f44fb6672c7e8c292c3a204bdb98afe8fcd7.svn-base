package cn.gtmap.realestate.common.core.dto.building;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/16
 * @description 地籍调查表DTO
 */
@ApiModel(value = "DjDcbAndQlrResponseDTO", description = "地籍调查表权利人实体")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz")
public class DjDcbAndQlrResponseDTO {

    /**
     * 行政区代码
     */
    private String xzqdm;
    /**
     * 地籍调查表
     */
    private DjDcbResponseDTO djDcbResponseDTO;
    /**
     * 权利人信息
     */
    private List<DjdcbQlrResponseDTO> qlrResponseDTOList;

    public DjDcbResponseDTO getDjDcbResponseDTO() {
        return djDcbResponseDTO;
    }

    public void setDjDcbResponseDTO(DjDcbResponseDTO djDcbResponseDTO) {
        this.djDcbResponseDTO = djDcbResponseDTO;
    }

    public List<DjdcbQlrResponseDTO> getQlrResponseDTOList() {
        return qlrResponseDTOList;
    }

    public void setQlrResponseDTOList(List<DjdcbQlrResponseDTO> qlrResponseDTOList) {
        this.qlrResponseDTOList = qlrResponseDTOList;
    }

    public String getXzqdm() {
        return xzqdm;
    }

    public void setXzqdm(String xzqdm) {
        this.xzqdm = xzqdm;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DjDcbAndQlrResponseDTO{");
        sb.append("xzqdm=").append(xzqdm);
        sb.append(",djDcbResponseDTO=").append(djDcbResponseDTO);
        sb.append(", qlrResponseDTOList=").append(qlrResponseDTOList);
        sb.append('}');
        return sb.toString();
    }
}