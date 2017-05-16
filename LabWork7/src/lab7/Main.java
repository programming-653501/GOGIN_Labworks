package lab7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.sql.SQLException;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ItemsDb.itmsDB();
        DiscountDB.DiscountDB();
        ItemsDb.CreateDB();
        DiscountDB.CreateDB();
        //DiscountDB.WriteDB();
        //ItemsDb.DeleteFromDB();
        //ItemsDb.WriteDB();
        ItemsDb.UpdatePriceInDBElement( 321789, 10);
        ItemsDb.ReadDB();
        Parent root = FXMLLoader.load(getClass().getResource("views/main.fxml"));
        primaryStage.setTitle("SUPER/MARKET");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    @Override
    public void stop() throws Exception
    {
        ItemsDb.CloseDataBase();
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        launch(args);
    }
}
