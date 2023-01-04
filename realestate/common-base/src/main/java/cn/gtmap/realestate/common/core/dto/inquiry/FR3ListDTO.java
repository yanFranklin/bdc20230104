package cn.gtmap.realestate.common.core.dto.inquiry;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2019/3/26
 * @description
 */
@XmlRootElement(name = "frs")
public class FR3ListDTO {

    private List<FR3DTO> fr;

    @XmlElement(name = "fr")
    public List<FR3DTO> getFr() {
        return fr;
    }

    public void setFr(List<FR3DTO> fr) {
        this.fr = fr;
    }


    public FR3ListDTO() {
        this.fr = Lists.newArrayList();
    }

}
