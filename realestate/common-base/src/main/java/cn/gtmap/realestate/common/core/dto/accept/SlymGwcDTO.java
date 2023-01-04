package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @program: realestate
 * @description: 受理页面打开购物车页面信息组织
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-08-04 15:27
 **/
@ApiModel(value = "SlymGwcDTO",description = "受理页面打开购物车页面信息组织")
public class SlymGwcDTO implements Serializable {
    private static final long serialVersionUID = 3161802945962761851L;

    @ApiModelProperty(value = "当前创建的项目信息")
    private List<BdcXmDO> bdcXmDOList;

    @ApiModelProperty(value = "证书数量-根据初始化房屋开关实例表判断1:批量生成一本证2: 批量生成多本证  3:任意组合 0: 不生成证书")
    private Integer zssl;

    @ApiModelProperty(value = "初始化房屋开关实例表")
    private List<BdcCshFwkgSlDO> bdcCshFwkgSlDOList;

    public List<BdcXmDO> getBdcXmDOList() {
        return bdcXmDOList;
    }

    public void setBdcXmDOList(List<BdcXmDO> bdcXmDOList) {
        this.bdcXmDOList = bdcXmDOList;
    }

    public Integer getZssl() {
        return zssl;
    }

    public void setZssl(Integer zssl) {
        this.zssl = zssl;
    }

    public List<BdcCshFwkgSlDO> getBdcCshFwkgSlDOList() {
        return bdcCshFwkgSlDOList;
    }

    public void setBdcCshFwkgSlDOList(List<BdcCshFwkgSlDO> bdcCshFwkgSlDOList) {
        this.bdcCshFwkgSlDOList = bdcCshFwkgSlDOList;
    }

    @Override
    public String toString() {
        return "SlymGwcDTO{" +
                "bdcXmDOList=" + bdcXmDOList +
                ", zssl=" + zssl +
                ", bdcCshFwkgSlDOList=" + bdcCshFwkgSlDOList +
                '}';
    }
}