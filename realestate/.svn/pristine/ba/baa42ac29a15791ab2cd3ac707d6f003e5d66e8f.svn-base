package cn.gtmap.realestate.natural.resource.service;


import cn.gtmap.realestate.common.core.domain.naturalresource.JbzkDO;
import cn.gtmap.realestate.common.core.dto.naturalresource.JbzkDTO;
import cn.gtmap.realestate.common.core.qo.naturalresource.ZrzyJbzkQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 为自然资源子系统提供分页查询自然资源基本状况服务
 *
 * @description:
 * @author: 王英浩
 * @create: 2021-10-25
 **/

public interface NaturalResourceJbzkService {

    /**
     * @param pageable
     * @param ysdm
     * @param zl
     * @return
     */
    public Page<JbzkDO> listJbzkByPage(Pageable pageable,
                                       ZrzyJbzkQO zrzyJbzkQO);


    /**
     * @param zrzydjdyh
     * @return
     */
    public JbzkDTO queryJbzkByZrzydjdyh(String zrzydjdyh);
}
