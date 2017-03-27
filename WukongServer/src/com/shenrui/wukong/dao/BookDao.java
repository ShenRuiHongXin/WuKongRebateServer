package com.shenrui.wukong.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.shenrui.wukong.vo.Book;

@Repository("bookDao")
public class BookDao{
	//定义hibernate模板
	@Autowired
	@Qualifier("hibernateTemplate")
	private HibernateTemplate hibernateTemplate; 

	/**
	 * 增
	 * @param book
	 */
	public void save(Book book) {
		System.out.println("Dao层保存图书.....");
		hibernateTemplate.save(book);
	}
	
	/**
	 * 删
	 * @param book
	 */
	public void delete(Book book){
	}
	
	/**
	 * 改
	 * @param book
	 */
	public void update(Book book){
	}
	
	/**
	 * 根据ID查找
	 * @param id
	 */
	public void findById(int id){
	}

}
