[Вопросы для собеседования](README.md)

# Основы Maven
+ [Что такое Maven?](#что-такое-maven)
+ [Какие преимущества Maven?](#какие-преимущества-maven)
+ [Какие недостатки Maven?](#какие-недостатки-maven)
+ [Какие типы репозиториев бывают?](#какие-типы-репозиториев-бывают)
+ [Какие существуют фазы сборки?](#какие-существуют-фазы-сборки)
+ [Что такое `modelVersion`, `groupId`, `artefactId`, `version`?](#что-такое-modelversion-groupid-artefactid-version)
+ [В чём разница между `SNAPSHOT` и версией?](#в-чём-разница-между-snapshot-и-версией)
+ [Что такое зависимость?](#что-такое-зависимость)
+ [Что такое транзитивная зависимость?](#что-такое-транзитивная-зависимость)
+ [Как Maven определяет какую версию зависимостей использовать, когда встречается множественный вариант выбора?](#как-maven-определяет-какую-версию-зависимостей-использовать-когда-встречается-множественный-вариант-выбора)
+ [Что такое область видимости зависимостей `dependency scope`? Назовите значения `dependency scope`](#что-такое-область-видимости-зависимостей-dependency-scope-назовите-значения-dependency-scope)
+ [Каким образом можно исключить зависимость в Maven?](#каким-образом-можно-исключить-зависимость-в-maven)
+ [Что такое цель(`goal`)?](#что-такое-цельgoal)
+ [Что такое плагин(`plugin`)?](#что-такое-плагинplugin)
+ [Какие есть типы плагинов?](#какие-есть-типы-плагинов)
+ [Что такое _жизненный цикл сборки_ в maven?](#что-такое-_жизненный-цикл-сборки_-в-maven)
+ [Опишите полный жизненный цикл сборки проектов по фазам](#опишите-полный-жизненный-цикл-сборки-проектов-по-фазам)
+ [Что такое сборка проекта, автоматизация сборки?](#что-такое-сборка-проекта-автоматизация-сборки)
+ [Что такое профиль сборки?](#что-такое-профиль-сборки)
+ [Какие существуют типы профилей сборки?](#какие-существуют-типы-профилей-сборки)
+ [Как можно активировать профили сборики?](#как-можно-активировать-профили-сборики)
+ [Что такое архетип?](#что-такое-архетип)
+ [Какими аспектами управляет Maven?](#какими-аспектами-управляет-maven)
+ [Как узнать используемую версию Maven?](#как-узнать-используемую-версию-maven)
+ [Для чего создан maven?](#для-чего-создан-maven)
+ [Где хранятся файлы классов при компиляции проекта Maven?](#где-хранятся-файлы-классов-при-компиляции-проекта-maven)
+ [Что такое `pom.xml`?](#что-такое-pomxml)
+ [Что такое _Super Pom_?](#что-такое-superpom)
+ [Какую информацию содержит `pom.xml`?](#какую-информацию-содержит-pomxml)
+ [Какие элементы необходимы для минимального _POM_?](#какие-элементы-необходимы-для-минимального-pom)
+ [Как сослаться на свойство(`property`), определённое в файле `pom.xml`?](#как-сослаться-на-свойствоproperty-определённое-в-файле-pomxml)
+ [Что такое репозиторий(`repository`)?](#что-такое-репозиторийrepository)
+ [Какие типы репозиториев существуют в Maven?](#какие-типы-репозиториев-существуют-в-maven)
+ [Какая команда устанавливает jar-файл в локальное хранилище?](#какая-команда-устанавливает-jar-файл-в-локальное-хранилище)
+ [Каков порядок поиска зависимостей Maven?](#каков-порядок-поиск-зависимостей-maven)
+ [Какие файлы настройки есть в Maven, где находятся и как называются?](#какие-файлы-настройки-есть-в-maven-где-находятся-и-как-называются)
+ [Что делает команда `mvn site-`?](#что-делает-команда-mvn-site-)
+ [Что делает команда `mvn clean-`?](#что-делает-команда-mvn-clean-)
+ [Из каких фаз состоит жизненный цикл сборки `clean`?](#из-каких-фаз-состоит-жизненный-цикл-сборки-clean)
+ [Из каких фаз состоит жизненный цикл сборки `site`?](#из-каких-фаз-состоит-жизненный-цикл-сборки-site)
+ [Что сделает команда `mvn clean dependency:copy-dependencies package`?](#что-сделает-команда-mvn-clean-dependencycopy-dependencies-package)
+ [Если при сборке проекта в тестах произошла ошибка, как собрать проект без запуска тестов?](#если-при-сборке-проекта-в-тестах-произошла-ошибка-как-собрать-проект-без-запуска-тестов)
+ [Как запустить только один тест?](#как-запустить-только-один-тест)
+ [Как остановить распределение наследования плагинов для дочерних `pom`?](#как-остановить-распределение-наследования-плагинов-для-дочерних-pom)
+ [Какие теги `pom.xml` вы знаете?](#какие-теги-pomxml-вы-знаете)

## Что такое Maven?

`Apache Maven` - это фреймворк для автоматизации сборки проектов, компиляции,
создания `jar`, создания дистрибутива программы, генерации документации. Если
собирать большие проекты с командной строки, то команда для сборки будет очень
длинной, поэтому её иногда записывают в bat/sh скрипт. Но такие скрипты зависят от
платформы. Для того чтобы избавиться от этой зависимостии и упростить написание
скрипта используют инструменты для сборки проекта.

`Maven`, обеспечивает декларативную, а не императивную сборку проекта. То есть, в
файлах проекта pom.xml содержится его декларативное описание, а не отдельные
команды. Все задачи по обработке файлов Maven выполняется через плагины"

[к оглавлению](#основы-maven)

## Какие преимущества Maven?

Основные преимущества `Maven`:
+ Независимость от OS. Сборка проекта происходит в любой операционной системе.
Файл проекта один и тот же.
+ Управление зависимостями. Редко какие проекты пишутся без использования
сторонних библиотек(зависимостей). Эти сторонние библиотеки зачастую тоже в
свою очередь используют библиотеки разных версий. Maven позволяет управлять
такими сложными зависимостями. Что позволяет разрешать конфликты версий и в
случае необходимости легко переходить на новые версии библиотек.
+ Возможна сборка из командной строки. Такое часто необходимо для автоматической
сборки проекта на сервере (Continuous Integration).
+ Хорошая интеграция со средами разработки. Основные среды разработки на java
легко открывают проекты которые собираются c помощью maven. При этом
зачастую проект настраивать не нужно - он сразу готов к дальнейшей разработке.
Как следствие - если с проектом работают в разных средах разработки, то maven
+ Удобный способ хранения настроек. Настроечный файл среды разработки и для
сборки один и тот же - меньше дублирования данных и соответственно ошибок.
+ Декларативное описание проекта.

[к оглавлению](#основы-maven)

## Какие недостатки Maven?

Недостатки `Maven`:
+ Неочевидность. Если в Ant указывается команда на удаление - и удаляется файл, то
в случае Maven надо всем сердцем довериться плагину и документации по нему.
При таком объёме необходимых знаний документации не так много, особенно по
каким-то специальным моментам. Да и просто читать придётся много. Порог
вхождения, если потребуется собирать даже не самое сложное приложение куда
выше, чем у Ant.
+ Если нужно найти какой-то специальный плагин - это будет сделать непросто,
плагинов много. И не факт, что найденный подойдёт на все 100% и будет работать
без ошибок.
+ Нужен доступ в интернет (или придётся разворачивать собственный репозиторий,
что трудоёмко)
+ Большие трудности, если проект не типовий.

[к оглавлению](#основы-maven)

## Какие типы репозиториев бывают?

Виды репозиториев:

+ локальные (`local`) - это директория, которая хранится на нашем компьютере. Она создаётся в момент первого 
выполнения любой команды Maven. Локальный репозиторий Maven хранит все зависимости проекта (библиотеки,
 плагины и т.д.). Когда мы выполняем сборку проекта с помощью Maven, то все зависимости (их JAR-файлы) 
автоматически загружаются в локальный репозиторий. Это помогает нам избежать использование ссылок на 
удалённый репозиторий при каждой сборке проекта.

+ центральные (`central`)– это репозиториий, который обеспечивается сообществом Maven. Он содержит огромное 
количество часто используемых библиотек. Если Maven не может найти зависимости в локальном репозитории, 
то автоматически начинается поиск необходимых файлов в центральном репозитории по 
этому адресу: http://repo1.maven.org/maven2/.

+ удалённые (`remote`) -является репозиторием, который определяется самим разработчиком. 
Там могут храниться все необходимые зависимости. Применяется например при подключении внутренних 
пакетов компании"

[к оглавлению](#основы-maven)

## Какие существуют фазы сборки?

Жизненный цикл сборки в Maven – это чётко определённая последовательность фаз во время выполнения которых 
должны быть достигнуты определённые цели.

Типичный жизненный цикл сборки Maven выглядит следующим образом:

```
 Фаза	                     |   Действия	             |                  Описание  
 
 prepare-resources	     |   Копирование ресурсов	     |       В этой фазе происходит копирование ресурсов, которое, также, может быть настроено.
 compile	             |   Компиляция	             |       В этой фазе происходит компиляция исходного кода.
 package	             |   Создание пакета	     |       В этой фазе, в зависимости от настроек создаётся архив JAR/WAR. Тип архива указывается в pom.xml файле. 
 install	             |   Установка	             |       В этой фазе происходит установка пакета в локальный/удалённый репозиторий maven.
```

Каждая из этих фаз имеет фазы pre и post. Они могут быть использованы для регистрации задач, которые должны 
быть запущены перед и после указанной фазы.

[к оглавлению](#основы-maven)

## Что такое `modelVersion`, `groupId`, `artefactId`, `version`?

+ `groupId` — идентификатор производителя объекта. Часто используется схема принятая в обозначении пакетов `Java`. 
Например, если производитель имеет домен visit7.com, то в качестве значения `groupId` удобно использовать значение com.visit7.
+ `artifactId` — идентификатор объекта. Обычно это имя создаваемого модуля или приложения.
+ `version` — версия описываемого объекта. Для незавершенных проектов принято добавлять суффикс `SNAPSHOT`. Например `1.0-SNAPSHOT`.

Значения идентификаторов `groupId` и `artifactId` подключаемых библиотек практически всегда можно найти на сайте www.mvnrepository.com. 
Если найти требуемую библиотеку в этом репозитории не удается, то можно использовать дополнительный репозиторий http://repo1.maven.org/maven2.

[к оглавлению](#основы-maven)

## В чём разница между `SNAPSHOT` и версией?

В случае с версией, если Maven однажды загрузил версию `data-service:1.0`, то он
больше не будет пытаться загрузить новую версию 1.0 из репозитория. Для того, чтобы
скачать обновлённый продукт `data-service` должен быть обновлён до версии 1.1.
В случае со `snapshot`, Maven автоматически будет подтягивать крайний snapshot
`(data-service:1.0-SNAPSHOT)` каждый раз, когда будет выполнятся сборка проекта.

[к оглавлению](#основы-maven)

## Что такое зависимость?

Зависимость(`dependency`) - библиотеки, устанавливаемые в файле `pom.xml`, где для каждого используемого в проекте артефакта необходимо указать :

+ параметры GAV (`groupId`, `artifactId`, `version`) и, в отдельных случаях, «необязательный» классификатор classifier;
+ области действия зависимостей scope (`compile`, `provided`, `runtime`, `test`, `system`, `import`);
+ месторасположение зависимости (для области действия зависимости system).

[к оглавлению](#основы-maven)

## Что такое транзитивная зависимость?

__Транзитивная зависимость__ - позволяет избегать необходимости изучать и указывать
библиотеки, которые требуются для самой зависимости, и включает их автоматически.
Необходимые библиотеки подгружаются в проект автоматически. При разрешении
конфликта версий используется принцип «ближайшей» зависимости, то есть
выбирается зависимость, путь к которой через список зависимых проектов является
наиболее коротким.

[к оглавлению](#основы-maven)

## Как Maven определяет какую версию зависимостей использовать, когда встречается множественный вариант выбора?

__Dependency mediation__ - определяет, какая версия зависимости будет использоваться,
когда встречается несколько версий артефактов, если две версии зависимости на той
же глубине в дереве зависимостей, то будет использоваться та которая объявлена
первой. Здесь важен порядок объявления: первое объявление выигрывает.

[к оглавлению](#основы-maven)

## Что такое область видимости зависимостей `dependency scope`? Назовите значения `dependency scope`

Существует 6 областей видимости:

+ `compile` - это область по умолчанию, использутся, если ничего больше не
определено. Compile зависимости доступны во всех _classpath_ проекта.
+ `provided` - это очень похоже на `compile`, но указывает на то, что вы ожидаете от JDK
или контейнера предоставить зависимость в ходе выполнения. Эта область доступна
только на `compilation` и `test classpath` и не является транзитивной.
+ `runtime` - эта область указывает на то, что зависимость не обязательна для
compilation, но для фаз выполнения.
+ `test` - эта область указывает, что зависимость не обязательна для нормального
использования приложения.
+ `system` - эта область похожа на `provided` за исключением того, что вы
предоставляете JAR. Артефакт всегда доступен и не смотрит в репозиторий.
+ `import` - эта область используется в зависимости типа pom в
<dependencyManagement> разделе. Это указывает на то, что определенный POM
будет заменен зависимостями в этом POM <dependencyManagement> разделе.

[к оглавлению](#основы-maven)

## Каким образом можно исключить зависимость в Maven?

Файл описания проекта предусматривает возможность исключить зависимость в
случае обнаружения цикличности или отсутствия необходимости в определённой
библиотеке. Зависимость может быть исключена используя элемент `exclusion`.

[к оглавлению](#основы-maven)

## Что такое цель(`goal`)?

`goal`-это специальная задача, которая относится к сборке проекта и его управлению. Она может привязываться как к 
нескольким фазам, так и ни к одной. Задача, которая не привязана ни к одной фазе, может быть запущена вне фаз 
сборки с помощью прямого вызова.Порядок выполнения зависит от порядка вызова целей и фаз.

Например, рассмотрим команду ниже. Аргументы `clean` и `package` являются фазами сборки до тех пор, пока 
`dependency: copy-dependencies` является задачей.
```
mvn clean dependency:copy-dependencies package
```
В этом случае, сначала будет выполнена фаза clean, после этого будет выполнена задача 
`dependency: copy-dependencies`. После чего будет выполнена фаза `package`.

[к оглавлению](#основы-maven)

## Что такое плагин(`plugin`)?

`Maven` – это фреймворк, который выполняет плагины. В этом фреймворке каждая задача, по сути своей, выполняется с помощью плагинов.

Плагины Maven использутся для:

+ создания `jar` – файла
+ создания `war` – файла
+ компиляции кода файлов
+ юнит-тестирования кода
+ создание отчётов проекта
+ создание документации проекта

В общей форме, плагин обеспечивает набор целей, которые могут быть выполнены с помощью такого синтаксиса:
```
mvn [имя-плагина]:[имя-цели]
```

[к оглавлению](#основы-maven)

## Какие есть типы плагинов?

+ __Плагины сборки__

Выполняются в процессе сборки и должны быть конфигурированны внутри блока <build></build> файла pom.xml

+ __Плагины отчётов__

Выполняются в процесса генерирования сайта и должны быть конфигурированны внутри блока <reporting></reporting> файла pom.xml.

[к оглавлению](#основы-maven)

## Что такое _жизненный цикл сборки_ в maven?
__Жизненный цикл сборки__ (_Lifecycle_) - это чётко опредлённая последовательность фаз, во время выполнения которых должын быть достигнуты определённые цели. 
Здесь фаза представляет собой стадию жизненного цикла.

[к оглавлению](#maven)

## Опишите полный жизненный цикл сборки проектов по фазам

+ `validate`	Подтверждает, является ли проект корректным и вся ли необходимая информация доступа для завершения процесса сборки.
+ `initialize`	Инициализирует состояние сборки, например, различные настройки.
+ `generate-sources`	Включает любой исходный код в фазу компиляции.
+ `process-sources`	Обрабатывает исходный код (подготавливает). Например, фильтрует определённые значения.
+ `generate-resources`	Генерирует ресурсы, которые должны быть включены в пакет.
+ `process-resources`	Копирует и отправляет ресурсы в указанную директорию. Это фаза перед упаковкой.
+ `compile`	Комплирует исходный код проекта.
+ `process-classes`	Обработка файлов, полученных в результате компиляции. Например, оптимизация байт-кода Java классов.
+ `generate-test-sources`	Генерирует любые тестовые ресурсы, которые должны быть включены в фазу компиляции.
+ `process-test-sources`	Обрабатывает исходный код тестов. Например, фильтрует значения.
+ `test-compile`	Компилирует исходный код тестов в указанную директорию тестов.
+ `process-test-classes`	Обрабатывает файлы, полученные в результате компиляции исходного кода тестов.
+ `test`	Запускает тесты, используя приемлемый фреймворк юнит-тестирования (например, Junit).
+ `prepare-package`	Выполняет все необходимые операции для подготовки пакет, непосредственно перед упаковкой.
+ `package`	Преобразует скомпилированный код и пакет в дистрибутивный формат. Такие как JAR, WAR или EAR.
+ `pre-integration-test`	Выполняет необходимые действия перед выполнением интеграционных тестов.
+ `integration-test`	Обрабатывает и распаковывает пакет, если необходимо, в среду, где будут выполняться интеграционные тесты.
+ `post-integration-test`	Выполняет действия, необходимые  после выполнения интеграционных тестов. Например, освобождение ресурсов.
+ `verify`	Выполняет любые проверки для подтверждения того, что пакет пригоден и отвечает критериям качества.
+ `install`	Устанавливает пакет в локальный репозиторий, который может быть использован как зависимость в других локальных проектах.
+ `deploy`	Копирует финальный пакет (архив) в удалённый репозиторий для, того, чтобы сделать его доступным другим разработчикам и проектам.

[к оглавлению](#основы-maven)

## Что такое сборка проекта, автоматизация сборки?

__Сборка__ (англ. assembly) - двоичный файл, содержащий исполняемый код программы
или другой, подготовленный для использования информационный продукт.
Автоматизация сборки - этап написания скриптов или автоматизация широкого
спектра задач применительно к ПО, применяемому разработчиками в их повседневной
деятельности, включая такие действия, как:
+ Компиляция исходного кода в бинарный код;
+ Сборка бинарного кода;
+ Выполнение тестов;
+ Разворачивание программы на производственной платформе;
+ Написание сопроводительной документации или описание изменений новой версии;

[к оглавлению](#основы-maven)

## Что такое профиль сборки?

__Профиль сборки__ - это множество настроек, которые могут быть использованы для установки или перезаписи стандартных значений сборки `Maven`. 
Используя профиль сборки `Maven`, мы можем настраивать сборку для различных окружений, таких как Разработка или Продакшн.

[к оглавлению](#основы-maven)

## Какие существуют типы профилей сборки?

В `Maven` существует три основных типа профилей сборки:

+ `Per Project` - определяется в POM файле pom.xml
+ `Per User` - определяется в настройках Maven - xml файл __(%USER_HOME%/.m2/settings.xml)__.
+ `Global` - определяется в глобальных настройках - xml файл __%M2_HOME%/conf/settings.xml)__.

[к оглавлению](#основы-maven)

## Как можно активировать профили сборики?

Профиль сборки `Maven` может быть активирован различными способами:

+ Использованием команды в консоли.
+ С помощью настроек `Maven`.
+ С помощью переменных окружения.
+ В настройках ОС.
+ Существующими, отсутствующими файлами

[к оглавлению](#основы-maven)

## Что такое архетип?

Для создания проекта, `Maven` использует архитипы. __Архетип__ в мавене — это шаблон нового проекта, со структурой и заготовками исходных и конфигурационных файлов.

Для создания простого Java приложения, мы будем использовать плагин `mvn-archetype-quickstart`. 

[к оглавлению](#основы-maven)

## Какими аспектами управляет Maven?

Вот основные аспекты, которыми позволяет управлять `Maven`:
+ Создание (`Build`)
+ Документирование (`Documentation`)
+ Отчёты (`Reporting`)
+ Зависимости (`Dependencies`)
+ Релизы (`Releases`)
+ SCM (`source code management`)
+ Список рассылки (`Mailing list`)
+ Дистрибьюция (`Distribution`)

[к оглавлению](#основы-maven)

## Как узнать используемую версию Maven?

При помощи команды : __mvn --version__

[к оглавлению](#основы-maven)

## Для чего создан maven?
Основной целью Maven является предоставление разработчику:
+ понятной модели для проектов, которая может быть использовано повторно и была бы проста в поддержании.
+ плагины, которые могут взаимодействовать с этой моделью.
+ структура и сожержание проекта Maven указывается в специальном _xml_-файле, который назывется Project Object Model (POM), который является базовым модулем всей системы.

[к оглавлению](#maven)

## Где хранятся файлы классов при компиляции проекта Maven?

Файлы классов хранятся в: __${basedir}/target/classes/__

[к оглавлению](#основы-maven)

## Что такое `pom.xml`?

`pom.xml` - это XML-файл, который содержит информацию о деталях проекта, и
конфигурации используемых для создания проекта на Maven. Он всегда находится в
базовом каталоге проекта. Этот файл также содержит задачи и плагины. Во время
выполнения задач, `Maven` ищет файл `pom.xml` в базовой директории проекта. Он
читает его и получает необходимую информацию, после чего выполняет задачи.
Внутри тега `<project>` содержится основная и обязательная информация
о проекте

[к оглавлению](#основы-maven)

## Что такое _Super Pom_?

Все __POM__-файлы являются наследниками родительского `pom.xml`. Этот __Pom__-файл
называется `Super Pom` и содержит значения, унаследованные по умолчанию.

[к оглавлению](#основы-maven)

## Какую информацию содержит `pom.xml`?

Среди информации которую содержит `pom.xml` мы можем выделить следующие:

+ Зависимости проекта (`project dependencies`)
+ Плагины (`plugins`)
+ Задачи/цели (`goals`)
+ Профиль создания (`build proles`)
+ Версия проекта (`project version`)
+ Разработчики (`developers`)
+ Список рассылки (`mailing list`)

[к оглавлению](#основы-maven)

## Какие элементы необходимы для минимального _POM_?

Требуемые элементы для минимального __POM__ - это корневой элемент, `modelVersion`,
`GroupID`, `artifactID`  и версия. 

[к оглавлению](#основы-maven)

## Как сослаться на свойство(`property`), определённое в файле `pom.xml`?

На все свойства в `pom.xml`, можно сослаться с помощью префиксов `project.` или `pom.` Ниже приведён пример некоторых часто используемых элементов:

__${project.build.directory}__ - ""target"" директория, или тоже самое __${pom.project.build.directory}__.

__${project.build.outputDirectory}__ - путь к директории куда компилятор складывает файлы по умолчанию `target/classes`.

__${project.name}__ или __${pom.name}__ - имя проекта.

__${project.version}__ или __${pom.version}__ - версия проекта.

[к оглавлению](#основы-maven)

## Что такое репозиторий(`repository`)?

Репозиторий (`repository`) - глобальное хранилище всех библиотек, доступных для
`Maven`, это место где хранятся артефакты: `jar` файлы, `pom`-файлы, `javadoc`, исходники,
плагины.

[к оглавлению](#основы-maven)

## Какие типы репозиториев существуют в Maven?

В `Maven` существуют три типа репозиториев:

+ __Локальный__ (`local`) репозиторий - это директория, которая хранится на нашем
компьютере. Она создаётся в момент первого выполнения любой команды Maven.
По умолчанию она расположена в <home директория>/.m2/repository -
персональная для каждого пользователя.
+ __Центральный__ (`central`) репозиторий - это репозиториий, который обеспечивается
сообществом Maven. Он содержит огромное количество часто используемых
библиотек. Который расположен в http://repo1.maven.org/maven2/ и доступен на
чтение для всех пользователей в интернете. Если Maven не может найти
зависимости в локальном репозитории, то автоматически начинается поиск
необходимых файлов в центральном репозитории
+ __Удалённые__ (`remote`) репозитории. Иногда Maven не может найти необходимые
зависимости в центральном репозитории. В этом случае, процесс сборки
прерывается и в консоль выводится сообщение об ошибке. Для того, чтобы
предотвратить подобную ситуацию, в Maven предусмотрен механизм Удалённого
репозитория, который является репозиторием, который определяется самим
разработчиком. Там могут храниться все необходимые зависимости.

[к оглавлению](#основы-maven)

## Какая команда устанавливает jar-файл в локальное хранилище?

```
mvn install
```

[к оглавлению](#основы-maven)

## Каков порядок поиска зависимостей Maven?

Когда мы выполняем собрку проекта в `Maven`, автоматически начинается поиск
необходимых зависимостей в следующем порядке:

__1.__ Поиск зависимостей в локальном репозитории. Если зависимости не обнаружены,
происходит переход к шагу 2.

__2.__ Поиск зависимостей в центральном репозитории. Если они не обнаружены и
удалённый репозиторий определён, то происходит переход к шагу 4.

__3.__ Если удалённый репозиторий не определён, то процесс сборки прекращается и
выводится сообщение об ошибке.

__4.__ Поиск зависимостей на удалённом репозитории, если они найдены, то происходит
их загрузка в локальный репозиторий, если нет - выводится сообщение об ошибке

[к оглавлению](#основы-maven)

## Какие файлы настройки есть в Maven, где находятся и как называются?

В Maven, файлы настройки называются `settings.xml`, и они расположены в двох
местах:

__1.__ Каталог где установлен Maven: __$M2_Home/conf/settings.xml__

__2.__ Домашняя директория пользователя: __${user.home}/.m2/settings.xml__

[к оглавлению](#основы-maven)

## Что делает команда `mvn site-`?

`mvn site` - создает веб-сайт проекта.

[к оглавлению](#основы-maven)

## Что делает команда `mvn clean-`?

`mvn clean` - эта команда очищает целевую директорию от созданных в процессе
сборки файлов.

[к оглавлению](#основы-maven)

## Из каких фаз состоит жизненный цикл сборки `clean`?

Жизненный цикл сборки __clean__ состоит из следующих этапов:

+ `pre-clean`
+ `clean`
+ `post-clean`

[к оглавлению](#основы-maven)

## Из каких фаз состоит жизненный цикл сборки `site`?

Жизненный цикл сборки __site__ состоит из следующих этапов:

+ `pre-site`
+ `site`
+ `post-site`
+ `site-deploy`

[к оглавлению](#основы-maven)

## Что сделает команда `mvn clean dependency:copy-dependencies package`?

Порядок выполнения зависит от порядка вызова целей и фаз. Рассмотрим даную
команду. Аргументы `clean` и `package` являются фазами сборки до тех пор, пока
`dependency:copy-dependencies` является задачей. В этом случае, сначала будет
выполнена фаза `clean`, после этого будет выполнена задача `dependency:copydependencies`. После чего будет выполнена фаза `package`.

[к оглавлению](#основы-maven)

## Если при сборке проекта в тестах произошла ошибка, как собрать проект без запуска тестов?

Для запуска сборки без выполнения тестов добавьте -Dmaven.test.skip=true к команде
в строке запуска maven:

```
mvn install -Dmaven.test.skip=true
```

[к оглавлению](#основы-maven)

## Как запустить только один тест?

Для запуска только одного теста добавьте `-Dtest=[Имя класса]` к команде в строке
запуска maven. Например:

```
mvn install -Dtest=org.apache.maven.utils.ConverterTest
```

[к оглавлению](#основы-maven)

## Как остановить распределение наследования плагинов для дочерних `pom`?

Установить `<inherited>` в `false`

[к оглавлению](#основы-maven)

## Какие теги `pom.xml` вы знаете?

+ `project` - описывает проект, это элемент верхнего уровня во всех файлах pom.xml.
+ `groupId` - по-сути, это имя пакета. Полностью отражается в структуре каталогов.
+ `artifactId` - название проекта. В структуре каталогов не отображается.
+ `version` - версия проекта.
+ `packaging` - определяет, какой тип файла будет собран. Варианты: pom, jar, war, ear.
+ `dependencies` - указываются зависимости.
+ `build` - информация о сборке проекта.
+ `name` - это уже необязательные описания проекта. В данном случае его название.
+ `description` - элемент представляет собой общее описание вашего проекта. Это часто
используется в генерации документации Maven.
+ `url` - интернет-страница проекта.
+ `repositories` - репозитории для артефактов.
+ `pluginRepositories` - репозитории для плагинов Maven.

[к оглавлению](#основы-maven)

[Вопросы для собеседования](README.md)
