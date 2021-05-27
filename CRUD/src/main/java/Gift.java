import java.util.Scanner;

public class Gift implements Crud<Gift>{
    private int giftID;
    private String name;
    private double price;
    private static  int count = 0;

    public Gift() {
        Scanner in = new Scanner(System.in);
        count++;
        this.giftID = count;
        System.out.print("Введите имя подарка: ");
        this.name = in.nextLine();
        this.price = 0;
        in.close();
    }

    public Gift(String name, double price) {
        count++;
        this.giftID = count;
        this.name = name;
        if (price<0) this.price = price;
        else this.price = 0;
    }

    public int getGiftID() {
        return giftID;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public void create(Gift model){
        this.name = model.name;
        this.price = model.price;
        this.giftID = model.giftID;
        Storage.gifts.add(this);
    }

    @Override
    public void update(Gift model) {
        this.name = model.name;
        this.price = model.price;
    }

    @Override
    public Gift find(Integer id) {
        for (Gift gift: Storage.gifts){
            if (gift.giftID == id){
                return gift;
            }
        }
        System.out.println("Такой записи не существует!");
        return null;
    }

    @Override
    public void delete(Integer id) {
        for (Gift gift: Storage.gifts){
            if (gift.giftID == id){
                Storage.gifts.remove(gift);
            }
        }
        System.out.println("Такой записи не существует!");
    }
}