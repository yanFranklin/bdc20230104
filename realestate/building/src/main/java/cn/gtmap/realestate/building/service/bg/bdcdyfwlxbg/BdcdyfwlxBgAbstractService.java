package cn.gtmap.realestate.building.service.bg.bdcdyfwlxbg;

import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.building.service.bg.FwBgService;
import cn.gtmap.realestate.common.core.dto.building.FwlxBgRequestDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-02-26
 * @description
 */
public abstract class BdcdyfwlxBgAbstractService implements BdcdyfwlxBgService {

    @Autowired
    protected FwXmxxService fwXmxxService;

    @Autowired
    protected FwLjzService fwLjzService;

    @Autowired
    protected FwHsService fwHsService;

    @Autowired
    protected BdcdyhService bdcdyhService;

    @Resource(name = "ljzJbxxBgServiceImpl")
    protected FwBgService ljzJbxxBgServiceImpl;

    @Autowired
    protected SSjHsbgjlbService sSjHsbgjlbService;

    /**
     * @param requestDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 执行变更
     */
    @Override
    @Transactional
    public void executeBg(FwlxBgRequestDTO requestDTO) {
        // 判断是否保存变更记录表
        boolean saveHsbgjlb = StringUtils.isNotBlank(requestDTO.getBgbh());
        if(saveHsbgjlb){
            bgWithBgjl(requestDTO);
        }else{
            bg(requestDTO);
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return void
     * @description 记录变更记录表的变更
     */
    public abstract void bgWithBgjl(FwlxBgRequestDTO requestDTO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return void
     * @description 不记录变更记录表的变更
     */
    public abstract void bg(FwlxBgRequestDTO requestDTO);

}
