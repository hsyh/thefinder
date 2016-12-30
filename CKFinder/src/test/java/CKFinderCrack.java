import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by brett on 29/12/2016.
 */
public class CKFinderCrack {


    static Pattern PATT1 = Pattern.compile("\\\\x([\\dA-Za-z]{2})");
    static Pattern PATT2 = Pattern.compile("\\\\(\\d{3})");


    public static void main(String[] args) throws FileNotFoundException {

        // 第一步
//        toOctal();

        // 第二步
        toCharacter();

    }

    public static void toOctal() throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("WebApp/src/main/webapp/ckfinder/ckfinder_org.js"), "utf-8");
        while (scanner.hasNextLine()) {
            String txt = scanner.nextLine();

            StringBuilder sb = new StringBuilder(txt);

            // 十六进制 转换 八进制
            int d = 0;
            String o;
            Matcher matcher = PATT1.matcher(sb);
            while (matcher.find()) {
//                System.out.println(matcher.group(1));
                d = Integer.parseInt(matcher.group(1), 16);
                o = d < 8 ? "\\00" + Integer.toOctalString(d) : (d < 64 ? "\\0" + Integer.toOctalString(d) : "\\" + Integer.toOctalString(d));

//                System.out.println(o);
                sb.replace(matcher.start(), matcher.end(), o);
            }

            System.out.println(sb);
        }
    }



    public static void toCharacter() throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("WebApp/src/main/webapp/ckfinder/ckfinder_org_octal.js"), "utf-8");
        while (scanner.hasNextLine()) {
            String txt = scanner.nextLine();

            StringBuilder sb = new StringBuilder(txt);

            // 八进制 转换 字符
            int d = 0, diff = 0;
            String o;
            Matcher matcher = PATT2.matcher(txt);
            while (matcher.find()) {
//                System.out.println(matcher.group(1));
                d = Integer.parseInt(matcher.group(1), 8);
//                System.out.println((char)d);
                sb.replace(matcher.start() - diff, matcher.end() - diff, String.valueOf((char)d));
                diff += 3;
            }

            System.out.println(sb);
        }

    }


}
