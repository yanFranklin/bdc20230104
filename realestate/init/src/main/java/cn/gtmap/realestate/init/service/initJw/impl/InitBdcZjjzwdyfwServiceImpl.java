package cn.gtmap.realestate.init.service.initJw.impl;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcQllxService;
import cn.gtmap.realestate.init.service.initJw.InitBdcJwService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 在建建筑抵押范围处理
 *
 * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
 * @version 下午2:28 2021/3/3
 */
@Service
public class InitBdcZjjzwdyfwServiceImpl extends InitBdcJwService {

    /**
     * 在建建筑物抵押权登记（包含首次、变更、转移）
     */
    @Value("#{'${yctscl.zjjzwdya.gzldyids:}'.split(',')}")
    private List<String> zjjzwdyas;

    @Autowired
    private BdcQllxService bdcQllxService;

    /**
     * 初始化入库数据之后的服务
     * 【在建建筑物抵押范围】
     * 土地证坐落+所有不动产单元的房间号（顿号分隔）+房屋及对应的建设用地使用权；
     *
     * @param initResultDTO 初始化后的数据
     * @param listQO
     * @throws Exception
     */
    @Override
    public void initJw(InitResultDTO initResultDTO, List<InitServiceQO> listQO) throws Exception {
        if (CollectionUtils.isNotEmpty(zjjzwdyas) && CollectionUtils.isNotEmpty(listQO) && zjjzwdyas.contains(listQO.get(0).getBdcXm().getGzldyid())) {
            List<BdcXmDO> bdcXmList = initResultDTO.getBdcXmList();
            List<BdcQl> bdcQlList = initResultDTO.getBdcQlList();
            // 增量初始化
            if (listQO.get(0) != null && listQO.get(0).getBdcCshFwkgSl() != null && CommonConstantUtils.SF_S_DM.equals(listQO.get(0).getBdcCshFwkgSl().getSfzlcsh())) {
                bdcQlList = bdcQllxService.listQlxxDO(bdcXmList.get(0).getSlbh(), bdcXmList.get(0).getGzlslid());
            }
            bdcQllxService.updateZjjwdyfw(bdcQlList, listQO.get(0).getBdcXm().getGzldyid(),listQO.get(0).getQjgldm());
        }
    }

    /**
     * 获取服务版本
     *
     * @return
     */
    @Override
    public List<String> getVersion() {
        return Arrays.asList(CommonConstantUtils.SYSTEM_VERSION_YC);
    }
}
