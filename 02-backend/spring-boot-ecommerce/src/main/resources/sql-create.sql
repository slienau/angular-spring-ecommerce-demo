create table product (active bit, unit_price decimal(38,2), units_in_stock integer, category_id bigint not null, date_created datetime(6), id bigint not null auto_increment, last_updated datetime(6), description varchar(255), image_url varchar(255), name varchar(255), sku varchar(255), primary key (id)) engine=InnoDB;
create table product_category (id bigint not null auto_increment, category_name varchar(255), primary key (id)) engine=InnoDB;
alter table product add constraint FK5cypb0k23bovo3rn1a5jqs6j4 foreign key (category_id) references product_category (id);
