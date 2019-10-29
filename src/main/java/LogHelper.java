import org.slf4j.*;

import com.google.common.base.*;

public class LogHelper {
  private final Object object;

  public LogHelper(Object object) {
    this.object = object;
  }

  public void log(Object... args) {
    // List<Object> parts = Lists.newArrayList();
    // parts.add(new Date());
    // parts.add(object);
    // for (Object arg : args)
    //   parts.add(arg);
    // System.out.println(Joiner.on(" ").useForNull("null").join(parts));
    LoggerFactory.getLogger(object.getClass()).info(Strings.repeat("{} ", args.length), args);
  }

}