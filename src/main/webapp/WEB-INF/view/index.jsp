<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<section class="container">
		<form method="get" action="index" class="form-inline mt-3">
			<select name="lectureDivide" class="form-control mx-1 mt-2">
				<option value="전체" <c:if test="${lectureDivide_m=='전체'|| lectureDivide_m==null}">selected</c:if>>전체</option>
				<option value="전공" <c:if test="${lectureDivide_m=='전공'}">selected</c:if>>전공</option>
				<option value="교양" <c:if test="${lectureDivide_m=='교양'}">selected</c:if>>교양</option>
				<option value="기타" <c:if test="${lectureDivide_m=='기타'}">selected</c:if>>기타</option>
			</select>
			<select name="searchType" class="form-control mx-1 mt-2">
				<option value="최신순" <c:if test="${searchType_m=='최신순'|| searchType_m==null}">selected</c:if>>최신순</option>
				<option value="추천순" <c:if test="${searchType_m=='추천순'}">selected</c:if>>추천순</option>
			</select>
			<input type="text" name="search" class="form-control mx-1 mt-2" placeholder="내용을 입력하세요.">
			<button type="submit" class="btn btn-primary mx-1 mt-2">검색</button>
			<a class="btn btn-primary mx-1 mt-2" data-toggle="modal" href="#registerModal">등록하기</a>
			<a class="btn btn-danger mx-1 mt-2" data-toggle="modal" href="#reportModal">신고하기</a>
		</form>
		
		<c:forEach var="n" items="${list }" begin="0" end="4" step="1" >
		<div class="card bg-light mt-3">
			<div class="card-header bg-light">
				<div class="row">
					<div class="col-8 text-left">${n.lectureName }&nbsp;<small>${n.professorName }</small></div>
					<div class="col-4 text-right">
						종합<span style="color: red;">${n.totalScore }</span>
					</div>
				</div>
			</div>
			<div class="card-body">
				<h5 class="card-title">
					${n.evaluationTitle }&nbsp;<small>(${n.lectureYear }년 ${n.semesterDivide })</small>
				</h5>
				<p class="card-text">${n.evaluationContent }</p>
				<div class="row">
					<div class="col-9 text-left">
						성적<span style="color: red;">${n.creditScore }</span>
						널널<span style="color: red;">${n.comfortableScore }</span>
						강의<span style="color: red;">${n.lectureScore }</span>
						<span style="color: green;">(추천: ${n.likeCount})</span>
					</div>
					<div class="col-3 text-right">
						<a onclick="return confirm('추천하시겠습니까?')" href="./likeAction.jsp?evaluationID=평가ID">추천</a>
						<a onclick="return confirm('삭제하시겠습니까?')" href="./deleteAction.jsp?evaluationID=평가ID">삭제</a>
					</div>
				</div>
			</div>
		</div>
		</c:forEach>
	</section>
	
	<ul class="pagination justify-content-center mt-3">
		<li class="page-item">
		<c:choose>
			<c:when test="${pageNum<=0||pageNum==null }">
				<a class="page-link disabled">이전</a>
			</c:when>
			<c:otherwise>
				<a class="page-link" href="index?lectureDivide=${lectureDivide_m}&searchType=${searchType_m}&search=${search_m}&pageNum=${pageNum-1 }">이전</a>
			</c:otherwise>
		</c:choose>
		</li>
		<c:choose>
			<c:when test="${size-1>0 }">
				<c:forEach begin="0" end="${size-1 }" step="1" var="n">
					<c:choose>
						<c:when test="${pageNum==n||pageNum==null }">
							<li class="page-item">
								<a class="page-link" >${n+1 }</a>	
							</li>
						</c:when>
						<c:otherwise>
							<li class="page-item">
								<a class="page-link" href="index?lectureDivide=${lectureDivide_m}&searchType=${searchType_m}&search=${search_m}&pageNum=${n }">${n+1 }</a>	
							</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</c:when>
			<c:when test="${size==0 }"></c:when>
			<c:otherwise>
				<li class="page-item">
					<a class="page-link" >1</a>	
				</li>
			</c:otherwise>
		</c:choose>
		<li class="page-item">
		<c:choose>
			<c:when test="${list.size()<6 }">
				<a class="page-link disabled">다음</a>
			</c:when>
			<c:otherwise>
				<a class="page-link" href="index?lectureDivide=${lectureDivide_m}&searchType=${searchType_m}&search=${search_m}&pageNum=${pageNum+1 }">다음</a>
			</c:otherwise>
		</c:choose>
		</li>
	</ul>