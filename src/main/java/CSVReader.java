import Entidades.*;
import TADs.Hash.HashTabla.HashTabla;
import TADs.Hash.HashTabla.HashTablaImpl;
import TADs.Hash.HashTabla.NodoHash;
import TADs.Hash.HashTable.LinearProbingHashTable;
import TADs.Heap.MyHeap;
import TADs.Heap.MyHeapImpl;
import TADs.Heap.NodoTreeBin;
import TADs.Lista.LL;
import TADs.MyArrayList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CSVReader {
    static FileManager fileManager = new FileManager();

    private static String DATA_SET = fileManager.getFilePath_DataSet();
    private static String DRIVERS = fileManager.getFilePath_Drivers();

//    public static void leerCSV(String[] args) {
//        try {
//            Reader in = new FileReader(DATA_SET);
//            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
//            for (CSVRecord record : records) {
//                String user_name = record.get("user_name");
//                String user_location = record.get("user_location");
//                String user_description = record.get("user_description");
//                String user_created = record.get("user_created");
//                String user_followers = record.get("user_followers");
//                String user_friends = record.get("user_friends");
//                String user_favourites = record.get("user_favourites");
//                String user_verified = record.get("user_verified");
//                String date = record.get("date");
//                String text = record.get("text");
//                String hashtags = record.get("hashtags");
//                String source = record.get("source");
//                String is_retweet = record.get("is_retweet");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    // ----------  ----------  Primer funcion ----------  ----------
    public static void topPilotos(int mesInput, int anoInput) {

        long startTime = System.nanoTime();

        // ----------  ----------  esto es para leer los pilotos del .txt  ----------  ----------
        LinearProbingHashTable<String, Integer> contadorPilotos = new LinearProbingHashTable<>();

        MyArrayList<String> listaPilotos = new MyArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(DRIVERS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                listaPilotos.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

// Inicializar los contadores de todos los pilotos a 0
        for (int i = 0; i < listaPilotos.size(); i++) {
            contadorPilotos.put(listaPilotos.get(i), 0);
        }

        try {
            Reader in = new FileReader(DATA_SET);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            int contadorTweets = 0;
            int conRecord = 0;

            for (CSVRecord record : records) {
                conRecord++;

                //recorro cada tupla
                String text = record.get("text");
                String date = record.get("date");

                //hago el split de la fecha por - para sacar dia mes y el año con la hora
                String[] parts = date.split("-");

                if (parts.length != 3) {
                    System.out.println("Fecha mal formada: " + date);
                    continue;
                }

                //saco las partes q me interesan
                String ano = parts[0];
                String mes = parts[1];

                int anoInt = Integer.parseInt(ano);
                int mesInt = Integer.parseInt(mes);

                if (anoInt == anoInput && mesInt == mesInput) contadorTweets++;

                // Comprueba cada piloto
                for (int i = 0; i < listaPilotos.size(); i++) {
                    String piloto = listaPilotos.get(i);
                    String[] partesPiloto = piloto.split(" ");
                    String nombrePiloto = partesPiloto[0];
                    String apePiloto = partesPiloto[1];

                    if (text.contains(nombrePiloto) || text.contains(apePiloto) && anoInt == anoInput && mesInt == mesInput) {
                        // Incrementa el contador para este piloto
                        contadorPilotos.put(piloto, contadorPilotos.get(piloto) + 1);
                    }
                }


            }
            System.out.println("tweets en ese mes: " + contadorTweets);
            System.out.println("tuplas leidas: " + conRecord);

            LL<Piloto> pilotosOrdenados = new LL<>();

            // Imprimir los contadores
            for (int i = 0; i < listaPilotos.size(); i++) {
                String piloto = listaPilotos.get(i);
                int contPiloto = contadorPilotos.get(piloto);
                Piloto tempPiloto = new Piloto(piloto, contPiloto);
                pilotosOrdenados.add(tempPiloto);
            }

            pilotosOrdenados.sort();

            for (int i = 0; i < 10; i++) {
                System.out.println(i + 1 + "." + pilotosOrdenados.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.println();
        double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;
        System.out.println("La función tardó " + roundedDuration + " segundos.");
        System.out.println();
    }


    // ----------  ----------  Segunda funcion ----------  ----------
    public static void topUsuariosTweets() {
        long startTime = System.nanoTime();

        try {
            Reader in = new FileReader(DATA_SET);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);

            LinearProbingHashTable<User, LL<Tweet>> usuariosReg = new LinearProbingHashTable<>();
            LL<UsuarioConTweets> usuariosAOrdenar = new LL<>();

            for (CSVRecord record : records) {
                // saco las 3 columnas q quiero
                String tweetIDstr = record.get("");
                int tweetId = Integer.parseInt(tweetIDstr);
                System.out.println("tweetId = " + tweetId);

                String user_name = record.get("user_name");
                String user_verified = record.get("user_verified");

                //creo un usuario y un tweet
                User tempUser = new User(user_name, user_verified);
                Tweet tempTweet = new Tweet(tweetId);
                //cargo todos los usuarios con su lista de tweets
                if (!usuariosReg.contains(tempUser)) {
                    tempUser.getListaTweets().add(tempTweet);
                    usuariosReg.put(tempUser, tempUser.getListaTweets());
                } else {
                    User registeredUser = usuariosReg.getUser(tempUser);
                    if (registeredUser != null) {
                        registeredUser.getListaTweets().add(tempTweet);
                        usuariosReg.put(registeredUser, registeredUser.getListaTweets());
                    }
                }
            }
            // intento ordenar un pedazo

            int totalUsuarios = usuariosReg.getEntries().size();
            int lote = 2000;  //hay que buscar el mejor tamaño de lote

            for (int subLote = 0; subLote < totalUsuarios; subLote += lote) {
                System.out.println("Procesando lote que comienza en el usuario " + subLote);

                for (int i = subLote; i < Math.min(subLote + lote, totalUsuarios); i++) {
                    String name = usuariosReg.getEntries().get(i).getKey().getName();
                    String verif = usuariosReg.getEntries().get(i).getKey().getVerificado();
                    LL<Tweet> listaTweetsAgregados = usuariosReg.getEntries().get(i).getKey().getListaTweets();
                    UsuarioConTweets tempUsuarioConTweets = new UsuarioConTweets(name, verif, listaTweetsAgregados);
                    usuariosAOrdenar.add(tempUsuarioConTweets);
                }
                usuariosAOrdenar.sort();
            }

            System.out.println();
            System.out.println();

            for (int i = 0; i < 15; i++) {
                System.out.println(i + 1 + "." + "Usuario: " + usuariosAOrdenar.get(i).getName() + " Verificado: " + usuariosAOrdenar.get(i).getIsVerified() + " Tweets: " + usuariosAOrdenar.get(i).getListaTweetsCargados().size());
            }
            System.out.println("Cantidad total de usuarios: " + usuariosReg.getEntries().size());

        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.println();
        double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;
        System.out.println("La función tardó " + roundedDuration + " segundos.");
        System.out.println();
    }

    // ----------  ----------  Tercera funcion ----------  ----------
    public static void hashtagDistintos(String fecha) {

        long startTime = System.nanoTime();

        try {
            Reader in = new FileReader(DATA_SET);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);

            LinearProbingHashTable<HashTag, Integer> hashTagsReg = new LinearProbingHashTable<>();

            for (CSVRecord record : records) {
                String date = record.get("date");
                String hashtags = record.get("hashtags");
                String[] hashtagsParts = hashtags.split(",");

                for (String parteHashtag : hashtagsParts) {
                    // le saco los corchetes
                    String parteHashSinCorcheteIzq = parteHashtag.replace("[", "");
                    String parteHashSinCorcheteDer = parteHashSinCorcheteIzq.replace("]", "");

                    // le saco las comillas simples y los espacios
                    String hashTagLimpio = parteHashSinCorcheteDer.replace("'", " ");
                    String hasTagReLimpio = hashTagLimpio.replace(" ", "");

                    HashTag hashTagIndividual = new HashTag(hasTagReLimpio);

                    if (!hashTagsReg.contains(hashTagIndividual) && date.contains(fecha)) { //en el caso que no este registrado
                        hashTagsReg.put(hashTagIndividual, 1);
                    } else if (hashTagsReg.contains(hashTagIndividual) && date.contains(fecha)) { //en el caso q ya este registrado
                        hashTagsReg.put(hashTagIndividual, hashTagsReg.get(hashTagIndividual) + 1);
                    }
                }
            }

            System.out.println("Cantidad de hashtags en el dia " + fecha + ": " + hashTagsReg.getEntries().size());
            long endTime = System.nanoTime();
            long durationInMilliseconds = Math.abs((endTime - startTime) / 1_000_000);
            System.out.println();
            double roundedDuration = Math.round(durationInMilliseconds * 10.0) / 10.0;
            System.out.println("La función tardó " + roundedDuration + " milisegundos.");
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ----------  ----------  Cuarta funcion ----------  ---------- *Michel*
    public static void hashtagMasUsado(String fecha) {
        try {
            long startTime = System.nanoTime();
            Reader in = new FileReader(DATA_SET);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);

            HashTabla<HashTag, Integer> hashTagsReg = new HashTablaImpl<>();

            for (CSVRecord record : records) {
                String date = record.get("date");
                String hashtags = record.get("hashtags");
                // limpia la data eliminando corchetes
                String parteHashSinCorcheteIzq = hashtags.replace("[", "");
                String parteHashSinCorcheteDer = parteHashSinCorcheteIzq.replace("]", "");
                // independisa los hashtags de la lista del dataset
                String[] hashtagsSplit = parteHashSinCorcheteDer.split(",");
                // chequea la fecha
                if (date.contains(fecha)) {
                    for (String parteHashtag : hashtagsSplit) {
                        // elimina espacios y comillas para procesar y chequear los hashtagas
                        HashTag value = new HashTag(parteHashtag.replaceAll("[\\s'']", ""));
                        if (!hashTagsReg.contains(value)) { //en el caso que no este registrado
                            hashTagsReg.put(value, 1);
                        } else if (hashTagsReg.contains(value)) { //en el caso q ya este registrado
                            NodoHash nodo = hashTagsReg.get(value);
                            Integer newValue = (Integer) nodo.getData();
                            hashTagsReg.upDate(value, newValue + 1);
                        }
                    }
                }
            }

            int contHashtags = 0;
            String hashtag = " ";

            for (NodoHash<HashTag, Integer> entry : hashTagsReg.getTabla()) {
                // controla en quedarce con aquel hashtag queaparece con mayor frecuencia y que nosea F1 o f1
                if (entry != null && entry.getData() > contHashtags && !entry.getKey().getText().equals("F1") && !entry.getKey().getText().equals("f1")) {
                    hashtag = entry.getKey().getText();
                    contHashtags = entry.getData();
                }
            }
            // imprime por salida estandar el hashtag de mas aparece para la fecha ingresada
            //en caso que no exista alguno para la fecha ingresada se advierte que no hay Hashtag para ese dia
            if (!hashtag.equals(" ")) {
                System.out.println();
                System.out.println("Hashtags más usado el " + fecha + ": " + hashtag);
            } else {
                System.out.println();
                System.out.println("No hay Hashtags para el día " + fecha);
            }
            long endTime = System.nanoTime();
            double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.println();
            double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;
            System.out.println("La función tardó " + roundedDuration + " segundos.");
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ----------  ----------  Quinta funcion ----------  ---------- *Michel*
    public static void topSiteCuentas() {
        try {
            long startTime = System.nanoTime();
            Reader in = new FileReader(DATA_SET);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);

            MyHeap<Integer, User> cuentas = new MyHeapImpl<>("maximo");

            for (CSVRecord record : records) {
                // limpia los espacios y recolecta nombre y favoritos
                String user_name = record.get("user_name").replaceAll("\\s", ""); //nombre usuario
                String user_favourites = record.get("user_favourites"); //cantidad de favoritos
                String user_verified = record.get("user_verified");
                Integer user_favouritesInt = 0;

                User newUser = new User(user_name, user_verified);

                boolean bandera = false;
                // limpia los datos de la columna favoritos y los lleva a valores puros Integer
                if (user_favourites.contains(".") && !user_favourites.contains("-") && !user_favourites.contains(":")) {
                    double doubleValue = Double.parseDouble(user_favourites);
                    user_favouritesInt = (int) doubleValue;
                    bandera = true;
                } else if (!user_favourites.contains("-") && !user_favourites.contains(":")) {
                    user_favouritesInt = Integer.parseInt(user_favourites);
                    bandera = true;
                }
                // en una estructura Heap carga los datos de cantidad de favoritos segun cada usuario
                // se decide esta estrucura ya que implementa un MAXheap organizado desde mayor a menor
                // usuado la cantidad de favoritos como clave
                if (bandera) {
                    newUser.setFavourites(user_favouritesInt);
                    cuentas.insert(user_favouritesInt, newUser);
                }
            }

            // como la estrucura Maxheap ya tiene en su nodo raiz el mayor usuario con favoritos
            // resta ir retirando las raizes en caso que este repetido el usuario se tomara
            // el primero ya que la estrucura MAXheap lo organizo estando primero el de mayor al momento de favoritos
            NodoTreeBin<Integer, User> user;
            HashTabla<String, Integer> usuariosYaRecorrido = new HashTablaImpl<>();
            // devuelvo el usuario y sus favoritos
            int i = 1;
            System.out.println();
            while (i <= 7) {
                user = cuentas.delete();
                String user_name = user.getData().getName(); // nombre usuario
                Integer user_favourites = user.getKey(); //cantidad de favoritos
                if (!usuariosYaRecorrido.contains(user_name)) {
                    usuariosYaRecorrido.put(user_name, user_favourites);
                    System.out.println(i + ") " + user_name + " " + user_favourites);
                    ++i;
                }
            }
            long endTime = System.nanoTime();
            double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.println();
            double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;
            System.out.println("La función tardó " + roundedDuration + " segundos.");
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ----------  ----------  Sexta funcion ----------  ---------- *Michel*
    public static void tweetsFrase(String frase) {
        try {
            long startTime = System.nanoTime();
            Reader in = new FileReader(DATA_SET);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            int count = 0;
            for (CSVRecord record : records) {
                //limpia los datos y chequea tweet a tweet la existencia de la palabra o frase solicitada
                String tweets = record.get("text").replaceAll("\\s", "");
                String fraseValue = frase.replaceAll("\\s", "");
                if (tweets.contains(fraseValue)) {
                    count++;
                }
            }
            // retorna por salida estandar la frecuencia de la palabra o frase ingresada por consola
            if (count != 0) {
                System.out.println();
                System.out.println("Existen " + count + " tweets con la frase/palabra " + "'" + frase + "'");
            } else {
                System.out.println();
                System.out.println("No hay tweets con la frase/palabra " + frase);
            }
            long endTime = System.nanoTime();
            double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.println();
            double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;
            System.out.println("La función tardó " + roundedDuration + " segundos.");
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
