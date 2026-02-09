VERCEL

https://m6uf3api-livid.vercel.app/



Endpoints



Llistar tots els socis

GET

https://m6uf3api-livid.vercel.app/socis




Filtrar socis per nom

GET

https://m6uf3api-livid.vercel.app/socis/nom/:nom


Exemple:

https://m6uf3api-livid.vercel.app/socis/nom/Marc




Filtrar socis per rang de dates d’assistència

GET

https://m6uf3api-livid.vercel.app/socis/entre-dates/:start/:end


Exemple:

https://m6uf3api-livid.vercel.app/socis/entre-dates/2026-01-01/2026-01-05




Afegir un nou soci

POST

https://m6uf3api-livid.vercel.app/socis


Cos de la sol·licitud (JSON):

{
  "nom": "Marc",
  "cognom": "López",
  "edat": 30,
  "pes": 78.2,
  "altura": 175,
  "subscripcio": "Anual",
  "rutina": ["Pes lliure", "CrossFit"],
  "assistencia": ["2026-01-01", "2026-01-03", "2026-01-05"],
  "objectius": "Guanyar massa muscular"
}




Actualitzar un soci per ID

PUT

https://m6uf3api-livid.vercel.app/socis/:id


Exemple:

https://m6uf3api-livid.vercel.app/socis/ID_DEL_SOCIO




Eliminar un soci per ID

DELETE

https://m6uf3api-livid.vercel.app/socis/:id


Exemple:

https://m6uf3api-livid.vercel.app/socis/ID_DEL_SOCIO