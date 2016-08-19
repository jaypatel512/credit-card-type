# CreditCardType

This is a utility library which returns possible credit card types based on the input. This is heavily copy/pasted from [Braintree Card Form](https://github.com/braintree/android-card-form) and [Braintree Credit Card Type Node library](https://github.com/braintree/credit-card-type).

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
