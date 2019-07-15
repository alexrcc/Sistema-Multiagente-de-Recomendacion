var contador=1;
function opcAvatar(){
    if(contador==1){
        $('.opc_avatar').slideDown("slow");
        contador=0;
    }
    else{
        $('.opc_avatar').slideUp("slow");
        contador=1;
    }
}
