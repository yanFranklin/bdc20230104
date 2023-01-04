package cn.gtmap.realestate.exchange.core.service;

import cn.gtmap.realestate.exchange.core.dto.RegisterLogDTO;
import cn.gtmap.realestate.exchange.core.qo.RegisterLogQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RegisterLogService {

    /**
     * @param pageable
     * @description 分页查询登簿上报日志
     */
    Page<RegisterLogDTO> listRegisterLogByPages(Pageable pageable, RegisterLogQO registerLogQO);

    /**
     * 查询登簿上报日志
     */
    List<RegisterLogDTO> listRegisterLog(RegisterLogQO registerLogQO);
}
