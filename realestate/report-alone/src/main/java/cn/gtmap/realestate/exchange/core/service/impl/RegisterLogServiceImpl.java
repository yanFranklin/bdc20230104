package cn.gtmap.realestate.exchange.core.service.impl;

import cn.gtmap.realestate.exchange.core.dto.RegisterLogDTO;
import cn.gtmap.realestate.exchange.core.qo.RegisterLogQO;
import cn.gtmap.realestate.exchange.core.service.RegisterLogService;
import cn.gtmap.realestate.exchange.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.exchange.core.support.mybatis.page.repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterLogServiceImpl implements RegisterLogService {

    @Autowired
    private Repo repository;

    @Override
    public Page<RegisterLogDTO> listRegisterLogByPages(Pageable pageable, RegisterLogQO registerLogQO) {
        return repository.selectPaging("listRegisterLogByPage", registerLogQO, pageable);
    }

    @Override
    public List<RegisterLogDTO> listRegisterLog(RegisterLogQO registerLogQO) {
        return repository.selectList("listRegisterLogByPage", registerLogQO);
    }
}
