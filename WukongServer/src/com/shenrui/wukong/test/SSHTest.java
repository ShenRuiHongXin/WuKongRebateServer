package com.shenrui.wukong.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shenrui.wukong.service.BookService;
import com.shenrui.wukong.vo.Book;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SSHTest {
	@Autowired
	@Qualifier("bookService")
	private BookService bookService;
	
	@Test
	public void add(){
		Book book = new Book();
		book.setName("Java核心技术");
		book.setPrice(75);
		bookService.add(book);
	}
	//2017年3月28日09:53:50

}
