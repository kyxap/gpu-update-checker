package github.kyxap.com.utils;

public class StringParser {
    public static String extractVersionFromHtlmString(String htmlAsString) {
        int str = htmlAsString.indexOf("gridItem driverName");
        String subRez = htmlAsString.substring(str, htmlAsString.indexOf("nowrap", str));
        String secondLookup = "<td class=\"gridItem\">";
        int strIndex2 = subRez.indexOf(secondLookup);
        String verParsed = subRez.substring(strIndex2 + secondLookup.length(), subRez.indexOf("</td>", strIndex2));

        System.out.printf("Latest extracted version from html: %s\n", verParsed);
        return verParsed;
    }
}
