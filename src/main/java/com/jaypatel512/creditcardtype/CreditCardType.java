package com.jaypatel512.creditcardtype;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Card types and related formatting and validation rules.
 */
public enum CreditCardType {

	VISA("^4\\d*",
			16, 16,
			3, "CVV",
			new int[] {4, 8, 12}),
	MASTERCARD("^(5|5[1-5]\\d*|2|22|222|222[1-9]\\d*|2[3-6]\\d*|27[0-1]\\d*|2720\\d*)",
			16, 16,
			3, "CVC",
			new int[] {4, 8, 12}),
	DISCOVER("^6(0|01|011\\d*|5\\d*|4|4[4-9]\\d*)?",
			16, 16,
			3, "CID",
			new int[] {4, 8, 12}),
	AMEX("^3[47]\\d*",
			15, 15,
			4, "CID",
			new int[] {4, 10}),
	DINERS_CLUB("^3((0([0-5]\\d*)?)|[689]\\d*)?",
			14, 14,
			3, "CVV",
			new int[] {4, 10}),
	JCB("^((2|21|213|2131\\d*)|(1|18|180|1800\\d*)|(3|35\\d*))",
			16, 16,
			3, "CVV",
			new int[] {4, 8, 12}),
	MAESTRO("^((5((0|[6-9])\\d*)?)|((6([0-9]\\d*)?)))",
			12, 19,
			3, "CVC",
			new int[] {4, 8, 12}),
	UNIONPAY("^6(2\\d*)?",
			16, 19,
			3, "CVN",
			new int[] {4, 8, 12}),
	UNKNOWN("\\d+",
			12, 19,
			3, "CVV",
			new int[] {4, 8, 12}),
	EMPTY("^$",
			12, 19,
			3, "CVV", null);

	private final Pattern pattern;
	private final int minCardLength;
	private final int maxCardLength;
	private final int securityCodeLength;
	private final String securityCodeName;
	private int[] spaceIndices;

	CreditCardType(String regex, int minCardLength, int maxCardLength, int securityCodeLength,
	               String securityCodeName, int[] spaceIndices) {
		if (minCardLength <= 0) {
			throw new IllegalArgumentException("minCardLength cannot be lower or equal to 0.");
		}
		if (maxCardLength < minCardLength) {
			throw new IllegalArgumentException("maxCardLength cannot be lower than minCardLength.");
		}

		this.pattern = Pattern.compile(regex);
		this.minCardLength = minCardLength;
		this.maxCardLength = maxCardLength;
		this.securityCodeLength = securityCodeLength;
		this.securityCodeName = securityCodeName;
		this.spaceIndices = spaceIndices;
	}

	/**
	 * Returns the card type matching this account, or {@link CreditCardType}
	 * for no match.
	 * <p/>
	 * A partial account type may be given, with the caveat that it may not have enough digits to
	 * match.
	 */
	public static CreditCardType[] forCardNumber(String cardNumber) {
		return forCardNumber(cardNumber, false);
	}

	public static CreditCardType[] forCardNumber(String cardNumber, boolean strict) {
		List<CreditCardType> matchedCardTypes = new ArrayList<CreditCardType>();
		for (CreditCardType creditCardType : values()) {
			if (strict && creditCardType.validate(cardNumber)) {
				matchedCardTypes.add(creditCardType);
			} else if (creditCardType.validatePattern(cardNumber)) {
				matchedCardTypes.add(creditCardType);
			}
		}
		// This removes UNKNOWN if anything else is matched
		if (matchedCardTypes.size() > 1) {
			matchedCardTypes.remove(UNKNOWN);
		}
		return matchedCardTypes.toArray(new CreditCardType[matchedCardTypes.size()]);
	}

	/**
	 * Returns the card type matching this account, or {@link CreditCardType}
	 * for no match.
	 * <p/>
	 * A partial account type may be given, with the caveat that it may not have enough digits to
	 * match.
	 */
	public static CreditCardType forCardNumberStrict(String cardNumber) {
		CreditCardType[] creditCardTypes = forCardNumber(cardNumber, true);
		if (creditCardTypes.length > 0) {
			return creditCardTypes[0];
		}
		return null;
	}

	/**
	 * @return The regex matching this card type.
	 */
	public Pattern getPattern() {
		return pattern;
	}

	/**
	 * @return The android resource id for the security code name for this card type.
	 */
	public String getSecurityCodeName() {
		return securityCodeName;
	}

	/**
	 * @return The length of the current card's security code.
	 */
	public int getSecurityCodeLength() {
		return securityCodeLength;
	}

	/**
	 * @return minimum length of a card for this {@link CreditCardType}
	 */
	public int getMinCardLength() {
		return minCardLength;
	}

	/**
	 * @return max length of a card for this {@link CreditCardType}
	 */
	public int getMaxCardLength() {
		return maxCardLength;
	}

	/**
	 * @return the locations where spaces should be inserted when formatting the card in a user
	 * friendly way. Only for display purposes.
	 */
	public int[] getSpaceIndices() {
		return spaceIndices;
	}

	/**
	 * @param cardNumber The card number to validate.
	 * @return {@code true} if this card number is locally valid.
	 */
	public boolean validate(String cardNumber) {
		if (cardNumber == null || cardNumber.isEmpty()) {
			return false;
		}
		return validateLength(cardNumber) && validatePattern(cardNumber);
	}

	public boolean validateLength(String cardNumber) {
		final int numberLength = cardNumber.length();
		if (numberLength < minCardLength || numberLength > maxCardLength) {
			return false;
		}
		return true;
	}

	public boolean validatePattern(String cardNumber) {
		return pattern.matcher(cardNumber).matches();
	}
}
