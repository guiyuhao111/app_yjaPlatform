<%@ page import="org.springframework.web.bind.annotation.SessionAttribute" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ page language="java" contentType="text/html; charset=utf-8"
             pageEncoding="utf-8"%>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>无标题文档</title>

</head>
<script type="text/javascript" id="-moblink-js" src="//f.moblink.mob.com/moblink.js?appkey=21df97c376228"></script>

<script type="text/javascript">
    MobLink.init({
        showDefaultUI: false,
        initCallback: function(){
        }
    });
</script>
<script type="text/javascript">
    function updateData() {
        console.log(111)
        MobLink.updateScheme({
            belongId: "<%=session.getAttribute("belongId") %>",
            id: "<%=session.getAttribute("id") %>",
            state: "<%=session.getAttribute("state") %>"
        });
    }
</script>
<body>
    <div id="moblink-href">
        <p id="openAppBtn" onclick="updateData()">正在前往app...</p>
    </div>
</body>
</html>
