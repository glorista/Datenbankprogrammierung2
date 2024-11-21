package at.campus02.dbp2.repository;

public class Application {

    // Hilfsmethode, damit der Application Log Output besser erkannt werden kann
    // (gradle schreibt noch mehr in die Konsole).
    public static void log(String msg) {
        System.out.println("Application:  --> " + msg);
    }

    public static void main(String[] args) {

        //CustomerRepository repository =new InMemoryRepository();
        //
        //CustomerRepository repository = new JdbcRepository("jdbc:derby:database;create=true");

        //Für JpaRepository, falls es nicht geht bei File und Project Structure, bei SDK unter Download JDK eine ältere Version(1.5 oder 11)
        CustomerRepository repository = new JpaRepository();

        Customer customer = new Customer();
        customer.setEmail("customer4@email.com");
        customer.setFirstname("Barbara");
        customer.setLastname("Karlich");

        //1) Create (neuen Datensatz anlegen)
        repository.create(customer);
        log("Customer created"+customer);



        //2) Read (Datensatz auslesen)
        Customer fromRepository = repository.read(customer.getEmail()); //speichern die email in einen neue Customer
        log("Customer read"+fromRepository);


        //3) Update - Vorname ändern und speichern
        fromRepository.setFirstname("Harald");
        repository.update(fromRepository);
        Customer updatedCustomer = repository.read(fromRepository.getEmail());
        log("Customer updated"+updatedCustomer);

        //4) Delete - geänderter Kunden aus Memory löschen
        repository.delete(updatedCustomer);
        log("Customer deleted"+updatedCustomer);
        Customer deleted = repository.read(updatedCustomer.getEmail());
        log("Customer deleted: "+deleted);





    }
}
