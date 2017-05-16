package lab7.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.print.Printer;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lab7.DiscountDB;
import lab7.ItemsDb;
import lab7.model.Item;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

public class Controller {

    private ObservableList<Item> usersData = FXCollections.observableArrayList();

    private double totalValue = 0;
    @FXML
    private Button printButton;
    @FXML
    private TextField discountInput;

    @FXML
    private Button getButton;

    @FXML
    private Label totalPrice;

    @FXML
    private TextField numberInput;

    @FXML
    private TextField barCodeInput;

    @FXML
    private Button addButton;

    @FXML
    private TableView<Item> tableCheque;

    @FXML
    private TableColumn<Item, Integer> idColumn;

    @FXML
    private TableColumn<Item, String> nameColumn;

    @FXML
    private TableColumn<Item, Integer> numberColumn;

    @FXML
    private TableColumn<Item, Integer> priceColumn;


    @FXML
    private void initialize() throws ClassNotFoundException, SQLException{
        initData();
        idColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("number"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("price"));
            addButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        int price = ItemsDb.SelectPriceByBarCode(Integer.parseInt(barCodeInput.getText()));
                        String name = ItemsDb.SelectNameByBarCode(Integer.parseInt(barCodeInput.getText()));
                        int id = ItemsDb.SelectIDByBarCode(Integer.parseInt(barCodeInput.getText()));
                        int number = getNumber();
                      usersData.add(new Item(id, name, number, price));
                      addToTotalPrice(price*number);
                      System.out.println(getTotalValue());
                      totalPrice.setText(Double.toString(getTotalValue()));
                    }
                    catch (SQLException e)
                    {
                        System.out.println("Any wrongs with DataBase " + e);
                    }
                    catch (ClassNotFoundException e)
                    {
                        System.out.println("Any wrongs with class " + e);
                    }
                }
            });
            getButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        double percentOfDisc = (1.0 - DiscountDB.DiscountByBarCode(getDiscountCode()));
                        double val = totalValue*percentOfDisc;
                        System.out.println(DiscountDB.DiscountByBarCode(getDiscountCode()));
                        totalPrice.setText(Double.toString(val));
                    }catch (ClassNotFoundException e){
                        System.out.println(e);
                    }catch (SQLException e){
                        System.out.println(e);
                    }

                }
            });

            printButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FileWriter fileWriter = new FileWriter("GoodsList.txt");
                        for(int i = 0; i < usersData.size(); i++){
                            Item item = usersData.get(i);
                            fileWriter.write(item.getId() + " " + item.getNumber() + " " + item.getPrice() + "\n");
                            System.out.print(item.getId() + " " + item.getNumber() + " " + item.getPrice() + "\n");
                            fileWriter.flush();
                        }
                    }catch (IOException e){
                        System.out.println(e);
                    }
                    Platform.exit();
                }
            });

        tableCheque.setItems(usersData);
    }

    private void initData() throws ClassNotFoundException, SQLException {
        int a = ItemsDb.SelectPriceByBarCode(125453);
        usersData.add(new Item(a, "alex", 1, 1));

    }

    public int getNumber()
    {
        String textFromField = numberInput.getText();
        System.out.println(textFromField);
        if(textFromField.length() > 0){
            return Integer.parseInt(textFromField);
        }
        else return 1;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void addToTotalPrice(double x)
    {
        if (x > 0){
            totalValue += x;
        }
    }
    public int getDiscountCode()
    {
        String discCode = discountInput.getText();
        System.out.println(discCode);
        return Integer.parseInt(discCode);
    }

}