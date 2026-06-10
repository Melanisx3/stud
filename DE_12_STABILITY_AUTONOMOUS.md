# 12️⃣ Основные понятия теории устойчивости (автономные системы)

## 📖 Определение

Автономная система:
$$\frac{d\mathbf{x}}{dt} = \mathbf{f}(\mathbf{x})$$

**Особенность:** Не зависит от $t$, поэтому анализ устойчивости снижен.

---

## 💡 Устойчивость автономных систем

### **Нулевое решение устойчиво**

if for any $\varepsilon > 0$ there exists $\delta(\varepsilon) > 0$ such that:
$$\|\mathbf{x}_0\| < \delta \Rightarrow \|\mathbf{x}(t; \mathbf{x}_0)\| < \varepsilon \quad \forall t > 0$$

### **Глобальная асимптотическая устойчивость**

$$\lim_{t \to \infty} \mathbf{x}(t; \mathbf{x}_0) = \mathbf{0} \quad \forall \mathbf{x}_0 \in \mathbb{R}^n$$

---

## 🎯 Равновесные точки

**Определение:** Точка $\mathbf{x}^* \in \mathbb{R}^n$ называется **равновесной точкой** (или **неподвижной точкой**), если:

$$\mathbf{f}(\mathbf{x}^*) = \mathbf{0}$$

**Пример:** Найти равновесные точки системы:
$$\frac{dx}{dt} = y, \quad \frac{dy}{dt} = -\sin(x)$$

Решение: $y = 0, \sin(x) = 0 \Rightarrow (x, y) = (n\pi, 0), n \in \mathbb{Z}$

---

## ❓ Интерактивные вопросы

**Q1:** Как найти равновесные точки автономной системы?
<details>
<summary>Ответ</summary>
Нужно решить систему $\mathbf{f}(\mathbf{x}) = \mathbf{0}$, т.е. положить все правые части равными нулю.
</details>