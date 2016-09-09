package com.jaypatel512.creditcardtype;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

import static junit.framework.Assert.assertTrue;

@RunWith(value = Parameterized.class)
public class CreditCardTypeTest {

	private static final int MIN_MIN_CARD_LENGTH = 12;
	private static final int MAX_MAX_CARD_LENGTH = 19;

	private static final int MIN_SECURITY_CODE_LENGTH = 3;
	private static final int MAX_SECURITY_CODE_LENGTH = 4;

	private String cardNumber;
	private CreditCardType creditCardType;


	// Inject via constructor
	public CreditCardTypeTest(String cardNumber, CreditCardType creditCardType) {
		this.cardNumber = cardNumber;
		this.creditCardType = creditCardType;
	}

	@Parameterized.Parameters(name = "{index}: creditCard({0}) should be of type {1}")
	public static Collection<Object[]> data() {
		return RandomCreditCards.getRandomCreditCards();
	}

	@Test
	public void allParametersSane() {
		for (final CreditCardType creditCardType : CreditCardType.values()) {
			final int minCardLength = creditCardType.getMinCardLength();
			assertTrue(String.format("%s: Min card length %s too small",
					creditCardType, minCardLength), minCardLength >= MIN_MIN_CARD_LENGTH);

			final int maxCardLength = creditCardType.getMaxCardLength();
			assertTrue(String.format("%s: Max card length %s too large",
					creditCardType, maxCardLength), maxCardLength <= MAX_MAX_CARD_LENGTH);

			assertTrue(String.format("%s: Min card length %s greater than its max %s",
					creditCardType, minCardLength, maxCardLength),
					minCardLength <= maxCardLength
			);

			final int securityCodeLength = creditCardType.getSecurityCodeLength();
			assertTrue(String.format("%s: Unusual security code length %s",
					creditCardType, securityCodeLength),
					securityCodeLength >= MIN_SECURITY_CODE_LENGTH &&
							securityCodeLength <= MAX_SECURITY_CODE_LENGTH
			);

			assertTrue(String.format("%s: No Security code resource declared", creditCardType),
					!creditCardType.getSecurityCodeName().isEmpty());

			if (creditCardType != CreditCardType.UNKNOWN && creditCardType != CreditCardType.EMPTY) {
				final Pattern pattern = creditCardType.getPattern();
				final String regex = pattern.toString();
				assertTrue(String.format("%s: Pattern must start with ^", creditCardType),
						regex.startsWith("^"));
			}
		}
	}

	@Test
	public void testForCardNumber() {
		final CreditCardType[] actualTypes = CreditCardType.forCardNumber(cardNumber);
		assertTrue(actualTypes.length >= 1); // Either EMPTY or UNKNOWN
		assertTrue(String.format("CreditCardType.forCardNumber failed for %s", cardNumber), Arrays.asList(actualTypes).contains(creditCardType));
	}
}
