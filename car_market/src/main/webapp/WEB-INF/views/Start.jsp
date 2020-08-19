<%@ page import="org.hibernate.Session" %>
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
    <script>
        function updateItems() {
            $.ajax('http://localhost:8080/getCars', {
                    method: 'GET',
                    dataType: 'application/json',
                    complete: function (data) {
                        var id = <%=session.getAttribute("id")%>;
                        var carOffers = JSON.parse(data.responseText);
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
                        console.log(items);
                    }
                }
            );
        }
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
        <h1 id="header">Car market</h1>
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
        <c:if test="${sessionScope.id > 0 }">
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
        <form method="POST" id=delete  action="deleteOffer">
        </form>
    </div>
</div>
</body>
</html>