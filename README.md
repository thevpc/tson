# tson
Type Safe Object Notation

`TSON` (pronounced Tyson) is a comprehensive superset of the JSON format that natively supports a wide range of simple and complex types.
It is ideal for use as a configuration format but is also suitable for communication and serialization purposes.

Supported features include :
- Primitive types: string, null, boolean, byte, short, int, long, bigint
- Floating-point numbers: float, double, decimal, bigdecimal
- Collections: arrays, objects (maps), matrices
- Complex numbers: using float, double, or bigdecimal components
- Named and parameterized arrays/objects
- Pairs and tuples (uplets)
- Named tuples (functions)
- Annotations on any element
- Expressions with standard operators (+, -, *, ...)
- Comments: both single-line and multi-line
- Multiline strings, regular expressions, char literals
- Binary and character streams
- Aliases and references
- Units on numeric values (e.g., 3GHz, 12kg)


# Why TSON?
## Why not JSON?
`JSON` lacks support for type-safe primitives, expressions, and even basic features like comments.

## Why not YAML?
While YAML is a superset of JSON, it relies on whitespace-sensitive syntax, which is error-prone.
`TSON` avoids these issues and offers richer type support in a cleaner, unambiguous format.

`TSON` is also more expressive and user-friendly, supporting:

- Multiline and raw strings
- Numeric values with units
- Complex types, matrices, and tuples
- Annotations, functions, expressions, and more


# Examples
A full-featured TSON object:

```tson
// This is a comment before a TSON object
{
    name: "some name",

    // Annotation can be added to any element
    @ThisIsMyAnnotation(something)
    short-observation: ¶ this is a single-line string
    
    long-observation: """
                       this is a multiline string
                      """
    
    weight: 12.3kg      // double with unit
    full: 3L%           // long with unit

    3.141592            // unkeyed value

    // Named and parameterized objects
    parent: item {
        (): "empty uplet"
        (1): "singleton"
    }

    parent: item(name: "new item") {
        (): "empty uplet"
        (1): "singleton"
    }

    // Named parameterized array
    someArray: item(name: "new item")[
        (): "empty uplet"
        "second item"
        "third item"
    ]
}
```


Minimal values:

```tson
12
```

```tson
null
```

``tson`` file may contain a more complex value such as

Simple Object

```tson
{
    name:"some name",
    value:12.3,
}
```


# Elements
## Primitive types
Atomic data values representing basic categories such as integers, floating-point numbers, booleans, null, and characters. They support various formats and suffixes to specify exact types and units, and do not contain internal structure.

```
    Byte       : 12b ,  -12B ,  0x12b,  0x12B,  012b,  0b011001b,  0b011001B
    Short      : 12s ,  -12S ,  0x12s,  0x12s,  012s,  0b011001s,  0b011001S
    Int        : 120 ,  -122 ,  0x123,  0x120,  0123,  0b0110011,  0b0110001
    Long       : 12L ,  -12L ,  0x12L,  0x12L,  012L,  0b011001L,  0b011001L
(*) BigInt     : 12G ,  -12G ,  0x12G,  0x12G,  012G,  0b011001G,  0b011001G
    Float      : 1.0f,  -1.2E-3F
    Double     : 1.0 ,  -1.2E-3 NaN -Inf +Inf +Bound -Bound
(*) Decimal    : 1.0d ,  -1.2E-3D
(*) BigDecimal : 1.0c ,  -1.2E-3c
    time       : 12:11:00
    date       : 2022-12-11
    datetime   : 2022-12-11 12:11:00
    boolean    : true, false
    null       : null
```

## Strings
A sequence of characters that can be represented in multiple forms, including single-line, multi-line, raw, or regular expression formats. It supports optional delimiters and preserves whitespace and escape sequences as defined by the syntax.

```
    "Hello\n"                       // normal string
    'c'                             // character
    'Hello world'                   // simple string
    /a*/                            // regular expression
    """Multiline string"""          // multiline (triple quotes)
    ```Multiline string```          // alternative multiline
    ¶ This is also a single-line string
```


## Array
An ordered structure with optional name and optional parameters, allowing any values (including duplicates), and supporting an internal body (a collection of elements). It preserves element order and can contain heterogeneous elements.

```
[12, 13]

someName[                      // named array
    12,
    a: 13,
    (1,2): [1, 2, 3]
]

someName(a, b, c)[             // parameterized named array
    12,
    a: 13,
    (1,2): [1, 2, 3]
]                 
```

## Uplet
An ordered structure with optional name, allowing any values and duplicate keys, without internal body
```
(12, 13)                       // unnamed tuple
someFunction(12, 13)           // named tuple (like a function)
```

## Objects
An ordered structure with optional name and optional parameters, allowing any values (including duplicate keys), supporting an internal body composed of key-value pairs or other elements. It preserves order and can contain heterogeneous entries.

```
{                              // basic object
    12,
    a: 13,
    (1,2): [1, 2, 3]
}

someName {                    // named object
    12,
    a: 13,
    (1,2): [1, 2, 3]
}

someName(a, b, c) {           // parameterized named object
    12,
    a: 13,
    (1,2): [1, 2, 3]
}
```


## Annotations
A metadata construct that can be attached to any TSON element. It consists of a name and optional parameters, providing additional descriptive information without affecting the element’s structure or value.

```
    @someAnnotation(a,b,c)
    someName(a,b,c){
        12,
        a:13
        (1,2):[1, 2, 3]
    }
```

## Char Streams
A sequence of characters enclosed between delimiters, allowing inclusion of arbitrary text content without interpretation. It preserves the exact character sequence, including whitespace and line breaks.

```
    ^SomeDelimiter[ Anything you cna think of]SomeDelimiter
```

## Binary Streams
A sequence of binary data encoded as Base64, enclosed between user-defined delimiters. It preserves the exact binary content while representing it in a text-friendly format suitable for inclusion in TSON documents.
```
    ^SomeDelimiter[Base64]SomeDelimiter
```

## Aliases & References
Provides a mechanism to define a value once and reuse it multiple times within a TSON document. An alias assigns a label to a value, and a reference uses that label to refer back to the original value, enabling reuse and avoiding duplication.

```
    a: @(#ref)1234     // define alias
    b: &ref            // use alias
```

## Comments
Textual annotations included within a TSON document to improve readability and documentation. They can be single-line or multi-line and are preserved by the parser without affecting the data structure or semantics.

```
    /* Multi-line
       comment */
    a: 1234,
    // Single-line comment
    b: &a
```

## Expressions
Sequences of operands and operators that represent computations or value derivations within TSON. They support standard arithmetic, logical, and comparison operators, enabling declarative calculation or evaluation embedded in the data structure.

```
    a+1
    a=1
    a:=1
    a<=1
    a==>1
    a**1
    a^1    
    a:=example    
    a=example    
```
