# HospitalWS
Repositório para entrega do trabalho bimestral da matéria da faculdade Programação para internet

Banco de dados:
CREATE TABLE tb_medico (
    id SERIAL PRIMARY KEY NOT NULL,
    nome VARCHAR,
    gmail VARCHAR,
    telefone VARCHAR(13),
    crm VARCHAR(12),
    especialidade CHAR(1),
    endereco_id INTEGER,
    cpf VARCHAR
);

CREATE TABLE tb_paciente (
    id SERIAL PRIMARY KEY NOT NULL,
    nome VARCHAR not null,
    gmail VARCHAR not null,
    telefone INTEGER not null,
    cpf VARCHAR not null,
    endereco_id INTEGER
);

create table tb_endereco(
    id SERIAL primary key not null,
    logradouro VARCHAR not null,
    numero INTEGER ,
    complemento VARCHAR,
    bairro VARCHAR not null,
    cidade VARCHAR not null,
    uf VARCHAR(2) not null,
    cep VARCHAR(9) not null
);
ALTER TABLE tb_medico
    ADD CONSTRAINT fk_endereco FOREIGN KEY (endereco_id) REFERENCES tb_medico(id);
ALTER TABLE tb_paciente
    ADD CONSTRAINT fk_endereco FOREIGN KEY (endereco_id) REFERENCES tb_paciente(id);
