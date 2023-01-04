package cn.gtmap.realestate.exchange.core.dto.wwsq.bdcdygzyz.response;

import cn.gtmap.realestate.exchange.core.dto.wwsq.bdcdygzyz.request.BdcdygzyzRequestBdcdyh;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-26
 * @description
 */
public class BdcdygzyzResponseDTO {

    private List<BdcdygzyzRequestBdcdyh> bdcdyhList;

    public List<BdcdygzyzRequestBdcdyh> getBdcdyhList() {
        return bdcdyhList;
    }

    public void setBdcdyhList(List<BdcdygzyzRequestBdcdyh> bdcdyhList) {
        this.bdcdyhList = bdcdyhList;
    }
}
