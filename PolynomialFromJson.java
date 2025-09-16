import java.io.FileReader;
import java.math.BigInteger;
import java.util.Map;
import java.util.Set;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PolynomialFromJson {
    public static void main(String[] args) {
        try {
            // JSON file read
            FileReader reader = new FileReader("data.json");
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            // keys {n, k}
            JsonObject keys = jsonObject.getAsJsonObject("keys");
            int n = keys.get("n").getAsInt();
            int k = keys.get("k").getAsInt();
            System.out.println("n = " + n + ", k = " + k);

            // iterate all elements
            Set<Map.Entry<String, com.google.gson.JsonElement>> entries = jsonObject.entrySet();
            for (Map.Entry<String, com.google.gson.JsonElement> entry : entries) {
                String key = entry.getKey();
                if (key.equals("keys"))
                    continue; // skip keys block

                JsonObject obj = entry.getValue().getAsJsonObject();
                int base = Integer.parseInt(obj.get("base").getAsString());
                String valueStr = obj.get("value").getAsString();

                // decode to BigInteger
                BigInteger decoded = new BigInteger(valueStr, base);

                System.out.println("Index = " + key +
                        " | Base = " + base +
                        " | Value = " + valueStr +
                        " | Decoded = " + decoded);

                // Example polynomial: y = ax^2 + bx + c
                int a = 1, b = 2;
                int x = Integer.parseInt(key); // index as x
                BigInteger y = decoded; // decoded as y

                // c = y - (ax^2 + bx)
                BigInteger c = y.subtract(BigInteger.valueOf(a * (x * x) + b * x));
                System.out.println("   → For x=" + x + ", y=" + y + " ⇒ c = " + c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
