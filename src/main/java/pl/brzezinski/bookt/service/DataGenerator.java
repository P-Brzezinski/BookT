package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Reservation;
import pl.brzezinski.bookt.model.ReservedTable;
import pl.brzezinski.bookt.model.Restaurant;
import pl.brzezinski.bookt.model.SchemaTable;
import pl.brzezinski.bookt.model.enums.Genre;
import pl.brzezinski.bookt.repository.ReservationRepository;
import pl.brzezinski.bookt.repository.RestaurantRepository;
import pl.brzezinski.bookt.repository.SchemaTableRepository;
import pl.brzezinski.bookt.repository.ReservedTableRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class DataGenerator {

    private RestaurantRepository restaurantDAO;
    private ReservedTableRepository tableDAO;
    private SchemaTableRepository schemaTableRepository;
    private ReservationRepository reservationRepository;
    private ReservedTableService reservedTableService;

    @Autowired
    public DataGenerator(RestaurantRepository restaurantDAO, ReservedTableRepository tableDAO, SchemaTableRepository schemaTableRepository, ReservationRepository reservationRepository, ReservedTableService reservedTableService) {
        this.restaurantDAO = restaurantDAO;
        this.tableDAO = tableDAO;
        this.schemaTableRepository = schemaTableRepository;
        this.reservationRepository = reservationRepository;
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
        restaurantDAO.save(restaurant1 = new Restaurant("Pod Fredra", "Rynek Ratusz 1", "Wroclaw", "54-900", Genre.POLISH, "http://www.podfredra.pl", "restauracja@podfreda.pl", LocalTime.of(10, 0), LocalTime.of(23, 0), "899-998-323"));
        restaurantDAO.save(restaurant2 = new Restaurant("Cesarsko Królewska", "Rynek 12", "Wroclaw", "32-999", Genre.POLISH, "http://www.ck.pl", "restauracja@ck.pl", LocalTime.of(12, 0), LocalTime.of(23, 0), "111-222-333"));
        restaurantDAO.save(restaurant3 = new Restaurant("La Scala", "Rynek 38", "Wroclaw", "50-102", Genre.ITALIAN, "http://www.lascala.pl", "restauracja@lascala.pl", LocalTime.of(12, 0), LocalTime.of(23, 0), "71-372-53-94"));
        restaurantDAO.save(restaurant4 = new Restaurant("Akropolis", "Rynek 16/17", "Wroclaw", "50-101", Genre.GREEK, "http://www.akropolis.wroc.pl", "restauracja@akropolis.pl", LocalTime.of(10, 0), LocalTime.of(23, 0), "71-343-14-13"));
    }

    @PostConstruct
    public void addSchemaTables() {
        schemaTable1 = new SchemaTable(1, 2);
        schemaTable2 = new SchemaTable(2, 2);
        schemaTable3 = new SchemaTable(3, 2);
        schemaTable4 = new SchemaTable(4, 2);
        schemaTable5 = new SchemaTable(5, 4);
        schemaTable6 = new SchemaTable(6, 6);
        schemaTable7 = new SchemaTable(1, 2);
//        SchemaTable schemaTable8 = new SchemaTable(2, 2 );
        schemaTable1.setRestaurant(restaurant1);
//        schemaTable2.setRestaurant(restaurant1);
//        schemaTable3.setRestaurant(restaurant1);
//        schemaTable4.setRestaurant(restaurant1);
        schemaTable5.setRestaurant(restaurant1);
        schemaTable6.setRestaurant(restaurant1);
        schemaTable7.setRestaurant(restaurant2);
//        schemaTable8.setRestaurant(restaurant2);
        schemaTableRepository.save(schemaTable1);
        schemaTableRepository.save(schemaTable2);
//        schemaTableRepository.save(schemaTable3);
//        schemaTableRepository.save(schemaTable4);
        schemaTableRepository.save(schemaTable5);
        schemaTableRepository.save(schemaTable6);
        schemaTableRepository.save(schemaTable7);
//        schemaTableRepository.save(schemaTable8);
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
}