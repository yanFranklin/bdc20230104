package cn.gtmap.realestate.exchange.core.dto.nantong.sq.response.qi;

import cn.gtmap.realestate.exchange.core.dto.nantong.sq.response.AbstractResponse;

/**
 * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description //南通大市，过户返回参数
 * @Date 2022/5/26 9:34
 **/
public class QiGhHmDataDto extends AbstractResponse {

    /**
     * code 为 1时有返回以下为（data）数据内容
     */
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
