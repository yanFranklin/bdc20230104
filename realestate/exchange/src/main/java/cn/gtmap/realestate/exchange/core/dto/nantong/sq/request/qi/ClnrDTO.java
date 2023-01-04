package cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.qi;

/**
 * @Author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @Description 南通水气过户请求参数
 * @Date 2022/6/14
 **/
public class ClnrDTO {
    /**
     * 材料名称-文件名称
     */
    private String fjmc;
    /**
     * 材料内容-文件base64
     */
    private String fjnr;

    public String getFjmc() {
        return fjmc;
    }

    public void setFjmc(String fjmc) {
        this.fjmc = fjmc;
    }

    public String getFjnr() {
        return fjnr;
    }

    public void setFjnr(String fjnr) {
        this.fjnr = fjnr;
    }

    @Override
    public String toString() {
        return "ClnrDTO{" +
                "fjmc='" + fjmc + '\'' +
                ", fjnr='" + fjnr + '\'' +
                '}';
    }
}
