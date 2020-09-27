// API endpoint --------------------------------------------
const baseUrl = 'https://pokeapi.co/api/v2/pokemon/';

// Get Elements --------------------------------------------
const getElement = document.querySelector.bind(document);
const searchInput = getElement('.search-input'),
      searchButton = getElement('.search-button'),
      container = getElement('.pokemon'),
      erroMessage = getElement('.error');

var pokeName, // Nombre o número pasado en el cuadro de búsqueda
    pokemon, // Responsable de guardar los datos recibidos de la API
    card; // Responsable de recibir HTML

// Funciones de construcción --------------------------------------------

// Función responsable de realizar solicitudes a la API e insertar respuestas en la variable pokemon
async function requestPokeInfo(url, name) {
  await fetch(url + name)
    .then(response => response.json())
    .then(data => {
      pokemon = data;
    })
    .catch(err => console.log(err));
}

// Función responsable de ensamblar el HTML que se muestra en la página.
function createCard () {
  card = `
    <div class="pokemon-picture">
      <img src="${pokemon.sprites.front_default}" alt="Sprite of ${pokemon.name}">
    </div>
    <div class="pokemon-info">
        <h1 class="name">${pokemon.name}</h1>
        <font class="number">Nº ${pokemon.id} - Type: ${pokemon.types.map(item => item.type.name).toString()}</font>
        <h3 class="skill">Skills: ${pokemon.moves.map(item => ' ' + item.move.name).toString()}</h3>
        <h3 class="weight">Weight: ${pokemon.weight  / 10}kg</h3>
        <h3 class="height">Height: ${pokemon.height  / 10}m</h3>
    </div>`;
  return card;
}

// Función que llama a las funciones principales e inicia la aplicación
async function startApp(pokeName) {
  await requestPokeInfo(baseUrl, pokeName);
    //Muestra un mensaje si el pokemon buscado no existe
    if(pokemon.detail) {
      erroMessage.style.display = 'block';
      container.style.display = 'none';
    }else{
      erroMessage.style.display = 'none';
      container.style.display = 'flex';
      container.innerHTML = createCard();
    }
}

// Add Eventos --------------------------------------------
searchButton.addEventListener('click', event => {
  event.preventDefault();
  pokeName = searchInput.value.toLowerCase();
  searchInput.value = '';
  startApp(pokeName);
  container.classList.add('fade');

  // Restablece el efecto de desvanecimiento eliminando la clase de desvanecimiento
  setTimeout(() => {
    container.classList.remove('fade');
  }, 3000);
});