package victor.training.spring.web.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import victor.training.spring.web.controller.RequestScopedBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@Slf4j
public class InspectingFilter implements Filter {
   @Autowired
   private RequestScopedBean requestScopedBean;

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      requestScopedBean.setTenantId(httpRequest.getHeader("tenant"));
      List<String> headerList = new ArrayList<>();
      for (Enumeration<String> e = httpRequest.getHeaderNames(); e.hasMoreElements(); ) {
         String headerName = e.nextElement();
         headerList.add("\t " + headerName + ": " + httpRequest.getHeader(headerName));
      }
      log.debug("Request Headers:\n" + String.join("\n", headerList));
      chain.doFilter(request, response);
   }
}
