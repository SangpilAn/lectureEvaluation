<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initail-scale=1,shrink-to-fit=no">
	<title></title>
	<link rel="stylesheet" href="/css/bootstrap.min.css">
	<link rel="stylesheet" href="/css/custom.css">
</head>
<body>

	<!-- nav -->
	<tiles:insertAttribute name="nav" />
	
	<!-- main -->
	<tiles:insertAttribute name="body" />
	
	<!-- 등록 modal -->
	<tiles:insertAttribute name="evaluationModal" />
	
	<!-- 신고 modal -->
	<tiles:insertAttribute name="reportModal" />
	
	<!-- footer -->
	<tiles:insertAttribute name="footer" />
	
	<!-- 제이쿼리 -->
	<script type="text/javascript" src="/js/jquery.min.js"></script>
	<script type="text/javascript" src="/js/popper.min.js"></script>
	<script type="text/javascript" src="/js/bootstrap.min.js"></script>
</body>
</html>