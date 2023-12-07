package github.kyxap.com.utils;

public class StringParser {
    public static String extractVersionFromHtlmString(final String htmlAsString) {
        final int str = htmlAsString.indexOf("gridItem driverName");
        final String subRez = htmlAsString.substring(str, htmlAsString.indexOf("nowrap", str));
        final String secondLookup = "<td class=\"gridItem\">";
        final int strIndex2 = subRez.indexOf(secondLookup);
        final String verParsed = subRez.substring(strIndex2 + secondLookup.length(), subRez.indexOf("</td>", strIndex2));

        Logger.log(String.format("Latest extracted version from html: %s", verParsed));
        return verParsed;
    }
}
