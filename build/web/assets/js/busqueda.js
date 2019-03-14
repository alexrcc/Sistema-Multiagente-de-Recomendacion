/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var band = false;

function MostrarOcultar(capa) 
{ 
    if (document.getElementById) 
        { if(band===false){
            $('#'+capa).slideDown("slow");
            band=true;
        }
        else{
            $('#'+capa).slideUp("slow");
            band=false;
        }
    } 
} 
function checkFunct(){
    
  //  alert("hola");
    if($('#checkbox').is(':checked')){
        document.getElementById("estilo").disabled = true;
        $('.mostrar').addClass('oculto');
        $('.oculto').removeClass('mostrar');
                document.getElementById("inteligencia").disabled = true;
    }
    else{
        document.getElementById("estilo").disabled = false;
$('.oculto').addClass('mostrar');
        $('.mostrar').removeClass('oculto');
        
        document.getElementById("inteligencia").disabled = false;
    }
}