package cn.gtmap.realestate.common.core.dto.exchange.ykq.jsswzt.request;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.tsztjs.YrbSwTsztjs;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/11/28
 * @description 一卡清接受税务状态dataDTO
 */
@ApiModel(value = "YkqSwztDataRequestDTO", description = "一卡清接受税务状态dataDTO")
public class YkqSwztDataRequestDTO {

    private List<YrbSwTsztjs> sbzttsxxlist;

    public List<YrbSwTsztjs> getSbzttsxxlist() {
        return sbzttsxxlist;
    }

    public void setSbzttsxxlist(List<YrbSwTsztjs> sbzttsxxlist) {
        this.sbzttsxxlist = sbzttsxxlist;
    }

    @Override
    public String toString() {
        return "YkqSwztDataRequestDTO{" +
                "sbzttsxxlist=" + sbzttsxxlist +
                '}';
    }
}
