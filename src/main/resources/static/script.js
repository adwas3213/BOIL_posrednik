// funkcja tworząca macierz kosztów transportu na podstawie liczby dostawców i odbiorców
function utworzMacierz(dostawcy, odbiorcy) {
    // tworzymy pustą macierz
    let macierz = '';
    // iterujemy po wierszach macierzy
    for (let i = 1; i <= dostawcy; i++) {
        // tworzymy wiersz macierzy
        let wiersz = '';
        // iterujemy po kolumnach macierzy
        for (let j = 1; j <= odbiorcy; j++) {
            // dodajemy inputa do wiersza
            wiersz += `<div class="col">
    <input type="number" class="form-control" id="koszt_${i}${j}" name="transportCost_${i};${j}">
  </div>`;
        }
        // dodajemy wiersz do macierzy
        macierz += `<div class="row">${wiersz}</div>`;
    }
    // ustawiamy wygenerowaną macierz kosztów w formularzu
    $('#matrix-container').html(macierz);
}

// nasłuchujemy zmiany wartości w polach liczby dostawców i odbiorców
$('#suppliers').on('input', function() {
    const dostawcy = parseInt($('#receiver').val());
    const odbiorcy = parseInt($('#suppliers').val());
    utworzMacierz(dostawcy, odbiorcy);
    utworzMacierzDostawcow(dostawcy);
    createReciverVector(odbiorcy);
    createBuyCostVector(odbiorcy);
    createSellCostVector(dostawcy);

});


$('#receiver').on('input', function() {
    const dostawcy = parseInt($('#receiver').val());
    const odbiorcy = parseInt($('#suppliers').val());
    utworzMacierz(dostawcy, odbiorcy);
    utworzMacierzDostawcow(dostawcy);
    createReciverVector(odbiorcy);
    createBuyCostVector(odbiorcy);
    createSellCostVector(dostawcy);
});

function utworzMacierzDostawcow(dostawcy) {
    let macierzDostawcow = '';
    let dostawcyHtml = '';
    for (let i = 1; i <= dostawcy; i++) {
        dostawcyHtml += `<div class="form-group">
            <label for="dostawca_${i}">Dostawca ${i}:</label>
            <input type="number" class="form-control" id="dostawca_${i}" name="suplier_${i}">
        </div>`;
    }
    macierzDostawcow += `<div class="row">${dostawcyHtml}</div>`;
    $('#suplier-vector').html(macierzDostawcow);
    return macierzDostawcow;

}
function createReciverVector(recivers) {
    let matrixRecivers = '';
    let reciversHtml = '';
    for (let i = 1; i <= recivers; i++) {
        reciversHtml += `<div class="form-group">
            <label for="receiver_${i}">Odbiorca ${i}:</label>
            <input type="number" class="form-control" id="receiver_${i}" name="receiver_${i}">
        </div>`;
    }
    matrixRecivers += `<div class="row">${reciversHtml}</div>`;
    $('#receiver-vector').html(matrixRecivers);
    return matrixRecivers;

}
function createBuyCostVector(count) {
    let vectorBuyCost = '';
    let buyCostHtml = '';
    for (let i = 1; i <= count; i++) {
        buyCostHtml += `<div class="form-group">
            <label for="buyCost_${i}">Cena kupna ${i}:</label>
            <input type="number" class="form-control" id="buyCost_${i}" name="buy">
        </div>`;
    }
    vectorBuyCost += `<div class="row">${buyCostHtml}</div>`;
    $('#buyCost-vector').html(vectorBuyCost);
    return vectorBuyCost;

}
function createSellCostVector(count) {
    let sellCostVector = '';
    let sellCostHtml = '';
    for (let i = 1; i <= count; i++) {
        sellCostHtml += `<div class="form-group">
            <label for="sellCost_${i}">Koszt sprzedaży ${i}:</label>
            <input type="number" class="form-control" id="sellCost_${i}" name="sell">
        </div>`;
    }
    sellCostVector += `<div class="row">${sellCostHtml}</div>`;
    $('#sellCost-vector').html(sellCostVector);
    return sellCostVector;

}
