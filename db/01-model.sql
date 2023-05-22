BEGIN TRANSACTION;


-- 
-- Autenticación de APIS
-- Usados sólo para usar credenciales
-- 
DROP TABLE IF EXISTS api_auths CASCADE;
CREATE TABLE api_auths (
    pk bigserial NOT NULL,
    name varchar(255) NOT NULL,
    token varchar(255) NOT NULL,
    api_key varchar(255) NOT NULL,
    active boolean NOT NULL DEFAULT '0',
    created timestamptz NOT NULL DEFAULT NOW(),
    updated timestamptz NOT NULL DEFAULT NOW(),
    PRIMARY KEY (pk)
);
CREATE UNIQUE INDEX api_auths_token_uidx ON api_auths(token);



-- 
-- Credenciales temporales para uso de operaciones API
-- 
DROP TABLE IF EXISTS credentials CASCADE;
CREATE TABLE credentials (
    pk bigserial NOT NULL,
    auth_fk bigint NOT NULL,
    state varchar(255) NOT NULL,
    code varchar(255),
    jwt text,
    success_url text NOT NULL,
    failed_url varchar(255) NOT NULL,
    created timestamptz NOT NULL DEFAULT NOW(),
    updated timestamptz NOT NULL DEFAULT NOW(),
    FOREIGN KEY (auth_fk)  REFERENCES api_auths(pk) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (pk)
);
CREATE UNIQUE INDEX credentials_state_uidx ON credentials(state);



DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (
    pk bigserial NOT NULL,
    email varchar(255) NOT NULL,
    role smallint NOT NULL DEFAULT '0',
    created timestamptz NOT NULL DEFAULT NOW(),
    updated timestamptz NOT NULL DEFAULT NOW(),
    PRIMARY KEY (pk)
);
CREATE UNIQUE INDEX users_email_uidx ON users(LOWER(TRIM(both FROM email)));


DROP TABLE IF EXISTS subjects CASCADE;
CREATE TABLE subjects (
    pk bigserial NOT NULL,
    code varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    created timestamptz NOT NULL DEFAULT NOW(),
    updated timestamptz NOT NULL DEFAULT NOW(),
    PRIMARY KEY (pk)
);
CREATE UNIQUE INDEX subjects_token_uidx ON subjects(UPPER(TRIM(both FROM code)));


DROP TABLE IF EXISTS sections CASCADE;
CREATE TABLE sections (
    pk bigserial NOT NULL,
    subject_fk bigint NOT NULL,
    token varchar(255) NOT NULL,
    semester int NOT NULL CHECK (semester >= 1) CHECK (semester <=2),
    year int NOT NULL CHECK (year >= 2023) CHECK (year <= 2099),
    active boolean NOT NULL DEFAULT '0',
    created timestamptz NOT NULL DEFAULT NOW(),
    updated timestamptz NOT NULL DEFAULT NOW(),
    FOREIGN KEY (subject_fk) REFERENCES subjects(pk) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (pk)
);
CREATE UNIQUE INDEX sections_token_uidx ON sections(token);
CREATE UNIQUE INDEX sections_subjectfk_semester_year_uidx ON sections(subject_fk, semester, year);


DROP TABLE IF EXISTS courses CASCADE;
CREATE TABLE courses (
    pk bigserial NOT NULL,
    section_fk bigint NOT NULL,
    user_fk bigint NOT NULL,
    created timestamptz NOT NULL DEFAULT NOW(),
    updated timestamptz NOT NULL DEFAULT NOW(),
    FOREIGN KEY (section_fk) REFERENCES sections(pk) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (user_fk) REFERENCES users(pk) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (pk)
);
CREATE UNIQUE INDEX courses_sectionfk_userfk_uidx ON courses(section_fk, user_fk);



DROP TABLE IF EXISTS votes CASCADE;
CREATE TABLE votes (
    pk bigserial NOT NULL,
    section_fk bigint NOT NULL,
    attendance date NOT NULL DEFAULT NOW(),
    choice int NOT NULL CHECK (choice >=0) CHECK (choice <= 10),
    created timestamptz NOT NULL DEFAULT NOW(),
    updated timestamptz NOT NULL DEFAULT NOW(),
    FOREIGN KEY (section_fk) REFERENCES sections(pk) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (pk)
);
CREATE UNIQUE INDEX votes_sectionfk_attendance_uidx ON votes(section_fk, attendance);



DROP TABLE IF EXISTS voters CASCADE;
CREATE TABLE voters (
    pk bigserial NOT NULL,
    user_fk bigint NOT NULL,
    section_fk bigint NOT NULL,
    attendance date NOT NULL DEFAULT NOW(),
    created timestamptz NOT NULL DEFAULT NOW(),
    updated timestamptz NOT NULL DEFAULT NOW(),
    FOREIGN KEY (user_fk) REFERENCES users(pk) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (section_fk) REFERENCES sections(pk) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (pk)
);
CREATE UNIQUE INDEX voters_sectionfk_userfk_attendance_uidx ON voters(section_fk, user_fk,attendance);


COMMIT;
