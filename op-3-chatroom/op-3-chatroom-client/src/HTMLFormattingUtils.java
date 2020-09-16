import java.util.Map;

public class HTMLFormattingUtils {
    public static String tag(String tag, String value, Map<String, String> css) {
        if(css == null || css.isEmpty()) return String.format("<%s>%s</%s>", tag, value, tag);
        else return String.format("<%s style=\"%s\">%s</%s>", tag, buildStyles(css), value, tag);
    }

    public static String tag(String tag, String value) {
        return tag(tag, value, null);
    }

    private static String buildStyles(Map<String, String> css) {
        StringBuilder builder = new StringBuilder();
        for(String key: css.keySet())
        {
            builder.append(key);
            builder.append(":");
            builder.append(css.get(key));
            builder.append(";");
        }
        return builder.toString();
    }

}
