<%-- 
    Document   : login
    Created on : Aug 9, 2015, 12:45:43 PM
    Author     : nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Avocado</title>
        <link type="text/css" href="common/css/custom.css" rel="stylesheet"/>
    </head>
    <body>
        <div class="avc-title">Avocado</div>
        <div class="login">
            <form method="POST" action="/avocado/Login">
                <table>
                    <tr>
                        <td>User Name</td>
                        <td><input type="text" name="userName"/></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="password"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="submit"value="Login"/></td>
                    </tr>
                </table>
            </form>
        </div>
    </body>
</html>
