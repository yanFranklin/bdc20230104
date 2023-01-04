package cn.gtmap.realestate.init.core.dto;

import cn.gtmap.realestate.common.core.domain.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 初始化服务最后返回的结果集
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/5.
 * @description
 */
@ApiModel(value = "InitServiceDTO", description = "初始化服务最后返回的结果集")
public class InitServiceDTO {

    @ApiModelProperty(value = "存储项目相关参数")
    private BdcXmDO bdcXm;

    @ApiModelProperty(value = "存储项目附表相关参数")
    private BdcXmFbDO bdcXmFb;

    @ApiModelProperty(value = "存储项目历史关系相关参数")
    private List<BdcXmLsgxDO> bdcXmLsgxList;

    @ApiModelProperty(value = "存储登记薄相关数据")
    private BdcBdcdjbDO bdcdjb;

    @ApiModelProperty(value = "存储宗地基本信息")
    private BdcBdcdjbZdjbxxDO bdcBdcdjbZdjbxx;

    @ApiModelProperty(value = "宗地变化情况")
    private List<BdcBdcdjbZdjbxxZdbhqkDO> bdcBdcdjbZdjbxxZdbhqkList;

    @ApiModelProperty(value = "宗海基本信息")
    private BdcBdcdjbZhjbxxDO bdcBdcdjbZhjbxx;

    @ApiModelProperty(value = "用海状况")
    private List<BdcBdcdjbZhjbxxYhzkDO> bdcBdcdjbZhjbxxYhzkList;

    @ApiModelProperty(value = "用海用岛坐标")
    private List<BdcBdcdjbZhjbxxYhydzbDO> bdcBdcdjbZhjbxxYhydzbList;

    @ApiModelProperty(value = "宗海变化情况")
    private List<BdcBdcdjbZhjbxxZhbhqkDO> bdcBdcdjbZhjbxxZhbhqkList;

    @ApiModelProperty(value = "存储证书相关参数")
    private List<BdcZsDO> bdcZsList;

    @ApiModelProperty(value = "存储项目证书关系数据")
    private List<BdcXmZsGxDO> bdcXmZsGxList;

    @ApiModelProperty(value = "存储权利信息相关参数")
    private BdcQl bdcQl;

    @ApiModelProperty(value = "存储更正信息相关参数")
    private BdcGzdjDO bdcGzdj;

    @ApiModelProperty(value = "存储项目内多幢信息")
    private List<BdcFdcqFdcqxmDO> bdcFdcqFdcqxmList;

    @ApiModelProperty(value = "建筑物区分所有权业主共有部分登记信息_共有部分")
    private List<BdcFdcq3GyxxDO> bdcFdcq3GyxxList;

    @ApiModelProperty(value = "存储权利人相关参数")
    private List<BdcQlrDO> bdcQlrList;

    @ApiModelProperty(value = "第三权利人信息")
    private List<BdcDsQlrDO> bdcDsQlrDOList;

    @ApiModelProperty(value = "存储权利人和户口本关系信息")
    private List<BdcQlrHkbGxDO> bdcQlrHkbGxDOList;

    @ApiModelProperty(value = "存储家庭成员信息")
    private List<BdcJtcyDO> bdcJtcyDOList;

    @ApiModelProperty(value = "存储业务开关实例相关数据")
    private BdcCshFwkgSlDO bdcCshFwkgSlDO;

    @ApiModelProperty(value = "存储房屋附属设施相关数据")
    private List<BdcFwfsssDO> fwfsssList;

    @ApiModelProperty(value = "存储比对权籍时权籍对象字段值为空，但是登记字段有值的字段集合")
    private Set<String> fieldNameSet;

    @ApiModelProperty(value = "存储比对权籍时权籍对象字段值为空，但是登记xm表字段有值的字段集合")
    private Set<String> xmFieldNameSet = new HashSet<>();

    @ApiModelProperty(value = "存储项目建设用地量化信息关系相关数据")
    private List<BdcXmJsydlhxxGxDO> bdcXmJsydlhxxGxList;

    @ApiModelProperty(value = "存储代理人相关数据")
    private List<BdcDlrDO> bdcDlrDOList;

    public Set<String> getFieldNameSet() {
        return fieldNameSet;
    }

    public void setFieldNameSet(Set<String> fieldNameSet) {
        this.fieldNameSet = fieldNameSet;
    }

    public Set<String> getXmFieldNameSet() {
        return xmFieldNameSet;
    }

    public void setXmFieldNameSet(Set<String> xmFieldNameSet) {
        this.xmFieldNameSet = xmFieldNameSet;
    }

    public BdcCshFwkgSlDO getBdcCshFwkgSlDO() {
        return bdcCshFwkgSlDO;
    }

    public void setBdcCshFwkgSlDO(BdcCshFwkgSlDO bdcCshFwkgSlDO) {
        this.bdcCshFwkgSlDO = bdcCshFwkgSlDO;
    }

    public BdcXmDO getBdcXm() {
        return bdcXm;
    }

    public void setBdcXm(BdcXmDO bdcXm) {
        this.bdcXm = bdcXm;
    }

    public List<BdcXmLsgxDO> getBdcXmLsgxList() {
        if (bdcXmLsgxList == null) {
            bdcXmLsgxList = new ArrayList<>();
        }
        return bdcXmLsgxList;
    }

    public void setBdcXmLsgxList(List<BdcXmLsgxDO> bdcXmLsgxList) {
        this.bdcXmLsgxList = bdcXmLsgxList;
    }

    public BdcBdcdjbDO getBdcdjb() {
        return bdcdjb;
    }

    public void setBdcdjb(BdcBdcdjbDO bdcdjb) {
        this.bdcdjb = bdcdjb;
    }

    public BdcBdcdjbZdjbxxDO getBdcBdcdjbZdjbxx() {
        return bdcBdcdjbZdjbxx;
    }

    public void setBdcBdcdjbZdjbxx(BdcBdcdjbZdjbxxDO bdcBdcdjbZdjbxx) {
        this.bdcBdcdjbZdjbxx = bdcBdcdjbZdjbxx;
    }

    public List<BdcBdcdjbZdjbxxZdbhqkDO> getBdcBdcdjbZdjbxxZdbhqkList() {
        return bdcBdcdjbZdjbxxZdbhqkList;
    }

    public void setBdcBdcdjbZdjbxxZdbhqkList(List<BdcBdcdjbZdjbxxZdbhqkDO> bdcBdcdjbZdjbxxZdbhqkList) {
        this.bdcBdcdjbZdjbxxZdbhqkList = bdcBdcdjbZdjbxxZdbhqkList;
    }

    public BdcBdcdjbZhjbxxDO getBdcBdcdjbZhjbxx() {
        return bdcBdcdjbZhjbxx;
    }

    public void setBdcBdcdjbZhjbxx(BdcBdcdjbZhjbxxDO bdcBdcdjbZhjbxx) {
        this.bdcBdcdjbZhjbxx = bdcBdcdjbZhjbxx;
    }

    public List<BdcBdcdjbZhjbxxYhzkDO> getBdcBdcdjbZhjbxxYhzkList() {
        return bdcBdcdjbZhjbxxYhzkList;
    }

    public void setBdcBdcdjbZhjbxxYhzkList(List<BdcBdcdjbZhjbxxYhzkDO> bdcBdcdjbZhjbxxYhzkList) {
        this.bdcBdcdjbZhjbxxYhzkList = bdcBdcdjbZhjbxxYhzkList;
    }

    public List<BdcBdcdjbZhjbxxYhydzbDO> getBdcBdcdjbZhjbxxYhydzbList() {
        return bdcBdcdjbZhjbxxYhydzbList;
    }

    public void setBdcBdcdjbZhjbxxYhydzbList(List<BdcBdcdjbZhjbxxYhydzbDO> bdcBdcdjbZhjbxxYhydzbList) {
        this.bdcBdcdjbZhjbxxYhydzbList = bdcBdcdjbZhjbxxYhydzbList;
    }

    public List<BdcBdcdjbZhjbxxZhbhqkDO> getBdcBdcdjbZhjbxxZhbhqkList() {
        return bdcBdcdjbZhjbxxZhbhqkList;
    }

    public void setBdcBdcdjbZhjbxxZhbhqkList(List<BdcBdcdjbZhjbxxZhbhqkDO> bdcBdcdjbZhjbxxZhbhqkList) {
        this.bdcBdcdjbZhjbxxZhbhqkList = bdcBdcdjbZhjbxxZhbhqkList;
    }

    public List<BdcZsDO> getBdcZsList() {
        if (bdcZsList == null) {
            bdcZsList = new ArrayList<>();
        }
        return bdcZsList;
    }

    public void setBdcZsList(List<BdcZsDO> bdcZsList) {
        this.bdcZsList = bdcZsList;
    }

    public BdcQl getBdcQl() {
        return bdcQl;
    }

    public void setBdcQl(BdcQl bdcQl) {
        this.bdcQl = bdcQl;
    }

    public BdcGzdjDO getBdcGzdj() {
        return bdcGzdj;
    }

    public void setBdcGzdj(BdcGzdjDO bdcGzdj) {
        this.bdcGzdj = bdcGzdj;
    }

    public List<BdcQlrDO> getBdcQlrList() {
        if (bdcQlrList == null) {
            bdcQlrList = new ArrayList<>();
        }
        return bdcQlrList;
    }

    public void setBdcQlrList(List<BdcQlrDO> bdcQlrList) {
        this.bdcQlrList = bdcQlrList;
    }

    public List<BdcFdcqFdcqxmDO> getBdcFdcqFdcqxmList() {
        return bdcFdcqFdcqxmList;
    }

    public void setBdcFdcqFdcqxmList(List<BdcFdcqFdcqxmDO> bdcFdcqFdcqxmList) {
        this.bdcFdcqFdcqxmList = bdcFdcqFdcqxmList;
    }

    public List<BdcFdcq3GyxxDO> getBdcFdcq3GyxxList() {
        return bdcFdcq3GyxxList;
    }

    public void setBdcFdcq3GyxxList(List<BdcFdcq3GyxxDO> bdcFdcq3GyxxList) {
        this.bdcFdcq3GyxxList = bdcFdcq3GyxxList;
    }

    public List<BdcFwfsssDO> getFwfsssList() {
        return fwfsssList;
    }

    public void setFwfsssList(List<BdcFwfsssDO> fwfsssList) {
        this.fwfsssList = fwfsssList;
    }

    public List<BdcXmZsGxDO> getBdcXmZsGxList() {
        if (bdcXmZsGxList == null) {
            bdcXmZsGxList = new ArrayList<>();
        }
        return bdcXmZsGxList;
    }

    public void setBdcXmZsGxList(List<BdcXmZsGxDO> bdcXmZsGxList) {
        this.bdcXmZsGxList = bdcXmZsGxList;
    }

    public BdcXmFbDO getBdcXmFb() {
        return bdcXmFb;
    }

    public void setBdcXmFb(BdcXmFbDO bdcXmFb) {
        this.bdcXmFb = bdcXmFb;
    }

    public List<BdcQlrHkbGxDO> getBdcQlrHkbGxDOList() {
        return bdcQlrHkbGxDOList;
    }

    public void setBdcQlrHkbGxDOList(List<BdcQlrHkbGxDO> bdcQlrHkbGxDOList) {
        this.bdcQlrHkbGxDOList = bdcQlrHkbGxDOList;
    }

    public List<BdcJtcyDO> getBdcJtcyDOList() {
        return bdcJtcyDOList;
    }

    public void setBdcJtcyDOList(List<BdcJtcyDO> bdcJtcyDOList) {
        this.bdcJtcyDOList = bdcJtcyDOList;
    }

    public List<BdcXmJsydlhxxGxDO> getBdcXmJsydlhxxGxList() {
        return bdcXmJsydlhxxGxList;
    }

    public void setBdcXmJsydlhxxGxList(List<BdcXmJsydlhxxGxDO> bdcXmJsydlhxxGxList) {
        this.bdcXmJsydlhxxGxList = bdcXmJsydlhxxGxList;
    }

    public List<BdcDsQlrDO> getBdcDsQlrDOList() {
        if (bdcDsQlrDOList == null) {
            bdcDsQlrDOList = new ArrayList<>();
        }
        return bdcDsQlrDOList;
    }

    public void setBdcDsQlrDOList(List<BdcDsQlrDO> bdcDsQlrDOList) {
        this.bdcDsQlrDOList = bdcDsQlrDOList;
    }

    public List<BdcDlrDO> getBdcDlrDOList() {
        return bdcDlrDOList;
    }

    public void setBdcDlrDOList(List<BdcDlrDO> bdcDlrDOList) {
        this.bdcDlrDOList = bdcDlrDOList;
    }
}
