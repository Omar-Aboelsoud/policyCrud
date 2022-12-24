
CREATE TABLE hlth_policy
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    creation_date     datetime              NOT NULL,
    modification_date datetime              NULL,
    policy_id         VARCHAR(255)          NOT NULL,
    start_date        date                  NOT NULL,
    CONSTRAINT pk_hlth_policy PRIMARY KEY (id)
);

CREATE TABLE hlth_insured_person
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    creation_date     datetime     NOT NULL,
    modification_date datetime     NULL,
    first_name        VARCHAR(255) NULL,
    last_name         VARCHAR(255) NULL,
    premium           DECIMAL      NULL,
    policy_id         BIGINT       NULL,
    CONSTRAINT pk_hlth_insured_person PRIMARY KEY (id)
);

ALTER TABLE hlth_insured_person
    ADD CONSTRAINT FK_HLTH_INSURED_PERSON_ON_POLICY FOREIGN KEY (policy_id) REFERENCES hlth_policy (id);

