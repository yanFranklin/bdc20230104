package cn.gtmap.realestate.common.core.domain.job;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 执行器 表xxl_job_group 注册的执行器信息
 * Created by xuxueli on 16/9/30.
 */
@Table(name = "BDC_JOB_GROUP")
public class BdcJobGroupDO {

    @Id
    @ApiModelProperty(value = "ID")
    private int id;
    @ApiModelProperty(value = "appname")
    private String appname;
    @ApiModelProperty(value = "title")
    private String title;
    @ApiModelProperty(value = "addresstype")
    private int addresstype;
    @ApiModelProperty(value = "addresslist")// 执行器地址类型：0=自动注册、1=手动录入
    private String addresslist;     // 执行器地址列表，多地址逗号分隔(手动录入)
    @ApiModelProperty(value = "updatetime")
    private Date updatetime;

    // registry list
    private List<String> registryList;  // 执行器地址列表(系统注册)
    public List<String> getRegistryList() {
        if (addresslist!=null && addresslist.trim().length()>0) {
            registryList = new ArrayList<String>(Arrays.asList(addresslist.split(",")));
        }
        return registryList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAddresstype() {
        return addresstype;
    }

    public void setAddresstype(int addresstype) {
        this.addresstype = addresstype;
    }

    public String getAddresslist() {
        return addresslist;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public void setAddresslist(String addresslist) {
        this.addresslist = addresslist;
    }

}
