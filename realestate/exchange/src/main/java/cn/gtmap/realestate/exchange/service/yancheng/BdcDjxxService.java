package cn.gtmap.realestate.exchange.service.yancheng;

import cn.gtmap.realestate.exchange.core.dto.yancheng.bdcdj.BdcDjxxReqDto;
import cn.gtmap.realestate.exchange.core.dto.yancheng.bdcdj.BdcDjxxResDto;

public interface BdcDjxxService {
    BdcDjxxResDto listDjxx(BdcDjxxReqDto req);
}
