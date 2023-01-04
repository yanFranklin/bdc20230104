package cn.gtmap.realestate.init.core.thread;


import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcCxYwxxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;


import java.util.List;
import java.util.Map;

/**
 * 初始化的多线程服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/3/11.
 * @description
 */
public class InitCxdjThread extends CommonThread{

    /**
     * 存储初始化后的项目ID对应的初始化数据
     */
    private Map<String, InitServiceDTO> serviceDataMap;

    /**
     * 存储初始化参数
     */
    private InitServiceQO initServiceQO;

    /**
     * 撤销业务服务
     */
    private BdcCxYwxxService bdcCxYwxxService;

    /**
     * 构造函数
     * @param serviceDataMap
     * @param initServiceQO
     */
    public InitCxdjThread(Map<String, InitServiceDTO> serviceDataMap, InitServiceQO initServiceQO, BdcCxYwxxService bdcCxYwxxService){
        this.serviceDataMap=serviceDataMap;
        this.initServiceQO=initServiceQO;
        this.bdcCxYwxxService=bdcCxYwxxService;
    }

    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
        if(initServiceQO != null){
            List<InitServiceDTO> initServiceDTOList =bdcCxYwxxService.initCxYwxx(initServiceQO);
            if(CollectionUtils.isNotEmpty(initServiceDTOList)) {
                for(InitServiceDTO initServiceDTO:initServiceDTOList) {
                    if(initServiceDTO.getBdcXm() != null &&StringUtils.isNotBlank(initServiceDTO.getBdcXm().getXmid())) {
                        serviceDataMap.put(initServiceDTO.getBdcXm().getXmid(), initServiceDTO);
                    }
                }
            }
        }
    }
}
