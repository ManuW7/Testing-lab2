# Testing lab2

Лабораторная работа по тестированию системы математических функций. Для `x <= 0` вычисляется тригонометрическая ветка, для `x > 0` - логарифмическая. В проекте есть unit-тесты, интеграционные тесты с Mockito-заглушками, CSV-выгрузки и SVG-графики.

Подробное описание архитектуры и тестов: `src/LAB2_ARCHITECTURE_AND_TESTING.md`.

## Требования

- JDK 26
- Maven, например встроенный Maven из IntelliJ IDEA

Если обычная команда `java -version` показывает старую Java, в PowerShell выставьте JDK 26:

```powershell
$env:JAVA_HOME='C:\Users\mamon\.jdks\openjdk-26-1'
$env:Path="$env:JAVA_HOME\bin;$env:Path"
```

## Запуск тестов

```powershell
& 'C:\Program Files\JetBrains\IntelliJ IDEA 2026.1\plugins\maven\lib\maven3\bin\mvn.cmd' test
```

## Генерация CSV

Сначала запустите тесты или любую Maven-сборку, чтобы появились классы в `target/classes`.

Все CSV для графиков:

```powershell
& "$env:JAVA_HOME\bin\java.exe" -cp target\classes CsvTools.AllGraphCsvGenerator csv-exports
```

Один модуль:

```powershell
& "$env:JAVA_HOME\bin\java.exe" -cp target\classes CsvTools.ModuleCsvGenerator system -2 2 0.1 csv-exports\system.csv
```

## Генерация графиков

```powershell
& "$env:JAVA_HOME\bin\java.exe" -cp target\classes CsvTools.CsvGraphSvgGenerator csv-exports graph-exports
```

После запуска общий просмотр графиков находится в:

```text
graph-exports/index.html
```
