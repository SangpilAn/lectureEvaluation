<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="../../index">강의평가 웹 사이트</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active">
					<a class="nav-link active" href="../../index">메인</a>
				</li>
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown">
						회원관리
					</a>
					<div class="dropdown-menu" aria-labelledby="dropdown">
						<a class="dropdown-item" href="userLogin">로그인</a>
						<a class="dropdown-item" href="userReg">회원가입</a>					
						<a class="dropdown-item" href="userLogout.jsp">로그아웃</a>
					</div>
				</li>
			</ul>
			<form action="../../index" method="get" class="form-inline my-2 my-lg-0">
				<input type="text" name="search" class="form-control mr-sm-2" type="search" placeholder="내용을 입력하세요." aria-label="Search">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">검색</button>
			</form>
		</div>
	</nav>