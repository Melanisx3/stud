# 2️⃣ Тройные интегралы

## 📖 Определение

**Тройной интеграл** функции $f(x,y,z)$ по области $V$:

$$\iiint_V f(x,y,z) \, dV = \lim_{\|\Delta\| \to 0} \sum_{i,j,k} f(x_{ijk}, y_{ijk}, z_{ijk}) \Delta V_{ijk}$$

Где $\Delta V_{ijk}$ — объём элементарного параллелепипеда.

---

## 🎯 Геометрический смысл

- **Объём области** $V$: $V = \iiint_V 1 \, dV$
- **Гиперобъём** в 4D (при интегрировании по времени)
- Обобщение двойного интеграла в пространство

---

## 💪 Свойства

### 1. **Линейность**
$$\iiint_V [\alpha f + \beta g] \, dV = \alpha \iiint_V f \, dV + \beta \iiint_V g \, dV$$

### 2. **Аддитивность**
Если $V = V_1 \cup V_2$ и они не пересекаются внутри:
$$\iiint_V f \, dV = \iiint_{V_1} f \, dV + \iiint_{V_2} f \, dV$$

### 3. **Монотонность**
Если $f \leq g$ на $V$:
$$\iiint_V f \, dV \leq \iiint_V g \, dV$$

### 4. **Оценка**
$$m \cdot V_0 \leq \iiint_V f \, dV \leq M \cdot V_0$$

---

## 📐 Вычисление

### Декартовы координаты

**Метод сечений (обобщённый принцип Кавальери):**
$$\iiint_V f(x,y,z) \, dxdydz = \int_a^b \left( \iint_{D(z)} f(x,y,z) \, dxdy \right) dz$$

где $D(z)$ — сечение области плоскостью $z = \text{const}$

### Цилиндрические координаты

**Связь:**
- $x = r\cos\theta$
- $y = r\sin\theta$
- $z = z$

**Якобиан:** $J = r$

$$\iiint_V f(x,y,z) \, dxdydz = \iiint_V f(r\cos\theta, r\sin\theta, z) \cdot r \, dr\, d\theta\, dz$$

**Когда использовать:** оси симметрии совпадают с осью $z$

### Сферические координаты

**Связь:**
- $x = \rho\sin\varphi\cos\theta$
- $y = \rho\sin\varphi\sin\theta$
- $z = \rho\cos\varphi$

**Якобиан:** $J = \rho^2\sin\varphi$

$$\iiint_V f \, dxdydz = \iiint_V f(\rho\sin\varphi\cos\theta, \rho\sin\varphi\sin\theta, \rho\cos\varphi) \cdot \rho^2\sin\varphi \, d\rho\, d\varphi\, d\theta$$

**Когда использовать:** сферические области, центр в начале координат

---

## 🎯 Якобиан для 3D преобразований

**Якобиан преобразования** $(x,y,z) = (x(u,v,w), y(u,v,w), z(u,v,w))$:

$$J = \begin{vmatrix} \frac{\partial x}{\partial u} & \frac{\partial x}{\partial v} & \frac{\partial x}{\partial w} \\ \frac{\partial y}{\partial u} & \frac{\partial y}{\partial v} & \frac{\partial y}{\partial w} \\ \frac{\partial z}{\partial u} & \frac{\partial z}{\partial v} & \frac{\partial z}{\partial w} \end{vmatrix}$$

**Геометрический смысл:** $|J|$ — коэффициент растяжения объёмов

---

## 💡 Физический смысл

### 1. **Масса тела**
$$m = \iiint_V \rho(x,y,z) \, dV$$

### 2. **Статические моменты**
$$M_{xy} = \iiint_V z\rho \, dV, \quad M_{xz} = \iiint_V y\rho \, dV, \quad M_{yz} = \iiint_V x\rho \, dV$$

### 3. **Центр масс**
$$\bar{x} = \frac{M_{yz}}{m}, \quad \bar{y} = \frac{M_{xz}}{m}, \quad \bar{z} = \frac{M_{xy}}{m}$$

### 4. **Моменты инерции**
$$I_x = \iiint_V (y^2 + z^2)\rho \, dV$$
$$I_y = \iiint_V (x^2 + z^2)\rho \, dV$$
$$I_z = \iiint_V (x^2 + y^2)\rho \, dV$$

---

## 📝 Примеры

### Пример 1: Объём шара
$$V = \iiint_V 1 \, dV = \int_0^{2\pi} \int_0^\pi \int_0^R \rho^2\sin\varphi \, d\rho\, d\varphi\, d\theta$$
$$= \int_0^{2\pi} d\theta \cdot \int_0^\pi \sin\varphi\, d\varphi \cdot \int_0^R \rho^2 d\rho = 2\pi \cdot 2 \cdot \frac{R^3}{3} = \frac{4\pi R^3}{3}$$

### Пример 2: Цилиндрическое тело
Объём цилиндра радиуса $R$ и высоты $h$:
$$V = \int_0^{2\pi} \int_0^R \int_0^h r \, dz\, dr\, d\theta = \int_0^{2\pi} d\theta \cdot \int_0^R rh \, dr = 2\pi \cdot \frac{R^2h}{2} = \pi R^2 h$$

---

## 📊 Суммы Дарбу для тройного интеграла

**Верхняя сумма:**
$$U(f, P) = \sum_{i,j,k} M_{ijk} \Delta V_{ijk}$$

**Нижняя сумма:**
$$L(f, P) = \sum_{i,j,k} m_{ijk} \Delta V_{ijk}$$

**Связь с интегралом:**
$$\lim_{\|P\|\to 0} L(f,P) = \iiint_V f \, dV = \lim_{\|P\|\to 0} U(f,P)$$

---

## ❓ Интерактивные вопросы

**В1:** Как изменится якобиан, если поменять порядок координат в преобразовании?
<details>
<summary>Ответ</summary>
Якобиан может изменить знак, но его абсолютное значение остаётся тем же. Это зависит от ориентации координатной системы.
</details>

**В2:** Почему в сферических координатах якобиан содержит множитель $\rho^2\sin\varphi$?
<details>
<summary>Ответ</summary>
Это результат вычисления определителя матрицы частных производных. $\rho^2$ отвечает за радиальное растяжение, $\sin\varphi$ — за угловое.
</details>

**В3:** Что произойдёт, если в двойном интеграле выбрать неправильный порядок интегрирования?
<details>
<summary>Ответ</summary>
Вычисления станут сложнее, границы интегрирования будут запутанными, но результат (если правильно их найти) будет одинаковым.
</details>