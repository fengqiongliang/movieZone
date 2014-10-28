<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>


<c:forEach var="mv" items="${movies.data}"  varStatus="status">
<tr hoverClass="adminTrHover"><td>${mv.movieid}</td><td style="text-align:left;padding-left:10px">${mv.name}</td><td>${mv.type}</td><td>${mv.score}</td><td>${mv.favorite}</td><td>${mv.download}</td><td>${mv.broswer}</td><td>${mv.approve}</td><td>21</td><td>${mv.publishtime}</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
</c:forEach>
<tr height="40" valign="middle"><td colspan="20" style="text-align:right;background:#F1F8FE">${movies.pageNo}/${movies.pageTotal} 共${movies.total}条   &nbsp&nbsp<c:if test="${!movies.isfirstPage}"><input  style="width:60px" class="adminBtn" hoverClass="adminBtnHover" type="button" value="<上一页" onclick="adminQuery(this,'up')"></input></c:if><c:if test="${!movies.islastPage}"><input style="width:60px" class="adminBtn" hoverClass="adminBtnHover" type="button" value="下一页>" onclick="adminQuery(this,'down')"></input></c:if></td></tr>
