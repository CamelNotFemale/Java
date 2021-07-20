import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import org.springframework.security.crypto.bcrypt.BCrypt;
public class User implements Crud<User> {
    private int userID;
    private String name;
    private String soname;
    private String email;
    private String password;
    private int age;
    private String status;
    private List<User> friends;
    private List<Gift> gifts;
    private List<String> messageIn;
    private List<String> messageOut;
    private static int count = 0;

    private String inputPassword(Scanner in){
        System.out.print("Введите пароль (не меньше 6 символов): ");
        try{
            String password = in.nextLine();
            if (password.length() < 6) throw new Exception("Слишком короткий пароль");
            password = BCrypt.hashpw(password, BCrypt.gensalt());
            System.out.println("Зашифрованный пароль: " + password);
        }
        catch(Exception ex){
            ex.printStackTrace();
            inputPassword(in);
        }
        return password;
    }
    private int inputAge(Scanner in){
        System.out.print("Введите возраст: ");
        try{
            this.age = in.nextInt();
            if (age <= 0 || age > 150)
                throw new Exception("Введён недопустимый возраст (от 0 до 150)");
        }
        catch(Exception ex){
            ex.printStackTrace();
            inputAge(in);
        }
        return age;
    }
    private String inputEmail(Scanner in){
        System.out.print("Введите email: ");
        try{
            String email = in.nextLine();
            if (!email.contains("@")) throw new Exception("Отсутствует доменное имя почты");
        }
        catch(Exception ex){
            ex.printStackTrace();
            inputEmail(in);
        }
        return email;
    }

    public User(){
        Scanner in = new Scanner(System.in);
        count++;
        this.userID = count;
        System.out.print("Введите имя пользователя: ");
        this.name = in.nextLine();
        System.out.print("Введите фамилию пользователя: ");
        this.soname = in.nextLine();
        this.email = inputEmail(in);
        this.password = inputPassword(in);
        this.age = inputAge(in);
        this.friends = new ArrayList<>();
        this.gifts = new ArrayList<>();
        this.messageIn = new ArrayList<>();
        this.messageOut = new ArrayList<>();
    }

    public User(String name, String soname, String email, String password, int age, String status) {
        count++;
        this.userID = count;
        this.name = name;
        this.soname = soname;
        this.email = email;
        this.password = password;
        this.age = age;
        this.status = status;
        this.friends = new ArrayList<>();
        this.gifts = new ArrayList<>();
        this.messageIn = new ArrayList<>();
        this.messageOut = new ArrayList<>();
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getSoname() {
        return soname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getStatus() {
        return status;
    }

    public List<User> getFriends() {
        return friends;
    }

    public List<Gift> getGifts() {
        return gifts;
    }

    public List<String> getMessageIn() {
        return messageIn;
    }

    public List<String> getMessageOut() {
        return messageOut;
    }

    public static int getCount() {
        return count;
    }

    public void addGift(Gift gift){
        gifts.add(gift);
        System.out.println(this.name + " получил в подарок " + gift.getName());
    }

    public void addFriend(User friend){
        friends.add(friend);
        System.out.println(this.name + " подружился с " + friend.getName());
    }

    public void deleteFriend(User friend) {
        if (friends.contains(friend)) {
            friends.remove(friend);
            System.out.println(this.name + " поругался с " + friend.getName());
        }
        else System.out.println("У " + this.name + " нет такого друга");
    }

    @Override
    public void create(User model){
        this.userID = model.userID;
        this.name = model.name;
        this.soname = model.soname;
        this.email = model.email;
        this.password = model.password;
        this.age = model.age;
        this.status = model.status;
        this.friends = model.friends;
        this.gifts = model.gifts;
        this.messageIn = model.messageIn;
        this.messageOut = model.messageOut;
        Storage.users.add(this);
        for (Gift gift: gifts) {
            if (!Storage.gifts.contains(gift)) { // если у пользователя имеются подарки еще не добавленные в базу
                gift.create(gift); // то добавим их
            }
        }
    }

    @Override
    public void update(User model) {
        this.name = model.name;
        this.soname = model.soname;
        this.email = model.email;
        this.password = model.password;
        this.age = model.age;
        this.status = model.status;
        this.friends.addAll(model.friends);
        this.gifts.addAll(model.gifts);
        this.messageIn.addAll(model.messageIn);
        this.messageOut.addAll(model.messageOut);
    }

    @Override
    public User find(Integer id) {
        for (User user: Storage.users){
            if (user.userID == id){
                return user;
            }
        }
        System.out.println("Такой записи не существует!");
        return null;
    }

    @Override
    public void delete(Integer id) {
        for (User user: Storage.users){
            if (user.userID == id){
                Storage.users.remove(user);
            }
        }
        System.out.println("Такой записи не существует!");
    }
}