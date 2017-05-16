package lab7;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Created by yuragogin on 06.05.17.
 */
public class DiscountDB {
    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultSet;
    // ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ
    public static void DiscountDB() throws ClassNotFoundException, SQLException
    {
        connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:discounts.db");

        System.out.println("База Дисконтных Карт Подключена!");
    }

    // --------Создание таблицы--------
    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statement = connection.createStatement();
        statement.execute("CREATE TABLE if not exists 'discounts' " +
                "('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'barcode' INT, 'discount_value' REAL);");

        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void WriteDB() throws SQLException
    {
        statement.execute("INSERT INTO 'discounts' ('barcode', 'discount_value') VALUES (125453, 0.1); ");
        statement.execute("INSERT INTO 'discounts' ('barcode', 'discount_value') VALUES (321789, 0.2); ");
        statement.execute("INSERT INTO 'discounts' ('barcode', 'discount_value') VALUES (456123, 0.3); ");

        System.out.println("Таблица заполнена");
    }

    public static void DeleteFromDB() throws ClassNotFoundException, SQLException {
        statement.execute("DELETE FROM 'discounts' WHERE ID = 16;");
        statement.execute("DELETE FROM 'discounts' WHERE ID = 17;");
        statement.execute("DELETE FROM 'discounts' WHERE ID = 18;");

        System.out.println("Таблица удалена");

    }

    public static void CloseDataBase() throws ClassNotFoundException, SQLException
    {
        connection.close();
        statement.close();
        resultSet.close();

        System.out.println("Соединения с базой закрыты");
    }

    public static double DiscountByBarCode(int barcode) throws ClassNotFoundException, SQLException, NullPointerException
    {
        resultSet = statement.executeQuery("SELECT * FROM discounts");

        while(resultSet.next()) {
            if(resultSet.getInt("barcode") == barcode) return  resultSet.getDouble("discount_value");
        }
        return 0;
    }
}


