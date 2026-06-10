# 8️⃣ ФСР для линейной однородной системы n-го порядка (комплексные корни)

## 📖 Определение

Если матрица $A$ вещественная, комплексные собственные значения появляются комплексно сопряженными парами:

$$\lambda = \alpha \pm i\beta, \quad \beta \neq 0$$

---

## 🐝 Решения для комплексных корней

### **Метод 1: Эйлера**

**По Коюлерови формуле:**
$$e^{(\alpha + i\beta)t} = e^{\alpha t}(\cos(\beta t) + i\sin(\beta t))$$

**Чтобы получить вещественные решения**, принимаем вещественную и мнимо части:

$$\mathbf{x}_1(t) = e^{\alpha t}[\cos(\beta t) \mathbf{a} - \sin(\beta t) \mathbf{b}]$$
$$\mathbf{x}_2(t) = e^{\alpha t}[\sin(\beta t) \mathbf{a} + \cos(\beta t) \mathbf{b}]$$

где $\mathbf{a} = \text{Re}(\mathbf{v}), \mathbf{b} = \text{Im}(\mathbf{v})$ — вещественная и мнимая части комплексного собственного вектора.

---

## 📝 Алгоритм

### **Шаг 1:** Найти комплексное собственное значение $\lambda = \alpha + i\beta$

### **Шаг 2:** Найти комплексный собственный вектор $\mathbf{v}$ из $(A - \lambda I)\mathbf{v} = \mathbf{0}$

### **Шаг 3:** Найти $\mathbf{a} = \text{Re}(\mathbf{v}), \mathbf{b} = \text{Im}(\mathbf{v})$

### **Шаг 4:** Построить два вещественных решения:
$$\mathbf{x}_1(t) = e^{\alpha t}[\cos(\beta t) \mathbf{a} - \sin(\beta t) \mathbf{b}]$$
$$\mathbf{x}_2(t) = e^{\alpha t}[\sin(\beta t) \mathbf{a} + \cos(\beta t) \mathbf{b}]$$

---

## 📝 Пример

### Осциллятор с дампированием

$$\frac{d\mathbf{x}}{dt} = \begin{pmatrix} -1 & -4 \\ 1 & -1 \end{pmatrix} \mathbf{x}$$

**Шаг 1: Характеристическое уравнение**

$$\det(A - \lambda I) = \det\begin{pmatrix} -1-\lambda & -4 \\ 1 & -1-\lambda \end{pmatrix} = (\lambda + 1)^2 + 4 = \lambda^2 + 2\lambda + 5 = 0$$

$$\lambda = \frac{-2 \pm \sqrt{4-20}}{2} = \frac{-2 \pm 4i}{2} = -1 \pm 2i$$

Выберем $\lambda = -1 + 2i$, тогда $\alpha = -1, \beta = 2$.

**Шаг 2: Собственный вектор**

$$(A - (-1+2i)I)\mathbf{v} = \begin{pmatrix} -2i & -4 \\ 1 & -2i \end{pmatrix} \mathbf{v} = \mathbf{0}$$

От первого уравнения: $-2i v_1 - 4v_2 = 0 \Rightarrow v_1 = 2iv_2$

Выбираем $v_2 = 1$, тогда $\mathbf{v} = \begin{pmatrix} 2i \\ 1 \end{pmatrix}$

**Шаг 3: Вещественная и мнимая части**

$$\mathbf{a} = \begin{pmatrix} 0 \\ 1 \end{pmatrix}, \quad \mathbf{b} = \begin{pmatrix} 2 \\ 0 \end{pmatrix}$$

**Шаг 4: Вещественные решения**

$$\mathbf{x}_1(t) = e^{-t}[\cos(2t) \begin{pmatrix} 0 \\ 1 \end{pmatrix} - \sin(2t) \begin{pmatrix} 2 \\ 0 \end{pmatrix}] = e^{-t} \begin{pmatrix} -2\sin(2t) \\ \cos(2t) \end{pmatrix}$$

$$\mathbf{x}_2(t) = e^{-t}[\sin(2t) \begin{pmatrix} 0 \\ 1 \end{pmatrix} + \cos(2t) \begin{pmatrix} 2 \\ 0 \end{pmatrix}] = e^{-t} \begin{pmatrix} 2\cos(2t) \\ \sin(2t) \end{pmatrix}$$

---

## ❓ Интерактивные вопросы

**Q1:** Почему комплексные собственные значения вещественной матрицы появляются комплексно сопряженными парами?
<details>
<summary>Ответ</summary>
Потому что та матрица вещественная, а характеристический полином имеет вещественные коэффициенты. Фундаментальная теорема алгебры для реальных полиномов о возникновении комплексных корней сопряженными парами.
</details>

**Q2:** Как построить два линейно независимых вещественных решения из одного комплексного?
<details>
<summary>Ответ</summary>
Это делается применением формулы Эйлера и берением вещественной и мнимо частей решения.
</details>