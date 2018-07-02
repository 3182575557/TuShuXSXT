package com.bookshop.common.pojo;

import java.io.Serializable;


public class OrderPojo implements Serializable {
	private String orderId;
	private String userId;
	private String status;
	private String paymentType;
	private String createTime;
	private String paymentTime;
	private String consignTime;
	private long postFee;
	private String num;
	private String bookId;
	private long totalFee;
	private String receiverMobile;
	private String receiverState;
	private String receiverCity;
	private String receiverDistrict;
	private String receiverAddress;
	private String receiverName;
	private String shippingName;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getConsignTime() {
		return consignTime;
	}
	public void setConsignTime(String consignTime) {
		this.consignTime = consignTime;
	}
	public long getPostFee() {
		return postFee;
	}
	public void setPostFee(long postFee) {
		this.postFee = postFee;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public long getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(long totalFee) {
		this.totalFee = totalFee;
	}
	public String getReceiverMobile() {
		return receiverMobile;
	}
	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	public String getReceiverState() {
		return receiverState;
	}
	public void setReceiverState(String receiverState) {
		this.receiverState = receiverState;
	}
	public String getReceiverCity() {
		return receiverCity;
	}
	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}
	public String getReceiverDistrict() {
		return receiverDistrict;
	}
	public void setReceiverDistrict(String receiverDistrict) {
		this.receiverDistrict = receiverDistrict;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getShippingName() {
		return shippingName;
	}
	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}
	@Override
	public String toString() {
		return "OrderPojo [orderId=" + orderId + ", userId=" + userId + ", status=" + status + ", paymentType="
				+ paymentType + ", createTime=" + createTime + ", paymentTime=" + paymentTime + ", consignTime="
				+ consignTime + ", postFee=" + postFee + ", num=" + num + ", bookId=" + bookId + ", totalFee="
				+ totalFee + ", receiverMobile=" + receiverMobile + ", receiverState=" + receiverState
				+ ", receiverCity=" + receiverCity + ", receiverDistrict=" + receiverDistrict + ", receiverAddress="
				+ receiverAddress + ", receiverName=" + receiverName + ", shippingName=" + shippingName
				+ ", getOrderId()=" + getOrderId() + ", getUserId()=" + getUserId() + ", getStatus()=" + getStatus()
				+ ", getPaymentType()=" + getPaymentType() + ", getCreateTime()=" + getCreateTime()
				+ ", getPaymentTime()=" + getPaymentTime() + ", getConsignTime()=" + getConsignTime()
				+ ", getPostFee()=" + getPostFee() + ", getNum()=" + getNum() + ", getBookId()=" + getBookId()
				+ ", getTotalFee()=" + getTotalFee() + ", getReceiverMobile()=" + getReceiverMobile()
				+ ", getReceiverState()=" + getReceiverState() + ", getReceiverCity()=" + getReceiverCity()
				+ ", getReceiverDistrict()=" + getReceiverDistrict() + ", getReceiverAddress()=" + getReceiverAddress()
				+ ", getReceiverName()=" + getReceiverName() + ", getShippingName()=" + getShippingName()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
}
