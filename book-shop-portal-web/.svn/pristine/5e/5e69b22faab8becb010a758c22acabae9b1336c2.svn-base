package com.bookshop.book.pojo;

import com.bookshop.pojo.TbBook;

public class Book extends TbBook {
	public Book(){
		
	}
	public Book(TbBook tbBook){
		//初始化属性
		this.setId(tbBook.getId());
		this.setTitle(tbBook.getTitle());
		this.setSellPoint(tbBook.getSellPoint());
		this.setPrice(tbBook.getPrice());
		this.setNum(tbBook.getNum());
		this.setBarcode(tbBook.getBarcode());
		this.setImage(tbBook.getImage());
		this.setCid(tbBook.getCid());
		this.setStatus(tbBook.getStatus());
		this.setCreated(tbBook.getCreated());
		this.setUpdated(tbBook.getUpdated());
	}
	
	public String[] getImages(){
		String image2 = this.getImage();
		if(image2!=null&&!"".equals(image2)){
			String[] strings = image2.split(",");
			return strings;
		}
		return null;
	}
	
}
