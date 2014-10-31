<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>


<c:forEach var="wrapper" items="${wrappers.data}"  varStatus="status">
<tr hoverClass="adminTrHover">
	<td>${wrapper.movie.movieid}</td>
	<td style="text-align:left;padding-left:10px"><a href="${base}/content.do?id=${wrapper.movie.movieid}">${wrapper.movie.name}</a></td>
	<td>${wrapper.movie.type}</td>
	<td>${wrapper.movie.score}</td>
	<td>${wrapper.movie.favorite}</td>
	<td>${wrapper.movie.download}</td>
	<td>${wrapper.movie.broswer}</td>
	<td>${wrapper.movie.approve}</td>
	<td>${wrapper.cmmtCount}</td>
	<td>${wrapper.strPubTime}</td>
	<td style="text-align:left;padding-left:10px" title="<c:forEach var="module" items="${wrapper.modules}">${module.modname}&#10;</c:forEach>">
		<c:forEach var="module" items="${wrapper.modules}"  varStatus="status" begin="0" end="3">
			${module.modname}<br/>
		</c:forEach>
		<c:if test="${fn:length(wrapper.modules)>4}">...</c:if>
	</td>
	<td>修改 删除</td>
</tr>
</c:forEach>
<tr height="40" valign="middle"><td colspan="20" style="text-align:right;background:#F1F8FE">${wrappers.pageNo}/${wrappers.pageTotal} 共${wrappers.total}条   &nbsp&nbsp<c:if test="${!wrappers.isfirstPage}"><input  style="width:60px" class="adminBtn" hoverClass="adminBtnHover" type="button" value="<上一页" onclick="adminQuery(this,'up')"></input></c:if><c:if test="${!wrappers.islastPage}"><input style="width:60px" class="adminBtn" hoverClass="adminBtnHover" type="button" value="下一页>" onclick="adminQuery(this,'down')"></input></c:if></td></tr>
