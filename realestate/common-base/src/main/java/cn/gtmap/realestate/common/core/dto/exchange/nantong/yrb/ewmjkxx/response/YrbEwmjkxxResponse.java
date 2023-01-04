package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.ewmjkxx.response;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/11
 * @description 1.13.    房产交易缴款结果查询【A019】
 */
public class YrbEwmjkxxResponse {


    /**
     * jkzt : 缴款状态  0（缴款成功） 1（未缴款）
     * jkfhxx : 缴款返回信息
     */

    private String jkzt;
    private String jkfhxx;

    public String getJkzt() {
        return jkzt;
    }

    public void setJkzt(String jkzt) {
        this.jkzt = jkzt;
    }

    public String getJkfhxx() {
        return jkfhxx;
    }

    public void setJkfhxx(String jkfhxx) {
        this.jkfhxx = jkfhxx;
    }

    @Override
    public String toString() {
        return "YrbEwmjkxxResponse{" +
                "jkzt='" + jkzt + '\'' +
                ", jkfhxx='" + jkfhxx + '\'' +
                '}';
    }
}
