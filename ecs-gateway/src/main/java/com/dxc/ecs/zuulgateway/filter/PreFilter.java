package com.dxc.ecs.zuulgateway.filter;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class PreFilter extends ZuulFilter {

  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
    System.out.println("Inside Route Filter");
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    
    if("/api/ecs-oauth-service/oauth/token".equals(request.getRequestURI())) {
      Map<String, List<String>> reqParameterMap = new HashMap<>();
      reqParameterMap.put("grant_type", Arrays.asList("password"));
      ctx.addZuulRequestHeader("Authorization", "Basic ZWNzYXBwOmVjc2FwcDEyMw==");
      ctx.setRequestQueryParams(reqParameterMap);
    }
    Enumeration<String> headers = request.getHeaderNames();
   while(headers.hasMoreElements()) {
	   String header = headers.nextElement();
	   System.out.println(header + ":" + request.getHeader(header));
   }
    System.out.println("Request Method : " + request.getMethod() + " Request URL : " + request.getRequestURL().toString() + "Context Path:" +  request.getRequestURI());
    return null;
  }

}