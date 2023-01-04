package cn.gtmap.realestate.register.core.thread;

import cn.gtmap.realestate.common.core.domain.BdcLhxxCzrzDO;
import cn.gtmap.realestate.common.core.domain.building.FwJsydzrzxxDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.register.service.BdcLhxxCzrzService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/1/5
 * @description 记录量化信息操作日志线程类
 */
public class BdcLhxxCzrzThread extends CommonThread {

    /**
     * 量化类型
     */
    private Integer lhlx;
    /**
     * 量化操作
     */
    private Integer lhcz;
    /**
     * 工作流实例ID
     */
    private String gzlslid;

    /**
     * 需要操作建设用地自然幢信息
     */
    private List<FwJsydzrzxxDO> fwJsydzrzxxDOList;

    /**
     * 量化信息操作日志接口服务
     */
    private BdcLhxxCzrzService bdcLhxxCzrzService;
    /**
     * 不动产项目接口服务
     */
    private BdcXmFeignService bdcXmFeignService;

    public BdcLhxxCzrzThread(Integer lhlx, Integer lhcz, String gzlslid, List<FwJsydzrzxxDO> fwJsydzrzxxDOList,
                             BdcLhxxCzrzService bdcLhxxCzrzService, BdcXmFeignService bdcXmFeignService){
        this.lhlx = lhlx;
        this.lhcz = lhcz;
        this.gzlslid = gzlslid;
        this.fwJsydzrzxxDOList = fwJsydzrzxxDOList;
        this.bdcLhxxCzrzService = bdcLhxxCzrzService;
        this.bdcXmFeignService = bdcXmFeignService;
    }

    @Override
    public void execute() throws Exception {
        // 添加量化信息操作日志
        if(CollectionUtils.isNotEmpty(fwJsydzrzxxDOList)){
            List<BdcLhxxCzrzDO> bdcLhxxCzrzDOList = new ArrayList<>(fwJsydzrzxxDOList.size());
            String gzldyid = this.queryGzldyid(gzlslid);
            for(FwJsydzrzxxDO fwJsydzrzxxDO : fwJsydzrzxxDOList){
                BdcLhxxCzrzDO bdcLhxxCzrzDO = new BdcLhxxCzrzDO();
                BeanUtils.copyProperties(fwJsydzrzxxDO,  bdcLhxxCzrzDO);
                bdcLhxxCzrzDO.setCzlx(lhcz);
                bdcLhxxCzrzDO.setLhlx(lhlx);
                bdcLhxxCzrzDO.setGzldyid(gzldyid);
                bdcLhxxCzrzDO.setGzlslid(gzlslid);
                bdcLhxxCzrzDO.setZh(fwJsydzrzxxDO.getZrzh());
                bdcLhxxCzrzDO.setLhzt(this.getLhzt(fwJsydzrzxxDO));
                bdcLhxxCzrzDO.setCzsj(new Date());
                bdcLhxxCzrzDOList.add(bdcLhxxCzrzDO);
            }
            this.bdcLhxxCzrzService.plAddLhxxCzrz(bdcLhxxCzrzDOList);
        }
    }

    /**
     * 根据工作流实例ID查询工作流定义ID
     */
    private String queryGzldyid(String gzlslid){
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
            return bdcXmDTOList.get(0).getGzldyid();
        }
        return "";
    }

    /**
     * 获取自然幢量化状态
     */
    private Integer getLhzt(FwJsydzrzxxDO fwJsydzrzxxDO){
        Integer lhzt = null;
        switch(lhlx){
            case 1:
                lhzt = fwJsydzrzxxDO.getLhsdqlzt();
                break;
            case 2:
                lhzt = fwJsydzrzxxDO.getLhdycs();
                break;
            case 3:
                lhzt = fwJsydzrzxxDO.getLhcfcs();
                break;
            default:
        }
        return lhzt;
    }
}
