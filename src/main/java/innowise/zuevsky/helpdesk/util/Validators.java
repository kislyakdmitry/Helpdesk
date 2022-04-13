package innowise.zuevsky.helpdesk.util;

import static lombok.AccessLevel.PRIVATE;

import java.util.function.Supplier;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class Validators {

	public static void validateThat(boolean condition, Supplier<RuntimeException> exceptionSupplier) {
		if (!condition) {
			throw exceptionSupplier.get();
		}
	}
}
