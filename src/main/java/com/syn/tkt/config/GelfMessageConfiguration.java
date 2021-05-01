package com.syn.tkt.config;

import java.net.InetSocketAddress;

import org.graylog2.gelfclient.GelfConfiguration;
import org.graylog2.gelfclient.GelfTransports;
import org.graylog2.gelfclient.transport.GelfTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GelfMessageConfiguration {

	private final ApplicationProperties applicationProperties;
	
	public GelfMessageConfiguration(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
	
	@Bean
	public GelfConfiguration getGelfConfiguration() {
		String host = this.applicationProperties.getGelfTcpHost();
		int port = Integer.parseInt(applicationProperties.getAlertActivityGelfTcpPort());
		
		return new GelfConfiguration(new InetSocketAddress(host, port))
        .transport(GelfTransports.TCP)
        .queueSize(512)
        .connectTimeout(5000)
        .reconnectDelay(1000)
        .tcpNoDelay(true)
        .sendBufferSize(32768);
	}
	
	@Bean
	public GelfTransport getGelfTransport() {
		return GelfTransports.create(getGelfConfiguration());
	}
	
	
}
