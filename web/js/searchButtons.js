let radioBtnAchat = document.getElementById("achat");
let checkboxEncheresOuvertes = document.getElementById("encheresOuvertes");
let checkboxMesEncheresEnCours = document.getElementById("mesEncheresEnCours");
let checkboxEncheresRemportees = document.getElementById("encheresRemportees");

let radioBtnvente = document.getElementById("vente");
let checkboxVentesEnCours = document.getElementById("ventesEnCours");
let checkboxVentesNonDebutees = document.getElementById("ventesNonDebutees");
let checkboxVentesTerminees = document.getElementById("ventesTerminees");

//gestion radio bouton Achat
document.addEventListener("DOMContentLoaded", function(){
        radioBtnAchat.addEventListener("click", function(){
            sessionStorage.sRadioAchat ? // verification qu'un localStorage existe
                sessionStorage.sRadioAchat = sessionStorage.sRadioAchat.indexOf(this.id+",") == -1 // vérification que le localStorage contient l'id
                    ? sessionStorage.sRadioAchat+this.id+"," // n'existe pas : ajout de l'id au localStorage
                    : sessionStorage.sRadioAchat+this.id+","  :
                sessionStorage.sRadioAchat = this.id+",";  // n'existe pas. on la crée avec l'id de la case à cocher
        });
    if(sessionStorage.sRadioAchat){ // verification que le localStorage existe
      // existe, parcourir les checkbox
            radioBtnAchat.checked = sessionStorage.sRadioAchat.indexOf(radioBtnAchat.id+",") != -1 ? true : false; //mettre true sur les ids qui existent dans localStorage

    }
});

//Gestion radio bouton vente
document.addEventListener("DOMContentLoaded", function(){
    radioBtnvente.addEventListener("click", function(){
            localStorage.sRadioVente ? // verification qu'un localStorage existe
                localStorage.sRadioVente = sessionStorage.sRadioVente.indexOf(this.id+",") == -1 // vérification que le localStorage contient l'id
                    ? sessionStorage.sRadioVente+this.id+"," // n'existe pas : ajout de l'id au localStorage
                    : sessionStorage.sRadioVente+this.id+"," :
                sessionStorage.sRadioVente = this.id+",";  // n'existe pas. on la crée avec l'id de la case à cocher
        });
    if(sessionStorage.sRadioVente){ // verification que le localStorage existe
      // existe, parcourir les checkbox
        radioBtnvente.checked = sessionStorage.sRadioVente.indexOf(radioBtnvente.id+",") != -1 ? true : false; //mettre true sur les ids qui existent dans localStorage

    }
});

//gestion des checkbox enchères
document.addEventListener("DOMContentLoaded", function(){
    let checkboxEncheres = document.querySelectorAll("#encheresOuvertes, #mesEncheresEnCours, #encheresRemportees");
    for(let item of checkboxEncheres){
        item.addEventListener("click", function(){
            sessionStorage.sEncheres ? // verification qu'un localStorage existe
                sessionStorage.sEncheres = sessionStorage.sEncheres.indexOf(this.id+",") == -1 // vérification que le localStorage contient l'id
                    ? sessionStorage.sEncheres+this.id+"," // n'existe pas : ajout de l'id au localStorage
                    : sessionStorage.sEncheres.replace(this.id+",","") : // existe, supprimer l'id du localStorage
                sessionStorage.sEncheres = this.id+",";  // n'existe pas. on la crée avec l'id de la case à cocher
        });
    }

    if(sessionStorage.sEncheres){ // verification que le localStorage existe
        for(let item of checkboxEncheres){ // existe, parcourir les checkbox
            item.checked = sessionStorage.sEncheres.indexOf(item.id+",") != -1 ? true : false; //mettre true sur les ids qui existent dans localStorage
        }
    }
});

//gestion des checkbox Ventes
document.addEventListener("DOMContentLoaded", function(){
    let checkboxVentes = document.querySelectorAll("#ventesEnCours, #ventesNonDebutees, #ventesTerminees");
    for(let item of checkboxVentes){
        item.addEventListener("click", function(){
            sessionStorage.sVentes ? // verification qu'un localStorage existe
                sessionStorage.sVentes = sessionStorage.sVentes.indexOf(this.id+",") == -1 // vérification que le localStorage contient l'id
                    ? sessionStorage.sVentes+this.id+"," // n'existe pas : ajout de l'id au localStorage
                    : sessionStorage.sVentes.replace(this.id+",","") : // existe, supprimer l'id du localStorage
                sessionStorage.sVentes = this.id+",";  // n'existe pas. on la crée avec l'id de la case à cocher
        });
    }

    if(sessionStorage.sVentes){ // verification que le localStorage existe
        for(let item of checkboxVentes){ // existe, parcourir les checkbox
            item.checked = sessionStorage.sVentes.indexOf(item.id+",") != -1 ? true : false; //mettre true sur les ids qui existent dans localStorage
        }
    }
});

document.addEventListener("DOMContentLoaded", function(){
    if(sessionStorage.getItem("sEncheres")){
        desactiveVentes();
    }else{
        desactiveAchat();
    }
});

//gestion storage vide (premier chargement de la page)
document.addEventListener('DOMContentLoaded', function() {
    if (!sessionStorage.getItem('sRadioAchat') && !sessionStorage.getItem('sRadioVente')) {
        radioBtnAchat.checked = true;
        desactiveVentes();
    }
});

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
    sessionStorage.removeItem("sVentes");
    sessionStorage.removeItem("sRadioVente")
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
    sessionStorage.removeItem("sEncheres");
    sessionStorage.removeItem("sRadioAchat");
}

