# TSON — Type-Safe Object Notation

TSON (pronounced *Tyson*) is a typed, expressive data language designed for configuration, DSLs, and structured data exchange.

It extends JSON with a real type system, expressions, annotations, reusable references, and first-class mathematical structures—without relying on whitespace-sensitive syntax or implicit typing rules.

TSON is meant for humans to write and machines to trust.

---

## Why TSON?

JSON is intentionally minimal. That minimalism becomes friction the moment you need:

- Comments
- Precise numeric types
- Units (`250ms`, `3GHz`, `12kg`)
- Dates and times
- Reusable values
- Derived or computed configuration values

YAML attempts to solve this, but does so with indentation rules, implicit typing, and syntactic ambiguity that make tooling and validation fragile.

TSON takes a different approach:  
**explicit syntax, explicit types, explicit intent.**

---

## A Quick Taste

A valid TSON configuration:

```tson
server {
    host: "localhost"
    port: 8080
    timeout: 250ms
    maxLoad: cores * 0.75
    startedAt: 2024-12-11 09:30:00
}
```

This is not JSON with comments bolted on.  
This is configuration that can *express meaning*.

---

## First-Class Matrices

TSON supports matrices as native structures, not as nested arrays pretending to be math.

```tson
transform {
    rotation: [
        [1,     0,      0],
        [0,  0.866,  -0.5],
        [0,   0.5,   0.866]
    ]
}
```

Matrices are ordered, explicit, and structurally meaningful.

---

## Core Ideas

TSON is built around a few simple principles:

- **Strong typing**  
  Integers, decimals, big numbers, dates, times, complex numbers, and units are first-class.

- **Declarative computation**  
  Expressions and named tuples allow values to be derived, not duplicated.

- **Structural clarity**  
  Objects, arrays, tuples, and matrices are explicit and ordered—no encoding tricks, no conventions.

- **Metadata without hacks**  
  Annotations attach information to any element without altering its value.

- **Human-friendly syntax**  
  Multiline strings, comments, raw text, and streams are built in.

---

## What TSON Can Represent

TSON natively supports:

- Primitive numeric types (`byte`, `short`, `int`, `long`, `bigint`)
- Floating-point and decimal types (`float`, `double`, `decimal`, `bigdecimal`)
- Dates, times, and datetimes
- Strings, characters, regular expressions, and multiline text
- Arrays, objects, and matrices (rectangular, ordered)
- Tuples and named tuples (function-like constructs)
- Complex numbers
- Aliases and references
- Annotations
- Expressions with standard operators
- Binary and character streams
- Units on numeric values

---

## A Rich Example

```tson
// Full-featured TSON object
{
    name: "example"

    @Info(source: "sensor-A")
    weight: 12.3kg

    base: 100
    threshold: base * 1.2

    stiffness: [
        [12.0, -3.0],
        [-3.0, 12.0]
    ]

    force: (10, 5)

    // Expression semantics depend on the host environment
    displacement: stiffness^-1 * force

    parent: item(name: "new item") {
        (): "empty uplet"
        (1): "singleton"
    }
}
```

---

## Typical Use Cases

TSON is well suited for:

- Application and system configuration
- Domain-specific languages
- Scientific and engineering data
- Tooling formats and automation pipelines
- Teaching structured data and computation

---

## Tooling & Ecosystem

TSON is designed to be:

- Parsed deterministically
- Validated strictly
- Transformed into JSON, XML, YAML, or domain models
- Embedded in existing systems

Reference implementations and tooling are provided in this repository.

---

## Documentation

- **Language Specification** → `docs/spec.md`  
  Formal semantics and language rules.

- **Grammar** → `docs/grammar/`  
  EBNF / JavaCC / ANTLR definitions.

- **Reference Manual** → `docs/reference/`  
  Types, expressions, matrices, units, streams, and annotations.

- **Examples** → `examples/`

---

## Status

TSON is actively developed and used in production and educational tools.  
The language evolves carefully, with backward compatibility as a priority.

---

## License

Apache Licence 2.0
