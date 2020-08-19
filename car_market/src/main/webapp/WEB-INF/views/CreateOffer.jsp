<%@ page import="java.time.Year" %><%--
  Created by Intellij IDEA.
  User: Vitaly Zubov
  Email: Zubov.VP@yandex.ru
  Version: $Id$
  Date: 05.02.2019
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>CreateOffer</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Popper JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>
<style type="text/css">
    body {
        margin: 0;
        background: url("../view.jpg") no-repeat center center fixed;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
    }

    .display-4 {
        text-align: center;
    }

    .create {
        width: 150px;
        height: 40px;
        border-radius: 20px;
        background: #459DE5;
        color: #fff;
        font-size: 18px;
        cursor: pointer;
        position: relative;
        left: 50%;
        transform: translate(-50%, 0);
    }

    table {
        font-size: larger;
        border: black;
        background-color: white;
        margin: auto;
        width: 85%;
    }

    .header_table {
        color: black;
        font-weight: bold;
        text-align: center;
    }

</style>
<div class="display-4">
    New offer
</div>
<br>
<table border="2">
    <tr class="header_table">
        <td>category</td>
        <td>mark</td>
        <td>year of issue</td>
        <td>type of body</td>
        <td>transmission</td>
        <td>price</td>
    </tr>
    <form id="create" height:="25px" enctype="multipart/form-data" method="post" action="createOffer">
        <td> category : <label>
            <select name="category" id="category">
                <option value="car">car</option>
                <option value="track">track</option>
            </select>
        </label>
        </td>
        <td> mark : <input type="text" id="mark" name="mark" placeholder="Your mark"></td>
        <td> year of issue : <input type="number" min="1800" max="<%=Year.now().getValue()%>"
                                    value="<%=Year.now().getValue()%>" id="year_of_issue" name="year_of_issue"></td>
        <td> type of body : <label>
            <select name="type_body" id="type_body">
                <option value="Saloon">Saloon</option>
                <option value="Hatchback">Hatchback</option>
                <option value="Estate">Estate</option>
                <option value="Coupe">Coupe</option>
                <option value="Pickup">Pickup</option>
                <option value="Van">Van</option>
                <option value="Sedan">Sedan</option>
            </select>
        </label>
        </td>
        <td> transmission : <label>
            <select name="transmission" id="transmission">
                <option value="manual gearbox">manual gearbox</option>
                <option value="automatic">automatic</option>
                <option value="robot">robot</option>
                <option value="CVT">CVT</option>
            </select>
        </label>
        </td>
            <td> price : <input type="text" id="price" name="price" placeholder="Your price"></td>
    </form>
</table>
<br>
<div align="left">
    <p><b>Photos your car :</b><br>
    <p><input type="file" name="file" id="file" multiple accept="image/*" form="create"></p>
</div>
<button type="submit" class="create" form="create">Create</button>
</body>
</html>