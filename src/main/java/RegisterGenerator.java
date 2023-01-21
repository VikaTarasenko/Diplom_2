import org.apache.commons.lang3.RandomStringUtils;

public class RegisterGenerator {


    public Register generic(){
        return new Register("Pas@yandex.ru", "rgfghdjd", "Taras");
    }
    public Register random() {
        return new Register( RandomStringUtils.randomAlphanumeric(10)+"@yandex.ru", RandomStringUtils.randomAlphanumeric(10), "Taras"); //создаем рандомный логин курьера, таким же принципом можно создать рандомный пароли и фамилию

    }
}
