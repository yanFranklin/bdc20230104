package cn.gtmap.realestate.portal.ui.core.dto;

import java.util.List;
import java.util.Map;

/**
 * 消息中心信息
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/2/27.
 * @description
 */
public class BdcMessageDTO {
    /*
   *  待办数量
   * */
    private Integer wbSize;

    /*
  *  待办数量
  * */
    private Integer ybSize;

    /*
   *  未读
   * */
    private List<MessageBackDto> wdList;
    /*
     *  已读
     * */
    private List<MessageBackDto> ydList;

    /*
     *  需确认未读信息
     * */
    private List<MessageBackDto> qrList;

    private Map<String, List<MessageBackDto>> qrxxMap;

    public List<MessageBackDto> getWdList() {
        return wdList;
    }

    public void setWdList(List<MessageBackDto> wdList) {
        this.wdList = wdList;
    }

    public List<MessageBackDto> getYdList() {
        return ydList;
    }

    public void setYdList(List<MessageBackDto> ydList) {
        this.ydList = ydList;
    }

    public Integer getWbSize() {
        return wbSize;
    }

    public void setWbSize(Integer wbSize) {
        this.wbSize = wbSize;
    }

    public Integer getYbSize() {
        return ybSize;
    }

    public void setYbSize(Integer ybSize) {
        this.ybSize = ybSize;
    }

    public List<MessageBackDto> getQrList() {
        return qrList;
    }

    public void setQrList(List<MessageBackDto> qrList) {
        this.qrList = qrList;
    }

    public Map<String, List<MessageBackDto>> getQrxxMap() {
        return qrxxMap;
    }

    public void setQrxxMap(Map<String, List<MessageBackDto>> qrxxMap) {
        this.qrxxMap = qrxxMap;
    }
}
