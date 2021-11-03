class Libro {
    constructor(titolo, autori, anno, editore) {
        this.titolo = titolo;
        this.autori = autori;
        this.anno = anno;
        this.editore = editore;
    }
}

class Autore {
    constructor(nome, cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }
}

let autori = []
let libri = []

function createAutore(nome, cognome) {
    let nuovoAutore = new Autore(nome, cognome);
    autori.push(nuovoAutore);
}

function getAutori() {
    return autori;
}

function creaLibro(titolo, autori, anno, editore) {
    libri.push(new Libro(titolo, autori, anno, editore))
    console.log(libri)
}