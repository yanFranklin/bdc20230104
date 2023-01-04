package cn.gtmap.realestate.exchange.core.dto.shucheng.sq.request;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcSdqghDO;
import cn.hutool.core.date.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @date 2022/5/9
 * @description
 */
public class ShuChengShuiGhReqDto implements Serializable {


    private static final long serialVersionUID = 2820348034203694559L;
    /**
     * 用户编号
     */
    private String userid;
    /**
     * 原用户名称
     */
    private String oldName;
    /**
     * 原用户证件名称
     */
    private String oldIdName;

    private String oldId;


    /**
     * 新用户名称
     */
    private String newName;
    /**
     * 新证件名称
     */
    private String idName;
    /**
     * 新证件号
     */
    private String id;
    /**
     * 新电话号码
     */
    private String tel;
    /**
     * 经办人
     */
    private String byer;
    /**
     * 办理日期 yyyy-yy-dd
     */
    private String date;
    /**
     * 说明
     */
    private String remark;
    /**
     * 办理方的受理号
     */
    private String aId;
    /**
     * 1和空-更新userinfor, 0-不直接更新用户资料
     */
    private String update;
    /**
     * 数据文件，base64
     */
    private String data;
    /**
     * 文件类型，如jpg,doc,dwg等
     */
    private String datatype;

    public void updateBodyNotData(BdcSdqghDO bdcSdqghDO, BdcQlrDO qlr, BdcQlrDO ywr, String ywrzjmc, String qlrzjmc) {
        this.userid = bdcSdqghDO.getConsno();
        this.oldName = ywr.getQlrmc();
        this.oldIdName = ywrzjmc;
        this.oldId = ywr.getZjh();

        this.newName = qlr.getQlrmc();
        this.idName = qlrzjmc;
        this.id = qlr.getZjh();
        this.tel = qlr.getDh();
        this.byer = bdcSdqghDO.getSqblrmc();
        this.date = DateUtil.formatDate(new Date());
        this.remark = "";
        this.aId = bdcSdqghDO.getSlbh();
        this.update = "0";
    }

    public void updateData(String data, String datatype) {
        this.data = data;
        this.datatype = datatype;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getByer() {
        return byer;
    }

    public void setByer(String byer) {
        this.byer = byer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getOldIdName() {
        return oldIdName;
    }

    public void setOldIdName(String oldIdName) {
        this.oldIdName = oldIdName;
    }

    public String getOldId() {
        return oldId;
    }

    public void setOldId(String oldId) {
        this.oldId = oldId;
    }



    @Override
    public String toString() {
        return "ShuChengShuiGhReqDto{" +
                "userid='" + userid + '\'' +
                ", oldName='" + oldName + '\'' +
                ", oldIdName='" + oldIdName + '\'' +
                ", oldId='" + oldId + '\'' +
                ", newName='" + newName + '\'' +
                ", idName='" + idName + '\'' +
                ", id='" + id + '\'' +
                ", tel='" + tel + '\'' +
                ", byer='" + byer + '\'' +
                ", date='" + date + '\'' +
                ", remark='" + remark + '\'' +
                ", aId='" + aId + '\'' +
                ", update='" + update + '\'' +
                ", data='" + data + '\'' +
                ", datatype='" + datatype + '\'' +
                '}';
    }
}
