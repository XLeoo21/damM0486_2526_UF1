require('dotenv').config();
const express = require('express');
const mongoose = require('mongoose');
const app = express();
const port = process.env.PORT || 3021;

// Middleware per parsejar JSON
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// URI de MongoDB des del .env
const uri = process.env.MONGO_URI;
console.log("URI:", uri);

// Connecta amb Mongoose
mongoose.connect(uri)
  .then(() => console.log('Connected to MongoDB: GymManager'))
  .catch(err => console.log('Error connecting to MongoDB:', err));

// Definició del model Socis (simplificat)
const socisSchema = new mongoose.Schema({
    nom: String,
    cognom: String,
    edat: Number,
    pes: Number,
    altura: Number,
    subscripcio: String,
    rutina: [String],
    assistencia: [String],
    objectius: String
});

const Soci = mongoose.model('Socis', socisSchema, 'Socis');

/********************************************
 * ENDPOINTS
 ********************************************/

// Ruta arrel
app.get('/', (req, res) => {
    res.send('API GymManager is running!');
});

// Obtenir tots els socis
app.get('/socis', async (req, res) => {
    try {
        const socis = await Soci.find();
        res.status(200).json(socis);
    } catch (err) {
        res.status(500).json({ message: 'Error fetching socis', error: err.message });
    }
});

// Afegir un nou soci
app.post('/socis', async (req, res) => {
    try {
        const nouSoci = new Soci(req.body);
        await nouSoci.save();
        res.status(201).json({ message: 'Soci created', data: nouSoci });
    } catch (err) {
        res.status(500).json({ message: 'Error creating soci', error: err.message });
    }
});

// Filtrar socis per nom
app.get('/socis/nom/:nom', async (req, res) => {
    try {
        const { nom } = req.params;
        const result = await Soci.find({ nom: nom });
        res.status(200).json(result);
    } catch (err) {
        res.status(500).json({ message: 'Error fetching soci by nom', error: err.message });
    }
});

// Filtrar socis per dates d'assistència
app.get('/socis/entre-dates/:start/:end', async (req, res) => {
  try {
    const { start, end } = req.params;

    const socis = await Soci.find({
      assistencia: {
        $elemMatch: {
          $gte: start,
          $lte: end
        }
      }
    });

    res.status(200).json(socis);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});



// Actualitzar un soci per ID
app.put('/socis/:id', async (req, res) => {
    try {
        const updated = await Soci.findByIdAndUpdate(req.params.id, req.body, { new: true });
        res.status(200).json({ message: 'Soci updated', data: updated });
    } catch (err) {
        res.status(500).json({ message: 'Error updating soci', error: err.message });
    }
});

// Eliminar un soci per ID
app.delete('/socis/:id', async (req, res) => {
    try {
        await Soci.findByIdAndDelete(req.params.id);
        res.status(200).json({ message: 'Soci deleted' });
    } catch (err) {
        res.status(500).json({ message: 'Error deleting soci', error: err.message });
    }
});

/********************************************
 * INICI DEL SERVIDOR
 ********************************************/
app.listen(port, '0.0.0.0', () => {
    console.log(`Server is running at http://localhost:${port}`);
});
