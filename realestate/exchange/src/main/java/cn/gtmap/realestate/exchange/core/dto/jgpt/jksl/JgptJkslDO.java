package cn.gtmap.realestate.exchange.core.dto.jgpt.jksl;

public class JgptJkslDO {

    /**
     * jkmc : 推送税务信息
     * jkdz :  exchange/api/v1/bdc/query/commit/result
     * jkdycs : 50
     */

    private String jkmc;
    private String jkdz;
    private Integer jkdycs;

    public String getJkmc() {
        return jkmc;
    }

    public void setJkmc(String jkmc) {
        this.jkmc = jkmc;
    }

    public String getJkdz() {
        return jkdz;
    }

    public void setJkdz(String jkdz) {
        this.jkdz = jkdz;
    }

    public Integer getJkdycs() {
        return jkdycs;
    }

    public void setJkdycs(Integer jkdycs) {
        this.jkdycs = jkdycs;
    }

    @Override
    public String toString() {
        return "JgptJkslDO{" +
                "jkmc='" + jkmc + '\'' +
                ", jkdz='" + jkdz + '\'' +
                ", jkdycs=" + jkdycs +
                '}';
    }
}
