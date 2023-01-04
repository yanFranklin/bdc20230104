package cn.gtmap.realestate.common.core.dto.exchange.nantong.sw.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/11/22
 * @description 获取契税完税信息
 */
@XmlRootElement(name = "TAXBIZML")
@XmlAccessorType(XmlAccessType.FIELD)

public class SwWsxxQO {


    /**
     * FCJYSKXXLIST : {"SJBH":"收件编号","SJRQ":"收件日期","ZLFCLFBZ":"增量房存量房标志","SJGSDQ":"数据归属地区","HTBH":"合同编号","JYUUID":"交易编号","FWUUID":"房屋编号","NSRSBH":"纳税人识别号","TDFWDZ":"土地房屋地址","NSRMC":"纳税人名称","DZSPHM":"电子税票号码"}
     */
    @XmlElement(name = "FCJYSKXXLIST")
    private FcjyskxxListQO fcjyskxxlist;

    //    @XmlElement(name = "FCJYSKXXLIST")
    public FcjyskxxListQO getFcjyskxxListQO() {
        return fcjyskxxlist;
    }

    public void setFcjyskxxListQO(FcjyskxxListQO fcjyskxxlist) {
        this.fcjyskxxlist = fcjyskxxlist;
    }
}