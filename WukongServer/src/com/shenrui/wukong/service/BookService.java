package com.shenrui.wukong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shenrui.wukong.dao.BookDao;
import com.shenrui.wukong.vo.Book;

@Transactional
@Service("bookService")
public class BookService {
	//在Service中注入Dao
	@Autowired
	@Qualifier("bookDao")
	private BookDao bookDao;
	
	/**
	 * 增
	 * @param book
	 */
	public void add(Book book) {
		System.out.println("Service层保存图书.....");
		bookDao.save(book);
	}
	
	/**
	 * 删
	 * @param book
	 */
	public void delete(Book book){
		bookDao.delete(book);
	}
	
	/**
	 * 改
	 * @param book
	 */
	public void update(Book book){
		bookDao.update(book);
	}
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	public void findById(int id){
//		return bookDao.findById(id);
	}

}
