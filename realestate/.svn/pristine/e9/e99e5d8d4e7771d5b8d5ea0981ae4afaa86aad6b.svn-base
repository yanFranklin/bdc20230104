package cn.gtmap.realestate.common.config.accept;


import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/8/24
 * @description 去除加“等”字样
 */
@Component
public class QcjdConfig {

    /**
     * 定义哪些流程，去除"等"字样
     */
    @Value("#{'${qcjd.gzldyids:}'.split(',')}")
    private List<String> qcjdGzldyids;

    public List<String> getQcjdGzldyids() {
        return qcjdGzldyids;
    }

    public void setQcjdGzldyids(List<String> qcjdGzldyids) {
        this.qcjdGzldyids = qcjdGzldyids;
    }

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcZsFeignService bdcZsFeignService;

    /**
     * 判断当前流程坐落、不动产单元号 是否去除加 “等”
     * @param gzldyid 工作流定义ID
     * @return true : 去除加“等”; false: 不去除;
     */
    public boolean qcjd(String gzldyid){
        if(CollectionUtils.isNotEmpty(qcjdGzldyids) && StringUtils.isNotBlank(gzldyid)){
            if(qcjdGzldyids.contains(gzldyid)){
                return true;
            }
        }
        return false;
    }

    /**
     * 根据不动产项目判断当前流程是否需要去除加“等”
     * @param bdcXmDO 不动产项目信息
     * @return true : 去除加“等”; false: 不去除;
     */
    public boolean qcjdByBdcXm(BdcXmDO bdcXmDO){
        if(CollectionUtils.isNotEmpty(qcjdGzldyids) && Objects.nonNull(bdcXmDO) && StringUtils.isNotBlank(bdcXmDO.getGzldyid())){
            if(qcjdGzldyids.contains(bdcXmDO.getGzldyid())){
                return true;
            }
        }
        return false;
    }

    /**
     * 根据xmid判断当前流程坐落、不动产单元号，是否去除加“等”
     * @param xmid 项目id
     * @return true : 去除加“等”; false: 不去除;
     */
    public boolean qcjdByXmid(String xmid){
        if(StringUtils.isNotBlank(xmid) && CollectionUtils.isNotEmpty(qcjdGzldyids)){
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)
                    && StringUtils.isNotBlank(bdcXmDOList.get(0).getGzldyid())){
                if(qcjdGzldyids.contains(bdcXmDOList.get(0).getGzldyid())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 根据zsid判断当前流程坐落、不动产单元号，是否去除加“等”
     * @param zsid  证书ID
     * @return true : 去除加“等”; false: 不去除;
     */
    public boolean qcjdByZsid(String zsid){
        if(StringUtils.isNotBlank(zsid) && CollectionUtils.isNotEmpty(qcjdGzldyids)){
            List<BdcXmDO> bdcXmDOList = this.bdcZsFeignService.queryZsXmByZsid(zsid);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)
                    && StringUtils.isNotBlank(bdcXmDOList.get(0).getGzldyid())){
                if(qcjdGzldyids.contains(bdcXmDOList.get(0).getGzldyid())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取不动产项目中非土地证的坐落地址
     * <p>获取登记项目中的非土地的坐落信息，没有非土地的默认获取第一条项目的坐落</p>
     * @param bdcXmDOList 登记项目集合
     * @return 非土地证的坐落
     */
    public String getNoTdzZlByBdcXm(List<BdcXmDO> bdcXmDOList){
        String noTdzZl = bdcXmDOList.get(0).getZl();
        for(BdcXmDO bdcXmDO : bdcXmDOList){
            if(!Objects.equals(Integer.parseInt(CommonConstantUtils.BDCLX_TD_DM), bdcXmDO.getBdclx())){
                noTdzZl = bdcXmDO.getZl();
                break;
            }
        }
        return noTdzZl;
    }

    /**
     * 获取不动产项目中非土地证的不动产单元号
     * <p>获取登记项目中的非土地的不动产单元号，没有非土地的默认获取第一条项目的不动产单元号</p>
     * @param bdcXmDOList 登记项目集合
     * @return 非土地证的坐落
     */
    public String getNoTdzBdcdyhByBdcXm(List<BdcXmDO> bdcXmDOList){
        String noTdzBdcdyh = bdcXmDOList.get(0).getBdcdyh();
        for(BdcXmDO bdcXmDO : bdcXmDOList){
            if(!Objects.equals(Integer.parseInt(CommonConstantUtils.BDCLX_TD_DM), bdcXmDO.getBdclx())){
                noTdzBdcdyh = bdcXmDO.getBdcdyh();
                break;
            }
        }
        return noTdzBdcdyh;
    }

    /**
     * 获取不动产项目中非土地证的坐落与不动产单元号
     * <p>获取登记项目中的非土地的坐落与不动产单元号，没有非土地的默认获取第一条项目的坐落与不动产单元号</p>
     * @param bdcXmDOList 登记项目集合
     * @return Pair<bdcdyh, zl>
     */
    public Pair<String, String> getNoTdzZlAndBdcdyhByBdcXm(List<BdcXmDO> bdcXmDOList){
        String noTdzBdcdyh = bdcXmDOList.get(0).getBdcdyh();
        String noTdzZl = bdcXmDOList.get(0).getZl();
        for(BdcXmDO bdcXmDO : bdcXmDOList){
            if(!Objects.equals(Integer.parseInt(CommonConstantUtils.BDCLX_TD_DM), bdcXmDO.getBdclx())){
                noTdzBdcdyh = bdcXmDO.getBdcdyh();
                noTdzZl = bdcXmDO.getZl();
                break;
            }
        }
        return Pair.of(noTdzBdcdyh, noTdzZl);
    }


    /**
     * 获取不动产受理项目中非土地证的坐落地址
     * <p>获取受理项目中的非土地的坐落信息，没有非土地的默认获取第一条项目的坐落</p>
     * @param bdcSlXmDOList 受理项目集合
     * @return 非土地证的坐落
     */
    public String getNoTdzZlByBdcSlXm(List<BdcSlXmDO> bdcSlXmDOList){
        String noTdzZl = bdcSlXmDOList.get(0).getZl();
        for(BdcSlXmDO bdcSlXmDO : bdcSlXmDOList){
            if(!Objects.equals(Integer.parseInt(CommonConstantUtils.BDCLX_TD_DM), bdcSlXmDO.getBdclx())){
                noTdzZl = bdcSlXmDO.getZl();
                break;
            }
        }
        return noTdzZl;
    }

    /**
     * 获取不动产受理项目中非土地证的不动产单元号
     * <p>获取受理项目中的非土地的不动产单元号，没有非土地的默认获取第一条项目的不动产单元号</p>
     * @param bdcSlXmDOList 受理项目集合
     * @return 非土地证的不动产单元号
     */
    public String getNoTdzBdcdyhByBdcSlXm(List<BdcSlXmDO> bdcSlXmDOList){
        String noTdzBdcdyh = bdcSlXmDOList.get(0).getBdcdyh();
        for(BdcSlXmDO bdcSlXmDO : bdcSlXmDOList){
            if(!Objects.equals(Integer.parseInt(CommonConstantUtils.BDCLX_TD_DM), bdcSlXmDO.getBdclx())){
                noTdzBdcdyh = bdcSlXmDO.getBdcdyh();
                break;
            }
        }
        return noTdzBdcdyh;
    }

    /**
     * 获取不动产受理项目中非土地证的坐落与不动产单元号
     * <p>获取受理项目中的非土地的坐落与不动产单元号，没有非土地的默认获取第一条项目的坐落与不动产单元号</p>
     * @param bdcSlXmDOList 受理项目集合
     * @return Pair<bdcdyh, zl>
     */
    public Pair<String, String> getNoTdzZlAndBdcdyhBySlXm(List<BdcSlXmDO> bdcSlXmDOList){
        String noTdzBdcdyh = bdcSlXmDOList.get(0).getBdcdyh();
        String noTdzZl = bdcSlXmDOList.get(0).getZl();
        for(BdcSlXmDO bdcSlXmDO : bdcSlXmDOList){
            if(!Objects.equals(Integer.parseInt(CommonConstantUtils.BDCLX_TD_DM), bdcSlXmDO.getBdclx())){
                noTdzBdcdyh = bdcSlXmDO.getBdcdyh();
                noTdzZl = bdcSlXmDO.getZl();
                break;
            }
        }
        return Pair.of(noTdzBdcdyh, noTdzZl);
    }
}
