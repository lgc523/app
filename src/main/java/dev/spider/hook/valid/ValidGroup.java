package dev.spider.hook.valid;

public class ValidGroup {
    public interface DefaultGroup {

    }

    public interface FooGroup extends DefaultGroup {

    }

    public interface BarGroup extends FooGroup {

    }
}
