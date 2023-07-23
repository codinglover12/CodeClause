import java.sql.*;

public class URLShortener {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/url_shortener";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Techmaster@123";

    private Connection connection;

    public URLShortener() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to generate a random alphanumeric short URL
    private String generateShortURL() {
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int length = 6;
        StringBuilder shortURL = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * allowedChars.length());
            shortURL.append(allowedChars.charAt(randomIndex));
        }
        return shortURL.toString();
    }

    // Method to shorten a long URL and store it in the database
    public String shortenURL(String longURL) {
        String shortURL = generateShortURL();
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO urls (short_url, long_url) VALUES (?, ?)");
            statement.setString(1, shortURL);
            statement.setString(2, longURL);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shortURL;
    }

    // Method to redirect to the original long URL
    public String redirectToLongURL(String shortURL) {
        String longURL = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT long_url FROM urls WHERE short_url = ?");
            statement.setString(1, shortURL);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                longURL = resultSet.getString("long_url");
                System.out.println("Redirecting to: " + longURL);
                // Add your logic to redirect the user to the long URL (e.g., open in a browser)
            } else {
                System.out.println("Invalid short URL.");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return longURL;
    }

    public static void main(String[] args) {
        URLShortener urlShortener = new URLShortener();

        // Example usage
        String longURL = "https://www.example.com";
        String shortURL = urlShortener.shortenURL(longURL);
        System.out.println("Short URL: " + shortURL);

        String redirectURL = shortURL;
        urlShortener.redirectToLongURL(redirectURL);
    }
}
