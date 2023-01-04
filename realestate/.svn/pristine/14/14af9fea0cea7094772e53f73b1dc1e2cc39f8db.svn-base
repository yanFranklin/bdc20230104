package cn.gtmap.realestate.common.core.dto.building;

import cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO;
import cn.gtmap.realestate.common.core.domain.building.ZdJzwsuqgydcDO;
import cn.gtmap.realestate.common.core.domain.building.ZdQlrDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author  <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description 地籍宗地信息
 * @Date 2022/5/7
 **/
@ApiModel(value = "DjxxZdResponseDTO", description = "地籍的宗地和权利人实体")
public class DjxxZdResponseDTO {
    /**
     * 地籍列表
     */
    @ApiModelProperty(value = "地籍列表")
    private List<ZdxxResponseDTO> list;

    public List<ZdxxResponseDTO> getList() {
        return list;
    }

    public void setList(List<ZdxxResponseDTO> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "DjxxZdResponseDTO{" +
                "list=" + list +
                '}';
    }

    @ApiModel(value = "ZdxxResponseDTO", description = "地籍实体实体")
    public static class ZdxxResponseDTO{
        /**
         * 地籍列表
         */
        @ApiModelProperty(value = "地籍")
        private ZdDjdcbDO zdDjdcbDO;

        /**
         * 权利人
         */
        @ApiModelProperty(value = "权利人")
        private ZdQlrDO zdQlrDO;


        public ZdDjdcbDO getZdDjdcbDO() {
            return zdDjdcbDO;
        }

        public void setZdDjdcbDO(ZdDjdcbDO zdDjdcbDO) {
            this.zdDjdcbDO = zdDjdcbDO;
        }

        public ZdQlrDO getZdQlrDO() {
            return zdQlrDO;
        }

        public void setZdQlrDO(ZdQlrDO zdQlrDO) {
            this.zdQlrDO = zdQlrDO;
        }

        @Override
        public String toString() {
            return "ZdxxResponseDTO{" +
                    "zdDjdcbDO=" + zdDjdcbDO +
                    ", zdQlrDO=" + zdQlrDO +
                    '}';
        }
    }

}