require('dotenv').config();
const express = require('express');
const mongoose = require('mongoose');
const app = express();
const port = process.env.PORT;
const uri = process.env.MONGO_URI;

// Middleware per parsejar el cos de les sol·licituds a JSON
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

console.log("starting server");
console.log("URI: ", uri, port);
const clientOptions = { family: 4, serverApi: { version: '1', strict: true, deprecationErrors: true }};

if (uri != "mongodb://????") {
  mongoose.connect(uri, clientOptions)
    .then(() => console.log('Connected to MongoDB: examen'))
    .catch(err => console.log('Error connecting to MongoDB:', err));
}
// Definició del model de dades (un exemple simple d'un model de "Usuari")
const albumsSchema = new mongoose.Schema({
  title: { type: String, required: true },
  artist: { type: String, required: true },
  date: { type: String, required: true }
},{ versionKey: false });

const Albums = mongoose.model('albums', albumsSchema, 'albums');


/******************************************************** */
/******************************************************** */
/******************************************************** */
/******************************************************** */
/******************************************************** */
/**
 * END POINTS
 */
//  '/' (GET) 
// per comprovar que el servidor està funcionant
// respondre amb json {"message": "Yout API is running!"}
// També console.log("Yout API is running - ARA!") al servidor quan es rebi una petició a aquesta ruta
/****************************/
/** Ex 6a ESCRIU EL END POINT */
/****************************/
app.get('/', (req, res) => {
  res.json({ message: "Your API is running - ARA!" });
  console.log("Your API is running - ARA!");
});

// '/albums/:dataini/:datafi' (GET)
// Ruta per filtrar els àlbums per data
// Respon amb code 200 i un json amb els àlbums que tinguin una data entre dataini i datafi (ambdós inclosos)
/****************************/
/** Ex 6b ESCRIU EL END POINT */
/****************************/

app.get('/albums/:dataini/:datafi', async (req, res) => {
  const { dataini, datafi } = req.params;
  try {
    const albums = await Albums.find({
      String: { gte: dataini, lte: datafi }
    });
    res.status(200).json(albums);
  } catch (err) {
    res.status(500).json({ message: "Error", error: err.message });
  }
});

// '/add' (POST)
// Ruta per afegir un album
// Rebre al body un json amb les dades de l'àlbum a afegir (title, artist, date)
// Respon amb code 201 i un json amb el missatge "Album added successfully" si s'ha afegit correctament
// Si hi ha un error, respon amb code 400 i un json amb el missatge "Error adding album" i l'error detallat
/****************************/
/** Ex 6c ESCRIU EL END POINT */
/****************************/

//No el puc probar, no funciona el postman pero crec que va bé.

app.post('/add', async (req, res) => {
  const { title, artist, date } = req.body;
  const newAlb = new Albums({ title, artist, date });
  try {
    await newAlb.save();
    res.status(201).json({ message: "Album added successfully" });
  } catch (err) {
    res.status(400).json({ message: "Error adding album", error: err.message });
  }
});

// Inicia el servidor
if (uri != "mongodb://127.0.0.1:27017") {
  app.listen(port,  '0.0.0.0', () => {
    console.log(`Server is running on http://localhost:${port}`);
  });
}


