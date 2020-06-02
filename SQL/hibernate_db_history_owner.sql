create table history_owner (
  car_id     integer not null,
  driver_id  integer not null,
  CONSTRAINT FK_CAR_ID FOREIGN KEY (car_id)
      REFERENCES cars (id),
  CONSTRAINT FK_DRIVER_ID FOREIGN KEY (driver_id)
      REFERENCES drivers (id)
);