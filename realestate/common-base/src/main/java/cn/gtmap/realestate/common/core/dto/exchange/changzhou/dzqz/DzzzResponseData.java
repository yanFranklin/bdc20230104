package cn.gtmap.realestate.common.core.dto.exchange.changzhou.dzqz;

public class DzzzResponseData {
	private String zzbs;

	// 证照照面信息
	private String zzzmxx;
	// 内容
	private String content;
	// 内容类型
	private String contentType;

	private String zzqzlj;
	private String zzbh;
	private String info;

	private Boolean status = true;

	public Boolean isStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getZzbs() {
		return zzbs;
	}

	public void setZzbs(String zzbs) {
		this.zzbs = zzbs;
	}

	public String getZzqzlj() {
		return zzqzlj;
	}

	public void setZzqzlj(String zzqzlj) {
		this.zzqzlj = zzqzlj;
	}

	public String getZzbh() {
		return zzbh;
	}

	public void setZzbh(String zzbh) {
		this.zzbh = zzbh;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getZzzmxx() {
		return zzzmxx;
	}

	public void setZzzmxx(String zzzmxx) {
		this.zzzmxx = zzzmxx;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		return "DzzzResponseData{" +
				"zzbs='" + zzbs + '\'' +
				", zzzmxx='" + zzzmxx + '\'' +
				", status='" + status + '\'' +
				", content='" + content + '\'' +
				", contentType='" + contentType + '\'' +
				", zzqzlj='" + zzqzlj + '\'' +
				", zzbh='" + zzbh + '\'' +
				", info='" + info + '\'' +
				'}';
	}
}
