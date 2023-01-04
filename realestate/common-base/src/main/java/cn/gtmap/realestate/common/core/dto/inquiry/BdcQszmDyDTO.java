package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/11/15
 * @description 查询子系统抵权属证明打印参数DTO
 */
public class BdcQszmDyDTO {
    @ApiModelProperty(value = "查询号")
    private String cxh;

    @ApiModelProperty(value = "缓存的Redis Key")
    private String redisKey;

    @ApiModelProperty(value = "编辑权限")
    private Boolean canEdit = false;


    public String getCxh() {
        return cxh;
    }

    public void setCxh(String cxh) {
        this.cxh = cxh;
    }

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    @Override
    public String toString() {
        return "BdcQszmDyDTO{" +
                "cxh='" + cxh + '\'' +
                ", redisKey='" + redisKey + '\'' +
                ", canEdit=" + canEdit +
                '}';
    }
}
