package interpreter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Linter {

	// formatted code
	private String newCode;

	// CONSTANTS

	// LIMITS

	// limit size of a chunk of any legal character
	// -> if the limit is 5, code will be formatted like this
	// ++++++++ -> +++++ +++
	private final int CHAR_CHUNK_LIMIT = 5;

	public Linter(String code) {

		// new code is set equal to the old code
		// in order for it to be edited, rather created chunk by chunk
		this.newCode = code;

		// clean the new code off
		// obvious redundant whitespace
		this.newCode.replaceAll("  ", "");

		// seperate chunks of chars
		CharSequence incrChunk = (CharSequence) String.format("%0" + CHAR_CHUNK_LIMIT + "d", 0).replace('0', '+');
		CharSequence decrChunk = (CharSequence) String.format("%0" + CHAR_CHUNK_LIMIT + "d", 0).replace('0', '-');

		// only adding and subtracting needs to be
		// seperated -> only these characters will be
		// written in larger quantities
		this.newCode = this.newCode.replace(incrChunk, (CharSequence) new String(incrChunk + " "));
		this.newCode = this.newCode.replace(decrChunk, (CharSequence) new String(decrChunk + " "));

		// add line breaks before every loop characters
		this.newCode = this.newCode.replace((CharSequence) "[", (CharSequence) "\n[\n");
		this.newCode = this.newCode.replace((CharSequence) "]", (CharSequence) "\n]\n");

		// add a line break before the pointer shifting chars
		// only if there's none following up

		// add whitespace to the loops
		Pattern loopRegex = Pattern.compile("\\[[\\.\\,\\+\\-<>\\s]*?]");
		Matcher finds = loopRegex.matcher((CharSequence) this.newCode);

		// iterate over finds and replace in this.code
		System.out.println("loops:");
		while (finds.find()) {

			// Old unformatted found loop
			String oldLoop = finds.group();

			// in every find replace the line breaks
			String newLoop = oldLoop.replaceAll("\n[^\\]\\.\\,<>\\+\\-]", "\n\t");

			// replace the loops by the formatted loops
			// in the new code
			this.newCode = this.newCode.replace((CharSequence) oldLoop, (CharSequence) newLoop);
		}
	}

	public String getNewCode() {
		return this.newCode;
	}
}
