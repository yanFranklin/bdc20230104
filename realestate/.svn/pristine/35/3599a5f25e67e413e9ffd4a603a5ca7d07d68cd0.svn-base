package cn.gtmap.realestate.etl.core.domian.huaian;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2017/10/24
 * @description 南通附件信息表
 */
@Table(name = "t_ywbl_attachment")
public class TYwblAttachment {
    @Id
    private String no;//唯一标识
    private String internalNo;//唯一办件编号
    private String attafkonlyno;//所属对象关联唯一标识
    private String sjFileName;//材料名称
    private Integer sjNo;//材料份数
    private String smFileName;//上传/扫描件名称
    private byte[] smFile;//上传/扫描附件
    private String sjFileIf;//材料是否必须
    private String sjFileStatu;//收料收取情况
    private String ifEcPage;//是否需要电子版
    private String ord;//收件材料序号
    private String dataSources;//数据来源
    private Date syncDate;//信息同步时间
    //新增字段
    private String gettype;//收取方式

    /**
     * 56643 【淮安市】3.0版本_新增政务一张网接口需求 #4
     */
    private Date jhptUpdateTime;
    /**
     * 38386 【南通】一张网推送逻辑修改需求 新增以下字段
     */
    private String updateSign;
    private Date updateDate;
    private String updateErrorDesc;

    public String getUpdateSign() {
        return updateSign;
    }

    public void setUpdateSign(String updateSign) {
        this.updateSign = updateSign;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateErrorDesc() {
        return updateErrorDesc;
    }

    public void setUpdateErrorDesc(String updateErrorDesc) {
        this.updateErrorDesc = updateErrorDesc;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getInternalNo() {
        return internalNo;
    }

    public void setInternalNo(String internalNo) {
        this.internalNo = internalNo;
    }

    public String getAttafkonlyno() {
        return attafkonlyno;
    }

    public void setAttafkonlyno(String attafkonlyno) {
        this.attafkonlyno = attafkonlyno;
    }

    public String getSjFileName() {
        return sjFileName;
    }

    public void setSjFileName(String sjFileName) {
        this.sjFileName = sjFileName;
    }

    public Integer getSjNo() {
        return sjNo;
    }

    public void setSjNo(Integer sjNo) {
        this.sjNo = sjNo;
    }

    public String getSmFileName() {
        return smFileName;
    }

    public void setSmFileName(String smFileName) {
        this.smFileName = smFileName;
    }

    public byte[] getSmFile() {
        return smFile;
    }

    public void setSmFile(byte[] smFile) {
        this.smFile = smFile;
    }

    public String getSjFileIf() {
        return sjFileIf;
    }

    public void setSjFileIf(String sjFileIf) {
        this.sjFileIf = sjFileIf;
    }

    public String getSjFileStatu() {
        return sjFileStatu;
    }

    public void setSjFileStatu(String sjFileStatu) {
        this.sjFileStatu = sjFileStatu;
    }

    public String getIfEcPage() {
        return ifEcPage;
    }

    public void setIfEcPage(String ifEcPage) {
        this.ifEcPage = ifEcPage;
    }

    public String getOrd() {
        return ord;
    }

    public void setOrd(String ord) {
        this.ord = ord;
    }

    public String getDataSources() {
        return dataSources;
    }

    public void setDataSources(String dataSources) {
        this.dataSources = dataSources;
    }

    public Date getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(Date syncDate) {
        this.syncDate = syncDate;
    }


    public String getGettype() {
        return gettype;
    }

    public void setGettype(String gettype) {
        this.gettype = gettype;
    }

    public Date getJhptUpdateTime() {
        return jhptUpdateTime;
    }

    public void setJhptUpdateTime(Date jhptUpdateTime) {
        this.jhptUpdateTime = jhptUpdateTime;
    }
}
