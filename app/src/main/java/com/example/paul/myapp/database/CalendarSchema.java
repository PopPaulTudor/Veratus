package com.example.paul.myapp.database;


class CalendarSchema {

    static final class DailyTable {
        static final String NAME = "daily";

        static final class Cols {
            static final String UUID = "uuid";
            static final String EVENT_ID = "event_id";
            static final String START_EVENT = "start";
            static final String DURATION = "duration";
            static final String COST = "cost";
            static final String DESCRIPTION = "description";
            static final String USER_ID = "user_id";
            static final String REPEATABLE = "repeatable";
            static final String UNTIL = "until";
        }

    }


    static final class EventTable {

        static final String NAME = "event";

        static final class Cols {

            static final String ID = "uuid_event";
            static final String TYPE = "type";
            static final String NUME = "nume";
        }

    }


    static final class UserTable {

        static final String NAME = "user";

        static final class Cols {

            static final String USERNAME = "username";
            static final String PASSWORD = "password";
            static final String NAME = "nume";
            static final String PRENAME = "prename";
            static final String AGE = "age";
            static final String YEAR = "year";
            static final String ID = "id";
            static final String POINT = "point";
        }

    }


}
