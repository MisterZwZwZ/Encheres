var today = new Date().toISOString().split('T')[0];
document.getElementsByName("dateDebutEnchere")[0].setAttribute('min', today);

var dateMinEnchere = new Date().toISOString().split('T')[0];
document.getElementsByName("dateFinEnchere")[0].setAttribute('min', dateMinEnchere);


