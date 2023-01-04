package cn.gtmap.realestate.common.core.domain.exchange.yzw;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zq
 * Date: 15-12-30
 * Time: 下午11:15
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "inf_apply_result")
public class InfApplyResultDO {
	@Id
	@ApiModelProperty(value = "流水号")
	private String no;
	@ApiModelProperty(value = "部门编码")
	private String orgId;
	@ApiModelProperty(value = "部门内部办件编号")
	private String internalNo;
	@ApiModelProperty(value = "权力编码")
	private String itemId;
	@ApiModelProperty(value = "办结状态")
	private String status;
	@ApiModelProperty(value = "办结意见")
	private String note;
	@ApiModelProperty(value = "办理附件")
	private String attachment;
	@ApiModelProperty(value = "办结时间")
	private Date finishDate;
	@ApiModelProperty(value = "录入自建系统时间")
	private Date createDate;
	@ApiModelProperty(value = "写入前置机时间")
	private Date updateDate;
	@ApiModelProperty(value = "信息同步读取时间")
	private Date syncDate;
	@ApiModelProperty(value = "信息同步标志")
	private String syncSign;
	@ApiModelProperty(value = "信息同步错误原因")
	private String syncErrorDesc;
	@ApiModelProperty(value = "许可/不予许可文号")
	private String resultNo;
	@ApiModelProperty(value = "决定附件名称")
	private String resultFileName;
	@ApiModelProperty(value = "决定附件")
	private byte[] resultFile;
	@ApiModelProperty(value = "决定时间")
	private Date resultDate;
	@ApiModelProperty(value = "数据来源")
	private String dataSources;
	@ApiModelProperty(value = "办理人员姓名")
	private String userName;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getInternalNo() {
		return internalNo;
	}

	public void setInternalNo(String internalNo) {
		this.internalNo = internalNo;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getSyncDate() {
		return syncDate;
	}

	public void setSyncDate(Date syncDate) {
		this.syncDate = syncDate;
	}

	public String getSyncSign() {
		return syncSign;
	}

	public void setSyncSign(String syncSign) {
		this.syncSign = syncSign;
	}

	public String getSyncErrorDesc() {
		return syncErrorDesc;
	}

	public void setSyncErrorDesc(String syncErrorDesc) {
		this.syncErrorDesc = syncErrorDesc;
	}

	public String getResultNo() {
		return resultNo;
	}

	public void setResultNo(String resultNo) {
		this.resultNo = resultNo;
	}

	public String getResultFileName() {
		return resultFileName;
	}

	public void setResultFileName(String resultFileName) {
		this.resultFileName = resultFileName;
	}

	public byte[] getResultFile() {
		return resultFile;
	}

	public void setResultFile(byte[] resultFile) {
		this.resultFile = resultFile;
	}

	public Date getResultDate() {
		return resultDate;
	}

	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}

	public String getDataSources() {
		return dataSources;
	}

	public void setDataSources(String dataSources) {
		this.dataSources = dataSources;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "InfApplyResultDO{" +
				"no='" + no + '\'' +
				", orgId='" + orgId + '\'' +
				", internalNo='" + internalNo + '\'' +
				", itemId='" + itemId + '\'' +
				", status='" + status + '\'' +
				", note='" + note + '\'' +
				", attachment='" + attachment + '\'' +
				", finishDate=" + finishDate +
				", createDate=" + createDate +
				", updateDate=" + updateDate +
				", syncDate=" + syncDate +
				", syncSign='" + syncSign + '\'' +
				", syncErrorDesc='" + syncErrorDesc + '\'' +
				", resultNo='" + resultNo + '\'' +
				", resultFileName='" + resultFileName + '\'' +
				", resultFile=" + Arrays.toString(resultFile) +
				", resultDate=" + resultDate +
				", dataSources='" + dataSources + '\'' +
				", userName='" + userName + '\'' +
				'}';
	}
}
