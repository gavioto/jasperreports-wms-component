package com.sourcepole.jasperreports.wmsmap;

import static java.net.URLEncoder.encode;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class WmsRequestBuilder {

  private static final String UTF_8 = "UTF-8";

  private final Map<WmsRequestParameter, String> parameters = new HashMap<WmsRequestParameter, String>();

  public static WmsRequestBuilder createGetMapRequest(String serviceUrl) {
    WmsRequestBuilder builder = new WmsRequestBuilder();
    builder.parameters.put(WmsRequestParameter.WMS_URL, serviceUrl);
    builder.parameters.put(WmsRequestParameter.REQUEST,
        WmsRequestType.GET_MAP.getType());
    return builder;
  }

  public static WmsRequestBuilder createGetMapRequest(
      Map<WmsRequestParameter, String> params) {
    WmsRequestBuilder builder = new WmsRequestBuilder();
    builder.parameters.putAll(params);
    return builder;
  }

  public WmsRequestBuilder layers(String layers) {
    parameters.put(WmsRequestParameter.LAYERS, layers);
    return this;
  }

  public WmsRequestBuilder styles(String styles) {
    parameters.put(WmsRequestParameter.STYLE, styles);
    return this;
  }

  public WmsRequestBuilder format(String format) {
    parameters.put(WmsRequestParameter.FORMAT, format);
    return this;
  }

  public WmsRequestBuilder boundingBox(String boundingBox) {
    parameters.put(WmsRequestParameter.BBOX, boundingBox);
    return this;
  }

  public WmsRequestBuilder transparent(boolean transparent) {
    parameters.put(WmsRequestParameter.TRANSPARENT, Boolean
        .valueOf(transparent)
        .toString());
    return this;
  }

  public WmsRequestBuilder version(String version) {
    parameters.put(WmsRequestParameter.VERSION, version);
    return this;
  }

  public WmsRequestBuilder srsCrs(String srsCrs) {
    parameters.put(WmsRequestParameter.SRS_CRS, srsCrs);
    return this;
  }

  public WmsRequestBuilder urlParameters(String urlParameters) {
    parameters.put(WmsRequestParameter.URL_PARAMETERS, urlParameters);
    return this;
  }

  public WmsRequestBuilder width(int width) {
    parameters
        .put(WmsRequestParameter.WIDTH, Integer.valueOf(width).toString());
    return this;
  }

  public WmsRequestBuilder height(int height) {
    parameters.put(WmsRequestParameter.HEIGHT, Integer.valueOf(height)
        .toString());
    return this;
  }

  public URL toMapUrl() throws MalformedURLException {
    StringBuilder url = new StringBuilder();
    Map<WmsRequestParameter, String> params =
        new LinkedHashMap<WmsRequestParameter, String>(this.parameters);
    String wmsUrl = params.remove(WmsRequestParameter.WMS_URL).toString();
    url.append(wmsUrl);
    if (wmsUrl.indexOf("?") == -1) {
      url.append("?");
    }
    java.util.List<String> paramList = new ArrayList<String>();

    for (WmsRequestParameter parameter : WmsRequestParameter.parameterValues()) {
      if (parameter == WmsRequestParameter.URL_PARAMETERS) {
        continue;
      }
      Object defaultValue = parameter.defaultValue(params);
      if (params.containsKey(parameter) || parameter.isRequired(params)
          || defaultValue != null) {
        String parameterName = parameter.parameterName(params);
        Object value = parameter.extract(params);
        paramList.add(encodeParameter(parameterName, value.toString()));
      }
    }
    Iterator<String> iterator = paramList.iterator();
    while (iterator.hasNext()) {
      String param = iterator.next();
      url.append(param);
      if (iterator.hasNext()) {
        url.append("&");
      }
    }
    Object urlParams = params.remove(WmsRequestParameter.URL_PARAMETERS);
    if (urlParams != null) {
      String extraUrlParams = urlParams.toString();
      if (!extraUrlParams.startsWith("&")) {
        url.append("&");
      }
      url.append(extraUrlParams);
    }
    return new URL(url.toString());
  }

  private static String encodeParameter(String key, Object value) {
    try {
      String encName = encode(key, UTF_8);
      String encValue = encode(value.toString(), UTF_8);
      String encodedParameter = String.format("%s=%s", encName, encValue);
      return encodedParameter;
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Encoding UTF-8 not supported on runtime");
    }
  }
}