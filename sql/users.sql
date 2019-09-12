/*
A generic users sql table that supports various thirdparty auth.
oauth_github {

}
*/


create table users (
       uid UUID PRIMARY KEY, -- an internal uuid
       name varchar(80), -- user's name
       email varchar(600), -- user's email address
       mobile_country_code varchar(10), -- mobile phone contry code
       mobile_phone varchar(80), -- mobile phone number
       token varchar(320), -- login token
       description text, -- user description
       last_update timestamp with time zone, -- last update
       create_date timestamp with time zone -- create date
);

create table oauth (
       provider varchar(80), -- provider name
       app_id varchar(80) PRIMARY KEY, -- app id with in provider
       user_handle varchar(200), -- user unique handle in app
       open_id varchar(80), -- wx opoen id
       union_id varchar(80), -- wx union id
       wx_developer_id varchar(80), -- wx developer id
       email varchar(600), -- email from oauth
       country varchar(200), -- contry code from oauth
       province varchar(200), -- province from oauth
       gender varchar(2), --gender from oauth
       city varchar(200), -- city from oauth
       mobile_contry_code varchar(10), -- mobile contry code
       mobile_phone varchar(80), -- mobile phone
       nickname varchar(600), -- user's nick name
       avartar text , -- user's avatar
       privilege text, -- user' weixin privilege
       uid UUID, -- user id.
       last_update timestamp with time zone, -- last update
       create_date timestamp with time zone -- create date
);
