

function changeAccess(param){

    var toggle=function (el) {
        el.style.display = (el.style.display == 'none') ? '' : 'none'
    };

    var currentState=document.getElementById(param).disabled;

    document.getElementById(param).disabled=!currentState;

    toggle(document.getElementById("edit"+param));

    toggle(document.getElementById("update"+param));

    toggle(document.getElementById("delete"+param));

    toggle(document.getElementById("cancel"+param));


}



