package com.bookshop.portal.pojo;
/**
 * 小广告
 * @author 剑影随风
 *
 */
public class AD2Node {


	private String alt;
	private String src;
	private String href;
	private String index;
	private String ext;
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public AD2Node(String alt, String src, String href, String index, String ext) {
		super();
		this.alt = alt;
		this.src = src;
		this.href = href;
		this.index = index;
		this.ext = ext;
	}
	public AD2Node() {
		super();
	}
	
	
}
