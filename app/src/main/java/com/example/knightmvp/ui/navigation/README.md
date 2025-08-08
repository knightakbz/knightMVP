# Архитектура навигации

## Обзор

Данная архитектура навигации применяет принципы **чистой архитектуры** и **чистого кода** для создания масштабируемого и поддерживаемого решения навигации в Android приложении с использованием Jetpack Compose Navigation.

## Структура файлов

```
navigation/
├── AppNavigation.kt          # Основной компонент навигации
├── NavigationActions.kt      # Интерфейс и реализация навигационных действий
├── ScreenFactory.kt          # Фабрика для создания экранов
├── GraphBuilders.kt          # Построители графов навигации
└── README.md                 # Документация
```

## Принципы архитектуры

### 1. Разделение ответственности (Single Responsibility Principle)
- **NavigationActions** - отвечает только за навигационные действия
- **ScreenFactory** - отвечает только за создание экранов
- **GraphBuilder** - отвечает только за построение графов
- **AppNavigation** - оркестрирует все компоненты

### 2. Инверсия зависимостей (Dependency Inversion Principle)
- Все компоненты зависят от абстракций (интерфейсов), а не от конкретных реализаций
- Легко заменить любую реализацию без изменения остального кода

### 3. Открытость/Закрытость (Open/Closed Principle)
- Архитектура открыта для расширения (добавление новых экранов/графов)
- Закрыта для модификации (не нужно изменять существующий код)

### 4. Единственная ответственность (Single Responsibility)
- Каждый класс имеет одну четко определенную ответственность
- Функции короткие и выполняют одну задачу

## Компоненты

### 1. NavigationActions
```kotlin
interface NavigationActions {
    fun navigateToScreen(route: String)
    fun navigateToGraph(graphRoute: String)
    fun popBackStack(): Boolean
    fun exitApp()
}
```
**Ответственность**: Абстракция для всех навигационных действий

### 2. ScreenFactory
```kotlin
interface ScreenFactory {
    fun createScreen(
        route: String,
        navigationActions: NavigationActions,
        onMenuClick: () -> Unit
    ): @Composable () -> Unit
}
```
**Ответственность**: Создание Compose экранов на основе маршрута

### 3. GraphBuilder
```kotlin
interface GraphBuilder {
    fun buildGraph(
        navGraphBuilder: NavGraphBuilder,
        navigationActions: NavigationActions,
        onMenuClick: () -> Unit,
        onExitApp: () -> Unit
    )
}
```
**Ответственность**: Построение графов навигации

### 4. BaseGraphBuilder
**Ответственность**: Базовая реализация построения графов с общей логикой

### 5. Конкретные GraphBuilder'ы
- **HomeGraphBuilder** - граф главного экрана (Home, About, Settings)
- **SecondGraphBuilder** - граф второго экрана (Second, Profile)
- **DetailGraphBuilder** - граф деталей (Detail, Info)

## Преимущества

### 1. Тестируемость
- Все компоненты можно легко тестировать изолированно
- Можно создавать моки для NavigationActions и ScreenFactory

### 2. Масштабируемость
- Добавление нового экрана требует только обновления ScreenFactory
- Добавление нового графа требует только создания нового GraphBuilder

### 3. Поддерживаемость
- Код легко читается и понимается
- Изменения локализованы в соответствующих компонентах

### 4. Гибкость
- Можно легко заменить любую реализацию
- Поддержка различных стратегий навигации

## Примеры использования

### Добавление нового экрана
1. Добавить новый route в `Screen`
2. Обновить `ScreenFactoryImpl` для создания нового экрана
3. Добавить экран в соответствующий `GraphBuilder`

### Добавление нового графа
1. Создать новый `GraphBuilder` наследующий от `BaseGraphBuilder`
2. Добавить его в список `graphBuilders` в `AppNavigation`

### Изменение логики навигации
1. Обновить `NavigationActionsImpl` или создать новую реализацию
2. Внедрить новую реализацию в `AppNavigation`

## Соответствие принципам SOLID

- ✅ **S** - Single Responsibility: каждый класс имеет одну ответственность
- ✅ **O** - Open/Closed: открыт для расширения, закрыт для модификации
- ✅ **L** - Liskov Substitution: все реализации можно заменить на их интерфейсы
- ✅ **I** - Interface Segregation: интерфейсы содержат только необходимые методы
- ✅ **D** - Dependency Inversion: зависимости от абстракций, а не от конкретных классов
