package de.bund.bva.isyfact.shop.core.daten;

public class ProduktBo {

    private long id;

    private String name;

    private String beschreibung;

    /**
     * The default constructor is required by the jackson json converter.
     */
    public ProduktBo() {

    }

    /**
     * the all-arguments-constructor is used in the ProduktBoMapper class.
     * @param id
     * @param name
     * @param beschreibung
     */
    public ProduktBo(long id, String name, String beschreibung) {
        this.id = id;
        this.name = name;
        this.beschreibung = beschreibung;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setDescription(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String toString() {
        return String.format( "{ id: %d, name:'%s', beschreibung:'%s' }", this.id, this.name, this.beschreibung);
    }

}
