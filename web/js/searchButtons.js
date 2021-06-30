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
            localStorage.sRadioAchat ? // verification qu'un localStorage existe
                localStorage.sRadioAchat = localStorage.sRadioAchat.indexOf(this.id+",") == -1 // vérification que le localStorage contient l'id
                    ? localStorage.sRadioAchat+this.id+"," // n'existe pas : ajout de l'id au localStorage
                    : localStorage.sRadioAchat+this.id+","  :
                localStorage.sRadioAchat = this.id+",";  // n'existe pas. on la crée avec l'id de la case à cocher
        });
    if(localStorage.sRadioAchat){ // verification que le localStorage existe
      // existe, parcourir les checkbox
            radioBtnAchat.checked = localStorage.sRadioAchat.indexOf(radioBtnAchat.id+",") != -1 ? true : false; //mettre true sur les ids qui existent dans localStorage

    }
});

//Gestion radio bouton vente
document.addEventListener("DOMContentLoaded", function(){
    radioBtnvente.addEventListener("click", function(){
            localStorage.sRadioVente ? // verification qu'un localStorage existe
                localStorage.sRadioVente = localStorage.sRadioVente.indexOf(this.id+",") == -1 // vérification que le localStorage contient l'id
                    ? localStorage.sRadioVente+this.id+"," // n'existe pas : ajout de l'id au localStorage
                    : localStorage.sRadioVente+this.id+"," :
                localStorage.sRadioVente = this.id+",";  // n'existe pas. on la crée avec l'id de la case à cocher
        });
    if(localStorage.sRadioVente){ // verification que le localStorage existe
      // existe, parcourir les checkbox
        radioBtnvente.checked = localStorage.sRadioVente.indexOf(radioBtnvente.id+",") != -1 ? true : false; //mettre true sur les ids qui existent dans localStorage

    }
});

//gestion des checkbox enchères
document.addEventListener("DOMContentLoaded", function(){
    let checkboxEncheres = document.querySelectorAll("#encheresOuvertes, #mesEncheresEnCours, #encheresRemportees");
    for(let item of checkboxEncheres){
        item.addEventListener("click", function(){
            localStorage.sEncheres ? // verification qu'un localStorage existe
                localStorage.sEncheres = localStorage.sEncheres.indexOf(this.id+",") == -1 // vérification que le localStorage contient l'id
                    ? localStorage.sEncheres+this.id+"," // n'existe pas : ajout de l'id au localStorage
                    : localStorage.sEncheres.replace(this.id+",","") : // existe, supprimer l'id du localStorage
                localStorage.sEncheres = this.id+",";  // n'existe pas. on la crée avec l'id de la case à cocher
        });
    }

    if(localStorage.sEncheres){ // verification que le localStorage existe
        for(let item of checkboxEncheres){ // existe, parcourir les checkbox
            item.checked = localStorage.sEncheres.indexOf(item.id+",") != -1 ? true : false; //mettre true sur les ids qui existent dans localStorage
        }
    }
});

//gestion des checkbox Ventes
document.addEventListener("DOMContentLoaded", function(){
    let checkboxVentes = document.querySelectorAll("#ventesEnCours, #ventesNonDebutees, #ventesTerminees");
    for(let item of checkboxVentes){
        item.addEventListener("click", function(){
            localStorage.sVentes ? // verification qu'un localStorage existe
                localStorage.sVentes = localStorage.sVentes.indexOf(this.id+",") == -1 // vérification que le localStorage contient l'id
                    ? localStorage.sVentes+this.id+"," // n'existe pas : ajout de l'id au localStorage
                    : localStorage.sVentes.replace(this.id+",","") : // existe, supprimer l'id du localStorage
                localStorage.sVentes = this.id+",";  // n'existe pas. on la crée avec l'id de la case à cocher
        });
    }

    if(localStorage.sVentes){ // verification que le localStorage existe
        for(let item of checkboxVentes){ // existe, parcourir les checkbox
            item.checked = localStorage.sVentes.indexOf(item.id+",") != -1 ? true : false; //mettre true sur les ids qui existent dans localStorage
        }
    }
});

document.addEventListener("DOMContentLoaded", function(){
    if(localStorage.getItem("sEncheres")){
        desactiveVentes();
    }else{
        desactiveAchat();
    }
});

//gestion storage vide (premier chargement de la page)
document.addEventListener('DOMContentLoaded', function() {
    if (!localStorage.getItem('sRadioAchat') && !localStorage.getItem('sRadioVente')) {
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
    localStorage.removeItem("sVentes");
    localStorage.removeItem("sRadioVente")
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
    localStorage.removeItem("sEncheres");
    localStorage.removeItem("sRadioAchat");
}

