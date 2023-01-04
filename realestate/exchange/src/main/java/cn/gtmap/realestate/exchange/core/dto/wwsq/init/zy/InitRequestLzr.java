package cn.gtmap.realestate.exchange.core.dto.wwsq.init.zy;

import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">caolu</a>
 * @version 1.0  2019-08-11
 * @description 领证人实体
 */
@IgnoreCast(ignoreNum = 3)
public class InitRequestLzr {

    // 领证人姓名
    private String lzrxm;
    // 领证人身份证件种类
    private String lzrsfzjzl;
    // 领证人证件号
    private String lzrzjh;
    // 领证人联系电话
    private String lzrlxdh;

    public String getLzrxm() {
        return lzrxm;
    }

    public void setLzrxm(String lzrxm) {
        this.lzrxm = lzrxm;
    }

    public String getLzrsfzjzl() {
        return lzrsfzjzl;
    }

    public void setLzrsfzjzl(String lzrsfzjzl) {
        this.lzrsfzjzl = lzrsfzjzl;
    }

    public String getLzrzjh() {
        return lzrzjh;
    }

    public void setLzrzjh(String lzrzjh) {
        this.lzrzjh = lzrzjh;
    }

    public String getLzrlxdh() {
        return lzrlxdh;
    }

    public void setLzrlxdh(String lzrlxdh) {
        this.lzrlxdh = lzrlxdh;
    }
}
