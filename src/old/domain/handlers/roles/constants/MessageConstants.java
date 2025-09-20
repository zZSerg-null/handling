package ru.old.domain.handlers.roles.constants;

public class MessageConstants {

    public static final String DATA_WAS_SET = "✅";
    public static final String EMPTY_FIELD = "❗";

    public static final String EDIT_COMMAND = "/start";

    public static final String UNKNOWN_DATA = "Даже не знаю что это такое";

    /**
     * Сообщения выпадающего меню Telegram
     */

    public static final String NEW_USER = "new_user_menu_request";
    public static final String UPDATE_COMMAND = "/update"; //Присылает клавиатуру и последнее сообщение бота
    public static final String QUEST_COMMAND = "/quests"; //меню квестов
    public static final String ACCOUNT_COMMAND = "/account"; //меню аккаунта


    /**
     * Команды, существующие только в режиме создателя - реакция на нажатие ReplyKeyboard при создании квеста
     */
    public static final String APPLY_COMMAND = "Сохранить"; //меню аккаунта
    public static final String SET_NULL_COMMAND = "Удалить все"; //меню аккаунта



    public static final String UNSAVED_NODE_DATA = "Не указаны основные данные этапа. Все второстепенные данные будут сброшены. Хотите продолжить?";




    public static final String EMPTY_UPDATE_DATA = "Выберите интересующий вас пункт меню что бы начать работу с ботом";

    public static final String QUEST_CREATION_STARTED_TEXT = "Имя квеста успешно записано! \n\n" +
            "Теперь можно приступить к созданию первого этапа квеста. Для простоты, мы представили этапы четырьмя " +
            "разными типами:\n\n";


    public static final String STOP_CREATING = " - сохранен. \nВы всегда можете вернуться к нему что бы дополнить или изменить";




    public static final String QUEST_MAIN_MENU = "Выберите действие";
    public static final String ACCOUNT_MAIN_MENU = "Выберите действие";


    /**
     * Сообщения User подменю
     */
    public static final String USER_MAIN_MENU = "Выберите действие";
    public static final String QUEST_MENU_SHOW_ALL = "Список всех ваших квестов";
    public static final String QUEST_MENU_RUNNING_QUESTS = "Список запущенных квестов";
    public static final String NEW_QUEST_WAS_CREATED = "Квест создан!\n" +
            "Но как известно, у каждого квеста должно быть название, по этому давайте его придумаем. " +
            "Оно должно быть простым, красивым и отражать его суть.";


    /**
     * Сообщения Account подменю
     */
    public static final String ACCOUNT_MENU_STATISTIC = "Статистика по пользователю";
    public static final String ACCOUNT_MENU_CHANGE_NAME = "Хотите сменить имя?";


    /**
     * Сообщение выбора типа ноды
     */
    public static final String NODE_TYPE_SELECTION_MENU_TEXT =
            "1) Простое сообщение: содержит текст или файл с описанием\n\n" +
            "2) Опрос: обычный опрос с вариантами ответов и указанием правильных из них\n\n" +
            "3) Геопозиция: содержит гео точку, которую необходимо достигнуть\n\n" +
            "4) Группа файлов: файлы, которые будут объеденины и отправлены пользователю одним сообщением";


    /**
     * Сообщения при выборе конкретной ноды
     */
    public static final String GEO_NODE_MENU_TEXT = "Здесь можно настроить геопозицию которая будет показана пользователю, " +
            "а так же указать, следует ли ему ее достигнуть её для завершения этапа и определить ответы, которые пользователь должен дать.";
    public static final String POLL_NODE_MENU_TEXT = "Здесь можно настроить опросник. Для этого нужно просто указать вопрос и варианты ответов с новой строки. \n" +
            "Верные ответы должны быть помечены знаком двойного сложения '++' в начале строки. \n\n" +
            "Пример: \n" +
            "Сколько цветов на флаге России?\n" +
            "Пять\n" +
            "Семь\n" +
            "++Три\n\n" +
            "Или так:\n" +
            "Какие живые существа умеют летать?\n" +
            "Пингвин\n" +
            "++Бабочка\n" +
            "++Птица\n" +
            "Собака";
    public static final String MESSAGE_NODE_MENU_TEXT = "Простое сообщение может содержать текст, прикрепленный файл и ожидаемые ответы пользователя";
    public static final String MEDIA_NODE_MENU_TEXT = "Прикрепите один или несколько файлов, для этого просто перетащите их сюда. \n" +
            "Обратите внимание: одно сообщение не может содержать более 10 файлов. \n" +
            "В случае, если будет прикреплено более 10 файлов, лишние будут отправлены отдельным сообщением.";


    /**
     * Message Node
     */
    public static final String ADDING_TEXT = "Пожалуйста, введите текст сообщения этапа";
    public static final String ADDING_FILE = "Добавьте необходимый файл. " +
            "Обратите внимание, платформа Telegram не позволяет прикрепить к сообщению с текстом более одного файла за раз. " +
            "Если файлов будет несколько, они все будут отправлены отдельным сообщением, а все остальное - отдельным.";

    /**
     * Geopoint Node
     */
    public static final String ADDING_GEOPOINT = "Укажите геоточку. Значек скрепки внизу - прикрепить, выберите 'Геопозиция'.\n" +
            "Затем выберите точку на карте (можно не включать GPS) и нажмите 'Отправить выбранную геопозицию'";
    public static final String GEOPOINT_RADIUS_MENU = "Здесь вы можете указать радиус от точки в метрах, при входе в который игроку будет показано сообщение, а так же указать само сообщение.";
    public static final String DIALOG_NODE_ADD_RADIUS_VALUE = "Укажите активный радиус в метрах используя целый числа. Минимальный радиус - 10";
    public static final String DIALOG_NODE_ADD_RADIUS_MESSAGES = "Укажите сообщение, которое должно быть показано пользователю";


    /**
     * Текст менюшек настройки общения с пользователем: ожидаемые ответы, реакции и прочее
     */
    public static final String EXPECTED_ANSWERS_MENU = "Здесь вы можете настроить ";
    public static final String ADDING_EXPECTED_ANSWERS = "Укажите через ';' ответы, которые бот будет ожидать от пользователя. \n" +
            "Если ответ неоднозначный, следует предусмотреть все варианты.\n\n" +
            "Например... Предположим, текст этапа заканчивался словами: '... какое животное по мнению большинства является настоящим другом человека?', " +
            "ожидаемые ответы пользователя в таком случае, могут быть следующими:\n" +
            "собака; собачка; пес; псина; песик; бася";
    public static final String ADDING_EXPECTED_ANSWERS_COUNT = "Укажите минимальное количество ответов, которые должен дать пользователь. Сейчас установлено: ";
    public static final String ADDING_CORRECT_MESSAGE_REACT = "Укажите через ';' сообщения, которые" +
            " будут выдаваться пользователю в случае, если он дает верные ответы. (Если сообщения не указаны - реакции на верные ответы не будет). \n" +
            "Например: Молодец!; Умница \uD83D\uDE0A; Ответ как всегда верный \uD83D\uDC4D";
    public static final String ADDING_WRONG_MESSAGE_REACT = "Укажите через ';' сообщения, которые" +
            " будут выдаваться пользователю в случае, если он дает НЕверные ответы. (Если сообщения не указаны - реакции на НЕверные ответы не будет)\n" +
            "Например: \uD83D\uDE15; Ну, не совсем; А еще подумать? \uD83E\uDD78; \uD83D\uDE14; А вот и нет\uD83E\uDD2A;";


    public static final String ADDING_POLL_NODE_QUESTION = "public static final String ";

    public static final String ADDING_POLL_NODE_ANSWERS = "public static final String ";
    public static final String ADDING_POLL_NODE_EXPLANATION = "public static final String ";


}
