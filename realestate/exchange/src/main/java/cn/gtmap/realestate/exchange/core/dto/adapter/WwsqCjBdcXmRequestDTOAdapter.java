package cn.gtmap.realestate.exchange.core.dto.adapter;

import cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmRequestDTO;
import cn.gtmap.realestate.exchange.core.component.ExchangeFactory;
import cn.gtmap.realestate.exchange.core.dto.wwsq.dj.request.GyjsydscdjDto;
/**
 * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description //国土建设用地适配器
 * @Date 2022/5/17 11:10
 **/
public class WwsqCjBdcXmRequestDTOAdapter implements ParamTarget {
    /**
     * 源dto
     */
    private GyjsydscdjDto gyjsydscdjDto;

    public WwsqCjBdcXmRequestDTOAdapter(GyjsydscdjDto gyjsydscdjDto) {
        this.gyjsydscdjDto = gyjsydscdjDto;
    }

    /**
     * 构建外网申请 创建项目 请求实体（目标dto）
     * @return
     */
    @Override
    public WwsqCjBdcXmRequestDTO buildParam() {
        WwsqCjBdcXmRequestDTO dto = new WwsqCjBdcXmRequestDTO();
        dto.setGzyz(true);
        dto.setSlRoleCode(ExchangeFactory.getRole().getSlRoleCode("wwsqsl"));
        dto.setYhxzqdm(gyjsydscdjDto.getData().get(0).getDwdm());
        // 设置受理信息实体
        dto.setBdcSlxxDTO(gyjsydscdjDto.buildBdcSlxxDTO());
        dto.setSfss(false);
        return dto;
    }
}