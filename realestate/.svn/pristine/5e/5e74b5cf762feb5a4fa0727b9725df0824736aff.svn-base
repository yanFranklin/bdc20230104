package cn.gtmap.realestate.init.config;

import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.ofdrw.converter.FontLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;


@Repository
public class OfdConfig {
    @Value("${dzzz.ofdfontpath:null}")
    private String ofdFontPath;

    @PostConstruct
    public void dzzzConfig(){
        setOfdFont();
    }

    /**
     * 设置ofd自定义字体库
     */
    private void setOfdFont(){
        if(StringUtils.isNotBlank(ofdFontPath)){
            File fontFolder = new File(ofdFontPath);
            if (fontFolder.exists() && fontFolder.isDirectory()) {
                File[] files = fontFolder.listFiles();
                if (null != files) {
                    for (File file : files) {
                        if (file.isFile()) {
                            String name = file.getName();
                            name = name.substring(0,name.lastIndexOf(Constants.DZZZ_SPLIT_SLIGHT));
                            FontLoader.getInstance()
                                    .addAliasMapping(name,file.getAbsolutePath())
                                    .addSystemFontMapping(name,file.getAbsolutePath());
                        }
                    }
                }
            }
        }
    }
}
