package de.bund.bva.isyfact.shop.persistence.entity;

import javax.persistence.*;

@Entity
@Table(name = "produkt")
public class ProduktEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final long id;

    @Column(name = "name")
    private String name;

    @Column(name = "beschreibung")
    private String beschreibung;

    public ProduktEntity(long id, String name, String beschreibung) {
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

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Override
    public String toString() {
        return String.format( "{ id: %d, name:'%s', beschreibung:'%s' }",
                this.id, this.name, this.beschreibung);
    }
}
