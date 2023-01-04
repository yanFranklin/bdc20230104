package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.CommonUtil;
import cn.gtmap.realestate.init.core.service.BdcBdcdyService;
import cn.gtmap.realestate.init.util.DozerUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/19
 * @description 不动产单元信息服务实现
 */
@Service
@Validated
public class BdcBdcdyServiceImpl implements BdcBdcdyService {

    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;
    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private EntityMapper entityMapper;

    /**
     * @param bdcdyh
     * @return boolean 是否是房屋
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 通过不动产单元号判断当前不动产类型是否是房屋（是返回true，否返回false）
     */
    @Override
    public Boolean judgeIsFwByBdcdyh(String bdcdyh) {
        return judgeZdlxInSourceByBdcdyh(bdcdyh,new String[]{CommonConstantUtils.BDCLX_TZM_F});
    }

    @Override
    public Boolean judgeZdlxInSourceByBdcdyh(String bdcdyh, String[] zdlxSource) {
        Boolean isInZdlxSource = false;
        if (StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() > 20) {
            String dzwTzm = bdcdyh.substring(19, 20);
            if (CommonUtil.indexOfStrs(zdlxSource,dzwTzm)) {
                isInZdlxSource = true;
            }
        }
        return isInZdlxSource;
    }

    /**
     * @param bdcdyh
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 回写状态到权籍
     */
    @Override
    public void syncBdcdyZtByBdcdyh(@NotBlank(message = "不动产单元号不能为空") String bdcdyh,String qjgldm) {
        //回写状态到权籍
        List<String> bdcdyList=new ArrayList<>();
        bdcdyList.add(bdcdyh);
        bdcdyZtFeignService.syncBdcdyZtByBdcdyh(bdcdyList,qjgldm);
    }

    @Override
    public void syncBdcdyZtByXmid(@NotBlank(message = "项目ID不能为空") String xmid){
        if(StringUtils.isNotBlank(xmid)){
            BdcXmDO xm=entityMapper.selectByPrimaryKey(BdcXmDO.class,xmid);
            if(xm != null){
                String qjgldm ="";
                BdcXmFbDO bdcXmFbDO = entityMapper.selectByPrimaryKey(BdcXmFbDO.class, xmid);
                if (bdcXmFbDO != null) {
                    qjgldm= bdcXmFbDO.getQjgldm();
                }
                syncBdcdyZtByBdcdyh(xm.getBdcdyh(),qjgldm);
            }
        }

    }
}
