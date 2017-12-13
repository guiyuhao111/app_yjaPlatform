<!DOCTYPE html>
<html>

<head>
    <%@ page language="java" contentType="text/html; charset=utf-8"
             pageEncoding="utf-8"%>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0" />
    <meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
    <%--<script type="text/javascript" src="js/jquery.form.js"></script>--%>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <title>姚江贷</title>
    <style>
        html,
        body {
            padding: 0;
            margin: 0;
        }

        .header {
            margin: 0 62px;
            padding: 10px 0;
            overflow: hidden;
        }

        .avater {
            float: left;
        }

        .avater img {
            width: 5.5rem;
            height: 5.5rem;
        }

        .header-content {
            margin-left: 4.5rem;
        }
        .header-content2 {
            margin-left: 6.5rem;
        }

        .title {
            font-size: 0.85rem;
            color: #000;
            font-weight: bold;
        }

        .desc {
            font-size: 0.65rem;
            color: gray;
        }

        .openBtn {
            float: right;
            border: solid green 1px;
            padding: 5px;
            font-size: 0.65rem;
            border-radius: 5px;
            color: green;
        }

        .openBtn3 {
            float: right;
            border: solid #bfbbbb 1px;
            padding: 5px;
            font-size: 0.65rem;
            border-radius: 5px;
            width: 2.5rem;
            text-align: center;
            margin-left: 0.25rem;
        }

        .middle {
            margin: 20px;
        }

        .middle-txt {
            font-size: 0.65rem;
            color: #c3bfbf;
            line-height: 1.5rem;
        }

        .middle-txt p {
            margin: 0;
        }

        .openBtn2 {
            padding: 10px;
            text-align: center;
            border-radius: 20px;
            background-color: #009688;
            color: #fff;
            margin: 10px;
        }

        .footer {
            margin-top: 20px;
        }

        .footer ul {
            padding: 0;
            margin: 10px;
        }

        ul li {
            list-style-type: none;
        }

        .list img {
            width: 4rem;
            height: 4rem;
            float: left;
            border-radius: 10px;
        }

        .tuijian {
            margin: 0 10px;
            border-bottom: solid #f5efef 1px;
            padding: 10px 0;
            font-size: 1rem;
        }

        .list {
            overflow: hidden;
            padding: 10px 0;
            border-bottom: solid #f5efef 1px;
        }

        .list:last-child {
            border-bottom: 0;
        }

        .title2 {
            font-size: 1.65rem;
            text-align: center;
            margin: 20px 0;
        }
    </style>

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

    <script type="text/javascript">
        $(document).ready(function () {
            var url="findPictureById.do";
            var params={
                id:"<%=session.getAttribute("id") %>"
            }
            $.get(url,params,function (result) {
                console.log(result);
                $("#title").html(result.data.title);
                $("#content").html(result.data.content);
                $("#img").attr("src",result.data.img);
            });
        })
    </script>

</head>

<body>
<div class="header">
    <div class="title2" id="title"></div>
    <div class="avater">
        <img id="img"/>
    </div>
    <div class="header-content2">
        <div class="desc" id="content"></div>
    </div>
</div>
<div class="middle" id="moblink-href">
    <div class="openBtn2" onclick="updateData()">立即申请</div>
</div>
<div class="footer">
    <div class="tuijian">热门推荐</div>
    <ul>
        <li class="list">
            <img src="qrIndex/img/01.png" />
            <div class="header-content">
                <div class="title">平安银行宅e贷</div>
                <div class="desc">全国范围10-500万利息5厘起</div>
            </div>
        </li>
        <li class="list">
            <img src="qrIndex/img/02.png" />
            <div class="header-content">
                <div class="title">国信车贷</div>
                <div class="desc">押车月利息仅两分</div>
            </div>
        </li>
        <li class="list">
            <img src="qrIndex/img/03.png" />
            <div class="header-content">
                <div class="title">工薪贷</div>
                <div class="desc">打卡满六个月即可贷款</div>
            </div>
        </li>
        <li class="list">
            <img src="qrIndex/img/05.png" />
            <div class="header-content">
                <div class="title">银行转贷</div>
                <div class="desc">抵押类转贷 信用类转贷</div>
            </div>
        </li>
    </ul>
</div>
</body>

</html>