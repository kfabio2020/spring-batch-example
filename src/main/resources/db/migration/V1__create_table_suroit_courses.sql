CREATE TABLE suroit_courses
(
    id              SERIAL PRIMARY KEY,
    codestifligne VARCHAR(255)  NULL,
    antenne         VARCHAR(255) NOT NULL,
    idtm            VARCHAR(255) NOT NULL,
    idmiss          VARCHAR(255) NOT NULL,
    course          VARCHAR(255) NOT NULL,
    nommiss         VARCHAR(255) NOT NULL,
    sens            VARCHAR(255) NOT NULL,
    numseq          VARCHAR(255) NOT NULL,
    hrpass          VARCHAR(255) NOT NULL,
    idptar          VARCHAR(255) NOT NULL,
    zdep            VARCHAR(255) NOT NULL
);