package Client;

/**
 * Created by Android on 05.10.2016.
 */
public class Report {
    static final public int SQL_EXCEPTION = 601; // Ошибка выполнения запроса
    static final public int THE_USER_EXIST = 602; // Ошибка: Пользователь существует
    static final public int THE_USER_IS_NOT_EXIST = 603; // Такого пользователя несуществует
    static final public int COOKIE_FAIL = 604; // Ошибка: У пользователя нет куков или они не верны
    static final public int JSON_DECODE_FAIL = 605; // Ошибка декодирования json
    static final public int LOG_OR_PASS_IS_NOT_COR = 606; // Логин или пароль неверные
    static final public int FRIEND_IS_NOT_DEL = 607; // друг не удален
    static final public int FRIEND_IS_NOT_ADD= 608; // Друг не добавлен

    static final public int SUCCESSFUL_SQL = 200;


    // TYPE
    static final public int MESSAGE = 1; // Сообщение
    static final public int CONTACT = 2; // добавить Контакт
    static final public int DEL_FRIEND = 3; // Удалить друга
    static final public int FIND_CONTACTS = 4; // Запрос на поиск контакта // такой же ответ
    static final public int UPDATE_MESSAGE = 5;
    static final public int UPDATE_LIST = 6;
    static final public int GIVE_ME_ABOUT = 7;
    static final public int GIVE_ME_STATUS = 8;

    static final public int GIVE_MY_FRIENDS = 32; // Запрос, дай мне список контактов
    static final public int REGISTATION = 30; // Запрос регистрации
    static final public int AUTHORISATION = 31; // Запрос авторизации
    static final public int GIVE_MY_DIALOG = 33; // Запрос на диалог
    static final public int SENDING_MESSAGE = 34; // Отправка сообщения
    // Ответ сервера
    static final public int FIND_CONTACT = 15;
    static final public int NOT_FIND_CONTACT = 16;
    static final public int SUCCESSFUL_REG = 17; // Удачная региcтрация
    static final public int SUCCESSFUL_AUTH = 18; // Удачная авторизация
    static final public int SUCCESSFUL_SEND_MES = 19; // Удачная отправка сообщения
    static final public int SUCCESSFUL_FRIENDS = 20; // Удачно отправленный список друзей
    static final public int SUCCESSFUL_DEL = 21; //удачное удаление контакта
    static final public int SUCCESSFUL_ADD = 22; // Удачное добавление друга
    static final public int SUCCESSFUL_MES = 23; // Удачно отправлено сообщение
    static final public int SUCCESSFULL_ABOUT = 24; // Удачно отправлена информация о пользователе
    static final public int SUCCESSFUL_STATUS = 26;
    static final public int NO_UPDATES = 25; // Обновлений нет

    public int type;
    public Object data;
}
