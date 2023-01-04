package cn.gtmap.realestate.natural.core.service;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyGgmbDo;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXmDO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyXmDTO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyDbxxQO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyXmListQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description 自然资源项目
 */
public interface ZrzyXmService {

    /**
      * @param gzlslid 工作流实例ID
      * @return 自然资源项目信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据工作流实例ID获取自然资源项目信息
      */
    ZrzyXmDO queryZrzyXmByGzlslid(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 自然资源项目信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取自然资源项目信息
     */
    List<ZrzyXmDO> queryAllZrzyXmByGzlslid(String gzlslid);

    /**
     * @param xmid 项目ID
     * @return 自然资源项目信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID获取自然资源项目信息
     */
    ZrzyXmDO queryZrzyXmByXmid(String xmid);

    /**
     * 选择产权证
     * @param pageable
     * @param zrzyXmListQO
     * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
     * @return
     */
    Page<ZrzyXmDTO> zrzyXmList(Pageable pageable, ZrzyXmListQO zrzyXmListQO);

    /**
     * 更新登簿信息
     * @param zrzyDbxxQO
     */
    public void updateZrzyXmDbxx(ZrzyDbxxQO zrzyDbxxQO);

    /**
     * 更新原项目登簿信息
     * @param zrzyDbxxQo
     */
    public void updateYxmDbxx(ZrzyDbxxQO zrzyDbxxQo);


    /**
     * @param xmid 工作流实例ID
     * @return 自然资源项目信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取模板信息
     */
    List<ZrzyGgmbDo> listGgmbByXmid(String xmid);

    void addGgmb(String xmid,String mbxxid,String mbnr);

    void updateGgmbByXmid(String xmid,String mbnr);
}
