package com.example.filters;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "testb")
public class TestBProperties {

	private Map<String, String> ruts = new HashMap<String, String>();
	
	private String url;

	public Map<String, String> getRuts() {
		return ruts;
	}

	public void setRuts(Map<String, String> ruts) {
		this.ruts = ruts;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
