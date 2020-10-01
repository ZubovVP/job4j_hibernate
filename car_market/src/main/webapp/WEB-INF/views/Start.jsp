<%@ page import="java.time.Year" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Start</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Popper JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
    <script>
        function deleteElements() {
            $("#table tr:gt(0)").remove();
        }

        function updateItems() {
            $.ajax('http://localhost:8080/getCars', {
                    method: 'GET',
                    contentType: 'application/x-www-form-urlencoded',
                    dataType: 'html',
                    complete: function (data) {
                        deleteElements();
                        var carOffers = JSON.parse(data.responseText);
                        fiilTable(carOffers);
                    }
                }
            );
        }

        function fiilTable(carOffers) {
            var id = <%=session.getAttribute("id")%>;
            for (var i = 0; i < carOffers.length; i++) {
                var idI = carOffers[i].id;
                var markI = carOffers[i].mark;
                var yearOfIssue = carOffers[i].yearOfIssue;
                var typeBody = carOffers[i].typeBody;
                var transmission = carOffers[i].transmission;
                var category = carOffers[i].category;
                var price = carOffers[i].price;
                var userId = carOffers[i].user.id;
                var userName = carOffers[i].user.name;
                var telephone = carOffers[i].user.telephone;
                var dir_photos = carOffers[i].dir_photos;
                $('#table tr:last').after('<tr><td width="20%" rowspan="5"><img src="' + dir_photos + '"  width="500" height="300"></td>' +
                    '<td><b>Category : ' + category + '</b></td>' +
                    '<td rowspan="5"><b>Name of owner : ' + userName + '</b>' +
                    '<br><br>' +
                    '<b>Contact of owner : ' + telephone + '</b><br><br>' +
                    '<b>Price : ' + price + '</b></td>');
                if (userId === id) {
                    $('#table td:last').after('<td rowspan="5"><div class="row float-right">' +
                        '<input type=hidden form="update" name="idI" value=' + idI + '>' +
                        '<input type=hidden form="update" name="idUser" value=' + id + '>' +
                        '<input type=hidden form="update" name="markI" value=' + markI + '>' +
                        '<input type=hidden form="update" name="yearOfIssue" value=' + yearOfIssue + '>' +
                        '<input type=hidden form="update" name="typeBody" value=' + typeBody + '>' +
                        '<input type=hidden form="update" name="transmission" value=' + transmission + '>' +
                        '<input type=hidden form="update" name="category" value=' + category + '>' +
                        '<input type=hidden form="update" name="priceI" value=' + price + '>' +
                        '<button type="submit" form="update" class="btn btn-success">Correct offer</button></form>' +
                        '<input type=hidden name="idI" form="delete" value=' + idI + '>' +
                        '<button type="submit" form="delete" class="btn btn-success">Delete  offer </button></form></div></td></tr>');
                }
                $('#table tr:last').after('<tr><td><b>Mark : ' + markI + '</b></td></tr>' +
                    '<tr><td><b>Type of body : ' + typeBody + '</b></td></tr>' +
                    '<tr><td><b>Transmission : ' + transmission + '</b></td></tr>' +
                    '<tr><td><b>Year of issue : ' + yearOfIssue + '</b></td></tr>');
            }
        }

        function findByType() {
            var type = $('#type').val();
            $.ajax('http://localhost:8080/getByType', {
                    method: 'GET',
                    data: {type: type},
                    dataType: 'application/json',
                    complete: function (data) {
                        deleteElements();
                        var carOffers = JSON.parse(data.responseText);
                        deleteElements();
                        fiilTable(carOffers);
                    }
                }
            );
        }

        function findByPhotos() {
            $.ajax({
                    type: 'GET',
                    url: "${pageContext.servletContext.contextPath}/getCars",
                    data: {
                        date: date
                    },
                    dataType: 'application/json',
                    complete: function (data) {
                        deleteElements();
                        var carOffers = JSON.parse(data.responseText);
                        deleteElements();
                        fiilTable(carOffers);
                    }
                }
            );
        }
        $(function() {
            $('input[name="daterange"]').daterangepicker({
                opens: 'left'
            }, function(start, end) {
                $.ajax({
                        type: 'GET',
                        url: "${pageContext.servletContext.contextPath}/getByDate",
                        data: {
                            start : start.format('YYYY-MM-DD'),
                             finish : end.format('YYYY-MM-DD')
                        },
                        dataType: 'application/json',
                        complete: function (data) {
                            deleteElements();
                            var carOffers = JSON.parse(data.responseText);
                            deleteElements();
                            fiilTable(carOffers);
                        }
                    }
                );
            });
        });
    </script>
    <style>
        body {
            margin: 0;
            background: url("../view.jpg") no-repeat center center fixed;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
        }
    </style>
<body onload="updateItems()">
<div class="container">
    <div class="row pt-3">
        <h1>Car market</h1><br>
        <table class="table table-bordered" id="filters">
            <tbody>
            <tr>
                <td colspan="4" align="center">
                    <h3>Filters</h3>
                </td>
            </tr>
            <tr>
                <td align="center"><b>With photos</b></td>
                <td align="center"><b>Find by type</b></td>
                <td align="center"><b>Find by date</b></td>
                <td align="center"><b>Reset</b></td>
            </tr>
            <tr align="center">
                <td><input type="radio"
                           id="with_photos" name="with_photos" onchange="findByPhotos()"></td>
                <td>
                    <input type="text"
                           id="type" name="type" onchange="findByType()">
                </td>
                <td>
                    <input type="text" name="daterange" value="Range date"/>
                </td>
                <td>
                    <input type="reset"
                           id="reset" name="reset" onclick="updateItems()">
                </td>
            </tr>
            </tbody>
        </table>
        <table class="table table-bordered" id="table">
            <tbody>
            <tr>
                <td align="center"><b>Photo</b></td>
                <td align="center"><b>Specifications</b></td>
                <td align="center"><b>Owner/Price</b></td>
                <td align="center"><b>Functions</b></td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="row float-left">
        <form method="GET" action="createOffer">
            <button type="submit" class="btn btn-success">New offer</button>
        </form>
        <c:if test="${sessionScope.id > 0}">
            <div>
                <form method="GET" action="updateUser">
                    <button type="submit" class="btn btn-success">Correct user</button>
                    <input type=hidden form="update" name="idUser" value="${sessionScope.id}">
                </form>
            </div>
            <div>
                <form method="POST" action="logout">
                    <button type="submit" class="btn btn-success">Logout</button>
                </form>
            </div>
        </c:if>
        <form method="GET" id=update action="updateOffer">
        </form>
        <form method="POST" id=delete action="deleteOffer">
        </form>
    </div>
</div>
</body>
</html>