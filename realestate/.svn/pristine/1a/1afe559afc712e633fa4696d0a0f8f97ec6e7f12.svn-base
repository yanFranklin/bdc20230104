package cn.gtmap.realestate.common.core.dto.exchange.sjxxgx;

import cn.gtmap.realestate.common.util.DateUtils;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0  2022-11-10
 * @description 婚姻登记查询 响应结果
 */
public class HydjcxResponseDTO implements Comparable<HydjcxResponseDTO>{

	// 登记机构
	private String DJJG;
	// 登记日期
	private String DJRQ;
	// 登记证号
	private String DJZH;
	// 公民身份号码
	private String GMSFHM;
	// 婚姻登记业务类型
	private String HYDJYWLX;
	// 女方出生日期
	private String LFCSRQ;
	// 女方国籍
	private String LFGJ;
	// 女方公民身份号码
	private String LFGMSFHM;
	// 女方姓名
	private String LFXM;
	// 男方出生日期
	private String NFCSRQ;
	// 男方国籍
	private String NFGJ;
	// 男方公民身份号码
	private String NFGMSFHM;
	// 男方姓名
	private String NFXM;
	// 姓名
	private String XM;

	public String getDJJG() {
		return DJJG;
	}

	public void setDJJG(String DJJG) {
		this.DJJG = DJJG;
	}

	public String getDJRQ() {
		return DJRQ;
	}

	public void setDJRQ(String DJRQ) {
		this.DJRQ = DJRQ;
	}

	public String getDJZH() {
		return DJZH;
	}

	public void setDJZH(String DJZH) {
		this.DJZH = DJZH;
	}

	public String getGMSFHM() {
		return GMSFHM;
	}

	public void setGMSFHM(String GMSFHM) {
		this.GMSFHM = GMSFHM;
	}

	public String getHYDJYWLX() {
		return HYDJYWLX;
	}

	public void setHYDJYWLX(String HYDJYWLX) {
		this.HYDJYWLX = HYDJYWLX;
	}

	public String getLFCSRQ() {
		return LFCSRQ;
	}

	public void setLFCSRQ(String LFCSRQ) {
		this.LFCSRQ = LFCSRQ;
	}

	public String getLFGJ() {
		return LFGJ;
	}

	public void setLFGJ(String LFGJ) {
		this.LFGJ = LFGJ;
	}

	public String getLFGMSFHM() {
		return LFGMSFHM;
	}

	public void setLFGMSFHM(String LFGMSFHM) {
		this.LFGMSFHM = LFGMSFHM;
	}

	public String getLFXM() {
		return LFXM;
	}

	public void setLFXM(String LFXM) {
		this.LFXM = LFXM;
	}

	public String getNFCSRQ() {
		return NFCSRQ;
	}

	public void setNFCSRQ(String NFCSRQ) {
		this.NFCSRQ = NFCSRQ;
	}

	public String getNFGJ() {
		return NFGJ;
	}

	public void setNFGJ(String NFGJ) {
		this.NFGJ = NFGJ;
	}

	public String getNFGMSFHM() {
		return NFGMSFHM;
	}

	public void setNFGMSFHM(String NFGMSFHM) {
		this.NFGMSFHM = NFGMSFHM;
	}

	public String getNFXM() {
		return NFXM;
	}

	public void setNFXM(String NFXM) {
		this.NFXM = NFXM;
	}

	public String getXM() {
		return XM;
	}

	public void setXM(String XM) {
		this.XM = XM;
	}

	@Override
	public String toString() {
		return "HydjcxResponseDTO{" +
				"DJJG='" + DJJG + '\'' +
				", DJRQ='" + DJRQ + '\'' +
				", DJZH='" + DJZH + '\'' +
				", GMSFHM='" + GMSFHM + '\'' +
				", HYDJYWLX='" + HYDJYWLX + '\'' +
				", LFCSRQ='" + LFCSRQ + '\'' +
				", LFGJ='" + LFGJ + '\'' +
				", LFGMSFHM='" + LFGMSFHM + '\'' +
				", LFXM='" + LFXM + '\'' +
				", NFCSRQ='" + NFCSRQ + '\'' +
				", NFGJ='" + NFGJ + '\'' +
				", NFGMSFHM='" + NFGMSFHM + '\'' +
				", NFXM='" + NFXM + '\'' +
				", XM='" + XM + '\'' +
				'}';
	}

	/**
	 * @description: 比较登记日期 降序，从大到小
	 * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
	 * @date: 2022/12/15 17:47
	 * @param o
	 * @return: int
	 **/
	@Override
	public int compareTo(HydjcxResponseDTO o) {
		return o.getDJRQ().compareTo(this.DJRQ);
	}

}
