<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : createWord
    Created on : Jul 26, 2015, 11:45:49 AM
    Author     : nguyen
--%>

<div class="search-content">
    <div class="search-title">Find meaning of word that you want to learn.</div>
    <input name="word" type="text" id="key_word"/>
    <button onclick="ajaxRequest()">Find</button>
    <div class="search-custom">or you can <a id="custom-add" href="#"> custom add</a>.</div>
</div>
<div id="result" class="result-content" style="display:none">
    
</div>
<div  id="button-save" style="display:none">
    <button onclick="saveToAlbum()">Add</button>
</div>

