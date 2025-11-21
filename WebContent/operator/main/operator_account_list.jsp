<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h3>運営者アカウント一覧</h3>
    	<table>
      		<thead>
        		<tr>
        			<th>ID</th>
        			<th>権限</th>
        			<th>状態</th>
        			<th>作成日</th>
        			<th>更新日</th>
        			<th>操作</th>
        		</tr>
      		</thead>
      		<thead>
        		<tr>
          			<td>○○</td>
          			<td>運営者、管理者</td>
          			<td><span class="tag ok">有効</span> : <span class="tag ng">無効</span></td>
          			<td>12月12日00:12</td>
          			<td>12月12日00:12</td>
          			<td><a class="btn secondary" href="#">管理</a></td>
       			</tr>
      		</thead>
    	</table>
	</c:param>
</c:import>