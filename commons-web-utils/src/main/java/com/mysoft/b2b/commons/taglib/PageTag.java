package com.mysoft.b2b.commons.taglib;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 分页标签
 * 
 * @author ganq
 * 
 */
public class PageTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer _page;
	private Integer _allrows;
	private Integer _pagesize;
	
	
	private Object page;
	private Object allrows;
	private Object pagesize;
	
	
	
	private String url;
	private String domain;

	private int pageCount = 1;

	public int doEndTag() throws JspException {

		JspWriter out = pageContext.getOut();
		try {

			String result = createPageHtml(page, allrows, pagesize);
			out.print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return TagSupport.EVAL_PAGE;
	}

	/**
	 * 检查页码正确性
	 */
	private void checkPage() {
		if (_allrows % _pagesize == 0) {
			pageCount = _allrows / _pagesize;
		} else {
			pageCount = (_allrows / _pagesize) + 1;
		}

		if (_page < 1) {
			_page = 1;
		}
		if (pageCount < 1) {
			pageCount = 1;
		}
		if (_page > pageCount) {
			_page = pageCount;
		}

	}

	/**
	 * 创建新的url
	 */
	private String createUrl(int param) {
		
		
		String [] urlArray = url.split("\\?");
		String pathName = urlArray.length>=1?urlArray[0]:"";
		String search = urlArray.length==2?urlArray[1]:"";
		
		String [] searchList = search.split("\\&");
		Map<String,String> searchMap = new LinkedHashMap<String, String>();
		for (String string : searchList) {
			String [] item = string.split("=");
			if (item.length != 2) {
				continue;
			}
			searchMap.put(item[0], item[1]);
		}
		searchMap.remove("page");
		searchMap.put("page",(param==0?"":param+""));
		
		StringBuilder newSearch = new StringBuilder();
		String newUrl = "";
		for (String key : searchMap.keySet()) {
			newSearch.append(key+"="+searchMap.get(key)+"&");
		}
		newSearch.delete(newSearch.length()-1, newSearch.length());
		newUrl = pathName + "?" + newSearch.toString();
		
		
		if (!StringUtils.isBlank(domain)) {
			newUrl = DomainTag.getDomainUrlByKey(domain, "/") + newUrl;
		}
		return newUrl;
	}

	/**
	 * 创建翻页html结构
	 * 
	 * @param page
	 * @param allrows
	 * @param pagesize
	 * @return
	 */
	public String createPageHtml(Object page, Object allrows, Object pagesize) {

		this._page = NumberUtils.toInt(ObjectUtils.toString(page),1);
		this._allrows = NumberUtils.toInt(ObjectUtils.toString(allrows));
		this._pagesize = NumberUtils.toInt(ObjectUtils.toString(pagesize));

		if (_allrows == 0 || _pagesize == 0) {
			return "";
		}

		checkPage();

		StringBuilder strHtml = new StringBuilder();
		int prevPage = _page - 1;
		int nextPage = _page + 1;
		
		strHtml.append("<p class='page_total'>总共" + _allrows + "条记录</p>");

		if (pageCount <= 1) {
			return strHtml.toString();
		}

		strHtml.append("<ul class='pagination'>");
		if (prevPage < 1) {
			//strHtml.append("<span>首页</span>");
			strHtml.append("<li class='disabled'>上一页</li>");
		} else {
			//strHtml.append("<span><a href='" + createUrl(1) + "'>首页</a></span>");
			strHtml.append("<li><a href='" + createUrl(prevPage) + "'>上一页</a></li>");
		}
		if (_page != 1) {
			strHtml.append("<li><a href='" + createUrl(1) + "'>1</a></li>");
		}
		if (_page >= 5) {
			strHtml.append("<li style='border:none'>...</li>");
		}
		int endPage = 0;
		if (pageCount > _page + 2) {
			endPage = _page + 2;
		} else {
			endPage = pageCount;
		}
		for (int i = _page - 2; i <= endPage; i++) {
			if (i > 0) {
				if (i == _page) {
					strHtml.append("<li class='current'>" + i + "</li>");
				} else {
					if (i != 1 && i != pageCount) {
						strHtml.append("<li><a href='" + createUrl(i) + "'>" + i + "</a></li>");
					}
				}
			}
		}
		if (_page + 3 < pageCount) {
			strHtml.append("<li style='border:none'>...</li>");
		}
		if (_page != pageCount) {
			strHtml.append("<li><a href='" + createUrl(pageCount) + "'>" + pageCount + "</a></li>");
		}
		if (nextPage > this.pageCount) {
			strHtml.append("<li class='disabled'>下一页</li>");
			//strHtml.append("<span>尾页</span>");
		} else {
			strHtml.append("<li><a href='" + createUrl(nextPage) + "'>下一页</a></li>");
			//strHtml.append("<span><a href='" + createUrl(pageCount) + "'>尾页</a></span>");
		}
		strHtml.append("<li class='page_set'>");
		strHtml.append("共" + pageCount + "页，到第");
		strHtml.append("<input class='txt_common txt_page_num' id='paginationNumTxt' value='" + _page + "'  ");
		strHtml.append(" onkeydown='javascript:return (function (evt){function c(){evt = (evt) ? evt : ((window.event) ? window.event : \"\");var k = evt.keyCode?evt.keyCode:evt.which;if((k>=48&&k<=57)||(k>=96&&k<=105)||(k==8)||(k>=37&&k<=40)){return true}else{return false}};return c();})(event); '");
		strHtml.append(">页");
		strHtml.append("<input class='btn_common btn_silvery btn_size_30' type='button' value='确定' onclick='javascript:");
		strHtml.append("(function(){var p = document.getElementById(\"paginationNumTxt\").value;if(p > "+pageCount+"){p = "+pageCount+";}location.href = \""+createUrl(0)+"\"+p})();'>");
		strHtml.append("</li>");
		strHtml.append("</ul>");
		strHtml.append("<span class='clear'></span>");

		return strHtml.toString();
	}


	public static void main(String[] args) {
		PageTag a= new PageTag();
		a.setUrl("http://search.b2bdev.com:8888/announcements?keyword=阿斯蒂芬&page=1");
		System.out.println(a.createPageHtml("2ghj", "500", "10"));
	}

	public Object getPage() {
		return page;
	}

	public void setPage(Object page) {
		this.page = page;
	}

	public Object getAllrows() {
		return allrows;
	}

	public void setAllrows(Object allrows) {
		this.allrows = allrows;
	}

	public Object getPagesize() {
		return pagesize;
	}

	public void setPagesize(Object pagesize) {
		this.pagesize = pagesize;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
}
