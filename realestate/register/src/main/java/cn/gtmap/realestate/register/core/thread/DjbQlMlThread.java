package cn.gtmap.realestate.register.core.thread;

import cn.gtmap.realestate.common.core.dto.register.BdcQlDjMlDTO;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDjbQlMlVO;
import cn.gtmap.realestate.register.service.BdcDjbxxService;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/6/5
 * @description
 */
public class DjbQlMlThread extends CommonThread {

    BdcDjbxxService bdcDjbxxService;
    /**
     * 入参
     */
    BdcQlDjMlDTO bdcQlDjMlDTO;
    Boolean onlyQlfm;
    /**
     * 返回参数
     */
    BdcDjbQlMlVO bdcDjbQlMlVO;

    public DjbQlMlThread(BdcQlDjMlDTO bdcQlDjMlDTO, BdcDjbxxService bdcDjbxxService, Boolean onlyQlfm) {
        this.bdcQlDjMlDTO = bdcQlDjMlDTO;
        this.bdcDjbxxService = bdcDjbxxService;
        this.onlyQlfm = onlyQlfm;
    }

    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
//        bdcDjbQlMlVO = new BdcDjbQlMlVO();
//        String bdcdyh = bdcQlDjMlDTO.getBdcdyh();
//        bdcDjbQlMlVO.setBdcdyh(bdcdyh);
//        bdcDjbQlMlVO.setBdcdyUrl(BdcDjbQlMlEnum.BdcDjbJbxxUrlEnum.BDC_DJB_BDCDYQLFM_URL.getName() + bdcdyh);
//        /**
//         * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
//         * @description 获取不动产单元的权利信息并组织结果
//         */
//        BdcDjbQlDTO bdcDjbQlDTO = bdcDjbxxService.queryBdcDjbQl(bdcdyh);
//        List<Map<String, String>> bdcQlList = new ArrayList();
//        bdcQlList = bdcDjbxxService.getBdcDjbQlVO(bdcQlList, bdcDjbQlDTO, bdcdyh);
//        bdcDjbQlMlVO.setBdcQlList(bdcQlList);

        String bdcdyh = bdcQlDjMlDTO.getBdcdyh();
        bdcDjbQlMlVO = bdcDjbxxService.generateDyhQlMl(bdcdyh, onlyQlfm);
    }

    public BdcDjbQlMlVO getDjbQlMl() {
        return bdcDjbQlMlVO;
    }
}
