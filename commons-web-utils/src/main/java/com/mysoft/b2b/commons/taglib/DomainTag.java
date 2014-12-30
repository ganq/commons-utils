package com.mysoft.b2b.commons.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.mysoft.b2b.commons.DomainUtil;
import com.mysoft.b2b.commons.VersionUtil;

public class DomainTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String domain;
	private String uri;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public int doEndTag() throws JspException {

		JspWriter out = pageContext.getOut();
		try {
			
			String result = getDomainUrlByKey(domain, uri);
			out.print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return TagSupport.EVAL_PAGE;
	}

	/**
	 * 根据domain和uri获取全路径
	 * @param _domain
	 * @param _uri
	 * @return
	 */
	public static String getDomainUrlByKey(String _domain, String _uri) {
		if (StringUtils.isBlank(_domain) || StringUtils.isBlank(_uri)) {
			return "";
		}

		String[] uriArrays = _uri.split("\\.");
		String suffix = "";
		if (uriArrays != null && uriArrays.length > 1) {
			suffix = uriArrays[1];
			if (suffix.contains("?")) {
				suffix = suffix.substring(0, suffix.indexOf("?"));
			}
		}

		String version = VersionUtil.getVersion(suffix + ".version");
		String paramConnector = _uri.contains("?") ? "&" : "?";
		String versionParam = StringUtils.isBlank(version) ? "" : (paramConnector + "ver=" + version);
		String result = DomainUtil.getDomain(_domain) + _uri + versionParam;
		
		return result;
	}
}
