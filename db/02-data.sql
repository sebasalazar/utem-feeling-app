BEGIN TRANSACTION;

--
-- Credenciales
--
TRUNCATE TABLE api_auths RESTART IDENTITY CASCADE;
INSERT INTO api_auths (name, token, api_key, active) VALUES ('Seba','sebastian.cl','aaa-bbb-ccc-ddd','1');
INSERT INTO api_auths (name, token, api_key, active) VALUES ('Grupo A','grupoa', encode(SHA224('3wF,JEH.QL'), 'hex'),'1');
INSERT INTO api_auths (name, token, api_key, active) VALUES ('Grupo B','grupob', encode(SHA224('ZZ9,upm.MU'), 'hex'),'1');
INSERT INTO api_auths (name, token, api_key, active) VALUES ('Grupo C','grupoc', encode(SHA224('CWX,WbU.dh'), 'hex'),'1');
INSERT INTO api_auths (name, token, api_key, active) VALUES ('Grupo D','grupod', encode(SHA224('bVv,Xcr.Cj'), 'hex'),'1');
INSERT INTO api_auths (name, token, api_key, active) VALUES ('Grupo E','grupoe', encode(SHA224('yRt,FKF.yC'), 'hex'),'1');
INSERT INTO api_auths (name, token, api_key, active) VALUES ('Grupo F','grupof', encode(SHA224('LNR,yEG.9p'), 'hex'),'1');
INSERT INTO api_auths (name, token, api_key, active) VALUES ('Grupo G','grupog', encode(SHA224('5L2,54H.du'), 'hex'),'1');
INSERT INTO api_auths (name, token, api_key, active) VALUES ('Grupo H','grupoh', encode(SHA224('Zcb,WsX.cm'), 'hex'),'1');
INSERT INTO api_auths (name, token, api_key, active) VALUES ('Grupo I','grupoi', encode(SHA224('fTM,pSw.6A'), 'hex'),'1');
INSERT INTO api_auths (name, token, api_key, active) VALUES ('Grupo J','grupoj', encode(SHA224('dHE,6K2.vj'), 'hex'),'1');
INSERT INTO api_auths (name, token, api_key, active) VALUES ('Grupo K','grupok', encode(SHA224('fmg,Tc6.gf'), 'hex'),'1');
INSERT INTO api_auths (name, token, api_key, active) VALUES ('Grupo L','grupol', encode(SHA224('qky,39m.bn'), 'hex'),'1');

TRUNCATE TABLE users RESTART IDENTITY CASCADE;
INSERT INTO users (email, role) VALUES ('ssalazar@utem.cl','1');
INSERT INTO users (email, role) VALUES ('gabriel.abarcam@utem.cl','1');
INSERT INTO users (email, role) VALUES ('jean.agurton@utem.cl','1');
INSERT INTO users (email, role) VALUES ('rarayaa@utem.cl','1');
INSERT INTO users (email, role) VALUES ('ibattistoni@utem.cl','1');
INSERT INTO users (email, role) VALUES ('dcastilloa@utem.cl','1');
INSERT INTO users (email, role) VALUES ('lcastrov@utem.cl','1');
INSERT INTO users (email, role) VALUES ('cdiazr@utem.cl','1');
INSERT INTO users (email, role) VALUES ('mestrada@utem.cl','1');
INSERT INTO users (email, role) VALUES ('ngaray@utem.cl','1');
INSERT INTO users (email, role) VALUES ('mhurtado@utem.cl','1');
INSERT INTO users (email, role) VALUES ('gmarcolini@utem.cl','1');
INSERT INTO users (email, role) VALUES ('fmunozc@utem.cl','1');
INSERT INTO users (email, role) VALUES ('norellana@utem.cl','1');
INSERT INTO users (email, role) VALUES ('vpalacios@utem.cl','1');
INSERT INTO users (email, role) VALUES ('virla.parrar@utem.cl','1');
INSERT INTO users (email, role) VALUES ('bponce@utem.cl','1');
INSERT INTO users (email, role) VALUES ('dquezadaj@utem.cl','1');
INSERT INTO users (email, role) VALUES ('francisco.robledov@utem.cl','1');
INSERT INTO users (email, role) VALUES ('matias.rubilarm@utem.cl','1');
INSERT INTO users (email, role) VALUES ('pruiz@utem.cl','1');
INSERT INTO users (email, role) VALUES ('nruizr@utem.cl','1');
INSERT INTO users (email, role) VALUES ('claudio.silvaa@utem.cl','1');
INSERT INTO users (email, role) VALUES ('msotod@utem.cl','1');
INSERT INTO users (email, role) VALUES ('osoto@utem.cl','1');
INSERT INTO users (email, role) VALUES ('csotov@utem.cl','1');
INSERT INTO users (email, role) VALUES ('isubaique@utem.cl','1');
INSERT INTO users (email, role) VALUES ('stapia@utem.cl','1');
INSERT INTO users (email, role) VALUES ('vtoledo@utem.cl','1');
INSERT INTO users (email, role) VALUES ('jaime.ugazr@utem.cl','1');
INSERT INTO users (email, role) VALUES ('juribe@utem.cl','1');
INSERT INTO users (email, role) VALUES ('pvasquez@utem.cl','1');
INSERT INTO users (email, role) VALUES ('rvasquez@utem.cl','1');
INSERT INTO users (email, role) VALUES ('vvelasquez@utem.cl','1');
INSERT INTO users (email, role) VALUES ('gzamora@utem.cl','1');
INSERT INTO users (email, role) VALUES ('mzapata@utem.cl','1');
INSERT INTO users (email, role) VALUES ('pzunigan@utem.cl','1');

TRUNCATE TABLE subjects RESTART IDENTITY CASCADE;
INSERT INTO subjects (code, name) VALUES ('INFB8090','COMPUTACIÓN PARALELA Y DISTRIBUIDA');
INSERT INTO sections (token, subject_fk, semester, year, active) VALUES ('2a94bea0-2b58-49f0-a4cd-5f41d454406d','1','1','2023','1');
INSERT INTO courses (section_fk, user_fk) SELECT 1 AS section_fk, pk AS user_fk FROM users;

COMMIT;
