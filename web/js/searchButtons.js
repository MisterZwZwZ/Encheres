let radioBtnAchat = document.getElementById("achat");
let checkboxEncheresOuvertes = document.getElementById("encheresOuvertes");
let checkboxMesEncheresEnCours = document.getElementById("mesEncheresEnCours");
let checkboxEncheresRemportees = document.getElementById("encheresRemportees");

let radioBtnvente = document.getElementById("vente");
let checkboxVentesEnCours = document.getElementById("ventesEnCours");
let checkboxVentesNonDebutees = document.getElementById("ventesNonDebutees");
let checkboxVentesTerminees = document.getElementById("ventesTerminees");

document.addEventListener('DOMContentLoaded', desactiveVentes);
radioBtnAchat.onclick = desactiveVentes;
radioBtnvente.onclick = desactiveAchat;



function desactiveVentes (){
    radioBtnvente.checked = false;
    checkboxVentesEnCours.checked = false;
    checkboxVentesEnCours.disabled = true;
    checkboxVentesNonDebutees.checked = false;
    checkboxVentesNonDebutees.disabled = true;
    checkboxVentesTerminees.checked = false;
    checkboxVentesTerminees.disabled = true;
    checkboxEncheresOuvertes.disabled = false;
    checkboxMesEncheresEnCours.disabled = false;
    checkboxEncheresRemportees.disabled = false;

}


function desactiveAchat (){
    radioBtnAchat.checked = false;
    checkboxEncheresOuvertes.checked = false;
    checkboxEncheresOuvertes.disabled = true;
    checkboxMesEncheresEnCours.checked = false;
    checkboxMesEncheresEnCours.disabled = true;
    checkboxEncheresRemportees.checked = false;
    checkboxEncheresRemportees.disabled = true;
    checkboxVentesEnCours.disabled = false;
    checkboxVentesNonDebutees.disabled = false;
    checkboxVentesTerminees.disabled = false;
}

//TODO essayer session storage pour conserver l'état des checkbox après rechargement