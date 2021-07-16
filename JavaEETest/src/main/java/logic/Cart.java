package logic;
/** Класс Корзины покупателя */
public class Cart {
    /** Наименование товара */
    private String name;
    /** Количество */
    private int quantity;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
