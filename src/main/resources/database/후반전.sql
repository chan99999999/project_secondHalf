-- 제약조건과 함께 테이블 삭제하기(명령어 실행 후 제약조건 다시 생성하고 더미데이터 넣어야 함) 
drop table 테이블명 cascade constraints;

-- 테이블 생성 (회원, 관리자, 채팅, 댓글, 좋아요, 모임게시글, 일상게시글, 카테고리, 메세지, 태그, 파일, 위치정보, 알림, 후기)

-- 회원 테이블
create table member (
  member_id      varchar2(20) not null,
  member_address varchar2(30) not null,
  member_passwd  varchar2(20) not null,
  name           varchar2(30) not null,
  nickname       varchar2(30) not null,
  birth_date     date not null,
  gender         char(1) not null,
  email          varchar2(30) not null,
  grade          varchar2(10) default 'GENERAL',
  hobby          varchar2(10),
  interest       varchar2(10),
  introduce      varchar2(50),
  picture        varchar2(100) default 'profile.jpg',
  admin_id       varchar2(10)
);

ALTER table member add regdate date null;
ALTER table member MODIFY regdate default sysdate;
ALTER table member add store_picture varchar2(100) null;


-- 관리자 테이블
create table admin (
  admin_id     varchar2(20) not null,
  admin_grade  number(10) default 2,
  admin_passwd varchar2(20) not null
);

-- 채팅 테이블
create table chatting (
  chat_id          number(20) not null,
  created_at       timestamp default current_timestamp,
  first_member_id  varchar2(50) not null,
  second_member_id varchar2(50) not null
);

-- 댓글 테이블
create table reply (
  reply_id     number(20) not null,
  d_article_id number(20),
  m_article_id number(20),
  content      varchar2(2000) not null,
  regdate      date default sysdate
);

-- 좋아요 테이블
create table heart (
  heart_id     number(20) not null,
  heart_count  number(20) not null,
  d_article_id number(20) not null,
  member_id    varchar2(20)
);

-- 모임 게시글 테이블
create table meet_article (
  m_article_id number(20) not null,
  title        varchar2(400) not null,
  content      varchar2(4000) not null,
  regdate      date default sysdate,
  time         date not null,
  enter        number(5) default 2,
  hitcount     number(20) default 0,
  category_id  number(20),
  member_id    varchar2(20),
  place_id     number(20)
);

-- 일상 게시글 테이블
create table daily_article (
  d_article_id number(20) not null,
  title        varchar2(400),
  content      varchar2(4000),
  regdate      date default sysdate,
  hitcount     number(20) default 0,
  category_id  number(20),
  member_id    varchar2(10)
);

-- 카테고리 테이블
create table category (
  category_id   number(20) not null,
  category_name varchar2(20) not null,
  admin_id      varchar2(20)
);

-- 메세지 테이블
create table message (
  message_id number(20) not null,
  content    varchar2(2000) not null,
  sent_at    timestamp default current_timestamp,
  chat_id    number(20),
  sender_id  varchar2(50)
);

-- 태그 테이블
create table tag (
  tag_id   number(20) not null,
  tag_name varchar2(20)
);

-- 파일 테이블
create table attach_file (
  file_id        number(20) not null,
  file_name      varchar2(500) default 'default.jpg',
  encrypted_name varchar2(1000),
  file_path      varchar2(400) default '/resources/static/img/',
  d_article_id   number(20)
);

-- 위치정보 테이블
create table placemap (
  place_id    number(20) not null,
  place_name  varchar2(100),
  map_address varchar2(200),
  latitude    number(20),
  longitude   number(20),
  map_id      number(20)
);

-- 알림 테이블
create table notification (
  notification_id number(20) not null,
  is_read         varchar2(10) default 'F',
  read_at         timestamp default current_timestamp,
  created_at      timestamp default current_timestamp,
  member_id       varchar2(20),
  message_id      number(20)
);

-- 후기 테이블
create table review (
  review_id number(20) not null,
  review    varchar2(2000),
  place_id  number(20),
  regdate   date default sysdate,
  member_id varchar2(20)
);

-- 태그게시글 테이블(매핑 테이블)
create table tag_article (
  m_article_id number(20),
  d_article_id number(20),
  tag_id       number(20)
);

-- pk 생성
alter table member add constraint pk_member primary key ( member_id );

alter table admin add constraint pk_admin primary key ( admin_id );

alter table chatting add constraint pk_chatting primary key ( chat_id );

alter table reply add constraint pk_reply primary key ( reply_id );

alter table heart add constraint pk_heart primary key ( heart_id );

alter table meet_article add constraint pk_meet_article primary key ( m_article_id );

alter table daily_article add constraint pk_daily_article primary key ( d_article_id );

alter table category add constraint pk_category primary key ( category_id );

alter table message add constraint pk_message primary key ( message_id );

alter table tag add constraint pk_tag primary key ( tag_id );

alter table attach_file add constraint pk_attach_file primary key ( file_id );

alter table placemap add constraint pk_placemap primary key ( place_id );

alter table notification add constraint pk_notification primary key ( notification_id );

alter table review add constraint pk_review primary key ( review_id );

-- fk 생성

-- 회원 fk
alter table member
  add constraint member_admin_id_fk foreign key ( admin_id )
    references admin ( admin_id )
      on delete cascade;

-- 채팅 fk
alter table chatting
  add constraint chatting_first_member_id_fk foreign key ( first_member_id )
    references member ( member_id );

alter table chatting
  add constraint chatting_second_member_id_fk foreign key ( second_member_id )
    references member ( member_id );

-- 좋아요 fk
alter table heart add (
  constraint heart_d_article_id_fk foreign key ( d_article_id )
    references daily_article ( d_article_id ),
  constraint heart_member_id_fk foreign key ( member_id )
    references member ( member_id )
);

-- 댓글 fk
alter table reply add (
  constraint reply_d_article_id_fk foreign key ( d_article_id )
    references daily_article ( d_article_id ),
  constraint reply_m_article_id_fk foreign key ( m_article_id )
    references meet_article ( m_article_id )
);

-- 일상 게시글 fk
alter table daily_article
  add constraint d_article_category_id_fk foreign key ( category_id )
    references category ( category_id );

alter table daily_article
  add constraint d_article_member_id_fk foreign key ( member_id )
    references member ( member_id );

-- 카테고리 fk
alter table category
  add constraint category_admin_id_fk foreign key ( admin_id )
    references admin ( admin_id );

-- 메세지 fk
alter table message
  add constraint message_chat_id_fk foreign key ( chat_id )
    references chatting ( chat_id );

alter table message
  add constraint message_sender_id_fk foreign key ( sender_id )
    references member ( member_id );

-- 파일 fk
alter table attach_file
  add constraint file_d_article_id_fk foreign key ( d_article_id )
    references daily_article ( d_article_id );

-- 모임 게시글 fk
alter table meet_article add (
  constraint m_article_category_id_fk foreign key ( category_id )
    references category ( category_id ),
  constraint m_article_member_id_fk foreign key ( member_id )
    references member ( member_id ),
  constraint m_article_place_id_fk foreign key ( place_id )
    references placemap ( place_id )
);

-- 알림 fk
alter table notification add (
  constraint notification_message_id_fk foreign key ( message_id )
    references message ( message_id )
      on delete cascade,
  constraint notification_member_id_fk foreign key ( member_id )
    references member ( member_id )
      on delete cascade
);

-- 후기 fk
alter table review add (
  constraint review_place_id_fk foreign key ( place_id )
    references placemap ( place_id )
      on delete cascade,
  constraint review_member_id_fk foreign key ( member_id )
    references member ( member_id )
      on delete cascade
);

-- 태그게시글 fk
alter table tag_article add (
  constraint tag_article_tag_id_fk foreign key ( tag_id )
    references tag ( tag_id )
      on delete cascade,
  constraint daily_article_d_article_id_fk foreign key ( d_article_id )
    references daily_article ( d_article_id )
      on delete cascade,
  constraint meet_article_m_article_id_fk foreign key ( m_article_id )
    references meet_article ( m_article_id )
      on delete cascade
);

-- ck 생성

-- 관리자 ck
alter table admin add (
  constraint admin_grade check ( admin_grade in ( '1',
                                                  '2' ) )
);

-- 회원 ck
alter table member add (
  constraint gender check ( gender in ( 'W',
                                        'M' ) ),
  constraint grade check ( grade in ( 'GENERAL',
                                      'BLACK' ) )
);

-- 알림 ck
alter table notification add (
  constraint is_read check ( is_read in ( 'F',
                                          'T' ) )
);

-- 시퀀스 생성 및 삭제
create sequence chat_id_seq;
drop sequence chat_id_seq;

create sequence message_id_seq;
drop sequence message_id_seq;

create sequence notification_id_seq;
drop sequence notification_id_seq;

create sequence review_id_seq;
drop sequence review_id_seq;

create sequence place_id_seq;
drop sequence place_id_seq;

create sequence m_article_id_seq;
drop sequence m_article_id_seq;

create sequence reply_id_seq;
drop sequence reply_id_seq;

create sequence tag_id_seq;
drop sequence tag_id_seq;

create sequence heart_id_seq;
drop sequence heart_id_seq;

create sequence d_article_id_seq;
drop sequence d_article_id_seq;

create sequence file_id_seq;
drop sequence file_id_seq;

-- 더미데이터 생성

-- 일상 게시글 더미
DESC daily_article;

insert into daily_article (
  d_article_id,
  title,
  content,
  regdate,
  hitcount
) values (
  d_article_id_seq.nextval,
  '일상공유제목',
  '일상공유내용',
  to_date('2024-05-17','YYYY-MM-DD'),
  1
);

insert into daily_article (
  d_article_id,
  title,
  content,
  regdate,
  hitcount
) values (
  d_article_id_seq.nextval,
  '일상공유제목2',
  '일상공유내용2',
  to_date('2024-05-18','YYYY-MM-DD'),
  10
);

insert into daily_article (
  d_article_id,
  title,
  content,
  regdate,
  hitcount
) values (
  d_article_id_seq.nextval,
  '일상공유제목3',
  '일상공유내용3',
  to_date('2024-05-18','YYYY-MM-DD'),
  10
);

insert into daily_article (
  d_article_id,
  title,
  content,
  regdate,
  hitcount
) values (
  d_article_id_seq.nextval,
  '일상공유제목4',
  '일상공유내용4',
  to_date('2024-05-18','YYYY-MM-DD'),
  10
);

insert into daily_article (
  d_article_id,
  title,
  content,
  regdate,
  hitcount
) values (
  d_article_id_seq.nextval,
  '일상공유제목5',
  '일상공유내용5',
  to_date('2024-05-18','YYYY-MM-DD'),
  10
);

select *
  from daily_article;

-- 카테고리 더미
DESC category;

insert into category (
  category_id,
  category_name
) values (
  1,
  '우리동네'
);

insert into category (
  category_id,
  category_name
) values (
  2,
  '일상공유'
);

insert into category (
  category_id,
  category_name
) values (
  3,
  '동네친구'
);

select *
  from category;

-- 메세지 더미
DESC message;

insert into message (
  message_id,
  content
) values (
  message_id_seq.nextval,
  '메세지 내용입니다'
);

select *
  from message;

-- 파일 더미
DESC attach_file;

insert into attach_file (
  file_id,
  encrypted_name
) values (
  file_id_seq.nextval,
  '암호화이름'
);

select *
  from attach_file;

-- 알림 더미
DESC notification;

insert into notification ( notification_id ) values ( notification_id_seq.nextval );

select *
  from notification;

-- 위치정보 더미
DESC placemap;

insert into placemap (
  place_id,
  place_name,
  map_address,
  latitude,
  longitude
) values (
  place_id_seq.nextval,
  '위치정보이름',
  '위치주소',
  10,
  10
);

select *
  from placemap;

-- 회원 더미
DESC member;

insert into member (
  member_id,
  member_address,
  member_passwd,
  nickname,
  birth_date,
  gender,
  email,
  name
) values (
  'monday',
  '서울 노원구 상계동',
  '1111',
  '월요일',
  to_date('2024-05-20','YYYY-MM-DD'),
  'W',
  'monday@gmail.com',
  '먼데이'
);

insert into member (
  member_id,
  member_address,
  member_passwd,
  nickname,
  birth_date,
  gender,
  email,
  name
) values (
  'tuesday',
  '서울 노원구 상계동',
  '1111',
  '화요일',
  to_date('2024-05-20','YYYY-MM-DD'),
  'W',
  'tuesday@gmail.com',
  '튜스데이'
);

insert into member (
  member_id,
  member_address,
  member_passwd,
  nickname,
  birth_date,
  gender,
  email,
  name
) values (
  'wednseday',
  '서울 노원구 상계동',
  '1111',
  '수요일',
  to_date('2024-05-20','YYYY-MM-DD'),
  'W',
  'wednseday@gmail.com',
  '웬스데이'
);

insert into member (
  member_id,
  member_address,
  member_passwd,
  nickname,
  birth_date,
  gender,
  email,
  name
) values (
  'thursday',
  '서울 노원구 상계동',
  '1111',
  '목요일',
  to_date('2024-05-20','YYYY-MM-DD'),
  'W',
  'thursday@gmail.com',
  '떨스데이'
);

insert into member (
  member_id,
  member_address,
  member_passwd,
  nickname,
  birth_date,
  gender,
  email,
  name
) values (
  'friday',
  '서울 노원구 상계동',
  '1111',
  '금요일',
  to_date('2024-05-20','YYYY-MM-DD'),
  'W',
  'friday@gmail.com',
  '프라이데이'
);

insert into member (
  member_id,
  member_address,
  member_passwd,
  nickname,
  birth_date,
  gender,
  email,
  name
) values (
  'saturday',
  '서울 노원구 상계동',
  '1111',
  '토요일',
  to_date('2024-05-20','YYYY-MM-DD'),
  'W',
  'saturday@gmail.com',
  '세터데이'
);

insert into member (
  member_id,
  member_address,
  member_passwd,
  nickname,
  birth_date,
  gender,
  email,
  name
) values (
  'sunday',
  '서울 노원구 상계동',
  '1111',
  '일요일',
  to_date('2024-05-20','YYYY-MM-DD'),
  'W',
  'sunday@gmail.com',
  '선데이'
);

insert into member (
  member_id,
  member_address,
  member_passwd,
  nickname,
  birth_date,
  gender,
  email,
  grade,
  hobby,
  interest,
  introduce,
  picture,
  name
) values (
  '덕소',
  '서울 노원구 상계동',
  '1111',
  '나나',
  to_date('1994-01-19','YYYY-MM-DD'),
  'W',
  'banana@gmail.com',
  'GENERAL',
  '꽂꽂이',
  '친구',
  '만나서반갑습니다',
  'profile.jpg',
  '김덕소'
);

select *
  from member;

-- 관리자 더미
DESC admin;

insert into admin values (
  'ADMIN',
  1,
  'ADMIN'
);

select *
  from admin;

-- 태그 더미
DESC tag;

insert into tag values (
  1,
  '기타'
);

select *
  from tag;

-- 댓글 더미
DESC reply;

insert into reply (
  reply_id,
  d_article_id,
  m_article_id,
  content,
  regdate
) values (
  reply_id_seq.nextval,
  1,
  null,
  '댓글내용',
  sysdate
);

insert into reply (
  reply_id,
  d_article_id,
  m_article_id,
  content,
  regdate
) values (
  reply_id_seq.nextval,
  2,
  null,
  '일상게시글 댓글내용',
  sysdate
);

insert into reply (
  reply_id,
  d_article_id,
  m_article_id,
  content,
  regdate
) values (
  reply_id_seq.nextval,
  3,
  null,
  '일상게시글 댓글내용',
  sysdate
);

select *
  from reply;

-- 모임 게시글 더미
DESC meet_article;

insert into meet_article (
  m_article_id,
  title,
  content,
  regdate,
  time,
  enter,
  hitcount
) values (
  m_article_id_seq.nextval,
  '모임명',
  '모임에 관한 내용',
  sysdate,
  to_date('2024-05-25','YYYY-MM-DD'),
  8,
  0
);

insert into meet_article (
  m_article_id,
  title,
  content,
  regdate,
  time,
  enter,
  hitcount
) values (
  m_article_id_seq.nextval,
  '꽂꽂이 클럽',
  '여럿이서 꽂꽂이를 하는 모임입니다',
  sysdate,
  to_date('2024-05-25','YYYY-MM-DD'),
  8,
  0
);

insert into meet_article (
  m_article_id,
  title,
  content,
  regdate,
  time,
  enter,
  hitcount
) values (
  m_article_id_seq.nextval,
  '책 읽는 모임',
  '책을 읽고 토론하는 모임입니다',
  sysdate,
  to_date('2024-05-25','YYYY-MM-DD'),
  8,
  0
);

SELECT *
FROM MEET_ARTICLE;

-- 좋아요 더미
DESC heart;

insert into heart (
  heart_id,
  heart_count,
  d_article_id,
  member_id
) values (
  heart_id_seq.nextval,
  100,
  1,
  'sunday'
);

-- 후기 더미
DESC review;

insert into review (
  review_id,
  review,
  place_id,
  member_id
) values (
  review_id_seq.nextval,
  '장소 후기입니다',
  1,
  'sunday'
);

select *
  from review;

-- 태그게시글 더미
DESC tag_article;

insert into tag_article (
  m_article_id,
  d_article_id,
  tag_id
) values (
  1,
  null,
  1
);

select *
  from tag_article;

-- 채팅 더미
DESC chatting;

insert into chatting (
  chat_id,
  first_member_id,
  second_member_id
) values (
  chat_id_seq.nextval,
  'sunday',
  'monday'
);

select *
  from chatting;

commit;



ALTER table member add regdate date null;





ALTER table member MODIFY regdate default sysdate;
ALTER table member add store_picture varchar2(100) null;



ALTER TABLE reply ADD writer VARCHAR2(20);


ALTER TABLE reply DROP COLUMN member_id;

ALTER TABLE reply ADD CONSTRAINT reply_writer_fk FOREIGN KEY (writer)
REFERENCES member(member_id);

---재현씨 코드 
drop table 테이블명 cascade constraints;

-- 태그 테이블
create table tag (
   tag_id   number(20) not null,
   tag_name varchar2(20),
   t_article_id NUMBER(20)
);
-- 태그게시글 테이블(매핑 테이블)
create table tag_article (
   t_article_id NUMBER(20),
   m_article_id NUMBER(20),
   d_article_id NUMBER(20)
);

alter table tag add constraint pk_tag primary key ( tag_id );
alter table tag_article add constraint pk_tag_article primary key ( t_article_id );

-- 태그게시글 fk
alter table tag_article add (
   constraint daily_article_d_article_id_fk foreign key ( d_article_id )
      references daily_article ( d_article_id )
         on delete cascade,
   constraint meet_article_m_article_id_fk foreign key ( m_article_id )
      references meet_article ( m_article_id )
         on delete cascade
);

-- 태그 FK
ALTER TABLE tag
ADD   CONSTRAINT tag_article_t_article_id_fk FOREIGN KEY (t_article_id)
   REFERENCES tag_article(t_article_id);

drop table tag cascade constraints;
drop table tag_article cascade constraints;

-- 태그게시글 더미
DESC tag_article;

insert into tag_article (
   t_article_id,
   m_article_id,
   d_article_id
) values (
   1,
   1,
   1
);
SELECT * FROM tag_article;

-- 태그 더미
DESC tag;

insert into tag values (
   4,
   '기타',
   1
);

select *
from tag;






2024-05-24
-- placemap 테이블 변경코드 알려드립니다.

-- 1. 컬럼명 변경
ALTER TABLE placemap RENAME COLUMN map_address TO address_name;
ALTER TABLE placemap RENAME COLUMN latitude TO y;
ALTER TABLE placemap RENAME COLUMN longitude TO x;

-- 2. 새로운 컬럼 추가
ALTER TABLE placemap ADD road_address_name varchar2(200);


-- 3-1 placemap 더미 있는 경우 삭제  더미 없는경우 3-2로

DELETE FROM placemap
WHERE place_id = 1;

-- 3-2 x,y 컬럼의 타입을 NUMBER->VARCHAR2(50)으로 바꿔줍니다.위도 경도의 경우 소숫점 이하값이 있어서..NUMBER로 하면 select문 사용시에 코드가 매우 복잡해져서 VARCHAR2로 변경하였습니다...
ALTER TABLE placemap
MODIFY (y VARCHAR2(50),
        x VARCHAR2(50));

-- 4 시퀀스 날리고 다시 생성 
drop sequence place_id_seq;
create sequence place_id_seq;


-- 5 새로운 더미넣기 ;
INSERT INTO placemap 
(place_id, place_name, address_name, x, y, map_id, road_address_name)
VALUES (place_id_seq.nextval, '장소이름', '지번주소', '경도', '위도', 1234, '도로명주소');

select * from placemap;


UPDATE placemap
SET
  place_name = '장소이름',
  address_name = '지번주소',
  y = 37.5665,
  x = 126.9780,
  map_id = 12345, 
  road_address_name = '도로명주소'
WHERE
  place_id = 1;



  select * from review;



--찬규님 ;

ALTER table member MODIFY picture varchar2(1000);
ALTER table member MODIFY store_picture varchar2(1000);
ALTER table member MODIFY store_picture default 'profile.png';

회원 테이블 사진, 저장사진 컬럼 변경되었습니다!