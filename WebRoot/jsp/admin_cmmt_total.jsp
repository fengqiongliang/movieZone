<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>


<c:forEach var="cmmt" items="${sceneCmmts.data}"  varStatus="status">
<tr hoverClass="adminTrHover">
	<td>${cmmt.commentid}</td>
	<td style="text-align:left;padding-left:10px">${cmmt.user.nickname}</td>
	<td>${cmmt.user.userid}</td>
	<td>${cmmt.user.createarea}</td>
	<td style="text-align:left;padding-left:10px"><a href="${base}/content.do?id=${cmmt.movie.movieid}"  target="_blank">${cmmt.movie.name}</a></td>
	<td>${cmmt.movie.movieid}</td>
	<td>${cmmt.movie.type}</td>
	<td>${cmmt.movie.download}</td>
	<td style="text-align:left;padding-left:10px" title="${cmmt.content}">${cmmt.content}</td>
	<td>${cmmt.strCreatetime}</td>
	<td>
		<c:if test="${cmmt.orderseq < 0}"><span class="adminAction" onclick="sceneCmmt(this,${cmmt.commentid})">精选</span></c:if>
		<c:if test="${cmmt.orderseq > 0}"><span style="color:gray">精选</span></c:if>
		<span class="adminAction" onclick="delCmmt(this,${cmmt.commentid})">删除</span>
	</td>
</tr>
</c:forEach>
<tr height="40" valign="middle"><td colspan="20" style="text-align:right;background:#F1F8FE">${sceneCmmts.pageNo}/${sceneCmmts.pageTotal} 共${sceneCmmts.total}条   &nbsp&nbsp<c:if test="${!sceneCmmts.isfirstPage}"><input  style="width:60px" class="adminBtn" hoverClass="adminBtnHover" type="button" value="<上一页" onclick="adminQuery(this,'up')"></input></c:if><c:if test="${!sceneCmmts.islastPage}"><input style="width:60px" class="adminBtn" hoverClass="adminBtnHover" type="button" value="下一页>" onclick="adminQuery(this,'down')"></input></c:if></td></tr>
