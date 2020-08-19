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
    <title>CreateUser</title>
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
                createUser();
            }
            return result;
        }

        function validateSingIn() {
            var result = true;
            if ($('#email2').val() === '') {
                result = false;
                alert('Please, fill email');
            }
            if ($('#password').val() === '') {
                result = false;
                alert('Please, fill your password');
            }
            if (result) {
                checkUser();
            }
            return result;
        }

        function createUser() {
            var name = $('#name').val();
            var surname = $('#surname').val();
            var email = $('#email').val();
            var telephone = $('#telephone').val();
            var password = $('#pwd').val();
            $.ajax({
                type: 'POST',
                url: "${pageContext.servletContext.contextPath}/login",
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

        function checkUser() {
            var email = $('#email2').val();
            var password = $('#password').val();
            $.ajax({
                type: 'GET',
                url: "${pageContext.servletContext.contextPath}/login",
                data: {
                    email: email,
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
                <form role="form" onsubmit="return validate();">
                    <fieldset>
                        <p class="text-uppercase pull-center" id="title"> SIGN UP.</p>
                        <div class="form-group">
                            <input type="text" name="name" id="name" class="form-control input-lg"
                                   placeholder="Name">
                        </div>
                        <div class="form-group">
                            <input type="text" name="surname" id="surname" class="form-control input-lg"
                                   placeholder="Surname">
                        </div>

                        <div class="form-group">
                            <input type="email" name="email" id="email" class="form-control input-lg"
                                   placeholder="Email Address">
                        </div>
                        <div class="form-group">
                            <input type="password" name="password" id="pwd" class="form-control input-lg"
                                   placeholder="Password">
                        </div>
                        <div class="form-group">
                            <input type="tel" name="tel" id="telephone"
                                   pattern="\+7\s?[\(]{0,1}9[0-9]{2}[\)]{0,1}\s?\d{3}[-]{0,1}\d{2}[-]{0,1}\d{2}"
                                   class="form-control input-lg"
                                   placeholder="+7-xxx-xxx-xx-xx">
                        </div>
                        <div>
                            <input type="submit" class="btn btn-lg btn-primary" value="Create">
                        </div>
                    </fieldset>
                </form>
            </div>
            <div class="col-md-5">
                <form role="form" onsubmit="return validateSingIn();">
                    <fieldset>
                        <p class="text-uppercase" id="title"> Login using your account: </p>

                        <div class="form-group">
                            <input type="email" name="email" id="email2" class="form-control input-lg"
                                   placeholder="email">
                        </div>
                        <div class="form-group">
                            <input type="password" name="password" id="password" class="form-control input-lg"
                                   placeholder="Password">
                        </div>
                        <div>
                            <input type="submit" class="btn btn-lg btn-primary" value="Sign In">
                        </div>
                    </fieldset>
                </form>
                <c:if test="${error != ''}">
                    <div style="background-color: red">
                        <h2><c:out value="${error}"/></h2>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>