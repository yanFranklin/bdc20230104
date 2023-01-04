package cn.gtmap.realestate.register.core.builder.xxbl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.util.UUIDGenerator;

import java.util.Date;

/**
 * 补录挂接项目构造器
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 3:35 下午 2020/3/17
 */
public class BlGjXmBuilder {
    /**
     * 内部保留一个项目对象，避免后期维护需要额外添加对象
     */
    private BdcXmDO bdcXmDO;

    /**
     * @param bdcXmDO
     * @return BdcXmBuilder 构造器
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 复制不动产项目数据，项目id 重新生成
     */
    public BlGjXmBuilder copyBdcXm(BdcXmDO bdcXmDO) {
        this.bdcXmDO = bdcXmDO;
        this.bdcXmDO.setXmid(UUIDGenerator.generate16());
        return this;
    }

    public BlGjXmBuilder bdcdyh(String bdcdyh) {
        bdcXmDO.setBdcdyh(bdcdyh);
        return this;
    }

    /**
     * @param fwHsDO 权籍信息
     * @return BlGjXmBuilder 挂接项目构造器
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description bdcdywybh，zl，dzwmj以及dzwyt 取权籍数据
     */
    public BlGjXmBuilder fwHs(FwHsDO fwHsDO) {
        bdcXmDO.setBdcdywybh(fwHsDO.getFwbm());
        bdcXmDO.setZl(fwHsDO.getZl());
        bdcXmDO.setDzwmj(fwHsDO.getScjzmj());
        bdcXmDO.setDzwyt(fwHsDO.getGhyt() != null ? Integer.valueOf(fwHsDO.getGhyt()) : null);
        return this;
    }

    /**
     * @param userDto 用户信息
     * @return BlGjXmBuilder 挂接项目构造器
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 修改受理人，受理人ID 以及受理时间「当前时间」
     */
    public BlGjXmBuilder user(UserDto userDto) {
        bdcXmDO.setSlr(userDto.getAlias());
        bdcXmDO.setSlrid(userDto.getId());
        bdcXmDO.setSlsj(new Date());
        return this;
    }

    public BlGjXmBuilder djjg(String djjg) {
        bdcXmDO.setDjjg(djjg);
        return this;
    }

    public BlGjXmBuilder xmly(Integer xmly) {
        bdcXmDO.setXmly(xmly);
        return this;
    }

    public BdcXmDO build() {
        return this.bdcXmDO;
    }
}
