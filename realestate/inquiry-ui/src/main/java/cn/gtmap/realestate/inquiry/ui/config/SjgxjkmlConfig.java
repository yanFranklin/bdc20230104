package cn.gtmap.realestate.inquiry.ui.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/2/2
 * @description 省级共享接口配置
 */
@Component
@ConfigurationProperties(prefix = "sjgx")
public class SjgxjkmlConfig {
	
	/**
	 * 接口目录
	 */
	private Map<String,List<String>> jkml = new HashMap<>();


	public Map<String, List<String>> getJkml() {
		return jkml;
	}

	public void setJkml(Map<String, List<String>> jkml) {
		this.jkml = jkml;
	}

	public List<String> getJkml(String gzldyid) {
		if(StringUtils.isNotBlank(gzldyid)) {
			return jkml.get(gzldyid);
		}
		return Collections.emptyList();
	}
}
