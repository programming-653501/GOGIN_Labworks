package lab7;
/**
 * Created by yuragogin on 26.04.17.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class ItemsDb {
    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultSet;

    // ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ
    public static void itmsDB() throws ClassNotFoundException, SQLException
    {
        connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:it_market.db");

        System.out.println("База Подключена!");
    }

    // --------Создание таблицы--------
    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statement = connection.createStatement();
        statement.execute("CREATE TABLE if not exists 'supermarket_items' " +
                "('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'barcode' INT, 'price' REAL);");

        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void WriteDB() throws SQLException
    {
        statement.execute("INSERT INTO 'supermarket_items' ('name', 'barcode', 'price') VALUES ('Petya', 125453, 1); ");
        statement.execute("INSERT INTO 'supermarket_items' ('name', 'barcode', 'price') VALUES ('Vasya', 321789, 2); ");
        statement.execute("INSERT INTO 'supermarket_items' ('name', 'barcode', 'price') VALUES ('Masha', 456123, 3); ");

        System.out.println("Таблица заполнена");
    }

    // -------- Вывод таблицы--------
    public static void ReadDB() throws ClassNotFoundException, SQLException
    {
        resultSet = statement.executeQuery("SELECT * FROM supermarket_items");

        while(resultSet.next())
        {
            int id = resultSet.getInt("id");
            String  name = resultSet.getString("name");
            String  price = resultSet.getString("price");
            System.out.println( "ID = " + id );
            System.out.println( "name = " + name );
            System.out.println( "prise = " + price );
            System.out.println();
        }

        System.out.println("Таблица выведена");
    }

    public static void DeleteFromDB() throws ClassNotFoundException, SQLException {
        statement.execute("DELETE FROM 'supermarket_items' WHERE ID = 16;");
        statement.execute("DELETE FROM 'supermarket_items' WHERE ID = 17;");
        statement.execute("DELETE FROM 'supermarket_items' WHERE ID = 18;");

        System.out.println("Таблица удалена");

    }

    public static void CloseDataBase() throws ClassNotFoundException, SQLException
    {
        connection.close();
        statement.close();
        resultSet.close();

        System.out.println("Соединения с базой закрыты");
    }

    public static void UpdatePriceInDBElement(int barcode, int newPrice) throws ClassNotFoundException, SQLException
    {
        statement.execute("UPDATE 'supermarket_items' SET price = " + newPrice + "  WHERE barcode = " + barcode + ";");
    }


    public static int SelectPriceByBarCode(int barcode) throws ClassNotFoundException, SQLException, NullPointerException
    {
        resultSet = statement.executeQuery("SELECT * FROM supermarket_items");

        while(resultSet.next()) {
            if(resultSet.getInt("barcode") == barcode) return  resultSet.getInt("price");
        }
        return 0;
    }
    public static String SelectNameByBarCode(int barcode) throws ClassNotFoundException, SQLException
    {
        resultSet = statement.executeQuery("SELECT * FROM supermarket_items");

        while(resultSet.next()) {
            if(resultSet.getInt("barcode") == barcode) return  resultSet.getString("name");
        }
        return "";
    }
    public static int SelectIDByBarCode(int barcode) throws ClassNotFoundException, SQLException
    {
        resultSet = statement.executeQuery("SELECT * FROM supermarket_items");

        while(resultSet.next()) {
            if(resultSet.getInt("barcode") == barcode) return  resultSet.getInt("id");
        }
        return 0;
    }

}
