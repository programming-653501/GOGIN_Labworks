package lab7.model;
import java.lang.String;
public class Item {

    private int id;
    private String name;
    int number;
    private int price;

    public Item(int id, String name, int number, int price) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.price = price;
    }

    public Item() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
