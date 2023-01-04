package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcDjxlDjyyGxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlShxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXzxxDO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: realestate
 * @description: 常州修正流程受理页面信息
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2020-09-24 08:52
 **/
public class BdcSlXzxxDTO implements Serializable {

    private static final long serialVersionUID = -346976947317381955L;

    @ApiModelProperty(value = "受理项目信息")
    private BdcSlXmDO bdcSlXmDO;

    @ApiModelProperty(value = "受理基本信息")
    private BdcSlJbxxDO bdcSlJbxxDO;

    @ApiModelProperty(value = "修正信息")
    private BdcSlXzxxDO bdcSlXzxxDO;

    @ApiModelProperty(value = "签字审核信息")
    private List<BdcSlShxxDO> bdcSlShxxDOList;

    @ApiModelProperty(value = "项目信息")
    private BdcXmDO bdcXmDO;

    @ApiModelProperty(value = "当前登录用户")
    private UserDto userDto;

    @ApiModelProperty(value = "当前时间")
    private Date nowDate;

    @ApiModelProperty(value = "登记原因登记小类集合")
    private List<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOList;

    public BdcSlXmDO getBdcSlXmDO() {
        return bdcSlXmDO;
    }

    public void setBdcSlXmDO(BdcSlXmDO bdcSlXmDO) {
        this.bdcSlXmDO = bdcSlXmDO;
    }

    public BdcSlJbxxDO getBdcSlJbxxDO() {
        return bdcSlJbxxDO;
    }

    public void setBdcSlJbxxDO(BdcSlJbxxDO bdcSlJbxxDO) {
        this.bdcSlJbxxDO = bdcSlJbxxDO;
    }


    public List<BdcSlShxxDO> getBdcSlShxxDOList() {
        return bdcSlShxxDOList;
    }

    public void setBdcSlShxxDOList(List<BdcSlShxxDO> bdcSlShxxDOList) {
        this.bdcSlShxxDOList = bdcSlShxxDOList;
    }


    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public BdcSlXzxxDO getBdcSlXzxxDO() {
        return bdcSlXzxxDO;
    }

    public void setBdcSlXzxxDO(BdcSlXzxxDO bdcSlXzxxDO) {
        this.bdcSlXzxxDO = bdcSlXzxxDO;
    }

    public BdcXmDO getBdcXmDO() {
        return bdcXmDO;
    }

    public void setBdcXmDO(BdcXmDO bdcXmDO) {
        this.bdcXmDO = bdcXmDO;
    }

    public Date getNowDate() {
        return nowDate;
    }

    public void setNowDate(Date nowDate) {
        this.nowDate = nowDate;
    }

    public List<BdcDjxlDjyyGxDO> getBdcDjxlDjyyGxDOList() {
        return bdcDjxlDjyyGxDOList;
    }

    public void setBdcDjxlDjyyGxDOList(List<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOList) {
        this.bdcDjxlDjyyGxDOList = bdcDjxlDjyyGxDOList;
    }

    @Override
    public String toString() {
        return "BdcSlXzxxDTO{" +
                "bdcSlXmDO=" + bdcSlXmDO +
                ", bdcSlJbxxDO=" + bdcSlJbxxDO +
                ", bdcSlXzxxDO=" + bdcSlXzxxDO +
                ", bdcSlShxxDOList=" + bdcSlShxxDOList +
                ", bdcXmDO=" + bdcXmDO +
                ", userDto=" + userDto +
                ", nowDate=" + nowDate +
                ", bdcDjxlDjyyGxDOList=" + bdcDjxlDjyyGxDOList +
                '}';
    }
}
