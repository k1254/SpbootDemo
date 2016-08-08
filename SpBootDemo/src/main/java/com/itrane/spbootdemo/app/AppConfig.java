package com.itrane.spbootdemo.app;

import javax.annotation.Resource;

import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Import({ DbConfig.class })
public class AppConfig extends WebMvcConfigurerAdapter {

	@Resource private Environment env;	
	@Resource EmbeddedWebApplicationContext ewa;

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename(env.getProperty("message.source.basename"));
		messageSource.setUseCodeAsDefaultMessage(true); // メッセージのキーがない場合にキーを表示
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(0); // -1: リロードしない、0: 常にリロード
		return messageSource;
	}
}
