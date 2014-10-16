<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>


<c:forEach var="item" items="${normalUsers.data}" begin="0" end="24" varStatus="status">
<tr hoverClass="adminTrHover"><td>${item.userid}</td><td style="text-align:left;padding-left:10px">${item.nickname}</td><td>${item.createarea}</td><td style="text-align:left;padding-left:10px">${item.createip}</td><td>${item.nextnick}</td><td><c:if test="${item.nextface != null}"><img src="./${item.nextface}" /></c:if></td><td><span class="adminAction" onclick="permitModify(this,${item.userid})">批准修改</span> 跟踪 <span class="adminAction" onclick="addNormalForbit(this,${item.userid})">禁止</span> <span class="adminAction" onclick="delNormalUser(this,${item.userid})">删除</span></td></tr>
</c:forEach>
<tr height="40" valign="middle"><td colspan="20" style="text-align:right;background:#F1F8FE">${normalUsers.pageNo}/${normalUsers.pageTotal} 共${normalUsers.total}条 &nbsp&nbsp<c:if test="${!normalUsers.isfirstPage}"><input  style="width:60px" class="adminBtn" hoverClass="adminBtnHover" type="button" value="<上一页" onclick="adminQuery(this,'up')"></input></c:if><c:if test="${!normalUsers.islastPage}"><input style="width:60px" class="adminBtn" hoverClass="adminBtnHover" type="button" value="下一页>" onclick="adminQuery(this,'down')"></input></c:if></td></tr>
