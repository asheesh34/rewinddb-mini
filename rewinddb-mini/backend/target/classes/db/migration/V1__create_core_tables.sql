create table users (
    id uuid not null,
    email varchar(255) not null,
    password_hash varchar(255) not null,
    display_name varchar(255) not null,
    role varchar(255) not null,
    enabled boolean not null,
    created_at timestamp(6) with time zone not null,
    updated_at timestamp(6) with time zone not null,
    constraint users_pkey primary key (id),
    constraint uk_users_email unique (email)
);

create table database_connections (
    id uuid not null,
    owner_id uuid not null,
    name varchar(255) not null,
    host varchar(255) not null,
    port integer not null,
    database_name varchar(255) not null,
    username varchar(255) not null,
    encrypted_password varchar(255) not null,
    status varchar(255) not null,
    created_at timestamp(6) with time zone not null,
    updated_at timestamp(6) with time zone not null,
    constraint database_connections_pkey primary key (id),
    constraint fk_database_connections_owner foreign key (owner_id) references users (id)
);

create table trigger_definitions (
    id uuid not null,
    connection_id uuid not null,
    schema_name varchar(255) not null,
    table_name varchar(255) not null,
    trigger_name varchar(255) not null,
    status varchar(255) not null,
    created_at timestamp(6) with time zone not null,
    updated_at timestamp(6) with time zone not null,
    constraint trigger_definitions_pkey primary key (id),
    constraint fk_trigger_definitions_connection foreign key (connection_id) references database_connections (id)
);

create table change_events (
    id uuid not null,
    connection_id uuid not null,
    trigger_id uuid,
    schema_name varchar(255) not null,
    table_name varchar(255) not null,
    primary_key_value varchar(255) not null,
    operation_type varchar(255) not null,
    before_data jsonb,
    after_data jsonb,
    status varchar(255) not null,
    captured_at timestamp(6) with time zone not null,
    constraint change_events_pkey primary key (id),
    constraint fk_change_events_connection foreign key (connection_id) references database_connections (id),
    constraint fk_change_events_trigger foreign key (trigger_id) references trigger_definitions (id)
);

create table history_versions (
    id uuid not null,
    connection_id uuid not null,
    change_event_id uuid not null,
    version_number bigint not null,
    schema_name varchar(255) not null,
    table_name varchar(255) not null,
    row_snapshot jsonb,
    created_at timestamp(6) with time zone not null,
    constraint history_versions_pkey primary key (id),
    constraint fk_history_versions_connection foreign key (connection_id) references database_connections (id),
    constraint fk_history_versions_change_event foreign key (change_event_id) references change_events (id)
);

create table rollback_operations (
    id uuid not null,
    connection_id uuid not null,
    requested_by uuid not null,
    target_version bigint not null,
    status varchar(255) not null,
    failure_reason text,
    requested_at timestamp(6) with time zone not null,
    completed_at timestamp(6) with time zone,
    constraint rollback_operations_pkey primary key (id),
    constraint fk_rollback_operations_connection foreign key (connection_id) references database_connections (id),
    constraint fk_rollback_operations_requested_by foreign key (requested_by) references users (id)
);

create table audit_logs (
    id uuid not null,
    user_id uuid,
    action varchar(255) not null,
    resource_type varchar(255) not null,
    resource_id varchar(255),
    metadata jsonb,
    ip_address varchar(255),
    created_at timestamp(6) with time zone not null,
    constraint audit_logs_pkey primary key (id),
    constraint fk_audit_logs_user foreign key (user_id) references users (id)
);
