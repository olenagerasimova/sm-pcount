# pcount

Small library to count message parts.

## How to use
`MessageSplitMode` enum describes all possible at the moment message split algorithms, to calculate how many
parts message contains simple call `partsIn()` method on any constant. For example:
```java
String message = "Avoid composite names of variables, like timeOfDay, firstItem, or httpRequest. I mean with both - class variables and in-method ones. A variable name should be long enough to avoid ambiguity in its scope of visibility, but not too long if possible. A name should be a noun in singular or plural form.";
MessageSplitMode.UDHI_8.partsIn(message); //2
MessageSplitMode.PAYLOAD.partsIn(message); //1
```
Message split mode can be also parsed into enum item from string with the help of static method `MessageSplitMode.parse(String mode)`.

## How to make changes
Create new branch, make changes, send a merge request. Someone will review
your changes and apply them to the `master` branch shortly, provided
they don't violate quality standards. To avoid frustration, before
sending us your merge request please run full Maven build:
```
$ mvn clean install -Pqulice
```
Note that 
* this small library is created according to EO standards, if you do not know what it is, please, visit https://www.elegantobjects.org/
* `qulice` https://www.qulice.com/ is used to check code style, do not dare to disable it, write clean code instead

## Unit-tests
Unit test is junit4 test case started by `maven-surefire-plugin`.
Test method name should be read as an English sentence: `TargetClass` can `methodName`
as described in "Test Method Names" section in this post: https://www.yegor256.com/2014/04/27/typical-mistakes-in-java-code.html

For assertions "Hamcrest" library is used, so it's good practice to design each test method
as single assertion with matching expected result.

```java
@Test
public void returnZero() {
  MatcherAssert.assertThat(
    new TargetClass().action(),
    new IsEqual<>(0)
  );
}
```

If all test methods are named properly javadocs for them can be skipped.