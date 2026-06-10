# 10️⃣ Линейные неоднородные системы n-го порядка. Метод вариации произвольных постоянных

## 📖 Оновная идея

**Метод вариации постоянных** — это замена констант в общем решении на временные функции:

$$\mathbf{x}(t) = c_1(t) \mathbf{x}_1(t) + c_2(t) \mathbf{x}_2(t) + \cdots + c_n(t) \mathbf{x}_n(t)$$

где $c_i(t)$ — функции, которые надо найти.

---

## ud83d� Алгоритм

### **Шаг 1: Найти ФСР однородной системы**

$$\mathbf{x}_1(t), \mathbf{x}_2(t), \ldots, \mathbf{x}_n(t)$$

### **Шаг 2: Построить фундаментальную матрицу**

$$X(t) = [\mathbf{x}_1(t) | \mathbf{x}_2(t) | \cdots | \mathbf{x}_n(t)]$$

### **Шаг 3: Выражение для показателей**

$$\mathbf{c}'(t) = X(t)^{-1} \mathbf{f}(t)$$

### **Шаг 4: Интегрировать**

$$\mathbf{c}(t) = \int X(t)^{-1} \mathbf{f}(t) \, dt$$

### **Шаг 5: Частное решение**

$$\mathbf{x}_p(t) = X(t) \int X(t)^{-1} \mathbf{f}(t) \, dt$$

---

## ud83d� Пример

### Одномерная неоднородная система

$$\frac{d\mathbf{x}}{dt} = \begin{pmatrix} 0 & 1 \\ -1 & 0 \end{pmatrix} \mathbf{x} + \begin{pmatrix} 0 \\ \sec(t) \end{pmatrix}$$

**Шаг 1: ФСР однородной**

$$\mathbf{x}_1 = \begin{pmatrix} \cos(t) \\ -\sin(t) \end{pmatrix}, \mathbf{x}_2 = \begin{pmatrix} \sin(t) \\ \cos(t) \end{pmatrix}$$

**Шаг 2: Фундаментальная матрица**

$$X(t) = \begin{pmatrix} \cos(t) & \sin(t) \\ -\sin(t) & \cos(t) \end{pmatrix}$$

**Шаг 3: Обратная матрица**

$$X(t)^{-1} = \begin{pmatrix} \cos(t) & -\sin(t) \\ \sin(t) & \cos(t) \end{pmatrix}$$

**Шаг 4: Овведение**

$$\mathbf{c}'(t) = X(t)^{-1} \mathbf{f}(t) = \begin{pmatrix} \cos(t) & -\sin(t) \\ \sin(t) & \cos(t) \end{pmatrix} \begin{pmatrix} 0 \\ \sec(t) \end{pmatrix}$$

$$= \begin{pmatrix} -\sin(t)\sec(t) \\ \cos(t)\sec(t) \end{pmatrix} = \begin{pmatrix} -\tan(t) \\ 1 \end{pmatrix}$$

$$\mathbf{c}(t) = \begin{pmatrix} \ln|\cos(t)| \\ t \end{pmatrix} + \begin{pmatrix} C_1 \\ C_2 \end{pmatrix}$$

**Шаг 5: Частное решение**

$$\mathbf{x}_p(t) = X(t) \begin{pmatrix} \ln|\cos(t)| \\ t \end{pmatrix}$$

---

## ❓ Интерактивные вопросы

**Q1:** Почему метод вариации лучше иных методов?
<details>
<summary>Ответ</summary>
Он работает для любых векторов неоднородности $\mathbf{f}(t)$, а не только для стандартных видов.
</details>