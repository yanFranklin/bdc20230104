package cn.gtmap.realestate.common.core.dto.exchange.sjxxgx;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0  2022-11-10
 * @description 单位法人查询 响应结果
 */
public class DwfrcxResponseDTO {

	// 登记管理机构
	private String DJGLJG;
	// 法定代表人
	private String FDDBR;
	// 举办单位名称
	private String JBDW;
	// 经费来源
	private String JFLY;
	// 开办资金
	private String KBZJ;
	// 名称
	private String MC;
	// 统一社会信用代码
	private String TYSHXYDM;
	// 住所
	private String ZS;
	// 证书有效截止日期
	private String ZSYXJSRQ;
	// 证书有效起始日期
	private String ZSYXQSRQ;
	// 宗旨和业务范围
	private String ZZHYWFW;

	public String getDJGLJG() {
		return DJGLJG;
	}

	public void setDJGLJG(String DJGLJG) {
		this.DJGLJG = DJGLJG;
	}

	public String getFDDBR() {
		return FDDBR;
	}

	public void setFDDBR(String FDDBR) {
		this.FDDBR = FDDBR;
	}

	public String getJBDW() {
		return JBDW;
	}

	public void setJBDW(String JBDW) {
		this.JBDW = JBDW;
	}

	public String getJFLY() {
		return JFLY;
	}

	public void setJFLY(String JFLY) {
		this.JFLY = JFLY;
	}

	public String getKBZJ() {
		return KBZJ;
	}

	public void setKBZJ(String KBZJ) {
		this.KBZJ = KBZJ;
	}

	public String getMC() {
		return MC;
	}

	public void setMC(String MC) {
		this.MC = MC;
	}

	public String getTYSHXYDM() {
		return TYSHXYDM;
	}

	public void setTYSHXYDM(String TYSHXYDM) {
		this.TYSHXYDM = TYSHXYDM;
	}

	public String getZS() {
		return ZS;
	}

	public void setZS(String ZS) {
		this.ZS = ZS;
	}

	public String getZSYXJSRQ() {
		return ZSYXJSRQ;
	}

	public void setZSYXJSRQ(String ZSYXJSRQ) {
		this.ZSYXJSRQ = ZSYXJSRQ;
	}

	public String getZSYXQSRQ() {
		return ZSYXQSRQ;
	}

	public void setZSYXQSRQ(String ZSYXQSRQ) {
		this.ZSYXQSRQ = ZSYXQSRQ;
	}

	public String getZZHYWFW() {
		return ZZHYWFW;
	}

	public void setZZHYWFW(String ZZHYWFW) {
		this.ZZHYWFW = ZZHYWFW;
	}

	@Override
	public String toString() {
		return "DwfrcxResponseDTO{" +
				"DJGLJG='" + DJGLJG + '\'' +
				", FDDBR='" + FDDBR + '\'' +
				", JBDW='" + JBDW + '\'' +
				", JFLY='" + JFLY + '\'' +
				", KBZJ='" + KBZJ + '\'' +
				", MC='" + MC + '\'' +
				", TYSHXYDM='" + TYSHXYDM + '\'' +
				", ZS='" + ZS + '\'' +
				", ZSYXJSRQ='" + ZSYXJSRQ + '\'' +
				", ZSYXQSRQ='" + ZSYXQSRQ + '\'' +
				", ZZHYWFW='" + ZZHYWFW + '\'' +
				'}';
	}
}
