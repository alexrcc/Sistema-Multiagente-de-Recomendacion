/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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