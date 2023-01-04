package cn.gtmap.realestate.common.core.vo.inquiry;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description  常州生成查询证明单PDF接口返回数据实体
 */
public class BdcCxzmdPdfDataVO {
    /**
     * 标识ID（原样返回大数据局接口中的ownerid，为了标识具体哪套房产）
     */
    private String ownerid;

    /**
     * 证明类型：
     *  zmd      不动产登记簿查询证明单
     *  bdcqz    不动产登记簿查询证明明细单（不动产权证）
     *  fcztdz   不动产登记簿查询证明明细单（房产证和土地证）
     */
    private String type;

    /**
     * 对应PDF文件base64数据
     */
    private String data;

    /**
     * 姓名
     */
    private String xm;

    /**
     * 坐落
     */
    private String zl;

    /**
     * 共有人
     */
    private String gyr;


    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getGyr() {
        return gyr;
    }

    public void setGyr(String gyr) {
        this.gyr = gyr;
    }

    public String getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid;
    }

    public BdcCxzmdPdfDataVO() {

    }

    public BdcCxzmdPdfDataVO(String type, String data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BdcCxzmdPdfDataVO{" +
                "ownerid='" + ownerid + '\'' +
                ", type='" + type + '\'' +
                ", data='" + data + '\'' +
                ", xm='" + xm + '\'' +
                ", zl='" + zl + '\'' +
                ", gyr='" + gyr + '\'' +
                '}';
    }
}
