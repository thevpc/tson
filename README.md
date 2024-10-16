# tson
Type Safe Object Notation

``tson`` (read Tyson) is a comprehensive, json-like format, that supports out of the box several simple and complex types.
``tson`` is ideal as a configuration format but can also be used as for communication (serialization).

Supported features include :
* null values and boolean type
* byte, short, int, long and bigint types
* float, double, bigdecimal types
* arrays, objects (maps)
* complex types (float, double and bigdecimal based complex values)
* arrays and matrices (of any other type)
* expressions (+,-,*...)
* annotations
* function calls
* json object
* uplets


# Rationale
Why not JSON. Well json does not support type safe primitives and is very limited. For instance it does not even support comments.
Why not YAML. YAML is a superset of json. So is ``tson``, however, unlike yaml, tson does not support space based syntax and hence 
is less error prone. Besides, ``tson`` is ways more comprehensive and support ways more types and features.
``tson`` is also more user friendly with support of multiline strings, regular expressions, numeric values with units (think of 3GHz for three Giga Hertz for instance), 
complex values, and so on.

# Some Examples
The simplest ``tson`` file may contain a single literal, such as

```tson
12
```
or even a bar null value
```tson
null
```

``tson`` file may contain a more complex value such as

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
    string          : "Hello\n"
    char            : 'a'
    simple string   : 'some string'
    regexp          : /a*/
    multiline string: """
                    some string
                    """
```


## Array

```
    array          :   [12, 13]

    named array    :   someName[
                            12,
                            a:13
                            (1,2):[1, 2, 3]
                        ]

    named function array    : 
                        someName(a,b,c)[
                            12,
                            a:13
                            (1,2):[1, 2, 3]
                        ]
                        
```

## Uplet
```
    (12, 13)
```

## Objects
```
   object            : {
                            12,
                            a:13
                            (1,2):[1, 2, 3]
                        }
   named object      : someName{
                            12,
                            a:13
                            (1,2):[1, 2, 3]
                        }
   named function object   : 
                    someName(a,b,c){
                            12,
                            a:13
                            (1,2):[1, 2, 3]
                        }
```


## Annotation
```
    @someAnnotation(a,b,c)
    someName(a,b,c){
        12,
        a:13
        (1,2):[1, 2, 3]
    }
```

## Char Stream

```
    ^SomeDelimiter[ Anything you cna think of]SomeDelimiter
```

## Binary Stream

```
    ^SomeDelimiter[Base64]SomeDelimiter
```

## Alias

```
    {
        a: @(#ref)1234
        b:&ref
    }
```

## Comments

```
    {  /* 
            this is a comment 
       */
        a:1234
        b:&a
    }
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
```
