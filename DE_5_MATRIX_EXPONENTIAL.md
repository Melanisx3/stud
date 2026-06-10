# 5️⃣ Матричные ряды. Матричная экспонента

## 📖 Определение матричных рядов

**Матричный ряд** — это ряд вида:

$$\sum_{n=0}^{\infty} A_n = A_0 + A_1 + A_2 + \cdots$$

где $A_n$ — матрицы размера $m \times m$.

**Частичные суммы:**
$$S_N = \sum_{n=0}^{N} A_n$$

**Сходимость:** Ряд сходится, если существует лимит:
$$S = \lim_{N \to \infty} S_N$$

---

## 🔑 Критерий сходимости

### **Критерий 1: По норме**

Если $\|A_n\| \to 0$ (норма матрицы), то необходимое условие сходимости выполнено.

### **Критерий 2: Абсолютная сходимость**

Если ряд норм $$\sum_{n=0}^{\infty} \|A_n\|$$ сходится, то исходный матричный ряд абсолютно сходится.

---

## 🐝 Матричная экспонента

**Определение:** Для постоянной матрицы $A$ размера $n \times n$ **матричная экспонента** определяется как:

$$e^A = I + A + \frac{A^2}{2!} + \frac{A^3}{3!} + \cdots = \sum_{k=0}^{\infty} \frac{A^k}{k!}$$

Обозначение: $\exp(A)$ или $e^A$.

**Обобщение для зависимости от времени:**

$$e^{tA} = I + tA + \frac{(tA)^2}{2!} + \frac{(tA)^3}{3!} + \cdots = \sum_{k=0}^{\infty} \frac{t^k A^k}{k!}$$

---

## 🔑 Основные свойства матричной экспоненты

### **Свойство 1: $e^0 = I$**

$$e^{0 \cdot t} = \sum_{k=0}^{\infty} \frac{(0)^k}{k!} = I$$

---

### **Свойство 2: Обратность**

$$\left(e^A\right)^{-1} = e^{-A}$$

**Доказательство:**

$$e^A \cdot e^{-A} = \left(\sum_{k=0}^{\infty} \frac{A^k}{k!}\right) \left(\sum_{j=0}^{\infty} \frac{(-A)^j}{j!}\right) = \sum_{n=0}^{\infty} \frac{1}{n!} \sum_{k=0}^{n} \binom{n}{k} A^k (-A)^{n-k}$$

$$= \sum_{n=0}^{\infty} \frac{(A - A)^n}{n!} = \sum_{n=0}^{\infty} \frac{0^n}{n!} = I$$

---

### **Свойство 3: Коммутативность**

Если матрицы $A$ и $B$ коммутируют (т.е. $AB = BA$), то:

$$e^A e^B = e^{A+B} = e^B e^A$$

**Остережение:** Если $AB \neq BA$, то генерально $e^A e^B \neq e^{A+B}$!

**Правильная формула (формула Бекера):**
$$e^{A+B} = \lim_{n \to \infty} \left(e^{A/n} e^{B/n}\right)^n$$

---

### **Свойство 4: Производная**

$$\frac{d}{dt} e^{tA} = A e^{tA} = e^{tA} A$$

**Доказательство:**

$$\frac{d}{dt} e^{tA} = \frac{d}{dt} \sum_{k=0}^{\infty} \frac{(tA)^k}{k!} = \sum_{k=1}^{\infty} \frac{k t^{k-1} A^k}{k!} = \sum_{k=1}^{\infty} \frac{t^{k-1} A^k}{(k-1)!}$$

$$= A \sum_{j=0}^{\infty} \frac{(tA)^j}{j!} = A e^{tA}$$

---

### **Свойство 5: Детерминант**

$$\det(e^A) = e^{\text{tr}(A)}$$

где $\text{tr}(A)$ — след матрицы $A$.

---

## 📝 Примеры

### Пример 1: Диагональная матрица

$$A = \begin{pmatrix} \lambda_1 & 0 \\ 0 & \lambda_2 \end{pmatrix}$$

**Решение:**

$$e^{tA} = \begin{pmatrix} e^{t\lambda_1} & 0 \\ 0 & e^{t\lambda_2} \end{pmatrix}$$

**Объяснение:** Для диагональной матрицы степени тривиальны, так что:
$$A^k = \begin{pmatrix} \lambda_1^k & 0 \\ 0 & \lambda_2^k \end{pmatrix}$$

---

### Пример 2: Укосно-симметричная матрица

$$A = \begin{pmatrix} 0 & -\omega \\ \omega & 0 \end{pmatrix}$$

**Найти $e^{tA}$.**

**Решение:**

Так как $A^2 = \begin{pmatrix} -\omega^2 & 0 \\ 0 & -\omega^2 \end{pmatrix} = -\omega^2 I$, мы имеем:

$$e^{tA} = I + tA + \frac{(tA)^2}{2!} + \frac{(tA)^3}{3!} + \cdots$$

$$= I + tA - \frac{t^2 \omega^2}{2!} I - \frac{t^3 \omega^3}{3!} A + \cdots$$

$$= \left(1 - \frac{(t\omega)^2}{2!} + \frac{(t\omega)^4}{4!} - \cdots\right) I + \left(t - \frac{(t\omega)^3}{3!} + \cdots\right) \frac{A}{\omega}$$

$$= \cos(t\omega) I + \sin(t\omega) \frac{A}{\omega}$$

$$= \begin{pmatrix} \cos(t\omega) & -\sin(t\omega) \\ \sin(t\omega) & \cos(t\omega) \end{pmatrix}$$

---

### Пример 3: Коммутирующие матрицы

Пусть $A = \begin{pmatrix} 1 & 0 \\ 0 & 2 \end{pmatrix}$ и $B = \begin{pmatrix} 0 & 1 \\ 0 & 0 \end{pmatrix}$.

**Проверить $AB = BA$.**

$$AB = \begin{pmatrix} 1 & 0 \\ 0 & 2 \end{pmatrix} \begin{pmatrix} 0 & 1 \\ 0 & 0 \end{pmatrix} = \begin{pmatrix} 0 & 1 \\ 0 & 0 \end{pmatrix}$$

$$BA = \begin{pmatrix} 0 & 1 \\ 0 & 0 \end{pmatrix} \begin{pmatrix} 1 & 0 \\ 0 & 2 \end{pmatrix} = \begin{pmatrix} 0 & 2 \\ 0 & 0 \end{pmatrix}$$

**Вывод:** $AB \neq BA$, поэтому $e^{A+B} \neq e^A e^B$ в общем случае.

---

## 💪 Применение к системам ОДУ

### **Основное применение**

для системы с постоянными коэффициентами:

$$\frac{d\mathbf{x}}{dt} = A\mathbf{x}, \quad \mathbf{x}(0) = \mathbf{x}_0$$

**решение имеет вид:**

$$\mathbf{x}(t) = e^{tA} \mathbf{x}_0$$

**Матрица $e^{tA}$ является фундаментальной матрицей!**

---

## ❓ Интерактивные вопросы

**Q1:** Почему ряд для матричной экспоненты всегда сходится?
<details>
<summary>Ответ</summary>
Потому что ряд норм $\sum_{k=0}^{\infty} \frac{\|A\|^k}{k!} = e^{\|A\|}$ сходится, и он так же сходится абсолютно для любой матрицы.
</details>

**Q2:** Почему нельзя использовать $e^A e^B = e^{A+B}$ для некоммутирующих матриц?
<details>
<summary>Ответ</summary>
Потому что в доказательстве этого свойства домножаются ряды, что требует коммутативности. При $AB \neq BA$ результат неверн.
</details>

**Q3:** Как используется матричная экспонента для решения линейных систем?
<details>
<summary>Ответ</summary>
Фундаментальная матрица для системы $\frac{d\mathbf{x}}{dt} = A\mathbf{x}$ — это $X(t) = e^{tA}$, и решение явным дано: $\mathbf{x}(t) = e^{tA} \mathbf{x}_0$.
</details>