<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login</title>
</head>
<body>
<div align=center>
<h1>User Login</h1>
</div>
<form action=LoginServlet method=post>
<table align=center>
<tr><td>Enter name:</td><td><input type=text name=txtName></td></tr>
<tr><td>Enter password:</td><td><input type=password name=txtPwd></td></tr>
<tr><td><input type=submit value=login></td><td><input type=reset></td></tr>
</table>
</form>
</body>
</html>

