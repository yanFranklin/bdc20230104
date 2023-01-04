package cn.gtmap.realestate.natural.resource.service.impl;


import cn.gtmap.realestate.common.core.domain.naturalresource.JbzkDO;
import cn.gtmap.realestate.common.core.dto.naturalresource.JbzkDTO;
import cn.gtmap.realestate.common.core.qo.naturalresource.ZrzyJbzkQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repository;
import cn.gtmap.realestate.natural.resource.core.mapper.JbzkMapper;
import cn.gtmap.realestate.natural.resource.service.NaturalResourceJbzkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 为自然资源子系统提供分页查询自然资源基本状况服务
 *
 * @description:
 * @author: wangyinghao
 * @create: 2021-10-25
 **/
@Service
public class NaturalResourceJbzkServiceImpl implements NaturalResourceJbzkService {
    @Autowired
    JbzkMapper jbzkMapper;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private Repository repo;

    @Override
    public Page<JbzkDO> listJbzkByPage(Pageable pageable, ZrzyJbzkQO zrzyJbzkQO) {
        return repo.selectPaging("listJbzkByPage", zrzyJbzkQO, pageable);
    }

    @Override
    public JbzkDTO queryJbzkByZrzydjdyh(String zrzydjdyh) {
        return jbzkMapper.queryJbzkByZrzydjdyh(zrzydjdyh);
    }
}
