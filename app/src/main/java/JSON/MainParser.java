//package JSON;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Scanner;
//import org.codehaus.jackson.map.ObjectMapper;
//
//
//public class MainParser {
//
//    private String j;
//    public MainParser() {
//        j = (jsonGetRequest("https://opentdb.com/api.php?amount=10&type=multiple"));
//    }
//
//    private String streamToString(InputStream inputStream) {
//        return new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
//
//    }
//
//    private String jsonGetRequest(String urlQueryString) {
//        String json = null;
//        try {
//            URL url = new URL(urlQueryString);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoOutput(true);
//            connection.setInstanceFollowRedirects(false);
//            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Content-Type", "application/json");
//            connection.setRequestProperty("charset", "utf-8");
//            connection.connect();
//            InputStream inStream = connection.getInputStream();
//            json = streamToString(inStream); // input stream to string
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            System.out.println("JSON Error");
//        }
//        System.out.println(json);
//        return json;
//    }
//
//    public String getJSON(){
//        return j;
//    }
//}
//
