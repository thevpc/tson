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
```
(12, 13)                       // unnamed tuple
someFunction(12, 13)           // named tuple (like a function)
```

## Objects
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
```
    @someAnnotation(a,b,c)
    someName(a,b,c){
        12,
        a:13
        (1,2):[1, 2, 3]
    }
```

## Char Streams

```
    ^SomeDelimiter[ Anything you cna think of]SomeDelimiter
```

## Binary Streams

```
    ^SomeDelimiter[Base64]SomeDelimiter
```

## Aliases & References

```
    a: @(#ref)1234     // define alias
    b: &ref            // use alias
```

## Comments

```
    /* Multi-line
       comment */
    a: 1234,
    // Single-line comment
    b: &a
```

## Expressions

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
