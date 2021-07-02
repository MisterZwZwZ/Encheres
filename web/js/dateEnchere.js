//Permet de sélectionner une date d'enchère à partir de la date du jour lors de la mise en vente d'un article
var today = new Date().toISOString().split('T')[0];
document.getElementsByName("dateDebutEnchere")[0].setAttribute('min', today);

//Faire en sorte que ça soit la date de début d'enchère qui soit le min et non la date du jour quand on choisi la date de fin d'enchère.
var dateMinEnchere = new Date().toISOString().split('T')[0];
document.getElementsByName("dateFinEnchere")[0].setAttribute('min', dateMinEnchere);
