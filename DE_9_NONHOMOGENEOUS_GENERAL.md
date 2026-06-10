# 9️⃣ Линейные неоднородные системы n-го порядка. Общее решение

## 📖 Определение

**Линейная неоднородная система** имеет вид:

$$\frac{d\mathbf{x}}{dt} = A(t)\mathbf{x} + \mathbf{f}(t)$$

где $A(t)$ — матрица, $\mathbf{f}(t)$ — вектор неоднородности.

---

## 🎯 Объединённая система

**О��ъединённая система** (неоднородная + однородная):

$$\begin{pmatrix} \frac{d\mathbf{x}}{dt} \\ \frac{d\xi}{dt} \end{pmatrix} = \begin{pmatrix} A & \mathbf{f} \\ 0 & 0 \end{pmatrix} \begin{pmatrix} \mathbf{x} \\ \xi \end{pmatrix}$$

где $\xi$ — вспомогательная переменная.

---

## ✅ Общее решение неоднородной системы

**Теорема:** Общее решение линейной неоднородной системы есть сумма:

$$\mathbf{x}(t) = \mathbf{x}_h(t) + \mathbf{x}_p(t)$$

где:
- $\mathbf{x}_h(t)$ — **общее решение однородной системы**
- $\mathbf{x}_p(t)$ — **частное решение неоднородной системы**

**Общее решение однородной:**
$$\mathbf{x}_h(t) = c_1 \mathbf{x}_1(t) + c_2 \mathbf{x}_2(t) + \cdots + c_n \mathbf{x}_n(t) = X(t)\mathbf{c}$$

где $\mathbf{x}_1, \ldots, \mathbf{x}_n$ — ФСР, $X(t)$ — фундаментальная матрица.

---

## 📝 Пример

### Одномерная неоднородная система

$$\frac{d\mathbf{x}}{dt} = \begin{pmatrix} -1 & 1 \\ 0 & -1 \end{pmatrix} \mathbf{x} + \begin{pmatrix} e^{-t} \\ 0 \end{pmatrix}$$

**Уда 1: Однородная система**

$$\frac{d\mathbf{x}}{dt} = \begin{pmatrix} -1 & 1 \\ 0 & -1 \end{pmatrix} \mathbf{x}$$

Собственные значения: $\lambda = -1$ (кратность 2)

Общее решение:
$$\mathbf{x}_h(t) = c_1 e^{-t} \begin{pmatrix} 1 \\ 0 \end{pmatrix} + c_2 e^{-t} \begin{pmatrix} t \\ 1 \end{pmatrix}$$

**Шаг 2: Общее решение неоднородной**

$$\mathbf{x}(t) = c_1 e^{-t} \begin{pmatrix} 1 \\ 0 \end{pmatrix} + c_2 e^{-t} \begin{pmatrix} t \\ 1 \end{pmatrix} + \mathbf{x}_p(t)$$

частное решение находится методом вариации постоянных.

---

## ❓ Интерактивные вопросы

**Q1:** Почему общее решение неоднородной системы это сумма однородного и частного решений?
<details>
<summary>Ответ</summary>
Это следует из линейности системы: если $\mathbf{x}_h$ и $\mathbf{x}_p$ — решения, то их сумма тоже решение.
</details>