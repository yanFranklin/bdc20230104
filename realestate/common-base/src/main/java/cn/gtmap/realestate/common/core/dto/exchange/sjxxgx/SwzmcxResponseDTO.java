package cn.gtmap.realestate.common.core.dto.exchange.sjxxgx;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0  2022-11-10
 * @description 死亡证明查询 响应结果
 */
public class SwzmcxResponseDTO {

	// 公民身份号码
	private String GMSFHM;
	// 民族
	private String MZ;
	// 死亡原因
	private String SWYY;
	// 性别
	private String XB;
	// 姓名
	private String XM;

	public String getGMSFHM() {
		return GMSFHM;
	}

	public void setGMSFHM(String GMSFHM) {
		this.GMSFHM = GMSFHM;
	}

	public String getMZ() {
		return MZ;
	}

	public void setMZ(String MZ) {
		this.MZ = MZ;
	}

	public String getSWYY() {
		return SWYY;
	}

	public void setSWYY(String SWYY) {
		this.SWYY = SWYY;
	}

	public String getXB() {
		return XB;
	}

	public void setXB(String XB) {
		this.XB = XB;
	}

	public String getXM() {
		return XM;
	}

	public void setXM(String XM) {
		this.XM = XM;
	}

	@Override
	public String toString() {
		return "SwzmcxResponseDTO{" +
				"GMSFHM='" + GMSFHM + '\'' +
				", MZ='" + MZ + '\'' +
				", SWYY='" + SWYY + '\'' +
				", XB='" + XB + '\'' +
				", XM='" + XM + '\'' +
				'}';
	}
}
