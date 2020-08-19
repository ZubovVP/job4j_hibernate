<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 10.08.2020
  Time: 22:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.time.Year" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>UpdateOffer</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Popper JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
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

    .update {
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
<script>
    function validateElements() {
        var result = true;
        if ($('#mark').val() === '') {
            result = false;
            alert('Please, fill mark');
        }
        if ($('#price').val() === '') {
            result = false;
            alert('Please, fill your price');
        }
        if (result) {
            updateOffer();
        }
        return result;
    }

    function updateOffer() {
        var mark = $('#mark').val();
        var price = $('#price').val();
        var category = $('#category').val();
        var year_of_issue = $('#year_of_issue').val();
        var type_body = $('#type_body').val();
        var transmission = $('#transmission').val();
        var id = $('#id').val();
        var idUser = $('#idUser').val();
        $.ajax({
            type: 'POST',
            url: "${pageContext.servletContext.contextPath}/updateOffer",
            data: {
                mark: mark,
                price: price,
                category: category,
                year_of_issue: year_of_issue,
                type_body: type_body,
                transmission: transmission,
                id: id,
                idUser: idUser
            },
            dataType: 'application/json',
            success: function (data) {
                console.log(JSON.parse(data.responseText));
            }
        });
    }
</script>
<body>
<div class="display-4">
    Update offer
</div>
<br>
<table border="2">
    <tr class="header_table">
        <td>Category</td>
        <td>Mark</td>
        <td>Year of issue</td>
        <td>Type of body</td>
        <td>Transmission</td>
        <td>Price</td>
    </tr>
    <form id="update" height:="25px" action="${pageContext.servletContext.contextPath}/start" method="GET"
          onsubmit="return validateElements();">
        <td> category : <label>
            <select name="category" id="category">
                <option value="car">car</option>
                <option value="track">track</option>
            </select>
        </label>
        </td>
        <td> mark : <input type="text" id="mark" name="mark" value="<%=request.getParameter("markI")%>"></td>
        <td> year of issue : <input type="number" min="1800" max="<%=Year.now().getValue()%>"
                                    value="<%=request.getParameter("yearOfIssue")%>" id="year_of_issue"
                                    name="year_of_issue"></td>
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
        <td> price : <input type="text" id="price" name="price" value="<%=request.getParameter("priceI")%>"></td>
        <input type="hidden" id="id" name="id" value="<%=request.getParameter("idI")%>">
        <input type="hidden" id="idUser" name="idUser" value="<%=request.getParameter("idUser")%>">
    </form>
</table>
<br>
<input type="submit" form="update" class="update" value="Update">
</body>
</html>
