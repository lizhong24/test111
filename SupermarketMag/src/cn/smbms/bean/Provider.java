package cn.smbms.bean;

import java.util.Date;

import cn.smbms.util.PageUtil;

public class Provider {
	private Integer id;// id
	private String proCode;// 供应商编码
	private String proName;// 供应商名称
	private String proDesc;// 供应商描述
	private String proContact;// 供应商联系人
	private String proPhone;// 供应商电话
	private String proAdderss;// 供应商地址
	private String proFax;// 供应商传真
	private Integer createdBy;// 创建者
	private Date creationDate;// 创建时间
	private Integer modifyBy;// 更新者
	private Date modifyDate;// 更新时间

	private PageUtil pageUtil; // 分页工具对象

	public PageUtil getPageUtil() {
		return pageUtil;
	}

	public void setPageUtil(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProDesc() {
		return proDesc;
	}

	public void setProDesc(String proDesc) {
		this.proDesc = proDesc;
	}

	public String getProContact() {
		return proContact;
	}

	public void setProContact(String proContact) {
		this.proContact = proContact;
	}

	public String getProPhone() {
		return proPhone;
	}

	public void setProPhone(String proPhone) {
		this.proPhone = proPhone;
	}

	public String getProAdderss() {
		return proAdderss;
	}

	public void setProAdderss(String proAdderss) {
		this.proAdderss = proAdderss;
	}

	public String getProFax() {
		return proFax;
	}

	public void setProFax(String proFax) {
		this.proFax = proFax;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(Integer modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Provider(Integer id, String proCode, String proName, String proDesc,
			String proContact, String proPhone, String proAdderss,
			String proFax, Integer createdBy, Date creationDate,
			Integer modifyBy, Date modifyDate, PageUtil pageUtil) {
		super();
		this.id = id;
		this.proCode = proCode;
		this.proName = proName;
		this.proDesc = proDesc;
		this.proContact = proContact;
		this.proPhone = proPhone;
		this.proAdderss = proAdderss;
		this.proFax = proFax;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.modifyBy = modifyBy;
		this.modifyDate = modifyDate;
		this.pageUtil = pageUtil;
	}

	public Provider() {
		super();
	}

	@Override
	public String toString() {
		return "Provider [id=" + id + ", proCode=" + proCode + ", proName="
				+ proName + ", proDesc=" + proDesc + ", proContact="
				+ proContact + ", proPhone=" + proPhone + ", proAdderss="
				+ proAdderss + ", proFax=" + proFax + ", createdBy="
				+ createdBy + ", creationDate=" + creationDate + ", modifyBy="
				+ modifyBy + ", modifyDate=" + modifyDate + ", pageUtil="
				+ pageUtil + "]";
	}

}
