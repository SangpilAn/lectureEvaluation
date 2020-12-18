<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<section class="container mt-3" style="max-width: 560px;">
		<form method="post" action="userReg">
			<div class="form-group">
				<label>아이디</label>
				<input type="text" name="userID" id="userID"class="form-control">
			</div>
			<div class="form-group">
				<label>비밀번호</label>
				<input type="password" name="userPassword" id="userPassword" class="form-control">
			</div>
			<div class="form-group">
				<label>이메일</label>
				<input type="email" name="userEmail" id="userEmail" class="form-control">
			</div>
			<button type="submit" class="btn btn-primary" id="subBtn">회원가입</button>
		</form>
	</section>