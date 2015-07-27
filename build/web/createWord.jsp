<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : createWord
    Created on : Jul 26, 2015, 11:45:49 AM
    Author     : nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Avocado</h1>
            <input name="word" type="text" id="key_word"/>
            <button onclick="ajaxRequest()">Submit</button>
        <form>
        <div id="result">
            <div>${definition}</div>
            <div>${type}</div>
            <div>${origin}</div>
        </div>
        <div>
            <button type="submit">Save to album</button>
        </div>
        </form>
        <script>
            function ajaxRequest(){
                var xmlhttp;
                var name = document.getElementById("key_word").value;
                xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange=function(){
                    if(xmlhttp.readyState==4 && xmlhttp.status==200)
                    {
                        var data = xmlhttp.responseText;
                        alert(data)
                        document.getElementById("result").innerHTML=xmlhttp.responseText;
                    }
                }
                xmlhttp.open("GET","/avocado/createWord?word"+name+"&format=fragment",true);
                xmlhttp.send();
           
            }
        </script>
    </body>
</html>
