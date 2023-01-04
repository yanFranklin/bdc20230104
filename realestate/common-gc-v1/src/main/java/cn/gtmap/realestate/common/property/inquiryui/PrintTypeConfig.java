package cn.gtmap.realestate.common.property.inquiryui;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "printType")
public class PrintTypeConfig {

    private static List<String> defaultPrintTypes = Lists.newArrayList();
    static {
        defaultPrintTypes.add("有房无房证明");
        defaultPrintTypes.add("权属证明");
    }

    private List<String> nantong = new ArrayList<>();

    private List<String> heifei = new ArrayList<>();

    private List<String> standard = new ArrayList<>();

    public List<String> getNantong() {
        if(!CollectionUtils.isNotEmpty(nantong)){
            return defaultPrintTypes;
        }
        return nantong;
    }

    public void setNantong(List<String> nantong) {
        this.nantong = nantong;
    }

    public List<String> getHeifei() {
        if(!CollectionUtils.isNotEmpty(nantong)){
            return defaultPrintTypes;
        }
        return heifei;
    }

    public void setHeifei(List<String> heifei) {
        this.heifei = heifei;
    }

    public List<String> getStandard() {
        if(!CollectionUtils.isNotEmpty(nantong)){
            return defaultPrintTypes;
        }
        return standard;
    }

    public void setStandard(List<String> standard) {
        this.standard = standard;
    }

    @Override
    public String toString() {
        return "PrintTypeConfig{" +
                "nantong=" + nantong +
                ", heifei=" + heifei +
                ", standard=" + standard +
                '}';
    }
}
