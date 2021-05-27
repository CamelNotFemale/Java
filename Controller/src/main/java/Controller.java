import com.leti.*;

import javax.persistence.*;

public class Controller {
    public static void main(String[] args) {
        // Создание и отображение экранной формы
        App controller= new App();
        controller.show();

        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_persistence");
        //EntityManager em = emf.createEntityManager();
        /*List<Bus> busList = em.createQuery("SELECT b FROM Bus b").getResultList();
        if (busList!=null) {
            for (Bus bus: busList) {
                System.out.println(bus.toTableFormat()[1]);
            }
        }*/
        /*em.getTransaction().begin();
        System.out.println("before");
        Bus bus = new Bus("n777nn", 68);
        Bus test = new Bus();
        Driver Dima = new Driver();
        Route route_5 = new Route(5, "Stul", "Krovat'");
        bus.hireToDriver(Dima);
        bus.chooseRoute(route_5);
        em.persist(bus);
        em.persist(test);

        em.getTransaction().commit();
        System.out.println("Well done!");*/
        /*,
                minibus = new Bus("n123mo", 36);
        Driver Vlad = new Driver("Vlad", 27, 3),
                Gleb = new Driver("Gleb", 56, 9);
        Route route_2 = new Route(2, "Pionerskaya", "Petrogradskaya"),
                route_666 = new Route(666, "Krovat'", "Stul");*/
        // тесты второй лабы
        //em.persist(bus);
        //em.persist(test);
        //em.getTransaction().commit();

        //System.out.println("ID: " + test.getId());
        /*
        // тесты для первой лабы
        bus.hireToDriver(Vlad);
        bus.chooseRoute(route_2);
        Route.showReport();
        minibus.hireToDriver(Vlad);
        bus.hireToDriver(Gleb);
        minibus.chooseRoute(route_2);
        bus.chooseRoute(route_666);
        Route.showReport();
        Driver.printList();*/
    }
}