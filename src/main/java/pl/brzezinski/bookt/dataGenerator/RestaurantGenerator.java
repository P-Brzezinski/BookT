package pl.brzezinski.bookt.dataGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.restaurantMenu.Meal;
import pl.brzezinski.bookt.model.restaurantMenu.RestaurantMenu;
import pl.brzezinski.bookt.model.tables.ReservedTable;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.tables.SchemaTable;
import pl.brzezinski.bookt.model.enums.Genre;
import pl.brzezinski.bookt.repository.*;
import pl.brzezinski.bookt.service.ReservedTableService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantGenerator {

    private RestaurantRepository restaurantRepository;
    private ReservedTableRepository reservedTableRepository;
    private SchemaTableRepository schemaTableRepository;
    private ReservationRepository reservationRepository;
    private RestaurantMenuRepository restaurantMenuRepository;
    private MealRepository mealRepository;

    private ReservedTableService reservedTableService;

    @Autowired
    public RestaurantGenerator(RestaurantRepository restaurantRepository, ReservedTableRepository reservedTableRepository, SchemaTableRepository schemaTableRepository, ReservationRepository reservationRepository, RestaurantMenuRepository restaurantMenuRepository, MealRepository mealRepository, ReservedTableService reservedTableService) {
        this.restaurantRepository = restaurantRepository;
        this.reservedTableRepository = reservedTableRepository;
        this.schemaTableRepository = schemaTableRepository;
        this.reservationRepository = reservationRepository;
        this.restaurantMenuRepository = restaurantMenuRepository;
        this.mealRepository = mealRepository;
        this.reservedTableService = reservedTableService;
    }

    private Restaurant restaurant1;
    private Restaurant restaurant2;
    private Restaurant restaurant3;
    private Restaurant restaurant4;

    SchemaTable schemaTable1;
    SchemaTable schemaTable2;
    SchemaTable schemaTable3;
    SchemaTable schemaTable4;
    SchemaTable schemaTable5;
    SchemaTable schemaTable6;
    SchemaTable schemaTable7;

    @PostConstruct
    public void createRestaurantData() {
        restaurantRepository.save(restaurant1 = new Restaurant("Pod Fredra", "Rynek Ratusz 1", "Wroclaw", "54-900", Genre.POLISH, "http://www.podfredra.pl", "restauracja@podfreda.pl", LocalTime.of(10, 0), LocalTime.of(23, 0), "899-998-323"));
        restaurantRepository.save(restaurant2 = new Restaurant("Cesarsko Królewska", "Rynek 12", "Wroclaw", "32-999", Genre.POLISH, "http://www.ck.pl", "restauracja@ck.pl", LocalTime.of(12, 0), LocalTime.of(23, 0), "111-222-333"));
        restaurantRepository.save(restaurant3 = new Restaurant("La Scala", "Rynek 38", "Wroclaw", "50-102", Genre.ITALIAN, "http://www.lascala.pl", "restauracja@lascala.pl", LocalTime.of(12, 0), LocalTime.of(23, 0), "71-372-53-94"));
        restaurantRepository.save(restaurant4 = new Restaurant("Akropolis", "Rynek 16/17", "Wroclaw", "50-101", Genre.GREEK, "http://www.akropolis.wroc.pl", "restauracja@akropolis.pl", LocalTime.of(10, 0), LocalTime.of(23, 0), "71-343-14-13"));
    }

    @PostConstruct
    public void addSchemaTables() {
        schemaTable1 = new SchemaTable(1, 2);
        schemaTable2 = new SchemaTable(2, 2);
        schemaTable3 = new SchemaTable(3, 2);
        schemaTable4 = new SchemaTable(4, 2);
        schemaTable5 = new SchemaTable(5, 4);
        schemaTable6 = new SchemaTable(6, 4);
        schemaTable7 = new SchemaTable(7, 6);
        schemaTable1.setRestaurant(restaurant1);
        schemaTable2.setRestaurant(restaurant1);
        schemaTable3.setRestaurant(restaurant1);
        schemaTable4.setRestaurant(restaurant1);
        schemaTable5.setRestaurant(restaurant1);
        schemaTable6.setRestaurant(restaurant1);
        schemaTable7.setRestaurant(restaurant1);
        schemaTableRepository.save(schemaTable1);
        schemaTableRepository.save(schemaTable2);
        schemaTableRepository.save(schemaTable3);
        schemaTableRepository.save(schemaTable4);
        schemaTableRepository.save(schemaTable5);
        schemaTableRepository.save(schemaTable6);
        schemaTableRepository.save(schemaTable7);
    }

    @PostConstruct
    public void addReservations() {
        Reservation reservation1 = new Reservation();
        reservation1.setName("Rezerwacja 1");
        reservation1.setNumberOfPersons(2);
        reservation1.setPhoneNumber("999-000-333");
        reservation1.setNotes("Nice table please");
        reservation1.setDateTime(LocalDateTime.of(2020, 12, 12, 16, 00));
        reservation1.setRestaurant(restaurant1);

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
        reservation2.setRestaurant(restaurant1);

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
        reservation3.setRestaurant(restaurant1);

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

        restaurantMenu.setMeals(mealRepository.findAll());
        restaurantMenu.setRestaurant(restaurant1);
        restaurantMenuRepository.save(restaurantMenu);

        restaurant1.setRestaurantMenu(restaurantMenu);
        restaurantRepository.save(restaurant1);
    }
}