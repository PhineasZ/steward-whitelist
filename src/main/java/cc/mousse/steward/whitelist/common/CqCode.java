package cc.mousse.steward.whitelist.common;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * CQ Code编码
 *
 * @author PhineasZ
 */
@Data
public class CqCode {
  private Type type;
  private Map<String, String> data;

  private CqCode(Type type) {
    this.type = type;
    data = new HashMap<>();
  }

  public static CqCode reply() {
    return new CqCode(Type.REPLY);
  }

  public static CqCode at() {
    return new CqCode(Type.AT);
  }

  public CqCode putQq(String userId) {
    this.data.put("qq", userId);
    return this;
  }

  public CqCode putId(String id) {
    this.data.put("id", id);
    return this;
  }

  @Override
  public String toString() {
    var builder = new StringBuilder();
    builder.append("[CQ:").append(type.toString().toLowerCase()).append(",");
    var entries = data.entrySet();
    for (var entry : entries) {
      builder.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
    }
    builder.setCharAt(builder.length() - 1, ']');
    return builder.toString();
  }

  public enum Type {
    /**
     * REPLY: 回复<br>
     * AT: @某人
     */
    REPLY,
    AT
  }
}
