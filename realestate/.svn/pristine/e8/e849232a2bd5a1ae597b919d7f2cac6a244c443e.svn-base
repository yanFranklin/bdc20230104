package cn.gtmap.realestate.init.service.initJw.impl;

import cn.gtmap.gtc.workflow.clients.manage.ProcessInsCustomExtendClient;
import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcCzrzService;
import cn.gtmap.realestate.init.service.initJw.InitBdcJwService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/3/26
 * @description 第三方退件信息回写
 */
@Service("bdcHxDsftjxxServiceImpl")
public class InitBdcHxDsftjxxServiceImpl extends InitBdcJwService {

    @Autowired
    private ProcessInsCustomExtendClient processInsCustomExtendClient;

    @Autowired
    private BdcCzrzService bdcCzrzService;

    @Override
    public void initJw(InitResultDTO initResultDTO, List<InitServiceQO> listQO) throws Exception {
        if(CollectionUtils.isNotEmpty(initResultDTO.getBdcXmList()) &&StringUtils.isNoneBlank(initResultDTO.getBdcXmList().get(0).getSpxtywh(),initResultDTO.getBdcXmList().get(0).getGzlslid())){
            String gzlslid =initResultDTO.getBdcXmList().get(0).getGzlslid();
            //根据审批系统业务号查询日志表最新一次记录
            List<BdcCzrzDO> czrzDOList =bdcCzrzService.listBdcCzrzBySpxtywh(initResultDTO.getBdcXmList().get(0).getSpxtywh(),"czsj desc");
            if(CollectionUtils.isNotEmpty(czrzDOList)){
                Map<String, Object> processInsExtendDto = new HashMap<>();
                processInsExtendDto.put("PROC_INS_ID", gzlslid);
                processInsExtendDto.put("SCTJR", czrzDOList.get(0).getCzr());
                processInsExtendDto.put("SCTJYY", czrzDOList.get(0).getCzyy());
                //先获取
                List<Map<String, Object>> list=processInsCustomExtendClient.getProcessInsCustomExtend(gzlslid);
                if(CollectionUtils.isNotEmpty(list)){
                    processInsCustomExtendClient.updateProcessInsCustomExtend(gzlslid,processInsExtendDto);
                }else {
                    processInsCustomExtendClient.addProcessInsCustomExtend(processInsExtendDto);
                }
            }
        }
    }

    /**
     * 获取服务版本
     *
     * @return
     */
    @Override
    public List<String> getVersion() {
        List<String> versionList =new ArrayList<>();
        versionList.add(CommonConstantUtils.SYSTEM_VERSION_YC);
        return versionList;
    }
}
