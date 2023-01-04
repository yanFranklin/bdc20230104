package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdFeignService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSdVO;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 2020/08/26,1.0
 * @description 受理锁定/冻结控制层
 */
@Controller
@RequestMapping("/slym/sd")
public class SlymSdController extends BaseController {

    @Autowired
    BdcSdFeignService bdcSdFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcXmFeignService bdcXmFeignService;

    /**
     * 通过工作流实例ID获取流程相关联的锁定/冻结信息
     * @param gzlslid 工作流实例ID
     * @return 不动产锁定信息VO
     */
    @ResponseBody
    @GetMapping("/getSdxx")
    public BdcSdVO getSdxx(@RequestParam(name="gzlslid") String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException(messageProvider.getMessage("未获取到工作流实例ID。"));
        }
        BdcSdVO bdcSdVO = new BdcSdVO();
        // 获取证书锁定信息
        List<BdcZssdDO> bdcZssdDOList = bdcSdFeignService.queryBdczsSdByGzlslid(gzlslid);
        if(CollectionUtils.isNotEmpty(bdcZssdDOList)){
            initZsSdxx(bdcZssdDOList);
            bdcSdVO.setZssdDOList(bdcZssdDOList);
        } else{
            List<BdcDysdDO> bdcDysdDOList = bdcSdFeignService.queryBdcDySdByGzlslid(gzlslid);
            if(CollectionUtils.isNotEmpty(bdcDysdDOList)){
                initDySdxx(bdcDysdDOList);
                bdcSdVO.setDysdDOList(bdcDysdDOList);
            }
        }
        // 获取不动产项目信息
        bdcSdVO.setBdcXmDO(this.getBdcXmxx(gzlslid));
        return bdcSdVO;
    }

    /**
     * 通过工作流实例ID获取解锁/解冻数据
     * <p>解锁数据通过工作流实例ID获取历史关系的原项目ID，在通过原项目ID获取对应的冻结数据</p>
     * @param gzlslid 工作流实例ID
     * @return 解锁/解冻数据
     */
    @ResponseBody
    @GetMapping("/getJdxx")
    public BdcSdVO getJdxx(@RequestParam(name="gzlslid") String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException(messageProvider.getMessage("未获取到工作流实例ID。"));
        }
        BdcSdVO bdcSdVO = new BdcSdVO();
        List<BdcZssdDO> bdcZssdDOList = this.bdcSdFeignService.queryYxmZssd(gzlslid, null);
        if(CollectionUtils.isNotEmpty(bdcZssdDOList)){
            initZsJdxx(bdcZssdDOList);
            bdcSdVO.setZssdDOList(bdcZssdDOList);
        }else{
            List<BdcDysdDO> bdcDysdDOList = this.bdcSdFeignService.queryYxmDysd(gzlslid,null);
            if(CollectionUtils.isNotEmpty(bdcDysdDOList)){
                initDyJdxx(bdcDysdDOList);
                bdcSdVO.setDysdDOList(bdcDysdDOList);
            }
        }
        bdcSdVO.setBdcXmDO(this.getBdcXmxx(gzlslid));
        return bdcSdVO;
    }
    /**
     * 获取不动产项目信息
     */
    private BdcXmDO getBdcXmxx(String gzlslid){
        BdcXmQO bdcXmQO = new BdcXmQO().withGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOList)){
            BdcXmDO bdcXmDO  = bdcXmDOList.get(0);
            if(StringUtils.isBlank(bdcXmDO.getSlr())){
                UserDto userDto = userManagerUtils.getCurrentUser();
                bdcXmDO.setSlr(userDto.getAlias());
                bdcXmDO.setSlrid(userDto.getId());
            }
            return bdcXmDO;
        }
        return null;
    }
    /**
     * 初始化锁定时间信息
     */
    private void initZsSdxx(List<BdcZssdDO> bdcZssdDOList){
        if(CollectionUtils.isNotEmpty(bdcZssdDOList)){
            for(BdcZssdDO bdcZssdDO:bdcZssdDOList){
                if(Objects.isNull(bdcZssdDO.getSdsj())){
                    bdcZssdDO.setSdsj(new Date());
                }
            }
        }
    }
    /**
     * 初始化锁定时间信息
     */
    private void initDySdxx(List<BdcDysdDO> bdcDysdDOList){
        if(CollectionUtils.isNotEmpty(bdcDysdDOList)){
            for(BdcDysdDO bdcDysdDO:bdcDysdDOList){
                if(Objects.isNull(bdcDysdDO.getSdsj())){
                    bdcDysdDO.setSdsj(new Date());
                }
            }
        }
    }
    /**
     * 初始化证书解冻数据：解冻人、解冻人ID、解冻时间
     */
    private void initZsJdxx(List<BdcZssdDO> list){
        if(CollectionUtils.isNotEmpty(list)){
            UserDto userDto = userManagerUtils.getCurrentUser();
            for(BdcZssdDO bdcZssdDO : list){
                if(Objects.isNull(bdcZssdDO.getJssj())){
                    bdcZssdDO.setJssj(new Date());
                }
                if(StringUtils.isBlank(bdcZssdDO.getJsr())){
                    bdcZssdDO.setJsr(userDto.getAlias());
                    bdcZssdDO.setJsrid(userDto.getId());
                }
            }
        }
    }
    /**
     * 初始化单元解冻：解冻人、解冻人ID、解冻时间
     */
    private void initDyJdxx(List<BdcDysdDO> list){
        if(CollectionUtils.isNotEmpty(list)){
            UserDto userDto = userManagerUtils.getCurrentUser();
            for(BdcDysdDO bdcDysdDO : list){
                if(Objects.isNull(bdcDysdDO.getJssj())){
                    bdcDysdDO.setJssj(new Date());
                }
                if(StringUtils.isBlank(bdcDysdDO.getJsr())){
                    bdcDysdDO.setJsr(userDto.getAlias());
                    bdcDysdDO.setJsrid(userDto.getId());
                }
            }
        }
    }

    /**
     * 批量修改锁定/冻结信息
     * <p>根据工作流实例ID进行批量更新</p>
     * @param bdcZssdDO 不动产证书锁定DO
     */
    @ResponseBody
    @PatchMapping("/updateSdxxByGzlslid")
    public void modifySdxxByGzlslid(@RequestBody BdcZssdDO bdcZssdDO){
        if(null == bdcZssdDO || StringUtils.isBlank(bdcZssdDO.getGzlslid())){
            throw new MissingArgumentException(messageProvider.getMessage("未获取到需要修改内容。"));
        }
        this.bdcSdFeignService.updateZssdByGzlslid(bdcZssdDO);

        // 修改单元锁定信息
        BdcDysdDO bdcDysdDO = new BdcDysdDO();
        BeanUtils.copyProperties(bdcZssdDO, bdcDysdDO);
        this.bdcSdFeignService.updateDysdByGzlslid(bdcDysdDO);
    }

    /**
     * 单个或批量修改解冻信息
     * <p>根据主键进行更新</p>
     * @param bdcZssdDO 不动产证书锁定DO
     */
    @ResponseBody
    @PatchMapping("/updateJdxx")
    public void modifyJdxx(@RequestBody BdcZssdDO bdcZssdDO){
        if(null == bdcZssdDO || StringUtils.isBlank(bdcZssdDO.getGzlslid())){
            throw new MissingArgumentException(messageProvider.getMessage("未获取到需要修改内容。"));
        }
        String gzlslid = bdcZssdDO.getGzlslid();
        List<BdcZssdDO> bdcZssdDOList = this.bdcSdFeignService.queryYxmZssd(gzlslid, null);
        if(CollectionUtils.isNotEmpty(bdcZssdDOList)){
            String jsr = bdcZssdDO.getJsr();
            String jsyy = bdcZssdDO.getJsyy();
            for(BdcZssdDO bdcZsssd : bdcZssdDOList){
                bdcZsssd.setJsr(jsr);
                bdcZsssd.setJsyy(jsyy);
            }
            this.bdcSdFeignService.updateSdZs(bdcZssdDOList);
        }

        List<BdcDysdDO> bdcDysdDOList = this.bdcSdFeignService.queryYxmDysd(gzlslid, null);
        if(CollectionUtils.isNotEmpty(bdcDysdDOList)){
            for(BdcDysdDO bdcDysdDO : bdcDysdDOList){
                bdcDysdDO.setJsr(bdcZssdDO.getJsr());
                bdcDysdDO.setJsyy(bdcZssdDO.getJsyy());
                this.bdcSdFeignService.saveBdcdysdBz(bdcDysdDO);
            }

        }
    }

    /**
     * 通过工作流实例ID获取原项目id信息
     * @param gzlslid 工作流实例ID
     * @return 原项目ids
     */
    @ResponseBody
    @GetMapping("/getYxmidByGzlslid")
    public List<String> getYxmidByGzlslid(@RequestParam(name="gzlslid") String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException(messageProvider.getMessage("未获取到工作流实例ID。"));
        }
        List<BdcXmDO> yxmList = this.bdcXmFeignService.listYxmByGzlslid(new BdcXmLsgxDO(), gzlslid, null);
        List<String> yxmids = yxmList.stream().map(t->t.getXmid()).collect(Collectors.toList());
        return yxmids;
    }


}
