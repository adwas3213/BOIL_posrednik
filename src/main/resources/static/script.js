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
    <input type="text" class="form-control" id="koszt_${i}${j}" name="koszt_${i}${j}">
  </div>`;
        }
        // dodajemy wiersz do macierzy
        macierz += `<div class="row">${wiersz}</div>`;
    }
    // ustawiamy wygenerowaną macierz kosztów w formularzu
    $('#matrix-container').html(macierz);
}

// nasłuchujemy zmiany wartości w polach liczby dostawców i odbiorców
$('#dostawcy').on('input', function() {
    const dostawcy = parseInt(this.value);
    const odbiorcy = parseInt($('#odbiorcy').val());
    utworzMacierz(dostawcy, odbiorcy);
});

$('#odbiorcy').on('input', function() {
    const dostawcy = parseInt($('#dostawcy').val());
    const odbiorcy = parseInt(this.value);
    utworzMacierz(dostawcy, odbiorcy);
});
