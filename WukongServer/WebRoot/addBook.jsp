<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>My JSP 'addBook.jsp' starting page</title>

</head>

<body>
<h1>添加图书</h1>
<s:form action="book_add" namespace="/" method="post" theme="simple">
	图书名称：<s:textfield name="name"/><br/>
	图书价格：<s:textfield name="price"/><br/>
	<s:submit value="添加图书"/>
</s:form>
</body>
</html>
