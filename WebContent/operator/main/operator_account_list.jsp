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
            <tbody>
                <!-- OperatorAccountListAction でセットされた operators をループ -->
                <c:forEach var="op" items="${operators}">
                    <tr>
                        <td>${op.id}</td>
                        <td>
                            <c:choose>
                                <c:when test="${op.admin}">管理者</c:when>
                                <c:otherwise>運営者</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${op.enable}">
                                    <span class="tag ok">有効</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="tag ng">無効</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <!-- LocalDateTime -->
                            ${op.createdAt}
                        </td>
                        <td>
                            <!-- LocalDateTime -->
                            ${op.updatedAt}
                        </td>
                        <td>
                            <!-- 管理メニューへ遷移 -->
                            <a class="btn secondary" href="OperatorAccountManage.action?id=${op.id}">管理</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:param>
</c:import>