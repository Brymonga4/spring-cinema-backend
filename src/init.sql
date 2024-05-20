--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2
-- Dumped by pg_dump version 16.2

-- Started on 2024-05-20 20:49:25

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE IF EXISTS cinema;
--
-- TOC entry 4886 (class 1262 OID 24651)
-- Name: cinema; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE cinema WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Spain.1252';


ALTER DATABASE cinema OWNER TO postgres;

\connect cinema

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 226 (class 1259 OID 24714)
-- Name: bookings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bookings (
                                 booking_id integer NOT NULL,
                                 user_id integer NOT NULL
);


ALTER TABLE public.bookings OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 24713)
-- Name: bookings_booking_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.bookings_booking_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.bookings_booking_id_seq OWNER TO postgres;

--
-- TOC entry 4887 (class 0 OID 0)
-- Dependencies: 225
-- Name: bookings_booking_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.bookings_booking_id_seq OWNED BY public.bookings.booking_id;


--
-- TOC entry 224 (class 1259 OID 24705)
-- Name: movies; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.movies (
                               movie_id integer NOT NULL,
                               title character varying NOT NULL,
                               orig_title character varying NOT NULL,
                               release_date date NOT NULL,
                               genres character varying NOT NULL,
                               actors character varying NOT NULL,
                               directors character varying NOT NULL,
                               script character varying NOT NULL,
                               producers character varying NOT NULL,
                               synopsis character varying NOT NULL,
                               original_version boolean NOT NULL,
                               spanish_version boolean NOT NULL,
                               image character varying NOT NULL,
                               trailer character varying NOT NULL,
                               age_rating character varying NOT NULL,
                               duration integer NOT NULL
);


ALTER TABLE public.movies OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 24704)
-- Name: movies_movie_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.movies_movie_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.movies_movie_id_seq OWNER TO postgres;

--
-- TOC entry 4888 (class 0 OID 0)
-- Dependencies: 223
-- Name: movies_movie_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.movies_movie_id_seq OWNED BY public.movies.movie_id;


--
-- TOC entry 232 (class 1259 OID 24760)
-- Name: reviews; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reviews (
                                review_id integer NOT NULL,
                                movie_id integer NOT NULL,
                                user_id integer NOT NULL,
                                title character varying NOT NULL,
                                opinion character varying NOT NULL,
                                rating smallint NOT NULL,
                                review_date timestamp with time zone NOT NULL
);


ALTER TABLE public.reviews OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 24759)
-- Name: reviews_review_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.reviews_review_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.reviews_review_id_seq OWNER TO postgres;

--
-- TOC entry 4889 (class 0 OID 0)
-- Dependencies: 231
-- Name: reviews_review_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.reviews_review_id_seq OWNED BY public.reviews.review_id;


--
-- TOC entry 218 (class 1259 OID 24662)
-- Name: screen_rows; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.screen_rows (
                                    row_id integer NOT NULL,
                                    screen_id integer NOT NULL,
                                    row_number integer NOT NULL,
                                    number_of_seats integer NOT NULL
);


ALTER TABLE public.screen_rows OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 24661)
-- Name: screen_rows_row_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.screen_rows_row_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.screen_rows_row_id_seq OWNER TO postgres;

--
-- TOC entry 4890 (class 0 OID 0)
-- Dependencies: 217
-- Name: screen_rows_row_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.screen_rows_row_id_seq OWNED BY public.screen_rows.row_id;


--
-- TOC entry 230 (class 1259 OID 24743)
-- Name: screenings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.screenings (
                                   screening_id integer NOT NULL,
                                   movie_id integer NOT NULL,
                                   screen_id integer NOT NULL,
                                   start_time timestamp with time zone NOT NULL
);


ALTER TABLE public.screenings OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 24742)
-- Name: screenings_screening_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.screenings_screening_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.screenings_screening_id_seq OWNER TO postgres;

--
-- TOC entry 4891 (class 0 OID 0)
-- Dependencies: 229
-- Name: screenings_screening_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.screenings_screening_id_seq OWNED BY public.screenings.screening_id;


--
-- TOC entry 216 (class 1259 OID 24653)
-- Name: screens; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.screens (
                                id_screen integer NOT NULL,
                                supports character varying NOT NULL
);


ALTER TABLE public.screens OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 24652)
-- Name: screens_id_screen_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.screens_id_screen_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.screens_id_screen_seq OWNER TO postgres;

--
-- TOC entry 4892 (class 0 OID 0)
-- Dependencies: 215
-- Name: screens_id_screen_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.screens_id_screen_seq OWNED BY public.screens.id_screen;


--
-- TOC entry 220 (class 1259 OID 24674)
-- Name: seats; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.seats (
                              seat_id integer NOT NULL,
                              row_id integer NOT NULL,
                              seat_number integer NOT NULL,
                              seat_type character varying NOT NULL,
                              reserved boolean DEFAULT false NOT NULL
);


ALTER TABLE public.seats OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 24673)
-- Name: seats_seat_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seats_seat_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seats_seat_id_seq OWNER TO postgres;

--
-- TOC entry 4893 (class 0 OID 0)
-- Dependencies: 219
-- Name: seats_seat_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.seats_seat_id_seq OWNED BY public.seats.seat_id;


--
-- TOC entry 228 (class 1259 OID 24726)
-- Name: tickets; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tickets (
                                ticket_id integer NOT NULL,
                                booking_id integer NOT NULL,
                                seat_id integer NOT NULL,
                                screening_id integer NOT NULL,
                                available boolean DEFAULT true NOT NULL
);


ALTER TABLE public.tickets OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 24725)
-- Name: tickets_ticket_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tickets_ticket_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tickets_ticket_id_seq OWNER TO postgres;

--
-- TOC entry 4894 (class 0 OID 0)
-- Dependencies: 227
-- Name: tickets_ticket_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tickets_ticket_id_seq OWNED BY public.tickets.ticket_id;


--
-- TOC entry 222 (class 1259 OID 24689)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
                              user_id integer NOT NULL,
                              nickname character varying NOT NULL,
                              password character varying NOT NULL,
                              name character varying NOT NULL,
                              surname character varying NOT NULL,
                              email character varying NOT NULL,
                              phone character varying NOT NULL,
                              points bigint DEFAULT 0 NOT NULL,
                              premium boolean DEFAULT false NOT NULL,
                              admin boolean DEFAULT false NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 24688)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 4895 (class 0 OID 0)
-- Dependencies: 221
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;


--
-- TOC entry 4683 (class 2604 OID 24717)
-- Name: bookings booking_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bookings ALTER COLUMN booking_id SET DEFAULT nextval('public.bookings_booking_id_seq'::regclass);


--
-- TOC entry 4682 (class 2604 OID 24708)
-- Name: movies movie_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movies ALTER COLUMN movie_id SET DEFAULT nextval('public.movies_movie_id_seq'::regclass);


--
-- TOC entry 4687 (class 2604 OID 24763)
-- Name: reviews review_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews ALTER COLUMN review_id SET DEFAULT nextval('public.reviews_review_id_seq'::regclass);


--
-- TOC entry 4675 (class 2604 OID 24665)
-- Name: screen_rows row_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.screen_rows ALTER COLUMN row_id SET DEFAULT nextval('public.screen_rows_row_id_seq'::regclass);


--
-- TOC entry 4686 (class 2604 OID 24746)
-- Name: screenings screening_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.screenings ALTER COLUMN screening_id SET DEFAULT nextval('public.screenings_screening_id_seq'::regclass);


--
-- TOC entry 4674 (class 2604 OID 24656)
-- Name: screens id_screen; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.screens ALTER COLUMN id_screen SET DEFAULT nextval('public.screens_id_screen_seq'::regclass);


--
-- TOC entry 4676 (class 2604 OID 24677)
-- Name: seats seat_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seats ALTER COLUMN seat_id SET DEFAULT nextval('public.seats_seat_id_seq'::regclass);


--
-- TOC entry 4684 (class 2604 OID 24729)
-- Name: tickets ticket_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tickets ALTER COLUMN ticket_id SET DEFAULT nextval('public.tickets_ticket_id_seq'::regclass);


--
-- TOC entry 4678 (class 2604 OID 24692)
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- TOC entry 4874 (class 0 OID 24714)
-- Dependencies: 226
-- Data for Name: bookings; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4872 (class 0 OID 24705)
-- Dependencies: 224
-- Data for Name: movies; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4880 (class 0 OID 24760)
-- Dependencies: 232
-- Data for Name: reviews; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4866 (class 0 OID 24662)
-- Dependencies: 218
-- Data for Name: screen_rows; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4878 (class 0 OID 24743)
-- Dependencies: 230
-- Data for Name: screenings; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4864 (class 0 OID 24653)
-- Dependencies: 216
-- Data for Name: screens; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4868 (class 0 OID 24674)
-- Dependencies: 220
-- Data for Name: seats; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4876 (class 0 OID 24726)
-- Dependencies: 228
-- Data for Name: tickets; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4870 (class 0 OID 24689)
-- Dependencies: 222
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4896 (class 0 OID 0)
-- Dependencies: 225
-- Name: bookings_booking_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bookings_booking_id_seq', 1, false);


--
-- TOC entry 4897 (class 0 OID 0)
-- Dependencies: 223
-- Name: movies_movie_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.movies_movie_id_seq', 1, false);


--
-- TOC entry 4898 (class 0 OID 0)
-- Dependencies: 231
-- Name: reviews_review_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.reviews_review_id_seq', 1, false);


--
-- TOC entry 4899 (class 0 OID 0)
-- Dependencies: 217
-- Name: screen_rows_row_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.screen_rows_row_id_seq', 1, false);


--
-- TOC entry 4900 (class 0 OID 0)
-- Dependencies: 229
-- Name: screenings_screening_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.screenings_screening_id_seq', 1, false);


--
-- TOC entry 4901 (class 0 OID 0)
-- Dependencies: 215
-- Name: screens_id_screen_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.screens_id_screen_seq', 1, false);


--
-- TOC entry 4902 (class 0 OID 0)
-- Dependencies: 219
-- Name: seats_seat_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seats_seat_id_seq', 1, false);


--
-- TOC entry 4903 (class 0 OID 0)
-- Dependencies: 227
-- Name: tickets_ticket_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tickets_ticket_id_seq', 1, false);


--
-- TOC entry 4904 (class 0 OID 0)
-- Dependencies: 221
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 1, false);


--
-- TOC entry 4703 (class 2606 OID 24719)
-- Name: bookings bookings_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bookings
    ADD CONSTRAINT bookings_pkey PRIMARY KEY (booking_id);


--
-- TOC entry 4701 (class 2606 OID 24712)
-- Name: movies movies_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movies
    ADD CONSTRAINT movies_pkey PRIMARY KEY (movie_id);


--
-- TOC entry 4709 (class 2606 OID 24767)
-- Name: reviews reviews_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT reviews_pkey PRIMARY KEY (review_id);


--
-- TOC entry 4691 (class 2606 OID 24667)
-- Name: screen_rows screen_rows_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.screen_rows
    ADD CONSTRAINT screen_rows_pkey PRIMARY KEY (row_id);


--
-- TOC entry 4707 (class 2606 OID 24748)
-- Name: screenings screenings_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.screenings
    ADD CONSTRAINT screenings_pkey PRIMARY KEY (screening_id);


--
-- TOC entry 4689 (class 2606 OID 24660)
-- Name: screens screens_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.screens
    ADD CONSTRAINT screens_pkey PRIMARY KEY (id_screen);


--
-- TOC entry 4693 (class 2606 OID 24682)
-- Name: seats seats_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seats
    ADD CONSTRAINT seats_pkey PRIMARY KEY (seat_id);


--
-- TOC entry 4705 (class 2606 OID 24731)
-- Name: tickets tickets_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT tickets_pkey PRIMARY KEY (ticket_id);


--
-- TOC entry 4695 (class 2606 OID 24703)
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 4697 (class 2606 OID 24701)
-- Name: users users_nickname_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_nickname_key UNIQUE (nickname);


--
-- TOC entry 4699 (class 2606 OID 24699)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 4712 (class 2606 OID 24720)
-- Name: bookings bookings_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bookings
    ADD CONSTRAINT bookings_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON DELETE CASCADE;


--
-- TOC entry 4718 (class 2606 OID 24768)
-- Name: reviews reviews_movie_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT reviews_movie_id_fkey FOREIGN KEY (movie_id) REFERENCES public.movies(movie_id) ON DELETE CASCADE;


--
-- TOC entry 4719 (class 2606 OID 24773)
-- Name: reviews reviews_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT reviews_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON DELETE CASCADE;


--
-- TOC entry 4710 (class 2606 OID 24668)
-- Name: screen_rows screen_rows_screen_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.screen_rows
    ADD CONSTRAINT screen_rows_screen_id_fkey FOREIGN KEY (screen_id) REFERENCES public.screens(id_screen) ON DELETE CASCADE;


--
-- TOC entry 4716 (class 2606 OID 24749)
-- Name: screenings screenings_movie_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.screenings
    ADD CONSTRAINT screenings_movie_id_fkey FOREIGN KEY (movie_id) REFERENCES public.movies(movie_id) ON DELETE CASCADE;


--
-- TOC entry 4717 (class 2606 OID 24754)
-- Name: screenings screenings_screen_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.screenings
    ADD CONSTRAINT screenings_screen_id_fkey FOREIGN KEY (screen_id) REFERENCES public.screens(id_screen) ON DELETE CASCADE;


--
-- TOC entry 4711 (class 2606 OID 24683)
-- Name: seats seats_row_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seats
    ADD CONSTRAINT seats_row_id_fkey FOREIGN KEY (row_id) REFERENCES public.screen_rows(row_id) ON DELETE CASCADE;


--
-- TOC entry 4713 (class 2606 OID 24732)
-- Name: tickets tickets_booking_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT tickets_booking_id_fkey FOREIGN KEY (booking_id) REFERENCES public.bookings(booking_id) ON DELETE CASCADE;


--
-- TOC entry 4714 (class 2606 OID 24779)
-- Name: tickets tickets_screening_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT tickets_screening_id_fkey FOREIGN KEY (screening_id) REFERENCES public.screenings(screening_id) ON DELETE CASCADE;


--
-- TOC entry 4715 (class 2606 OID 24737)
-- Name: tickets tickets_seat_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT tickets_seat_id_fkey FOREIGN KEY (seat_id) REFERENCES public.seats(seat_id) ON DELETE CASCADE;


-- Completed on 2024-05-20 20:49:26

--
-- PostgreSQL database dump complete
--

