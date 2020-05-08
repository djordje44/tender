DROP TABLE IF EXISTS offers;
DROP TABLE IF EXISTS tenders;
DROP TABLE IF EXISTS construction_sites;
DROP TABLE IF EXISTS issuers;
DROP TABLE IF EXISTS bidders;

-- create tables
CREATE TABLE construction_sites
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(100) NOT NULL,
    description  VARCHAR(250) DEFAULT NULL,
    location     VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE issuers
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE bidders
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE tenders
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    issuer_id            INT NOT NULL,
    construction_site_id INT NOT NULL,
    name                 VARCHAR(50) NOT NULL,
    description          VARCHAR(250) DEFAULT NULL,
    closed               TINYINT(1) DEFAULT FALSE,
    PRIMARY KEY (id),
    CONSTRAINT FK_tender_issuer_id FOREIGN KEY (issuer_id) REFERENCES issuers (id),
    CONSTRAINT FK_tender_construction_site_id FOREIGN KEY (construction_site_id) REFERENCES construction_sites (id)
);

CREATE TABLE offers
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    tender_id INT NOT NULL,
    bidder_id INT NOT NULL,
    price     DECIMAL(10, 2) NOT NULL,
    accepted  TINYINT(1) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_offers_tender_id FOREIGN KEY (tender_id) REFERENCES tenders (id),
    CONSTRAINT FK_offers_bidder_id FOREIGN KEY (bidder_id) REFERENCES bidders (id)
);


-- Adding demo data
INSERT INTO construction_sites (project_name, description, location)
VALUES ('Warehouse for company X', 'Building warehouse for company X', 'North Boulevard 123');

INSERT INTO issuers (first_name, last_name)
VALUES ('John', 'Doe');

INSERT INTO bidders (company_name) VALUES ('Hyper Building');
INSERT INTO bidders (company_name) VALUES ('Lego Constructions');