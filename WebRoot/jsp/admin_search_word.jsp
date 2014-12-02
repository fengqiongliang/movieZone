<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>


<c:forEach var="word" items="${words.data}"  varStatus="status">
<tr hoverClass="adminTrHover">
	<td>${word.id}</td>
	<td style="text-align:left;padding-left:10px">${word.keyword}</td>
	<td>${word.strCreatetime}</td>
	<td>
		<span class="adminAction" onclick="unSceneCmmt(this,${word.id})">删除</span>
	</td>
</tr>
</c:forEach>
<tr height="40" valign="middle"><td colspan="20" style="text-align:right;background:#F1F8FE">${words.pageNo}/${words.pageTotal} 共${words.total}条   &nbsp&nbsp<c:if test="${!words.isfirstPage}"><input  style="width:60px" class="adminBtn" hoverClass="adminBtnHover" type="button" value="<上一页" onclick="adminQuery(this,'up')"></input></c:if><c:if test="${!words.islastPage}"><input style="width:60px" class="adminBtn" hoverClass="adminBtnHover" type="button" value="下一页>" onclick="adminQuery(this,'down')"></input></c:if></td></tr>
