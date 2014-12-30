package com.mysoft.b2b.commons.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.mysoft.b2b.commons.FileUtil;

/**
 * 获取公用HTML片段(不支持带参，定制HTML片段的内容)
 * 
 * @author chengp
 * 
 */
public class IncludeHtmlTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private String page;
	private boolean breakLine = true;

	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.print(FileUtil.getFileContentFromDisk(page, breakLine));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return TagSupport.EVAL_PAGE;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public boolean isBreakLine() {
		return breakLine;
	}

	public void setBreakLine(boolean breakLine) {
		this.breakLine = breakLine;
	}

}
