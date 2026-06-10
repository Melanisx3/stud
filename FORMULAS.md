# 📋 Справочник формул

## 🔢 Двойные интегралы

### Замена переменных (полярные координаты)
$$x = r\cos\theta, \quad y = r\sin\theta$$
$$\iint_D f(x,y) \, dxdy = \iint_D f(r\cos\theta, r\sin\theta) \cdot r \, dr\, d\theta$$

### Площадь плоской области
$$S = \iint_D dA = \iint_D 1 \, dxdy$$

### Масса пластины
$$m = \iint_D \rho(x,y) \, dA$$

### Центр масс
$$\bar{x} = \frac{1}{m}\iint_D x\rho(x,y) \, dA, \quad \bar{y} = \frac{1}{m}\iint_D y\rho(x,y) \, dA$$

### Моменты инерции
$$I_x = \iint_D y^2\rho(x,y) \, dA, \quad I_y = \iint_D x^2\rho(x,y) \, dA$$
$$I_O = I_x + I_y = \iint_D (x^2 + y^2)\rho(x,y) \, dA$$

---

## 🔢 Тройные интегралы

### Объём тела
$$V = \iiint_V dV = \iiint_V 1 \, dxdydz$$

### Масса тела
$$m = \iiint_V \rho(x,y,z) \, dV$$

### Центр масс
$$\bar{x} = \frac{1}{m}\iiint_V x\rho \, dV, \quad \bar{y} = \frac{1}{m}\iiint_V y\rho \, dV, \quad \bar{z} = \frac{1}{m}\iiint_V z\rho \, dV$$

### Моменты инерции
$$I_z = \iiint_V (x^2 + y^2)\rho \, dV$$
$$I_x = \iiint_V (y^2 + z^2)\rho \, dV, \quad I_y = \iiint_V (x^2 + z^2)\rho \, dV$$

### Цилиндрические координаты
$$x = r\cos\theta, \quad y = r\sin\theta, \quad z = z$$
$$dxdydz = r \, dr\, d\theta\, dz$$

### Сферические координаты
$$x = \rho\sin\varphi\cos\theta, \quad y = \rho\sin\varphi\sin\theta, \quad z = \rho\cos\varphi$$
$$dxdydz = \rho^2\sin\varphi \, d\rho\, d\varphi\, d\theta$$

---

## 🔢 Криволинейные интегралы

### Интеграл I рода
$$\int_C f(x,y) \, ds = \int_a^b f(x(t), y(t)) \sqrt{(x'(t))^2 + (y'(t))^2} \, dt$$

### Интеграл II рода
$$\int_C P \, dx + Q \, dy = \int_a^b [P(x(t),y(t))x'(t) + Q(x(t),y(t))y'(t)] \, dt$$

### Масса к��ивой
$$m = \int_C \rho(x,y) \, ds$$

### Центр масс кривой
$$\bar{x} = \frac{1}{m}\int_C x\rho(x,y) \, ds, \quad \bar{y} = \frac{1}{m}\int_C y\rho(x,y) \, ds$$

### Момент инерции кривой
$$I = \int_C r^2(x,y)\rho(x,y) \, ds$$

### Работа силового поля
$$W = \int_C \mathbf{F} \cdot d\mathbf{r} = \int_C P \, dx + Q \, dy + R \, dz$$

### Формула Грина (теорема о циркуляции)
$$\oint_C P \, dx + Q \, dy = \iint_D \left( \frac{\partial Q}{\partial x} - \frac{\partial P}{\partial y} \right) dxdy$$

---

## 🔢 Поверхностные интегралы

### Интеграл I рода
$$\iint_S f(x,y,z) \, dS = \iint_D f(x,y,z(x,y)) \sqrt{1 + p^2 + q^2} \, dxdy$$
где $p = \frac{\partial z}{\partial x}$, $q = \frac{\partial z}{\partial y}$

### Площадь поверхности
$$S = \iint_S dS = \iint_D \sqrt{1 + p^2 + q^2} \, dxdy$$

### Интеграл II рода
$$\iint_S P \, dydz + Q \, dxdz + R \, dxdy = \iint_D \left( -Pp - Qq + R \right) dxdy$$

### Поток векторного поля
$$\Phi = \iint_S \mathbf{F} \cdot d\mathbf{S}$$

### Формула Гаусса-Остроградского
$$\iint_S \mathbf{F} \cdot d\mathbf{S} = \iiint_V (\nabla \cdot \mathbf{F}) \, dV$$

### Формула Стокса
$$\oint_C \mathbf{F} \cdot d\mathbf{r} = \iint_S (\nabla \times \mathbf{F}) \cdot d\mathbf{S}$$

---

## 🔢 Операторы теории поля

### Градиент
$$\nabla u = \left( \frac{\partial u}{\partial x}, \frac{\partial u}{\partial y}, \frac{\partial u}{\partial z} \right)$$

### Дивергенция
$$\nabla \cdot \mathbf{F} = \frac{\partial P}{\partial x} + \frac{\partial Q}{\partial y} + \frac{\partial R}{\partial z}$$

### Ротор
$$\nabla \times \mathbf{F} = \begin{vmatrix} \mathbf{i} & \mathbf{j} & \mathbf{k} \\ \frac{\partial}{\partial x} & \frac{\partial}{\partial y} & \frac{\partial}{\partial z} \\ P & Q & R \end{vmatrix}$$

### Лапласиан
$$\Delta u = \nabla^2 u = \frac{\partial^2 u}{\partial x^2} + \frac{\partial^2 u}{\partial y^2} + \frac{\partial^2 u}{\partial z^2}$$

---

## 🔢 Оси симметрии и их использование

| Область | Система координат | Якобиан | Области интегрирования |
|---------|-------------------|--------|------------------------|
| Круг/кольцо в $xy$ | Полярные $(r,\theta)$ | $r$ | $0 \leq r \leq R$, $0 \leq \theta \leq 2\pi$ |
| Цилиндр | Цилиндрические $(r,\theta,z)$ | $r$ | $0 \leq r \leq R$, $0 \leq \theta \leq 2\pi$, $0 \leq z \leq h$ |
| Шар/сфера | Сферические $(\rho,\varphi,\theta)$ | $\rho^2\sin\varphi$ | $0 \leq \rho \leq R$, $0 \leq \varphi \leq \pi$, $0 \leq \theta \leq 2\pi$ |

---

## 📝 Типичные задачи

### 1. Найти объём под поверхностью
$$V = \iint_D z(x,y) \, dxdy$$

### 2. Найти массу пластины
$$m = \iint_D \rho(x,y) \, dxdy$$

### 3. Найти работу силового поля
$$W = \int_C \mathbf{F} \cdot d\mathbf{r}$$

### 4. Найти поток через поверхность
$$\Phi = \iint_S \mathbf{F} \cdot \mathbf{n} \, dS$$

### 5. Найти циркуляцию
$$\Gamma = \oint_C \mathbf{F} \cdot d\mathbf{r}$$

---

## 💡 Мнемонические правила

1. **Якобиан** = определитель матрицы частных производных
2. **Интеграл II рода** зависит от направления, **интеграл I рода** — не зависит
3. **Дивергенция** = источники (скаляр), **ротор** = вихри (вектор)
4. **Потенциальное поле** ⟹ $\text{rot} = 0$, **соленоидальное** ⟹ $\text{div} = 0$
5. **Формула Грина** связывает криволинейный интеграл с двойным
6. **Формула Стокса** связывает циркуляцию с потоком ротора
7. **Формула Гаусса-Остроградского** связывает поток с дивергенцией