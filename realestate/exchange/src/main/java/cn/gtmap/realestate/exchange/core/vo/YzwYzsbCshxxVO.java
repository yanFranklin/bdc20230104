package cn.gtmap.realestate.exchange.core.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/4
 * @description 一张网验证失败初始化信息
 */
@ApiModel(value = "YzwYzsbCshxxVO", description = "一张网结果信息")
public class YzwYzsbCshxxVO {

    @ApiModelProperty(value = "收件超时信息")
    private YzwYzsbSjcsVO sjcsxx;

    @ApiModelProperty(value = "项目超期办结信息")
    private YzwYzsbXmcqbjVO xmcqbj;

    @ApiModelProperty(value = "项目超期未办结信息")
    private YzwYzsbXmcqwbjVO xmcqwbj;

    @ApiModelProperty(value = "退件未推送结果数据信息")
    private YzwYzsbTjwtsjgVO tjwtsjg;

    @ApiModelProperty(value = "缺失过程信息")
    private YzwYzsbQsgcxxVO qsgcxx;

    @ApiModelProperty(value = "证件号码不规范信息")
    private YzwYzsbZjhmbghVO zjhmbgh;

    @ApiModelProperty(value = "一张网编号")
    private String yzwbh;

    public YzwYzsbSjcsVO getSjcsxx() {
        return sjcsxx;
    }

    public void setSjcsxx(YzwYzsbSjcsVO sjcsxx) {
        this.sjcsxx = sjcsxx;
    }

    public YzwYzsbXmcqbjVO getXmcqbj() {
        return xmcqbj;
    }

    public void setXmcqbj(YzwYzsbXmcqbjVO xmcqbj) {
        this.xmcqbj = xmcqbj;
    }

    public YzwYzsbXmcqwbjVO getXmcqwbj() {
        return xmcqwbj;
    }

    public void setXmcqwbj(YzwYzsbXmcqwbjVO xmcqwbj) {
        this.xmcqwbj = xmcqwbj;
    }

    public YzwYzsbTjwtsjgVO getTjwtsjg() {
        return tjwtsjg;
    }

    public void setTjwtsjg(YzwYzsbTjwtsjgVO tjwtsjg) {
        this.tjwtsjg = tjwtsjg;
    }

    public YzwYzsbQsgcxxVO getQsgcxx() {
        return qsgcxx;
    }

    public void setQsgcxx(YzwYzsbQsgcxxVO qsgcxx) {
        this.qsgcxx = qsgcxx;
    }

    public YzwYzsbZjhmbghVO getZjhmbgh() {
        return zjhmbgh;
    }

    public void setZjhmbgh(YzwYzsbZjhmbghVO zjhmbgh) {
        this.zjhmbgh = zjhmbgh;
    }

    public String getYzwbh() {
        return yzwbh;
    }

    public void setYzwbh(String yzwbh) {
        this.yzwbh = yzwbh;
    }
}
