package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.shgx.request;

import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.hyxx.request.HyxxCxywcsRequestDTO;

import java.util.List;

/**
 * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
 * @version 1.0  2019-08-26
 * @description 社会关系信息查询请求实体
 */
public class ShgxRequestDTO {

    private Integer page;

    private Integer size;

    private List<ShgxCxywcsRequestDTO> cxywcs;

    public List<ShgxCxywcsRequestDTO> getCxywcs() {
        return cxywcs;
    }

    public void setCxywcs(List<ShgxCxywcsRequestDTO> cxywcs) {
        this.cxywcs = cxywcs;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}
