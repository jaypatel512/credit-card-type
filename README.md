# CreditCardType

This is a utility library which returns possible credit card types based on the input. This is heavily copy/pasted from [Braintree Card Form](https://github.com/braintree/android-card-form) and [Braintree Credit Card Type Node library](https://github.com/braintree/credit-card-type).
The card images used in this library are thanks to [Minh Do](https://www.iconfinder.com/minhm2m), [Nazarrudin Ansyari](https://www.iconfinder.com/nazarr) and [Linh Pham Thi Dieu](https://www.iconfinder.com/phdieuli).

# Setup

## Gradle

Step 1. Add it in your root build.gradle at the end of repositories:
```gradle
    allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
```

Step 2. Add the dependency
```gradle
    dependencies {
        compile 'com.github.jaypatel512:credit-card-type:master-SNAPSHOT'
    }
```

### Maven

Step 1. Add repository in pom.xml
```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
```

Step 2. Add the dependency
```xml
    <dependency>
        <groupId>com.github.jaypatel512</groupId>
        <artifactId>credit-card-type</artifactId>
        <version>master-SNAPSHOT</version>
    </dependency>
```

# Sample
```java
CreditCardType[] types = CreditCardType.forCardNumber("6");
System.out.println(Arrays.deepToString(types)); // Should return [DISCOVER, MAESTRO, UNIONPAY]

types = CreditCardType.forCardNumber("62");
System.out.println(Arrays.deepToString(types)); // Should return [MAESTRO, UNIONPAY]

types = CreditCardType.forCardNumber("4111111111");
System.out.println(Arrays.deepToString(types)); // Should return [VISA]

CreditCardType type = types[0];

type.getSecurityCodeName();   // Should return CVV
type.getSecurityCodeLength(); // Should return 3 (size of CVV)
type.getMinCardLength();      // Minimum Card length 16
type.getMaxCardLength();      // Maximum Card length 16
type.getSpaceIndices();       // Indices useful for formatting
```
