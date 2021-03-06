package com.devederno.cursomc.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HeaderExposureFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(
    ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain
  ) throws IOException, ServletException {
    HttpServletResponse res = (HttpServletResponse) servletResponse;
    res.addHeader("access-control-expose-headers", "location");
    filterChain.doFilter(servletRequest, res);
  }

  @Override
  public void destroy() {}
}
