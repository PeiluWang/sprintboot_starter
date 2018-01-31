package org.pillow.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pillow.common.response.BaseResponse;
import org.pillow.model.dto.ServerErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class WebServerExceptionHandler {
	
	final Logger SYSTEM_LOG = LoggerFactory.getLogger("system");
	
	/**
	 * 统一处理一般错误
	 * 注：该方法对拦截器中的异常无效
	 */
	@ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse<ServerErrorInfo> defaultErrorHandler(HttpServletRequest req, HttpServletResponse response, Exception e) throws Exception {
		String requestId = (String) req.getAttribute("requestId");
		//若有后台定义的错误码则按该错误码返回
    	if(e instanceof WebServerException){
    		//e.printStackTrace();
    		WebServerException webExp=(WebServerException)e;
    		String api = req.getRequestURI();
    		SYSTEM_LOG.warn(api+"\t"+requestId+"\t"+webExp.getError()+"\t"+webExp.getErrorDetailMsg());
    		return new BaseResponse<ServerErrorInfo>(1, new ServerErrorInfo(webExp.getError(), webExp.getErrorDetailMsg()));
    	} else {
    	//其他
    		String errMsg=getErrorInfoFromException(e);
    		//e.printStackTrace();
    		SYSTEM_LOG.error(requestId, e);
    		return new BaseResponse<ServerErrorInfo>(1, new ServerErrorInfo(WebServerError.OTHER, errMsg));
    	}
    }
	
	
	private static String getErrorInfoFromException(Exception e) {  
        try {  
            StringWriter sw = new StringWriter();  
            PrintWriter pw = new PrintWriter(sw);  
            e.printStackTrace(pw);  
            return sw.toString();  
        } catch (Exception e2) {  
            return "bad getErrorInfoFromException";  
        }  
    }  
}
