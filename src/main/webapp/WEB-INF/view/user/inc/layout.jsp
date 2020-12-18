<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initail-scale=1,shrink-to-fit=no">
	<title><tiles:getAsString name="title"/></title>
	<link rel="stylesheet" href="/css/bootstrap.min.css">
	<link rel="stylesheet" href="/css/custom.css">
</head>
<body>

	<!-- nav -->
	<tiles:insertAttribute name="nav" />
	
	<!-- main -->
	<tiles:insertAttribute name="body" />
	
	<!-- footer -->
	<tiles:insertAttribute name="footer" />
	
	<!-- 제이쿼리 -->
	<script type="text/javascript" src="/js/jquery.min.js"></script>
	<script type="text/javascript" src="/js/popper.min.js"></script>
	<script type="text/javascript" src="/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/js/loginValid.js"></script>
</body>
</html>