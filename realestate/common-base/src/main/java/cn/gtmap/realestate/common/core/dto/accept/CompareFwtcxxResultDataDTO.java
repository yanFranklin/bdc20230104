package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwtcxxDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxDTO;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2021-01-28
 * @description 房屋套次信息比对结果
 */
public class CompareFwtcxxResultDataDTO {
    private String code;

    private String msg;

    private CompareResultSqrDTO sqrDTO;

    private List<BdcSlFwtcxxDO> bdcSlFwtcxxDOS;
    /**
     * 根据用途过滤后的房屋套次信息
     */
    private List<BdcZfxxDTO> filterYtFwtcxx;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CompareResultSqrDTO getSqrDTO() {
        return sqrDTO;
    }

    public void setSqrDTO(CompareResultSqrDTO sqrDTO) {
        this.sqrDTO = sqrDTO;
    }

    public List<BdcSlFwtcxxDO> getBdcSlFwtcxxDOS() {
        return bdcSlFwtcxxDOS;
    }

    public void setBdcSlFwtcxxDOS(List<BdcSlFwtcxxDO> bdcSlFwtcxxDOS) {
        this.bdcSlFwtcxxDOS = bdcSlFwtcxxDOS;
    }

    public List<BdcZfxxDTO> getFilterYtFwtcxx() {
        return filterYtFwtcxx;
    }

    public void setFilterYtFwtcxx(List<BdcZfxxDTO> filterYtFwtcxx) {
        this.filterYtFwtcxx = filterYtFwtcxx;
    }
}
