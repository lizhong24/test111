package cn.smbms.bean;

import java.math.BigDecimal;
import java.util.Date;

public class Bill {
	private Integer id;// id
	private String billCode;// 账单编码
	private String productName;// 商品名称
	private String productDesc;// 商品描述
	private String productUnit;// 商品单位
	private BigDecimal productCount;// 商品数量
	private BigDecimal totalPrice;// 总金额
	private Integer isPayment;// 是否支付
	private Integer providerId;// 供应商ID
	private Date creationDate;// 创建时间
	private Integer createdBy;// 创建者
	private Date modifyDate;// 更新时间
	private Integer modifyBy;// 更新者
	private String providerName; // 供应商名称

	public Bill() {
		super();
	}

	public Bill(Integer id, String billCode, String productName,
			String productDesc, String productUnit, BigDecimal productCount,
			BigDecimal totalPrice, Integer isPayment, Integer providerId,
			Date creationDate, Integer createdBy, Date modifyDate,
			Integer modifyBy, String providerName) {
		super();
		this.id = id;
		this.billCode = billCode;
		this.productName = productName;
		this.productDesc = productDesc;
		this.productUnit = productUnit;
		this.productCount = productCount;
		this.totalPrice = totalPrice;
		this.isPayment = isPayment;
		this.providerId = providerId;
		this.creationDate = creationDate;
		this.createdBy = createdBy;
		this.modifyDate = modifyDate;
		this.modifyBy = modifyBy;
		this.providerName = providerName;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}

	public BigDecimal getProductCount() {
		return productCount;
	}

	public void setProductCount(BigDecimal productCount) {
		this.productCount = productCount;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getIsPayment() {
		return isPayment;
	}

	public void setIsPayment(Integer isPayment) {
		this.isPayment = isPayment;
	}

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Integer getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(Integer modifyBy) {
		this.modifyBy = modifyBy;
	}

	@Override
	public String toString() {
		return "Bill [id=" + id + ", billCode=" + billCode + ", productName="
				+ productName + ", productDesc=" + productDesc
				+ ", productUnit=" + productUnit + ", productCount="
				+ productCount + ", totalPrice=" + totalPrice + ", isPayment="
				+ isPayment + ", providerId=" + providerId + ", creationDate="
				+ creationDate + ", createdBy=" + createdBy + ", modifyDate="
				+ modifyDate + ", modifyBy=" + modifyBy + ", providerName="
				+ providerName + "]";
	}

}