package net.thevpc.tson;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class TsonRandom {
    public static TsonElement randomElement(Predicate<TsonElementType> t) {
        TsonElementType tt = null;
        for (int i = 0; i < 1000; i++) {
            TsonElementType t0 = TsonElementType.values()[randomInt(TsonElementType.values().length)];
            if (t.test(t0)) {
                tt = t0;
                break;
            }
        }
        if (tt == null) {
            throw new IllegalArgumentException("Unable to create TsonElement");
        }
        return randomElement(tt);
    }

    public static String randomString() {
        String[] names = {"hello", "world"};
        return names[randomInt(names.length)];
    }

    public static String randomId() {
        String[] names = {"hello", "world"};
        return names[randomInt(names.length)];
    }

    public static String randomRegex() {
        String[] names = {"[a-z]", "[0-9]"};
        return names[randomInt(names.length)];
    }

    public static int randomInt(int a) {
        return (int) (Math.random() * a);
    }

    public static TsonElement randomElement() {
        int i = randomInt(TsonElementType.values().length);
        return randomElement(TsonElementType.values()[i]);
    }

    public static TsonElement randomElement(TsonElementType type) {
        switch (type) {
            case LONG:
                return Tson.longElem(Double.doubleToLongBits(Math.random()));
            case INT:
                return Tson.intElem((int) Double.doubleToLongBits(Math.random()));
            case SHORT:
                return Tson.shortElem((short) Double.doubleToLongBits(Math.random()));
            case BYTE:
                return Tson.byteElem((byte) Double.doubleToLongBits(Math.random()));
            case FLOAT:
                return Tson.floatElem((float) Math.random());
            case DOUBLE:
                return Tson.doubleElem(Math.random());
            case DATETIME:
                return Tson.datetime(Instant.now());
            case DATE:
                return Tson.date(LocalDate.now());
            case TIME:
                return Tson.time(LocalTime.now());
            case REGEX:
                return Tson.regex(Pattern.compile(randomRegex()));
            case NAME:
                return Tson.name(randomId());
            case STRING:
                return Tson.string(randomString());
            case CHAR:
                return Tson.charElem(randomString().charAt(0));
            case BOOLEAN:
                return Tson.booleanElem(randomBoolean());
            case NULL:
                return Tson.nullElem();
            case FUNCTION: {
                TsonFunctionBuilder f = Tson.function();
                f.setName(randomId());
                int max = randomInt(3);
                for (int i = 0; i < max; i++) {
                    f.add(randomElement());
                }
                return f.build();
            }
            case PAIR: {
                return Tson.pair(
                        randomElement(t -> t != TsonElementType.PAIR),
                        randomElement(t -> t != TsonElementType.PAIR)
                );
            }
            case ARRAY: {
                TsonArrayBuilder f = Tson.array();
                if (randomBoolean()) {
                    f.getHeader().setName(randomId());
                }
                if (randomBoolean()) {
                    int max = randomInt(3);
                    for (int i = 0; i < max; i++) {
                        f.getHeader().add(randomElement());
                    }
                }
                int max = randomInt(3);
                for (int i = 0; i < max; i++) {
                    f.add(randomElement());
                }
                return f.build();
            }
            case OBJECT: {
                TsonObjectBuilder f = Tson.obj();
                if (randomBoolean()) {
                    f.getHeader().setName(randomId());
                }
                if (randomBoolean()) {
                    int max = randomInt(3);
                    for (int i = 0; i < max; i++) {
                        f.getHeader().add(randomElement());
                    }
                }
                int max = randomInt(3);
                for (int i = 0; i < max; i++) {
                    f.add(randomElement());
                }
                return f.build();
            }
            case UPLET: {
                TsonUpletBuilder f = Tson.uplet();
                int max = randomInt(3);
                max = randomInt(3);
                for (int i = 0; i < max; i++) {
                    f.add(randomElement());
                }
                return f.build();
            }
            case BIG_INT: {
                return Tson.bigInt(randomBigInteger());
            }
            case BIG_DECIMAL: {
                return Tson.bigDecimal(randomBigDecimal());
            }
            case BIG_COMPLEX:{
                return Tson.bigComplex(randomBigDecimal(),randomBigDecimal());
            }
            case FLOAT_COMPLEX:{
                return Tson.floatComplex(randomFloat(),randomFloat());
            }
            case DOUBLE_COMPLEX:{
                return Tson.doubleComplex(randomDouble(),randomDouble());
            }
            case CHAR_STREAM:{
                return Tson.stopStream(randomString(),"<STOP>");
            }
            case BINARY_STREAM:{
                return Tson.bstream(randomString().getBytes());
            }
            case ALIAS:{
                return Tson.alias("customAlias");
            }
        }
        throw new IllegalArgumentException("Unsupported Random for " + type);
    }

    public static float randomFloat() {
        return (float) Math.random();
    }

    public static double randomDouble() {
        return Math.random();
    }

    private static BigDecimal randomBigDecimal() {
        return new BigDecimal(randomBigInteger(),randomInt(10));
    }

    private static BigInteger randomBigInteger() {
        return new BigInteger("100000000000000000");
    }

    private static BigInteger randomBigInteger(BigInteger upperLimit) {

        int nlen = upperLimit.bitLength();
        BigInteger nm1 = upperLimit.subtract(BigInteger.ONE);
        BigInteger randomNumber, temp;
        do {
            temp = new BigInteger(nlen + 100, new Random());
            randomNumber = temp.mod(upperLimit);
        } while (upperLimit.subtract(randomNumber).add(nm1).bitLength() >= nlen + 100);
        return randomNumber;
    }

    private static boolean randomBoolean() {
        return Math.random() > 0.5;
    }

    public static String randomMapListJson(int c2) {
        return new Gson().toJson(randomMapList(c2));
    }

    public static List randomMapList(int complexity) {
        Map<String, Object> val = new HashMap<>();
        for (int i = 0; i < complexity; i++) {
            val.put("Hello" + i, i);
        }
        List all = new ArrayList();
        for (int i = 0; i < complexity; i++) {
            all.add(val);
        }
        return all;
    }

    public static Person randomPerson() {
        Person p = new Person();
        p.setStringValue("some string");
        p.firstName = "Ahmed";
        p.lastName = "Alchimist";
        p.birthDateUtilDate = new Date();
        p.birthDateSqlDate = new java.sql.Date(2010, 10, 5);
        p.birthDateLocalDate = LocalDate.now();
        p.birthSqlTime = new Time(10, 52, 32);
        p.birthLocalTime = LocalTime.now();
        p.someInstant = Instant.now();
        p.age = randomInt(50);
        return p;
    }

    public static Person[] randomPersonArray(int size) {
        List<Person> all = new ArrayList<>();
        int max = size;
        for (int i = 0; i < max; i++) {
            all.add(randomPerson());
        }
        return all.toArray(new Person[0]);
    }

    public static class Any {
        private String stringValue;

        public String getStringValue() {
            return stringValue;
        }

        public void setStringValue(String stringValue) {
            this.stringValue = stringValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Any any = (Any) o;
            return Objects.equals(stringValue, any.stringValue);
        }

        @Override
        public int hashCode() {
            return Objects.hash(stringValue);
        }
    }

    public static class Person extends Any {
        private String firstName;
        private String lastName;
        private Instant someInstant;
        private Date birthDateUtilDate;
        private java.sql.Date birthDateSqlDate;
        private LocalDate birthDateLocalDate;
        private java.sql.Time birthSqlTime;
        private LocalTime birthLocalTime;
        private int age;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Instant getSomeInstant() {
            return someInstant;
        }

        public void setSomeInstant(Instant someInstant) {
            this.someInstant = someInstant;
        }

        public Date getBirthDateUtilDate() {
            return birthDateUtilDate;
        }

        public void setBirthDateUtilDate(Date birthDateUtilDate) {
            this.birthDateUtilDate = birthDateUtilDate;
        }

        public java.sql.Date getBirthDateSqlDate() {
            return birthDateSqlDate;
        }

        public void setBirthDateSqlDate(java.sql.Date birthDateSqlDate) {
            this.birthDateSqlDate = birthDateSqlDate;
        }

        public LocalDate getBirthDateLocalDate() {
            return birthDateLocalDate;
        }

        public void setBirthDateLocalDate(LocalDate birthDateLocalDate) {
            this.birthDateLocalDate = birthDateLocalDate;
        }

        public Time getBirthSqlTime() {
            return birthSqlTime;
        }

        public void setBirthSqlTime(Time birthSqlTime) {
            this.birthSqlTime = birthSqlTime;
        }

        public LocalTime getBirthLocalTime() {
            return birthLocalTime;
        }

        public void setBirthLocalTime(LocalTime birthLocalTime) {
            this.birthLocalTime = birthLocalTime;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void diff(Person person) {
            if (age != person.age) {
                System.out.println("age");
            }
            if (!Objects.equals(firstName, person.firstName)) {
                System.out.println("firstName");
            }
            if (!Objects.equals(lastName, person.lastName)) {
                System.out.println("lastName");
            }
            if (!Objects.equals(someInstant, person.someInstant)) {
                System.out.println("someInstant");
            }
            if (!Objects.equals(birthDateUtilDate, person.birthDateUtilDate)) {
                System.out.println("birthDateUtilDate");
            }
            if (!Objects.equals(birthDateSqlDate, person.birthDateSqlDate)) {
                System.out.println("birthDateSqlDate " + birthDateSqlDate + " :: " + person.birthDateSqlDate);
                System.out.println("\t" + birthDateSqlDate.getTime() + " :: " + person.birthDateSqlDate.getTime());
            }
            if (!Objects.equals(birthDateLocalDate, person.birthDateLocalDate)) {
                System.out.println("birthDateLocalDate " + birthDateSqlDate + " :: " + person.birthDateLocalDate);
            }
            if (!Objects.equals(birthSqlTime, person.birthSqlTime)) {
                System.out.println("birthSqlTime " + birthSqlTime + " :: " + person.birthSqlTime);
                System.out.println("\t" + birthSqlTime.getTime() + " :: " + person.birthSqlTime.getTime());
            }
            if (!Objects.equals(birthLocalTime, person.birthLocalTime)) {
                System.out.println("birthLocalTime");
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Person person = (Person) o;
            return age == person.age &&
                    Objects.equals(firstName, person.firstName) &&
                    Objects.equals(lastName, person.lastName) &&
                    Objects.equals(someInstant, person.someInstant) &&
                    Objects.equals(birthDateUtilDate, person.birthDateUtilDate) &&
                    Objects.equals(birthDateSqlDate, person.birthDateSqlDate) &&
                    Objects.equals(birthDateLocalDate, person.birthDateLocalDate) &&
                    Objects.equals(birthSqlTime, person.birthSqlTime) &&
                    Objects.equals(birthLocalTime, person.birthLocalTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), firstName, lastName, someInstant, birthDateUtilDate, birthDateSqlDate, birthDateLocalDate, birthSqlTime, birthLocalTime, age);
        }
    }
}
