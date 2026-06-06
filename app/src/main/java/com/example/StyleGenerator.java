package com.example;

import java.util.ArrayList;
import java.util.List;

public class StyleGenerator {

    // Helper method to convert standard characters using high-plane Unicode offsets
    private static String transformText(String text, int upperOffset, int lowerOffset, int digitOffset) {
        if (text == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            int codePoint = text.codePointAt(i);
            if (codePoint >= 'A' && codePoint <= 'Z') {
                sb.append(Character.toChars(codePoint + upperOffset));
            } else if (codePoint >= 'a' && codePoint <= 'z') {
                sb.append(Character.toChars(codePoint + lowerOffset));
            } else if (codePoint >= '0' && codePoint <= '9' && digitOffset != 0) {
                sb.append(Character.toChars(codePoint + digitOffset));
            } else {
                sb.append(Character.toChars(codePoint));
            }
            if (Character.isSurrogate(text.charAt(i))) {
                i++; // Skip second half of surrogate pair if any
            }
        }
        return sb.toString();
    }

    // Custom transform helper with manual overrides if needed
    private static String transformCombining(String text, char combiningChar) {
        if (text == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            sb.append(c);
            if (Character.isLetterOrDigit(c)) {
                sb.append(combiningChar);
            }
        }
        return sb.toString();
    }

    // Specific Font Translators
    public static String getBoldSerif(String text) {
        return transformText(text, 119808 - 65, 119834 - 97, 120782 - 48);
    }

    public static String getItalicSerif(String text) {
        return transformText(text, 119860 - 65, 119886 - 97, 0);
    }

    public static String getBoldItalicSerif(String text) {
        return transformText(text, 119912 - 65, 119938 - 97, 0);
    }

    public static String getScriptBold(String text) {
        return transformText(text, 120016 - 65, 120042 - 97, 0);
    }

    public static String getGothicBold(String text) {
        return transformText(text, 120172 - 65, 120198 - 97, 0);
    }

    public static String getDoubleStruck(String text) {
        // Outline Font
        return transformText(text, 120120 - 65, 120146 - 97, 120792 - 48);
    }

    public static String getSansNormal(String text) {
        return transformText(text, 120224 - 65, 120250 - 97, 120802 - 48);
    }

    public static String getSansBold(String text) {
        return transformText(text, 120224 - 65, 120250 - 97, 120812 - 48);
    }

    public static String getSansItalic(String text) {
        return transformText(text, 120276 - 65, 120302 - 97, 0);
    }

    public static String getSansBoldItalic(String text) {
        return transformText(text, 120328 - 65, 120354 - 97, 0);
    }

    public static String getMonospace(String text) {
        return transformText(text, 120432 - 65, 120458 - 97, 120822 - 48);
    }

    public static String getCircles(String text) {
        return transformText(text, 9398 - 65, 9424 - 97, 9312 - 49);
    }

    public static String getSquares(String text) {
        return transformText(text, 127280 - 65, 127280 - 65, 0);
    }

    public static String getParenthesis(String text) {
        return transformText(text, 9372 - 97, 9372 - 97, 0);
    }

    public static String getStrikethrough(String text) {
        return transformCombining(text, '\u0336');
    }

    public static String getUnderline(String text) {
        return transformCombining(text, '\u0332');
    }

    public static String getSlash(String text) {
        return transformCombining(text, '\u0338');
    }

    public static String getSmallCaps(String text) {
        if (text == null) return "";
        String normal = "abcdefghijklmnopqrstuvwxyz";
        String smallCaps = "ᴀʙᴄᴅᴇғɢʜɪᴊᴋʟᴍɴᴏᴘǫʀsᴛᴜᴠᴡxʏᴢ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int index = normal.indexOf(Character.toLowerCase(c));
            if (index != -1) {
                sb.append(smallCaps.charAt(index));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String getGreekStyle(String text) {
        if (text == null) return "";
        String normal = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Greek = "αв¢∂єƒgнιנкℓмησρqяѕтυνωχуzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int index = normal.indexOf(c);
            if (index != -1 && index < Greek.length()) {
                sb.append(Greek.charAt(index));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String getCurly(String text) {
        if (text == null) return "";
        String normal = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String curly = "αв¢∂єfgнιјкℓмησpqrѕтυvωxуzαв¢∂єfgнιјкℓмησpqrѕтυvωxуz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int index = normal.indexOf(c);
            if (index != -1 && index < curly.length()) {
                sb.append(curly.charAt(index));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    // Static list generator to create 100+ styles
    public static List<String> generateAllStyles(String input) {
        List<String> list = new ArrayList<>();
        if (input == null || input.trim().isEmpty()) {
            return list;
        }

        input = input.trim();

        // 1. Base fonts
        String bold = getBoldSerif(input);
        String italic = getItalicSerif(input);
        String boldItalic = getBoldItalicSerif(input);
        String script = getScriptBold(input);
        String gothic = getGothicBold(input);
        String outline = getDoubleStruck(input);
        String sans = getSansNormal(input);
        String sansBold = getSansBold(input);
        String sansItalic = getSansItalic(input);
        String sansBoldItalic = getSansBoldItalic(input);
        String mono = getMonospace(input);
        String circles = getCircles(input);
        String squares = getSquares(input);
        String smallCaps = getSmallCaps(input);
        String greek = getGreekStyle(input);
        String curly = getCurly(input);
        String strikethrough = getStrikethrough(input);
        String underline = getUnderline(input);
        String slash = getSlash(input);

        // Add raw bases
        list.add(bold);
        list.add(italic);
        list.add(boldItalic);
        list.add(script);
        list.add(gothic);
        list.add(outline);
        list.add(sans);
        list.add(sansBold);
        list.add(sansItalic);
        list.add(sansBoldItalic);
        list.add(mono);
        list.add(circles);
        list.add(squares);
        list.add(smallCaps);
        list.add(greek);
        list.add(curly);
        list.add(strikethrough);
        list.add(underline);
        list.add(slash);

        // Standard Spaced style
        StringBuilder spaced = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            spaced.append(input.charAt(i)).append(" ");
        }
        String spacedStr = spaced.toString().trim();
        list.add(spacedStr);
        list.add(getBoldSerif(spacedStr));
        list.add(getCircles(spacedStr));

        // 2. Gaming, Clan Emblem, and Borders Decorations
        // We'll iterate through our fonts and wrap them inside stylish gaming decorations
        String[] fontsToWrap = {input, bold, italic, boldItalic, script, gothic, outline, circles, smallCaps, greek, curly, spacedStr};

        // A rich collection of gaming borders and decorations
        String[][] decorations = {
            {"꧁༒ ", " ༒꧂"},
            {"亗 ", " 亗"},
            {"༺ ", " ༻"},
            {"⚡ ", " ⚡"},
            {"☠ ", " ☠"},
            {"★ ", " ★"},
            {"✿ ", " ✿"},
            {"◥ ", " ◤"},
            {"『 ", " 』"},
            {"ツ ", " シ"},
            {"☯ ", " ☯"},
            {"♣ ", " ♣"},
            {"♠ ", " ♠"},
            {"♥ ", " ♥"},
            {"♦ ", " ♦"},
            {"☬ ", " ☬"},
            {"⎝⎝✧ ", " ✧⎠⎠"},
            {"『OP』", "☠"},
            {"⚡ ", " ⚡"},
            {"⚔ ", " ⚔"},
            {"♔ ", " ♔"},
            {"♕ ", " ♕"},
            {"❃ ", " ❃"},
            {"❄ ", " ❄"},
            {"☄ ", " ☄"},
            {"🔥 ", " 🔥"},
            {"🌙 ", " 🌙"},
            {"🧸 ", " 🧸"},
            {"🦖 ", " 🦖"},
            {"🐾 ", " 🐾"},
            {"👾 ", " 👾"},
            {"🛸 ", " 🛸"},
            {"🚀 ", " 🚀"},
            {"🎯 ", " 🎯"},
            {"🎪 ", " 🎪"},
            {"🎭 ", " 🎭"},
            {"🎨 ", " 🎨"},
            {"🎸 ", " 🎸"},
            {"👑 ", " 👑"},
            {"💎 ", " 💎"},
            {"✨ ", " ✨"},
            {"🎈 ", " 🎈"},
            {"🎐 ", " 🎐"},
            {"🎵 ", " 🎵"},
            {"🎶 ", " 🎶"},
            {"✔ ", " ✔"},
            {"✖ ", " ✖"},
            {"[• ", " •]"},
            {"【", "】"},
            {"《", "》"},
            {"(◕‿◕) ", " (◕‿◕)"},
            {"(づ｡◕‿‿◕｡)づ ", " (づ｡◕‿‿◕｡)づ"},
            {" (•_•) ", " (•_•)"},
            {" ¯\\_(ツ)_/¯ ", ""},
            {" ̿̿ ̿̿ ̿̿ ̿'̿'\\̵͇̿̿\\ ", " ̿̿ ̿̿ ̿̿ ̿'̿'\\̵͇̿̿\\"},
            {"▄︻デ ", " ═━━"},
            {"☣ ", " ☣"},
            {"☯️ ", " ☯️"},
            {"⚜️ ", " ⚜️"},
            {"✴️ ", " ✴️"},
            {"❇️ ", " ❇️"},
            {"▫️ ", " ▫️"},
            {"▪️ ", " ▪️"},
            {"✧ ", " ✧"},
            {"✪ ", " ✪"},
            {"❈ ", " ❈"},
            {"❉ ", " ❉"},
            {"❊ ", " ❊"},
            {"† ", " †"},
            {"‡ ", " ‡"},
            {"•´¯`• ", " •´¯`•"},
            {"`·.¸¸.·´´¯`··._.· ", " ·._.··`¯´´·.¸¸.·`"},
            {"•°¤*(¯`★´¯)*¤° ", " °¤*(¯`★´¯)*¤°•"},
            {"•۞• ", " •۞•"},
            {"۩ ", " ۩"},
            {"✖_✖ ", " ✖_✖"},
            {"-_- ", " -_-"},
            {"^o^ ", " ^o^"},
            {"^_- ", " ^_-"},
            {"•｡• ", " •｡•"},
            {"◦•●◉✿ ", " ✿◉●•◦"},
            {"░▒▓█ ", " █▓▒░"},
            {" ▃▅▆█ ", " █▆▅▃ "},
            {"▌│█║▌║ ", " ║▌║▌│█"},
            {"【⚡】", "【⚡】"},
            {"『⭐️』", "『⭐️』"},
            {"☁️ ", " ☁️"},
            {"⚓️ ", " ⚓️"},
            {"🎗️ ", " 🎗️"},
            {"🧸 ", " 🧸"},
            {"🧬 ", " 🧬"},
            {"🔮 ", " 🔮"},
            {"🩸 ", " 🩸"},
            {">> ", " <<"},
            {"|| ", " ||"},
            {"== ", " =="},
            {"## ", " ##"},
            {"$$ ", " $$"},
            {"%% ", " %%"}
        };

        // Standard dynamic styled generator to make sure we hit > 100 styles easily
        int fontIdx = 0;
        for (String[] dec : decorations) {
            String font = fontsToWrap[fontIdx];
            list.add(dec[0] + font + dec[1]);
            fontIdx = (fontIdx + 1) % fontsToWrap.length;
        }

        // Add additional combinations if we need more than 100 guaranteed styles
        // Currently we've added ~20 basic fonts + ~100 decorations = ~120 styles!
        // Let's mix standard ones to enrich the count.
        for (int i = 0; i < Math.min(15, fontsToWrap.length); i++) {
            list.add("★彡 " + fontsToWrap[i] + " 彡★");
            list.add("☠️☠️ " + fontsToWrap[i] + " ☠️☠️");
            list.add("⚡⛓️ " + fontsToWrap[i] + " ⛓️⚡");
        }

        return list;
    }
}
