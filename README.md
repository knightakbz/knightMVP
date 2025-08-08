# KnightMVP - Android App

## Описание изменений

Проект был переработан с **MVP + Cicerone** на **MVP + Navigation Compose**.

## 🎯 Цель

Создать современное Android приложение с использованием:
- **MVP архитектуры** - для четкого разделения ответственности
- **Jetpack Compose** - для современного UI
- **Navigation Compose** - для навигации
- **Moxy** - для MVP паттерна
- **Hilt** - для dependency injection
- **Retrofit** - для сетевых запросов

### Что изменилось:

#### ✅ Удалено:
- **Cicerone** - библиотека навигации для MVP
- **Fragments** - все фрагменты заменены на Compose экраны

#### ✅ Добавлено:
- **Navigation Compose** - нативная навигация для Compose
- **Moxy** - сохранена для MVP архитектуры
- **Compose экраны** - HomeScreen, SecondScreen, DetailScreen
- **Presenters** - для каждого экрана в MVP стиле

### Архитектура:

```
MVP + Compose
├── UI Layer (Compose)
│   ├── HomeScreen
│   ├── SecondScreen
│   └── DetailScreen
├── Presenter Layer
│   ├── HomePresenter
│   ├── SecondPresenter
│   └── DetailPresenter
├── View Layer (Interfaces)
│   ├── HomeView
│   ├── SecondView
│   └── DetailView
├── Navigation
│   └── AppNavigation
└── DI (Hilt)
    ├── AppModule
    └── NetworkModule
```

### Преимущества новой архитектуры:

1. **Сохранен MVP** - проверенная архитектура
2. **Современный UI** - Compose вместо Fragments
3. **Лучшая производительность** - нативная навигация
4. **Четкое разделение** - Presenter, View, Model
5. **Простота тестирования** - Presenters легко тестировать

### Навигация:

```kotlin
// Переход к экрану
navController.navigate(Screen.Second.route)

// Возврат назад
navController.popBackStack()
```

### Зависимости:

- **Navigation Compose** - навигация
- **Moxy** - MVP архитектура
- **Hilt** - dependency injection
- **Retrofit** - сетевые запросы
- **Compose BOM** - UI компоненты

### Пример использования:

```kotlin
// Presenter
class HomePresenter @Inject constructor(
    private val apiService: ApiService
) : BasePresenter<HomeView>() {
    
    fun loadData() {
        viewState.showLoading(true)
        // API вызов
        viewState.showMessage("Данные загружены")
    }
}

// View Interface
interface HomeView : BaseView {
    fun showLoading(isLoading: Boolean)
    fun showMessage(message: String)
}

// Compose Screen
@Composable
fun HomeScreen() {
    val presenter = remember { HomePresenter() }
    val view = remember { /* View implementation */ }
    
    presenter.attachView(view)
    // UI логика
}
```
