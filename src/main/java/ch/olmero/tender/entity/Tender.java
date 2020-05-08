package ch.olmero.tender.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tenders")
public class Tender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private boolean closed;
    @ManyToOne
    @JoinColumn(name = "issuer_id")
    private Issuer issuer;
    @ManyToOne
    @JoinColumn(name = "construction_site_id")
    private ConstructionSite constructionSite;
}
