import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Storage {
    public static List<User> users = new ArrayList<>();
    public static List<Gift> gifts = new ArrayList<>();

    public static void main(String[] args){

        User unknownUser = new User(),
                Dima = new User("Dmitrii", "Dementev", "dimon.limon27@instagram.com", "qwerty", 20, "single");
        Gift flowers = new Gift("Flowers", 570.99),
                car = new Gift("BMW", 3000000);
        Dima.addFriend(unknownUser);
        Dima.addGift(car);
        unknownUser.addGift(flowers);
        Dima.create(Dima);
        unknownUser.create(unknownUser);
    }
}
