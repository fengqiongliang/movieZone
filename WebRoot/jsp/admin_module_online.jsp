<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>


<c:forEach var="movie"  items="${movies.data}"  varStatus="status">
<tr hoverClass="adminTrHover">
	<td>${movie.modmvid}</td>
	<td style="text-align:left;padding-left:10px"><a href="${base}/content.do?id=${movie.movieid}"  target="_blank">${movie.name}</a></td>
	<td>${movie.broswer}</td>
	<td>${movie.download}</td>
	<td>
		<span class="adminAction" onclick="mvModuleMv(this,'up')">上移</span> 
		<span class="adminAction" onclick="mvModuleMv(this,'down')">下移</span> 
		<span class="adminAction" onclick="delModuleMv(this,${movie.modmvid})">删除</span>
	</td>
</tr>
</c:forEach>
<tr valign="middle" height="40" class="evenTr"><td style="text-align:right;background:#F1F8FE" colspan="20"><span style="color:gray">${movies.pageNo}/${movies.pageTotal} 共${movies.total}条   &nbsp&nbsp</span><c:if test="${!movies.isfirstPage}"><input type="button" onclick="queryModuleMvs(this,'up')" value="&lt;上一页" hoverclass="adminBtnHover" class="adminBtn" style="width:60px" /></c:if><c:if test="${!movies.islastPage}"><input type="button" onclick="queryModuleMvs(this,'down')" value="下一页&gt;" hoverclass="adminBtnHover" class="adminBtn" style="width:60px" /></c:if></td></tr>
