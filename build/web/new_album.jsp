<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : createWord
    Created on : Jul 26, 2015, 11:45:49 AM
    Author     : nguyen
--%>

<div class="navigator">
    <div id='mode'>
        <div id="auto" onclick="return switchMode('auto')">Auto</div>
        <div id="manual" onclick="return switchMode('manual')">Manual</div>
    </div>
    <div class="find-tool" id='auto-content' style="display: block">
        <input name="word" type="text" id="key_word"/>
        <button onclick="ajaxRequest()" class="search-button">Find</button>
    </div>
    <div id="custom-content" style='display:none' class="find-tool">
        <table>
            <tr>
                <td><input name="manual-word" placeholder="word"></td>
            </tr>
            <tr>
                <td><input name="manual-type" placeholder="type"></td>
            </tr>
            <tr>
                <td><input name="manual-pro" placeholder="pronuncation"></td>
            </tr>
            <tr>
                <td><textarea name="manual-defi" placeholder="definition"></textarea></td>
            </tr>
            <tr>
                <td><textarea name="manual-ex" placeholder="example"></textarea></td>
            </tr
            <tr><td align="right"><button>Add</button></td></tr>
        </table>
    </div>
</div>
<div class="main-panel">
    <div id='result-content' class='result-content' style='display:none'>
        <div id="result" style="display:none">

        </div>
        <div  id="button-save" style="display:none">
            <div id='avc-add-button'>
            </div>
        </div>
        <div  id="message" style="display:none">
        </div>
    </div>
</div>






