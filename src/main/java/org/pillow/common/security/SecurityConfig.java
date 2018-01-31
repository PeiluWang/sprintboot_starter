package org.pillow.common.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 安全拦截器初始化配置
 * @author peilu.wang
 */
@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter {

	/**
	 * 指定接口跳转
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry){
		//将跟目录跳转到指定目录
		//registry.addViewController("/").setViewName("forward:/back/sysinfo/is_alive");
	}
	
	/**
	 * 添加拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 设置不检查token的路径
		String[] securityExcludePatterns = new String[]{
				"/auth/login",
				"/auth/register",
				"/sysinfo/**"};
		// 添加拦截器
		registry.addInterceptor(new SecurityInterceptor())
				.excludePathPatterns(securityExcludePatterns);
	}
}
