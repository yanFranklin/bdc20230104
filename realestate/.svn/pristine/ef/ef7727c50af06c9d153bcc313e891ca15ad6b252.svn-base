package cn.gtmap.realestate.register.core.builder.xxbl;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.util.UUIDGenerator;

/**
 * 补录挂接房地产权构造器
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 3:51 下午 2020/3/17
 */
public class BlGjFdcqBuilder {
    /**
     * 内部保留一个项目对象，避免后期维护需要额外添加对象
     */
    private BdcFdcqDO fdcqDO;

    /**
     * @param bdcQl 权利信息
     * @return BlGjFdcqBuilder 构造器
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 复制权利信息，权利 id 重新生成
     */
    public BlGjFdcqBuilder copyQl(BdcQl bdcQl) {
        if (bdcQl instanceof BdcFdcqDO) {
            fdcqDO = (BdcFdcqDO) bdcQl;
        } else {
            fdcqDO = new BdcFdcqDO();
        }
        fdcqDO.setQlid(UUIDGenerator.generate16());
        return this;
    }

    /**
     * @param fwHsDO 权籍信息
     * @return BlGjFdcqBuilder 构造器
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description bdcdywybh, jzmj, ghyt, zl, cg, dytdmj, ftjzmj, fwxz 取权籍数据
     */
    public BlGjFdcqBuilder fwHs(FwHsDO fwHsDO) {
        fdcqDO.setBdcdywybh(fwHsDO.getFwbm());
        fdcqDO.setJzmj(fwHsDO.getScjzmj());
        fdcqDO.setGhyt(fwHsDO.getGhyt() != null ? Integer.valueOf(fwHsDO.getGhyt()) : null);
        fdcqDO.setZl(fwHsDO.getZl());
        fdcqDO.setCg(fwHsDO.getCg());
        fdcqDO.setDytdmj(fwHsDO.getDytdmj());
        fdcqDO.setFtjzmj(fwHsDO.getFttdmj());
        fdcqDO.setFwxz(fwHsDO.getFwxz() != null ? Integer.valueOf(fwHsDO.getFwxz()) : null);
        return this;
    }

    /**
     * @param bdcXmDO 项目信息
     * @return BlGjFdcqBuilder 构造器
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description xmid, slbh 和 bdcdyh 取项目数据
     */
    public BlGjFdcqBuilder bdcXm(BdcXmDO bdcXmDO) {
        fdcqDO.setXmid(bdcXmDO.getXmid());
        fdcqDO.setSlbh(bdcXmDO.getSlbh());
        fdcqDO.setBdcdyh(bdcXmDO.getBdcdyh());
        return this;
    }

    public BdcFdcqDO build() {
        return this.fdcqDO;
    }

}
