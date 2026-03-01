package dam.m6.uf3.Model;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Model {

    private static final String BASE_URL = "https://m6uf3api-livid.vercel.app";

    private HttpClient httpClient;
    private boolean responseReceived;

    public Model() {
        this.httpClient = HttpClient.newHttpClient();
    }

    // ───────────── GET ALL SOCIS ─────────────
    public List<Socis> getAllSocis() {
        List<Socis> socisList = new ArrayList<>();
        responseReceived = false;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/list"))
                    .GET()
                    .build();

            CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request,
                    HttpResponse.BodyHandlers.ofString());

            responseFuture.thenAccept(response -> {
                String body = response.body();
                socisList.addAll(parseSocis(body));
                responseReceived = true;
            }).exceptionally(e -> {
                e.printStackTrace();
                responseReceived = true;
                return null;
            });

            while (!responseReceived)
                Thread.sleep(100);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return socisList;
    }

    // ───────────── INSERT SOCI ─────────────
    public void inserirSoci(Socis s) {
        responseReceived = false;

        try {
            String jsonBody = s.toJson();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/add_soci"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request,
                    HttpResponse.BodyHandlers.ofString());

            responseFuture.thenAccept(resp -> responseReceived = true)
                    .exceptionally(e -> {
                        e.printStackTrace();
                        responseReceived = true;
                        return null;
                    });

            while (!responseReceived)
                Thread.sleep(100);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ───────────── DELETE SOCI ─────────────
    public void eliminarSoci(String id) {
        responseReceived = false;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/delete_soci/" + id))
                    .DELETE()
                    .build();

            CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request,
                    HttpResponse.BodyHandlers.ofString());

            responseFuture.thenAccept(resp -> responseReceived = true)
                    .exceptionally(e -> {
                        e.printStackTrace();
                        responseReceived = true;
                        return null;
                    });

            while (!responseReceived)
                Thread.sleep(100);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ───────────── UPDATE SOCI ─────────────
    public void modificarSoci(String id, Socis s) {
        responseReceived = false;

        try {
            String jsonBody = s.toJson();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/update_soci/" + id))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request,
                    HttpResponse.BodyHandlers.ofString());

            responseFuture.thenAccept(resp -> responseReceived = true)
                    .exceptionally(e -> {
                        e.printStackTrace();
                        responseReceived = true;
                        return null;
                    });

            while (!responseReceived)
                Thread.sleep(100);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ───────────── GET SOCIS BY NAME ─────────────
    public List<Socis> getSocisByNom(String nom) {
        List<Socis> filtrats = new ArrayList<>();
        for (Socis s : getAllSocis()) {
            if (s.getNom().toLowerCase().contains(nom.toLowerCase())) {
                filtrats.add(s);
            }
        }
        return filtrats;
    }

    // ───────────── GET SOCIS BY DATE ─────────────
    public List<Socis> getSocisByDate(LocalDate start, LocalDate end) {
        List<Socis> filtrats = new ArrayList<>();
        for (Socis s : getAllSocis()) {
            for (LocalDate d : s.getAssistencia()) {
                if (!d.isBefore(start) && !d.isAfter(end)) {
                    filtrats.add(s);
                    break;
                }
            }
        }
        return filtrats;
    }

    // ───────────── PARSE JSON A SOCIS ─────────────
    private List<Socis> parseSocis(String json) {
        List<Socis> llista = new ArrayList<>();

        if (json == null || json.trim().isEmpty() || json.equals("[]") || json.contains("message")) {
            return llista;
        }

        try {

            JSONObject wrapper = new JSONObject(json);

            JSONArray arr;
            if (wrapper.has("data")) {
                arr = wrapper.getJSONArray("data");
            } else {

                arr = new JSONArray(json);
            }

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);

                String nom = obj.getString("nom");
                String cognom = obj.getString("cognom");
                int edat = obj.getInt("edat");
                double pes = obj.getDouble("pes");
                int altura = obj.getInt("altura");
                String subscripcio = obj.getString("subscripcio");

                // Rutina
                ArrayList<String> rutina = new ArrayList<>();
                JSONArray rut = obj.getJSONArray("rutina");
                for (int j = 0; j < rut.length(); j++) {
                    rutina.add(rut.getString(j));
                }

                // Assistència
                ArrayList<LocalDate> assist = new ArrayList<>();
                JSONArray ass = obj.getJSONArray("assistencia");
                for (int j = 0; j < ass.length(); j++) {
                    assist.add(LocalDate.parse(ass.getString(j)));
                }

                String objectius = obj.getString("objectius");

                llista.add(new Socis(nom, cognom, edat, pes, altura, subscripcio, rutina, assist, objectius));
            }

        } catch (Exception e) {
            System.err.println("Error parseando JSON: " + e.getMessage());
        }

        return llista;
    }
}