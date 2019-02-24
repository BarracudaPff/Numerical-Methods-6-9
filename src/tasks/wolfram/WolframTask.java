package tasks.wolfram;

import functions.Function;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WolframTask {
    private final String KEY = "3X243L-365U7PG6EK";
    private final String input;

    public WolframTask(Function f) {
        this.input = f.toWolframString();
    }

    public double sendGet() throws Exception {
        System.out.println("Trying to get the exact value...");
        String url = "http://api.wolframalpha.com/v2/query?" +
                "appid=" + KEY +
                "&input=" + input +
                "&output=json";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        Scanner scanner = new Scanner(obj.openStream());
        String response = scanner.useDelimiter("\\Z").next();
        scanner.close();
        JSONObject json = new JSONObject(response);

        try {
            String s = json.getJSONObject("queryresult")
                    .getJSONArray("pods")
                    .getJSONObject(0)
                    .getJSONArray("subpods")
                    .getJSONObject(0)
                    .getString("plaintext");
            if (!s.contains("="))
                throw new RuntimeException("No = in answer");
            return Double.parseDouble(s.split("=")[1].replaceAll(" ", ""));
        } catch (JSONException e) {
            throw new IllegalArgumentException("Wrong input");
        }


    }
}
