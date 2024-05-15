package model;

/**
 * Clasa care reprezinta un client
 */
public class Clients {

    private int id;
    private String name;
    private String email;
    private int age;

    /**
     * Constructor pentru initializarea unui client cu ID
     *
     * @param id    ID-ul clientului
     * @param name  Numele clientului
     * @param email Adresa de email a clientului
     * @param age   Varsta clientului
     */
    public Clients(int id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    /**
     * Constructor pentru initializarea unui client fara ID
     *
     * @param name  Numele clientului
     * @param email Adresa de email a clientului
     * @param age   Varsta clientului
     */
    public Clients(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    /**
     * Returneaza ID-ul clientului
     *
     * @return ID-ul clientului
     */
    public int getId() {
        return id;
    }

    /**
     * Seteaza ID-ul clientului
     *
     * @param id ID-ul clientului
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returneaza numele clientului.
     *
     * @return Numele clientului
     */
    public String getName() {
        return name;
    }

    /**
     * Returneaza adresa de email a clientului
     *
     * @return Adresa de email a clientului
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returneaza varsta clientului
     *
     * @return Varsta clientului
     */
    public int getAge() {
        return age;
    }

    /**
     * Returneaza numele ca string
     *
     * @return Numele clientului
     */
    @Override
    public String toString() {
        return name;
    }
}
