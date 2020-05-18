package pl.brzezinski.bookt.dataGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.restaurantMenu.Meal;
import pl.brzezinski.bookt.model.restaurantMenu.RestaurantMenu;
import pl.brzezinski.bookt.model.tables.ReservedTable;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.tables.SchemaTable;
import pl.brzezinski.bookt.model.enums.Genre;
import pl.brzezinski.bookt.model.users.Role;
import pl.brzezinski.bookt.model.users.User;
import pl.brzezinski.bookt.repository.*;
import pl.brzezinski.bookt.service.ReservedTableService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Service
public class GenerateSomeData {


    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_RESTAURATEUR = "ROLE_RESTAURATEUR";

    private RestaurantRepository restaurantRepository;
    private ReservedTableRepository reservedTableRepository;
    private SchemaTableRepository schemaTableRepository;
    private ReservationRepository reservationRepository;
    private RestaurantMenuRepository restaurantMenuRepository;
    private MealRepository mealRepository;
    private UserRepository userRepository;
    private ReservedTableService reservedTableService;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public GenerateSomeData(RestaurantRepository restaurantRepository, ReservedTableRepository reservedTableRepository, SchemaTableRepository schemaTableRepository, ReservationRepository reservationRepository, RestaurantMenuRepository restaurantMenuRepository, MealRepository mealRepository, ReservedTableService reservedTableService, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.restaurantRepository = restaurantRepository;
        this.reservedTableRepository = reservedTableRepository;
        this.schemaTableRepository = schemaTableRepository;
        this.reservationRepository = reservationRepository;
        this.restaurantMenuRepository = restaurantMenuRepository;
        this.mealRepository = mealRepository;
        this.reservedTableService = reservedTableService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    Restaurant podFredra;
    Restaurant cK;
    Restaurant restaurant3;
    Restaurant restaurant4;

    User user;
    User admin;
    User restaurateurPodFreda;
    User restaurateurCK;

    SchemaTable schemaTable1;
    SchemaTable schemaTable2;
    SchemaTable schemaTable3;
    SchemaTable schemaTable4;
    SchemaTable schemaTable5;
    SchemaTable schemaTable6;
    SchemaTable schemaTable7;
    SchemaTable schemaTable8;
    SchemaTable schemaTable9;
    SchemaTable schemaTable10;
    SchemaTable schemaTable11;
    SchemaTable schemaTable12;


    @PostConstruct
    public void createData(){
        createRoleIfNotFound(ROLE_ADMIN);
        createRoleIfNotFound(ROLE_USER);
        createRoleIfNotFound(ROLE_RESTAURATEUR);

        Role adminRole = roleRepository.findByName(ROLE_ADMIN);
        Role userRole = roleRepository.findByName(ROLE_USER);
        Role restaurateurRole = roleRepository.findByName(ROLE_RESTAURATEUR);

        admin = new User();
        admin.setName("admin");
        admin.setPassword(passwordEncoder.encode("adminPassword"));
        admin.setEmail("admin@admin.com");
        admin.setRoles(Arrays.asList(adminRole));
        userRepository.save(admin);

        user = new User();
        user.setName("Pawel");
        user.setPassword(passwordEncoder.encode("userPassword"));
        user.setEmail("user@user.com");
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);

        restaurateurPodFreda = new User();
        restaurateurPodFreda.setName("Cecylia Frycz");
        restaurateurPodFreda.setPassword(passwordEncoder.encode("restaurateurPassword"));
        restaurateurPodFreda.setEmail("restaurant@podfreda.com");
        restaurateurPodFreda.setRoles(Arrays.asList(restaurateurRole));
        userRepository.save(restaurateurPodFreda);

        restaurateurCK = new User();
        restaurateurCK.setName("Gruby Benek");
        restaurateurCK.setPassword(passwordEncoder.encode("restaurateurPassword"));
        restaurateurCK.setEmail("restaurant@ck.com");
        restaurateurCK.setRoles(Arrays.asList(restaurateurRole));
        userRepository.save(restaurateurCK);
    }


    private Role createRoleIfNotFound(String name){
        Role role = roleRepository.findByName(name);
        if (role == null){
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }


    @PostConstruct
    public void createRestaurantData() {
        podFredra = new Restaurant("Pod Fredra", "Rynek Ratusz 1", "Wroclaw", "54-900", Genre.POLISH, "http://www.podfredra.pl", "restauracja@podfreda.pl", LocalTime.of(10, 0), LocalTime.of(23, 0), "899-998-323");
        podFredra.setDefaultMinutesForReservation(180);
        podFredra.setMinutesBetweenReservations(15);
        podFredra.setMinimumMinutesForReservation(60);
        podFredra.setMinPlaces(0);
        podFredra.setMaxPlaces(2);
        podFredra.setRestaurantOwner(restaurateurPodFreda);
        restaurantRepository.save(podFredra);

        cK = new Restaurant("Cesarsko Królewska", "Rynek 12", "Wroclaw", "32-999", Genre.POLISH, "http://www.ck.pl", "restauracja@ck.pl", LocalTime.of(12, 0), LocalTime.of(23, 0), "111-222-333");
        cK.setDefaultMinutesForReservation(120);
        cK.setMinutesBetweenReservations(20);
        cK.setMinimumMinutesForReservation(60);
        cK.setMinPlaces(0);
        cK.setMaxPlaces(2);
        cK.setRestaurantOwner(restaurateurCK);
        restaurantRepository.save(cK);

//        restaurantRepository.save(restaurant3 = new Restaurant("La Scala", "Rynek 38", "Wroclaw", "50-102", Genre.ITALIAN, "http://www.lascala.pl", "restauracja@lascala.pl", LocalTime.of(12, 0), LocalTime.of(23, 0), "71-372-53-94"));
//        restaurantRepository.save(restaurant4 = new Restaurant("Akropolis", "Rynek 16/17", "Wroclaw", "50-101", Genre.GREEK, "http://www.akropolis.wroc.pl", "restauracja@akropolis.pl", LocalTime.of(10, 0), LocalTime.of(23, 0), "71-343-14-13"));
    }

    @PostConstruct
    public void addSchemaTables() {
        schemaTable1 = new SchemaTable(1, 2);
        schemaTable2 = new SchemaTable(2, 2);
        schemaTable3 = new SchemaTable(3, 2);
        schemaTable4 = new SchemaTable(4, 2);
        schemaTable5 = new SchemaTable(5, 4);
        schemaTable6 = new SchemaTable(6, 4);
        schemaTable7 = new SchemaTable(7, 20);
        schemaTable1.setRestaurant(podFredra);
        schemaTable2.setRestaurant(podFredra);
        schemaTable3.setRestaurant(podFredra);
        schemaTable4.setRestaurant(podFredra);
        schemaTable5.setRestaurant(podFredra);
        schemaTable6.setRestaurant(podFredra);
        schemaTable7.setRestaurant(podFredra);
        schemaTableRepository.save(schemaTable1);
        schemaTableRepository.save(schemaTable2);
        schemaTableRepository.save(schemaTable3);
        schemaTableRepository.save(schemaTable4);
        schemaTableRepository.save(schemaTable5);
        schemaTableRepository.save(schemaTable6);
        schemaTableRepository.save(schemaTable7);


        schemaTable8 = new SchemaTable(1, 2);
        schemaTable9 = new SchemaTable(2, 4);
        schemaTable10 = new SchemaTable(3, 6);
        schemaTable11 = new SchemaTable(4, 8);
        schemaTable12 = new SchemaTable(5, 10);
        schemaTable8.setRestaurant(cK);
        schemaTable9.setRestaurant(cK);
        schemaTable10.setRestaurant(cK);
        schemaTable11.setRestaurant(cK);
        schemaTable12.setRestaurant(cK);
        schemaTableRepository.save(schemaTable8);
        schemaTableRepository.save(schemaTable9);
        schemaTableRepository.save(schemaTable10);
        schemaTableRepository.save(schemaTable11);
        schemaTableRepository.save(schemaTable12);
    }

    @PostConstruct
    public void addReservations() {
        Reservation reservation1 = new Reservation();
        reservation1.setName("Rezerwacja 1");
        reservation1.setNumberOfPersons(2);
        reservation1.setPhoneNumber("999-000-333");
        reservation1.setNotes("Nice table please");
        reservation1.setDateTime(LocalDateTime.of(2020, 12, 12, 16, 00));
        reservation1.setRestaurant(podFredra);

        ReservedTable reservedTable1 = new ReservedTable(
                schemaTable1.getTableNumber(),
                schemaTable1.getPlaces(),
                reservation1.getDateTime());
        reservationRepository.save(reservation1);
        reservedTable1.setReservation(reservation1);
        reservedTable1.setRestaurant(reservation1.getRestaurant());
        reservedTableService.add(reservedTable1);
        reservation1.setReservedTable(reservedTable1);
        reservationRepository.save(reservation1);

        Reservation reservation2 = new Reservation();
        reservation2.setName("Rezerwacja 2");
        reservation2.setNumberOfPersons(2);
        reservation2.setPhoneNumber("222-000-111");
        reservation2.setNotes("By the window please");
        reservation2.setDateTime(LocalDateTime.of(2020, 12, 12, 10, 00));
        reservation2.setRestaurant(podFredra);

        ReservedTable reservedTable2 = new ReservedTable(
                schemaTable1.getTableNumber(),
                schemaTable1.getPlaces(),
                reservation2.getDateTime());
        reservationRepository.save(reservation2);
        reservedTable2.setReservation(reservation2);
        reservedTable2.setRestaurant(reservation2.getRestaurant());
        reservedTableService.add(reservedTable2);
        reservation2.setReservedTable(reservedTable2);
        reservationRepository.save(reservation2);

        Reservation reservation3 = new Reservation();
        reservation3.setName("Rezerwacja 3");
        reservation3.setNumberOfPersons(2);
        reservation3.setPhoneNumber("555-000-555");
        reservation3.setNotes("Sweet table please");
        reservation3.setDateTime(LocalDateTime.of(2020, 12, 12, 20, 00));
        reservation3.setRestaurant(podFredra);

        ReservedTable reservedTable3 = new ReservedTable(
                schemaTable1.getTableNumber(),
                schemaTable1.getPlaces(),
                reservation3.getDateTime());
        reservationRepository.save(reservation3);
        reservedTable3.setReservation(reservation3);
        reservedTable3.setRestaurant(reservation3.getRestaurant());
        reservedTableService.add(reservedTable3);
        reservation3.setReservedTable(reservedTable3);
        reservationRepository.save(reservation3);

        Reservation reservation4 = new Reservation();
        reservation4.setName("Rezerwacja 4");
        reservation4.setNumberOfPersons(6);
        reservation4.setPhoneNumber("999-000-333");
        reservation4.setNotes("Important meeting!");
        reservation4.setDateTime(LocalDateTime.of(2020, 12, 12, 12, 00));
        reservation4.setRestaurant(cK);

        ReservedTable reservedTable4 = new ReservedTable(
                schemaTable10.getTableNumber(),
                schemaTable10.getPlaces(),
                reservation4.getDateTime());
        reservationRepository.save(reservation4);
        reservedTable4.setReservation(reservation4);
        reservedTable4.setRestaurant(reservation4.getRestaurant());
        reservedTableService.add(reservedTable4);
        reservation4.setReservedTable(reservedTable4);
        reservationRepository.save(reservation4);
    }

    @PostConstruct
    public void addMenu(){
        RestaurantMenu restaurantMenu = new RestaurantMenu();
        restaurantMenuRepository.save(restaurantMenu);

        Meal meal1 = new Meal("Duck with apples", 49.0);
        Meal meal2 = new Meal("Rabbit with cream sauce", 59.0);
        Meal meal3 = new Meal("Beefsteak", 89.0);
        meal1.setRestaurantMenu(restaurantMenu);
        meal2.setRestaurantMenu(restaurantMenu);
        meal3.setRestaurantMenu(restaurantMenu);
        mealRepository.save(meal1);
        mealRepository.save(meal2);
        mealRepository.save(meal3);

        restaurantMenu.setMeals(List.of(meal1, meal2, meal3));
        restaurantMenu.setRestaurant(podFredra);
        restaurantMenuRepository.save(restaurantMenu);

        podFredra.setRestaurantMenu(restaurantMenu);
        restaurantRepository.save(podFredra);
    }
}