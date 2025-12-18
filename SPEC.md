# TSON Specification

## 1. Overview

TSON (Type Safe Object Notation) is a superset of JSON that supports rich types, expressions, annotations, named/parameterized objects and arrays, tuples, streams, and more. It is designed as a pivot format for configuration, serialization, and transformation between formats like JSON, YAML, and XML.

---

## 2. Primitive Types

### 2.1 Numbers

Supports integers, floating point, complex, special values, with optional suffixes and units.

* Decimal integers: `12`, `-34`
* Hexadecimal: `0x1A`, `0xFF_L`
* Octal: `0o17`
* Binary: `0b1010`
* Long: `12L`
* BigInt: `12G`
* Float/Double: `1.0f`, `-2.5E3`
* BigDecimal/Decimal: `1.0c`, `2.5D`
* Complex: `3+4i`
* Special: `NaN`, `PInf`, `NInf`
* With units: `12kg`, `3GHz`

**Examples:**

```tson
weight: 70kg
big: 12345678901234567890G
complex: 3+4i
pi: 3.1415926535
```

### 2.2 Strings

Supports multiple types:

* Single-line ¶ strings: `¶hello world` (cannot span multiple lines)
* Quoted strings: `'hello'`, `"world"`
* Triple-quoted: `'''multi\nline'''`, `"""another\nmulti"""`
* Backtick strings: `` `raw string` ``
* Escapes:

    * `\\` → `\`
    * `\'`, `\"`, `````
    * `\n`, `\t`, etc.

**Examples:**

```tson
single: ¶hello
quoted: "world"
multi: '''line1
line2'''
raw: `C:\Users\path`
```

---

## 3. Uplets (Tuples)

* Unnamed: `(1,2,3)`
* Named (function-like): `sum(1,2)`
* Can contain any element (primitives, objects, arrays, annotations, expressions)

**Examples:**

```tson
(1,2,3)
pair("a", "b")
nested(sum(1,2), [1,2], {x:1})
```

---

## 4. Objects

### 4.1 Unnamed Objects

* `{}` → empty object
* Can contain:

    * Key-value pairs: `key:value`
    * Unkeyed values: `123`
    * Arrays: `[]`
    * Uplets: `()`
    * Nested objects: `{...}`
    * Expressions: `a+b`

**Examples:**

```tson
{
    a:1,
    b,
    c: [],
    d() {},
    e { f:2 },
    (1,2),
    ¶str,
    @anno(123)
}
```

### 4.2 Named Objects

* `name { ... }`
* Named + parameterized: `name(a,b) { ... }`

**Examples:**

```tson
person(name:"John") {
    age:30,
    contacts: ["email","phone"]
}
```

---

## 5. Arrays

* Unnamed: `[1,2,3]`
* Named: `arr [1,2]`
* Parameterized: `arr(a,b) [1,2]`
* Can contain any element, including objects, arrays, uplets, annotations, expressions
* Row separator for matrices: `;`

**Examples:**

```tson
[1, 2, 3]
matrix [
    1,2,3;
    4,5,6
]
mixedArray [1, "two", {a:1}, (1,2)]
```

---

## 6. Annotations

* `@name(params...)`
* Can attach to any element, including uplets, objects, arrays, primitives
* Parameters are TSON elements themselves

**Examples:**

```tson
@info(version:1)
person { name: "Alice" }
@validate( min:0, max:100 )
[1,2,3]
```

---

## 7. Aliases and References

* Define alias: `a: @(#ref) value`
* Use reference: `b: &ref`

**Example:**

```tson
base: @(#x) { a:1 }
copy: &x
```

---

## 8. Expressions

* Arithmetic: `+`, `-`, `*`, `/`, `%`
* Comparison: `<`, `<=`, `==`, `>`
* Assignment: `:=`, `=`
* Can appear anywhere: objects, arrays, uplets

**Example:**

```tson
result: a + b * c
threshold := 100
```

---

## 9. Binary & Char Streams

* Char stream: `^delim[ ... ]delim`
* Binary stream: Base64-encoded `^delim[ ... ]delim`

**Example:**

```tson
^C[Hello World]C
^B[SGVsbG8gV29ybGQ=]B
```

---

## 10. Mixed Examples

````tson
document {
    numbers [1,2,3.0,0xFF_L]
    info @meta(author:"Alice", version:1)
    person(name:"Bob") {
        age:30
        contacts: ["email","phone"]
    }
    matrix [
        1,2,3;
        4,5,6
    ]
    pair(a:b)
    complex: 3+4i
    text ¶single-line
    raw ```raw string```
}
````
