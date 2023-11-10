package com.blanc.side.servlet.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class CustomRequestWrapper extends HttpServletRequestWrapper {

  private final Map<String, String[]> modifiedParameter;

  public CustomRequestWrapper(HttpServletRequest request) {
    super(request);
    this.modifiedParameter = new HashMap<>(request.getParameterMap());
  }

  @Override
  public String getParameter(String name) {
    String[] values = modifiedParameter.get(name);
    return (values != null && values.length > 0) ? values[0] : null;
  }

  @Override
  public Map<String, String[]> getParameterMap() {
    return Collections.unmodifiableMap(modifiedParameter);
  }

  @Override
  public Enumeration<String> getParameterNames() {
    return Collections.enumeration(modifiedParameter.keySet());
  }

  @Override
  public String[] getParameterValues(String name) {
    return modifiedParameter.get(name);
  }

  public void setParameter(String name, String value) {
    modifiedParameter.put(name, new String[]{value});
  }
}
