package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2020/10/24 14:01
 * @description 移交单任务关系实体类
 */
@Table(name = "BDC_YJD_TASK_GX")
@ApiModel(value = "BdcYjdTaskGx", description = "移交单任务关系")
public class BdcYjdTaskGxDO {
    @ApiModelProperty(value = "关系id")
    @Id
    private String gxid;

    @ApiModelProperty(value = "任务id")
    private String taskid;

    @ApiModelProperty(value = "移交单号")
    private String yjdh;

    @ApiModelProperty(value = "打印状态")
    private Integer dyzt;

    public String getGxid() {
        return gxid;
    }

    public void setGxid(String gxid) {
        this.gxid = gxid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public void setDyzt(Integer dyzt) {
        this.dyzt = dyzt;
    }

    public String getTaskid() {
        return taskid;
    }

    public String getYjdh() {
        return yjdh;
    }

    public void setYjdh(String yjdh) {
        this.yjdh = yjdh;
    }

    public Integer getDyzt() {
        return dyzt;
    }

    @Override
    public String toString() {
        return "BdcYjdGzlGxDO{" +
                "gxid='" + gxid + '\'' +
                ", taskid='" + taskid + '\'' +
                ", yjdh='" + yjdh + '\'' +
                ", dyzt='" + dyzt + '\'' +
                '}';
    }
}
