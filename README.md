# Java 8 Functional Utilities

[![Build Status](https://travis-ci.com/evolvedbinary/j8fu.png?branch=master)](https://travis-ci.com/evolvedbinary/j8fu)

Some extra utility classes for making functional programming with Java 8
just that little bit easier.

These utilities do not attempt to teach good programming practice or style,
rather then offer the string and glue which you sometimes need to put odd
things together.

## Functional Programming with Checked Exceptions

Often you may want to adopt functional programming with Java 8 in an
existing codebase, however the pervasive use of checked exceptions within
that codebase makes calling existing functions from within lambdas difficult
due to the restriction that a lambda cannot throw a checked exception.

We provide a series of classes to ease the transition from the non-functional
style of programming which predominated (in the Java ecosystem) before Java 8,
and the functional programming styles which are now possible with Java 8
and up.

### Example

Consider the following fictitious code where the legacy codebase has an `IndexManager`
which can find an on-disk index (`#getIndexId(String)`), and then read the index
into memory (`#readIndex(int)`).

```java
interface IndexManager {
   @Nullable public int getIndexId(final String name);
   public Map<byte[], byte[]> readIndex(final int indexId) throws IOException;
}
```

If we wanted to use those functions with a functional style call, we might attempt something like:
```java
Optional.ofNullable(indexManager.getIndex("index-1"))
    .map(indexId -> indexManager.readIndex(indexId));
```

Unfortunately that won't compile, because `#readIndex(int)` can throw an `IOException`, which
is a checked exception, and such checked exceptions are forbidden within a lambda expression.

So what can we do?

There are 3 options available to us, the first two are fairly straight forward and could be considered "*idiomatic*" Java:

1. Refactor the legacy code to remove checked exceptions, either:
    1. We could change them to un-checked exceptions, although this has its own pitfalls (TODO ref).
    2. We could instead return a Result class for the method calls, which indicated success or failure. We provide utilities for this in [Either](#Either).
2. Use `try/catch` within the lamda to handle the exception. Again, one such solution would be to return a Result class (such as [Either](#Either)) from the lamba. 
3. Make use of `FunctionE`! If we recognize that the lambda `indexId -> indexManager.readIndex(indexId)`, is really just a `Function<Integer, Map<byte[], byte[]>>` with an additional exception, we could create our own [Function Interface](https://docs.oracle.com/javase/8/docs/api/java/lang/FunctionalInterface.html) which just captures and rethrows the exception outside the lamda.

See `com.evolvedbinary.j8fu.function.*`.

# Either
