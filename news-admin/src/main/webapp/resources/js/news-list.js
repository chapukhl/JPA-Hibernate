function validateBeforeSubmit(){
    var isChoose=false;
    var count=0;
    for(var i=0;i< document.getElementsByName('deleteItem').length;i++){
        if( document.getElementsByName('deleteItem').item(i).checked){
            isChoose=true;
            count++;
        }
    }

    if(isChoose){
        if(confirm("Вы уверены что хотите удалить  элементы ("+count+")?")){
            document.getElementById('deleteNewsForm').submit();

        }
    }else{
        alert('Выберите хотя бы один элемент')
    }

}
