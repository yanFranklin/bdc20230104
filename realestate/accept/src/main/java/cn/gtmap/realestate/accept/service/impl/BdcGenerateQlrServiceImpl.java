package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.service.BdcGenerateQlrService;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.util.CardNumberTransformation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: realestate
 * @description: 义务人数据组织封装实现方法
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-11-28 21:26
 **/
@Service
public class BdcGenerateQlrServiceImpl implements BdcGenerateQlrService {
    /**
     * @param bdcQlrDOList 需要封装的权利人数据
     * @return 封装后的权利人信息
     * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    @Override
    public BdcQlrDO getYwrData(List<BdcQlrDO> bdcQlrDOList) {
        BdcQlrDO bdcYwrDO = new BdcQlrDO();
        String ywrmc = "";
        String ywrlxdh = "";
        String ywrzjh = "";
        String ywrdlrmc = "";
        String ywrdlrdh = "";
        bdcQlrDOList.sort((o1, o2) -> o1.getXmid().compareTo(o2.getXmid()));
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            StringBuilder ywrStringBuilder = new StringBuilder();
            StringBuilder lxdhStringBuilder = new StringBuilder();
            StringBuilder zjhStringBuilder = new StringBuilder();
            StringBuilder dlrmcStringBuilder = new StringBuilder();
            StringBuilder dlrdhStringBuilder = new StringBuilder();
            Set<String> ywr = new LinkedHashSet<>();
            for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                String zjh = bdcQlrDO.getZjh();
                if (StringUtils.isNotBlank(zjh) && zjh.length() == 18 && CardNumberTransformation.idCard18(zjh)) {
                    zjh = CardNumberTransformation.idCard18to15(zjh);
                }
                if (StringUtils.isNotBlank(bdcQlrDO.getQlrmc()) && !ywr.contains(bdcQlrDO.getQlrmc() + zjh)) {
                    ywr.add(bdcQlrDO.getQlrmc() + zjh);
                    ywrStringBuilder.append(StringUtils.isNotBlank(bdcQlrDO.getQlrmc()) ? bdcQlrDO.getQlrmc() : "").append(",");
                    if (StringUtils.isNotBlank(bdcQlrDO.getDh())) {
                        lxdhStringBuilder.append(bdcQlrDO.getDh()).append(",");
                    }
                    if (StringUtils.isNotBlank(bdcQlrDO.getZjh())) {
                        zjhStringBuilder.append(bdcQlrDO.getZjh()).append(",");
                    }
                    if (StringUtils.isNotBlank(bdcQlrDO.getDlrmc())) {
                        dlrmcStringBuilder.append(bdcQlrDO.getDlrmc()).append(",");
                    }
                    if (StringUtils.isNotBlank(bdcQlrDO.getDlrdh())) {
                        dlrdhStringBuilder.append(bdcQlrDO.getDlrdh()).append(",");
                    }
                }
            }
            ywrmc = ywrStringBuilder.substring(0, ywrStringBuilder.length() - 1);
            if (StringUtils.isNotBlank(lxdhStringBuilder)) {
                ywrlxdh = lxdhStringBuilder.substring(0, lxdhStringBuilder.length() - 1);
            }
            if (StringUtils.isNotBlank(zjhStringBuilder)) {
                ywrzjh = zjhStringBuilder.substring(0, zjhStringBuilder.length() - 1);
            }
            if (StringUtils.isNotBlank(dlrmcStringBuilder)) {
                ywrdlrmc = dlrmcStringBuilder.substring(0, dlrmcStringBuilder.length() - 1);
            }
            if (StringUtils.isNotBlank(dlrdhStringBuilder)) {
                ywrdlrdh = dlrdhStringBuilder.substring(0, dlrdhStringBuilder.length() - 1);
            }
        }
        bdcYwrDO.setQlrmc(ywrmc);
        bdcYwrDO.setDh(ywrlxdh);
        bdcYwrDO.setZjh(ywrzjh);
        bdcYwrDO.setDlrmc(ywrdlrmc);
        bdcYwrDO.setDlrdh(ywrdlrdh);
        return bdcYwrDO;
    }
}
