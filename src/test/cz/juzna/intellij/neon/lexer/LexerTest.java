package test.cz.juzna.intellij.neon.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.psi.tree.IElementType;
import com.intellij.testFramework.UsefulTestCase;
import cz.juzna.intellij.neon.lexer.NeonLexer;
import cz.juzna.intellij.neon.util.Pair;
import org.jetbrains.annotations.NonNls;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static cz.juzna.intellij.neon.lexer.NeonTokenTypes.*;

/**
 *
 */
public class LexerTest extends UsefulTestCase {
	// which lexer to test
	private static NeonLexer createLexer() {
		return new NeonLexer();
	}

	/*** helpers ***/

	/**
	 * Test that lexing a given piece of code will give particular tokens
	 *
	 * @param text Sample piece of neon code
	 * @param expectedTokens List of tokens expected from lexer
	 */
	protected static void doTest(@NonNls String text, @NonNls Pair<IElementType, String>[] expectedTokens) {
		Lexer lexer = createLexer();
		doTest(text, expectedTokens, lexer);
	}

	private static void doTest(String text, Pair<IElementType, String>[] expectedTokens, Lexer lexer) {
		lexer.start(text);
		int idx = 0;
		while (lexer.getTokenType() != null) {
			if (idx >= expectedTokens.length) fail("Too many tokens from lexer; unexpected " + lexer.getTokenType());

			Pair expected = expectedTokens[idx++];
			assertEquals("Wrong token", expected.first, lexer.getTokenType());

			String tokenText = lexer.getBufferSequence().subSequence(lexer.getTokenStart(), lexer.getTokenEnd()).toString();
			assertEquals(expected.second, tokenText);
			lexer.advance();
		}

		if (idx < expectedTokens.length) fail("Not enough tokens from lexer, expected " + expectedTokens.length + " but got only " + idx);
	}



	/*** tests here ***/

	@Test
	public void testSimple() throws Exception {
		doTest("name: 'Jan'", new Pair[] {
				Pair.of(NEON_LITERAL, "name"),
				Pair.of(NEON_COLON, ":"),
				Pair.of(NEON_WHITESPACE, " "),
				Pair.of(NEON_STRING, "'Jan'"),
		});
	}

	@Test
	public void testTabAfterKey() throws Exception {
		doTest("name: \t'Jan'\nsurname:\t \t 'Dolecek'", new Pair[] {
				Pair.of(NEON_LITERAL, "name"),
				Pair.of(NEON_COLON, ":"),
				Pair.of(NEON_WHITESPACE, " \t"),
				Pair.of(NEON_STRING, "'Jan'"),
				Pair.of(NEON_INDENT, "\n"),
				Pair.of(NEON_LITERAL, "surname"),
				Pair.of(NEON_COLON, ":"),
				Pair.of(NEON_WHITESPACE, "\t \t "),
				Pair.of(NEON_STRING, "'Dolecek'"),
		});
	}

	@Test
	public void testArray1() throws Exception {
		doTestFromFile();
	}

	@Test
	public void testArray2() throws Exception {
		doTestFromFile();
	}

	@Test
	public void testArray3() throws Exception {
		doTestFromFile();
	}

	@Test
	public void testArray4() throws Exception {
		doTestFromFile();
	}

	@Test
	public void testArray5() throws Exception {
		doTestFromFile();
	}

	@Test
	public void testArray6() throws Exception {
		doTestFromFile();
	}

	@Test
	public void testArray10() throws Exception {
		doTestFromFile();
	}

	@Test
	public void testArrayEntity() throws Exception {
		doTestFromFile();
	}

	@Test
	public void testArrayIndentedFile() throws Exception {
		doTestFromFile();
	}

	@Test
	public void testArrayNoSpaceColon() throws Exception {
		doTestFromFile();
	}

	@Test
	public void testMultiline1() throws Exception {
		doTestFromFile();
	}

	public void doTestFromFile() throws Exception {
		String code = doLoadFile("src/test/data/parser", getTestName(false) + ".neon");

		Lexer lexer = createLexer();
		StringBuilder sb = new StringBuilder();

		lexer.start(code);
		while (lexer.getTokenType() != null) {
			sb.append(lexer.getTokenType().toString());
			sb.append("\n");
			lexer.advance();
		}

//		System.out.println(sb);

		// Match to original
		String lexed = doLoadFile("src/test/data/parser", getTestName(false) + ".lexed");
		assertEquals(lexed, sb.toString());
	}

	private static String doLoadFile(String myFullDataPath, String name) throws IOException {
		String fullName = myFullDataPath + File.separatorChar + name;
		String text = FileUtil.loadFile(new File(fullName), CharsetToolkit.UTF8);
		text = StringUtil.convertLineSeparators(text);
		return text;
	}

}
