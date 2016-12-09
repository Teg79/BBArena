<%@ page import="bbarena.server.MatchEndpoint" %>
<%@ page import="javax.websocket.Session" %><%
for (Session s : MatchEndpoint.SESSIONS) {
    s.getAsyncRemote().sendText(request.getParameter("msg"));
}
%>
