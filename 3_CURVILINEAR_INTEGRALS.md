# 3️⃣ Криволинейные интегралы

## 📖 Определение

### Криволинейный интеграл I рода

**По длине дуги** кривой $C$:

$$\int_C f(x,y) \, ds = \lim_{\|\Delta\| \to 0} \sum_i f(x_i, y_i) \Delta s_i$$

где $\Delta s_i$ — длина элемента кривой

**Вычисление:** Если $C: \mathbf{r}(t) = (x(t), y(t))$, $t \in [a,b]$:

$$\int_C f(x,y) \, ds = \int_a^b f(x(t), y(t)) \cdot |\mathbf{r}'(t)| \, dt$$

где $|\mathbf{r}'(t)| = \sqrt{(x'(t))^2 + (y'(t))^2}$

---

### Криволинейный интеграл II рода

**По координатам** (по направлению кривой):

$$\int_C P(x,y) \, dx + Q(x,y) \, dy$$

**Вычисление:** Если $C: x = x(t)$, $y = y(t)$, $t \in [a,b]$:

$$\int_C P \, dx + Q \, dy = \int_a^b [P(x(t), y(t)) \cdot x'(t) + Q(x(t), y(t)) \cdot y'(t)] \, dt$$

**Векторная форма:**
$$\int_C \mathbf{F} \cdot d\mathbf{r} = \int_C \mathbf{F}(\mathbf{r}(t)) \cdot \mathbf{r}'(t) \, dt$$

---

## 🔍 Геометрический смысл

### Интеграл I рода
- **Площадь цилиндрической поверхности** под кривой $z = f(x,y)$ над кривой $C$
- **Масса кривой** с переменной линейной плотностью $f(x,y)$

### Интеграл II рода
- **Работа** силового поля $\mathbf{F}$ при движении вдоль кривой $C$
- **Циркуляция** векторного поля

---

## 💪 Свойства

### Криволинейный интеграл I рода:

**1. Линейность:**
$$\int_C [\alpha f + \beta g] \, ds = \alpha \int_C f \, ds + \beta \int_C g \, ds$$

**2. Аддитивность:**
Если $C = C_1 + C_2$:
$$\int_C f \, ds = \int_{C_1} f \, ds + \int_{C_2} f \, ds$$

**3. Независимость от направления:**
$$\int_C f \, ds = \int_{-C} f \, ds$$

**4. Оценка:**
$$\left| \int_C f \, ds \right| \leq M \cdot L_C$$
где $M = \max f$, $L_C$ — длина кривой

### Криволинейный интеграл II рода:

**1. Линейность:**
$$\int_C [\alpha \mathbf{F} + \beta \mathbf{G}] \cdot d\mathbf{r} = \alpha \int_C \mathbf{F} \cdot d\mathbf{r} + \beta \int_C \mathbf{G} \cdot d\mathbf{r}$$

**2. Зависимость от направления:**
$$\int_{-C} \mathbf{F} \cdot d\mathbf{r} = -\int_C \mathbf{F} \cdot d\mathbf{r}$$

**3. Замкнутая кривая:**
$$\oint_C \mathbf{F} \cdot d\mathbf{r} = -\oint_{-C} \mathbf{F} \cdot d\mathbf{r}$$

---

## 💡 Физический смысл

### Интеграл I рода
- **Масса кривой:** $m = \int_C \rho(x,y) \, ds$
- **Центр масс кривой:** $\bar{x} = \frac{1}{m}\int_C x\rho \, ds$, $\bar{y} = \frac{1}{m}\int_C y\rho \, ds$
- **Момент инерции:** $I = \int_C r^2(x,y) \rho(x,y) \, ds$

### Интеграл II рода
- **Работа силового поля:** $W = \int_C \mathbf{F} \cdot d\mathbf{r}$
- **Поток вектора через кривую:** определяет количество жидкости
- **Циркуляция:** $\Gamma = \oint_C \mathbf{F} \cdot d\mathbf{r}$

---

## 🎯 Формула Грина (связь II рода с двойным интегралом)

$$\oint_C P \, dx + Q \, dy = \iint_D \left( \frac{\partial Q}{\partial x} - \frac{\partial P}{\partial y} \right) dxdy$$

где $C$ — замкнутая кривая, $D$ — область, которую она ограничивает, обход против часовой стрелки.

**Векторная форма:**
$$\oint_C \mathbf{F} \cdot d\mathbf{r} = \iint_D (\nabla \times \mathbf{F}) \cdot \mathbf{k} \, dA$$

---

## ❓ Интерактивные вопросы

**В1:** Почему интеграл I рода не зависит от направления обхода кривой?
<details>
<summary>Ответ</summary>
Потому что $ds$ — скаляр (длина элемента), а направление обхода не влияет на длину.
</details>

**В2:** Почему интеграл II рода зависит от направления кривой?
<details>
<summary>Ответ</summary>
Потому что $d\mathbf{r}$ — вектор, который меняет направление при изменении направления обхода, и скалярное произведение $\mathbf{F} \cdot d\mathbf{r}$ меняет знак.
</details>

**В3:** Когда криволинейный интеграл II рода не зависит от пути интегрирования?
<details>
<summary>Ответ</summary>
Когда поле потенциально (консервативно), то есть $\mathbf{F} = \nabla U$ для некоторой функции $U$.
</details>