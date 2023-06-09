package cc.mousse.steward.whitelist.utils;

import cc.mousse.steward.whitelist.common.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PhineasZ
 */
public class StrUtil {
  private static final char QUOTE = '\"';

  private StrUtil() {}

  /**
   * 获取合规角色名称
   *
   * @param originalName 群名片
   * @return idx0：角色名数量范围内；idx1：角色名数量范围外
   */
  public static List<List<String>> getLegal(String originalName) {
    return getLegal(originalName, true);
  }

  /**
   * @param originalName 群名片
   * @param limitFlag 是否启用名称数量限制
   * @return idx0：角色名数量范围内；idx1：角色名数量范围外
   */
  public static List<List<String>> getLegal(String originalName, boolean limitFlag) {
    var names = new ArrayList<List<String>>(2);
    names.add(new ArrayList<>());
    names.add(new ArrayList<>());
    var chars = originalName.toCharArray();
    var builder = new StringBuilder();
    int count = 0;
    for (var i = 0; i <= chars.length; i++) {
      if (i < chars.length && wordLegalCheck(chars[i])) {
        builder.append(chars[i]);
      } else if (builder.length() != 0) {
        if (count < Integer.parseInt(Config.getIdLimit()) || !limitFlag) {
          // 角色名数量范围内
          names.get(0).add(builder.toString());
        } else {
          names.get(1).add(builder.toString());
        }
        builder = new StringBuilder();
        count++;
      }
    }
    return names;
  }

  public static String getString(String s, Object... args) {
    var res = new StringBuilder();
    var idx = 0;
    var ss = s.split("\\{}");
    for (var str : ss) {
      res.append(str);
      if (idx < args.length) {
        res.append(args[idx++]);
      }
    }
    return res.toString();
  }

  private static boolean wordLegalCheck(char c) {
    return c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '_';
  }

  public static String removeQuotes(String s) {
    if (s != null) {
      if (s.charAt(0) == QUOTE) {
        s = s.substring(1);
      }
      if (s.charAt(s.length() - 1) == QUOTE) {
        s = s.substring(0, s.length() - 1);
      }
    }
    return s;
  }

  public static <T> String splice(String s, List<T> data) {
    var builder = new StringBuilder();
    var ss = s.split("\\$");
    var iter = data.iterator();
    for (var str : ss) {
      builder.append(str);
      if (iter.hasNext()) {
        builder.append(iter.next());
      }
    }
    return builder.toString();
  }
}
