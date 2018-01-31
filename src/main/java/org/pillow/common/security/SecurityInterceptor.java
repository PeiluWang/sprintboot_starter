package org.pillow.common.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pillow.common.util.DateTimeUtil;
import org.pillow.common.util.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 全局拦截器，检查用户是否有权限访问页面
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter{

	final Logger REQUEST_LOG = LoggerFactory.getLogger("api_request");
	final Logger RESPONSE_LOG = LoggerFactory.getLogger("api_response");

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		AuthorizationParam param = new AuthorizationParam(request);
		String token = param.getToken();
		//todo: 检查token是否合法
		//如果token合法，根据token获取用户信息，保存在request中
		
		//访问的端口
		String api = request.getRequestURI();
		String ip = HttpRequestUtil.getIp(request);
		String reqParam = HttpRequestUtil.getParam(request);
		//访问的id，用于日志分析
		String requestId = DateTimeUtil.timestamp()+"_"+RandomUtil.randStr(3);
		//记录访问时间，用于统计分析
		long requestTime = System.currentTimeMillis();
		request.setAttribute("requestTime", requestTime);
		request.setAttribute("requestId", requestId);
		//记录访问日志
		REQUEST_LOG.info(api+"\t"+requestId+"\t"+ip+"\t"+reqParam);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String requestId = (String) request.getAttribute("requestId");
		long startTime = (Long) request.getAttribute("requestTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        String api = request.getRequestURI();
        RESPONSE_LOG.info(api+"\t"+requestId+"\t"+executeTime);
	}

	
	
	
}

