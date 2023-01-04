package cn.gtmap.realestate.common.core.dto.inquiry.nantong;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2021/01/26
 * @description 打印参数缓存到Redis后返回信息
 */
public class BdcPrintParamQO {
    /**
     * 缓存参数key
     */
    private String redisKey;

    /**
     * 查询编号
     */
    private String cxbh;


    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public String getCxbh() {
        return cxbh;
    }

    public void setCxbh(String cxbh) {
        this.cxbh = cxbh;
    }
}
