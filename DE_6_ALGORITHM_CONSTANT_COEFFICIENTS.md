# 6️⃣ Построение ФСР для линейной однородной системы с постоянными коэффициентами

## 🎯 Общая стратегия

для системы:
$$\frac{d\mathbf{x}}{dt} = A\mathbf{x}$$

с постоянной матрицей $A$:

1. Найти **характеристическое уравнение**: $\det(A - \lambda I) = 0$
2. Найти **собственные значения** $\lambda_1, \lambda_2, \ldots, \lambda_n$
3. Для каждого $\lambda_i$ найти **собственные векторы**: $(A - \lambda_i I)\mathbf{v}_i = \mathbf{0}$
4. Построить решения: $\mathbf{x}_i(t) = e^{\lambda_i t} \mathbf{v}_i$
5. Решения образуют **ФСР**

---

## ✅ Основная теорема

**Теорема:** Пусть $\lambda_1, \lambda_2, \ldots, \lambda_n$ — разные не не бобошные собственные значения матрицы $A$, и соответствующие собственные векторы $\mathbf{v}_1, \ldots, \mathbf{v}_n$ линейно независимы.

Тогда вектор-функции:
$$\mathbf{x}_i(t) = e^{\lambda_i t} \mathbf{v}_i, \quad i = 1, 2, \ldots, n$$

бормируют **фундаментальную систему решений**.

---

## 📝 Пример

### Отыскание режима накладка

$$\frac{d\mathbf{x}}{dt} = \begin{pmatrix} -2 & 1 \\ 1 & -2 \end{pmatrix} \mathbf{x}$$

**Шаг 1: Характеристическое уравнение**

$$\det \begin{pmatrix} -2 - \lambda & 1 \\ 1 & -2 - \lambda \end{pmatrix} = (-2-\lambda)^2 - 1 = \lambda^2 + 4\lambda + 3 = 0$$

$$\lambda_1 = -1, \quad \lambda_2 = -3$$

**Шаг 2: Собственные векторы**

для $\lambda_1 = -1$:
$$(A + I)\mathbf{v}_1 = \begin{pmatrix} -1 & 1 \\ 1 & -1 \end{pmatrix} \mathbf{v}_1 = \mathbf{0}$$

$$\mathbf{v}_1 = \begin{pmatrix} 1 \\ 1 \end{pmatrix}$$

для $\lambda_2 = -3$:
$$(A + 3I)\mathbf{v}_2 = \begin{pmatrix} 1 & 1 \\ 1 & 1 \end{pmatrix} \mathbf{v}_2 = \mathbf{0}$$

$$\mathbf{v}_2 = \begin{pmatrix} 1 \\ -1 \end{pmatrix}$$

**Шаг 3: ФСР**

$$\mathbf{x}_1(t) = e^{-t} \begin{pmatrix} 1 \\ 1 \end{pmatrix}, \quad \mathbf{x}_2(t) = e^{-3t} \begin{pmatrix} 1 \\ -1 \end{pmatrix}$$

**Общее решение:**
$$\mathbf{x}(t) = c_1 e^{-t} \begin{pmatrix} 1 \\ 1 \end{pmatrix} + c_2 e^{-3t} \begin{pmatrix} 1 \\ -1 \end{pmatrix}$$

---

## ❓ Интерактивные вопросы

**Q1:** Почему $e^{\lambda t} \mathbf{v}$ является решением системы?
<details>
<summary>Ответ</summary>
Потому что $\frac{d}{dt}(e^{\lambda t}\mathbf{v}) = \lambda e^{\lambda t}\mathbf{v} = A(e^{\lambda t}\mathbf{v})$ вследствие того, что $A\mathbf{v} = \lambda \mathbf{v}$.
</details>

**Q2:** Как отыскать собственные векторы для нестрицтного собственного значения?
<details>
<summary>Ответ</summary>
Нужно решить систему $(A - \lambda I)\mathbf{v} = \mathbf{0}$ и найти ее нетривиальные решения.
</details>