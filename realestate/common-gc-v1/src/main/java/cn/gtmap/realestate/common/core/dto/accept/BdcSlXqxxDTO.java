package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlShxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXqxxDO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @program: realestate
 * @description: 常州查档受理页面信息
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2020-09-24 08:52
 **/
public class BdcSlXqxxDTO implements Serializable {

    private static final long serialVersionUID = -346976947317381955L;

    @ApiModelProperty(value = "受理项目信息")
    private BdcSlXmDO bdcSlXmDO;

    @ApiModelProperty(value = "受理基本信息")
    private BdcSlJbxxDO bdcSlJbxxDO;

    @ApiModelProperty(value = "需求信息")
    private BdcSlXqxxDO bdcSlXqxxDO;

    @ApiModelProperty(value = "签字审核信息")
    private List<BdcSlShxxDO> bdcSlShxxDOList;

    @ApiModelProperty(value = "签字地址前缀")
    private String signImageUrl;

    @ApiModelProperty(value = "当前登录用户")
    private UserDto userDto;

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

    public BdcSlXqxxDO getBdcSlXqxxDO() {
        return bdcSlXqxxDO;
    }

    public void setBdcSlXqxxDO(BdcSlXqxxDO bdcSlXqxxDO) {
        this.bdcSlXqxxDO = bdcSlXqxxDO;
    }

    public List<BdcSlShxxDO> getBdcSlShxxDOList() {
        return bdcSlShxxDOList;
    }

    public void setBdcSlShxxDOList(List<BdcSlShxxDO> bdcSlShxxDOList) {
        this.bdcSlShxxDOList = bdcSlShxxDOList;
    }

    public String getSignImageUrl() {
        return signImageUrl;
    }

    public void setSignImageUrl(String signImageUrl) {
        this.signImageUrl = signImageUrl;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
