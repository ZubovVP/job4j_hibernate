<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 10.08.2020
  Time: 23:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>UpdateUser</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <!-- Popper JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script>
        function validate() {
            var result = true;
            if ($('#name').val() === '') {
                result = false;
                alert('Please, fill your name');
            }
            if ($('#surname').val() === '') {
                result = false;
                alert('Please, fill your surname');
            }
            if ($('#email').val() === '') {
                result = false;
                alert('Please, fill your email');
            }
            if ($('#pwd').val() === '') {
                result = false;
                alert('Please, fill your password');
            }
            if ($('#telephone').val() === '') {
                result = false;
                alert('Please, fill your telephone');
            }
            if (result) {
                updateUser();
            }
            return result;
        }

        function updateUser() {
            var name = $('#name').val();
            var surname = $('#surname').val();
            var email = $('#email').val();
            var telephone = $('#telephone').val();
            var password = $('#pwd').val();
            $.ajax({
                type: 'POST',
                url: "${pageContext.servletContext.contextPath}/updateUser",
                data: {
                    name: name,
                    surname: surname,
                    email: email,
                    telephone: telephone,
                    password: password
                },
                dataType: 'application/json',
                success: function (data) {
                    console.log(JSON.parse(data.responseText));
                }
            });
        }
    </script>
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

    .container-fluid {
        padding: 50px;
    }

    .container {
        background-color: white;
        padding: 50px;
    }

    #title {
        font-family: 'Lobster', cursive;;
    }
</style>
<div class="container-fluid">
    <div class="container">
        <h2 class="text-center" id="title">Create user</h2>
        <hr>
        <div class="row">
            <div class="col-md-5">
                <form role="form" action="${pageContext.servletContext.contextPath}/start" method="GET"
                      onsubmit="return validate();">
                    <fieldset>
                        <p class="text-uppercase pull-center" id="title"> SIGN UP.</p>
                        <div class="form-group">
                            <input type="text" name="name" id="name" value="${user.name}" class="form-control input-lg">
                        </div>
                        <div class="form-group">
                            <input type="text" name="surname" id="surname" value="${user.surname}"
                                   class="form-control input-lg">
                        </div>

                        <div class="form-group">
                            <input type="email" name="email" id="email" placeholder="Email"
                                   class="form-control input-lg">
                        </div>
                        <div class="form-group">
                            <input type="password" name="password" id="pwd" placeholder="Password"
                                   class="form-control input-lg">
                        </div>
                        <div class="form-group">
                            <input type="tel" name="tel" id="telephone"
                                   pattern="\+7\s?[\(]{0,1}9[0-9]{2}[\)]{0,1}\s?\d{3}[-]{0,1}\d{2}[-]{0,1}\d{2}"
                                   class="form-control input-lg"
                                   value="${user.telephone}">
                        </div>
                        <div>
                            <input type="submit" class="btn btn-lg btn-primary" value="Update">
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>