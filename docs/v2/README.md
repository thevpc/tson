# TSON v2 — Human‑Friendly, Strongly‑Typed Superset of JSON

TSON (pronounced “ty‑son”) is a structured text format for data that preserves human readability while providing built‑in type safety, rich literals, annotations, and DSL‑friendly constructs. TSON is both a configuration format and a data interchange format with strong typing baked into the syntax.

Where JSON is stringly‑typed (everything without quotes is just a number or string), TSON makes type information explicit and resilient at parse time. It supports primitives with type suffixes, structured literals, streams, annotations, and more — all while being a strict superset of JSON. That means every valid JSON document is valid TSON.
---

# 🚀 What's New in v2.0?

TSON aims to solve the common pain points of JSON and YAML:
- JSON has no comments, limited types, and no metadata.
- YAML adds readability at the cost of ambiguous indentation and implicit typing.
- TSON gives you explicit, deterministic types, comments, metadata through annotations, and structure without whitespace sensitivity.
 
Unlike formats that try to infer types (or silently coerce them), TSON’s parser understands your intent from the tokens themselves:
- `12u32` is a 32‑bit unsigned integer,
- `2025‑12‑01` is a date literal,
- `3.14kg` is a decimal with a unit suffix,

and all are preserved faithfully through parse → transform → emit cycles.

---

## Core Principles
TSON is built around a small set of design goals:
- **Fail‑never**: Invalid or unusual tokens are preserved so tools and editors can report diagnostics without losing data.
- **Token‑preserving**: Spaces, newlines, and comments are retained so TSON round‑trips cleanly through formatters and tooling. 
- **Literal‑first**: Escape sequences and backslash magic are minimized — literal text is treated as literal, reducing surprises. 
- **JSON‑compatible**: Any JSON document is valid TSON; you can adopt TSON gradually.

## High‑Level Features

### Native Types
- Booleans, numbers with bit‑width suffixes,
- precise decimal and integer types,
- date/time literals,
- units on numbers (e.g., 12ms, 3GHz).

### Structured Literals
- Objects `({ … })`,
- Arrays `([ … ])`,
- Tuples `(...)`,
- Named and parameterized containers.

### Annotations
- Metadata on any structure using '@', without affecting the value.

### Comments
- Inline (`// …`) and block (`/*...*/`) comments allowed everywhere.

### Expressions
- TSON preserves operator sequences in value positions for DSL use.

### Streams
- Binary or text streams with explicit delimiters and optional encodings.

## Example TSON (v2)

A valid TSON configuration:

```tson
// A simple configuration
appName: "ExampleApp"
version: 1.2.0

// Port with annotation
@required
serverPort: 8080u16

server(host:"localhost", port:8080u16,disabled) {

    timeout: 30ms
    retry: 4/1mn

    database(type:"postgres") {
        host: "db.local"
        port: 5432u16
    }

    features: [
        "logging",
        "metrics"
    ]
}

// Unit suffix on numbers
timeout: 30ms

// A raw text block without escape sequences
¶¶ This text can contain
¶¶ newlines and backslashes
¶¶ without escaping.

// Another raw text block
"""
This text can contain
newlines and backslashes
without escaping.
"""

// Annotations on values
@range(1, 100)
retryCount: everyMinute(3)

// An array with mixed, typed values
features: [
    "logging",
    "metrics",
    "tracing"
]

tasks : 
  • task1
  • ¶ task 2
  • {name:"task",value:3}

```

This example shows how TSON keeps developer intent alive in the syntax — types are explicit, documentation can live alongside code, and tools can preserve structures precisely.

---

## How Is This Different from v1?

TSON v1 (as seen in older repos) focused on typed JSON with enhancements such as:
- complex numbers,
- matrices,
- named arrays,
- parameterized objects,
- units and functional constructs.

TSON v2 expands and refines these ideas with:
- broader literal intelligence (dates, temporal types),
- richer support (block strings, ordered lists, unordered lists),
- deeper whitespace and comment preservation,
- improved DSL capability,
- token‑preserving round trips for tooling.

Together, this makes TSON v2 not just a data format, but a tool‑friendly configuration and DSL layer that still feels like plain text to authors.

## Getting Started
- View the full TSON v2 Specification for syntax and semantics ([SPEC.md](SPEC.md)).
- Use a TSON parser/serializer library for your platform (use [Nuts reference implementation for Java](https://github.com/thevpc/nuts))
- Start with a JSON file — it’ll already be valid TSON.
- Add type suffixes, annotations, and comments as you grow your schema.

## Where to Learn More

The canonical, detailed specification is in the TSON v2 specs (see [SPEC.md](SPEC.md) in this repository). This README just scratches the surface; the full spec covers formal grammar, lexing rules, and every literal type.

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
