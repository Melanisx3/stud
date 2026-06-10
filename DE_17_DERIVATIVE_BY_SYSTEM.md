# 17️⃣ Положительно определенные функции. Производная в силу системы

## ud83d� Определение

**Производная в силу системы** для системы:

$$\frac{d\mathbf{x}}{dt} = \mathbf{f}(t, \mathbf{x})$$

Определяется как:

$$\frac{dV}{dt}\bigg|_{sys} = \frac{\partial V}{\partial t} + \nabla V \cdot \mathbf{f}$$

---

## ud83d� Это ссылка на функцию $V$ среди на траекториях системы.

**Основная формула:**

$$\frac{dV}{dt} = \frac{\partial V}{\partial t} + \sum_{i=1}^n \frac{\partial V}{\partial x_i} f_i(t, \mathbf{x})$$

---

## ud83d� Пример

$$\frac{dx}{dt} = -y - x^3, \quad \frac{dy}{dt} = x - y^3$$

Выберем $V = \frac{x^2 + y^2}{2}$

$$\frac{dV}{dt} = x\frac{dx}{dt} + y\frac{dy}{dt} = x(-y - x^3) + y(x - y^3) = -x^4 - y^4 < 0$$

→ ствемы **асимптотически устойчивы**.