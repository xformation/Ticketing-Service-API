package com.syn.tkt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Servicedesk.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
	private String kafkaSendDataUrl;
	private String host;
	private String alertSrvUrl;
	private String alertActivityKafaTopic;
	
	public String getKafkaSendDataUrl() {
		return kafkaSendDataUrl;
	}

	public void setKafkaSendDataUrl(String kafkaSendDataUrl) {
		this.kafkaSendDataUrl = kafkaSendDataUrl;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getAlertSrvUrl() {
		return alertSrvUrl;
	}

	public void setAlertSrvUrl(String alertSrvUrl) {
		this.alertSrvUrl = alertSrvUrl;
	}

	public String getAlertActivityKafaTopic() {
		return alertActivityKafaTopic;
	}

	public void setAlertActivityKafaTopic(String alertActivityKafaTopic) {
		this.alertActivityKafaTopic = alertActivityKafaTopic;
	}

	
	
	
	
}

