package cn.gtmap.realestate.building.core.resource;

import cn.gtmap.realestate.building.config.lpb.LpbConfig;
import cn.gtmap.realestate.building.core.bo.LinkBO;
import cn.gtmap.realestate.building.util.LpbUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-15
 * @description 链接资源
 */
public class LinkResource extends AttributeResouce {

    /**
     * 链接配置标志，用来指定读取配置中哪个维度的链接配置
     */
    private String linkFlag;

    /**
     * @param resource
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构造函数
     */
    public LinkResource(AbstractResource resource) {
        super(resource);
        this.linkFlag = LpbConfig.FLAG_HS;
    }

    /**
     * @param resource
     * @param linkFlag
     * @return
     * @author <a [resource, linkFlag]">liyinqiao</a>
     * @description 构造函数
     */
    public LinkResource(AbstractResource resource, String linkFlag) {
        super(resource);
        this.linkFlag = linkFlag;
    }

    /**
     * @param
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理 LINK参数
     */
    @Override
    public void dealAttribute() {
        List<LinkBO> linkConfigList = LpbConfig.getLinkList(this.configCode, this.linkFlag);
        List<Link> linkList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(linkConfigList)) {
            for (LinkBO linkBO : linkConfigList) {
                // 转换 URL
                String url = LpbUtils.revertPlaceholder(linkBO.getUrl(), LpbUtils.parseObjectToMap(this.paramObject, null));
                if (StringUtils.isNotBlank(url)) {
                    linkList.add(new Link(url).withRel(linkBO.getName()));
                }
            }
        }
        this.resouceDTO.setLinks(linkList);
    }

}
